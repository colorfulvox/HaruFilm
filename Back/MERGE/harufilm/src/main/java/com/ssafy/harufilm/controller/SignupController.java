package com.ssafy.harufilm.controller;

import com.ssafy.harufilm.fcm.FcmController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.harufilm.common.ErrorResponseBody;
import com.ssafy.harufilm.common.MessageBody;
import com.ssafy.harufilm.dto.account.SignupRequestDto;
import com.ssafy.harufilm.entity.User;
import com.ssafy.harufilm.service.user.UserService;

import static com.ssafy.harufilm.fcm.FcmController.*;

@RestController
@RequestMapping("/api/account")
public class SignupController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> SignupCheck(@RequestBody SignupRequestDto signupRequestDto) throws Exception {
        User user; // 중복된 아이디 검사용 변수
        try {
            user = userService.getuserbyId(signupRequestDto.getUserid());
            // Userid로 DB에 해당 유저 가져오기
        } catch (Exception e) {

            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,
                    "Interanl Server Error,계정 생성 실패"));
        }

        if (user != null)// 만약 null이 아닐경우 이미 있는 아이디
        {
            return ResponseEntity.status(401).body(ErrorResponseBody.of(401, false, "중복된 아이디가 있습니다."));
        }

        String userpassword = signupRequestDto.getUserpassword();
        signupRequestDto.setUserpassword(passwordEncoder.encode(userpassword));
        // 비밀번호 암호화

        try {
            userService.userNewSave(signupRequestDto);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,
                    "Interanl Server Error,계정 생성 실패"));
            // TODO: handle exception
        }

        // to : 받을 기기의 fcm토큰, title : 푸시 제목, message : 푸시 내용
        // try {
        // FcmController.FCMMessaging("a", "회원 가입 완료", "계정 생성이 완료되었습니다.");
        // } catch (Exception e) {
        // return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,
        // "FCM 에러"));
        // }
        return ResponseEntity.status(200).body(MessageBody.of(true, "계정 생성이 완료되었습니다."));
    }
}

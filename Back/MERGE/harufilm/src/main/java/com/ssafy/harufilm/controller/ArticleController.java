package com.ssafy.harufilm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.harufilm.common.ErrorResponseBody;
import com.ssafy.harufilm.common.MessageBody;
import com.ssafy.harufilm.dto.article.ArticleRequestDto;
import com.ssafy.harufilm.service.article.ArticleService;


@RestController
@RequestMapping("/api/article")
public class ArticleController {
    
    @Autowired
    ArticleService articleService;

    @PostMapping("/create")
    public ResponseEntity<?> setArticle(@RequestBody ArticleRequestDto articleRequestDto){
        try{
            articleService.articleSave(articleRequestDto);
        }catch(Exception e){
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,
            "Interanl Server Error, 글 저장 실패"));
        }
        return ResponseEntity.status(200).body(MessageBody.of(true, "글 저장 성공"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteArticle(@RequestBody int articleidx){
        try{
            articleService.articleDelete(articleidx);
        }catch(Exception e){
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,
            "Interanl Server Error, 글 삭제 실패"));
        }
        return ResponseEntity.status(200).body(MessageBody.of(true, "글 삭제 성공"));
    }

    @GetMapping("/mylist")
    public ResponseEntity<?> mylist(@RequestBody int userpid){
        try{
            articleService.getmyArticle(userpid);
        }catch(Exception e){
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,
            "Interanl Server Error, 글 목록 불러오기 실패"));
        }
        return ResponseEntity.status(200).body(MessageBody.of(true, "글 목록 불러오기 성공"));
    }

        @GetMapping("/list")
    public ResponseEntity<?> list(@RequestBody int userpid){
        try{
            articleService.getFollowedArticleList(userpid);
        }catch(Exception e){
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,
            "Interanl Server Error, 글 목록 불러오기 실패"));
        }
        return ResponseEntity.status(200).body(MessageBody.of(true, "글 목록 불러오기 성공"));
    }

    @GetMapping("/getarticle")
    public ResponseEntity<?> getArticle(@RequestBody int articleidx){
        try{
            articleService.findByArticleidx(articleidx);
        }catch(Exception e){
            return ResponseEntity.status(500).body(ErrorResponseBody.of(500, false,
            "Interanl Server Error, 글 불러오기 실패"));
        }
        return ResponseEntity.status(200).body(MessageBody.of(true, "글 불러오기 성공"));
    }
}
package com.project.lms.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.service.FeedBackService;
import com.project.lms.vo.feedback.FeedBackResponseVO;
import com.project.lms.vo.feedback.FeedBackVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@Tag(description = "피드백 API입니다.", name = "피드백")
public class FeedBackAPIController {
    private final FeedBackService fService;
    @Operation(summary = "피드백 작성")
    @PutMapping("/{stuSeq}")
    public ResponseEntity<FeedBackResponseVO> putFeedBack(@PathVariable Long stuSeq, @RequestBody FeedBackVO data, @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(/* fService.putFeedBack(stuSeq, data, userDetails) */null, HttpStatus.OK);
    }
}
package com.project.lms.vo.feedback;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.project.lms.entity.feedback.FeedbackInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedBackResponseVO {
    @Schema (description = "상태" , example = "true")
    private Boolean status;
    @Schema (description = "메세지" , example = "성공")
    private String message;
    @Schema (description = "code" , example = "OK.")
    private HttpStatus code;

}

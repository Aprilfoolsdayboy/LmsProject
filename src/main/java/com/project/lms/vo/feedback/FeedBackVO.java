package com.project.lms.vo.feedback;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedBackVO {
    @Schema(description = "글 제목", example = "안녕하세요")
    private String fiTitle;
    @Schema(description = "글 내용", example = "독해 성적이 낮네요")
    private String fiContent;
}

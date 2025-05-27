package com.overfitting.overfitting.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChapterRequestDto {
    private MultipartFile file;
    private String webtoonTitle;
    private String chapterTitle;
}

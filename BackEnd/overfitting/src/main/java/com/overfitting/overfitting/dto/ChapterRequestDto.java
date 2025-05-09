package com.overfitting.overfitting.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

// 회차 업로드 시 사용하는 DTO입니다.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChapterRequestDto {

    // 업로드할 이미지 파일
    private MultipartFile file;

    // 해당 회차가 소속될 웹툰의 ID
    private Long webtoonId;
}

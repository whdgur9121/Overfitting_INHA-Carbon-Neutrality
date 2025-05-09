package com.overfitting.overfitting.dto;

import lombok.*;

// 업로드가 완료된 후 클라이언트에게 응답할 DTO입니다.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadResponseDto {

    // 업로드된 파일명
    private String filename;

    // 저장된 서버 경로 또는 접근 가능한 경로
    private String filepath;
}

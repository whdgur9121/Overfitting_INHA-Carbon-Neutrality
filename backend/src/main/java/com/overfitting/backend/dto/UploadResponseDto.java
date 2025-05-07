// ✅ UploadResponseDto.java (응답용 DTO)
package com.overfitting.backend.dto;

import lombok.AllArgsConstructor; // 전체 필드를 받는 생성자 자동 생성
import lombok.Data; // Getter/Setter, toString 등 자동 생성

@Data
@AllArgsConstructor
public class UploadResponseDto {
    private String filename; // 최종 압축된 이미지 파일 이름
    private String downloadUrl; // 프론트에서 이미지 다운로드용 URL
}
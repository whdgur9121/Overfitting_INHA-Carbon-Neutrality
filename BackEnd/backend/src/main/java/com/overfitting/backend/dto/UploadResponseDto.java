// ✅ UploadResponseDto.java
package com.overfitting.backend.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // (String, String) 생성자 생성
@NoArgsConstructor  // 기본 생성자도 생성
public class UploadResponseDto {
    private String filename;
    private String downloadUrl;
}
package com.overfitting.overfitting.dto;

import lombok.*;

// 업로드가 완료된 후 클라이언트에게 응답할 DTO입니다.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UploadResponseDto {
    private String filename;
    private String filepath;
    private String status;
}

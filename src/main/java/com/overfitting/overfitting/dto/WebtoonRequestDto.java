package com.overfitting.overfitting.dto;

import lombok.*;

// 웹툰을 생성할 때 사용하는 요청 DTO입니다.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebtoonRequestDto {

    // 사용자가 입력한 웹툰 디렉토리명
    private String title;
}

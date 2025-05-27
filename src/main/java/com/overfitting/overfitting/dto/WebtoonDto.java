package com.overfitting.overfitting.dto;

import lombok.*;
import java.util.List;

// 웹툰 목록 조회 시 클라이언트에게 전달할 DTO입니다.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WebtoonDto {
    private String title;
    private List<EpisodeDto> episodes; // ✅ 포함 필수
}

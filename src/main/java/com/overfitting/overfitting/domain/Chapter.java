package com.overfitting.overfitting.domain;

import jakarta.persistence.*;
import lombok.*;

// Chapter는 Webtoon의 하위 개념으로 각 회차를 의미합니다.
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chapter {

    // ID 필드: 자동 생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 회차 제목 또는 파일명
    private String filename;

    // 실제 저장된 파일 경로 (ex: /uploads/original/image1.jpg)
    private String filepath;

    // Webtoon과의 N:1 관계 설정
    @ManyToOne
    @JoinColumn(name = "webtoon_id")
    private Webtoon webtoon;
}

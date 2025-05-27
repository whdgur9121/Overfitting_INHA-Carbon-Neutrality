package com.overfitting.overfitting.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// @Entity는 이 클래스가 JPA의 엔티티(데이터베이스 테이블과 매핑됨)임을 나타냅니다.
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Webtoon {

    // ID 필드이며, 자동으로 생성되도록 설정합니다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 웹툰 제목 (디렉토리명)
    private String title;

    // 업로드 시간 등 추가 필드가 필요할 경우 여기에 선언

    // Webtoon과 Chapter는 1:N 관계입니다.
    // Webtoon 삭제 시 연관된 Chapter도 함께 삭제되도록 cascade 설정
    @OneToMany(mappedBy = "webtoon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapter> chapters = new ArrayList<>();

    // 편의 메서드: chapter를 추가하고 양방향 연관관계를 유지
    public void addChapter(Chapter chapter) {
        chapters.add(chapter);
        chapter.setWebtoon(this);
    }
}

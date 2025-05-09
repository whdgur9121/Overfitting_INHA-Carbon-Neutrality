package com.overfitting.overfitting.repository;

import com.overfitting.overfitting.domain.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Chapter 엔티티에 대한 CRUD 기능을 제공하는 JPA 리포지토리입니다.
@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    // 기본적인 JPA 메서드들 제공됨
}

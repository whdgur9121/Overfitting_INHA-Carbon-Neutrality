package com.overfitting.overfitting.repository;

import com.overfitting.overfitting.domain.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Webtoon 엔티티에 대한 기본적인 CRUD 기능을 제공하는 JPA 리포지토리입니다.
@Repository
public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {
    // 기본적인 findById, save, delete 등의 메서드는 JpaRepository가 자동 제공
}

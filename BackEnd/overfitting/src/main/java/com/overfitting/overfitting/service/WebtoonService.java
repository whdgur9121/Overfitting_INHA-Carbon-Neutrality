package com.overfitting.overfitting.service;

import com.overfitting.overfitting.domain.Chapter;
import com.overfitting.overfitting.domain.Webtoon;
import com.overfitting.overfitting.dto.ChapterRequestDto;
import com.overfitting.overfitting.dto.UploadResponseDto;
import com.overfitting.overfitting.dto.WebtoonRequestDto;
import com.overfitting.overfitting.repository.ChapterRepository;
import com.overfitting.overfitting.repository.WebtoonRepository;
import com.overfitting.overfitting.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor // 생성자 주입 자동 생성 (final 필드에 대해)
public class WebtoonService {

    // 의존성 주입: Repository 2개 + 파일 유틸
    private final WebtoonRepository webtoonRepository;
    private final ChapterRepository chapterRepository;
    private final FileUtil fileUtil;

    // ✅ 웹툰 디렉토리 생성
    public Webtoon createWebtoon(WebtoonRequestDto dto) {
        // 사용자가 입력한 title을 기반으로 Webtoon 엔티티 생성
        Webtoon webtoon = Webtoon.builder()
                .title(dto.getTitle())
                .build();

        // DB에 저장 후 반환
        return webtoonRepository.save(webtoon);
    }

    // ✅ 회차 이미지 업로드
    public UploadResponseDto uploadChapter(ChapterRequestDto dto) throws IOException {
        // 웹툰 ID로 해당 웹툰 조회 (없으면 예외 발생)
        Webtoon webtoon = webtoonRepository.findById(dto.getWebtoonId())
                .orElseThrow(() -> new IllegalArgumentException("웹툰 ID를 찾을 수 없습니다."));

        MultipartFile file = dto.getFile();

        // 원본 파일명
        String originalFilename = file.getOriginalFilename();

        // 저장 경로 확보 및 디렉토리 생성
        String uploadDir = fileUtil.getOrCreateChapterDirectory(webtoon.getTitle());
        String fullPath = fileUtil.getFullPath(webtoon.getTitle(), originalFilename);

        // 실제 파일 저장
        file.transferTo(new File(fullPath));

        // Chapter 엔티티 생성 및 저장
        Chapter chapter = Chapter.builder()
                .filename(originalFilename)
                .filepath(fullPath) // 실제 저장 경로
                .webtoon(webtoon)
                .build();
        chapterRepository.save(chapter);

        // 클라이언트에게 반환할 상대 경로 (정적 경로)
        return UploadResponseDto.builder()
                .filename(originalFilename)
                .filepath(fileUtil.getClientPath(webtoon.getTitle(), originalFilename)) // 예: /uploads/웹툰명/파일.jpg
                .build();
    }
}

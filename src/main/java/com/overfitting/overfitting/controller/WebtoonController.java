package com.overfitting.overfitting.controller;

import com.overfitting.overfitting.domain.Webtoon;
import com.overfitting.overfitting.dto.WebtoonRequestDto;
import com.overfitting.overfitting.dto.WebtoonDto;
import com.overfitting.overfitting.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webtoons")
public class WebtoonController {

    private final WebtoonService webtoonService;

    // 1. 웹툰 디렉토리 생성
    @PostMapping
    public ResponseEntity<Webtoon> createWebtoon(@RequestBody WebtoonRequestDto dto) {
        Webtoon created = webtoonService.createWebtoon(dto);
        return ResponseEntity.ok(created);
    }

    // 2. 전체 웹툰 및 회차 목록 반환
    @GetMapping
    public ResponseEntity<List<WebtoonDto>> getAllWebtoons() {
        List<WebtoonDto> webtoons = webtoonService.loadWebtoonsFromDirectory();
        return ResponseEntity.ok(webtoons);
    }

    // 3. 회차 디렉토리 생성
    @PostMapping("/{title}/chapters/{chapter}/create")
    public ResponseEntity<String> createChapterDirectory(
            @PathVariable String title,
            @PathVariable String chapter
    ) {
        webtoonService.createChapterDirectory(title, chapter);
        return ResponseEntity.ok("디렉토리 생성 성공");
    }

    // 4. 회차 이미지 목록 반환
    @GetMapping("/{title}/{chapter}/images")
    public ResponseEntity<List<String>> getChapterFiles(
            @PathVariable String title,
            @PathVariable String chapter
    ) {
        List<String> filenames = webtoonService.getChapterFileList(title, chapter);
        return ResponseEntity.ok(filenames);
    }

    // 5. 이미지 업로드 처리 (MultipartFile[] 방식)
    @PostMapping("/{title}/chapters/{chapter}/upload")
    public ResponseEntity<String> uploadMultipleImages(
            @PathVariable String title,
            @PathVariable String chapter,
            @RequestParam("files") MultipartFile[] files
    ) throws IOException {
        System.out.println("✅ 업로드 요청 도착: " + title + "/" + chapter);
        webtoonService.saveMultipleImages(title, chapter, files);
        return ResponseEntity.ok("업로드 성공");
    }
}
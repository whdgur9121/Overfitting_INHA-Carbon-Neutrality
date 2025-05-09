package com.overfitting.overfitting.controller;

import com.overfitting.overfitting.domain.Webtoon;
import com.overfitting.overfitting.dto.ChapterRequestDto;
import com.overfitting.overfitting.dto.UploadResponseDto;
import com.overfitting.overfitting.dto.WebtoonRequestDto;
import com.overfitting.overfitting.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webtoons") // 모든 API는 /api/webtoons 하위로 시작
public class WebtoonController {

    private final WebtoonService webtoonService;

    // 웹툰 생성 API (ex: POST /api/webtoons)
    @PostMapping
    public ResponseEntity<Webtoon> createWebtoon(@RequestBody WebtoonRequestDto dto) {
        Webtoon created = webtoonService.createWebtoon(dto);
        return ResponseEntity.ok(created);
    }

    // 회차(이미지) 업로드 API (ex: POST /api/webtoons/upload)
    @PostMapping("/upload")
    public ResponseEntity<UploadResponseDto> uploadChapter(
            @RequestParam("file") MultipartFile file,
            @RequestParam("webtoonId") Long webtoonId
    ) {
        try {
            ChapterRequestDto dto = ChapterRequestDto.builder()
                    .file(file)
                    .webtoonId(webtoonId)
                    .build();
            UploadResponseDto response = webtoonService.uploadChapter(dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    UploadResponseDto.builder()
                            .filename("에러 발생")
                            .filepath(e.getMessage())
                            .build()
            );
        }
    }
}
// ✅ ImageController.java (이미지 업로드 및 다운로드 처리)
package com.overfitting.backend.controller;

import com.overfitting.backend.dto.UploadResponseDto; // 업로드 후 응답 DTO import
import com.overfitting.backend.service.ImageService; // 이미지 처리 서비스 import
import lombok.RequiredArgsConstructor; // 생성자 자동 생성 어노테이션 import
import org.springframework.core.io.FileSystemResource; // 파일을 리소스로 변환하기 위한 클래스 import
import org.springframework.core.io.Resource; // HTTP 응답에 사용할 리소스 객체 import
import org.springframework.http.HttpHeaders; // HTTP 응답 헤더 제어용 클래스 import
import org.springframework.http.MediaType; // 응답 콘텐츠 타입 설정을 위한 클래스 import
import org.springframework.http.ResponseEntity; // HTTP 응답 래퍼 클래스 import
import org.springframework.web.bind.annotation.*; // REST API 관련 어노테이션 import
import org.springframework.web.multipart.MultipartFile; // 파일 업로드용 클래스 import

import java.io.File;
import java.io.IOException;

@RestController // REST API 컨트롤러로 지정
@RequestMapping("/api/image") // 공통 URL prefix 설정
@RequiredArgsConstructor // 생성자 주입 자동화 (final 필드 대상)
public class ImageController {

    private final ImageService imageService; // 이미지 처리 로직을 담당할 서비스 클래스 의존성 주입

    // ✅ 이미지 업로드 API (React에서 호출)
    @PostMapping("/upload")
    public ResponseEntity<UploadResponseDto> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        UploadResponseDto response = imageService.saveAndCompress(file); // 서비스로 위임하여 저장 및 압축 처리
        return ResponseEntity.ok(response); // JSON 형태의 결과 반환
    }

    // ✅ 압축된 이미지 다운로드 API
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadImage(@RequestParam("filename") String filename) {
        File file = new File("uploads/compressed/" + filename); // 다운로드할 파일 객체 생성
        Resource resource = new FileSystemResource(file); // 파일을 리소스로 래핑

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName()) // 파일 다운로드로 설정
                .contentType(MediaType.IMAGE_JPEG) // 응답 타입을 이미지로 설정
                .body(resource); // 리소스 전송
    }
}
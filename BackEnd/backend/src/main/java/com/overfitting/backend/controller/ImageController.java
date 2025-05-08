// ✅ ImageController.java (이미지 업로드, 경로 업로드, 다운로드, 리스트 조회 포함)
package com.overfitting.backend.controller;

import com.overfitting.backend.dto.UploadResponseDto;
import com.overfitting.backend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    // ✅ 1. MultipartFile 기반 업로드 API
    @PostMapping("/upload")
    public ResponseEntity<UploadResponseDto> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        File saved = imageService.saveFile(file);
        return ResponseEntity.ok(
                new UploadResponseDto(
                        saved.getName(),
                        "/api/image/download?filename=" + saved.getName()
                )
        );
    }

    // ✅ 2. 경로 기반 업로드 API
    @PostMapping("/upload-path")
    public ResponseEntity<UploadResponseDto> uploadFromPath(@RequestParam String path) throws IOException {
        File saved = imageService.copyFromPath(path);
        return ResponseEntity.ok(
                new UploadResponseDto(
                        saved.getName(),
                        "/api/image/download?filename=" + saved.getName()
                )
        );
    }

    // ✅ 3. 다운로드 API
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadImage(@RequestParam("filename") String filename) throws UnsupportedEncodingException {
        File file = new File("uploads/original/" + filename);
        Resource resource = new FileSystemResource(file);

        // 한글 및 공백 문제 해결 → URLEncoder로 인코딩
        String encodedFilename = URLEncoder.encode(file.getName(), "UTF-8").replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    // ✅ 4. 이미지 리스트 API
    @GetMapping("/list")
    public ResponseEntity<List<String>> listImages() {
        List<String> files = imageService.getFileList();
        return ResponseEntity.ok(files);
    }
}

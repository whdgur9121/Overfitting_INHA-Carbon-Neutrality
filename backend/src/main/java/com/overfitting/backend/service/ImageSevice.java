// ✅ ImageService.java (업로드, 압축 처리)
package com.overfitting.backend.service;

import com.overfitting.backend.dto.UploadResponseDto; // 응답용 DTO import
import com.overfitting.backend.util.CompressionUtil; // 이미지 압축 유틸 import
import org.springframework.stereotype.Service; // 스프링 서비스 빈으로 등록
import org.springframework.web.multipart.MultipartFile; // 파일 업로드 타입 import

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service // 서비스 계층 클래스임을 명시
public class ImageService {

    private final String uploadDir = "uploads/original/"; // 원본 이미지 저장 디렉토리
    private final String compressedDir = "uploads/compressed/"; // 압축 이미지 저장 디렉토리

    public UploadResponseDto saveAndCompress(MultipartFile file) throws IOException {
        // 1️⃣ 원본 파일 저장
        File originalFile = saveFile(file);

        // 2️⃣ 압축 이미지 생성 및 저장
        File compressedFile = CompressionUtil.compressImage(originalFile, compressedDir);

        // 3️⃣ 응답 객체 반환
        return new UploadResponseDto(
                compressedFile.getName(),
                "/api/image/download?filename=" + compressedFile.getName() // 다운로드 URL 구성
        );
    }

    private File saveFile(MultipartFile multipartFile) throws IOException {
        Path dirPath = Paths.get(uploadDir);
        if (!Files.exists(dirPath)) Files.createDirectories(dirPath); // 폴더 없으면 생성

        String filePath = uploadDir + multipartFile.getOriginalFilename(); // 저장 경로 구성
        File dest = new File(filePath);
        multipartFile.transferTo(dest); // 실제 파일 저장
        return dest;
    }
}
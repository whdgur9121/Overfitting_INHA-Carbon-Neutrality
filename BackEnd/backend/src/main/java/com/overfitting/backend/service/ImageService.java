// ✅ ImageService.java
package com.overfitting.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private final String uploadDir = "uploads/original/";

    // ✅ MultipartFile 저장
    public File saveFile(MultipartFile multipartFile) throws IOException {
        System.out.println("📁 업로드 요청 도착");

        // 현재 실행 디렉토리 기준 절대 경로로 생성
        Path dirPath = Paths.get(System.getProperty("user.dir"), "uploads", "original");
        if (!Files.exists(dirPath)) {
            System.out.println("📁 디렉토리 생성");
            Files.createDirectories(dirPath);
        }

        // 저장 대상 경로 설정
        String filePath = dirPath.resolve(multipartFile.getOriginalFilename()).toString();
        System.out.println("📄 저장 파일 경로: " + filePath);

        File dest = new File(filePath);
        multipartFile.transferTo(dest);

        System.out.println("✅ 파일 저장 완료");

        return dest;
    }

    // ✅ 경로 기반 파일 복사 저장
    public File copyFromPath(String path) throws IOException {
        File inputFile = new File(path);
        if (!inputFile.exists()) {
            throw new IOException("파일이 존재하지 않습니다: " + path);
        }

        Path dirPath = Paths.get(uploadDir);
        if (!Files.exists(dirPath)) Files.createDirectories(dirPath);

        File outputFile = new File(uploadDir + inputFile.getName());
        Files.copy(inputFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return outputFile;
    }

    // ✅ 저장된 이미지 파일 목록 조회
    public List<String> getFileList() {
        File dir = new File(uploadDir);
        if (!dir.exists()) return List.of();

        String[] files = dir.list((d, name) -> name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png"));
        return Arrays.stream(files != null ? files : new String[] {})
                .collect(Collectors.toList());
    }


}
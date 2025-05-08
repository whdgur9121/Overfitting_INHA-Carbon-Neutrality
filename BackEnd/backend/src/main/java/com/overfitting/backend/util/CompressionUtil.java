// ✅ CompressionUtil.java (압축 유틸리티 - 현재는 실제 압축 제외)
package com.overfitting.backend.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class CompressionUtil {

    // ✅ 실제 압축은 생략하고 파일만 복사하는 메서드
    public static File passThrough(File inputFile, String outputDir) throws IOException {
        File dir = new File(outputDir);
        if (!dir.exists()) dir.mkdirs(); // 출력 디렉토리 없으면 생성

        File outputFile = new File(outputDir + inputFile.getName()); // 출력 파일 경로 설정

        // ⚠️ 압축은 하지 않고 복사만 수행함
        Files.copy(inputFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return outputFile; // 복사된 파일 반환
    }
}
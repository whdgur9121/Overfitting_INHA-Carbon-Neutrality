package com.overfitting.overfitting.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUtil {

    // 현재 프로젝트 경로 기준 uploads 디렉토리 (절대경로)
    private final String baseDir = System.getProperty("user.dir") + "/uploads/";

    // 웹툰 제목 디렉토리 생성
    public String getOrCreateChapterDirectory(String webtoonTitle) throws IOException {
        Path path = Paths.get(baseDir + webtoonTitle);
        File dir = path.toFile();

        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new IOException("디렉토리 생성 실패: " + path);
            }
        }

        return path.toString();
    }

    // 전체 파일 경로 반환
    public String getFullPath(String webtoonTitle, String filename) {
        return baseDir + webtoonTitle + "/" + filename;
    }

    // 클라이언트용 상대 경로 반환
    public String getClientPath(String webtoonTitle, String filename) {
        return "/uploads/" + webtoonTitle + "/" + filename;
    }
}

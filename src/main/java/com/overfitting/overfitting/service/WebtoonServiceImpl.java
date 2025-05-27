package com.overfitting.overfitting.service;

import com.overfitting.overfitting.domain.Webtoon;
import com.overfitting.overfitting.dto.ChapterRequestDto;
import com.overfitting.overfitting.dto.UploadResponseDto;
import com.overfitting.overfitting.dto.WebtoonDto;
import com.overfitting.overfitting.dto.WebtoonRequestDto;
import com.overfitting.overfitting.dto.EpisodeDto;
import com.overfitting.overfitting.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class WebtoonServiceImpl implements WebtoonService {

    private final WebtoonRepository webtoonRepository;

    // 문자열 정규화 함수
    private String sanitize(String input) {
        return input.replaceAll("\\s+", "_").replaceAll("[^\\w가-힣_-]", "_");
    }

    @Override
    public Webtoon createWebtoon(WebtoonRequestDto dto) {
        Webtoon webtoon = Webtoon.builder()
                .title(dto.getTitle())
                .build();

        Path webtoonDir = Paths.get("uploads", sanitize(dto.getTitle()));
        try {
            Files.createDirectories(webtoonDir);
        } catch (IOException e) {
            throw new RuntimeException("웹툰 디렉토리 생성 실패", e);
        }

        return webtoonRepository.save(webtoon);
    }

    @Override
    public UploadResponseDto uploadChapter(ChapterRequestDto dto) throws IOException {
        MultipartFile file = dto.getFile();
        String webtoonTitle = sanitize(dto.getWebtoonTitle());
        String chapterTitle = sanitize(dto.getChapterTitle());

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("업로드된 파일이 없습니다.");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("파일 이름이 유효하지 않습니다.");
        }

        Path chapterPath = Paths.get("uploads", webtoonTitle, chapterTitle);
        Files.createDirectories(chapterPath);

        Path targetPath = chapterPath.resolve(originalFilename);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        return new UploadResponseDto(originalFilename, "업로드 성공", "/uploads/" + webtoonTitle + "/" + chapterTitle + "/" + originalFilename);
    }

    @Override
    public List<WebtoonDto> loadWebtoonsFromDirectory() {
        Path uploadsPath = Paths.get("uploads");
        List<WebtoonDto> webtoonList = new ArrayList<>();

        try (DirectoryStream<Path> webtoonDirs = Files.newDirectoryStream(uploadsPath)) {
            for (Path webtoonPath : webtoonDirs) {
                if (Files.isDirectory(webtoonPath)) {
                    String webtoonTitle = webtoonPath.getFileName().toString();
                    List<EpisodeDto> episodes = new ArrayList<>();

                    try (DirectoryStream<Path> episodeDirs = Files.newDirectoryStream(webtoonPath)) {
                        for (Path episodePath : episodeDirs) {
                            if (Files.isDirectory(episodePath)) {
                                String episodeTitle = episodePath.getFileName().toString();
                                episodes.add(EpisodeDto.builder().title(episodeTitle).build());
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("회차 목록 읽기 실패: " + e.getMessage());
                    }

                    webtoonList.add(new WebtoonDto(webtoonTitle, episodes));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("웹툰 목록 불러오기 실패", e);
        }

        return webtoonList;
    }

    @Override
    public List<String> getChapterFileList(String webtoonTitle, String chapterTitle) {
        String safeTitle = sanitize(webtoonTitle);
        String safeChapter = sanitize(chapterTitle);

        Path chapterPath = Paths.get("uploads", safeTitle, safeChapter);
        if (!Files.exists(chapterPath)) {
            return new ArrayList<>();
        }

        try (Stream<Path> files = Files.list(chapterPath)) {
            return files
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("파일 목록 조회 실패", e);
        }
    }

    @Override
    public void saveMultipleImages(String title, String chapter, MultipartFile[] files) throws IOException {
        String safeTitle = sanitize(title);
        String safeChapter = sanitize(chapter);
        Path chapterPath = Paths.get("uploads", safeTitle, safeChapter);

        Files.createDirectories(chapterPath);

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                System.err.println("⚠️ 업로드된 파일이 비어 있습니다. 무시됨.");
                continue;
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isBlank()) {
                System.err.println("⚠️ 파일 이름이 유효하지 않습니다. 무시됨.");
                continue;
            }

            try {
                Path targetPath = chapterPath.resolve(originalFilename);
                Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("✅ 파일 저장됨: " + targetPath);
            } catch (IOException e) {
                System.err.println("❌ 파일 저장 실패: " + originalFilename);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void createChapterDirectory(String title, String chapter) {
        String safeTitle = sanitize(title);
        String safeChapter = sanitize(chapter);

        Path chapterPath = Paths.get("uploads", safeTitle, safeChapter);
        try {
            Files.createDirectories(chapterPath);
        } catch (IOException e) {
            throw new RuntimeException("회차 디렉토리 생성 실패", e);
        }
    }
}

package com.overfitting.overfitting.service;

import com.overfitting.overfitting.dto.*;
import com.overfitting.overfitting.domain.Webtoon;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface WebtoonService {

    Webtoon createWebtoon(WebtoonRequestDto dto);

    UploadResponseDto uploadChapter(ChapterRequestDto dto) throws IOException;

    List<WebtoonDto> loadWebtoonsFromDirectory();

    List<String> getChapterFileList(String webtoonTitle, String chapterTitle);

    void saveMultipleImages(String title, String chapter, MultipartFile[] files) throws IOException;

    void createChapterDirectory(String title, String chapter);
}
// ✅ CompressionUtil.java (Java 기반 JPEG 압축 유틸리티)
package com.overfitting.backend.util;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class CompressionUtil {

    public static File compressImage(File inputFile, String outputDir) throws IOException {
        BufferedImage image = ImageIO.read(inputFile); // 이미지 읽기

        File dir = new File(outputDir);
        if (!dir.exists()) dir.mkdirs(); // 디렉토리 없으면 생성

        String outputFilePath = outputDir + inputFile.getName(); // 출력 경로 지정
        File compressedFile = new File(outputFilePath);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg"); // JPG 포맷 작성기 찾기
        ImageWriter writer = writers.next(); // 첫 번째 writer 사용

        FileImageOutputStream output = new FileImageOutputStream(compressedFile); // 출력 스트림 설정
        writer.setOutput(output); // writer에 출력 스트림 연결

        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // 압축 모드 명시적 설정
        param.setCompressionQuality(0.7f); // 압축 품질 설정 (0.0~1.0)

        writer.write(null, new IIOImage(image, null, null), param); // 이미지 저장
        writer.dispose(); // writer 종료
        output.close(); // 스트림 종료

        return compressedFile; // 압축된 파일 반환
    }
}
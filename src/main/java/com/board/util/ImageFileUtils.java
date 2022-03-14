package com.board.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.board.domain.ImageVO;
import com.board.exception.file.ImageUploadFailException;

public class ImageFileUtils {
	
	private static final String DEFAULT_FILE_PATH = "C:\\joonggo_market\\images";
	
	public static String getFileName(MultipartFile multipartFile) {
		StringBuilder builder = new StringBuilder();
		UUID uuid = UUID.randomUUID();
		String extension = getExtension(multipartFile.getOriginalFilename());
		builder.append(uuid).append(".").append(extension);
		return builder.toString();
	}

	private static String getExtension(String fileName) {
		int ext = fileName.lastIndexOf(".");
		return fileName.substring(ext + 1);
	}

	public static String getFilePath() {
		File imageDir = new File(DEFAULT_FILE_PATH);
		if (!imageDir.exists()) {
			imageDir.mkdirs();
		}
		return DEFAULT_FILE_PATH;
	}

	public static void saveImages(String filePath, String fileName, MultipartFile multipartFile) {
		File saveImages = new File(filePath, fileName);
		try {
			multipartFile.transferTo(saveImages);
		} catch (IOException e) {
			throw new ImageUploadFailException("이미지 등록에 실패했습니다.");
		} 
	}

	public static void deleteImages(ImageVO imageVO) {
		File file = new File(DEFAULT_FILE_PATH + "\\" + imageVO.getFileName());
		if (file.exists()) {
			file.delete();
		} 			
	}

}

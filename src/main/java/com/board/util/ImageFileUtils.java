package com.board.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.board.domain.ImageVO;

public class ImageFileUtils {
	
	private static final String DEFAULT_FILE_PATH = "C:\\joonggo_market\\images";
	
	/**
	 * 파일 이름 생성
	 */
	public static String getFileName(MultipartFile multipartFile) {
		StringBuilder builder = new StringBuilder();
		UUID uuid = UUID.randomUUID();
		String extension = getExtension(multipartFile.getOriginalFilename());
		builder.append(uuid).append(".").append(extension);
		return builder.toString();
	}

	/**
	 * 첨부하는 파일의 확장자 제거
	 */
	private static String getExtension(String fileName) {
		int ext = fileName.lastIndexOf(".");
		return fileName.substring(ext + 1);
	}

	/**
	 * 파일 경로 가져오기.
	 * 경로가 없다면 새로운 경로를 생성한다.
	 */
	public static String getFilePath() {
		File imageDir = new File(DEFAULT_FILE_PATH);
		if (!imageDir.exists()) {
			imageDir.mkdirs();
		}
		return DEFAULT_FILE_PATH;
	}

	/**
	 * 로컬 경로에 이미지 저장.
	 * @throws IOException 
	 * @throws SdkClientException 
	 * @throws AmazonServiceException 
	 */
	public static void saveImages(MultipartFile multipartFile) throws AmazonServiceException, SdkClientException, IOException {
		ObjectMetadata data = new ObjectMetadata();
		data.setContentDisposition(multipartFile.getContentType());
		data.setContentLength(multipartFile.getSize());
		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withRegion(Regions.AP_NORTHEAST_2)
				.build();
		String imageFileName = getFileName(multipartFile);
		PutObjectResult objectResult = s3client.putObject("joonggo-bucket", imageFileName, multipartFile.getInputStream(), data);
		System.out.println(objectResult.getContentMd5());
	}

	public static void deleteImages(ImageVO imageVO) {
		File file = new File(DEFAULT_FILE_PATH + "\\" + imageVO.getFileName());
		if (file.exists()) {
			file.delete();
		} 			
	}
	
	/**
	 * 회원 탈퇴 작업에 사용되는 메소드.
	 */
	public static void deleteImagesPermanent(String fileName) {
		File file = new File(DEFAULT_FILE_PATH + "\\" + fileName);
		if (file.exists()) {
			file.delete();
		} 			
	}

}

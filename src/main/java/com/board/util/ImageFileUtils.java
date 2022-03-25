package com.board.util;

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
	
	private static final AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2).build();
	
	private static final String AWS_BUCKET_NAME = "joonggo-bucket";
	
	public static String AWS_S3_URL = "https://joonggo-bucket.s3.ap-northeast-2.amazonaws.com/";
	
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
	 * 로컬 경로에 이미지 저장.
	 */
	public static void saveImages(MultipartFile multipartFile, String imageFileName) throws AmazonServiceException, SdkClientException, IOException {
		ObjectMetadata data = new ObjectMetadata();
		data.setContentDisposition(multipartFile.getContentType());
		data.setContentLength(multipartFile.getSize());
		PutObjectResult objectResult = s3client.putObject(AWS_BUCKET_NAME, imageFileName, multipartFile.getInputStream(), data);
		System.out.println(objectResult.getContentMd5());
	}

	public static void deleteImages(ImageVO imageVO) {
		try {
			s3client.deleteObject(AWS_BUCKET_NAME, imageVO.getFileName());
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
		}
	}
	
}

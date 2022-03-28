package com.board.mapper;

import java.util.List;

import com.board.domain.ImageVO;
import com.board.domain.ProductVO;

public interface ImageMapper {

	void addImages(ImageVO image);

	List<ImageVO> getImagesById(String prdtId);
	
	List<ImageVO> imagesById(ProductVO prdtId);
	
	void deleteImages(String prdtId);

	void deleteImagesPermanent(String prdtId);
}

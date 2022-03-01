package com.board.mapper;

import java.util.List;

import com.board.domain.ImageVO;

public interface ImageMapper {

	void addImages(ImageVO image);

	List<ImageVO> getImagesById(String prdtId);

}

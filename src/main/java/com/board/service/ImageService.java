package com.board.service;

import java.util.List;

import com.board.domain.ImageVO;

public interface ImageService {

	List<ImageVO> getImagesById(String prdtId);

}

package com.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.board.domain.ImageVO;
import com.board.mapper.ImageMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
	
	private final ImageMapper imageMapper;

	@Override
	public List<ImageVO> getImagesById(String prdtId) {
		return imageMapper.getImagesById(prdtId);
	}

}

package com.board.service;

import org.springframework.stereotype.Service;

import com.board.domain.StoreVO;
import com.board.mapper.StoreMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
	
	private final StoreMapper mapper;

	@Override
	public void addStore(StoreVO store) {
		mapper.addStore(store);
	}

	@Override
	public void deleteStore(String userId) {
		mapper.deleteStore(userId);
	}

}

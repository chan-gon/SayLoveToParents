package com.board.service;

import com.board.domain.StoreVO;

public interface StoreService {
	
	void addStore(StoreVO store);

	void deleteStore(String userId);
}

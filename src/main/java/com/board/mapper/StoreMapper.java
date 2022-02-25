package com.board.mapper;

import com.board.domain.StoreVO;

public interface StoreMapper {

	void addStore(StoreVO store);
	
	void deleteStore(String userId);
}

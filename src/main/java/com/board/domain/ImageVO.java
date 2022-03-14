package com.board.domain;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class ImageVO implements Serializable {
	
	private final String fileId;
	private final String prdtId;
	private final String fileName;
	private final String filePath;

}

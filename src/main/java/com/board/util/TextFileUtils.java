package com.board.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.board.exception.file.TextFileException;

public class TextFileUtils {

	private static final String DEFAULT_FILE_PATH = "C:\\joonggo_market\\messages";

	public static void saveMessages(String roomId, String message) {
		
		try {
			createFolder(DEFAULT_FILE_PATH);
			File file = new File(DEFAULT_FILE_PATH + "\\" + roomId + ".txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			
			writer.write(message);
			writer.newLine();
			
			writer.flush();
			writer.close();
		} catch (IOException e) {
			throw new TextFileException("메시지 전송에 실패했습니다.");
		}
	}

	private static void createFolder(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	public static void deleteFile(String roomId) {
		File file = new File(DEFAULT_FILE_PATH + "\\" + roomId + ".txt");
		if (file.exists()) {
			file.delete();
		}
	}
	
}
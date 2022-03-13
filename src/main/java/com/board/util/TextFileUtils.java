package com.board.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileUtils {

	private static final String DEFAULT_FILE_PATH = "C:\\joonggo_market\\messages";

	public static void saveMessages(String roomId, String message) throws IOException {
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
	}

	private static void createFolder(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}
	}
	
}
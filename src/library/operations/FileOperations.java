package library.operations;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperations {

	public static void open(String fileName) throws IOException {
		try {
			File file = new File(fileName);
			if (file.exists()) {
				FileReader fr = new FileReader(fileName);
				int r = 0;
				while ((r = fr.read()) != -1) {
					System.out.print((char) r);
				}
				fr.close();
			} else {
				throw new IOException(String.format("File %s was not found.", fileName));
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public static void save(String content, String fileName) throws IOException {
		FileWriter fileWriter = new FileWriter(fileName);
		try {
			fileWriter.write(content);
			System.out.println(String.format("Successfully saved contents to file %s.", fileName));
		} catch (IOException e) {
			throw e;
		} finally {
			fileWriter.close();
		}
	}

	public static void saveAs(String content, String filename) throws IOException {
		save(content, filename);
	}

	public static void close() {
		// ...
	}
}

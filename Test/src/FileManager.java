import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {

	public ArrayList<String> readFile(String file) {
		try {
			ArrayList<String> fileContent = new ArrayList<String>();
			// 입력 버퍼 생성
			BufferedReader bufReader = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = bufReader.readLine()) != null) {
				fileContent.add(line);
			}

			bufReader.close();

			return fileContent;
		} catch (IOException e) {
			return null;
		}
	}


}

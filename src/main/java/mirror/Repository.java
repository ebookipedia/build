package mirror;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

class Repository {
	
	String name;
	String qualifier;
	String language;
	String article;
	
	Repository(String string, List<String> languages) {
		try (OutputStream file = new FileOutputStream("target.repository")) {
			int index = string.indexOf('/');
			if (index == -1) {
				throw new IllegalArgumentException("Invalid repository name");
			}
			qualifier = string.substring(0, index);
			file.write((qualifier + "/" + qualifier + ".github.io").getBytes("utf-8"));
			name = string.substring(index + 1);
			if (languages == null) {
				return;
			}
			index = qualifier.indexOf('-');
			if (index != -1) {
				String suffix = qualifier.substring(index + 1);
				if (languages.contains(suffix)) {
					language = suffix;
					article = qualifier.substring(0, index);
				} 
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}	
	}
}
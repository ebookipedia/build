package mirror;

import java.io.File;

import environment.Set;

public class Wikipedia {

	public static void main(String[] args) {
		System.out.println("user.dir: " + System.getProperty("user.dir"));
		File file = new File("source.epub");
		boolean starting = !file.exists();
		System.out.println("starting: " + starting);
		Set.variable("starting", starting);
	}

}

package mirror;

import java.io.File;

import environment.Set;

public class Wikipedia {

	public static void main(String[] args) {
		System.out.println("user.dir: " + System.getProperty("user.dir"));
		File file = new File("source.epub");
		boolean exists = file.exists();
		System.out.println("exists: " + exists);
		Set.variable("starting", String.valueOf(!exists));
	}

}

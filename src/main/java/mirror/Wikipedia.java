package mirror;

import java.io.File;

import environment.Set;

public class Wikipedia {

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		Set.variable("starting", String.valueOf(!new File("source.epub").exists()));
	}

}

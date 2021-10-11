package mirror;

import org.kohsuke.github.GHEventPayload.Repository;

public class Wikipedia {

	public static void main(String[] args) {
		System.out.println("Hello, World!");
		Repository r = new Repository();
		System.out.println(r);
		System.out.println(args[0]);
	}

}

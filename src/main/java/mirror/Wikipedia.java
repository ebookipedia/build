package mirror;

import org.kohsuke.github.GHEventPayload.Repository;

public class Wikipedia {

	public static void main(String[] args) {
		System.out.println("Hello, World!");
		System.out.println(new Repository());
		if (args.length > 0) {
			System.out.println(args[0]);
		}
	}

}

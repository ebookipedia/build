package repository;

import com.softalks.ebookipedia.Output;
import com.softalks.github.Repository;

public class Parse {

	private Parse() {
		// SONAR mandatory
	}

	/**
	 * @param args An array with one only item having the value of the
	 *             GITHUB_REPOSITORY variable
	 */

	public static void main(String[] args) {
		Repository repository = new Repository(args[0]);
		String owner = repository.owner;
		Output.target(owner + "/" + owner + ".github.io");
	}
	
}

package repository;

import com.softalks.ebookipedia.Output;
import com.softalks.github.Repository;

public class Parse {

	public static void main(String[] args) {
		Repository repository = new Repository(args[0]);
		String owner = repository.owner;
		Output.target(owner + "/" + owner + ".github.io");
	}
	
}

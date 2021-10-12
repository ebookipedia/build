package repository;

import static com.softalks.ebookipedia.Output.print;

import java.io.IOException;

import org.kohsuke.github.GHContentBuilder;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import com.softalks.github.Repository;

public class Target {

	private Target() {
		// SONAR mandatory
	}

	/**
	 * @param args An array with one only item having the value of the
	 *             GITHUB_REPOSITORY variable
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			//args = new String[]{"Abiogenesis-en/wikipedia"};
			args = new String[]{"hiebra/oauth"};
		}
		Repository source = new Repository(args[0]);
		String owner = source.owner;
		Repository target = new Repository(owner, owner + ".github.io");
		print("target", target);
		GitHub account;
		try {
//			account = GitHub.connect("hiebra@hotmail.com", "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");
			account = GitHubBuilder.fromEnvironment().build();
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
		GHOrganization organization;
		try {
			organization = account.getOrganization(owner);
		} catch (IOException e) {
			organization = null;
		}
		try {
			if (organization == null) {
				account.getRepository(target.toString());	
			} else {
				organization.getRepository(target.toString());	
			}
		} catch (IOException e) {
			GHCreateRepositoryBuilder newRepository = getBuilder(account, organization, target.name);
			create(newRepository);	
		}
	}
	
	private static GHCreateRepositoryBuilder getBuilder(GitHub account, GHOrganization organization, String name) {
		if (organization == null) {
			return account.createRepository(name);
		} else {
			return organization.createRepository(name);
		}
		
	}
	
	private static GHRepository create(GHCreateRepositoryBuilder builder) {
		GHRepository ghTarget;
		try {
			ghTarget = builder.create();
			GHContentBuilder content = ghTarget.createContent();
			content.message("test");
			content.path("hello.txt");
			content.content("Hello, World!".getBytes());
			content.commit();
			return ghTarget;
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
}
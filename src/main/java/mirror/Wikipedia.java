package mirror;

public class Wikipedia {

	private Wikipedia() {
		// SONAR mandatory
	}

	/**
	 * This command should be invoked on each change on the excerpt.html
	 * @param args One only element with the Github repository name hosting the
	 *             eBook-friendly Wikipedia article mirror
	 */
	public static void main(String[] args) {
		String[] parameters = args.length == 0 ? new String[] { "Abiogenesis-en/mirror" } : args;
		if (parameters.length == 1) {
			new com.softalks.ebookipedia.Wikipedia(parameters[0]);
		}
		throw new IllegalStateException("Parameter missing: Github repository name ($GITHUB_REPOSITORY)");
	}

}

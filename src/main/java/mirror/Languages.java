package mirror;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ibm.icu.util.ULocale;

class Languages extends ArrayList<String> {

	Languages() {
		List<String> deprecated = Arrays.asList("bh", "in", "iw", "ji", "jw", "mo", "sh");
		List<String> all = Arrays.asList(ULocale.getISOLanguages());
		for (String language : all) {
			if (language.length() != 2) {
				continue; // the array of languages returned by getISOLanguages does not match its documentation
			}
			if (!deprecated.contains(language)) {
				add(language);
			}
		}		
	}
	
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class processes each sentence into tokens.
 * 
 * @author Sahithi Karre
 *
 */
public class Sentence {
	String sentence;
	Map<String, Integer> tokenMap;
	List<String> namedEntities;
    /**
     * This constructor creates a token map and named entities list for each 
     * sentence.
     *  
     * @param str takes input as each sentence
     */
	Sentence(String str) {
		this.sentence = str;
		tokenMap = new HashMap<String, Integer>();
		namedEntities = new ArrayList<String>();
	}
    /**
     * This function converts sentence to a string.
     * 
     * @param s takes sentence as input
     * @return gives the string form of a sentence
     */
	public String getSentence(Sentence s) {
		return s.sentence;
	}
    /**
     * This function tokenizes the sentence by splitting them accordingly when
     * a special character appears.
     */
	public void tokenizeSentence() {

		if (sentence != null) {
			String s = sentence;
			s = s.replaceAll("\\'s", "")
					.replaceAll(
							"[\\[\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\{\\}\\[\\]\\`\\~\\]\\,\\.\\/\\?\"\\;\\']",
							"");
			String[] words = s.split(" ");
			for (String word : words) {
				int count = 0;
				if (tokenMap.containsKey(word))
					count = tokenMap.get(word);
				tokenMap.put(word, count + 1);

			}

		}

	}
}

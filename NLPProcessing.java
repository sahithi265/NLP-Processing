import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
/**
 * This class demonstrates the processing of nlp_data.txt data.
 * 
 * @author Sahithi Karre
 */
public class NLPProcessing {
	public static void main(String args[]) throws Exception {
		try {
			ZipFile zf = new ZipFile(
					"C:/Users/Sahithi/Documents/programming_test.zip");
			Enumeration<? extends ZipEntry> entries = zf.entries();
			createXML xml = new createXML();
			ArrayList<Sentence> listofSentences = new ArrayList<Sentence>();
			BufferedReader input = new BufferedReader(new InputStreamReader(
					System.in));
			while (entries.hasMoreElements()) {
				ZipEntry ze = (ZipEntry) entries.nextElement();
				System.out.println("Read " + ze.getName() + "?");
				String inputLine = input.readLine();
				if (inputLine.equalsIgnoreCase("yes")) {
					long size = ze.getSize();
					if (size > 0) {
						System.out.println("Length is " + size);
						BufferedReader br = new BufferedReader(
								new InputStreamReader(zf.getInputStream(ze)));
						String line;
						StringBuffer sb = new StringBuffer();
						while ((line = br.readLine()) != null) 
						{
							sb.append(line);
						}
						BreakIterator iterator = BreakIterator
								.getSentenceInstance(Locale.US);
						listofSentences = sentenceDetector(iterator,
								sb.toString());
						processNER(listofSentences,"C:/Users/Sahithi/Documents/programming_test/NLP_test/NER.txt");
						printLists(listofSentences);
						xml.createXml(listofSentences);
						br.close();
						break;
					}

				}
			}
			zf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function detects all the sentences that are present in the input text
	 * based on Capitalization of words.
	 * 
	 * @param bi It is the BreakIterator object
	 * @param text It is the string in which the sentences are detected
	 * @return returns ArrayList of sentences that are identified
	 */
	public static ArrayList<Sentence> sentenceDetector(BreakIterator bi,
			String text) {
		ArrayList<Sentence> sentences = new ArrayList<Sentence>();
		bi.setText(text);

		int lastIndex = bi.first();
		while (lastIndex != BreakIterator.DONE) {
			int firstIndex = lastIndex;
			lastIndex = bi.next();

			if (lastIndex != BreakIterator.DONE) {
				Sentence s = new Sentence(text.substring(firstIndex, lastIndex));
				s.tokenizeSentence();
				sentences.add(s);

			}
		}
		return sentences;
	}
    /**
     * This function recognizes all Named Entities present in each sentence
     * comparing with the named entities list(NER.txt).
     * 
     * @param listofSentences takes ArrayList of sentences 
     * @param path defines the path of NER.txt file
     * @throws FileNotFoundException throws exception when NER.txt is not found
     */
	public static void processNER(ArrayList<Sentence> listofSentences,
			String path) throws FileNotFoundException {

		Scanner sc = new Scanner(new File(path));
		while (sc.hasNext()) {
			String properNoun = sc.nextLine();
			for (Sentence sentence : listofSentences) {
				Map<String, Integer> tokenMap = sentence.tokenMap;
				int index = properNoun.indexOf(" ");
				if (index > 0) {
					String[] nameParts = properNoun.split(" ");
					boolean isPresent = false;
					for (String name : nameParts) {
						if (tokenMap.containsKey(name))
							isPresent = true;
						else {
							isPresent = false;
							break;
						}
					}
					if (isPresent) {
						for (String name : nameParts) {
							int count = tokenMap.get(name);
							if (count == 1)
								tokenMap.remove(name);
							else
								tokenMap.put(name, count - 1);
						}
						tokenMap.put(properNoun, 1);
						sentence.namedEntities.add(properNoun);
					}
				} else if (tokenMap.containsKey(properNoun))
					sentence.namedEntities.add(properNoun);
			}
		}
		sc.close();
	}
	/**
	 * This function prints all the sentences,tokens in the sentences and named 
	 * entities in the sentences.
	 * 
	 * @param listofSentences takes list of sentences
	 */
	public static void printLists(ArrayList<Sentence> listofSentences)
	{
		for(Sentence sentence:listofSentences)
		{
			System.out.println("----------------------------------------------------");
			System.out.println("Program 1: Identified Sentences"+sentence.sentence);
			System.out.println("Program 1: Identified Tokens"+sentence.tokenMap);
			System.out.println("Program 2: Identified Named Entities"+sentence.namedEntities);
			System.out.println("----------------------------------------------------");
		}
	}

}

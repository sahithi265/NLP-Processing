import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class MainProcess {
	public static void main(String args[]) throws Exception {
		// ZipFile zf = new
		// ZipFile("); //C:/Users/Sahithi/Documents/programming_test/NLP_test/nlp_data.zip");
		// //args [0]
		// Enumeration<? extends ZipEntry> entries = zf.entries();
		BufferedReader input = new BufferedReader(new InputStreamReader(
				System.in));
		String nerPath = args[1]; // args
																							// []
		ExecutorService executor = Executors.newFixedThreadPool(5);// creating a
																	// pool of 5
																	// threads
		final File folder = new File(args[0]);//"C:/Users/Sahithi/Documents/programming_test/NLP_test/nlp_data"
		for (final File fileEntry : folder.listFiles()) {

			Runnable worker = new WorkerThread(fileEntry, nerPath);

			executor.execute(worker);// calling execute method of
										// ExecutorService
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
	}
}

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
import java.util.zip.ZipEntry;
class WorkerThread implements Runnable {  
    
	private File fileEntry;
	private String nerPath; 
	
    public WorkerThread(File fileEntry,String nerPath){ 
        this.fileEntry=fileEntry;
        this.nerPath = nerPath;
    }  
     public void run() {  
        System.out.println(Thread.currentThread().getName()+" fileName "+ this.fileEntry );  
        try {
			processmessage();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//call processmessage method that sleeps the thread for 2 seconds  
        System.out.println(Thread.currentThread().getName()+" (End)");//prints thread name  
    }  
    private void processmessage() throws FileNotFoundException, Exception {  
        try {  
        	NLPProcessing np = new NLPProcessing();
			np.ProcessFile(new FileInputStream(this.fileEntry), this.nerPath);
        } catch (InterruptedException e) { e.printStackTrace(); }  
    }  
} 
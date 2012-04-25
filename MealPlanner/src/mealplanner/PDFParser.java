package mealplanner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;


public class PDFParser {
	//these are the locations of the shell programs
	//that curl the brown dining webpages and save them as text files
	//in the ltmp directory
	public static final String CURL_IVY_SHELL = "/CurlIvy";
	public static final String CURL_VW_SHELL = "/CurlVW";
	public static final String CURL_RATTY_SHELL = "/CurlRatty";
	//these are the locations of the text files created by the above shell programs
	public static final String RATTY_PAGE_TXT = "/ltmp/RattyPage.txt";
	public static final String VW_PAGE_TXT = "/ltmp/VWPage.txt";
	public static final String IVY_PAGE_TXT = "/ltmp/IvyPage.txt";
	//these are the locations of shell programs that
	//go to a saved brown dining PDF, convert it to text
	//and save that text file in ltmp
	public static final String RATTY_TO_PDF = "/IvyToPDF";
	public static final String VW_TO_PDF = "/RattyToPDF";
	public static final String IVY_TO_PDF = "/VWToPDF";
	//
	public static final String RATTY_TXT ="/ltmp/ratty.txt";
	public static final String VW_TXT= "/ltmp/vw.txt";
	public static final String IVY_TXT = "/ltmp/ivy.txt";
	//The names of the dining halls
	public static final String RATTY = "Ratty";
	public static final String VW = "VW";
	public static final String IVY_ROOM = "Ivy Room";

	/**
	 * @param args
	 */
	//TODO this is only so I can run this myself, the backend will just call getCurrentEateryInfo
	public static void main(String[] args) {
		PDFParser parse = new PDFParser();
		ArrayList<Dish> results = parse.getCurrentEateryInfo();
		System.out.println("Done");
	}

	public void close(){
		clearOld();
	}
	public boolean clearOld(){
		boolean returnval = false;
		if(deleteFile(RATTY_PAGE_TXT)==false)
			returnval= true;
		if(deleteFile(VW_PAGE_TXT)==false)
			returnval=  true;
		if(deleteFile(IVY_PAGE_TXT)==false)
			returnval=  true;
		if (deleteFile(RATTY_TXT)==false)
			returnval=  true;
		if (deleteFile(VW_TXT)==false)
			returnval=  true;
		if (deleteFile(IVY_TXT)==false)
			returnval=  true;

		return returnval;
	}
	public boolean deleteFile(String path){
		File file = new File(path);
		if(file.exists()){
			if(file.canWrite()){
				return file.delete();
			}else
				return false;
		}else{
			return true;
		}

	}
	//given a string array and an executor, create a runCmd in a future, then tell the executor to execute
	//that future and return the instance of the future so the caller can keep track of it
	public FutureTask<String> RunFuture(Executor exec, String[] commands){
		Callable<String> callable = new RunCmd(commands);
		FutureTask<String> task = new FutureTask<String>(callable);
		exec.execute(task);
		return task;
	}
	public ArrayList<Dish> getCurrentEateryInfo(){
		if(clearOld()==true)
			System.out.println("old text files found in /ltmp directory. Results may not be valid, please remove text file from /ltmp directory to ensure valid results");

		ArrayList<Dish> dishes = new ArrayList<Dish>();
		String path = null;
		File directory = new File(".");
		ExecutorService _pool;
		FutureTask<String> taskIvyMenu,taskIvy;
		FutureTask<String> taskRattyMenu,taskRatty;
		FutureTask<String> taskVWMenu,taskVW;
		//get the file path of the java files we are running
		try {
			path = directory.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//create a runcmd that will run the toPDFshell shell
		if (path != null){
			//thread pool so I can run multiple shells at once, speed things up
			_pool= Executors.newCachedThreadPool();
			//call the executable that will curl the webpage for the ivy room
			taskIvyMenu = RunFuture(_pool,new String[] {path + CURL_IVY_SHELL});

			//call the executable that will curl the webpage for the ratty
			taskRattyMenu = RunFuture(_pool, new String []{path + CURL_RATTY_SHELL});

			//call the executable that will curl the webpage for the VW
			taskVWMenu = RunFuture(_pool,new String []{path + CURL_VW_SHELL});

			//Now wait for all the text files to be created (for the futures to return

			try {//TODO check for null return values and do something if this is the case
				taskIvyMenu.get();
				taskRattyMenu.get();
				taskVWMenu.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			//now figure out what menu needs to be looked at using a menufinder object
			MenuFinder mFinder = new MenuFinder();
			String RattyPath = mFinder.getMenu(RATTY_PAGE_TXT);
			String VWPath = mFinder.getMenu(VW_PAGE_TXT);
			String IvyPath = mFinder.getMenu(IVY_PAGE_TXT);
			//TODO check to make sure none of the calls to mfinder above returned null

			//now get the PDFs off the internet and convert them to a saved text file that can be parsed
			taskIvy = RunFuture(_pool,new String[] {path+IVY_TO_PDF,IvyPath});

			taskRatty= RunFuture(_pool,new String[] {path+RATTY_TO_PDF,RattyPath});

			taskVW= RunFuture(_pool,new String[] {path+VW_TO_PDF,VWPath});


			try {
				//wait for all three to finish
				String ret = taskIvy.get();
				if (!ret.equals("Done"))
					System.out.println(ret);
				ret = taskRatty.get();
				if (!ret.equals("Done"))
					System.out.println(ret);
				ret = taskVW.get();
				if (!ret.equals("Done"))
					System.out.println(ret);
			} catch (InterruptedException e2) {
				System.out.println("interrupted exception when running PDF to text shells");
			} catch (ExecutionException e2) {
				System.out.println("Execution Error when running PDF to text shells");
			}

			//call the eateryreader with the name of the text file that the runcmd created
			int sucess;
			EateryReader reader = new EateryReader();
			dishes.addAll(reader.ReadInEatery(IVY_TXT,IVY_ROOM));
			dishes.addAll(reader.ReadInEatery(VW_TXT, VW));
			dishes.addAll(reader.ReadInEatery(RATTY_TXT, RATTY));
		}
		//arraylist will contain null values if there were any errors
		clearOld();
		return dishes;
	}
}

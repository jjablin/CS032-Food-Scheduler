package mealplanner;

import java.io.*;


import java.util.concurrent.Callable;

public class RunCmd implements Callable<String> {
	private String[] _cmdArray;
	public RunCmd(String[] cmdArray){
		_cmdArray=cmdArray;
	}

	@Override
	public String call() throws Exception {
		try {
			//open and execute a process (the shell)
			Process shellexec = Runtime.getRuntime().exec(_cmdArray);
			shellexec.waitFor(); //need to wait for the shell to finish before you return succes or failure
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(shellexec.getOutputStream()));
			BufferedReader reader = new BufferedReader(new InputStreamReader(shellexec.getInputStream()));
			writer.close();
			reader.close();
		} catch (IOException e) {
			if(_cmdArray[0] != null)
				return "Error: IO Exception when running the following \n" + _cmdArray[0];
			else
				return "Error: IO Exception when running the following \n";
		}catch(Exception e){
			if(_cmdArray[0] != null)
				return "Error: unidentified error when running the following \n" +_cmdArray[0];
			else
				return "Error: IO Exception when running the following \n";
		}
		return "Done";
	}
}

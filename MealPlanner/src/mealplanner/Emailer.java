/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mealplanner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.swing.JOptionPane;
/**
 *
 * @author kpdurfee
 */

public class Emailer {
    private static String EMAIL_TEXT_FILE="/tmp/email.txt";
    private FileWriter _writer;
    private BufferedWriter _fwriter;;
    public Emailer(){
        try {
            _writer = new FileWriter(EMAIL_TEXT_FILE);
            _fwriter = new BufferedWriter(_writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String GetDay(int day){
        String dayOfWeek=null;
        switch(day){
            case 0: {
                dayOfWeek="Sunday";
                break;
            }
            case 1: {
                dayOfWeek="Monday";
                break;
            }
           case 2: {
                dayOfWeek="Tuesday";
                break;
            }
            case 3: {
                dayOfWeek="Wednesday";
                break;
            }
            case 4: {
                dayOfWeek="Thursday";
                break;
            }
            case 5: {
                dayOfWeek="Friday";
                break;
            }
            case 6: {
                dayOfWeek="Saturday";
                break;
            }
        }
        return dayOfWeek;
    }
    public void sendEmail(String userAdress, Set<String> userlikes){

        if(userAdress.isEmpty())
        {
            return;
        }
         try{
             File f = new File(EMAIL_TEXT_FILE);
             if(f.canWrite() && f.canRead()){
                 _fwriter.newLine();
                 _fwriter.write("Your Brown Dining information has been updated to provide you with \n");
                 _fwriter.write("details about the current week. The following dishes that you have marked as \n");
                 _fwriter.write("favorites are available this week :");
                 _fwriter.newLine();
                 _fwriter.newLine();
                 _fwriter.newLine();

                 Iterator<String> likesItr = userlikes.iterator();
                 while(likesItr.hasNext()){
                     String next = likesItr.next();
                     _fwriter.write(next);
                     _fwriter.newLine();
                 }
                 _fwriter.flush();
             }else{
                 //You don't have the right file permissions
                 JOptionPane.showMessageDialog(null, "Necessary Read/Write permissions on file " + EMAIL_TEXT_FILE + " not correct. \n Try deleting this file before continuing");
             }
         }catch(Exception e){

         }
        ExecutorService thread = Executors.newSingleThreadExecutor();
        String[] cmd = {"./Mailer",userAdress};
        RunFuture(thread, cmd);
    }

    public FutureTask<String> RunFuture(Executor exec, String[] commands){
		Callable<String> callable = new RunCmd(commands);
		FutureTask<String> task = new FutureTask<String>(callable);
		exec.execute(task);
		return task;
	}
    public void close(){
        try{
        _fwriter.close();
        _writer.close();
        }catch(IOException ioe){
            //IO Exception
        }catch(Exception e){
            //Another issue
        }
    }
}

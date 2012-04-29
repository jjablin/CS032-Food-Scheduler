/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mealplanner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
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
    /* //TODO get rid of this as it is only to test
    public static void main(String[] args) {
        ArrayList<Dish> dlist = new ArrayList<Dish>();
        Dish d = new Dish("NEW FOOF MAN");
        d.setLocation(new Location("Ratty"));
        Calendar curdate = Calendar.getInstance();
                curdate.set(Calendar.MONTH, (3));
                curdate.set(Calendar.DAY_OF_MONTH, 29);
                curdate.set(Calendar.HOUR, 0);
                curdate.set(Calendar.MINUTE, 0);
                curdate.set(Calendar.SECOND,0);
                curdate.set(Calendar.MILLISECOND,0);
        d.setDate(curdate);
        dlist.add(d);
        Emailer e = new Emailer();
        e.sendEmail("Durfguy@gmail.com",dlist);
	}
     * */
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
    public void sendEmail(String userAdress, List<Dish> userlikes){

         try{
             _fwriter.newLine();
             _fwriter.write("Your Brown Dining information has been updated to provide you with \n");
             _fwriter.write("details about the current week. The following dishes that you have marked as \n");
             _fwriter.write("favorites are available this week :");
             _fwriter.newLine();
             _fwriter.newLine();
             _fwriter.newLine();

             for(int x =0;x<userlikes.size();x++){
                 _fwriter.write(userlikes.get(x).getName() + " is available at the " +userlikes.get(x).getLocation() + " on " + GetDay((userlikes.get(x).getDate().getTime().getDay())));
                 _fwriter.newLine();
             }
             _fwriter.flush();
         }catch(Exception e){

         }
        ExecutorService thread = Executors.newSingleThreadExecutor();
        System.out.println("mutt"+" -s "+" Dining Information Update " + userAdress + "<"+EMAIL_TEXT_FILE);
        String[] cmd = {"./Mailer",userAdress};
        RunFuture(thread, cmd);
    }

    public FutureTask<String> RunFuture(Executor exec, String[] commands){
		Callable<String> callable = new RunCmd(commands);
		FutureTask<String> task = new FutureTask<String>(callable);
		exec.execute(task);
		return task;
	}

}

package mealplanner;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class EateryReader {

    private ArrayList<Dish> _dishes;
    private FileInputStream _stream = null;
    private DataInputStream _instream = null;
    private BufferedReader _bufread = null;


    public EateryReader() {


    }
    //call this to create a new eatery by reading in the filepath

    public ArrayList<Dish> ReadInEatery(String filepath, String name) {
        _dishes = new ArrayList<Dish>();
        int sucess = readMeals(name, filepath);
        if (sucess == -1) {
            JOptionPane.showMessageDialog(null, "Failure to find, create, or parse text file of nutrition information in /tmp directory");
            return null;
        } else if (sucess == 0) {
            return null;
        } else {
            return _dishes;
        }

    }
    //called by neweatery to parse a text file

    @SuppressWarnings("deprecation")
    private int readMeals(String eateryname, String filePath) {
        Dish curdish;
        Meal curmeal = null;
        Calendar curdate = Calendar.getInstance();
        curdate.set(0, 0, 0); // Initialize date to 0.
        boolean newdate = false;
        boolean nutrerror = false;
        try {
            _stream = new FileInputStream(filePath);

            _instream = new DataInputStream(_stream);
            _bufread = new BufferedReader(new InputStreamReader(_instream));

            TextParser parser = new TextParser();
            String line;
            boolean ingrediants;

            while (true) { //loop until we break
                nutrerror = false; //this is if the PDF leaves out necessary information in the nutrition facts section
                //initially set this to false at the start of reading each dish (each loop)

                //Read in the line that starts a new dish, and create a dish with the string in this line
                if ((line = _bufread.readLine()) != null) {
                    //read in the dish name and create a new dish
                    if (line.charAt(0) == '') { //check for and delete this weird character the PDF to text sometimes gives.
                        line = line.substring(1);
                    }

                    //create a new dish of this name
                    curdish = new Dish(line);
                    curdish.setLocation(new Location(eateryname));
                } else {
                    break;
                }

                //Read in the line under the name. It should be the ingrediants
                if ((line = _bufread.readLine()) != null) {
                    ingrediants = parser.SetIngrediants(curdish, line);

                } else {
                    break;
                }
                if (ingrediants) { //if ingrediants did not have an issue then read the empty line
                    //and then read in the ingrediants header
                    // otherwise you don't want to do this as there were no ingrediants
                    //and the line you thought was ingrediants is really the nutrition facts header

                    //Read in the empty line seperator
                    if ((line = _bufread.readLine()) != null) { //read in an empty line

                    } else {
                        break;
                    }
                    //Read in the next line and make sure it is the nutrition facts header
                    if ((line = _bufread.readLine()) != null) {
                        if (line.compareTo("Nutrition Facts") != 0) {
                            //this means things are not formatted as expected
                        }
                    } else {
                        break;
                    }
                }
                //Under the nutrition facts header is the list of nutrition facts
                if ((line = _bufread.readLine()) != null) {
                    nutrerror = parser.setNutritionFacts(curdish, line);
                } else {
                    break;
                }

                //read in empty line separator
                if ((line = _bufread.readLine()) != null) {

                } else {
                    break;
                }

                //read in the location line
                if ((line = _bufread.readLine()) != null) {
                } else {
                    break;
                }

                //read in the empty line separator
                if ((line = _bufread.readLine()) != null) {

                } else {
                    break;
                }

                //read in the line that should have the date
                //use result to check for error and set newdate
                if ((line = _bufread.readLine()) != null) { //this line should have the date
                    Calendar d = parser.setDate(curdish, line, curdate);
                    if (d.equals(curdate)) //if the date returned is the same date, then there was no changed
                    {
                        newdate = false;
                    } else { //if the date returned is different, then there is a newdate and curdate should be updated
                        newdate = true;
                        curdate = d;
                    }
                } else {
                    break;
                }

                //read in the empty line separator
                if ((line = _bufread.readLine()) != null) {

                } else {
                    break;
                }

                //this line says Breakfast, Lunch, Dinner.... etc
                //check this line and the newdate variable to see if the dish should be added to the current meal
                //or if we need a whole new meal
                if ((line = _bufread.readLine()) != null) {
                    //if this is the first dish, so there is no meal create a meal and add the dish && set the date
                    if (curmeal == null) {
                        curmeal = new Meal(line.toLowerCase());
                        curdish.setDate(curdate);
                        curdish.setMeal(curmeal);
                        _dishes.add(curdish);
                    } else if (curmeal.getMeal().equals(line) == false || newdate == true) {
                        //this is the beginning of a new meal, add the cur meal to eatery and create a new one with the most recent date
                        curmeal = new Meal(line.toLowerCase());
                        curdish.setMeal(curmeal);
                        curdish.setDate(curdate);
                    } else { //this is just part of the current meal, add it and move on to the next one
                        curdish.setMeal(curmeal);
                        curdish.setDate(curdate);
                    }
                } else {
                    break;
                }

                //read in the empty line separator
                if ((line = _bufread.readLine()) != null) {

                } else {
                    break;
                }
                _dishes.add(curdish);
            //move on to the next dish in the text file
            }
            
        } catch (FileNotFoundException e) {
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (_instream != null) {
                try {
                    _instream.close();
                } catch (IOException e) {

                }
            }

            if (_bufread != null) {
                try {
                    _bufread.close();
                } catch (IOException e) {

                }
            }

            if (_stream != null) {
                try {
                    _stream.close();
                } catch (IOException e) {

                }
            }
        }
        return 1;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mealplanner;

/**
 *
 * @author seadams
 */
import java.io.Serializable;

public class MarkedDishKey implements Comparable,Serializable {

  private Location _location;
  private Meal _meal;
  private String _date;

  public MarkedDishKey(Location location, Meal meal, String date) {
    _location = location;
    _meal = meal;
    _date = date;
  }

  public int compareTo(Object obj) {
    String objStr = new String(((MarkedDishKey)obj)._location.toString() +
                               ((MarkedDishKey)obj)._meal.toString() +
                               ((MarkedDishKey)obj)._date);
    String thisStr = new String(_location.toString() +
                                _meal.toString() +
                                _date);
    return thisStr.compareTo(objStr);
  }
}

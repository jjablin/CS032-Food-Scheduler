/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mealplanner;

/**
 *
 * @author seadams
 */
public class MarkedDishKey implements Comparable {

  private Location _location;
  private Meal _meal;
  private Day _day;

  public MarkedDishKey(Location location, Meal meal, Day day) {
    _location = location;
    _meal = meal;
    _day = day;
  }

  public int compareTo(Object obj) {
    String objStr = new String(((MarkedDishKey)obj)._location.toString() +
                               ((MarkedDishKey)obj)._meal.toString() +
                               ((MarkedDishKey)obj)._day.toString());
    String thisStr = new String(_location.toString() +
                                _meal.toString() +
                                _day.toString());
    return thisStr.compareTo(objStr);
  }
}

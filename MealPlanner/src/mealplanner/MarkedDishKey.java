/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mealplanner;

/**
 *
 * @author seadams
 */
public class MarkedDishKey {

    private Location _location;
    private Meal _meal;
    private Day _day;

    public MarkedDishKey(Location location, Meal meal, Day day) {
        _location = location;
        _meal = meal;
        _day = day;
    }
}

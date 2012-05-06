package mealplanner;

import java.util.ArrayList;
import java.util.Calendar;
import java.io.Serializable;

public class MarkedDish implements Serializable{

    private static final long serialVersionUID = 7526471155622776147L;
    int _servings;
    private Dish _dish;

    public MarkedDish(Dish dish) {
        _dish = dish;

        _servings = 0;
    }

    public MarkedDish(Dish dish, int servings) {
        _dish = dish;

        _servings = servings;
    }

    public Dish getDish() {
        return _dish;
    }

    public Calendar getDate()
    {
        return _dish.getDate();
    }

    public Meal getMeal()
    {
        return _dish.getMeal();
    }

    public Location getLocation()
    {
        return _dish.getLocation();
    }

    public String getPortion() {
        return _dish.getPortion();
    }

    public double getCalories() {
        return _dish.getCalories();
    }

    public double getFat() {
        return _dish.getFat();
    }

    public ArrayList<String> getIngredients() {//returns null if there are none
        return _dish.getIngredients();
    }

    public String getName() { //returns null if not named
        return _dish.getName();
    }

    public double getSaturatedFat() {
        return _dish.getSaturatedFat();
    }

    public double getChol() {
        return _dish.getChol();
    }

    public double getSodium() {
        return _dish.getSodium();
    }

    public double getCarbs() {
        return _dish.getCarbs();
    }

    public double getFiber() {
        return _dish.getFiber();
    }

    public double getProtein() {
        return _dish.getProtein();
    }

    public int getServings() {
        return _servings;
    }

    public void setServings(int servings) {
        _servings = servings;
    }
}
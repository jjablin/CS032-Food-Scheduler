package mealplanner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Dish implements Serializable{

  private static final long serialVersionUID = 7526471155622776147L;

  private Meal _meal;
  private Calendar _date;
  private Location _location;

	private String _name;
	private ArrayList<String> _ingredients;
	private double _calories;
	private double _fat;
	private double _saturatedFat;
	private double _chol;
	private double _sodium;
	private double _carbs;
	private double _fiber;
	private double _protein;
	private String _portion;

	public Dish(String name){
		_ingredients=new ArrayList<String>();
		_name=name;
		_calories=-1;
		_fat=-1;
		_saturatedFat=-1;
		_chol=-1;
		_sodium=-1;
		_carbs=-1;
		_fiber=-1;
		_protein=-1;
		_portion=null;

                _meal = new Meal("");
                _date = Calendar.getInstance();
                _location = new Location("");
	}

  public Calendar getDate() {
    return _date;
  }

  public void setDate(Calendar date) {
    _date = date;
  }

  public Location getLocation() {
    return _location;
  }

  public void setLocation(Location location) {
    _location = location;
  }

  public Meal getMeal() {
          return _meal;
  }

  public void setMeal(Meal meal) {
    _meal = meal;
  }

  public void setName(String name) {
    _name = name;
  }

	public String getPortion(){
		return _portion;
	}
	public void setPortion(String portion){
		_portion=portion;
	}
	public double getCalories() {
		return _calories;
	}
	public void setCalories(double calories) {
		_calories = calories;
	}
	public double getFat() {
		return _fat;
	}
	public void setFat(double fat) {
		_fat = fat;
	}
	public ArrayList<String> getIngredients(){//returns null if there are none
		return _ingredients;
	}
	public String getName(){ //returns null if not named
		return _name;
	}
	public double getSaturatedFat(){
		return _saturatedFat;
	}
	public double getChol(){
		return _chol;
	}
	public double getSodium(){
		return _sodium;
	}
	public double getCarbs(){
		return _carbs;
	}
	public double getFiber(){
		return _fiber;
	}
	public double getProtein(){
		return _protein;
	}
	public void addIngrediant(String ingrediant){
		if(ingrediant!=null){
			_ingredients.add(ingrediant);
		}
	}

	public void setSaturatedFat(double satf){
		_saturatedFat=satf;
	}
	public void setChol(double chol){
		_chol=chol;
	}
	public void setSodium(double sod){
		_sodium=sod;
	}
	public void setCarbs(double carbs){
		_carbs=carbs;
	}
	public void setFiber(double fiber){
		_fiber=fiber;
	}
	public void setProtein(double prot){
		_protein=prot;
	}

}

package mealplanner;

import java.io.Serializable;

public class Meal implements Serializable{
 String _meal=null; 
 public Meal(String meal){
	 _meal=meal;
 }
 public void setMeal(String meal){
	 _meal=meal;
 }
 public String getMeal(){
	 return _meal;
 }
/*
  public String toString() {
    return super.toString();
  }
  */
}

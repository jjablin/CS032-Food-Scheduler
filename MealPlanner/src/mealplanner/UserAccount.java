/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mealplanner;


import java.util.*;

// A serializable class for storing relevant user information.
public class UserAccount extends NullAccount implements Comparable {

  private static final long serialVersionUID = 7526471155622776147L;

  String _username;
  String _password;
  Set<String> _likes;
  Set<String> _dislikes;
  Set<Allergy> _allergies;
  String _email;
  double _fatGoal;
  double _proteinGoal;
  double _carbGoal;
  double _calGoal;
  Map< MarkedDishKey, ArrayList<MarkedDish>> _dishes;
  Map< String, Location > _selectedLocations; // Key is concatenation of Date and Meal.

  public UserAccount() {
    super();
    _valid = true;
    _username = "";
    _password = "";
    _likes = new HashSet<String>();
    _dislikes = new HashSet<String>();
    _allergies = new HashSet<Allergy>();
    _email = "";
    _dishes = new TreeMap< MarkedDishKey, ArrayList< MarkedDish >>();
    _selectedLocations = new TreeMap< String, Location >();
    _fatGoal = 0;
    _proteinGoal = 0;
    _carbGoal = 0;
    _calGoal = 0;
  }

  public UserAccount(String username, String password) {
    super();
    _valid = true;
    _username = username;
    _password = password;
    _likes = new HashSet<String>();
    _dislikes = new HashSet<String>();
    _allergies = new HashSet<Allergy>();
    _email = "";
    _dishes = new TreeMap< MarkedDishKey, ArrayList< MarkedDish >>();
    _selectedLocations = new TreeMap< String, Location >();
    _fatGoal = 0;
    _proteinGoal = 0;
    _carbGoal = 0;
    _calGoal = 0;
  }

  public UserAccount(String username, String password,
                 Set<String> likes, Set<String> dislikes,
                 Set<Allergy> allergies) {
    super();
    _valid = true;
    _username = username;
    _password = password;
    _likes = likes;
    _dislikes = dislikes;
    _allergies = allergies;
    _email = "";
    _dishes = new TreeMap< MarkedDishKey, ArrayList< MarkedDish >>();
    _selectedLocations = new TreeMap< String, Location >();
    _fatGoal = 0;
    _proteinGoal = 0;
    _carbGoal = 0;
    _calGoal = 0;
  }

  public String getEmail()
  {
      return _email;
  }

  public void setEmail(String email)
  {
      _email = email;
  }

  public double getFatGoal()
  {
      return _fatGoal;
  }

  public double getProteinGoal()
  {
      return _proteinGoal;
  }

  public double getCarbGoal()
  {
      return _carbGoal;
  }

  public double getCalGoal()
  {
      return _calGoal;
  }

  public void setFatGoal(double goal)
  {
      _fatGoal = goal;
  }

  public void setProteinGoal(double goal)
  {
      _proteinGoal = goal;
  }

  public void setCarbGoal(double goal)
  {
      _carbGoal = goal;
  }

  public void setCalGoal(double goal)
  {
      _calGoal = goal;
  }

  public void setSelectedLocation(Calendar date, Meal meal, Location location) {
    String key = new String(FoodDatabase.CalendarToString(date) + meal.getMeal());
    _selectedLocations.put(key, location);
  }

  public Location getSelectedLocation(Calendar date, Meal meal) {
      String key = new String(FoodDatabase.CalendarToString(date) + meal.getMeal());
      if(!_selectedLocations.containsKey(key))
          return new Location("");
      return _selectedLocations.get(key);
  }

  public void addAllergy(Allergy allergy) {
    _allergies.add(allergy);
  }

  public void addDislike(String dislike) {
    if(!_likes.contains(dislike))
      _dislikes.add(dislike);
  }

  public void addLike(String like) {
    if(!_dislikes.contains(like))
      _likes.add(like);
  }

  public void clearAllergies() {
    _allergies.clear();
  }

  public void clearDislikes() {
    _dislikes.clear();
  }

  public void clearLikes() {
    _likes.clear();
  }

  public Set< Allergy > getAllergies() {
    return _allergies;
  }

  public Set< String > getDislikes() {
    return _dislikes;
  }

  public Set< String > getLikes() {
    return _likes;
  }

  public String getPassword() {
    return _password;
  }

  public String getUsername() {
    return _username;
  }

  public void removeLike(String like) {
    _likes.remove(like);
  }

  public void removeDislike(String dislike) {
    _dislikes.remove(dislike);
  }

  public void removeAllergy(Allergy allergy) {
    _allergies.remove(allergy);
  }

  public void setUsername(String username) {
    _username = username;
  }

  public void setPassword(String password) {
    if(password.length() > 0)
      _password = password;
  }

  public void addMarkedDish(MarkedDish dish) {
      //Day day = Day.intToDay(dish.getDate().get(Calendar.DAY_OF_WEEK) - 2);
    MarkedDishKey mdk = new MarkedDishKey(dish.getLocation(),
                                          dish.getMeal(),
                                          FoodDatabase.CalendarToString(dish.getDate()));
      ArrayList<MarkedDish> markedDishes = _dishes.get(mdk);
      if(markedDishes == null) {
          _dishes.put(mdk, new ArrayList<MarkedDish>());
          markedDishes = _dishes.get(mdk);
      }
      markedDishes.add(dish);
  }

  public void removeMarkedDish(Dish dish) {
    MarkedDishKey mdk = new MarkedDishKey(dish.getLocation(),
                                          dish.getMeal(),
                                          FoodDatabase.CalendarToString(dish.getDate()));
    ArrayList<MarkedDish> markedDishes = _dishes.get(mdk);
    for(int i = 0, e = markedDishes.size(); i < e; ++i) {
        if(markedDishes.get(i).getDish().getName().equals(dish.getName())) {
            markedDishes.remove(i);
            break;
        }
    }
    _dishes.put(mdk, markedDishes);
  }


  public List<MarkedDish> getMarkedDishes(Location location, Meal meal, Calendar date) {
      ArrayList<MarkedDish> dishes = _dishes.get(new MarkedDishKey(location, meal,
                                    FoodDatabase.CalendarToString(date)));
      if(dishes == null)
      {
          return new ArrayList<MarkedDish>();
      }
      return dishes;
  }

  @Override
  public String toString() {
    return getUsername() + ", " + getPassword();
  }

  public int compareTo(Object obj) {
    return this.getUsername().compareTo(((UserAccount)obj).getUsername());
  }
}

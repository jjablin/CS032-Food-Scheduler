package food;

import java.io.*;
import java.util.*;

public class Dish implements Serializable {

  private static final long serialVersionUID = 7526471155622776147L;

  Date _date;
  Location _location;
  Meal _meal;
  String _name;

  public Dish() {
    _date = new Date();
    _location = new Location(new String("earth"));
    _meal = Meal.BREAKFAST;
    _name = new String("jello");
  }

  public Date getDate() {
    return _date;
  }

  public Location getLocation() {
    return _location;
  }

  public Meal getMeal() {
    return _meal;
  }

  public String getName() {
    return _name;
  }

  public String toString() {
    return getName() + ", " +
      getDate().toString() + ", " +
      getLocation() + ", " +
      getMeal().toString();
  }
}

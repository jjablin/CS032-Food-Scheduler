package food;

import java.io.*;
import java.util.*;

public class Dish implements Serializable {

  private static final long serialVersionUID = 7526471155622776147L;

  String _name;
  Date _date;
  Meal _meal;

  public Dish() {
    _name = new String("jello");
    _meal = Meal.BREAKFAST;
    _date = new Date();
  }

  public String getName() {
    return _name;
  }

  public Date getDate() {
    return _date;
  }

  public Meal getMeal() {
    return _meal;
  }
}

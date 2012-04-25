package food;

import java.io.*;
import java.util.*;

public class MarkedDish extends Dish {

  private static final long serialVersionUID = 7526471155622776147L;

  double _servings;

  public MarkedDish(Dish dish) {
    super();

    _servings = 0.0;
  }

  public MarkedDish(Dish dish, double servings) {
    super();

    _servings = servings;
  }


  public double getServings() {
    return _servings;
  }

  public void setServings(double servings) {
    _servings = servings;
  }
}

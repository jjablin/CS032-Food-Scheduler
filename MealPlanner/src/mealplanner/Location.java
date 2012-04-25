package mealplanner;

import java.io.*;

public class Location implements Serializable {

  private static final long serialVersionUID = 7526471155622776147L;

  String _name;

  public Location() {
    _name = new String();
  }

  public Location(String name) {
    _name = name;
  }

  public String getName() {
    return _name;
  }

  public void setName(String name) {
    _name = name;
  }

    @Override
  public String toString() {
    return _name;
  }
}

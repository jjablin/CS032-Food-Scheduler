package mealplanner;

public enum DatabaseType {
  DATE, SERIALIZABLE, STRING;

  public String toString() {
    String type = super.toString();
    if(type.equals("SERIALIZABLE"))
      return "other";
    else if(type.equals("STRING"))
      return "varchar_casesensitive(255)";
    else
      return type;
  }
}

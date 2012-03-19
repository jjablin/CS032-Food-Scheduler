package food;

public enum DatabaseType {
  STRING, SERIALIZABLE;

  public String toString() {
    String type = super.toString();
    if(type.equals("STRING"))
      return "varchar_casesensitive(255)";
    else if(type.equals("SERIALIZABLE"))
      return "other";
    else
      return "unknown";
  }
}
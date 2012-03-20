package food;

public class DatabaseVariable {
  DatabaseType _type;
  String _name;
  boolean _key;

  public DatabaseVariable(DatabaseType type, String name) {
    _type = type;
    _name = name;
    _key = false;
  }

  public DatabaseVariable(DatabaseType type, String name, boolean key) {
    _type = type;
    _name = name;
    _key = key;
  }

  public String getName() {
    return _name;
 }

  public DatabaseType getType() {
    return _type;
  }

  public boolean isKey() {
    return _key;
  }

  public void setKey(boolean key) {
    _key = key;
  }

  public void setName(String name) {
    _name = name;
  }

  public void setType(DatabaseType type) {
    _type = type;
  }

  public String toString() {
    return _name + " " + _type.toString() + " " + keyToString();
  }

  String keyToString() {
    return (isKey() ? "primary key" : "");
  }
}

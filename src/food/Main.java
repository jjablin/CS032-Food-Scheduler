package  food;

import java.util.*;

public class Main {

  public static void main(String[] args) {
    FoodDatabase db = new FoodDatabase("testdb");
    ArrayList<DatabaseVariable> variables = new ArrayList<DatabaseVariable>();
    variables.add(new DatabaseVariable(DatabaseType.STRING, "username"));
    variables.add(new DatabaseVariable(DatabaseType.SERIALIZABLE, "account"));
    db.createTable("users", variables);
    UserAccount user = new UserAccount("jamie", "123");
    db.addUser(user);
    Account result = db.getUser(user);

    if(result.isValid())
      System.out.println(((UserAccount)result).toString());
    db.writeUsersCSV("test.csv");
    db.clear();
    db.close();
    System.out.println("Hello World!");
  }
}

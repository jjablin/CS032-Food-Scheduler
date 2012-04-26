package mealplanner;

import java.io.*;
import java.sql.*;
import java.util.*;
import org.h2.jdbcx.*;

public class FoodDatabase {
  String _fileURL;
  Connection _conn;

  public FoodDatabase(String fileURL) {
    _fileURL = fileURL;
    String url = "jdbc:h2:" + _fileURL;
    try {
      DriverManager.registerDriver(new org.h2.Driver());
      _conn = DriverManager.getConnection(url, "sa", "sa");
    } catch(SQLException sqle) {
      throw new IllegalStateException("Failed to initialize database: " +
                                      sqle.getMessage());
    }
  }

  public void close() {
    try {
      _conn.close();
    } catch(SQLException sqle) {
      throw new IllegalStateException("Failed to close database connection: " +
                                      sqle.getMessage());
    }
  }

  public void clear() {
    StringBuilder cmd = new StringBuilder();
    cmd.append("drop all objects delete files");
    executeCommand(cmd.toString());
  }

  public void update() {
    // Call Kyle's pdf parsing routines and update database.
    PDFParser parse = new PDFParser();
    ArrayList<Dish> results = parse.getCurrentEateryInfo();
    for(Dish dish : results) {
      addDish(dish);
    }
  }

  public void createDishesTable() {
    ArrayList< DatabaseVariable > variables = new ArrayList< DatabaseVariable >();
    variables.add(new DatabaseVariable(DatabaseType.DATE, "date"));
    variables.add(new DatabaseVariable(DatabaseType.STRING, "location"));
    variables.add(new DatabaseVariable(DatabaseType.STRING, "meal"));
    variables.add(new DatabaseVariable(DatabaseType.STRING, "name"));
        variables.add(new DatabaseVariable(DatabaseType.SERIALIZABLE, "dish"));
    createTable("dishes", variables);
  }

  public boolean addDish(Dish dish) {
    int result;
    StringBuilder cmd = new StringBuilder();
    cmd.append("insert into ");
    cmd.append("dishes(date, location, meal, name, dish) ");
    cmd.append("values(?, ?, ?, ?, ?)");
    PreparedStatement pstmt = null;
    try {
      pstmt = _conn.prepareStatement(cmd.toString());
      pstmt.setDate(1, new java.sql.Date(dish.getDate().getTime().getTime()));
      pstmt.setString(2, dish.getLocation().toString());
      pstmt.setString(3, dish.getMeal().toString());
      pstmt.setString(4, dish.getName());


      // Serialize Dish object.
      Pack pack = new Pack(dish);
      pstmt.setBinaryStream(5, pack.getStream(), pack.getLength());
      result = pstmt.executeUpdate();
      pstmt.close();
    } catch(SQLException sqle) {
      throw new IllegalStateException("Problems executing pstmt: " +
                                      sqle.getMessage());
    } finally {
      try {
        pstmt.close();
      } catch(Throwable ignore) {
      }
    }
    return result > 0;
  }

  public boolean removeDish(Dish dish) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("delete from dishes where name='");
    cmd.append(dish.getName());
    cmd.append("'");
    return executeCommand(cmd.toString());
  }

  public boolean updateDish(Dish dish) {
    removeDish(dish);
    return addDish(dish);
  }

  public HashSet getDishes(Dish dish) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where name='");
    cmd.append(dish.getName());
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public HashSet getDishes(Calendar date) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where date='");
    cmd.append(new java.sql.Date(date.getTime().getTime()));
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public HashSet getDishes(Location location) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where location='");
    cmd.append(location.getName());
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public HashSet getDishes(Meal meal) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where meal='");
    cmd.append(meal.toString());
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public HashSet getDishes(String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where name='");
    cmd.append(name);
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public HashSet getDishes(Calendar date, Location location) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where date='");
    cmd.append(new java.sql.Date(date.getTime().getTime()));
    cmd.append("' and location='");
    cmd.append(location.getName());
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public HashSet getDishes(Location location, Calendar date) {
    return getDishes(date, location);
  }

  public HashSet getDishes(Calendar date, Meal meal) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where date='");
    cmd.append(new java.sql.Date(date.getTime().getTime()));
    cmd.append("' and meal='");
    cmd.append(meal.toString());
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public HashSet getDishes(Meal meal, Calendar date) {
    return getDishes(date, meal);
  }

  public HashSet getDishes(Calendar date, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where date='");
    cmd.append(new java.sql.Date(date.getTime().getTime()));
    cmd.append("' and name='");
    cmd.append(name);
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public HashSet getDishes(String name, Calendar date) {
    return getDishes(date, name);
  }

  public HashSet getDishes(Location location, Meal meal) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where location='");
    cmd.append(location.getName());
    cmd.append("' and meal='");
    cmd.append(meal.toString());
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public HashSet getDishes(Meal meal, Location location) {
    return getDishes(location, meal);
  }

  public HashSet getDishes(Location location, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where location='");
    cmd.append(location.getName());
    cmd.append("' and name='");
    cmd.append(name);
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public HashSet getDishes(String name, Location location) {
    return getDishes(location, name);
  }

  public HashSet getDishes(Meal meal, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where meal='");
    cmd.append(meal.toString());
    cmd.append("' and name='");
    cmd.append(name);
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public HashSet getDishes(String name, Meal meal) {
    return getDishes(meal, name);
  }

  public HashSet getDishes(Calendar date, Location location, Meal meal) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where date='");
    cmd.append(new java.sql.Date(date.getTime().getTime()));
    cmd.append("' and location='");
    cmd.append(location.getName());
    cmd.append("' and meal='");
    cmd.append(meal.toString());
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public HashSet getDishes(Calendar date, Meal meal, Location location) {
    return getDishes(date, location, meal);
  }

  public HashSet getDishes(Meal meal, Calendar date, Location location) {
    return getDishes(date, location, meal);
  }

  public HashSet getDishes(Meal meal, Location location, Calendar date) {
    return getDishes(date, location, meal);
  }

  public HashSet getDishes(Location location, Meal meal, Calendar date) {
    return getDishes(date, location, meal);
  }

  public HashSet getDishes(Location location, Calendar date, Meal meal) {
    return getDishes(date, location, meal);
  }

  public HashSet getDishes(Calendar date, Location location, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where date='");
    cmd.append(new java.sql.Date(date.getTime().getTime()));
    cmd.append("' and location='");
    cmd.append(location.getName());
    cmd.append("' and name='");
    cmd.append(name);
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public HashSet getDishes(Calendar date, String name, Location location) {
    return getDishes(date, location, name);
  }

  public HashSet getDishes(String name, Calendar date, Location location) {
    return getDishes(date, location, name);
  }

  public HashSet getDishes(String name, Location location, Calendar date) {
    return getDishes(date, location, name);
  }

  public HashSet getDishes(Location location, String name, Calendar date) {
    return getDishes(date, location, name);
  }

  public HashSet getDishes(Location location, Calendar date, String name) {
    return getDishes(date, location, name);
  }

  public HashSet getDishes(Calendar date, Meal meal, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where date='");
    cmd.append(new java.sql.Date(date.getTime().getTime()));
    cmd.append("' and meal='");
    cmd.append(meal.toString());
    cmd.append("' and name='");
    cmd.append(name);
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public HashSet getDishes(Calendar date, String name, Meal meal) {
    return getDishes(date, meal, name);
  }

  public HashSet getDishes(String name, Calendar date, Meal meal) {
    return getDishes(date, meal, name);
  }

  public HashSet getDishes(String name, Meal meal, Calendar date) {
    return getDishes(date, meal, name);
  }

  public HashSet getDishes(Meal meal, String name, Calendar date) {
    return getDishes(date, meal, name);
  }

  public HashSet getDishes(Meal meal, Calendar date, String name) {
    return getDishes(date, meal, name);
  }

  public HashSet getDishes(Location location, Meal meal, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where location='");
    cmd.append(location.getName());
    cmd.append("' and meal='");
    cmd.append(meal.toString());
    cmd.append("' and name='");
    cmd.append(name);
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public HashSet getDishes(Location location, String name, Meal meal) {
    return getDishes(location, meal, name);
  }

  public HashSet getDishes(String name, Location location, Meal meal) {
    return getDishes(location, meal, name);
  }

  public HashSet getDishes(String name, Meal meal, Location location) {
    return getDishes(location, meal, name);
  }

  public HashSet getDishes(Meal meal, String name, Location location) {
    return getDishes(location, meal, name);
  }

  public HashSet getDishes(Meal meal, Location location, String name) {
    return getDishes(location, meal, name);
  }

  public void createUsersTable() {
    ArrayList< DatabaseVariable > variables = new ArrayList< DatabaseVariable >();
    variables.add(new DatabaseVariable(DatabaseType.STRING, "username"));
    variables.add(new DatabaseVariable(DatabaseType.SERIALIZABLE, "account"));
    createTable("users", variables);
  }

  public boolean addUser(UserAccount account) {
    int result;
    StringBuilder cmd = new StringBuilder();
    cmd.append("insert into ");
    cmd.append("users(username, account) values(?, ?)");
    PreparedStatement pstmt = null;
    try {
      pstmt = _conn.prepareStatement(cmd.toString());
      pstmt.setString(1, account.getUsername());

      // Serialize UserAccount object.
      Pack pack = new Pack(account);
      pstmt.setBinaryStream(2, pack.getStream(), pack.getLength());
      result = pstmt.executeUpdate();
      pstmt.close();
    } catch(SQLException sqle) {
      throw new IllegalStateException("Problems executing pstmt: " +
                                      sqle.getMessage());
    } finally {
      try {
        pstmt.close();
      } catch(Throwable ignore) {
      }
    }
    return result > 0;
  }

  public Account getUser(String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select account from users where username='");
    cmd.append(name);
    cmd.append("'");
    return executeGetUserCommand(cmd.toString());
  }

  public Account getUser(UserAccount account) {
    if(!account.isValid())
      return account;

    StringBuilder cmd = new StringBuilder();
    cmd.append("select account from users where username='");
    cmd.append(account.getUsername());
    cmd.append("'");
    Account result = executeGetUserCommand(cmd.toString());
    return result.isValid() ? checkPassword((UserAccount)result, account) : result;
  }

  public boolean updateUser(UserAccount account) {
    removeUser(account);
    return addUser(account);
  }

  public boolean removeUser(UserAccount account) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("delete from users where username='");
    cmd.append(account.getUsername());
    cmd.append("'");
    return executeCommand(cmd.toString());
  }

  public void createTable(String tablename, List< DatabaseVariable > variables) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("create table if not exists ");
    cmd.append(tablename);
    cmd.append("(");
    ListIterator< DatabaseVariable > it = variables.listIterator();
    while(it.hasNext()) {
      cmd.append(it.next().toString());
      cmd.append(", ");
    }
    cmd.append(")");
    executeCommand(cmd.toString());
  }

  public void dropTable(String tablename) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("drop table ");
    cmd.append(tablename);
    executeCommand(cmd.toString());
  }

  public void writeUsersCSV(String filename) {
    writeCSV("users", filename);
  }

  public void writeDishesCSV(String filename) {
    writeCSV("dishes", filename);
  }

  // FROM tablename, TO filename
  void writeCSV(String tablename, String filename) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("call csvwrite('");
    cmd.append(filename);
    cmd.append("', ");
    cmd.append("'select * from ");
    cmd.append(tablename);
    cmd.append("')");
    executeCommand(cmd.toString());
  }

  boolean executeCommand(String cmd) {
    boolean result;
    Statement stmt = null;
    try {
      stmt = _conn.createStatement();
      result = stmt.execute(cmd);
      stmt.close();
    } catch(SQLException sqle) {
      throw new IllegalStateException("Problems executing stmt: " +
                                      sqle.getMessage());
    } finally {
      try {
        stmt.close();
      } catch(Throwable ignore) {
        System.out.println("test555");
      }
    }
    return result;
  }

  HashSet executeSelect(String cmd) {
    try {
      Statement stmt = _conn.createStatement();
      ResultSet results = stmt.executeQuery(cmd.toString());
      HashSet<Object> resultSet = new HashSet<Object>();
      while(results.next()) {
          resultSet.add(results.getObject(1));
      }
      return resultSet;
    } catch(SQLException sqle) {
      throw new IllegalStateException("Failed attempting to query database.");
    }
  }

  Account executeGetUserCommand(String cmd) {
    HashSet resultSet = executeSelect(cmd.toString());
    if(resultSet.isEmpty())
      return new NullAccount();
    return (Account)resultSet.iterator().next();
  }

  Account checkPassword(UserAccount test, UserAccount master) {
    // Check if password is correct.
    return (test.isValid() && test.getPassword().equals(master.getPassword())) ?
      test : new NullAccount();
  }
}

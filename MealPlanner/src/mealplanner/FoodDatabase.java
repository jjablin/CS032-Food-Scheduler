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
    variables.add(new DatabaseVariable(DatabaseType.STRING, "date"));
    variables.add(new DatabaseVariable(DatabaseType.STRING, "location"));
    variables.add(new DatabaseVariable(DatabaseType.STRING, "meal"));
    variables.add(new DatabaseVariable(DatabaseType.STRING, "name"));
        variables.add(new DatabaseVariable(DatabaseType.SERIALIZABLE, "dish"));
    createTable("dishes", variables);
  }

  public static Calendar StringToCalendar(String now) {    
    Calendar day = Calendar.getInstance();
    String[] parts = now.split("/");
    day.set(Calendar.MONTH, Integer.parseInt(parts[0]));
    day.set(Calendar.DATE, Integer.parseInt(parts[1]));
    day.set(Calendar.YEAR, Integer.parseInt(parts[2]));
    return day;
  }

  public Calendar getCurrentDate() {
    /* To calculate the number of days since the dishes table has been updated... */
    //java.util.Calendar now = java.util.Calendar.getInstance();
    //java.util.Calendar dbDate = fd.getCurrentDate();
    //int days = now.get(java.util.Calendar.DAY_OF_YEAR) - dbDate.get(java.util.Calendar.DAY_OF_YEAR);

    StringBuilder cmd = new StringBuilder();
    cmd.append("select max(date) from dishes");
    TreeSet resultSet = executeSelect(cmd.toString());
    String date = new String("01/01/2012");
    if(!resultSet.isEmpty()) {
        String max = (String)resultSet.iterator().next();        
        if(max != null)
            date = max;
    }
    return StringToCalendar(date);
  }

  public static String CalendarToString(Calendar now) {
    return new String(now.get(Calendar.MONTH) + "/" +
                      now.get(Calendar.DATE) + "/" +
                      now.get(Calendar.YEAR));
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
      pstmt.setString(1, CalendarToString(dish.getDate()));
      pstmt.setString(2, dish.getLocation().getName());
      pstmt.setString(3, dish.getMeal().getMeal());
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

  public TreeSet getDishes(Dish dish) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where name='");
    cmd.append(dish.getName());
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getDishes(Calendar date) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where date='");
    cmd.append(CalendarToString(date));
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getDishes(Location location) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where location='");
    cmd.append(location.getName());
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getDishes(Meal meal) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where meal='");
    cmd.append(meal.getMeal());
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getDishes(String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where name='");
    cmd.append(name);
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getFuzzyDishes(String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where name like '%");
    cmd.append(name);
    cmd.append("%'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getDishes(Calendar date, Location location) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where date='");
    cmd.append(CalendarToString(date));
    cmd.append("' and location='");
    cmd.append(location.getName());
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getDishes(Location location, Calendar date) {
    return getDishes(date, location);
  }

  public TreeSet getDishes(Calendar date, Meal meal) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where date='");
    cmd.append(CalendarToString(date));
    cmd.append("' and meal='");
    cmd.append(meal.getMeal());
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getDishes(Meal meal, Calendar date) {
    return getDishes(date, meal);
  }

  public TreeSet getDishes(Calendar date, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where date='");
    cmd.append(CalendarToString(date));
    cmd.append("' and name='");
    cmd.append(name);
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getFuzzyDishes(Calendar date, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where date='");
    cmd.append(CalendarToString(date));
    cmd.append("' and name like '%");
    cmd.append(name);
    cmd.append("%'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getDishes(String name, Calendar date) {
    return getDishes(date, name);
  }

  public TreeSet getFuzzyDishes(String name, Calendar date) {
    return getFuzzyDishes(date, name);
  }

  public TreeSet getDishes(Location location, Meal meal) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where location='");
    cmd.append(location.getName());
    cmd.append("' and meal='");
    cmd.append(meal.getMeal());
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getDishes(Meal meal, Location location) {
    return getDishes(location, meal);
  }

  public TreeSet getDishes(Location location, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where location='");
    cmd.append(location.getName());
    cmd.append("' and name='");
    cmd.append(name);
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getFuzzyDishes(Location location, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where location='");
    cmd.append(location.getName());
    cmd.append("' and name like '%");
    cmd.append(name);
    cmd.append("%'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getDishes(String name, Location location) {
    return getDishes(location, name);
  }

  public TreeSet getFuzzyDishes(String name, Location location) {
    return getFuzzyDishes(location, name);
  }

  public TreeSet getDishes(Meal meal, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where meal='");
    cmd.append(meal.getMeal());
    cmd.append("' and name='");
    cmd.append(name);
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getFuzzyDishes(Meal meal, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where meal='");
    cmd.append(meal.getMeal());
    cmd.append("' and name like '%");
    cmd.append(name);
    cmd.append("%'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getDishes(String name, Meal meal) {
    return getDishes(meal, name);
  }

  public TreeSet getFuzzyDishes(String name, Meal meal) {
    return getFuzzyDishes(meal, name);
  }

  public TreeSet getDishes(Calendar date, Location location, Meal meal) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where date='");
    cmd.append(CalendarToString(date));
    cmd.append("' and location='");
    cmd.append(location.getName());
    cmd.append("' and meal='");
    cmd.append(meal.getMeal());
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getDishes(Calendar date, Meal meal, Location location) {
    return getDishes(date, location, meal);
  }

  public TreeSet getDishes(Meal meal, Calendar date, Location location) {
    return getDishes(date, location, meal);
  }

  public TreeSet getDishes(Meal meal, Location location, Calendar date) {
    return getDishes(date, location, meal);
  }

  public TreeSet getDishes(Location location, Meal meal, Calendar date) {
    return getDishes(date, location, meal);
  }

  public TreeSet getDishes(Location location, Calendar date, Meal meal) {
    return getDishes(date, location, meal);
  }

  public TreeSet getDishes(Calendar date, Location location, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where date='");
    cmd.append(CalendarToString(date));
    cmd.append("' and location='");
    cmd.append(location.getName());
    cmd.append("' and name='");
    cmd.append(name);
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getFuzzyDishes(Calendar date, Location location, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where date='");
    cmd.append(CalendarToString(date));
    cmd.append("' and location='");
    cmd.append(location.getName());
    cmd.append("' and name like '%");
    cmd.append(name);
    cmd.append("%'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getDishes(Calendar date, String name, Location location) {
    return getDishes(date, location, name);
  }

  public TreeSet getFuzzyDishes(Calendar date, String name, Location location) {
    return getFuzzyDishes(date, location, name);
  }

  public TreeSet getDishes(String name, Calendar date, Location location) {
    return getDishes(date, location, name);
  }

  public TreeSet getFuzzyDishes(String name, Calendar date, Location location) {
    return getFuzzyDishes(date, location, name);
  }

  public TreeSet getDishes(String name, Location location, Calendar date) {
    return getDishes(date, location, name);
  }

  public TreeSet getFuzzyDishes(String name, Location location, Calendar date) {
    return getFuzzyDishes(date, location, name);
  }

  public TreeSet getDishes(Location location, String name, Calendar date) {
    return getDishes(date, location, name);
  }

  public TreeSet getFuzzyDishes(Location location, String name, Calendar date) {
    return getFuzzyDishes(date, location, name);
  }

  public TreeSet getDishes(Location location, Calendar date, String name) {
    return getDishes(date, location, name);
  }

  public TreeSet getFuzzyDishes(Location location, Calendar date, String name) {
    return getFuzzyDishes(date, location, name);
  }

  public TreeSet getDishes(Calendar date, Meal meal, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where date='");
    cmd.append(CalendarToString(date));
    cmd.append("' and meal='");
    cmd.append(meal.getMeal());
    cmd.append("' and name='");
    cmd.append(name);
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getFuzzyDishes(Calendar date, Meal meal, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where date='");
    cmd.append(CalendarToString(date));
    cmd.append("' and meal='");
    cmd.append(meal.getMeal());
    cmd.append("' and name like '%");
    cmd.append(name);
    cmd.append("%'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getDishes(Calendar date, String name, Meal meal) {
    return getDishes(date, meal, name);
  }

  public TreeSet getFuzzyDishes(Calendar date, String name, Meal meal) {
    return getFuzzyDishes(date, meal, name);
  }

  public TreeSet getDishes(String name, Calendar date, Meal meal) {
    return getDishes(date, meal, name);
  }

  public TreeSet getFuzzyDishes(String name, Calendar date, Meal meal) {
    return getFuzzyDishes(date, meal, name);
  }

  public TreeSet getDishes(String name, Meal meal, Calendar date) {
    return getDishes(date, meal, name);
  }

  public TreeSet getFuzzyDishes(String name, Meal meal, Calendar date) {
    return getFuzzyDishes(date, meal, name);
  }

  public TreeSet getDishes(Meal meal, String name, Calendar date) {
    return getDishes(date, meal, name);
  }

  public TreeSet getFuzzyDishes(Meal meal, String name, Calendar date) {
    return getFuzzyDishes(date, meal, name);
  }

  public TreeSet getDishes(Meal meal, Calendar date, String name) {
    return getDishes(date, meal, name);
  }

  public TreeSet getFuzzyDishes(Meal meal, Calendar date, String name) {
    return getFuzzyDishes(date, meal, name);
  }

  public TreeSet getDishes(Location location, Meal meal, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where location='");
    cmd.append(location.getName());
    cmd.append("' and meal='");
    cmd.append(meal.getMeal());
    cmd.append("' and name='");
    cmd.append(name);
    cmd.append("'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getFuzzyDishes(Location location, Meal meal, String name) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select dish from dishes where location='");
    cmd.append(location.getName());
    cmd.append("' and meal='");
    cmd.append(meal.getMeal());
    cmd.append("' and name like '%");
    cmd.append(name);
    cmd.append("%'");
    return executeSelect(cmd.toString());
  }

  public TreeSet getDishes(Location location, String name, Meal meal) {
    return getDishes(location, meal, name);
  }

  public TreeSet getFuzzyDishes(Location location, String name, Meal meal) {
    return getFuzzyDishes(location, meal, name);
  }

  public TreeSet getDishes(String name, Location location, Meal meal) {
    return getDishes(location, meal, name);
  }

  public TreeSet getFuzzyDishes(String name, Location location, Meal meal) {
    return getFuzzyDishes(location, meal, name);
  }

  public TreeSet getDishes(String name, Meal meal, Location location) {
    return getDishes(location, meal, name);
  }

  public TreeSet getFuzzyDishes(String name, Meal meal, Location location) {
    return getFuzzyDishes(location, meal, name);
  }

  public TreeSet getDishes(Meal meal, String name, Location location) {
    return getDishes(location, meal, name);
  }

  public TreeSet getFuzzyDishes(Meal meal, String name, Location location) {
    return getFuzzyDishes(location, meal, name);
  }

  public TreeSet getDishes(Meal meal, Location location, String name) {
    return getDishes(location, meal, name);
  }

  public TreeSet getFuzzyDishes(Meal meal, Location location, String name) {
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
      }
    }
    return result;
  }

  TreeSet executeSelect(String cmd) {
    try {
      Statement stmt = _conn.createStatement();
      ResultSet results = stmt.executeQuery(cmd.toString());
      TreeSet<Object> resultSet = new TreeSet<Object>();
      while(results.next()) {
        resultSet.add(results.getObject(1));
      }
      return resultSet;
    } catch(SQLException sqle) {
      throw new IllegalStateException("Failed attempting to query database.");
    }
  }

  Account executeGetUserCommand(String cmd) {
    TreeSet resultSet = executeSelect(cmd.toString());
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

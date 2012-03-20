package tests;

import food.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import junit.framework.*;

public class FoodDatabaseTest extends TestCase {

  void compare(String filenameA, String filenameB) {
    FileInputStream streamA = null, streamB = null;
    try {
      streamA = new FileInputStream(filenameA);
      streamB = new FileInputStream(filenameB);
    } catch(FileNotFoundException fnfe) {
      assertTrue(false);
    }
    int intA = 0, intB = 0;
    while(intA != -1 && intB != -1)
      try {
        intA = streamA.read();
        intB = streamB.read();
        assertTrue(intA == intB);
      } catch(IOException ioe) {
        assertTrue(false);
      }
  }

  void deleteFile(String filename) {
    assertTrue(new File(filename).delete());
  }

  public void testBadDatabase() {
    try {
      // Fail attempting to create and open a database at /dev/null/.
      FoodDatabase db = new FoodDatabase("/dev/null/test");
      assertTrue(false);
    } catch(IllegalStateException ise) {
      assertTrue(true);
    }
  }

  public void testGoodEmptyDatabase() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseEmptyUsersTable() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createUsersTable();
      db.writeUsersCSV("test/tests/testGoodDatabaseEmptyUsersTable.test");
      compare("test/tests/testGoodDatabaseEmptyUsersTable.test",
              "test/tests/testGoodDatabaseEmptyUsersTable.master");
      deleteFile("test/tests/testGoodDatabaseEmptyUsersTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseUsersTable() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createUsersTable();

      // Create some users.
      UserAccount emily = new UserAccount("emily", "123");
      UserAccount rick  = new UserAccount("rick", "pswd");
      UserAccount sam   = new UserAccount("sam", "sams_password");
      UserAccount phil  = new UserAccount("phil", "09876");
      UserAccount bob   = new UserAccount("bob", "0BPhU5(473");

      db.addUser(emily);
      db.addUser(rick);
      db.addUser(sam);
      db.addUser(phil);
      db.addUser(bob);

      db.removeUser(emily);
      db.removeUser(rick);
      db.removeUser(sam);
      db.removeUser(phil);
      db.removeUser(bob);

      db.writeUsersCSV("test/tests/testGoodDatabaseUsersTable.test");
      compare("test/tests/testGoodDatabaseUsersTable.test",
              "test/tests/testGoodDatabaseEmptyUsersTable.master");
      deleteFile("test/tests/testGoodDatabaseUsersTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseQueryingUsersTableWithName() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createUsersTable();

      // Create some users.
      UserAccount emily = new UserAccount("emily", "123");
      UserAccount rick  = new UserAccount("rick", "pswd");

      db.addUser(emily);
      db.addUser(rick);

      UserAccount badEmily = new UserAccount("emily", "124"); // Bad password.
      Account result = db.getUser(badEmily);
      assertFalse(result.isValid());

      UserAccount badRick = new UserAccount("ricky", "pswd"); // Bad username.
      result = db.getUser(badRick);
      assertFalse(result.isValid());

      UserAccount goodRick = new UserAccount("rick", "pswd"); // Good username.
      result = db.getUser(goodRick);
      assertTrue(result.isValid());

      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseUpdatingUsersTable() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createUsersTable();

      // Create some users.
      UserAccount emily = new UserAccount("emily", "123");
      UserAccount rick  = new UserAccount("rick", "pswd");

      db.addUser(emily);
      db.addUser(rick);

      // Update username.
      emily.setUsername(new String("emilie"));
      assertTrue(db.updateUser(emily));

      // Update password.
      rick.setPassword(new String("password"));
      assertTrue(db.updateUser(rick));

      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseEmptyDishesTable() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();
      db.writeDishesCSV("test/tests/testGoodDatabaseEmptyDishesTable.test");
      compare("test/tests/testGoodDatabaseEmptyDishesTable.test",
              "test/tests/testGoodDatabaseEmptyDishesTable.master");
      deleteFile("test/tests/testGoodDatabaseEmptyDishesTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseDishesTable() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();

      // Create a dish.
      Dish jello = new Dish();

      db.addDish(jello);

      db.removeDish(jello);

      db.writeDishesCSV("test/tests/testGoodDatabaseDishesTable.test");
      compare("test/tests/testGoodDatabaseDishesTable.test",
              "test/tests/testGoodDatabaseEmptyDishesTable.master");
      deleteFile("test/tests/testGoodDatabaseDishesTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseQueryingDishesTableWithDish() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();

      // Create a dish.
      Dish jello = new Dish();

      db.addDish(jello);

      ResultSet result = db.getDishes(jello);
      try {
        result.next();

        assertTrue(result.getObject(1).toString().equals(jello.toString()));
      } catch(SQLException sqle) {
        assertTrue(false);
      }

      db.removeDish(jello);

      db.writeDishesCSV("test/tests/testGoodDatabaseDishesTable.test");
      compare("test/tests/testGoodDatabaseDishesTable.test",
              "test/tests/testGoodDatabaseEmptyDishesTable.master");
      deleteFile("test/tests/testGoodDatabaseDishesTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseQueryingDishesTableWithName() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();

      // Create a dish.
      Dish jello = new Dish();

      db.addDish(jello);

      ResultSet result = db.getDishes(jello.getName());
      try {
        result.next();
        assertTrue(result.getObject(1).toString().equals(jello.toString()));
      } catch(SQLException sqle) {
        assertTrue(false);
      }

      db.removeDish(jello);

      db.writeDishesCSV("test/tests/testGoodDatabaseDishesTable.test");
      compare("test/tests/testGoodDatabaseDishesTable.test",
              "test/tests/testGoodDatabaseEmptyDishesTable.master");
      deleteFile("test/tests/testGoodDatabaseDishesTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseQueryingDishesTableWithDate() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();

      // Create a dish.
      Dish jello = new Dish();

      db.addDish(jello);

      ResultSet result = db.getDishes(jello.getDate());
      try {
        result.next();
        assertTrue(result.getObject(1).toString().equals(jello.toString()));
      } catch(SQLException sqle) {
        assertTrue(false);
      }

      db.removeDish(jello);

      db.writeDishesCSV("test/tests/testGoodDatabaseDishesTable.test");
      compare("test/tests/testGoodDatabaseDishesTable.test",
              "test/tests/testGoodDatabaseEmptyDishesTable.master");
      deleteFile("test/tests/testGoodDatabaseDishesTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseQueryingDishesTableWithLocation() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();

      // Create a dish.
      Dish jello = new Dish();

      db.addDish(jello);

      ResultSet result = db.getDishes(jello.getLocation());
      try {
        result.next();
        assertTrue(result.getObject(1).toString().equals(jello.toString()));
      } catch(SQLException sqle) {
        assertTrue(false);
      }

      db.removeDish(jello);

      db.writeDishesCSV("test/tests/testGoodDatabaseDishesTable.test");
      compare("test/tests/testGoodDatabaseDishesTable.test",
              "test/tests/testGoodDatabaseEmptyDishesTable.master");
      deleteFile("test/tests/testGoodDatabaseDishesTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseQueryingDishesTableWithMeal() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();

      // Create a dish.
      Dish jello = new Dish();

      db.addDish(jello);

      ResultSet result = db.getDishes(jello.getMeal());
      try {
        result.next();
        assertTrue(result.getObject(1).toString().equals(jello.toString()));
      } catch(SQLException sqle) {
        assertTrue(false);
      }

      db.removeDish(jello);

      db.writeDishesCSV("test/tests/testGoodDatabaseDishesTable.test");
      compare("test/tests/testGoodDatabaseDishesTable.test",
              "test/tests/testGoodDatabaseEmptyDishesTable.master");
      deleteFile("test/tests/testGoodDatabaseDishesTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseQueryingDishesTableWithDateAndLocation() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();

      // Create a dish.
      Dish jello = new Dish();

      db.addDish(jello);

      ResultSet result = db.getDishes(jello.getLocation(), jello.getDate());
      try {
        result.next();
        assertTrue(result.getObject(1).toString().equals(jello.toString()));
      } catch(SQLException sqle) {
        assertTrue(false);
      }

      db.removeDish(jello);

      db.writeDishesCSV("test/tests/testGoodDatabaseDishesTable.test");
      compare("test/tests/testGoodDatabaseDishesTable.test",
              "test/tests/testGoodDatabaseEmptyDishesTable.master");
      deleteFile("test/tests/testGoodDatabaseDishesTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseQueryingDishesTableWithDateAndMeal() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();

      // Create a dish.
      Dish jello = new Dish();

      db.addDish(jello);

      ResultSet result = db.getDishes(jello.getMeal(), jello.getDate());
      try {
        result.next();
        assertTrue(result.getObject(1).toString().equals(jello.toString()));
      } catch(SQLException sqle) {
        assertTrue(false);
      }

      db.removeDish(jello);

      db.writeDishesCSV("test/tests/testGoodDatabaseDishesTable.test");
      compare("test/tests/testGoodDatabaseDishesTable.test",
              "test/tests/testGoodDatabaseEmptyDishesTable.master");
      deleteFile("test/tests/testGoodDatabaseDishesTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseQueryingDishesTableWithDateAndName() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();

      // Create a dish.
      Dish jello = new Dish();

      db.addDish(jello);

      ResultSet result = db.getDishes(jello.getName(), jello.getDate());
      try {
        result.next();
        assertTrue(result.getObject(1).toString().equals(jello.toString()));
      } catch(SQLException sqle) {
        assertTrue(false);
      }

      db.removeDish(jello);

      db.writeDishesCSV("test/tests/testGoodDatabaseDishesTable.test");
      compare("test/tests/testGoodDatabaseDishesTable.test",
              "test/tests/testGoodDatabaseEmptyDishesTable.master");
      deleteFile("test/tests/testGoodDatabaseDishesTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseQueryingDishesTableWithLocationAndMeal() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();

      // Create a dish.
      Dish jello = new Dish();

      db.addDish(jello);

      ResultSet result = db.getDishes(jello.getMeal(), jello.getLocation());
      try {
        result.next();
        assertTrue(result.getObject(1).toString().equals(jello.toString()));
      } catch(SQLException sqle) {
        assertTrue(false);
      }

      db.removeDish(jello);

      db.writeDishesCSV("test/tests/testGoodDatabaseDishesTable.test");
      compare("test/tests/testGoodDatabaseDishesTable.test",
              "test/tests/testGoodDatabaseEmptyDishesTable.master");
      deleteFile("test/tests/testGoodDatabaseDishesTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseQueryingDishesTableWithLocationAndName() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();

      // Create a dish.
      Dish jello = new Dish();

      db.addDish(jello);

      ResultSet result = db.getDishes(jello.getName(), jello.getLocation());
      try {
        result.next();
        assertTrue(result.getObject(1).toString().equals(jello.toString()));
      } catch(SQLException sqle) {
        assertTrue(false);
      }

      db.removeDish(jello);

      db.writeDishesCSV("test/tests/testGoodDatabaseDishesTable.test");
      compare("test/tests/testGoodDatabaseDishesTable.test",
              "test/tests/testGoodDatabaseEmptyDishesTable.master");
      deleteFile("test/tests/testGoodDatabaseDishesTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseQueryingDishesTableWithMealAndName() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();

      // Create a dish.
      Dish jello = new Dish();

      db.addDish(jello);

      ResultSet result = db.getDishes(jello.getName(), jello.getMeal());
      try {
        result.next();
        assertTrue(result.getObject(1).toString().equals(jello.toString()));
      } catch(SQLException sqle) {
        assertTrue(false);
      }

      db.removeDish(jello);

      db.writeDishesCSV("test/tests/testGoodDatabaseDishesTable.test");
      compare("test/tests/testGoodDatabaseDishesTable.test",
              "test/tests/testGoodDatabaseEmptyDishesTable.master");
      deleteFile("test/tests/testGoodDatabaseDishesTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseQueryingDishesTableWithDateAndLocationAndMeal() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();

      // Create a dish.
      Dish jello = new Dish();

      db.addDish(jello);

      ResultSet result = db.getDishes(jello.getLocation(), jello.getMeal(),
                                      jello.getDate());
      try {
        result.next();
        assertTrue(result.getObject(1).toString().equals(jello.toString()));
      } catch(SQLException sqle) {
        assertTrue(false);
      }

      db.removeDish(jello);

      db.writeDishesCSV("test/tests/testGoodDatabaseDishesTable.test");
      compare("test/tests/testGoodDatabaseDishesTable.test",
              "test/tests/testGoodDatabaseEmptyDishesTable.master");
      deleteFile("test/tests/testGoodDatabaseDishesTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseQueryingDishesTableWithDateAndLocationAndName() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();

      // Create a dish.
      Dish jello = new Dish();

      db.addDish(jello);

      ResultSet result = db.getDishes(jello.getLocation(), jello.getName(),
                                      jello.getDate());
      try {
        result.next();
        assertTrue(result.getObject(1).toString().equals(jello.toString()));
      } catch(SQLException sqle) {
        assertTrue(false);
      }

      db.removeDish(jello);

      db.writeDishesCSV("test/tests/testGoodDatabaseDishesTable.test");
      compare("test/tests/testGoodDatabaseDishesTable.test",
              "test/tests/testGoodDatabaseEmptyDishesTable.master");
      deleteFile("test/tests/testGoodDatabaseDishesTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseQueryingDishesTableWithDateAndMealAndName() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();

      // Create a dish.
      Dish jello = new Dish();

      db.addDish(jello);

      ResultSet result = db.getDishes(jello.getName(), jello.getMeal(), jello.getDate());
      try {
        result.next();
        assertTrue(result.getObject(1).toString().equals(jello.toString()));
      } catch(SQLException sqle) {
        assertTrue(false);
      }

      db.removeDish(jello);

      db.writeDishesCSV("test/tests/testGoodDatabaseDishesTable.test");
      compare("test/tests/testGoodDatabaseDishesTable.test",
              "test/tests/testGoodDatabaseEmptyDishesTable.master");
      deleteFile("test/tests/testGoodDatabaseDishesTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseQueryingDishesTableWithLocationAndMealAndName() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();

      // Create a dish.
      Dish jello = new Dish();

      db.addDish(jello);

      ResultSet result = db.getDishes(jello.getName(), jello.getMeal(),
                                      jello.getLocation());
      try {
        result.next();
        assertTrue(result.getObject(1).toString().equals(jello.toString()));
      } catch(SQLException sqle) {
        assertTrue(false);
      }

      db.removeDish(jello);

      db.writeDishesCSV("test/tests/testGoodDatabaseDishesTable.test");
      compare("test/tests/testGoodDatabaseDishesTable.test",
              "test/tests/testGoodDatabaseEmptyDishesTable.master");
      deleteFile("test/tests/testGoodDatabaseDishesTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }

  public void testGoodDatabaseUpdatingDishesTable() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createDishesTable();

      // Create a dish.
      Dish jello = new Dish();

      db.addDish(jello);

      // Update date.
      jello.setDate(new java.util.Date());
      assertTrue(db.updateDish(jello));

      // Update location.
      jello.setLocation(new Location("mars"));
      assertTrue(db.updateDish(jello));

      // Update meal.
      jello.setMeal(Meal.LUNCH);
      assertTrue(db.updateDish(jello));

      // Update name.
      jello.setName("green jello");
      assertTrue(db.updateDish(jello));

      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      System.err.println(ise.getMessage());
      assertTrue(false);
    }
  }
}

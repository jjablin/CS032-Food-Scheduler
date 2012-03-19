package tests;

import food.*;
import java.io.*;
import junit.framework.*;
import java.util.*;

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
      assertTrue(false);
    }
  }

  public void testGoodDatabaseEmptyUsersTable() {
    try {
      FoodDatabase db = new FoodDatabase("test/tests/testdb");
      db.createUsersTable();
      db.writeUsersCSV("test/tests/testGoodDatabaseEmptyTable.test");
      compare("test/tests/testGoodDatabaseEmptyTable.test",
              "test/tests/testGoodDatabaseEmptyTable.master");
      deleteFile("test/tests/testGoodDatabaseEmptyTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
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

      db.writeUsersCSV("test/tests/testGoodDatabaseTable.test");
      compare("test/tests/testGoodDatabaseTable.test",
              "test/tests/testGoodDatabaseEmptyTable.master");
      deleteFile("test/tests/testGoodDatabaseTable.test");
      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      assertTrue(false);
    }
  }

  public void testGoodDatabaseQueryingUsersTable() {
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
      UserAccount newEmily = new UserAccount("emilie", "123");
      assertTrue(db.updateUser(newEmily));

      // Update password.
      UserAccount newRick = new UserAccount("rick", "password");
      assertTrue(db.updateUser(newRick));

      db.clear();
      db.close();
      assertTrue(true);
    } catch(IllegalStateException ise) {
      assertTrue(false);
    }
  }
}
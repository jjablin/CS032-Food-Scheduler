package tests;

import mealplanner.*;
import java.io.*;
import junit.framework.*;
import java.util.*;

public class UserAccountTest extends TestCase {

  public void testSimpleUserAccountUpdatePassword() {
    UserAccount account = new UserAccount("john doe", "123456");
    account.setPassword("456789");
    assertTrue(account.getPassword().equals("456789"));
    assertTrue(account.isValid());
  }

  public void testSimpleUserAccountUpdateAllergy() {
    UserAccount account = new UserAccount("john doe", "123456");
    Set< Allergy > master = new HashSet< Allergy >();
    master.add(Allergy.GARLIC);
    master.add(Allergy.MSG);
    master.add(Allergy.EGG);
    master.add(Allergy.MILK);

    for(Allergy allergy : master)
      account.addAllergy(allergy);

    Set< Allergy > test = account.getAllergies();
    Iterator< Allergy > it = master.iterator();
    Iterator< Allergy > jt = test.iterator();
    while(it.hasNext())
      assertTrue(it.next().getName().equals(jt.next().getName()));

    for(Allergy allergy : master)
      account.removeAllergy(allergy);

    test = account.getAllergies();
    assertTrue(test.size() == 0);
  }

  public void testSimpleUserAccountUpdateLikes() {
    UserAccount account = new UserAccount("john doe", "123456");
    Set< String > master = new HashSet< String >();
    master.add("lettuce");
    master.add("grapes");
    master.add("banana");
    master.add("apple");
    master.add("orange");
    master.add("pear");
    master.add("pineapple");
    master.add("strawberry");
    master.add("tangerine");
    master.add("cherry");
    master.add("watermelon");

    for(String food : master)
      account.addLike(food);

    Set test = account.getLikes();
    Iterator it = master.iterator();
    while(it.hasNext())
      assertTrue(test.contains(it.next()));
    assertTrue(test.size() == master.size());

    for(String food : master)
      account.removeLike(food);

    test = account.getLikes();
    assertTrue(test.size() == 0);
    assertTrue(account.isValid());
  }

  public void testSimpleUserAccountUpdateDislikes() {
    UserAccount account = new UserAccount("john doe", "123456");
    Set< String > master = new HashSet< String >();
    master.add("lettuce");
    master.add("grapes");
    master.add("banana");
    master.add("apple");
    master.add("orange");
    master.add("pear");
    master.add("pineapple");
    master.add("strawberry");
    master.add("tangerine");
    master.add("cherry");
    master.add("watermelon");

    for(String food : master)
      account.addDislike(food);

    Set test = account.getDislikes();
    Iterator it = master.iterator();
    while(it.hasNext())
      assertTrue(test.contains(it.next()));
    assertTrue(test.size() == master.size());

    for(String food : master)
      account.removeDislike(food);

    test = account.getDislikes();
    assertTrue(test.size() == 0);
    assertTrue(account.isValid());
  }

  public void testSimpleUserAccountIntersectionOfLikesAndDislikes() {
    UserAccount account = new UserAccount("john doe", "123456");
    Set< String > master = new HashSet< String >();
    master.add("lettuce");
    master.add("grapes");
    master.add("banana");
    master.add("apple");

    for(String food : master) {
      account.addDislike(food);
      account.addLike(food);
    }

    Set test = account.getLikes();
    Iterator it = master.iterator();
    while(it.hasNext())
      assertFalse(test.contains(it.next()));
    assertTrue(test.size() == 0);

    test = account.getDislikes();
    it = master.iterator();
    while(it.hasNext())
      assertTrue(test.contains(it.next()));
    assertTrue(test.size() == master.size());

    for(String food : master)
      account.removeDislike(food);

    test = account.getDislikes();
    assertTrue(test.size() == 0);
    assertTrue(account.isValid());
  }

  public void testSimpleUserAccountIntersectionOfDislikesAndLikes() {
    UserAccount account = new UserAccount("john doe", "123456");
    Set< String > master = new HashSet< String >();
    master.add("lettuce");
    master.add("grapes");
    master.add("banana");
    master.add("apple");

    for(String food : master) {
      account.addLike(food);
      account.addDislike(food);
    }

    Set test = account.getDislikes();
    Iterator it = master.iterator();
    while(it.hasNext())
      assertFalse(test.contains(it.next()));
    assertTrue(test.size() == 0);

    test = account.getLikes();
    it = master.iterator();
    while(it.hasNext())
      assertTrue(test.contains(it.next()));
    assertTrue(test.size() == master.size());

    for(String food : master)
      account.removeLike(food);

    test = account.getLikes();
    assertTrue(test.size() == 0);
    assertTrue(account.isValid());
  }

  public void testSimpleUserAccountSetAndGetMarkedDishes() {
    UserAccount account = new UserAccount("john doe", "123456");

    MarkedDish jello = new MarkedDish(new Dish("jello"), 10);

    account.addMarkedDish(jello);
    List<MarkedDish> results =
      account.getMarkedDishes(new Location(new String("earth")),
                              new Meal("Lunch"), // Wrong meal!
                              jello.getDate());
    assertTrue(results.size() == 0);

    results =
      account.getMarkedDishes(new Location(new String("earth")),
                              new Meal("Breakfast"), // Correct meal!
                              jello.getDate());
    assertTrue(results.size() == 0);
  }
}

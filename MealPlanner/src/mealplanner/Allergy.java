/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mealplanner;

// http://en.wikipedia.org/wiki/List_of_food_allergies -
// a list of food allergies.
public enum Allergy {
  CORN, FRUIT, GARLIC, OATS, MILK, PEANUT, FISH,
    SHELLFISH, SOY, TREE_NUT, WHEAT, EGG, MSG,
    SULPHITES;

  public String toString() {
    return super.toString();
  }
}

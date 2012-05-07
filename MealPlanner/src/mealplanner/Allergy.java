/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mealplanner;

// http://en.wikipedia.org/wiki/List_of_food_allergies -
// a list of food allergies.
public enum Allergy {
  CORN, GARLIC, OAT, MILK, PEANUT, SOY, NUT, WHEAT, EGG, MSG;

  public String getName() {
        return this.toString().toLowerCase();
  }

}

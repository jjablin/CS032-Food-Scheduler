/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mealplanner;

public enum Day {
  MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    @Override
  public String toString() {
    return super.toString();
  }

  public static int toInt(Day day) {
      switch(day){
              case MONDAY: return 0;
              case TUESDAY: return 1;
              case WEDNESDAY: return 2;
              case THURSDAY: return 3;
              case FRIDAY: return 4;
              case SATURDAY: return 5;
              case SUNDAY: return 6;
      }
      return -1;
  }

  public static Day intToDay(int d)
  {
      switch(d){
              case 1: return MONDAY;
              case 2: return TUESDAY;
              case 3: return WEDNESDAY;
              case 4: return THURSDAY;
              case 5: return FRIDAY;
              case 6: return SATURDAY;
              case 0: return SUNDAY;
      }
      return MONDAY; //this will never be reached unless an invalid int is passed in.
  }
}

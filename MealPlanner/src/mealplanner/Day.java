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

  public int toInt(Day day) {
      switch(day){
              case MONDAY: return 1;
              case TUESDAY: return 2;
              case WEDNESDAY: return 3;
              case THURSDAY: return 4;
              case FRIDAY: return 5;
              case SATURDAY: return 6;
              case SUNDAY: return 0;
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


package mealplanner;

public enum Day {
  MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    @Override
  public String toString() {
    return super.toString();
  }

  public static int toInt(Day day) {
      switch(day){
              case MONDAY: return 2;
              case TUESDAY: return 3;
              case WEDNESDAY: return 4;
              case THURSDAY: return 5;
              case FRIDAY: return 6;
              case SATURDAY: return 7;
              case SUNDAY: return 1;
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

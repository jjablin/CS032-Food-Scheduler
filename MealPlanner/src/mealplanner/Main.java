
package mealplanner;


public class Main {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FoodDatabase fd = new FoodDatabase("./database/database");
        fd.createUsersTable();
        fd.createDishesTable();
        //fd.writeDishesCSV("dishes_table");
        WindowManager wm = new WindowManager(fd);
    }

}

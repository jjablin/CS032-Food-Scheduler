
package mealplanner;


public class Main {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FoodDatabase fd = new FoodDatabase("./database/database");
        fd.createUsersTable();
        fd.createDishesTable();
        //TODO: check if we need to update
        //fd.update();
        //fd.writeDishesCSV("dishes_table");
        WindowManager wm = new WindowManager(fd);
    }

}

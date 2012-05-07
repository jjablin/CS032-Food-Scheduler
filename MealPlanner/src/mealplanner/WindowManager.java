
package mealplanner;

import javax.swing.JFrame;


public class WindowManager {
    
    private JFrame _frame;
    private UserAccount _user;
    private FoodDatabase _database;

    public WindowManager(FoodDatabase fd)
    {
        _database = fd;
        startup();
    }

    /**called from the constructor.
     * creates and displays the login window
     */
    private void startup()
    {
         _frame = new LoginWindow(this);
         //center the frame in the middle of the screen
         _frame.setLocationRelativeTo(null);
         _frame.setVisible(true);
    }

    public UserAccount getUser()
    {
        return _user;
    }

    public void setUser(UserAccount user)
    {
        _user = user;
    }

    public FoodDatabase getDatabase()
    {
        return _database;
    }

    public void showNewAccountWindow()
    {
        JFrame f = new NewAccountWindow(this);
        switchFrame(f);
    }

    public void showLoginWindow()
    {
        JFrame f = new LoginWindow(this);
        switchFrame(f);
    }

    public void showLikeDislikeWindow()
    {
        JFrame f = new LikeDislikeWindow(this);
        switchFrame(f);
    }

    public void showEmailWindow()
    {
        JFrame f = new EmailWindow(this);
        switchFrame(f);
    }

    public void showAllergyWindow()
    {
        JFrame f = new AllergyWindow(this);
        switchFrame(f);
    }

    public void showPlannerMainWindow()
    {
        JFrame f = new PlannerMainWindow(this);
        switchFrame(f);
    }

    public void showGoalsWindow()
    {
        JFrame f = new GoalsWindow(this);
        switchFrame(f);
    }

    //closes the current frame and displays f in its place
    private void switchFrame(JFrame f)
    {
        f.setLocationRelativeTo(_frame);
        //close the old frame
        _frame.setVisible(false);
        _frame.dispose();
        //display the new frame
        _frame = f;
        _frame.setVisible(true);
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mealplanner;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author seadams
 */
public class DinerPanel extends JSplitPane{

    private PlannerMainWindow _parent;

    private JPanel _mealPanel;
    private JPanel _menuPanel;
    private WindowManager _windowManager;
    private JLabel _mealHeader;
    private JLabel _menuHeader;
    private JLabel _mealItemHeading;
    private JLabel _mealServingsHeading;
    private JLabel _menuItemHeading;
    private JScrollPane _mealPane;
    private JScrollPane _menuPane;
    private JPanel _menuDisplay;


    public DinerPanel(WindowManager wm, PlannerMainWindow parent)
    {
        super();
        _windowManager = wm;
        _parent = parent;
        initComponents();
        displayMenu();
        displayMeal(new ArrayList<MarkedDish>());
    }
   

    private void initComponents()
    {
        _mealHeader = new JLabel("Your Meal:");
        _menuHeader = new JLabel("Menu:");
        _mealItemHeading = new JLabel("Item");
        _mealServingsHeading = new JLabel("Servings");
        _menuItemHeading = new JLabel("Item");

        JViewport mealview = new JViewport();
        _mealPane = new JScrollPane(mealview);
        JPanel mealDisplay = new JPanel();
        mealview.setView(mealDisplay);
        mealDisplay.add(_mealItemHeading);
        mealDisplay.add(_mealServingsHeading);

        _mealPanel = new JPanel();
        _mealPanel.setLayout(new BoxLayout(_mealPanel, BoxLayout.Y_AXIS));
        _mealPanel.add(_mealHeader);
        _mealPanel.add(_mealPane);
        this.setLeftComponent(_mealPanel);

        JViewport menuview = new JViewport();
        _menuPane = new JScrollPane(menuview);
        _menuDisplay = new JPanel();
        _menuDisplay.setLayout(new BoxLayout(_menuDisplay, BoxLayout.Y_AXIS));
        menuview.setView(_menuDisplay);
        _menuDisplay.add(_menuItemHeading);
        _menuDisplay.add(Box.createRigidArea(new Dimension(0, 5))); //add some blank space below the label

        _menuPanel = new JPanel();
        _menuPanel.setLayout(new BoxLayout(_menuPanel, BoxLayout.Y_AXIS));
        _menuPanel.add(_menuHeader);
        _menuPanel.add(_menuPane);
        this.setBottomComponent(_menuPanel);
    }

    public void displayMeal(List<MarkedDish> meal)
    {
        for(int i = 0; i < meal.size(); i++)
        {
            MarkedDish d = meal.get(i);
            JLabel dishLabel = new JLabel(d.getName());
            if(_windowManager._user.getDislikes().contains(d.getName()))
                dishLabel.setForeground(Color.RED);
            if(_windowManager._user.getLikes().contains(d.getName()))
                dishLabel.setForeground(Color.GREEN);
            _mealPanel.add(dishLabel);

            int servings = d.getServings();
            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(servings, 0, Integer.MAX_VALUE, 1);
            JSpinner servingsSpinner = new JSpinner(spinnerModel);
            _mealPane.add(servingsSpinner);

        }
    }

    //gets the menu to display from the main window (_parent) and displays the dishes in order:
    //liked dishes in green at the top, no preference dishes in black in the middle,
    //and disliked dishes in red at the bottom
    public void displayMenu()
    {
        HashSet<Dish> menu = _parent.getMenu();
        ArrayList<Dish> noPreferences = new ArrayList<Dish>();
        ArrayList<Dish> dislikes = new ArrayList<Dish>();
        //sort the dishes by preference and display the liked dishes
        int j = 0;
        Iterator itr = menu.iterator();
        while(itr.hasNext())
        {
            Dish d = (Dish) itr.next();
            if(_windowManager._user.getDislikes().contains(d.getName()))
                dislikes.add(d);
            else if(_windowManager._user.getLikes().contains(d.getName()))
            {
                JLabel dishLabel = new JLabel(d.getName());
                dishLabel.setForeground(Color.GREEN);
                _menuDisplay.add(dishLabel);
            }
            else
                noPreferences.add(d);
            j++;
        }
        //display the no preference dishes
        for(int i = 0; i < noPreferences.size(); i++)
        {
            Dish d = noPreferences.get(i);
            JLabel dishLabel = new JLabel(d.getName());
            _menuDisplay.add(dishLabel);
        }
        //display the disliked dishes
        for(int i = 0; i < dislikes.size(); i++)
        {
            Dish d = noPreferences.get(i);
            JLabel dishLabel = new JLabel(d.getName());
            dishLabel.setForeground(Color.RED);
            _menuDisplay.add(dishLabel);
        }
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mealplanner;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author seadams
 */
public class DinerPanel extends JSplitPane{

    private JPanel _mealPanel;
    private JPanel _menuPanel;
    private WindowManager _windowManager;
    private JLabel _mealHeader;
    private JLabel _menuHeader;
    private JLabel _mealItemHeading;
    private JLabel _mealServingsHeading;
    private JLabel _menuItemHeading;
    private JLabel _menuServingsHeading;
    private JScrollPane _mealPane;
    private JScrollPane _menuPane;
    private JPanel _menuDisplay;


    public DinerPanel(WindowManager wm)
    {
        super();
        _windowManager = wm;
        initComponents();
        displayMenu(new ArrayList<Dish>());
        displayMeal(new ArrayList<MarkedDish>());
    }
   

    private void initComponents()
    {
        _mealHeader = new JLabel("Your Meal:");
        _menuHeader = new JLabel("Menu:");
        _mealItemHeading = new JLabel("Item");
        _mealServingsHeading = new JLabel("Servings");
        _menuItemHeading = new JLabel("Item");
        _menuServingsHeading = new JLabel("Servings");

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
        menuview.setView(_menuDisplay);
        _menuDisplay.add(_menuItemHeading);
        _menuDisplay.add(_menuServingsHeading);

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
            JLabel dishLabel = new JLabel(meal.get(i).getName());
            if(_windowManager._user.getDislikes().contains(meal.get(i).getName()))
                dishLabel.setForeground(Color.RED);
            if(_windowManager._user.getLikes().contains(meal.get(i).getName()))
                dishLabel.setForeground(Color.GREEN);
            _mealPanel.add(dishLabel);

            int servings = meal.get(i).getServings();
            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(servings, 0, Integer.MAX_VALUE, 1);
            JSpinner servingsSpinner = new JSpinner(spinnerModel);
            _mealPane.add(servingsSpinner);

        }
    }

    public void displayMenu(List<Dish> menu)
    {
        List<Dish> m = new ArrayList<Dish>();
        m.add(new Dish("dish1"));
        m.add(new Dish("dish2"));

        for(int i = 0; i < m.size(); i++)
        {
            JLabel dishLabel = new JLabel(m.get(i).getName());
            if(_windowManager._user.getDislikes().contains(m.get(i).getName()))
                dishLabel.setForeground(Color.RED);
            if(_windowManager._user.getLikes().contains(m.get(i).getName()))
                dishLabel.setForeground(Color.GREEN);
            _menuDisplay.add(dishLabel);
        }
    }

}

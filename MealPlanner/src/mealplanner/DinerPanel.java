/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mealplanner;

import java.awt.Container;
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


    public DinerPanel(WindowManager wm)
    {
        super();
        _windowManager = wm;
        initComponents();
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
        JPanel menuDisplay = new JPanel();
        menuview.setView(menuDisplay);
        menuDisplay.add(_menuItemHeading);
        menuDisplay.add(_menuServingsHeading);

        _menuPanel = new JPanel();
        _menuPanel.setLayout(new BoxLayout(_menuPanel, BoxLayout.Y_AXIS));
        _menuPanel.add(_menuHeader);
        _menuPanel.add(_menuPane);
        this.setBottomComponent(_menuPanel);
    }

    public void displayMeal(List<MarkedDish> meal)
    {
//        for(int i = 0; i < meal.size(); i++)
//        {
//            JLabel dishLabel = new JLabel(meal.get(i)._dish.getName());
//            if(meal.get(i)._preferenceVal < 0)
//                dishLabel.setForeground(Color.RED);
//            if(meal.get(i)._preferenceVal > 0)
//                dishLabel.setForeground(Color.GREEN);
//            _mealPanel.add(dishLabel);
//
//            int servings = 0; //TODO: should be the saved number of servings
//            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(servings, 0, Integer.MAX_VALUE, 1);
//            JSpinner servingsSpinner = new JSpinner(spinnerModel);
//            _mealPanel.add(servingsSpinner);
//
//        }
    }

}

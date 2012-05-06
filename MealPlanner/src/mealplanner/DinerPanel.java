/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanner;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

public class DinerPanel extends JSplitPane {

    private PlannerMainWindow _parent;
    private JPanel _mealPanel;
    private JPanel _menuPanel;
    private WindowManager _windowManager;
    private JLabel _mealHeader;
    private JLabel _menuHeader;
    private JLabel _mealItemHeading;
    private JLabel _mealServingsHeading;
    private JScrollPane _mealPane;
    private JScrollPane _menuPane;
    private JPanel _menuDisplay;
    private JPanel _mealDisplay;

    public DinerPanel(WindowManager wm, PlannerMainWindow parent) {
        super();
        _windowManager = wm;
        _parent = parent;
        initComponents();
        displayMenu();
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE, Day.toInt(_parent.getDay()) - (day.get(Calendar.DAY_OF_WEEK) - 2));
        List<MarkedDish> savedMeal = _windowManager.getUser().getMarkedDishes(
                _parent.getDiningHall(),
                _parent.getMeal(),
                day);
        displayMeal(savedMeal);
    }

    @Override
    public PlannerMainWindow getParent() {
        return _parent;
    }

    public WindowManager getWindowManager() {
        return _windowManager;
    }

    private void initComponents() {
        _mealHeader = new JLabel("Your Meal:");
        _menuHeader = new JLabel("Menu:");
        _mealItemHeading = new JLabel("Item");
        _mealServingsHeading = new JLabel("Servings");

        _mealPane = new JScrollPane();
        _mealPane.getHorizontalScrollBar().setUnitIncrement(3); //makes the arrows on the scroll bars scroll faster
        _mealPane.getVerticalScrollBar().setUnitIncrement(3);
        _mealDisplay = new JPanel();
        _mealDisplay.setLayout(new BoxLayout(_mealDisplay, BoxLayout.Y_AXIS));
        _mealPane.setViewportView(_mealDisplay);
        JPanel headingPanel = new JPanel();
        headingPanel.add(_mealItemHeading);
        headingPanel.add(_mealServingsHeading);
        _mealDisplay.add(headingPanel);

        _mealPanel = new JPanel();
        _mealPanel.setLayout(new BoxLayout(_mealPanel, BoxLayout.Y_AXIS));
        _mealPanel.add(_mealHeader);
        _mealPanel.add(_mealPane);
        this.setLeftComponent(_mealPanel);

        _menuPane = new JScrollPane();
        _menuPane.getHorizontalScrollBar().setUnitIncrement(3); //makes the arrows on the scroll bars scroll faster
        _menuPane.getVerticalScrollBar().setUnitIncrement(3);
        _menuDisplay = new JPanel();
        _menuDisplay.setLayout(new BoxLayout(_menuDisplay, BoxLayout.Y_AXIS));
        _menuPane.setViewportView(_menuDisplay);

        _menuPanel = new JPanel();
        _menuPanel.setLayout(new BoxLayout(_menuPanel, BoxLayout.Y_AXIS));
        _menuPanel.add(_menuHeader);
        _menuPanel.add(_menuPane);
        this.setBottomComponent(_menuPanel);

        this.setDividerLocation(350);
    }

    //displays the meal with likes at the top and dislikes at the bottom.
    public void displayMeal(List<MarkedDish> meal) {
        //alphabetize by dish name
        alphabetizeMeal(meal);

        //sort items by preference
        ArrayList<MarkedDish> likes = new ArrayList<MarkedDish>();
        ArrayList<MarkedDish> dislikes = new ArrayList<MarkedDish>();
        ArrayList<MarkedDish> noPrefs = new ArrayList<MarkedDish>();
        ArrayList<MarkedDish> allergies = new ArrayList<MarkedDish>();
        for(int i = 0; i < meal.size(); i++)
        {
            MarkedDish d = meal.get(i);

            //check if it has allergins
            boolean allergic = false;
            Iterator<Allergy> allergyItr = _windowManager.getUser().getAllergies().iterator();
            while (allergyItr.hasNext()) {
                Allergy next = allergyItr.next();
                String allergy = next.getName();
                ArrayList<String> ingredients = d.getIngredients();
                for(int j = 0; j < ingredients.size(); j++)
                {
                    if(ingredients.get(j).toLowerCase().contains(allergy))
                    {
                        allergic = true;
                        break;
                    }
                }
            }

            if(allergic)
            {
                allergies.add(d);
            }
            else if (_windowManager.getUser().getDislikes().contains(d.getName())) {
                dislikes.add(d);
            }
            else if (_windowManager.getUser().getLikes().contains(d.getName())) {
                likes.add(d);
            }
            else
            {
                noPrefs.add(d);
            }
        }

        Dimension spinnerSize = new Dimension(40, 20);
        //display likes
        for(int i = 0; i < likes.size(); i++)
        {
            JPanel itemPanel = new JPanel();
            MarkedDish d = likes.get(i);
            JLabel dishLabel = new JLabel(d.getName());
            dishLabel.setForeground(Color.GREEN);
            itemPanel.add(dishLabel);
            int servings = d.getServings();
            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(servings, 0, Integer.MAX_VALUE, 1);
            JSpinner servingsSpinner = new JSpinner(spinnerModel);
            servingsSpinner.setPreferredSize(spinnerSize);
            DefaultEditor editor = (DefaultEditor) servingsSpinner.getEditor();
            DefaultFormatter formatter = (DefaultFormatter) editor.getTextField().getFormatter();
            formatter.setCommitsOnValidEdit(true);
            //removeSpinnerButtons(servingsSpinner);
            //editor.getTextField().addFocusListener(new SpinnerListener(this));
            servingsSpinner.addChangeListener(new SpinnerListener(this));
            itemPanel.add(servingsSpinner);
            _mealDisplay.add(itemPanel);
        }

        //display noPrefs
        for(int i = 0; i < noPrefs.size(); i++)
        {
            JPanel itemPanel = new JPanel();
            MarkedDish d = noPrefs.get(i);
            JLabel dishLabel = new JLabel(d.getName());
            itemPanel.add(dishLabel);
            int servings = d.getServings();
            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(servings, 0, Integer.MAX_VALUE, 1);
            JSpinner servingsSpinner = new JSpinner(spinnerModel);
            servingsSpinner.setPreferredSize(spinnerSize);
            DefaultEditor editor = (DefaultEditor) servingsSpinner.getEditor();
            DefaultFormatter formatter = (DefaultFormatter) editor.getTextField().getFormatter();
            formatter.setCommitsOnValidEdit(true);
            //removeSpinnerButtons(servingsSpinner);
            //editor.getTextField().addFocusListener(new SpinnerListener(this));
            servingsSpinner.addChangeListener(new SpinnerListener(this));
            itemPanel.add(servingsSpinner);
            _mealDisplay.add(itemPanel);
        }

        //display dislikes
        for(int i = 0; i < dislikes.size(); i++)
        {
            JPanel itemPanel = new JPanel();
            MarkedDish d = dislikes.get(i);
            JLabel dishLabel = new JLabel(d.getName());
            dishLabel.setForeground(Color.RED);
            itemPanel.add(dishLabel);
            int servings = d.getServings();
            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(servings, 0, Integer.MAX_VALUE, 1);
            JSpinner servingsSpinner = new JSpinner(spinnerModel);
            servingsSpinner.setPreferredSize(spinnerSize);
            DefaultEditor editor = (DefaultEditor) servingsSpinner.getEditor();
            DefaultFormatter formatter = (DefaultFormatter) editor.getTextField().getFormatter();
            formatter.setCommitsOnValidEdit(true);
           // removeSpinnerButtons(servingsSpinner);
            //editor.getTextField().addFocusListener(new SpinnerListener(this));
            servingsSpinner.addChangeListener(new SpinnerListener(this));
            itemPanel.add(servingsSpinner);
            _mealDisplay.add(itemPanel);
        }

        //display allergies
        for(int i = 0; i < allergies.size(); i++)
        {
            JPanel itemPanel = new JPanel();
            MarkedDish d = allergies.get(i);
            JLabel dishLabel = new JLabel(d.getName());
            dishLabel.setForeground(Color.ORANGE);
            itemPanel.add(dishLabel);
            int servings = d.getServings();
            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(servings, 0, Integer.MAX_VALUE, 1);
            JSpinner servingsSpinner = new JSpinner(spinnerModel);
            servingsSpinner.setPreferredSize(spinnerSize);
            DefaultEditor editor = (DefaultEditor) servingsSpinner.getEditor();
            DefaultFormatter formatter = (DefaultFormatter) editor.getTextField().getFormatter();
            formatter.setCommitsOnValidEdit(true);
            //removeSpinnerButtons(servingsSpinner);
            //editor.getTextField().addFocusListener(new SpinnerListener(this));
            servingsSpinner.addChangeListener(new SpinnerListener(this));
            itemPanel.add(servingsSpinner);
            _mealDisplay.add(itemPanel);
        }
        
        _mealPanel.paintAll(_mealPanel.getGraphics());
    }

    private void alphabetizeMeal(List meal)
    {
        Collections.sort(meal, new MarkedDishComparator());
    }

    class MarkedDishComparator implements Comparator<MarkedDish>{

        public int compare(MarkedDish o1, MarkedDish o2)
        {
            String name1 = o1.getName();
            String name2 = o2.getName();

            return name1.compareTo(name2);
        }
    }

    //gets the menu to display from the main window (_parent) and displays the dishes in order:
    //liked dishes in green at the top, no preference dishes in black in the middle,
    //and disliked dishes in red at the bottom
    public void displayMenu() {
        TreeSet<Dish> menu = _parent.getMenu();
        ArrayList<Dish> noPreferences = new ArrayList<Dish>();
        ArrayList<Dish> dislikes = new ArrayList<Dish>();
        ArrayList<Dish> allergies = new ArrayList<Dish>();
        //sort the dishes by preference and display the liked dishes
        Iterator itr = menu.iterator();
        while (itr.hasNext()) {
            Dish d = (Dish) itr.next();
            //check if it has allergins
            boolean allergic = false;
            Iterator<Allergy> allergyItr = _windowManager.getUser().getAllergies().iterator();
            while (allergyItr.hasNext()) {
                Allergy next = allergyItr.next();
                String allergy = next.getName();
                ArrayList<String> ingredients = d.getIngredients();
                for(int i = 0; i < ingredients.size(); i++)
                {
                    if(ingredients.get(i).toLowerCase().contains(allergy))
                    {
                        allergic = true;
                        break;
                    }
                }
            }

            if(allergic)
            {
                allergies.add(d);
            }
            else if (_windowManager.getUser().getDislikes().contains(d.getName())) {
                dislikes.add(d);
            } else if (_windowManager.getUser().getLikes().contains(d.getName())) {
                JLabel dishLabel = new JLabel(d.getName());
                dishLabel.setForeground(Color.GREEN);
                dishLabel.addMouseListener(new DishListener(this));
                _menuDisplay.add(dishLabel);
            } else {
                noPreferences.add(d);
            }
        }
        //display the no preference dishes
        for (int i = 0; i < noPreferences.size(); i++) {
            Dish d = noPreferences.get(i);
            JLabel dishLabel = new JLabel(d.getName());
            dishLabel.addMouseListener(new DishListener(this));
            _menuDisplay.add(dishLabel);
        }
        //display the disliked dishes
        for (int i = 0; i < dislikes.size(); i++) {
            Dish d = dislikes.get(i);
            JLabel dishLabel = new JLabel(d.getName());
            dishLabel.setForeground(Color.RED);
            dishLabel.addMouseListener(new DishListener(this));
            _menuDisplay.add(dishLabel);
        }

        //display the allergy dishes
        for (int i = 0; i < allergies.size(); i++) {
            Dish d = allergies.get(i);
            JLabel dishLabel = new JLabel(d.getName());
            dishLabel.setForeground(Color.ORANGE);
            dishLabel.addMouseListener(new DishListener(this));
            _menuDisplay.add(dishLabel);
        }

        //_menuDisplay.repaint();
        _menuPanel.paintAll(_menuPanel.getGraphics());
    }

    public void redisplayMeal() {
        clearMeal();
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE, Day.toInt(_parent.getDay()) - (day.get(Calendar.DAY_OF_WEEK) - 2));
        List<MarkedDish> savedMeal = _windowManager.getUser().getMarkedDishes(
                _parent.getDiningHall(),
                _parent.getMeal(),
                day);
        displayMeal(savedMeal);
    }

    public void clearMeal() {
        //remove all meal items (the first component is the header)
        int components = _mealDisplay.getComponentCount();
        for (int i = 1; i < components; i++) {
            _mealDisplay.remove(1);
        }
    }

    public void clearMealAndMenu() {
        //remove all menu items
        int components = _menuDisplay.getComponentCount();
        for (int i = 0; i < components; i++) {
            _menuDisplay.remove(0);
        }

        _menuDisplay.repaint();
        _menuPanel.repaint();
        _menuPane.repaint();

        clearMeal();
        _menuPanel.paintAll(_menuPanel.getGraphics());
    }

    class DishListener implements MouseListener {

        private DinerPanel _panel;

        public DishListener(DinerPanel panel) {
            _panel = panel;
        }

        //displays the nutrition information for this item
        public void mouseEntered(MouseEvent e) {
            Dish dish = new Dish("");
            Iterator itr = _panel.getParent().getMenu().iterator();
            JLabel dishLabel = (JLabel) e.getComponent();
            String dishName = dishLabel.getText();
            while (itr.hasNext())
            {
                Dish d = (Dish) itr.next();
                if (d.getName().equals(dishName))
                {
                    dish = d;
                    break;
                }
            }
            _parent.showNutritionInfo(dish);
        }

        public void mouseExited(MouseEvent e) {
            //do nothing
        }

        public void mousePressed(MouseEvent e) {
            //do nothing
        }

        public void mouseReleased(MouseEvent e) {
            //do nothing
        }

        //adds the item to the meal if it is not already, and updates the display
        public void mouseClicked(MouseEvent e) {
            //get the dish
            Dish dish = new Dish("");
            Iterator itr = _panel.getParent().getMenu().iterator();
            JLabel dishLabel = (JLabel) e.getComponent();
            String dishName = dishLabel.getText();
            while (itr.hasNext())
            {
                Dish d = (Dish) itr.next();
                if (d.getName().equals(dishName))
                {
                    dish = d;
                    break;
                }
            }

            //check if the meal already contains the dish
            Location location = _panel.getParent().getDiningHall();
            Meal meal = _panel.getParent().getMeal();
            Calendar day = Calendar.getInstance();
            day.add(Calendar.DATE, Day.toInt(_panel.getParent().getDay()) - (day.get(Calendar.DAY_OF_WEEK) - 2));
            List<MarkedDish> currentMeal = _panel.getWindowManager().getUser().getMarkedDishes(location, meal, day);
            boolean alreadyInMeal = false;
            Iterator<MarkedDish> iterator = currentMeal.iterator();
            while(iterator.hasNext())
            {
                MarkedDish md = iterator.next();
                if(md.getName().equals(dishName))
                {
                    //remove the old marked dish and add a new one with 1 more serving
                    int servings = md.getServings();
                    _panel.getWindowManager().getUser().removeMarkedDish(md.getDish());
                    _panel.getWindowManager().getUser().addMarkedDish(new MarkedDish(dish, servings + 1));
                    alreadyInMeal = true;
                    break;
                }
            }

            if(alreadyInMeal == false)
            {
                //add the dish to the meal
                MarkedDish md = new MarkedDish(dish, 1);
                _panel.getWindowManager().getUser().addMarkedDish(md);
            }

            //update the database
            _panel.getWindowManager().getDatabase().updateUser(_panel.getWindowManager().getUser());

            //update the display
            _panel.redisplayMeal();
            _panel.getParent().updateNutritionSliders();
        }
    }

    class SpinnerListener implements ChangeListener {

        private DinerPanel _panel;

        public SpinnerListener(DinerPanel panel) {
            _panel = panel;
        }

        public void stateChanged(ChangeEvent e) {
//            JFormattedTextField textField = (JFormattedTextField) e.getComponent();
//            JSpinner sp = (JSpinner) textField.getParent().getParent();
            JSpinner sp = (JSpinner) e.getSource();

            Iterator<Dish> itr = _panel.getParent().getMenu().iterator();
            JLabel dishLabel = (JLabel) sp.getParent().getComponent(0);
            String dishName = dishLabel.getText();
            Dish dish = new Dish("");
            while (itr.hasNext()) {
                Dish d = itr.next();
                if (d.getName().equals(dishName)) {
                    dish = d;
                    break;
                }
            }

            int servings = (Integer) sp.getValue();
            if (servings == 0) 
            { //we want to remove the dish from the meal
                _panel.getWindowManager().getUser().removeMarkedDish(dish);
            }
            else
            {//we want to change the number of servings of that dish in the meal
                MarkedDish md = new MarkedDish(dish, servings);
                //remove the old dish from the meal
                _panel.getWindowManager().getUser().removeMarkedDish(dish);
                //add the dish to the meal with the new serving number
                _panel.getWindowManager().getUser().addMarkedDish(md);
            }
            //update the database and display
            _panel.getWindowManager().getDatabase().updateUser(_panel.getWindowManager().getUser());
            _panel.redisplayMeal();
            _panel.getParent().updateNutritionSliders();
        }

        public void focusGained(FocusEvent evt)
        {
            //do nothing
        }
    }
}

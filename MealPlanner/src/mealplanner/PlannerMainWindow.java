/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PlannerMainWindow.java
 *
 * Created on Apr 23, 2012, 1:18:32 PM
 */
package mealplanner;

import java.awt.Color;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.List;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author seadams
 */
public class PlannerMainWindow extends javax.swing.JFrame {

    private WindowManager _windowManager;
    private Day _day;
    private Meal _meal;
    private Location _diningHall;
    private TreeSet<Dish> _menu;

    /** Creates new form PlannerMainWindow */
    public PlannerMainWindow(WindowManager wm) {
        _windowManager = wm;
        _meal = new Meal("breakfast");
        _day = Day.MONDAY; //this should be the current day
        _diningHall = new Location("Ratty");
        setMenu();
        initComponents();
        colorDayButtons();
        updateNutritionSliders();
    }

    //queries the data base for the meal using the current values of _diningHall,
    //_meal, and day.  sets _meal appropriately
    private void setMenu()
    {
      Calendar day = Calendar.getInstance();
      day.add(Calendar.DATE, Day.toInt(_day) - (day.get(Calendar.DAY_OF_WEEK) - 2));
      TreeSet<Dish> menu = _windowManager.getDatabase().getDishes(_diningHall, _meal, day);
      _menu = menu;
    }

    public Meal getMeal() {
        return _meal;
    }

    public Day getDay() {
        return _day;
    }

    public Location getDiningHall()
    {
        return _diningHall;
    }

    public TreeSet<Dish> getMenu() {
        return _menu;
    }

    public void updateNutritionSliders()
    {
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE, Day.toInt(_day) - (day.get(Calendar.DAY_OF_WEEK) - 2));
        Location breakfastLoc = _windowManager.getUser().getSelectedLocation(day, new Meal("breakfast"));
        Location lunchLoc = _windowManager.getUser().getSelectedLocation(day, new Meal("lunch"));
        Location dinnerLoc = _windowManager.getUser().getSelectedLocation(day, new Meal("dinner"));
        List<MarkedDish> breakfast = _windowManager.getUser().getMarkedDishes(breakfastLoc, _meal, day);
        List<MarkedDish> lunch = _windowManager.getUser().getMarkedDishes(lunchLoc, _meal, day);
        List<MarkedDish> dinner = _windowManager.getUser().getMarkedDishes(dinnerLoc, _meal, day);
        double totalFat = 0;
        double totalProtein = 0;
        double totalCarbs = 0;
        double totalCals = 0;
        Iterator itr = breakfast.iterator();
        while(itr.hasNext())
        {
            MarkedDish md = (MarkedDish) itr.next();
            totalFat = totalFat + md.getFat();
            totalProtein = totalProtein + md.getProtein() * md.getServings();
            totalCarbs = totalCarbs + md.getCarbs() * md.getServings();
            totalCals = totalCals + md.getCalories() * md.getServings();
        }
        itr = lunch.iterator();
        while(itr.hasNext())
        {
            MarkedDish md = (MarkedDish) itr.next();
            totalFat = totalFat + md.getFat() * md.getServings();
            totalProtein = totalProtein + md.getProtein() * md.getServings();
            totalCarbs = totalCarbs + md.getCarbs() * md.getServings();
            totalCals = totalCals + md.getCalories() * md.getServings();
        }
        itr = dinner.iterator();
        while(itr.hasNext())
        {
            MarkedDish md = (MarkedDish) itr.next();
            totalFat = totalFat + md.getFat() * md.getServings();
            totalProtein = totalProtein + md.getProtein() * md.getServings();
            totalCarbs = totalCarbs + md.getCarbs() * md.getServings();
            totalCals = totalCals + md.getCalories() * md.getServings();
        }

        double fatGoal = _windowManager.getUser().getFatGoal();
        double proteinGoal = _windowManager.getUser().getProteinGoal();
        double carbGoal = _windowManager.getUser().getCarbGoal();
        double calsGoal = _windowManager.getUser().getCalGoal();

        double fatProgress = totalFat / fatGoal * 100;
        double proteinProgress = totalProtein / proteinGoal * 100;
        double carbProgress = totalCarbs / carbGoal * 100;
        double calProgress = totalCals / calsGoal * 100;

        if(fatProgress > 100)
        { //they've exceeded their goal
            fatProgress = 100;
        }
        if(proteinProgress > 100)
        { //they've exceeded their goal
            proteinProgress = 100;
        }
        if(carbProgress > 100)
        { //they've exceeded their goal
            carbProgress = 100;
        }
        if(calProgress > 100)
        { //they've exceeded their goal
            calProgress = 100;
        }

        fatProgressBar.setValue((int) fatProgress);
        proteinProgressBar.setValue((int) proteinProgress);
        carbsProgressBar.setValue((int) carbProgress);
        calsProgressBar.setValue((int) calProgress);
    }

    private void colorDayButtons() {
        Color brown = new Color(139, 69, 19);
        Color red = new Color(178, 34, 34);
        monButton.setBackground(brown);
        tueButton.setBackground(brown);
        wedButton.setBackground(brown);
        thurButton.setBackground(brown);
        friButton.setBackground(brown);
        satButton.setBackground(brown);
        sunButton.setBackground(brown);

        switch (_day) {
            case MONDAY:
                monButton.setBackground(red);
                break;
            case TUESDAY:
                tueButton.setBackground(red);
                break;
            case WEDNESDAY:
                wedButton.setBackground(red);
                break;
            case THURSDAY:
                thurButton.setBackground(red);
                break;
            case FRIDAY:
                friButton.setBackground(red);
                break;
            case SATURDAY:
                satButton.setBackground(red);
                break;
            case SUNDAY:
                sunButton.setBackground(red);
                break;
        }
    }

    public void showNutritionInfo(Dish dish)
    {
        dishnameLabel.setText(dish.getName());
        String portionSize = dish.getPortion();
        try{
            if(portionSize != null)
            {
                double size = Double.parseDouble(portionSize);
                portionSize = size + "oz"; //if it can be parsed to a number, it is in ounces
            }
            else
            {
                portionSize = "unknown";
            }
        } catch(NumberFormatException e){
            //portionSize is not a number, so leave it alone
        }
        servingSizeValLabel.setText(portionSize);
        if(dish.getCalories() != -1)
        {
            caloriesValLabel.setText(Double.toString(dish.getCalories()));
        }
        else
            caloriesValLabel.setText("unknown");
        if(dish.getFat() != -1)
        {
            totalFatValLabel.setText(dish.getFat() + "g");
        }
        else
            totalFatValLabel.setText("unknown");
        if(dish.getSaturatedFat() != -1)
        {
            satFatValLabel.setText(dish.getSaturatedFat() + "g");
        }
        else
            satFatValLabel.setText("unknown");
        if(dish.getChol() != -1)
        {
            cholesterolValLabel.setText(dish.getChol() + "mg");
        }
        else
            cholesterolValLabel.setText("unknown");
        if(dish.getSodium() != -1)
        {
            sodiumValLabel.setText(dish.getSodium() + "mg");
        }
        else
            sodiumValLabel.setText("unknown");
        if(dish.getCarbs() != -1)
        {
            totalCarbValLabel.setText(dish.getCarbs() + "g");
        }
        else
            totalCarbValLabel.setText("unknown");
        if(dish.getFiber() != -1)
        {
            fiberValLabel.setText(dish.getFiber() + "g");
        }
        else
            fiberValLabel.setText("unknown");
        if(dish.getProtein() != -1)
        {
            proteinValLabel.setText(dish.getProtein() + "g");
        }
        else
            proteinValLabel.setText("unknown");

        dishnameLabel.setVisible(true);
        servingLabel.setVisible(true);
        servingSizeValLabel.setVisible(true);
        calsLabel.setVisible(true);
        caloriesValLabel.setVisible(true);
        totalFatLabel.setVisible(true);
        totalFatValLabel.setVisible(true);
        satFatLabel.setVisible(true);
        satFatValLabel.setVisible(true);
        cholesterolLabel.setVisible(true);
        cholesterolValLabel.setVisible(true);
        sodiumLabel.setVisible(true);
        sodiumValLabel.setVisible(true);
        totalCarbsLabel.setVisible(true);
        totalCarbValLabel.setVisible(true);
        fiberLabel.setVisible(true);
        fiberValLabel.setVisible(true);
        totalProteinLabel.setVisible(true);
        proteinValLabel.setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mealGroup = new javax.swing.ButtonGroup();
        toPreferencesButton = new javax.swing.JButton();
        toGoalsButton = new javax.swing.JButton();
        breakfastRB = new javax.swing.JRadioButton();
        lunchRB = new javax.swing.JRadioButton();
        dinnerRB = new javax.swing.JRadioButton();
        dinerTabs = new javax.swing.JTabbedPane();
        monButton = new javax.swing.JButton();
        tueButton = new javax.swing.JButton();
        wedButton = new javax.swing.JButton();
        thurButton = new javax.swing.JButton();
        friButton = new javax.swing.JButton();
        satButton = new javax.swing.JButton();
        sunButton = new javax.swing.JButton();
        fatLabel = new javax.swing.JLabel();
        progressLabel = new javax.swing.JLabel();
        fatProgressBar = new javax.swing.JProgressBar();
        proteinLabel = new javax.swing.JLabel();
        proteinProgressBar = new javax.swing.JProgressBar();
        carbsLabel = new javax.swing.JLabel();
        carbsProgressBar = new javax.swing.JProgressBar();
        totalCalsLabel = new javax.swing.JLabel();
        calsProgressBar = new javax.swing.JProgressBar();
        selectMealButton = new javax.swing.JButton();
        dishnameLabel = new javax.swing.JLabel();
        servingLabel = new javax.swing.JLabel();
        calsLabel = new javax.swing.JLabel();
        totalFatLabel = new javax.swing.JLabel();
        satFatLabel = new javax.swing.JLabel();
        cholesterolLabel = new javax.swing.JLabel();
        servingSizeValLabel = new javax.swing.JLabel();
        caloriesValLabel = new javax.swing.JLabel();
        totalFatValLabel = new javax.swing.JLabel();
        satFatValLabel = new javax.swing.JLabel();
        sodiumLabel = new javax.swing.JLabel();
        totalCarbsLabel = new javax.swing.JLabel();
        fiberLabel = new javax.swing.JLabel();
        totalCarbValLabel = new javax.swing.JLabel();
        sodiumValLabel = new javax.swing.JLabel();
        fiberValLabel = new javax.swing.JLabel();
        cholesterolValLabel = new javax.swing.JLabel();
        totalProteinLabel = new javax.swing.JLabel();
        proteinValLabel = new javax.swing.JLabel();
        logOutButton = new javax.swing.JButton();

        mealGroup.add(breakfastRB);
        mealGroup.add(lunchRB);
        mealGroup.add(dinnerRB);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        toPreferencesButton.setText("Edit Preferences");
        toPreferencesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toPreferencesButtonMouseClicked(evt);
            }
        });

        toGoalsButton.setText("Edit Goals");
        toGoalsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toGoalsButtonMouseClicked(evt);
            }
        });

        breakfastRB.setText("Breakfast");
        breakfastRB.setSelected(true);
        breakfastRB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                breakfastRBMouseClicked(evt);
            }
        });

        lunchRB.setText("Lunch");
        lunchRB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lunchRBMouseClicked(evt);
            }
        });

        dinnerRB.setText("Dinner");
        dinnerRB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dinnerRBMouseClicked(evt);
            }
        });

        DinerPanel rattyPanel = new DinerPanel(_windowManager, this);
        DinerPanel vwPanel = new DinerPanel(_windowManager, this);
        DinerPanel ivyRoomPanel = new DinerPanel(_windowManager, this);

        dinerTabs.addTab("Ratty", rattyPanel);
        dinerTabs.addTab("V-Dub", vwPanel);
        dinerTabs.addTab("Ivy Room", ivyRoomPanel);

        ChangeListener l = new TabChangeListener(this);
        dinerTabs.addChangeListener(l);

        monButton.setText("Monday");
        monButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                monButtonMouseClicked(evt);
            }
        });

        tueButton.setText("Tuesday");
        tueButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tueButtonMouseClicked(evt);
            }
        });

        wedButton.setText("Wednesday");
        wedButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wedButtonMouseClicked(evt);
            }
        });

        thurButton.setText("Thursday");
        thurButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                thurButtonMouseClicked(evt);
            }
        });

        friButton.setText("Friday");
        friButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                friButtonMouseClicked(evt);
            }
        });

        satButton.setText("Saturday");
        satButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                satButtonMouseClicked(evt);
            }
        });

        sunButton.setText("Sunday");
        sunButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sunButtonMouseClicked(evt);
            }
        });

        fatLabel.setText("Fat:");

        progressLabel.setText("Progress towards daily intake goals:");

        proteinLabel.setText("Protein:");

        carbsLabel.setText("Carbs:");

        totalCalsLabel.setText("Total Calories:");

        selectMealButton.setText("Select Meal");
        selectMealButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectMealButtonMouseClicked(evt);
            }
        });

        dishnameLabel.setText("Dishname");
        dishnameLabel.setVisible(false);

        servingLabel.setText("Serving Size:");
        servingLabel.setVisible(false);

        calsLabel.setText("Calories:");
        calsLabel.setVisible(false);

        totalFatLabel.setText("Total Fat:");
        totalFatLabel.setVisible(false);

        satFatLabel.setText("Saturated Fat:");
        satFatLabel.setVisible(false);

        cholesterolLabel.setText("Cholesterol:");
        cholesterolLabel.setVisible(false);

        servingSizeValLabel.setText("0g");
        servingSizeValLabel.setVisible(false);

        caloriesValLabel.setText("0");
        caloriesValLabel.setVisible(false);

        totalFatValLabel.setText("0g");
        totalFatValLabel.setVisible(false);

        satFatValLabel.setText("0g");
        satFatValLabel.setVisible(false);

        sodiumLabel.setText("Sodium:");
        sodiumLabel.setVisible(false);

        totalCarbsLabel.setText("Total Carbohydrate:");
        totalCarbsLabel.setVisible(false);

        fiberLabel.setText("Fiber:");
        fiberLabel.setVisible(false);

        totalCarbValLabel.setText("0g");
        totalCarbValLabel.setVisible(false);

        sodiumValLabel.setText("0mg");
        sodiumValLabel.setVisible(false);

        fiberValLabel.setText("0g");
        fiberValLabel.setVisible(false);

        cholesterolValLabel.setText("0mg");
        cholesterolValLabel.setVisible(false);

        totalProteinLabel.setText("Protein:");
        totalProteinLabel.setVisible(false);

        proteinValLabel.setText("0g");
        proteinValLabel.setVisible(false);

        logOutButton.setText("Log out");
        logOutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logOutButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(toGoalsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 810, Short.MAX_VALUE)
                .addComponent(toPreferencesButton))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(breakfastRB)
                    .addComponent(lunchRB)
                    .addComponent(dinnerRB)
                    .addComponent(selectMealButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(monButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tueButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wedButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(thurButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(friButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(satButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sunButton))
                    .addComponent(dinerTabs))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(totalFatLabel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(calsLabel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(servingLabel)
                        .addGap(18, 18, 18)
                        .addComponent(servingSizeValLabel))
                    .addComponent(dishnameLabel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressLabel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(satFatLabel)
                            .addComponent(cholesterolLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cholesterolValLabel)
                            .addComponent(totalFatValLabel)
                            .addComponent(satFatValLabel)
                            .addComponent(caloriesValLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(totalCarbsLabel)
                            .addComponent(sodiumLabel)
                            .addComponent(fiberLabel)
                            .addComponent(totalProteinLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(proteinValLabel)
                            .addComponent(fiberValLabel)
                            .addComponent(sodiumValLabel)
                            .addComponent(totalCarbValLabel)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(totalCalsLabel)
                            .addComponent(proteinLabel)
                            .addComponent(carbsLabel)
                            .addComponent(fatLabel))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fatProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                            .addComponent(proteinProgressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                            .addComponent(carbsProgressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                            .addComponent(calsProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)))
                    .addComponent(logOutButton))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(dinerTabs, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(monButton)
                            .addComponent(tueButton)
                            .addComponent(wedButton)
                            .addComponent(thurButton)
                            .addComponent(friButton)
                            .addComponent(satButton)
                            .addComponent(sunButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(toPreferencesButton)
                            .addComponent(toGoalsButton))
                        .addGap(74, 74, 74)
                        .addComponent(progressLabel)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(breakfastRB)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lunchRB)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dinnerRB))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(fatProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(proteinProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(carbsProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(calsProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(fatLabel)
                                        .addGap(12, 12, 12)
                                        .addComponent(proteinLabel)
                                        .addGap(9, 9, 9)
                                        .addComponent(carbsLabel)
                                        .addGap(9, 9, 9)
                                        .addComponent(totalCalsLabel)))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(dishnameLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(servingLabel)
                                        .addComponent(servingSizeValLabel))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(calsLabel)
                                        .addComponent(caloriesValLabel))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(totalFatLabel)
                                        .addComponent(totalFatValLabel))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(satFatLabel)
                                        .addComponent(satFatValLabel))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cholesterolLabel)
                                        .addComponent(cholesterolValLabel)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                                    .addComponent(selectMealButton)
                                    .addGap(70, 70, 70)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(64, 64, 64)
                                        .addComponent(totalProteinLabel))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(sodiumLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(totalCarbsLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(fiberLabel))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(sodiumValLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(totalCarbValLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(fiberValLabel)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(proteinValLabel)))))))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(410, Short.MAX_VALUE)
                .addComponent(logOutButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void toPreferencesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toPreferencesButtonMouseClicked
        _windowManager.showLikeDislikeWindow();
    }//GEN-LAST:event_toPreferencesButtonMouseClicked

    private void toGoalsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toGoalsButtonMouseClicked
        _windowManager.showGoalsWindow();
    }//GEN-LAST:event_toGoalsButtonMouseClicked

    private void monButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_monButtonMouseClicked
        _day = Day.MONDAY;
        colorDayButtons();
        updateSelectedTab();
        updateNutritionSliders();

    }//GEN-LAST:event_monButtonMouseClicked

    private void tueButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tueButtonMouseClicked
        _day = Day.TUESDAY;
        colorDayButtons();
        updateSelectedTab();
        updateNutritionSliders();
    }//GEN-LAST:event_tueButtonMouseClicked

    private void wedButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wedButtonMouseClicked
        _day = Day.WEDNESDAY;
        colorDayButtons();
        updateSelectedTab();
        updateNutritionSliders();
    }//GEN-LAST:event_wedButtonMouseClicked

    private void thurButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_thurButtonMouseClicked
        _day = Day.THURSDAY;
        colorDayButtons();
        updateSelectedTab();
        updateNutritionSliders();
    }//GEN-LAST:event_thurButtonMouseClicked

    private void friButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_friButtonMouseClicked
        _day = Day.FRIDAY;
        colorDayButtons();
        updateSelectedTab();
        updateNutritionSliders();
    }//GEN-LAST:event_friButtonMouseClicked

    private void satButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_satButtonMouseClicked
        _day = Day.SATURDAY;
        colorDayButtons();
        updateSelectedTab();
        updateNutritionSliders();
    }//GEN-LAST:event_satButtonMouseClicked

    private void sunButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sunButtonMouseClicked
        _day = Day.SUNDAY;
        colorDayButtons();
        updateSelectedTab();
        updateNutritionSliders();
    }//GEN-LAST:event_sunButtonMouseClicked

    private void breakfastRBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_breakfastRBMouseClicked
        if (breakfastRB.isSelected()) {
            _meal.setMeal("breakfast");
            updateSelectedTab();
        }
    }//GEN-LAST:event_breakfastRBMouseClicked

    private void lunchRBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lunchRBMouseClicked
        if (lunchRB.isSelected()) {
            _meal.setMeal("lunch");
            updateSelectedTab();
        }
    }//GEN-LAST:event_lunchRBMouseClicked

    private void dinnerRBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dinnerRBMouseClicked
        if (dinnerRB.isSelected()) {
            _meal.setMeal("dinner");
            updateSelectedTab();
        }
    }//GEN-LAST:event_dinnerRBMouseClicked

    private void selectMealButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectMealButtonMouseClicked
        //set current meal as selected meal for current day and Meal
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE, Day.toInt(_day) - (day.get(Calendar.DAY_OF_WEEK) - 2));
        _windowManager.getUser().setSelectedLocation(day, _meal, _diningHall);
        _windowManager.getDatabase().updateUser(_windowManager.getUser());
        //update sliders
        updateNutritionSliders();
    }//GEN-LAST:event_selectMealButtonMouseClicked

    private void logOutButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutButtonMouseClicked
        _windowManager.setUser(null);
        _windowManager.showLoginWindow();
    }//GEN-LAST:event_logOutButtonMouseClicked

    public void onTabChange() {
        int tab = dinerTabs.getSelectedIndex();
        Location diningHall = new Location();
        switch (tab) {
            case 0:
                diningHall.setName("Ratty");
                break;
            case 1:
                diningHall.setName("VW");
                break;
            case 2:
                diningHall.setName("Ivy Room");
                break;
        }
        _diningHall = diningHall;

        updateSelectedTab();
    }

    private void updateSelectedTab()
    {
        //clear the old meal and menu
        DinerPanel selected = (DinerPanel) dinerTabs.getSelectedComponent();
        selected.clearMealAndMenu();
        //get and display the meal
        selected.redisplayMeal();
        //get and display the menu
        setMenu();
        selected.displayMenu();
    }

    class TabChangeListener implements ChangeListener {

        private PlannerMainWindow _window;

        public TabChangeListener(PlannerMainWindow window) {
            _window = window;
        }

        public void stateChanged(ChangeEvent e) {
            _window.onTabChange();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton breakfastRB;
    private javax.swing.JLabel caloriesValLabel;
    private javax.swing.JLabel calsLabel;
    private javax.swing.JProgressBar calsProgressBar;
    private javax.swing.JLabel carbsLabel;
    private javax.swing.JProgressBar carbsProgressBar;
    private javax.swing.JLabel cholesterolLabel;
    private javax.swing.JLabel cholesterolValLabel;
    private javax.swing.JTabbedPane dinerTabs;
    private javax.swing.JRadioButton dinnerRB;
    private javax.swing.JLabel dishnameLabel;
    private javax.swing.JLabel fatLabel;
    private javax.swing.JProgressBar fatProgressBar;
    private javax.swing.JLabel fiberLabel;
    private javax.swing.JLabel fiberValLabel;
    private javax.swing.JButton friButton;
    private javax.swing.JButton logOutButton;
    private javax.swing.JRadioButton lunchRB;
    private javax.swing.ButtonGroup mealGroup;
    private javax.swing.JButton monButton;
    private javax.swing.JLabel progressLabel;
    private javax.swing.JLabel proteinLabel;
    private javax.swing.JProgressBar proteinProgressBar;
    private javax.swing.JLabel proteinValLabel;
    private javax.swing.JButton satButton;
    private javax.swing.JLabel satFatLabel;
    private javax.swing.JLabel satFatValLabel;
    private javax.swing.JButton selectMealButton;
    private javax.swing.JLabel servingLabel;
    private javax.swing.JLabel servingSizeValLabel;
    private javax.swing.JLabel sodiumLabel;
    private javax.swing.JLabel sodiumValLabel;
    private javax.swing.JButton sunButton;
    private javax.swing.JButton thurButton;
    private javax.swing.JButton toGoalsButton;
    private javax.swing.JButton toPreferencesButton;
    private javax.swing.JLabel totalCalsLabel;
    private javax.swing.JLabel totalCarbValLabel;
    private javax.swing.JLabel totalCarbsLabel;
    private javax.swing.JLabel totalFatLabel;
    private javax.swing.JLabel totalFatValLabel;
    private javax.swing.JLabel totalProteinLabel;
    private javax.swing.JButton tueButton;
    private javax.swing.JButton wedButton;
    // End of variables declaration//GEN-END:variables
}

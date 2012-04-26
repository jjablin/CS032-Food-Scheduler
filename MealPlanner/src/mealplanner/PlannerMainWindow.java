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
import java.util.Date;
import java.util.HashSet;
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

    /** Creates new form PlannerMainWindow */
    public PlannerMainWindow(WindowManager wm) {
        _windowManager = wm;
        _meal = new Meal("breakfast");
        initComponents();
        _day = Day.MONDAY; //this should be the current day
        colorDayButtons();
    }

    private void colorDayButtons()
    {
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
        breakfastRB.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                breakfastRBStateChanged(evt);
            }
        });

        lunchRB.setText("Lunch");
        lunchRB.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                lunchRBStateChanged(evt);
            }
        });

        dinnerRB.setText("Dinner");
        dinnerRB.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                dinnerRBStateChanged(evt);
            }
        });

        DinerPanel rattyPanel = new DinerPanel(_windowManager);
        DinerPanel vwPanel = new DinerPanel(_windowManager);
        DinerPanel ivyRoomPanel = new DinerPanel(_windowManager);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(toGoalsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 709, Short.MAX_VALUE)
                .addComponent(toPreferencesButton))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(breakfastRB)
                    .addComponent(lunchRB)
                    .addComponent(dinnerRB))
                .addGap(18, 18, 18)
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
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(totalCalsLabel)
                            .addComponent(proteinLabel)
                            .addComponent(carbsLabel)
                            .addComponent(fatLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fatProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                            .addComponent(proteinProgressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                            .addComponent(carbsProgressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                            .addComponent(calsProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))))
                .addGap(53, 53, 53))
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
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fatLabel)
                                    .addComponent(fatProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(proteinLabel)
                                    .addComponent(proteinProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(carbsLabel)
                                    .addComponent(carbsProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(totalCalsLabel)
                                    .addComponent(calsProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(25, Short.MAX_VALUE))
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
    }//GEN-LAST:event_monButtonMouseClicked

    private void tueButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tueButtonMouseClicked
        _day = Day.TUESDAY;
        colorDayButtons();
    }//GEN-LAST:event_tueButtonMouseClicked

    private void wedButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wedButtonMouseClicked
        _day = Day.WEDNESDAY;
        colorDayButtons();
    }//GEN-LAST:event_wedButtonMouseClicked

    private void thurButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_thurButtonMouseClicked
        _day = Day.THURSDAY;
        colorDayButtons();
    }//GEN-LAST:event_thurButtonMouseClicked

    private void friButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_friButtonMouseClicked
        _day = Day.FRIDAY;
        colorDayButtons();
    }//GEN-LAST:event_friButtonMouseClicked

    private void satButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_satButtonMouseClicked
        _day = Day.SATURDAY;
        colorDayButtons();
    }//GEN-LAST:event_satButtonMouseClicked

    private void sunButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sunButtonMouseClicked
        _day = Day.SUNDAY;
        colorDayButtons();
    }//GEN-LAST:event_sunButtonMouseClicked

    private void breakfastRBStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_breakfastRBStateChanged
        if(breakfastRB.isSelected())
            _meal.setMeal("breakfast");
    }//GEN-LAST:event_breakfastRBStateChanged

    private void lunchRBStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_lunchRBStateChanged
        if(lunchRB.isSelected())
            _meal.setMeal("lunch");
    }//GEN-LAST:event_lunchRBStateChanged

    private void dinnerRBStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_dinnerRBStateChanged
        if(dinnerRB.isSelected())
            _meal.setMeal("dinner");
    }//GEN-LAST:event_dinnerRBStateChanged

    public void onTabChange()
    {
        int tab = dinerTabs.getSelectedIndex();
        Location diningHall = new Location();
        switch (tab){
            case 1:
                diningHall.setName("Ratty");
                break;
            case 2:
                diningHall.setName("V-Dub");
                break;
            case 3:
                diningHall.setName("Ivy Room");
                break;
        }

       List<MarkedDish> savedMeal =  _windowManager._user.getMarkedDishes(diningHall, _meal, _day);
       //display the meal
       DinerPanel p = (DinerPanel) dinerTabs.getSelectedComponent();
       p.displayMeal(savedMeal);
       //TODO: get and display the menu
       Calendar now = Calendar.getInstance();
       now.set(Calendar.DAY_OF_WEEK, Day.toInt(_day));
       HashSet<Dish> menu = _windowManager.getDatabase().getDishes(now, diningHall, _meal);
    }

    //converts _day to a date
    //TODO: fix this method
    public Date dayToDate()
    {
        Date today = new Date();
        long time = today.getTime();
        int todayDay = today.getDay();
        return new Date();
    }

    class TabChangeListener implements ChangeListener{

        private PlannerMainWindow _window;

        public TabChangeListener(PlannerMainWindow window)
        {
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
    private javax.swing.JProgressBar calsProgressBar;
    private javax.swing.JLabel carbsLabel;
    private javax.swing.JProgressBar carbsProgressBar;
    private javax.swing.JTabbedPane dinerTabs;
    private javax.swing.JRadioButton dinnerRB;
    private javax.swing.JLabel fatLabel;
    private javax.swing.JProgressBar fatProgressBar;
    private javax.swing.JButton friButton;
    private javax.swing.JRadioButton lunchRB;
    private javax.swing.ButtonGroup mealGroup;
    private javax.swing.JButton monButton;
    private javax.swing.JLabel progressLabel;
    private javax.swing.JLabel proteinLabel;
    private javax.swing.JProgressBar proteinProgressBar;
    private javax.swing.JButton satButton;
    private javax.swing.JButton sunButton;
    private javax.swing.JButton thurButton;
    private javax.swing.JButton toGoalsButton;
    private javax.swing.JButton toPreferencesButton;
    private javax.swing.JLabel totalCalsLabel;
    private javax.swing.JButton tueButton;
    private javax.swing.JButton wedButton;
    // End of variables declaration//GEN-END:variables

}

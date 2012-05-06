/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LikeDislikeWindow.java
 *
 * Created on Apr 10, 2012, 3:21:03 PM
 */

package mealplanner;

import java.awt.CheckboxGroup;
import java.awt.Checkbox;
import java.awt.Cursor;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.*;

/**
 *
 * @author seadams
 */
public class LikeDislikeWindow extends javax.swing.JFrame {

    private WindowManager _windowManager;

    /** Creates new form LikeDislikeWindow */
    public LikeDislikeWindow(WindowManager wm) {
        this.setCursor(Cursor.getDefaultCursor());
        initComponents();
        _windowManager = wm;
        showCurrentPreferences();
    }

    private void showCurrentPreferences()
    {
        Set<String> likes = _windowManager.getUser().getLikes();
        Set<String> dislikes = _windowManager.getUser().getDislikes();

        //clear any old like labels
        int numLikesLabels = likesPanel.getComponentCount();
        for(int i = 0; i < numLikesLabels; i++)
        {
            likesPanel.remove(0);
        }

        //display all likes
        Iterator likesItr = likes.iterator();
        while(likesItr.hasNext())
        {
            String nextLike = (String) likesItr.next();
            JLabel nextLabel = new JLabel(nextLike);
            likesPanel.add(nextLabel);
        }

        //clear any old dislike labels
        int numDislikesLabels = dislikesPanel.getComponentCount();
        for(int i = 0; i < numDislikesLabels; i++)
        {
            dislikesPanel.remove(0);
        }

        //display all dislikes
        Iterator dislikesItr = dislikes.iterator();
        while(dislikesItr.hasNext())
        {
            String nextLike = (String) dislikesItr.next();
            JLabel nextLabel = new JLabel(nextLike);
            dislikesPanel.add(nextLabel);
        }

        //display changes
        likesPane.paintAll(likesPane.getGraphics());
        dislikesPane.paintAll(dislikesPane.getGraphics());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        instructionLabel = new javax.swing.JLabel();
        dishLabel = new javax.swing.JLabel();
        dishField = new javax.swing.JTextField();
        searchResultsLabel = new javax.swing.JLabel();
        resultDisplay = new javax.swing.JScrollPane();
        resultPanel = new javax.swing.JPanel();
        clearDislikesButton = new javax.swing.JButton();
        clearLikesButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        likesLabel = new javax.swing.JLabel();
        dislikesLabel = new javax.swing.JLabel();
        likesPane = new javax.swing.JScrollPane();
        likesPanel = new javax.swing.JPanel();
        dislikesPane = new javax.swing.JScrollPane();
        dislikesPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        instructionLabel.setText("Search for and enter your likes and dislikes below");
        instructionLabel.setAlignmentY(this.CENTER_ALIGNMENT);

        dishLabel.setText("Dish:");

        dishField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dishFieldKeyPressed(evt);
            }
        });

        searchResultsLabel.setText("Search results:");

        resultPanel.setLayout(new javax.swing.BoxLayout(resultPanel, javax.swing.BoxLayout.Y_AXIS));
        resultDisplay.setViewportView(resultPanel);

        AdjustmentListener listener = new ScrollListener();
        resultDisplay.getHorizontalScrollBar().addAdjustmentListener(listener);
        resultDisplay.getVerticalScrollBar().addAdjustmentListener(listener);

        clearDislikesButton.setText("Clear all dislikes");
        clearDislikesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearDislikesButtonMouseClicked(evt);
            }
        });

        clearLikesButton.setText("Clear all likes");
        clearLikesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearLikesButtonMouseClicked(evt);
            }
        });

        nextButton.setText("Next");
        nextButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nextButtonMouseClicked(evt);
            }
        });

        likesLabel.setText("Likes:");

        dislikesLabel.setText("Dislikes:");

        javax.swing.GroupLayout likesPanelLayout = new javax.swing.GroupLayout(likesPanel);
        likesPanel.setLayout(likesPanelLayout);
        likesPanelLayout.setHorizontalGroup(
            likesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 162, Short.MAX_VALUE)
        );
        likesPanelLayout.setVerticalGroup(
            likesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 262, Short.MAX_VALUE)
        );

        likesPanel.setLayout(new BoxLayout(likesPanel, BoxLayout.Y_AXIS));

        likesPane.setViewportView(likesPanel);

        javax.swing.GroupLayout dislikesPanelLayout = new javax.swing.GroupLayout(dislikesPanel);
        dislikesPanel.setLayout(dislikesPanelLayout);
        dislikesPanelLayout.setHorizontalGroup(
            dislikesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 159, Short.MAX_VALUE)
        );
        dislikesPanelLayout.setVerticalGroup(
            dislikesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 262, Short.MAX_VALUE)
        );

        dislikesPanel.setLayout(new BoxLayout(dislikesPanel, BoxLayout.Y_AXIS));

        dislikesPane.setViewportView(dislikesPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(clearLikesButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearDislikesButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(881, Short.MAX_VALUE)
                        .addComponent(nextButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dishLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dishField, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(instructionLabel)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(resultDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchResultsLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(likesLabel)
                            .addComponent(likesPane, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dislikesLabel)
                            .addComponent(dislikesPane, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(instructionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dishLabel)
                    .addComponent(dishField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchResultsLabel)
                    .addComponent(likesLabel)
                    .addComponent(dislikesLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dislikesPane, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addComponent(likesPane, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addComponent(resultDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearDislikesButton)
                    .addComponent(clearLikesButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //if the key pressed is enter, displays the menu items which contain the
    //entered text in their name
    private void clearLikesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearLikesButtonMouseClicked
        _windowManager.getUser().clearLikes(); //clears the user object's likes

        //update the window to show the likes have been cleared
        for(int i = 0; i < resultPanel.getComponentCount(); i++)
        {
            JPanel p = (JPanel) resultPanel.getComponent(i);
            Checkbox likeBox = (Checkbox) p.getComponent(1);
            Checkbox noPrefBox = (Checkbox) p.getComponent(3);
            if(likeBox.getState())
            {
                likeBox.setState(false);
                noPrefBox.setState(true);
            }
        }
        showCurrentPreferences();
    }//GEN-LAST:event_clearLikesButtonMouseClicked

    private void clearDislikesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearDislikesButtonMouseClicked
        _windowManager.getUser().clearDislikes(); //clears the user object's dislikes

        //update the window to show the likes have been cleared
        for(int i = 0; i < resultPanel.getComponentCount(); i++)
        {
            JPanel p = (JPanel) resultPanel.getComponent(i);
            Checkbox dislikeBox = (Checkbox) p.getComponent(2);
            Checkbox noPrefBox = (Checkbox) p.getComponent(3);
            if(dislikeBox.getState())
            {
                dislikeBox.setState(false);
                noPrefBox.setState(true);
            }
        }
        showCurrentPreferences();
    }//GEN-LAST:event_clearDislikesButtonMouseClicked

    private void nextButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nextButtonMouseClicked
        _windowManager.getDatabase().updateUser(_windowManager.getUser());
        _windowManager.showEmailWindow();
}//GEN-LAST:event_nextButtonMouseClicked

    private void dishFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dishFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            //get the dishes to display
            String input = dishField.getText().toUpperCase();
            TreeSet<Dish> dishes = _windowManager.getDatabase().getFuzzyDishes(input);

            //clear any old dishes
            int numOldDishes = resultPanel.getComponentCount();
            for(int i = 0; i < numOldDishes; i++)
            {
                resultPanel.remove(0);
            }


            //display the dishes
            HashSet<String> names = new HashSet<String>();
            for(Dish dish: dishes)
            {
                if(!names.contains(dish.getName())) {
                  names.add(dish.getName());
                  addDishToResultDisplay(dish.getName());
                }
            }
            //show the new components
            resultDisplay.revalidate();
            this.paintAll(this.getGraphics());
        }
    }//GEN-LAST:event_dishFieldKeyPressed

    private void addDishToResultDisplay(String dishName)
    {
        JLabel l = new JLabel(dishName);
        CheckboxGroup group = new CheckboxGroup();
        Checkbox likeBox = new Checkbox("Like", false, group);
        Checkbox dislikeBox = new Checkbox("Dislike", false, group);
        Checkbox noPreferenceBox = new Checkbox("No preference", true, group);
        if(_windowManager.getUser().getLikes().contains(dishName))
        {
            likeBox.setState(true);
        }
        else if(_windowManager.getUser().getDislikes().contains(dishName))
        {
            dislikeBox.setState(true);
        }

        //add mouse listeners to the check boxes
        likeBox.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                likeBoxClicked(evt);
            }
        });

        dislikeBox.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dislikeBoxClicked(evt);
            }
        });

        noPreferenceBox.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                noPreferenceBoxClicked(evt);
            }
        });

        JPanel p = new JPanel();
        //add components to p
        p.add(l);
        p.add(likeBox);
        p.add(dislikeBox);
        p.add(noPreferenceBox);

        //add p to resultPanel
        resultPanel.add(p);
    }

    private void likeBoxClicked(java.awt.event.MouseEvent evt)
    {
        JPanel p = (JPanel) evt.getComponent().getParent();
        JLabel l = (JLabel) p.getComponent(0);
        String dishName = l.getText();
        if(! _windowManager.getUser().getLikes().contains(dishName))
        {
            _windowManager.getUser().removeDislike(dishName);
            //must remove from dislikes before adding to likes bcs of implementation in UserAccount
            _windowManager.getUser().addLike(dishName);
            showCurrentPreferences();
        }
    }

    private void dislikeBoxClicked(java.awt.event.MouseEvent evt)
    {
        JPanel p = (JPanel) evt.getComponent().getParent();
        JLabel l = (JLabel) p.getComponent(0);
        String dishName = l.getText();
        if(! _windowManager.getUser().getDislikes().contains(dishName))
        {
            _windowManager.getUser().removeLike(dishName);//must remove from likes before adding to likes bcs of implementation in UserAccount
            _windowManager.getUser().addDislike(dishName);
            dislikesPanel.add(new JLabel(dishName));
            showCurrentPreferences();
        }
    }

    private void noPreferenceBoxClicked(java.awt.event.MouseEvent evt)
    {
        JPanel p = (JPanel) evt.getComponent().getParent();
        JLabel l = (JLabel) p.getComponent(0);
        String dishName = l.getText();
        _windowManager.getUser().removeDislike(dishName);
        _windowManager.getUser().removeLike(dishName);
        showCurrentPreferences();
    }

    class ScrollListener implements AdjustmentListener {


        public ScrollListener() {
            
        }

        public void adjustmentValueChanged(AdjustmentEvent e) {
            resultDisplay.revalidate();
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
    private javax.swing.JButton clearDislikesButton;
    private javax.swing.JButton clearLikesButton;
    private javax.swing.JTextField dishField;
    private javax.swing.JLabel dishLabel;
    private javax.swing.JLabel dislikesLabel;
    private javax.swing.JScrollPane dislikesPane;
    private javax.swing.JPanel dislikesPanel;
    private javax.swing.JLabel instructionLabel;
    private javax.swing.JLabel likesLabel;
    private javax.swing.JScrollPane likesPane;
    private javax.swing.JPanel likesPanel;
    private javax.swing.JButton nextButton;
    private javax.swing.JScrollPane resultDisplay;
    private javax.swing.JPanel resultPanel;
    private javax.swing.JLabel searchResultsLabel;
    // End of variables declaration//GEN-END:variables

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AllergyWindow.java
 *
 * Created on Apr 22, 2012, 8:25:48 PM
 */

package mealplanner;

import java.util.Set;

/**
 *
 * @author seadams
 */
public class AllergyWindow extends javax.swing.JFrame {

    private WindowManager _windowManager;

    /** Creates new form AllergyWindow */
    public AllergyWindow(WindowManager wm) {
        initComponents();
        _windowManager = wm;

        selectAllergies();
    }

    private void selectAllergies()
    {
        Set<Allergy> allergies = _windowManager._user.getAllergies();
        if(allergies.contains(Allergy.CORN))
            cornBox.setSelected(true);
        if(allergies.contains(Allergy.EGG))
            eggsBox.setSelected(true);
        if(allergies.contains(Allergy.FISH))
            fishBox.setSelected(true);
        if(allergies.contains(Allergy.FRUIT))
            fruitBox.setSelected(true);
        if(allergies.contains(Allergy.GARLIC))
            garlicBox.setSelected(true);
        if(allergies.contains(Allergy.MILK))
            milkBox.setSelected(true);
        if(allergies.contains(Allergy.MSG))
            msgBox.setSelected(true);
        if(allergies.contains(Allergy.OATS))
            oatsBox.setSelected(true);
        if(allergies.contains(Allergy.PEANUT))
            peanutsBox.setSelected(true);
        if(allergies.contains(Allergy.SHELLFISH))
            shellfishBox.setSelected(true);
        if(allergies.contains(Allergy.SOY))
            soyBox.setSelected(true);
        if(allergies.contains(Allergy.SULPHITES))
            sulphitesBox.setSelected(true);
        if(allergies.contains(Allergy.TREE_NUT))
            treeNutsBox.setSelected(true);
        if(allergies.contains(Allergy.WHEAT))
            wheatBox.setSelected(true);
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
        cornBox = new javax.swing.JCheckBox();
        fruitBox = new javax.swing.JCheckBox();
        garlicBox = new javax.swing.JCheckBox();
        oatsBox = new javax.swing.JCheckBox();
        milkBox = new javax.swing.JCheckBox();
        peanutsBox = new javax.swing.JCheckBox();
        fishBox = new javax.swing.JCheckBox();
        shellfishBox = new javax.swing.JCheckBox();
        soyBox = new javax.swing.JCheckBox();
        treeNutsBox = new javax.swing.JCheckBox();
        wheatBox = new javax.swing.JCheckBox();
        eggsBox = new javax.swing.JCheckBox();
        msgBox = new javax.swing.JCheckBox();
        sulphitesBox = new javax.swing.JCheckBox();
        toPlannerButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        instructionLabel.setText("Select your allergies below");

        cornBox.setText("Corn");

        fruitBox.setText("Fruit");

        garlicBox.setText("Garlic");

        oatsBox.setText("Oats");

        milkBox.setText("Milk");

        peanutsBox.setText("Peanuts");

        fishBox.setText("Fish");

        shellfishBox.setText("Shellfish");

        soyBox.setText("Soy");

        treeNutsBox.setText("Tree nuts");

        wheatBox.setText("Wheat");

        eggsBox.setText("Eggs");

        msgBox.setText("MSG");

        sulphitesBox.setText("Sulphites");

        toPlannerButton.setText("Go to Meal Planner");
        toPlannerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toPlannerButtonMouseClicked(evt);
            }
        });

        backButton.setText("Back");
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cornBox)
                            .addComponent(fruitBox)
                            .addComponent(garlicBox)
                            .addComponent(oatsBox)
                            .addComponent(milkBox)
                            .addComponent(peanutsBox)
                            .addComponent(fishBox))
                        .addGap(72, 72, 72)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sulphitesBox)
                            .addComponent(msgBox)
                            .addComponent(eggsBox)
                            .addComponent(wheatBox)
                            .addComponent(treeNutsBox)
                            .addComponent(soyBox)
                            .addComponent(shellfishBox)))
                    .addComponent(instructionLabel)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(backButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                        .addComponent(toPlannerButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(instructionLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cornBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fruitBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(garlicBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(oatsBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(milkBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(peanutsBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fishBox))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(shellfishBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(soyBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(treeNutsBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wheatBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eggsBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(msgBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sulphitesBox)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toPlannerButton)
                    .addComponent(backButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backButtonMouseClicked
        saveAllergies();
         _windowManager.getDatabase().updateUser(_windowManager._user);
        _windowManager.showLikeDislikeWindow();
    }//GEN-LAST:event_backButtonMouseClicked

    private void toPlannerButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toPlannerButtonMouseClicked
        saveAllergies();
         _windowManager.getDatabase().updateUser(_windowManager._user);
        _windowManager.showPlannerMainWindow();
    }//GEN-LAST:event_toPlannerButtonMouseClicked

    private void saveAllergies()
    {
        if(cornBox.isSelected())
            _windowManager._user.addAllergy(Allergy.CORN);
        else
            _windowManager._user.removeAllergy(Allergy.CORN);
        if(eggsBox.isSelected())
            _windowManager._user.addAllergy(Allergy.EGG);
        else
            _windowManager._user.removeAllergy(Allergy.EGG);
        if(fishBox.isSelected())
            _windowManager._user.addAllergy(Allergy.FISH);
        else
            _windowManager._user.removeAllergy(Allergy.FISH);
        if(fruitBox.isSelected())
            _windowManager._user.addAllergy(Allergy.FRUIT);
        else
            _windowManager._user.removeAllergy(Allergy.FRUIT);
        if(garlicBox.isSelected())
            _windowManager._user.addAllergy(Allergy.GARLIC);
        else
            _windowManager._user.removeAllergy(Allergy.GARLIC);
        if(milkBox.isSelected())
            _windowManager._user.addAllergy(Allergy.MILK);
        else
            _windowManager._user.removeAllergy(Allergy.MILK);
        if(msgBox.isSelected())
            _windowManager._user.addAllergy(Allergy.MSG);
        else
            _windowManager._user.removeAllergy(Allergy.MSG);
        if(oatsBox.isSelected())
            _windowManager._user.addAllergy(Allergy.OATS);
        else
            _windowManager._user.removeAllergy(Allergy.OATS);
        if(peanutsBox.isSelected())
            _windowManager._user.addAllergy(Allergy.PEANUT);
        else
            _windowManager._user.removeAllergy(Allergy.PEANUT);
        if(shellfishBox.isSelected())
            _windowManager._user.addAllergy(Allergy.SHELLFISH);
        else
            _windowManager._user.removeAllergy(Allergy.SHELLFISH);
        if(soyBox.isSelected())
            _windowManager._user.addAllergy(Allergy.SOY);
        else
            _windowManager._user.removeAllergy(Allergy.SOY);
        if(sulphitesBox.isSelected())
            _windowManager._user.addAllergy(Allergy.SULPHITES);
        else
            _windowManager._user.removeAllergy(Allergy.SULPHITES);
        if(treeNutsBox.isSelected())
            _windowManager._user.addAllergy(Allergy.TREE_NUT);
        else
            _windowManager._user.removeAllergy(Allergy.TREE_NUT);
        if(wheatBox.isSelected())
            _windowManager._user.addAllergy(Allergy.WHEAT);
        else
            _windowManager._user.removeAllergy(Allergy.WHEAT);
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
    private javax.swing.JButton backButton;
    private javax.swing.JCheckBox cornBox;
    private javax.swing.JCheckBox eggsBox;
    private javax.swing.JCheckBox fishBox;
    private javax.swing.JCheckBox fruitBox;
    private javax.swing.JCheckBox garlicBox;
    private javax.swing.JLabel instructionLabel;
    private javax.swing.JCheckBox milkBox;
    private javax.swing.JCheckBox msgBox;
    private javax.swing.JCheckBox oatsBox;
    private javax.swing.JCheckBox peanutsBox;
    private javax.swing.JCheckBox shellfishBox;
    private javax.swing.JCheckBox soyBox;
    private javax.swing.JCheckBox sulphitesBox;
    private javax.swing.JButton toPlannerButton;
    private javax.swing.JCheckBox treeNutsBox;
    private javax.swing.JCheckBox wheatBox;
    // End of variables declaration//GEN-END:variables

}
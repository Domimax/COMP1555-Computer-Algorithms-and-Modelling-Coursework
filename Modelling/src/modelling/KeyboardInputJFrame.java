/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Maks Domas Smirnov
 */
public class KeyboardInputJFrame extends JFrame implements ActionListener {

    private Modelling modelling;
    
    private JTextField fieldBathrooms;
    private JTextField fieldAreaSite;
    private JTextField fieldLivingSpace;
    private JTextField fieldGarages;
    private JTextField fieldRooms;
    private JTextField fieldBedrooms;
    private JTextField fieldAge;
    private JTextField fieldPrice;
    
    public KeyboardInputJFrame(Modelling modelling) {
        this.modelling = modelling;
        initialise();
    }

    private void initialise() {
        this.setTitle("Input data through the keyboard.");
        this.getContentPane().setLayout(null);
        this.setBounds(100, 100, 500, 500);
        
        JLabel labelBathrooms = new JLabel("Number of bathrooms:");
        labelBathrooms.setBounds(50, 25, 200, 25);
        this.getContentPane().add(labelBathrooms);

        JLabel labelAreaSite = new JLabel("Total site area:");
        labelAreaSite.setBounds(50, 50, 200, 25);
        this.getContentPane().add(labelAreaSite);

        JLabel labelLivingSpace = new JLabel("Total living space:");
        labelLivingSpace.setBounds(50, 75, 200, 25);
        this.getContentPane().add(labelLivingSpace);

        JLabel labelGarages = new JLabel("Number of garages:");
        labelGarages.setBounds(50, 100, 200, 25);
        this.getContentPane().add(labelGarages);

        JLabel labelRooms = new JLabel("Number of rooms:");
        labelRooms.setBounds(50, 125, 200, 25);
        this.getContentPane().add(labelRooms);

        JLabel labelBedrooms = new JLabel("Number of bedrooms:");
        labelBedrooms.setBounds(50, 150, 200, 25);
        this.getContentPane().add(labelBedrooms);

        JLabel labelAge = new JLabel("Age of propery:");
        labelAge.setBounds(50, 175, 200, 25);
        this.getContentPane().add(labelAge);
               
        JLabel labelPrice = new JLabel("Price of the property:");
        labelPrice.setBounds(50, 200, 200, 25);
        this.getContentPane().add(labelPrice);

        fieldBathrooms = new JTextField();
        fieldBathrooms.setBounds(300, 25, 100, 25);
        this.getContentPane().add(fieldBathrooms);

        fieldAreaSite = new JTextField();
        fieldAreaSite.setBounds(300, 50, 100, 25);
        this.getContentPane().add(fieldAreaSite);

        fieldLivingSpace = new JTextField();
        fieldLivingSpace.setBounds(300, 75, 100, 25);
        this.getContentPane().add(fieldLivingSpace);

        fieldGarages = new JTextField();
        fieldGarages.setBounds(300, 100, 100, 25);
        this.getContentPane().add(fieldGarages);

        fieldRooms = new JTextField();
        fieldRooms.setBounds(300, 125, 100, 25);
        this.getContentPane().add(fieldRooms);

        fieldBedrooms = new JTextField();
        fieldBedrooms.setBounds(300, 150, 100, 25);
        this.getContentPane().add(fieldBedrooms);

        fieldAge = new JTextField();
        fieldAge.setBounds(300, 175, 100, 25);
        this.getContentPane().add(fieldAge);
        
        fieldPrice = new JTextField();
        fieldPrice.setBounds(300, 200, 100, 25);
        this.getContentPane().add(fieldPrice);

        JButton submitButton = new JButton("Submit data");
        submitButton.setBounds(50, 225, 200, 25);
        submitButton.setActionCommand("Submit");
        submitButton.addActionListener(this);
        this.getContentPane().add(submitButton);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {   
        if(e.getActionCommand().equals("Submit")){
            storeData();
        }
    }
    
    private void storeData(){   
        modelling.getNoOfBathroomsX1().add(Float.parseFloat(fieldBathrooms.getText()));
        modelling.getSiteAreaX2().add(Float.parseFloat(fieldAreaSite.getText()));
        modelling.getLivingSpaceX3().add(Float.parseFloat(fieldLivingSpace.getText()));
        modelling.getNoOfGaragesX4().add(Integer.parseInt(fieldGarages.getText()));
        modelling.getNoOfRoomsX5().add(Integer.parseInt(fieldRooms.getText()));
        modelling.getNoOfBedroomsX6().add(Integer.parseInt(fieldBedrooms.getText()));
        modelling.getAgeX7().add(Integer.parseInt(fieldAge.getText()));
        modelling.getNoOfBathroomsX1().add(Float.parseFloat(fieldBathrooms.getText()));   
    }
}

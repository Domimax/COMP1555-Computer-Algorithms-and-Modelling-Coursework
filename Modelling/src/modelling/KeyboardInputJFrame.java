package modelling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * A simple form to read in data through the keyboard.
 *
 * @author Roshaan Nazir, ID: rn6706a
 * @author Maks Domas Smirnov, ID: ms8749c
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

    // GUI initialisation
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

        JLabel labelAge = new JLabel("Age of property:");
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
        //Stores the data in the arraylist of properties in the modelling class and deletes 
        //all associations to this form, so that the garbage collector can dispose of it
        if (e.getActionCommand().equals("Submit")) {
            try {
                storeKeyboardInputData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Please ensure you are only entering positive numbers");
            }
            this.setVisible(false);
            modelling.setForm(null);
            modelling = null;
        }
    }

    // Method is used to store data inputted through the keyboard
    private void storeKeyboardInputData() throws NumberFormatException, IOException {
        Property newProperty = new Property(Property.getIdCount(), Float.parseFloat(getFieldBathrooms().getText()),
                Float.parseFloat(getFieldAreaSite().getText()), Float.parseFloat(getFieldLivingSpace().getText()),
                (float) Integer.parseInt(getFieldGarages().getText()), (float) Integer.parseInt(getFieldRooms().getText()),
                (float) Integer.parseInt(getFieldBedrooms().getText()), (float) Integer.parseInt(getFieldAge().getText()),
                Float.parseFloat(getFieldPrice().getText()));
        if (newProperty.getNoOfBathroomsX1() >= 0 && newProperty.getSiteAreaX2() >= 0
                && newProperty.getLivingSpaceX3() >= 0 && newProperty.getNoOfGaragesX4() >= 0
                && newProperty.getNoOfRoomsX5() >= 0 && newProperty.getNoOfBedroomsX6() >= 0
                && newProperty.getAgeX7() >= 0 && newProperty.getPriceY() >= 0) {
            Property.setIdCount(Property.getIdCount() + 1);
            modelling.getProperties().add(newProperty);
        } else {
            throw new IOException();
        }

    }

    public JTextField getFieldBathrooms() {
        // returns fieldBathrooms
        return fieldBathrooms;
    }

    public JTextField getFieldAreaSite() {
        //returns fieldAreaSite
        return fieldAreaSite;
    }

    public JTextField getFieldLivingSpace() {
        //returns fieldLivingSpace
        return fieldLivingSpace;
    }

    public JTextField getFieldGarages() {
        //returns fieldGarages
        return fieldGarages;
    }

    public JTextField getFieldRooms() {
        //returns fieldRooms
        return fieldRooms;
    }

    public JTextField getFieldBedrooms() {
        //returns fieldBedrooms
        return fieldBedrooms;
    }

    public JTextField getFieldAge() {
        //returns fieldAge
        return fieldAge;
    }

    public JTextField getFieldPrice() {
        //returns fieldPrice
        return fieldPrice;
    }
}

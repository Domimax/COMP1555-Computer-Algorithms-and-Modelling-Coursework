package modelling;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ms8794c
 * @author rn6706a
 */
public class Modelling implements ActionListener {

    private KeyboardInputJFrame form;

    private ArrayList<Float> noOfBathroomsX1;
    private ArrayList<Float> siteAreaX2;
    private ArrayList<Float> livingSpaceX3;
    private ArrayList<Integer> noOfGaragesX4;
    private ArrayList<Integer> noOfRoomsX5;
    private ArrayList<Integer> noOfBedroomsX6;
    private ArrayList<Integer> ageX7;
    private ArrayList<Float> priceY;

    public static void main(String[] args) {
        Modelling application = new Modelling();
    }

    public Modelling() {
        model();
        view();
        controller();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Called if we want to manually add data about a property via keyboard
        if (e.getActionCommand().equals("Keyboard")) {
            form = new KeyboardInputJFrame(this);
        }
        //Called if we want to add data about a property via an external file
        if (e.getActionCommand().equals("File")) {
            System.out.println("This functionality is not coded yet.");
        }
    }

    private void model() {
        noOfBathroomsX1 = new ArrayList<>();
        siteAreaX2 = new ArrayList<>();
        livingSpaceX3 = new ArrayList<>();
        noOfGaragesX4 = new ArrayList<>();
        noOfRoomsX5 = new ArrayList<>();
        noOfBedroomsX6 = new ArrayList<>();
        ageX7 = new ArrayList<>();
        priceY = new ArrayList<>();
    }

    // GUI initialisation
    private void view() {
        Font fnt = new Font("Times New Roman", Font.PLAIN, 24);
        JFrame mainMenu = new JFrame();
        JMenuBar menuBar = new JMenuBar();
        JMenu inputMenu = new JMenu("Input data");

        JMenuItem addThroughKeyboard = new JMenuItem();
        JMenuItem addThroughFile = new JMenuItem();

        inputMenu.setFont(fnt);

        addThroughKeyboard.setText("Enter data through keyboard");
        addThroughKeyboard.setFont(fnt);
        addThroughKeyboard.setActionCommand("Keyboard");
        addThroughKeyboard.addActionListener(this);
        inputMenu.add(addThroughKeyboard);

        addThroughFile.setText("Read in a file");
        addThroughFile.setFont(fnt);
        addThroughFile.setActionCommand("File");
        addThroughFile.addActionListener(this);
        inputMenu.add(addThroughFile);

        mainMenu.setJMenuBar(menuBar);
        menuBar.add(inputMenu);
        mainMenu.setExtendedState(JFrame.MAXIMIZED_BOTH);

        mainMenu.setTitle("Coursework");
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainMenu.setVisible(true);
    }

    private void controller() {

    }

    public ArrayList<Float> getNoOfBathroomsX1() {
        return noOfBathroomsX1;
    }

    public ArrayList<Float> getSiteAreaX2() {
        return siteAreaX2;
    }

    public ArrayList<Float> getLivingSpaceX3() {
        return livingSpaceX3;
    }

    public ArrayList<Integer> getNoOfGaragesX4() {
        return noOfGaragesX4;
    }

    public ArrayList<Integer> getNoOfRoomsX5() {
        return noOfRoomsX5;
    }

    public ArrayList<Integer> getNoOfBedroomsX6() {
        return noOfBedroomsX6;
    }

    public ArrayList<Integer> getAgeX7() {
        return ageX7;
    }
    
    public ArrayList<Float> getPriceY() {
        return priceY;
    }

    public void setForm(KeyboardInputJFrame form) {
        this.form = form;
    }
}

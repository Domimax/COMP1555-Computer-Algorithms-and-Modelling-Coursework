package modelling;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author ms8794c
 * @author rn6706a
 */
public class Modelling implements ActionListener {

    private KeyboardInputJFrame form;
    private JComboBox xChosen;

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
        xChosen = new JComboBox();
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

        xChosen.setFont(fnt);
        xChosen.addActionListener(this);
        xChosen.setActionCommand("IndependentX");
        xChosen.addItem("Select your independent variable");
        xChosen.addItem("No. of bathrooms");
        xChosen.addItem("Site area (1000's square feet)");
        xChosen.addItem("Living space (1000's square feet)");
        xChosen.addItem("No. of garages");
        xChosen.addItem("No. of rooms");
        xChosen.addItem("No. of bedrooms");
        xChosen.addItem("Age (years)");

        mainMenu.setJMenuBar(menuBar);
        menuBar.add(inputMenu);
        menuBar.add(xChosen);
        menuBar.add(xChosen);
        mainMenu.setExtendedState(JFrame.MAXIMIZED_BOTH);

        mainMenu.setTitle("Coursework");
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainMenu.setVisible(true);
    }

    private void controller() {

    }

    public void storeKeyboardInputData() {
        noOfBathroomsX1.add(Float.parseFloat(form.getFieldBathrooms().getText()));
        siteAreaX2.add(Float.parseFloat(form.getFieldAreaSite().getText()));
        livingSpaceX3.add(Float.parseFloat(form.getFieldLivingSpace().getText()));
        noOfGaragesX4.add(Integer.parseInt(form.getFieldGarages().getText()));
        noOfRoomsX5.add(Integer.parseInt(form.getFieldRooms().getText()));
        noOfBedroomsX6.add(Integer.parseInt(form.getFieldBedrooms().getText()));
        ageX7.add(Integer.parseInt(form.getFieldAge().getText()));
        priceY.add(Float.parseFloat(form.getFieldPrice().getText()));
    }

    public void setForm(KeyboardInputJFrame form) {
        this.form = form;
    }
}

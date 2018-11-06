/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelling;

import javax.swing.*;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author rn6706a
 */
public class Modelling implements ActionListener {

    ArrayList<Float> dependent;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* private String message = "Click to open program";
        JOptionPane.showMessageDiaolog(null, "information", "information", JOptionPane.INFORMATION_MESSAGE);
        Modelling prg = new Modelling();
         */
        Modelling application = new Modelling();

    }

    public Modelling() {
        model();
        view();
        controller();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("keyboard")) {
            
            JFrame form = new JFrame();
            
            form.getContentPane().setLayout(null);
            form.setBounds(100, 100, 500, 500);

            JLabel labelBathrooms = new JLabel("Number of bathrooms:");
            labelBathrooms.setBounds(50, 25, 200, 25);
            form.getContentPane().add(labelBathrooms);
            
            JLabel labelAreaSite = new JLabel("Total site area:");
            labelAreaSite.setBounds(50, 50, 200, 25);
            form.getContentPane().add(labelAreaSite);
            
            JLabel labelLivingSpace = new JLabel("Total living space:");
            labelLivingSpace.setBounds(50, 75, 200, 25);
            form.getContentPane().add(labelLivingSpace);
            
            JLabel labelGarages = new JLabel("Number of garages:");
            labelGarages.setBounds(50, 100, 200, 25);
            form.getContentPane().add(labelGarages);
            
            JLabel labelRooms = new JLabel("Number of rooms:");
            labelRooms.setBounds(50, 125, 200, 25);
            form.getContentPane().add(labelRooms);
            
            JLabel labelBedrooms = new JLabel("Number of bedrooms:");
            labelBedrooms.setBounds(50, 150, 200, 25);
            form.getContentPane().add(labelBedrooms);
            
            JLabel labelAge = new JLabel("Age of propery:");
            labelAge.setBounds(50, 175, 200, 25);
            form.getContentPane().add(labelAge);
            
            
            
            JTextField fieldBathrooms = new JTextField();
            fieldBathrooms.setBounds(300, 25, 100, 25);
            form.getContentPane().add(fieldBathrooms);
               
            JTextField fieldAreaSite = new JTextField();
            fieldAreaSite.setBounds(300, 50, 100, 25);
            form.getContentPane().add(fieldAreaSite);
            
            JTextField fieldLivingSpace = new JTextField();
            fieldLivingSpace.setBounds(300, 75, 100, 25);
            form.getContentPane().add(fieldLivingSpace);
            
            JTextField fieldGarages = new JTextField();
            fieldGarages.setBounds(300, 100, 100, 25);
            form.getContentPane().add(fieldGarages);
            
            JTextField fieldRooms = new JTextField();
            fieldRooms.setBounds(300, 125, 100, 25);
            form.getContentPane().add(fieldRooms);
            
            JTextField fieldBedrooms = new JTextField();
            fieldBedrooms.setBounds(300, 150, 100, 25);
            form.getContentPane().add(fieldBedrooms);
            
            JTextField fieldAge = new JTextField();
            fieldAge.setBounds(300, 175, 100, 25);
            form.getContentPane().add(fieldAge);
            
            
            form.setVisible(true);
//            boolean more = true;
//            JOptionPane option = new JOptionPane();
//            while (more) {
//                option.showInputDialog(null, "it worked", "information", JOptionPane.INFORMATION_MESSAGE);
//                if (option.CLOSED_OPTION == -1) {
//                    more = false;
//                }
//            }
//            System.out.println("It worked again");
        }
    }

    private void model() {

        dependent = new ArrayList<Float>();
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void view() {
        Font fnt = new Font("Times New Roman", Font.PLAIN, 24);
        JFrame mainMenu = new JFrame();
        JMenuBar menuBar = new JMenuBar();
        JMenu inputData = new JMenu("Input data");

        JMenuItem addThroughKeyboard = new JMenuItem();
        JMenuItem addThroughFile = new JMenuItem();

        inputData.setFont(fnt);

        addThroughKeyboard.setText("Enter data through keyboard");
        addThroughKeyboard.setFont(fnt);
        addThroughKeyboard.setActionCommand("keyboard");
        addThroughKeyboard.addActionListener(this);
        inputData.add(addThroughKeyboard);

        addThroughFile.setText("Read in a file");
        addThroughFile.setFont(fnt);
        inputData.add(addThroughFile);

        mainMenu.setJMenuBar(menuBar);
        menuBar.add(inputData);
        mainMenu.setExtendedState(JFrame.MAXIMIZED_BOTH);

        mainMenu.setTitle("Coursework");
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainMenu.setVisible(true);
        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void controller() {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

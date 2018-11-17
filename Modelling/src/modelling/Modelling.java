package modelling;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @author ms8794c
 * @author rn6706a
 */
public class Modelling implements ActionListener {

    private JFrame mainMenu;
    private KeyboardInputJFrame form;
    private JComboBox xChosen;
    private ChartPanel chartDisplay;

    private ArrayList<Float> priceY;
    private ArrayList<Float> noOfBathroomsX1;
    private ArrayList<Float> siteAreaX2;
    private ArrayList<Float> livingSpaceX3;
    private ArrayList<Float> noOfGaragesX4;
    private ArrayList<Float> noOfRoomsX5;
    private ArrayList<Float> noOfBedroomsX6;
    private ArrayList<Float> ageX7;

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
            try {
                readDataFromFile();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Modelling.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Called if we want to plot a scatter chart.
        if(e.getActionCommand().equals("ScatterChart")){
            //For now just a random data set to plot a line chart (not a scatter chart yet.)
            XYDataset ds = createDataset();
            
            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesLinesVisible(0, false);
            renderer.setSeriesShapesVisible(1, true);
            
            JFreeChart chart
                    = ChartFactory.createXYLineChart("Test Chart",
                            "x", "y", ds, PlotOrientation.VERTICAL, true, true,
                            false);
            XYPlot plot = (XYPlot)chart.getPlot();
            plot.setRenderer(renderer);

            chartDisplay.setChart(chart);
            mainMenu.setVisible(true);
        }
    }

    private void model() {
        priceY = new ArrayList<>();
        noOfBathroomsX1 = new ArrayList<>();
        siteAreaX2 = new ArrayList<>();
        livingSpaceX3 = new ArrayList<>();
        noOfGaragesX4 = new ArrayList<>();
        noOfRoomsX5 = new ArrayList<>();
        noOfBedroomsX6 = new ArrayList<>();
        ageX7 = new ArrayList<>();
    }

    // GUI initialisation
    private void view() {
        Font fnt = new Font("Times New Roman", Font.PLAIN, 24);

        mainMenu = new JFrame();
        xChosen = new JComboBox();
        JMenuBar menuBar = new JMenuBar();
        JMenu inputMenu = new JMenu("Input data");
        JMenu chartMenu = new JMenu("Choose a chart");
        JMenuItem addThroughKeyboard = new JMenuItem();
        JMenuItem addThroughFile = new JMenuItem();
        JMenuItem plotScatterChart = new JMenuItem();

        inputMenu.setFont(fnt);
        chartMenu.setFont(fnt);

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
        
        plotScatterChart.setText("Plot a scatter chart");
        plotScatterChart.setFont(fnt);
        plotScatterChart.setActionCommand("ScatterChart");
        plotScatterChart.addActionListener(this);
        chartMenu.add(plotScatterChart);

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
        
        JFreeChart freeChart  = ChartFactory.createXYLineChart("Test Chart",
                            "x", "y", new DefaultXYDataset(), PlotOrientation.VERTICAL, true, true,
                            false);
        chartDisplay = new ChartPanel(freeChart);

        mainMenu.setJMenuBar(menuBar);
        menuBar.add(inputMenu);
        menuBar.add(chartMenu);
        menuBar.add(xChosen);
        mainMenu.add(chartDisplay);
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
        noOfGaragesX4.add((float)Integer.parseInt(form.getFieldGarages().getText()));
        noOfRoomsX5.add((float)Integer.parseInt(form.getFieldRooms().getText()));
        noOfBedroomsX6.add((float)Integer.parseInt(form.getFieldBedrooms().getText()));
        ageX7.add((float)Integer.parseInt(form.getFieldAge().getText()));
        priceY.add(Float.parseFloat(form.getFieldPrice().getText()));
    }

    public void setForm(KeyboardInputJFrame form) {
        this.form = form;
    }
    
    //Creation of a random data set
    private XYDataset createDataset() {

        DefaultXYDataset ds = new DefaultXYDataset();
        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series = new XYSeries("Series1");
        for(int i = 0; i < ageX7.size()-1 ;i++){
            series.add(ageX7.get(i), priceY.get(i));
        }
        double[][] data = {{0.1, 0.2, 0.3}, {1, 2, 3}};
        
        double[][] data1 = {{0.1, 0.3}, {2, 4}};

        dataset.addSeries(series);
        
        ds.addSeries("series2", data1);

        return dataset;
    }
    
    private void readDataFromFile() throws FileNotFoundException {
         //Reads and stores the text file into an array list
        Scanner scan = new Scanner(new File("Training Data Set.txt"));
        ArrayList<String> list = new ArrayList();
        while (scan.hasNext()) {
            list.add(scan.nextLine());
        }
       // iterative process to store each column into an arrary
        for (int i = 0; i < list.size()-1; i++) {
            String[] tmp = new String[8];
            tmp = list.get(i).split("\t");
            priceY.add(Float.parseFloat(tmp[1]));
            noOfBathroomsX1.add(Float.parseFloat(tmp[2]));
            siteAreaX2.add(Float.parseFloat(tmp[3]));
            livingSpaceX3.add(Float.parseFloat(tmp[4]));
            noOfGaragesX4.add((float)Integer.parseInt(tmp[5]));
            noOfRoomsX5.add((float)Integer.parseInt(tmp[6]));
            noOfBedroomsX6.add((float)Integer.parseInt(tmp[7]));
            ageX7.add((float)Integer.parseInt(tmp[8]));
            System.out.println(ageX7.get(i));
        }
     }
}

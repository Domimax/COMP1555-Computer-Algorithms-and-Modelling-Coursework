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
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * The main Modelling class for the coursework provides a simple GUI to plot
 * scatter charts and the their respective regression lines.
 *
 * @author Maks Domas Smirnov, ID: ms8749c
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
                JOptionPane.showMessageDialog(null, "The file has not been found.");
            }
        }
        //Called if we want to plot a scatter chart.
        if (e.getActionCommand().equals("ScatterChart")) {
            // Check if there is any data at all
            if (!priceY.isEmpty()) {
                XYDataset ds = createDataset(xChosen.getSelectedIndex());
                XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                renderer.setSeriesLinesVisible(0, false);
                JFreeChart chart
                        = ChartFactory.createXYLineChart("Your Chart",
                                "x", "y", ds, PlotOrientation.VERTICAL, true, true,
                                false);
                XYPlot plot = (XYPlot) chart.getPlot();
                plot.setRenderer(renderer);
                chartDisplay.setChart(chart);
                mainMenu.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Please add data through keyboard or read in a file first.");
            }
        }
        //Called if we want to plot a scatter chart with a regression line.
        if (e.getActionCommand().equals("ScatterRegressionChart")) {
            // Check if there is any data at all
            if (!priceY.isEmpty()) {
                XYDataset ds = createDatasetRegression(xChosen.getSelectedIndex());
                XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                renderer.setSeriesLinesVisible(0, false);
                renderer.setSeriesShapesVisible(1, false);
                JFreeChart chart
                        = ChartFactory.createXYLineChart("Your Chart",
                                "x", "y", ds, PlotOrientation.VERTICAL, true, true,
                                false);
                XYPlot plot = (XYPlot) chart.getPlot();
                plot.setRenderer(renderer);

                chartDisplay.setChart(chart);
                mainMenu.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Please add data through keyboard or read in a file first.");
            }
        }
    }

    // Initialisation of the ArrayLists
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
        JMenuItem plotScatterRegressionChart = new JMenuItem();

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

        plotScatterRegressionChart.setText("Plot a scatter chart with a regression line");
        plotScatterRegressionChart.setFont(fnt);
        plotScatterRegressionChart.setActionCommand("ScatterRegressionChart");
        plotScatterRegressionChart.addActionListener(this);
        chartMenu.add(plotScatterRegressionChart);

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

        JFreeChart emptyChart = ChartFactory.createXYLineChart("Your Chart",
                "x", "y", null, PlotOrientation.VERTICAL, true, true,
                false);
        chartDisplay = new ChartPanel(emptyChart);

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

    // Method is used to store data inputted through the keyboard into the corresponding ArrayLists
    public void storeKeyboardInputData() {
        noOfBathroomsX1.add(Float.parseFloat(form.getFieldBathrooms().getText()));
        siteAreaX2.add(Float.parseFloat(form.getFieldAreaSite().getText()));
        livingSpaceX3.add(Float.parseFloat(form.getFieldLivingSpace().getText()));
        noOfGaragesX4.add((float) Integer.parseInt(form.getFieldGarages().getText()));
        noOfRoomsX5.add((float) Integer.parseInt(form.getFieldRooms().getText()));
        noOfBedroomsX6.add((float) Integer.parseInt(form.getFieldBedrooms().getText()));
        ageX7.add((float) Integer.parseInt(form.getFieldAge().getText()));
        priceY.add(Float.parseFloat(form.getFieldPrice().getText()));
    }

    public void setForm(KeyboardInputJFrame form) {
        this.form = form;
    }

    //Creation of a data set for the chart
    private XYDataset createDataset(int chosenXID) {
        // This ArrayList will store a reference to the ArrayList which holds the
        // selected independant variables and will not be modified, only read.
        ArrayList<Float> chosen = null;
        // A switch statement to determine which independant variable was selected
        // from the JComboBox to create a chart.
        switch (chosenXID) {
            case 0:
                JOptionPane.showMessageDialog(null, "Please select an independent variable above to continue.");
                break;
            case 1:
                chosen = noOfBathroomsX1;
                break;
            case 2:
                chosen = siteAreaX2;
                break;
            case 3:
                chosen = livingSpaceX3;
                break;
            case 4:
                chosen = noOfGaragesX4;
                break;
            case 5:
                chosen = noOfRoomsX5;
                break;
            case 6:
                chosen = noOfBedroomsX6;
                break;
            case 7:
                chosen = ageX7;
                break;
        }
        // Determine if an independant variable is selected. If not, a null is
        // returned instead of a dataset and the graph stays either empty 
        // if no chart has been created before or the last correctly created 
        // chart is going to be displayed in the graph.
        if (chosen != null) {
            XYSeriesCollection dataset = new XYSeriesCollection();
            // Create a series for the display of scatter points and a series
            // to display a regression line.
            XYSeries series1 = new XYSeries(xChosen.getSelectedItem().toString());
            for (int i = 0; i < chosen.size(); i++) {
                series1.add(chosen.get(i), priceY.get(i));
            }
            dataset.addSeries(series1);
            return dataset;
        } else {
            return null;
        }
    }
    
    //Creation of a data set for the chart
    private XYDataset createDatasetRegression(int chosenXID) {
        // This ArrayList will store a reference to the ArrayList which holds the
        // selected independant variables and will not be modified, only read.
        ArrayList<Float> chosen = null;
        // A switch statement to determine which independant variable was selected
        // from the JComboBox to create a chart.
        switch (chosenXID) {
            case 0:
                JOptionPane.showMessageDialog(null, "Please select an independent variable above to continue.");
                break;
            case 1:
                chosen = noOfBathroomsX1;
                break;
            case 2:
                chosen = siteAreaX2;
                break;
            case 3:
                chosen = livingSpaceX3;
                break;
            case 4:
                chosen = noOfGaragesX4;
                break;
            case 5:
                chosen = noOfRoomsX5;
                break;
            case 6:
                chosen = noOfBedroomsX6;
                break;
            case 7:
                chosen = ageX7;
                break;
        }
        // Determine if an independant variable is selected. If not, a null is
        // returned instead of a dataset and the graph stays either empty 
        // if no chart has been created before or the last correctly created 
        // chart is going to be displayed in the graph.
        if (chosen != null) {
            XYSeriesCollection dataset = new XYSeriesCollection();
            // Create a series for the display of scatter points and a series
            // to display a regression line.
            XYSeries series1 = new XYSeries(xChosen.getSelectedItem().toString());
            XYSeries regression = new XYSeries("Regression line");
            float largestX = 0;
            for (int i = 0; i < chosen.size(); i++) {
                series1.add(chosen.get(i), priceY.get(i));
                if (chosen.get(i) > largestX) {
                    largestX = chosen.get(i);
                }
            }
            RegressionAlgorithm alg = new RegressionAlgorithm(chosen, priceY);
            float y1 = alg.getBeta1() * chosen.get(0) + alg.getBeta0();
            float y2 = alg.getBeta1() * chosen.get(chosen.size() - 1) + alg.getBeta0();
            regression.add(0, y1);
            regression.add(largestX + 2, y2);
            dataset.addSeries(series1);
            dataset.addSeries(regression);
            return dataset;
        } else {
            return null;
        }
    }
    
    //The method reads in data from a provided .txt file
    private void readDataFromFile() throws FileNotFoundException {
        //Read and store the lines into an ArrayList
        Scanner scan = new Scanner(new File("Training Data Set.txt"));
        ArrayList<String> list = new ArrayList();
        while (scan.hasNext()) {
            list.add(scan.nextLine());
        }
        // Iterative process to store each peace of data into corresponding ArrayLists
        for (int i = 0; i < list.size(); i++) {
            String[] tmp = new String[8];
            tmp = list.get(i).split("\t");
            priceY.add(Float.parseFloat(tmp[1]));
            noOfBathroomsX1.add(Float.parseFloat(tmp[2]));
            siteAreaX2.add(Float.parseFloat(tmp[3]));
            livingSpaceX3.add(Float.parseFloat(tmp[4]));
            noOfGaragesX4.add((float) Integer.parseInt(tmp[5]));
            noOfRoomsX5.add((float) Integer.parseInt(tmp[6]));
            noOfBedroomsX6.add((float) Integer.parseInt(tmp[7]));
            ageX7.add((float) Integer.parseInt(tmp[8]));
        }
    }
}

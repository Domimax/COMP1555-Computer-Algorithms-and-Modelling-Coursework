package modelling;

import java.awt.Dimension;
import javax.swing.*;
import java.awt.Font;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

/**
 * The main Modelling class for the coursework provides a simple GUI to plot
 * scatter charts and the their respective regression lines.
 *
 * @author Maks Domas Smirnov, ID: ms8749c
 * @author Roshaan Nazir, ID: rn6706a
 */
public class Modelling implements ActionListener {

    private JFrame mainMenu;
    private KeyboardInputJFrame form;
    private TablesJFrame tables;
    private JComboBox xChosen;
    private ChartPanel chartDisplay;
    private float[][] test;

    private ArrayList<Property> properties;
    private ArrayList<Property> comparisonProperties;

    public static void main(String[] args) {
        Modelling application = new Modelling();
    }

    public Modelling() {
        model();
        view();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Called if we want to manually add data about a property via keyboard
        if (e.getActionCommand().equals("Keyboard")) {
            setForm(new KeyboardInputJFrame(this));
        }
        //Called if we want to add data about a property via an external file
        if (e.getActionCommand().equals("File")) {
            try {
                readDataFromFile();
                JOptionPane.showMessageDialog(null, "\"Training data set.txt\" has been successfully inputted!");
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "The file has not been found.");
            }
        }
        //Called if we want to add comparison data
        if (e.getActionCommand().equals("Comparison")) {
            String[] options = {"Town A", "Town B", "Town C", "Other?"};
            int choice = JOptionPane.showOptionDialog(null, ("Select a town"),
                    "Select a town", 0, 3, null, options, null);
            if (choice == 0) {
                try {
                    readDataFromFile("Town A");
                    JOptionPane.showMessageDialog(null, "Town A.txt successfully added");
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "File not Found");
                }
            } else if (choice == 1) {
                try {
                    readDataFromFile("Town B");
                    JOptionPane.showMessageDialog(null, "Town B.txt successfully added");
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "File not Found");
                }
            } else if (choice == 2) {
                try {
                    readDataFromFile("Town C");
                    JOptionPane.showMessageDialog(null, "Town C.txt successfully added");
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "File not Found");
                }
            } else if (choice == 3) {
                String input = JOptionPane.showInputDialog("Enter the name of the file");
                if (input == null || input == "") {
                    JOptionPane.showMessageDialog(null, "Enter the name of the file");
                } else {
                    try {
                        readDataFromFile(input);
                        JOptionPane.showMessageDialog(null, input + ".txt successfully added");
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "File not Found");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "File not Found");
            }
        }
        //Called when we want to view the data tables
        if (e.getActionCommand().equals("Table")) {
            if (tables != null) {
                JOptionPane.showMessageDialog(null, "Figures are given to an accuracy "
                        + "of 6 decimal places. Trailing zeroes are disregarded");
                tables.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Please make sure you created a chart.");
            }
        }
        //Called if we want to plot a scatter chart.
        if (e.getActionCommand().equals("ScatterChart")) {
            // Check if there is any data at all
            if (!properties.isEmpty()) {
                XYSeriesCollection ds = createDataset(xChosen.getSelectedIndex());
                if (ds != null) {
                    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                    renderer.setSeriesLinesVisible(0, false);
                    renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
                    JFreeChart chart
                            = ChartFactory.createXYLineChart("Your Chart",
                                    xChosen.getSelectedItem().toString(), "Price (£100,000's)", ds, PlotOrientation.VERTICAL, true, true,
                                    false);
                    XYPlot plot = (XYPlot) chart.getPlot();
                    plot.getDomainAxis().setLowerBound(0);
                    plot.setRenderer(renderer);
                    chartDisplay.setChart(chart);
                    mainMenu.setVisible(true);
                    tables = new TablesJFrame(this);
                    tables.createTables(xChosen.getSelectedIndex());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please add data through keyboard or read in a file first.");
            }
        }
        //Called if we want to plot a scatter chart with a regression line.
        if (e.getActionCommand().equals("RegressionChart")) {
            // Check if there is any data at all
            if (!properties.isEmpty()) {
                XYSeriesCollection ds = createDatasetRegression(xChosen.getSelectedIndex());
                if (ds != null) {
                    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                    renderer.setSeriesLinesVisible(0, false);
                    renderer.setSeriesShapesVisible(1, false);
                    renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
                    JFreeChart chart
                            = ChartFactory.createXYLineChart("Your Chart",
                                    xChosen.getSelectedItem().toString(), "Price (£100,000's)", ds, PlotOrientation.VERTICAL, true, true,
                                    false);
                    XYPlot plot = (XYPlot) chart.getPlot();
                    plot.getDomainAxis().setLowerBound(0);
                    plot.setRenderer(renderer);
                    chartDisplay.setChart(chart);
                    mainMenu.setVisible(true);
                    tables = new TablesJFrame(this);
                    tables.createTables(xChosen.getSelectedIndex());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please add data through keyboard or read in a file first.");
            }
        }
        //Called if we want to plot a scatter chart with a regression line and a prediction.
        if (e.getActionCommand().equals("PredictionChart")) {
            float value = -1;
            // Check if there is any data at all
            if (!properties.isEmpty()) {
                try {
                    value = Float.parseFloat(JOptionPane.showInputDialog(null, "Input a value for prediction."));
                    // Check if the value is positive, if not throw an exception.
                    if (value <= 0) {
                        throw new Exception();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Please input a positive number.");
                }
                // Check if the value is a postive number.
                if (value > 0) {
                    XYSeriesCollection ds = createDatasetPrediction(xChosen.getSelectedIndex(), value);
                    if (ds != null) {
                        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                        renderer.setSeriesLinesVisible(0, false);
                        renderer.setSeriesShapesVisible(1, false);
                        renderer.setSeriesLinesVisible(2, false);
                        renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
                        JFreeChart chart
                                = ChartFactory.createXYLineChart("Your Chart",
                                        xChosen.getSelectedItem().toString(), "Price (£100,000's)", ds, PlotOrientation.VERTICAL, true, true,
                                        false);
                        XYPlot plot = (XYPlot) chart.getPlot();
                        plot.getDomainAxis().setLowerBound(0);
                        plot.setRenderer(renderer);
                        chartDisplay.setChart(chart);
                        mainMenu.setVisible(true);
                        tables = new TablesJFrame(this);
                        tables.createTables(xChosen.getSelectedIndex());

                        JOptionPane.showMessageDialog(null, "The predicted price is £"
                                + Float.toString((float) Math.ceil(ds.getSeries(2).getDataItem(0).getYValue() * 100000)));
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please add data through keyboard or read in a file first.");
            }
        }
        //Called when the user wants to plot comparison data with training data
        if (e.getActionCommand().equals("ComparisonChart")) {
            // Check if there is any data at all
            if (!properties.isEmpty() && !comparisonProperties.isEmpty()) {
                XYSeriesCollection ds = createDatasetComparison(xChosen.getSelectedIndex());
                if (ds != null) {
                    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                    renderer.setSeriesLinesVisible(0, false);
                    renderer.setSeriesShapesVisible(1, false);
                    renderer.setSeriesLinesVisible(2, false);
                    renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
                    JFreeChart chart
                            = ChartFactory.createXYLineChart("Your Chart",
                                    xChosen.getSelectedItem().toString(),
                                    "Price (£100,000's)", ds, PlotOrientation.VERTICAL,
                                    true, true, false);
                    Shape point = ShapeUtilities.createDiamond(2);
                    renderer.setSeriesShape(1, point);
                    renderer.setSeriesShape(2, point);
                    XYPlot plot = (XYPlot) chart.getPlot();
                    plot.getDomainAxis().setLowerBound(0);
                    plot.setRenderer(renderer);
                    chartDisplay.setChart(chart);
                    mainMenu.setVisible(true);
                    tables = new TablesJFrame(this);
                    tables.createTablesComparison(xChosen.getSelectedIndex());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please ensure training data and comparison data are inputted.");
            }
        }
        //Called if we want to plot a scatter chart with a regression line and a prediction.
        if (e.getActionCommand().equals("BestMeasure")) {
            // Check if there is any data at all
            if (!properties.isEmpty() && !comparisonProperties.isEmpty()) {
                XYSeriesCollection ds = createDatasetBestMeasure(getHighestCorrelationCoefficient());
                if (ds != null) {
                    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                    renderer.setSeriesLinesVisible(0, false);
                    renderer.setSeriesShapesVisible(1, false);
                    renderer.setSeriesLinesVisible(2, false);
                    renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
                    JFreeChart chart
                            = ChartFactory.createXYLineChart("Your Chart",
                                    xChosen.getItemAt(getHighestCorrelationCoefficient()).toString(), "Price (£100,000's)", ds, PlotOrientation.VERTICAL, true, true,
                                    false);
                    XYPlot plot = (XYPlot) chart.getPlot();
                    plot.getDomainAxis().setLowerBound(0);
                    plot.setRenderer(renderer);
                    chartDisplay.setChart(chart);
                    mainMenu.setVisible(true);
                    tables = new TablesJFrame(this);
                    tables.createTablesComparison(getHighestCorrelationCoefficient());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please ensure training data and comparison data are inputted.");
            }
        }
        //Called if we want to view the correlation coefficients of every independent variable
        if (e.getActionCommand().equals("correlationData")) {
            if (!properties.isEmpty()) {
                getHighestCorrelationCoefficient();
                String text = "";
                for (int i = 0; i < test.length; i++) {
                    text += "Correlation for " + xChosen.getItemAt((int) test[i][0] + 1).toString()
                            + " is: " + test[i][1] + "\r\n";
                }
                JOptionPane.showMessageDialog(null,
                        "The independent variables with the correlation coefficients in ascending order are:\r\n\r\n" + text);
            } else {
                JOptionPane.showMessageDialog(null, "Please ensure you have added the training data");
            }
        }
    }

    // Initialisation of the ArrayLists
    private void model() {
        properties = new ArrayList<>();
        comparisonProperties = new ArrayList<>();
    }

    // GUI initialisation
    private void view() {
        Font fnt = new Font("Times New Roman", Font.PLAIN, 24);

        mainMenu = new JFrame();
        xChosen = new JComboBox();
        JMenuBar menuBar = new JMenuBar();
        JMenu inputMenu = new JMenu("Input data");
        JMenu chartMenu = new JMenu("Choose a chart");
        JMenu viewMenu = new JMenu("View");

        inputMenu.setFont(fnt);
        chartMenu.setFont(fnt);
        viewMenu.setFont(fnt);

        inputMenu.add(makeMenuItem("Enter data through keyboard", fnt, "Keyboard"));
        inputMenu.add(makeMenuItem("Read in training data", fnt, "File"));
        inputMenu.add(makeMenuItem("Read in a comparison data", fnt, "Comparison"));
        chartMenu.add(makeMenuItem("Training data", fnt, "ScatterChart"));
        chartMenu.add(makeMenuItem("Training data with a regression line", fnt, "RegressionChart"));
        chartMenu.add(makeMenuItem("Training data with a regression line and a predicted variable", fnt, "PredictionChart"));
        chartMenu.add(makeMenuItem("Training data with a regression line and comparison data", fnt, "ComparisonChart"));
        chartMenu.add(makeMenuItem("\"Best measure\" training data with a regression line and comparison data", fnt, "BestMeasure"));
        viewMenu.add(makeMenuItem("Data summary tables", fnt, "Table"));
        viewMenu.add(makeMenuItem("View correlation coefficients", fnt, "correlationData"));

        xChosen.setMaximumSize(new Dimension(500, 500));
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
                "Independent variable", "Price (£100,000's)", null, PlotOrientation.VERTICAL, true, true,
                false);
        chartDisplay = new ChartPanel(emptyChart);
        mainMenu.setJMenuBar(menuBar);
        menuBar.add(inputMenu);
        menuBar.add(xChosen);
        menuBar.add(chartMenu);
        menuBar.add(viewMenu);
        mainMenu.add(chartDisplay);
        mainMenu.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainMenu.setTitle("Coursework");
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.setVisible(true);
    }

    //A method to determine which independent variable is selected. Returns an ArrayList
    // of all values of a chosen independent variable.
    private ArrayList<Float> determineIndependentVariable(int chosenXID, ArrayList<Property> properties) {
        // This ArrayList will store all the values of a chosen independent variable.
        ArrayList<Float> chosen = new ArrayList<>();
        // A switch statement to determine which independant variable was selected
        // from the JComboBox.
        switch (chosenXID) {
            case 0:
                chosen = null;
                JOptionPane.showMessageDialog(null, "Please select an independent variable above to continue.");
                break;
            case 1:
                // A simple for loop to extract the required values from all the properties.
                for (int i = 0; i < properties.size(); i++) {
                    chosen.add(properties.get(i).getNoOfBathroomsX1());
                }
                break;
            case 2:
                for (int i = 0; i < properties.size(); i++) {
                    chosen.add(properties.get(i).getSiteAreaX2());
                }
                break;
            case 3:
                for (int i = 0; i < properties.size(); i++) {
                    chosen.add(properties.get(i).getLivingSpaceX3());
                }
                break;
            case 4:
                for (int i = 0; i < properties.size(); i++) {
                    chosen.add(properties.get(i).getNoOfGaragesX4());
                }
                break;
            case 5:
                for (int i = 0; i < properties.size(); i++) {
                    chosen.add(properties.get(i).getNoOfRoomsX5());
                }
                break;
            case 6:
                for (int i = 0; i < properties.size(); i++) {
                    chosen.add(properties.get(i).getNoOfBedroomsX6());
                }
                break;
            case 7:
                for (int i = 0; i < properties.size(); i++) {
                    chosen.add(properties.get(i).getAgeX7());
                }
                break;
        }
        return chosen;
    }

    //Creation of a data set for the chart
    private XYSeriesCollection createDataset(int chosenXID) {
        // An ArrayList to store all the values of an independent variable.
        ArrayList<Float> chosen = determineIndependentVariable(chosenXID, properties);
        // Determine if an independant variable is selected. If not, a null is
        // returned instead of a dataset and the graph stays either empty 
        // if no chart has been created before or the last correctly created 
        // chart is going to be displayed in the graph.
        if (chosen != null) {
            XYSeriesCollection dataset = new XYSeriesCollection();
            // Create a series for the display of scatter points.
            XYSeries scatter = new XYSeries(xChosen.getSelectedItem().toString());
            // A for loop toa dd scatter points to the series.
            for (int i = 0; i < chosen.size(); i++) {
                scatter.add((float) chosen.get(i), (float) properties.get(i).getPriceY());
            }
            dataset.addSeries(scatter);
            return dataset;
        } else {
            return null;
        }
    }

    //Creation of a data set for the chart and the regression line.
    private XYSeriesCollection createDatasetRegression(int chosenXID) {
        // An ArrayList to store all the values of an independent variable.
        ArrayList<Float> chosen = determineIndependentVariable(chosenXID, properties);
        // Determine if an independant variable is selected. If not, a null is
        // returned instead of a dataset and the graph stays either empty 
        // if no chart has been created before or the last correctly created 
        // chart is going to be displayed in the graph.
        if (chosen != null) {
            XYSeriesCollection dataset = new XYSeriesCollection();
            // Create a series for the display of scatter points and a series
            // to display a regression line.
            XYSeries scatter = new XYSeries(xChosen.getSelectedItem().toString());
            XYSeries regression = new XYSeries("Regression line");
            // Two interval points between which the regression line will be drawn.
            float largestX = chosen.get(0);
            float smallestX = chosen.get(0);
            // An ArrayList to hold the prices of all properties. 
            ArrayList<Float> prices = new ArrayList<>();
            // A for loop to extract all the prices from properties and add scatter 
            // points to the series. Also used to find the interval for the regression line.
            for (int i = 0; i < chosen.size(); i++) {
                prices.add(properties.get(i).getPriceY());
                scatter.add((float) chosen.get(i), (float) properties.get(i).getPriceY());
                if (chosen.get(i) > largestX) {
                    largestX = chosen.get(i);
                }
                if (chosen.get(i) < smallestX) {
                    smallestX = chosen.get(i);
                }
            }
            // Performing regression analysis using the two ArrayLists.
            RegressionAlgorithm alg = new RegressionAlgorithm(chosen, prices);
            // Y points for the start and end of the regression line.
            float y1 = alg.getBeta1() * smallestX + alg.getBeta0();
            float y2 = alg.getBeta1() * largestX + alg.getBeta0();
            regression.add(smallestX, y1);
            regression.add(largestX, y2);
            dataset.addSeries(scatter);
            dataset.addSeries(regression);
            return dataset;
        } else {
            return null;
        }
    }

    //Creation of a data set for the chart, the regression line and a prediction
    private XYSeriesCollection createDatasetPrediction(int chosenXID, float value) {
        // An ArrayList to store all the values of an independent variable.
        ArrayList<Float> chosen = determineIndependentVariable(chosenXID, properties);
        // Determine if an independant variable is selected. If not, a null is
        // returned instead of a dataset and the graph stays either empty 
        // if no chart has been created before or the last correctly created 
        // chart is going to be displayed in the graph.
        if (chosen != null) {
            XYSeriesCollection dataset = new XYSeriesCollection();
            // Create a series for the display of scatter points, a series
            // to display a regression line and a series to contain a prediction.
            XYSeries scatter = new XYSeries(xChosen.getSelectedItem().toString());
            XYSeries regression = new XYSeries("Regression line");
            XYSeries prediction = new XYSeries("Predicted variable");
            // Two interval points between which the regression line will be drawn.
            float largestX = chosen.get(0);
            float smallestX = chosen.get(0);
            // An ArrayList to hold the prices of all properties. 
            ArrayList<Float> prices = new ArrayList<>();
            // A for loop to extract all the prices from properties and add scatter 
            // points to the series. Also used to find the interval for the regression line.
            for (int i = 0; i < chosen.size(); i++) {
                prices.add(properties.get(i).getPriceY());
                scatter.add((float) chosen.get(i), (float) properties.get(i).getPriceY());
                if (chosen.get(i) > largestX) {
                    largestX = chosen.get(i);
                }
                if (chosen.get(i) < smallestX) {
                    smallestX = chosen.get(i);
                }
            }
            // Performing regression analysis using the two ArrayLists.
            RegressionAlgorithm alg = new RegressionAlgorithm(chosen, prices);
            // Y points for the start and end of the regression line.
            float y1 = alg.getBeta1() * smallestX + alg.getBeta0();
            float y2 = alg.getBeta1() * largestX + alg.getBeta0();
            // Y point for the prediction.
            float y3 = alg.getBeta1() * value + alg.getBeta0();
            regression.add(smallestX, y1);
            regression.add(largestX, y2);
            prediction.add(value, y3);
            dataset.addSeries(scatter);
            dataset.addSeries(regression);
            dataset.addSeries(prediction);
            return dataset;
        } else {
            return null;
        }
    }

    //Creation of a data set for the chart, the regression line and comparison data
    private XYSeriesCollection createDatasetComparison(int chosenXID) {
        // An ArrayList to store all the values of an independent variable.
        ArrayList<Float> chosen = determineIndependentVariable(chosenXID, properties);
        // An ArrayList to store all the values of an independent variable from the comparison data.
        ArrayList<Float> comparisonChosen = determineIndependentVariable(chosenXID, comparisonProperties);
        // Determine if an independant variable is selected. If not, a null is
        // returned instead of a dataset and the graph stays either empty 
        // if no chart has been created before or the last correctly created 
        // chart is going to be displayed in the graph.
        if (chosen != null && comparisonChosen != null) {
            XYSeriesCollection dataset = new XYSeriesCollection();
            // Create a series for the display of scatter points, a series
            // to display a regression line and a series for the display of comparison scatter points.
            XYSeries scatter = new XYSeries(xChosen.getSelectedItem().toString());
            XYSeries regression = new XYSeries("Regression line");
            XYSeries comparisonScatter = new XYSeries("Comparison set");
            // Two interval points between which the regression line will be drawn.
            float largestX = chosen.get(0);
            float smallestX = chosen.get(0);
            // An ArrayList to hold the prices of all properties. 
            ArrayList<Float> prices = new ArrayList<>();
            // A for loop to extract all the prices from properties and add scatter 
            // points to the series. Also used to find the interval for the regression line.
            for (int i = 0; i < chosen.size(); i++) {
                prices.add(properties.get(i).getPriceY());
                scatter.add((float) chosen.get(i), (float) properties.get(i).getPriceY());
                if (chosen.get(i) > largestX) {
                    largestX = chosen.get(i);
                }
                if (chosen.get(i) < smallestX) {
                    smallestX = chosen.get(i);
                }
            }
            // Performing regression analysis using the two ArrayLists.
            RegressionAlgorithm alg = new RegressionAlgorithm(chosen, prices);
            // Y points for the start and end of the regression line.
            float y1 = alg.getBeta1() * smallestX + alg.getBeta0();
            float y2 = alg.getBeta1() * largestX + alg.getBeta0();
            regression.add(smallestX, y1);
            regression.add(largestX, y2);
            // A for loop to add scatter points to the series.
            for (int i = 0; i < comparisonChosen.size(); i++) {
                comparisonScatter.add((float) comparisonChosen.get(i), (float) comparisonProperties.get(i).getPriceY());
            }
            dataset.addSeries(scatter);
            dataset.addSeries(regression);
            dataset.addSeries(comparisonScatter);
            return dataset;
        } else {
            return null;
        }
    }

    //Creation of a data set for the "best measure " chart, the regression line and a comparison data
    private XYSeriesCollection createDatasetBestMeasure(int chosenXID) {
        // An ArrayList to store all the values of an independent variable.
        ArrayList<Float> chosen = determineIndependentVariable(chosenXID, properties);
        // An ArrayList to store all the values of an independent variable from the comparison data.
        ArrayList<Float> comparisonChosen = determineIndependentVariable(chosenXID, comparisonProperties);
        // Determine if an independant variable is selected. If not, a null is
        // returned instead of a dataset and the graph stays either empty 
        // if no chart has been created before or the last correctly created 
        // chart is going to be displayed in the graph.
        if (chosen != null && comparisonChosen != null) {
            XYSeriesCollection dataset = new XYSeriesCollection();
            // Create a series for the display of scatter points, a series
            // to display a regression line and a series for the comparison scatter points.
            XYSeries scatter = new XYSeries(xChosen.getSelectedItem().toString());
            XYSeries regression = new XYSeries("Regression line");
            XYSeries comparisonScatter = new XYSeries("Comparison set");
            // Two interval points between which the regression line will be drawn.
            float largestX = chosen.get(0);
            float smallestX = chosen.get(0);
            // An ArrayList to hold the prices of all properties. 
            ArrayList<Float> prices = new ArrayList<>();
            // A for loop to extract all the prices from properties and add scatter 
            // points to the series. Also used to find the interval for the regression line.
            for (int i = 0; i < chosen.size(); i++) {
                prices.add(properties.get(i).getPriceY());
                scatter.add((float) chosen.get(i), (float) properties.get(i).getPriceY());
                if (chosen.get(i) > largestX) {
                    largestX = chosen.get(i);
                }
                if (chosen.get(i) < smallestX) {
                    smallestX = chosen.get(i);
                }
            }
            // A for loop to add comparison scatter points to the series. Also used to find the interval for the regression line.
            for (int i = 0; i < comparisonChosen.size(); i++) {
                comparisonScatter.add((float) comparisonChosen.get(i), (float) comparisonProperties.get(i).getPriceY());
                if (comparisonChosen.get(i) > largestX) {
                    largestX = comparisonChosen.get(i);
                }
                if (comparisonChosen.get(i) < smallestX) {
                    smallestX = comparisonChosen.get(i);
                }
            }
            // Performing regression analysis using the two ArrayLists.
            RegressionAlgorithm alg = new RegressionAlgorithm(chosen, prices);
            // Y points for the start and end of the regression line.
            float y1 = alg.getBeta1() * smallestX + alg.getBeta0();
            float y2 = alg.getBeta1() * largestX + alg.getBeta0();
            regression.add(smallestX, y1);
            regression.add(largestX, y2);
            dataset.addSeries(scatter);
            dataset.addSeries(regression);
            dataset.addSeries(comparisonScatter);
            return dataset;
        } else {
            return null;
        }
    }

    //The method reads in training data
    private void readDataFromFile() throws FileNotFoundException {
        //Read and store the lines into an ArrayList
        Scanner scan = new Scanner(new File("Training Data Set.txt"));
        ArrayList<String> list = new ArrayList<>();
        while (scan.hasNext()) {
            list.add(scan.nextLine());
        }
        // Iterative process to store each peace of data.
        for (int i = 0; i < list.size(); i++) {
            String[] tmp = new String[8];
            tmp = list.get(i).split("\t");
            Property newProperty = new Property(Property.getIdCount(), Float.parseFloat(tmp[2]),
                    Float.parseFloat(tmp[3]), Float.parseFloat(tmp[4]),
                    (float) Integer.parseInt(tmp[5]), (float) Integer.parseInt(tmp[6]),
                    (float) Integer.parseInt(tmp[7]), (float) Integer.parseInt(tmp[8]),
                    Float.parseFloat(tmp[1]));
            Property.setIdCount(Property.getIdCount() + 1);
            properties.add(newProperty);

        }
    }

    // The method finds the independent variable with the highest correlation coefficient.
    private int getHighestCorrelationCoefficient() {
        ArrayList<Float> coef = new ArrayList<>();
        ArrayList<Float> price = new ArrayList<>();
        ArrayList<Float> noOfBathrooms = new ArrayList<>();
        ArrayList<Float> siteArea = new ArrayList<>();
        ArrayList<Float> livingSpace = new ArrayList<>();
        ArrayList<Float> noOfGarages = new ArrayList<>();
        ArrayList<Float> noOfRooms = new ArrayList<>();
        ArrayList<Float> noOfBedrooms = new ArrayList<>();
        ArrayList<Float> age = new ArrayList<>();
        for (int i = 0; i < properties.size(); i++) {
            price.add(properties.get(i).getPriceY());
            noOfBathrooms.add(properties.get(i).getNoOfBathroomsX1());
            siteArea.add(properties.get(i).getSiteAreaX2());
            livingSpace.add(properties.get(i).getLivingSpaceX3());
            noOfGarages.add(properties.get(i).getNoOfGaragesX4());
            noOfRooms.add(properties.get(i).getNoOfRoomsX5());
            noOfBedrooms.add(properties.get(i).getNoOfBedroomsX6());
            age.add(properties.get(i).getAgeX7());

        }
        RegressionAlgorithm ra = new RegressionAlgorithm(noOfBathrooms, price);
        coef.add(ra.getR2());
        ra = new RegressionAlgorithm(siteArea, price);
        coef.add(ra.getR2());
        ra = new RegressionAlgorithm(livingSpace, price);
        coef.add(ra.getR2());
        ra = new RegressionAlgorithm(noOfGarages, price);
        coef.add(ra.getR2());
        ra = new RegressionAlgorithm(noOfRooms, price);
        coef.add(ra.getR2());
        ra = new RegressionAlgorithm(noOfBedrooms, price);
        coef.add(ra.getR2());
        ra = new RegressionAlgorithm(age, price);
        coef.add(ra.getR2());
        float[][] coeff = new float[coef.size()][2];
        for (int i = 0;
                i < coef.size();
                i++) {
            coeff[i][0] = (float) i;
            coeff[i][1] = coef.get(i);
        }
        float biggest = 0;
        for (int i = 0;
                i < coef.size();
                i++) {
            for (int j = 1; j < coef.size(); j++) {
                if (coeff[j - 1][1] < coeff[j][1]) {
                    float tempindex = coeff[j - 1][0];
                    coeff[j - 1][0] = coeff[j][0];
                    coeff[j][0] = tempindex;
                    float temp = coeff[j - 1][1];
                    coeff[j - 1][1] = coeff[j][1];
                    coeff[j][1] = temp;
                }
            }
        }
        //Storing the multi-dimensional array 'coeff' in to another 
        //multi-dimensional array called 'test' in order to view correlation coefficients
        test = coeff;
        return (int) coeff[0][0] + 1;
    }

    //The method reads in the chosen comparison data
    private void readDataFromFile(String town) throws FileNotFoundException {
        //Read and store the lines into an ArrayList 
        Scanner scan = new Scanner(new File(town + ".txt"));
        ArrayList<String> list = new ArrayList<>();
        // Clear the ArrayList of old comparison data
        comparisonProperties.clear();
        while (scan.hasNext()) {
            list.add(scan.nextLine());
        }
        // Iterative process to store each peace of data
        for (int i = 0; i < list.size(); i++) {
            String[] tmp = new String[8];
            tmp = list.get(i).split("\t");
            Property newProperty = new Property(i, Float.parseFloat(tmp[2]),
                    Float.parseFloat(tmp[3]), Float.parseFloat(tmp[4]),
                    (float) Integer.parseInt(tmp[5]), (float) Integer.parseInt(tmp[6]),
                    (float) Integer.parseInt(tmp[7]), (float) Integer.parseInt(tmp[8]),
                    Float.parseFloat(tmp[1]));
            comparisonProperties.add(newProperty);
        }
    }

    //Method to create a menuItem
    private JMenuItem makeMenuItem(String name, Font fnt, String actionCommand) {

        JMenuItem menuItem = new JMenuItem();
        menuItem.setText(name);
        menuItem.setFont(fnt);
        menuItem.setActionCommand(actionCommand);
        menuItem.addActionListener(this);

        return menuItem;
    }

    /**
     * @param form the form to set
     */
    public void setForm(KeyboardInputJFrame form) {
        this.form = form;
    }

    /**
     * @return the properties
     */
    public ArrayList<Property> getProperties() {
        return properties;
    }

    /**
     * @return the comparisonProperties
     */
    public ArrayList<Property> getComparisonProperties() {
        return comparisonProperties;
    }
}

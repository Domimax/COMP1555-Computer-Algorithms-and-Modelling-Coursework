package modelling;

import javax.swing.*;
import java.awt.Font;
import java.awt.Shape;
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
import org.jfree.util.ShapeUtilities;

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
                Logger.getLogger(Modelling.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "The file has not been found.");
            }
        }

        if (e.getActionCommand().equals("Comparison")) {
            String[] options = {"Town A", "Town B", "Town C"};
            int choice = JOptionPane.showOptionDialog(null, ("Select a town"),
                    "Select a town", 0, 3, null, options, null);
            if (choice == 0) {
                try {
                    readDataFromFile("Town A");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Modelling.class.getName()).log(Level.SEVERE, null, ex);

                }
            } else if (choice == 1) {
                try {
                    readDataFromFile("Town B");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Modelling.class.getName()).log(Level.SEVERE, null, ex);

                }
            } else if (choice == 2) {
                try {
                    readDataFromFile("Town C");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Modelling.class.getName()).log(Level.SEVERE, null, ex);

                }
            } else {
                JOptionPane.showMessageDialog(null, "File not Found");
            }
        }
        // Table
        if (e.getActionCommand().equals("Table")) {
            if (!properties.isEmpty()) {
                TablesJFrame tables = new TablesJFrame(this, xChosen.getSelectedIndex());
            } else {
                JOptionPane.showMessageDialog(null, "Please add data through keyboard or read in a file first.");
            }
        }
        //Called if we want to plot a scatter chart.
        if (e.getActionCommand().equals("ScatterChart")) {
            // Check if there is any data at all
            if (!properties.isEmpty()) {
                XYDataset ds = createDataset(xChosen.getSelectedIndex());
                if (ds != null) {
                    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                    renderer.setSeriesLinesVisible(0, false);
                    JFreeChart chart
                            = ChartFactory.createXYLineChart("Your Chart",
                                    xChosen.getSelectedItem().toString(), "Price (£100,000's)", ds, PlotOrientation.VERTICAL, true, true,
                                    false);
                    XYPlot plot = (XYPlot) chart.getPlot();
                    plot.setRenderer(renderer);
                    chartDisplay.setChart(chart);
                    mainMenu.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please add data through keyboard or read in a file first.");
            }
        }
        //Called if we want to plot a scatter chart with a regression line.
        if (e.getActionCommand().equals("RegressionChart")) {
            // Check if there is any data at all
            if (!properties.isEmpty()) {
                XYDataset ds = createDatasetRegression(xChosen.getSelectedIndex());
                if (ds != null) {
                    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                    renderer.setSeriesLinesVisible(0, false);
                    renderer.setSeriesShapesVisible(1, false);
                    JFreeChart chart
                            = ChartFactory.createXYLineChart("Your Chart",
                                    xChosen.getSelectedItem().toString(), "Price (£100,000's)", ds, PlotOrientation.VERTICAL, true, true,
                                    false);
                    XYPlot plot = (XYPlot) chart.getPlot();
                    plot.setRenderer(renderer);

                    chartDisplay.setChart(chart);
                    mainMenu.setVisible(true);
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
                // Check if the value is postive.
                if (value > 0) {
                    XYDataset ds = createDatasetPrediction(xChosen.getSelectedIndex(), value);
                    if (ds != null) {
                        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                        renderer.setSeriesLinesVisible(0, false);
                        renderer.setSeriesShapesVisible(1, false);
                        renderer.setSeriesLinesVisible(2, false);
                        JFreeChart chart
                                = ChartFactory.createXYLineChart("Your Chart",
                                        xChosen.getSelectedItem().toString(), "Price (£100,000's)", ds, PlotOrientation.VERTICAL, true, true,
                                        false);
                        XYPlot plot = (XYPlot) chart.getPlot();
                        plot.setRenderer(renderer);

                        chartDisplay.setChart(chart);
                        mainMenu.setVisible(true);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please add data through keyboard or read in a file first.");
            }
        }

        if (e.getActionCommand().equals("ComparisonChart")) {
            // Check if there is any data at all
            if (!properties.isEmpty() && !comparisonProperties.isEmpty()) {
                XYDataset ds = createDatasetComparison(xChosen.getSelectedIndex());
                if (ds != null) {
                    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                    renderer.setSeriesLinesVisible(0, false);
                    renderer.setSeriesShapesVisible(1, false);
                    renderer.setSeriesLinesVisible(2, false);

                    JFreeChart chart
                            = ChartFactory.createXYLineChart("Your Chart",
                                    xChosen.getSelectedItem().toString(),
                                    "Price (£100,000's)", ds, PlotOrientation.VERTICAL,
                                    true, true, false);
                    Shape point = ShapeUtilities.createDiamond(2);
                    renderer.setSeriesShape(1, point);
                    renderer.setSeriesShape(2, point);
                    XYPlot plot = (XYPlot) chart.getPlot();
                    plot.setRenderer(renderer);
                    chartDisplay.setChart(chart);
                    mainMenu.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please ensure training data and comparison data are inputted.");
            }
        }

        //Called if we want to plot a scatter chart with a regression line and a prediction.
        if (e.getActionCommand().equals("BestMeasure")) {
            float value = -1;
            // Check if there is any data at all
            if (!properties.isEmpty() && !comparisonProperties.isEmpty()) {
                try {
                    value = Float.parseFloat(JOptionPane.showInputDialog(null, "Input a value for prediction."));
                    // Check if the value is positive, if not throw an exception.
                    if (value <= 0) {
                        throw new Exception();
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Please input a positive number.");
                }
                // Check if the value is postive.
                if (value > 0) {
                    XYDataset ds = createDatasetBestMeasure(getHighestCorrelationCoefficient(), value);
                    if (ds != null) {
                        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                        renderer.setSeriesLinesVisible(0, false);
                        renderer.setSeriesShapesVisible(1, false);
                        renderer.setSeriesLinesVisible(2, false);
                        renderer.setSeriesLinesVisible(3, false);
                        JFreeChart chart
                                = ChartFactory.createXYLineChart("Your Chart",
                                        xChosen.getItemAt(getHighestCorrelationCoefficient()).toString(), "Price (£100,000's)", ds, PlotOrientation.VERTICAL, true, true,
                                        false);
                        XYPlot plot = (XYPlot) chart.getPlot();
                        plot.setRenderer(renderer);

                        chartDisplay.setChart(chart);
                        mainMenu.setVisible(true);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please ensure training data and comparison data are inputted.");
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
        JMenuItem addThroughKeyboard = new JMenuItem();
        JMenuItem addThroughFile = new JMenuItem();
        JMenuItem addComparisonFile = new JMenuItem();
        JMenuItem plotScatterChart = new JMenuItem();
        JMenuItem plotRegressionChart = new JMenuItem();
        JMenuItem plotPredictionChart = new JMenuItem();
        JMenuItem plotComparisonData = new JMenuItem();
        JMenuItem plotBestMeasure = new JMenuItem();
        JMenuItem tables = new JMenuItem();
        JMenuItem cc = new JMenuItem();
        inputMenu.setFont(fnt);
        chartMenu.setFont(fnt);
        addThroughKeyboard.setText("Enter data through keyboard");
        addThroughKeyboard.setFont(fnt);
        addThroughKeyboard.setActionCommand("Keyboard");
        addThroughKeyboard.addActionListener(this);
        inputMenu.add(addThroughKeyboard);
        addThroughFile.setText("Read in Training data set.txt");
        addThroughFile.setFont(fnt);
        addThroughFile.setActionCommand("File");
        addThroughFile.addActionListener(this);
        inputMenu.add(addThroughFile);
        addComparisonFile.setText("Read in comparison data set");
        addComparisonFile.setFont(fnt);
        addComparisonFile.setActionCommand("Comparison");
        addComparisonFile.addActionListener(this);
        inputMenu.add(addComparisonFile);
        tables.setText("View data tables for a specific independent variable");
        tables.setFont(fnt);
        tables.setActionCommand("Table");
        tables.addActionListener(this);
        inputMenu.add(tables);
        cc.setText("View highest correlation coefficient");
        cc.setFont(fnt);
        cc.setActionCommand("Cc");
        cc.addActionListener(this);
        inputMenu.add(cc);
        plotScatterChart.setText("Training data");
        plotScatterChart.setFont(fnt);
        plotScatterChart.setActionCommand("ScatterChart");
        plotScatterChart.addActionListener(this);
        chartMenu.add(plotScatterChart);
        plotRegressionChart.setText("Training data with a regression line");
        plotRegressionChart.setFont(fnt);
        plotRegressionChart.setActionCommand("RegressionChart");
        plotRegressionChart.addActionListener(this);
        chartMenu.add(plotRegressionChart);
        plotPredictionChart.setText("Training data with a regression line and a predicted variable");
        plotPredictionChart.setFont(fnt);
        plotPredictionChart.setActionCommand("PredictionChart");
        plotPredictionChart.addActionListener(this);
        chartMenu.add(plotPredictionChart);
        plotComparisonData.setText("Training data with a regression line and comparison data");
        plotComparisonData.setFont(fnt);
        plotComparisonData.setActionCommand("ComparisonChart");
        plotComparisonData.addActionListener(this);
        chartMenu.add(plotComparisonData);
        plotBestMeasure.setText("View training data with a \"best measure\" regression line, comparison data and a predicted variable");
        plotBestMeasure.setFont(fnt);
        plotBestMeasure.setActionCommand("BestMeasure");
        plotBestMeasure.addActionListener(this);
        inputMenu.add(plotBestMeasure);
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
        // from the JComboBox to create a chart.
        switch (chosenXID) {
            case 0:
                chosen = null;
                JOptionPane.showMessageDialog(null, "Please select an independent variable above to continue.");
                break;
            case 1:
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
    private XYDataset createDataset(int chosenXID) {
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
            for (int i = 0; i < chosen.size(); i++) {
                scatter.add((float) chosen.get(i), (float) properties.get(i).getPriceY());
            }
            dataset.addSeries(scatter);
            return dataset;
        } else {
            return null;
        }
    }

    //Creation of a data set for the chart and the regression line
    private XYDataset createDatasetRegression(int chosenXID) {
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
            float largestX = chosen.get(0);
            float smallestX = chosen.get(0);
            ArrayList<Float> prices = new ArrayList<>();
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
            RegressionAlgorithm alg = new RegressionAlgorithm(chosen, prices);
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

    //Creation of a data set for the chart, the regression line and aprediction
    private XYDataset createDatasetPrediction(int chosenXID, float value) {
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
            XYSeries prediction = new XYSeries("Predicted variable");
            float largestX = chosen.get(0);
            float smallestX = chosen.get(0);
            ArrayList<Float> prices = new ArrayList<>();
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
            RegressionAlgorithm alg = new RegressionAlgorithm(chosen, prices);
            float y1 = alg.getBeta1() * smallestX + alg.getBeta0();
            float y2 = alg.getBeta1() * largestX + alg.getBeta0();
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

    private XYDataset createDatasetComparison(int chosenXID) {
        ArrayList<Float> chosen = determineIndependentVariable(chosenXID, properties);
        ArrayList<Float> comparisonChosen = determineIndependentVariable(chosenXID, comparisonProperties);
        // Determine if an independant variable is selected. If not, a null is
        // returned instead of a dataset and the graph stays either empty 
        // if no chart has been created before or the last correctly created 
        // chart is going to be displayed in the graph.
        if (chosen != null && comparisonChosen != null) {
            XYSeriesCollection dataset = new XYSeriesCollection();
            // Create a series for the display of scatter points and a series
            // to display a regression line.
            XYSeries scatter = new XYSeries(xChosen.getSelectedItem().toString());
            XYSeries regression = new XYSeries("Regression line");
            XYSeries comparisonScatter = new XYSeries("Comparison set");
            float largestX = chosen.get(0);
            float smallestX = chosen.get(0);
            ArrayList<Float> prices = new ArrayList<>();
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
            RegressionAlgorithm alg = new RegressionAlgorithm(chosen, prices);
            float y1 = alg.getBeta1() * smallestX + alg.getBeta0();
            float y2 = alg.getBeta1() * largestX + alg.getBeta0();
            regression.add(smallestX, y1);
            regression.add(largestX, y2);
            // Create a series for the display of scatter points and a series
            // to display a regression line.
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

    private XYDataset createDatasetBestMeasure(int chosenXID, float value) {
        ArrayList<Float> chosen = determineIndependentVariable(chosenXID, properties);
        ArrayList<Float> comparisonChosen = determineIndependentVariable(chosenXID, comparisonProperties);
        // Determine if an independant variable is selected. If not, a null is
        // returned instead of a dataset and the graph stays either empty 
        // if no chart has been created before or the last correctly created 
        // chart is going to be displayed in the graph.
        if (chosen != null && comparisonChosen != null) {
            XYSeriesCollection dataset = new XYSeriesCollection();
            // Create a series for the display of scatter points and a series
            // to display a regression line.
            XYSeries scatter = new XYSeries(xChosen.getSelectedItem().toString());
            XYSeries regression = new XYSeries("Regression line");
            XYSeries prediction = new XYSeries("Predicted variable");
            XYSeries comparisonScatter = new XYSeries("Comparison set");
            float largestX = chosen.get(0);
            float smallestX = chosen.get(0);
            ArrayList<Float> prices = new ArrayList<>();
            for (int i = 0; i < chosen.size(); i++) {
                prices.add(properties.get(i).getPriceY());
                scatter.add((float) chosen.get(i), (float) properties.get(i).getPriceY());
                if (chosen.get(i) > largestX) {
                    largestX = chosen.get(i);
                }
                if (value > largestX) {
                    largestX = value;
                }
                if (chosen.get(i) < smallestX) {
                    smallestX = chosen.get(i);
                }
                if (value < smallestX) {
                    smallestX = value;
                }
            }
            for (int i = 0; i < comparisonChosen.size(); i++) {
                if (comparisonChosen.get(i) > largestX) {
                    largestX = comparisonChosen.get(i);
                }
                if (comparisonChosen.get(i) < smallestX) {
                    smallestX = comparisonChosen.get(i);
                }
            }
            RegressionAlgorithm alg = new RegressionAlgorithm(chosen, prices);
            float y1 = alg.getBeta1() * smallestX + alg.getBeta0();
            float y2 = alg.getBeta1() * largestX + alg.getBeta0();
            float y3 = alg.getBeta1() * value + alg.getBeta0();
            regression.add(smallestX, y1);
            regression.add(largestX, y2);
            prediction.add(value, y3);
            // Create a series for the display of scatter points and a series
            // to display a regression line.

            for (int i = 0; i < comparisonChosen.size(); i++) {
                comparisonScatter.add((float) comparisonChosen.get(i), (float) comparisonProperties.get(i).getPriceY());
            }
            dataset.addSeries(scatter);
            dataset.addSeries(regression);
            dataset.addSeries(prediction);
            dataset.addSeries(comparisonScatter);
            return dataset;
        } else {
            return null;
        }
    }

    //The method reads in data from a provided .txt file
    private void readDataFromFile() throws FileNotFoundException {
        //Read and store the lines into an ArrayList
        Scanner scan = new Scanner(new File("Training Data Set.txt"));
        ArrayList<String> list = new ArrayList<>();
        while (scan.hasNext()) {
            list.add(scan.nextLine());
        }
        // Iterative process to store each peace of data into corresponding ArrayLists
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
        return (int) coeff[0][0] + 1;
    }

    private void readDataFromFile(String town) throws FileNotFoundException {
        //Read and store the lines into an ArrayList
        Scanner scan = new Scanner(new File(town + ".txt"));
        ArrayList<String> list = new ArrayList<>();
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

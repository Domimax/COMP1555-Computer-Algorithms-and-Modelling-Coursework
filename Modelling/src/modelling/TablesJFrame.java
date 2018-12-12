/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelling;

import java.awt.FlowLayout;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * The class displays tabular data summary in 4 different tables, as required by
 * the coursework specification.
 *
 * @author Roshaan Nazir, ID: rn6706a
 * @author Maks Domas Smirnov, ID: ms8749c
 */
public class TablesJFrame extends JFrame {

    private Modelling modelling;
    private DecimalFormat format;

    public TablesJFrame(Modelling modelling) {
        this.modelling = modelling;
    }

    //creating tables from training data set
    public void createTables(int chosenXID) {
        format = new DecimalFormat("#.######");
        format.setRoundingMode(RoundingMode.CEILING);
        // This ArrayList will store all the values of a chosen independent variable.
        ArrayList<Float> chosen = new ArrayList<>();
        ArrayList<Float> price = new ArrayList<>();
        String name = "";
        // A switch statement to determine which independant variable was selected
        // from the JComboBox to create a chart. For loops to populate the above arraylists
        switch (chosenXID) {
            case 0:
                chosen = null;
                JOptionPane.showMessageDialog(null, "Please select an independent variable above to continue.");
                break;
            case 1:
                for (int i = 0; i < modelling.getProperties().size(); i++) {
                    chosen.add(modelling.getProperties().get(i).getNoOfBathroomsX1());
                    price.add(modelling.getProperties().get(i).getPriceY());
                    name = "No. of bathrooms";
                }
                break;
            case 2:
                for (int i = 0; i < modelling.getProperties().size(); i++) {
                    chosen.add(modelling.getProperties().get(i).getSiteAreaX2());
                    price.add(modelling.getProperties().get(i).getPriceY());
                    name = "Site area (1000's sq feet)";
                }
                break;
            case 3:
                for (int i = 0; i < modelling.getProperties().size(); i++) {
                    chosen.add(modelling.getProperties().get(i).getLivingSpaceX3());
                    price.add(modelling.getProperties().get(i).getPriceY());
                    name = "Living space (1000's sq feet)";
                }
                break;
            case 4:
                for (int i = 0; i < modelling.getProperties().size(); i++) {
                    chosen.add(modelling.getProperties().get(i).getNoOfGaragesX4());
                    price.add(modelling.getProperties().get(i).getPriceY());
                    name = "No. of garages";
                }
                break;
            case 5:
                for (int i = 0; i < modelling.getProperties().size(); i++) {
                    chosen.add(modelling.getProperties().get(i).getNoOfRoomsX5());
                    price.add(modelling.getProperties().get(i).getPriceY());
                    name = "No. of rooms";
                }
                break;
            case 6:
                for (int i = 0; i < modelling.getProperties().size(); i++) {
                    chosen.add(modelling.getProperties().get(i).getNoOfBedroomsX6());
                    price.add(modelling.getProperties().get(i).getPriceY());
                    name = "No. of bedrooms";
                }
                break;
            case 7:
                for (int i = 0; i < modelling.getProperties().size(); i++) {
                    chosen.add(modelling.getProperties().get(i).getAgeX7());
                    price.add(modelling.getProperties().get(i).getPriceY());
                    name = "age (years)";
                }
                break;
        }
        //check if there is any data
        if (chosen != null) {
            RegressionAlgorithm ra = new RegressionAlgorithm();
            //Initialising the first table and populating it with values obtained 
            //from regression and modelling class
            Object columns[] = {" ", "X - " + name, "Y - Price (100000's £)"};
            Object rows[][] = {{"N", format.format(chosen.size()), format.format(price.size())}, {"Mean",
                format.format(ra.mean(chosen)), format.format(ra.mean(price))},
            {"Variance", format.format(ra.variance(chosen)), format.format(ra.variance(price))},
            {"Standard Deviation", format.format(ra.standardDeviation(chosen)),
                format.format(ra.standardDeviation(price))}};
            //Adding the table to the JFrame
            TableModel model = new DefaultTableModel(rows, columns);
            JTable table1 = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table1,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            table1.setSize(table1.getPreferredSize());
            table1.setEnabled(false);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setLayout(new FlowLayout());
            add(scrollPane);
            //Initialising the second table and populating it with values obtained 
            //from regression and modelling class
            Object columns2[] = {" ", " "};
            Object rows2[][] = {{"∑X", format.format(ra.sum(chosen))},
            {"∑X^2", format.format(ra.sumSquares(chosen))},
            {"∑Y", format.format(ra.sum(price))},
            {"∑Y^2", format.format((float) Math.pow(ra.sum(price), 2))},
            {"∑XY", format.format(ra.sumSquares(chosen, price))}};
            //Adding the table to the JFrame
            TableModel model2 = new DefaultTableModel(rows2, columns2);
            JTable table2 = new JTable(model2);
            JScrollPane scrollPane2 = new JScrollPane(table2,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            table2.setSize(table2.getPreferredSize());
            table2.setEnabled(false);
            add(scrollPane2);
            ra = new RegressionAlgorithm(chosen, price);
            //Initialising the third table and populating it with values obtained 
            //from regression and modelling class
            Object columns3[] = {"R", "R^2", "Slope", "Y intercept"};
            Object rows3[][] = {{format.format((float) Math.sqrt(ra.getR2())), format.format(ra.getR2()),
                format.format(ra.getBeta1()), format.format(ra.getBeta0())}};
            //Adding the table to the JFrame
            TableModel model3 = new DefaultTableModel(rows3, columns3);
            JTable table3 = new JTable(model3);
            JScrollPane scrollPane3 = new JScrollPane(table3,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            table3.setSize(table3.getPreferredSize());
            table3.setEnabled(false);
            add(scrollPane3);
            //Initialising the fourth table and populating it with values obtained 
            //from regression and modelling class
            Object columns4[] = {"X - " + name, "Y - Price (100000's £)", "Forecasted Y",
                "Standard error of estimate"};
            Object rows4[][] = new Object[28][4];
            //Multi-dimensional array populated by performing regression algorithm on each item in the 
            //arraylists, then storing those values into the table
            for (int i = 0; i < chosen.size(); i++) {
                rows4[i][0] = format.format(chosen.get(i));
                rows4[i][1] = format.format(price.get(i));
                ra = new RegressionAlgorithm(chosen, price);
                rows4[i][2] = format.format(ra.getBeta1() * chosen.get(i) + ra.getBeta0());
                rows4[i][3] = format.format((Object) Math.sqrt(Math.pow(ra.getBeta1() * chosen.get(i)
                        + ra.getBeta0() - price.get(i), 2)));
            }
            //Adding the table to the JFrame
            TableModel model4 = new DefaultTableModel(rows4, columns4);
            JTable table4 = new JTable(model4);
            JScrollPane scrollPane4 = new JScrollPane(table4,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            table4.setSize(table4.getPreferredSize());
            table4.setEnabled(false);
            add(scrollPane4);
            setLocationRelativeTo(null);
        }
    }
    //creating tables from comparison data set
    public void createTablesComparison(int chosenXID) {
        format = new DecimalFormat("#.######");
        format.setRoundingMode(RoundingMode.CEILING);
        // This ArrayList will store all the values of a chosen independent variable.
        //for the training data
        ArrayList<Float> chosen = new ArrayList<>();
        //This arraylist will store all the values of a chosen independent variable
        //for the comparison data
        ArrayList<Float> comparisonChosen = new ArrayList<>();
        //This arraylist stores
        ArrayList<Float> price = new ArrayList<>();
        String name = "";
        // A switch statement to determine which independant variable was selected
        // from the JComboBox to create a chart. For loops to populate the above arraylists
        switch (chosenXID) {
            case 0:
                chosen = null;
                JOptionPane.showMessageDialog(null, "Please select an independent variable above to continue.");
                break;
            case 1:
                for (int i = 0; i < modelling.getProperties().size(); i++) {
                    chosen.add(modelling.getProperties().get(i).getNoOfBathroomsX1());
                    price.add(modelling.getProperties().get(i).getPriceY());
                    name = "No. of bathrooms";
                }
                for (int i = 0; i < modelling.getComparisonProperties().size(); i++) {
                    comparisonChosen.add(modelling.getComparisonProperties().get(i).getNoOfBathroomsX1());
                }
                break;
            case 2:
                for (int i = 0; i < modelling.getProperties().size(); i++) {
                    chosen.add(modelling.getProperties().get(i).getSiteAreaX2());
                    price.add(modelling.getProperties().get(i).getPriceY());
                    name = "Site area (1000's sq feet)";
                }
                for (int i = 0; i < modelling.getComparisonProperties().size(); i++) {
                    comparisonChosen.add(modelling.getComparisonProperties().get(i).getSiteAreaX2());
                }
                break;
            case 3:
                for (int i = 0; i < modelling.getProperties().size(); i++) {
                    chosen.add(modelling.getProperties().get(i).getLivingSpaceX3());
                    price.add(modelling.getProperties().get(i).getPriceY());
                    name = "Living space (1000's sq feet)";
                }
                for (int i = 0; i < modelling.getComparisonProperties().size(); i++) {
                    comparisonChosen.add(modelling.getComparisonProperties().get(i).getLivingSpaceX3());
                }
                break;
            case 4:
                for (int i = 0; i < modelling.getProperties().size(); i++) {
                    chosen.add(modelling.getProperties().get(i).getNoOfGaragesX4());
                    price.add(modelling.getProperties().get(i).getPriceY());
                    name = "No. of garages";
                }
                for (int i = 0; i < modelling.getComparisonProperties().size(); i++) {
                    comparisonChosen.add(modelling.getComparisonProperties().get(i).getNoOfGaragesX4());
                }
                break;
            case 5:
                for (int i = 0; i < modelling.getProperties().size(); i++) {
                    chosen.add(modelling.getProperties().get(i).getNoOfRoomsX5());
                    price.add(modelling.getProperties().get(i).getPriceY());
                    name = "No. of rooms";
                }
                for (int i = 0; i < modelling.getComparisonProperties().size(); i++) {
                    comparisonChosen.add(modelling.getComparisonProperties().get(i).getNoOfRoomsX5());
                }
                break;
            case 6:
                for (int i = 0; i < modelling.getProperties().size(); i++) {
                    chosen.add(modelling.getProperties().get(i).getNoOfBedroomsX6());
                    price.add(modelling.getProperties().get(i).getPriceY());
                    name = "No. of bedrooms";
                }
                for (int i = 0; i < modelling.getComparisonProperties().size(); i++) {
                    comparisonChosen.add(modelling.getComparisonProperties().get(i).getNoOfBedroomsX6());
                }
                break;
            case 7:
                for (int i = 0; i < modelling.getProperties().size(); i++) {
                    chosen.add(modelling.getProperties().get(i).getAgeX7());
                    price.add(modelling.getProperties().get(i).getPriceY());
                    name = "age (years)";
                }
                for (int i = 0; i < modelling.getComparisonProperties().size(); i++) {
                    comparisonChosen.add(modelling.getComparisonProperties().get(i).getAgeX7());
                }
                break;
        }
        //check if there is any data
        if (chosen != null) {

            RegressionAlgorithm ra = new RegressionAlgorithm();
            //Initialising the first table and populating it with values obtained 
            //from regression and property class
            Object columns[] = {" ", "X - " + name, "Y - Price (100000's £)"};
            Object rows[][] = {{"N", format.format(chosen.size()), format.format(price.size())}, {"Mean",
                format.format(ra.mean(chosen)), format.format(ra.mean(price))},
            {"Variance", format.format(ra.variance(chosen)), format.format(ra.variance(price))},
            {"Standard Deviation", format.format(ra.standardDeviation(chosen)),
                format.format(ra.standardDeviation(price))}};
            //Adding the table to the JFrame
            TableModel model = new DefaultTableModel(rows, columns);
            JTable table1 = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table1,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            table1.setSize(400, 400);
            table1.setEnabled(false);
            setSize(800, 800);
            setLayout(new FlowLayout());
            add(scrollPane);
            //Initialising the second table and populating it with values obtained 
            //from regression and properties class
            Object columns2[] = {" ", " "};
            Object rows2[][] = {{"∑X", format.format(ra.sum(chosen))},
            {"∑X^2", format.format(ra.sumSquares(chosen))},
            {"∑Y", format.format(ra.sum(price))},
            {"∑Y^2", format.format((float) Math.pow(ra.sum(price), 2))},
            {"∑XY", format.format(ra.sumSquares(chosen, price))}};
            //Adding the table to the JFrame
            TableModel model2 = new DefaultTableModel(rows2, columns2);
            JTable table2 = new JTable(model2);
            JScrollPane scrollPane2 = new JScrollPane(table2,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            table2.setSize(400, 400);
            table2.setEnabled(false);
            add(scrollPane2);
            ra = new RegressionAlgorithm(chosen, price);
            //Initialising the third table and populating it with values obtained 
            //from regression and properties class
            Object columns3[] = {"R", "R^2", "Slope", "Y intercept"};
            Object rows3[][] = {{format.format((float) Math.sqrt(ra.getR2())), format.format(ra.getR2()),
                format.format(ra.getBeta1()), format.format(ra.getBeta0())}};
            //Adding the table to the JFrame
            TableModel model3 = new DefaultTableModel(rows3, columns3);
            JTable table3 = new JTable(model3);
            JScrollPane scrollPane3 = new JScrollPane(table3,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            table3.setSize(10, 10);
            table3.setEnabled(false);
            add(scrollPane3);
            //Initialising the fourth table and populating it with values obtained 
            //from regression and properties class
            Object columns4[] = {"X - " + name, "Y - Price (100000's £)", "Forecasted Y",
                "Standard error of estimate"};
            Object rows4[][] = new Object[chosen.size() + 1 + comparisonChosen.size()][4];
            //Multi-dimensional array populated by performing regression algorithm on each item in the 
            //arraylists, then storing those values into the table
            for (int i = 0; i < chosen.size(); i++) {
                rows4[i][0] = format.format(chosen.get(i));
                rows4[i][1] = format.format(modelling.getProperties().get(i).getPriceY());
                rows4[i][2] = format.format(ra.getBeta1() * chosen.get(i) + ra.getBeta0());
                rows4[i][3] = format.format((Object) Math.sqrt(Math.pow(ra.getBeta1() * chosen.get(i)
                        + ra.getBeta0() - modelling.getProperties().get(i).getPriceY(), 2)));
            }
            rows4[chosen.size()][0] = "Comparison X - " + name;
            rows4[chosen.size()][1] = "Comparison Y - Price (100000's £)";
            rows4[chosen.size()][2] = "Forecasted Y";
            rows4[chosen.size()][3] = "Standard error of estimate";

            for (int i = chosen.size() + 1; i < chosen.size() + 1 + comparisonChosen.size(); i++) {
                rows4[i][0] = format.format(comparisonChosen.get(i - (chosen.size() + 1)));
                rows4[i][1] = format.format(modelling.getComparisonProperties().get(i - (chosen.size() + 1)).getPriceY());
                rows4[i][2] = format.format(ra.getBeta1() * comparisonChosen.get(i - (chosen.size() + 1)) + ra.getBeta0());
                rows4[i][3] = format.format((Object) Math.sqrt(Math.pow(ra.getBeta1() * comparisonChosen.get(i - (chosen.size() + 1))
                        + ra.getBeta0() - modelling.getComparisonProperties().get(i - (chosen.size() + 1)).getPriceY(), 2)));
            }
            //Adding the table to the JFrame
            TableModel model4 = new DefaultTableModel(rows4, columns4);
            JTable table4 = new JTable(model4);
            JScrollPane scrollPane4 = new JScrollPane(table4,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            table4.setSize(400, 400);
            table4.setEnabled(false);
            add(scrollPane4);
            setLocationRelativeTo(null);
        }
    }
}
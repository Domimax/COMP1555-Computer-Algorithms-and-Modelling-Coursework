/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelling;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author rn6706a
 */
public class TablesJFrame extends JFrame {

    private Modelling modelling;

    public TablesJFrame(Modelling modelling) {
        this.modelling = modelling;
    }

    public void createTables(int chosenXID) {
        // This ArrayList will store all the values of a chosen independent variable.
        ArrayList<Float> chosen = new ArrayList<>();
        ArrayList<Float> price = new ArrayList<>();
        String name = "";
        // A switch statement to determine which independant variable was selected
        // from the JComboBox to create a chart.
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
        if (chosen != null) {

            RegressionAlgorithm ra = new RegressionAlgorithm();
            Object columns[] = {" ", "X - " + name, "Y - Price (100000's £)"};
            Object rows[][] = {{"N", chosen.size(), price.size()}, {"Mean",
                ra.mean(chosen), ra.mean(price)},
            {"Variance", ra.variance(chosen), ra.variance(price)},
            {"Standard Deviation", ra.standardDeviation(chosen),
                ra.standardDeviation(price)}};

            TableModel model = new DefaultTableModel(rows, columns);
            JTable table1 = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table1,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            table1.setSize(400, 400);

            setSize(1000, 1000);
            setLayout(new BorderLayout());
            add(scrollPane, BorderLayout.WEST);

            Object columns2[] = {" ", " "};
            Object rows2[][] = {{"∑X", ra.sum(chosen)},
            {"∑X^2", ra.sumSquares(chosen)},
            {"∑Y", ra.sum(price)},
            {"∑Y^2", (float) Math.pow(ra.sum(price), 2)},
            {"∑XY", ra.sumSquares(chosen, price)}};

            TableModel model2 = new DefaultTableModel(rows2, columns2);
            JTable table2 = new JTable(model2);
            JScrollPane scrollPane2 = new JScrollPane(table2,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            table2.setSize(400, 400);
            add(scrollPane2, BorderLayout.EAST);
            ra = new RegressionAlgorithm(chosen, price);
            Object columns3[] = {"R", "R^2", "Slope", "Y intercept"};
            Object rows3[][] = {{(float) Math.sqrt(ra.getR2()), ra.getR2(),
                ra.getBeta1(), ra.getBeta0()}};

            TableModel model3 = new DefaultTableModel(rows3, columns3);
            JTable table3 = new JTable(model3);
            JScrollPane scrollPane3 = new JScrollPane(table3,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            table3.setSize(10, 10);
            add(scrollPane3, BorderLayout.SOUTH);

            Object columns4[] = {"X - " + name, "Y - Price (100000's £)", "Forecasted Y",
                "Standard error of estimate"};
            Object rows4[][] = new Object[28][4];

            for (int i = 0; i < chosen.size(); i++) {
                rows4[i][0] = chosen.get(i);
                rows4[i][1] = price.get(i);
                ra = new RegressionAlgorithm(chosen, price);
                rows4[i][2] = ra.getBeta1() * chosen.get(i) + ra.getBeta0();
                rows4[i][3] = (Object) Math.pow(ra.getBeta1() * chosen.get(i)
                        + ra.getBeta0() - price.get(i), 2);

            }
            TableModel model4 = new DefaultTableModel(rows4, columns4);
            JTable table4 = new JTable(model4);
            JScrollPane scrollPane4 = new JScrollPane(table4,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            table4.setSize(400, 400);
            add(scrollPane4, BorderLayout.NORTH);
            setLocationRelativeTo(null);
        }
    }

    public void createTablesComparison(int chosenXID) {
        // This ArrayList will store all the values of a chosen independent variable.
        ArrayList<Float> chosen = new ArrayList<>();
        ArrayList<Float> comparisonChosen = new ArrayList<>();
        ArrayList<Float> price = new ArrayList<>();
        String name = "";
        // A switch statement to determine which independant variable was selected
        // from the JComboBox to create a chart.
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
        if (chosen != null) {

            RegressionAlgorithm ra = new RegressionAlgorithm();
            Object columns[] = {" ", "X - " + name, "Y - Price (100000's £)"};
            Object rows[][] = {{"N", chosen.size(), price.size()}, {"Mean",
                ra.mean(chosen), ra.mean(price)},
            {"Variance", ra.variance(chosen), ra.variance(price)},
            {"Standard Deviation", ra.standardDeviation(chosen),
                ra.standardDeviation(price)}};

            TableModel model = new DefaultTableModel(rows, columns);
            JTable table1 = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table1,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            table1.setSize(400, 400);

            setSize(1000, 1000);
            setLayout(new BorderLayout());
            add(scrollPane, BorderLayout.WEST);

            Object columns2[] = {" ", " "};
            Object rows2[][] = {{"∑X", ra.sum(chosen)},
            {"∑X^2", ra.sumSquares(chosen)},
            {"∑Y", ra.sum(price)},
            {"∑Y^2", (float) Math.pow(ra.sum(price), 2)},
            {"∑XY", ra.sumSquares(chosen, price)}};

            TableModel model2 = new DefaultTableModel(rows2, columns2);
            JTable table2 = new JTable(model2);
            JScrollPane scrollPane2 = new JScrollPane(table2,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            table2.setSize(400, 400);
            add(scrollPane2, BorderLayout.EAST);
            ra = new RegressionAlgorithm(chosen, price);
            Object columns3[] = {"R", "R^2", "Slope", "Y intercept"};
            Object rows3[][] = {{(float) Math.sqrt(ra.getR2()), ra.getR2(),
                ra.getBeta1(), ra.getBeta0()}};

            TableModel model3 = new DefaultTableModel(rows3, columns3);
            JTable table3 = new JTable(model3);
            JScrollPane scrollPane3 = new JScrollPane(table3,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            table3.setSize(10, 10);
            add(scrollPane3, BorderLayout.SOUTH);

            Object columns4[] = {"X - " + name, "Y - Price (100000's £)", "Forecasted Y",
                "Standard error of estimate"};
            Object rows4[][] = new Object[chosen.size() + 1 + comparisonChosen.size()][4];
            for (int i = 0; i < chosen.size(); i++) {
                rows4[i][0] = chosen.get(i);
                rows4[i][1] = modelling.getProperties().get(i).getPriceY();
                rows4[i][2] = ra.getBeta1() * chosen.get(i) + ra.getBeta0();
                rows4[i][3] = (Object) Math.sqrt(Math.pow(ra.getBeta1() * chosen.get(i)
                        + ra.getBeta0() - modelling.getProperties().get(i).getPriceY(), 2));

            }
            rows4[chosen.size()][0] = "Comparison X - " + name;
            rows4[chosen.size()][1] = "Comparison Y - Price (100000's £)";
            rows4[chosen.size()][2] = "Forecasted Y";
            rows4[chosen.size()][3] = "Standard error of estimate";

            for (int i = chosen.size() + 1; i < chosen.size() + 1 + comparisonChosen.size(); i++) {
                rows4[i][0] = comparisonChosen.get(i - (chosen.size() + 1));
                rows4[i][1] = modelling.getComparisonProperties().get(i - (chosen.size() + 1)).getPriceY();
                rows4[i][2] = ra.getBeta1() * comparisonChosen.get(i - (chosen.size() + 1)) + ra.getBeta0();
                rows4[i][3] = (Object) Math.sqrt(Math.pow(ra.getBeta1() * comparisonChosen.get(i - (chosen.size() + 1))
                        + ra.getBeta0() - modelling.getComparisonProperties().get(i - (chosen.size() + 1)).getPriceY(), 2));
            }

            TableModel model4 = new DefaultTableModel(rows4, columns4);
            JTable table4 = new JTable(model4);
            JScrollPane scrollPane4 = new JScrollPane(table4,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            table4.setSize(400, 400);
            add(scrollPane4, BorderLayout.NORTH);
            setLocationRelativeTo(null);
        }
    }
}

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

    public TablesJFrame(Modelling modelling, int chosenXID) {
        this.modelling = modelling;
        createTables(chosenXID);
    }

    private void createTables(int chosenXID) {
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
                chosen = modelling.getNoOfBathroomsX1();
                break;
            case 2:
                chosen = modelling.getSiteAreaX2();
                break;
            case 3:
                chosen = modelling.getLivingSpaceX3();
                break;
            case 4:
                chosen = modelling.getNoOfGaragesX4();
                break;
            case 5:
                chosen = modelling.getNoOfRoomsX5();
                break;
            case 6:
                chosen = modelling.getNoOfBedroomsX6();
                break;
            case 7:
                chosen = modelling.getAgeX7();
                break;
        }
        if (chosen != null) {

            RegressionAlgorithm ra = new RegressionAlgorithm();
            Object columns[] = {" ", "X", "Y"};
            Object rows[][] = {{"N", chosen.size(), modelling.getPriceY().size()}, {"Mean",
                ra.mean(chosen), ra.mean(modelling.getPriceY())},
            {"Variance", ra.variance(chosen), ra.variance(modelling.getPriceY())},
            {"Standard Deviation", ra.standardDeviation(chosen),
                ra.standardDeviation(modelling.getPriceY())}};

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
            {"∑Y", ra.sum(modelling.getPriceY())},
            {"∑Y^2", (float) Math.pow(ra.sum(modelling.getPriceY()), 2)},
            {"∑XY", ra.sumSquares(chosen, modelling.getPriceY())}};

            TableModel model2 = new DefaultTableModel(rows2, columns2);
            JTable table2 = new JTable(model2);
            JScrollPane scrollPane2 = new JScrollPane(table2,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            table2.setSize(400, 400);
            add(scrollPane2, BorderLayout.EAST);
            ra = new RegressionAlgorithm(chosen, modelling.getPriceY());
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

            Object columns4[] = {"X", "Y", "Forecasted Y",
                "Standard error of estimate"};
            Object rows4[][] = new Object[28][4];

            for (int i = 0; i < chosen.size(); i++) {
                rows4[i][0] = chosen.get(i);
                rows4[i][1] = modelling.getPriceY().get(i);
                ra = new RegressionAlgorithm(chosen, modelling.getPriceY());
                rows4[i][2] = ra.getBeta1() * chosen.get(i) + ra.getBeta0();
                rows4[i][3] = (Object) Math.pow(ra.getBeta1() * chosen.get(i)
                        + ra.getBeta0() - modelling.getPriceY().get(i), 2);

            }
            TableModel model4 = new DefaultTableModel(rows4, columns4);
            JTable table4 = new JTable(model4);
            JScrollPane scrollPane4 = new JScrollPane(table4,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            table4.setSize(400, 400);
            add(scrollPane4, BorderLayout.NORTH);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }
}

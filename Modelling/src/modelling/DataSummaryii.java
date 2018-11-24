package modelling;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DataSummaryii extends JPanel{
    public DataSummaryii() {
        
        //coll names
        String[] SumOfColumn = {"∑","",};
        
        //row names
         Object[][] SumOffRow = {
             {"∑X", "∑X2", "∑Y", "∑Y2", "∑XY"}
         };
          final JTable table = new JTable(SumOffRow, SumOfColumn);
        table.setPreferredScrollableViewportSize(new Dimension(800, 150));
        table.setFillsViewportHeight(true);
        
        //Create a scroll pane so you can scroll
        JScrollPane scrollPane = new JScrollPane(table);
 
        //SO the scrollpane is added
        add(scrollPane);
        
    }
    //Codee which create the table needed and shows it
       private static void SumOffGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Data Summary(∑)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane
        DataSummaryii newContentPane = new DataSummaryii();
        newContentPane.setOpaque(true); //content panes must be able to show
        frame.setContentPane(newContentPane);
 
        //Show the windowhe window
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SumOffGUI();
            }
        });
    }
}

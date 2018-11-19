package modelling;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DataSummary extends JPanel {
     
    public DataSummary() {
        super(new GridLayout(1,0));
        //coll name
        String[] DataSummaryColumn = {"Data Summary i","Xi","Y",};
        
        //Data under names
        Object[][] DataSummaryRow = {
        {"N", "1","Data", },
        {"Mean", "Data","Data",  },
        {"Variance", "Data","Data", },
        {"Std.Dev", "Data","Data Data", },
        
        };
 
        final JTable table = new JTable(DataSummaryRow, DataSummaryColumn);
        table.setPreferredScrollableViewportSize(new Dimension(800, 150));
        table.setFillsViewportHeight(true);
 
    
        //Create a scroll pane so you can scroll
        JScrollPane scrollPane = new JScrollPane(table);
 
        //SO the scrollpane is added
        add(scrollPane);
    
}
  
    
     //Codee which create the table needed and shows it
       private static void DataSummaryGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Data Summary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane
        DataSummary newContentPane = new DataSummary();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DataSummaryGUI();
            }
        });
    }
}

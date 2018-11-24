package modelling;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ForcastedValues extends JPanel{
       public ForcastedValues() {
        
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
       private static void ForcastedValues() {
            //Create and set up the window.
           JFrame frame = new JFrame("Forecasted Values");
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           
           ForcastedValues newContentPane = new ForcastedValues();
           newContentPane.setOpaque(true);
           frame.setContentPane(newContentPane); 
           
            frame.pack();
        frame.setVisible(true);
           }
         public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ForcastedValues();
            }
        });
    }   
    
}

    
    


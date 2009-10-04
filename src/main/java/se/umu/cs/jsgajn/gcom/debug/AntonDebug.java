package se.umu.cs.jsgajn.gcom.debug;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class AntonDebug extends JFrame {
    private static final long serialVersionUID = 1L;
    private JList mList;
    private DefaultListModel mListModel;

    public AntonDebug() {
        this.mListModel = new DefaultListModel();
        this.mList = new JList(mListModel);
        this.mList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Show frame
        Dimension frameDim = new Dimension(200, 200);
        this.setPreferredSize(frameDim);
        this.pack();
        this.setVisible(true);
    }
    
    public static void main(String[] args) {
        new AntonDebug();
    }
}

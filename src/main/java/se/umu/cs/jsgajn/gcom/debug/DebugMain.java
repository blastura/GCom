package se.umu.cs.jsgajn.gcom.debug;

import javax.swing.*;

public class DebugMain {

    public static void main(String[] args) {
        JFrame frame = DebugView.create(new DebugController());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}

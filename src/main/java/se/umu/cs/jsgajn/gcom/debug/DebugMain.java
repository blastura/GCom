package se.umu.cs.jsgajn.gcom.debug;

import javax.swing.*;

public class DebugMain {

    public static void main(String[] args) {
    	DebugController controller = new DebugController();
        JFrame frame = DebugView.create(controller, 
        		controller.getDebugModel(), controller.getCurrentContact());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}

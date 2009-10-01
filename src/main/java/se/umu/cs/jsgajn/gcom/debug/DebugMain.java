package se.umu.cs.jsgajn.gcom.debug;

import javax.swing.*;

public class DebugMain {

    public static void main(String[] args) {
    	DebugModel model = new DebugModel();
    	DebugController controller = new DebugController(model);
        JFrame frame = DebugView.create(controller, model);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}

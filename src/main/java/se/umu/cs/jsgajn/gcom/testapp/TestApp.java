package se.umu.cs.jsgajn.gcom.testapp;

import java.awt.Dimension;

import javax.swing.JFrame;

public class TestApp {

    public TestApp() {
        Model model = new Model();
        Controller controller = new Controller(model);
        
        JFrame view = new View(controller, model);
        view.setPreferredSize(new Dimension(500, 250));
        view.setLocationRelativeTo(null);
        view.pack();
        view.setVisible(true);
    }
    
    public static void main(final String[] args) {
        new TestApp();
    }
    
}

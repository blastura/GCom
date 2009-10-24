package se.umu.cs.jsgajn.gcom.testapp;

import javax.swing.JFrame;

public class TestApp {

    public TestApp() {
        Model model = new Model();
        Controller controller = new Controller(model);
        
        JFrame view = new View(controller, model);
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
    
    public static void main(final String[] args) {
        new TestApp();
    }
    
}

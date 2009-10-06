package se.umu.cs.jsgajn.gcom.debug;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.*;

import se.umu.cs.jsgajn.gcom.testapp.ChatMember;

public class DebugMain {

    public static void main(String[] args) {
    	DebugController controller = new DebugController();
        JFrame frame = DebugView.create(controller, 
        		controller.getDebugModel(), controller.getCurrentContact());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        
        
        try {
        	ChatMember chatmember = new ChatMember(args[0], 
        			Integer.parseInt(args[1]), args[2]);
        	System.out.println("heeeeej");
        	chatmember.addDebugger(controller);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
    }    
}

package se.umu.cs.jsgajn.gcom.debug;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import se.umu.cs.jsgajn.gcom.testapp.ChatMember;




public class DebugMain {

    ChatMember chatmember;

    public DebugMain(final String[] args) {
    	ContactModel model = new ContactModel();
        final DebugController controller = new DebugController(model);
        Debugger.setDebugHandler(controller);

        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    JFrame frame = new DebugView(controller,
                                                    controller.getCurrentContact());
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            });

        try {
            if (args.length == 3) 
                DebugMain.this.chatmember = new ChatMember(args[0],
                                                           Integer.parseInt(args[1]),
                                                           args[2]);
            else if (args.length == 4)
                DebugMain.this.chatmember = new ChatMember(args[0],
                                                           Integer.parseInt(args[1]),
                                                           args[2],
                                                           Integer.parseInt(args[3]), null);
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        System.out.println("Started client");
        System.out.println("cm: " + chatmember + ", controller: " + controller);
    }

    public static void main(final String[] args) {
        new DebugMain(args);
    }
}
package se.umu.cs.jsgajn.gcom.debug;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import se.umu.cs.jsgajn.gcom.debug.Debugger;

import javax.swing.*;

import se.umu.cs.jsgajn.gcom.testapp.ChatMember;

public class DebugMain {

    ChatMember chatmember;

    public DebugMain(final String[] args) {
        final DebugController controller = new DebugController();
        Debugger.setDebugHandler(controller);

        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    JFrame frame = DebugView.create(controller,
                                                    controller.getDebugModel(),
                                                    controller.getCurrentContact());
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            });

        try {
            DebugMain.this.chatmember = new ChatMember(args[0],
                                                       Integer.parseInt(args[1]),
                                                       args[2]);
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
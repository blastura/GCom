package se.umu.cs.jsgajn.gcom.debug;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.*;

import se.umu.cs.jsgajn.gcom.testapp.ChatMember;

public class DebugMain {

    ChatMember chatmember;

    public DebugMain(final String[] args) {
        final DebugController controller = new DebugController();

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

        new Thread (new Runnable() {
                public void run() {
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
                }
            }).start();
        System.out.println("heeeeej");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("cm: " + chatmember + ", controller: " + controller);
        chatmember.addDebugger(controller);
    }

    public static void main(final String[] args) {
        new DebugMain(args);
    }
}
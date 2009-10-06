package se.umu.cs.jsgajn.gcom.debug;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.*;

import se.umu.cs.jsgajn.gcom.testapp.ChatMember;

public class DebugMain2 {


	public DebugMain2() {
		DebugController controller = new DebugController();

		JFrame frame = DebugView.create(controller,
				controller.getDebugModel(),
				controller.getCurrentContact());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public static void main(final String[] args) {
		new DebugMain2();
	}
}
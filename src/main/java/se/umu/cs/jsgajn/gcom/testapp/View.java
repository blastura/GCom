package se.umu.cs.jsgajn.gcom.testapp;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.beans.EventHandler;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

public class View extends JFrame {
    
    public View(Controller controller, Model model) {
        
        
        model.setFrame(this);

        JPanel panel = (JPanel) this.getContentPane();
        
        GridBagLayout gridBagLayout0 = new GridBagLayout();
        gridBagLayout0.columnWidths = new int[]{12, 20, 6, 0, 6, 150, 12};
        gridBagLayout0.rowHeights = new int[]{12, 20, 6, 67, 12};
        gridBagLayout0.columnWeights = new double[]{0, 1, 0, 0, 0, 0, 0};
        gridBagLayout0.rowWeights = new double[]{0, 1, 0, 0, 0};
        panel.setLayout(gridBagLayout0);
        
        // Memberlist
        JList members = new JList();
        
        DefaultListModel membersModel = new DefaultListModel();
        members.setModel(membersModel);
        
        JScrollPane membersScrollPane = new JScrollPane(members);
        membersScrollPane.setPreferredSize(new Dimension(23, 23));
        panel.add(membersScrollPane, new GridBagConstraints(5, 1, 1, 3, 0.0, 0.0, 15, 1, new Insets(0, 0, 0, 0), 0, 0));

        model.setMembers(membersModel);
        
        
        // Chatmessages
        JTextArea chatMessages = new JTextArea();
        chatMessages.setText("Welcome");
        chatMessages.setEditable(false);
        
        JScrollPane chatMessagesScrollPane = new JScrollPane(chatMessages);
        chatMessagesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(chatMessagesScrollPane, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0, 13, 1, new Insets(0, 0, 0, 0), 0, 0));
        
        model.setChat(chatMessages);
        
        
        // Write message-box
        JTextArea writeMessage = new JTextArea();

        JScrollPane writeMessageScrollPane = new JScrollPane(writeMessage);
        writeMessageScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(writeMessageScrollPane, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, 15, 1, new Insets(0, 0, 0, 0), 0, 0));
        
        model.setMessage(writeMessage);
        
        
        // Send button
        JButton send = new JButton();
        send.setText("Send");
        
        send.addActionListener(EventHandler.create(ActionListener.class, controller, "send"));
        
        panel.add(send, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, 14, 3, new Insets(0, 0, 0, 0), 0, 0));

        
        
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setBounds(new Rectangle(500, 0, 835, 528));
        

        JFormattedTextField host = new JFormattedTextField();
        JFormattedTextField nick = new JFormattedTextField();
        JFormattedTextField channel = new JFormattedTextField();
        JFormattedTextField hostPort = new JFormattedTextField();
        JFormattedTextField localPort = new JFormattedTextField();
        
        host.setText("localhost");
        nick.setText("Anonymous");
        channel.setText("Dist");
        hostPort.setText("1078");
        localPort.setText("33445");
        
        model.setHost(host);
        model.setNick(nick);
        model.setChannel(channel);
        model.setHostPort(hostPort);
        model.setLocalPort(localPort);
        
        ConnectDialog connectDialog = new ConnectDialog(host, nick, 
                channel, hostPort, localPort);

        JOptionPane.showMessageDialog(this, connectDialog,
                "Connect",JOptionPane.PLAIN_MESSAGE);
        
        controller.init();
    }
   
    


}

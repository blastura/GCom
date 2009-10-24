package se.umu.cs.jsgajn.gcom.testapp;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import javax.swing.JFormattedTextField;


public class ConnectDialog extends JPanel {

    public ConnectDialog(JFormattedTextField host, JFormattedTextField nick,
            JFormattedTextField channel, JFormattedTextField hostPort,
            JFormattedTextField localPort) {

        GridBagLayout gridBagLayout0 = new GridBagLayout();
        gridBagLayout0.columnWidths = new int[]{12, 0, 12, 20, 12, 0, 50, 12};
        gridBagLayout0.rowHeights = new int[]{6, 0, 0, 0, 13};
        gridBagLayout0.columnWeights = new double[]{0, 0, 0, 1, 0, 0, 0, 0};
        gridBagLayout0.rowWeights = new double[]{0, 0, 0, 0, 1};
        this.setLayout(gridBagLayout0);

        localPort.setBounds(new Rectangle(263, 34, 50, 28));
        localPort.setColumns(8);
        this.add(localPort, new GridBagConstraints(6, 2, 1, 1, 0.0, 0.0, 768, 2, new Insets(0, 0, 0, 0), 0, 0));

        JLabel label0 = new JLabel();
        label0.setBounds(new Rectangle(238, 40, 25, 16));
        label0.setHorizontalTextPosition(SwingConstants.CENTER);
        label0.setText("Port");
        this.add(label0, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0, 768, 0, new Insets(0, 0, 0, 0), 0, 0));

        channel.setBounds(new Rectangle(75, 62, 238, 28));
        channel.setColumns(8);
        this.add(channel, new GridBagConstraints(3, 3, 4, 1, 0.0, 0.0, 512, 2, new Insets(0, 0, 0, 0), 0, 0));

        JLabel label1 = new JLabel();
        label1.setBounds(new Rectangle(12, 68, 51, 16));
        label1.setHorizontalTextPosition(SwingConstants.RIGHT);
        label1.setText("Channel");
        this.add(label1, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, 768, 0, new Insets(0, 0, 0, 0), 0, 0));

        nick.setBounds(new Rectangle(75, 34, 151, 28));
        nick.setColumns(8);
        this.add(nick, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, 512, 2, new Insets(0, 0, 0, 0), 0, 0));

        hostPort.setBounds(new Rectangle(263, 6, 50, 28));
        hostPort.setColumns(8);
        this.add(hostPort, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0, 768, 2, new Insets(0, 0, 0, 0), 0, 0));

        host.setBounds(new Rectangle(75, 6, 151, 28));
        host.setColumns(8);
        this.add(host, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, 512, 2, new Insets(0, 0, 0, 0), 0, 0));

        JLabel label2 = new JLabel();
        label2.setBounds(new Rectangle(34, 40, 29, 16));
        label2.setHorizontalTextPosition(SwingConstants.RIGHT);
        label2.setText("Nick");
        this.add(label2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, 768, 0, new Insets(0, 0, 0, 0), 0, 0));

        JLabel label3 = new JLabel();
        label3.setBounds(new Rectangle(238, 12, 25, 16));
        label3.setHorizontalTextPosition(SwingConstants.CENTER);
        label3.setText("Port");
        this.add(label3, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, 768, 0, new Insets(0, 0, 0, 0), 0, 0));

        JLabel label4 = new JLabel();
        label4.setBounds(new Rectangle(25, 12, 38, 16));
        label4.setHorizontalTextPosition(SwingConstants.RIGHT);
        label4.setText("Server");
        this.add(label4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, 768, 0, new Insets(0, 0, 0, 0), 0, 0));
        
    }
}
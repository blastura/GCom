package se.umu.cs.jsgajn.gcom.testapp;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Model {
    
    private JFrame frame;
    private String name;
    private DefaultListModel members;
    private JTextArea chat;
    private JTextArea message;
    private JButton send;
    
    private JFormattedTextField host, hostPort, nick, localPort, channel;
    
    public Model() {

    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JFrame getFrame() {
        return frame;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        frame.setTitle(name);
    }

    public DefaultListModel getMembers() {
        return members;
    }

    public void setMembers(DefaultListModel members) {
        this.members = members;
    }

    public JTextArea getChat() {
        return chat;
    }

    public void setChat(JTextArea chat) {
        this.chat = chat;
    }

    public JTextArea getMessage() {
        return message;
    }

    public void setMessage(JTextArea message) {
        this.message = message;
    }

    public JButton getSend() {
        return send;
    }

    public void setSend(JButton send) {
        this.send = send;
    }

    public JFormattedTextField getHost() {
        return host;
    }

    public void setHost(JFormattedTextField host) {
        this.host = host;
    }

    public JFormattedTextField getHostPort() {
        return hostPort;
    }

    public void setHostPort(JFormattedTextField hostPort) {
        this.hostPort = hostPort;
    }

    public JFormattedTextField getNick() {
        return nick;
    }

    public void setNick(JFormattedTextField nick) {
        this.nick = nick;
    }

    public JFormattedTextField getLocalPort() {
        return localPort;
    }

    public void setLocalPort(JFormattedTextField localPort) {
        this.localPort = localPort;
    }

    public JFormattedTextField getChannel() {
        return channel;
    }

    public void setChannel(JFormattedTextField channel) {
        this.channel = channel;
    }

}

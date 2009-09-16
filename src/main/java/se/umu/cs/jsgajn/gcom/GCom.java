package se.umu.cs.jsgajn.gcom;

import java.rmi.Remote;

public class GCom implements Remote { 
    public GCom() {
        System.out.println("It all starts here?");
    }

    public static void main(String[] args) {
        new GCom();
    }
}

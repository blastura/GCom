package se.umu.cs.jsgajn.gcom.debug;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import processing.core.*;
//import processing.opengl.*;
import se.umu.cs.jsgajn.gcom.Client;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModule;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModuleImpl;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

public class Debugger extends PApplet implements Client {
    private GroupModule groupMember;
    
    public void setup() {
        size(500,500,JAVA2D);
        hint(ENABLE_OPENGL_2X_SMOOTH);
        smooth();
        background(100);
        ellipse(10,10,100,100);

        try {
            this.groupMember = new GroupModuleImpl(this, "itchy", 1099, "hej");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

      }

      public void draw() {
        stroke(255);
        if (mousePressed) {
          line(mouseX,mouseY,pmouseX,pmouseY);
          groupMember.send(new PaintObj(mouseX,mouseY,pmouseX,pmouseY));
        }
      }

    public void deliver(Object message) {
        // TODO Auto-generated method stub
        
    }

}

package se.umu.cs.jsgajn.gcom.groupcommunication;

import se.umu.cs.jsgajn.gcom.Module;
import se.umu.cs.jsgajn.gcom.debug.Debuggable;

public interface CommunicationModule extends Module, Debuggable {
    public Receiver getReceiver();
    public void setOrderingModule(Module m);
    public void setMulticastMethod(Multicast m);
}
package se.umu.cs.jsgajn.gcom.groupcommunication;

import se.umu.cs.jsgajn.gcom.Module;

public interface CommunicationModule extends Module {
    public Receiver getReceiver();
    public void setOrderingModule(Module m);
    public void setMulticastMethod(Multicast m);
}
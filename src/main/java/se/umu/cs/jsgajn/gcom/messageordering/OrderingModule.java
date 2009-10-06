package se.umu.cs.jsgajn.gcom.messageordering;

import se.umu.cs.jsgajn.gcom.Module;

public interface OrderingModule extends Module {
    public void setCommunicationsModule(Module m);
    public void setOrdering(Ordering o);
    public void start();
}
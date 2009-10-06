package se.umu.cs.jsgajn.gcom.messageordering;

import se.umu.cs.jsgajn.gcom.Module;
import se.umu.cs.jsgajn.gcom.debug.Debuggable;

public interface OrderingModule extends Module, Debuggable { 
    public void setCommunicationsModule(Module m);
    public void setOrdering(Ordering o);
    public void start();
}
package se.umu.cs.jsgajn.gcom.ordering;

import se.umu.cs.jsgajn.gcom.Module;

/**
 * Interface for ordering modules.
 * 
 * @author dit06ajn, dit06jsg
 */ 
public interface OrderingModule extends Module {
    
    /**
     * Set an implementation of the module to send outoging messages to.
     * @param m The module to send prepared messages to.
     */
    public void setCommunicationsModule(Module m);
    /**
     * @param o The ordering to use for ordering messages.
     */
    public void setOrdering(Ordering o);
}
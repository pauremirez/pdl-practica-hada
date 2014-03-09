package compiler.semantic.symbol;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;



/**
 * Class for SymbolVariable.
 */

// TODO: Student work
//       Include properties to characterize variables

public class SymbolVariable extends SymbolBase
{  
    private int desplazamiento;
    private boolean referencia;
    private int size;       // Tamanyo para conjuntos
    private int address;
    /**
     * Constructor for SymbolVariable.
     * @param scope The declaration scope.
     * @param name The symbol name.
     * @param type The symbol type.
     */
    public SymbolVariable (ScopeIF scope, 
                           String name,
                           TypeIF type)
    {
        super (scope, name, type);
    }
    public SymbolVariable (ScopeIF scope, 
                           String name,
                           TypeIF type, 
                           int desplazamiento)
    {
        super (scope, name, type);
        this.desplazamiento=desplazamiento;
    }        
    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if two objects has the same properties.
     */
    public boolean equalsVaraible (Object other)
    {
        // TODO: Student Work
        return super.equals(other);
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCodeVariable ()
    {
        // TODO: Student Work
        return super.hashCode();
    }

    public boolean getReferencia() {
        return referencia;
    }

    public void setReferencia(boolean referencia) {
        this.referencia = referencia;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */
    public String toStringVariable()
    {
        // TODO: Student Work (optional)
        return super.toString();
    } 
    
    public int getDesplazamiento() {
        return this.desplazamiento;
    }

    public void setDesplazamiento(int desplazamiento) {
        this.desplazamiento = desplazamiento;
    }
    
	/**
	 * @return the address
	 */
	public int getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(int address) {
		this.address = address;
	}
    
}

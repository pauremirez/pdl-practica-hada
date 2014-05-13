package compiler.semantic.symbol;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Class for SymbolConstant.
 */

public class SymbolConstant extends SymbolBase
{
	private int value;
	int desplazamiento;
    
    /**
     * Constructor for SymbolConstant.
     * @param scope The declaration scope.
     * @param name The symbol name.
     * @param type The symbol type.
     */
    public SymbolConstant (ScopeIF scope, String name, TypeIF type)
    {
        super (scope, name, type);
    } 
    
    public SymbolConstant (ScopeIF scope,
            String name,
            TypeIF type,
            int desplazamiento)
    {
    	super (scope, name, type);
    	this.desplazamiento=desplazamiento;
    }
    
    public void setValue(int value){
    	this.value=value;
    }
    
    public int getValue(){
    	return value;
    }
    
    public int getDesplazamiento() {
        return desplazamiento;
    }

    public void setDesplazamiento(int desplazamiento) {
        this.desplazamiento = desplazamiento;
    }

}

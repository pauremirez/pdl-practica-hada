package compiler.semantic.type;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Class for TypeArray.
 */


public class TypeArray extends TypeBase
{   
	/**
     * Constructor for TypeArray.
     * @param scope The declaration scope.
     */
	private int valIni;
	private int valFin;
	private TypeIF tipo;
	
    public TypeArray (ScopeIF scope)
    {
        super (scope);
    }

    /**
     * Constructor for TypeArray.
     * @param scope The declaration scope.
     * @param name The name of the type.
     */
    public TypeArray (ScopeIF scope, String name)
    {
        super (scope, name);
    }

	public int getValIni() {
	   return valIni;
	}

	public void setValIni(int valIni) {
	   this.valIni = valIni;
	}
	
	public int getValFin() {
	    return valFin;
	}

	public void setValFin(int valFin) {
	   this.valFin = valFin;
	}
	
	public void setTipo(TypeIF tipo){
		this.tipo=tipo;
	}
	
	public TypeIF getTipo(){
		return tipo;
	}
	    
	public int getSize() {
	  return (this.valFin - this.valIni +1);
	}
}

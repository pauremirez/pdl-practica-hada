package compiler.semantic.type;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeBase;

/**
 * Class for TypeSet.
 */

// TODO: Student work
//       Include properties to characterize sets

public class TypeSet extends TypeBase
{
	private int valIni;
	private int valFin;
	    
	public TypeSet (ScopeIF scope){
	    super (scope);
	}

	/**
	* Constructor for TypeSet.
	* @param scope The declaration scope.
	* @param name The name of the type.
	*/
	public TypeSet (ScopeIF scope, String name){
	    super (scope, name);
	}

	public int getValFin() {
	    return valFin;
	}

	public void setValFin(int valFin) {
	   this.valFin = valFin;
	}

	public int getValIni() {
	   return valIni;
	}

	public void setValIni(int valIni) {
	   this.valIni = valIni;
	}
	    
	public int getSize() {
	  return (this.valFin - this.valIni +1);
	}
}

package compiler.semantic.symbol;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Class for SymbolFunction.
 */

// TODO: Student work
//       Include properties to characterize function calls

public class SymbolFunction extends SymbolBase
{
	private SymbolParameter parametros;
  
/**
 * Constructor for SymbolFunction.
 * @param scope The declaration scope.
 * @param name The symbol name.
 * @param type The symbol type.
 */
	public SymbolFunction (ScopeIF scope, 
                       String name,
                       TypeIF type)	{
	    super (scope, name, type);
	}
	
	public SymbolFunction (ScopeIF scope, String name, TypeIF type, SymbolParameter parametros)	{
		super (scope, name, type);
		this.parametros=parametros;
	}

	public SymbolParameter getParametros() {
		return parametros;
	}
	
	public void setParametros(SymbolParameter parametros) {
		this.parametros = parametros;
	}

}

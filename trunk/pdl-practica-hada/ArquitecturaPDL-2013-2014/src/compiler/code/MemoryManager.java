package compiler.code;

import java.util.ArrayList;
import java.util.List;

import compiler.CompilerContext;
import compiler.intermediate.Temporal;
import compiler.semantic.symbol.SymbolParameter;
import compiler.semantic.symbol.SymbolVariable;

import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;

/**
 * gestiona las direcciones de memoria de variables, temporales
 * y par�metros.
 *
 */
public class MemoryManager {

	private static int gAddress = 0;
	private static List<Integer> sizeOfScopes = new ArrayList<Integer>();
	
	/**
	 * @return the gAddress
	 */
	public static int getgAddress() {
		return gAddress;
	}

	/**
	 * @param gAddress the gAddress to set
	 */
	public static void setgAddress(int gAddress) {
		MemoryManager.gAddress = gAddress;
	}

	public static void assignAddresses()
    {
    	int lOffset = 0;  		
 		int parameterOffset = 0; 
 		 List<ScopeIF> scopes = CompilerContext.getScopeManager().getAllScopes ();
 		 for (ScopeIF scope: scopes) {
           List<SymbolIF> symbols = scope.getSymbolTable ().getSymbols();
           for (SymbolIF s: symbols) {
               if (s instanceof SymbolVariable){
                   SymbolVariable symbol = (SymbolVariable)s;
                   if (scope.getLevel () == 0)
                   {
                       symbol.setAddress(gAddress);
                       gAddress += symbol.getType().getSize();
                   }else {
                	   symbol.setAddress(lOffset + symbol.getType().getSize());
                	   lOffset += symbol.getType().getSize();
                   }
               }
               else if(s instanceof SymbolParameter) {
            	   SymbolParameter symbol = (SymbolParameter) s;
                   symbol.setAddress(parameterOffset + symbol.getType().getSize());
                   parameterOffset += symbol.getType().getSize();
               }
           }
           List<TemporalIF> temporals = scope.getTemporalTable().getTemporals();
           for (TemporalIF t: temporals)
           {
        	   Temporal temp = (Temporal)t;
               t.setAddress (lOffset + temp.getSize());
               lOffset += temp.getSize();                   
           } 
           //Registrar el tama�o que ocupan las variables y temporales en cada ambito.
           sizeOfScopes.add(lOffset);
           lOffset = 0;
           parameterOffset = 0;
 		 }
    }
	
	/**
	 * Si el nivel del �mbito es mayor a los registrados por MemoryManager,
	 * devuelve el tama�o del �ltimo�mbito registrado, ya que se trata de una 
	 * llamada recursiva.
	 * @param levelOfScope
	 * @return
	 */
	public static int getSizeOfScope(int levelOfScope)
	{
		//El n�mero de elementos de sizeOfScopes es el n�mero de �mbitos
		// registrados.
		if(levelOfScope >= sizeOfScopes.size())
		{
			return sizeOfScopes.get(sizeOfScopes.size()-1);
		}
		return sizeOfScopes.get(levelOfScope);
	}
}

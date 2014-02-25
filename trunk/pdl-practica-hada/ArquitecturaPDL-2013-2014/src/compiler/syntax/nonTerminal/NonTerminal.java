package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import es.uned.lsi.compiler.intermediate.QuadrupleIF;
import es.uned.lsi.compiler.semantic.SemanticErrorManager;
import es.uned.lsi.compiler.semantic.type.TypeIF;
import es.uned.lsi.compiler.semantic.type.TypeTableIF;
import es.uned.lsi.compiler.syntax.nonTerminal.NonTerminalIF;

/**
 * Abstract class for non terminals.
 */
public abstract class NonTerminal implements NonTerminalIF
{
    private List<QuadrupleIF> intermediateCode;
	private TypeTableIF typeTable;
	private TypeIF typeIF;
	private SemanticErrorManager semanticErrorManager;
    
    /**
     * Constructor for NonTerminal.
     */
    public NonTerminal ()
    {
        super ();
        this.intermediateCode = new ArrayList<QuadrupleIF> ();
    }

    /**
     * Returns the intermediateCode.
     * @return Returns the intermediateCode.
     */
    public List<QuadrupleIF> getIntermediateCode ()
    {
        return intermediateCode;
    }

    /**
     * Sets The intermediateCode.
     * @param intermediateCode The intermediateCode to set.
     */
    public void setIntermediateCode (List<QuadrupleIF> intermediateCode)
    {
        this.intermediateCode = intermediateCode;
    }
    
    public TypeTableIF getTypeTable() {
        return typeTable;
    }

    public void setTypeTable(TypeTableIF typeTable) {
        this.typeTable = typeTable;
    }
    

   public SemanticErrorManager getSemanticErrorManager() {
        return semanticErrorManager;
    }

    public void setSemanticErrorManager(SemanticErrorManager semanticErrorManager) {
        this.semanticErrorManager = semanticErrorManager;
    }
    
    public TypeIF getType() {
        return typeIF;
    }

    public void setType(TypeIF typeIF) {
        this.typeIF = typeIF;
    }
    
    
  //Conversión de tipos
    public TypeIF cast(TypeIF t1, String id){
    	if (t1.getName().equals(id))
    		return t1;
    	else return null;     	
    }
    
    
}
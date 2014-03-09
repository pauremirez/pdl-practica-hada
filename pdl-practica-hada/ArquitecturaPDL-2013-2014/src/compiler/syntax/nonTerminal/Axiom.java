package compiler.syntax.nonTerminal;


import es.uned.lsi.compiler.intermediate.LabelIF;
import java.util.List;



/**
 * Abstract Class for Axiom non terminal.
 */


public class Axiom extends NonTerminal {

	private LabelIF mainLabel;


	List intermediateCode;

    /**
     * Constructor for Axiom.
     */
    public Axiom ()
    {
        super();
    }

    
    public void setMainLabel(LabelIF mainLabel) {
		this.mainLabel = mainLabel;
	}
	public LabelIF getMainLabel() {
		return mainLabel;
	}

    
    @Override
    public List getIntermediateCode() {
        return intermediateCode;
    }
    @Override
    public void setIntermediateCode(List intermediateCode) {
        this.intermediateCode = intermediateCode;
    }


}

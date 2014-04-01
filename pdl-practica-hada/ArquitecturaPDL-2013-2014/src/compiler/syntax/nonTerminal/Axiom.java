package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import es.uned.lsi.compiler.intermediate.LabelIF;


/**
 * Abstract Class for Axiom non terminal.
 */

public class Axiom
    extends NonTerminal
{
	private LabelIF mainLabel;

    /**
     * Constructor for Axiom.
     */
    public Axiom ()
    {
        super();
    }
    
    @Override
    public List getIntermediateCode() {
        return intermediateCode;
    }
    @Override
    public void setIntermediateCode(List intermediateCode) {
        this.intermediateCode = intermediateCode;
    }
    
    public void setMainLabel(LabelIF mainLabel) {
		this.mainLabel = mainLabel;
	}
	public LabelIF getMainLabel() {
		return mainLabel;
	}

}

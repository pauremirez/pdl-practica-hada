package compiler.syntax.nonTerminal;

import java.util.List;

/**
 * Abstract Class for Axiom non terminal.
 */

public class Axiom extends NonTerminal
{

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

}

package compiler.semantic.type;

import es.uned.lsi.compiler.semantic.ScopeIF;


/**
 * Class for TypeBoolean.
 */

public class TypeBoolean extends TypeSimple
{
    /**
     * Constructor for TypeSimple.
     * @param scope The declaration scope.
     */
    public TypeBoolean (ScopeIF scope)
    {
        super (scope);
    	super.setName("BOOLEAN");
    }
   
    /**
     * Returns the size of the type.
     * @return the size of the type.
     */
    @Override
    public int getSize ()
    {
    	return super.getSize();
    }
}
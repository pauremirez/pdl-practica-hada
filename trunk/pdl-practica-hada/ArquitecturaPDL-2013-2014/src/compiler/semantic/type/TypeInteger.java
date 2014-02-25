package compiler.semantic.type;

import es.uned.lsi.compiler.semantic.ScopeIF;


/**
 * Class for TypeInteger.
 */


public class TypeInteger extends TypeSimple
{
    
    /**
     * Constructor for TypeInteger.
     * @param scope The declaration scope.
     */
    public TypeInteger (ScopeIF scope)
    {
         super(scope);
         super.setName("INTEGER");
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
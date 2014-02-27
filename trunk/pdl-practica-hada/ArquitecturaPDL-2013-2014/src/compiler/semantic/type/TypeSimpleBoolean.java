package compiler.semantic.type;

import compiler.CompilerContext;

import es.uned.lsi.compiler.semantic.ScopeIF;

public class TypeSimpleBoolean extends TypeSimple {

        public TypeSimpleBoolean(ScopeIF scope) {
                super(scope, "BOOLEAN");
        }
        
        /**
         * Constructor que crea el objeto en el ambito actual.
         */
        public TypeSimpleBoolean() {
                super(CompilerContext.getScopeManager().getCurrentScope(), "BOOLEAN");
        }
}
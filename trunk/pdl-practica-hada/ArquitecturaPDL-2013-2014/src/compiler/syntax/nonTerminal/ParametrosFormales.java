package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import compiler.CompilerContext;
import compiler.lexical.Token;
import compiler.semantic.symbol.SymbolParameter;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class ParametrosFormales extends NonTerminal {
        
        List<SymbolParameter> parametros;
        TypeIF type;
        private ScopeIF scope;
        
        public ParametrosFormales(ListaIdentificadores lista, TypeIF tipo)
        {
                parametros = new ArrayList<SymbolParameter>();
                scope = CompilerContext.getScopeManager().getCurrentScope();
                addParametros(lista, tipo);   
        }
        

        public List<SymbolParameter> getParametros() {
                return parametros;
        }


        public void setParametros(List<SymbolParameter> parametros) {
                this.parametros = parametros;
        }


        public void addParametros(ListaIdentificadores lista, TypeIF tipo)
        {       
                for(Token t : lista.getListaIdentificadores())
                {
                        SymbolParameter parametro = new SymbolParameter(scope, t.getLexema(), tipo);
                        scope.getSymbolTable().addSymbol(parametro);
                        parametros.add(parametro);                      
                }
        }
        
        public TypeIF getType(){
        	return type;
        }
        
        public void setType(TypeIF type){
        	this.type=type;
        }
}
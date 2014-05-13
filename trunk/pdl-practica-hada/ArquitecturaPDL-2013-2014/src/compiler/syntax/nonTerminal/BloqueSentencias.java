package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import es.uned.lsi.compiler.intermediate.OperandIF;

/**
 *
 * @author Administrador
 */
public class BloqueSentencias extends NonTerminal {

    private List codigoIntermedio;
    private OperandIF resultado;
    private OperandIF resultadoIndex;
    
    Expresion exp;
    
    public BloqueSentencias() {
        this.codigoIntermedio = new ArrayList();
    }
    
    public BloqueSentencias(Expresion exp) {
       this.exp=exp;
       this.codigoIntermedio = new ArrayList();
      }

    public OperandIF getResultado() {
        return resultado;
    }

    public void setResultado(OperandIF resultado) {
        this.resultado = resultado;
    }
    
    public OperandIF getResultadoIndex() {
        return resultadoIndex;
    }

    public void setResultadoIndex(OperandIF resultadoIndex) {
        this.resultadoIndex = resultadoIndex;
    }
}

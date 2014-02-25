package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import es.uned.lsi.compiler.intermediate.OperandIF;

/**
 *
 * @author Administrador
 */
public class BloqueSentencias extends NonTerminal {

    // private Boolean tieneDevuelve;
    // private TypeIF tipoDevuelve;
    private List codigoIntermedio;
    private OperandIF resultado;
    
    public BloqueSentencias() {
      //   this.tieneDevuelve = false;
        this.codigoIntermedio = new ArrayList();
    }

    public OperandIF getResultado() {
        return resultado;
    }

    public void setResultado(OperandIF resultado) {
        this.resultado = resultado;
    }
}

package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.intermediate.OperandIF;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;

public class Expresion extends NonTerminal {
    private TypeIF tipo;
    private OperandIF resultado;
    private int linea;
    private int columna;
    private boolean referencia=false;       
    private int valIni;                     
    private int valFin;                     

    public Expresion(TypeIF tipo, int linea, int columna) {
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
    }
    
    public Expresion(TypeIF tipo) {
        this.tipo = tipo;
    }
    
    public Expresion() {
    }
    
    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }
    
     public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public TypeIF getTipo() {
        return tipo;
    }

    public void setTipo(TypeIF tipo) {
        this.tipo = tipo;
    }
    
    public OperandIF getResultado() {
        return resultado;
    }

    public void setResultado(OperandIF resultado) {
        this.resultado = resultado;
    }

    public boolean getReferencia() {
        return referencia;
    }

    public void setReferencia(boolean referencia) {
        this.referencia = referencia;
    }

    public int getValFin() {
        return valFin;
    }

    public void setValFin(int valFin) {
        this.valFin = valFin;
    }

    public int getValIni() {
        return valIni;
    }

    public void setValIni(int valIni) {
        this.valIni = valIni;
    }
    
    public boolean castingTipos(Expresion exp1, Expresion exp2){
        boolean error = true;
    	 if((exp1.tipo.getName().equals("INTEGER") && exp2.tipo.getName().equals("INTEGER"))){
    		error=false;
        }else{
                error = true;
    	}
        return error;     	
    }
}

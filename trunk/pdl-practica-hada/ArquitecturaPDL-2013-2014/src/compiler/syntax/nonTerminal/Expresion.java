package compiler.syntax.nonTerminal;

import java.util.List;

import compiler.lexical.Token;
import es.uned.lsi.compiler.intermediate.TemporalIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;
import es.uned.lsi.compiler.intermediate.QuadrupleIF;

public class Expresion extends NonTerminal{
	
/*	private Token identificador;
	private Vect vector;
	private TipBoo tipoBooleano;
	private SenFun sentenciaFuncion;
	private Token id;*/

	private TypeIF type;
	private Expresion e;
	private TemporalIF temporal;
	List <QuadrupleIF> code;
	
	private int line;
	private int column;

	/** Creates a new instance of Expresion */
    public Expresion() {
    }
    
    public Expresion(Expresion e) {
    	this.e=e;
    }
    
    public Expresion(TypeIF typeIF){
        this.type = typeIF;
    }
    
    public Expresion(TypeIF type, int line, int column) {
        this.type = type;
        this.line = line;
        this.column = column;
    }
    
    public TemporalIF getTemporal(){
    	return temporal;
    }
    
    public void setTemporal(TemporalIF temporal){
    	this.temporal=temporal;
    }
    
    long hashcode(){ 
    	return 67 * 67 * code.hashCode() + 67 * type.hashCode() + temporal.hashCode();
    }
    
    public boolean equals (Object o){
    	if(!(o instanceof Expresion)) return false;
    	else{ Expresion e=(Expresion)o;
    		return e.code.equals(this.code)&& e.type.equals(this.type) && e.temporal.equals(this.temporal);
    	}
    }
    
    public String toString(){
    	return e.toString();
    }

    public TypeIF getType() {
        return type;
    }

    public void setType(TypeIF type) {
        this.type= type;
    }
    
/*    public Expresion(TypeIF typeIF, int linea, int columna) {
        this.type = typeIF;
        this.linea = linea;
        this.columna = columna;
    }
    
    public Expresion(Token identificador){
        this.identificador = identificador;
    }
    
    public Expresion(Vect vector){
        this.vector = vector;
    }
    
    public Expresion(TipBoo tipoBooleano){
        this.tipoBooleano = tipoBooleano;
    }
    
    public Expresion(SenFun sentenciaFuncion){
        this.sentenciaFuncion = sentenciaFuncion;
    }

    public Token getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Token identificador) {
        this.identificador = identificador;
    }
*/    
}

package compiler.syntax.nonTerminal;

import es.uned.lsi.compiler.semantic.type.TypeIF;

public class Var extends NonTerminal {

    private String name;
    private Integer value;
    private Integer line;
    private Integer column;


    public Var() {
    }

    public Var(String name, Integer value, Integer line, Integer column) {
        this.name = name;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
	
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getLine() {
        return line;
    }

    public void setLinea(Integer line) {
        this.line = line;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }


	@Override
    public String toString() {
        return "VAR--> Name:"+this.getName() + "; Value:" + this.getValue() + "; Line:" + this.getLine() + "; Column:" + this.getColumn();
    }

}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import es.uned.lsi.compiler.intermediate.OperandIF;

/**
 *
 * @author Administrador
 */
public class ListaSentencias extends NonTerminal {

	List variables = null;

    public ListaSentencias() {
        this.variables = new ArrayList();
    }

    public ListaSentencias(String item) {
        this.variables = new ArrayList();
        variables.add(item);
    }

    public ListaSentencias(ListaSentencias lista) {
        this.variables = new ArrayList();
            for (int i = 0; i < lista.size(); i++) {
            variables.add(lista.get(i));
        }
    }

    public boolean add(Object objeto) {
        return variables.add(objeto);
    }

    public int size() {
        return variables.size();
    }

    public Object get(int indice) {
        return variables.get(indice);
    }

    @Override
    public String toString() {
        return "" + variables.toString();
    }
}
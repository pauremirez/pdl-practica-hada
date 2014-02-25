package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

public class ListaObjetos extends NonTerminal {

    List variables = null;

    public ListaObjetos() {
        this.variables = new ArrayList();
    }

    public ListaObjetos(String item) {
        this.variables = new ArrayList();
        variables.add(item);
    }

    public ListaObjetos(ListaObjetos lista) {
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
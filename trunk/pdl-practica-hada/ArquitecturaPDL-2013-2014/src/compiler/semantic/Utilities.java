package compiler.semantic;

import compiler.semantic.type.TypeTable;
import compiler.syntax.nonTerminal.ListaObjetos;
import compiler.syntax.nonTerminal.Parametro;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import es.uned.lsi.compiler.semantic.Scope;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;


public class Utilities {
    
   
    /** Creates a new instance of Utilidades */
    public Utilities () {
    }
    
    public static List getListFromTreeMap(TreeMap tree) {
        ArrayList list = new ArrayList();
        Iterator it = tree.values().iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }
        return list;
    }
    
     public static List getListFromHashMap(HashMap map) {
        ArrayList list = new ArrayList();
        Iterator it = map.values().iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }
        return list;
    }
    public static List ordenaParametros(ListaObjetos lista) {
        
        TreeMap tablaParametros = new TreeMap();
        
        for (int i=0; i<lista.size(); i++){
            Parametro p = (Parametro) lista.get(i); //--> Problema NO CAST BETWEEN VAR Y PARAMETRO
            tablaParametros.put(p.getColumna(), p);
        }
        return getListFromTreeMap(tablaParametros);
    }
    public static TypeIF searchType(ScopeManagerIF sm, String tipoBuscado) {
        //Busca em todos los scopes a ver si existe el tipo, buscandolo a partir de su string
        
        List scopes = sm.getOpenScopes();
        Iterator it = scopes.iterator();
        Scope unScope;
        TypeTable unaTT;
        boolean found = false;
        while (it.hasNext() && !found) {
            unScope = (Scope) it.next();
            unaTT = (TypeTable) unScope.getTypeTable();
            if (unaTT.containsType(tipoBuscado)) {
                return unaTT.getType(tipoBuscado);
            }
        }
        return null;
    }

  }
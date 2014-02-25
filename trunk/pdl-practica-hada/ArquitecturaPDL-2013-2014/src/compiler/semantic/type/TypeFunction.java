package compiler.semantic.type;

import compiler.semantic.symbol.SymbolVariable;
import compiler.syntax.nonTerminal.Expresion;
import compiler.syntax.nonTerminal.ListaObjetos;
import compiler.syntax.nonTerminal.Parametro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import compiler.semantic.Utilities;
import es.uned.lsi.compiler.intermediate.LabelIF;
import es.uned.lsi.compiler.semantic.Scope;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.ScopeManagerIF;
import es.uned.lsi.compiler.semantic.type.TypeBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Class for TypeFunction.
 */

// TODO: Student work
//       Include properties to characterize function declarations

public class TypeFunction extends TypeBase
{   
    // Definicion de campos del registro
    private TreeMap tablaParametros = new TreeMap();
    private ArrayList listaParametros= new ArrayList();
    private TypeIF tipoRetorno;
    private boolean hayRetorno;
    private LabelIF etiqSub;
    private LabelIF etiqSubFin;
    
    /**
     * Constructor for TypeFunction.
     * @param scope The declaration scope.
     */
    public TypeFunction (ScopeIF scope)
    {
        super (scope);
        
    }

    /**
     * Constructor for TypeFunction.
     * @param scope The declaration scope
     * @param name The name of the function.
     */
    public TypeFunction (ScopeIF scope, String name)
    {
        super (scope, name);
    }

    // Gestion de parametros de la funcion
    public List getListaParametros(){
        return listaParametros;
    }
    public void setListaParametros(ArrayList listaParametros){
        this.listaParametros=listaParametros;
    }
    public TreeMap getTablaParametros(){
        return tablaParametros;
    }
    public void setTablaParametros(TreeMap tablaParametros){
        this.tablaParametros=tablaParametros;
    }
    
    public void setParametro(SymbolVariable par){
        this.listaParametros.add(par);
    
    }
    // Gestion parametros retorno
    public TypeIF getTipoRetorno(){
        return this.tipoRetorno;
    }
    public void setTipoRetorno(TypeIF tretorno){
        this.tipoRetorno=tretorno;
    }
    
    // Etiquetas Subprograma
    public LabelIF getEtiqSub(){
        return this.etiqSub;
    }
    public void setEtiqSub(LabelIF etiqSub){
        this.etiqSub=etiqSub;
    }
    
    // Etiqueta Fin Subprograma
    public LabelIF getEtiqSubFin(){
        return this.etiqSubFin;
    }
    public void setEtiqSubFin(LabelIF etiqSub){
        this.etiqSubFin=etiqSub;
    }
    public TypeIF getTypeParametro(Integer columna){
        Object tipo= this.getTablaParametros().get(columna);
        if (tipo != null && tipo instanceof TypeIF) {
            return (TypeIF) tipo;
        } else return null;
    }
    public void setTypeParametro(Integer columna, TypeIF tipo){
        this.getTablaParametros().put(columna, tipo);
        
    }
    
    // Anyadir Parametro al procedimiento
    public void addParametro (Integer columna, TypeIF type)   {
        this.getTablaParametros().put(columna, type);
    
    }
    public String getTypes ()  {    
        return this.getTablaParametros().toString();   
    }
        
    public boolean comparaParametros(ListaObjetos listaPar){
        // comprobar el numdero de parametros
        ArrayList listaArg = (ArrayList) Utilities.getListFromTreeMap(tablaParametros);
        if (!(listaPar.size()==listaArg.size()) )  return false;
        
        // comprobar el tipo de cada parametro: La lista de parametros viene en orden inverso.
        int j=listaArg.size();
        for (int i=0; i<listaArg.size(); i++){
        
            TypeIF arg = (TypeIF) listaArg.get(i);
            Expresion exp = (Expresion) listaPar.get(--j);
            TypeIF par = (TypeIF) exp.getType();
            if (! arg.equals(par) ) return false;
        }    						
        return true;
    }
    // Comprobar que hay sentencia return definida en la funcion
    public boolean getHayRetorno(){
        return this.hayRetorno;
    }
    public void setHayRetorno(boolean hayRetorno){
        this.hayRetorno=hayRetorno;
    }
    
    public List getListFromTreeMap(TreeMap tree) {
        ArrayList list = new ArrayList();
        Iterator it = tree.values().iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }
        return list;
    }
    
     public List getListFromHashMap(HashMap map) {
        ArrayList list = new ArrayList();
        Iterator it = map.values().iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }
        return list;
    }
    public List ordenaParametros(ListaObjetos lista) {
        
        TreeMap tablaParametros = new TreeMap();
        
        for (int i=0; i<lista.size(); i++){
            Parametro p = (Parametro) lista.get(i); //--> Problema NO CAST BETWEEN VAR Y PARAMETRO
            tablaParametros.put(p.getColumna(), p);
        }
        return getListFromTreeMap(tablaParametros);
    }
    public TypeIF searchType(ScopeManagerIF sm, String tipoBuscado) {
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

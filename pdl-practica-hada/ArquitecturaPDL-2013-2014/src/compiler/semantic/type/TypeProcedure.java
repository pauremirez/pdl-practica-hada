package compiler.semantic.type;

import compiler.semantic.Utilidades;
import compiler.semantic.symbol.SymbolVariable;
import compiler.syntax.nonTerminal.Expresion;
import compiler.syntax.nonTerminal.ListaObjetos;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import es.uned.lsi.compiler.intermediate.LabelIF;
import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.type.TypeBase;
import es.uned.lsi.compiler.semantic.type.TypeIF;

/**
 * Class for TypeProcedure.
 */

// TODO: Student work
//       Include properties to characterize procedure declarations

public class TypeProcedure
    extends TypeBase
{   
    // Definicion de campos del registro
    private TreeMap tablaParametros = new TreeMap();   
    private ArrayList listaParametros= new ArrayList();
    private LabelIF etiqSub;
    private LabelIF etiqSubFin;
    
   /**
     * Constructor for TypeProcedure.
     * @param scope The declaration scope.
     */
    public TypeProcedure (ScopeIF scope)
    {
        super (scope);
    }

    /**
     * Constructor for TypeProcedure.
     * @param scope The declaration scope
     * @param name The name of the procedure.
     */
    public TypeProcedure (ScopeIF scope, String name)
    {
        super (scope, name);
        
    }

   // Gestion de parametros del procedimiento
    public TreeMap getTablaParametros(){
        return tablaParametros;
    }
    public void setTablaParametros(TreeMap tablaParametros){
        this.tablaParametros=tablaParametros;
    }
     public void setParametro(SymbolVariable par){
        this.listaParametros.add(par);
    
    }
    public TypeIF getTypeParametro(Integer columna){
        Object tipo= this.getTablaParametros().get(columna);
        if (tipo != null && tipo instanceof TypeIF) {
            return (TypeIF) tipo;
        } else return null;
    }
    public void setTypeParametro(Integer columna, TypeIF tipo){
        this.getTablaParametros().put(columna,tipo);
        
    }
    
    // Anyadir Parametro al procedimiento
    public void addParametro (Integer columna, TypeIF type)   {
        this.getTablaParametros().put(columna, type);
    
    }
    public String getTypes ()  {    
        return this.getTablaParametros().toString();   
    }
    
    // Gestion de parametros de la funcion
    public List getListaParametros(){
        return listaParametros;
    }
    public void setListaParametros(ArrayList listaParametros){
        this.listaParametros=listaParametros;
    }
        
    public boolean comparaParametros(ListaObjetos listaPar){
        // comprobar el numero de parametros
        ArrayList listaArg = (ArrayList) Utilidades.getListFromTreeMap(tablaParametros);
        if (!(listaPar.size()==listaArg.size()) )  return false;
        
        // comprobar el tipo de cada parametro: La lista de parametros viene en orden inverso.
        int j=listaArg.size();
        for (int i=0; i<listaArg.size(); i++){
            TypeIF arg = (TypeIF) listaArg.get(i);
            // el parámetro es una expresion hay que convertirla
            Expresion exp = (Expresion) listaPar.get(--j);
            TypeIF par = exp.getTipo();
            if (! arg.equals(par) ) return false;
        }    						
        return true;
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

    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if two objects has the same properties.
     */

    public boolean equalsProcedure (Object other)
    {
        // TODO: Student work
        if (this == other) return true;
        
        if (!(this instanceof TypeBase)) return false;
        
        TypeBase aType = (TypeBase) other;
        return aType.getName().equals(this.getName());
    }

    /**
    * Returns a hash code for the object.
    */

    public int hashCodeProcedure ()
    {
        // TODO: Student work
        // return super.hashCode ();
         return 255*getName().hashCode()+getScope().hashCode();

    }

    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */

    public String toStringProcedure ()
    {
        List lista = new ArrayList();
        // TODO: Student work (optional)
        String str = "TypeProcedure " + this.getScope().getLevel() + " con el nombre: " + this.getName()+" y los parametros:";
        for (Iterator i = Utilidades.getListFromTreeMap(tablaParametros).iterator(); i.hasNext();) {
           str += "\n      " + i.next().toString();
        }

        return str;
    }

}

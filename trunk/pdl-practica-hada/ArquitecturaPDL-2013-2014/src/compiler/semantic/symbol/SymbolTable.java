package compiler.semantic.symbol;

import java.util.List;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import compiler.semantic.Utilidades;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolTableIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;



/**
 * Class for SymbolTable.
 */

public class SymbolTable 
    implements SymbolTableIF
{
  
    private ScopeIF scope = null;
    private HashMap tabla = new HashMap();
    private int desplazamiento;
    private boolean referencia;
    private int size;      
    private int address;
    
    /**
     * Constructor for SymbolTable.
     */
    public SymbolTable ()
    {
        // TODO: Student Work
    }
    
    /**
     * Constructor for SymbolTable.
     * @param The scope of the symbol table.
     */
    public SymbolTable (ScopeIF scope)
    {       
        this ();
        this.scope = scope;
    }
    
       
    
    /**
     * Returns the scope of the Symbol table. 
     * @return the scope of the Symbol table. 
     */
    public ScopeIF getScope ()
    {
        return  scope;
    }

    /**
     * Returns a symbol from the symbol table.
     * @param name the symbol name.
     */
    public SymbolIF getSymbol (String name)
    {
        // TODO: Student Work
        Object simbolo = this.getTabla().get(name);
        if (simbolo != null && simbolo instanceof SymbolIF) {
            return (SymbolIF) simbolo;
        } else {
            return null;
        }
    }
    
    /**
     * Adds a new symbol to the symbol table.
     * @param symbol the symbol.
     */
    public void addSymbol (SymbolIF symbol)
    {
        // TODO: Student Work
    }
    
    /**
     * Adds a new symbol to the symbol table.
     * @param name the symbol name.
     * @param symbol the symbol.
     */
    public void addSymbol (String name, SymbolIF symbol)
    {
        // TODO: Student Work
        this.getTabla().put(name, symbol);
    }
    
    /**
     * Returns the list of all symbols of symbol table.
     * @return A list of symbols.
     */
    public List getSymbols ()
    {
        // TODO: Student Work
        ArrayList listaSimbolos = new ArrayList();
        Iterator it = this.getTabla().values().iterator();
        while (it.hasNext()){
            listaSimbolos.add(it.next());
        }
        return listaSimbolos;
 
    }
    // Devuelve la funcion declarada en el scope
    public SymbolIF getSymbolFunction ()
    {
        Iterator it = this.getTabla().values().iterator();
        while (it.hasNext()){
            if (it.next() instanceof SymbolFunction ){
                SymbolIF funcion = (SymbolIF) it.next();
                return funcion;
            }
        }
        return null;
 
    }
    
    /**
     * Indicates if the symbol is contained in the symbol table.
     * @param symbol The symbol. 
     * @return True if the symbol is contained in the symbol table.
     */
    public boolean containsSymbol (SymbolIF symbol)
    {
        // TODO: Student Work
        return this.getTabla().containsValue(symbol);

    }
    
    /**
     * Indicates if the symbol is contained in the symbol table.
     * @param name The symbol name. 
     * @return True if the symbol is contained in the symbol table.
     */
    public boolean containsSymbol (String name)
    {
        // TODO: Student Work
        return this.getTabla().containsKey(name);

    }
    
    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if both objects has the same properties.
     */
    public boolean equals (Object other)
    {
        // TODO: Student Work
        return super.equals(other);
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
        // TODO: Student Work
        return super.hashCode();
    } 

    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */
    public String toString ()
    {
        // TODO: Student Work
        return super.toString();
    } 
    public HashMap getTabla(){
        return tabla;
    }
    public void setTabla(HashMap tabla){
        this.tabla=tabla;
    }
    public List getLista() {
        return Utilidades.getListFromHashMap (this.getTabla());
    }

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getSize(Class<SymbolIF> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<SymbolIF> getSymbols(Class<SymbolIF> arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
    public boolean getReferencia() {
        return referencia;
    }

    public void setReferencia(boolean referencia) {
        this.referencia = referencia;
    }


    public void setSize(int size) {
        this.size = size;
    }
    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */
    public String toStringVariable()
    {
        // TODO: Student Work (optional)
        return super.toString();
    } 
    
    public int getDesplazamiento() {
        return this.desplazamiento;
    }

    public void setDesplazamiento(int desplazamiento) {
        this.desplazamiento = desplazamiento;
    }
    
	/**
	 * @return the address
	 */
	public int getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(int address) {
		this.address = address;
	}
}
package compiler.semantic.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import es.uned.lsi.compiler.semantic.ScopeIF;
import es.uned.lsi.compiler.semantic.symbol.SymbolIF;
import es.uned.lsi.compiler.semantic.type.TypeIF;
import es.uned.lsi.compiler.semantic.type.TypeTableIF;

public class TypeTable implements TypeTableIF{
	private ScopeIF scope;
	private Map <String, TypeIF> tTable;

	public TypeTable(ScopeIF scope) {
		this.scope=scope;
		tTable= new HashMap<String, TypeIF> ();
	}

	public TypeIF getType(String id){
		return tTable.get(id);
	}
	
	public void addType (TypeIF type){
		String id=type.getName();
		addType(id,type);
	}
	
	public void addType(String id, TypeIF type){
		type.setScope(this.scope);
		tTable.put(id, type);
	}
	
	public boolean containsType(String id){
		return tTable.containsKey(id);
	}
	
	public boolean containsType(TypeIF type){
		String id=type.getName();
		return containsType(id);
	}
	
	//...
	public int hashCode ()
    {
       return super.hashCode();
    } 

    public String toString ()
    {
       return super.toString();
    }

	@Override
	public ScopeIF getScope() {
		return scope;
	}

	@Override
	public int getSize() {
		return tTable.size();
	}

	 public  Map<String, TypeIF> gettTable(){
		 return tTable;
	 }

	@Override
	public List<TypeIF> getTypes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<TypeIF> getTypes(Class<SymbolIF> arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	
}

package pish.symbol;

import java.util.*;

import pish.ast.*;

public class SymbolTable {

	private ASTNode scopeNode;
	private int depth;
	private Map<String, Symbol> symbolMap;
	private SymbolTable parentSymbolTable;
	private List<SymbolTable> list = new ArrayList<SymbolTable>();
	private Map<String, List<SystemType> > funcMap = new HashMap<String, List<SystemType> >();
	private Map<String, SymbolTable > recordMap = new HashMap<String, SymbolTable>();
	
	
	public SymbolTable(ASTNode scopeNode) {
		this(scopeNode,null,0);
	}

	public SymbolTable(ASTNode scopeNode,SymbolTable parentSymbolTable, int depth) {
		this.depth = depth;
		symbolMap = new HashMap<String,Symbol>();	
		this.parentSymbolTable=parentSymbolTable;
		this.scopeNode = scopeNode;
	}
	
	public void addChildTable( SymbolTable t ) {
		list.add(t);
	}
	
	public List<SymbolTable> getChildrenTables(){
		return list;
	}

	public int getDepth() {
		return depth;
	}
	
	public SystemType get(ASTNode node ) throws Exception{
		if ( node == null ){
			return SystemType.NULL();
		}
		Symbol symbol= symbolMap.get(node.toString().toUpperCase());
		if (symbol==null)  {	   
			if ( parentSymbolTable == null  )
			{
				throw new Exception("Error (line "+node.getLineNumber()+") Cannot find symbol("+node.toString()+"). Can't Beleive You've Done This");
			}
			return parentSymbolTable.get(node);   
		}
		if (!(symbol.getType() instanceof SystemType )){
			if ( symbol.getType() instanceof ArrayType )
			{
				return get(((ArrayType)symbol.getType()).type);
				
			}
			return get(symbol.getType());
		}
		else{
			return (SystemType)symbol.getType();
		}
	}

	public ASTNode getRecordType(ASTNode node) throws Exception{
		Symbol symbol= symbolMap.get(node.toString().toUpperCase());
		if (symbol==null)  {	   
			if ( parentSymbolTable == null  )
			{
				throw new Exception("Error (line "+node.getLineNumber()+") Cannot find symbol("+node.toString()+") . Can't Beleive You've Done This ");
			}
			return parentSymbolTable.getRecordType(node);   
		}
		if (!(symbol.getType() instanceof RecordType )){
			if ( symbol.getType() instanceof ArrayType)
			{
				if ( ((ArrayType)symbol.getType()).type instanceof RecordType){
					return symbol.getNode();
				}
				return getRecordType(((ArrayType)symbol.getType()).type);
			}
			return getRecordType(symbol.getType());
		}
		else{
			return symbol.getNode();
		}	
	}
	
	public void put(ASTNode node, ASTNode type) throws Exception{
		if ( symbolMap.get(node.toString().toUpperCase()) != null)
		{
			String s = "variable";
			if ( node instanceof FuncVar )
				s = "Function/Procedure";
			throw new Exception("Error (line="+node.getLineNumber()+") "+s+" ("+node+") already exists can't redefine it in the same scope("+depth+")");
		}
		symbolMap.put(node.toString().toUpperCase(),new Symbol(node,type,depth));
	}

	public SymbolTable getParent() {
		return parentSymbolTable;
	}
	
	public String toString(){
		
		String s="";
			s = "\n"+getTabs()+"Depth("+ depth +") "+scopeNode.toString()+"[\n";
			s += getTabs()+"\tHashTable [\n";
			Iterator<Map.Entry<String, Symbol>> it = symbolMap.entrySet().iterator();
		    while (it.hasNext()) {
		    	Map.Entry<String, Symbol> pairs = (Map.Entry<String, Symbol>)it.next();
		        s +=getTabs()+("\t\t "+pairs.getKey() + " = " + pairs.getValue())+"\n";
		    }
		    s += getTabs()+"\t]\n";
		    if ( recordMap != null )
		    {
		    	s += getTabs()+"\tRecordMap [\n";
				Iterator<Map.Entry<String, SymbolTable>> i = recordMap.entrySet().iterator();
			    while (i.hasNext()) {
			    	Map.Entry<String, SymbolTable> pairs = (Map.Entry<String, SymbolTable>)i.next();
			        s +=getTabs()+("\t "+pairs.getKey() + " = " + pairs.getValue())+"";
			    }
			    s += getTabs()+"\t]\n";
		    }
			if ( list.size() >0){
				s += getTabs()+"\tChildren [\n ";
				for ( SymbolTable t : list)
				{
					s+=t;
				}
				s += getTabs()+"\t] End Children\n";	
			}
			s += getTabs()+"] End Depth("+depth+")\n";
		return s;
	}
	
	public String getTabs(){
		String s="";
		for ( int i = 0; i < depth ; i++){
			s+="\t";
		}
		return s;
	}

	public void setScopeNode(ASTNode scopeNode) {
		this.scopeNode = scopeNode;
	}

	public ASTNode getScopeName() {
		return scopeNode;
	}

	public SymbolTable getScope(ASTNode scopeNode) {
		
		for ( SymbolTable st : list) {
			if ( st.scopeNode.toString().equalsIgnoreCase( scopeNode.toString()) ){
				return st;
			}
		}
		return new SymbolTable(scopeNode,this,getDepth()+1);
	}

	public void putMethod(FuncVar id, SimpleDeclerationList fields , SymbolVisitor visit) {
		if ( fields == null )
		{
			funcMap.put(id.toString().toUpperCase(), new ArrayList<SystemType>());
			return;
		}
		funcMap.put(id.toString().toUpperCase(), fields.toArray(visit));
	}

	public List<SystemType> getArgs(FuncVar id) throws Exception {
		List<SystemType> ret = funcMap.get(id.toString().toUpperCase());
		if ( ret == null ) {
			
			if ( parentSymbolTable == null  )
			{			
				throw new Exception("Error (line "+id.getLineNumber()+") Function/Procedure ("+id+") is not defined" );
			}
			
			return parentSymbolTable.getArgs(id);
		}
		
		return ret;
	}

	public SymbolTable openRecordScope(ASTNode id) throws Exception{
		ASTNode originalRecord = getRecordType(id);
		SymbolTable table =  recordMap.get(originalRecord.toString().toUpperCase());
		
		if ( table == null )
		{
			if ( this.parentSymbolTable == null ){
				throw new Exception("cannot find symbol table ("+originalRecord+")");
			}
			return parentSymbolTable.openRecordScope(originalRecord);
		}
		
		return table;
	}
	
	public SymbolTable createRecordScope(ASTNode id) {
		SymbolTable table = recordMap.get(id.toString().toUpperCase());
		if ( table == null )
		{
			table = new SymbolTable(id,this,getDepth()+1);
			recordMap.put(id.toString().toUpperCase(), table);
		}
		
		return table;
	}

	public boolean isMember(ASTNode id) {
		Symbol symbol = this.symbolMap.get(id.toString().toUpperCase());
		return ( symbol !=null );
		
	}	
}

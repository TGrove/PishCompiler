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
	
	private int offSetCounter;
	private boolean isAddress=false;
	
	public SymbolTable(ASTNode scopeNode) {
		this(scopeNode,null,0);
	}

	public SymbolTable(ASTNode scopeNode,SymbolTable parentSymbolTable, int depth) {
		this.depth = depth;
		symbolMap = new HashMap<String,Symbol>();	
		this.parentSymbolTable=parentSymbolTable;
		this.scopeNode = scopeNode;
		this.setOffSetCounter(4);
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
		if ( node instanceof ArrayType )
		{
			return get(((ArrayType)node).type);			
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
			if ( symbol.getType() instanceof UnaryOpExpr ){
				return get(((UnaryOpExpr)symbol.getType()).operand);
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
	
	public Symbol put(ASTNode node, ASTNode type) throws Exception{
		return put(node,type,null,null);
	}

	public Symbol put(ASTNode node, ASTNode type,Integer size, Integer offSet) throws Exception{
		Symbol temp = null;
		
		if ( symbolMap.get(node.toString().toUpperCase()) != null)
		{
			String s = "variable";
			if ( node instanceof FuncVar )
				s = "Function/Procedure";
			throw new Exception("Error (line="+node.getLineNumber()+") "+s+" ("+node+") already exists can't redefine it in the same scope("+depth+")");
		}
		
		temp = new Symbol(node,type,depth);
		temp.setOffSet(offSet);
		temp.setSize(size);
		//temp.setIsAddress(isAddress);
		symbolMap.put(node.toString().toUpperCase(), temp);
		return temp;
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
			table.setOffSetCounter(0);
			recordMap.put(id.toString().toUpperCase(), table);
		}
		
		return table;
	}

	public boolean isMember(ASTNode id) {
		Symbol symbol = this.symbolMap.get(id.toString().toUpperCase());
		return ( symbol !=null );
		
	}

	public boolean isConstant(Variable id) {
		Symbol symbol = this.symbolMap.get(id.toString().toUpperCase());
		if ( symbol  == null ){
			if ( getParent() == null)
				return false;
			return getParent().isConstant(id);
		}
		return ( symbol.getNode() instanceof ConstantId);
	}

	public void setOffSetCounter(int offsetCounter) {
		this.offSetCounter = offsetCounter;
	}

	public int getOffSetCounter() {
		return offSetCounter;
	}

	public void incrementOffSetCounter(Integer intTmp) {
		this.offSetCounter += intTmp.intValue();
	}

	public int getSizeOfSymbol(ASTNode node) throws Exception{
		Symbol symbol= symbolMap.get(node.toString().toUpperCase());
		if (symbol==null)  {	   
			if ( parentSymbolTable == null  )
			{
				throw new Exception("Error (line "+node.getLineNumber()+") Cannot find symbol("+node.toString()+"). Can't Beleive You've Done This");
			}
			return parentSymbolTable.getSizeOfSymbol(node);   
		}
	
			return symbol.getSize();
		
	}

	public Symbol getSymbol(ASTNode node) throws Exception{
		Symbol symbol= symbolMap.get(node.toString().toUpperCase());
		if (symbol==null)  {	   
			if ( parentSymbolTable == null  )
			{
				throw new Exception("Error (line "+node.getLineNumber()+") Cannot find symbol("+node.toString()+"). Can't Beleive You've Done This");
			}
			return parentSymbolTable.getSymbol(node);   
		}
	
		return symbol;
	}

	public Integer getSizeOfArrayRecord(ASTNode node) {
		Symbol symbol= symbolMap.get(node.toString().toUpperCase());
		if ( symbol != null){
			if ( symbol.getType() instanceof RecordType){
				return symbol.getSize();
			}
			if ( symbol.getType() instanceof SystemType){
				return ((SystemType) symbol.getType()).getType().getMemoryBytes();
			}
			if ( symbol.getType() instanceof ArrayType){
				return getSizeOfArrayRecord(((ArrayType)symbol.getType()).type);
			}
			
			return getSizeOfArrayRecord(symbol.getType());
		}
		if (this.parentSymbolTable != null)
		{
			return parentSymbolTable.getSizeOfArrayRecord(node);
		}
		return null;
	}

	public boolean isArray(ASTNode node) {
		if ( node instanceof ArrayType){
			return true;
		}
		try{
			Symbol symbol = getSymbol(node);
		
			if ( symbol != null )
			{
				if (symbol.getType() instanceof ArrayType)
					return true;
				if ( symbol.getType() instanceof SystemType)
				{
					return false;
				}
				return isArray(symbol.getType());
			}
		}catch (Exception e)
		{
		}
		return false;
	}

	public boolean isRecord(ASTNode node) {
		if ( node instanceof RecordType){
			return true;
		}
		try{
			Symbol symbol = getSymbol(node);
		
			if ( symbol != null )
			{
				if (symbol.getType() instanceof RecordType)
					return true;
				return isArray(symbol.getType());
			}
		}catch (Exception e)
		{
		}
		return false;
	}

	public boolean isGlobalVariable(ASTNode node) {
		if ( node instanceof ConstantExpr){
			return false;			
		}

		
		Symbol symbol= symbolMap.get(node.toString().toUpperCase());
		if (symbol==null)  {	   
			if ( parentSymbolTable == null  ) {
				return false;
			}
			return parentSymbolTable.isGlobalVariable(node);   
		}
		if ( this.depth == 0) {
			return true;
		}
		return false;
	}

	public void switchAddress(boolean on) {
		this.isAddress=true;
	}
	
	public List<Symbol> getAllSymbols(){
		List<Symbol> array = new ArrayList<Symbol>(this.symbolMap.values());
		Collections.sort(array);
		return array;
	}

	public boolean isFunction( ASTNode node ){
		boolean found = this.funcMap.containsKey(node.toString().toUpperCase());
		if (!found && parentSymbolTable != null ){
			return parentSymbolTable.isFunction(node);
		}
		return found;
	}

	public boolean getIsAddress() {
		return isAddress;
	}
}

package pish.symbol;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import pish.ast.*;

public class SymbolNodeVisitor implements SymbolVisitor{

	private SymbolTable currSymbolTable;
	Stack<SymbolTable> visitedRecords = new Stack<SymbolTable>();
	
	public SymbolNodeVisitor(){
	}

	public SymbolTable getSymbolTable(){
		return currSymbolTable;
	}
	
	@Override
	public void visit(Program program) {
		program.accept(this,null);	
	}

	@Override
	public void visit(Constant constant) {
		constant.accept(this, null);
	}

	@Override
	public void visit(TypeDef type) {
		type.accept(this,null);
		
	}

	@Override
	public void visit(SimpleDecleration simpleDec) {
		simpleDec.accept(this,null);
		
	}

	@Override
	public void visit(SubProgramDecleration subDec) {
		subDec.accept(this,null);
		
	}

	@Override
	public void visit(ConstantList constantList) {
		constantList.accept(this, null);
		
	}

	@Override
	public void visit(TypeDefList typeDefList) {
		typeDefList.accept(this,null);
		
	}

	@Override
	public void visit(SimpleDeclerationList simpleDecList) {
		simpleDecList.accept(this,null);
		
	}

	@Override
	public void visit(SubProgramDeclerationList subProgramList) {
		subProgramList.accept(this,null);
	}

	@Override
	public void visit(StmtList stmtList) {
		stmtList.accept(this,null);
		
	}

	@Override
	public void visit(Decleration decleration) {
		decleration.accept(this,null);
		
	}

	@Override
	public void visit(DeclerationList list) {
		list.accept(this,null);
		
	}

	@Override
	public void visit(Expr expr) {
		expr.accept(this,null);
		
	}

	@Override
	public void visit(ExprList list) {
		list.accept(this,null);
		
	}

	@Override
	public void closeScope() {
		SymbolTable temp = this.currSymbolTable;
		if ( this.currSymbolTable.getParent() != null ){
			this.currSymbolTable = this.currSymbolTable.getParent();
			if (!currSymbolTable.getChildrenTables().contains(temp))
			{
				this.currSymbolTable.addChildTable(temp);
			}
		}
	}
	
	@Override
	public void openScope(ASTNode scopeNode) {
		if ( currSymbolTable == null )
		{
			this.currSymbolTable = new SymbolTable(scopeNode);
		}else
		{
			this.currSymbolTable = currSymbolTable.getScope(scopeNode);
			
		}
	}

	@Override
	public void visit(Stmt stmt) {
		stmt.accept(this,null);
		
	}

	public void printSymbolTable() {
		PrintWriter printer;
		String filePath = "symbolTable.txt";
		try{
			printer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
			//System.out.println(currSymbolTable);
			printer.println(currSymbolTable.toString());
			printer.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public Symbol registerSymbol(ASTNode node, ASTNode type) {
		Symbol tmpSym = null;
		int tmpOS;
		Integer tmpInt;
		
		
		try{
			if (type == null ){
				tmpSym = currSymbolTable.put(node, type);
			}else{
				/* Getting Size of Symbol and Current Table OffSet */
				tmpInt = type.getSize(this);
				tmpOS = this.getCurrOffSet();
				tmpSym = currSymbolTable.put(node, type,tmpInt,tmpOS);
				/* Increment Table OffSetCounter */
				if ( tmpInt != null) {
					this.incrementOffSetCounter(tmpInt);
				}
				//System.out.println(""+currSymbolTable);
			
			}
		}catch(Exception e){
			System.out.println("Unexpected Error: "+e.getMessage()+" NODE "+node+" TYPE "+type );
			//e.printStackTrace();
		}
		return tmpSym;
	}

	@Override
	public void registerMethod(FuncVar id, SimpleDeclerationList fields) {
		currSymbolTable.putMethod(id, fields, this);
		
	}

	@Override
	public List<SystemType> getArgs(FuncVar id) {
		try{
			return currSymbolTable.getArgs(id);
		}catch(Exception e ) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public SystemType lookup(ASTNode node) {
		try{
			return currSymbolTable.get(node);
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return SystemType.NULL();
	}

	@Override
	public void closeRecordScope(ASTNode id) throws Exception {
		this.currSymbolTable = visitedRecords.pop();
	}

	@Override
	public void finishRecordScope(ASTNode id) throws Exception {
		if ( this.currSymbolTable.getParent() != null ){
			this.currSymbolTable = this.currSymbolTable.getParent();
		}
		else {
			throw new Exception("COMPILER PANIC: SOMETHING WENT TERRIBLY WRONG CAN'T FIND THE PARENT SYMBOL TABLE OF THE RECORD SYMBOL TABLE FOR ID ("+id+") !!!!");
		}
	}

	@Override
	public void openRecordScope(ASTNode id) throws Exception{
		
		visitedRecords.push(currSymbolTable);
		currSymbolTable = currSymbolTable.openRecordScope(id);	
	}

	@Override
	public void createRecordScope(ASTNode id){
		currSymbolTable = currSymbolTable.createRecordScope(id);	
		
	}

	@Override
	public boolean isMember(ASTNode id) {
		if ( currSymbolTable != null )
		{
			return currSymbolTable.isMember(id);
		}
		return false;
	}

	@Override
	public boolean isConstant(Variable id) {
		return currSymbolTable.isConstant(id);
	}

	@Override
	public void incrementOffSetCounter(Integer tmpInt) {
		currSymbolTable.incrementOffSetCounter(tmpInt);
	}

	@Override
	public int getSizeOfASTNode(ASTNode node) throws Exception{
		return currSymbolTable.getSizeOfSymbol(node);

		
	}

	@Override
	public int getCurrOffSet() {
		return currSymbolTable.getOffSetCounter();
	}

	@Override
	public int getIntValue(ASTNode node) throws Exception{
		int tmpInt = 0;
		Symbol tmpSym;
		ASTNode tmpN;
		
		if (node instanceof NumberExpr){
			tmpInt = ((IntegerExpr)node).value;
			
		}else if(node instanceof ConstantId || node instanceof UnaryOpExpr){
			
				tmpSym = currSymbolTable.getSymbol( node );
				tmpN = tmpSym.getType();
				
				if(tmpN instanceof NumberExpr ){
					tmpInt = (Integer)((IntegerExpr)tmpN).getValue();
					
				}else if(tmpN instanceof ConstantId){
					tmpInt = getIntValue(tmpN );
					
				}else if(tmpN instanceof UnaryOpExpr){
					tmpInt = getIntValue(((UnaryOpExpr) tmpN).operand );
				}
		}else{
			throw new Exception("Error(Line "+node.getLineNumber()+"): "+ node + "cannot be used as an array size declaration." );
		}
			
		return tmpInt;
	}
	
	@Override
	public double getRealValue(ASTNode node) throws Exception{
		double tmpReal = 0;
		Symbol tmpSym;
		ASTNode tmpN;
		
		if (node instanceof NumberExpr){
			tmpReal = ((RealExpr)node).value;
			
		}else if(node instanceof ConstantId || node instanceof UnaryOpExpr){
			
				tmpSym = currSymbolTable.getSymbol( node );
				tmpN = tmpSym.getType();
				
				if(tmpN instanceof NumberExpr ){
					tmpReal = (Double)((RealExpr)tmpN).getValue();
					
				}else if(tmpN instanceof ConstantId){
					tmpReal = getRealValue(tmpN );
					
				}else if(tmpN instanceof UnaryOpExpr){
					tmpReal = getRealValue(((UnaryOpExpr) tmpN).operand );
				}
		}else{
			throw new Exception("Error(Line "+node.getLineNumber()+"): "+ node + "cannot be used as an array size declaration." );
		}
			
		return tmpReal;
	}

	@Override
	public void visit(CompoundStmt stmt) {
		stmt.accept(this,null);
	}

	@Override
	public Symbol getSymbol(ASTNode node) throws Exception {
		return this.currSymbolTable.getSymbol(node);
	}

	@Override
	public Integer getSizeOfArrayRecord(ASTNode node) {
		return currSymbolTable.getSizeOfArrayRecord(node);
	}

	@Override
	public boolean isArray(ASTNode node) {
		return currSymbolTable.isArray(node);
	}

	@Override
	public boolean isRecord(ASTNode node) {
		return currSymbolTable.isRecord(node);
	}

	@Override
	public boolean isGlobalVariable(ASTNode node) {
		return currSymbolTable.isGlobalVariable(node);
	}

	@Override
	public void switchAddress(boolean on) {
		currSymbolTable.switchAddress(on);
	}

	
	@Override
	public List<Symbol> getAllSymbols(){
		return currSymbolTable.getAllSymbols();
	}

	@Override
	public boolean isAddress() {
		// TODO Auto-generated method stub
		return currSymbolTable.getIsAddress();
	}
	
	@Override
	public boolean isFunction( ASTNode node ){
		return currSymbolTable.isFunction(node);
	}	
}

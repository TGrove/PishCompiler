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
			this.currSymbolTable.addChildTable(temp);
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
	public boolean registerSymbol(ASTNode node, ASTNode type) {
		try{
			currSymbolTable.put(node, type);
			return true;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return false;
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
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public SystemType lookup(ASTNode node) {
		try{
			return currSymbolTable.get(node);
		}catch(Exception e){
		//	e.printStackTrace();
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
}

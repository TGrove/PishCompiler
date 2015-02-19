package pish.ast;

import pish.symbol.SymbolVisitor;

public class Program extends ASTNode {
	
	public IDNode id;
	public ConstantList constantList;
	public TypeDefList typeDefList; 
	public SimpleDeclerationList simpleDecList;
	public SubProgramDeclerationList subProgramList;
	public StmtList stmtList;
	
	
	public Program ( IDNode id , ConstantList constantList, TypeDefList typeDefList
			,SimpleDeclerationList simpleDecList , SubProgramDeclerationList subProgramList,StmtList stmtList){
		
		this.id = id;
		this.constantList = constantList;
		this.simpleDecList = simpleDecList;
		this.typeDefList = typeDefList;
		this.subProgramList = subProgramList;
		this.stmtList = stmtList;
	}

	public String toString(){
		return "Program";
	}

	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(id, level);
		id.accept(visit, level+1);
		if ( constantList != null){
			visit.visit(constantList, level);
			constantList.accept(visit, level+1);
		}
		if ( typeDefList != null ){
			visit.visit(typeDefList, level);
			typeDefList.accept(visit, level+1);
		}
		if ( simpleDecList != null ){
			visit.visit(simpleDecList, level);
			simpleDecList.accept(visit, level+1);
		}
		if ( subProgramList != null ){
			visit.visit(subProgramList, level);
			subProgramList.accept(visit, level+1);
		}
		if ( stmtList != null ) {
			visit.visit(stmtList, level);
			stmtList.accept(visit, level+1);
		}
		visit.visit("]",level);
	}


	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		/*
		 * TO DO:
		 * 	1. load (INT,REAL,CHAR, WRITEINT, WRITEREAL, WRITECHAR , TRUE, FALSE , MAXINT) 
		 * 
		 */
		
		visit.openScope(this);
		visit.registerSymbol(new IDNode("TRUE"), new IntegerExpr(1));
		visit.registerSymbol(new IDNode("FALSE"), new IntegerExpr(0));
		visit.registerSymbol(new IntType(), SystemType.INT());
		visit.registerSymbol(new RealType(), SystemType.REAL());
		visit.registerSymbol(new CharType(), SystemType.CHAR());
		visit.registerSymbol(new IntegerExpr(0), SystemType.INT());
		visit.registerSymbol(new RealExpr(0), SystemType.REAL());
		visit.registerSymbol(new CharExpr(' '), SystemType.CHAR());
		visit.registerSymbol(new StringExpr(""), SystemType.STRING());
		visit.registerSymbol(new RecordType(), SystemType.RECORD());
		visit.registerSymbol(new FuncVar("WRITEINT"), SystemType.NULL());
		visit.registerSymbol(new FuncVar("WRITEREAL"), SystemType.NULL());
		visit.registerSymbol(new FuncVar("WRITECHAR"), SystemType.NULL());
		visit.registerMethod(new FuncVar("WRITEINT"), new SimpleDeclerationList(new SimpleDecleration(new VariableList(new IDNode("X")),new IntType())));
		visit.registerMethod(new FuncVar("WRITEREAL"), new SimpleDeclerationList(new SimpleDecleration(new VariableList(new IDNode("X")),new RealType())));
		visit.registerMethod(new FuncVar("WRITECHAR"), new SimpleDeclerationList(new SimpleDecleration(new VariableList(new IDNode("X")),new CharType())));
		
		
		if ( constantList != null){
			visit.visit(constantList);
		}
		if ( typeDefList != null ){
			visit.visit(typeDefList);
		}
		if ( simpleDecList != null ){
			visit.visit(simpleDecList);
		}
		if ( subProgramList != null ){
			visit.visit(subProgramList);
		}
		if ( stmtList != null ) {
			visit.visit(stmtList);
		}
		
		visit.closeScope();
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		// TODO Auto-generated method stub
		return null;
	}
}

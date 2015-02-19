package pish.ast;

import pish.symbol.SymbolVisitor;

public class StmtList extends ASTNode {
	
	public Stmt  stmt;
	public StmtList list;
	
	public StmtList(Stmt stmt) {
		this(stmt,null);
	}

	public StmtList(Stmt stmt, StmtList list) {
		this.stmt = stmt;
		this.list = list;
	}

	public String toString(){
		return "StmtList";
	}
	
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(stmt, level);
		stmt.accept(visit, level+1);
		if ( list != null ) {
			visit.visit(list, level);
			list.accept(visit, level+1);
		}
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		if ( list != null ) {
			visit.visit(list);
		}
		visit.visit(stmt);		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		// TODO Auto-generated method stub
		return null;
	}
}

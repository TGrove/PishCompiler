package pish.ast;

import pish.symbol.SymbolVisitor;

public class CompoundStmt extends Stmt {

	public StmtList list;
	
	public CompoundStmt(StmtList list){
		this.list = list;
	}

	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[", level);
		
		list.accept(visit, level+1);
		
		visit.visit("]", level);
		
		
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		visit.openScope(this);
		list.accept(visit, Type);
		visit.closeScope();
	}

	public String toString(){
		return "CompoundStmt";
	}


	@Override
	public SystemType checkType(SymbolVisitor visit) {
		System.out.println("TYPE CHECKING CompoundStmt !!!!! SHOULD NEVER HAPPEND WTH");
		return null;
	}
}

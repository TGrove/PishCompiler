package pish.ast;

import pish.symbol.SymbolVisitor;

public class Constant extends Variable {
	
	public ConstantId id;
	public Expr expr; // right hand side
	
	public Constant(ConstantId id , Expr expr){
		this.id = id;
		this.expr = expr;
	}

	public String toString(){
		return "Constant";
	}

	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(id, level);
		id.accept(visit, level+1);
		visit.visit(expr, level);
		expr.accept(visit, level+1);
		visit.visit("]",level);	
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		visit.registerSymbol(id, checkType(visit));
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return expr.checkType(visit);
	}
	
}

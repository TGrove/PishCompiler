package pish.ast;

import pish.symbol.SymbolVisitor;

public class ConstantList extends Variable {
	public Constant expr;
	public ConstantList list;
	
	public ConstantList(Constant expr){
		this(expr,null);
	}
	
	public ConstantList(Constant expr, ConstantList list){
		this.expr = expr;
		this.list = list;
	}

	public String toString(){
		return "ConstantList";
	}

	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(expr, level);
		expr.accept(visit, level+1);
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
		visit.visit(expr);

	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return null;
	}
}

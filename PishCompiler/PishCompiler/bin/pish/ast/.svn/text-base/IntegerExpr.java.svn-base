package pish.ast;

import pish.JavaSymbol;
import pish.symbol.SymbolVisitor;

public class IntegerExpr extends NumberExpr {
	public int value;
	
	public IntegerExpr(int value) {
		this.value = value;
	}
	
	public IntegerExpr(JavaSymbol id2) {
		value = (Integer)id2.getValue();
		setLineNumber(id2.getLine());
	}

	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(value, level);				
		visit.visit("]",level);
	}	
	
	public String toString() {
		return "IntegerExpr";
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);
		
		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return SystemType.INT();
	}
}

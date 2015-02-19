package pish.ast;

import pish.symbol.SymbolVisitor;

public class CharExpr extends Variable {
	public  char value;
	
	public CharExpr(char val){
		this.value = val;
	}
	
	public String toString(){
		return "CharExpr";
	}

	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(value, level);		
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return SystemType.CHAR();
	}
}

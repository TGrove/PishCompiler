package pish.ast;

import pish.symbol.SymbolVisitor;

public class IntType extends Type {
	public IntType(){}

	public String toString(){
		return "IntType";
	}
	
	@Override
	public void accept(PrinterVisitor visit, int level) {	
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

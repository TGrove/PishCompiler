package pish.ast;

import pish.symbol.SymbolVisitor;

public class RealType extends Type {
	public RealType(){}

	public String toString(){
		return "RealType";
	}
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return SystemType.REAL();
	}
}

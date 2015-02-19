package pish.ast;

import pish.symbol.SymbolVisitor;

public class CharType extends Type {
	public CharType(){}

	public String toString(){
		return "CharType";
	}
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return SystemType.CHAR();
	}
}

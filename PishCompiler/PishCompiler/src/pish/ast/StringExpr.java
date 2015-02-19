package pish.ast;

import pish.JavaSymbol;
import pish.symbol.SymbolVisitor;

public class StringExpr extends ConstantExpr {
	public String value;
	
	public StringExpr( String value ){
		this.value = value;
	}

	public StringExpr(JavaSymbol id2) {
		this((String)id2.getValue());
		setLineNumber(id2.getLine());
	}

	public String toString(){
		return "StringVar";
	}
	
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(value, level);		
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return SystemType.STRING();
	}
}

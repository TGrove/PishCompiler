package pish.ast;

import pish.JavaSymbol;
import pish.symbol.SymbolVisitor;

public class FuncVar extends Variable {
	public String id;
	
	public FuncVar(String id) { 
		this.id = id;
	}
	
	public FuncVar(JavaSymbol id2) {
		this((String)id2.getValue());
		setLineNumber(id2.getLine());
	}

	public String toString(){
		return id;
	}

	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(id,level);
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return visit.lookup(this);
	}
}

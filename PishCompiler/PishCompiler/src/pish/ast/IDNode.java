package pish.ast;

import pish.JavaSymbol;
import pish.symbol.SymbolVisitor;


public class IDNode extends Variable {

	public String id;
	
	
	public IDNode( String id ) {
		this.id = id;
	}

	public IDNode(JavaSymbol id2) {
		id = (String)id2.getValue();
		setLineNumber(id2.getLine());
	}

	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit(id, level);		
	}

	public String toString(){
		return id;
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		visit.registerSymbol(this, Type);
		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return visit.lookup(this);
	}
}

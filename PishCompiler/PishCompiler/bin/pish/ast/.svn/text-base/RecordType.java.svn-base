package pish.ast;

import pish.symbol.SymbolVisitor;

public class RecordType extends Type {
	public RecordList list;
	
	public RecordType() {
		this(null);
	}

	public RecordType( RecordList list){
		this.list = list;
	}
	
	public String toString(){
		return "RecordType";
	}

	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		list.accept(visit, level+1);
		visit.visit("]",level);
		
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		visit.visit(list);
		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		// TODO Auto-generated method stub
		return null;
	}
}

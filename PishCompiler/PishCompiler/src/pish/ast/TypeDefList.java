package pish.ast;

import pish.symbol.SymbolVisitor;

public class TypeDefList extends Decleration {

	public TypeDef type;
	public TypeDefList list;
	
	public TypeDefList(TypeDef type){
		this(type,null);
	}
	
	public TypeDefList(TypeDef type, TypeDefList list){
		this.type = type;
		this.list = list;
	}

	public String toString(){
		return "TypeDefList";
	}
	
	
	@Override
	public void accept(PrinterVisitor visitor, int level) {
		visitor.visit("[",level);
		visitor.visit(type, level);
		type.accept(visitor, level+1);
		if ( list != null ) {
			visitor.visit(list, level);
			list.accept(visitor, level+1);
		}
		visitor.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visitor, Variable Type) {
		if ( list != null ) {
			visitor.visit(list);
		}	
		visitor.visit(type);
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		// TODO Auto-generated method stub
		return null;
	}
}

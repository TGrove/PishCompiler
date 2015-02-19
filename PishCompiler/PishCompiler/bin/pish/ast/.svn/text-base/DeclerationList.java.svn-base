package pish.ast;

import pish.symbol.SymbolVisitor;

public class DeclerationList extends ASTNode {
	public Decleration decleration;
	public DeclerationList list;
	
	public DeclerationList(Decleration decleration ){
		this(decleration, null);
	}
	
	public DeclerationList( Decleration decleration , DeclerationList list){
		this.decleration = decleration ; 
		this.list = list;
	}

	public String toString(){
		return "DeclerationList";
	}

	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(decleration, level);
		decleration.accept(visit, level+1);
		if ( list != null ) {
			visit.visit(list, level);
			list.accept(visit, level+1);
		}
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);
		visit.visit(decleration);
		if (list != null ) {
			visit.visit(list);
		}
		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return decleration.checkType(visit);
	}
	
	
}

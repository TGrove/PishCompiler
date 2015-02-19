package pish.ast;

import pish.symbol.SymbolVisitor;

public class TypeDef extends Decleration {

	public IDNode id;
	public Variable type;
	
	public TypeDef( IDNode id, Variable type){
		this.id = id;
		this.type = type;
	}

	public String toString(){
		return "TypeDef";
	}
	
	@Override 
	public int getLineNumber(){
		return this.id.getLineNumber();
	}
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(id, level);
		id.accept(visit, level+1);
		visit.visit(type, level);
		type.accept(visit, level+1);
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);
		visit.registerSymbol(id, type);
		if ( type instanceof RecordType ){
			
			visit.createRecordScope(id);
			visit.visit(type);
			try{
				visit.finishRecordScope(id);
			}catch(Exception e ){
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return type.checkType(visit);
	}
	
}

package pish.ast;

import pish.symbol.SymbolVisitor;
import java.util.*;
public class SimpleDecleration extends Decleration {
	public VariableList ids;
	public Variable type;
	
	public SimpleDecleration( VariableList id , Variable type){
		this.ids = id;
		this.type = type;
		
	}


	public String toString(){
		return "SimpleDecleration";
	}
	
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(ids, level);
		ids.accept(visit, level+1);
		visit.visit(type, level);
		type.accept(visit, level+1);
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {

		checkType(visit);
		ids.accept(visit, type);
	}

	public List<SystemType> toArray(SymbolVisitor visit){
		Stack<SystemType> ret = new Stack<SystemType>();
		if ( ids != null ) {
			List<SystemType> tempList = ids.toArray(visit);
			for ( SystemType s : tempList ){
				ret.push(type.checkType(visit));
			}
		
		}
		return ret;
	}
	
	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return type.checkType(visit);
	}
}

package pish.ast;

import java.util.List;
import java.util.Stack;

import pish.symbol.SymbolVisitor;



public class VariableList extends Expr {
	public Variable id;
	public VariableList list;
	
	public VariableList(Variable id) {
		this(id, null);
	}
	public VariableList(Variable id,VariableList list) {
		this.id = id;
		this.list = list;
	}
	
	public String toString(){
		return "VariableList";
	}
	
	public List<SystemType> toArray(SymbolVisitor visit){
		Stack<SystemType> ret = new Stack<SystemType>();
		if ( list != null ) {
			List<SystemType> tempList = list.toArray(visit);
			for ( SystemType s : tempList ){
				ret.push(s);
			}
		}
		ret.push(SystemType.NULL());
	
		return ret;
	}
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(id, level);
		id.accept(visit, level+1);
		if ( list != null ) {
			visit.visit(list, level);
			list.accept(visit, level+1);
		}
		visit.visit("]",level);
		
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		if ( list != null ) {
			list.accept(visit, Type);
		}
		visit.registerSymbol(id, Type);
		if ( Type instanceof RecordType ){
			
			visit.createRecordScope(id);
			visit.visit(Type);
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
		// TODO Auto-generated method stub
		return null;
	}
}

package pish.ast;

import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
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

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		if(list == null){
			return decleration.getSize(visitor);
		}else{
			return list.getSize(visitor) + decleration.getSize(visitor);
		}
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Temp genCode(IntermediateVisitor visitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Temp> getRequiredTemps() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

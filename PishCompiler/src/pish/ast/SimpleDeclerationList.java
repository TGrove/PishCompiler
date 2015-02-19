package pish.ast;

import java.util.*;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class SimpleDeclerationList extends Decleration {
	public SimpleDecleration simpleDec;
	public SimpleDeclerationList list;
	
	public SimpleDeclerationList(SimpleDecleration simpleDec){
		this(simpleDec,null);
	}

	public SimpleDeclerationList(SimpleDecleration simpleDec , SimpleDeclerationList list) {
		 this.simpleDec = simpleDec;
		 this.list = list;
	}

	public String toString(){
		return "SimpleDeclerationList";
	}
	
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(simpleDec, level);
		simpleDec.accept(visit, level+1);
		if ( list != null ) {
			visit.visit(list, level);
			list.accept(visit, level+1);
		}
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		if ( list != null ) {
			visit.visit(list);
		}	
		visit.visit(simpleDec);
	}

	
	
	public List<SystemType> toArray(SymbolVisitor visit){
		Stack<SystemType> ret = new Stack<SystemType>();
		if ( list != null ) {
			List<SystemType> tempList = list.toArray(visit);
			for ( SystemType s : tempList ){
				ret.push(s);
			}
		}
		for ( SystemType s : simpleDec.toArray(visit) )
			ret.push(s);
		return ret;
	}
	
	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return simpleDec.checkType(visit);
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		if(list == null){
			return simpleDec.getSize(visitor);
		}else{
			return list.getSize(visitor) 
				+ simpleDec.getSize(visitor);
		}
		
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		if ( list != null ) {
			visitor.visit(list);
		}	
		visitor.visit(simpleDec);	
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
		List<Temp> temps = new ArrayList<Temp>();

		return temps;
	}
}


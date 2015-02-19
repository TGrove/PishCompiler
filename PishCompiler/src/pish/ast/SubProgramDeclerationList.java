package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class SubProgramDeclerationList extends Decleration {

	public SubProgramDecleration function;
	public SubProgramDeclerationList list;
	
	public SubProgramDeclerationList( SubProgramDecleration function ){
		this(function,null);
	}
	
	public SubProgramDeclerationList(SubProgramDecleration function,	SubProgramDeclerationList list) { 
		this.function = function;
		this.list = list;
	}

	public String toString(){
		return "SubProgramDeclerationList";
	}
	
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(function, level);
		function.accept(visit, level+1);
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
		visit.visit(function);
		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		if ( list != null ) {
			visitor.visit(list);
		}
		visitor.visit(function);
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

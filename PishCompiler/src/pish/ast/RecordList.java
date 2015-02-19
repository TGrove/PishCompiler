package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class RecordList extends Variable {
	
	public SimpleDeclerationList list;
	
	public RecordList(SimpleDeclerationList list){
		this.list = list;
	}
	
	public String toString(){
		return "RecordList";
	}
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[", level);
		
		list.accept(visit, level+1);
		
		visit.visit("]", level);
		
		
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

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		return list.getSize(visitor);
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
		List<Temp> temps = new ArrayList<Temp>();

		return temps;
	}

}

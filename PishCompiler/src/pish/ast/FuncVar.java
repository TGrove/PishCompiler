package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.JavaSymbol;
import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class FuncVar extends Variable {
	public String id;
	
	public FuncVar(String id) { 
		this.id = id;
	}
	
	public FuncVar(JavaSymbol id2) {
		this((String)id2.getValue());
		setLineNumber(id2.getLine());
	}

	public String toString(){
		return id;
	}

	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(id,level);
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return visit.lookup(this);
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		return null;
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

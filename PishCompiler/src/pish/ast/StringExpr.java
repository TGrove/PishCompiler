package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.JavaSymbol;
import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class StringExpr extends ConstantExpr {
	public String value;
	
	public StringExpr( String value ){
		this.value = value;
	}

	public StringExpr(JavaSymbol id2) {
		this((String)id2.getValue());
		setLineNumber(id2.getLine());
	}

	public String toString(){
		return "StringExpr";
	}
	
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(value, level);		
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return SystemType.STRING();
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		// TODO Auto-generated method stub
		return value.length() * SystemType.Type.CHAR.getMemoryBytes();
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValue() {
		return value;
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

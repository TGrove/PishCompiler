package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class RealType extends Type {
	public RealType(){}

	public String toString(){
		return "RealType";
	}
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return SystemType.REAL();
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		return SystemType.Type.REAL.getMemoryBytes();
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

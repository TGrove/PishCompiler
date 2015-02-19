package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class CharType extends Type {
	public CharType(){}

	public String toString(){
		return "CharType";
	}
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return SystemType.CHAR();
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		return SystemType.Type.CHAR.getMemoryBytes();
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

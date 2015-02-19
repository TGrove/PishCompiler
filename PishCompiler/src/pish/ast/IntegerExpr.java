package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.JavaSymbol;
import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class IntegerExpr extends NumberExpr {
	public int value;
	Temp requiredTemp = Temp.getNewTemp();
	
	
	public IntegerExpr(int value) {
		this.value = value;
		requiredTemp.setAddress(false);
	}
	
	public IntegerExpr(JavaSymbol id2) {
		value = (Integer)id2.getValue();
		setLineNumber(id2.getLine());
		requiredTemp.setAddress(false);
	}

	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(value, level);				
		visit.visit("]",level);
	}	
	
	public String toString() {
		return "IntegerExpr";
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);

		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return SystemType.INT();
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		return SystemType.Type.INT.getMemoryBytes();
	}
	
	@Override
	public Object getValue(){
		return this.value;
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Temp genCode(IntermediateVisitor visitor) {
		if ( visitor.isIntermediateCode() ){
			requiredTemp.setOffSet(visitor.getSymbolVisitor().getCurrOffSet());
			visitor.getSymbolVisitor().incrementOffSetCounter(requiredTemp.getSizeOf());				
		}else{
			visitor.writeAssembly("\t li \t $t0, "+value);
			visitor.writeAssembly("\t sw \t $t0, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
		}
		return requiredTemp;
	}

	@Override
	public List<Temp> getRequiredTemps() {
		List<Temp> temps = new ArrayList<Temp>();
		temps.add(requiredTemp);
		return temps;
	}
}

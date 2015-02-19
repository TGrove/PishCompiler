package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.JavaSymbol;
import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class RealExpr extends NumberExpr {
	public double value;
	Temp requiredTemp = Temp.getNewTemp();
	public static int floatNum = 0;
	
	
	public RealExpr(double value){
		this.value = value;
		requiredTemp.setAddress(false);
		
	}

	public RealExpr(JavaSymbol id2) {
		value = (Double)id2.getValue();
		setLineNumber(id2.getLine());
		requiredTemp.setAddress(false);
	}

	public String toString(){
		return "RealExpr";
	}
	
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(value, level);
		visit.visit("]",level);
		
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);	
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
			String f = getNextFloat();
			visitor.writeAssembly(".data");
			visitor.writeAssembly("\t"+f+":\t.float "+value);
			visitor.writeAssembly(".text");					
			visitor.writeAssembly("\t l.s \t $f3, "+f);
			visitor.writeAssembly("\t s.s \t $f3, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
		}
		return requiredTemp;
	}

	@Override
	public List<Temp> getRequiredTemps() {
		List<Temp> temps = new ArrayList<Temp>();
		temps.add(requiredTemp);
		return temps;
	}
	
	public static String getNextFloat(){
		return "float"+floatNum++;
	}
}

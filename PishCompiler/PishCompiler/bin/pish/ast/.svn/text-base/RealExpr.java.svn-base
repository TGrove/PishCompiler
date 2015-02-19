package pish.ast;

import pish.JavaSymbol;
import pish.symbol.SymbolVisitor;

public class RealExpr extends NumberExpr {
	public  double value;
	
	public RealExpr(double value){
		this.value = value;
	}

	public RealExpr(JavaSymbol id2) {
		value = (Double)id2.getValue();
		setLineNumber(id2.getLine());
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return SystemType.REAL();
	}
}

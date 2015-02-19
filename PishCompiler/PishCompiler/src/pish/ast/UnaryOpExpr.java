package pish.ast;

import pish.symbol.SymbolVisitor;

public class UnaryOpExpr extends Expr {
	public Operator op;
	public Expr operand;
	
	public UnaryOpExpr(Expr operand, Operator op) {
		this.operand = operand;
		this.op = op;
	}
	
	public String toString() {
		return "UnaryOpExpr";
	}

	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(op, level);
		visit.visit(operand, level);
		operand.accept(visit, level+1);
		visit.visit("]",level);
	
	}
	@Override 
	public int getLineNumber(){
		return this.operand.getLineNumber();
	}
	
	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);
		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		SystemType operandType = operand.checkType(visit);
		if ( operandType.getType() != SystemType.Type.INT && operandType.getType() != SystemType.Type.REAL  ){
			System.out.println("ERROR (line "+getLineNumber()+") cannot assign a ("+op+") to a ("+operand+"("+operandType+"))");
		}
		return operandType;
	}
}

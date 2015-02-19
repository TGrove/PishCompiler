package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.*;
import pish.symbol.SymbolVisitor;

public class UnaryOpExpr extends Expr {
	public Operator op;
	public Expr operand;
	public Temp requiredTemp = Temp.getNewTemp();
	
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

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		return operand.getSize(visitor);
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
	}

	@Override
	public Object getValue() {
		return null;
	}

	public int getSign(){
		int ret = 1;
		if ( op == Operator.MINUS)
			ret =  -1;
		if ( operand instanceof UnaryOpExpr){
			ret *= ((UnaryOpExpr)operand).getSign();
		}
		return ret;
	}
	
	@Override
	public Temp genCode(IntermediateVisitor visitor) {
		if ( visitor.isIntermediateCode()){
			requiredTemp.setOffSet(visitor.getSymbolVisitor().getCurrOffSet());
			visitor.getSymbolVisitor().incrementOffSetCounter(requiredTemp.getSizeOf());	
			Temp r =  operand.genCode(visitor);
			visitor.writeIntermediateCode(requiredTemp+" := 0 "+op.toIntermediateCode()+" "+ r);
		}else{
		/*Assembly*/
			
			
		}
		return requiredTemp;
	}

	public ASTNode getOperand() {
		if ( operand instanceof UnaryOpExpr )
			return ((UnaryOpExpr)operand).getOperand();
		return operand;
	}

	@Override
	public List<Temp> getRequiredTemps() {
		List<Temp> temps = new ArrayList<Temp>();
		temps.addAll(operand.getRequiredTemps());
		temps.add(requiredTemp);
		
		return temps;
	}
}

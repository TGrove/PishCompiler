package pish.ast;

import pish.JavaSymbol;
import pish.symbol.SymbolVisitor;

public class BinaryOpExpr extends Expr {
	public Expr lExpr;
	public Expr rExpr;
	public Operator op;

	public BinaryOpExpr(Expr lExpr,String op, Expr rExpr){
		this(lExpr,Operator.lookup(op),rExpr);
	}

	public BinaryOpExpr(Expr lExpr,Operator op, Expr rExpr){
		this.lExpr=lExpr;
		this.op=op;
		this.rExpr=rExpr;
	}

	public BinaryOpExpr(Expr e1, JavaSymbol r, Expr e2) {
		this(e1,(String)r.getValue(),e2);
		setLineNumber(r.getLine());
	}

	
	@Override 
	public int getLineNumber(){
		return this.lExpr.getLineNumber();
	}
	
	public String toString(){
		return "BinaryOpExpr";
	}

	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(lExpr, level);
		lExpr.accept(visit, level+1);
		visit.visit(op, level);
		visit.visit(rExpr, level);
		rExpr.accept(visit, level+1);
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);
		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		SystemType lType = lExpr.checkType(visit);
		SystemType rType = rExpr.checkType(visit);
		if ( lType.getType() != rType.getType() ){
			System.out.println("Error (line "+getLineNumber()+") cannot perform comparison using ("+op+")  for ("+lExpr+"("+lType+")) to ("+rExpr+"("+rType+")) ");
		}
		if ( op.isRelational() )
		{
			return SystemType.INT();
		}
		return lType;
	}
}

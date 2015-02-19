package pish.ast;

import pish.symbol.SymbolVisitor;

public class WhileStmt extends Stmt {
	public Expr expr;
	public StmtList body;
	
	public WhileStmt(Expr expr, StmtList body)
	{
		this.expr = expr;
		this.body = body;
	}

	public String toString(){
		return "WhileStmt";
	}
	
	@Override 
	public int getLineNumber(){
		return this.expr.getLineNumber();
	}
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(expr, level);
		expr.accept(visit, level+1);
		visit.visit(body, level);
		body.accept(visit, level+1);
		
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);
		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		SystemType exprType = expr.checkType(visit);
		if ( exprType.getType() != SystemType.Type.INT ){
			System.out.println("ERROR(line "+getLineNumber()+"): invalid expr("+expr+") in while statement. expression returns ("+exprType+") where it should return (INT) ");
		}
		return exprType;
	}
}

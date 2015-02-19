package pish.ast;

import pish.symbol.SymbolVisitor;

public class IfStmt extends Stmt {
	public Expr expr;
	public StmtList thenPart, elsePart;
	
	public IfStmt ( Expr expr, StmtList thenPart) { 
		this(expr,thenPart,null);
	}
	
	public IfStmt ( Expr expr, StmtList thenPart , StmtList elsePart ) { 
		this.expr = expr;
		this.thenPart = thenPart;
		this.elsePart = elsePart;
	}

	public String toString(){
		return "IfStmt";
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
		visit.visit(thenPart, level);
		thenPart.accept(visit, level+1);
		if ( elsePart != null ) {
			visit.visit(elsePart, level);
			elsePart.accept(visit, level+1);
		}
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);
		thenPart.accept(visit, Type);
		if ( elsePart != null ) {
			elsePart.accept(visit, Type);
		}
		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		SystemType exprType = expr.checkType(visit);
		if ( exprType.getType() != SystemType.Type.INT )
		{
			System.out.println("ERROR(line "+getLineNumber()+"): invalid expr("+expr+") in if statement. expression returns ("+exprType+") where it should return (INT) ");
		}
		
		
		return null;
	}

}

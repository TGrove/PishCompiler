package pish.ast;

import pish.symbol.SymbolVisitor;

public class AssignStmt extends Stmt {
	public Variable id;
	public Expr expr; // right hand side

	public AssignStmt(Variable id, Expr expr) {
		this.id = id;
		this.expr = expr;
	}

	public String toString(){
		return "AssignStmt";
	}

	@Override 
	public int getLineNumber(){
		return this.id.getLineNumber();
	}
	
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(id, level);
		id.accept(visit, level+1);
		visit.visit(expr, level);
		expr.accept(visit, level+1);
		visit.visit("]",level);
	
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);	
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		SystemType idType = id.checkType(visit);
		SystemType exprType = expr.checkType(visit);
		if ( idType.getType() == SystemType.Type.INT && exprType.getType() == SystemType.Type.REAL)
		{
			System.out.println("Error (line "+getLineNumber()+") cannot assign ("+expr+"("+exprType+")) to ("+id+"("+idType+"))");
		}
		else if ( idType.getType() == SystemType.Type.CHAR && exprType.getType() != SystemType.Type.CHAR)
		{
			System.out.println("Error (line "+getLineNumber()+") cannot assign ("+expr+"("+exprType+")) to ("+id+"("+idType+"))");
		}
		else if ( idType.getType() == SystemType.Type.STRING && exprType.getType() != SystemType.Type.STRING)
		{
			System.out.println("Error (line "+getLineNumber()+") cannot assign ("+expr+"("+exprType+")) to ("+id+"("+idType+"))");
		}
		else if (idType.getType() == SystemType.Type.RECORD || exprType.getType() == SystemType.Type.RECORD  )
		{
			System.out.println("Error (line "+getLineNumber()+") cannot assign ("+expr+"("+exprType+")) to ("+id+"("+idType+"))");			
		}
		return exprType;
	}
}

package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Line;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class IfStmt extends Stmt {
	public Expr expr;
	public StmtList thenPart, elsePart;
	Line elseL	 = Line.getNewLine();
	Line thenL = Line.getNewLine();

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
			System.out.println("Error (line "+getLineNumber()+"): invalid expr("+expr+") in if statement. expression returns ("+exprType+") where it should return (INT) ");
		}
		
		
		return null;
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		return null;
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		if ( visitor.isIntermediateCode()){
			visitor.writeIntermediateCode("#BEIGN IF STMT");
			visitor.addTab(1);		
			Temp t = expr.genCode(visitor);
			visitor.writeIntermediateCode("if "+ t +" := 0 goto "+elseL);
			visitor.visit(thenPart);
			visitor.writeIntermediateCode("goto "+thenL);
			visitor.writeIntermediateCode(elseL+": nop");
			if ( elsePart != null ) {
				visitor.visit(elsePart);
			}			
			visitor.writeIntermediateCode(thenL+": nop");
			visitor.addTab(-1);
			visitor.writeIntermediateCode("#END IF STMT");
		}
		else{
			visitor.addTab(1);	
			visitor.writeAssembly("#START OF IF STMT");
			Temp t = expr.genCode(visitor);
			switch( expr.checkType(visitor.getSymbolVisitor()).getType() )
			{
				case INT:
					visitor.writeAssembly("\t lw \t $t1,-"+t.getOffSet()+FRAMEPOINTER);
					break;
				case REAL:		
			}
			visitor.writeAssembly("\t beqz \t $t1 ,"+elseL );
			visitor.writeAssembly("\t\t#THEN STMT");
			visitor.visit(thenPart);
			visitor.writeAssembly("j "+thenL);
			visitor.writeAssembly(elseL+": #ELSE IF STMT");
			if ( elsePart != null ) {
				visitor.visit(elsePart);
			}
			visitor.writeAssembly(thenL+": #END IF STMT");
			visitor.addTab(-1);			
		}
	}

	@Override
	public Object getValue() {
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
		temps.addAll(expr.getRequiredTemps());
		temps.addAll(thenPart.getRequiredTemps());
		if ( elsePart != null )
		{
			temps.addAll(elsePart.getRequiredTemps());
		}
		return temps;

	}

}

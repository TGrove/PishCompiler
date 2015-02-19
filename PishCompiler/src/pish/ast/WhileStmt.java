package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Line;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class WhileStmt extends Stmt {
	public Expr expr;
	public StmtList body;
	Line begin = Line.getNewLine();
	Line end = Line.getNewLine();

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
		visit.visit(body);
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		SystemType exprType = expr.checkType(visit);
		if ( exprType.getType() != SystemType.Type.INT ){
			System.out.println("ERROR(line "+getLineNumber()+"): invalid expr("+expr+") in while statement. expression returns ("+exprType+") where it should return (INT) ");
		}
		return exprType;
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		if ( visitor.isIntermediateCode() ){
			visitor.writeIntermediateCode("#BEGIN WHILE LOOP");
			visitor.writeIntermediateCode(begin+": nop");
			visitor.addTab(1);		
			Temp t = expr.genCode(visitor);
			visitor.writeIntermediateCode("if "+ t +" := 0 goto "+end );
			visitor.visit(body);
			visitor.writeIntermediateCode("goto "+begin);
			visitor.addTab(-1);			
			visitor.writeIntermediateCode(end+": nop");
			visitor.writeIntermediateCode("#END WHILE LOOP");
		}else{
			visitor.writeAssembly("");
			visitor.writeAssembly(begin+": #BEGIN WHILE LOOP");
			visitor.addTab(1);		
			Temp t = expr.genCode(visitor);
			switch( expr.checkType(visitor.getSymbolVisitor()).getType() )
			{
				case INT:
					visitor.writeAssembly("\t lw \t $t1,-"+t.getOffSet()+FRAMEPOINTER);
					break;
				case REAL:		
			}
			visitor.writeAssembly("\t beqz \t $t1 ,"+end );
			visitor.visit(body);
			visitor.writeAssembly("j "+begin);
			visitor.addTab(-1);			
			visitor.writeAssembly(end+": \t #END WHILE LOOP");
			visitor.writeAssembly("");		
		}
		
		
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Temp genCode(IntermediateVisitor visitor) {

		return null;
	}

	@Override
	public List<Temp> getRequiredTemps() {
		List<Temp> temps = new ArrayList<Temp>();
		temps.addAll(expr.getRequiredTemps());
		temps.addAll(body.getRequiredTemps());
		return temps;
	}
}

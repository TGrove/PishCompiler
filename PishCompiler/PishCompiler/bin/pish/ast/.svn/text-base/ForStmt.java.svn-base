package pish.ast;

import pish.symbol.SymbolVisitor;

/**
 * FOR ID ASSIGN num forprime num DO
 * */
public class ForStmt extends Stmt {
	public IDNode id;
	public Variable fromNum;
	public Variable toNum;
	public StmtList body;
	public boolean downTo;
	
	public ForStmt(String id, Variable fromNum, Variable toNum, StmtList body ,boolean downTo)	{
		this(new IDNode(id) , fromNum , toNum, body,downTo);
	}
	
	public ForStmt(IDNode id, Variable fromNum, Variable toNum, StmtList body ,boolean downTo)	{
		this.id = id;
		this.fromNum = fromNum;
		this.toNum = toNum; 
		this.body = body;
		this.downTo = downTo;
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
		visit.visit(fromNum, level);
		fromNum.accept(visit, level+1);
		if(downTo){	
			visit.visit("downto", level);
		}
		else{
			visit.visit("to", level);
		}
		visit.visit(toNum, level);
		toNum.accept(visit, level+1);
		visit.visit(body, level);
		body.accept(visit, level+1);		
		visit.visit("]",level);
	}
	
	public String toString()  {
		return "ForStmt";
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);
		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		SystemType fromType = fromNum.checkType(visit);
		SystemType toType = toNum.checkType(visit);
		if ( fromType.getType() != SystemType.Type.INT ){
			System.out.println("Error(line "+getLineNumber()+") cannot use From value of type ("+fromType+") inside of a for loop");
		}
		if ( toType.getType() != SystemType.Type.INT){
			System.out.println("Error(line "+getLineNumber()+") cannot use To value of type ("+toType+") inside of a for loop");
		}
		
		boolean found = false;
		if ( fromNum instanceof ConstantId){
			found = true;
		}
		if ( fromNum instanceof ConstantExpr){
			found = true;
		}	
		if ( !found ) {
			System.out.println("Error(line "+getLineNumber()+"): FROM number in for loop  has to be Constant or Number type of INT");		
		}
		found = false;
		if ( toNum instanceof ConstantId){
			found = true;
		}
		if ( toNum instanceof ConstantExpr){
			found = true;
		}	
		if ( !found ) {
			System.out.println("Error(line "+getLineNumber()+"): TO number in for loop has to be Constant or Number type of INT");		
		}
		found = false;
		
		return toType;
	}
}	

package pish.ast;

import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class CompoundStmt extends Stmt {

	String name;
	public StmtList list;
	public SimpleDeclerationList declist;
	public CompoundStmt(StmtList list){
		this(list,null);
	}
	public CompoundStmt(StmtList list,SimpleDeclerationList declist){
		this(list,declist, ASTNode.getNextCompoundStmtName());
	}
	public CompoundStmt(StmtList list,SimpleDeclerationList declist,String name){
		this.list = list;
		// This has been commented out to ignore dec's in compound stmt
		//this.declist = declist;
		this.name = name;
	}

	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[", level);
		if ( declist != null )
			declist.accept(visit, level);
		if ( list != null ){
			list.accept(visit, level+1);
		}
		visit.visit("]", level);
		
		
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		//visit.openScope(this);
		if ( declist != null )
			declist.accept(visit, Type);
		if ( list != null ){
			list.accept(visit, Type);
		}
		//visit.closeScope();
		
	}

	public void setName(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}


	@Override
	public SystemType checkType(SymbolVisitor visit) {
		System.out.println("TYPE CHECKING CompoundStmt !!!!! SHOULD NEVER HAPPEND WTH");
		return null;
	}


	@Override
	public Integer getSize(SymbolVisitor visitor) {
		return null;
	}


	@Override
	public void accept(IntermediateVisitor visitor) {
		
		SymbolVisitor sVisitor = visitor.getSymbolVisitor();
		//sVisitor.openScope(this);
		if ( declist != null ){
			visitor.visit(declist);
		}
		if ( list != null ){
			visitor.visit(list);
		
		}
		//sVisitor.closeScope();
		
	}


	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Temp genCode(IntermediateVisitor visitor) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Temp> getRequiredTemps() {
		return list.getRequiredTemps();
	}
}

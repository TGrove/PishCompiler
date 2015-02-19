package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class StmtList extends ASTNode {
	
	public Stmt  stmt;
	public StmtList list;
	
	public StmtList(Stmt stmt) {
		this(stmt,null);
	}

	public StmtList(Stmt stmt, StmtList list) {
		this.stmt = stmt;
		this.list = list;
	}

	public String toString(){
		return "StmtList";
	}
	
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(stmt, level);
		stmt.accept(visit, level+1);
		if ( list != null ) {
			visit.visit(list, level);
			list.accept(visit, level+1);
		}
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		if ( list != null ) {
			visit.visit(list);
		}
		visit.visit(stmt);		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		// we are visiting the statement no need to check here
		return null;
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		if(list == null){
			return stmt.getSize(visitor);
		}else{
			return list.getSize(visitor) + stmt.getSize(visitor);
		}
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		if ( list != null ) {
			visitor.visit(list);
		}
		visitor.visit(stmt);		
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
		List<Temp> temps = new ArrayList<Temp>();
		if(list == null){
			return stmt.getRequiredTemps();
		}else{
			temps.addAll(list.getRequiredTemps());
			temps.addAll(stmt.getRequiredTemps());
		}
		return temps;
	}
}

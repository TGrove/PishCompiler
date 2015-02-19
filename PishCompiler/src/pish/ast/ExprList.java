package pish.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class ExprList extends Stmt {

	public Expr expr;
	public ExprList list;
	
	public ExprList(Expr expr){
		this(expr,null);
	}
	
	public ExprList(Expr expr,ExprList list) {
		this.expr = expr;
		this.list = list;
	}

	public String toString(){
		return "ExprList";
	}

	public List<SystemType> toArray(SymbolVisitor visit){
		Stack<SystemType> ret = new Stack<SystemType>();
		if ( list != null ) {
			List<SystemType> tempList = list.toArray(visit);
			for ( SystemType s : tempList ){
				ret.push(s);
			}
		}
		ret.push(expr.checkType(visit));
		return ret;
	}
	
	
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(expr, level);
		expr.accept(visit, level+1);
		if ( list != null ) {
			visit.visit(list, level);
			list.accept(visit, level+1);
		}
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		if ( list != null) {
			visit.visit(list);
		}
		checkType(visit);
		visit.visit(expr);
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return expr.checkType(visit);
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		if(list == null){
			return expr.getSize(visitor);
		}else{
			return list.getSize(visitor) + expr.getSize(visitor);
		}
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		if ( list != null) {
			visitor.visit(list);
		}
		expr.genCode(visitor);		
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
		if(list == null){
			return expr.getRequiredTemps();
		}else{
			temps.addAll(list.getRequiredTemps());
			temps.addAll(expr.getRequiredTemps());
		}
		return temps;
	}
}

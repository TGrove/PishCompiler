package pish.ast;

import java.util.List;
import java.util.Stack;

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
		checkType(visit);
		visit.visit(expr);
		if ( list != null) {
			visit.visit(list);
		}
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return expr.checkType(visit);
	}
}

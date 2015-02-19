package pish.ast;

import pish.JavaSymbol;
import pish.symbol.SymbolVisitor;
import java.util.*;
public class FunctionCallExpr extends Expr {

	public FuncVar id;
	public ExprList list;
	
	public FunctionCallExpr(String id, ExprList list) {
		this(new FuncVar(id),list);
	}
	
	public FunctionCallExpr(JavaSymbol id2, ExprList list) {
		this((String)id2.getValue(),list);
		setLineNumber(id2.getLine());
		id.setLineNumber(id2.getLine());
	}
	
	public FunctionCallExpr(FuncVar id, ExprList list) {
		this.id = id;
		this.list = list;
	}	
	


	public String toString(){
		return "CallExpr";
	}


	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(id, level);
		if ( list != null ) {
			visit.visit(list, level);
			list.accept(visit, level+1);
		}		
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		List<SystemType> realArgs = visit.getArgs(id);
		List<SystemType> callArgs = list.toArray(visit);
		
		
		boolean found = false;

		if ( realArgs == null  || callArgs == null ) {
			found = true;
		}
		if ( !found ){
			if ( realArgs.size() != callArgs.size() ){
				System.out.println("Error (line "+getLineNumber()+"): invalid number of arguments in Function/Procedure("+id+") call");
				found = true;
			}
		}
		if ( !found ){
			for ( int i = 0; i < realArgs.size() ; i++ ){
				if ( realArgs.get(i).getType() != callArgs.get(i).getType() )
				{
					System.out.println("Error (line "+getLineNumber()+"): invalid argument("+(i+1)+") expecting type ("+realArgs.get(i).getType()+") and using ("+callArgs.get(i).getType()+") in Function/Procedure("+id+") call");
					found = true;
				}
			}
		}
		
		return id.checkType(visit);
	}
	
}

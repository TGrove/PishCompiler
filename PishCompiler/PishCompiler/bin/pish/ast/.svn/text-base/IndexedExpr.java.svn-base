package pish.ast;

import pish.symbol.SymbolVisitor;

public class IndexedExpr extends Variable {
	public IDNode id;
	public Expr offSet;
	
	
	public IndexedExpr(IDNode id, Expr offSet){
		this.id = id;
		this.offSet =offSet;
	}
	
	public String toString(){
		return id.id;
	}

	@Override
	public int getLineNumber(){
		return id.getLineNumber();
	}
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(id, level);
		id.accept(visit, level+1);
		visit.visit(offSet, level);
		offSet.accept(visit, level+1);		
		visit.visit("]",level);
		
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		SystemType idType = id.checkType(visit);
		SystemType offSetType = offSet.checkType(visit);
		
		if (offSetType.getType() != SystemType.Type.INT ){
			System.out.println("ERROR(line "+getLineNumber()+"): invalid offSet("+offSet+") in Indexed Expression. OffSet returns ("+
					offSetType+") where it should return (INT) ");
		}
		
		return idType;
	}
}

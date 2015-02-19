package pish.ast;

import pish.symbol.SymbolVisitor;

public class ArrayType extends Type {
	public Variable fromNum;
	public Variable toNum;
	public Variable type;
	
	public ArrayType() {
		this(null,null,null );
	}
	
	public ArrayType(Variable	fromNum, Variable	toNum, Variable type){
		this.fromNum = fromNum;
		this.toNum = toNum;
		this.type = type;
	}



	public String toString(){
		return "ArrayType";
	}
	
	@Override
	public int getLineNumber(){
		if ( fromNum == null)
			return 0;
		return fromNum.getLineNumber();
	}
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(fromNum, level);
		fromNum.accept(visit, level+1);
		visit.visit(toNum, level);
		toNum.accept(visit, level+1);
		visit.visit(type, level);
		type.accept(visit, level+1);		
		visit.visit("]",level);
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
			System.out.println("Error (line "+getLineNumber()+") cannot use From value of type ("+fromType+") inside of an array decleration");
		}
		if ( toType.getType() != SystemType.Type.INT){
			System.out.println("Error (line "+getLineNumber()+") cannot use To value of type ("+toType+") inside of an array decleration");
		}
		
		boolean found = false;
		if ( fromNum instanceof ConstantId){
			found = true;
		}
		if ( fromNum instanceof ConstantExpr){
			found = true;
		}	
		if ( !found ) {
			System.out.println("Error(line "+getLineNumber()+"): FROM("+fromType+")  number in array decleration must be (Constant or Number) type of INT");		
		}
		found = false;
		if ( toNum instanceof ConstantId){
			found = true;
		}
		if ( toNum instanceof ConstantExpr){
			found = true;
		}	
		if ( !found ) {
			System.out.println("Error(line "+getLineNumber()+"): TO("+toType+") number in array decleration must be (Constant or Number) type of INT");		
		}
		found = false;
		
		return type.checkType(visit);

	}
	
	
}

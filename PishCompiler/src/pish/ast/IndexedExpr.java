package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class IndexedExpr extends Variable {
	public IDNode id;
	public Expr offSet;
	public Temp requiredTemp = Temp.getNewTemp();
	
	
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
			System.out.println("Error (line "+getLineNumber()+"): invalid offSet("+offSet+") in Indexed Expression. OffSet returns ("+
					offSetType+") where it should return (INT) ");
		}
		
		return idType;
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Temp genCode(IntermediateVisitor visitor) {
		if ( visitor.isIntermediateCode()){

			Temp t = id.genCode(visitor);
			Temp e = offSet.genCode(visitor);
			Temp temp = new Temp("RG#");
			Integer sizeofRec =  visitor.getSymbolVisitor().getSizeOfArrayRecord(id);
			visitor.writeIntermediateCode("#Calculating off set of the record in the array");
			visitor.writeIntermediateCode(temp +" = "+e+" * "+sizeofRec);
			visitor.writeIntermediateCode("#moving the array point to the position of the record");
			requiredTemp.setOffSet(visitor.getSymbolVisitor().getCurrOffSet());
			visitor.getSymbolVisitor().incrementOffSetCounter(requiredTemp.getSizeOf());
			visitor.writeIntermediateCode(requiredTemp +" = "+t+" + "+temp );
		}
		else{
			Temp idt = id.genCode(visitor);
			Temp et = offSet.genCode(visitor);
			Integer sizeofRec =  visitor.getSymbolVisitor().getSizeOfArrayRecord(id);
			
			visitor.writeAssembly("");
			visitor.writeAssembly("\t #Calculating Index of ("+id+")");
			
			visitor.writeAssembly("\t lw \t $t0, -"+et.getOffSet()+"($fp) \t #Index");
			visitor.writeAssembly("\t li \t $t1, "+sizeofRec+" \t #size of element ");
			visitor.writeAssembly("\t mul \t $t2, $t0, $t1 ");
			
			visitor.writeAssembly("\t lw \t $a0, -"+ idt.getOffSet() +"($fp)");
			visitor.writeAssembly("\t add \t $a0, $a0, $t2 \t #Increment Pointer ");
			visitor.writeAssembly("\t sw \t $a0, -"+requiredTemp.getOffSet()+"($fp) \t #store new pointer in temp" );
			visitor.writeAssembly("");
			
			requiredTemp.setAddress(true);
			
		}
		return requiredTemp;
	}

	@Override
	public List<Temp> getRequiredTemps() {
		List<Temp> temps = new ArrayList<Temp>();
		temps.addAll(offSet.getRequiredTemps());
		temps.add(requiredTemp);
		return temps;
	}
}

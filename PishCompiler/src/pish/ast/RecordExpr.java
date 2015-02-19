package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.Symbol;
import pish.symbol.SymbolVisitor;

public class RecordExpr extends Variable {
	public Variable recordId;
	public Variable memberId;
	Temp requiredTemp = Temp.getNewTemp();
	
	
	public RecordExpr ( Variable recordId , Variable memberId ){
		this.recordId = recordId;
		this.memberId = memberId;
	}
	
	
	@Override 
	public int getLineNumber(){
		return this.recordId.getLineNumber();
	}
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		
		recordId.accept(visit, level+1);
		
		memberId.accept(visit, level+1);
		
		visit.visit("]",level);
	}

	
	public String toString() {
		return recordId + "." + memberId;
	}



	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);
	}



	@Override
	public SystemType checkType(SymbolVisitor visit) {
		SystemType recordIdType = recordId.checkType(visit);
		
		SystemType type = SystemType.NULL();
		if ( recordIdType.getType() != SystemType.Type.RECORD )
		{
			System.out.println("Error (line "+getLineNumber()+") variable ("+recordId+"("+recordIdType+")) is not a record type");
		}
		else{
			try{
				visit.openRecordScope(recordId);
			}
			catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			if ( visit.isMember(memberId) || (memberId instanceof RecordExpr) ){
				type = memberId.checkType(visit);
			}
			if (type.getType() == SystemType.Type.NULL)
			{
				System.out.println("Error (line "+memberId.getLineNumber()+") ("+memberId+") is not a member of  ("+recordId+");");			
			}
			try{
				visit.closeRecordScope(recordId);
			}
			catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
				
			}
		}
		return type;

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
			try{
				Temp t = this.recordId.genCode(visitor);
				Temp r = this.memberId.genCode(visitor);			
				requiredTemp.setOffSet(visitor.getSymbolVisitor().getCurrOffSet());
				visitor.getSymbolVisitor().incrementOffSetCounter(requiredTemp.getSizeOf());
				visitor.getSymbolVisitor().openRecordScope(recordId);
				Symbol symbol = visitor.getSymbolVisitor().getSymbol(memberId);
				visitor.writeIntermediateCode("#moving the point to where the offset is for member "+memberId);
				visitor.writeIntermediateCode(requiredTemp+" = "+t+" + "+symbol.getOffSet());
				visitor.getSymbolVisitor().closeRecordScope(recordId);		
			}
			catch (Exception e){	
			}
		}
		else{
			try{
				
				Temp idt = this.recordId.genCode(visitor);
				visitor.getSymbolVisitor().openRecordScope(recordId);
				Temp et = this.memberId.genCode(visitor);	
				Symbol symbol = visitor.getSymbolVisitor().getSymbol(memberId);
				
				visitor.writeAssembly("");
				visitor.writeAssembly("\t #Calculating Index of ("+memberId+")");
			
				visitor.writeAssembly("\t lw \t $a0, -"+ idt.getOffSet() +"($fp) \t #loading record address");
				visitor.writeAssembly("\t li \t $t1, "+symbol.getOffSet()+" \t #size of element ");
				visitor.writeAssembly("\t add \t $a0, $a0, $t1 \t #Increment Pointer ");
				visitor.writeAssembly("\t sw \t $a0, -"+requiredTemp.getOffSet()+"($fp) \t #store new pointer in temp" );
				
				visitor.writeAssembly("");
				
				requiredTemp.setAddress(true);
				visitor.getSymbolVisitor().closeRecordScope(recordId);		
			}
			catch (Exception e){	
			}
		}
		return requiredTemp;
	}


	@Override
	public List<Temp> getRequiredTemps() {
		List<Temp> temps = new ArrayList<Temp>();
		temps.addAll(recordId.getRequiredTemps());
		temps.addAll(memberId.getRequiredTemps());
		temps.add(requiredTemp);
		return temps;
	}
}

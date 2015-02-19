package pish.ast;

import pish.symbol.SymbolVisitor;

public class RecordExpr extends Variable {
	public Variable recordId;
	public Variable memberId;
	
	
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
}

package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Line;
import pish.intermediate.Temp;
import pish.symbol.*;

/**
 * FOR ID ASSIGN num forprime num DO
 * */
public class ForStmt extends Stmt {
	public IDNode id;
	public Variable fromNum;
	public Variable toNum;
	public StmtList body;
	public boolean downTo;
	Temp requiredTemp = Temp.getNewTemp();
	Line begin = Line.getNewLine();
	Line end = Line.getNewLine();
	
	public ForStmt(String id, Variable fromNum, Variable toNum, StmtList body ,boolean downTo)	{
		this(new IDNode(id) , fromNum , toNum, body,downTo);
	}
	
	public ForStmt(IDNode id, Variable fromNum, Variable toNum, StmtList body ,boolean downTo)	{
		this.id = id;
		this.fromNum = fromNum;
		this.toNum = toNum; 
		this.body = body;
		this.downTo = downTo;
	}
	@Override 
	public int getLineNumber(){
		return this.id.getLineNumber();
	}
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(id, level);
		id.accept(visit, level+1);
		visit.visit(fromNum, level);
		fromNum.accept(visit, level+1);
		if(downTo){	
			visit.visit("downto", level);
		}
		else{
			visit.visit("to", level);
		}
		visit.visit(toNum, level);
		toNum.accept(visit, level+1);
		visit.visit(body, level);
		body.accept(visit, level+1);		
		visit.visit("]",level);
	}
	
	public String toString()  {
		return "ForStmt";
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);
		visit.visit(body);
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		SystemType fromType = fromNum.checkType(visit);
		SystemType toType = toNum.checkType(visit);
		if ( fromType.getType() != SystemType.Type.INT ){
			System.out.println("Error(line "+getLineNumber()+") cannot use From value of type ("+fromType+") inside of a for loop");
		}
		if ( toType.getType() != SystemType.Type.INT){
			System.out.println("Error(line "+getLineNumber()+") cannot use To value of type ("+toType+") inside of a for loop");
		}
		
		boolean found = false;
		if ( fromNum instanceof ConstantId){
			found = true;
		}
		if ( fromNum instanceof ConstantExpr){
			found = true;
		}	
		if ( !found ) {
			System.out.println("Error(line "+getLineNumber()+"): FROM number in for loop  has to be Constant or Number type of INT");		
		}
		found = false;
		if ( toNum instanceof ConstantId){
			found = true;
		}
		if ( toNum instanceof ConstantExpr){
			found = true;
		}	
		if ( !found ) {
			System.out.println("Error(line "+getLineNumber()+"): TO number in for loop has to be Constant or Number type of INT");		
		}
		found = false;
		
		return toType;
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		return null;
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		if ( visitor.isIntermediateCode()){
			visitor.writeIntermediateCode("#BEGINING FOR LOOP");
			visitor.writeIntermediateCode(begin+": nop");
			visitor.addTab(1);
			
			Temp from = fromNum.genCode(visitor);
			Temp to = toNum.genCode(visitor);
			
			requiredTemp.setAddress(true);
			
			requiredTemp.setOffSet(visitor.getSymbolVisitor().getCurrOffSet());
			visitor.getSymbolVisitor().incrementOffSetCounter(requiredTemp.getSizeOf());	
			visitor.writeIntermediateCode(requiredTemp+" := &"+id.toString());
			visitor.writeIntermediateCode("*"+requiredTemp+" := "+from);
			visitor.writeIntermediateCode("if "+ requiredTemp +" := "+to+" goto "+end );
			visitor.visit(body);
			if (this.downTo){
				visitor.writeIntermediateCode("*"+requiredTemp+" := *"+requiredTemp+" - 1");		
			}
			else{
				visitor.writeIntermediateCode("*"+requiredTemp+" := *"+requiredTemp+" + 1");		
			}
			visitor.writeIntermediateCode("goto "+begin);
			visitor.addTab(-1);			
			visitor.writeIntermediateCode(end+": nop");
			visitor.writeIntermediateCode("#ENDING FOR LOOP");
		}
		else{

			Temp from = fromNum.genCode(visitor);
			Temp to = toNum.genCode(visitor);
			SymbolVisitor sv = visitor.getSymbolVisitor();
			Symbol sym;
			SystemType st;
			
			//load "from"
			//load required temp off stack
			//store pointer into required temp
			//mark requiredtemp as isAddress
			//set label
			//load requiredtemp
			//load value from temp
			//load "to" (value)
			//compare values
			//statements
			//decrement requiredtemp
			//go to label
			
			visitor.writeAssembly("#FOR LOOP - SETUP");
			
			//load FROM (ADDRESS)
			visitor.writeAssembly("\t#ASSIGNING FROM pointer to Counter");
			if( from.isAddress() ){
				visitor.writeAssembly("\t lw \t $t0, -"+from.getOffSet()+"($fp)");
			}else{
				visitor.writeAssembly("\t la \t $t0,-"+from.getOffSet()+FRAMEPOINTER);
				//visitor.writeAssembly("\t lw \t $t0, $a0");
			}
			
			//SET 'REQUIRED TEMP' to 'FROM'
			//load req temp (always a pointer)
			//visitor.writeAssembly("\t lw \t $t1,  -"+requiredTemp.getOffSet()+FRAMEPOINTER+"\t #loading reqtemp");
			visitor.writeAssembly("\t sw \t $t0,  -"+requiredTemp.getOffSet()+FRAMEPOINTER+"\t #loading reqtemp");
			
			//store address to value at mem of pointer
			//visitor.writeAssembly("\t sw \t $t0, ($t1) \t #Storing address into reqtemp");
			//visitor.writeAssembly("\t sw \t $t0,  -"+requiredTemp.getOffSet()+FRAMEPOINTER+"\t #loading reqtemp");
			
			visitor.writeAssembly(begin+": \t #BEGIN FOR LOOP");
			visitor.addTab(1);		

			// Loading Required Temp value
			visitor.writeAssembly("\t#loading 'req temp' value");
			st= fromNum.checkType(sv);
			switch( st.getType() ){
				case INT:
					
					if( requiredTemp.isAddress() ){
						//deref
						visitor.writeAssembly("\t lw \t $a0, -"+requiredTemp.getOffSet()+"($fp)");
						visitor.writeAssembly("\t lw \t $t0, ($a0) \t #deref to int");
					}else{
						visitor.writeAssembly("\t lw \t $t0,-"+requiredTemp.getOffSet()+FRAMEPOINTER);
						
					}
					
					break;
				case REAL:
					if( requiredTemp.isAddress() ){
						//deref
						visitor.writeAssembly("\t lw \t $a0, -"+requiredTemp.getOffSet()+"($fp)");
						visitor.writeAssembly("\t l.s \t $f0, ($a0) \t #deref real");
					}else{
						visitor.writeAssembly("\t l.s \t $f0,-"+requiredTemp.getOffSet()+FRAMEPOINTER);
					}
					break;
			}
			
			
			// Loading "To" 
			visitor.writeAssembly("\t#loading 'to' value");
			st= fromNum.checkType(sv);
			switch( st.getType() ){
				case INT:
					
					if( to.isAddress() ){
						//deref
						visitor.writeAssembly("\t lw \t $a0, -"+to.getOffSet()+"($fp)");
						visitor.writeAssembly("\t lw \t $t1, ($a0) \t #deref to int");
					}else{
						visitor.writeAssembly("\t lw \t $t1,-"+to.getOffSet()+FRAMEPOINTER);
						
					}
					
					break;
				case REAL:
					if( requiredTemp.isAddress() ){
						//deref
						visitor.writeAssembly("\t lw \t $a0, -"+requiredTemp.getOffSet()+"($fp)");
						visitor.writeAssembly("\t l.s \t $f1, ($a0) \t #deref real");
					}else{
						visitor.writeAssembly("\t l.s \t $f1,-"+requiredTemp.getOffSet()+FRAMEPOINTER);
					}
					break;
			}
			
			//compare
			st= fromNum.checkType(sv);
			switch( st.getType() ){
				case INT:
					
					if (this.downTo){
						visitor.writeAssembly(" \t\t#compare greater than or equal");		
						visitor.writeAssembly("\t sge \t $t2, $t0, $t1");
						visitor.writeAssembly("\t beqz \t $t2, "+end);
					}
					else{
						visitor.writeAssembly(" \t\t#compare Less than or equal");		
						visitor.writeAssembly("\t sle \t $t2, $t0, $t1");
						visitor.writeAssembly("\t beqz \t $t2, "+end);
					}
					
					break;
				case REAL:
					if (this.downTo){
						visitor.writeAssembly(" \t\t#compare greater than or equal");		
						visitor.writeAssembly("\t sge \t $f2, $f0, $f1");
						visitor.writeAssembly("\t beqz \t $f2, "+end);
					}
					else{
						visitor.writeAssembly(" \t\t#compare Less than or equal");		
						visitor.writeAssembly("\t sle \t $f2, $f0, $f1");
						visitor.writeAssembly("\t beqz \t $f2, "+end);
					}
					
					break;
			}
			
			visitor.writeAssembly("\t #loop statements");
			visitor.visit(body);
			visitor.writeAssembly("\t #end loop statements");
			
			//decrement
			// Loading, dec, store Required Temp value
			visitor.writeAssembly("\t#loading reqtemp value");
			st= fromNum.checkType(sv);
			switch( st.getType() ){
				case INT:
					//load
					if( requiredTemp.isAddress() ){
						//deref
						visitor.writeAssembly("\t lw \t $a0, -"+requiredTemp.getOffSet()+"($fp)");
						visitor.writeAssembly("\t lw \t $t0, ($a0) \t #deref to int");
					}else{
						visitor.writeAssembly("\t lw \t $t0,-"+requiredTemp.getOffSet()+FRAMEPOINTER);
						
					}
					//dec or int
					if (this.downTo){
						visitor.writeAssembly("\t subi \t $t0, $t0, 1 \t #decrement counter");		
					}
					else{
						visitor.writeAssembly("\t addi \t $t0, $t0, 1 \t #increment counter");		
					}
					//store
					if( requiredTemp.isAddress() ){
						//assign to mem
						visitor.writeAssembly("\t lw \t $a0, -"+requiredTemp.getOffSet()+"($fp)");
						visitor.writeAssembly("\t sw \t $t0, ($a0) \t #store ");
					}else{
						visitor.writeAssembly("\t la \t $a0, -"+requiredTemp.getOffSet()+"($fp)");
						visitor.writeAssembly("\t sw \t $t0, ($a0) \t #store ");
					}
					
					break;
				case REAL:
					//load
					if( requiredTemp.isAddress() ){
						//deref
						visitor.writeAssembly("\t lw \t $a0, -"+requiredTemp.getOffSet()+"($fp)");
						visitor.writeAssembly("\t l.s \t $f0, ($a0) \t #deref real");
					}else{
						visitor.writeAssembly("\t l.s \t $f0,-"+requiredTemp.getOffSet()+FRAMEPOINTER);
					}
					
					//dec or inc
					if (this.downTo){
						visitor.writeAssembly("\t subi.s \t $f0, $f0, 1 \t #decrement counter");		
					}
					else{
						visitor.writeAssembly("\t addi.s \t $f0, $f0, 1 \t #increment counter");		
					}
					//store
					if( requiredTemp.isAddress() ){
						//assign to mem
						visitor.writeAssembly("\t lw \t $a0, -"+requiredTemp.getOffSet()+"($fp)");
						visitor.writeAssembly("\t s.s \t $f0, ($a0) \t #store ");
					}else{
						visitor.writeAssembly("\t la \t $a0,-"+requiredTemp.getOffSet()+FRAMEPOINTER);
						visitor.writeAssembly("\t s.s \t $f0,($a0) \t # Storing Result");
					}
					break;
			}
			
			visitor.writeAssembly("\t j "+begin);
			visitor.addTab(-1);			
			visitor.writeAssembly(end+": \t #END FOR LOOP");
			visitor.writeAssembly("");	
		}
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
		temps.add(requiredTemp);
		temps.addAll(body.getRequiredTemps());
		return temps;
	}
}	

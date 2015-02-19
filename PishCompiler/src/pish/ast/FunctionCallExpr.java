package pish.ast;

import pish.JavaSymbol;
import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;
import java.util.*;
public class FunctionCallExpr extends Expr {

	public FuncVar id;
	public ExprList list;
	Temp requiredTemp= Temp.getNewTemp();
	
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
		return id.toString();
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

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		genCode(visitor);
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Temp genCode(IntermediateVisitor visitor) {
		if ( visitor.isIntermediateCode()){
			visitor.writeIntermediateCode("JUMP "+id.toString());
			
			visitor.visit(list);
			
			requiredTemp.setOffSet(visitor.getSymbolVisitor().getCurrOffSet());
			visitor.getSymbolVisitor().incrementOffSetCounter(requiredTemp.getSizeOf());	
			
		}else{
			SymbolVisitor sv = visitor.getSymbolVisitor();
			
			visitor.writeAssembly("# FUNCTION CALL TO ("+ id.id +")");
			//grab temp list
			//push the addresses on stack
			//jump
			//remove params from stack on return
			//save return val in new temp
			 
			visitor.visit(list);
			
			List<Temp> argList = list.getRequiredTemps();
			
			for ( Temp temp : argList ){
				//load address
				//load onto sp
				//dec sp
				
				if (temp.isAddress()){
					
					visitor.writeAssembly("# LOAD PARAM ("+temp.toString()+")");
					visitor.writeAssembly("\t la \t $a0, -"+temp.getOffSet()+"($fp) \t #loading address of ("+temp+")");
					visitor.writeAssembly("\t lw \t $a0, ($a0) \t #defreerce pointer");
				}else{
					visitor.writeAssembly("# LOAD PARAM ("+temp.toString()+")");
					visitor.writeAssembly("\t la \t $a0, -"+temp.getOffSet()+"($fp) \t #loading address of ("+temp+")");
					
				}
				
				visitor.writeAssembly("\t sw \t $a0 , ($sp) \t #Pushing onto Stack");
				visitor.writeAssembly("\t addi $sp, $sp, -4 \t #decrement stack");
				
			}
			/* JUMP TO FUNCTION */
			visitor.writeAssembly("");
			visitor.writeAssembly("\t jal "+id.id+" \t #Jump to Function");
			
			/* STORE RETURN VALUE */
			/* Return values are stored in $v0,
			 * which happens to work for both int and floats with single precision*/
			SystemType type = id.checkType(sv);
			
			visitor.writeAssembly("");
			visitor.writeAssembly("\t #STORING RETURN VAL");
			switch(type.getType()){
				case INT:
					//visitor.writeAssembly("\t lw \t $t0, $v0 \t #Get INT value ");
					visitor.writeAssembly("\t sw \t $v1, -"+requiredTemp.getOffSet()+"($fp)" );
					break;
					
				case REAL:
					//visitor.writeAssembly("\t l.s \t $f0, $v0 \t #Get FLOAT value ");
					visitor.writeAssembly("\t lw \t $v1, -"+requiredTemp.getOffSet()+"($fp)" );
					break;
				
				case NULL:
					visitor.writeAssembly("\t #NO RETURN VALUE");
					break;
					
				case CHAR:
					//uh no idea
					break;
			}
			visitor.writeAssembly("");
			
			/* REMOVE PARAMS OFF STACK */
			for ( Temp temp : argList ){
				//inc sp
				visitor.writeAssembly("\t #REMOVING PARAMS");
				visitor.writeAssembly("\t addi $sp, $sp, -4 \t #inc stack");
			}
			//visitor.writeAssembly("\t addi $sp, $sp, -4 \t #inc stack");
			
			visitor.writeAssembly("");
		}
		return requiredTemp;
	}

	@Override
	public List<Temp> getRequiredTemps() {
		List<Temp> temps = new ArrayList<Temp>();
		temps.addAll(list.getRequiredTemps());
		temps.add(requiredTemp);
		return temps;
	}
	
}

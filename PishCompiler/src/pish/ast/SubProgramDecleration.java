package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.*;

public class SubProgramDecleration extends Decleration {
	private static String PUSH_STACK = "\t addi \t $sp, $sp, -4";
	private static String POP_STACK = "\t addi \t $sp, $sp, 4";
	public SubProgramHeader header;
	public SimpleDeclerationList declerations;
	public CompoundStmt compStmt;  // body
	public SubProgramDeclerationList list; // Sub programs under this function or procedure


	
	public SubProgramDecleration ( SubProgramHeader header){
		this(header,null,null);
	}
	
	public SubProgramDecleration ( SubProgramHeader header,SimpleDeclerationList declerations ,CompoundStmt compStmt) { 
		this(header,declerations,null,compStmt);
	}

	/* main constructor */
	public SubProgramDecleration ( SubProgramHeader header,SimpleDeclerationList declerations, SubProgramDeclerationList list,CompoundStmt compStmt ){
		this.header = header;
		this.declerations = declerations;
		this.compStmt = compStmt;
		this.list = list;
	}

	public String toString(){
		return "SubProgramDecleration";
	}

	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		
		visit.visit("[",level);
		if ( header != null ) {
			visit.visit(header, level);
			header.accept(visit, level+1);
		}
		if ( declerations != null ) {
			visit.visit(declerations, level);
			declerations.accept(visit, level+1);
		}
		if ( compStmt != null ){
			visit.visit(compStmt, level);
			compStmt.accept(visit, level+1);
		}
		if ( list != null ) {
			visit.visit(list, level);
			list.accept(visit, level+1);
		}
		visit.visit("]",level);
		
	}

	private boolean isForward(){
		return (header != null &&  compStmt==null);
	}
	
	private boolean isForwardBody(){
		return (header.fields == null && header.returnType == null && compStmt != null);
	}
	
	@Override
	public void accept(SymbolVisitor visit, Variable Type) {

		if ( isForward() ){
			visit.registerSymbol(header.id, header.returnType);
			visit.registerMethod(header.id,header.fields);
			visit.openScope(header.id);
			visit.visit(header);
			visit.closeScope();
			return;
		}
		else if ( !isForwardBody() ){
			visit.registerSymbol(header.id, header.returnType);
			visit.registerMethod(header.id,header.fields);
		}
		
		visit.openScope(header.id);
		if ( header != null ){
			visit.visit(header);
		}
		if ( declerations != null ) {
			visit.visit(declerations);
		}
		if ( compStmt != null ){
			visit.visit(compStmt);
		}
		if ( list != null ) {
			visit.visit(list);
		}
		visit.closeScope();
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		
		if ( isForward() ) {
			return;
		}
		
		if ( visitor.isIntermediateCode()){
			visitor.writeIntermediateCode(header.id.id+": nop");
			visitor.addTab(1);
			SymbolVisitor sVisitor = visitor.getSymbolVisitor();
			sVisitor.openScope(header.id);
			if ( compStmt != null ) {
				visitor.visit(compStmt);
			}
			if ( list != null ) {
				visitor.visit(list);
			}
			
			visitor.addTab(-1);
			sVisitor.closeScope();
			
		}else{
			//gen label
			//store ra
			//store old fp
			//set fp = sp
			//push vars
			//push temps
			//load params into local memory
			//visit statements
			//start cleanup
			//move fp
			//restore fp
			//restor ra
			//jump back
			
			
			SymbolVisitor sv =visitor.getSymbolVisitor();
			sv.openScope(header.id);
			
			/* Generate Lable */
			visitor.writeAssembly(""+header.id.id+": ");
			//setup $ra
			visitor.writeAssembly("\t sw \t $ra, ($sp) \t # push the return addr");
			visitor.writeAssembly(PUSH_STACK);
			//setup $fp old
			visitor.writeAssembly("\t sw \t $fp, ($sp) \t # push the old frame pointer");
			visitor.writeAssembly(PUSH_STACK);
			//set fp = sp
			visitor.writeAssembly("\t move \t $fp, $sp \t # establish new frame pointer.");
			
			/* LOAD VARS */
			List<Symbol> syms= sv.getAllSymbols();
			visitor.writeAssembly("");
			visitor.writeAssembly("# Allocate space for Variables");
			
			for ( Symbol sym: syms){
			 //	visitor.writeAssembly(\t sw \t ?, ($sp) );
			 	visitor.writeAssembly("\t addi, $sp, $sp, -" +sym.getSize()+ " \t # Allocate space for "+ sym );
			 }		  

			visitor.writeAssembly("");
			
		/*LOAD TEMPS*/
			List<Temp> tempList = compStmt.getRequiredTemps();
			
			visitor.writeAssembly("# Allocate space for Temps");
			for ( Temp temp : tempList ){
				/* Allocate space on stack */
				visitor.writeAssembly("\t addi \t $sp,$sp,-4 \t # and decrement stack pointer Temp("+temp+")");
				/* Default value (maybe)*/
			}
			visitor.writeAssembly("");
			
			/*Load Params into local memory*/
				//this is now pass by value in a sense
				//this is copying the value stored in the address word on the stack into our local frame memory
				
			//get param list
			List<SystemType> argList = sv.getArgs(header.id);
			SystemType arg;
			visitor.writeAssembly("# Copying Params into local Memory");
			
			for (int i = 0; i< argList.size(); i++){ 
				//load address
				//load value from address
				
				arg = argList.get(i);
				/*load address*/
				visitor.writeAssembly("\t lw \t $a0, "+ (i * 4 + 12) +"($fp) \t #"+arg );
				
				/*load value from address*/
				switch(arg.getType()){
					case CHAR:
						//i think pushing char to INT will work too since we think chars are a word.
						
					case INT:
						visitor.writeAssembly("\t lw \t $t0, ($a0) ");
						visitor.writeAssembly("\t sw \t $t0, -" + ((i+1)*4) +"($fp) \t #Copying INTEGER value");
										
						break;
						
					case REAL:
						visitor.writeAssembly("\t l.s \t $f0, ($a0) ");
						visitor.writeAssembly("\t s.s \t $f0, -" + ((i+1)*4) +"($fp) \t #Copying REAL value");
						
						break;
				}
			}
			
			visitor.writeAssembly("# END PARAM COPY");
			//loop through them, get them from fp+offset and store to local mem
			
			/* Statements */
			if ( compStmt != null ){
				visitor.writeAssembly("# BEGIN");
				visitor.visit(compStmt);
				visitor.writeAssembly("# END");
			}

			/* CLEAN UP STACK */
				
			visitor.writeAssembly("");
			
			/* Destroy All Temp memory*/
					
			/* restore fp */	
			visitor.writeAssembly("\t move \t $sp, $fp \t # all local var's gone");
			visitor.writeAssembly("\t addi \t $sp, $sp, 4 \t # and pop that word");
			/* restore fp */
			visitor.writeAssembly("\t lw \t $fp, ($sp) \t # restore old $fp");
			visitor.writeAssembly("\t addi \t $sp, $sp, 4 \t # and pop that word");
			/* restor ra */
			visitor.writeAssembly("\t lw \t $ra, ($sp) \t # restore return address");
			/* jump back */
			visitor.writeAssembly("\t jr \t $ra \t # return");
			
			visitor.writeAssembly("");
			
			sv.closeScope();
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
		temps.addAll(compStmt.getRequiredTemps());
	
		return temps;
	}
}

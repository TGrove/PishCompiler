package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.Symbol;
import pish.symbol.SymbolVisitor;

public class Program extends ASTNode {
	private static String PUSH_STACK = "\t addi \t $sp, $sp, -4";
	private static String POP_STACK = "\t addi \t $sp, $sp, 4";
	public IDNode id;
	public ConstantList constantList;
	public TypeDefList typeDefList; 
	public SimpleDeclerationList simpleDecList;
	public SubProgramDeclerationList subProgramList;
	public CompoundStmt compStmt;
	
	
	public Program ( IDNode id , ConstantList constantList, TypeDefList typeDefList
			,SimpleDeclerationList simpleDecList , SubProgramDeclerationList subProgramList,CompoundStmt compStmt){
		
		this.id = id;
		this.constantList = constantList;
		this.simpleDecList = simpleDecList;
		this.typeDefList = typeDefList;
		this.subProgramList = subProgramList;
		this.compStmt = compStmt;
		compStmt.setName("MAIN");
	}

	public String toString(){
		return "Program";
	}

	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(id, level);
		id.accept(visit, level+1);
		if ( constantList != null){
			visit.visit(constantList, level);
			constantList.accept(visit, level+1);
		}
		if ( typeDefList != null ){
			visit.visit(typeDefList, level);
			typeDefList.accept(visit, level+1);
		}
		if ( simpleDecList != null ){
			visit.visit(simpleDecList, level);
			simpleDecList.accept(visit, level+1);
		}
		if ( subProgramList != null ){
			visit.visit(subProgramList, level);
			subProgramList.accept(visit, level+1);
		}
		if ( compStmt != null ) {
			visit.visit(compStmt, level);
			compStmt.accept(visit, level+1);
		}
		visit.visit("]",level);
	}


	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		/*
		 * TO DO:
		 * 	1. load (INT,REAL,CHAR, WRITEINT, WRITEREAL, WRITECHAR , TRUE, FALSE , MAXINT) 
		 * 
		 */
		
		visit.openScope(this);
		visit.registerSymbol(new IDNode("TRUE"), new IntegerExpr(1));
		visit.registerSymbol(new IDNode("FALSE"), new IntegerExpr(0));
		visit.registerSymbol(new IntType(), SystemType.INT());
		visit.registerSymbol(new RealType(), SystemType.REAL());
		visit.registerSymbol(new CharType(), SystemType.CHAR());
		visit.registerSymbol(new IntegerExpr(0), SystemType.INT());
		visit.registerSymbol(new RealExpr(0), SystemType.REAL());
		visit.registerSymbol(new CharExpr(' '), SystemType.CHAR());
		visit.registerSymbol(new StringType(), SystemType.STRING());
		visit.registerSymbol(new StringExpr(""), SystemType.STRING());
		visit.registerSymbol(new RecordType(), SystemType.RECORD());
		visit.registerSymbol(new FuncVar("WRITEINT"), SystemType.NULL());
		visit.registerSymbol(new FuncVar("WRITEREAL"), SystemType.NULL());
		visit.registerSymbol(new FuncVar("WRITECHAR"), SystemType.NULL());
		visit.registerMethod(new FuncVar("WRITEINT"), new SimpleDeclerationList(new SimpleDecleration(new VariableList(new IDNode("X")),new IntType())));
		visit.registerMethod(new FuncVar("WRITEREAL"), new SimpleDeclerationList(new SimpleDecleration(new VariableList(new IDNode("X")),new RealType())));
		visit.registerMethod(new FuncVar("WRITECHAR"), new SimpleDeclerationList(new SimpleDecleration(new VariableList(new IDNode("X")),new StringType())));
		
		
		if ( constantList != null){
			visit.visit(constantList);
		}
		if ( typeDefList != null ){
			visit.visit(typeDefList);
		}
		if ( simpleDecList != null ){
			visit.visit(simpleDecList);
		}
		if ( subProgramList != null ){
			visit.visit(subProgramList);
		}
		if ( compStmt != null ) {
			visit.openScope(compStmt);
			visit.visit(compStmt);
			visit.closeScope();
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
		return null;
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		
		if ( visitor.isIntermediateCode()){
			if ( constantList != null){
				visitor.addTab(1);
				visitor.visit(constantList);
				visitor.addTab(-1);
			}
			if ( typeDefList != null ){
				visitor.addTab(1);
				visitor.visit(typeDefList);
				visitor.addTab(-1);
			}
			if ( simpleDecList != null ){
				visitor.addTab(1);
				visitor.visit(simpleDecList);
				visitor.addTab(-1);
			}
			if ( subProgramList != null ){
				visitor.visit(subProgramList);
			}
			if ( compStmt != null ) {
				visitor.getSymbolVisitor().openScope(compStmt);
				visitor.addTab(1);
				visitor.visit(compStmt);
				visitor.addTab(-1);
				visitor.getSymbolVisitor().closeScope();
			}
	
		}else{
			//MIPS file setup
			//File Comments
			//data declarations
			//function declarations
			//.text
			//.globl main
			//main:
			
			visitor.writeAssembly("#This Assembly Code has been auto-generated by The Mohamad and Tom Pish Compiler.");
			visitor.setDataSegment(true);
			
			if ( constantList != null){
				visitor.writeAssembly("");
				visitor.writeAssembly("# Constant Declaration Section");
				visitor.addTab(1);
				
				visitor.writeAssembly(".data");	
				visitor.writeAssembly("\t newline: \t .ascii \t \"\\n\" ");
				visitor.writeAssembly("\t\t .space \t 6");
				visitor.writeAssembly("");
				
				
				visitor.visit(constantList);
				
				visitor.addTab(-1);
			}
			if ( typeDefList != null ){
			//	visitor.writeAssembly("");
			//	visitor.writeAssembly("# Typedef Declaration Section");
			//	visitor.addTab(1);
			//	visitor.writeAssembly(".data");
			//	visitor.visit(typeDefList);
			//	visitor.addTab(-1);
				
				
			}
			if ( simpleDecList != null ){
				
				visitor.writeAssembly("");
				visitor.writeAssembly("# Simple Declaration Section");
				visitor.addTab(1);
				visitor.writeAssembly(".data");
				
				visitor.visit(simpleDecList);
				
				visitor.addTab(-1);
			}
			//Write Jump to Main label
			visitor.writeAssembly("");
			visitor.writeAssembly("# Set jump to main to override");
			visitor.writeAssembly(".text");
			visitor.writeAssembly(".globl MAIN");
			visitor.writeAssembly("j MAIN");
			visitor.setDataSegment(false);
			generateBuiltInFunctions( visitor );
			
			
			// write in WRITEREAL, WRITEINT and WRITECHAR
			
			
			if ( subProgramList != null ){
				
				visitor.writeAssembly("");
				
				visitor.visit(subProgramList);
			}
			
			
			if ( compStmt != null ) {
				/*INTER*/
				visitor.writeIntermediateCode("MAIN:");
				
			/*ASSEMBLY*/
				visitor.writeAssembly("");
				visitor.writeAssembly("# Program Main ");	
				visitor.writeAssembly("MAIN:");
				
			/*SET UP FRAME POINTER AND MEMORY*/
				/*return address*/
				visitor.writeAssembly("\tsw\t$fp,($sp)\t# (PUSH) save previous frame pointer on stack ");
				visitor.writeAssembly("\taddi\t$sp,$sp,-4\t# and decrement stack pointer");
				visitor.writeAssembly("");
				
				/*old frame pointer*/
				visitor.writeAssembly("\tsw\t$fp,($sp)\t# (PUSH) save previous frame pointer on stack ");
				visitor.writeAssembly("\taddi\t$sp,$sp,-4\t# and decrement stack pointer");
				visitor.writeAssembly("");
				
				/*set frame pointer */
				visitor.writeAssembly("\tmove\t$fp, $sp\t# establish new frame pointer.");
				visitor.writeAssembly("");
				
			/*SET UP VARIABLE MEMORY*/
				//none in main
				
			/*SET UP TEMP MEMORY*/
				List<Temp> tempList = compStmt.getRequiredTemps();
				
				for ( Temp temp : tempList ){
					/* Allocate space on stack */
					visitor.writeAssembly("\taddi\t$sp,$sp,-4\t# and decrement stack pointer Temp("+temp+")");
					/* Default value (maybe)*/
				}
				visitor.writeAssembly("");
				
				/* |  old    | <-fp
				 * |  temp1  | fp -4
				 * |  temp2  | fp -8
				 */
				visitor.addTab(1);
				visitor.getSymbolVisitor().openScope(compStmt);
				visitor.visit(compStmt);
				visitor.getSymbolVisitor().closeScope();
			
				//write end syscall
				visitor.writeAssembly("\t li \t $v0, 10	\t # system call code for exit = 10");
				visitor.writeAssembly("\t syscall \t # call operating sys");	
				
				visitor.addTab(-1);
			}
			
			visitor.writeAssembly("# New line to make some simulators happy");
			visitor.writeAssembly("");
		}
	}

	private void generateBuiltInFunctions(IntermediateVisitor visitor) {
		//write int
		//write real
		//write char
		if (visitor.isIntermediateCode()){
		
		}else{
			/*WRITEINT*/
			visitor.writeAssembly("WRITEINT:");
			visitor.writeAssembly("\t sw \t $ra, ($sp) \t # push the return addr");
			visitor.writeAssembly(PUSH_STACK);
			visitor.writeAssembly("\t sw \t $fp, ($sp) \t # push the old frame pointer");
			visitor.writeAssembly(PUSH_STACK);
			visitor.writeAssembly("\t move \t $fp, $sp \t # establish new frame pointer.");
			visitor.writeAssembly("");
			visitor.writeAssembly("\t lw \t $a0, 12($fp)\t # getting the param (address)");
			visitor.writeAssembly("\t lw \t $a0, ($a0)");
			visitor.writeAssembly("\t li \t $v0, 1 \t # load INTEGER print system call code(1) into register $v0");
			visitor.writeAssembly("\t syscall");
			visitor.writeAssembly("");
			
			visitor.writeAssembly("\t la \t $a0, newline");
			visitor.writeAssembly("\t sw \t $a0 , ($sp) \t # push the address on");
			visitor.writeAssembly("\t addi \t $sp, $sp, -4");
			visitor.writeAssembly("\t jal \t WRITECHAR");
			visitor.writeAssembly("\t addi \t $sp, $sp, 4");
			visitor.writeAssembly("");

			visitor.writeAssembly("\t move \t $sp, $fp \t # all local var's gone");
			visitor.writeAssembly(POP_STACK);
			visitor.writeAssembly("\t lw \t $fp, ($sp)	\t # restore old $fp");
			visitor.writeAssembly(POP_STACK);
			visitor.writeAssembly("\t lw \t $ra, ($sp) \t # restore return address");
			visitor.writeAssembly("\t jr \t $ra	\t # return");
			visitor.writeAssembly("");
			visitor.writeAssembly("");
			
			/*WRITEREAL*/
			visitor.writeAssembly("WRITEREAL:");
			visitor.writeAssembly("\t sw \t $ra, ($sp) \t # push the return addr");
			visitor.writeAssembly(PUSH_STACK);
			visitor.writeAssembly("\t sw \t $fp, ($sp) \t # push the old frame pointer");
			visitor.writeAssembly(PUSH_STACK);
			visitor.writeAssembly("\t move \t $fp, $sp \t # establish new frame pointer.");
			visitor.writeAssembly("");
			visitor.writeAssembly("\t lw \t $a0, 12($fp) \t # getting the param (address)");
			visitor.writeAssembly("\t l.s \t $f12, ($a0) \t #loading adress into float reg for printing");
			visitor.writeAssembly("\t li \t $v0, 2 \t # load REAL print system call code(2) into register $v0");
			visitor.writeAssembly("\t syscall");
			visitor.writeAssembly("");
			visitor.writeAssembly("\t la \t $a0, newline");
			visitor.writeAssembly("\t sw \t $a0 , ($sp) \t # push the address on");
			visitor.writeAssembly("\t addi \t $sp, $sp, -4");
			visitor.writeAssembly("\t jal \t WRITECHAR");
			visitor.writeAssembly("\t addi \t $sp, $sp, 4");
			visitor.writeAssembly("\t move \t $sp, $fp \t # all local var's gone");
			visitor.writeAssembly(POP_STACK);
			visitor.writeAssembly("\t lw \t $fp, ($sp)	\t # restore old $fp");
			visitor.writeAssembly(POP_STACK);
			visitor.writeAssembly("\t lw \t $ra, ($sp) \t # restore return address");
			visitor.writeAssembly("\t jr \t $ra	\t # return");
			visitor.writeAssembly("");
			visitor.writeAssembly("");
			
			/*WRITECHAR*/
			visitor.writeAssembly("WRITECHAR:");
			visitor.writeAssembly("\t sw \t $ra, ($sp) \t # push the return addr");
			visitor.writeAssembly(PUSH_STACK);
			visitor.writeAssembly("\t sw \t $fp, ($sp) \t # push the old frame pointer");
			visitor.writeAssembly(PUSH_STACK);
			visitor.writeAssembly("\t move \t $fp, $sp \t # establish new frame pointer.");
			visitor.writeAssembly("");
			visitor.writeAssembly("\t lw \t $a0, 12($fp) \t # load address of string to be printed into $a0");
			visitor.writeAssembly("\t li \t $v0, 4 \t # load STRING print system call code(2) into register $v0");
			visitor.writeAssembly("\t syscall");
			visitor.writeAssembly("");
			
			visitor.writeAssembly("\t move \t $sp, $fp \t # all local var's gone");
			visitor.writeAssembly(POP_STACK);
			visitor.writeAssembly("\t lw \t $fp, ($sp)	\t # restore old $fp");
			visitor.writeAssembly(POP_STACK);
			visitor.writeAssembly("\t lw \t $ra, ($sp) \t # restore return address");
			visitor.writeAssembly("\t jr \t $ra	\t # return");
			visitor.writeAssembly("");
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

		return temps;
	}
}

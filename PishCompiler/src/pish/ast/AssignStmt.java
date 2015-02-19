package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.*;

public class AssignStmt extends Stmt {
	public Variable id;
	public Expr expr; // right hand side

	public AssignStmt(Variable id, Expr expr) {
		this.id = id;
		this.expr = expr;
	}

	public String toString(){
		return "AssignStmt";
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
		visit.visit(expr, level);
		expr.accept(visit, level+1);
		visit.visit("]",level);
	
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);	
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		SystemType idType = id.checkType(visit);
		SystemType exprType = expr.checkType(visit);
		if ( visit.isConstant(id)){
			System.out.println("Error (line "+getLineNumber()+") cannot assign a ("+expr+"("+exprType+")) to a constant ("+id+"("+idType+"))");			
		}
		else if ( idType.getType() == SystemType.Type.INT && exprType.getType() == SystemType.Type.REAL)
		{
			System.out.println("Error (line "+getLineNumber()+") cannot assign ("+expr+"("+exprType+")) to ("+id+"("+idType+"))");
		}
		else if ( idType.getType() == SystemType.Type.REAL && exprType.getType() == SystemType.Type.INT)
		{
			System.out.println("Error (line "+getLineNumber()+") cannot assign ("+expr+"("+exprType+")) to ("+id+"("+idType+"))");
		}
		else if ( idType.getType() == SystemType.Type.CHAR && exprType.getType() != SystemType.Type.CHAR)
		{
			System.out.println("Error (line "+getLineNumber()+") cannot assign ("+expr+"("+exprType+")) to ("+id+"("+idType+"))");
		}
		else if ( idType.getType() == SystemType.Type.STRING && exprType.getType() != SystemType.Type.STRING)
		{
			System.out.println("Error (line "+getLineNumber()+") cannot assign ("+expr+"("+exprType+")) to ("+id+"("+idType+"))");
		}
		else if (idType.getType() == SystemType.Type.RECORD || exprType.getType() == SystemType.Type.RECORD  )
		{
			System.out.println("Error (line "+getLineNumber()+") cannot assign ("+expr+"("+exprType+")) to ("+id+"("+idType+"))");			
		}
		else if (idType.getType() == SystemType.Type.NULL || exprType.getType() == SystemType.Type.NULL  )
		{
			System.out.println("Error (line "+getLineNumber()+") cannot assign ("+expr+"("+exprType+")) to ("+id+"("+idType+"))");			
		}
		return exprType;
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		return null;
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		if ( visitor.isIntermediateCode() ){
			visitor.writeIntermediateCode("#BEGIN ASSIGN STMT");
			Temp idt = id.genCode(visitor);
			Temp et = expr.genCode(visitor);
			visitor.writeIntermediateCode(idt+" := "+et);
			visitor.writeIntermediateCode("#END ASSIGN STMT");
		
		}else{
			SymbolVisitor sv = visitor.getSymbolVisitor();
			
			/*ASSEMBLY*/
			Temp idt = id.genCode(visitor);
			Temp et = expr.genCode(visitor);
			
			
			
			if(sv.isFunction(id)){
				visitor.writeAssembly("\t#RETURN EXPRESSION");
				
			}else{
				visitor.writeAssembly("\t#BEGIN ASSIGN STMT");
				visitor.writeAssembly("\t lw \t $t0,-"+idt.getOffSet()+FRAMEPOINTER);
			}
			
			switch( (expr.checkType(visitor.getSymbolVisitor())).getType() )
			{
				case INT:
					visitor.writeAssembly("\t#INTEGER ASSIGNMENT");
					if(sv.isFunction(id)){
						//return value
						if( et.isAddress() ){
							visitor.writeAssembly("\t lw \t $t0, -"+et.getOffSet()+"($fp)");
							visitor.writeAssembly("\t lw \t $v1, ($t0)");
						}else{
							visitor.writeAssembly("\t la \t $t0, -"+et.getOffSet()+"($fp)");
							visitor.writeAssembly("\t lw \t $v1, ($t0)");
											
						}
						
					}else if(id instanceof IndexedExpr || id instanceof RecordExpr ){
						//add to memory where pointer is pointing to
						visitor.writeAssembly("");
						
						//load address pointer
						visitor.writeAssembly("\t lw \t $a0,-"+idt.getOffSet()+FRAMEPOINTER);
						
						if( et.isAddress() ){
							//deref then assign
							visitor.writeAssembly("\t lw \t $a1, -"+et.getOffSet()+"($fp)");
							visitor.writeAssembly("\t lw \t $t1, ($a1) \t #deref to int");
							visitor.writeAssembly("\t sw \t $t1, ($a0) \t #store ");
							
						}else{
							//value to be assigned
							visitor.writeAssembly("\t lw \t $t1, -"+et.getOffSet()+"($fp) \t #load direct value");
							visitor.writeAssembly("\t sw \t $t1, ($a0) \t #store to memory");
						}
			
						
						visitor.writeAssembly("");
						
						
					}else{
						//LEFTSIDE NOT an array or record 
						
						if( et.isAddress() ){
							//deref then assign
							visitor.writeAssembly("\t lw \t $a1, -"+et.getOffSet()+"($fp)");
							visitor.writeAssembly("\t lw \t $t1, ($a1) \t #deref to int");
							visitor.writeAssembly("\t sw \t $t1, ($t0) \t #store ");
						}else{
							visitor.writeAssembly("\t lw \t $t1,-"+et.getOffSet()+FRAMEPOINTER);
							visitor.writeAssembly("\t sw \t $t1,($t0) \t # Storing Result");
						}
						
					} 
				
					break;
					
				case REAL:
					visitor.writeAssembly("\t#REAL ASSIGNMENT");
					if(sv.isFunction(id)){
						//return value
						if( et.isAddress() ){
							//is a pointer
							visitor.writeAssembly("\t lw \t $t0, -"+et.getOffSet()+"($fp)");
							visitor.writeAssembly("\t lw \t $v1, ($t0)");
						}else{
							//is a direct value
							visitor.writeAssembly("\t la \t $t0, -"+et.getOffSet()+"($fp)");
							visitor.writeAssembly("\t lw \t $v1, ($t0)");
											
						}
						
					}else if(id instanceof IndexedExpr || id instanceof RecordExpr ){
						//add to memory where pointer is pointing to
						visitor.writeAssembly("");
						
						//load address pointer
						visitor.writeAssembly("\t lw \t $a0,-"+idt.getOffSet()+FRAMEPOINTER);
						
						if( et.isAddress() ){
							//deref then assign
							visitor.writeAssembly("\t lw \t $a1, -"+et.getOffSet()+"($fp)");
							visitor.writeAssembly("\t l.s \t $f1, ($a1) \t #deref to real");
							visitor.writeAssembly("\t s.s \t $f1, ($a0) \t #store ");
							
						}else{
							//value to be assigned
							visitor.writeAssembly("\t l.s \t $f1, -"+et.getOffSet()+"($fp) \t #load direct value");
							visitor.writeAssembly("\t s.s \t $f1, ($a0) \t #store to memory");
						}
				
						visitor.writeAssembly("");
						
						
					}else{
						//LEFTSIDE NOT an array or record 
						
						if( et.isAddress() ){
							//deref then assign
							visitor.writeAssembly("\t lw \t $a1, -"+et.getOffSet()+"($fp)");
							visitor.writeAssembly("\t l.s \t $f1, ($a1) \t #deref real");
							visitor.writeAssembly("\t s.s \t $f1, ($t0) \t #store ");
						}else{
							visitor.writeAssembly("\t l.s \t $f1,-"+et.getOffSet()+FRAMEPOINTER);
							visitor.writeAssembly("\t s.s \t $f1,($t0) \t # Storing Result");
						}
						
					} 
					break;
			}
			
			
			visitor.writeAssembly("\t#END ASSIGN STMT");			
			
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
		temps.addAll(id.getRequiredTemps());
		temps.addAll(expr.getRequiredTemps());
		return temps;
	}
}

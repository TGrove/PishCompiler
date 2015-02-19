package pish.ast;
import pish.symbol.*;
import java.util.*;
import pish.intermediate.*;

public abstract class ASTNode {
	
	public final String FRAMEPOINTER = "($fp)";
	public final String STACKPOINTER = "($sp)";
	
	public static int compoundNumber=0;
	protected int lineNumber;
	
	public int getLineNumber(){
		return lineNumber;
	}
	
	public void setLineNumber(int line){
		this.lineNumber = line;
	}

	public abstract SystemType checkType(SymbolVisitor visit);
	public abstract void accept(PrinterVisitor visit,int level);
	public abstract void accept(SymbolVisitor visit,Variable Type);
	public abstract Integer getSize(SymbolVisitor visitor);
	public abstract void accept(IntermediateVisitor visitor);
	public abstract Object getValue();
	public abstract Temp genCode(IntermediateVisitor visitor);
	public abstract List<Temp> getRequiredTemps();
	
	public static int hash(Object o) {
		return o == null ? 0 : o.hashCode();
	}

	public static String getNextCompoundStmtName() {
		// TODO Auto-generated method stub
		return "CompoundStmt"+compoundNumber++;
	}
	
}

package pish.ast;
import pish.symbol.*;
public abstract class ASTNode {
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
	
	public static int hash(Object o) {
		return o == null ? 0 : o.hashCode();
	}
}

package pish.intermediate;
import pish.ast.*;
import pish.symbol.SymbolVisitor;
public interface IntermediateVisitor {

	
	public void writeIntermediateCode(String str);
	public void writeAssembly(String str);
	public void visit(ASTNode node);
	public Temp genCode(ASTNode node);
	public void setTab(int i);
	public void addTab(int i);
	public SymbolVisitor getSymbolVisitor();
	public boolean isDataSegment();
	public void setDataSegment(boolean b);
	public boolean isIntermediateCode();
}

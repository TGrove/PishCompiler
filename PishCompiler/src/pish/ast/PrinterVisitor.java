package pish.ast;

public interface PrinterVisitor {
	public void visit(ASTNode node,int level);
	public void visit(Operator op,int level);
	public void visit(String value, int level);
	public void visit(double value, int level);
}

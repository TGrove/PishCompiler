package pish.symbol;
import java.util.List;

import pish.ast.*;

public interface SymbolVisitor {

	public void visit(Program program);
	public void visit(Constant constant);
	public void visit(TypeDef type);
	public void visit(SimpleDecleration simpleDec);
	public void visit(SubProgramDecleration subDec);
	public void visit(ConstantList constantList);
	public void visit(TypeDefList typeDefList);
	public void visit(SimpleDeclerationList simpleDecList);
	public void visit(SubProgramDeclerationList subProgramList);
	public void visit(StmtList stmtList);
	public Symbol registerSymbol(ASTNode node , ASTNode type);
	public void visit(Decleration decleration);
	public void visit(DeclerationList list);
	public void visit(Expr expr);
	public void visit(ExprList list);
	public void visit(Stmt stmt);
	public void visit(CompoundStmt stmt);
	public void closeScope();
	public void openScope(ASTNode scopeNode);
	public void registerMethod(FuncVar id, SimpleDeclerationList fields);
	public List<SystemType> getArgs(FuncVar id);
	public SystemType lookup(ASTNode node);
	public void openRecordScope(ASTNode id) throws Exception;
	public void closeRecordScope(ASTNode id) throws Exception;
	public void createRecordScope(ASTNode id);
	public void finishRecordScope(ASTNode id) throws Exception;
	public boolean isMember(ASTNode id) ;
	public boolean isConstant(Variable id);
	public void incrementOffSetCounter(Integer tmpInt);
	public int getSizeOfASTNode(ASTNode node) throws Exception;
	public int getCurrOffSet();
	public int getIntValue(ASTNode node) throws Exception;
	public double getRealValue(ASTNode node) throws Exception;
	public Symbol getSymbol(ASTNode node) throws Exception ;
	public Integer getSizeOfArrayRecord(ASTNode node);
	public boolean isArray(ASTNode node); 
	public boolean isRecord(ASTNode node);
	public boolean isGlobalVariable(ASTNode node);
	public void switchAddress(boolean on);
	public List<Symbol> getAllSymbols();
	public boolean isFunction( ASTNode node );
	public boolean isAddress();
}

package pish.symbol;

import pish.ast.*;

public class Symbol {
	
	private ASTNode node;
	private ASTNode type;
	private int depth;
	
	public Symbol ( ASTNode node,ASTNode type){
		this(node,type,-1);
	}	

	public Symbol ( ASTNode node,ASTNode type, int depth){
		this.node = node;
		this.type = type;
		this.depth = depth;
	}

	public ASTNode getNode(){
		return node;
	}

	public int getDepth(){
		return depth;
	}
	
	public void setDepth(int depth){
		this.depth = depth;
	}
	
	public void setNode(ASTNode node){
		this.node = node;
	}
	
	public ASTNode getType(){
		return type;
	}
	
	public void setType(ASTNode type){
		this.type = type;
	}
	
	public String toString(){
		return "Symbol["+node+"] Type["+type+"] depth["+depth+"] at line "+node.getLineNumber();
	}
}

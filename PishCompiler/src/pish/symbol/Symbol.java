package pish.symbol;

import pish.ast.*;

public class Symbol implements Comparable<Symbol> {
	
	private ASTNode node;
	private ASTNode type;
	private int depth;
	private Integer offSet;
	private Integer size;
	private boolean isAddress;
	
	public Symbol ( ASTNode node,ASTNode type){
		this(node,type,-1);
	}	

	public Symbol ( ASTNode node,ASTNode type, int depth){
		this.node = node;
		this.type = type;
		this.depth = depth;
		this.setOffSet(null);
		this.setSize(0);
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
		return "Symbol["+node+"] Type["+type+"] depth["+depth+"] offset["+offSet+"] size["+size+"] at line "+node.getLineNumber();
	}

	public void setOffSet(Integer offSet) {
		this.offSet = offSet;
	}

	public Integer getOffSet() {
		return offSet;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public void setIsAddress(boolean isAddress) {
		this.isAddress=isAddress;
	}
	
	public boolean isAddress(){
		return isAddress;
	}
	
	@Override
	public int compareTo(Symbol symbol) {
		if ( this.offSet < symbol.getOffSet() )
		{
			return -1;
		}
		if ( this.offSet > symbol.getOffSet() )
		{
			return 1;
		}
		return 0;
	}
}

package pish.intermediate;
import java.util.*;

import pish.ast.ASTNode;
import pish.symbol.*;

import java.io.*;

public class IntermediateNodeVisitor implements IntermediateVisitor {

	
	HashMap<String,Symbol> intermediateMap = new HashMap<String,Symbol>();
	
	public String intermediateCodeFile="pish.intr";
	public String assemblyFile="pish.mips";
	private PrintWriter printerCode;
	private PrintWriter printerAssembly;
	private boolean isDataSeg;
	private boolean isInterRep;
	public SymbolVisitor symbolVisitor;
	int tabs=0;
	
	public IntermediateNodeVisitor ( SymbolVisitor symbolVisit ){
		this.symbolVisitor = symbolVisit;
		this.setDataSegment(false);
		setUpFiles();
	}
	


	private void setUpFiles(){
		try{
//			treeFile = new File(filePath);	
			printerCode = new PrintWriter(new BufferedWriter(new FileWriter(intermediateCodeFile)));
			printerAssembly = new PrintWriter(new BufferedWriter(new FileWriter(assemblyFile)));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void writeAssembly(String str) {
		printerAssembly.println(getTabs()+str);
		printerAssembly.flush();
		
	}

	@Override
	public void writeIntermediateCode(String str) {
		printerCode.println(getTabs()+str);
		printerCode.flush();
		
	}

	@Override
	public void visit(ASTNode node) {
		node.accept(this);
	}

	@Override
	public Temp genCode(ASTNode node) {
		node.genCode(this);
		return null;
	}

	@Override
	public void setTab(int i) {
		tabs = i;
	}
	
	public String getTabs(){
		String str ="";
		for ( int i = 0 ;i < tabs; i++){
			str+="\t";
		}
		return str;
	}

	@Override
	public SymbolVisitor getSymbolVisitor() {
		return symbolVisitor;
	}

	@Override
	public void addTab(int i) {
		tabs+=i;
	}

	@Override
	public boolean isDataSegment() {
		return isDataSeg;
	}
	
	@Override
	public void setDataSegment(boolean b) {
		this.isDataSeg = b;		
	}



	@Override
	public boolean isIntermediateCode() {
		return isInterRep();
	}



	public void setInterRep(boolean isInterRep) {
		this.isInterRep = isInterRep;
	}



	public boolean isInterRep() {
		return isInterRep;
	}
	
	
	
}

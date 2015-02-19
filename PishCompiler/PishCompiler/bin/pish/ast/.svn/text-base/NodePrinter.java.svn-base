package pish.ast;

import java.io.*;

public class NodePrinter implements PrinterVisitor {
	private PrintWriter printer;
	private String filePath = "parseTree.txt";
	
	public NodePrinter(){
		this.setUpFile();
	}
	
	private void setUpFile(){
		try{
//			treeFile = new File(filePath);	
			printer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void tabPrint(int level)	{
		for ( int i = 0 ; i < level ; i++){
//			System.out.print("\t");
			printer.print("\t");
		}
	}
	
	@Override
	public void visit(ASTNode node, int level) {
		tabPrint(level);
//		System.out.println(node);
		printer.println(node);
		printer.flush();
	}

	@Override
	public void visit(Operator op, int level) {
		tabPrint(level);
//		System.out.println(op);
		printer.println(op);
		printer.flush();
	}

	@Override
	public void visit(String value, int level) {
		tabPrint(level);
//		System.out.println(value);
		printer.println(value);
		printer.flush();
	}

	@Override
	public void visit(double value, int level) {
		tabPrint(level);
//		System.out.println(value);
		printer.println(value);
		printer.flush();
	}

}

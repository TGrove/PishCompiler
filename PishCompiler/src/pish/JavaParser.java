package pish;

import java.io.*;

import java_cup.runtime.Symbol;
import pish.ast.*;
import pish.intermediate.*;
import pish.symbol.*;

/**
 * Simple test driver for the java parser. Just runs it on some
 * input files, gives no useful output.
 */
public class JavaParser {

  public static void main(String argv[]) {
	Symbol parseSymbol = null;
	
    for (int i = 0; i < argv.length; i++) {
      try {
        System.out.println("Parsing ["+argv[i]+"]");
        PishLex s = new PishLex(new FileReader(argv[i]));
        PishCup p = new PishCup(s);
        
        try{
        	parseSymbol = p.parse();
        }catch (Exception e){
        	//System.out.println("ERROR:");
        	parseSymbol = null;
        }
        
        
        if(parseSymbol == null){
        	System.out.println("COMPILATION FAILED, CHECK ERRORS.");
        	return;
        }
        
        ASTNode root = (ASTNode) parseSymbol.value;
        
        NodePrinter printer = new NodePrinter();
        
        root.accept(printer, 0);
        
        SymbolNodeVisitor symbolVisit = new SymbolNodeVisitor();
        symbolVisit.visit((Program)root);
        symbolVisit.printSymbolTable();
        
        IntermediateNodeVisitor interVisitor = new IntermediateNodeVisitor(symbolVisit);
        interVisitor.setInterRep(true);
        interVisitor.visit(root);
        interVisitor.setInterRep(false);
        interVisitor.visit(root);
        
   //     System.out.println("No errors.");
      }
      catch (Exception e) {
        e.printStackTrace(System.out);
        System.exit(1);
      }
    }
  }

}

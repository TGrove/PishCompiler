package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.*;

public class Constant extends Variable {
	
	public ConstantId id;
	public Expr expr; // right hand side
	
	public Constant(ConstantId id , Expr expr){
		this.id = id;
		this.expr = expr;
	}

	public String toString(){
		return "Constant";
	}

	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(id, level);
		id.accept(visit, level+1);
		visit.visit(expr, level);
		expr.accept(visit, level+1);
		visit.visit("]",level);	
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);
		visit.registerSymbol(id, expr);
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return expr.checkType(visit);
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		return expr.getSize(visitor);
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		if ( visitor.isIntermediateCode()) {
			ASTNode node = expr;
			//int or real or char= "label .word value"
			int sign=1;
			if (expr instanceof UnaryOpExpr){
				node = ((UnaryOpExpr)expr).getOperand();
				sign = ((UnaryOpExpr)expr).getSign();
			}
			if ( node instanceof ConstantId){
				SystemType stype = visitor.getSymbolVisitor().lookup(node);
				try{
					if (stype.getType() == SystemType.Type.INT){
						visitor.writeIntermediateCode("CONST "+id.id+ " "+ sign * (Integer)visitor.getSymbolVisitor().getIntValue(node)) ;
					}
					if (stype.getType() == SystemType.Type.REAL){
						visitor.writeIntermediateCode("CONST "+id.id+ " "+ sign * (Double)visitor.getSymbolVisitor().getRealValue(node)) ;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			if ( node instanceof IntegerExpr ){
				visitor.writeIntermediateCode("CONST "+id.id+ " "+ sign * (Integer)node.getValue()) ;
			}
			if ( node instanceof RealExpr ){
				visitor.writeIntermediateCode("CONST "+id.id+ " "+ sign * (Double)node.getValue()) ;
			}
			if ( node instanceof CharExpr ){
				visitor.writeIntermediateCode("CONST "+id.id+ " "+ node.getValue()) ;
			}
			
		}else{
			ASTNode node = expr;
			//int or real or char= "label .word value"
			int sign=1;
			if (expr instanceof UnaryOpExpr){
				node = ((UnaryOpExpr)expr).getOperand();
				sign = ((UnaryOpExpr)expr).getSign();
			}
			if ( node instanceof ConstantId){
				SystemType stype = visitor.getSymbolVisitor().lookup(node);
				try{
					if (stype.getType() == SystemType.Type.INT){
						visitor.writeAssembly(""+ id.id +":\t.word\t"+ sign * (Integer)visitor.getSymbolVisitor().getIntValue(node) +"");
					}
					if (stype.getType() == SystemType.Type.REAL){
						visitor.writeAssembly(""+ id.id +":\t.float\t"+ sign * (Double)visitor.getSymbolVisitor().getRealValue(node) +"");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			if ( node instanceof IntegerExpr ){
				visitor.writeAssembly(""+ id.id +":\t.word\t"+ sign * (Integer)node.getValue() +"");
			}
			if ( node instanceof RealExpr ){
				visitor.writeAssembly(""+ id.id +":\t.float\t"+ sign * (Double)node.getValue() +"");
			}
			if ( node instanceof CharExpr ){
				visitor.writeAssembly(""+ id.id+ ":\t.word\t'"+ node.getValue() +"'" );
			}
			
			//String "Label .ascii "WORDS" "
			//padding "<none> .space (ASTNODE.szie - WORDS.length - 1)"
			if ( node instanceof StringExpr ){
				String tmpStr = node.getValue().toString();
				String tmpStr2;
				
				tmpStr2 = tmpStr.substring(1, tmpStr.length()-1);
				//System.out.println(tmpStr2);
				
				visitor.writeAssembly(""+ id.id +":\t.ascii\t"+ "\""+ tmpStr2+ "\"" +"");
				try{
				visitor.writeAssembly("\t\t.space\t"+ (visitor.getSymbolVisitor().getSizeOfASTNode(id) 
						- ((String)node.getValue()).length() - 1));
				}catch(Exception e){/*blah*/ e.printStackTrace(); }
			}			
		}

		
		
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Temp genCode(IntermediateVisitor visitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Temp> getRequiredTemps() {
		List<Temp> temps = new ArrayList<Temp>();

		return temps;
	}
	
}

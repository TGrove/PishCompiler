package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.JavaSymbol;
import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class BinaryOpExpr extends Expr {
	public Expr lExpr;
	public Expr rExpr;
	public Operator op;
	Temp requiredTemp= Temp.getNewTemp();

	
	public BinaryOpExpr(Expr lExpr,String op, Expr rExpr){
		this(lExpr,Operator.lookup(op),rExpr);
	}

	public BinaryOpExpr(Expr lExpr,Operator op, Expr rExpr){
		this.lExpr=lExpr;
		this.op=op;
		this.rExpr=rExpr;
	}

	public BinaryOpExpr(Expr e1, JavaSymbol r, Expr e2) {
		this(e1,(String)r.getValue(),e2);
		setLineNumber(r.getLine());
	}

	
	@Override 
	public int getLineNumber(){
		return this.lExpr.getLineNumber();
	}
	
	public String toString(){
		return "BinaryOpExpr";
	}

	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(lExpr, level);
		lExpr.accept(visit, level+1);
		visit.visit(op, level);
		visit.visit(rExpr, level);
		rExpr.accept(visit, level+1);
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);
		requiredTemp.setAddress(visit.isAddress());
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		SystemType lType = lExpr.checkType(visit);
		SystemType rType = rExpr.checkType(visit);
		if ( !op.isRelational() && (lType.getType() == SystemType.Type.CHAR || rType.getType() == SystemType.Type.CHAR)) {
			System.out.println("Error (line "+getLineNumber()+") cannot perform computation using ("+op+")  for any CHAR type object");
			
		}
		if ( lType.getType() != rType.getType() ){
			System.out.println("Error (line "+getLineNumber()+") cannot perform comparison/computation using ("+op+")  for ("+lExpr+"("+lType+")) to ("+rExpr+"("+rType+")) ");
		}
		if ( op.isRelational() )
		{
			return SystemType.INT();
		}
		return lType;
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		return null;
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Temp genCode(IntermediateVisitor visitor) {
		if ( visitor.isIntermediateCode()){
			Temp l =  lExpr.genCode(visitor);
			Temp r =  rExpr.genCode(visitor);
			requiredTemp.setOffSet(visitor.getSymbolVisitor().getCurrOffSet());
			visitor.getSymbolVisitor().incrementOffSetCounter(requiredTemp.getSizeOf());

			visitor.writeIntermediateCode(requiredTemp+" := "+ l+ " "+op.toIntermediateCode()+" "+ r);
		}
		else {
			visitor.writeAssembly("\t#BEGIN BOP ("+op+")");
			SymbolVisitor sv = visitor.getSymbolVisitor();
			
			Temp l =  lExpr.genCode(visitor);
			Temp r =  rExpr.genCode(visitor);
			
			//l.s for float
			//lw for word
			SystemType lst = lExpr.checkType(sv);
			SystemType rst = rExpr.checkType(sv);
			
			/*LEFT SIDE LOAD*/
			visitor.writeAssembly("");
			visitor.writeAssembly("\t\t#LEFT HAND SIDE");
			switch(lst.getType()){
				case INT:
					if (l.isAddress()){
						visitor.writeAssembly("\t lw \t $a0, -"+l.getOffSet()+FRAMEPOINTER+"\t #RETRIVING LEFT ADDRESS " );
						visitor.writeAssembly("\t lw \t $t0, ($a0) \t #GETTING VALUE OF LEFT ADDRESS ");
					}else{
						visitor.writeAssembly("\t lw \t $t0,  -"+l.getOffSet()+FRAMEPOINTER+"\t #RETRIVING LEFT VALUE");
					}
					break;
					
				case REAL:
					if (l.isAddress()){
						visitor.writeAssembly("\t lw \t $a0, -"+l.getOffSet()+FRAMEPOINTER+"\t #RETRIVING LEFT ADDRESS " );
						visitor.writeAssembly("\t l.s \t $f0, ($a0) \t #GETTING VALUE OF LEFT ADDRESS ");	
					}else{
						visitor.writeAssembly("\t l.s \t $f0,  -"+l.getOffSet()+FRAMEPOINTER+"\t #RETRIVING LEFT VALUE");
					}
					break;
				
			}
			visitor.writeAssembly("");
			visitor.writeAssembly("\t\t#RIGHT HAND SIDE");
			/*RIGHT SIDE LOAD*/
			switch(rst.getType()){
				case INT:
					if (r.isAddress()){
						visitor.writeAssembly("\t lw \t $a0, -"+r.getOffSet()+FRAMEPOINTER+"\t #RETRIVING RIGHT ADDRESS " );
						visitor.writeAssembly("\t lw \t $t1, ($a0)  \t #GETTING VALUE OF RIGHT ADDRESS ");

					}else{
						visitor.writeAssembly("\t lw \t $t1,  -"+r.getOffSet()+FRAMEPOINTER+"\t #RETRIVING LEFT VALUE");
						
					}
					break;
					
				case REAL:
					if (r.isAddress()){
						visitor.writeAssembly("\t lw \t $a0, -"+r.getOffSet()+FRAMEPOINTER+"\t #RETRIVING RIGHT ADDRESS " );
						visitor.writeAssembly("\t l.s \t $f1, ($a0)  \t #GETTING VALUE OF RIGHT ADDRESS ");
						
					}else{
						visitor.writeAssembly("\t l.s \t $f1,  -"+r.getOffSet()+FRAMEPOINTER+"\t #RETRIVING LEFT VALUE");
					}
					
					break;
				
			}
			
			switch( op ){
				case MINUS:
					/*LEFT SIDE PRECIDENCE*/
					switch(lst.getType()){
						case INT:
							visitor.writeAssembly("\t sub \t $t2, $t0, $t1");
							visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
							break;
							
						case REAL:
							visitor.writeAssembly("\t sub.s \t $f2, $f0, $f1");
							visitor.writeAssembly("\t s.s \t $f2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
							break;
					}
					break;
					
				case PLUS:
					/*LEFT SIDE PRECIDENCE*/
					switch(lst.getType()){
						case INT:
							visitor.writeAssembly("\t add \t $t2, $t0, $t1");
							visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
							break;
							
						case REAL:
							visitor.writeAssembly("\t add.s \t $f2, $f0, $f1");
							visitor.writeAssembly("\t s.s \t $f2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
							break;
					}
					
					break;
				case MULT:
					/*LEFT SIDE PRECIDENCE*/
					switch(lst.getType()){
						case INT:
							visitor.writeAssembly("\t mul \t $t2, $t0, $t1");
							visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
							break;
							
						case REAL:
							visitor.writeAssembly("\t mul.s \t $f2, $f0, $f1");
							visitor.writeAssembly("\t s.s \t $f2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
							break;
					}
					break;
				case DIV: 
				case DIVIDE:
					/*LEFT SIDE PRECIDENCE*/
					switch(lst.getType()){
						case INT:
							visitor.writeAssembly("\t div \t $t0, $t1");
							/*Store Integer*/
							//visitor.writeAssembly("\t mfhi \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER+" \t #Store Integer");
							visitor.writeAssembly("\t mflo \t $t2");
							visitor.writeAssembly("\t sw \t $t2 ,-"+requiredTemp.getOffSet()+FRAMEPOINTER+" \t  #Store remainder");
							/*Store Remainder*/
							//dropping
							break;
							
						case REAL:
							visitor.writeAssembly("\t div.s \t $f2, $f0, $f1");
							visitor.writeAssembly("\t s.s \t $f2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
							break;
					}
					break;
					
				case MOD: 
					/*LEFT SIDE PRECIDENCE*/
					switch(lst.getType()){
						case INT:
							visitor.writeAssembly("\t div \t $t0, $t1");
							/*Store Integer*/
							//don't care
							
							/*Store Remainder*/
							//visitor.writeAssembly("\t mflo \t $t2");
							visitor.writeAssembly("\t mfhi \t $t2");
							visitor.writeAssembly("\t sw \t $t2 ,-"+requiredTemp.getOffSet()+FRAMEPOINTER+" \t  #Store remainder");
							break;
							
						case REAL:
							System.out.println("TOM SAYS:OMFG WE ARE MODING A REAL AHHHHHHHHHHHHHHHHH.");
							break;
					}
					break;
				case LT: 
					switch(lst.getType()){
						case INT:
							visitor.writeAssembly("\t slt \t $t2, $t0, $t1");
							visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
							break;
							
						case REAL:
							visitor.writeAssembly("\t slt \t $t2, $t0, $t1");
							visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
							break;
					}					
					break;
				case GT: 
					switch(lst.getType()){
					case INT:
						visitor.writeAssembly("\t sgt \t $t2, $t0, $t1");
						visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
						break;
						
					case REAL:
						visitor.writeAssembly("\t sgt \t $t2, $t0, $t1");
						visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
						break;
					}
					break;
				case LE: 
					switch(lst.getType()){
					case INT:
						visitor.writeAssembly("\t sle \t $t2, $t0, $t1");
						visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
						break;
						
					case REAL:
						visitor.writeAssembly("\t sle \t $t2, $t0, $t1");
						visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
						break;
					}
					break;
				case GE: 
					switch(lst.getType()){
					case INT:
						visitor.writeAssembly("\t sge \t $t2, $t0, $t1");
						visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
						break;
						
					case REAL:
						visitor.writeAssembly("\t sge \t $t2, $t0, $t1");
						visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
						break;
					}
					break;
				case LAND: 
					switch(lst.getType()){
					case INT:
						visitor.writeAssembly("\t seq \t $t2, $t0, $t1");
						visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
						break;
						
					case REAL:
						visitor.writeAssembly("\t seq \t $t2, $t0, $t1");
						visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
						break;
					}
					break;
				case LOR: 
					switch(lst.getType()){
					case INT:
						visitor.writeAssembly("\t or \t $t2, $t0, $t1");
						visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
						break;
						
					case REAL:
						visitor.writeAssembly("\t or \t $t2, $t0, $t1");
						visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
						break;
					}
					break;
				case EQ: 
					switch(lst.getType()){
					case INT:
						visitor.writeAssembly("\t seq \t $t2, $t0, $t1");
						visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
						break;
						
					case REAL:
						visitor.writeAssembly("\t seq \t $t2, $t0, $t1");
						visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
						break;
					}
					break;
				case NOTEQ: 
					switch(lst.getType()){
					case INT:
						visitor.writeAssembly("\t bne \t $t2, $t0, $t1");
						visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
						break;
						
					case REAL:
						visitor.writeAssembly("\t bne \t $t2, $t0, $t1");
						visitor.writeAssembly("\t sw \t $t2, -"+requiredTemp.getOffSet()+FRAMEPOINTER);
						break;
					}
					break;
		
				}
			
			
			visitor.writeAssembly("\t#END BOP("+op+")");

			
		}
		return requiredTemp;
	}

	@Override
	public List<Temp> getRequiredTemps() {
		List<Temp> temps = new ArrayList<Temp>();
		List<Temp> lExprTemps = lExpr.getRequiredTemps();
		List<Temp> rExprTemps = rExpr.getRequiredTemps();
		temps.addAll(lExprTemps);
		temps.addAll(rExprTemps);
		temps.add(requiredTemp);
		return temps;
	}
}

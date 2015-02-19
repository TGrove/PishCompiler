package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.JavaSymbol;
import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.Symbol;
import pish.symbol.SymbolVisitor;


public class ConstantId extends Variable {

	public String id;
	public Temp  requiredTemp = Temp.getNewTemp();
	
	public ConstantId( String id ) {
		this.id = id;
	}

	public ConstantId(JavaSymbol id2) {
		id = (String)id2.getValue();
		setLineNumber(id2.getLine());
	}

	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit(id, level);		
	}

	public String toString(){
		return id;
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		visit.registerSymbol(this, Type);
		
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return visit.lookup(this);
	}

	@Override
	public Integer getSize(SymbolVisitor visitor){
		try{
			return visitor.getSizeOfASTNode(this);
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public Temp genCode(IntermediateVisitor visitor) {
		if ( visitor.isIntermediateCode()){
			requiredTemp.setOffSet(visitor.getSymbolVisitor().getCurrOffSet());
			visitor.getSymbolVisitor().incrementOffSetCounter(requiredTemp.getSizeOf());
			requiredTemp.setAddress(visitor.getSymbolVisitor().isGlobalVariable(this));
		}else{
			SymbolVisitor sv;
			Symbol sym;
			
			sv = visitor.getSymbolVisitor();
			
			try{
				sym = sv.getSymbol(this);
				//send in label address
				visitor.writeAssembly("\t la \t $t0, "+sym.getNode().toString());
				visitor.writeAssembly("\t sw \t $t0, -"+(requiredTemp.getOffSet()) +FRAMEPOINTER);
				requiredTemp.setAddress(true);
				
			}catch(Exception e){
				
			}	
		}
		return requiredTemp;
	}

	@Override
	public List<Temp> getRequiredTemps() {
		List<Temp> temps = new ArrayList<Temp>();
		temps.add(requiredTemp);
		return temps;
	}
}

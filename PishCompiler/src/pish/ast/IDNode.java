package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.JavaSymbol;
import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.*;


public class IDNode extends Variable {

	public String id;
	Temp requiredTemp = Temp.getNewTemp();
	
	public IDNode( String id ) {
		this.id = id;
	}

	public IDNode(JavaSymbol id2) {
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
	public Integer getSize(SymbolVisitor visitor) {
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
		// TODO Auto-generated method stub
		return id;
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
				if (sv.isFunction(this))
					return requiredTemp;
				/*IF Global Variable (will have a label)*/
				if (requiredTemp.isAddress()){
					//send in label address
					visitor.writeAssembly("\t la \t $t0, "+sym.getNode().toString());
					visitor.writeAssembly("\t sw \t $t0, -"+(requiredTemp.getOffSet()) +FRAMEPOINTER);
				}else{
				/*On stack (current FP - offset)*/	
					//send in address of stack memory
					requiredTemp.setAddress(true);
					visitor.writeAssembly("\t la \t $t0, -"+sym.getOffSet()+FRAMEPOINTER);
					visitor.writeAssembly("\t sw \t $t0, -"+(requiredTemp.getOffSet()) +FRAMEPOINTER);
				}
				
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

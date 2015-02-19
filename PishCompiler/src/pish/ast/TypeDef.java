package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class TypeDef extends Decleration {

	public IDNode id;
	public Variable type;
	
	public TypeDef( IDNode id, Variable type){
		this.id = id;
		this.type = type;
	}

	public String toString(){
		return "TypeDef";
	}
	
	@Override 
	public int getLineNumber(){
		return this.id.getLineNumber();
	}
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(id, level);
		id.accept(visit, level+1);
		visit.visit(type, level);
		type.accept(visit, level+1);
		visit.visit("]",level);
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		checkType(visit);
		visit.registerSymbol(id, type);
		if ( type instanceof RecordType ){
			
			visit.createRecordScope(id);
			visit.visit(type);
			try{
				visit.finishRecordScope(id);
			}catch(Exception e ){
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return type.checkType(visit);
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(IntermediateVisitor visitor) {
		if ( visitor.isIntermediateCode())
		{}
		else{
			try{
				visitor.writeAssembly(""+id.id +":\t\t.space\t"+ (visitor.getSymbolVisitor().getSizeOfASTNode(id) ) );
			}catch(Exception e){
			//	e.printStackTrace();
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

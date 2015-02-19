package pish.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.Symbol;
import pish.symbol.SymbolVisitor;


import pish.ast.SystemType;
public class VariableList extends Expr {
	public Variable id;
	public VariableList list;
	
	public VariableList(Variable id) {
		this(id, null);
	}
	public VariableList(Variable id,VariableList list) {
		this.id = id;
		this.list = list;
	}
	
	public String toString(){
		return "VariableList";
	}
	
	public List<SystemType> toArray(SymbolVisitor visit){
		Stack<SystemType> ret = new Stack<SystemType>();
		if ( list != null ) {
			List<SystemType> tempList = list.toArray(visit);
			for ( SystemType s : tempList ){
				ret.push(s);
			}
		}
		ret.push(SystemType.NULL());
	
		return ret;
	}
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(id, level);
		id.accept(visit, level+1);
		if ( list != null ) {
			visit.visit(list, level);
			list.accept(visit, level+1);
		}
		visit.visit("]",level);
		
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		if ( list != null ) {
			list.accept(visit, Type);
		}
		visit.registerSymbol(id, Type);

		
		if ( Type instanceof RecordType ){
			
			visit.createRecordScope(id);
			visit.visit(Type);
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
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer getSize(SymbolVisitor visitor) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void accept(IntermediateVisitor visitor) {
		Symbol tempSym;
		SystemType type;
		
		if ( list != null ) {
			visitor.visit(list);
		}
		
		if ( visitor.isIntermediateCode()){
		}else{
			
			try{
				//look up symbol
				tempSym = visitor.getSymbolVisitor().getSymbol(id);
				
				//if data seg, .data "global decs"
				if (visitor.isDataSegment() ){
						if ( visitor.getSymbolVisitor().isArray(tempSym.getType()) || visitor.getSymbolVisitor().isRecord(tempSym.getType()) ){
							visitor.writeAssembly(""+id.toString()+": \t.space\t"+tempSym.getSize() );						
						}
						else{
							type = visitor.getSymbolVisitor().lookup(tempSym.getType());
							
							switch(type.getType()){
								case INT:
									visitor.writeAssembly(""+id.toString() +": \t.word\t0"  );
									break;
									
								case REAL:
									visitor.writeAssembly(""+tempSym.getNode().toString() +": \t.float\t0"  );
									break;
									
								case CHAR:
									//TODO
									break;
							}
						}
					
				}else{
				//push on stack (loading values as needed or skipping space)
					
				}
				
			}catch(Exception e){
				
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

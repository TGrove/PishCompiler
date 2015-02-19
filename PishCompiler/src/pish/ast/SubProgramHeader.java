package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class SubProgramHeader extends Decleration {
	public FuncVar id; // name of function or procedure
	public SimpleDeclerationList fields; // arguments
	public Variable returnType; // return type

	/* this is for declaring forward definition of procedures */
	public SubProgramHeader ( FuncVar id) { 
		this(id,null,null);
	}	
	
	/* this is for declaring forward definition of procedures */
	public SubProgramHeader ( FuncVar id, SimpleDeclerationList fields ) { 
		this(id,fields,null);
	}	
	
	/* this is for declaring forward definition of function */
	public SubProgramHeader ( FuncVar id, SimpleDeclerationList fields , Variable returnType){
		this.id = id;
		this.fields = fields;
		this.returnType = returnType;
	}

	
	public String toString(){
		return "SubProgramHeader";
	}
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		visit.visit("[",level);
		visit.visit(id, level);
		id.accept(visit, level+1);
		if ( fields != null ){
			visit.visit(fields, level);
			fields.accept(visit, level+1);
		}
		if ( returnType != null ) {
			visit.visit(returnType, level);
			returnType.accept(visit, level+1);
		}
		visit.visit("]",level);
		
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		if ( fields != null ) {
			visit.switchAddress(true);
			visit.visit(fields);
			visit.switchAddress(false);			
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
		// TODO Auto-generated method stub
		
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

package pish.ast;

import pish.symbol.SymbolVisitor;

public class SubProgramDecleration extends Decleration {
	public SubProgramHeader header;
	public SimpleDeclerationList declerations;
	public StmtList stmt;  // body
	public SubProgramDeclerationList list; // Sub programs under this function or procedure
		
	public SubProgramDecleration ( SubProgramHeader header){
		this(header,null,null);
	}
	
	public SubProgramDecleration ( SubProgramHeader header,SimpleDeclerationList declerations ,StmtList stmt) { 
		this(header,declerations,null,stmt);
	}

	/* main constructor */
	public SubProgramDecleration ( SubProgramHeader header,SimpleDeclerationList declerations, SubProgramDeclerationList list,StmtList stmt ){
		this.header = header;
		this.declerations = declerations;
		this.stmt = stmt;
		this.list = list;
	}

	public String toString(){
		return "SubProgramDecleration";
	}

	
	@Override
	public void accept(PrinterVisitor visit, int level) {
		
		visit.visit("[",level);
		if ( header != null ) {
			visit.visit(header, level);
			header.accept(visit, level+1);
		}
		if ( declerations != null ) {
			visit.visit(declerations, level);
			declerations.accept(visit, level+1);
		}
		if ( stmt != null ){
			visit.visit(stmt, level);
			stmt.accept(visit, level+1);
		}
		if ( list != null ) {
			visit.visit(list, level);
			list.accept(visit, level+1);
		}
		visit.visit("]",level);
		
	}

	private boolean isForward(){
		return (header != null &&  stmt==null);
	}
	
	private boolean isForwardBody(){
		return (header.fields == null && header.returnType == null && stmt != null);
	}
	
	@Override
	public void accept(SymbolVisitor visit, Variable Type) {
		
		if ( isForward() ){
			visit.registerSymbol(header.id, header.returnType);
			visit.registerMethod(header.id,header.fields);
			visit.openScope(header.id);
			visit.visit(header);
			visit.closeScope();
			return;
		}
		else if ( !isForwardBody() ){
			visit.registerSymbol(header.id, header.returnType);
			visit.registerMethod(header.id,header.fields);
		}
		
		visit.openScope(header.id);
		if ( header != null ){
			visit.visit(header);
		}
		if ( declerations != null ) {
			visit.visit(declerations);
		}
		if ( stmt != null ){
			visit.visit(stmt);
		}
		if ( list != null ) {
			visit.visit(list);
		}
		visit.closeScope();
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		// TODO Auto-generated method stub
		return null;
	}
}

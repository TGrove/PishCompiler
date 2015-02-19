package pish.ast;

import java.util.ArrayList;
import java.util.List;

import pish.intermediate.IntermediateVisitor;
import pish.intermediate.Temp;
import pish.symbol.SymbolVisitor;

public class SystemType extends ASTNode{
	

	public static enum Type {
		CHAR("CHAR") {public int getMemoryBytes(){ return 4; }} ,
		INT("INT")  {public int getMemoryBytes(){ return 4; }} ,
		REAL("REAL") {public int getMemoryBytes(){ return 4; }} ,
		STRING("STRING") {} ,
		NULL("NULL") {} ,
		RECORD("RECORD")
		;
		private String name;
		private Type(String name){this.name = name; };
		public int getMemoryBytes(){ return 0; }
		public String toString(){ return name; }
	}
	
	Type curType;
	
	public SystemType ( Type type ) {
		this.curType = type;
	}
	
	@Override
	public void accept(PrinterVisitor visit, int level) {
	}

	@Override
	public void accept(SymbolVisitor visit, Variable Type) {	
	}

	@Override
	public SystemType checkType(SymbolVisitor visit) {
		return null;
	}

	public static SystemType NULL() {
		return new SystemType(Type.NULL);
	}
	
	public static SystemType CHAR() {
		return new SystemType(Type.CHAR);
	}
	
	public static SystemType REAL() {
		return new SystemType(Type.REAL);
	}
	
	public static SystemType INT() {
		return new SystemType(Type.INT);
	}

	public static SystemType STRING() {
		return new SystemType(Type.STRING);
	}

	public static SystemType RECORD() {
		return new SystemType(Type.RECORD);
	}

	public Type getType(){return curType;}
	
	public String toString(){
		return curType.toString();
	}

	@Override
	public Integer getSize(SymbolVisitor visitor) {
		return curType.getMemoryBytes();
		
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
package pish;

public class JavaSymbol extends java_cup.runtime.Symbol {
	private final int line;
	private final String name;

	public JavaSymbol(int line, String name, int id, Object value) {
		super(id, value);
		this.name = name;
		this.line = line + 1;
	}
	
	public JavaSymbol(int line, String name, int id) {
		super(id, null);
		this.name = name;
		this.line = line + 1;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String toString() {
		
		return  (name);
	}
	
	public int getLine() {
		return line;
	}

	public Object getValue() {
		return value;
	}
}

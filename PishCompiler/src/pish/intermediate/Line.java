package pish.intermediate;

public class Line {
	private String name;
	
	static int lineNo=0;
	
	public Line(int l){
		this.name = "L"+l;
	}
	
	public static Line getNewLine(){
		return new Line(lineNo++);
	}
	
	public String toString(){
		return name;
	}
}

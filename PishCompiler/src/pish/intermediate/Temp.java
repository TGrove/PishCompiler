package pish.intermediate;

public class Temp {
	private String name;
	private int offSet;
	private int sizeOf=4; 
	public static int tempNo=0;
	public boolean address;
	
	public Temp(int i){
		this.name= i+"";
	}
	public Temp(double i){
		this.name= i+"";
	}
	public Temp(String name){
		this.name = name;
	}
	
	public static Temp getNewTemp(){
		return new Temp("T"+tempNo++);
	}

	public String toString(){
		return name+"("+offSet+")";
		
	}
	
	public int getSizeOf(){
		return sizeOf;
	}
	
	public int getOffSet(){
		return offSet;
	}
	
	public void setOffSet(int offSet){
		this.offSet = offSet;
	}
	
	public boolean isAddress(){
		return this.address;
	}
	
	public void setAddress(boolean b){
		this.address = b;
	}
}

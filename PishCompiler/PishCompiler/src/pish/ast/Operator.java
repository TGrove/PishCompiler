package pish.ast;

public enum Operator {
	MINUS, PLUS, MULT, DIV,DIVIDE, LT, GT, LE, GE, LAND, LOR, MOD, EQ, NOTEQ, NOT;
	
	/** Prints the operator in the same way it appears in the program.
	 */
	
	public static Operator lookup(String op){
			if ( op.equals("-"))
				return MINUS;
			if ( op.equals("+"))
				return PLUS;
			if ( op.equals("*"))
				return MULT;
			if ( op.equals("/"))
				return DIV;
			if ( op.equalsIgnoreCase("div"))
				return DIVIDE;
			if ( op.equals("<"))
				return LT;
			if ( op.equals(">"))
				return GT;
			if ( op.equals("<="))
				return LE;
			if ( op.equals(">="))
				return GE;
			if ( op.equalsIgnoreCase("and"))
				return LAND;
			if ( op.equalsIgnoreCase("or"))
				return LOR;
			if ( op.equalsIgnoreCase("mod"))
				return MOD;
			if ( op.equalsIgnoreCase("="))
				return EQ;
			if ( op.equalsIgnoreCase("<>"))
				return NOTEQ;	
			if ( op.equalsIgnoreCase("NOT"))
				return NOT;	
			
			return null;
	}
	public String toString() {
		switch (this) {
			case MINUS: return "-";
			case PLUS: return "+";
			case MULT: return "*";
			case DIV: return "/";
			case DIVIDE:return "div";
			case MOD: return "mod";
			case LT: return "<";
			case GT: return ">";
			case LE: return "<=";
			case GE: return ">=";
			case LAND: return "and";
			case LOR: return "or";
			case EQ: return "=";
			case NOTEQ: return "<>";
			case NOT: return "not";
			default: throw new RuntimeException("Unexpted value: " + this.name());
		}
	}
	public boolean isRelational() {
		switch (this) {
		case LT:
		case GT: 
		case LE: 
		case GE:
		case LAND: 
		case LOR: 
		case EQ:
		case NOTEQ:
		case NOT: 
			return true;
		default:
			return false;
			
		}
	}
}

package pish;

import java_cup.runtime.*;
import java.io.IOException;

import static pish.PishSym.*;

%%

%class PishLex

%unicode
%line 
%column

// %public
%final
// %abstract

%cupsym PishSym
%cup
//%cupdebug

%init{
	// TODO: code that goes to constructor
%init}

%{
	private Symbol symbol(int type) {
	    return new JavaSymbol(yyline, yytext(), type);
	}
	
	private Symbol symbol(int type, Object value) {
	   return new Symbol(type,new JavaSymbol(yyline, yytext(), type, value));
	}


	private void error()
	throws IOException
	{
		throw new IOException("illegal text at line = "+yyline+", column = "+yycolumn+", text = '"+yytext()+"'");
	}
%}

WHITE_SPACE_CHAR=[\n\r\ \t\b\012]

// ************* TYPEDEF DECLERATIONS
ALPHA=[A-Za-z]
DIGIT=[0-9]


// ************* NUMERICALS 
EXPONENT=([eE][-+]?{DIGIT}+)
INT = {DIGIT}+{EXPONENT}?
DECIMAL = {DIGIT}+\.{DIGIT}+{EXPONENT}?

// ************* STRINGS/ID/CHAR

ID = {ALPHA}({ALPHA}|{DIGIT}|_)*

//ANY			=	.  //this is a CHAR as Well
STRING_LITERAL = \'.*\'   

CHAR = [cC][hH][aA][rR]
INTEGER = [iI][nN][tT][eE][gG][eE][rR] 
REAL = [rR][eE][aA][lL]

// ***************   RESERVED WORDS 

// *** CONTROL STATEMENTS

IF = [iI][fF]
ELSE = [eE][lL][sS][eE]
THEN = [tT][hH][eE][nN]
FORWARD = [fF][oO][rR][wW][aA][rR][dD]
DO = [dD][oO]
WHILE = [wW][hH][iI][lL][eE]
FOR = [fF][oO][rR]
BEGIN = [bB][eE][gG][iI][nN]
END = [eE][nN][dD]
AND = [aA][nN][dD]
OR = [oO][rR]
NOT = [nN][oO][tT]
OF = [oO][fF]
DOWNTO = [dD][oO][wW][nN][tT][oO]
TO = [tT][oO]


// ******** PROGRAM DEFS


PROGRAM = [pP][rR][oO][gG][rR][aA][mM]
FUNCTION = [fF][uU][nN][cC][tT][iI][oO][nN] 
PROCEDURE = [pP][rR][oO][cC][eE][dD][uU][rR][eE] 

//  *********  VARIABLES
ARRAY = [aA][rR][rR][aA][yY]
CONST = [cC][oO][nN][sS][tT]
TYPE = [tT][yY][pP][eE]
VAR = [vV][aA][rR] 
RECORD = [rR][eE][cC][oO][rR][dD]

// ***********  MATH 

MOD = [mM][oO][dD]
DIV = [dD][iI][vV]
MULTI = \*
DIVIDE = \/

MULOP = {MOD}|{DIV}|{MULTI}|{DIVIDE}|{AND}

// ************ SIGN
PLUS = \+
MINUS = \-

// ******** SPERATOR

SEMICOLON = ;
COLON = :
COMMA = ,
DOT = \.

// ******** RELATION OPERATORS
LESSTHAN = <
GREATERTHAN = > 
EQUAL = =
NOTEQUAL = {LESSTHAN}{GREATERTHAN}
LESSTHAN_OR_EQUAL = {LESSTHAN}{EQUAL}
GREATERTHAN_OR_EQUAL = {GREATERTHAN}{EQUAL}

RELOP = {LESSTHAN}|{GREATERTHAN}|{NOTEQUAL}|{LESSTHAN_OR_EQUAL}|{GREATERTHAN_OR_EQUAL}

ASSIGN = {COLON}{EQUAL}



LPAREN = \(
RPAREN = \)
LSPAREN = \[
RSPAREN = \]



// ********  COMMENT

COMMENT = (\(\*(.|{WHITE_SPACE_CHAR})*\*\))|(\{(.|{WHITE_SPACE_CHAR})*\})

%%

<YYINITIAL>  {
	{COMMENT}    		{	/*System.out.println(yytext());*/ /*System.out.println("COMMENT");*/  }
	{VAR}				{ 	/**System.out.println("VAR");**/		return symbol(VAR, new String(yytext())); }
	{DOWNTO}			{	/**System.out.println("DOWNTO");**/		return symbol(DOWNTO); }
	{END}				{	/**System.out.println("END");	**/			return symbol(END); }
	{THEN}				{	/**System.out.println("THEN");	**/			return symbol(THEN); }
	{WHILE}				{	/**System.out.println("WHILE");**/			return symbol(WHILE); }
	{TO}				{	/**System.out.println("TO");	**/			return symbol(TO); }
	{ELSE}				{	/**System.out.println("ELSE");	**/			return symbol(ELSE); }
	{PROCEDURE}			{	/**System.out.println("PROCEDURE");**/		return symbol(PROCEDURE); }
	{PROGRAM}			{  	/**System.out.println("PROGRAM");**/		return symbol(PROGRAM); }
	{REAL}				{	/**System.out.println("REAL");	**/			return symbol(REAL); }
	{INTEGER}			{	/**System.out.println("INTEGER");	**/		return symbol(INTEGER); }
	{CHAR}				{	/**System.out.println("CHAR");	**/			return symbol(CHAR); }
	{OF}				{	/**System.out.println("OF");		**/		return symbol(OF); }
	{IF}				{	/**System.out.println("IF");	**/			return symbol(IF); }
	{BEGIN}				{	/**System.out.println("BEGIN");	**/		return symbol(BEGIN); }
	{TYPE}				{	/**System.out.println("TYPE");	**/			return symbol(TYPE); }
	{RECORD}			{	/**System.out.println("RECORD");**/		return symbol(RECORD); }
	{NOT}				{	/**System.out.println("NOT");	**/			return symbol(NOT); }
	{FOR}				{	/**System.out.println("FOR");	**/			return symbol(FOR); }
	{FORWARD}			{	/**System.out.println("FORWARD");	**/		return symbol(FORWARD); }
	{ARRAY}				{	/**System.out.println("ARRAY");**/			return symbol(ARRAY); }
	{CONST}				{	/**System.out.println("CONST");**/			return symbol(CONST); }
	{FUNCTION}			{	/**System.out.println("FUNCTION");**/		return symbol(FUNCTION); }
	{DO}				{	/**System.out.println("DO");**/			return symbol(DO);  }
	{OR}				{ 	/**System.out.println("OR");**/	    	return symbol(OR); }
}
{DOT}				{ 	/**System.out.println("DOT"); 	**/			return symbol(DOT); }
{LPAREN}			{ 	/**System.out.println("LPAREN"); **/		return symbol(LPAREN); }
{RPAREN}			{ 	/**System.out.println("RPAREN");**/		return symbol(RPAREN); }
{LSPAREN}			{ 	/**System.out.println("LSPAREN");**/		return symbol(LSPAREN); }
{RSPAREN}			{ 	/**System.out.println("RSPAREN");**/		return symbol(RSPAREN); }
{SEMICOLON}			{	/**System.out.println("SEMICOLON");**/		return symbol(SEMICOLON); }
{COMMA}   			{	/**System.out.println("COMMA");**/			return symbol(COMMA); }
{MULOP}				{ 	/**System.out.println("MULOP");**/		    return symbol(MULOP,new String(yytext())); }
{MINUS}				{ 	/**System.out.println("MINUS");**/		    return symbol(MINUS); }
{PLUS}				{ 	/**System.out.println("PLUS");	**/	    	return symbol(PLUS); }
{EQUAL}				{	/**System.out.println("EQUAL");**/		    return symbol(EQUAL); }
{RELOP}				{ 	/**System.out.println("RELOP"); **/	    return symbol(RELOP,new String(yytext())); }
{ASSIGN}			{	/**System.out.println("ASSIGN");**/		return symbol(ASSIGN);  }
{COLON}				{	/**System.out.println("COLON");**/			return symbol(COLON);  }
{INT}				{	/**System.out.println("INT");	**/			return symbol(INT, new Integer(yytext())); }
{ID}   				{	/**System.out.println("ID");**/			return symbol(ID, new String(yytext())); }
{DECIMAL}			{	/**System.out.println("DECIMAL");**/		return symbol(DECIMAL, new Double(yytext())); }
{STRING_LITERAL}   	{ /**System.out.println("STRING_L");**/		return symbol(STRING_LITERAL,new String(yytext())); }
{WHITE_SPACE_CHAR} 	{/*DO NOTHING*/}

/* The following code was generated by JFlex 1.4.3 on 3/28/10 12:53 PM */


package pish;

import java_cup.runtime.*;
import java.io.IOException;

import static pish.PishSym.*;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 3/28/10 12:53 PM from the specification file
 * <tt>C:/SVN/Compliers/trunk/PishCompiler/CupJFLex/Pish.lex</tt>
 */
final class PishLex implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\10\0\2\1\1\11\2\0\1\1\22\0\1\1\6\0\1\10\1\47"+
    "\1\50\1\36\1\40\1\43\1\5\1\6\1\0\12\3\1\42\1\41"+
    "\1\44\1\46\1\45\2\0\1\14\1\30\1\12\1\27\1\4\1\23"+
    "\1\21\1\13\1\16\2\2\1\22\1\32\1\17\1\25\1\31\1\2"+
    "\1\15\1\24\1\20\1\33\1\35\1\26\1\2\1\34\1\2\1\51"+
    "\1\37\1\52\1\0\1\7\1\0\1\14\1\30\1\12\1\27\1\4"+
    "\1\23\1\21\1\13\1\16\2\2\1\22\1\32\1\17\1\25\1\31"+
    "\1\2\1\15\1\24\1\20\1\33\1\35\1\26\1\2\1\34\1\2"+
    "\1\53\1\0\1\54\uff82\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\1\2\1\4\1\5\1\0"+
    "\16\2\1\6\1\7\1\10\1\11\1\12\2\13\1\14"+
    "\1\15\1\16\1\17\1\20\3\0\2\2\1\21\6\2"+
    "\1\22\2\2\1\23\3\2\1\24\1\25\2\2\1\26"+
    "\3\2\1\27\1\13\1\0\1\30\1\3\1\0\1\31"+
    "\1\32\4\2\1\6\3\2\1\33\2\2\1\34\5\2"+
    "\1\35\2\0\1\36\1\37\3\2\1\40\1\2\1\41"+
    "\1\42\7\2\1\30\1\31\1\0\1\43\1\44\4\2"+
    "\1\45\1\2\1\46\2\2\1\47\3\2\1\50\2\2"+
    "\1\51\1\52\2\2\1\53\1\54\1\2\1\55";

  private static int [] zzUnpackAction() {
    int [] result = new int[134];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\55\0\132\0\207\0\264\0\55\0\55\0\341"+
    "\0\u010e\0\u013b\0\u0168\0\u0195\0\u01c2\0\u01ef\0\u021c\0\u0249"+
    "\0\u0276\0\u02a3\0\u02d0\0\u02fd\0\u032a\0\u0357\0\55\0\55"+
    "\0\55\0\u0384\0\55\0\u03b1\0\u03de\0\55\0\u040b\0\55"+
    "\0\55\0\55\0\u0438\0\u0465\0\u0492\0\u04bf\0\u04ec\0\341"+
    "\0\u0519\0\u0546\0\u0573\0\u05a0\0\u05cd\0\u05fa\0\132\0\u0627"+
    "\0\u0654\0\132\0\u0681\0\u06ae\0\u06db\0\132\0\132\0\u0708"+
    "\0\u0735\0\u0762\0\u078f\0\u07bc\0\u07e9\0\55\0\55\0\u0816"+
    "\0\u0438\0\u0843\0\u0843\0\u0870\0\132\0\u089d\0\u08ca\0\u08f7"+
    "\0\u0924\0\132\0\u0951\0\u097e\0\u09ab\0\132\0\u09d8\0\u0a05"+
    "\0\u0a32\0\u0a5f\0\u0a8c\0\u0ab9\0\u0ae6\0\u0b13\0\132\0\u0b40"+
    "\0\u0b6d\0\132\0\132\0\u0b9a\0\u0bc7\0\u0bf4\0\132\0\u0c21"+
    "\0\132\0\132\0\u0c4e\0\u0c7b\0\u0ca8\0\u0cd5\0\u0d02\0\u0d2f"+
    "\0\u0d5c\0\u0816\0\u0d89\0\u0d89\0\132\0\132\0\u0db6\0\u0de3"+
    "\0\u0e10\0\u0e3d\0\132\0\u0e6a\0\132\0\u0e97\0\u0ec4\0\132"+
    "\0\u0ef1\0\u0f1e\0\u0f4b\0\132\0\u0f78\0\u0fa5\0\132\0\132"+
    "\0\u0fd2\0\u0fff\0\132\0\132\0\u102c\0\132";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[134];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\0\1\2\1\3\1\4\1\5\1\6\1\7\1\0"+
    "\1\10\1\2\1\11\1\3\1\12\1\13\1\14\1\15"+
    "\1\16\2\3\1\17\1\3\1\20\1\21\1\22\1\23"+
    "\1\24\1\25\2\3\1\26\2\27\1\30\1\31\1\32"+
    "\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42"+
    "\1\43\60\0\3\3\2\0\1\3\2\0\24\3\22\0"+
    "\1\4\1\44\1\0\1\45\50\0\3\3\2\0\1\3"+
    "\2\0\5\3\1\46\2\3\1\47\13\3\17\0\10\10"+
    "\1\50\1\0\43\10\2\0\3\3\2\0\1\3\2\0"+
    "\1\3\1\51\11\3\1\52\10\3\21\0\3\3\2\0"+
    "\1\3\2\0\3\3\1\53\1\3\1\54\16\3\21\0"+
    "\2\3\1\55\2\0\1\3\2\0\24\3\21\0\3\3"+
    "\2\0\1\3\2\0\5\3\1\56\3\3\1\57\12\3"+
    "\21\0\3\3\2\0\1\3\2\0\13\3\1\60\10\3"+
    "\21\0\3\3\2\0\1\3\2\0\1\3\1\61\11\3"+
    "\1\62\6\3\1\63\1\3\21\0\3\3\2\0\1\3"+
    "\2\0\13\3\1\64\5\3\1\65\2\3\21\0\3\3"+
    "\2\0\1\3\2\0\3\3\1\66\5\3\1\67\12\3"+
    "\21\0\3\3\2\0\1\3\2\0\1\3\1\70\22\3"+
    "\21\0\3\3\2\0\1\3\2\0\4\3\1\71\6\3"+
    "\1\72\10\3\21\0\2\3\1\73\2\0\1\3\2\0"+
    "\24\3\21\0\3\3\2\0\1\3\2\0\3\3\1\74"+
    "\20\3\21\0\3\3\2\0\1\3\2\0\13\3\1\54"+
    "\10\3\21\0\3\3\2\0\1\3\2\0\2\3\1\75"+
    "\21\3\65\0\1\76\53\0\2\77\54\0\1\77\44\0"+
    "\1\100\16\0\54\43\1\101\3\0\1\102\1\0\1\103"+
    "\32\0\1\103\17\0\1\104\53\0\3\3\2\0\1\3"+
    "\2\0\15\3\1\105\6\3\21\0\3\3\2\0\1\3"+
    "\2\0\12\3\1\106\11\3\21\0\3\3\2\0\1\3"+
    "\2\0\2\3\1\107\21\3\21\0\3\3\2\0\1\3"+
    "\2\0\5\3\1\110\16\3\21\0\3\3\2\0\1\3"+
    "\2\0\3\3\1\111\20\3\21\0\3\3\2\0\1\3"+
    "\2\0\15\3\1\112\6\3\21\0\3\3\2\0\1\3"+
    "\2\0\1\113\1\3\1\114\21\3\21\0\3\3\2\0"+
    "\1\3\2\0\6\3\1\115\15\3\21\0\3\3\2\0"+
    "\1\3\2\0\6\3\1\116\15\3\21\0\2\3\1\117"+
    "\2\0\1\3\2\0\24\3\21\0\3\3\2\0\1\3"+
    "\2\0\17\3\1\120\4\3\21\0\3\3\2\0\1\3"+
    "\2\0\3\3\1\121\20\3\21\0\3\3\2\0\1\3"+
    "\2\0\5\3\1\122\16\3\21\0\3\3\2\0\1\3"+
    "\2\0\4\3\1\123\17\3\21\0\3\3\2\0\1\3"+
    "\2\0\23\3\1\112\21\0\3\3\2\0\1\3\2\0"+
    "\14\3\1\124\7\3\21\0\3\3\2\0\1\3\2\0"+
    "\7\3\1\125\14\3\21\0\3\3\2\0\1\3\2\0"+
    "\13\3\1\126\10\3\21\0\3\3\2\0\1\3\2\0"+
    "\3\3\1\127\20\3\17\0\36\100\1\130\16\100\3\0"+
    "\1\102\54\0\1\104\1\131\52\0\2\3\1\132\2\0"+
    "\1\3\2\0\24\3\21\0\3\3\2\0\1\3\2\0"+
    "\3\3\1\133\20\3\21\0\3\3\2\0\1\3\2\0"+
    "\12\3\1\134\11\3\21\0\3\3\2\0\1\3\2\0"+
    "\2\3\1\135\21\3\21\0\3\3\2\0\1\3\2\0"+
    "\13\3\1\136\10\3\21\0\3\3\2\0\1\3\2\0"+
    "\10\3\1\137\13\3\21\0\2\3\1\140\2\0\1\3"+
    "\2\0\24\3\21\0\3\3\2\0\1\3\2\0\5\3"+
    "\1\141\16\3\21\0\2\3\1\142\2\0\1\3\2\0"+
    "\24\3\21\0\3\3\2\0\1\3\2\0\14\3\1\143"+
    "\7\3\21\0\3\3\2\0\1\3\2\0\1\144\23\3"+
    "\21\0\3\3\2\0\1\3\2\0\10\3\1\145\13\3"+
    "\21\0\3\3\2\0\1\3\2\0\5\3\1\146\16\3"+
    "\21\0\3\3\2\0\1\3\2\0\4\3\1\147\17\3"+
    "\21\0\3\3\2\0\1\3\2\0\1\150\6\3\1\151"+
    "\14\3\17\0\36\100\1\130\11\100\1\152\4\100\3\0"+
    "\1\153\1\0\1\154\32\0\1\154\16\0\3\3\2\0"+
    "\1\3\2\0\6\3\1\155\15\3\21\0\3\3\2\0"+
    "\1\3\2\0\22\3\1\156\1\3\21\0\3\3\2\0"+
    "\1\3\2\0\3\3\1\157\20\3\21\0\3\3\2\0"+
    "\1\3\2\0\7\3\1\160\14\3\21\0\3\3\2\0"+
    "\1\3\2\0\2\3\1\161\21\3\21\0\3\3\2\0"+
    "\1\3\2\0\6\3\1\162\15\3\21\0\2\3\1\163"+
    "\2\0\1\3\2\0\24\3\21\0\3\3\2\0\1\3"+
    "\2\0\6\3\1\164\15\3\21\0\3\3\2\0\1\3"+
    "\2\0\5\3\1\165\16\3\21\0\2\3\1\166\2\0"+
    "\1\3\2\0\24\3\21\0\3\3\2\0\1\3\2\0"+
    "\3\3\1\167\20\3\22\0\1\153\53\0\3\3\2\0"+
    "\1\3\2\0\15\3\1\170\6\3\21\0\2\3\1\171"+
    "\2\0\1\3\2\0\24\3\21\0\3\3\2\0\1\3"+
    "\2\0\3\3\1\172\20\3\21\0\3\3\2\0\1\3"+
    "\2\0\4\3\1\173\17\3\21\0\3\3\2\0\1\3"+
    "\2\0\13\3\1\174\10\3\21\0\3\3\2\0\1\3"+
    "\2\0\15\3\1\175\6\3\21\0\3\3\2\0\1\3"+
    "\2\0\2\3\1\176\21\3\21\0\3\3\2\0\1\3"+
    "\2\0\3\3\1\177\20\3\21\0\3\3\2\0\1\3"+
    "\2\0\15\3\1\200\6\3\21\0\3\3\2\0\1\3"+
    "\2\0\13\3\1\201\10\3\21\0\3\3\2\0\1\3"+
    "\2\0\21\3\1\202\2\3\21\0\3\3\2\0\1\3"+
    "\2\0\20\3\1\203\3\3\21\0\3\3\2\0\1\3"+
    "\2\0\5\3\1\204\16\3\21\0\3\3\2\0\1\3"+
    "\2\0\3\3\1\205\20\3\21\0\2\3\1\206\2\0"+
    "\1\3\2\0\24\3\17\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[4185];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\3\1\2\11\1\0\16\1\3\11\1\1"+
    "\1\11\2\1\1\11\1\1\3\11\3\0\30\1\2\11"+
    "\1\0\2\1\1\0\24\1\2\0\22\1\1\0\32\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[134];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
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


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  PishLex(java.io.Reader in) {
  	// TODO: code that goes to constructor
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  PishLex(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 168) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 15: 
          { /**System.out.println("LSPAREN");**/		return symbol(LSPAREN);
          }
        case 46: break;
        case 12: 
          { /**System.out.println("EQUAL");**/		    return symbol(EQUAL);
          }
        case 47: break;
        case 28: 
          { /**System.out.println("FOR");	**/			return symbol(FOR);
          }
        case 48: break;
        case 29: 
          { /**System.out.println("VAR");**/		return symbol(VAR);
          }
        case 49: break;
        case 24: 
          { /*System.out.println(yytext());*/ /*System.out.println("COMMENT");*/
          }
        case 50: break;
        case 35: 
          { /**System.out.println("CONST");**/			return symbol(CONST);
          }
        case 51: break;
        case 39: 
          { /**System.out.println("RECORD");**/		return symbol(RECORD);
          }
        case 52: break;
        case 43: 
          { /**System.out.println("PROGRAM");**/		return symbol(PROGRAM);
          }
        case 53: break;
        case 37: 
          { /**System.out.println("WHILE");**/			return symbol(WHILE);
          }
        case 54: break;
        case 17: 
          { /**System.out.println("STRING_L");**/		return symbol(STRING_LITERAL,new String(yytext()));
          }
        case 55: break;
        case 23: 
          { /**System.out.println("ASSIGN");**/		return symbol(ASSIGN);
          }
        case 56: break;
        case 41: 
          { /**System.out.println("INTEGER");	**/		return symbol(INTEGER);
          }
        case 57: break;
        case 25: 
          { /**System.out.println("DECIMAL");**/		return symbol(DECIMAL, new Double(yytext()));
          }
        case 58: break;
        case 11: 
          { /**System.out.println("RELOP"); **/	    return symbol(RELOP,new String(yytext()));
          }
        case 59: break;
        case 30: 
          { /**System.out.println("ELSE");	**/			return symbol(ELSE);
          }
        case 60: break;
        case 9: 
          { /**System.out.println("COLON");**/			return symbol(COLON);
          }
        case 61: break;
        case 34: 
          { /**System.out.println("TYPE");	**/			return symbol(TYPE);
          }
        case 62: break;
        case 32: 
          { /**System.out.println("REAL");	**/			return symbol(REAL);
          }
        case 63: break;
        case 13: 
          { /**System.out.println("LPAREN"); **/		return symbol(LPAREN);
          }
        case 64: break;
        case 31: 
          { /**System.out.println("CHAR");	**/			return symbol(CHAR);
          }
        case 65: break;
        case 36: 
          { /**System.out.println("ARRAY");**/			return symbol(ARRAY);
          }
        case 66: break;
        case 22: 
          { /**System.out.println("DO");**/			return symbol(DO);
          }
        case 67: break;
        case 26: 
          { /**System.out.println("END");	**/			return symbol(END);
          }
        case 68: break;
        case 6: 
          { /**System.out.println("MULOP");**/		    return symbol(MULOP,new String(yytext()));
          }
        case 69: break;
        case 42: 
          { /**System.out.println("FORWARD");	**/		return symbol(FORWARD);
          }
        case 70: break;
        case 27: 
          { /**System.out.println("NOT");	**/			return symbol(NOT);
          }
        case 71: break;
        case 40: 
          { /**System.out.println("DOWNTO");**/		return symbol(DOWNTO);
          }
        case 72: break;
        case 20: 
          { /**System.out.println("OR");**/	    	return symbol(OR);
          }
        case 73: break;
        case 18: 
          { /**System.out.println("IF");	**/			return symbol(IF);
          }
        case 74: break;
        case 16: 
          { /**System.out.println("RSPAREN");**/		return symbol(RSPAREN);
          }
        case 75: break;
        case 33: 
          { /**System.out.println("THEN");	**/			return symbol(THEN);
          }
        case 76: break;
        case 7: 
          { /**System.out.println("PLUS");	**/	    	return symbol(PLUS);
          }
        case 77: break;
        case 3: 
          { /**System.out.println("INT");	**/			return symbol(INT, new Integer(yytext()));
          }
        case 78: break;
        case 19: 
          { /**System.out.println("TO");	**/			return symbol(TO);
          }
        case 79: break;
        case 14: 
          { /**System.out.println("RPAREN");**/		return symbol(RPAREN);
          }
        case 80: break;
        case 38: 
          { /**System.out.println("BEGIN");	**/		return symbol(BEGIN);
          }
        case 81: break;
        case 10: 
          { /**System.out.println("COMMA");**/			return symbol(COMMA);
          }
        case 82: break;
        case 8: 
          { /**System.out.println("SEMICOLON");**/		return symbol(SEMICOLON);
          }
        case 83: break;
        case 45: 
          { /**System.out.println("PROCEDURE");**/		return symbol(PROCEDURE);
          }
        case 84: break;
        case 1: 
          { /*DO NOTHING*/
          }
        case 85: break;
        case 44: 
          { /**System.out.println("FUNCTION");**/		return symbol(FUNCTION);
          }
        case 86: break;
        case 21: 
          { /**System.out.println("OF");		**/		return symbol(OF);
          }
        case 87: break;
        case 5: 
          { /**System.out.println("DOT"); 	**/			return symbol(DOT);
          }
        case 88: break;
        case 2: 
          { /**System.out.println("ID");**/			return symbol(ID, new String(yytext()));
          }
        case 89: break;
        case 4: 
          { /**System.out.println("MINUS");**/		    return symbol(MINUS);
          }
        case 90: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { return new java_cup.runtime.Symbol(PishSym.EOF); }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}

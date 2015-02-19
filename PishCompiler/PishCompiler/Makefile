BUILD      = ./bin/
LIBS       = ./lib/
CUP_LIB    = $(LIBS)java-cup-11a.jar
JFLEX_LIB  = $(LIBS)JFlex.jar
SRC        = ./src/
FLEXCUP	   = ./CupJFLex/

JAVAPARSER = pish.JavaParser

CUP        = -jar $(CUP_LIB)
JFLEX      = -jar $(JFLEX_LIB)
JAVA       = java
JAVAC      = javac
JAVACFLAGS = -d $(BUILD)
CLASSPATH  = -classpath $(CUP_LIB)
RCLASSPATH = -classpath $(BUILD):$(CUP_LIB)

# --------------------------------------------------

all: clean compile

run: all run1 run2 run3 run4 run5

run1: 1.pish
	@echo "****** Running file 1.pish ******"
	$(JAVA) $(RCLASSPATH) $(JAVAPARSER) 1.pish

run2: 2.pish
	@echo "****** Running file 2.pish ******"
	$(JAVA) $(RCLASSPATH) $(JAVAPARSER) 2.pish

run3: 3.pish
	@echo "****** Running file 3.pish ******"
	$(JAVA) $(RCLASSPATH) $(JAVAPARSER) 3.pish

run4: 4.pish
	@echo "****** Running file 4.pish ******"
	$(JAVA) $(RCLASSPATH) $(JAVAPARSER) 4.pish

run5: 5.pish
	@echo "****** Running file 5.pish ******"
	$(JAVA) $(RCLASSPATH) $(JAVAPARSER) 5.pish

compile: scanner parser
	$(JAVAC) $(JAVACFLAGS) $(CLASSPATH) $(SRC)pish/*.java $(SRC)pish/ast/*.java $(SRC)pish/symbol/*.java 

parser: parser.java

parser.java: $(FLEXCUP)Pish.cup
	$(JAVA)  $(CUP)  -parser PishCup -symbols PishSym $(FLEXCUP)Pish.cup 
	mv PishCup.java ./src/pish/PishCup.java
	mv PishSym.java ./src/pish/PishSym.java
	

scanner: Scanner.java

Scanner.java: $(FLEXCUP)Pish.lex 
	$(JAVA) $(JFLEX) -d $(SRC)pish/ $(FLEXCUP)Pish.lex 

createBin: $(BUILD)
	mkdir bin

clean:
	rm -rf $(BUILD)
	rm -f $(SRC)pish/PishCup.java
	rm -f $(SRC)pish/PishSym.java
	rm -f $(SRC)pish/PishLex.java
	mkdir bin
	



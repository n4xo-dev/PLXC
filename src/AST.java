import java.util.ArrayList;
import java.util.List;

/**
 * The AST class defines the Abstract Syntax Tree for the PLXC language.
 * <p>
 * The AST is defined as a set of nested classes, each representing a node in
 * the tree.
 * The AST is used to generate the Three-Address-Code (TAC) for a given PLXC
 * program.
 * 
 * The AST class contains the following nested classes:
 * <ul>
 * <li><b>Node</b>: The abstract base class for all nodes in the AST. It defines
 * the interface for all nodes in the tree.
 * <li><b>Program</b>: The root node of the AST. It contains a list of
 * sentences, which are the children of the program node.
 * <li><b>SentenceList</b>: A list of sentences. It implements the list of
 * sentences, which are the children of the sentence list node.
 * <li><b>If</b>: A conditional statement. It contains a condition, a true
 * branch, and an optional false branch.
 * <li><b>Print</b>: A print statement. It contains an expression to be printed.
 * <li><b>Condition</b>: The abstract base class for all conditions in the AST.
 * It defines the interface for all conditions in the tree.
 * <li><b>BinaryCondition</b>: A binary condition. It contains two expressions
 * and an operator.
 * <li><b>Or</b>: A logical OR condition. It contains two conditions.
 * <li><b>And</b>: A logical AND condition. It contains two conditions.
 * <li><b>Expression</b>: The abstract base class for all expressions in the
 * AST. It defines the interface for all expressions in the tree.
 * <li><b>NumericLiteral</b>: A numeric literal expression.
 * <li><b>Identifier</b>: An identifier expression.
 * <li><b>Assignment</b>: An assignment expression. It contains an identifier
 * and an expression.
 * <li><b>BinaryArithmetic</b>: A binary arithmetic expression. It contains two
 * expressions and an operator.
 * <li><b>UnaryMinus</b>: A unary minus expression. It contains an expression.
 * </ul>
 */
class AST {
  private SymTable symTable;
  private String tab = "   ";
  private String nl = "\n";
  private boolean debug = false;

  /**
   * Default constructor. Uses default values for TAB and NL (three spaces and a
   * newline).
   */
  AST() {
    symTable = new SymTable();
  }

  /**
   * Constructor that takes a custom value for TAB.
   * 
   * @param tab - The string to use for indentation.
   */
  AST(boolean debug) {
    this.symTable = new SymTable(debug);
    this.debug = debug;
  }

  /**
   * Constructor that takes custom values for TAB and NL.
   * 
   * @param tab - The string to use for indentation.
   * @param nl  - The string to use for newlines.
   */
  AST(String tab, String nl) {
    this();
    this.tab = tab;
    this.nl = nl;
  }

  /**
   * Constructor that takes custom values for TAB, NL, and debug.
   * 
   * @param tab   - The string to use for indentation.
   * @param nl    - The string to use for newlines.
   * @param debug - Whether to print debug information.
   */
  AST(String tab, String nl, boolean debug) {
    this(debug);
    this.tab = tab;

  }

  /**
   * The Node class is an abstract class that defines the interface for all nodes
   * in the AST.
   * Each node must implement the gen method, which generates the TAC code for the
   * node and its children.
   */
  public abstract class Node {

    /**
     * Generates the TAC code for the node and its children.
     * 
     * @return The TAC code for the node and its children.
     */
    abstract String gen();
  }

  /**
   * The Program class represents the root node of the AST.
   * It contains a list of sentences, which are the children of the program node.
   * The gen method of the Program class is the entry point for generating the TAC
   * code for a PLXC program.
   */
  public class Program extends Node {
    private SentenceList children;

    /**
     * Constructor for the Program class.
     * 
     * @param children - The list of sentences that are the children of the program
     *                 node.
     */
    public Program(SentenceList children) {
      this.children = children;

      if (debug) {
        System.err.println("  <AST> Program created with children: " + children + "\n\t" + symTable);
      }
    }

    /**
     * Generates the TAC code for the entire program by calling the gen method of
     * its children.
     * 
     * @return The TAC code for the entire program.
     */
    public String gen() {
      return children.gen();
    }
  }

  /**
   * The SentenceList class represents a list of sentences in the AST.
   * It contains a list of sentences, which are the children of the sentence list
   * node.
   */
  public class SentenceList extends Node {
    private List<Node> sentences;

    /**
     * Constructor for the SentenceList class.
     * 
     * @param sentences - The list of sentences that are the children of the
     *                  sentence list node.
     */
    public SentenceList(Node first) {
      sentences = new ArrayList<Node>();
      sentences.add(first);

      if (debug) {
        System.err.println("  <AST> SentenceList created with first sentence: " + first + "\n\t" + symTable);
      }
    }

    /**
     * Adds a sentence to the list of sentences.
     * 
     * @param sentence - The sentence to add to the list of sentences.
     * @return The SentenceList object, for chaining.
     */
    public SentenceList add(Node sentence) {
      sentences.add(sentence);
      return this;
    }

    /**
     * Generates the TAC code for the entire list of sentences by calling the gen
     * method of its children.
     * 
     * @return The TAC code for the entire list of sentences.
     */
    public String gen() {
      StringBuilder sb = new StringBuilder();
      for (Node sentence : sentences) {
        sb.append(sentence.gen());
      }
      return sb.toString();
    }
  }

  /**
   * The If class represents a conditional statement in the AST.
   * It contains a condition, a true branch, and an optional false branch.
   */
  public class If extends Node {
    private Condition condition;
    private Node trueSentence;
    private Node falseSentence; // Can be null.

    /**
     * Constructor for the If class.
     * 
     * @param condition   - The condition of the if statement.
     * @param trueBranch  - The true branch of the if statement.
     * @param falseBranch - The false branch of the if statement (can be null).
     */
    public If(Condition condition, Node trueBranch, Node falseBranch) {
      this.condition = condition;
      this.trueSentence = trueBranch;
      this.falseSentence = falseBranch;

      if (debug) {
        System.err.println("  <AST> If created with condition: " + condition + ", true branch: " + trueBranch
            + ", and false branch: " + falseBranch + "\n\t" + symTable);
      }
    }

    /**
     * Generates the TAC code for the entire conditional statement by calling the
     * gen method of its children
     * and combining the results with conditional and unconditional jumps.
     * 
     * @return The TAC code for the entire conditional statement.
     */
    public String gen() {
      String l1 = symTable.newLabel();
      String l2 = symTable.newLabel();
      String l3 = falseSentence == null ? null : symTable.newLabel();
      // String trueBranch = condition.requiresInversion() ? l2 : l1;
      // String falseBranch = condition.requiresInversion() ? l1 : l2;
      String conditionCode = condition.gen(l1, l2);
      String trueBranchCode = trueSentence.gen();
      String falseBranchCode = falseSentence == null ? "" : falseSentence.gen();
      String skipFalseBranch = falseSentence == null ? "" : tab + "goto " + l3 + ";" + nl;
      String skipFalseBranchLabel = falseSentence == null ? "" : l3 + ":" + nl;
      return conditionCode +
          l1 + ":" + nl +
          trueBranchCode +
          skipFalseBranch +
          l2 + ":" + nl +
          falseBranchCode +
          skipFalseBranchLabel;
    }
  }

  /**
   * The While class represents a while loop in the AST.
   * It contains a condition and a body.
   */
  public class While extends Node {
    private Condition condition;
    private Node body;

    /**
     * Constructor for the While class.
     * 
     * @param condition - The condition of the while loop.
     * @param body      - The body of the while loop.
     */
    public While(Condition condition, Node body) {
      this.condition = condition;
      this.body = body;

      if (debug) {
        System.err
            .println("  <AST> While created with condition: " + condition + " and body: " + body + "\n\t" + symTable);
      }
    }

    /**
     * Generates the TAC code for the entire while loop by calling the gen method of
     * the condition (to evaluate the condition), the body (to generate the body
     * code),
     * and combining the results with conditional and unconditional jumps.
     * 
     * @return The TAC code for the entire while loop.
     */
    public String gen() {
      String l1 = symTable.newLabel();
      String l2 = symTable.newLabel();
      String l3 = symTable.newLabel();
      String conditionCode = condition.gen(l2, l3);
      String bodyCode = body.gen();
      return l1 + ":" + nl +
          conditionCode + // tab + "if (" + condition + ") goto " + l2 + ";" + nl +
          // tab + "goto " + l3 + ";" + nl +
          l2 + ":" + nl +
          bodyCode +
          tab + "goto " + l1 + ";" + nl +
          l3 + ":" + nl;
    }
  }

  /**
   * The DoWhile class represents a do-while loop in the AST.
   * It contains a condition and a body.
   */
  public class DoWhile extends Node {
    private Condition condition;
    private Node body;

    /**
     * Constructor for the DoWhile class.
     * 
     * @param condition - The condition of the do-while loop.
     * @param body      - The body of the do-while loop.
     */
    public DoWhile(Condition condition, Node body) {
      this.condition = condition;
      this.body = body;

      if (debug) {
        System.err
            .println("  <AST> DoWhile created with condition: " + condition + " and body: " + body + "\n\t" + symTable);
      }
    }

    /**
     * Generates the TAC code for the entire do-while loop by calling the gen method
     * of
     * the body (to generate the body code), the condition (to evaluate the
     * condition),
     * and combining the results with conditional and unconditional jumps.
     * 
     * @return The TAC code for the entire do-while loop.
     */
    public String gen() {
      String l1 = symTable.newLabel();
      String conditionCode = condition.gen(l1, null);
      String bodyCode = body.gen();
      return l1 + ":" + nl +
          bodyCode +
          conditionCode; // The condition is evaluated at the end of the loop. After the body has been
                         // executed.
      // tab + "if (" + condition + ") goto " + l1 + ";" + nl;
    }
  }

  /**
   * The For class represents a for loop in the AST.
   * It contains an initialization statement, a condition, an update statement,
   * and a body.
   */
  public class For extends Node {
    private Node init; // Can be null.
    private Condition condition;
    private Node update; // Can be null.
    private Node body;

    /**
     * Constructor for the For class.
     * 
     * @param init      - The initialization statement of the for loop.
     * @param condition - The condition of the for loop.
     * @param update    - The update statement of the for loop.
     * @param body      - The body of the for loop.
     */
    public For(Node init, Condition condition, Node update, Node body) {
      this.init = init;
      this.condition = condition;
      this.update = update;
      this.body = body;

      if (debug) {
        System.err.println("  <AST> For created with init: " + init + ", condition: " + condition + ", update: "
            + update + ", and body: " + body + "\n\t" + symTable);
      }
    }

    /**
     * Generates the TAC code for the entire for loop by calling the gen method of
     * the initialization statement, the condition (to evaluate the condition), the
     * body (to generate the body code),
     * and the update statement, and combining the results with conditional and
     * unconditional jumps.
     * 
     * @return The TAC code for the entire for loop.
     */
    public String gen() {
      String l1 = symTable.newLabel();
      String l2 = symTable.newLabel();
      String l3 = symTable.newLabel();
      String initCode = init == null ? "" : init.gen();
      String conditionCode = condition.gen(l2, l3);
      String updateCode = update == null ? "" : update.gen();
      String bodyCode = body.gen();
      return initCode +
          l1 + ":" + nl +
          conditionCode + // tab + "if (" + condition + ") goto " + l2 + ";" + nl +
          // tab + "goto " + l3 + ";" + nl +
          l2 + ":" + nl +
          bodyCode +
          updateCode +
          tab + "goto " + l1 + ";" + nl +
          l3 + ":" + nl;
    }
  }

  /**
   * The Print class represents a print statement in the AST.
   * It contains an expression to be printed.
   */
  public class Print extends Node {
    private Expression expression;

    /**
     * Constructor for the Print class.
     * 
     * @param expression - The expression to be printed.
     */
    public Print(Expression expression) {
      this.expression = expression;

      if (debug) {
        System.err.println("  <AST> Print created with expression: " + expression + "\n\t" + symTable);
      }
    }

    /**
     * Generates the TAC code for the print statement by calling the gen method of
     * its child expression
     * and combining the result with the print statement.
     * 
     * @return The TAC code for the print statement.
     */
    public String gen() {
      String expressionCode = expression.gen();
      String print = expression.getType() == Type.CHAR ? "printc " : "print ";
      return expressionCode +
          tab + print + expression + ";" + nl;
    }
  }

  /**
   * The Expression class is an abstract class that defines the interface for all
   * expressions in the AST.
   * Each expression must implement the gen method, which generates the TAC code
   * for the expression.
   * The value of the expression is the variable name that holds the result of the
   * expression or a numeric literal.
   * The toString method is overridden to return the value of the expression.
   */
  public class Expression extends Node {
    private String value; // The value of the expression is the variable name that holds the result of the
                          // expression or a numeric literal.
    private Type type;

    /**
     * Constructor for the Expression class.
     * 
     * @param value - The value of the expression, which is the variable name that
     *              holds the result of the expression or a numeric literal.
     */
    public Expression(Type type, String value) {
      this.value = value;
      this.type = type;
    }

    /**
     * Gets the value of the expression, which is the variable name that holds the
     * result of the expression or a numeric literal.
     * 
     * @return The value of the expression.
     */
    public String getValue() {
      return value;
    }

    /**
     * Gets the type of the expression.
     * 
     * @return The type of the expression.
     */
    public Type getType() {
      return type;
    }

    /**
     * Generates the TAC code for the expression.
     * Only some expressions generate TAC code, so the default implementation
     * returns an empty string.
     * 
     * @return The TAC code for the expression.
     */
    public String gen() {
      return ""; // No code is generated for some expressions.
    }

    /**
     * Returns the value of the expression, which is the variable name that holds
     * the result of the expression or a numeric literal.
     * 
     * @return The value of the expression.
     */
    @Override
    public String toString() {
      return value;
    }
  }

  /**
   * The NumericLiteral class represents a numeric literal expression in the AST.
   * It contains the value of the numeric literal.
   */
  public class NumericLiteral extends Expression {

    /**
     * Constructor for the NumericLiteral class.
     * 
     * @param value - The value of the numeric literal.
     */
    public NumericLiteral(Type type, String value) {
      super(type, value);

      if (debug) {
        System.err.println(
            "  <AST> NumericLiteral created with value: " + value + " and type: " + this.getType());
      }
    }
  }

  public class CharLiteral extends Expression {

    public CharLiteral(String value) {
      super(Type.CHAR, Type.getCharCode(value));

      if (debug) {
        System.err.println(
            "  <AST> CharLiteral created with value: " + value + " and type: " + this.getType());
      }
    }
  }

  public class ArrayLiteralList extends Expression {
    private List<Expression> expressions;

    public ArrayLiteralList(Expression first) {
      super(first.getType(), null);
      expressions = new ArrayList<Expression>();
      expressions.add(first);

      if (debug) {
        System.err.println("  <AST> ArrayLiteralList created with first expression: " + first + "\n\t" + symTable);
      }
    }

    public ArrayLiteralList add(Expression expression) {
      if (expression.getType() != this.getType()) {
        throw new RuntimeException("ALL0 Incompatible types: " + this.getType() + " and " + expression.getType());
      }
      expressions.add(expression);
      return this;
    }

    public Expression get(int index) {
      return expressions.get(index);
    }

    public String gen() {
      StringBuilder sb = new StringBuilder();
      for (Expression expression : expressions) {
        sb.append(expression.gen());
      }
      return sb.toString();
    }
  }

  /**
   * The Identifier class represents an identifier expression in the AST.
   * It contains the name of the identifier.
   */
  public class Identifier extends Expression {

    /**
     * Constructor for the Identifier class.
     * 
     * @param value - The name of the identifier.
     */
    public Identifier(String id) {
      super(symTable.getTypeOf(id), symTable.getHolderOf(id));

      if (debug) {
        System.err
            .println("  <AST> Identifier created with value: " + id + " and type: " + this.getType());
      }
    }
  }

  public class ArrayAccess extends Expression {
    private String holder;
    private String arrayHolder;
    private Expression index;

    public ArrayAccess(String id, Expression index) {
      super(symTable.getTypeOf(id), symTable.newTemp(symTable.getTypeOf(id)));
      this.holder = this.getValue();
      this.arrayHolder = symTable.getHolderOf(id);
      this.index = index;

      if (debug) {
        System.err.println("  <AST> ArrayAccess created with id: " + id + " and index: " + index + "\n\t" + symTable);
      }
    }

    public String gen() {
      String indexCode = index.gen();
      String l0 = symTable.newLabel();
      String l1 = symTable.newLabel();
      return indexCode +
          tab + "if (" + index + " < 0) goto " + l0 + ";" + nl +
          tab + "if ($" + arrayHolder + "_length < " + index + ") goto " + l0 + ";" + nl +
          tab + "if ($" + arrayHolder + "_length == " + index + ") goto " + l0 + ";" + nl +
          tab + "goto " + l1 + ";" + nl +
          l0 + ":" + nl +
          tab + "error;" + nl + tab + "halt;" + nl +
          l1 + ":" + nl +  
          tab + holder + " = " + arrayHolder + "[" + index + "];" + nl;
    }
  }

  /**
   * The Declaration class represents a declaration expression in the AST.
   * It contains an identifier and an expression.
   */
  public class Declaration extends Expression {
    private String holder; // The identifier that holds the result of the expression. (holder of the
                           // variable)
    private Expression expression; // Can be null. (I.e., the variable is initialized to 0.)
    private String implicitCast = "";

    /**
     * Constructor for the Assignment class.
     * 
     * @param id         - The identifier that holds the result of the expression.
     * @param expression - The expression to be assigned to the identifier.
     */
    public Declaration(Type type, String id, Expression expression) throws RuntimeException {
      super(type, symTable.add(type, id));
      this.holder = this.getValue();
      this.expression = expression;

      // Check if the types are compatible. And perform implicit casting if necessary.
      if (expression == null) {
        return; // Variables are initialized to 0 by default.
      }
      if (this.getType() != expression.getType()) {
        if (this.getType() == Type.FLOAT) {
          if (expression.getType() == Type.INT) {
            String temp = symTable.newTemp(Type.FLOAT);
            this.implicitCast = tab + temp + " = (float) " + expression + ";" + nl;
          } else {
            throw new RuntimeException("A1 Incompatible types: " + this.getType() + " and " + expression.getType());
          }
        } else if (this.getType() == Type.INT && (expression.getType() != Type.INT || expression.getType() == Type.CHAR)) {
          throw new RuntimeException("A2 Incompatible types: " + this.getType() + " and " + expression.getType());
        } else if (this.getType() == Type.CHAR && expression.getType() != Type.CHAR) { // TODO: interoperate with int
          throw new RuntimeException("A3 Incompatible types: " + this.getType() + " and " + expression.getType());
        }
      }

      if (debug) {
        System.err.println("  <AST> Declaration of type " + type + " created with id: " + id + " and expression: "
            + expression + "\n\t" + symTable);
      }
    }

    /**
     * Generates the TAC code for the assignment expression by calling the gen
     * method of its child expression
     * and combining the result with the assignment statement.
     * 
     * @return The TAC code for the assignment expression.
     */
    public String gen() {
      if (expression == null) {
        return ""; // Variables are initialized to 0 by default.
      }
      String expressionCode = expression.gen();
      return expressionCode + implicitCast +
          tab + holder + " = " + expression + ";" + nl;
    }
  }

  public class ArrayDeclaration extends Expression {
    private String holder;
    private int size;

    public ArrayDeclaration(Type type, String id, String size) {
      super(type, symTable.add(type, id));
      this.holder = this.getValue();
      try {
        this.size = Integer.parseInt(size);
      } catch (NumberFormatException e) {
        throw new RuntimeException("Invalid array size: " + size);
      }

      symTable.addArray(id);

      if (debug) {
        System.err.println("  <AST> ArrayDeclaration created with id: " + id + " and size: " + size + "\n\t" + symTable);
      }
    }

    public String gen() {
      return tab + "$" + holder + "_length = " + size + ";" + nl;
    }
  }

  public class DeclarationList extends Node {
    private List<Declaration> declarations;
    private Type type;

    public DeclarationList(Type type, String firstId, Expression firstExpression) {
      declarations = new ArrayList<Declaration>();
      declarations.add(new Declaration(type, firstId, firstExpression));
      this.type = type;

      if (debug) {
        System.err.println(
            "  <AST> DeclarationList created with first id: " + firstId + " and first expression: " + firstExpression
                + "\n\t" + symTable);
      }
    }

    public DeclarationList add(String id, Expression expression) {
      declarations.add(new Declaration(this.type, id, expression));
      return this;
    }

    public String gen() {
      StringBuilder sb = new StringBuilder();
      for (Declaration declaration : declarations) {
        sb.append(declaration.gen());
      }
      return sb.toString();
    }
  }

  /**
   * The Assignment class represents an assignment expression in the AST.
   * It contains an identifier and an expression.
   */
  public class Assignment extends Expression {
    private String holder; // The identifier that holds the result of the expression. (holder of the
                           // variable)
    private Expression expression;
    private String implicitCast = "";

    /**
     * Constructor for the Assignment class.
     * 
     * @param id         - The identifier that holds the result of the expression.
     * @param expression - The expression to be assigned to the identifier.
     */
    public Assignment(String id, Expression expression) throws RuntimeException {
      super(symTable.getTypeOf(id), expression.getValue());
      this.holder = symTable.getHolderOf(id);
      this.expression = expression;

      // Check if the types are compatible. And perform implicit casting if necessary.
      if (this.getType() != expression.getType()) {
        if (symTable.isArray(id)) {
          throw new RuntimeException("A0 Cannot assign to " + this.getType() + " array " + id + " with " + expression.getType());
        }
        if (this.getType() == Type.FLOAT) {
          if (expression.getType() == Type.INT) {
            String temp = symTable.newTemp(Type.FLOAT);
            this.implicitCast = tab + temp + " = (float) " + expression + ";" + nl;
            this.expression = new Declaration(Type.FLOAT, temp, expression);
          } else {
            throw new RuntimeException("A1 Incompatible types: " + this.getType() + " and " + expression.getType());
          }
        } else if (this.getType() == Type.INT && (expression.getType() != Type.INT || expression.getType() == Type.CHAR)) {
          throw new RuntimeException("A2 Incompatible types: " + this.getType() + " and " + expression.getType());
        } else if (this.getType() == Type.CHAR && expression.getType() != Type.CHAR) { // TODO: interoperate with int
          throw new RuntimeException("A3 Incompatible types: " + this.getType() + " and " + expression.getType());
        }
      }
      // if (symTable.isArray(id) && !(expression instanceof ArrayLiteralList)) {
      //   throw new RuntimeException("A4 Cannot assign to array " + id + " with non-array expression");
      // }

      if (debug) {
        System.err.println("  <AST> Assignment created with id: " + id + " (" + this.getType() + ") and expression: "
            + expression + "\n\t" + symTable);
      }
    }

    /**
     * Generates the TAC code for the assignment expression by calling the gen
     * method of its child expression
     * and combining the result with the assignment statement.
     * 
     * @return The TAC code for the assignment expression.
     */
    public String gen() {
      String expressionCode = expression.gen();
      String action = "";
      if (symTable.isArray(holder) && expression instanceof ArrayLiteralList) {
        for (int i = 0; i < ((ArrayLiteralList) expression).expressions.size(); i++) {
          action += tab + holder + "[" + i + "] = " + ((ArrayLiteralList) expression).expressions.get(i) + ";" + nl;
        }
      } else {
        action = tab + holder + " = " + expression + ";" + nl;
      }
      return expressionCode + implicitCast + action;
    }
  }

  public class ArrayElementAssignment extends Expression {
    private String holder;
    private Expression index;
    private Expression expression;

    public ArrayElementAssignment(String id, Expression index, Expression expression) {
      super(symTable.getTypeOf(id), expression.getValue());
      this.holder = symTable.getHolderOf(id);
      this.index = index;
      this.expression = expression;

      // Check if the types are compatible. And perform implicit casting if necessary.
      if (this.getType() != expression.getType()) {
        if (this.getType() == Type.FLOAT) {
          if (expression.getType() == Type.INT) {
            String temp = symTable.newTemp(Type.FLOAT);
            this.expression = new Declaration(Type.FLOAT, temp, expression);
          } else {
            throw new RuntimeException("AA1 Incompatible types: " + this.getType() + " and " + expression.getType());
          }
        } else if (this.getType() == Type.INT && (expression.getType() != Type.INT || expression.getType() == Type.CHAR)) {
          throw new RuntimeException("AA2 Incompatible types: " + this.getType() + " and " + expression.getType());
        } else if (this.getType() == Type.CHAR && expression.getType() != Type.CHAR) { // TODO: interoperate with int
          throw new RuntimeException("AA3 Incompatible types: " + this.getType() + " and " + expression.getType());
        }
      }

      if (debug) {
        System.err.println("  <AST> ArrayAssignment created with id: " + id + " and expression: " + expression
            + "\n\t" + symTable);
      }
    }

    public String gen() {
      String indexCode = index.gen();
      String expressionCode = expression.gen();
      String l0 = symTable.newLabel();
      String l1 = symTable.newLabel();
      return indexCode + expressionCode +
          tab + "if (" + index + " < 0) goto " + l0 + ";" + nl +
          tab + "if ($" + holder + "_length < " + index + ") goto " + l0 + ";" + nl +
          tab + "if ($" + holder + "_length == " + index + ") goto " + l0 + ";" + nl +
          tab + "goto " + l1 + ";" + nl +
          l0 + ":" + nl +
          tab + "error;" + nl + tab + "halt;" + nl +
          l1 + ":" + nl +  
          tab + holder + "[" + index + "] = " + expression + ";" + nl;
    }
  }

  public class Cast extends Expression {
    private Expression expression;

    public Cast(Type type, Expression expression) {
      super(type, type.isCastUnnecessary(expression.getType()) ? expression.getValue() : symTable.newTemp(type));
      this.expression = expression;

      this.getType().checkCastable(type);

      if (debug) {
        System.err.println("  <AST> Cast created with expression: " + expression + " (" + this.getType() + ")"
            + "\n\t" + symTable);
      }
    }

    public String gen() {
      String expressionCode = expression.gen();
      if (this.getType().isCastUnnecessary(this.expression.getType())) {
        return expressionCode;
      }
      return expressionCode + 
        tab + this.getValue() + " = " + "(" + this.getType().getJavaType() + ") " + expression + ";" + nl;
    }
  }

  /**
   * The BinaryArithmetic class represents a binary arithmetic expression in the
   * AST.
   * It contains two expressions and an operator.
   * It also contains a temporary variable to hold the result of the expression.
   */
  public class BinaryArithmetic extends Expression {
    private Expression left;
    private Expression right;
    private String op;
    private String temp;
    private String implicitCast = "";
    /**
     * Constructor for the BinaryArithmetic class.
     * 
     * @param left  - The left-hand side expression of the arithmetic expression.
     * @param right - The right-hand side expression of the arithmetic expression.
     * @param op    - The operator of the arithmetic expression.
     */
    public BinaryArithmetic(Expression left, Expression right, String op) throws RuntimeException {
      super(Type.getPrevalentType(left.getType(), right.getType()),
          symTable.newTemp(Type.getPrevalentType(left.getType(), right.getType())));
      this.temp = this.getValue();
      this.left = left;
      this.right = right;
      this.op = this.getType() == Type.FLOAT ? op + "r" : op;

      // Check if the types are compatible. And perform implicit casting if necessary.
      if (this.getType() != left.getType()) {
        if (this.getType() == Type.FLOAT) {
          if (left.getType() == Type.INT) {
            String temp = symTable.newTemp(Type.FLOAT);
            this.implicitCast = tab + temp + " = (float) " + left + ";" + nl;
            left = new Declaration(Type.FLOAT, temp, left);
          } else {
            throw new RuntimeException("BA1 Incompatible types: " + this.getType() + " and " + left.getType());
          }
        } else if (this.getType() == Type.INT){
          if (left.getType() != Type.CHAR) {
            throw new RuntimeException("BA2 Incompatible types: " + this.getType() + " and " + left.getType());
          }
        } else {
          throw new RuntimeException("BA3 Incompatible types: " + this.getType() + " and " + left.getType());
        }
      } else if (this.getType() != right.getType()) {
        if (this.getType() == Type.FLOAT) {
          if (right.getType() == Type.INT) {
            String temp = symTable.newTemp(Type.FLOAT);
            this.implicitCast += tab + temp + " = (float) " + right + ";" + nl;
            right = new Declaration(Type.FLOAT, temp, right);
          } else {
            throw new RuntimeException("BA4 Incompatible types: " + this.getType() + " and " + right.getType());
          }
        } else if (this.getType() == Type.INT){
          if (right.getType() != Type.CHAR) {
            throw new RuntimeException("BA5 Incompatible types: " + this.getType() + " and " + right.getType());
          }
        } else {
          throw new RuntimeException("BA6 Incompatible types: " + this.getType() + " and " + right.getType());
        }
      }

      if (debug) {
        System.err
            .println("  <AST> BinaryArithmetic created with left: " + left + ", right: " + right + ", and op: " + op
                + " (" + this.getType() + ")");
      }
    }

    /**
     * Generates the TAC code for the arithmetic expression by calling the gen
     * method of its children
     * and combining the result with the arithmetic operation.
     * 
     * @return The TAC code for the arithmetic expression.
     */
    public String gen() {
      String leftCode = left.gen();
      String rightCode = right.gen();
      return leftCode + rightCode + implicitCast +
          tab + temp + " = " + left + " " + op + " " + right + ";" + nl;
    }
  }

  /**
   * The UnaryMinus class represents a unary minus expression in the AST.
   * It contains an expression and a temporary variable to hold the result of the
   * expression.
   */
  public class UnaryMinus extends Expression {
    private Expression expression;
    private String temp;

    /**
     * Constructor for the UnaryMinus class.
     * 
     * @param expression - The expression to be negated.
     */
    public UnaryMinus(Expression expression) {
      super(expression.getType(), symTable.newTemp(expression.getType()));
      this.temp = this.getValue();
      this.expression = expression;

      if (debug) {
        System.err.println("  <AST> UnaryMinus created with expression: " + expression + " (" + this.getType() + ")"
            + "\n\t" + symTable);
      }
    }

    /**
     * Generates the TAC code for the unary minus expression by calling the gen
     * method of its child expression
     * and combining the result with the unary minus operation.
     * 
     * @return The TAC code for the unary minus expression.
     */
    public String gen() {
      String expressionCode = expression.gen();
      return expressionCode +
          tab + temp + " = -" + expression + ";" + nl;
    }
  }

  /**
   * The Condition class is an abstract class that defines the interface for all
   * conditions in the AST.
   * Each condition must implement the getCondition and requiresInversion methods.
   * 
   * This class is used more as a reference than as a class to inherit from.
   */
  public abstract class Condition extends Node {
    public abstract String gen(String trueBrachLabel, String falseBranchLabel);
  }

  /**
   * The BinaryCondition class represents a binary condition in the AST.
   * It contains two expressions and an operator.
   */
  public class BinaryCondition extends Condition {
    private Expression left;
    private Expression right;
    private String condition; // The condition after the reduction.
    private boolean requiresInversion = false;

    /**
     * Constructor for the BinaryCondition class.
     * It also generates the condition string and sets the requiresInversion flag
     * based on the operator.
     * 
     * @param left  - The left-hand side expression of the condition.
     * @param right - The right-hand side expression of the condition.
     * @param op    - The operator of the condition.
     */
    public BinaryCondition(Expression left, Expression right, String op) {
      this.left = left;
      this.right = right;
      if (op.equals("==") || op.equals("!=") || op.equals("<")) {
        this.requiresInversion = false;
        this.condition = left + " " + op + " " + right;
      } else if (op.equals(">")) {
        this.requiresInversion = false;
        this.condition = right + " < " + left;
      } else if (op.equals(">=")) {
        this.requiresInversion = true;
        this.condition = left + " < " + right;
      } else if (op.equals("<=")) {
        this.requiresInversion = true;
        this.condition = right + " < " + left;
      }

      if (debug) {
        System.err
            .println("  <AST> BinaryCondition created with left: " + left + ", right: " + right + ", and op: " + op);
      }
    }

    /**
     * Generates the code necessary to evaluate the condition.
     * I.e. the code needed to reduce the condition to a Three-Address-Code
     * valid condition where there is only two operands and one operator.
     */
    public String gen(String trueBrachLabel, String falseBranchLabel) {
      String lExpressionCode = left.gen();
      String rExpressionCode = right.gen();
      if (this.requiresInversion) {
        String temp = trueBrachLabel;
        trueBrachLabel = falseBranchLabel;
        falseBranchLabel = temp;
      }
      return lExpressionCode + rExpressionCode +
          tab + "if (" + condition + ") goto " + trueBrachLabel + ";" + nl +
          (falseBranchLabel == null ? "" : tab + "goto " + falseBranchLabel + ";" + nl);
    }

    public String gen() {
      return "ERROR: BinaryCondition.gen() called without labels. This should not happen.";
    };
  }

  public class Not extends Condition {
    private Condition condition;

    public Not(Condition condition) {
      this.condition = condition;

      if (debug) {
        System.err.println("  <AST> Not created with condition: " + condition + "\n\t" + symTable);
      }
    }

    public String gen(String trueBrachLabel, String falseBranchLabel) {
      return condition.gen(falseBranchLabel, trueBrachLabel);
    }

    public String gen() {
      return "ERROR: Negation.gen() called without labels. This should not happen.";
    };
  }

  /**
   * The Or class represents a logical OR condition in the AST.
   * It contains two conditions.
   */
  public class Or extends Condition {
    private Condition left;
    private Condition right;

    /**
     * Constructor for the Or class.
     * 
     * @param left  - The left-hand side condition of the OR condition.
     * @param right - The right-hand side condition of the OR condition.
     */
    public Or(Condition left, Condition right) {
      this.left = left;
      this.right = right;

      if (debug) {
        System.err.println("  <AST> Or created with left: " + left + " and right: " + right + "\n\t" + symTable);
      }
    }

    /**
     * Generates the TAC code for the OR condition by calling the gen method of its
     * children
     * and combining the results with conditional and unconditional jumps.
     * 
     * @return The TAC code for the OR condition.
     */
    public String gen(String trueBrachLabel, String falseBranchLabel) {
      String lcontinue = symTable.newLabel();
      String lConditionCode = left.gen(trueBrachLabel, lcontinue);
      String rConditionCode = right.gen(trueBrachLabel, falseBranchLabel);
      return lConditionCode +
          lcontinue + ":" + nl +
          rConditionCode;
    }

    public String gen() {
      return "ERROR: Or.gen() called without labels. This should not happen.";
    };
  }

  /**
   * The And class represents a logical AND condition in the AST.
   * It contains two conditions.
   */
  public class And extends Condition {
    private Condition left;
    private Condition right;

    /**
     * Constructor for the And class.
     * 
     * @param left  - The left-hand side condition of the AND condition.
     * @param right - The right-hand side condition of the AND condition.
     */
    public And(Condition left, Condition right) {
      this.left = left;
      this.right = right;

      if (debug) {
        System.err.println("  <AST> And created with left: " + left + " and right: " + right + "\n\t" + symTable);
      }
    }

    /**
     * Generates the TAC code for the AND condition by calling the gen method of its
     * children
     * and combining the results with conditional and unconditional jumps.
     * 
     * @return The TAC code for the AND condition.
     */
    public String gen(String trueBrachLabel, String falseBranchLabel) {
      String l1 = symTable.newLabel();
      String lConditionCode = left.gen(l1, falseBranchLabel);
      String rConditionCode = right.gen(trueBrachLabel, falseBranchLabel);
      return lConditionCode +
          l1 + ":" + nl +
          rConditionCode;
    }

    public String gen() {
      return "ERROR: And.gen() called without labels. This should not happen.";
    };
  }

  /**
   * Pushes a new scope to the scope table.
   */
  public void pushScope() {
    symTable.pushScope();
  }

  /**
   * Pops the top scope from the scope table.
   */
  public void popScope() {
    symTable.popScope();
  }

  public String printTable() {
    return symTable.toString();
  }
}

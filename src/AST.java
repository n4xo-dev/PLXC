import java.util.List;

/**
 * The AST class defines the Abstract Syntax Tree for the PLXC language.
 * <p>
 * The AST is defined as a set of nested classes, each representing a node in the tree.
 * The AST is used to generate the Three-Address-Code (TAC) for a given PLXC program.
 * 
 * The AST class contains the following nested classes:
 * <ul>
 * <li><b>Node</b>: The abstract base class for all nodes in the AST. It defines the interface for all nodes in the tree.
 * <li><b>Program</b>: The root node of the AST. It contains a list of sentences, which are the children of the program node.
 * <li><b>SentenceList</b>: A list of sentences. It implements the list of sentences, which are the children of the sentence list node.
 * <li><b>If</b>: A conditional statement. It contains a condition, a true branch, and an optional false branch.
 * <li><b>Print</b>: A print statement. It contains an expression to be printed.
 * <li><b>Condition</b>: The abstract base class for all conditions in the AST. It defines the interface for all conditions in the tree.
 * <li><b>BinaryCondition</b>: A binary condition. It contains two expressions and an operator.
 * <li><b>Expression</b>: The abstract base class for all expressions in the AST. It defines the interface for all expressions in the tree.
 * <li><b>NumericLiteral</b>: A numeric literal expression.
 * <li><b>Identifier</b>: An identifier expression.
 * <li><b>Assignment</b>: An assignment expression. It contains an identifier and an expression.
 * <li><b>BinaryArithmetic</b>: A binary arithmetic expression. It contains two expressions and an operator.
 * <li><b>UnaryMinus</b>: A unary minus expression. It contains an expression.
 * </ul>
 */
class AST {
  private SymTable symTable = new SymTable();
  private String TAB = "   ";
  private String NL = "\n";

  /**
   * Default constructor. Uses default values for TAB and NL (three spaces and a newline).
   */
  AST() {}

  /**
   * Constructor that takes custom values for TAB and NL.
   * @param tab - The string to use for indentation.
   * @param nl - The string to use for newlines.
   */
  AST(String tab, String nl) {
    TAB = tab;
    NL = nl;
  }

  
  /**
   * The Node class is an abstract class that defines the interface for all nodes in the AST.
   * Each node must implement the gen method, which generates the TAC code for the node and its children.
   */
  public abstract class Node {

    /**
     * Generates the TAC code for the node and its children.
     * @return The TAC code for the node and its children.
     */
    abstract String gen();
  }

  /**
   * The Program class represents the root node of the AST.
   * It contains a list of sentences, which are the children of the program node.
   * The gen method of the Program class is the entry point for generating the TAC code for a PLXC program.
   */
  public class Program extends Node {
    private SentenceList children;

    /**
     * Constructor for the Program class.
     * @param children - The list of sentences that are the children of the program node.
     */
    public Program(SentenceList children) {
      this.children = children;
    }

    /**
     * Generates the TAC code for the entire program by calling the gen method of its children.
     * @return The TAC code for the entire program.
     */
    public String gen() {
      return children.gen();
    }
  }

  /**
   * The SentenceList class represents a list of sentences in the AST.
   * It contains a list of sentences, which are the children of the sentence list node.
   */
  public class SentenceList extends Node {
    private List<Node> sentences;
    
    /**
     * Constructor for the SentenceList class.
     * @param sentences - The list of sentences that are the children of the sentence list node.
     */
    public SentenceList(List<Node> sentences) {
      this.sentences = sentences;
    }

    /**
     * Adds a sentence to the list of sentences.
     * @param sentence - The sentence to add to the list of sentences.
     */
    public void add(Node sentence) {
      sentences.add(sentence);
    }

    /**
     * Generates the TAC code for the entire list of sentences by calling the gen method of its children.
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
     * @param condition - The condition of the if statement.
     * @param trueBranch - The true branch of the if statement.
     * @param falseBranch - The false branch of the if statement (can be null).
     */
    public If(Condition condition, Node trueBranch, Node falseBranch) {
      this.condition = condition;
      this.trueSentence = trueBranch;
      this.falseSentence = falseBranch;
    }

    /**
     * Generates the TAC code for the entire conditional statement by calling the gen method of its children
     * and combining the results with conditional and unconditional jumps.
     * @return The TAC code for the entire conditional statement.
     */
    public String gen() {
      String conditionCode = condition.gen();
      String trueBranchCode = trueSentence.gen();
      String falseBranchCode = falseSentence == null ? "" : falseSentence.gen();
      String l1 = symTable.newLabel();
      String l2 = symTable.newLabel();
      String l3 = falseSentence == null ? null : symTable.newLabel();
      String trueBranch = condition.requiresInversion() ? l2 : l1;
      String falseBranch = condition.requiresInversion() ? l1 : l2;
      return conditionCode +
        TAB + "if (" + condition + ") goto " + trueBranch + ";" + NL +
        TAB + "goto " + falseBranch + ";" + NL +
        l1 + ":" + NL +
        trueBranchCode +
        (falseSentence == null ? "" : TAB + "goto " + l3 + ";" + NL) +
        l2 + ":" + NL +
        falseBranchCode +
        (falseSentence == null ? "" : l3 + ":" + NL);
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
     * @param expression - The expression to be printed.
     */
    public Print(Expression expression) {
      this.expression = expression;
    }

    /**
     * Generates the TAC code for the print statement by calling the gen method of its child expression
     * and combining the result with the print statement.
     * @return The TAC code for the print statement.
     */
    public String gen() {
      String expressionCode = expression.gen();
      return expressionCode +
        TAB + "print " + expression + ";" + NL;
    }
  }

  /**
   * The Condition class is an abstract class that defines the interface for all conditions in the AST.
   * Each condition must implement the getCondition and requiresInversion methods.
   * 
   * This class is used more as a reference than as a class to inherit from.
   */
  public class Condition extends Node {
    private String condition;
    private boolean requiresInversion;
  
    public String gen() {
      return "";  // No code is generated by default.
    }

    public String getCondition() {
      return condition;
    }

    public boolean requiresInversion() {
      return requiresInversion;
    }
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
     * It also generates the condition string and sets the requiresInversion flag based on the operator.
     * @param left - The left-hand side expression of the condition.
     * @param right - The right-hand side expression of the condition.
     * @param op - The operator of the condition.
     */
    public BinaryCondition(Expression left, Expression right, String op) {
      this.left = left;
      this.right = right;
      if (op.equals("==") || op.equals("!=") || op.equals("<")) {
        this.requiresInversion = false;
        this.condition = left + " " + op + " " + right;
      } else if (op.equals(">")){
        this.requiresInversion = false;
        this.condition = right + " < " + left;
      } else if (op.equals(">=")) {
        this.requiresInversion = true;
        this.condition = left + " < " + right;
      } else if (op.equals("<=")) {
        this.requiresInversion = true;
        this.condition = right + " < " + left;
      }
    }

    /**
     * Gets the valid TAC condition string.
     * @return The condition string.
     */
    public String getCondition() {
      return condition;
    }

    /**
     * Gets the requiresInversion flag.
     * @return The requiresInversion flag.
     */
    public boolean requiresInversion() {
      return requiresInversion;
    }

    /**
     * Generates the code necessary to evaluate the condition.
     * I.e. the code needed to reduce the condition to a Three-Address-Code
     * valid condition where there is only two operands and one operator.
     */
    public String gen() {
      String lExpressionCode = left.gen();
      String rExpressionCode = right.gen();
      return lExpressionCode + rExpressionCode;
    }
    
    /**
     * Returns the condition string.
     * Which is the values of the left and right expressions and the valid TAC operator.
     * @return The condition string.
     */
    @Override
    public String toString() {
      return condition;
    }
  }

  /**
   * The Expression class is an abstract class that defines the interface for all expressions in the AST.
   * Each expression must implement the gen method, which generates the TAC code for the expression.
   * The value of the expression is the variable name that holds the result of the expression or a numeric literal.
   * The toString method is overridden to return the value of the expression.
   */
  public class Expression extends Node {
    private String value; // The value of the expression is the variable name that holds the result of the expression or a numeric literal.
    
    /**
     * Constructor for the Expression class.
     * @param value - The value of the expression, which is the variable name that holds the result of the expression or a numeric literal.
     */
    public Expression(String value) {
      this.value = value;
    }

    /**
     * Gets the value of the expression, which is the variable name that holds the result of the expression or a numeric literal.
     * @return The value of the expression.
     */
    public String getValue() {
      return value;
    }

    /**
     * Generates the TAC code for the expression.
     * Only some expressions generate TAC code, so the default implementation returns an empty string.
     * @return The TAC code for the expression.
     */
    public String gen() {
      return "";  // No code is generated for some expressions.
    }

    /**
     * Returns the value of the expression, which is the variable name that holds the result of the expression or a numeric literal.
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
     * @param value - The value of the numeric literal.
     */
    public NumericLiteral(String value) {
      super(value);
    }
  }

  /**
   * The Identifier class represents an identifier expression in the AST.
   * It contains the name of the identifier.
   */
  public class Identifier extends Expression {

    /**
     * Constructor for the Identifier class.
     * @param value - The name of the identifier.
     */
    public Identifier(String value) {
      super(value);
    }
  }

  /**
   * The Assignment class represents an assignment expression in the AST.
   * It contains an identifier and an expression.
   */
  public class Assignment extends Expression {
    private String id;
    private Expression expression;

    /**
     * Constructor for the Assignment class.
     * @param id - The identifier that holds the result of the expression.
     * @param expression - The expression to be assigned to the identifier.
     */
    public Assignment(String id, Expression expression) {
      super(id);  // The value of the expression is the variable name that holds the result of the expression.
      this.id = id;
      this.expression = expression;
    }

    /**
     * Generates the TAC code for the assignment expression by calling the gen method of its child expression
     * and combining the result with the assignment statement.
     * @return The TAC code for the assignment expression.
     */
    public String gen() {
      String expressionCode = expression.gen();
      return expressionCode +
        TAB + id + " = " + expression + ";" + NL;
    }
  }

  /**
   * The BinaryArithmetic class represents a binary arithmetic expression in the AST.
   * It contains two expressions and an operator.
   * It also contains a temporary variable to hold the result of the expression.
   */
  public class BinaryArithmetic extends Expression {
    private Expression left;
    private Expression right;
    private String op;
    private String temp;

    /**
     * Constructor for the BinaryArithmetic class.
     * @param left - The left-hand side expression of the arithmetic expression.
     * @param right - The right-hand side expression of the arithmetic expression.
     * @param op - The operator of the arithmetic expression.
     */
    public BinaryArithmetic(Expression left, Expression right, String op) {
      super(symTable.newTemp());
      this.temp = this.getValue();
      this.left = left;
      this.right = right;
      this.op = op;
    }

    /**
     * Generates the TAC code for the arithmetic expression by calling the gen method of its children
     * and combining the result with the arithmetic operation.
     * @return The TAC code for the arithmetic expression.
     */
    public String gen() {
      String leftCode = left.gen();
      String rightCode = right.gen();
      return leftCode + rightCode +
        TAB + temp + " = " + left + " " + op + " " + right + ";" + NL;
    }
  }

  /**
   * The UnaryMinus class represents a unary minus expression in the AST.
   * It contains an expression and a temporary variable to hold the result of the expression.
   */
  public class UnaryMinus extends Expression {
    private Expression expression;
    private String temp;

    /**
     * Constructor for the UnaryMinus class.
     * @param expression - The expression to be negated.
     */
    public UnaryMinus(Expression expression) {
      super(symTable.newTemp());
      this.temp = this.getValue();
      this.expression = expression;
    }

    /**
     * Generates the TAC code for the unary minus expression by calling the gen method of its child expression
     * and combining the result with the unary minus operation.
     * @return The TAC code for the unary minus expression.
     */
    public String gen() {
      String expressionCode = expression.gen();
      return expressionCode +
        TAB + temp + " = -" + expression + ";" + NL;
    }
  }
}

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

/**
 * A scope table is a data structure that keeps track of the scope of variables
 * in a program. It is used to resolve variable names to their current holders.
 * <p>
 * A holder is a unique name for a variable in a program. It is used to
 * distinguish between different instances of the same variable name in different
 * scopes.
 * <p>
 * A scope table is a stack of sets of variable names. The top set of variable
 * names is the current scope. The table maps variable names to holders.
 * <p>
 * The table also keeps track of the current number of temporary variables and
 * labels in the program. These are used to generate unique names for temporary
 * variables and labels.
 */
public class ScopeTable {
  private Map<String, Stack<String>> table; // Map from variable names to holders
  private Stack<HashSet<String>> scopes; // Stack of sets of variables in each scope
  private int tempCount;
  private int labelCount;
  private boolean debug;

  /**
   * Constructs a new symbol table.
   */
  public ScopeTable() {
    table = new HashMap<String, Stack<String>>();
    scopes = new Stack<HashSet<String>>();
    scopes.push(new HashSet<String>()); // Push the global scope
    tempCount = 0;
    labelCount = 0;
  }

  public ScopeTable(boolean debug) {
    this();
    this.debug = debug;
  }

  /**
   * Pushes a new scope to the symbol table.
   */
  public void pushScope() {
    scopes.push(new HashSet<String>());

    if (debug) {
      System.err.println(" [[SCOPE]] New scope: " + scopes.peek());
    }
  }

  /**
   * Pops the top scope from the symbol table.
   * 
   * All variables in the popped scope are removed from the table.
   */
  public void popScope() {
    if (debug) {
      System.err.println(" [[SCOPE]] Popping scope: " + scopes.peek());
    }
    for (String key : scopes.pop()) {
      remove(key);
    }
  }

  /**
   * Adds a variable to the top scope of the symbol table.
   * 
   * If the variable is already in the top scope, a RuntimeException is thrown.
   * 
   * If the variable is not in the table, it is added to the table with a new
   * stack of holders.
   * 
   * If the variable is already in the table, it is added to the top of the
   * stack of holders.
   * 
   * @param key
   * @return The name of the variable in the table.
   */
  public String add(String key) {
    if (debug) {
      System.err.println(" [[SCOPE]] Adding " + key + " to scope: " + scopes.peek());
    }

    if (scopes.peek().contains(key)) {
      throw new RuntimeException("Variable " + key + " already declared in this scope");
    }
    scopes.peek().add(key);

    if (!table.containsKey(key)) {
      table.put(key, new Stack<String>());
      table.get(key).push(key);
    } else {
      table.get(key).push(key + "_" + table.get(key).size());
    }
    return table.get(key).peek();
  }

  /**
   * Removes the top holder of the variable from the symbol table.
   * 
   * If the variable is not in the table, nothing happens.
   * 
   * If the variable has no more holders, it is removed from the table.
   * (I.e., the variable is out of scope.)
   * @param key
   */
  public void remove(String key) {
    if (table.containsKey(key)) {
      table.get(key).pop();
      if (table.get(key).isEmpty()) {
        table.remove(key);
      }
    }
  }

  /**
   * Returns the top holder of the variable.
   * @param key
   * @return
   */
  public String get(String key) {
    if (table.containsKey(key)) {
      return table.get(key).peek();
    }
    return key;
  }
  public boolean contains(String key) {
    return table.containsKey(key);
  }

  public String newTemp() {
    return "$t" + tempCount++;
  }

  public String newLabel() {
    return "L" + labelCount++;
  }

  public String toString() {
    return table.toString();
  }
}

import java.util.HashMap;
import java.util.Map;

public class SymTable {
  private Map<String, String> table;
  private int tempCount = 0;

  public SymTable() {
    table = new HashMap<String, String>();
  }

  public void add(String key, String value) {
    table.put(key, value);
  }

  public String get(String key) {
    return table.get(key);
  }

  public boolean contains(String key) {
    return table.containsKey(key);
  }

  public String newTemp() {
    return "$t" + tempCount++;
  }

  public String toString() {
    return table.toString();
  }
}

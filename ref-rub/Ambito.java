import java.util.Stack;

public class Ambito {
	private boolean debug;

	private int numAmbito;
	private Stack<Integer> pilaAmbito;

	public Ambito(String debug){
		this.debug = debug.equals("debug") ? true : false;
		
		this.numAmbito = 1;
		this.pilaAmbito = new Stack<>();
		this.pilaAmbito.push(numAmbito);
	}

	public Ambito(){
		this("");
	}

	public int getBloque(){
		return pilaAmbito.peek();
	}

	public void entrarBloque(){
		numAmbito++;
		pilaAmbito.push(numAmbito);

		if (debug) {
			System.out.println("# Entramos al ambito: "+getBloque());
		}
	}

	public void salirBloque(){
		pilaAmbito.pop();

		if (debug) {
			System.out.println("# Salimos al ambito: "+getBloque());
		}
	}

	@Override
	public String toString() {
		return "Te encuentras en el ambito: "+getBloque();
	}
}

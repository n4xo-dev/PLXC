import java.util.Vector;

public abstract class Objeto {
	private String nombre;
	private int ambito;
	private Valor valor;

	public Objeto(String nombre, int ambito, Valor valor){
		this.nombre = nombre;
		this.ambito = ambito;
		this.valor = valor;
	}

	public String getNombre() {
		return nombre;
	}

	public int getAmbito() {
		return ambito;
	}

	public Valor getValor() {
		return valor;
	}

	public String toString(){
		String cad = "";

		switch (valor) {
			case CONSTANTE:
				cad = nombre;
			break;
			case TEMPORAL:
				cad = nombre;
			break;
			case VARIABLE:
				cad = (ambito == 1) ? nombre : nombre+"_"+ambito;
			break;
			default:
				cad = "ERROR";
			break;
		}

		return cad;
	}

	public abstract Tipado getTipo();
	
	public abstract Objeto metodosInstancia(TablaSimbolos tabla, Metodos metodo, Vector<Objeto> parametros);
}

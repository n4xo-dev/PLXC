import java.util.Vector;

public class ObjetoVoid extends Objeto{
	private Tipado tipo;

	public ObjetoVoid(String nombre, int ambito, Valor valor) {
		super(nombre, ambito, valor);
		tipo = Tipado.VOID;
	}

	@Override
	public Tipado getTipo() {
		return tipo;
	}

	@Override
	public Objeto metodosInstancia(TablaSimbolos tabla, Metodos metodo, Vector<Objeto> parametros) {
		return null;
	}
	
}

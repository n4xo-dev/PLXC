public abstract class ObjetoArray extends Objeto{
	private int dimension;

	public ObjetoArray(String nombre, int ambito, int dimension, Valor valor) {
		super(nombre, ambito, valor);
		this.dimension = dimension;
	}

	public int getDimension() {
		return dimension;
	}

}

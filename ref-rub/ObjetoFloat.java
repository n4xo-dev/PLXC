import java.util.Vector;

public class ObjetoFloat extends Objeto{
	Tipado tipo;

	public ObjetoFloat(String nombre, int ambito, Valor valor) {
		super(nombre, ambito, valor);
		tipo = Tipado.FLOAT;
	}

	@Override
	public Tipado getTipo() {
		return tipo;
	}

	@Override
	public Objeto metodosInstancia(TablaSimbolos tabla, Metodos metodo, Vector<Objeto> parametros) {
		Objeto result = null;

		switch (metodo) {
			case PRINT: {
				PLXC.out.println("   print "+this+";");
			}
			break;

			case SUMA: {
				Objeto segundo = parametros.get(0);
				switch (segundo.getTipo()) {
					case FLOAT:
						result = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+this+" +r "+segundo+";");
						break;
					case INT:
						Objeto cast = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+cast+" = (float) "+segundo+";");
						result = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+this+" +r "+cast+";");
						break;
					default:
						PLXC.out.println("# ERROR: El tipo de las expresiones es diferente "+this.getTipo().name()+" y "+segundo.getTipo().name());
						PLXC.out.println("   error;");
						PLXC.out.println("   halt;");
						System.exit(0);
						break;
				}
			}
			break;
			
			case RESTA: {
				Objeto segundo = parametros.get(0);
				switch (segundo.getTipo()) {
					case FLOAT:
						result = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+this+" -r "+segundo+";");
						break;
					case INT:
						Objeto cast = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+cast+" = (float) "+segundo+";");
						result = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+this+" -r "+cast+";");
						break;
					default:
						PLXC.out.println("# ERROR: El tipo de las expresiones es diferente "+this.getTipo().name()+" y "+segundo.getTipo().name());
						PLXC.out.println("   error;");
						PLXC.out.println("   halt;");
						System.exit(0);
						break;
				}
			}
			break;

			case MENOSUNARIO: {
				result = new ObjetoInt(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
				PLXC.out.println("   "+result+" = -"+this+";");
			}
			break;

			case MULTIPLICACION: {
				Objeto segundo = parametros.get(0);
				switch (segundo.getTipo()) {
					case FLOAT:
						result = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+this+" *r "+segundo+";");
						break;
					case INT:
						Objeto cast = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+cast+" = (float) "+segundo+";");
						result = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+this+" *r "+cast+";");
						break;
					default:
						PLXC.out.println("# ERROR: El tipo de las expresiones es diferente "+this.getTipo().name()+" y "+segundo.getTipo().name());
						PLXC.out.println("   error;");
						PLXC.out.println("   halt;");
						System.exit(0);
						break;
				}
			}
			break;

			case DIVISION: {
				Objeto segundo = parametros.get(0);
				switch (segundo.getTipo()) {
					case FLOAT:
						result = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+this+" /r "+segundo+";");
						break;
					case INT:
						Objeto cast = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+cast+" = (float) "+segundo+";");
						result = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+this+" /r "+cast+";");
						break;
					default:
						PLXC.out.println("# ERROR: El tipo de las expresiones es diferente "+this.getTipo().name()+" y "+segundo.getTipo().name());
						PLXC.out.println("   error;");
						PLXC.out.println("   halt;");
						System.exit(0);
						break;
				}
			}
			break;

			case ASIGNACION: {
				Objeto segundo = parametros.get(0);
				switch (segundo.getTipo()) {
					case FLOAT:
						PLXC.out.println("   "+this+" = "+segundo+";");
						result = segundo;
						break;
					case INT:
						Objeto cast = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+cast+" = (float) "+segundo+";");
						result = this;
						PLXC.out.println("   "+this+" = "+cast+";");
						break;
					default:
						PLXC.out.println("# ERROR: Se esta asignando un valor "+segundo.getTipo().name()+" a una variable "+this.getTipo().name());
						PLXC.out.println("   error;");
						PLXC.out.println("   halt;");
						System.exit(0);
						break;
				}
			}
			break;

			case CAST_INT: {
				Objeto cast = new ObjetoInt(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
				PLXC.out.println("   "+cast+" = (int) "+this+";");
				result = cast;
			}
			break;

			case CAST_FLOAT: {
				result = this;
			}
			break;

			default:
				PLXC.out.println("# ERROR: Metodo "+metodo.name()+" no permitido para el tipo "+tipo.name());
				PLXC.out.println("   error;");
				PLXC.out.println("   halt;");
				System.exit(0);
				break;
		}

		return result;
	}
}

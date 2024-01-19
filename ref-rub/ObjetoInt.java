import java.util.Vector;

public class ObjetoInt extends Objeto {
	private Tipado tipo;
	
	public ObjetoInt(String nombre, int ambito, Valor valor) {
		super(nombre, ambito, valor);
		this.tipo = Tipado.INT;
	}

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
					case INT:
						result = new ObjetoInt(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+this+" + "+segundo+";");
						break;
					case FLOAT:
						Objeto cast = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+cast+" = (float) "+this+";");
						result = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+cast+" +r "+segundo+";");
						break;
					case CHAR:
						result = new ObjetoInt(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+this+" + "+segundo+";");
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
					case INT:
						result = new ObjetoInt(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+this+" - "+segundo+";");
						break;
					case FLOAT:
						Objeto cast = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+cast+" = (float) "+this+";");
						result = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+cast+" -r "+segundo+";");
						break;
					case CHAR:
						result = new ObjetoInt(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+this+" - "+segundo+";");
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
					case INT:
						result = new ObjetoInt(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+this+" * "+segundo+";");
						break;
					case FLOAT:
						Objeto cast = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+cast+" = (float) "+this+";");
						result = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+cast+" *r "+segundo+";");
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
					case INT:
						result = new ObjetoInt(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+this+" / "+segundo+";");
						break;
					case FLOAT:
						Objeto cast = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+cast+" = (float) "+this+";");
						result = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
						PLXC.out.println("   "+result+" = "+cast+" /r "+segundo+";");
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
					case INT:
						PLXC.out.println("   "+this+" = "+segundo+";");
						result = segundo;
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
				result = this;
			}
			break;

			case CAST_FLOAT: {
				Objeto cast = new ObjetoFloat(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
				PLXC.out.println("   "+cast+" = (float) "+this+";");
				result = cast;
			}
			break;

			case CAST_CHAR: {
				Objeto cast = new ObjetoChar(this.getNombre(), this.getAmbito() , this.getValor());
				result = cast;
			}
			break;
			
			default: {
				PLXC.out.println("# ERROR: Metodo "+metodo.name()+" no permitido para el tipo "+tipo.name());
				PLXC.out.println("   error;");
				PLXC.out.println("   halt;");
				System.exit(0);
			}
			break;
		}

		return result;
	}

}

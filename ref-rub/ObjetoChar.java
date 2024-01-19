import java.util.Vector;

public class ObjetoChar extends Objeto{
	Tipado tipo;

	public ObjetoChar(String nombre, int ambito, Valor valor) {
		super(nombre, ambito, valor);
		this.tipo = Tipado.CHAR;
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
				PLXC.out.println("   printc "+this+";");
			}
			break;

			case ASIGNACION: {
				Objeto segundo = parametros.get(0);
				switch (segundo.getTipo()) {
					case CHAR:
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
				Objeto cast = new ObjetoInt(this.getNombre(), this.getAmbito() , this.getValor());
				result = cast;
			}
			break;
			
			case SUMA: {
				Objeto segundo = parametros.get(0);
				switch (segundo.getTipo()) {
					case INT:
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
			
			default:
				PLXC.out.println("# ERROR: Metodo "+metodo.name()+" no permitido para el tipo "+tipo.name());
				PLXC.out.println("   error;");
				PLXC.out.println("   halt;");
				System.exit(0);
				break;
		}

		return result;
	}

	public static String ascii(String caracter){
		String ascii = "";

		if(caracter.length() == 1){
			ascii = (int) caracter.charAt(0) +"";
		} else if(caracter.length() == 2){
			switch (caracter.substring(1, 2)) {
				case "b":
					ascii = (int) '\b' +"";
					break;
				case "n":
					ascii = (int) '\n' +"";
					break;
				case "f":
					ascii = (int) '\f' +"";
					break;
				case "r":
					ascii = (int) '\r' +"";
					break;
				case "t":
					ascii = (int) '\t' +"";
					break;
				case "'":
					ascii = (int) '\'' +"";
					break;
				case "\"":
					ascii = (int) '\"' +"";
					break;
				case "\\":
					ascii = (int) '\\' +"";
					break;
				default:
					System.out.println("# ERROR: Caracter no reconocido");
					break;
			}
		} else if(caracter.length() == 6){
			ascii = Integer.parseInt(caracter.substring(2,6), 16) +"";
		} else {
			// ERROR;
		}
		
		return ascii;
	}
	
}

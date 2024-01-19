import java.util.Vector;

public class ObjetoArrayChar extends ObjetoArray{
	private Tipado tipo;

	public ObjetoArrayChar(String nombre, int ambito, int lengths, Valor valor) {
		super(nombre, ambito, lengths, valor);
		this.tipo = Tipado.ARRAY_CHAR;
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
				Objeto temporal = new ObjetoChar(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
				for (int i = 0; i < this.getDimension(); i++) {
					PLXC.out.println("   "+temporal+" = "+this+"["+i+"];");
					temporal.metodosInstancia(tabla, Metodos.PRINT, null);
				}
			}
			break;

			case DEFINIR_LENGTH: {
				PLXC.out.println("   $"+this+"_length = "+this.getDimension()+";");
			}
			break;

			case MOSTRAR_LENGTH: {
				result = new ObjetoInt("$"+this.getNombre()+"_length", this.getAmbito(), Valor.TEMPORAL);
			}
			break;

			case COMPROBAR_DIMENSION: {
				
				int dimension = this.getDimension();
				Objeto indice = parametros.get(0);
				
				String eF = tabla.getEtiqueta();
				String eV = tabla.getEtiqueta();
				
				PLXC.out.println("   if ("+indice   +" < " +0     +") goto "+eF+";");
				PLXC.out.println("   if ("+dimension+" < " +indice+") goto "+eF+";");
				PLXC.out.println("   if ("+dimension+" == "+indice+") goto "+eF+";");
				PLXC.out.println("   goto "+eV+";");
				PLXC.out.println(eF+":");
				PLXC.out.println("   error;");
				PLXC.out.println("   halt;");
				PLXC.out.println(eV+":");

			}
			break;

			case DEVOLVER_ARRAY: {
				Objeto indice = parametros.get(0);

				result = new ObjetoChar(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
				PLXC.out.println("   "+result+" = "+this+"["+indice+"];");
			}
			break;
			
			case ASIGNACION: {
				Objeto expresion = parametros.get(0);
				switch (expresion.getTipo()) {
					case ARRAY_CHAR:
						result = this;

						if(this.getDimension() < ((ObjetoArray) expresion).getDimension()){
							PLXC.out.println("# ERROR: La dimension del array "+result+" es menor que la de "+expresion);
							PLXC.out.println("   error;");
							PLXC.out.println("   halt;");
							System.exit(0);
						}
						else{
							Objeto temporal = new ObjetoChar(tabla.getTemporal(), tabla.getAmbito().getBloque(), Valor.TEMPORAL);
							for (int i = 0; i < ((ObjetoArray) expresion).getDimension(); i++) {
								PLXC.out.println("   "+temporal+" = "+expresion+"["+i+"];");
								PLXC.out.println("   "+result+"["+i+"] = "+temporal+";");
							}

						}

						break;
					default:
						PLXC.out.println("# ERROR: Se esta asignando un valor "+expresion.getTipo().name()+" a una variable "+this.getTipo().name());
						PLXC.out.println("   error;");
						PLXC.out.println("   halt;");
						System.exit(0);
						break;
				}
			}
			break;

			case ASIGNACION_INDICE: {
				Objeto indice = parametros.get(0);
				Objeto expresion = parametros.get(1);
				switch (expresion.getTipo()) {
					case CHAR:
						result = this;
						PLXC.out.println("   "+result+"["+indice+"]"+" = "+expresion+";");
						break;
					default:
						PLXC.out.println("# ERROR: Se esta asignando un valor "+expresion.getTipo().name()+" a una variable "+this.getTipo().name());
						PLXC.out.println("   error;");
						PLXC.out.println("   halt;");
						System.exit(0);
						break;
				}
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

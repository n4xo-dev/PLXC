import java.util.Vector;

public class ObjetoString extends Objeto {
	private Tipado tipo;

	private int length;

	public ObjetoString(String nombre, int ambito, Valor valor) {
		super(nombre, ambito, valor);
		this.tipo = Tipado.STRING;
		length = 0;
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
				String t1 = tabla.getTemporal();
				String t2 = tabla.getTemporal();
				String eI = tabla.getEtiqueta();
				String eV = tabla.getEtiqueta();
				String eF = tabla.getEtiqueta();
				
				PLXC.out.println("   "+t1+" = 0;");
				
				PLXC.out.println(eI+":");
				
				PLXC.out.println("   if ("+t1+" < " + this.getLength() + ") goto "+eV+";");
				PLXC.out.println("   goto "+eF+";");
				
				PLXC.out.println(eV+":");

				PLXC.out.println("   "+t2+" = "+this+"["+t1+"];");
				PLXC.out.println("   writec "+t2+";");
				PLXC.out.println("   "+t1+" = "+t1+" + 1;");
				PLXC.out.println("   goto "+eI+";");
				
				PLXC.out.println(eF+":");
				
				PLXC.out.println("   writec 10;");
			}
			break;

			case ASIGNACION: {
				Objeto segundo = parametros.get(0);
				switch (segundo.getTipo()) {
					case STRING:
						String t1 = tabla.getTemporal();
						String t2 = tabla.getTemporal();
						String t3 = tabla.getTemporal();	// No se usa en PLXC
						String eI = tabla.getEtiqueta();
						String eV = tabla.getEtiqueta();
						String eF = tabla.getEtiqueta();
						String eN1 = tabla.getEtiqueta();	// No se usa en PLXC
						String eN2 = tabla.getEtiqueta();
						String eN3 = tabla.getEtiqueta();

						PLXC.out.println("# asignar array "+this+" <- "+segundo);
						
						PLXC.out.println("   "+this.getLength()+" = 0;");
						PLXC.out.println("   "+t1+" = 0;");
				
						PLXC.out.println(eI+":");
						
						PLXC.out.println("   if ("+t1+" < " + ((ObjetoString) segundo).getLength() + ") goto "+eV+";");
						PLXC.out.println("   goto "+eF+";");
						
						PLXC.out.println(eV+":");
						
						PLXC.out.println("   "+t2+" = "+segundo+"["+t1+"];");
						PLXC.out.println(eN2+":");
						PLXC.out.println("   "+this+"["+this.getLength()+"] = "+t2+";");
						PLXC.out.println("   "+this.getLength()+" = "+this.getLength()+" + 1;");
						PLXC.out.println(eN3+":");
						PLXC.out.println("   "+t1+" = "+t1+" + 1;");
						PLXC.out.println("   goto "+eI+";");
						
						PLXC.out.println(eF+":");
						
						result = this;
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

			case STRING_CONSTANTE: {
				Objeto segundo = parametros.get(0);
				
				PLXC.out.println("   "+this+"["+length+"] = "+segundo+";");
				length++;
				
			}
			break;

			case LENGTH_CONSTANTE: {
				
				PLXC.out.println("   "+getLength()+" = "+length+";");
				
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
	
	public String getLength(){
		return "$"+this.toString()+"_length";
	}

}

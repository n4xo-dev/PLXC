import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

public class TablaSimbolos {
	
	// Almacena todos los objetos del sistema. Compuesto por <"Nombre de la variable", <"Ambito", "Objeto">>
	private Map<String, TreeMap<Integer, Objeto>> tabla;
	// Indice de las variables temporales
	private int temporal;
	// Indice de las etiquetas
	private int etiqueta;
	// ambito de bloque en el que nos encontramos
	private Ambito ambito;

	// Constructor
	public TablaSimbolos(){
		tabla = new HashMap<String, TreeMap<Integer, Objeto>>();
		temporal = 0;
		etiqueta = 0;
		ambito = new Ambito();
	}

	// Obtenemos una variable de la tabla a traves de su nombre en el ambito mas alto.
	public Objeto obtenerVariable(String nombre){
		Objeto o = null;

		TreeMap<Integer, Objeto> ambitos = tabla.get(nombre);
		if(ambitos == null){
			PLXC.out.println("# ERROR: Variable '"+ nombre +"' no declarada");
			PLXC.out.println("error;");
			PLXC.out.println("halt;");
			System.exit(0);
		}
		else{
			o = ambitos.get(ambitos.lastKey());
		}

		return o;
	}

	// Almacenamos una variable en la tabla
	public void declararVariable(Objeto o){
		TreeMap<Integer, Objeto> ambitos = tabla.get(o.getNombre());
		if(ambitos == null){
			// La variable no se ha declarado todavia.
			tabla.put(o.getNombre(), new TreeMap<Integer, Objeto>());
			tabla.get(o.getNombre()).put(o.getAmbito(), o);
		}
		else{
			// La variable ya ha sido declarada en algun ambito.
			if (ambitos.get(ambito.getBloque()) == null) {
				// La variable no ha sido declarada en este ambito
				tabla.get(o.getNombre()).put(o.getAmbito(), o);
			}
			else {
				// La variable ha sido declarada en este ambito
				PLXC.out.println("# ERROR: Variable '"+ o.getNombre() +"' ya ha sido declarada");
				PLXC.out.println("error;");
				PLXC.out.println("halt;");
				System.exit(0);
			}
		}
	}

	// Al salir de un ambito borramos todas las variabbles que pertenezca a ese ambito
	public void borrarAmbito(int ambito){
		for (String clave : tabla.keySet()) {
			TreeMap<Integer, Objeto> ambitos = tabla.get(clave);

			if(tabla.containsKey(clave) && tabla.get(clave).containsKey(ambito)){
				ambitos.remove(ambito);
			
				if (ambitos.isEmpty()) {
					tabla.remove(clave);
				}
			}
		}
	}

	// Obtener nombre de nueva variable temporal
	public String getTemporal(){
		return "$t" + temporal++;
	}

	// Obtener nombre de nueva etiqueta
	public String getEtiqueta(){
		return "L" + etiqueta++;
	}

	// Obtener ambito
	public Ambito getAmbito(){
		return ambito;
	}

	// Busqueda de una variable en la tabla de simbolos.
}

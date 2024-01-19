public enum Tipado {
	VOID,
	INT,
	FLOAT,
	CHAR,
	STRING,
	ARRAY_INT,
	ARRAY_FLOAT,
	ARRAY_CHAR;

	public static boolean isArray(Tipado tipo){
		return tipo == ARRAY_INT   ||
					 tipo == ARRAY_FLOAT ||
					 tipo == ARRAY_CHAR;
	}
}

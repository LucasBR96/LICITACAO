package modelos;

public enum Medida {
	
	METROS( 0 ), QUILOS( 1 ), UNIDADES( 2 ), CENTENAS( 3 ), DUZIAS( 4 ), LITROS( 5 );
	
	private final int valor;
	Medida( int valor ) {
		this.valor = valor;
	}
}

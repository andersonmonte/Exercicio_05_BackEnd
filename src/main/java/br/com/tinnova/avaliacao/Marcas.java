package br.com.tinnova.avaliacao;

/**
 * Enum criado para padronizar os nomes das marcas de veículo.
 */
public enum Marcas {
	VOLKSWAGEN("Volkswagen"), 
	CHEVROLET("Chevrolet"),
	FORD("Ford"),
	FIAT("Fiat"),
	RENAULT("Renault"),
	MITSUBISHI("Mitsubishi"),
	HONDA("Honda"),
	HYUNDAI("Hyundai");

    private final String nome;

    /**
     * @param text
     */
    Marcas(final String nome) {
        this.nome = nome;
    }
    
	/**
	 * Busca o enum correto a partir de uma String.
	 * 
	 * @param nome - String;
	 * 
	 * @return Enum, ou null
	 */
	public static Marcas buscarMarcaEnum(String nome){
	    for(Marcas m : Marcas.values()){
	        if( m.toString().toUpperCase().equals(nome.toUpperCase())){
	            return m;
	        }
	    }
	    return null;
	}
    
    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return nome;
    }
}

package br.com.tinnova.avaliacao;

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

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return nome;
    }
}

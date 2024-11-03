package objetos;

public class Vaga {
    public int numero;
    public int tamanho;
    public boolean disponivel;

    public Vaga(int numero, int tamanho) {
        this.numero = numero;
        this.tamanho = tamanho;
        this.disponivel = true;
    }

    @Override
    public String toString() {
        return "Número: " + numero + " | Tamanho: " + tamanho + " | Disponível: " + disponivel;
    }
}

package objetos;

public class Ocupacao {

    public int id;
    public Vaga vaga;
    public Veiculo veiculo;
    public double valorPago;
    public long duracaoEmMinutos;

    public Ocupacao(int id, Vaga vaga, Veiculo veiculo) {
        this.id = id;
        this.vaga = vaga;
        this.veiculo = veiculo;
        this.valorPago = 0;
        this.duracaoEmMinutos = 0;
    }

}

package objetos;

import java.time.LocalTime;

public class Veiculo {
    public String placa;
    public String modelo;
    public int tamanho;
    public LocalTime horaEntrada;
    public LocalTime horaSaida;

    public Veiculo(String placa, String modelo, int tamanho) {
        this.placa = placa;
        this.modelo = modelo;
        this.tamanho = tamanho;
    }

    @Override
    public String toString() {
        return "Placa: " + placa + " | Modelo: " + modelo + " | Tamanho: " + tamanho + " | Hora de Entrada: " + horaEntrada + " | Hora de Saída: " + horaSaida;
    }

}
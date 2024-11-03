import objetos.Ocupacao;
import objetos.Vaga;
import objetos.Veiculo;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static List<Vaga> vagas = new ArrayList<>();
    public static List<Veiculo> veiculos = new ArrayList<>();
    public static List<Ocupacao> ocupacoes = new ArrayList<>();
    public static List<Ocupacao> vagasOcupadas = new ArrayList<>();

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        System.out.println("\n-- Menu --\n" +
                "1 - Cadastrar Vaga\n" +
                "2 - Cadastrar Veículo\n" +
                "3 - Entrada de Veículo\n" +
                "4 - Saída de Veículo\n" +
                "5 - Relatório de Vagas Ocupadas\n" +
                "6 - Histórico de permanência\n" +
                "7 - Sair\n");

        executar();
    }

    public static void executar() {
        System.out.print("Digite a opção desejada: ");
        int entrada = scanner.nextInt();
        scanner.nextLine();

        switch (entrada) {
            case 1:
                cadastrarVaga();
                break;
            case 2:
                cadastrarVeiculo();
                break;
            case 3:
                entradaVeiculo();
                break;
            case 4:
                saidaVeiculo();
                break;
            case 5:
                relatorioVagasOcupadas();
                break;
            case 6:
                historicoVeiculos();
                break;
            case 7:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida!\n");
                break;
        }
        if (entrada != 7) {
            menu();
        }
    }

    public static void cadastrarVaga() {
        System.out.print("Número da vaga: ");
        int numero = scanner.nextInt();
        scanner.nextLine();

        for(Vaga vaga : vagas) {
            if(vaga.numero == numero) {
                System.out.println("Vaga já existente!");
                return;
            }
        }

        System.out.print("Tamanho da vaga (1 - Pequeno, 2 - Médio, 3 - Grande): ");
        int tamanho = scanner.nextInt();
        scanner.nextLine();

        if (tamanho < 1 || tamanho > 3) {
            System.out.println("Opção de tamanho inválida!");
            return;
        }

        Vaga vaga = new Vaga(numero, tamanho);
        vagas.add(vaga);
        System.out.println("Vaga cadastrada com sucesso!");
    }

    public static void cadastrarVeiculo() {
        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine();

        for(Veiculo veiculo : veiculos) {
            if(veiculo.placa.equals(placa)) {
                System.out.println("Placa já existente!");
                return;
            }
        }

        System.out.print("Modelo do veículo: ");
        String modelo = scanner.nextLine();

        System.out.print("Tamanho do carro (1 - Pequeno, 2 - Médio, 3 - Grande): ");
        int tamanho = scanner.nextInt();
        scanner.nextLine();

        if (tamanho < 1 || tamanho > 3) {
            System.out.println("Opção de tamanho inválida!");
            return;
        }

        Veiculo veiculo = new Veiculo(placa, modelo, tamanho);
        veiculos.add(veiculo);
        System.out.println("Veículo cadastrado com sucesso!");
    }

    public static void entradaVeiculo() {
        System.out.print("Digite a placa do veículo: ");
        String placa = scanner.nextLine();

        Veiculo veiculo = encontrarVeiculo(placa);

        if (veiculo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }

        for (Ocupacao oc : vagasOcupadas) {
            if (oc.veiculo.equals(veiculo) && !oc.vaga.disponivel) {
                System.out.println("Veículo já está estacionado na vaga " + oc.vaga.numero);
                return;
            }
        }

        vagasDisponiveis(veiculo.tamanho);
        System.out.print("Escolha a vaga para estacionar: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        Vaga vagaEscolhida = null;

        for (Vaga vaga : vagas) {
            if (escolha == vaga.numero) {
                vagaEscolhida = vaga;
            }
        }

        System.out.print("Digite a hora de entrada (formato HH:mm): ");
        String entradaHora = scanner.nextLine();

        veiculo.horaEntrada = LocalTime.parse(entradaHora);
        vagaEscolhida.disponivel = false;

        Ocupacao ocupacao = new Ocupacao(ocupacoes.size() + 1, vagaEscolhida, veiculo);
        ocupacoes.add(ocupacao);
        vagasOcupadas.add(ocupacao);

        System.out.println("Vaga reservada com sucesso!");
    }

    public static void saidaVeiculo() {
        System.out.print("Digite a placa do veículo para saída: ");
        String placa = scanner.nextLine();

        Veiculo veiculo = encontrarVeiculo(placa);
        if (veiculo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }

        Ocupacao ocupacao = null;
        for (Ocupacao oc : ocupacoes) {
            if (oc.veiculo.equals(veiculo)) {
                ocupacao = oc;
                break;
            }
        }

        if (ocupacao == null) {
            System.out.println("Ocupação não encontrada para o veículo!");
            return;
        }

        System.out.print("Digite a hora de saída (formato HH:mm): ");
        String saidaHora = scanner.nextLine();
        veiculo.horaSaida = LocalTime.parse(saidaHora);

        long duracaoEmMinutos = java.time.Duration.between(veiculo.horaEntrada, veiculo.horaSaida).toMinutes();
        double valorPago;

        if (duracaoEmMinutos <= 60) {
            valorPago = 5.00;
        } else if (duracaoEmMinutos <= 180) {
            valorPago = 10.00;
        } else {
            valorPago = 15.00;
        }

        ocupacao.valorPago = valorPago;
        ocupacao.vaga.disponivel = true;
        ocupacao.duracaoEmMinutos = duracaoEmMinutos;

        vagasOcupadas.remove(ocupacao);

        System.out.println("Veículo liberado com sucesso!");
        System.out.println("Tempo de permanência: " + (duracaoEmMinutos / 60) + " horas e " + (duracaoEmMinutos % 60) + " minutos");
        System.out.println("Valor a ser pago: R$ " + valorPago);
    }

    public static void relatorioVagasOcupadas(){
        System.out.println("\n-- Relatório de Vagas Ocupadas --");

        boolean haOcupacao = false;

        for (Ocupacao ocupacao : vagasOcupadas) {
            if (!ocupacao.vaga.disponivel) {
                haOcupacao = true;
                System.out.println("Vaga: " + ocupacao.vaga.numero);
                System.out.println("Tamanho da Vaga: " + tamanhoParaTexto(ocupacao.vaga.tamanho));
                System.out.println("Placa do Veículo: " + ocupacao.veiculo.placa);
                System.out.println("---------------------------");
            }
        }

        if (!haOcupacao) {
            System.out.println("Não há vagas ocupadas no momento.");
        }
    }

    public static void historicoVeiculos() {
        System.out.println("\n-- Histórico de Permanência dos Veículos --");

        boolean haHistorico = false;

        for (Ocupacao ocupacao : ocupacoes) {
            if (ocupacao.veiculo.horaSaida != null) {
                haHistorico = true;

                System.out.println("Placa do Veículo: " + ocupacao.veiculo.placa);
                System.out.println("Tempo de Permanência: " + (ocupacao.duracaoEmMinutos / 60) + " horas e " + (ocupacao.duracaoEmMinutos % 60) + " minutos");

                System.out.printf("Valor Pago: R$ %.2f%n", ocupacao.valorPago);

                System.out.println("---------------------------");
            }
        }

        if (!haHistorico) {
            System.out.println("Nenhum histórico de permanência encontrado.");
        }
    }

    public static Veiculo encontrarVeiculo(String placa){
        Veiculo veiculoEncontrado = null;

        for (Veiculo veiculo : veiculos) {
            if(veiculo.placa.equals(placa)){
                veiculoEncontrado = veiculo;
            }
        }

        return veiculoEncontrado;
    }

    public static void vagasDisponiveis(int tamanho) {
        int contador = 0;

        System.out.println("-- Vagas disponíveis --");
        for (Vaga vaga : vagas) {
            if (vaga.disponivel && vaga.tamanho >= tamanho) {
                System.out.println(vaga.numero + " - " + tamanhoParaTexto(vaga.tamanho));
                contador++;
            }
        }

        if(contador == 0) {
            System.out.println("Nenhuma vaga disponível para o veículo.");
        }
    }

    public static String tamanhoParaTexto(int tamanho) {
        switch (tamanho) {
            case 1: return "Pequeno";
            case 2: return "Médio";
            case 3: return "Grande";
            default: return "Desconhecido";
        }
    }


}

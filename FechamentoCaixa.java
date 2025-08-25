import java.util.InputMismatchException;
import java.util.Scanner;

public class FechamentoCaixa {

    // Acumuladores gerais para armazenar dados financeiros e de vendas
    private static double receitaTotal = 0.0; 
    private static int numeroVendas = 0; 
    private static int itensTotais = 0;

    // Variáveis para rastrear a maior e menor venda do dia
    private static double maiorVendaValor = Double.NEGATIVE_INFINITY; 
    private static String maiorVendaProduto = null; 

    private static double menorPrecoUnitario = Double.POSITIVE_INFINITY;
    private static String menorPrecoProduto = null;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        
        while (true) {
            exibirMenu();
            int opcao = lerInteiro(sc, "Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    registrarVenda(sc); 
                    break;
                case 2:
                    exibirRelatorio(false);
                    break;
                case 3:
                    exibirRelatorio(true);
                    System.out.println("\nDia fechado. Programa encerrado.");
                    sc.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente (1, 2 ou 3).");
                    break;
            }
        }
    }

    // Método para exibir o menu de opções
    private static void exibirMenu() {
        System.out.println("\n========================");
        System.out.println("  FECHAMENTO DE CAIXA");
        System.out.println("========================");
        System.out.println("1 - Registrar venda do produto");
        System.out.println("2 - Exibir relatório parcial");
        System.out.println("3 - Fechar o dia (encerrar)");
    }

    // Método para registrar uma nova venda
    private static void registrarVenda(Scanner sc) {
        System.out.println("\n--- Registrar Venda ---");

        String nome = lerStringNaoVazia(sc, "Nome do produto: ");

        double preco = lerDoublePositivo(sc, "Preço unitário (R$): ");

        int quantidade = lerInteiroPositivo(sc, "Quantidade (Unidades): ");

        // Calcula o valor total da venda
        double valorVenda = preco * quantidade;

        // Atualiza os acumuladores com os dados da venda
        receitaTotal += valorVenda;
        numeroVendas++;
        itensTotais += quantidade;

        // Atualiza a maior venda se a venda atual for maior
        if (valorVenda > maiorVendaValor) {
            maiorVendaValor = valorVenda;
            maiorVendaProduto = nome;
        }

        // Atualiza o menor preço unitário se o preço atual for menor
        if (preco < menorPrecoUnitario) {
            menorPrecoUnitario = preco;
            menorPrecoProduto = nome;
        }

        // Mensagem de confirmação da venda registrada
        System.out.println("\nVenda registrada com sucesso!");
        System.out.println("Produto: " + nome);
        System.out.println("Preço unitário: " + formatarMoeda(preco));
        System.out.println("Quantidade: " + quantidade);
        System.out.println("Valor da venda: " + formatarMoeda(valorVenda));
    }

    // Método para exibir o relatório de vendas
    private static void exibirRelatorio(boolean finalDoDia) {
        System.out.println("\n================================");
        System.out.println(finalDoDia ? "    RELATÓRIO FINAL DO DIA" : " RELATÓRIO PARCIAL");
        System.out.println("================================");

        // Verifica se não houve vendas registradas
        if (numeroVendas == 0) {
            System.out.println("Nenhuma venda registrada até o momento.");
            System.out.println("--------------------------------");
            System.out.println("Receita total: " + formatarMoeda(0));
            System.out.println("Nº de vendas (transações): 0");
            System.out.println("Total de itens vendidos: 0");
            System.out.println("Maior venda: —");
            System.out.println("Produto com menor preço unitário: —");
            return; // Sai do método se não houver vendas
        }

        // Exibe os dados do relatório
        System.out.println("Receita total: " + formatarMoeda(receitaTotal));
        System.out.println("Nº de vendas (transações): " + numeroVendas);
        System.out.println("Total de itens vendidos: " + itensTotais);

        System.out.println("Maior venda: " + (maiorVendaProduto != null ? formatarMoeda(maiorVendaValor) + " (Produto: " + maiorVendaProduto + ")" : "—"));
        System.out.println("Produto com menor preço unitário: " + (menorPrecoProduto != null ? menorPrecoProduto + " (" + formatarMoeda(menorPrecoUnitario) + ")" : "—"));
    }

    // ===== Utilidades de leitura com validação =====

    // Método para ler um número inteiro com tratamento de exceções
    private static int lerInteiro(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = sc.nextInt();
                sc.nextLine();
                return v;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
                sc.nextLine();
            }
        }
    }

    // Método para ler um número inteiro positivo
    private static int lerInteiroPositivo(Scanner sc, String prompt) {
        while (true) {
            int v = lerInteiro(sc, prompt);
            if (v > 0) return v;
            System.out.println("Valor inválido. O número deve ser maior que zero.");
        }
    }

    // Método para ler um número decimal (double) com tratamento de exceções
    private static double lerDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = sc.nextDouble();
                sc.nextLine();
                return v;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número (use ponto para decimais).");
                sc.nextLine();
            }
        }
    }

    // Método para ler um número decimal positivo
    private static double lerDoublePositivo(Scanner sc, String prompt) {
        while (true) {
            double v = lerDouble(sc, prompt);
            if (v > 0) return v;
            System.out.println("Valor inválido. O número deve ser maior que zero.");
        }
    }

    // Método para ler uma string não vazia
    private static String lerStringNaoVazia(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Valor inválido. O texto não pode ser vazio.");
        }
    }

    // Método para formatar um valor monetário
    private static String formatarMoeda(double v) {
        return String.format("R$ %.2f", v);
    }
}

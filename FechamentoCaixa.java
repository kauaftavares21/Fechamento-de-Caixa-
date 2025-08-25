import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

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
        try (Scanner sc = new Scanner(System.in)) {
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
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente (1, 2 ou 3).");
                        break;
                }
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
        System.out.println("-----------------------------");
        System.out.printf("Produto:   %-20s%n", nome);
        System.out.printf("Preço:     %-20s%n", formatarMoeda(preco));
        System.out.printf("Qtd:       %-20d%n", quantidade);
        System.out.printf("Total:     %-20s%n", formatarMoeda(valorVenda));
    }

    // Método para exibir o relatório de vendas (parcial ou final)
    private static void exibirRelatorio(boolean finalDoDia) {
        System.out.println("\n================================");
        System.out.println(finalDoDia ? "    RELATÓRIO FINAL DO DIA" : "      RELATÓRIO PARCIAL");
        System.out.println("================================");

        if (numeroVendas == 0) {
            System.out.println("Nenhuma venda registrada até o momento.");
            System.out.println("---------------------------------------");
            System.out.printf("%-30s %s%n", "Receita total:", formatarMoeda(0));
            System.out.printf("%-30s %d%n", "Nº de vendas (transações):", 0);
            System.out.printf("%-30s %d%n", "Total de itens vendidos:", 0);
            System.out.printf("%-30s %s%n", "Maior venda:", "—");
            System.out.printf("%-30s %s%n", "Produto com menor preço:", "—");
            return;
        }

        // Exibe os dados do relatório em formato de tabela
        System.out.printf("%-30s %s%n", "Receita total:", formatarMoeda(receitaTotal));
        System.out.printf("%-30s %d%n", "Nº de vendas (transações):", numeroVendas);
        System.out.printf("%-30s %d%n", "Total de itens vendidos:", itensTotais);

        if (maiorVendaProduto != null) {
            System.out.printf("%-30s %s (%s)%n", "Maior venda:", maiorVendaProduto, formatarMoeda(maiorVendaValor));
        } else {
            System.out.printf("%-30s %s%n", "Maior venda:", "—");
        }

        if (menorPrecoProduto != null) {
            System.out.printf("%-30s %s (%s)%n", "Produto com menor preço:", menorPrecoProduto, formatarMoeda(menorPrecoUnitario));
        } else {
            System.out.printf("%-30s %s%n", "Produto com menor preço:", "—");
        }
    }

    // ===== Utilidades de leitura com validação =====

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

    private static int lerInteiroPositivo(Scanner sc, String prompt) {
        while (true) {
            int v = lerInteiro(sc, prompt);
            if (v > 0) return v;
            System.out.println("Valor inválido. O número deve ser maior que zero.");
        }
    }

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

    private static double lerDoublePositivo(Scanner sc, String prompt) {
        while (true) {
            double v = lerDouble(sc, prompt);
            if (v > 0) return v;
            System.out.println("Valor inválido. O número deve ser maior que zero.");
        }
    }

    private static String lerStringNaoVazia(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Valor inválido. O texto não pode ser vazio.");
        }
    }

    private static String formatarMoeda(double v) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(v);
    }
}

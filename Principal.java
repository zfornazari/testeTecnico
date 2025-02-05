import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.*;
import java.text.DecimalFormat;

public class Principal{
    public static void main(String[] args){
        //3.1
        List<Funcionario> funcionarios = criarFuncionarios();
        //System.out.println("\nLista de funcionários iniciais:");
        //imprimirFuncionarios(funcionarios);

        //3.2
        removerFuncionario(funcionarios, "João");

        //3.3
        System.out.println("\nLista de funcionários:");
        imprimirFuncionarios(funcionarios);

        //3.4
        aumentarSalarios(funcionarios);
        System.out.println("\nFuncionários com aumento de salário aplicado:");
        imprimirFuncionarios(funcionarios);

        //3.6
        System.out.println("\nFuncionários agrupados por função:");
        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparPorFuncao(funcionarios);
        imprimirFuncionariosPorFuncao(funcionariosPorFuncao);

        //3.8
        System.out.println("\nAniversariantes de Outubro e Dezembro:");
        imprimirAniversariantes(funcionarios);

        //3.9
        System.out.println("\nFuncionário mais velho:");
        imprimirFuncionarioMaisVelho(funcionarios);

        //3.10
        ordenarPorNome(funcionarios);
        System.out.println("\nFuncionários ordenados por nome, em ordem alfabética:");
        imprimirFuncionarios(funcionarios);

        //3.11
        System.out.println("\nTotal dos salários:");
        imprimirTotalSalarios(funcionarios);

        //3.12
        System.out.println("\nSalários mínimos por funcionário:");
        imprimirSalariosMinimos(funcionarios);
    }

    //3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela acima.
    private static List<Funcionario> criarFuncionarios(){
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
        return funcionarios;
    }

    //3.2 – Remover o funcionário “João” da lista.
    private static void removerFuncionario(List<Funcionario> funcionarios, String nome){
        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase(nome));
    }

    //3.3 – Imprimir todos os funcionários com todas suas informações, sendo que:
    //• informação de data deve ser exibido no formato dd/mm/aaaa;
    //• informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula.
    private static void imprimirFuncionarios(List<Funcionario> funcionarios){
        //format do salario
        DecimalFormat df = new DecimalFormat("#,##0.00");
        
        for (Funcionario f : funcionarios){
            //nome | data | R$ 0.000,00 | funcao
            System.out.println(f.getNome() +
                    " | " +
                    f.getDataNascimento().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                    " | " +
                    "R$ " +
                    df.format(f.getSalario()) +
                    " | " +
                    f.getFuncao());
        }
    }

    //3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
    private static void aumentarSalarios(List<Funcionario> funcionarios){
        for (Funcionario f : funcionarios){
            f.setSalario(f.getSalario().multiply(new BigDecimal("1.10")));
        }
    }

    //3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.
    private static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios){
        Map<String, List<Funcionario>> mapa = new HashMap<>();
        for (Funcionario f : funcionarios){
            mapa.computeIfAbsent(f.getFuncao(), k -> new ArrayList<>()).add(f);
        }
        return mapa;
    }

    //3.6 – Imprimir os funcionários, agrupados por função.
    private static void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> mapa){
        for (Map.Entry<String, List<Funcionario>> entry : mapa.entrySet()){
            System.out.println("\n" + entry.getKey() + ":");
            imprimirFuncionarios(entry.getValue());
        }
    }

    //3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
    private static void imprimirAniversariantes(List<Funcionario> funcionarios){
        for (Funcionario f : funcionarios){
            Month mes = f.getDataNascimento().getMonth();
            if (mes == Month.OCTOBER || mes == Month.DECEMBER){
                System.out.println(f.getNome() +
                " - " +
                f.getDataNascimento().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }
    }

    //3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
    private static void imprimirFuncionarioMaisVelho(List<Funcionario> funcionarios){
        Funcionario maisVelho = Collections.min(funcionarios, Comparator.comparing(Funcionario::getDataNascimento));
        int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
        //nome - xx anos.
        System.out.println(maisVelho.getNome() +
                             " - " + 
                             idade + 
                             " anos.");
    }

    //3.10 – Imprimir a lista de funcionários por ordem alfabética.
    private static void ordenarPorNome(List<Funcionario> funcionarios){
        funcionarios.sort(Comparator.comparing(Funcionario::getNome));
    }

    //3.11 – Imprimir o total dos salários dos funcionários.
    private static void imprimirTotalSalarios(List<Funcionario> funcionarios){
        BigDecimal total = funcionarios.stream()
            .map(Funcionario::getSalario)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("R$ " + new DecimalFormat("#,##0.00").format(total));
    }


    //3.12 – Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.
    private static final BigDecimal salarioMinimo = new BigDecimal("1212.00");

    private static void imprimirSalariosMinimos(List<Funcionario> funcionarios){
        for (Funcionario f : funcionarios){
            BigDecimal qtdSalariosMinimos = f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            //nome recebe x.y salários minimos.
            System.out.println(f.getNome() +
                                " recebe " +
                                qtdSalariosMinimos +
                                " salários mínimos.");
        }
    }
}

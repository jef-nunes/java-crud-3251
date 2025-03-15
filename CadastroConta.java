import java.util.ArrayList;
import java.util.Scanner;

public class CadastroConta {

    private static final int REGISTRAR_CONTA = 1;
    private static final int VISUALIZAR_CONTA = 2;
    private static final int SAIR = 3;

    private static Scanner sc;

    private static int limiteContas = 1000;

    private static ArrayList<Conta> contasRegistradas = new ArrayList();

    private static boolean contaExiste(int numeroConta){
        boolean existe = false;
        for (Conta conta : contasRegistradas){
            if(conta.getNumeroConta()==numeroConta){
                existe = true;
            }
        }
        return existe;
    }
    private static void registraNovaConta(int numeroConta, String nomeTitular, double saldo) throws ExcecaoElementoJaExiste{
        if(CadastroConta.contaExiste(numeroConta)){
            throw new ExcecaoElementoJaExiste();
        }
        else{
            Conta conta = new Conta(numeroConta,nomeTitular,saldo);
            contasRegistradas.add(conta);
        }
    }

    private static void visualizarConta(int numeroConta) throws ExcecaoElementoInexistente{
        boolean elementoEncontrado = false;
        int indiceAtual = 0;
        int indiceElementoEncontrado = -1;
        for (Conta conta : contasRegistradas){
            if(conta.getNumeroConta()==numeroConta){
                elementoEncontrado = true;
                indiceElementoEncontrado = indiceAtual;
            }
            indiceAtual++;
        }
        if(elementoEncontrado){
            Conta contaEncontrada = contasRegistradas.get(indiceElementoEncontrado);
            System.out.println("[Dados da conta]");
            System.out.println("Titular: "+contaEncontrada.getNomeTitular());
            System.out.println("Número: "+contaEncontrada.getNumeroConta());
            System.out.println("Saldo: "+contaEncontrada.getSaldo());
        }
        else{
            throw new ExcecaoElementoInexistente();
        }
    }

    private static boolean limiteDeContasAtingido(){
        return(contasRegistradas.size()==limiteContas);
    }

    private static void loopPrincipal() throws ExcecaoRepositorio{
        int escolha = 0;
        while (escolha != SAIR){
            System.out.println("_____ Cadastrar Contas _____");
            System.out.println("1. Registra conta");
            System.out.println("2. Visualiza conta");
            System.out.println("3. Sair");
            System.out.println("Escolha uma opção: ");
            escolha = sc.nextInt();
            sc.nextLine();
            switch (escolha) {
                case REGISTRAR_CONTA:
                    if(limiteDeContasAtingido()){
                        throw new ExcecaoRepositorio();
                    }
                    else{
                        double saldoInicial = 0;
                        String nomeTitular = "";
                        int numeroConta = -1;
                        
                        System.out.println("Informe o nome do titular: ");
                        nomeTitular = sc.nextLine();

                        System.out.println("Informe o número da conta: ");
                        numeroConta = sc.nextInt();
                        sc.nextLine();

                        System.out.println("Informe o saldo: ");
                        saldoInicial = sc.nextDouble();
                        sc.nextLine();

                        try {
                            registraNovaConta(numeroConta, nomeTitular, saldoInicial);
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                    }
                    break;
                case VISUALIZAR_CONTA:
                    int numConta = -1;
                    System.out.println("Informe o número da conta: ");
                    numConta = sc.nextInt();
                    sc.nextLine();
                    try{
                        visualizarConta(numConta);
                    } catch(ExcecaoElementoInexistente e) {
                        System.out.println(e);
                    }
                    break;
                case SAIR:
                    System.out.println("Até mais!");
                    sc.close();
                    System.exit(0);
                default:
                    break;
            }
        }
    }

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        try {
            loopPrincipal();
        } catch (ExcecaoRepositorio e) {
            sc.close();
            System.exit(1);
        }
        sc.close();
    }
}

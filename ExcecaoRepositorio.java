public class ExcecaoRepositorio extends Exception{
    public ExcecaoRepositorio(){
        super("Erro - Limite de armazenamento atingido pelo sistema");
    }
}
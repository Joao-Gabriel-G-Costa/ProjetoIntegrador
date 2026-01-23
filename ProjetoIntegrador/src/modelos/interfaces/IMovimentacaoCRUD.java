package modelos.interfaces;
import java.util.ArrayList;
import modelos.classes.Movimentacao;

public interface IMovimentacaoCRUD {
    void salvar (Movimentacao movimentacoes) throws Exception;    
    ArrayList<Movimentacao> listaDeMovimentacoes() throws Exception;
    Movimentacao buscarPorId(int idMovimentacao) throws Exception;
    void atualizar(Movimentacao movimentacoes) throws Exception;
    void remover(int idMovimentacao) throws Exception;
   }

package controle;

import persistencia.MovimentacaoDAO;
import java.util.ArrayList;
import modelos.classes.Movimentacao;
import modelos.interfaces.IMovimentacaoCRUD;

public class ControleMovimentacao implements IMovimentacaoCRUD {

    private MovimentacaoDAO dao = new MovimentacaoDAO();

    public ControleMovimentacao() {}

    @Override
    public void salvar(Movimentacao mov) throws Exception {
        try {
            // Validações básicas
            if (mov.getData() == null || mov.getData().trim().isEmpty()) {
                throw new Exception("A data não pode estar vazia!");
            }
            if (mov.getValor() <= 0) {
                throw new Exception("O valor deve ser maior que zero!");
            }

            // Verificar se ID já existe
            Movimentacao existente = dao.buscarPorId(mov.getIdMovimentacao());
            if (existente != null) {
                throw new Exception("Já existe uma movimentação com esse ID!");
            }

            dao.salvar(mov);

        } catch (Exception erro) {
            throw erro;
        }
    }

    @Override
    public ArrayList<Movimentacao> listaDeMovimentacoes() throws Exception {
        return dao.listaDeMovimentacoes();
    }

    @Override
    public Movimentacao buscarPorId(int id) throws Exception {
        return dao.buscarPorId(id);
    }

    @Override
    public void atualizar(Movimentacao mov) throws Exception {
        try {
            if (mov.getData() == null || mov.getData().trim().isEmpty()) {
                throw new Exception("A data não pode estar vazia!");
            }
            if (mov.getValor() <= 0) {
                throw new Exception("O valor deve ser maior que zero!");
            }

            Movimentacao existente = dao.buscarPorId(mov.getIdMovimentacao());
            if (existente == null) {
                throw new Exception("Não existe movimentação com esse ID!");
            }

            dao.atualizar(mov);

        } catch (Exception erro) {
            throw erro;
        }
    }

    @Override
    public void remover(int id) throws Exception {
        dao.remover(id);
    }
}

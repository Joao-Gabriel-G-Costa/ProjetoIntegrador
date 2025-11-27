package controle;

import Persistencia.DespesasDAO;
import modelos.classes.Despesas.*;
import java.util.ArrayList;
import modelos.classes.Despesas;
import modelos.intefaces.DespesasCRUD;

public class ControleDespesas implements DespesasCRUD {

    private DespesasDAO dao = new DespesasDAO();

    public ControleDespesas() {
    
    }

    @Override
    public void salvar(Despesas tipoDeDespesa) throws Exception {
        try{
            if (tipoDeDespesa.getDescricao().isEmpty()) {
                throw new Exception("A descrição não pode estar vazia!");
            }
            Despesas existente = dao.buscarPorId(tipoDeDespesa.getID());
            if (existente != null) {
                throw new Exception("Já existe um tipo de despesa com esse ID!");
            }

            dao.salvar(tipoDeDespesa);
        }catch(Exception erro){
            throw erro;
        }
        }

    @Override
    public ArrayList<Despesas> listaDeTiposDeDespesas() throws Exception {
        return dao.listaDeTiposDeDespesas();
    }

    @Override
    public Despesas buscarPorId(int idTipoDeDespesa) throws Exception {
        return dao.buscarPorId(idTipoDeDespesa);
    }

    @Override
    public void atualizar(Despesas tipoDeDespesa) throws Exception {
        
        try {
        if (tipoDeDespesa.getDescricao().isEmpty()) {
            throw new Exception("A descrição não pode estar vazia!");
        }
        Despesas existente = dao.buscarPorId(tipoDeDespesa.getID());
        if (existente == null) {
            throw new Exception("Tipo de despesa com esse ID não existe!");
        }

        dao.atualizar(tipoDeDespesa);
    } catch (Exception erro) {
        throw erro;
    }
}

    @Override
    public void remover(int idTipoDeDespesa) throws Exception {
        dao.remover(idTipoDeDespesa);
    }

}
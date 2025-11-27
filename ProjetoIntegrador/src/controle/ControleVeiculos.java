package controle;

import Persistencia.VeiculosDAO;
import modelos.classes.Veiculos;
import modelos.intefaces.VeiculosCRUD;

import java.util.ArrayList;

public class ControleVeiculos implements VeiculosCRUD {

    private VeiculosDAO dao = new VeiculosDAO();

    public ControleVeiculos() {}

    @Override
    public void salvar(Veiculos veiculo) throws Exception {
        try {
            // Validações
            if (veiculo.getPlaca() == null || veiculo.getPlaca().trim().isEmpty()) {
                throw new Exception("A placa não pode estar vazia!");
            }
            if (veiculo.getMarca() == null || veiculo.getMarca().trim().isEmpty()) {
                throw new Exception("A marca não pode estar vazia!");
            }
            if (veiculo.getModelo() == null || veiculo.getModelo().trim().isEmpty()) {
                throw new Exception("O modelo não pode estar vazio!");
            }

            // Verificar se ID já existe
            Veiculos existente = dao.buscarPorId(veiculo.getIdVeiculo());
            if (existente != null) {
                throw new Exception("Já existe um veículo com esse ID!");
            }

            dao.salvar(veiculo);

        } catch (Exception erro) {
            throw erro;
        }
    }

    @Override
    public ArrayList<Veiculos> listaVeiculos() throws Exception {
        return dao.listaVeiculos();
    }

    @Override
    public Veiculos buscarPorId(int id) throws Exception {
        return dao.buscarPorId(id);
    }

    @Override
    public void atualizar(Veiculos veiculo) throws Exception {
        try {
            if (veiculo.getPlaca() == null || veiculo.getPlaca().trim().isEmpty()) {
                throw new Exception("A placa não pode estar vazia!");
            }
            if (veiculo.getMarca() == null || veiculo.getMarca().trim().isEmpty()) {
                throw new Exception("A marca não pode estar vazia!");
            }
            if (veiculo.getModelo() == null || veiculo.getModelo().trim().isEmpty()) {
                throw new Exception("O modelo não pode estar vazio!");
            }

            // Verificar existência
            Veiculos existente = dao.buscarPorId(veiculo.getIdVeiculo());
            if (existente == null) {
                throw new Exception("Não existe veículo com esse ID!");
            }

            dao.atualizar(veiculo);

        } catch (Exception erro) {
            throw erro;
        }
    }

    @Override
    public void remover(int id) throws Exception {
        dao.remover(id);
    }
}

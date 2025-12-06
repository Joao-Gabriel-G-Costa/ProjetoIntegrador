package controle;

import persistencia.VeiculosDAO;
import modelos.classes.Veiculos;

import java.util.ArrayList;
import modelos.interfaces.IVeiculosCRUD;

public class ControleVeiculos implements IVeiculosCRUD {

    private VeiculosDAO dao = new VeiculosDAO();

    public ControleVeiculos() {}
    
    public void validarAnoFab(String anoFabricacao) throws Exception{ 
        try {
            int ano = 0;
            ano = Integer.parseInt(anoFabricacao.trim());
        } catch (NumberFormatException e) {
            throw new Exception("O ano deve ser um número inteiro!");
        }
    }

    @Override
    public void salvar(Veiculos veiculo) throws Exception {
        validarAnoFab(veiculo.getAnoFabricacao());
        try {
            // Validações
            if (veiculo.getIdVeiculo() <= 0) {
                throw new Exception("O ID não ser menor ou igual a 0");
            }
            if (veiculo.getPlaca() == null || veiculo.getPlaca().isEmpty()) {
                throw new Exception("A placa não pode estar vazia!");
            }
            if (veiculo.getMarca() == null || veiculo.getMarca().trim().isEmpty()) {
                throw new Exception("A marca não pode estar vazia!");
            }
            if (veiculo.getModelo() == null || veiculo.getModelo().isEmpty()) {
                throw new Exception("O modelo não pode estar vazio!"); 
            }
            if (veiculo.getAnoFabricacao() == null || veiculo.getAnoFabricacao().isEmpty()) {
                throw new Exception("O Ano de fabricação não pode estar vazio!"); 
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
        validarAnoFab(veiculo.getAnoFabricacao());
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

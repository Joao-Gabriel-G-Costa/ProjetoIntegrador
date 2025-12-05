package controle;

import persistencia.MovimentacaoDAO;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelos.classes.Movimentacao;
import modelos.interfaces.IMovimentacaoCRUD;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;

public class ControleMovimentacao implements IMovimentacaoCRUD {

    private MovimentacaoDAO dao = new MovimentacaoDAO();

    public ControleMovimentacao() {
    }
    
    public void validarData(String data) throws Exception {
        try {
            if(data == null || data.trim().isEmpty()){
                throw new Exception("A data não pode estar vazia!");
            }
            if (data.length() != 10) {
                throw new  Exception("Use o formato 'dd/mm/aaaa'");
                
            }
            String[] dataSeparada = data.split("/");
            
            
            int mes = Integer.parseInt(dataSeparada[1]);
            int ano = Integer.parseInt(dataSeparada[2]);  
            
            if (mes < 1 || mes > 12) {
            throw new Exception(" Use valores de 1 a 12.");
            }
            
            if (ano < 2000 || ano > 2100) {
            throw new Exception("Ano inválido!");
            }
            
        } catch (Exception e) {
            throw e;
        }
    }
    

    @Override
    public void salvar(Movimentacao mov) throws Exception {
        try {
            
            validarData(mov.getData());
            // Validações básicas
            if (mov.getDescricao() == null || mov.getDescricao().trim().isEmpty()) {
                throw new Exception("A Descrição não pode estar vazia!");
            }
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
            validarData(mov.getData());
            
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

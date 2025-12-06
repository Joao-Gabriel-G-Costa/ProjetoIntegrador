
package persistencia;

import modelos.interfaces.IRelatorios;
import modelos.classes.Veiculos;
import modelos.classes.Movimentacao;
import modelos.classes.Despesas; 
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RelatoriosDAO implements IRelatorios {
    private VeiculosDAO veicDAO = new VeiculosDAO();
    private MovimentacaoDAO moviDAO = new MovimentacaoDAO(); 
    private DespesasDAO despDAO = new DespesasDAO();
    
    @Override
    public ArrayList<Veiculos> listarVeiculos() throws Exception {
        return veicDAO.listaVeiculos();
    }
    
    @Override
    public ArrayList<Veiculos> listarVeiculosParaCombo() throws Exception {
        return listarVeiculos(); 
    }
    
    @Override
    public Veiculos buscarVeiculoPorId(int id) throws Exception {
        return veicDAO.buscarPorId(id);
    }
    
    @Override
    public ArrayList<Movimentacao> despesasMes(int mes) throws Exception {
        ArrayList<Movimentacao> lista = moviDAO.listaDeMovimentacoes();
        ArrayList<Movimentacao> filtradas = new ArrayList<>();
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Movimentacao m: lista) {
            LocalDate data = LocalDate.parse(m.getData(), formato);
            if (data.getMonthValue() == mes) {
                filtradas.add(m);
            }
        }
        return filtradas;
    }
    
    @Override
    public ArrayList<Movimentacao> despesasAno(int ano) throws Exception {
        ArrayList<Movimentacao> lista = moviDAO.listaDeMovimentacoes();
        ArrayList<Movimentacao> filtradas = new ArrayList<>();
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Movimentacao m: lista) {
            LocalDate data = LocalDate.parse(m.getData(), formato);
            if (data.getYear() == ano) {
                filtradas.add(m);
            }
        }
        return filtradas;
    }
    
    @Override
    public ArrayList<Veiculos> listarInativos() throws Exception {
        ArrayList<Veiculos> lista = veicDAO.listaVeiculos();
        ArrayList<Veiculos> inativos = new ArrayList<>(); 
        
        for (Veiculos v : lista) {
            if (!v.isSituacao()) {
                inativos.add(v);
            }
        }
        return inativos;
    }
    
    @Override
    public ArrayList<Movimentacao> despesasVeiculos(int idVeiculo) throws Exception {
        ArrayList<Movimentacao> lista = moviDAO.listaDeMovimentacoes();
        ArrayList<Movimentacao> despesas = new ArrayList<>();
        
        for (Movimentacao m : lista) {
            if (m.getIdVeiculo() == idVeiculo) {
                despesas.add(m);
            }
        }
        return despesas;
    }
    
    @Override
    public ArrayList<Movimentacao> ipvaAno(int ano) throws Exception {
        ArrayList<Movimentacao> todasAno = despesasAno(ano);
        ArrayList<Movimentacao> ipvas = new ArrayList<>();

        for (Movimentacao m : todasAno) {
            Despesas d = despDAO.buscarPorId(m.getIdDespesa());
            if (d != null && "IPVA".equalsIgnoreCase(d.getDescricao())) {
                ipvas.add(m);
            }
        }
        return ipvas;
    }

    @Override
    public ArrayList<Movimentacao> multasVeiculoAno(int idVeiculo, int ano) throws Exception {
        ArrayList<Movimentacao> todasAno = despesasAno(ano);
        ArrayList<Movimentacao> multas = new ArrayList<>();

        for (Movimentacao m : todasAno) {
            if (m.getIdVeiculo() != idVeiculo) {
                continue;
            }

            Despesas d = despDAO.buscarPorId(m.getIdDespesa());
            if (d != null && "Multa".equalsIgnoreCase(d.getDescricao())) {
                multas.add(m);
            }
        }
        return multas;
    }

}
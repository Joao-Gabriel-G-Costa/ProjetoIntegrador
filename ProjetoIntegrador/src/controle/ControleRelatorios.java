package controle;

import modelos.interfaces.IRelatorios;
import persistencia.RelatoriosDAO;
import modelos.classes.Veiculos;
import modelos.classes.Movimentacao;
import modelos.classes.Despesas;
import persistencia.DespesasDAO;
import java.util.ArrayList;

public class ControleRelatorios {
    private IRelatorios relatoriosDAO;
    private DespesasDAO despDAO;
    
    public ControleRelatorios() {
        relatoriosDAO = new RelatoriosDAO();
        despDAO = new DespesasDAO();
    }
    
    // Métodos básicos
    public ArrayList<Veiculos> listarVeiculos() throws Exception {
        return relatoriosDAO.listarVeiculos();
    }
    
    public ArrayList<Veiculos> listarVeiculosParaCombo() throws Exception {
        return relatoriosDAO.listarVeiculosParaCombo();
    }
    
    public Veiculos buscarVeiculoPorId(int id) throws Exception {
        return relatoriosDAO.buscarVeiculoPorId(id);
    }
    
    // Métodos de relatórios (podem lançar exceções para serem tratadas na tela)
    public ArrayList<Veiculos> gerarRelatorioInativos() throws Exception {
        return relatoriosDAO.listarInativos();
    }
    
    public ResultadoRelatorioVeiculo gerarRelatorioDespesasVeiculo(int idVeiculo) throws Exception {
        ArrayList<Movimentacao> movimentacoes = relatoriosDAO.despesasVeiculos(idVeiculo);
        double total = 0;
        
        for (Movimentacao m : movimentacoes) {
            total += m.getValor();
        }
        
        return new ResultadoRelatorioVeiculo(idVeiculo, movimentacoes, total);
    }
    
    public ResultadoRelatorioPeriodo gerarRelatorioDespesasMes(int mes) throws Exception {
        ArrayList<Movimentacao> movimentacoes = relatoriosDAO.despesasMes(mes);
        double total = 0;
        
        for (Movimentacao m : movimentacoes) {
            total += m.getValor();
        }
        
        return new ResultadoRelatorioPeriodo(mes, movimentacoes, total);
    }
    
    public ResultadoRelatorioPeriodo gerarRelatorioCombustivelMes(int mes) throws Exception {
    ArrayList<Movimentacao> movimentacoes = relatoriosDAO.despesasMes(mes);
    ArrayList<Movimentacao> combustiveis = new ArrayList<>();
    double total = 0;
    
    for (Movimentacao m : movimentacoes) {
        Despesas d = despDAO.buscarPorId(m.getIdDespesa());
        if (d != null && d.getDescricao().toLowerCase().contains("combustivel")) {
            combustiveis.add(m);
            total += m.getValor();
        }
    }
    
    return new ResultadoRelatorioPeriodo(mes, combustiveis, total);
    }
    
    public ResultadoRelatorioPeriodo gerarRelatorioIpvaAno(int ano) throws Exception {
        ArrayList<Movimentacao> ipvas = relatoriosDAO.ipvaAno(ano);
        double total = 0;
        
        for (Movimentacao m : ipvas) {
            total += m.getValor();
        }
        
        return new ResultadoRelatorioPeriodo(ano, ipvas, total);
    }
    
    public ResultadoRelatorioVeiculoPeriodo gerarRelatorioMultasVeiculoAno(int idVeiculo, int ano) throws Exception {
        ArrayList<Movimentacao> multas = relatoriosDAO.multasVeiculoAno(idVeiculo, ano);
        double total = 0;
        
        for (Movimentacao m : multas) {
            total += m.getValor();
        }
        
        return new ResultadoRelatorioVeiculoPeriodo(idVeiculo, ano, multas, total);
    }
    
    // Classes internas para retornar resultados estruturados
    public class ResultadoRelatorioVeiculo {
        public int idVeiculo;
        public ArrayList<Movimentacao> movimentacoes;
        public double total;
        
        public ResultadoRelatorioVeiculo(int idVeiculo, ArrayList<Movimentacao> movimentacoes, double total) {
            this.idVeiculo = idVeiculo;
            this.movimentacoes = movimentacoes;
            this.total = total;
        }
    }
    
    public class ResultadoRelatorioPeriodo {
        public int periodo; // pode ser mês ou ano
        public ArrayList<Movimentacao> movimentacoes;
        public double total;
        
        public ResultadoRelatorioPeriodo(int periodo, ArrayList<Movimentacao> movimentacoes, double total) {
            this.periodo = periodo;
            this.movimentacoes = movimentacoes;
            this.total = total;
        }
    }
    
    public class ResultadoRelatorioVeiculoPeriodo {
        public int idVeiculo;
        public int ano;
        public ArrayList<Movimentacao> movimentacoes;
        public double total;
        
        public ResultadoRelatorioVeiculoPeriodo(int idVeiculo, int ano, ArrayList<Movimentacao> movimentacoes, double total) {
            this.idVeiculo = idVeiculo;
            this.ano = ano;
            this.movimentacoes = movimentacoes;
            this.total = total;
        }
    }
    
}
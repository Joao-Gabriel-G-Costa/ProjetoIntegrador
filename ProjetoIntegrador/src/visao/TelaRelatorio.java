/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;
import Persistencia.DespesasDAO;
import Persistencia.RelatoriosDAO;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import modelos.classes.Veiculos;
import modelos.classes.Movimentacao;
import modelos.classes.Despesas;

/**
 *
 * @author eugeniojulio
 */
public class TelaRelatorio extends javax.swing.JFrame {

    /**
     * Creates new form Relatorio
     * 
     */
    
    
    //OBJETO RELATORIODAO
    private RelatoriosDAO relDao = new RelatoriosDAO();
    
    //OBJETO DESPESAS
    private DespesasDAO despDAO = new DespesasDAO();
    
    
    
     //ATUALIZAR AS COLUNAS QUANDO O USUÁRIO ESCOLHE NO COMBOBOX
    private void atualizarColunas() {
        String tipo = (String) cbTipoRelatorio.getSelectedItem();
        
        if ("Veículos inativos".equals(tipo)) {
            
            DefaultTableModel model = new DefaultTableModel (
                    new Object [][]{},
                    new String []{"ID", "Placa", "Marca", "Modelo", "Ano", "Situação"}
                    
          );
            tabela.setModel(model);
                    
          }
        
        
        if ("Despesas de um veículo".equals(tipo)) {
            
            //MOSTRAR CAIXA PARA SELECIONAR O VEICULO
            cbVeiculos.setVisible(true);
            cbVeiculos.setEnabled(true);
            
            //LIMPAR ITENS ANTIGOS E PLACEHOLDER
            cbVeiculos.removeAllItems();
            cbVeiculos.addItem("Selecione um veículo");
            
            //PEGAR OS IDS DO RELATORIOSDAO
            ArrayList<Veiculos> lista = relDao.listarVeiculos();
            
            for (Veiculos v : lista) {
                
                 cbVeiculos.addItem(String.valueOf(v.getIdVeiculo()));
                
            }
            
                    
            if ("Despesas de um veículo".equals(tipo)){
            DefaultTableModel model = new DefaultTableModel (
                    new Object [][]{},
                    new String []{"ID","Tipo de Despesa","Descrição", "Valor"}
            
            );
            
            tabela.setModel (model);
            
            }
        }
        else {
            cbVeiculos.setVisible(false);
            cbVeiculos.setEnabled(false);
        }
    }
    
    public TelaRelatorio() {
        initComponents();
        
        //QUANDO SELECIONA NA COMBOBOX A TABELA MUDA
        cbTipoRelatorio.addActionListener(e -> atualizarColunas());
        
        
        this.setLocationRelativeTo(null);

    }
    
   
    
    private void gerarRelatoriosInativos(){
        try {
           DefaultTableModel model = (DefaultTableModel) tabela.getModel();
           //LIMPAR LINHAS ANTIGAS
           model.setRowCount(0);
           
           //CHAMAR O RELATORIODAO
           ArrayList<Veiculos> lista = relDao.listarInativos();
           
           
           //SE ESTIVER VAZIO ----> EXCEÇÃO (FAZER EM CONTROLE)
           if (lista == null || lista.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Não há veículos inativos na frota!");
                 return;
             }
           
           //LAÇO PRA POR OS VEICULOS NA TABELA
           for (Veiculos v : lista) {
               model.addRow(new Object[] {
                v.getIdVeiculo(),
                v.getPlaca(),
                v.getMarca(),
                v.getModelo(),
                v.getAnoFabricacao(), 
                "Inativo"
           });
        }
        }
         catch (Exception e) {
         JOptionPane.showMessageDialog(this, "ERRO AO GERAR RELATORIO" + e.getMessage());
        }
    }
    
    private void gerarDespesasVeiculo(){
        try {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();
            
            //LIMPAR LINHAS ANTIGAS
            model.setRowCount(0);
            
            //pegar o ID DO DO VEICULO SELECIONADO NO COMBO
            String selecionado = (String) cbVeiculos.getSelectedItem();
           if (selecionado == null || selecionado.equals("Selecione um veículo")) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo!");
            return;
        }
            
           int idVeiculo = Integer.parseInt(selecionado); // sem split
            
            //CHAMAR O RELATORIO DAO E despesasVeiculos
            ArrayList<Movimentacao> lista = relDao.despesasVeiculos(idVeiculo);
            
             //SE ESTIVER VAZIO ----> EXCEÇÃO (FAZER EM CONTROLE)
           if (lista == null || lista.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Esse veículo não possui despesas cadastradas!");
                 return;
             }
            
            //LAÇO PARA POR DADOS NA TABELA
            for (Movimentacao m : lista) {
               
                Despesas d = despDAO.buscarPorId(m.getIdDespesa());
                String tipoDespesa = (d != null) ? d.getDescricao() : "Não encontrado";

                
                model.addRow(new Object[] { 
                m.getIdVeiculo(),
                tipoDespesa,
                m.getDescricao(),
                m.getValor()
            });
        }
}
            catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "ERRO AO GERAR RELATORIO" + e.getMessage());
                    
        }
    
    }
    



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonVoltar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbTipoRelatorio = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        cbVeiculos = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JANELA @");

        jButtonVoltar.setText("Voltar");
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 3, 18)); // NOI18N
        jLabel1.setText("RELATÓRIOS");

        cbTipoRelatorio.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbTipoRelatorio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Despesas de um veículo", "Total geral de despesas da frota no mês", "Total de gastos com combustível no mês", "Total de IPVA da frota no ano", "Veículos inativos", "Multas pagas por veículo no ano" }));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("TIPO");

        jButton1.setText("GERAR RELATÓRIO");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabela);

        cbVeiculos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbVeiculos.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(783, Short.MAX_VALUE)
                .addComponent(jButtonVoltar)
                .addGap(36, 36, 36))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(246, 246, 246)
                        .addComponent(jLabel2)
                        .addGap(28, 28, 28)
                        .addComponent(cbTipoRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(348, 348, 348)
                        .addComponent(jButton1)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(362, 362, 362)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(198, 198, 198)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(376, 376, 376)
                        .addComponent(cbVeiculos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbTipoRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(28, 28, 28)
                .addComponent(cbVeiculos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButton1)
                .addGap(32, 32, 32)
                .addComponent(jButtonVoltar)
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        // TODO add your handling code here:

        MenuPrincipal Relatorio = new MenuPrincipal();
        Relatorio.setVisible(true);
        dispose();

    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         //BOTÃO GERAR RELATORIO
        String tipo = (String) cbTipoRelatorio.getSelectedItem();
        
        //DEPOIS FAZER EXCEÇÃO PARA MOSTRAR MESSAGEM QUE NAO TEM INATIVOS
        if ("Veículos inativos".equals(tipo)){
           gerarRelatoriosInativos();
        }
        
        if ("Despesas de um veículo".equals(tipo)){
           gerarDespesasVeiculo();
        }
        
        

    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbTipoRelatorio;
    private javax.swing.JComboBox<String> cbVeiculos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}

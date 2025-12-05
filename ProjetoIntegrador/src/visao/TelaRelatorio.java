/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;
import persistencia.DespesasDAO;
import persistencia.RelatoriosDAO;
import java.util.ArrayList;
import modelos.classes.Veiculos;
import modelos.classes.Movimentacao;
import modelos.classes.Despesas;
import persistencia.VeiculosDAO;

//IMPORTS PDF
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.io.FileOutputStream;
import java.io.File;


//IMPORTS PDF



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
    //private MovimentacaoDAO moviDAO = new MovimentacaoDAO();
    private VeiculosDAO veiDAO = new VeiculosDAO();

    
    
     //ATUALIZAR AS COLUNAS QUANDO O USUÁRIO ESCOLHE NO COMBOBOX
private void atualizarColunas() {
    String tipo = (String) cbTipoRelatorio.getSelectedItem();

    cbVariavel.setVisible(false);
    cbVariavel.setEnabled(false);

    // Veículos inativos não precisa de ComboBox
    if ("Veículos inativos".equals(tipo)) {
        return; 
    }

    // Despesas de um veículo
    if ("Despesas de um veículo".equals(tipo)) {

        //MOSTRAR CAIXA PARA SELECIONAR O VEICULO
        cbVariavel.setVisible(true);
        cbVariavel.setEnabled(true);

        //LIMPAR ITENS ANTIGOS E PLACEHOLDER
        cbVariavel.removeAllItems();
        cbVariavel.addItem("Selecione um veículo");

        //PEGAR OS IDS DO RELATORIOSDAO
        ArrayList<Veiculos> lista = relDao.listarVeiculos();

        for (Veiculos v : lista) {
            cbVariavel.addItem(String.valueOf(v.getIdVeiculo()));
        }

        return;
    }

    // Meses usados pelos relatórios por mês
    if ("Total geral de despesas da frota no mês".equals(tipo)
            || "Total de gastos com combustível no mês".equals(tipo)) {

        //MOSTRAR CAIXA PARA SELECIONAR MÊS
        cbVariavel.setVisible(true);
        cbVariavel.setEnabled(true);

        //LIMPAR ITENS ANTIGOS E PLACEHOLDER
        cbVariavel.removeAllItems();
        cbVariavel.addItem("Selecione um mês");
        cbVariavel.addItem("01 - Janeiro");
        cbVariavel.addItem("02 - Fevereiro");
        cbVariavel.addItem("03 - Março");
        cbVariavel.addItem("04 - Abril");
        cbVariavel.addItem("05 - Maio");
        cbVariavel.addItem("06 - Junho");
        cbVariavel.addItem("07 - Julho");
        cbVariavel.addItem("08 - Agosto");
        cbVariavel.addItem("09 - Setembro");
        cbVariavel.addItem("10 - Outubro");
        cbVariavel.addItem("11 - Novembro");
        cbVariavel.addItem("12 - Dezembro");

        return;
    }
}

   

      public TelaRelatorio() {
        initComponents();
        
        //QUANDO SELECIONA NA COMBOBOX A TABELA MUDA ?
        cbTipoRelatorio.addActionListener(e -> atualizarColunas());
        
        
        this.setLocationRelativeTo(null);

    }
    
   
    
    private ResultadoRelatorio gerarRelatoriosInativos() {
    try {
        ArrayList<Veiculos> lista = relDao.listarInativos();

        if (lista == null || lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há veículos inativos na frota!");
            return null;
        }

        // COLUNAS
        String[] colunas = { "ID", "Placa", "Marca", "Modelo", "Ano", "Situação" };

        // LINHAS
        ArrayList<String[]> linhas = new ArrayList<>();

        for (Veiculos v : lista) {
            linhas.add(new String[]{
                String.valueOf(v.getIdVeiculo()),
                v.getPlaca(),
                v.getMarca(),
                v.getModelo(),
                String.valueOf(v.getAnoFabricacao()),
                "Inativo"
            });
        }

        return new ResultadoRelatorio(colunas, linhas, null); // sem total

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "ERRO AO GERAR RELATÓRIO: " + e.getMessage());
        return null;
    }
}

    
  private ResultadoRelatorio gerarRelatorioDespesasVeiculo() {
    try {
        String selecionado = (String) cbVariavel.getSelectedItem();

        if (selecionado == null || selecionado.equals("Selecione um veículo")) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo!");
            return null;
        }

        int idVeiculo = Integer.parseInt(selecionado);

        ArrayList<Movimentacao> lista = relDao.despesasVeiculos(idVeiculo);

        if (lista == null || lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Esse veículo não possui despesas cadastradas!");
            return null;
        }

        // COLUNAS
        String[] colunas = { "ID Veículo", "Tipo de Despesa", "Descrição", "Valor" };

        // LINHAS
        ArrayList<String[]> linhas = new ArrayList<>();

        double total = 0;

        for (Movimentacao m : lista) {
            Despesas d = despDAO.buscarPorId(m.getIdDespesa());
            String tipoDespesa = (d != null) ? d.getDescricao() : "Não encontrado";

            total += m.getValor();

            linhas.add(new String[]{
                String.valueOf(m.getIdVeiculo()),
                tipoDespesa,
                m.getDescricao(),
                String.valueOf(m.getValor())
            });
        }

        return new ResultadoRelatorio(colunas, linhas, total);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "ERRO AO GERAR RELATÓRIO: " + e.getMessage());
        return null;
    }
}

    //OBJETO PARA RETORNAR LISTA + TOTAL
public class ResultadoDespesasMes {
    public ArrayList<Movimentacao> lista;
    public double total;

    public ResultadoDespesasMes(ArrayList<Movimentacao> lista, double total) {
        this.lista = lista;
        this.total = total;
    }
}


private ResultadoRelatorio gerarDespesasMes() {
    try {
        // Pegar o mês selecionado
        String selecionado = (String) cbVariavel.getSelectedItem();

        if (selecionado == null || selecionado.equals("Selecione um mês")) {
            JOptionPane.showMessageDialog(this, "Selecione um mês");
            return null;
        }

        int mes = Integer.parseInt(selecionado.substring(0, 2));

        // Buscar todas as movimentações do mês
        ArrayList<Movimentacao> lista = relDao.despesasMes(mes);

        if (lista == null || lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há despesas cadastradas nesse mês!");
            return null;
        }

        // Definir colunas do relatório
        String[] colunas = { "ID Veículo", "Veículo", "Tipo Despesa", "Data", "Valor" };
        ArrayList<String[]> linhas = new ArrayList<>();

        double total = 0;

        for (Movimentacao m : lista) {

            // Tipo despesa
            Despesas d = despDAO.buscarPorId(m.getIdDespesa());
            String tipoDespesa = (d != null) ? d.getDescricao() : "Não encontrado";

            // Veículo
            Veiculos v = relDao.buscarVeiculoPorId(m.getIdVeiculo());
            String modelo = (v != null) ? v.getModelo() : "Não encontrado";

            // Soma
            total += m.getValor();

            // Adicionar linha na lista final
            linhas.add(new String[] {
                String.valueOf(m.getIdVeiculo()),
                modelo,
                tipoDespesa,
                m.getData(),
                String.valueOf(m.getValor())
            });
        }

        return new ResultadoRelatorio(colunas, linhas, total);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao gerar relatório: " + e.getMessage());
        return null;
    }
}


    
    
//    Só despesas do tipo "Combustível", filtradas por mês.

private ResultadoRelatorio gerarDespesasCombustiveis () {
    try {
        // Pega o mês escolhido
        String selecionado = (String) cbVariavel.getSelectedItem();

        if (selecionado == null || selecionado.equals("Selecione um mês")) {
            JOptionPane.showMessageDialog(this, "Selecione um mês!");
            return null;
        }

        int mes = Integer.parseInt(selecionado.substring(0, 2));

        // Busca todas as movimentações do mês
        ArrayList<Movimentacao> lista = relDao.despesasMes(mes);

        if (lista == null || lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há despesas cadastradas nesse mês!");
            return null;
        }

        // COLUNAS DO RELATÓRIO
        String[] colunas = { "ID Veículo", "Veículo", "Tipo Despesa", "Data", "Valor" };
        ArrayList<String[]> linhas = new ArrayList<>();

        double total = 0;

        // Filtra APENAS COMBUSTÍVEL
        for (Movimentacao m : lista) {

            Despesas d = despDAO.buscarPorId(m.getIdDespesa());

            // Se não for Combustível, pula
            if (d == null) {
            continue;
            }

            String desc = d.getDescricao();
            if (desc == null) {
                continue;
            }

            // deixa tudo minúsculo e sem acento na comparação:
            if (!desc.toLowerCase().contains("combust")) {
                continue;
}

            Veiculos v = relDao.buscarVeiculoPorId(m.getIdVeiculo());
            String modelo = (v != null) ? v.getModelo() : "Não encontrado";

            total += m.getValor();

            linhas.add(new String[]{
                String.valueOf(m.getIdVeiculo()),
                modelo,
                d.getDescricao(),
                m.getData(),
                String.valueOf(m.getValor())
            });
        }

        if (linhas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há despesas de combustível nesse mês!");
            return null;
        }

        return new ResultadoRelatorio(colunas, linhas, total);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao gerar relatório de combustível: " + e.getMessage());
        return null;
    }
}

private ResultadoRelatorio gerarIpvaAno() {
    try {
        String anoStr = JOptionPane.showInputDialog(this, "Informe o ano (ex: 2024):");
        if (anoStr == null || anoStr.trim().isEmpty()) {
            return null;
        }

        int ano = Integer.parseInt(anoStr.trim());

            ArrayList<Movimentacao> lista = relDao.ipvaAno(ano);

        if (lista == null || lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há IPVA cadastrado para esse ano!");
            return null;
        }

        String[] colunas = { "ID Veículo", "Veículo", "Ano", "Valor" };
        ArrayList<String[]> linhas = new ArrayList<>();
        double total = 0;

        for (Movimentacao m : lista) {
            Veiculos v = relDao.buscarVeiculoPorId(m.getIdVeiculo());
            String modelo = (v != null) ? v.getModelo() : "Não encontrado";

            total += m.getValor();

            linhas.add(new String[] {
                String.valueOf(m.getIdVeiculo()),
                modelo,
                String.valueOf(ano),
                String.valueOf(m.getValor())
            });
        }

        return new ResultadoRelatorio(colunas, linhas, total);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao gerar relatório de IPVA: " + e.getMessage());
        return null;
    }
}

private ResultadoRelatorio gerarMultasVeiculoAno() {
    try {
        String selecionado = (String) cbVariavel.getSelectedItem();

        if (selecionado == null || selecionado.equals("Selecione um veículo")) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo!");
            return null;
        }

        int idVeiculo = Integer.parseInt(selecionado);

        String anoStr = JOptionPane.showInputDialog(this, "Informe o ano (ex: 2024):");
        if (anoStr == null || anoStr.trim().isEmpty()) {
            return null;
        }

        int ano = Integer.parseInt(anoStr.trim());

        ArrayList<Movimentacao> lista = relDao.multasVeiculoAno(idVeiculo, ano);

        if (lista == null || lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há multas pagas para esse veículo nesse ano!");
            return null;
        }

        String[] colunas = { "ID Veículo", "Veículo", "Data", "Descrição", "Valor" };
        ArrayList<String[]> linhas = new ArrayList<>();
        double total = 0;

        Veiculos v = relDao.buscarVeiculoPorId(idVeiculo);
        String modelo = (v != null) ? v.getModelo() : "Não encontrado";

        for (Movimentacao m : lista) {
            total += m.getValor();

            linhas.add(new String[] {
                String.valueOf(idVeiculo),
                modelo,
                m.getData(),
                m.getDescricao(),
                String.valueOf(m.getValor())
            });
        }

        return new ResultadoRelatorio(colunas, linhas, total);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao gerar relatório de multas: " + e.getMessage());
        return null;
    }
}



    
   //NOVO METODO PDF
private void gerarPdf(String caminhoArquivo,
                      String tituloRelatorio,
                      String[] colunas,
                      ArrayList<String[]> linhas,
                      Double totalOpcional) {
    try {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
        document.open();
        
        //BORDA VERMELHA DECORAR
        
        Rectangle rect = new Rectangle(document.getPageSize());
        rect.setBorder(Rectangle.BOX);
        rect.setBorderColor(BaseColor.RED);
        rect.setBorderWidth(20);
        document.add(rect);


        // TÍTULO
        Font tituloFont = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD);
        Paragraph titulo = new Paragraph(tituloRelatorio, tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Paragraph(" "));

        // TABELA
        PdfPTable table = new PdfPTable(colunas.length);
        table.setWidthPercentage(100);

        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font cellFont   = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

        // Cabeçalho
        for (String col : colunas) {
            PdfPCell cell = new PdfPCell(new Paragraph(col, headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        // Linhas
        for (String[] linha : linhas) {
            for (String valor : linha) {
                table.addCell(new Paragraph(valor, cellFont));
            }
        }

        document.add(table);

        // Mostra TOTAL caso exista
        if (totalOpcional != null) {
            document.add(new Paragraph(" "));
            Font totalFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            document.add(new Paragraph("Total: R$ " + totalOpcional, totalFont));
        }

        document.close();

        JOptionPane.showMessageDialog(this, "PDF gerado com sucesso!");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao gerar PDF: " + e.getMessage());
    }
}

    
    
        
    
    class ResultadoRelatorio {
    String[] colunas;
    ArrayList<String[]> linhas;
    Double total; // pode ser null

    public ResultadoRelatorio(String[] colunas, ArrayList<String[]> linhas, Double total) {
        this.colunas = colunas;
        this.linhas = linhas;
        this.total = total;
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
        cbVariavel = new javax.swing.JComboBox<>();

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

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setText("GERAR RELATÓRIO");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cbVariavel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbVariavel.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(750, Short.MAX_VALUE)
                .addComponent(jButtonVoltar)
                .addGap(36, 36, 36))
            .addGroup(layout.createSequentialGroup()
                .addGap(246, 246, 246)
                .addComponent(jLabel2)
                .addGap(28, 28, 28)
                .addComponent(cbTipoRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(362, 362, 362)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(376, 376, 376)
                        .addComponent(cbVariavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(302, 302, 302)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addComponent(cbVariavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
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
        // Criar pasta base (genérica para qualquer usuário)
    String pastaRelatorios = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Relatorios_Veiculos";
    new File(pastaRelatorios).mkdirs(); // cria se não existir

    //BOTÃO GERAR RELATÓRIO
    String tipo = (String) cbTipoRelatorio.getSelectedItem();

    if ("Veículos inativos".equals(tipo)) {

        ResultadoRelatorio res = gerarRelatoriosInativos();

        if (res == null) return;

        String caminho = pastaRelatorios + File.separator + "veiculos_inativos.pdf";
        gerarPdf(
            caminho,
            "Veículos Inativos",
            res.colunas,
            res.linhas,
            null // não tem total
        );
    }

    if ("Despesas de um veículo".equals(tipo)) {

        ResultadoRelatorio res = gerarRelatorioDespesasVeiculo();

        if (res == null) return;

        String caminho = pastaRelatorios + File.separator + "despesas_veiculo.pdf";
        gerarPdf(
            caminho,
            "Despesas do Veículo",
            res.colunas,
            res.linhas,
            res.total
        );
    }

    if ("Total geral de despesas da frota no mês".equals(tipo)) {

        ResultadoRelatorio res = gerarDespesasMes();

        if (res == null) return;

        String caminho = pastaRelatorios + File.separator + "despesas_mes.pdf";
        gerarPdf(
            caminho,
            "Despesas da Frota no Mês",
            res.colunas,
            res.linhas,
            res.total
        );
    }

    if ("Total de gastos com combustível no mês".equals(tipo)) {

        ResultadoRelatorio res = gerarDespesasCombustiveis();

        if (res == null) return;

        String caminho = pastaRelatorios + File.separator + "combustivel_mes.pdf";
        gerarPdf(
            caminho,
            "Gastos com Combustível no Mês",
            res.colunas,
            res.linhas,
            res.total
        );
    }
    
    if ("Total de IPVA da frota no ano".equals(tipo)) {

    ResultadoRelatorio res = gerarIpvaAno();

    if (res == null) return;

    String caminho = pastaRelatorios + File.separator + "ipva_ano.pdf";
    gerarPdf(
        caminho,
        "Total de IPVA da Frota no Ano",
        res.colunas,
        res.linhas,
        res.total
    );
}
    
    if ("Multas pagas por veículo no ano".equals(tipo)) {

    ResultadoRelatorio res = gerarMultasVeiculoAno();

    if (res == null) return;

    String caminho = pastaRelatorios + File.separator + "multas_veiculo_ano.pdf";
    gerarPdf(
        caminho,
        "Multas Pagas por Veículo no Ano",
        res.colunas,
        res.linhas,
        res.total
    );
}


   
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbTipoRelatorio;
    private javax.swing.JComboBox<String> cbVariavel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}


package visao;

import controle.ControleRelatorios;
import java.util.ArrayList;
import modelos.classes.Veiculos;
import modelos.classes.Movimentacao;
import modelos.classes.Despesas;
import persistencia.DespesasDAO;

// IMPORTS PDF 
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
    //linha
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.Chunk;




import javax.swing.JOptionPane;
import java.io.FileOutputStream;
import java.io.File;


public class TelaRelatorio extends javax.swing.JFrame {
    
    private ControleRelatorios controle = new ControleRelatorios();
    private DespesasDAO despDAO = new DespesasDAO();
    
    public TelaRelatorio() {
        initComponents();
        jTextPaneDescricao.setEditable(false);
        jTextPaneDescricao.setBorder(null); 
        
        // QUANDO SELECIONA NA COMBOBOX A TABELA MUDA
        cbTipoRelatorio.addActionListener(e -> atualizarColunas());
        atualizarColunas();
        
        this.setLocationRelativeTo(null);
    }
    
    private void atualizarColunas() {
        String tipo = (String) cbTipoRelatorio.getSelectedItem();

        cbVariavel.setVisible(false);
        cbVariavel.setEnabled(false);
        
        

        if ("Veículos inativos".equals(tipo)) {
            return;
        }

        if ("Despesas de um veículo".equals(tipo) 
            || "Multas pagas por veículo no ano".equals(tipo)) {

            cbVariavel.setVisible(true);
            cbVariavel.setEnabled(true);

            cbVariavel.removeAllItems();
            cbVariavel.addItem("Selecione um veículo");
            
            

            try {
                
                ArrayList<Veiculos> lista = controle.listarVeiculosParaCombo();
                for (Veiculos v : lista) {
                    cbVariavel.addItem(String.valueOf(v.getIdVeiculo() + " - "+ v.getModelo()));
                }
                
                
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,     
                    "Erro ao carregar veículos: " + e.getMessage());
            }

            return;
        }

        // Meses usados pelos relatórios por mês
        if ("Total geral de despesas da frota no mês".equals(tipo)
                || "Total de gastos com combustível no mês".equals(tipo)) {
            cbVariavel.setVisible(true);
            cbVariavel.setEnabled(true);

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
    
    // Método para gerar PDF 
    private void gerarPdf(String caminhoArquivo,
                          String tituloRelatorio,
                          String[] colunas,
                          ArrayList<String[]> linhas,
                          Double totalOpcional) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
            document.open();
            
            
            //BORDA
            Rectangle rect = new Rectangle(document.getPageSize());
            rect.setBorder(Rectangle.BOX);
            rect.setBorderColor(BaseColor.RED);
            rect.setBorderWidth(20);
            document.add(rect);
            
             //IMAGEM
            Image img = Image.getInstance("src/icons/logoICON.jpg");
            img.scaleToFit(120,120);
            img.setAlignment(Element.ALIGN_CENTER);
            
            document.add(img);
            //document.add(new Paragraph(" "));
            
            //LINHA ANTES DA TABELA
            LineSeparator linhaIcon = new LineSeparator();
            linhaIcon.setLineColor(BaseColor.BLACK);
            document.add(new Chunk(linhaIcon));
            

            //TÍTULO
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD);
            Paragraph titulo = new Paragraph(tituloRelatorio, tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph(" "));

            //TABELA
            PdfPTable table = new PdfPTable(colunas.length);
            table.setWidthPercentage(100);

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font cellFont   = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            //Cabeçalho
            for (String col : colunas) {
                PdfPCell cell = new PdfPCell(new Paragraph(col, headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            //Linhas
            for (String[] linha : linhas) {
                for (String valor : linha) {
                    table.addCell(new Paragraph(valor, cellFont));
                }
            }

            document.add(table);

            //Mostra TOTAL caso exista
            if (totalOpcional != null) {
                document.add(new Paragraph("" ));
             
                Font totalFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
                
                //formatação do valor
                String totalFormatado = String.format("%.2f", totalOpcional);
                
                
                Paragraph totalTexto = new Paragraph("Total: R$ " + totalFormatado, totalFont);
                totalTexto.setAlignment(Element.ALIGN_CENTER);
                document.add(new Paragraph(" "));
                document.add(totalTexto);
                  
            }
            
            //LINHA DEPOIS
            LineSeparator linha2 = new LineSeparator();
            linha2.setLineColor(BaseColor.BLACK);
            document.add(new Chunk(linhaIcon));
            
 
            

            document.close();
            JOptionPane.showMessageDialog(this, "PDF gerado com sucesso em: " + caminhoArquivo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar PDF: " + e.getMessage());
        }
    }
    
    //VEICULOS INATIVOS
    private ResultadoFormatado prepararRelatorioInativos() {
        try {
            ArrayList<Veiculos> lista = controle.gerarRelatorioInativos();
            
            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Não há veículos inativos na frota!");
                return null;
            }

            String[] colunas = { "ID", "Placa", "Marca", "Modelo", "Ano", "Situação" };
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

            return new ResultadoFormatado(colunas, linhas, null);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar relatório: " + e.getMessage());
            return null;
        }
    }
    
    //DESPESAS POR VEICULO
    private ResultadoFormatado prepararRelatorioDespesasVeiculo(int idVeiculo) {
        try {
            ControleRelatorios.ResultadoRelatorioVeiculo resultado = 
                controle.gerarRelatorioDespesasVeiculo(idVeiculo);
            
            
            
            if (resultado.movimentacoes.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Esse veículo não possui despesas cadastradas!");
                return null;
            }

            String[] colunas = { "ID Veículo", "Tipo de Despesa", "Descrição", "Valor" };
            ArrayList<String[]> linhas = new ArrayList<>();

            for (Movimentacao m : resultado.movimentacoes) {
                Despesas d = despDAO.buscarPorId(m.getIdDespesa());
                String tipoDespesa = (d != null) ? d.getDescricao() : "Não encontrado";

                linhas.add(new String[]{
                    String.valueOf(m.getIdVeiculo()),
                    tipoDespesa,
                    m.getDescricao(),
                    String.valueOf("R$ "+m.getValor())
                });
            }

            return new ResultadoFormatado(colunas, linhas, resultado.total);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar relatório: " + e.getMessage());
            return null;
        }
    }
    
    //CLASSE AUXILIAR PARA OS RESULTADOS FORMATADOS
    class ResultadoFormatado {
        String[] colunas;
        ArrayList<String[]> linhas;
        Double total;

        public ResultadoFormatado(String[] colunas, ArrayList<String[]> linhas, Double total) {
            this.colunas = colunas;
            this.linhas = linhas;
            this.total = total;
        }
    }
    
    //DESPESAS POR MES
private ResultadoFormatado prepararRelatorioDespesasMes(int mes) {
    try {
        ControleRelatorios.ResultadoRelatorioPeriodo resultado = 
            controle.gerarRelatorioDespesasMes(mes);
        
        if (resultado.movimentacoes.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Não há despesas cadastradas para este mês!");
            return null;
        }

        String[] colunas = { "ID Veículo", "Veículo", "Tipo Despesa", "Data", "Valor" };
        ArrayList<String[]> linhas = new ArrayList<>();

        for (Movimentacao m : resultado.movimentacoes) {
            // Tipo despesa
            Despesas d = despDAO.buscarPorId(m.getIdDespesa());
            String tipoDespesa = (d != null) ? d.getDescricao() : "Não encontrado";

            
            Veiculos v = null;
            try {
                v = controle.buscarVeiculoPorId(m.getIdVeiculo());
            } catch (Exception e) {
               
            }
            String modelo = (v != null) ? v.getModelo() : "Não encontrado";

            linhas.add(new String[]{
                String.valueOf(m.getIdVeiculo()),
                modelo,
                tipoDespesa,
                m.getData(),
                String.valueOf("R$ "+m.getValor())
            });
        }

        return new ResultadoFormatado(colunas, linhas, resultado.total);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao gerar relatório: " + e.getMessage());
        return null;
    }
}

//COMBUSTIVEL POR MES
private ResultadoFormatado prepararRelatorioCombustivelMes(int mes) {
    try {
        ControleRelatorios.ResultadoRelatorioPeriodo resultado = 
            controle.gerarRelatorioCombustivelMes(mes);
        
        if (resultado.movimentacoes.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Não há despesas de combustível para este mês!");
            return null;
        }

        String[] colunas = { "ID Veículo", "Veículo", "Data", "Descrição", "Valor" };
        ArrayList<String[]> linhas = new ArrayList<>();

        for (Movimentacao m : resultado.movimentacoes) {
            // Veículo
            Veiculos v = null;
            try {
                v = controle.buscarVeiculoPorId(m.getIdVeiculo());
            } catch (Exception e) {
                // Se não encontrar, continua
            }
            String modelo = (v != null) ? v.getModelo() : "Não encontrado";

            linhas.add(new String[]{
                String.valueOf(m.getIdVeiculo()),
                modelo,
                m.getData(),
                m.getDescricao(),
                String.valueOf("R$ "+m.getValor())
            });
        }

        return new ResultadoFormatado(colunas, linhas, resultado.total);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao gerar relatório: " + e.getMessage());
        return null;
    }
}

//IPVA POR ANO
private ResultadoFormatado prepararRelatorioIpvaAno(int ano) {
    try {
        ControleRelatorios.ResultadoRelatorioPeriodo resultado = 
            controle.gerarRelatorioIpvaAno(ano);
        
        if (resultado.movimentacoes.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Não há IPVA cadastrado para este ano!");
            return null;
        }

        String[] colunas = { "ID Veículo", "Veículo", "Ano", "Data Pagamento", "Valor" };
        ArrayList<String[]> linhas = new ArrayList<>();

        for (Movimentacao m : resultado.movimentacoes) {
            // Veículo
            Veiculos v = null;
            try {
                v = controle.buscarVeiculoPorId(m.getIdVeiculo());
            } catch (Exception e) {
                // Se não encontrar, continua
            }
            String modelo = (v != null) ? v.getModelo() : "Não encontrado";

            linhas.add(new String[]{
                String.valueOf(m.getIdVeiculo()),
                modelo,
                String.valueOf(ano),
                m.getData(),
                String.valueOf("R$ "+m.getValor())
            });
        }

        return new ResultadoFormatado(colunas, linhas, resultado.total);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao gerar relatório: " + e.getMessage());
        return null;
    }
}

//MULTAS POR VEICULO E ANO
private ResultadoFormatado prepararRelatorioMultasVeiculoAno(int idVeiculo, int ano) {
    try {
        ControleRelatorios.ResultadoRelatorioVeiculoPeriodo resultado = 
            controle.gerarRelatorioMultasVeiculoAno(idVeiculo, ano);
        
        if (resultado.movimentacoes.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Não há multas pagas para este veículo neste ano!");
            return null;
        }

        String[] colunas = { "ID Veículo", "Veículo", "Data", "Descrição", "Valor" };
        ArrayList<String[]> linhas = new ArrayList<>();

        // Buscar veículo uma vez
        Veiculos v = null;
        try {
            v = controle.buscarVeiculoPorId(idVeiculo);
        } catch (Exception e) {
            // Se não encontrar, continua
        }
        String modelo = (v != null) ? v.getModelo() : "Não encontrado";

        for (Movimentacao m : resultado.movimentacoes) {
            linhas.add(new String[]{
                String.valueOf(idVeiculo),
                modelo,
                m.getData(),
                m.getDescricao(),
                String.valueOf("R$ "+m.getValor())
            });
        }

        return new ResultadoFormatado(colunas, linhas, resultado.total);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao gerar relatório: " + e.getMessage());
        return null;
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

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jButtonVoltar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPaneDescricao = new javax.swing.JTextPane();
        cbTipoRelatorio = new javax.swing.JComboBox<>();
        cbVariavel = new javax.swing.JComboBox<>();
        jButtonGerarRelatorio = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JANELA @");

        jPanel1.setBackground(new java.awt.Color(234, 61, 70));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ICONLOGO.png"))); // NOI18N

        jButtonVoltar.setBackground(new java.awt.Color(102, 102, 102));
        jButtonVoltar.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jButtonVoltar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/VOLTAR.png"))); // NOI18N
        jButtonVoltar.setText("Voltar");
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jButtonVoltar))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonVoltar)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(132, 124, 124));

        jLabel3.setFont(new java.awt.Font("Arial Black", 1, 45)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Relatórios");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SELECIONE O RELATÓRIO DESEJADO:");

        jPanel3.setBackground(new java.awt.Color(163, 162, 164));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/DESPESA.png"))); // NOI18N

        jTextPaneDescricao.setEditable(false);
        jTextPaneDescricao.setBackground(new java.awt.Color(163, 162, 164));
        jTextPaneDescricao.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jTextPaneDescricao.setForeground(new java.awt.Color(255, 255, 255));
        jTextPaneDescricao.setText("Esta tela tem como objetivo apresentar o controle das movimentações financeiras registradas no sistema, permitindo a análise de despesas associadas aos veículos cadastrados.\n\nO usuário pode consultar as movimentações por período (mês e ano), facilitando o acompanhamento de gastos, organização administrativa e apoio à tomada de decisões.");
        jScrollPane1.setViewportView(jTextPaneDescricao);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        cbTipoRelatorio.setBackground(new java.awt.Color(163, 162, 164));
        cbTipoRelatorio.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbTipoRelatorio.setForeground(new java.awt.Color(255, 255, 255));
        cbTipoRelatorio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Despesas de um veículo", "Total geral de despesas da frota no mês", "Total de gastos com combustível no mês", "Total de IPVA da frota no ano", "Veículos inativos", "Multas pagas por veículo no ano" }));

        cbVariavel.setBackground(new java.awt.Color(163, 162, 164));
        cbVariavel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbVariavel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbVariavel.setEnabled(false);
        cbVariavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbVariavelActionPerformed(evt);
            }
        });

        jButtonGerarRelatorio.setBackground(new java.awt.Color(102, 102, 102));
        jButtonGerarRelatorio.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jButtonGerarRelatorio.setForeground(new java.awt.Color(255, 255, 255));
        jButtonGerarRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/relatorio.png"))); // NOI18N
        jButtonGerarRelatorio.setText("GERAR RELATÓRIO");
        jButtonGerarRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGerarRelatorioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(93, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(111, 111, 111))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButtonGerarRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(90, 90, 90))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(121, 121, 121)
                                .addComponent(cbTipoRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(194, 194, 194)
                                .addComponent(cbVariavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(cbTipoRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbVariavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(jButtonGerarRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        // TODO add your handling code here:

        MenuPrincipal Relatorio = new MenuPrincipal();
        Relatorio.setVisible(true);
        dispose();

    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jButtonGerarRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGerarRelatorioActionPerformed
        String pastaRelatorios = System.getProperty("user.home") + File.separator + 
                            "Documents" + File.separator + "Relatorios_Veiculos";
    new File(pastaRelatorios).mkdirs();

    String tipo = (String) cbTipoRelatorio.getSelectedItem();
    ResultadoFormatado resultado = null;
    String nomeArquivo = "";
    String tituloRelatorio = "";

    try {
        //1 VEÍCULOS INATIVOS
        if ("Veículos inativos".equals(tipo)) {
            resultado = prepararRelatorioInativos();
            nomeArquivo = "veiculos_inativos.pdf";
            tituloRelatorio = "Veículos Inativos";
            
        //2 DESPESAS DE UM VEÍCULO
        } else if ("Despesas de um veículo".equals(tipo)) {
            
            //veiculo selecionado pelo usuario (colocar split para separar ID de Marca)
            
            String selecionado = (String) cbVariavel.getSelectedItem();

            
            
            if (selecionado == null || selecionado.equals("Selecione um veículo")) {
                JOptionPane.showMessageDialog(this, "Selecione um veículo!");
                return;
            }
            
            //pegar so o id !!
            String[] partes = selecionado.split(" - ");
            String selecId = partes[0];
            String modelo = partes[1];
           
            int idVeiculo = Integer.parseInt(selecId);
            
            
            resultado = prepararRelatorioDespesasVeiculo(idVeiculo);
            nomeArquivo = "despesas_veiculo_ID" + idVeiculo + "_" + modelo + ".pdf";
            tituloRelatorio = "Despesas do Veículo: " + modelo;
            
        //3 TOTAL GERAL DE DESPESAS DA FROTA NO MÊS
        } else if ("Total geral de despesas da frota no mês".equals(tipo)) {
            String selecionado = (String) cbVariavel.getSelectedItem();
            if (selecionado == null || selecionado.equals("Selecione um mês")) {
                JOptionPane.showMessageDialog(this, "Selecione um mês!");
                return;
            }
            int mes = Integer.parseInt(selecionado.substring(0, 2));
            resultado = prepararRelatorioDespesasMes(mes);
            nomeArquivo = "despesas_frota_mes_" + String.format("%02d", mes) + ".pdf";
            tituloRelatorio = "Despesas da Frota - " + selecionado.substring(4);
            
        //4 TOTAL DE GASTOS COM COMBUSTÍVEL NO MÊS
        } else if ("Total de gastos com combustível no mês".equals(tipo)) {
            String selecionado = (String) cbVariavel.getSelectedItem();
            if (selecionado == null || selecionado.equals("Selecione um mês")) {
                JOptionPane.showMessageDialog(this, "Selecione um mês!");
                return;
            }
            int mes = Integer.parseInt(selecionado.substring(0, 2));
            resultado = prepararRelatorioCombustivelMes(mes);
            nomeArquivo = "combustivel_mes_" + String.format("%02d", mes) + ".pdf";
            tituloRelatorio = "Gastos com Combustível - " + selecionado.substring(4);
            
        //5 TOTAL DE IPVA DA FROTA NO ANO
        } else if ("Total de IPVA da frota no ano".equals(tipo)) {
            String anoStr = JOptionPane.showInputDialog(this, "Informe o ano (ex: 2024):");
            if (anoStr == null || anoStr.trim().isEmpty()) {
                return;
            }
            int ano = Integer.parseInt(anoStr.trim());
            resultado = prepararRelatorioIpvaAno(ano);
            nomeArquivo = "ipva_ano_" + ano + ".pdf";
            tituloRelatorio = "IPVA da Frota - Ano " + ano;
            
        //6 MULTAS PAGAS POR VEÍCULO NO ANO
        } else if ("Multas pagas por veículo no ano".equals(tipo)) {
            String selecionado = (String) cbVariavel.getSelectedItem();
            
            
            if (selecionado == null || selecionado.equals("Selecione um veículo")) {
                JOptionPane.showMessageDialog(this, "Selecione um veículo!");
                return;
            }
            
            
            //pegar so o id !!
            String[] partes = selecionado.split(" - ");
            String selecId = partes[0];
            String modelo = partes[1];
                       
            int idVeiculo = Integer.parseInt(selecId);
            
            
            String anoStr = JOptionPane.showInputDialog(this, "Informe o ano (ex: 2024):");
            if (anoStr == null || anoStr.trim().isEmpty()) {
                return;
            }
            
            int ano = Integer.parseInt(anoStr.trim());
            
            
            
            resultado = prepararRelatorioMultasVeiculoAno(idVeiculo, ano);
            nomeArquivo = "multas_veiculo_" + idVeiculo + "_ano_" + ano + ".pdf";
            tituloRelatorio = "Multas do Veículo "+ modelo + " - Ano " + ano;
            
        } else {
            JOptionPane.showMessageDialog(this, "Tipo de relatório não implementado!");
            return;
        }
        
        // Gerar PDF se houver resultado
        if (resultado != null) {
            String caminho = pastaRelatorios + File.separator + nomeArquivo;
            gerarPdf(caminho, tituloRelatorio, 
                    resultado.colunas, resultado.linhas, resultado.total);
        } else {
            
        }
        
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, 
            "Erro de formato numérico: " + e.getMessage());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Erro ao processar relatório: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_jButtonGerarRelatorioActionPerformed

    private void cbVariavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbVariavelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbVariavelActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbTipoRelatorio;
    private javax.swing.JComboBox<String> cbVariavel;
    private javax.swing.JButton jButtonGerarRelatorio;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPaneDescricao;
    // End of variables declaration//GEN-END:variables
}


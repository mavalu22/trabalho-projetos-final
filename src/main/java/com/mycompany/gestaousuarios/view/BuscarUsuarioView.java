package com.mycompany.gestaousuarios.view;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class BuscarUsuarioView extends javax.swing.JInternalFrame {

    /**
     * Creates new form BuscarUsuarioView
     */
    public BuscarUsuarioView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        srcPainel = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        txtNome = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnVisualizar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setIconifiable(true);
        setTitle("Buscar usuários");

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "id", "nome", "username", "email", "isAdmin", "isAutorizado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        srcPainel.setViewportView(tblUsuarios);
        if (tblUsuarios.getColumnModel().getColumnCount() > 0) {
            tblUsuarios.getColumnModel().getColumn(0).setResizable(false);
            tblUsuarios.getColumnModel().getColumn(0).setPreferredWidth(7);
            tblUsuarios.getColumnModel().getColumn(4).setResizable(false);
            tblUsuarios.getColumnModel().getColumn(4).setPreferredWidth(10);
            tblUsuarios.getColumnModel().getColumn(5).setResizable(false);
            tblUsuarios.getColumnModel().getColumn(5).setPreferredWidth(10);
        }

        jLabel1.setText("Nome:");

        btnVisualizar.setText("Visualizar");

        btnBuscar.setText("Buscar");

        btnAtualizar.setText("Atualizar");

        btnCancelar.setText("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(srcPainel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVisualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(srcPainel, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVisualizar)
                    .addComponent(btnAtualizar)
                    .addComponent(btnCancelar))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public JTextField getTxtNome() {
        return txtNome;
    }

    public JScrollPane getSrcPainel() {
        return srcPainel;
    }

    public JButton getBtnVisualizar() {
        return btnVisualizar;
    }

    public JButton getBtnAtualizar() {
        return btnAtualizar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnVisualizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane srcPainel;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}

package com.mycompany.gestaousuarios.presenter;

import com.mycompany.gestaousuarios.model.Usuario;
import com.mycompany.gestaousuarios.persistencia.service.usuario.IUsuarioService;
import com.mycompany.gestaousuarios.persistencia.service.usuario.UsuarioService;
import com.mycompany.gestaousuarios.view.BuscarUsuarioView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;


public class BuscarUsuarioPresenter implements ManterUsuarioObserver{
    private BuscarUsuarioView view;
    private IUsuarioService usuarioService;
    private DefaultTableModel tmUsuarios;
    private List<Usuario> usuarios;
    private List<VisualizarUsuarioObserver> observers;
    private List<SubJanelaObserver> subJanelaObservers;
    
    public BuscarUsuarioPresenter() {
        view = new BuscarUsuarioView();
        observers = new ArrayList<>();
        subJanelaObservers = new ArrayList<>();
        initServices();
        atualizarTabela();
        initListeners();
    }
    
    private String lerCampoBusca(){     
         return this.view.getTxtNome().getText();
    }
    
    private void atualizarTabelaFiltrada(String nome){
        try {
            usuarios = usuarioService.buscarPorNOme(nome);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view,
                    "Erro ao listar os usuários.\n\n"
                    + ex.getMessage(),
                    "ERRO",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        initTabela();
        popularTabela();
    }
    
    public void atualizarTabela(){
        try {
            usuarios = usuarioService.buscarTodos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view,
                    "Erro ao listar os usuários.\n\n"
                    + ex.getMessage(),
                    "ERRO",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        initTabela();
        popularTabela();
    }
    
    private void initServices(){
        usuarioService = new UsuarioService();
    }

    @Override
    public void atualizarUsuario() {
        atualizarTabela();
    }
    
    private void initListeners(){
        view.getBtnBuscar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = lerCampoBusca();
                atualizarTabelaFiltrada(nome);
            }
        });
        
        view.getBtnVisualizar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizarDetalhesUsuarios();
            }
            
        });
        
        view.getBtnAtualizar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getTxtNome().setText("");
                atualizarTabela();
            }
        });
        
        view.getBtnCancelar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                fechar();
            }
        });
    }
   
    private void limpaTabela() {
        int rowCount = tmUsuarios.getRowCount();
        if (rowCount > 0) {
            for (int i = rowCount - 1; i >= 0; i--) {
                tmUsuarios.removeRow(i);
            }
        }
    }

    private void popularTabela() {
        limpaTabela();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        if(!usuarios.isEmpty()){
            ListIterator<Usuario> iterator = this.usuarios.listIterator();
            while (iterator.hasNext()) {
                Usuario usuario = iterator.next();
                tmUsuarios.addRow(new Object[]{
                    usuario.getId(),
                    usuario.getLogin(),
                    usuario.getDataModificacao().format(formatter),
                    usuario.getDataCadastro().format(formatter),
                    usuario.isAdmin(),
                    usuario.isAutorizado()
                });
            }
        }
    }
    
    private void initTabela() { 
        JTable tabela = view.getTblUsuarios();
        tmUsuarios = new DefaultTableModel(
                new Object[][]{},
                new String[]{"id","username", "data modificação","data cadastro", "admin", "autorizado"}
        ){
            Class[] types = new Class [] {
                java.lang.Long.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.Boolean.class, 
                java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tmUsuarios.setNumRows(0);

        tabela.setModel(tmUsuarios);

    }

    public BuscarUsuarioView getView() {
        return view;
    }
    
    private void visualizarDetalhesUsuarios() {
        JTable tabela = view.getTblUsuarios();
        int linha = tabela.getSelectedRow();
        Usuario usuario;
        try {
            Object id = tabela.getModel().getValueAt(linha, 0);
            usuario = usuarioService.buscarPorID(Long.parseLong(id.toString()));
            for(VisualizarUsuarioObserver observer: observers)
                observer.visualizarUsuario(usuario);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view,
                    "Erro ao buscar usuario.\n\n"
                    + ex.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);
        } catch(ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(view,
                    "Você deve selecionar um usuario.\n\n",
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void subscribe(VisualizarUsuarioObserver observer){
        this.observers.add(observer);
    }
    
    public void subscribe(SubJanelaObserver observer){
        this.subJanelaObservers.add(observer);
    }
    
    public void fechar(){
        for(SubJanelaObserver o : subJanelaObservers)
            o.fecharJanela();
        
        view.dispose();
    }
}

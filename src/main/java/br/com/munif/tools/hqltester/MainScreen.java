package br.com.munif.tools.hqltester;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class MainScreen extends JFrame implements ActionListener {

    private EntityManagerFactory emf;
    private JTextField tfHQL;
    private JButton btConsulta;
    private JTable jTable;
    private JPanel pannel;

    public MainScreen() {
        setTitle("HQLTester");
        pannel=new JPanel();
        pannel.setLayout(new FlowLayout());
        add(pannel,BorderLayout.BEFORE_FIRST_LINE);
        pannel.add(new JLabel("HQL"));
        tfHQL = new JTextField(50);
        pannel.add(tfHQL);
        btConsulta = new JButton("Consulta");
        btConsulta.addActionListener(this);
        pannel.add(btConsulta);
        jTable = new JTable();
        JScrollPane jScrollPane = new JScrollPane(jTable);
        add(jScrollPane);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String... args) {
        MainScreen mainScreen = new MainScreen();
        mainScreen.emf = MunifHibernateTools.emf();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btConsulta) {
            consulta(tfHQL.getText());
            resizeColumnWidth(jTable);
            pack();
        }
    }

    private void consulta(String hql) {
        EntityManager em = emf.createEntityManager();
        List resultList = em.createQuery(hql).getResultList();
        jTable.setModel(new GumgaTableModel(resultList));
        for (Object obj : resultList) {
            System.out.println(obj.getClass() + ":" + obj.toString());
        }
        em.close();
    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300) {
                width = 300;
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

}

package br.com.munif.tools.hqltester;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class GumgaTableModel implements TableModel {

    private final List list;
    private List<Field> fields;

    public GumgaTableModel(List resultList) {
        this.list = resultList;
        fields = new ArrayList<>();
        for (Object obj : resultList) {
            for (Field field : getTodosAtributosNaoEstaticos(obj.getClass())) {
                if (!fields.contains(field)) {
                    fields.add(field);
                }
            }
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return fields.get(columnIndex).getType();

    }

    @Override
    public int getColumnCount() {
        return fields.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return fields.get(columnIndex).getName();
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Field f = fields.get(columnIndex);
        f.setAccessible(true);
        try {
            return f.get(list.get(rowIndex));
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    public static List<Field> getTodosAtributosNaoEstaticos(Class classe) throws SecurityException {
        List<Field> aRetornar = new ArrayList<>();
        List<Field> estaticos = new ArrayList<>();
        if (!classe.getSuperclass().equals(Object.class)) {
            aRetornar.addAll(getTodosAtributosNaoEstaticos(classe.getSuperclass()));
        }
        aRetornar.addAll(Arrays.asList(classe.getDeclaredFields()));
        for (Field f : aRetornar) {
            if (Modifier.isStatic(f.getModifiers())) {
                estaticos.add(f);
            }
        }
        aRetornar.removeAll(estaticos);
        return aRetornar;
    }

}

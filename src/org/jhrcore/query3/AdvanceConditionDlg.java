/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AdvanceConditionDlg.java
 *
 * Created on 2010-4-18, 15:57:32
 */
package org.jhrcore.query3;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JList;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.JListBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jhrcore.client.UserContext;
import org.jhrcore.comm.CodeManager;
import org.jhrcore.entity.Code;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.ui.FormulaEditorPanel;
import org.jhrcore.ui.HrTextPane;
import org.jhrcore.ui.SearchListFieldDialog;
import org.jhrcore.ui.listener.IPickFormulaEditorListener;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author Administrator
 */
public class AdvanceConditionDlg extends javax.swing.JDialog {

    private FormulaEditorPanel pnlEditor = new FormulaEditorPanel();
    private HrTextPane jtaFormulaText = null;
    private JList list_funs = new JList();
    private JComboBoxBinding jcomboBoxBindingEntity;
    private JListBinding jListBinding_Field = null;
    private List<TempEntity> listTempEntity = new ArrayList<TempEntity>();
    private boolean click_ok = false;
    private String queryEntity;
    private Hashtable<String, String> ht_funs = new Hashtable<String, String>();

    public boolean isClick_ok() {
        return click_ok;
    }

    /** Creates new form AdvanceConditionDlg */
    public AdvanceConditionDlg(java.awt.Frame parent, List<TempEntity> listTempEntity) {
        super(parent, true);
        this.setTitle("�߼���ѯ");
        this.listTempEntity = listTempEntity;
        initComponents();
        initOthers();
        setupEvents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cb_Entities = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1_Fields = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnCheck = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jSplitPane1.setDividerLocation(220);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("�����༭"));
        jPanel1.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setRightComponent(jPanel1);

        jLabel1.setText(" ��ѡ��");

        jScrollPane1.setViewportView(jList1_Fields);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_Entities, 0, 156, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_Entities, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("�ֶ��б�", jPanel2);

        jSplitPane1.setLeftComponent(jTabbedPane1);

        btnOk.setText("ȷ��");

        btnCancel.setText("ȡ��");

        btnCheck.setText("�鿴SQL");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(206, 206, 206)
                .addComponent(btnCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 264, Short.MAX_VALUE)
                .addComponent(btnOk)
                .addGap(12, 12, 12)
                .addComponent(btnCancel)
                .addGap(31, 31, 31))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCancel)
                    .addComponent(btnCheck))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCheck;
    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox cb_Entities;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1_Fields;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

    private void initOthers() {
        List<String> tmp_list_0 = new ArrayList();
        if (UserContext.getSql_dialect().equals("sqlserver")) {
            ht_funs.put("תΪСд()", "LOWER()");
            ht_funs.put("תΪ��д()", "UPPER()");
            ht_funs.put("ɾ����ո�()", "LTRIM()");
            ht_funs.put("ɾ���ҿո�()", "RTRIM()");
            ht_funs.put("ȡ�Ӵ�(,��ʼ,����)", "SUBSTRING(,1,1)");
            ht_funs.put("�滻�ַ���(,�滻ǰ,�滻��)", "REPLACE(,,)");
            ht_funs.put("ȡ���()", "YEAR()");
            ht_funs.put("ȡ�·�()", "MONTH()");
            ht_funs.put("ȡ��()", "DAY()");
            ht_funs.put("ȡ����(\"ww\",)", "DATEPART (\"ww\",)");
            ht_funs.put("�������(\"yy\",������,)", "DATEADD(\"yy\",1,)");
            ht_funs.put("�����·�(\"mm\",������,)", "DATEADD(\"mm\",1,)");
            ht_funs.put("������(\"dd\",������,)", "DATEADD(\"dd\",1,)");
        } else if (UserContext.getSql_dialect().equals("oracle")) {
            ht_funs.put("תΪСд()", "LOWER()");
            ht_funs.put("תΪ��д()", "UPPER()");
            ht_funs.put("ɾ����ո�()", "LTRIM()");
            ht_funs.put("ɾ���ҿո�()", "RTRIM()");
            ht_funs.put("ȡ�Ӵ�(,��ʼ,����)", "SUBSTR(,1,1)");
            ht_funs.put("�滻�ַ���(,�滻ǰ,�滻��)", "REPLACE(,,)");
            ht_funs.put("ȡ���()", "to_char(,'yyyy')");
            ht_funs.put("ȡ�·�()", "to_char(,'MM')");
            ht_funs.put("ȡ��()", "to_char(,'dd'");
            ht_funs.put("ȡ����(\"ww\",)", "to_char (,'ww')");
            ht_funs.put("�������(\"yy\",������,)", "ADD_MONTHS(,)");
            ht_funs.put("�����·�(\"mm\",������,)", "ADD_MONTHS(,)");
            ht_funs.put("������(\"dd\",������,)", "+");
        } else if (UserContext.getSql_dialect().equals("db2")) {
        }
        tmp_list_0.add("תΪСд()");
        tmp_list_0.add("תΪ��д()");
        tmp_list_0.add("ɾ����ո�()");
        tmp_list_0.add("ɾ���ҿո�()");
        tmp_list_0.add("ȡ�Ӵ�(,��ʼ,����)");
        tmp_list_0.add("�滻�ַ���(,�滻ǰ,�滻��)");
        tmp_list_0.add("ȡ���()");
        tmp_list_0.add("ȡ�·�()");
        tmp_list_0.add("ȡ��()");
        tmp_list_0.add("ȡ����(\"ww\",)");
        tmp_list_0.add("�������(\"yy\",������,)");
        tmp_list_0.add("�����·�(\"mm\",������,)");
        tmp_list_0.add("������(\"dd\",������,)");
        SwingBindings.createJListBinding(UpdateStrategy.READ, tmp_list_0, list_funs).bind();
        Hashtable<String, String> akeyword_groups = new Hashtable<String, String>();
        Hashtable<String, List> lookups = new Hashtable<String, List>();
        Hashtable<String, Hashtable<String, Code>> sys_codes = CodeManager.getCodeManager().getHt_types();
        Hashtable<String, String> k_keywords = new Hashtable<String, String>();

        for (TempEntity te : listTempEntity) {
            List tmp_list = lookups.get(te.getEntityCaption());
            if (tmp_list == null) {
                tmp_list = new ArrayList();
            }
            for (TempField tf : te.getTempFields()) {
                akeyword_groups.put("[" + te.getEntityCaption() + "." + tf.getDisplay_name() + "]", te.getEntityCaption());
                tmp_list.add("[" + te.getEntityCaption() + "." + tf.getDisplay_name() + "]");
                k_keywords.put("[" + te.getEntityCaption() + "." + tf.getDisplay_name() + "]", "[" + te.getEntityClass() + "." + tf.getField_name() + "]");
            }
            lookups.put(te.getEntityCaption(), tmp_list);
        }
        jtaFormulaText = new HrTextPane();
        jtaFormulaText.revokeDocumentKeys(lookups, akeyword_groups, k_keywords);
        jPanel1.add(jtaFormulaText, BorderLayout.CENTER);
        jTabbedPane1.add("�����", pnlEditor);
        jTabbedPane1.add("����", new javax.swing.JScrollPane(list_funs));

        jcomboBoxBindingEntity = SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE, listTempEntity, cb_Entities);
        jcomboBoxBindingEntity.bind();
        SearchListFieldDialog.doQuickSearch("�ֶ��б�", jList1_Fields);
    }

    public String getConditionDisplay() {
        return jtaFormulaText.getText();
    }

    private void setupEvents() {
        list_funs.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 2) {
                    return;
                }
                if (list_funs.getSelectedValue() == null) {
                    return;
                }

                String tmp_fun = ht_funs.get(list_funs.getSelectedValue().toString());
                tmp_fun = tmp_fun == null ? "" : tmp_fun;
                int tem = jtaFormulaText.getSelectionStart();
                jtaFormulaText.replaceSelection(tmp_fun);
                jtaFormulaText.setCaretPosition(tem + tmp_fun.length());
                jtaFormulaText.requestFocus();
            }
        });
        btnOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AdvanceConditionDlg.this.setVisible(false);
                click_ok = true;
            }
        });

        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AdvanceConditionDlg.this.setVisible(false);
            }
        });

        btnCheck.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MsgUtil.showHRMsg(convertHQL(), "");
            }
        });

        pnlEditor.addPickFormulaEditorListener(new IPickFormulaEditorListener() {

            @Override
            public void pickEditor(String operator) {
                int tem = jtaFormulaText.getSelectionStart();
                jtaFormulaText.replaceSelection(operator.toLowerCase());
                jtaFormulaText.setCaretPosition(tem + operator.length());
                jtaFormulaText.requestFocus();
            }
        });

        jList1_Fields.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 2) {
                    return;
                }
                if (jList1_Fields.getSelectedValue() == null) {
                    return;
                }
                TempField tf = (TempField) jList1_Fields.getSelectedValue();

                String tmp_field = "[" + cb_Entities.getSelectedItem().toString() + "." + tf.getDisplay_name() + "]";
                int tem = jtaFormulaText.getSelectionStart();
                jtaFormulaText.replaceSelection(tmp_field);
                jtaFormulaText.setCaretPosition(tem + tmp_field.length());
                jtaFormulaText.requestFocus();
            }
        });

        ActionListener al = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cb_Entities.getSelectedItem() == null) {
                    return;
                }
                TempEntity te = (TempEntity) cb_Entities.getSelectedItem();
                if (jListBinding_Field != null) {
                    jListBinding_Field.unbind();
                }
                jListBinding_Field = SwingBindings.createJListBinding(UpdateStrategy.READ, te.getTempFields(), jList1_Fields);
                jListBinding_Field.bind();
            }
        };
        cb_Entities.addActionListener(al);

        al.actionPerformed(null);
    }

    public String convertHQL() {
        String str = jtaFormulaText.getText().trim();
        if (str.equals("")) {
            return "1=1";
        }
        String tables = "";
        String joins = "";
        for (TempEntity te : listTempEntity) {
            String tmp_entity = te.getEntityClass().substring(te.getEntityClass().lastIndexOf(".") + 1);
            for (TempField tf : te.getTempFields()) {
                if (!str.contains("[" + te.getEntityCaption() + "." + tf.getDisplay_name() + "]")) {
                    continue;
                }
                str = str.replaceAll("\\[" + te.getEntityCaption() + "\\." + tf.getDisplay_name() + "\\]", "" + tmp_entity + "_1" + "\\." + tf.getField_name().replace("_code_", "") + "");
                String[] tmp_entityNames = tf.getEntityNames().split(";");
                String[] tmp_fieldClasses = tf.getFieldClasses().split(";");
                for (int i2 = 0; i2 < tmp_fieldClasses.length - 1; i2++) {
                    String entity1 = tmp_entityNames[i2];
                    String entity2 = tmp_entityNames[i2 + 1];
                    if (entity1.equals("Pay") || entity2.equals("Pay")) {
                        continue;
                    }
                    if (!tables.contains(entity1)) {
                        if (!tables.equals("")) {
                            tables = tables + ",";
                        }
                        tables = tables + entity1 + " " + entity1 + "_1";
                    }
                    if (!tables.contains(entity2)) {
                        if (!tables.equals("")) {
                            tables = tables + ",";
                        }
                        tables = tables + entity2 + " " + entity2 + "_1";
                    }

                    String fieldClass = tmp_fieldClasses[i2];
                    if (fieldClass.equals("2")) {
                        String tmp_join = entity1 + "_1" + "." + EntityBuilder.getEntityKey(entity2) + "="
                                + entity2 + "_1" + "." + EntityBuilder.getEntityKey(entity2);
                        if (!joins.contains(tmp_join)) {
                            if (!joins.equals("")) {
                                joins = joins + " and ";
                            }
                            joins = joins + tmp_join;
                        }
                    } else if (fieldClass.equals("3") || fieldClass.equals("4")) {
                        String tmp_join = entity1 + "_1" + "." + EntityBuilder.getEntityKey(entity1) + "="
                                + entity2 + "_1" + "." + EntityBuilder.getEntityKey(entity1);
                        if (!joins.contains(tmp_join)) {
                            if (!joins.equals("")) {
                                joins = joins + " and ";
                            }
                            joins = joins + tmp_join;
                        }
                    } else if (fieldClass.equals("5")) {
                        String tmp_join = entity1 + "_1" + "." + EntityBuilder.getEntityKey(entity2) + "="
                                + entity2 + "_1" + "." + EntityBuilder.getEntityKey(entity2);
                        if (!joins.contains(tmp_join)) {
                            if (!joins.equals("")) {
                                joins = joins + " and ";
                            }
                            joins = joins + tmp_join;
                        }
                    }
                }
            }
        }
        if (!joins.equals("")) {
            joins = joins + " and ";
        }

        if (tables.equals("")) {
            tables = queryEntity + " " + queryEntity + "_1";
        }
        str = " " + queryEntity + "."
                + EntityBuilder.getEntityKey(queryEntity)
                + " in (select " + queryEntity + "_1" + "." + EntityBuilder.getEntityKey(queryEntity)
                + " from " + tables + " where " + joins + "(" + str + "))";
        return str;
    }

    public String convertSQL() {
        String str = jtaFormulaText.getText().trim();
        if (str.equals("")) {
            return "1=1";
        }
        String tables = "";
        String joins = "";
        for (TempEntity te : listTempEntity) {
            String tmp_entity = te.getEntityClass().substring(te.getEntityClass().lastIndexOf(".") + 1);
            for (TempField tf : te.getTempFields()) {
                String val = "[" + te.getEntityCaption() + "." + tf.getDisplay_name() + "]";
                if (!str.contains(val)) {
                    continue;
                }
                Class c = tf.getEntityClass();
                boolean declare = true;
                String superClass = c.getSuperclass().getSimpleName();
                if (c.getSuperclass().getSimpleName().equals("A01") || c.getSuperclass().getSimpleName().equals("BasePersonChange")) {
                    Field[] fs = c.getSuperclass().getDeclaredFields();
                    for (Field f : fs) {
                        if (f.getName().equals(tf.getField_name())) {
                            declare = false;
                            break;
                        }
                    }
                    if (tables.equals("")) {
                        tables = queryEntity + " " + queryEntity + "_1";
                    }
                    if (!tables.contains(superClass)) {
                        tables += "," + superClass + " " + superClass + "_1";
                        String key_field = EntityBuilder.getEntityKey(queryEntity);
                        joins = queryEntity + "_1." + key_field + "=" + superClass + "_1." + key_field;
                    }
                }
                str = str.replace(val, "" + (declare ? tmp_entity : superClass) + "_1" + "." + tf.getField_name().replace("_code_", "") + "");
                String[] tmp_entityNames = tf.getEntityNames().split(";");
                String[] tmp_fieldClasses = tf.getFieldClasses().split(";");
                for (int i2 = 0; i2 < tmp_fieldClasses.length - 1; i2++) {
                    String entity1 = tmp_entityNames[i2];
                    String entity2 = tmp_entityNames[i2 + 1];
                    if (entity1.equals("Pay") || entity2.equals("Pay")) {
                        continue;
                    }
                    if (!tables.contains(entity1)) {
                        if (!tables.equals("")) {
                            tables = tables + ",";
                        }
                        tables = tables + entity1 + " " + entity1 + "_1";
                    }
                    if (!tables.contains(entity2)) {
                        if (!tables.equals("")) {
                            tables = tables + ",";
                        }
                        tables = tables + entity2 + " " + entity2 + "_1";
                    }
                    String fieldClass = tmp_fieldClasses[i2];
                    if (fieldClass.equals("2")) {
                        String tmp_join = entity1 + "_1" + "." + EntityBuilder.getEntityKey(entity2) + "="
                                + entity2 + "_1" + "." + EntityBuilder.getEntityKey(entity2);
                        if (!joins.contains(tmp_join)) {
                            if (!joins.equals("")) {
                                joins = joins + " and ";
                            }
                            joins = joins + tmp_join;
                        }
                    } else if (fieldClass.equals("3") || fieldClass.equals("4")) {
                        String tmp_join = entity1 + "_1" + "." + EntityBuilder.getEntityKey(entity1) + "="
                                + entity2 + "_1" + "." + EntityBuilder.getEntityKey(entity1);
                        if (!joins.contains(tmp_join)) {
                            if (!joins.equals("")) {
                                joins = joins + " and ";
                            }
                            joins = joins + tmp_join;
                        }
                    } else if (fieldClass.equals("5")) {
                        String tmp_join = entity1 + "_1" + "." + EntityBuilder.getEntityKey(entity2) + "="
                                + entity2 + "_1" + "." + EntityBuilder.getEntityKey(entity2);
                        if (!joins.contains(tmp_join)) {
                            if (!joins.equals("")) {
                                joins = joins + " and ";
                            }
                            joins = joins + tmp_join;
                        }
                    }
                }
            }
        }
        if (!joins.equals("")) {
            joins = joins + " and ";
        }

        if (tables.equals("")) {
            tables = queryEntity + " " + queryEntity + "_1";
        }
        str = " " + queryEntity + "."
                + EntityBuilder.getEntityKey(queryEntity)
                + " in (select " + queryEntity + "_1" + "." + EntityBuilder.getEntityKey(queryEntity)
                + " from " + tables + " where " + joins + "(" + str + "))";
        return str;
    }

    public void setQueryEntity(String queryEntity) {
        this.queryEntity = queryEntity;
    }

    public void setConditionDisplay(String displayValue) {
        jtaFormulaText.setText(displayValue);
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ReportXlsExportPanel.java
 *
 * Created on 2012-8-9, 23:47:17
 */
package org.jhrcore.client.report;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.report.ReportXlsScheme;
import org.jhrcore.mutil.ReportUtil;
import org.jhrcore.ui.ModelFrame;

/**
 *
 * @author mxliteboss
 */
public class ReportXlsExportPanel extends javax.swing.JPanel {

    private List scheme_list = new ArrayList();
    private JComboBoxBinding scheme_binding;

    /** Creates new form ReportXlsExportPanel */
    public ReportXlsExportPanel() {
        initComponents();
        initOthers();
        setupEvents();
    }

    private void initOthers() {
        scheme_list = CommUtil.fetchEntities("from ReportXlsScheme e where  e.scheme_type ='1' order by e.entity_name");
        ReportXlsScheme scheme = new ReportXlsScheme();
        scheme.setScheme_name("请选择导出模版");
        scheme_list.add(0, scheme);
        scheme_binding = SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE, scheme_list, jcbbScheme);
        scheme_binding.bind();
        jcbOpen.setSelected(true);
    }

    private void setupEvents() {
        btnOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (jcbbScheme.getSelectedIndex() == 0) {
                    return;
                }
                Object obj = jcbbScheme.getSelectedItem();
                ReportXlsScheme curExportScheme = (ReportXlsScheme) obj;
                ReportUtil.readXlsScheme(curExportScheme);
                ReportUtil.exportExcel(curExportScheme, jcbOpen.isSelected(), (JFrame) JOptionPane.getFrameForComponent(btnOk));
            }
        });
        btnClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ModelFrame.close();
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jcbbScheme = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnClose = new javax.swing.JButton();
        jcbOpen = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();

        btnOk.setText("导出");

        btnClose.setText("关闭");

        jcbOpen.setText("直接打开");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jcbOpen)
                .addGap(18, 18, 18)
                .addComponent(btnOk)
                .addGap(27, 27, 27)
                .addComponent(btnClose)
                .addContainerGap(122, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnOk)
                        .addComponent(jcbOpen))
                    .addComponent(btnClose))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jLabel1.setText("选择模板：");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jcbbScheme, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 211, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbbScheme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox jcbOpen;
    private javax.swing.JComboBox jcbbScheme;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ShowProcessDlg.java
 *
 * Created on 2010-9-27, 21:45:56
 */
package org.jhrcore.ui;

import java.awt.Dialog;
import java.awt.Frame;
import org.jhrcore.msg.CommMsg;

/**
 *
 * @author hflj
 */
public class ShowProcessDlg extends javax.swing.JDialog {

    private static ShowProcessDlg showProcessDlg = null;
    private java.awt.Frame parentFrame = null;
    private java.awt.Dialog parentDlg = null;

    /** Creates new form ShowProcessDlg */
    public ShowProcessDlg(java.awt.Frame parent, String msg) {
        super(parent, false);
        initComponents();
        this.setAlwaysOnTop(true);
        jLabel1.setText(msg);
    }

    public ShowProcessDlg(java.awt.Dialog parent, String msg) {
        super(parent, false);
        initComponents();
        this.setAlwaysOnTop(true);
        jLabel1.setText(msg);
    }

    public static void startProcess(final java.awt.Dialog parent) {
//        startProcess(parent,"���ڴ����У����Ժ�...");
        startProcess(parent, CommMsg.PROCESSING.toString());

    }

    public static void startProcess(final java.awt.Dialog parent, String msg) {
        showProcessDlg = new ShowProcessDlg(parent, msg);
        showProcessDlg.setParentDlg(parent);
        ContextManager.locateOnMainScreenCenter(showProcessDlg);
        showProcessDlg.setVisible(true);

    }

    public static void startProcess(final java.awt.Frame parent) {
//        startProcess(parent, "���ڴ����У����Ժ�...");
        startProcess(parent, CommMsg.PROCESSING.toString());
    }

    public static void startProcess(final java.awt.Frame parent, String msg) {
        showProcessDlg = new ShowProcessDlg(parent, msg);
        showProcessDlg.setParentFrame(parent);
        ContextManager.locateOnMainScreenCenter(showProcessDlg);
        showProcessDlg.setVisible(true);
    }

    public static void endProcess() {
        if (showProcessDlg != null) {
            showProcessDlg.dispose();
            if (showProcessDlg.getParentFrame() != null) {
                showProcessDlg.getParentFrame().setVisible(true);
            }
            if (showProcessDlg.getParentDlg() != null) {
                showProcessDlg.getParentDlg().setVisible(true);
            }
        }
    }

    public Frame getParentFrame() {
        return parentFrame;
    }

    public void setParentFrame(Frame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public Dialog getParentDlg() {
        return parentDlg;
    }

    public void setParentDlg(Dialog parentDlg) {
        this.parentDlg = parentDlg;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setText("���ڴ����У����Ժ�...");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/process.gif"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client.report;

import org.jhrcore.client.report.comm.ReportRightPanel;
import org.jhrcore.client.report.comm.ReportToModulePanel;
import org.jhrcore.client.report.comm.ReportPropertyDialog;
import com.foundercy.pf.control.table.ColumnGroup;
import com.foundercy.pf.control.table.FBaseTableColumn;
import com.foundercy.pf.control.table.FTable;
import com.fr.base.FRContext;
import com.fr.base.FRFont;
import com.fr.base.Style;
import com.fr.base.dav.Env;
import com.fr.base.dav.LocalEnv;
import com.fr.base.print.PrintUtils;
import com.fr.cell.Grid;
import com.fr.cell.ParameterJWorkSheet;
import com.fr.cell.ReportPane;
import com.fr.cell.event.GridSelectionChangeEvent;
import com.fr.cell.event.GridSelectionChangeListener;
import com.fr.cell.event.ReportDataChangeEvent;
import com.fr.cell.event.ReportDataChangeListener;
import com.fr.chart.ChartCollection;
import com.fr.data.TableData;
import com.fr.design.actions.ReportAction;
import com.fr.design.actions.ToolbarActionManager;
import com.fr.design.actions.edit.*;
import com.fr.view.PreviewPane;
import com.fr.report.*;
import com.fr.report.io.*;
import com.fr.design.actions.insert.cell.*;
import com.fr.design.actions.insert.flot.*;
import com.fr.design.actions.insert.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.ParserConfigurationException;
import org.jhrcore.entity.base.ModuleInfo;
import org.jhrcore.entity.report.ReportDef;
import com.fr.design.actions.file.*;
import com.fr.design.actions.insert.HyperlinkAction;
import com.fr.design.data.*;
import com.fr.design.data.source.DBTableDataPane;
import com.fr.design.data.source.NameTableDataDialog;
import com.fr.design.file.core.ChooseFileFilter;
import com.fr.design.gui.xpane.FILEChooserPane;
import com.fr.design.insert.editor.*;
import com.fr.design.mainframe.drag.ReportPaneDropTarget;
import com.fr.design.mainframe.*;
import com.fr.report.cellElement.Formula;
import com.fr.report.cellElement.core.*;
import com.fr.report.core.ParameterHelper;
import com.fr.report.painter.BiasTextPainter;
import com.fr.report.parameter.Parameter;
import com.fr.data.impl.*;
import com.fr.design.actions.cell.*;
import com.fr.design.actions.cell.style.*;
import com.fr.design.actions.report.*;
import com.fr.dialog.NameObject;
import com.fr.report.cellElement.CellExpandAttr;
import com.fr.report.cellElement.TableDataColumn;
import com.fr.report.script.Calculator;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.ComponentUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.base.Table2View;
import org.jhrcore.entity.query.Condition;
import org.jhrcore.entity.query.QueryAnalysisScheme;
import org.jhrcore.entity.right.RoleRightTemp;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.mutil.ReportUtil;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.FormulaTextDialog;
import org.jhrcore.ui.JCheckBoxList;
import org.jhrcore.ui.ModalDialog;
import org.jhrcore.ui.ModelFrame;
import org.jhrcore.ui.listener.IPickWindowCloseListener;
import org.jhrcore.ui.task.IModuleCode;
import org.jhrcore.util.DateUtil;
import org.jhrcore.util.UtilTool;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Administrator
 */
public class ReportPanel1 extends JPanel implements IModuleCode {

    private static Logger log = Logger.getLogger(ReportPanel1.class.getSimpleName());
    private ReportModel reportModel;
    private static DesignParameterJWorkSheet jWorkSheet;

    public static ParameterJWorkSheet getJWorkSheet() {
        return jWorkSheet;
    }
    private JTree jTree;
    private JPanel pnl_content = new JPanel(new BorderLayout());
    private JToolBar toolbar = new JToolBar();
    private JButton btnAdd = new JButton("����");
    private JButton btnCancel = new JButton("ȡ��");
    private JButton btnEditName = new JButton("������");
    private JButton btnDel = new JButton("ɾ��");
    private JButton btnSave = new JButton("����ģ��");
    private JButton btnImport = new JButton("����");
    private JButton btnSaveAs = new JButton("����ģ��");
    private JButton btnEdit = new JButton("��Ʊ���");
    private JButton btnExcute = new JButton("ִ�б���");
    private JButton btnUse = new JButton("ģ�鱨������");
    private JPopupMenu jpoFill = new JPopupMenu("�����");
    private JButton btnFill = new JButton("�����");
    private JMenuItem btnOutFill = new JMenuItem("����ģ������");
    private JMenuItem btnOutData = new JMenuItem("���������");
    private JMenuItem btnInPort = new JMenuItem("���������");
    private JPopupMenu pp_right = new JPopupMenu("");
    private JPopupMenu pp_import = new JPopupMenu("����");
    private JMenuItem miEdit = new JMenuItem("��Ʊ���");
    private JMenuItem miExcute = new JMenuItem("ִ�б���");
    private JMenuItem miDel = new JMenuItem("ɾ��");
    private JMenuItem miProperty = new JMenuItem("����");
    private JMenuItem miGiveRight = new JMenuItem("��Ȩ");
    private JMenuItem miImportXls = new JMenuItem("Excel�ļ�");
    private JMenuItem miImportCpt = new JMenuItem("ģ���ļ�");
    // ��ǰ�༭�ı�������
    private ReportDef reportDef;
    private JTabbedPane tp_container = new JTabbedPane();
    private JTabbedPane tp_bottom = new JTabbedPane();
    private TableDataTreePane tableDataTreePane = null;
    private static TableDataTreePane serverTableDataTreePane = null;
    private ModuleInfo cur_module;
    private DefaultMutableTreeNode cur_node;
    private CellCardAttrPane cellCardAttrPane = null;
    private JFileChooser fileChooser = null;
    private boolean hasNew = false;
    private boolean isDataChanged = false;
    private static List userViewlist = null;//���ڱ����û�����Ȩ�޼�¼
    private static List<Table2View> replaceViewList = new ArrayList<Table2View>();//���ڱ��浱ǰȨ�޼�¼
    public static boolean enable_locate_dict = false;
    private static HashMap nameHash = new HashMap();
//    private static Hashtable<Integer, List<CoverCellElement>> cellKeys = new Hashtable<Integer, List<CoverCellElement>>();
//    private static int pageNo = 0;
//    private static ReportPage curPage = null;
//    private static ParameterReport workSheet = null;
    private String module_code = "Report1";

    public ReportPanel1() {
        super(new BorderLayout());
        initOthers();
        setupEvents();
    }

    public void setFunctionRight() {
        ComponentUtil.setSysFuntion(this, "ReportMng");
        ComponentUtil.setSysCompFuntion(miEdit, "ReportMng.btnEdit");
        ComponentUtil.setSysCompFuntion(miExcute, "ReportMng.btnExcute");
        ComponentUtil.setSysCompFuntion(miDel, "ReportMng.btnDel");
        ComponentUtil.setSysCompFuntion(miProperty, "ReportMng.btnEditName");
        setMainState(false);
    }

    private void initOthers() {
        ContextManager.refreshStatusBar();
        initToolBar();
        List modules = CommUtil.fetchEntities("from ModuleInfo mi order by mi.order_no");
        List<ReportDef> reports = new ArrayList<ReportDef>();
        ReportUtil.initReportModel(modules, reports);
        reportModel = new ReportModel(modules, reports, UserContext.role_id);
        jTree = new JTree(reportModel);
        jTree.setRootVisible(false);
        jTree.setShowsRootHandles(true);
        JPanel pnl_container = new JPanel(new BorderLayout());
        pnl_container.add(toolbar, BorderLayout.NORTH);
        pnl_container.add(pnl_content, BorderLayout.CENTER);
        tp_container.add("����", new JScrollPane(jTree));
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tp_container, pnl_container);
        sp.setDividerLocation(200);
        sp.setDividerSize(3);
        this.add(sp, BorderLayout.CENTER);
    }

    private void initToolBar() {
        toolbar.setFloatable(false);
        toolbar.setPreferredSize(new Dimension(toolbar.getWidth(), 25));
        toolbar.setRollover(true);
        toolbar.add(btnAdd);
        toolbar.add(btnCancel);
        toolbar.add(btnImport);
        toolbar.add(btnEditName);
        toolbar.addSeparator();
        toolbar.add(btnDel);
        toolbar.addSeparator();
        toolbar.add(btnSave);
        toolbar.add(btnSaveAs);
        toolbar.addSeparator();
        toolbar.add(btnEdit);
        toolbar.add(btnExcute);
        toolbar.add(btnUse);
        toolbar.add(btnFill);

        pp_right.add(miEdit);
        pp_right.add(miExcute);
        pp_right.add(miDel);
        pp_right.add(miProperty);
        pp_right.add(miGiveRight);
        pp_import.add(miImportXls);
        pp_import.add(miImportCpt);
        jpoFill.add(btnOutFill);
        jpoFill.add(btnOutData);
        jpoFill.add(btnInPort);

    }

    private void setMainState(boolean hasNew) {
        this.hasNew = hasNew;
        boolean isReport = isReportNode();
        btnAdd.setEnabled(UserContext.hasFunctionRight("ReportMng.btnAdd") && !hasNew);
        btnCancel.setEnabled(UserContext.hasFunctionRight("ReportMng.btnCancel") && hasNew);
        jTree.setEnabled(!hasNew);
        boolean reporteditable = reportDef != null && reportDef.getReportDef_key() != null && UserContext.getReportRight(reportDef.getReportDef_key()) == 1;
        boolean editable = UserContext.hasFunctionRight("ReportMng.btnEdit") && !hasNew && reporteditable && isReport;
        boolean delable = UserContext.hasFunctionRight("ReportMng.btnDel") && !hasNew && reporteditable && isReport;
        boolean excuteable = UserContext.hasFunctionRight("ReportMng.btnExcute") && isReport && !hasNew;
        boolean renameable = UserContext.hasFunctionRight("ReportMng.btnEditName") && !hasNew && reporteditable && isReport;
        miEdit.setEnabled(editable);
        btnEdit.setEnabled(editable);
        miDel.setEnabled(delable);
        btnDel.setEnabled(delable);
        miProperty.setEnabled(renameable);
        btnEditName.setEnabled(renameable);
        btnExcute.setEnabled(excuteable);
        miExcute.setEnabled(excuteable);
        miGiveRight.setEnabled(UserContext.hasFunctionRight("ReportMng.miGiveRight") && !hasNew && reporteditable);
        btnSave.setEnabled(UserContext.hasFunctionRight("ReportMng.btnSave") && ((reporteditable && isReport) || hasNew));
        btnImport.setEnabled(UserContext.hasFunctionRight("ReportMng.btnImport") && !hasNew);
        btnSaveAs.setEnabled(UserContext.hasFunctionRight("ReportMng.btnSaveAs") && !hasNew && isReport);
    }

    private void setupEvents() {
        miGiveRight.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cur_node == null) {
                    return;
                }
                List re_list = new ArrayList();
                Enumeration ene = cur_node.depthFirstEnumeration();
                while (ene.hasMoreElements()) {
                    DefaultMutableTreeNode tmp_node = (DefaultMutableTreeNode) ene.nextElement();
                    if (tmp_node.getUserObject() instanceof ReportDef) {
                        re_list.add(tmp_node.getUserObject());
                    }
                }
                if (re_list.isEmpty()) {
                    return;
                }
                ReportRightPanel rfrPanel = new ReportRightPanel(re_list);
                ModelFrame.showModel((JFrame) JOptionPane.getFrameForComponent(btnEdit), rfrPanel, true, "������Ȩ", 700, 550);
            }
        });
        ActionListener al_property = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cur_node == null) {
                    return;
                }
                Object cur_obj = cur_node.getUserObject();
                if (cur_node.getLevel() <= 1) {
                    return;
                }
                if (!(cur_obj instanceof ReportDef)) {
                    return;
                }
                save_report(reportDef, true);
            }
        };
        btnEditName.addActionListener(al_property);
        miProperty.addActionListener(al_property);
        ActionListener edit_listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (jTree.getSelectionPath().getLastPathComponent() == null) {
                    return;
                }
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree.getSelectionPath().getLastPathComponent();
                edit_report(node.getUserObject());
            }
        };
        btnEdit.addActionListener(edit_listener);
        miEdit.addActionListener(edit_listener);
        ActionListener del_listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                del_report();
            }
        };
        miDel.addActionListener(del_listener);
        btnDel.addActionListener(del_listener);
        ActionListener exec_listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                excute_report();
            }
        };
        miExcute.addActionListener(exec_listener);
        btnExcute.addActionListener(exec_listener);
        jTree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                if (e.getPath() == null || e.getPath().getLastPathComponent() == null) {
                    return;
                }
                Object obj = e.getPath().getLastPathComponent();
                if (obj instanceof DefaultMutableTreeNode) {
                    cur_node = (DefaultMutableTreeNode) obj;
                    Object user_obj = cur_node.getUserObject();
                    if (user_obj instanceof ReportDef) {
                        reportDef = (ReportDef) user_obj;
                        cur_module = reportDef.getModuleInfo();
                        edit_report(reportDef);
                    } else if (user_obj instanceof RoleRightTemp) {
                        cur_module = ((RoleRightTemp) ((DefaultMutableTreeNode) cur_node).getUserObject()).getModuleInfo();
                    }
                    setMainState(hasNew);
                }
            }
        });
        jTree.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (hasNew) {
                    return;
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    pp_right.show(e.getComponent(), e.getX(), e.getY());
                } else if (e.getClickCount() == 2) {
                    excute_report();
                }
            }
        });
        btnImport.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pp_import.show(btnImport, 0, 30);
            }
        });
        btnFill.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jpoFill.show(btnFill, 0, 30);
            }
        });
        btnAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                add_report1();
                setMainState(true);
            }
        });
        btnSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                save_report(reportDef, false);
                setMainState(hasNew);
            }
        });
        btnSaveAs.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saveAs_report();
            }
        });
        miImportXls.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                importExcel();
                setMainState(true);
            }
        });
        miImportCpt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                open_report();
                setMainState(true);
            }
        });
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                reportDef = null;
                if (cur_node != null) {
                    TreePath tp = new TreePath(cur_node.getPath());
                    jTree.clearSelection();
                    jTree.setSelectionPath(tp);
                }
                setMainState(false);
            }
        });
        btnUse.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ReportToModulePanel rud = new ReportToModulePanel(reportModel);
                ModelFrame.showModel(ContextManager.getMainFrame(), rud, true, "���������", 700, 600);
            }
        });
        btnOutFill.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                XlsModelDefinePanel pnl = new XlsModelDefinePanel();
                ModelFrame.showModel(ContextManager.getMainFrame(), pnl, true, "����ģ������:", 1000, 650);
            }
        });
        btnOutData.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ReportXlsExportPanel pnl = new ReportXlsExportPanel();
                ModelFrame.showModel(ContextManager.getMainFrame(), pnl, true, "", 400, 200);
            }
        });

        btnInPort.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ImportDialog eDlg = new ImportDialog();
                ContextManager.locateOnMainScreenCenter(eDlg);
                eDlg.setVisible(true);
            }
        });

        ComponentUtil.initTreeSelection(jTree);
        if (cur_node != null && !(cur_node.getUserObject() instanceof ReportDef)) {
            ParameterReport workSheet = new ParameterReport();
            WorkBook workbook = new WorkBook();
            workbook.addReport(workSheet);
            createJWorkSheet(workSheet);
            Grid localGrid = jWorkSheet.getGrid();
            localGrid.setDefaultCellEditor(DSColumn.class, new DSColumnCellEditor());
            localGrid.setDefaultCellEditor(Formula.class, new FormulaCellEditor());
            localGrid.setDefaultCellEditor(ChartCollection.class, new ChartCellEditor());
            localGrid.setDefaultCellEditor(Image.class, new ImageCellEditor());
            localGrid.setDefaultCellEditor(BiasTextPainter.class, new BiasTextPainterCellEditor());
            localGrid.setDefaultFloatEditor(Formula.class, new FormulaFloatEditor());
            localGrid.setDefaultFloatEditor(ChartCollection.class, new ChartFloatEditor());
            localGrid.setDefaultFloatEditor(Image.class, new ImageFloatEditor());
            localGrid.setDefaultCellEditor(SubReport.class, new SubReportCellEditor());
            jWorkSheet.repaint();
            show_report_designer();
        }
        ((JSplitPane) pnl_content.getComponent(0)).setDividerLocation(this.getPreferredSize().height - 25);
    }

    private boolean isReportNode() {
        return cur_node != null && cur_node.getUserObject() instanceof ReportDef;
    }

    private List getGroupList() {
        DefaultMutableTreeNode parent_node = cur_node;
        List group_list = new ArrayList();
        if (parent_node.getUserObject() instanceof RoleRightTemp) {
            parent_node = (DefaultMutableTreeNode) parent_node.getParent();
        } else if (parent_node.getUserObject() instanceof ReportDef) {
            parent_node = (DefaultMutableTreeNode) parent_node.getParent();
            if (parent_node.getUserObject() instanceof RoleRightTemp) {
                parent_node = (DefaultMutableTreeNode) parent_node.getParent();
            }
        }
        Enumeration enumt = parent_node.breadthFirstEnumeration();
        while (enumt.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumt.nextElement();
            if (node.getLevel() <= 1) {
                continue;
            }
            Object obj = node.getUserObject();
            if (obj instanceof RoleRightTemp) {
                group_list.add(((RoleRightTemp) obj).getTemp_name());
            }
        }
        return group_list;
    }

    private void addReportAction(JToolBar toolbar, ReportAction ra, String tooltiptext) {
        addReportAction(toolbar, ra, tooltiptext, 27, 27);
    }

    private void addReportAction(JToolBar toolbar, ReportAction ra, String tooltiptext, int width, int height) {
        ra.setReportPane(jWorkSheet);
        ToolbarActionManager.createToolbarActionManager().registerAction(ra);
        final JComponent com = ra.createToolBarComponent();
        com.setToolTipText(tooltiptext);
        com.setPreferredSize(new Dimension(width, height));
        com.setMaximumSize(com.getPreferredSize());
        toolbar.add(com);
    }

    private void buildCellPop(JToolBar toolbar) {
        final JButton btn1 = new JButton(new ImageIcon(this.getClass().getResource("/com/fr/design/images/m_insert/cellPop.png")));
        toolbar.add(btn1);
        btn1.setToolTipText("��Ԫ��Ԫ��");
        final JPopupMenu popMenu1 = new JPopupMenu();
        ReportAction ra = new DSColumnCellAction();
        ra.setReportPane(jWorkSheet);
        popMenu1.add(ra);
        popMenu1.addSeparator();
        ra = new GeneralCellAction();
        ra.setReportPane(jWorkSheet);
        popMenu1.add(ra);
        ra = new FormulaCellAction();
        ra.setReportPane(jWorkSheet);
        popMenu1.add(ra);
        ra = new ChartCellAction();
        ra.setReportPane(jWorkSheet);
        popMenu1.add(ra);
        ra = new ImageCellAction();
        ra.setReportPane(jWorkSheet);
        popMenu1.add(ra);
        ra = new BiasCellAction();
        ra.setReportPane(jWorkSheet);
        popMenu1.add(ra);
        /*
        // �����ӱ���
        ra = new SubReportCellAction();
        ra.setReportPane(jWorkSheet);
        popMenu1.add(ra);
         */
        btn1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                popMenu1.show(btn1, 0, btn1.getHeight());
            }
        });
    }

    private void buildFloatPop(JToolBar toolbar) {
        final JButton btn2 = new JButton(new ImageIcon(this.getClass().getResource("/com/fr/design/images/m_insert/floatPop.png")));
        toolbar.add(btn2);
        final JPopupMenu popMenu2 = new JPopupMenu();

        ReportAction ra = new NoneShapeFloatAction();
        ra.setReportPane(jWorkSheet);
        popMenu2.add(ra);

        popMenu2.addSeparator();
        ra = new TextBoxFloatAction();
        ra.setReportPane(jWorkSheet);
        popMenu2.add(ra);
        ra = new FormulaFloatAction();
        ra.setReportPane(jWorkSheet);
        popMenu2.add(ra);
        ra = new ChartFloatAction();
        ra.setReportPane(jWorkSheet);
        popMenu2.add(ra);
        ra = new ImageFloatAction();
        ra.setReportPane(jWorkSheet);
        popMenu2.add(ra);
        toolbar.add(btn2);
        btn2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                popMenu2.show(btn2, 0, btn2.getHeight());
            }
        });
    }

    private void open_report() {
        FILEChooserPane localFILEChooserPane = FILEChooserPane.getInstance();
        //localFILEChooserPane.setCurrentDirectory(localFILE1);
        ChooseFileFilter localChooseFILEFilter = new ChooseFileFilter(new String[]{"cpt"}, "ģ���ļ�");
        localFILEChooserPane.addChooseFILEFilter(localChooseFILEFilter);
        if (localFILEChooserPane.showOpenDialog(DesignerContext.getDesignerFrame()) != 0) {
            return;
        }
        if (cur_node == null) {
            return;
        }
        if (cur_module == null) {
            return;
        }
        ReportDef reportDef1 = (ReportDef) UtilTool.createUIDEntity(ReportDef.class);
        reportDef1.setModuleInfo(cur_module);
        Object obj = cur_node.getUserObject();
        if (obj instanceof ReportDef) {
            ReportDef tmp = (ReportDef) obj;
            reportDef1.setReport_class(tmp.getReport_class());
        } else if (obj instanceof RoleRightTemp) {
            reportDef1.setReport_class(obj.toString());
        }
        File file = new File(localFILEChooserPane.getSelectedFILE().getPath().replace(".cpt", ".xml"));
        if (file.exists()) {
            byte[] buffer = null;
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(file);
//                buffer = BBImpl.getReportForDocument(doc, ConfigManager.getConfigManager().getProperty("base.ApplicationServer"));
            } catch (SAXException ex) {
                log.error(ex);
            } catch (ParserConfigurationException ex) {
                log.error(ex);
            } catch (FileNotFoundException e) {
                log.error(e);
            } catch (IOException e) {
                log.error(e);
            }
            file = new File(System.getProperty("user.home") + "/resources/datasource.xml");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            BufferedOutputStream output;
            try {
                output = new BufferedOutputStream(new FileOutputStream(file));
                output.write(buffer);
                output.close();
            } catch (FileNotFoundException e) {
                log.error(e);
                return;
            } catch (IOException e) {
                log.error(e);
                return;
            }
        }
        reportDef = reportDef1;
        LocalEnv localEnv = new LocalEnv(System.getProperty("user.home"));
        FRContext.setCurrentEnv(localEnv);
        try {
            enable_locate_dict = false;
            InputStream is = new FileInputStream(localFILEChooserPane.getSelectedFILE().getPath());
            TemplateImporter templateImporter = new TemplateImporter();
            WorkBook workSheet = templateImporter.generate(is);
            createJWorkSheet((ParameterReport) workSheet.getReport(0));
            Grid localGrid = jWorkSheet.getGrid();
            localGrid.setDefaultCellEditor(DSColumn.class, new DSColumnCellEditor());
            localGrid.setDefaultCellEditor(Formula.class, new FormulaCellEditor());
            localGrid.setDefaultCellEditor(ChartCollection.class, new ChartCellEditor());
            localGrid.setDefaultCellEditor(Image.class, new ImageCellEditor());
            localGrid.setDefaultCellEditor(BiasTextPainter.class, new BiasTextPainterCellEditor());
            localGrid.setDefaultFloatEditor(Formula.class, new FormulaFloatEditor());
            localGrid.setDefaultFloatEditor(ChartCollection.class, new ChartFloatEditor());
            localGrid.setDefaultFloatEditor(Image.class, new ImageFloatEditor());
            localGrid.setDefaultCellEditor(SubReport.class, new SubReportCellEditor());
            jWorkSheet.repaint();

            show_report_designer();
            enable_locate_dict = true;
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    private void saveAs_report() {
        if (reportDef == null) {
            return;
        }
        JFileChooser localJFileChooser = DesignerContext.getSaveFileChooser(new ChooseFileFilter(new String[]{"cpt"}, "ģ���ļ�(������)"));
        int i = localJFileChooser.showSaveDialog(JOptionPane.getFrameForComponent(ReportPanel1.this));
        if (i != 0) {
            return;
        }
        try {
            File file = localJFileChooser.getSelectedFile();
            String file_name = file.getAbsolutePath();
            if (!file_name.toLowerCase().endsWith(".cpt")) {
                file_name = file_name + ".cpt";
            }
            TemplateExporter templateExporter = new TemplateExporter();
            templateExporter.export(new FileOutputStream(file_name), jWorkSheet.getEditingReport().getWorkBook());

            file = new File(System.getProperty("user.home") + "/resources/datasource.xml");
            if (!file.exists()) {
                log.error("file not exists:" + file);
                return;
            }
            byte[] buffer = new byte[(int) file.length()];
            BufferedInputStream input = new BufferedInputStream(
                    new FileInputStream(file));
            input.read(buffer, 0, buffer.length);
            input.close();
            BufferedOutputStream output;
            try {
                output = new BufferedOutputStream(new FileOutputStream(new File(file_name.replace(".cpt", ".xml"))));
                output.write(buffer);
                output.close();
            } catch (FileNotFoundException e) {
                log.error(e);
                e.printStackTrace();
                return;
            } catch (IOException e) {
                log.error(e);
                e.printStackTrace();
                return;
            }
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    private void save_report(ReportDef reportDef, boolean isReName) {
        if (reportDef == null || reportDef.getReportDef_key() == null) {
            return;
        }
        if (reportDef.getNew_flag() == 1) {
            reportDef.setModuleInfo(cur_module);
            reportDef.setReport_name("�±���");
        }
        LocalEnv localEnv = new LocalEnv(System.getProperty("user.home"));
        FRContext.setCurrentEnv(localEnv);
        if (isReName || reportDef.getNew_flag() == 1) {
            List group_list = getGroupList();
            ReportPropertyDialog rpDlg = new ReportPropertyDialog(ContextManager.getMainFrame(), reportDef, group_list);
            ContextManager.locateOnMainScreenCenter(rpDlg);
            rpDlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            rpDlg.setVisible(true);
            String new_group = rpDlg.getNew_group();
            String new_name = rpDlg.getNew_name();
            int new_order = rpDlg.getNew_order();
            String reportCode = rpDlg.getReport_code();
            if (rpDlg.isClick_ok()) {
                reportDef.setReport_class(new_group);
                reportDef.setReport_name(new_name);
                reportDef.setOrder_no(new_order);
                reportDef.setReport_code(reportCode);
            } else {
                return;
            }
        }
        try {
            if (reportDef.getReport_name() == null || reportDef.getReport_name().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "�������Ʋ���Ϊ�գ�");
                return;
            }
            TemplateExporter templateExporter = new TemplateExporter();
            templateExporter.export(new FileOutputStream(System.getProperty("user.home") + "/" + "tmp_report.cpt"), jWorkSheet.getEditingReport().getWorkBook());
            File file = new File(System.getProperty("user.home") + "/" + "tmp_report.cpt");
            if (!file.exists()) {
                log.error("file not exists:" + file);
                return;
            }
            byte buffer_rpt[] = new byte[(int) file.length()];
            BufferedInputStream input = new BufferedInputStream(
                    new FileInputStream(file));
            input.read(buffer_rpt, 0, buffer_rpt.length);
            input.close();
            file = new File(System.getProperty("user.home") + "/resources/datasource.xml");
            if (!file.exists()) {
                log.error("file not exists:" + file);
                return;
            }
            byte buffer_datasource[] = new byte[(int) file.length()];
            input = new BufferedInputStream(
                    new FileInputStream(file));
            input.read(buffer_datasource, 0, buffer_datasource.length);
            input.close();
            ValidateSQLResult result = null;// BBImpl.saveReport(reportDef, buffer_datasource, buffer_rpt, reportDef.getNew_flag() == 1, UserContext.role_id);
            if (result.getResult() == 0) {
                if (reportDef.getNew_flag() == 1) {
                    DefaultMutableTreeNode next_node = null;
                    next_node = reportModel.addReportDef(reportDef);
                    ComponentUtil.initTreeSelection(jTree, next_node);
                }
                hasNew = false;
                jTree.updateUI();
                JOptionPane.showMessageDialog(
                        ContextManager.getMainFrame(), "����ɹ�:" + reportDef, "��ʾ", JOptionPane.INFORMATION_MESSAGE);
                if (reportDef.getNew_flag() == 1) {
                    if (!UserContext.isSA) {
                        UserContext.reportRights.put(reportDef.getReportDef_key(), 1);
                    }
                }
                reportDef.setNew_flag(0);
            } else {
                //FormulaTextDialog.showErrorMsg(result.getMsg());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex);
        }
    }

    private void importExcel() {
        if (jTree.getSelectionPath() == null) {
            return;
        }

        if (!(jTree.getSelectionPath().getLastPathComponent() instanceof DefaultMutableTreeNode)) {
            return;
        }

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree.getSelectionPath().getLastPathComponent();
        if (node.getLevel() == 0) {
            return;
        }
        if (cur_module == null) {
            return;
        }
        int result = fileChooser.showOpenDialog(fileChooser);
        String path = "";
        File selectedFile = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            path = fileChooser.getSelectedFile().getPath();
            if (!selectedFile.getName().endsWith(".xls")) {
                JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(btnImport), "��ѡ��Ĳ�����Ч��EXCEL�ļ�", "����", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            return;
        }
        selectedFile = new File(path);
        ReportDef reportDef1 = (ReportDef) UtilTool.createUIDEntity(ReportDef.class);
        reportDef1.setModuleInfo(cur_module);
        if (node.getUserObject() instanceof ReportDef) {
            ReportDef tmp = (ReportDef) node.getUserObject();
            reportDef1.setReport_class(tmp.getReport_class());
        }
        reportDef = reportDef1;

        // ���ػ�������Դ�����ļ�--->
        byte[] imageBytes = null;//BBImpl.getBase_datasource(ConfigManager.getConfigManager().getProperty("base.ApplicationServer"));
        if (imageBytes == null) {
            return;
        }
        File file = new File(System.getProperty("user.home") + "/resources/datasource.xml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        BufferedOutputStream output;
        try {
            output = new BufferedOutputStream(new FileOutputStream(file));
            output.write(imageBytes);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        LocalEnv localEnv = new LocalEnv(System.getProperty("user.home"));
        FRContext.setCurrentEnv(localEnv);
        // ���ػ�������Դ�����ļ�---<
        ExcelImporter localExcelImporter;
        try {
            enable_locate_dict = false;
            localExcelImporter = new ExcelImporter();
            //selectedFile
            WorkBook localWorkBook = (WorkBook) localExcelImporter.generate(new FileInputStream(selectedFile)); //.generateWorkBook();
            ParameterReport workSheet = (ParameterReport) localWorkBook.getReport(0);// .getWorkSheet(0);
            createJWorkSheet(workSheet);
            Grid localGrid = jWorkSheet.getGrid();

            localGrid.setDefaultCellEditor(DSColumn.class, new DSColumnCellEditor());
            localGrid.setDefaultCellEditor(Formula.class, new FormulaCellEditor());
            localGrid.setDefaultCellEditor(ChartCollection.class, new ChartCellEditor());
            localGrid.setDefaultCellEditor(Image.class, new ImageCellEditor());
            localGrid.setDefaultCellEditor(BiasTextPainter.class, new BiasTextPainterCellEditor());
            localGrid.setDefaultFloatEditor(Formula.class, new FormulaFloatEditor());
            localGrid.setDefaultFloatEditor(ChartCollection.class, new ChartFloatEditor());
            localGrid.setDefaultFloatEditor(Image.class, new ImageFloatEditor());
            localGrid.setDefaultCellEditor(SubReport.class, new SubReportCellEditor());

            jWorkSheet.repaint();
            show_report_designer();
            enable_locate_dict = true;
        } catch (Exception ex) {
            log.error(ex);
        }

    }

    private void add_report1() {
        if (cur_node == null) {
            return;
        }
        DefaultMutableTreeNode node = cur_node;
        if (node.getLevel() == 0) {
            return;
        }
        if (cur_module == null) {
            return;
        }
        ReportDef reportDef1 = (ReportDef) UtilTool.createUIDEntity(ReportDef.class);
        reportDef1.setModuleInfo(cur_module);

        if (node.getUserObject() instanceof ReportDef) {
            ReportDef tmp = (ReportDef) node.getUserObject();
            reportDef1.setReport_class(tmp.getReport_class());
        } else if (node.getUserObject() instanceof RoleRightTemp) {
            reportDef1.setReport_class(node.getUserObject().toString());
        }
        QueryAnalysisScheme queryAnalysisScheme = (QueryAnalysisScheme) UtilTool.createUIDEntity(QueryAnalysisScheme.class);
        queryAnalysisScheme.setModuleInfo(cur_module);
        queryAnalysisScheme.setQueryAnalysisScheme_type(reportDef1.getReport_class());
        queryAnalysisScheme.setQuery_code("sqlbuilder");
        ReportWizardModel reportWizardModel = new ReportWizardModel(queryAnalysisScheme);
//        FmWizardDialog.showWizard(reportWizardModel);
        add_report(reportDef1);

        NameTableDataDialog localNameTableDataDialog = NameTableDataDialog.showWindow(SwingUtilities.getWindowAncestor(tableDataTreePane));
        Object localObject = null;
        String str = null;

        localObject = new DBTableData();
        str = "ds";
        localNameTableDataDialog.populate(new NameObject(str, localObject), null, true);
        String sql = queryAnalysisScheme.buildSQLforReport(reportWizardModel.getQueryScheme());
        DBTableDataPane dBTableDataPane = localNameTableDataDialog.centralPanel.dbPanel;
        dBTableDataPane.sqlTextPane.setText(sql);

        Parameter[] arrayOfParameter = ParameterHelper.analyze4Parameters(dBTableDataPane.sqlTextPane.getText());

        ReportPanel1.analysisParam(arrayOfParameter, reportWizardModel);
        if (arrayOfParameter != null) {
            dBTableDataPane.refreshParameters(arrayOfParameter);
        }
        //localNameTableDataDialog.setSize(localNameTableDataDialog.getWidth() + 300, Math.min(localNameTableDataDialog.getHeight() + 250, ContextManager.getMainFrame().getHeight() - 100));
        ContextManager.locateOnScreenCenter(localNameTableDataDialog);
        localNameTableDataDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        localNameTableDataDialog.setVisible(true);
        if (localNameTableDataDialog.getReturnValue() == 0) {
            NameObject localNameObject = localNameTableDataDialog.update();
            tableDataTreePane.tableDataTree.addNameObject(localNameObject);
            if (tableDataTreePane.op != null) {
                tableDataTreePane.op.putTableDataAction(localNameObject.getName(), (TableData) localNameObject.getObject());
            }
            after_add_dbtabledata(tableDataTreePane, localNameObject);
            tableDataTreePane.tableDataTree.refresh();
        }

    }

    public static void after_add_dbtabledata(TableDataTreePane tableDataTreePane2, NameObject localNameObject) {
        com.fr.data.impl.DBTableData dbd = (com.fr.data.impl.DBTableData) localNameObject.getObject();
        String analyse_sql = dbd.getQuery();
        analyse_sql.replaceAll("\n", " ");
//        System.out.println("analyse_sql:" + analyse_sql);
        while (true) {
            // ѭ���������е�select��䣬��select �� from ֮��������ֶν��з�����
            // �ж��ֶ��Ƿ��Ǳ����ֶΣ�����Ǳ����ֶ�������Ӧ�ı������ݼ����������ֵ䡣
            String tmp_sql = analyse_sql;
            int i1 = tmp_sql.toLowerCase().indexOf("select ") + 7;
            int i2 = tmp_sql.toLowerCase().indexOf(" from ");
            if (i1 < 0 || i2 < 0 || i1 >= i2) {
                break;
            }
            analyse_sql = analyse_sql.substring(i2 + 5);
            String tmp_s = tmp_sql.substring(i1, i2);
            String[] t_fields = tmp_s.split(",");
            String[] t_fields2 = new String[t_fields.length];
            String[] t_fields3 = new String[t_fields.length]; // ����
            for (int i0 = 0; i0 < t_fields.length; i0++) {
                String t_s1 = t_fields[i0].trim();
                String[] tmp_str_s = t_s1.split(" ");
                t_fields2[i0] = tmp_str_s[0];
                if (tmp_str_s.length > 1) {
                    t_fields3[i0] = tmp_str_s[1];
                } else {
                    i1 = tmp_str_s[0].indexOf(".");
                    if (i1 >= 0) {
                        t_fields3[i0] = tmp_str_s[0].substring(i1);
                    } else {
                        t_fields3[i0] = tmp_str_s[0];
                    }
                }
            }
            String nullStr = "a.code_type_name is not null and a.code_type_name <> ''";
            if (UserContext.sql_dialect.equals("oracle")) {
                nullStr = "trim(a.code_type_name) is not null";
            }
            if(UserContext.getSql_dialect().equals("mysql")){
                tmp_sql = "select CONCAT(b.entityName,'.', a.field_name), a.code_type_name from system a, tabname b "
                    + "where a.entity_key=b.entity_key and " + nullStr + " and CONCAT(b.entityName,'.' ,a.field_name) "
                    + "in (";
            }else{
                tmp_sql = "select b.entityName "
                        + UserContext.conn_string
                        + " '.' "
                        + UserContext.conn_string
                        + " a.field_name, a.code_type_name from system a, tabname b "
                        + "where a.entity_key=b.entity_key and " + nullStr + " and b.entityName "
                        + UserContext.conn_string
                        + " '.' "
                        + UserContext.conn_string
                        + " a.field_name "
                        + "in (";
            }
            boolean bfirst = true;
            for (int i0 = 0; i0 < t_fields2.length; i0++) {
                if (!(t_fields2[i0].startsWith("'") && t_fields2[i0].endsWith("'"))) {
                    if (!bfirst) {
                        tmp_sql = tmp_sql + ",";
                    }
                    bfirst = false;
                    //���� �����ֶ�ת�壬���⺯������ɱ����ѯ���󣬴�Ϊ��ʱ�޸��������ܽ�������������µı����������
                    tmp_sql = tmp_sql + "'" + t_fields2[i0].replace("'", "''") + "'";
                    //ԭ��tmp_sql = tmp_sql + "'" + t_fields2[i0] + "'";
                }
            }
            tmp_sql = tmp_sql + ")";
            java.util.ArrayList al = org.jhrcore.client.CommUtil.selectSQL(tmp_sql);
            for (int i0 = 0; i0 < t_fields2.length; i0++) {
                String t_s1 = t_fields2[i0];
                boolean is_code = false;
                String code_type_name = "";
                for (int j0 = 0; j0 < al.size(); j0++) {
                    Object[] objs = (Object[]) al.get(j0);
                    if (t_s1.equals(objs[0])) {
                        is_code = true;
                        code_type_name = objs[1].toString();
                        break;
                    }
                }
                if (is_code) {
                    NameObject no = new com.fr.dialog.NameObject(t_fields3[i0], new com.fr.data.impl.DBTableData(dbd.getDatabase(), "select code_id,code_name from code where code_type='" + code_type_name + "'"));

                    if (FRContext.getDatasourceManager() != null
                            && jWorkSheet.getParameterReport() != null
                            && jWorkSheet.getParameterReport().getWorkBook() != null
                            && FRContext.getDatasourceManager().getTableData(t_fields3[i0]) == null && jWorkSheet.getParameterReport().getWorkBook().getTableData(t_fields3[i0]) == null) {
                        serverTableDataTreePane.tableDataTree.addNameObject(no);
                        if (serverTableDataTreePane.op != null) {
                            serverTableDataTreePane.op.putTableDataAction(no.getName(), (com.fr.data.TableData) no.getObject());
                        }
                    }
                    if (FRContext.getDatasourceManager().getDeprecatedDictionaryManager().getDictionary(t_fields3[i0]) == null) {
                        TableDataDictionary tdd = new TableDataDictionary();
                        tdd.setTableData((TableData) no.getObject());

                        FRContext.getDatasourceManager().getDeprecatedDictionaryManager().putDictionary(no.getName(), tdd);             // ����Ϊ�����ֵ�
                        //FRContext.getDatasourceManager().putDictionary(no.getName(), tdd); // ����Ϊ�������ֵ�
                    }
                }
            }
        }
    }

    public static void analysisParam(Parameter[] arrayOfParameter, ReportWizardModel reportWizardModel) {
        for (int ind = 0; ind < reportWizardModel.getQueryScheme().getConditions().size(); ind++) {
            Condition cond = reportWizardModel.getQueryScheme().getConditions().get(ind);
            cond.setTemp_flag(false);
        }
        for (Parameter param : arrayOfParameter) {
            int ind = 0;
            boolean multi = false;
            boolean isCode = false;
            boolean isBoolean = false;
            String code_type = "";
            for (ind = 0; ind < reportWizardModel.getQueryScheme().getConditions().size(); ind++) {
                Condition cond = reportWizardModel.getQueryScheme().getConditions().get(ind);
                if (cond.isTemp_flag()) {
                    continue;
                }
                String param_name = cond.getDisplayName().substring(cond.getDisplayName().indexOf(".") + 1);
                if (param.getName().contains(param_name)) {
                    cond.setTemp_flag(true);
                    multi = cond.getPara() == 2;
                    isCode = cond.getFieldName().contains("_code_");
                    isBoolean = cond.getFieldType().equals("boolean") || cond.getFieldType().equals("Boolean");

                    code_type = cond.getCode_type();
                    break;
                }
            }

            if (isCode) {
                param.setParam_type("��������");
                param.setParam_type2("ѡ�����");
                param.setParam_type3(code_type);
                /*
                CodeCellEditorDef de = new CodeCellEditorDef();
                ce = de;
                de.setCode_type(code_type);//(param.getName());
                de.setValueType(0);*/
            } else if (param.getName().contains("���ű���") || param.getName().contains("���Ŵ���")) {
                param.setParam_type("��������");
                param.setParam_type2("ѡ�����");
                /*DeptCodeCellEditorDef de = new DeptCodeCellEditorDef();
                ce = de;
                de.setValueType(0);*/
            } else if (param.getName().contains("��������")) {
                param.setParam_type("��������");
                param.setParam_type2("ѡ������");
                /*DeptCodeCellEditorDef de = new DeptCodeCellEditorDef();
                ce = de;
                de.setValueType(1);
                 */
            }
            //ce.setAllowBlank(multi);
            //param.setCellEditorDef(ce);
            param.setMulti(multi);
        }

        for (Parameter param : arrayOfParameter) {
            ReportPane localReportPane = ReportPanel1.getJWorkSheet();
            Report localReport = localReportPane.getEditingReport();// .getReport();
            ReportParameterAttr localReportParameterAttr = localReport.getWorkBook().getReportParameterAttr();
            if (localReportParameterAttr == null) {
                localReportParameterAttr = new ReportParameterAttr();
                if (localReport.getWorkBook() != null) {
                    localReport.getWorkBook().setReportParameterAttr(localReportParameterAttr);
                } else {
                    localReport.getWorkBook().setReportParameterAttr(localReportParameterAttr);
                }
            }

            boolean bexists = false;
            if (localReportParameterAttr != null) {
                int i = 0;
                int j = localReportParameterAttr.getParameters().length;//.getParameterCount();
                while (i < j) {
                    if (param.getName().equals(localReportParameterAttr.getParameters()[i].getName())) {
                        bexists = true;
                        break;
                    }
                    ++i;
                }
            }
            if (!bexists) {
                localReportParameterAttr.addParameter(param);
            }
        }
        ParamPickerPanel.getParamPickerPanel().showReportParamters();
    }

    private void add_report(ReportDef reportDef1) {
        if (reportDef1 == null) {
            reportDef = (ReportDef) UtilTool.createUIDEntity(ReportDef.class);
        } else {
            reportDef = reportDef1;
        }
        // ���ػ�������Դ�����ļ�--->
        byte[] imageBytes = null;// BBImpl.getBase_datasource(ConfigManager.getConfigManager().getProperty("base.ApplicationServer"));
        if (imageBytes == null) {
            return;
        }
        File file = new File(System.getProperty("user.home") + "/resources/datasource.xml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        BufferedOutputStream output;
        try {
            output = new BufferedOutputStream(new FileOutputStream(file));
            output.write(imageBytes);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        LocalEnv localEnv = new LocalEnv(System.getProperty("user.home"));
        FRContext.setCurrentEnv(localEnv);
        // ���ػ�������Դ�����ļ�---<
        enable_locate_dict = false;
        ParameterReport workSheet = new ParameterReport();
        WorkBook workbook = new WorkBook();
        //WorkSheet worksheet = new WorkSheet();
        workbook.addReport(workSheet);
        createJWorkSheet((ParameterReport) workSheet);
        Grid localGrid = jWorkSheet.getGrid();

        localGrid.setDefaultCellEditor(DSColumn.class, new DSColumnCellEditor());
        localGrid.setDefaultCellEditor(Formula.class, new FormulaCellEditor());
        localGrid.setDefaultCellEditor(ChartCollection.class, new ChartCellEditor());
        localGrid.setDefaultCellEditor(Image.class, new ImageCellEditor());
        localGrid.setDefaultCellEditor(BiasTextPainter.class, new BiasTextPainterCellEditor());
        localGrid.setDefaultFloatEditor(Formula.class, new FormulaFloatEditor());
        localGrid.setDefaultFloatEditor(ChartCollection.class, new ChartFloatEditor());
        localGrid.setDefaultFloatEditor(Image.class, new ImageFloatEditor());
        localGrid.setDefaultCellEditor(SubReport.class, new SubReportCellEditor());
        jWorkSheet.repaint();
        show_report_designer();
        jWorkSheet.updateUI();
        enable_locate_dict = true;
    }

    private void del_report() {
        if (this.jTree.getSelectionPath().getLastPathComponent() == null) {
            return;
        }
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.jTree.getSelectionPath().getLastPathComponent();
        Object obj = node.getUserObject();
        if (!(obj instanceof ReportDef)) {
            return;
        }
        ReportDef reportDef2 = (ReportDef) obj;
        if (JOptionPane.showConfirmDialog(ContextManager.getMainFrame(),
                "ȷ��Ҫɾ��" + reportDef2 + "��?", "ѯ��", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
            return;
        }
        ValidateSQLResult result = null;// BBImpl.delReport(reportDef);
        if (result.getResult() == 0) {
            DefaultMutableTreeNode next_node = ComponentUtil.getNextNode(node);
            node.removeFromParent();
            pnl_content.removeAll();
            reportDef = null;
            jWorkSheet = null;
            if (next_node != null) {
                TreePath tp = new TreePath(next_node.getPath());
                jTree.setSelectionPath(tp);
            }
            jTree.updateUI();
            pnl_content.updateUI();
        } else {
            //FormulaTextDialog.showErrorMsg(result.getMsg(), FormulaTextDialog.error_del_msg);
        }
    }

    private void edit_report(Object obj) {
        if (!(obj instanceof ReportDef)) {
            return;
        }
        reportDef = (ReportDef) obj;
        if (reportDef.getReportDef_key() != null) {
            ReportUtil.download_datasource(reportDef, false);//�޸�Ϊͨ�÷���������
        }
        LocalEnv localEnv = new LocalEnv(System.getProperty("user.home"));
        FRContext.setCurrentEnv(localEnv);
        byte[] imageBytes = null;// BBImpl.getReport_cpt(reportDef.getReportDef_key());
        if (imageBytes == null) {
            return;
        }
        try {
            enable_locate_dict = false;
            InputStream is = new ByteArrayInputStream(imageBytes);
            ParameterReport workSheet = new ParameterReport();
            createJWorkSheet((ParameterReport) workSheet);
            Grid localGrid = jWorkSheet.getGrid();

            localGrid.setDefaultCellEditor(DSColumn.class, new DSColumnCellEditor());
            localGrid.setDefaultCellEditor(Formula.class, new FormulaCellEditor());
            localGrid.setDefaultCellEditor(ChartCollection.class, new ChartCellEditor());
            localGrid.setDefaultCellEditor(Image.class, new ImageCellEditor());
            localGrid.setDefaultCellEditor(BiasTextPainter.class, new BiasTextPainterCellEditor());
            localGrid.setDefaultFloatEditor(Formula.class, new FormulaFloatEditor());
            localGrid.setDefaultFloatEditor(ChartCollection.class, new ChartFloatEditor());
            localGrid.setDefaultFloatEditor(Image.class, new ImageFloatEditor());
            localGrid.setDefaultCellEditor(SubReport.class, new SubReportCellEditor());
            TemplateImporter templateImporter = new TemplateImporter();
            jWorkSheet.setEditingReport(templateImporter.generate(is).getReport(0));//(workSheet);
            jWorkSheet.repaint();
            show_report_designer();
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        } finally {
            enable_locate_dict = true;
        }

    }

    private void show_report_designer() {
        if (cur_module != null) {
            FrSQLBuilderWizardAction.module_code = cur_module.module_code;
        }
        if (tableDataTreePane == null) {
            tableDataTreePane = new TableDataTreePane();
        }
        if (serverTableDataTreePane == null) {
            serverTableDataTreePane = new TableDataTreePane();
        }
        if (tp_container.getComponentCount() < 3) {
            tp_container.add("����Դ", tableDataTreePane);
            tp_container.add("����", serverTableDataTreePane);
        }
        serverTableDataTreePane.populate(new ReportPaneTableDataOP(jWorkSheet));
        tableDataTreePane.populate(new EnvTableDataOP());
        new ReportPaneDropTarget(jWorkSheet);
        pnl_content.removeAll();
        JPanel tmp_content = new JPanel(new BorderLayout());
        tmp_content.add(jWorkSheet, BorderLayout.CENTER);


        //////////////////////////////////////
        JToolBar tool_bar = new JToolBar();
        tool_bar.setFloatable(false);
        addReportAction(tool_bar, new PageSetupAction(), "ҳ������");
        addReportAction(tool_bar, new InsertGroupingReportAction(), "���鱨��");
        addReportAction(tool_bar, new InsertCrossReportAction(), "���汨��");
        addReportAction(tool_bar, new ReportParameterAction(), "��������");
        addReportAction(tool_bar, new ReportColumnsAction(), "��������");
        /*
        addReportAction(tool_bar, new PrintReportAction());
        addReportAction(tool_bar, new DesignReportAction());
        addReportAction(tool_bar, new PagePreviewAction());
         */
        tool_bar.addSeparator();
//        addReportAction(tool_bar, new UndoAction(), "����");
//        addReportAction(tool_bar, new RedoAction(), "����");
//        tool_bar.addSeparator();
        addReportAction(tool_bar, new CutAction(), "����");
        addReportAction(tool_bar, new CopyAction(), "����");
        addReportAction(tool_bar, new PasteAction(), "ճ��");
        addReportAction(tool_bar, new BrushAction(), "��ʽˢ");
        tool_bar.addSeparator();
        buildCellPop(tool_bar);
        buildFloatPop(tool_bar);
        tool_bar.addSeparator();
        addReportAction(tool_bar, new EditCellAction(), "�༭");
        addReportAction(tool_bar, new StyleAction(), "��ʽ(S)");
        addReportAction(tool_bar, new CellAttributeAction(), "��Ԫ������(A)");
//        addReportAction(tool_bar, new ForegroundHighlightAction(), "��Ԫ����������(H)");
        tool_bar.addSeparator();
        addReportAction(tool_bar, new ReportBackgroundAction(), "������������");
        addReportAction(tool_bar, new ReportHeaderAction(), "����ҳü����");
        addReportAction(tool_bar, new ReportFooterAction(), "����ҳ������");
        addReportAction(tool_bar, new ReportWriteAttrAction(), "���������");


        JToolBar toolbar2 = new JToolBar();
        toolbar2.setFloatable(false);
        addReportAction(toolbar2, new ReportFontBoldAction(), "�Ӵ�");
        addReportAction(toolbar2, new ReportFontItalicAction(), "б��");
        addReportAction(toolbar2, new ReportFontUnderlineAction(), "�»���");
        toolbar2.addSeparator();
        addReportAction(toolbar2, new AlignmentLeftAction("pa"), "�����");
        addReportAction(toolbar2, new AlignmentCenterAction("pa"), "���˶���");
        addReportAction(toolbar2, new AlignmentRightAction("pa"), "�Ҷ���");
        toolbar2.addSeparator();
        addReportAction(toolbar2, new MergeCellAction(), "�ϲ���Ԫ��");
        addReportAction(toolbar2, new UnmergeCellAction(), "��ֵ�Ԫ��");
        addReportAction(toolbar2, new CellWriteAttrAction(), "��������ؼ�");
        toolbar2.addSeparator();
        addReportAction(toolbar2, new HyperlinkAction(), "��������(K)");
        toolbar2.addSeparator();
        addReportAction(toolbar2, new ReportFontNameAction(), "����", 100, 27);
        addReportAction(toolbar2, new ReportFontSizeAction(), "�ֺ�", 50, 27);
        toolbar2.addSeparator();
        addReportAction(toolbar2, new BorderAction(), "�߿�");
        addReportAction(toolbar2, new StyleBackgroundAction(), "����");
        addReportAction(toolbar2, new ReportFontForegroundAction(), "ǰ��");

        addReportAction(toolbar2, new DanWeiAction(), "��λ", 50, 27);
        CellElementEditorPane cellElementEditorPane = new CellElementEditorPane();
        cellElementEditorPane.addListenersToReportPane(jWorkSheet);
        cellElementEditorPane.populate(jWorkSheet);
        JPanel tmp_pnl = new JPanel(new BorderLayout());
        tmp_pnl.add(tool_bar, BorderLayout.NORTH);
        tmp_pnl.add(toolbar2, BorderLayout.CENTER);
        tmp_pnl.add(cellElementEditorPane, BorderLayout.SOUTH);

        tmp_content.add(tmp_pnl, BorderLayout.NORTH);

        if (cellCardAttrPane != null) {
            tp_bottom.remove(cellCardAttrPane);
        }
        cellCardAttrPane = new CellCardAttrPane();
//        cellCardAttrPane.addListenersToReportPane(jWorkSheet);
        cellCardAttrPane.populate(jWorkSheet);
        tp_bottom.add("��չ", cellCardAttrPane);
        JSplitPane sp_tmp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tmp_content, tp_bottom);
        sp_tmp.setDividerLocation(pnl_content.getSize().height - 125);
        pnl_content.add(sp_tmp, BorderLayout.CENTER);
        //////////////////////////////////////
        pnl_content.updateUI();


        jWorkSheet.getUndoManager().discardAllEdits();
    }

    public static void excute_report(ReportDef reportDef) {
        excute_report(reportDef, null);
    }

    public static void initUserViewList() {
        replaceViewList.clear();
        if (userViewlist == null) {
            return;
        }
        userViewlist.clear();
    }
    public static int report_index = 0;

    private static void excute_inner_report(ReportDef reportDef, PreviewPane previewPane) {
        Env old_env = FRContext.getCurrentEnv();
        try {
            //JOptionPane.showConfirmDialog(com, com.getSelectedItem());
            //ReportDef reportDef = (ReportDef)com.getSelectedItem();
            ReportUtil.download_datasource(reportDef, true);

            LocalEnv localEnv = new LocalEnv(System.getProperty("user.home") + "/exec");
            FRContext.setCurrentEnv(localEnv);
            byte[] imageBytes = null;// BBImpl.getReport_cpt(reportDef.getReportDef_key());//us.getReport(rep_name);
            if (imageBytes == null) {
                return;
            }

            enable_locate_dict = false;
            InputStream is = new ByteArrayInputStream(imageBytes);
            TemplateImporter templateImporter = new TemplateImporter();
            //final PreviewPane previewPane = new PreviewPane();
            ReportPage.resetExtract_page_sum();

            if (previewPane.print(templateImporter.generate(is), previewPane.parameterMap) != 0) {
                enable_locate_dict = true;
                return;
            }

            enable_locate_dict = true;
        } catch (Exception ex) {
            FRContext.setCurrentEnv(old_env);
        }
    }

    public static void excute_report(final ArrayList<ReportDef> al) {
        JFrame jf = ContextManager.getMainFrame();
        Map parameterMap2 = null;
        report_index = 0;
        ReportDef reportDef = al.get(0);

        ReportPage.user_name = UserContext.person_name;
        ReportPage.a01_key = UserContext.person_key;
        ReportPage.a0190 = UserContext.person_code;
        ReportUtil.download_datasource(reportDef, true);
        final Env old_env = FRContext.getCurrentEnv();
        LocalEnv localEnv = new LocalEnv(System.getProperty("user.home") + "/exec");
        FRContext.setCurrentEnv(localEnv);
        byte[] imageBytes = null;// BBImpl.getReport_cpt(reportDef.getReportDef_key());//us.getReport(rep_name);
        if (imageBytes == null) {
            return;
        }
        try {
            enable_locate_dict = false;
            InputStream is = new ByteArrayInputStream(imageBytes);
            TemplateImporter templateImporter = new TemplateImporter();
            final PreviewPane previewPane = new PreviewPane();
            ReportPage.resetExtract_page_sum();
            if (parameterMap2 != null && parameterMap2.size() > 0) {
                if (previewPane.print(templateImporter.generate(is), parameterMap2) != 0) {
                    enable_locate_dict = true;
                    return;
                }
                //WorkBookTemplate excuteReport = templateImporter.generate(is).execute(parameterMap2);
                //previewPane.print(excuteReport);
                //if (previewPane.print(templateImporter.generate(is), parameterMap2) != 0) {
                //    enable_locate_dict = true;
                //    return;
                //}
            } else {
                if (previewPane.print(templateImporter.generate(is)) != 0) {
                    enable_locate_dict = true;
                    return;
                }
                //WorkBookTemplate excuteReport = templateImporter.generate(is).execute(new HashMap());
                //previewPane.print(excuteReport);
                //if (previewPane.print(templateImporter.generate(is)) != 0) {
                //    enable_locate_dict = true;
                //    return;
                //}
            }
            enable_locate_dict = true;
            final JButton btnExport = new JButton("����");
            ComponentUtil.setSysFuntion(btnExport, "ReportMng.btnExport");
            btnExport.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    ReportUtil.exportExcel(previewPane, btnExport);
                }
            });
            previewPane.getJpanel().add(btnExport);
            final JComboBox cb_zoom = new JComboBox(new Object[]{"100", "90", "80", "75", "50", "25"});
            cb_zoom.setPreferredSize(btnExport.getPreferredSize());
            cb_zoom.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    previewPane.innerPreviewPane.setD_zomm(Double.valueOf(cb_zoom.getSelectedItem().toString()) / 100.0d);
                }
            });
            previewPane.getJpanel().add(new JLabel("��ʾ������"));
            previewPane.getJpanel().add(cb_zoom);
            cb_zoom.setEnabled(!previewPane.bform);
            final JCheckBox ch_reverse = new JCheckBox("��ת", false);
            ch_reverse.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    previewPane.innerPreviewPane.setReverse(ch_reverse.isSelected());
                    previewPane.updateUI();
                }
            });

            final JButton btnCheck = new JButton("У��");
            btnCheck.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    previewPane.checkReport();
                }
            });
            previewPane.getJpanel().add(btnCheck);

            JPanel pnl_navigate = new JPanel(new FlowLayout());
            JButton btn_pre = new JButton("��һ������");
            final JLabel lbl_info = new JLabel("1/" + al.size());
            JButton btn_next = new JButton("��һ������");
            pnl_navigate.add(btn_pre);
            pnl_navigate.add(lbl_info);
            pnl_navigate.add(btn_next);
            btn_pre.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (report_index == 0) {
                        return;
                    }
                    report_index = Math.max(0, report_index - 1);

                    lbl_info.setText("" + (report_index + 1) + "/" + al.size());

                    ReportPage.begin_page_num = ReportPage.begin_page_num - previewPane.innerPreviewPane.reportPage.getTotalPages();

                    excute_inner_report(al.get(report_index), previewPane);
                }
            });
            btn_next.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (report_index == al.size() - 1) {
                        return;
                    }
                    report_index = Math.min(al.size() - 1, report_index + 1);

                    lbl_info.setText("" + (report_index + 1) + "/" + al.size());

                    ReportPage.begin_page_num = ReportPage.begin_page_num + previewPane.innerPreviewPane.reportPage.getTotalPages();

                    excute_inner_report(al.get(report_index), previewPane);
                }
            });

            JButton btn_print_all = new JButton("��ӡȫ��");

            btn_print_all.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    int from_index = 0;
                    if (report_index == 0) {
                        try {
                            PrintUtils.print(previewPane.getReportPageSet().getTraversingCase());
                        } catch (PrinterException printerexception) {
                            JOptionPane.showMessageDialog(previewPane, printerexception.getMessage());
                            return;
                        }
                        from_index = 1;
                    } else {
                        from_index = 0;
                        ReportPage.begin_page_num = 0;
                    }
                    for (report_index = from_index; report_index < al.size(); report_index++) {
                        lbl_info.setText("" + (report_index + 1) + "/" + al.size());
                        if (report_index > 0) {
                            ReportPage.begin_page_num = ReportPage.begin_page_num + previewPane.innerPreviewPane.reportPage.getTotalPages();
                        }
                        excute_inner_report(al.get(report_index), previewPane);

                        try {
                            PrintUtils.print(previewPane.getReportPageSet().getTraversingCase());
                        } catch (PrinterException printerexception) {
                            JOptionPane.showMessageDialog(previewPane, printerexception.getMessage());
                            return;
                        }
                    }
                }
            });
            pnl_navigate.add(btn_print_all);
            JButton btnPrintSelect = new JButton("");
            ComponentUtil.setIcon(btnPrintSelect, "printselect.png");
            ComponentUtil.setSize(btnPrintSelect, 90, 22);
            pnl_navigate.add(btnPrintSelect);
            btnPrintSelect.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    JCheckBoxList jcblReports = new JCheckBoxList(al);
                    jcblReports.setAllowRightItem(true);
                    JPanel pnlPrintSelect = new JPanel(new BorderLayout());
                    pnlPrintSelect.add(new JScrollPane(jcblReports));
                    pnlPrintSelect.setPreferredSize(new Dimension(400, 300));
                    List<Integer> reportIndexs = new ArrayList();
                    if (ModalDialog.doModal(previewPane, pnlPrintSelect, "ѡ����Ҫ��ӡ�ı�����")) {
                        for (int i : jcblReports.getCheckedIndices()) {
                            reportIndexs.add(i);
                        }
                    }
                    if (reportIndexs.isEmpty()) {
                        return;
                    }
                    int from_index = 0;
                    int first_index = reportIndexs.get(0);
                    if (report_index == first_index) {
                        try {
                            PrintUtils.print(previewPane.getReportPageSet().getTraversingCase());
                        } catch (Exception printerexception) {
                            JOptionPane.showMessageDialog(previewPane, printerexception.getMessage());
                            return;
                        }
                        from_index = 1;
                    } else {
                        from_index = 0;
                        ReportPage.begin_page_num = 0;
                    }
                    for (first_index = from_index; first_index < al.size(); first_index++) {
                        if (!reportIndexs.contains(first_index)) {
                            continue;
                        }
                        if (first_index > 0) {
                            ReportPage.begin_page_num = ReportPage.begin_page_num + previewPane.innerPreviewPane.reportPage.getTotalPages();
                        }
                        excute_inner_report(al.get(first_index), previewPane);
                        try {
                            PrintUtils.print(previewPane.getReportPageSet().getTraversingCase());
                        } catch (Exception printerexception) {
                            JOptionPane.showMessageDialog(previewPane, printerexception.getMessage());
                            return;
                        }
                    }
                }
            });
            previewPane.getJpanel().add(pnl_navigate);
            ReportPage.report_valid = true;
            if (!previewPane.bform) {
                previewPane.checkReport();
            }
            JFrame fm = ModelFrame.showModel(jf, previewPane, true, reportDef.getReport_name() + " ִ�н��", jf.getToolkit().getScreenSize().width - 50, jf.getToolkit().getScreenSize().height - 50);
            fm.setExtendedState(JFrame.MAXIMIZED_BOTH);
            fm.setLocation(0, 0);

            fm.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosed(WindowEvent e) {
                    FRContext.setCurrentEnv(old_env);
                }
            });
        } catch (Exception e) {
            FRContext.setCurrentEnv(old_env);
            log.error(e);
            e.printStackTrace();
        } finally {
        }
    }

    // �ṩ���ⲿ���򼯳ɱ���ʹ�ã��������Ϊ�������ƺͲ����б�
    // �������Ĳ����������б�������Ĳ�������ֱ����ʾ������������ʾ������������û�����δָ���Ĳ�������
    public static boolean excute_report(JFrame jf, ReportDef reportDef, final Map parameterMap2, boolean enable_uncomment, boolean showFrame, boolean enableSave) {
        if (userViewlist == null) {
            if (UserContext.isSA) {
                userViewlist = new ArrayList();
            } else {
                userViewlist = CommUtil.fetchEntities("from Table2View tv where tv.roleid='" + UserContext.rolea01_key + "'");
            }
        }
        replaceViewList.clear();
        String module_code = reportDef.getModuleInfo().getModule_code();
        for (Object obj : userViewlist) {
            Table2View tv = (Table2View) obj;
            if ("Emp".equalsIgnoreCase(module_code)) {
                if (tv.getTablename().equals("C21")) {
                    continue;
                }
            } else if ("Pay".equalsIgnoreCase(module_code)) {
                if (tv.getTablename().equals("A01")) {
                    continue;
                }
            }
            replaceViewList.add(tv);
        }
        final byte[] imageBytes = null;//BBImpl.getReport_cpt(reportDef.getReportDef_key());
        if (imageBytes == null) {
            return false;
        }
        ReportPage.user_name = UserContext.person_name;
        ReportPage.a01_key = UserContext.person_key;
        ReportPage.a0190 = UserContext.person_code;
        ReportUtil.download_datasource(reportDef, true);
        final Env oldEnv = FRContext.getCurrentEnv();
        LocalEnv localEnv = new LocalEnv(System.getProperty("user.home") + "/exec");
        FRContext.setCurrentEnv(localEnv);
        try {
            enable_locate_dict = false;
            com.fr.data.impl.TableDataAdapter.enable_uncomment = enable_uncomment;
            final InputStream is = new ByteArrayInputStream(imageBytes);
            final TemplateImporter templateImporter = new TemplateImporter();
            final PreviewPane previewPane = new PreviewPane();
            ReportPage.resetExtract_page_sum();
            if (parameterMap2 != null && parameterMap2.size() > 0) {
                if (previewPane.print(templateImporter.generate(is), parameterMap2) != 0) {
                    enable_locate_dict = true;
                    FRContext.setCurrentEnv(oldEnv);
                    return false;
                }
                //WorkBookTemplate excuteReport = templateImporter.generate(is).execute(parameterMap2);
                //previewPane.print(excuteReport);
                //if (previewPane.print(templateImporter.generate(is), parameterMap2) != 0) {
                //    enable_locate_dict = true;
                //    return;
                //}
            } else {
                if (previewPane.print(templateImporter.generate(is)) != 0) {
                    enable_locate_dict = true;
                    FRContext.setCurrentEnv(oldEnv);
                    return false;
                }
//                WorkBookTemplate excuteReport = templateImporter.generate(is).execute(new HashMap());
                //previewPane.print(excuteReport);
                //if (previewPane.print(templateImporter.generate(is)) != 0) {
                //    enable_locate_dict = true;
                //    return;
                //}
            }
            enable_locate_dict = true;
            if (!showFrame) {
                return true;
            }
            final JButton btnExport = new JButton("����");
            JPanel pnl = previewPane.getJpanel();
            ComponentUtil.setSysFuntion(btnExport, "ReportMng.btnExport");
            btnExport.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    ReportUtil.exportExcel(previewPane, btnExport);
                }
            });
            pnl.add(btnExport);
            final JButton btnCheck = new JButton("У��");
            btnCheck.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    previewPane.checkReport();
                }
            });
            pnl.add(btnCheck);
            final JButton btnDefine = new JButton("ģ��΢��");
            btnDefine.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        final InputStream is = new ByteArrayInputStream(imageBytes);
                        final TemplateImporter templateImporter = new TemplateImporter();
                        ParameterReport workSheet = new ParameterReport();
                        final DesignParameterJWorkSheet jWorkSheet = new DesignParameterJWorkSheet(workSheet);

                        jWorkSheet.setEditingReport(templateImporter.generate(is).getReport(0));
                        jWorkSheet.repaint();
                        JPanel pnl = new JPanel(new BorderLayout());
                        JToolBar toolbar = new JToolBar();
                        JButton btnExcute = new JButton("����ִ��");
                        toolbar.setFloatable(false);
                        toolbar.add(btnExcute);
                        pnl.add(toolbar, BorderLayout.NORTH);
                        pnl.add(jWorkSheet);
                        final ModelFrame mf = ModelFrame.showModel((ModelFrame) JOptionPane.getFrameForComponent(btnDefine), pnl, true, "ģ��΢��", 800, 600, false);
                        btnExcute.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                enable_locate_dict = false;
                                HashMap map = new HashMap();
                                for (Object obj : previewPane.parameterMap.keySet()) {
                                    map.put(obj, previewPane.parameterMap.get(obj));
                                }
                                previewPane.print(jWorkSheet.getEditingReport().getWorkBook().execute(map));
                                ReportPage.resetExtract_page_sum();
                                previewPane.updateUI();
                                enable_locate_dict = true;
                                ModelFrame.close(mf);
                            }
                        });
                        mf.setVisible(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            pnl.add(btnDefine);
            Component[] coms = pnl.getComponents();
            for (Component c : coms) {
                if (c instanceof JButton) {
                    JButton btn = (JButton) c;
                    String text = btn.getText();
                    String iconName = "";
                    int width = 25;
                    if ("����".equals(text)) {
                        btn.setName("save");
                        iconName = "save.png";
                        width = 40;
                        btn.setEnabled(enableSave);
                    } else if ("����ֽ��".equals(text)) {
                        iconName = "setpage.png";
                        width = 70;
                    } else if ("����".equals(text)) {
                        iconName = "export.png";
                        width = 40;
                    } else if ("У��".equals(text)) {
                        iconName = "check.png";
                        width = 40;
                    } else if ("ģ��΢��".equals(text)) {
                        width = 100;
                    }
                    if (!iconName.equals("")) {
                        btn.setText(null);
                        ComponentUtil.setIcon(btn, iconName);
                    }
                    btn.setPreferredSize(new Dimension(width, 22));
                }
            }
            final JComboBox cb_zoom = new JComboBox(new Object[]{"100", "90", "80", "75", "50", "25"});
            cb_zoom.setPreferredSize(btnExport.getPreferredSize());
            cb_zoom.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    previewPane.innerPreviewPane.setD_zomm(Double.valueOf(cb_zoom.getSelectedItem().toString()) / 100.0d);
                }
            });
            pnl.add(new JLabel("��ʾ������"));
            pnl.add(cb_zoom);
            ComponentUtil.setSize(cb_zoom, 50, 22);
//            cb_zoom.setEnabled(!previewPane.bform);
            final JCheckBox ch_reverse = new JCheckBox("��ת", false);
            ch_reverse.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    previewPane.innerPreviewPane.setReverse(ch_reverse.isSelected());
                    previewPane.updateUI();
                }
            });
            pnl.add(ch_reverse);
            ModelFrame fm = ModelFrame.showModel(jf, previewPane, true, reportDef.getReport_name() + " ִ�н��", jf.getToolkit().getScreenSize().width - 50, jf.getToolkit().getScreenSize().height - 50, false);
            fm.setExtendedState(JFrame.MAXIMIZED_BOTH);
            fm.setLocation(0, 0);
            fm.addIPickWindowCloseListener(new IPickWindowCloseListener() {

                @Override
                public void pickClose() {
                    nameHash.clear();
                    FRContext.setCurrentEnv(oldEnv);
                }
            });
            fm.setVisible(true);
            return true;
        } catch (Exception e) {
            FRContext.setCurrentEnv(oldEnv);
            log.error(e);
        } finally {
            com.fr.data.impl.TableDataAdapter.enable_uncomment = true;
            enable_locate_dict = true;
        }
        return false;
    }

    private static ColumnGroup getColumnGroupBy(FTable tab, FBaseTableColumn tc) {
        if (tab.leftHeader != null) {
            Enumeration enu = tab.leftHeader.getColumnGroups(tc);
            if (enu != null) {
                return (ColumnGroup) enu.nextElement();
            }
        }
        if (tab.header != null) {
            Enumeration enu = tab.header.getColumnGroups(tc);
            if (enu != null) {
                return (ColumnGroup) enu.nextElement();
            }
        }
        return null;
    }

    // �ṩ������ģ��ʹ�ã�ͨ������FTable���ɱ���
    public static void previewBy(String title, FTable tab) {
        // ���ػ�������Դ�����ļ�--->
        byte[] imageBytes = null;// BBImpl.getBase_datasource(ConfigManager.getConfigManager().getProperty("base.ApplicationServer"));
        if (imageBytes == null) {
            return;
        }
        File file = new File(System.getProperty("user.home") + "/exec/resources/datasource.xml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        BufferedOutputStream output;
        try {
            output = new BufferedOutputStream(new FileOutputStream(file));
            output.write(imageBytes);
            output.close();
        } catch (Exception e) {
            log.error(e);
            return;
        }
        Env old_env = FRContext.getCurrentEnv();
        LocalEnv localEnv = new LocalEnv(System.getProperty("user.home") + "/exec");
        FRContext.setCurrentEnv(localEnv);
        try {
            // ���ػ�������Դ�����ļ�---<
            ParameterReport workSheet = new ParameterReport();
            WorkBook workbook = new WorkBook();
            workbook.addReport(workSheet);
            FRContext.getDatasourceManager().clearAllTableData();
            FRContext.getDatasourceManager().putTableData("ds", new TableModelTableData(tab.getModel()));


            boolean mutil_header = (tab.leftHeader != null && tab.leftHeader.columnGroups != null && tab.leftHeader.columnGroups.size() > 0)
                    || (tab.header != null && tab.header.columnGroups != null && tab.header.columnGroups.size() > 0);

            Style style_center = Style.BORDER_STYLE;
            try {
                style_center = (Style) Style.BORDER_STYLE.clone();
                style_center = style_center.deriveHorizontalAlignment(0);
            } catch (CloneNotSupportedException ex) {
            }
            Style style_left = style_center.deriveHorizontalAlignment(2);
            Style style_right = style_center.deriveHorizontalAlignment(4);

            int row_num = 2;
            int title_row_num = row_num + (mutil_header ? 1 : 0);
            int value_row_num = row_num + (mutil_header ? 2 : 1);
            // ����Ϊ��һ�У��м��һ�У�������Ϊ�б��⣬������Ϊ����
            for (int i = 0; i < tab.getColumnCount(); i++) {
                workSheet.setColumnWidth(i, tab.getColumnModel().getColumn(i).getWidth());
                workSheet.setCellValue(i, title_row_num, tab.getColumnModel().getColumn(i).getTitle());
                workSheet.getCellElement(i, title_row_num).setStyle(style_center);

                if (mutil_header) { // ����Ƕ���ͷ������һ����ͷ
                    ColumnGroup cg = getColumnGroupBy(tab, tab.getColumnModel().getColumn(i));
                    if (cg != null) {
                        workSheet.setCellValue(i, row_num, cg.getTitle());
                        workSheet.getCellElement(i, row_num).setStyle(style_center);
                    } else {
                        workSheet.setCellValue(i, row_num, tab.getColumnModel().getColumn(i).getTitle());
                        workSheet.getCellElement(i, row_num).setStyle(style_center);
                    }
                }

                DSColumn dsc = new DSColumn();
                dsc.setDSName("ds");
                TableDataColumn tdc = TableDataColumn.createColumn(i + 1);//tab.getColumnModel().getColumn(i).getTitle()
                dsc.setColumn(tdc);
                workSheet.setCellValue(i, value_row_num, dsc);
                CellExpandAttr cea = new CellExpandAttr();
                cea.setDirection((byte) 0);
                workSheet.getCellElement(i, value_row_num).setCellExpandAttr(cea);
                workSheet.getCellElement(i, value_row_num).setStyle(style_center);


//                if (tab.getRowCount() > 0) {
//                    Component com = tab.getColumnModel().getColumn(i).getCellRenderer().getTableCellRendererComponent(tab.getRightActiveTable(), "", false, false, 0, i);
//                    if (com instanceof JTextField) {
//                        JTextField tf = (JTextField) com;
//                        if (tf.getHorizontalAlignment() == 2) {
//                            workSheet.getCellElement(i, value_row_num).setStyle(style_left);
//                        } else if (tf.getHorizontalAlignment() == 4) {
//                            workSheet.getCellElement(i, value_row_num).setStyle(style_right);
//                        }
//                    }
//                }



            }
            if (mutil_header) {
                // �ϲ���ͷ���¸���
                for (int i = 0; i < tab.getColumnCount(); i++) {
                    Object val1 = workSheet.getCellElement(i, row_num).getValue();
                    Object val2 = workSheet.getCellElement(i, title_row_num).getValue();
                    if (val1 != null && val1.equals(val2)) {
                        workSheet.merge(row_num, title_row_num, i, i);
                    }
                }

                // �ϲ��ڶ�����ͷ���Ҹ���
                int col_num = 0;
                while (col_num < tab.getColumnCount()) {
                    int col_num2 = col_num;
                    Object val1 = workSheet.getCellElement(col_num, row_num).getValue();
                    if (val1 != null) {
                        while (col_num2 < tab.getColumnCount()) {
                            Object val2 = workSheet.getCellElement(col_num2, row_num).getValue();
                            if (!val1.equals(val2)) {
                                break;
                            }
                            col_num2++;
                        }
                    }
                    if (col_num2 > (col_num + 1)) {
                        workSheet.merge(row_num, row_num, col_num, col_num2 - 1);
                    }
                    col_num = col_num2;
                }
            }

            workSheet.merge(0, 0, 0, tab.getColumnCount() - 1);
            workSheet.setCellValue(0, 0, title);
            style_center = Style.DEFAULT_STYLE;
            try {
                style_center = (Style) Style.DEFAULT_STYLE.clone();
                style_center = style_center.deriveHorizontalAlignment(0);
            } catch (CloneNotSupportedException ex) {
            }
            FRFont font = FRFont.getInstance(new FontUIResource("����", Font.BOLD, 18));
            style_center = style_center.deriveFRFont(font);
            workSheet.getCellElement(0, 0).setStyle(style_center);

            final PreviewPane previewPane = new PreviewPane();
            ReportPage.resetExtract_page_sum();

            previewPane.print(workbook);

            final JButton btnExport = new JButton("����");
            btnExport.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    ReportUtil.exportExcel(previewPane, btnExport);
                }
            });
            previewPane.getJpanel().add(btnExport);
            final JComboBox cb_zoom = new JComboBox(new Object[]{"100", "90", "80", "75", "50", "25"});
            cb_zoom.setPreferredSize(btnExport.getPreferredSize());
            cb_zoom.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    previewPane.innerPreviewPane.setD_zomm(Double.valueOf(cb_zoom.getSelectedItem().toString()) / 100.0d);
                }
            });
            previewPane.getJpanel().add(new JLabel("��ʾ������"));
            previewPane.getJpanel().add(cb_zoom);
            cb_zoom.setEnabled(!previewPane.bform);
            final JCheckBox ch_reverse = new JCheckBox("��ת", false);
            ch_reverse.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    previewPane.innerPreviewPane.setReverse(ch_reverse.isSelected());
                    previewPane.updateUI();
                }
            });

            JFrame jf = (JFrame) JOptionPane.getFrameForComponent(tab);
            JFrame fm = ModelFrame.showModel(jf, previewPane, true, title, jf.getToolkit().getScreenSize().width - 50, jf.getToolkit().getScreenSize().height - 50);
            fm.setExtendedState(JFrame.MAXIMIZED_BOTH);
            fm.setLocation(0, 0);
        } finally {
            FRContext.setCurrentEnv(old_env);
        }
    }

    public static void excute_report(ReportDef reportDef, Map parameterMap2) {
        excute_report(ContextManager.getMainFrame(), reportDef, parameterMap2, true, true, true);
    }

    public static void excute_report(JFrame jf, ReportDef reportDef, Map parameterMap2) {
        excute_report(jf, reportDef, parameterMap2, true, true, true);
    }

    private void excute_report() {
        long time1 = System.currentTimeMillis();
        ArrayList<ReportDef> al = new ArrayList<ReportDef>();
        for (TreePath tp : this.jTree.getSelectionPaths()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
            Object obj = node.getUserObject();
            if (!(obj instanceof ReportDef)) {
                continue;
            }
            al.add((ReportDef) obj);
        }
        if (al.isEmpty()) {
            return;
        }
        if (al.size() == 1) {
            reportDef = (ReportDef) al.get(0);
            excute_report(reportDef);
        } else if (al.size() > 1) {
            excute_report(al);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("ִ�б�����" + al.get(0).getReport_name() + ";��ʱ��" + (time2 - time1));
        /*
        if (this.jTree.getSelectionPath().getLastPathComponent() == null) {
        return;
        }
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.jTree.getSelectionPath().getLastPathComponent();
        Object obj = node.getUserObject();
        if (!(obj instanceof ReportDef)) {
        return;
        }
        reportDef = (ReportDef) obj;
        excute_report(reportDef);
         */
    }

//    private void excute_report() {
//        if (this.jTree.getSelectionPath().getLastPathComponent() == null) {
//            return;
//        }
//        DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.jTree.getSelectionPath().getLastPathComponent();
//        Object obj = node.getUserObject();
//        if (!(obj instanceof ReportDef)) {
//            return;
//        }
//        reportDef = (ReportDef) obj;
//        excute_report(reportDef);
//    }
    public static Object changeLabel(String column_name, Object value) {

        com.fr.data.TableData tabledata = org.jhrcore.client.report.ReportPanel1.getJWorkSheet().getEditingReport().getWorkBook().getTableData(column_name);
        if (tabledata == null) {
            return value;
        }
        com.fr.dialog.NameObject no = new com.fr.dialog.NameObject(column_name, tabledata);
        com.fr.data.impl.TableDataDictionary tabledatadictionary = new com.fr.data.impl.TableDataDictionary();
        tabledatadictionary.setTableData(new com.fr.data.impl.NameTableData(((com.fr.dialog.NameObject) no).getName()));
        tabledatadictionary.setKeyColumnIndex(0);
        tabledatadictionary.setValueColumnIndex(1);
        Object re_value = tabledatadictionary.get(value, Calculator.createCalculator());
        re_value = re_value == null ? "" : re_value;
        return re_value;
    }


    public static String getNewQuery(String sql, Parameter[] parameters) {
        if (replaceViewList.isEmpty() && !UserContext.isSA) {
            if (userViewlist == null) {
                userViewlist = CommUtil.fetchEntities("from Table2View tv where tv.roleid='" + UserContext.rolea01_key + "' ");
            }
            replaceViewList.addAll(userViewlist);
        }
        if (replaceViewList.size() > 0) {
            String tmp_sql = sql;
//            String[] pres = new String[]{" ", "\\,", "\n", "\\=", "\\("};
            String[] pres = new String[]{" ", "\\|", "\\+", "\\-", "\\*", "\\/", "	", "\\,", "\n", "\\=", "\\("};
            String[] atts = new String[]{" ", "\\,", "\n", "\\.", "\\=", "\\)"};

            for (Table2View tv : replaceViewList) {
                for (String pre : pres) {
                    for (String att : atts) {
                        String regEx = pre + tv.getTablename() + att; //"a+"; //��ʾһ������a
                        Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(tmp_sql);
                        tmp_sql = m.replaceAll(pre + tv.getViewname() + att);
                    }
                }
            }
            sql = tmp_sql;
        }

        if (parameters == null) {
            parameters = new Parameter[]{};
        }
        StringBuffer stringbuffer = new StringBuffer();
        java.util.HashMap hashmap = new java.util.HashMap();
//        System.out.println("AAAAAAAAAAAAsql:" + sql);
        for (int i = 0; i < parameters.length; i++) {
            hashmap.put(parameters[i].getName(), parameters[i].getValue());
//            System.out.println("parameter name and vlaue:" + parameters[i].getName() + "=" + parameters[i].getValue());
        }

        List<String> queryFragList = new ArrayList<String>();
        Pattern deprecatedParaPattern = Pattern.compile("\\[\\?[^\\]\\?]*\\?\\]");
        Matcher matcher = deprecatedParaPattern.matcher(sql);
        int last_ind = 0;
        do {
            if (!matcher.find()) {
                String tmp = sql.substring(last_ind, sql.length());
                if (tmp != null && !tmp.equals("")) {
                    queryFragList.add(tmp);
                }
                break;
            }
            queryFragList.add(sql.substring(last_ind, matcher.start()));
            last_ind = matcher.end();
            queryFragList.add(matcher.group());
        } while (true);

        com.fr.report.script.Calculator cal = null;
        for (int j = 0; j < queryFragList.size(); j++) {
            String s = (String) queryFragList.get(j);
//            System.out.println("AAAAAAAAAAAA:" + s);
            if (s.startsWith("[?") && s.endsWith("?]")) {
                int k = s.indexOf("[?");
                int l = s.indexOf("?]");
                String s1 = s.substring(k + 2, l);
                s1 = s1.replaceAll("\\|", "");
//                System.out.println("parameters name:" + s1);
                Object obj = hashmap.get(s1);
//                System.out.println("parameter value" + obj);
                if (obj == null) {
                    obj = "";
                }
                if (obj != null) {
                    if (obj instanceof com.fr.report.cellElement.Formula) {
                        com.fr.report.cellElement.Formula formula = (com.fr.report.cellElement.Formula) obj;
                        if (cal == null) {
                            cal = com.fr.report.script.Calculator.createCalculator();
                        }
                        try {
                            Object obj1 = cal.eval(formula.getContent().substring(1));
                            stringbuffer.append(obj1);
                        } catch (com.fr.report.script.core.parser.UtilEvalError utilevalerror) {
                            stringbuffer.append(formula);
                        }
                    } else {
                        String tmp_s = stringbuffer.toString();
                        last_ind = 0;
                        last_ind = Math.max(last_ind, tmp_s.replaceAll("\n", " ").replaceAll("\\(", " ").replaceAll("\\)", " ").toLowerCase().lastIndexOf(" or ") + 4);
                        last_ind = Math.max(last_ind, tmp_s.replaceAll("\n", " ").replaceAll("\\(", " ").replaceAll("\\)", " ").toLowerCase().lastIndexOf(" and ") + 5);
                        last_ind = Math.max(last_ind, tmp_s.replaceAll("\n", " ").replaceAll("\\(", " ").replaceAll("\\)", " ").toLowerCase().lastIndexOf(" where ") + 7);
                        last_ind = Math.max(last_ind, tmp_s.toLowerCase().lastIndexOf("(") + 1);
                        last_ind = Math.max(last_ind, tmp_s.lastIndexOf(",") + 1);
                        String tmp_fd = tmp_s.substring(last_ind);
                        String tmp_con = "";
                        if (obj instanceof Object[]) {
                            boolean isComment = tmp_s.contains("uncomment--begin") && !tmp_s.contains("uncomment--end");
                            Object[] tmp_objs = (Object[]) obj;
                            if (tmp_fd.indexOf(" in ") > 0) {
                                tmp_con = "(";
                                for (int i = 0; i < tmp_objs.length; i++) {
                                    Object tmp_obj = tmp_objs[i];
                                    tmp_con = tmp_con + tmp_fd + (tmp_obj == null ? "''" : "'" + tmp_obj + "'");
                                    if (i < (tmp_objs.length - 1)) {
                                        tmp_con = tmp_con + " , ";
                                    }
                                }
                                tmp_con = tmp_con + ")";
                                if (isComment) {
                                    tmp_con = tmp_con.replace("'", "''");
                                }
                                stringbuffer.append(tmp_con);
                            } else if (tmp_fd.indexOf("like") > 0) {
                                tmp_s = tmp_s.substring(0, last_ind);
                                tmp_con = "(";
                                for (int i = 0; i < tmp_objs.length; i++) {
                                    Object tmp_obj = tmp_objs[i];
                                    String tmp_v_0 = ((tmp_obj == null || obj.toString().trim().equals("")) ? "" : "" + tmp_obj + "");
                                    tmp_con = tmp_con + tmp_fd.replaceAll("%like%", "@lake '%" + tmp_v_0 + "%'").replaceAll("like%", "@lake '" + tmp_v_0 + "%'").replaceAll("%like", "@lake '%" + tmp_v_0 + "'").replaceAll("like", "@lake '" + tmp_v_0 + "'").replaceAll("@lake", "like");
                                    if (i < (tmp_objs.length - 1)) {
                                        tmp_con = tmp_con + " or ";
                                    }
                                }
                                tmp_con = tmp_con + ")";
                                if (isComment) {
                                    tmp_con = tmp_con.replace("'", "''");
                                }
                                tmp_s = tmp_s + tmp_con;
                                stringbuffer = new StringBuffer();
                                stringbuffer.append(tmp_s);
                            } else {
                                tmp_s = tmp_s.substring(0, last_ind);
                                tmp_con = "(";
                                for (int i = 0; i < tmp_objs.length; i++) {
                                    Object tmp_obj = tmp_objs[i];
                                    tmp_con = tmp_con + tmp_fd + (tmp_obj == null ? "''" : "'" + tmp_obj + "'");
                                    if (i < (tmp_objs.length - 1)) {
                                        tmp_con = tmp_con + " or ";
                                    }
                                }
                                tmp_con = tmp_con + ")";
                                if (isComment) {
                                    tmp_con = tmp_con.replace("'", "''");
                                }
                                tmp_s = tmp_s + tmp_con;
                                stringbuffer = new StringBuffer();
                                stringbuffer.append(tmp_s);
                            }
                        } else {
                            if (tmp_fd.indexOf(" in ") > 0) {
                                stringbuffer.append("(").append(obj == null ? "''" : "'" + obj + "'").append(")");
                            } else if (tmp_fd.indexOf("like") > 0) {
                                tmp_s = tmp_s.substring(0, last_ind);
                                String tmp_v_0 = ((obj == null || obj.toString().trim().equals("")) ? "@" : "" + obj + "");
                                tmp_fd = tmp_fd.replaceAll("%like%", "@lake '%" + tmp_v_0 + "%'").replaceAll("like%", "@lake '" + tmp_v_0 + "%'").replaceAll("%like", "@lake '%" + tmp_v_0 + "'").replaceAll("like", "@lake '" + tmp_v_0 + "'").replaceAll("@lake", "like");
                                stringbuffer = new StringBuffer();
                                stringbuffer.append(tmp_s);
                                stringbuffer.append(tmp_fd);
                            } else {
                                if (obj instanceof Date) {
                                    obj = DateUtil.toStringForQuery((Date) obj);
                                    stringbuffer.append(obj);
                                } else {
                                    stringbuffer.append(obj == null ? "''" : "'" + obj + "'");
                                }
                            }
                        }
                    }
                } else {
                    stringbuffer.append("");
                }
            } else {
                stringbuffer.append(s.toString());
            }
        }
//        System.out.println("reportSQL:" + stringbuffer.toString());
//        log.error("reportSQL:" + stringbuffer.toString());
        return stringbuffer.toString();
    }

    private void createJWorkSheet(ParameterReport workSheet) {
        isDataChanged = false;
        jWorkSheet = new DesignParameterJWorkSheet(workSheet);

        jWorkSheet.addGridSelectionChangeListener(new GridSelectionChangeListener() {

            @Override
            public void gridSelectionChanged(GridSelectionChangeEvent paramGridSelectionChangeEvent) {
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        ToolbarActionManager.createToolbarActionManager().update();
                    }
                });
            }
        });
        jWorkSheet.addReportDataChangeListener(new ReportDataChangeListener() {

            @Override
            public void reportDataChanged(ReportDataChangeEvent paramReportDataChangeEvent) {
                isDataChanged = true;
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        ToolbarActionManager.createToolbarActionManager().update();
                    }
                });
            }
        });
    }
//    private static void showReport(final PreviewPane previewPane, final JFrame jf, ReportDef reportDef, final Env oldEnv, boolean enableSave) {
//        final JPanel pnl = new JPanel(new BorderLayout());
//        final JButton btnExport = new JButton("����");
//        final JPanel pnlTop = previewPane.getJpanel();
//        ComponentUtil.setSysFuntion(btnExport, "ReportMng.btnExport");
//        btnExport.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ReportUtil.exportExcel(previewPane, btnExport);
//            }
//        });
//        pnlTop.add(btnExport);
//        final JButton btnCheck = new JButton("У��");
//        btnCheck.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                previewPane.checkReport();
//            }
//        });
//        pnlTop.add(btnCheck);
//        Component[] coms = pnlTop.getComponents();
//        JTextField jtf = null;
//        for (Component c : coms) {
//            if (c instanceof JButton) {
//                JButton btn = (JButton) c;
//                String text = btn.getText();
//                String iconName = "";
//                int width = 25;
//                if ("����".equals(text)) {
//                    btn.setName("save");
//                    iconName = "save.png";
//                    width = 40;
//                    btn.setEnabled(enableSave);
//                } else if ("����ֽ��".equals(text)) {
//                    iconName = "setpage.png";
//                    width = 70;
//                } else if ("����".equals(text)) {
//                    iconName = "export.png";
//                    width = 40;
//                } else if ("У��".equals(text)) {
//                    iconName = "check.png";
//                    width = 40;
//                }
//                if (!iconName.equals("")) {
//                    btn.setText(null);
//                    ComponentUtil.setIcon(btn, iconName);
//                }
//                btn.setPreferredSize(new Dimension(width, 22));
//            } else if (c instanceof JTextField) {
//                jtf = (JTextField) c;
//            }
//        }
//        final JTextField jtfPageNo = jtf;
//        jtfPageNo.getDocument().addDocumentListener(new DocumentListener() {
//
//            public void changedUpdate(DocumentEvent e) {
//                //�ı��ı�ʱ��Ӧ
//            }
//
//            public void insertUpdate(DocumentEvent e) {
//                //����ʱ��Ӧ
//                showReport(pnl, previewPane, Integer.valueOf(jtfPageNo.getText()) - 1, pnlTop);
//                pnl.updateUI();
//            }
//
//            public void removeUpdate(DocumentEvent e) {
//            }
//        });
//        final JComboBox cb_zoom = new JComboBox(new Object[]{"100", "90", "80", "75", "50", "25"});
//        cb_zoom.setPreferredSize(btnExport.getPreferredSize());
//        cb_zoom.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                previewPane.innerPreviewPane.setD_zomm(Double.valueOf(cb_zoom.getSelectedItem().toString()) / 100.0d);
//            }
//        });
//        pnlTop.add(new JLabel("��ʾ������"));
//        pnlTop.add(cb_zoom);
//        ComponentUtil.setSize(cb_zoom, 50, 22);
//        final JCheckBox ch_reverse = new JCheckBox("��ת", false);
//        ch_reverse.addChangeListener(new ChangeListener() {
//
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                previewPane.innerPreviewPane.setReverse(ch_reverse.isSelected());
//                previewPane.updateUI();
//            }
//        });
//        pnlTop.add(ch_reverse);
//        pnlTop.setMaximumSize(new Dimension(pnl.getWidth(), 24));
//        showReport(pnl, previewPane, 0, pnlTop);
//        ModelFrame fm = ModelFrame.showModel(jf, pnl, true, reportDef.getReport_name() + " ִ�н��", jf.getToolkit().getScreenSize().width - 50, jf.getToolkit().getScreenSize().height - 50, false);
//        fm.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        fm.setLocation(0, 0);
//        fm.addIPickWindowCloseListener(new IPickWindowCloseListener() {
//
//            @Override
//            public void pickClose() {
////                curPage = null;
//                cellKeys.clear();
//                FRContext.setCurrentEnv(oldEnv);
//            }
//        });
//        fm.setVisible(true);
//    }
//
//    private static void showReport(JPanel pnl, PreviewPane previewPane, int pageNo, JPanel pnlTop) {
//        if (ReportPanel.pageNo != pageNo) {
//            List<CoverCellElement> cells = cellKeys.get(ReportPanel.pageNo);
//            if (cells != null) {
//                for (CoverCellElement cell : cells) {
//                    int col = cell.getColumn();
//                    int row = cell.getRow();
//                    cell.setValue(workSheet.getCellValue(col, row));
//                }
//            }
//        }
//        ReportPanel.pageNo = pageNo;
//        pnl.removeAll();
//        workSheet = new ParameterReport();
//        DesignParameterJWorkSheet jWorkSheet = new DesignParameterJWorkSheet(workSheet);
//        pnl.add(jWorkSheet, BorderLayout.CENTER);
//        pnl.add(pnlTop, BorderLayout.NORTH);
//        ReportPage page = previewPane.getReportPageSet().getPage(pageNo);
//        if (page == null) {
//            return;
//        }
//        for (int i = 0; i < page.getRowCount(); i++) {
//            workSheet.setRowHeight(i, page.getRowHeight(i));
//        }
//        for (int i = 0; i < page.getColumnCount(); i++) {
//            workSheet.setColumnWidth(i, page.getColumnWidth(i));
//        }
//        List<CoverCellElement> cells = cellKeys.get(pageNo);
//        if (cells == null) {
//            cells = new ArrayList<CoverCellElement>();
//            Iterator it = page.cellIterator();
//            while (it.hasNext()) {
//                CoverCellElement cell = (CoverCellElement) it.next();
//                cells.add(cell);
//            }
//            cellKeys.put(pageNo, cells);
//        }
//        jWorkSheet.getGrid().setEditable(false);
//        for (CoverCellElement cell : cells) {
//            int col = cell.getColumn();
//            int row = cell.getRow();
//            workSheet.setCellValue(col, row, cell.getValue());
//            int col_span = cell.getColumnSpan();
//            int row_span = cell.getRowSpan();
//            if (col_span > 1 || row_span > 1) {
//                workSheet.merge(row, row + row_span - 1, col, col + col_span - 1);
//            }
//            Style style = cell.getStyle();
//            if (style == null) {
//                continue;
//            }
//            workSheet.getCellElement(col, row).setStyle(style);
//        }
//        workSheet.setReportSettings(page.getReportSettings());
//    }

    public String getModuleCode() {
        return module_code;
    }
}
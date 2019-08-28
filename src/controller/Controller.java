package controller;

import model.ListTuDien;
import model.TuDien;
import view.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class Controller implements IController {
    private MyPanel parentPanel;
    private ListTuDien listTuDien;
    private FileController fileController = new FileController();
    private TuDien currentDict;

    public Controller(MyPanel parentPanel, ListTuDien listTuDien) {
        this.parentPanel = parentPanel;
        this.listTuDien = listTuDien;
        fileController.readAllFile(listTuDien);
        currentDict = null;
    }

    public void start() {
        control();
        new MainFrame(parentPanel);
    }

    public void control() {
        listenPanelTimKiem();
        listenPanelTaoTuDien();
    }

    private void listenPanelTimKiem() {
        PanelTimKiem panelTimKiem = (PanelTimKiem) parentPanel.getComponent(0);

        panelTimKiem.updateCb();
        JComboBox cbChonTuDien = panelTimKiem.getCbChonTuDien();
        MyButton btnSuaTuDien = panelTimKiem.getBtnSuaTuDien();
        MyButton btnThemTuDien = panelTimKiem.getBtnThemTuDien();
        TextFieldWithAutoComp tfTimKiem = panelTimKiem.getTfTimKiem();
        MyButton btnTimKiem = panelTimKiem.getBtnTimKiem();
        MyButton btnSua = panelTimKiem.getBtnSua();
        MyButton btnLuu = panelTimKiem.getBtnLuu();
        MyButton btnKhoiPhuc = panelTimKiem.getBtnKhoiPhuc();
        JTextArea taKetQua = panelTimKiem.getTaKetQua();



        cbChonTuDien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = cbChonTuDien.getSelectedIndex();
                if (index == -1) return;
                if (index == cbChonTuDien.getItemCount() - 1) {
                    if (listTuDien.getListTuDien().size() == 0 || cbChonTuDien.getItemCount() > 1) { //Import tu dien
                        if (listTuDien.getListTuDien().size() == 0)
                            JOptionPane.showMessageDialog(parentPanel, "Khong co tu dien nao. Hay chon 1 tu dien");
                        JFileChooser chooser = new JFileChooser();
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("Dictionary File", "dict");
                        chooser.setFileFilter(filter);
                        chooser.setCurrentDirectory(new File("."));
                        int select = chooser.showOpenDialog(parentPanel);
                        if (select == JFileChooser.APPROVE_OPTION) {
                            File file = chooser.getSelectedFile();
                            try {
                                String url = file.getPath();
                                fileController.themTuDien(listTuDien, url);
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage() + "Controller - line 67");
                            }
                        } else if (select == JFileChooser.CANCEL_OPTION) {
                        }
                        panelTimKiem.updateCb();
                        currentDict = null;
                        tfTimKiem.setTuDien(currentDict);
                        return;
                    }
                }
                currentDict = listTuDien.getListTuDien().get(index);
                tfTimKiem.setTuDien(currentDict);
                panelTimKiem.clear();
            }
        });
//        panelTimKiem.getCbChonTuDien().addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) { //TODO
//                int state = e.getStateChange();
//                if(state == ItemEvent.SELECTED){
//
//                }
//            }
//        });

        btnTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelTimKiem.ketThucSua();
                if (currentDict == null) {
                    JOptionPane.showMessageDialog(parentPanel, "Hay chon tu dien");
                    return;
                }
                String tu = tfTimKiem.getText();
                if (tu.equals("")) return;
                String nghia = timKiem(currentDict, tu);
                if (nghia != null) {
                    taKetQua.setText(nghia);
                } else {
                    JOptionPane.showMessageDialog(null, "Khong co ket qua cho tu: " + tu);
                }
            }
        });


        tfTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cbInput = tfTimKiem.getCbInput();
                if (cbInput.getSelectedItem() != null)
                    tfTimKiem.setText(cbInput.getSelectedItem().toString());
                cbInput.setPopupVisible(false);
                btnTimKiem.doClick();
            }
        });

        tfTimKiem.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                panelTimKiem.ketThucSua();
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!taKetQua.getText().equals("") && !tfTimKiem.getText().equals(""))
                    panelTimKiem.sua();
            }
        });

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (taKetQua.getText().equals("")) {
                    JOptionPane.showMessageDialog(parentPanel, "Chua tao nghia cho tu");
                    return;
                }
                int click = JOptionPane.showConfirmDialog(null, "Luu thay doi?", "Luu", JOptionPane.YES_NO_OPTION);
                if (click == JOptionPane.YES_OPTION) {
                    suaTu(currentDict, tfTimKiem.getText(), taKetQua.getText());
                    panelTimKiem.ketThucSua();
                    fileController.ghiTuDien(currentDict);
                }
            }
        });
        btnKhoiPhuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nghia = timKiem(currentDict, tfTimKiem.getText());
                taKetQua.setText(nghia);
            }
        });

        btnSuaTuDien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTaoTuDien panelTaoTuDien = (PanelTaoTuDien) parentPanel.getComponent(1);
                panelTaoTuDien.setTuDien(currentDict);
                panelTaoTuDien.showCard();
            }
        });

        btnThemTuDien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTaoTuDien panelTaoTuDien = (PanelTaoTuDien) parentPanel.getComponent(1);
                panelTaoTuDien.setTuDien(null);
                panelTaoTuDien.showCard();
            }
        });
    }

    private void listenPanelTaoTuDien() {
        PanelTaoTuDien panelTaoTuDien = (PanelTaoTuDien) parentPanel.getComponent(1);
        Map<String, String> listThem = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        ArrayList<String> listXoa = new ArrayList<>();

        MyButton btnLuu = panelTaoTuDien.getBtnLuu();
        MyButton btnSua = panelTaoTuDien.getBtnSua();
        MyButton btnThem = panelTaoTuDien.getBtnThem();
        MyButton btnXoa = panelTaoTuDien.getBtnXoa();
        MyButton btnHuy = panelTaoTuDien.getBtnHuy();
        MyButton btnThoat = panelTaoTuDien.getBtnThoat();

        JList jListTuMoi = panelTaoTuDien.getListTuMoi();
        JTextField tfTuMoi = panelTaoTuDien.getTfTuMoi();

        jListTuMoi.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (jListTuMoi.getSelectedValue() != null) {
                        panelTaoTuDien.ketThucSua();
                        btnSua.setEnabled(true);
                    } else {
                        btnSua.setEnabled(false);
                    }
                }
            }
        });

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TuDien tuDien = panelTaoTuDien.getTuDien();
                if (tuDien != null) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Luu lai cac thay doi?", "Xac nhan", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.NO_OPTION) return;
                    Map<String, String> danhSach = tuDien.getDanhSach();
                    for (String x : listXoa) {
                        danhSach.remove(x);
                    }
                    danhSach.putAll(listThem);
                    fileController.ghiTuDien(tuDien);
                } else {
                    String ten = panelTaoTuDien.getTfTenTuDien().getText();
                    if (ten.equals("")) {
                        JOptionPane.showMessageDialog(null, "Hay nhap ten tu dien");
                        return;
                    } else if (listTuDien.contains(ten)) {
                        JOptionPane.showMessageDialog(null, "Tu dien: " + ten + " - Da ton tai");
                        return;
                    }
                    int confirm = JOptionPane.showConfirmDialog(null, "Tao tu dien: " + ten + "?", "Xac nhan", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.NO_OPTION) return;
                    TreeMap<String, String> danhSach = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                    danhSach.putAll(listThem);
                    tuDien = new TuDien(danhSach, ten);
                    listTuDien.addNew(tuDien);
                    fileController.ghiTuDien(tuDien);
                    fileController.addURL(tuDien.getUrl());
                    fileController.writeFile();
                }
                btnThoat.doClick();
            }
        });
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tu = panelTaoTuDien.getTfNhapTuMoi().getText();
                String nghia = panelTaoTuDien.getTaNghia().getText();
                if (nghia.isEmpty() || tu.isEmpty()) {
                    JOptionPane.showMessageDialog(parentPanel, "Hay dien day du thong tin");
                    return;
                }
                DefaultListModel dlm = (DefaultListModel) jListTuMoi.getModel();
                if (listThem.containsKey(tu) || (currentDict != null && currentDict.getDanhSach().containsKey(tu))) {
                } else {
                    dlm.addElement(tu);
                }
                listThem.put(tu, nghia);
                listXoa.remove(tu);
                panelTaoTuDien.ketThucSua();
                jListTuMoi.clearSelection();
            }
        });
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelTaoTuDien.sua();
                String tu = (String) jListTuMoi.getSelectedValue();
                String nghia;
                if (panelTaoTuDien.getTuDien() != null) {
                    nghia = listThem.get(tu);
                    if (nghia == null) nghia = panelTaoTuDien.getTuDien().getDanhSach().get(tu);
                } else nghia = listThem.get(tu);
                panelTaoTuDien.getTfNhapTuMoi().setText(tu);
                panelTaoTuDien.getTaNghia().setText(nghia);
            }
        });
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tu = (String) jListTuMoi.getSelectedValue();
                int confirm = JOptionPane.showConfirmDialog(null, "Xoa: " + tu + "?", "Xac nhan", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    listThem.remove(tu);
                    listXoa.add(tu);
                    panelTaoTuDien.ketThucSua();
                    DefaultListModel dlm = (DefaultListModel) jListTuMoi.getModel();
                    dlm.remove(jListTuMoi.getSelectedIndex());
                    panelTaoTuDien.ketThucSua();
                    jListTuMoi.clearSelection();
                }
            }
        });
        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelTaoTuDien.ketThucSua();
                jListTuMoi.clearSelection();
                listThem.clear();
                listXoa.clear();
            }
        });
        btnThoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelTaoTuDien.clear();
                listThem.clear();
                listXoa.clear();
                PanelTimKiem panelTimKiem = (PanelTimKiem) parentPanel.getComponent(0);
                panelTimKiem.showCard();
                listThem.clear();
                listXoa.clear();
            }
        });

        tfTuMoi.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                DefaultListModel<String> dlm = (DefaultListModel<String>) jListTuMoi.getModel();
                dlm.removeAllElements();
                String string = panelTaoTuDien.getTfTuMoi().getText();
                TuDien tuDien = panelTaoTuDien.getTuDien();
                String lowerString = string.toLowerCase();
                if (tuDien == null) {
                    for (String x : listThem.keySet()) {
                        if (x.toLowerCase().startsWith(lowerString)) {
                            dlm.addElement(x);
                        }
                    }
                } else {
                    for (String x : tuDien.getDanhSach().keySet()) {
                        if (x.toLowerCase().startsWith(lowerString) && !listXoa.contains(x)) {
                            dlm.addElement(x);
                        }
                    }
                    for (String x : listThem.keySet()) {
                        if (x.toLowerCase().startsWith(lowerString)) {
                            if (!tuDien.getDanhSach().containsKey(x) && !listXoa.contains(x)) {
                                dlm.addElement(x);
                            }
                        }
                    }
                }
            }
        });
    }

    public String timKiem(TuDien tuDien, String tu) {
        String nghia = tuDien.getDanhSach().get(tu);
        return nghia;
    }

    public void themTu(TuDien tuDien, String tu, String nghia) {
        tuDien.getDanhSach().put(tu, nghia);
    }

    public void suaTu(TuDien tuDien, String tu, String nghia) {
        tuDien.getDanhSach().put(tu, nghia);
    }

    public void xoaTu(TuDien tuDien, String tu) {
        tuDien.getDanhSach().remove(tu);
    }
}

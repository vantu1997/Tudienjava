package view;

import model.ListTuDien;
import model.TuDien;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelTimKiem extends MyPanel{
    public static final String NAME = "TimKiem";
    private PanelNhap panelNhap;
    private PanelChonTuDien panelChonTuDien;
    private JComboBox cbChonTuDien;
    private MyButton btnSuaTuDien, btnThemTuDien;
    private MyPanel panelTimKiem, panelKetQua, panelBtnChinhSua;
    private TextFieldWithAutoComp tfTimKiem;
    private MyButton btnTimKiem, btnSua, btnLuu, btnKhoiPhuc;
    private JTextArea taKetQua;
    private JScrollPane scroll;
    private MyPanel parent;
    private ListTuDien listTuDien;

    public JComboBox getCbChonTuDien() {
        return cbChonTuDien;
    }

    public MyButton getBtnSuaTuDien() {
        return btnSuaTuDien;
    }

    public MyButton getBtnThemTuDien() {
        return btnThemTuDien;
    }

    public TextFieldWithAutoComp getTfTimKiem() {
        return tfTimKiem;
    }

    public MyButton getBtnTimKiem() {
        return btnTimKiem;
    }

    public MyButton getBtnSua() {
        return btnSua;
    }
    public MyButton getBtnLuu() {
        return btnLuu;
    }

    public MyButton getBtnKhoiPhuc() {
        return btnKhoiPhuc;
    }

    public JTextArea getTaKetQua() {
        return taKetQua;
    }

    public MyPanel getParentPanel(){
        return parent;
    }

    private void initComponent(){
        panelNhap = new PanelNhap();
        panelChonTuDien = new PanelChonTuDien();
    }

    private void addComponent(){
        add(panelChonTuDien,BorderLayout.NORTH);
        add(panelNhap,BorderLayout.CENTER);
    }

    public void clear(){
        tfTimKiem.setText("");
        taKetQua.setText("");
    }

    public void showCard(){
        clear();
        updateCb();
        CardLayout cl = (CardLayout) parent.getLayout();
        cl.show(parent,PanelTimKiem.NAME);
    }

    public void updateCb(){
        cbChonTuDien.removeAllItems();
        for(TuDien x : listTuDien.getListTuDien()){
            cbChonTuDien.addItem(x.getTen());
        }
        cbChonTuDien.addItem("Them tu dien...");
        clear();
    }

    public void sua(){
        btnKhoiPhuc.setEnabled(true);
        btnLuu.setEnabled(true);
        taKetQua.setEditable(true);
        taKetQua.requestFocus();
    }

    public void ketThucSua(){
        btnKhoiPhuc.setEnabled(false);
        btnLuu.setEnabled(false);
        taKetQua.setEditable(false);
        tfTimKiem.requestFocus();
    }

    public PanelTimKiem(MyPanel parent, ListTuDien listTuDien){
        setLayout(new BorderLayout(10,10));
        this.listTuDien = listTuDien;
        this.parent = parent;
        initComponent();
        addComponent();
        btnThemTuDien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTaoTuDien panelTaoTuDien = (PanelTaoTuDien) parent.getComponent(1);
                panelTaoTuDien.setTuDien(null);
                panelTaoTuDien.showCard();
            }
        });
    }



    // Panel Nhap xuat ket qua (center panel)

    class PanelNhap extends MyPanel {
        private void initComponent() {
            panelTimKiem = new MyPanel();
            panelTimKiem.setLayout(new BorderLayout(10, 0));
            panelTimKiem.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(210, 207, 210)), "Tim kiem: ",TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.DEFAULT_POSITION,new Font(null,Font.BOLD,13)));
            panelTimKiem.setMaximumSize(new Dimension(800, 80));
            panelKetQua = new MyPanel();
            panelKetQua.setLayout(new BorderLayout());
            panelKetQua.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(210, 207, 210)), "Ket qua: ",TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.DEFAULT_POSITION,new Font(null,Font.BOLD,13)));
            panelBtnChinhSua = new MyPanel();
            panelBtnChinhSua.setLayout(new FlowLayout(FlowLayout.RIGHT));
            tfTimKiem = new TextFieldWithAutoComp();
            tfTimKiem.setColumns(30);
            tfTimKiem.setFont(new Font(null,0,14));
            btnTimKiem = new MyButton("Tim Kiem","icons/search.png",25,25);
            btnSua = new MyButton("Sua","icons/editNghia.png",MyButton.DEFAULT_WIDTH,MyButton.DEFAULT_HEIGHT);
            btnLuu = new MyButton("Luu","icons/saveNghia.png",MyButton.DEFAULT_WIDTH,MyButton.DEFAULT_HEIGHT);
            btnLuu.setEnabled(false);
            btnKhoiPhuc = new MyButton("Khoi Phuc","icons/khoiphuc.png",MyButton.DEFAULT_WIDTH,MyButton.DEFAULT_HEIGHT);
            btnKhoiPhuc.setEnabled(false);
            taKetQua = new JTextArea();
            taKetQua.setFont(new Font(null,0,14));
            taKetQua.setLineWrap(true);
            taKetQua.setWrapStyleWord(true);
            taKetQua.setEditable(false);
            scroll = new JScrollPane(taKetQua);
        }

        private void addComponent() {
            panelTimKiem.add(tfTimKiem, BorderLayout.CENTER);
            panelTimKiem.add(btnTimKiem, BorderLayout.EAST);
            panelKetQua.add(scroll, BorderLayout.CENTER);
            panelBtnChinhSua.add(btnSua);
            panelBtnChinhSua.add(btnLuu);
            panelBtnChinhSua.add(btnKhoiPhuc);
            panelKetQua.add(panelBtnChinhSua, BorderLayout.NORTH);
            add(panelTimKiem);
            add(panelKetQua);
        }

        public PanelNhap() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            initComponent();
            addComponent();
        }
    }

    // Panel Chon tu dien (north Panel)

    class PanelChonTuDien extends MyPanel {
        private JLabel lbTuDien;

        private void initComponent(){
            lbTuDien = new JLabel("Tu dien: ");
            lbTuDien.setFont(new Font(null,Font.BOLD,14));
//            cbChonTuDien = new JComboBox(new DefaultComboBoxModel());
            cbChonTuDien = new JComboBox();
            cbChonTuDien.setPreferredSize(new Dimension(120,30));
            cbChonTuDien.setFont(new Font(null,0,14));
            btnThemTuDien = new MyButton("Them tu dien","icons/them.png",23,23);
            btnSuaTuDien = new MyButton("Sua tu dien","icons/editTuDien.png",23,23);
        }
        private void addComponent(){
            add(lbTuDien);
            add(cbChonTuDien);
            add(btnSuaTuDien);
            add(btnThemTuDien);
        }

        public PanelChonTuDien(){
            setLayout(new FlowLayout(FlowLayout.RIGHT));
            initComponent();
            addComponent();
        }
    }
}

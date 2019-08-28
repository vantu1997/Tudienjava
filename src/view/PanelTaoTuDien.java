package view;

import model.ListTuDien;
import model.TuDien;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

public class PanelTaoTuDien extends MyPanel{
    public static final String NAME = "TaoTuDien";
    PanelThemTu panelThemTu;
    private MyPanel panelNhap, panelDanhSach;
    private MyPanel panelTuMoi, panelNghia, panelBtn;
    private JTextField tfNhapTuMoi, tfTuMoi, tfTenTuDien;
    private JTextArea taNghia;
    private JScrollPane scrollTA, scrollList;
    private MyButton btnLuu, btnThem, btnSua, btnXoa, btnHuy;
    private JList listTuMoi;
    private DefaultListModel<String> listModel;
    private MyButton btnThoat;
    private MyPanel parent;
    private TuDien tuDien;

    public TuDien getTuDien() {
        return tuDien;
    }

    public void setTuDien(TuDien tuDien) {
        this.tuDien = tuDien;
    }

    public JTextField getTfTenTuDien() {
        return tfTenTuDien;
    }
    public JTextField getTfNhapTuMoi() {
        return tfNhapTuMoi;
    }

    public JTextField getTfTuMoi() {
        return tfTuMoi;
    }

    public JTextArea getTaNghia() {
        return taNghia;
    }

    public MyButton getBtnLuu() {
        return btnLuu;
    }

    public MyButton getBtnThem() {
        return btnThem;
    }

    public MyButton getBtnSua() {
        return btnSua;
    }

    public MyButton getBtnXoa() {
        return btnXoa;
    }

    public MyButton getBtnHuy() {
        return btnHuy;
    }

    public JList getListTuMoi() {
        return listTuMoi;
    }

    public MyButton getBtnThoat() {
        return btnThoat;
    }

    public MyPanel getParentPanel(){
        return parent;
    }

    private void initComponent(){
        panelThemTu = new PanelThemTu();
        btnThoat = new MyButton("Thoat","icons/return3.png",25,23);
        tfTenTuDien = new JTextField(14);
        tfTenTuDien.setFont(new Font(null,0,14));
    }

    private void addComponent(){
        add(panelThemTu,BorderLayout.CENTER);
        MyPanel nortPanel = new MyPanel(new BorderLayout());

        MyPanel tmpPanel = new MyPanel(new FlowLayout());
        tmpPanel.add(btnThoat);
        nortPanel.add(tmpPanel,BorderLayout.EAST);

        tmpPanel = new MyPanel(new FlowLayout());
        JLabel tmpLabel = new JLabel("Tu dien: ");
        tmpLabel.setFont(new Font(null,Font.BOLD,14));
//        tmpLabel.setFont(new Font(null,0,14));
        tmpPanel.add(tmpLabel);
        tmpPanel.add(tfTenTuDien);
        nortPanel.add(tmpPanel,BorderLayout.WEST);

        add(nortPanel,BorderLayout.NORTH);
    }


    public void clear(){
        taNghia.setText("");
        tfNhapTuMoi.setText("");
        tfTuMoi.setText("");
        tfTenTuDien.setText("");
        tfTenTuDien.setEditable(true);
        listModel.removeAllElements();
        ketThucSua();
        tuDien = null;
    }

    public void showCard(){
        if(tuDien==null){
            clear();
        }else{
            showTuDien();
        }
        CardLayout cl = (CardLayout) parent.getLayout();
        cl.show(parent,PanelTaoTuDien.NAME);
    }

    public void showTuDien(){
        tfTenTuDien.setText(tuDien.getTen());
        tfTenTuDien.setEditable(false);
        Map<String,String> map = tuDien.getDanhSach();
        for(String x : map.keySet()){
            listModel.addElement(x);
        }
    }

    public void sua(){
        btnHuy.setEnabled(true);
        btnXoa.setEnabled(true);
    }

    public void ketThucSua(){
        btnHuy.setEnabled(false);
        btnXoa.setEnabled(false);
        tfNhapTuMoi.setText("");
        taNghia.setText("");
    }

    public PanelTaoTuDien(MyPanel parent){
        setLayout(new BorderLayout(10,10));
        tuDien = null;
        this.parent = parent;
        initComponent();
        addComponent();
    }

    class PanelThemTu extends MyPanel{

        private void initComponent(){
            panelNhap = new MyPanel();
            panelNhap.setLayout(new BorderLayout(0,5));
            panelDanhSach = new MyPanel();
            panelDanhSach.setLayout(new BorderLayout(5,10));
            panelDanhSach.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(51, 153, 102)), "Danh sach cac tu ",TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.DEFAULT_POSITION,new Font(null,Font.BOLD,13)));
            panelTuMoi = new MyPanel();
            panelTuMoi.setLayout(new BorderLayout());
            panelTuMoi.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(51, 153, 102)), "Tu moi: ",TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.DEFAULT_POSITION,new Font(null,Font.BOLD,13)));
            panelNghia = new MyPanel();
            panelNghia.setLayout(new BorderLayout());
            panelNghia.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(51, 153, 102)), "Nghia: ",TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.DEFAULT_POSITION,new Font(null,Font.BOLD,13)));
            panelBtn = new MyPanel();
//        panelBtn.setLayout(new GridLayout(5,1,5,10));
//        panelBtn.setBorder(new EmptyBorder(30,5,5,5));
            panelBtn.setLayout(new BoxLayout(panelBtn,BoxLayout.Y_AXIS));
            tfNhapTuMoi = new JTextField();
            tfNhapTuMoi.setFont(new Font(null,0,14));
            taNghia = new JTextArea();
            taNghia.setWrapStyleWord(true);
            taNghia.setLineWrap(true);
            scrollTA = new JScrollPane(taNghia);
            btnLuu = new MyButton("Luu","icons/save.png",MyButton.DEFAULT_WIDTH,MyButton.DEFAULT_HEIGHT);
            btnThem = new MyButton("Them","icons/right.png",MyButton.DEFAULT_WIDTH,MyButton.DEFAULT_HEIGHT);
            btnSua = new MyButton("Sua","icons/left.png",MyButton.DEFAULT_WIDTH,MyButton.DEFAULT_HEIGHT);
            btnSua.setEnabled(false);
            btnXoa = new MyButton("Xoa","icons/delete.png",MyButton.DEFAULT_WIDTH,MyButton.DEFAULT_HEIGHT);
            btnXoa.setEnabled(false);
            btnHuy = new MyButton("Huy","icons/huy.png",MyButton.DEFAULT_WIDTH,MyButton.DEFAULT_HEIGHT);
            btnHuy.setEnabled(false);

            tfTuMoi = new JTextField();
            tfTuMoi.setFont(new Font(null,0,14));
            listModel = new DefaultListModel<>();
            listTuMoi = new JList(listModel);
            listTuMoi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollList = new JScrollPane(listTuMoi);
            scrollList.setPreferredSize(new Dimension(150,0));
        }

        private void addComponent(){
//        panelBtn.add(Box.createVerticalGlue());
//        panelBtn.add(Box.createRigidArea(new Dimension(0,5)));
            Dimension minSize = new Dimension(0,5);
            Dimension maxSize = new Dimension(0,20);
            Dimension prefSize = new Dimension(0,10);
            panelBtn.add(new Box.Filler(minSize, null, new Dimension(0,65)));
            panelBtn.add(btnLuu);
            panelBtn.add(new Box.Filler(minSize, prefSize, maxSize));
            panelBtn.add(btnThem);
            panelBtn.add(new Box.Filler(minSize, prefSize, maxSize));
            panelBtn.add(btnSua);
            panelBtn.add(new Box.Filler(minSize, prefSize, maxSize));
            panelBtn.add(btnXoa);
            panelBtn.add(new Box.Filler(minSize, prefSize, maxSize));
            panelBtn.add(btnHuy);

            panelTuMoi.add(tfNhapTuMoi,BorderLayout.CENTER);
            panelNhap.add(panelTuMoi,BorderLayout.NORTH);
            panelNghia.add(scrollTA, BorderLayout.CENTER);
            panelNhap.add(panelNghia,BorderLayout.CENTER);


            panelDanhSach.add(tfTuMoi,BorderLayout.NORTH);
            panelDanhSach.add(scrollList,BorderLayout.CENTER);

            MyPanel eastPanel = new MyPanel();
            eastPanel.setLayout(new BorderLayout());
            eastPanel.add(panelDanhSach,BorderLayout.CENTER);
            eastPanel.add(panelBtn,BorderLayout.WEST);

            add(panelNhap,BorderLayout.CENTER);
            add(eastPanel,BorderLayout.EAST);
        }

        public PanelThemTu(){
            setLayout(new BorderLayout());
            initComponent();
            addComponent();
        }
    }
}

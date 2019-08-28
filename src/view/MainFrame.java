package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private MyPanel cardContainer;
//    private CardLayout cardLayout;
//    private PanelTimKiem panelTimKiem;
//    private PanelTaoTuDien panelTaoTuDien;
    private JMenuBar menuBar;
    private JMenu menuFile, menuEdit, menuAbout;
    private Container cp;
    private void initComponent(){
        menuBar = new JMenuBar();
        menuBar.setBorderPainted(true);
        menuBar.setBackground(Color.GRAY);
        menuFile = new JMenu("File");
        menuFile.setForeground(Color.WHITE);
        menuEdit = new JMenu("Edit");
        menuEdit.setForeground(Color.WHITE);
        menuAbout = new JMenu("About");
        menuAbout.setForeground(Color.WHITE);

//        cardLayout = new CardLayout();
//        cardContainer.setLayout(cardLayout);
//        panelTimKiem = new PanelTimKiem(cardContainer);
//        panelTaoTuDien = new PanelTaoTuDien(cardContainer);


//        addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                Component x = e.getComponent();
//                System.out.println("width: "+x.getBounds().width +" - height: " +x.getBounds().height);
//            }
//        });
    }
    private void addComponent(){
        setJMenuBar(menuBar);
        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuAbout);
//        cardContainer.add(panelTimKiem,"TimKiem");
//        cardContainer.add(panelTaoTuDien, "TaoTuDien");
        cp.add(cardContainer,BorderLayout.CENTER);
    }
    public MainFrame(MyPanel cardContainer){
        this.cardContainer = cardContainer;
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.setBackground(new Color(230, 255, 255));
        initComponent();
        addComponent();
        setLocation(200,200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(700,500);
        setMinimumSize(new Dimension(350,300));
        setTitle("Dictionary");
        setVisible(true);
        PanelTimKiem panelTimKiem = (PanelTimKiem) cardContainer.getComponent(0);
        panelTimKiem.showCard();
    }


}

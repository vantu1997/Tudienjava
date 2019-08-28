package model;

import controller.Controller;
import view.MainFrame;
import view.MyPanel;
import view.PanelTaoTuDien;
import view.PanelTimKiem;

import java.awt.*;
import java.util.ArrayList;

public class MainClass {
    public static void main(String[] args) {
        ListTuDien listTuDien = new ListTuDien();

        MyPanel parentPanel = new MyPanel(new CardLayout());
        PanelTimKiem panelTimKiem = new PanelTimKiem(parentPanel,listTuDien);
        PanelTaoTuDien panelTaoTuDien = new PanelTaoTuDien(parentPanel);
        parentPanel.add(panelTimKiem,PanelTimKiem.NAME);
        parentPanel.add(panelTaoTuDien,PanelTaoTuDien.NAME);

        Controller controller = new Controller(parentPanel,listTuDien);
        controller.start();

    }
}

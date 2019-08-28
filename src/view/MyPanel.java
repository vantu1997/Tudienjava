package view;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel implements IMyComponent{
    private JPanel panelParent;

    public MyPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        modifyDefaultValue();
    }
    public MyPanel(LayoutManager layout) {
        super(layout);
        modifyDefaultValue();
    }

    public MyPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        modifyDefaultValue();
    }

    public MyPanel(JPanel panelParent){
        this.panelParent = panelParent;
    }

    public MyPanel() {
        modifyDefaultValue();
    }

    public JPanel getParentPanel() {
        return panelParent;
    }

    public void setParentPanel(JPanel panelParent) {
        this.panelParent = panelParent;
    }

    private final boolean OPAQUE = false;

    public void modifyDefaultValue(){
        setOpaque(OPAQUE);
    }

}

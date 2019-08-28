package view;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton implements IMyComponent{
    public static final int DEFAULT_WIDTH = 20;
    public static final int DEFAULT_HEIGHT = 20;
    public MyButton() {
        modifyDefaultValue();
    }
    public MyButton(Icon icon) {
        super(icon);
        modifyDefaultValue();
    }

    public MyButton(String text) {
        super(text);
        modifyDefaultValue();
    }

    public MyButton(Action a) {
        super(a);
        modifyDefaultValue();
    }

    public MyButton(String text, Icon icon) {
        super(text, icon);
        modifyDefaultValue();
    }

    public MyButton(String toolTip, String urlIcon, int width, int height){
        setToolTipText(toolTip);
        ImageIcon icon = new ImageIcon(urlIcon);
        Image img = icon.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        setIcon(icon);
        modifyDefaultValue();
    }

    private final Color color = Color.WHITE;

    public void modifyDefaultValue(){
        setBackground(color);
    }
}

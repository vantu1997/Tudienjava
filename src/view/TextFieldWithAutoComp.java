package view;

import model.TuDien;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TextFieldWithAutoComp extends JTextField implements IMyComponent{

    private TuDien tuDien;
    private JComboBox cbInput;

    public TextFieldWithAutoComp(){
        this.tuDien = null;
        modifyDefaultValue();
    }

    public TextFieldWithAutoComp(TuDien tuDien){
        this.tuDien = tuDien;
        modifyDefaultValue();
    }

    public TextFieldWithAutoComp(String text, TuDien tuDien) {
        super(text);
        this.tuDien = tuDien;
        modifyDefaultValue();
    }

    public TextFieldWithAutoComp(int columns, TuDien tuDien) {
        super(columns);
        this.tuDien = tuDien;
        modifyDefaultValue();
    }

    public TextFieldWithAutoComp(String text, int columns, TuDien tuDien) {
        super(text, columns);
        this.tuDien = tuDien;
        modifyDefaultValue();
    }

    public TextFieldWithAutoComp(Document doc, String text, int columns, TuDien tuDien) {
        super(doc, text, columns);
        this.tuDien = tuDien;
        modifyDefaultValue();
    }

    @Override
    public void modifyDefaultValue() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        cbInput = new JComboBox(model);
        cbInput.setPreferredSize(new Dimension(cbInput.getPreferredSize().width,0));
        setupAutoComplete(this);
    }

    public TuDien getTuDien() {
        return tuDien;
    }

    public void setTuDien(TuDien tuDien) {
        this.tuDien = tuDien;
    }

    public JComboBox getCbInput() {
        return cbInput;
    }

    private boolean isAdjusting(JComboBox cbInput) {
        if (cbInput.getClientProperty("is_adjusting") instanceof Boolean) {
            return (Boolean) cbInput.getClientProperty("is_adjusting");
        }
        return false;
    }

    private void setAdjusting(JComboBox cbInput, boolean adjusting) {
        cbInput.putClientProperty("is_adjusting", adjusting);
    }

    private void setupAutoComplete(JTextField txtInput) {
        setAdjusting(cbInput, false);
//        for (String item : tuDien) {
//            model.addElement(item);
//        }
        cbInput.setSelectedItem(null);
        cbInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tuDien ==null) return;
                if (!isAdjusting(cbInput)) {
                    if (cbInput.getSelectedItem() != null) {
                        txtInput.setText(cbInput.getSelectedItem().toString());
                    }
                }
            }
        });

        txtInput.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if(tuDien ==null) return;
                setAdjusting(cbInput, true);
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    Object temp = e.getSource();
                    e.setSource(cbInput);
                    cbInput.dispatchEvent(e);
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cbInput.setPopupVisible(false);
                }
                setAdjusting(cbInput, false);
            }
        });

        txtInput.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateList();
            }

            public void removeUpdate(DocumentEvent e) {
                updateList();
            }

            public void changedUpdate(DocumentEvent e) {
                updateList();
            }

            private void updateList() {
                if(tuDien ==null) return;
                DefaultComboBoxModel model = (DefaultComboBoxModel) cbInput.getModel();
                setAdjusting(cbInput, true);
                model.removeAllElements();
                String input = txtInput.getText();
                if (!input.isEmpty()) {
                    for (String item : tuDien.getDanhSach().keySet()) {
                        if (item.toLowerCase().startsWith(input.toLowerCase())) {
                            model.addElement(item);
                        }
                    }
                }
                cbInput.setPopupVisible(false);
                cbInput.setPopupVisible(model.getSize() > 0);
                setAdjusting(cbInput, false);
            }
        });
        txtInput.setLayout(new BorderLayout());
        txtInput.add(cbInput, BorderLayout.SOUTH);
    }
}

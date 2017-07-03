package com.github.hteph.UI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChroniclePane extends JPanel{
    protected JTextField textField;
    protected JTextArea textArea;
    private final static String newline = "\n";
 
    public ChroniclePane() {
        super(new GridBagLayout());
 
     //   textField = new JTextField(20);
    //    textField.addActionListener(this);
 
        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
 
        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
 
        c.fill = GridBagConstraints.HORIZONTAL;
       // add(textField, c);
 
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }
 
//    public void actionPerformed(ActionEvent evt) {
//        String text = textField.getText();
//        textArea.append(text + newline);
//        textField.selectAll();
// 
//        //Make sure the new text is visible, even if there
//        //was a selection in the text area.
//        textArea.setCaretPosition(textArea.getDocument().getLength());
//    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TextDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add contents to the window.
        frame.add(new ChroniclePane());
 
        //Display the window.
        frame.setFocusable(false);
        frame.pack();
        frame.setVisible(true);
    }
}

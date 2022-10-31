package com.mtp;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.mtp.tools.ByteArray;
import com.mtp.tools.MyBase64;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.*;
import static com.mtp.tools.MyBase64.decodeToByteArray;
import static com.mtp.tools.MyBase64.encodeByteArray;


public class Window implements DocumentListener {

    private JPanel AppMain;
    private JButton encodeBtn;
    private JButton decodeBtn;
    private JTextArea encodeText;
    private JTextArea decodeText;
    private JScrollPane scrollEn;
    private JScrollPane scrollDe;
    private JButton pasteEncode;
    private JButton copyEncode;
    private JButton pasteDecode;
    private JButton copyDecode;
    private JButton delEncode;
    private JButton delDecode;
    private JButton delAll;
    private JButton data1;
    private JButton data2;
    private JButton data3;
    private JButton data4;
    private JButton about;


    public Window() {
        $$$setupUI$$$();
        this.delEncode.setToolTipText("删除密文");
        this.delDecode.setToolTipText("删除明文");
        this.delAll.setToolTipText("清空全部");
        decodeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = "";
                try {
                    data = new String(decodeToByteArray(new String(decodeToByteArray(encodeText.getText()).uncompress()).substring(3)).uncompress()).substring(4);
                    decodeText.setText(data);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        encodeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ByteArray byteArray = new ByteArray();
                try {
                    MyBase64.AMF3Serializer(decodeText.getText(), byteArray);
                    MyBase64.setAMF3Header(byteArray, (byte) 11);
                    byte[] compress = byteArray.compress();
                    byteArray = new ByteArray();
                    MyBase64.AMF3Serializer(encodeByteArray(new ByteInputStream(compress, compress.length)).toByteArray(), byteArray);
                    MyBase64.setAMF3Header(byteArray, (byte) 6);
                    compress = byteArray.compress();
                    encodeText.setText(encodeByteArray(new ByteInputStream(compress, compress.length)).toString());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });
        pasteEncode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encodeText.setText(getClipboardString());
            }
        });
        copyEncode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setClipboardString(encodeText.getText());
            }
        });
        pasteDecode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decodeText.setText(getClipboardString());
            }
        });
        copyDecode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setClipboardString(decodeText.getText());
            }
        });
        delEncode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encodeText.setText("");
            }
        });
        delDecode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decodeText.setText("");
            }
        });
        delAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encodeText.setText("");
                decodeText.setText("");
            }
        });
        data1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encodeText.setText(MyBase64.TEST_DATA_A);
            }
        });
        data2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encodeText.setText(MyBase64.TEST_DATA_B);
            }
        });
        data3.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encodeText.setText(MyBase64.TEST_DATA_C);
            }
        });
        data4.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encodeText.setText(MyBase64.TEST_DATA_D);
            }
        });
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Thank you for support!", "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        JFrame frame = new JFrame("49DataC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setContentPane(new Window().AppMain);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        this.encodeText.setLineWrap(false);
        this.decodeText.setLineWrap(false);
        this.encodeText.setWrapStyleWord(true);
        this.decodeText.setWrapStyleWord(true);
        this.scrollEn.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollEn.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.scrollDe.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollDe.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.decodeText.getDocument().addDocumentListener(this);
    }

    public void insertUpdate(DocumentEvent e) {
        //this.decodeText.setRows(this.decodeText.getLineCount());
        //this.decodeText.setColumns(this.decodeText.getColumns());
    }

    public void removeUpdate(DocumentEvent e) {
        System.out.println("r");
    }

    public void changedUpdate(DocumentEvent e) {
        System.out.println("c");
    }

    /**
     * 把文本设置到剪贴板（复制）
     */
    public static void setClipboardString(String text) {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 封装文本内容
        Transferable trans = new StringSelection(text);
        // 把文本内容设置到系统剪贴板
        clipboard.setContents(trans, null);
    }

    /**
     * 从剪贴板中获取文本（粘贴）
     */
    public static String getClipboardString() {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        // 获取剪贴板中的内容
        Transferable trans = clipboard.getContents(null);

        if (trans != null) {
            // 判断剪贴板中的内容是否支持文本
            if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    // 获取剪贴板中的文本内容
                    String text = (String) trans.getTransferData(DataFlavor.stringFlavor);
                    return text;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        AppMain = new JPanel();
        AppMain.setLayout(new FormLayout("left:4dlu:noGrow,fill:64px:noGrow,left:5dlu:noGrow,fill:d:grow,left:5dlu:noGrow", "top:4dlu:noGrow,center:50px:noGrow,center:4dlu:noGrow,center:189px:noGrow,top:4dlu:noGrow,center:36px:noGrow,center:36px:noGrow,center:36px:noGrow,center:36px:noGrow"));
        AppMain.setAlignmentX(0.5f);
        AppMain.setAutoscrolls(false);
        AppMain.setMinimumSize(new Dimension(300, 90));
        AppMain.setOpaque(true);
        AppMain.setPreferredSize(new Dimension(143, 90));
        AppMain.setVerifyInputWhenFocusTarget(true);
        AppMain.setVisible(true);
        final JLabel label1 = new JLabel();
        label1.setText("密文：");
        CellConstraints cc = new CellConstraints();
        AppMain.add(label1, cc.xy(2, 2, CellConstraints.CENTER, CellConstraints.DEFAULT));
        final JLabel label2 = new JLabel();
        label2.setRequestFocusEnabled(true);
        label2.setText("明文：");
        AppMain.add(label2, cc.xy(2, 4, CellConstraints.CENTER, CellConstraints.DEFAULT));
        delEncode = new JButton();
        delEncode.setText("D1");
        AppMain.add(delEncode, cc.xy(2, 6, CellConstraints.CENTER, CellConstraints.DEFAULT));
        decodeBtn = new JButton();
        decodeBtn.setText("解密");
        AppMain.add(decodeBtn, cc.xy(4, 6));
        delDecode = new JButton();
        delDecode.setText("D2");
        AppMain.add(delDecode, cc.xy(2, 7, CellConstraints.CENTER, CellConstraints.DEFAULT));
        encodeBtn = new JButton();
        encodeBtn.setText("加密");
        AppMain.add(encodeBtn, cc.xy(4, 7));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        AppMain.add(panel1, cc.xy(4, 8));
        pasteEncode = new JButton();
        pasteEncode.setText("粘贴密文");
        panel1.add(pasteEncode, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pasteDecode = new JButton();
        pasteDecode.setText("粘贴明文");
        panel1.add(pasteDecode, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        copyEncode = new JButton();
        copyEncode.setText("复制密文");
        panel1.add(copyEncode, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        copyDecode = new JButton();
        copyDecode.setText("复制明文");
        panel1.add(copyDecode, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        delAll = new JButton();
        delAll.setText("清空");
        AppMain.add(delAll, cc.xy(2, 8, CellConstraints.CENTER, CellConstraints.CENTER));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        AppMain.add(panel2, cc.xy(4, 9));
        data1 = new JButton();
        data1.setText("密文一");
        panel2.add(data1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        data2 = new JButton();
        data2.setText("密文二");
        panel2.add(data2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        data3 = new JButton();
        data3.setText("密文三");
        panel2.add(data3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        data4 = new JButton();
        data4.setText("密文四");
        panel2.add(data4, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        about = new JButton();
        about.setText("关于");
        AppMain.add(about, cc.xy(2, 9, CellConstraints.CENTER, CellConstraints.CENTER));
        scrollDe = new JScrollPane();
        AppMain.add(scrollDe, cc.xy(4, 4, CellConstraints.FILL, CellConstraints.FILL));
        decodeText = new JTextArea();
        decodeText.setToolTipText("明文数据");
        scrollDe.setViewportView(decodeText);
        scrollEn = new JScrollPane();
        AppMain.add(scrollEn, cc.xy(4, 2, CellConstraints.FILL, CellConstraints.FILL));
        encodeText = new JTextArea();
        encodeText.setAutoscrolls(true);
        encodeText.setToolTipText("密文数据");
        scrollEn.setViewportView(encodeText);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return AppMain;
    }

}

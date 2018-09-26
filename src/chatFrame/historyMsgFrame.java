package chatFrame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
public class historyMsgFrame extends JFrame{

    private JPanel p1;
    public  JTextArea area1;
    private JScrollPane js1;
    public static historyMsgFrame instance;
    private historyMsgFrame(){
        //set p1
        p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 1));
        area1 = new JTextArea(10,15);
        area1.setFont(new Font("dialog",0,15));
        area1.setLineWrap(true);
        area1.setEditable(false);//不可编辑
        js1 = new JScrollPane(area1);
        js1.setBorder(new TitledBorder("个人聊天"));
        js1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        p1.add(js1);
//        //set P2
//        p2 = new JPanel();
//        p2.setLayout(new FlowLayout(FlowLayout.CENTER));
//        beforBut = new JButton("<");
//        beforBut.addActionListener(this);
//        number = new JTextField();
//        number.setHorizontalAlignment(JTextField.CENTER);
//        number.setText("1");
//        afterBut = new JButton(">");
//        afterBut.addActionListener(this);
//        p2.add(beforBut);
//        p2.add(number);
//        p2.add(afterBut);
//        //set p3
//        p3 = new JPanel();
//        p3.setLayout(new BorderLayout());
//        p3.add(p1,BorderLayout.NORTH);
//        p3.add(p2,BorderLayout.SOUTH);
        this.getContentPane().add(p1);
    }
    private void setWindow()
    {
        this.setTitle("历史纪录");
        this.setSize(400,300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }
    public static historyMsgFrame getInstance()
    {
        if (instance == null)
            instance = new historyMsgFrame();
        return instance;
    }
    public void showWindows(){
        setWindow();
        this.setVisible(true);
        this.setExtendedState(NORMAL);//获取最小化前的状态
    }
}

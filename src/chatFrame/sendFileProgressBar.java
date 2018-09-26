package chatFrame;

import Login.loginFrame;
import javax.swing.*;
import java.awt.*;

public class sendFileProgressBar extends JFrame implements Runnable {
    public JProgressBar progressBar;
    private JLabel lbStatus;
    private String statusInfo;
    private String resultInfo;
    private Object object;
    public sendFileProgressBar(Object object) {
        this.object = object;
    }
    public void init() {
        this.setSize(300,100);
        this.setResizable(false);//禁用放大按钮
        this.setLocationRelativeTo((Component) object);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//窗体关闭
    }
    public void initUI() {
        System.out.println("显示进度条");
        statusInfo = "正在发送,请稍候……";
        resultInfo = "发送成功";
        JPanel mainPane = new JPanel(new BorderLayout());
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);//显示百分比
        progressBar.setValue(0);
        lbStatus = new JLabel("" + statusInfo);
        mainPane.add(progressBar,BorderLayout.NORTH);
        mainPane.add(lbStatus,BorderLayout.SOUTH);
        this.getContentPane().add(mainPane);
        this.setVisible(true);
        System.out.println("初始化完成");
    }
    @Override
    public void run() {
        int[] a = new int[2];
        int percent = 0;
        System.out.println("更新进度");
        while (true) {
            a = loginFrame.Msg.getSendState();
            System.out.println("a[0]:" + a[0]);
            System.out.println("a[1]:" + a[1]);
            if (a[0] != 0) {
                System.out.println("文件总长度:" + a[0]);
                System.out.println("已发送文件长度:" + a[1]);
                percent = 100 * a[1] / a[0];
                System.out.println("百分比:" + percent);
                progressBar.setValue(percent);
                if (percent == 100) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.dispose();
                    progressBar.setValue(0);
                    String title = "消息";
                    JOptionPane.showMessageDialog((Component) object, resultInfo, title, JOptionPane.INFORMATION_MESSAGE);
                    loginFrame.Msg.ResetReciveState();
                    break;
                }
            }
            //percent++;
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

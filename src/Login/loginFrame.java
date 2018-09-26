package Login;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import Login.createTextPanel1;
import Login.createTextPanel2;
import MySocket.MsgSender;
import Server.ChatUtil;
import chatFrame.AppenToTextArea;

public class loginFrame extends JFrame{
	//定义选项卡
	public static JTabbedPane jTabbedPane;//存放选项卡组件
	private String[] tabNames = {"登录","自定义"};
	public static AppenToTextArea Msg;
	public loginFrame(){
		init();
		layoutComponents();
	}
	//初始化窗口
	public void init(){
		this.setTitle("登录界面");
		this.setSize(350,200);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void layoutComponents() {
		jTabbedPane = new JTabbedPane();
		createTextPanel1 Panel1 = new createTextPanel1(this);
		createTextPanel2 Panel2 = new createTextPanel2();
		
		int i = 0;
		//添加选项标签
		jTabbedPane.addTab(tabNames[i++],Panel1.createTextPanel1());
		jTabbedPane.addTab(tabNames[i++],Panel2.createTextPanel2());

		// 添加选项卡选中状态改变的监听器
        jTabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int i = jTabbedPane.getSelectedIndex();
				System.out.println("当前选中的选项卡: " + i);
				if (i==1)
					Msg.destroy();
			}
        });
        
		this.setContentPane(jTabbedPane);
		this.setVisible(true);
		
	}	
	
	public static void main(String[] args) {
		new loginFrame();
		Msg= new AppenToTextArea();
		Msg.connect(ChatUtil.ADDRESS,ChatUtil.PORT);
		Msg.read();
	}
}

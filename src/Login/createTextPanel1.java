package Login;

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
import javax.swing.JTextField;

import Server.ChatUtil;
import chatFrame.chatFrame;

public class createTextPanel1 extends JFrame {
	
	private JPanel panel1,panel2,panel3,panel4,panel5;
	private JLabel userTab,passTab;
	public static JLabel show;
	private JTextField userText;
	private JPasswordField passField;
	private JButton loginButton,regButton;
	public String userName;
	public String userPwd;
	public loginFrame obj;
	
	public createTextPanel1(loginFrame reg) {
		this.obj = reg;
	}

		//点击选项，返回容器
		public JComponent createTextPanel1() {
			//设计布局
			panel1 = new JPanel();
			panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
			panel2 = new JPanel();
			panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
			panel3 = new JPanel();
			panel3.setLayout(new FlowLayout(FlowLayout.CENTER));
			panel4 = new JPanel();
			panel4.setLayout(new GridLayout(4, 1));
			panel5 = new JPanel();
			panel5.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			
			userTab = new JLabel("账号:");
			userText = new JTextField(11);
		
			passTab = new JLabel("密码:");
			passField = new JPasswordField(11);
			passField.setEchoChar('*');
			
			show = new JLabel("");
			show.setText("");
			
			loginButton = new JButton("登录");
			loginButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
						
						userName = userText.getText();
						userPwd = String.valueOf(passField.getPassword());
						
						System.out.println("用户名:"+userName);
						System.out.println("密码:"+userPwd);
						loginFrame.Msg.send(ChatUtil.LOGIN_CHECK+"#["+userName+"]["+userPwd+"]");
						loginFrame.Msg.ReciveLoginName(obj,userName);
				}
			});
			regButton = new JButton("注册");
			regButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					new registerFrame(obj);
					obj.setVisible(false);
				}
			}
					);
			//把组建添加到panel1
			panel1.add(userTab);
			panel1.add(userText);
			panel2.add(passTab);
			panel2.add(passField);
			panel5.add(show);
			
			panel3.add(loginButton);
			panel3.add(regButton);
			
			panel4.add(panel1);
			panel4.add(panel2);
			panel4.add(panel3);
			panel4.add(panel5);
			return panel4;
			
		}
		
}

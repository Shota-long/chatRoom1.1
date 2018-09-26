package Login;

import Server.ChatUtil;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class createTextPanel2 extends JFrame implements ActionListener {
	
	private JPanel panel5,panel6,panel7,panel8;
	private JLabel addrTab,portTab;
	private JTextField addrText,portText;
	private JButton submit;
	private String address,port;
	
	public JPanel createTextPanel2() {
		
		//设计布局
		panel5 = new JPanel();
		panel5.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel6 = new JPanel();
		panel6.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel7 = new JPanel();
		panel7.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel8 = new JPanel();
		panel8.setLayout(new GridLayout(3, 1));
		
		addrTab = new JLabel("地址ַ:");
		addrText = new JTextField(11);
		addrText.setText("192.168.0.105");
		portTab = new JLabel("端口:");
		portText = new JTextField(11);
		portText.setText("10086");
		//address = addrText.getText();
		//port = portText.getText();
		submit = new JButton("确定");
		submit.addActionListener(this);
		//把组件添加到panel1
		panel5.add(addrTab);
		panel5.add(addrText);
		panel6.add(portTab);
		panel6.add(portText);
		panel7.add(submit);
		panel8.add(panel5);
		panel8.add(panel6);
		panel8.add(panel7);
		return panel8;
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		address = addrText.getText().trim();
		port = portText.getText().trim();
		loginFrame.Msg.connect(address,Integer.valueOf(port));
		loginFrame.Msg.read();
		loginFrame.jTabbedPane.setSelectedIndex(0);
	}
}

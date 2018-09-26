package chatFrame;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import Login.loginFrame;
import Server.ChatUtil;

public class personalFrame extends JFrame implements ActionListener{

	private JPanel p1,p2,p3,p4,p5,p6,p7;
	public  JTextArea area1;
	private static JTextArea area2;
	private JScrollPane js1,js2,js3,js4;
	private JLabel[] smile ;
	private String[] smile_lab;
	private JButton sendBut,sendFile,Empty,historyMsgBut;
	public String user_send;
	public String user_recv;
	private static personalFrame instance;
	private sendFileProgressBar sendfileprogressBar;
	private SimpleDateFormat df;

	private personalFrame()
	{
		//set p1
		p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 1));
		area1 = new JTextArea(20,40);
		area1.setFont(new Font("dialog",0,15));
		area1.setLineWrap(true);
		area1.setEditable(false);
		js1 = new JScrollPane(area1);
		js1.setBorder(new TitledBorder("个人聊天"));
		js1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		p1.add(js1);
		//set p2
		p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		smile_lab = new String[]{"😂","😘","😍","😊","😁",
				"😭","😜","😝","☺","😄",
				"😡","😀","😥","🙃","😋"};
		smile = new JLabel[smile_lab.length];
		for (int i = 0 ; i<smile_lab.length ; i++) {
			smile[i] = new JLabel(smile_lab[i]);
			smile[i].setFont(new Font("dialog",0,20));
			smile[i].addMouseListener(new MyMouseListener());
		}
		for (int i = 0; i< smile_lab.length; i++)
		{
			p2.add(smile[i]);
		}
		ImageIcon icon = new ImageIcon("D:\\WorkTest\\chatRoom1\\src\\Image\\file.jpg");
		sendFile = new JButton(icon);
		sendFile.setPreferredSize(new Dimension(20,18));
		sendFile.addActionListener(this);
		p2.add(sendFile);
		historyMsgBut = new JButton("历史纪录");
		historyMsgBut.addActionListener(this);
		p2.add(historyMsgBut);
		//set p3
		p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		area2 = new JTextArea(5,40);
		area2.setFont(new Font("dialog",0,15));
		area2.setLineWrap(true);
		js4 = new JScrollPane(area2);
		js4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
		p3.add(js4);
		//p4
		p4 = new JPanel();
		p4.setLayout(new FlowLayout(FlowLayout.RIGHT));
		sendBut = new JButton("发送");
		Empty = new JButton("清空");
		sendBut.addActionListener(this);
		Empty.addActionListener(this);
		p4.add(sendBut);
		p4.add(Empty);

		p5 = new JPanel();
		p5.setLayout(new BorderLayout());
		p5.add(p1,BorderLayout.NORTH);
		p5.add(p2,BorderLayout.SOUTH);
		//
		p6 = new JPanel();
		p6.setLayout(new BorderLayout());
		p6.add(p3,BorderLayout.NORTH);
		p6.add(p4,BorderLayout.SOUTH);
		//
		p7 = new JPanel();
		p7.setLayout(new BorderLayout());
		p7.add(p5,BorderLayout.NORTH);
		p7.add(p6,BorderLayout.SOUTH);

		this.getContentPane().add(p7);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					user_recv = "";
					instance.dispose();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	private void setWindow()
	{
		this.setTitle("TO " + this.user_recv);
		this.setSize(620,800);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	public void setName(String user_send, String html_source)
	{
		this.user_send = user_send;
		this.user_recv = parse(html_source);
		setWindow();
		showWindow();
		instance.area1.setText("");
		int size = instance.area1.getText().length();
		instance.area1.replaceRange("",0,size);
		addMessage();
	}

	private void showWindow()
	{
		this.setVisible(true);
		this.setExtendedState(NORMAL);
		this.requestFocus();
	}


	public static personalFrame getInstance()
	{
		if (instance == null)
			instance = new personalFrame();
		return instance;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		try {
			// TODO Auto-generated method stub
			if (e.getActionCommand().equals("发送")) {
				String message = area2.getText();
				String time = df.format(new Date());
				loginFrame.Msg.send(ChatUtil.PRIVATE_CHAT + "#[" + user_send + "]To[" + user_recv + "]:"+time+"\n" + message);
				area1.append("[" + user_send + "]::" +time+"\n"+ message + "\n");
				MessgeMap messgeMap = MessgeMap.getInstance();
				messgeMap.addMessage(user_recv,"[" + user_send + "]::"+time+"\n" + message);
				area2.setText("");
			}
			if (e.getActionCommand().equals("清空")) {
				area2.setText("");
			}
			if (e.getSource()==sendFile) {
				sendFile();
			}
			if (e.getSource()==historyMsgBut){
				historyMsgFrame historyMsg = historyMsgFrame.getInstance();
				historyMsg.showWindows();
				historyMsg.area1.setText("");
				loginFrame.Msg.send(ChatUtil.PRIVATE_HISTORY+"#[" + user_send + "][" + user_recv + "]");
			}
		}catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	//发送文件
	private void sendFile() {
		System.out.println("发送文件");
		JFileChooser chooser = new javax.swing.JFileChooser();
		if (chooser.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
			File path = chooser.getCurrentDirectory();
			File file = chooser.getSelectedFile();
			System.out.println(file.getName());
			System.out.println(path);
			sendfileprogressBar= new sendFileProgressBar(this);
			sendfileprogressBar.init();
			sendfileprogressBar.initUI();
			loginFrame.Msg.send(file);
			showProcessBar();
		}
	}

	private void showProcessBar() {
		Thread t = new Thread(sendfileprogressBar);
		t.start();
	}

	public String parse(String name)
	{
		String expr = "'>(.+?)</";
		Pattern r = Pattern.compile(expr);
		Matcher m = r.matcher(name);
		if (m.find())
		{
			System.out.println(m.group(1));
			return m.group(1);
		}
		else return name;
	}
	private void addMessage(){
		MessgeMap messgeMap;
		messgeMap = MessgeMap.getInstance();
		Queue<String> messageQueue= messgeMap.getMessageQueue(this.user_recv);
		if (messageQueue == null)
			return;
		for (String q:messageQueue) {
			System.out.println("queue:"+q);
		}
		System.out.println("\n");
		for (String q:messageQueue) {
			area1.append(q+"\n");
			//messageQueue.
		}
	}
	//鼠标点击表情
	class MyMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount()==1){
				JLabel Recive_simle = (JLabel) e.getComponent();
				System.out.println(Recive_simle.getText());
				area2.append(Recive_simle.getText());
			}
		}
	}
}

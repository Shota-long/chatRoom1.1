package chatFrame;

import Login.createTextPanel1;
import Login.loginFrame;
import Login.registerFrame;
import MySocket.MsgSender;
import Server.ChatUtil;
import javax.swing.*;
import java.util.*;

public class AppenToTextArea extends MsgSender<JTextArea> {

    private chatFrame chat;
    private registerFrame register;
    List user_list = new ArrayList();
    private personalFrame personal;
    private loginFrame textPanel1;
    private String login_name;
    private Queue<String> queue = new LinkedList<String>();
    public static int flag = 0;
    private Map fileNameMap = new HashMap();
    @Override
    public void send(String s) {
        super.send(s);
    }
    public void RecivechatFrame(chatFrame chat){
        this.chat = chat;
    }
    public void ReciveregisterFrame(registerFrame register){this.register = register;}
    public void ReciveLoginName(loginFrame textPanel1,String login_name){
        this.textPanel1 = textPanel1;
        this.login_name = login_name;
    }
    @Override
    public void handleText(String s) {
//        System.out.println("接收到的消息："+s);
        int n = s.indexOf("#");
        String mes0 = s.substring(0,n);
        String mes1 = s.substring(n+1,s.length()).trim();
        //在线列表处理
        if (mes0.equals(ChatUtil.FRIEND_LIST)) {
            if (!(mes1.equals(""))){
                String[] list = mes1.split(",");
                for (String friendlist : list) {
                    chat.model.addElement(friendlist.trim());
                    user_list.add(friendlist.trim());
                }
            }
        }
        //私聊
        else if (mes0.equals(ChatUtil.PRIVATE_CHAT)){
            personal = personalFrame.getInstance();
            System.out.println("加到队列中");
            MessgeMap messgeMap = MessgeMap.getInstance();
                System.out.println("输出队列"+mes1);
                String sender_name = mes1.substring(1,mes1.indexOf("]"));
                System.out.println("sender_name"+sender_name);
                int i=0;
                if (flag == 0){
                    i = chat.model.indexOf(sender_name.trim());
                    System.out.println(i);
                }
                 else if (flag == 1 ){
                     i = chat.model.indexOf("<html><font color='red'>"+sender_name+"</font></html>");
                }
                //chat.model.setElementAt("<html><font color='red'>"+sender_name+"</font></html>",i);
                messgeMap.addMessage(sender_name,mes1);
                if (sender_name.equals(personal.user_recv))
                {
//                    System.out.println(sender_name);
//                    System.out.println("用户1："+personal.user_send);
//                    System.out.println("用户2:"+personal.user_recv);
                    personal.area1.append(mes1+"\n");
                    //personal.area1.paintImmediately(personal.area1.getBounds());
                }
                else{
                    chat.model.setElementAt("<html><font color='red'>"+sender_name+"</font></html>",i);
                    flag = 1;
                }
        }
        //进入聊天室
        else if (mes0.equals(ChatUtil.OPEN_ROOM)){
            chat.model.addElement(mes1);
            user_list.add(mes1);
        }
        //把消息添加到聊天框
        else if (mes0.equals(ChatUtil.PUBLLIC_CHAT))
            ui.append(mes1+"\n");
        //退出修改在线列表
        else if (mes0.equals(ChatUtil.CLOSE_ROOM)){
            chat.model.clear();
            for (Iterator iterators = user_list.iterator();iterators.hasNext();){
                String list =  (String )iterators.next();
                if (list.equals(mes1))
                    iterators.remove();
            }
            for (Iterator iterators = user_list.iterator();iterators.hasNext();){
                 String list =  (String )iterators.next();
                 chat.model.addElement(list);
            }
        }
        //注册
        else if(mes0.equals(ChatUtil.SIGN_IN)){
            if (mes1.trim().equals("true")) {
                new loginFrame();
                register.dispose();
            }
            else if (mes1.trim().equals("false"))
                register.show.setText("注册失败");
            else
                System.out.println("异常");
        }
        //登录验证
        else if (mes0.equals(ChatUtil.LOGIN_CHECK)){
            if (mes1.trim().equals("true")) {
                chatFrame chat= new chatFrame(login_name);
                chat.getSocket();
                textPanel1.dispose();
            }
            if (mes1.trim().equals("false"))
                createTextPanel1.show.setText("账号密码错误");
        }
        else if (mes0.equals(ChatUtil.FILENAME)){
            String[] mes2 = mes1.split("#");
            Init(mes2[0], mes2[1], mes2[2]);
            fileNameMap.put(mes2[1],mes2[0]);
            System.out.println("收到文件名:"+mes2[0]);
            System.out.println("收到uuid:"+mes2[1]);
            System.out.println("收到文件总长度:"+mes2[2]);
        }
        else if (mes0.equals(ChatUtil.FILECONTENT)){
            String getuuid = ChargeFile(getMsg_byte());
            System.out.println("获取文件uuid:"+getuuid);
            if(!getuuid.equals("")){
                int[] fileNumber=getReciveState(getuuid);
                if (fileNumber[0] == fileNumber[1]){
                    String title = "消息";
                    String fileName = (String) fileNameMap.get(getuuid);
                    fileNameMap.remove(getuuid);
                    JOptionPane.showMessageDialog(chat,fileName+"文件已接收成功,放置D:\\FileRecive",title,JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        else if(mes0.equals(ChatUtil.PUBLIC_HISTORY)){
            historyMsgFrame.instance.area1.append(mes1+"\n");
        }
        else if(mes0.equals(ChatUtil.PRIVATE_HISTORY)){
            historyMsgFrame.instance.area1.append(mes1+"\n");
        }
    }
}

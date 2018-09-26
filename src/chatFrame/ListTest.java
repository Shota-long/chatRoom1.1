package chatFrame;

import javax.swing.*;
import javax.swing.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ListTest {
    public static void main(String args[])
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        System.out.println(time);
    }
}

package ASimulatorSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {

    JLabel l1, l2;
    JButton b1, b2, b3, b4, b5, b6, b7, b8;
    JTextField t1;
    String pin;

    FastCash(String pin) {
        this.pin = pin;
        

        l1 = new JLabel("SELECT WITHDRAWL AMOUNT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        b1 = new JButton("Tk 100");
        b2 = new JButton("Tk 500");
        b3 = new JButton("Tk 1000");
        b4 = new JButton("Tk 2000");
        b5 = new JButton("Tk 5000");
        b6 = new JButton("Tk 10000");
        b7 = new JButton("BACK");

        setLayout(null);

        l1.setBounds(300,10,500,35);
        add(l1);

        b1.setBounds(200,200,150,35);
        add(b1);
        
        b2.setBounds(200+300,200,150,35);
        add(b2);
        
        b3.setBounds(200,200+100,150,35);
        add(b3);
        
        b4.setBounds(200+300,200+100,150,35);
        add(b4);
        
        b5.setBounds(200,200+100+100,150,35);
        add(b5);
        
        b6.setBounds(200+300,200+100+100,150,35);
        add(b6);
        
        b7.setBounds(200+300-150,200+100+100+100,150,35);
        add(b7);
        
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);

        getContentPane().setBackground(Color.BLACK);
        setSize(850,650);
        setLocation(250,70);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String amount = ((JButton)ae.getSource()).getText().substring(3); //k
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from bank where pin = '"+pin+"'");
            int balance = 0;
            while (rs.next()) {
                if (rs.getString("mode").equals("Deposit")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            } String num = "17";
            if (ae.getSource() != b7 && balance < Integer.parseInt(amount)) {
                JOptionPane.showMessageDialog(null, "Insuffient Balance");
                return;
            }

            if (ae.getSource() == b7) {
                this.setVisible(false);
                new Transactions(pin).setVisible(true);
            }else{
                Date date = new Date();
                c.s.executeUpdate("insert into bank values('"+pin+"', '"+date+"', 'Withdrawl', '"+amount+"')");
                JOptionPane.showMessageDialog(null, "Rs. "+amount+" Debited Successfully");
                    
                setVisible(false);
                new Transactions(pin).setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new FastCash("").setVisible(true);
    }
}

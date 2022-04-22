import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class SnakeGra extends JFrame
{
    static JButton startGry;
    static JButton zapiszWyniki;
    static JButton pokazWyniki;
    public SnakeGra() {
        super("Snake");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        startGry = new JButton("Start Gry");
        startGry.setBounds(310,230,140,40);
        startGry.addActionListener(new Start());

        zapiszWyniki = new JButton("Zapisz Wyniki");
        zapiszWyniki.setBounds(310,300,140,40);
        zapiszWyniki.addActionListener(new Wyniki());

        pokazWyniki = new JButton("Pokaz Wyniki");
        pokazWyniki.setBounds(310,370,140,40);
        pokazWyniki.addActionListener(new PokazWyniki());

        Okno okno = new Okno();
        add(startGry);
        add(pokazWyniki);
        add(zapiszWyniki);
        add(okno);
        setVisible(true);
    }
    private static class Start implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            startGry.setEnabled(false);
            zapiszWyniki.setEnabled(false);
            pokazWyniki.setEnabled(false);
            Okno.napisGameOver = false;
            Okno.timer.start();
        }
    }
    private class Wyniki implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            zapiszDoTxt();
        }
    }
    private static class PokazWyniki implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            OknoWyniki oknoZwynikami = new OknoWyniki();
        }
    }
    public void zapiszDoTxt()
    {
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime czas = LocalDateTime.now();
        try {
            FileWriter writer = new FileWriter("wyniki.txt", true);
            PrintWriter pw = new PrintWriter(writer);
            Collections.sort(Okno.wyniki);
            Collections.reverse(Okno.wyniki);
            pw.println("Data: "+ formater.format(czas));
            for(int i =0 ; i<Okno.wyniki.size();i++)
            {
                pw.println(i+1+". "+Okno.wyniki.get(i));
            }
            pw.println("-------------------");
            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    static class OknoWyniki extends JFrame
    {
        public OknoWyniki() {
            super("Wyniki");
            setSize(600, 300);
            setResizable(true);
            setLocationRelativeTo(null);
            setVisible(true);
            JTextArea wyniki = new JTextArea();
            StringBuilder s = new StringBuilder();
            for(int i =0; i<Okno.wyniki.size();i++)
            {
                s.append("Rozgrywka ").append(i + 1).append(": ").append(Okno.wyniki.get(i)).append("\n");
            }
            add(wyniki);
            wyniki.setForeground(Color.BLACK);
            wyniki.setText(s.toString());
            wyniki.setBounds(300,10,140,40);
        }
    }
    public static void main(String[] args) {
        SnakeGra gra = new SnakeGra();
    }
}


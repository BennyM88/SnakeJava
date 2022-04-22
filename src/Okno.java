import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Okno extends JPanel implements ActionListener {

    static final int szerokoscOkna = 745;
    static final int wysokoscOkna = 520;
    static final int jednostka = 10;
    static final int wielkoscOkna =4800;
    static int czas = 65;
    final int [] tabX = new int[wielkoscOkna];
    final int [] tabY = new int[wielkoscOkna];
    private int jablkoX;
    private int jablkoY;
    private final Image glowaPrawo;
    private final Image glowaLewo;
    private final Image glowaGora;
    private final Image glowaDol;
    private final Image jablko;
    private final Image cialo;
    private final Image obwod;
    private final Image gameover;
    public static Timer timer;
    boolean stanGry = false;
    int dlugoscWeza = 4;
    private boolean  gora,dol,prawa;
    private boolean lewa = true;
    Random random = new Random();
    static int wynik =0;
    static int najlepszyWynik = 0;
    static ArrayList<Integer> wyniki = new ArrayList<>();
    public static boolean napisGameOver = false;
    public Okno()
    {
        this.addKeyListener(new Przyciski());
        this.setFocusable(true);
        setBackground(Color.DARK_GRAY);
        ImageIcon igp = new ImageIcon(this.getClass().getResource("glowaPrawo.png"));
        glowaPrawo = igp.getImage();
        ImageIcon igl = new ImageIcon(this.getClass().getResource("glowaLewo.png"));
        glowaLewo = igl.getImage();
        ImageIcon igg = new ImageIcon(this.getClass().getResource("glowaGora.png"));
        glowaGora = igg.getImage();
        ImageIcon igd = new ImageIcon(this.getClass().getResource("glowaDol.png"));
        glowaDol = igd.getImage();
        ImageIcon ic = new ImageIcon(this.getClass().getResource("cialo.png"));
        cialo = ic.getImage();
        ImageIcon ij = new ImageIcon(this.getClass().getResource("jablko.png"));
        jablko = ij.getImage();
        ImageIcon io = new ImageIcon(this.getClass().getResource("obwod.png"));
        obwod = io.getImage();
        ImageIcon igo = new ImageIcon(this.getClass().getResource("gameover.png"));
        gameover = igo.getImage();
        StartGry();
    }
    public void StartGry()
    {
        for(int i =0; i<=dlugoscWeza; i++)
        {
            tabX[i] = 330 + i*10;
            tabY[i] = 230;
        }
        stanGry = true;
        timer = new Timer(czas, this);
        RespJablek();
    }
   public void paint(Graphics g)
   {
       super.paint(g);
       if(napisGameOver)
       {
           g.drawImage(gameover, 280, 30, this);
       }
       if(stanGry) {
           for (int i = 0; i < dlugoscWeza; i++) {
               if (i == 0) {
                   if (prawa)
                       g.drawImage(glowaPrawo, tabX[i], tabY[i], this);
                   else if (lewa)
                       g.drawImage(glowaLewo, tabX[i], tabY[i], this);
                   else if (gora)
                       g.drawImage(glowaGora, tabX[i], tabY[i], this);
                   else
                       g.drawImage(glowaDol, tabX[i], tabY[i], this);
               }
               else {
                   g.drawImage(cialo, tabX[i], tabY[i], this);
               }
           }
           g.drawImage(jablko,jablkoX,jablkoY,this);
       }
       for(int i =0;i<785;i+=10)
       {
           g.drawImage(obwod,i,0,this);
           g.drawImage(obwod,i,550,this);
           g.drawImage(obwod,i,10,this);
           g.drawImage(obwod,i,540,this);
           g.drawImage(obwod,i,20,this);
           g.drawImage(obwod,i,30,this);
           g.drawImage(obwod,i,40,this);
           g.drawImage(obwod,i,50,this);
           g.drawImage(obwod,i,530,this);
       }
       for(int i =10;i<555;i+=10)
       {
           g.drawImage(obwod,775,i,this);
           g.drawImage(obwod,0,i,this);
           g.drawImage(obwod,770,i,this);
           g.drawImage(obwod,10,i,this);
           g.drawImage(obwod,760,i,this);
           g.drawImage(obwod,20,i,this);
           g.drawImage(obwod,750,i,this);
       }
       g.setColor(Color.white);
       g.setFont(new Font("arial", Font.BOLD,18));
       g.drawString("Wynik: "+ wynik,180,35);
       g.drawString("Najlepszy Wynik: "+ najlepszyWynik,500,35);
   }
    public void Poruszanie()
    {
       for (int i = dlugoscWeza; i>0;i--) {
           tabX[i] = tabX[i-1];
           tabY[i]=tabY[i-1];
       }
       if (lewa) {
           tabX[0] -= jednostka;
       }
       if (prawa) {
           tabX[0] += jednostka;
       }
       if (gora) {
           tabY[0] -= jednostka;
       }
       if (dol) {
           tabY[0] += jednostka;
       }
    }
    public void RespJablek()
    {
            jablkoX = (random.nextInt(71)+3)*10;
            jablkoY = (random.nextInt(46)+6)*10;
    }
    public void JedzenieJablek()
    {
        if((tabX[0] == jablkoX) && (tabY[0]==jablkoY))
        {
            wynik++;
            dlugoscWeza++;
            RespJablek();
        }
    }
    public void Kolizje()
    {
        if(tabX[0]>szerokoscOkna | tabY[0] > wysokoscOkna | tabX[0] <30 | tabY[0] < 60)
        {
            stanGry = false;;
        }
        for (int i =1 ; i <dlugoscWeza;i++)
        {
            if ((tabX[0] == tabX[i + 1]) && (tabY[0] == tabY[i + 1])) {
                stanGry = false;
                break;
            }
        }
    }
    class Przyciski extends KeyAdapter
    {
       @Override
       public void keyPressed(KeyEvent e) {
           if ((e.getKeyCode() == KeyEvent.VK_LEFT) && (!prawa)) {
               lewa = true;
               gora = false;
               dol = false;
           }
           else if ((e.getKeyCode() == KeyEvent.VK_RIGHT) && (!lewa)) {
               prawa = true;
               gora = false;
               dol = false;
           }
           else if ((e.getKeyCode() == KeyEvent.VK_UP) && (!dol)) {
               gora = true;
               prawa = false;
               lewa = false;
           }
           else if ((e.getKeyCode() == KeyEvent.VK_DOWN) && (!gora)) {
               dol = true;
               prawa = false;
               lewa = false;
           }
       }
   }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(stanGry)
        {
            Poruszanie();
            JedzenieJablek();
            Kolizje();
            repaint();
        }
        else
        {
            napisGameOver =true;
            timer.stop();
            if (wynik > najlepszyWynik)
            {
                najlepszyWynik = wynik;
            }
            dlugoscWeza = 4;
            for(int i =0; i<=dlugoscWeza; i++)
            {
                tabX[i] = 330 + i*10;
                tabY[i] = 230;
            }
            stanGry = true;
            lewa = true;
            prawa = false;
            gora = false;
            dol = false;
            SnakeGra.startGry.setEnabled(true);
            SnakeGra.zapiszWyniki.setEnabled(true);
            SnakeGra.pokazWyniki.setEnabled(true);
            repaint();
            wyniki.add(wynik);
            wynik = 0;
        }
    }
}


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    public static void main(String[] args) {

        Arvore a = new Arvore();
        JFrame f = new JFrame("Árvore Binária");
        f.setSize(850, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());

        JTextField t = new JTextField(8);
        JButton b = new JButton("Inserir");
        JPanel pTop = new JPanel();
        pTop.add(new JLabel("Número:"));
        pTop.add(t);
        pTop.add(b);

        Painel p = new Painel(a);

        ActionListener acao = e -> {
            try {
                a.inserir(Integer.parseInt(t.getText()));
                t.setText("");
                t.requestFocus();
                p.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "Digite número válido");
            }
        };

        b.addActionListener(acao);
        t.addActionListener(acao);

        f.add(pTop, BorderLayout.NORTH);
        f.add(p);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}

class No {
    int v; No e, d;
    No(int v) { this.v = v; }
}

class Arvore {
    No r;
    void inserir(int v){ r = ins(r,v); }
    No ins(No n,int v){
        if(n==null) return new No(v);
        if(v<n.v) n.e=ins(n.e,v);
        else if(v>n.v) n.d=ins(n.d,v);
        return n;
    }
}

class Painel extends JPanel {

    Arvore a;
    Painel(Arvore a){ this.a=a; }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        draw(g2,a.r,getWidth()/2,60,getWidth()/4);
    }

    void draw(Graphics2D g,No n,int x,int y,int esp){
        if(n==null) return;
        int r=20;

        if(n.e!=null){
            int xe=x-esp, ye=y+80;
            line(g,x,y,xe,ye,r);
            draw(g,n.e,xe,ye,esp/2);
        }
        if(n.d!=null){
            int xd=x+esp, yd=y+80;
            line(g,x,y,xd,yd,r);
            draw(g,n.d,xd,yd,esp/2);
        }

        g.setColor(Color.WHITE);
        g.fillOval(x-r,y-r,r*2,r*2);
        g.setColor(Color.BLACK);
        g.drawOval(x-r,y-r,r*2,r*2);

        String s=String.valueOf(n.v);
        FontMetrics fm=g.getFontMetrics();
        g.drawString(s,x-fm.stringWidth(s)/2,y+fm.getAscent()/4);
    }

    void line(Graphics2D g,int x1,int y1,int x2,int y2,int r){
        double ang=Math.atan2(y2-y1,x2-x1);
        int xi=(int)(x1+r*Math.cos(ang));
        int yi=(int)(y1+r*Math.sin(ang));
        int xf=(int)(x2-r*Math.cos(ang));
        int yf=(int)(y2-r*Math.sin(ang));
        g.drawLine(xi,yi,xf,yf);
    }
}
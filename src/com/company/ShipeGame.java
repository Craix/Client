package com.company;

import com.company.DTO.Array2Int;
import com.company.DTO.Single2Int;
import javafx.geometry.Point3D;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ShipeGame extends JPanel implements ActionListener, MouseListener {

    enum GameStatus
    {
        start,
        loop,
        end,
    }

    private BufferedImage radar, sea, bg, ship, shipR, red, green, yellow, fire, blue;
    private JLabel explain = new JLabel();
    private JButton ready = new JButton();
    private JButton clear = new JButton();
    private int count = 0;
    private int Array[][] = new int[10][10];
    private Point3D[] shipPosition = new Point3D[6];
    private GameStatus status = GameStatus.start;
    private int hitPoint[][] = new int[10][10];
    private Point selectedPoint = new Point();
    private int PlayerPoint = 0;
    private int ComputerPoint = 0;
    private JButton lauch = new JButton();
    private JLabel shootText = new JLabel();
    private Network network = new Network();
    private boolean playerFirst = true;
    private JLabel gameResult = new JLabel();

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(bg, 0, 0, 800, 600, null);

        if(status == GameStatus.start)
            drawSelectShip(g2);

        if(status == GameStatus.loop)
            drawBoard(g2);

    }

    private void drawSelectShip(Graphics2D g2)
    {
        g2.drawImage(sea, 175, 25, 450, 450, null);

        for(int i = 0; i <count; i++)
        {
            if((int)shipPosition[i].getZ() == -1)
            {
                int x = (216 + (int)shipPosition[i].getX() * 41) - 41 + 17;
                int y = (66 + (int)shipPosition[i].getY() * 41)+ 6;
                g2.drawImage(shipR, x, y, 90, 28, null);
            }
            else if((int)shipPosition[i].getZ() == 1)
            {
                int x = 216 + (int)shipPosition[i].getX() * 41 + 6;
                int y = 66 + (int)shipPosition[i].getY() * 41 - 41 + 16;
                g2.drawImage(ship, x, y, 28, 90, null);
            }
        }
    }

    private void drawBoard(Graphics2D g2)
    {
        g2.drawImage(sea, 25, 75, 350, 350, null);
        g2.drawImage(radar, 425, 75, 350, 350, null);

        //draw shipe
        for(int i = 0; i < count; i++)
        {
            if((int)shipPosition[i].getZ() == -1)
            {
                int x = (25+32) + (int)shipPosition[i].getX() * 32 - 32 + 3;
                int y = (75+32) + (int)shipPosition[i].getY() * 32 + 2;
                g2.drawImage(shipR, x, y, 90, 28, null);
            }
            else if((int)shipPosition[i].getZ() == 1)
            {
                int x = (25+32) + (int)shipPosition[i].getX() * 32 + 2;
                int y = (75+32) + (int)shipPosition[i].getY() * 32  - 32+ 3;
                g2.drawImage(ship, x, y, 28, 90, null);
            }
        }

        //draw selcted
        if(!selectedPoint.equals(new Point(-1, -1)))
        {
            g2.drawImage(yellow, (425+32) + selectedPoint.x * 32 + 2, (75+32)+ selectedPoint.y * 32 + 2, 28, 28, null);
        }

        //draw hitpoitn
        for(int i = 0; i<10; i++)
        {
            for(int j = 0;j < 10; j++)
            {
                if(hitPoint[i][j] == 1)
                {
                    g2.drawImage(green, (425+32) + i * 32 + 2, (75+32)+ j * 32 + 2, 28, 28, null);
                }

                if(hitPoint[i][j] == -1)
                {
                    g2.drawImage(red, (425+32) + i * 32 + 2, (75+32)+ j * 32 + 2, 28, 28, null);
                }
            }
        }

        //draw shipe map
        for(int i = 0; i<10; i++)
        {
            for(int j = 0;j < 10; j++)
            {
                if(Array[i][j] == -1)
                {
                    g2.drawImage(fire, (25+32) + i * 32 + 2, (75+32)+ j * 32 + 2, 28, 28, null);
                }

                if(Array[i][j] == -2)
                {
                    g2.drawImage(blue, (25+32) + i * 32 + 2, (75+32)+ j * 32 + 2, 28, 28, null);
                }
            }
        }
    }

    public void start(boolean start)
    {
        this.setSize(800, 600);
        this.setLocation(0,0);
        this.addMouseListener(this);
        this.setLayout(null);

        ready.setBounds(25, 525, 100, 50);
        ready.setText("Ready");
        ready.addActionListener(this);
        this.add(ready);

        clear.setBounds(150, 525, 100, 50);
        clear.setText("Clear");
        clear.addActionListener(this);
        this.add(clear);

        explain.setBounds(250, 525, 550, 50);
        String exp = "<html>Use mouse (left or right) to mark ship position.<BR>Press ready if done to start or clear to restart.</html>";
        explain.setText(exp);
        explain.setFont(explain.getFont().deriveFont(18.0f));
        explain.setHorizontalAlignment(SwingConstants.CENTER);
        explain.setVerticalAlignment(SwingConstants.CENTER);
        this.add(explain);

        lauch.setBounds(50, 525, 100, 50);
        lauch.setText("Lauch");
        lauch.addActionListener(this);

        shootText.setBounds(200, 525, 600, 50);
        shootText.setText("Select empty filed on right map, and click lauch to set missle");
        shootText.setFont(shootText.getFont().deriveFont(18.0f));
        shootText.setHorizontalAlignment(SwingConstants.CENTER);
        shootText.setVerticalAlignment(SwingConstants.CENTER);

        gameResult.setBounds(200, 525, 600, 50);
        gameResult.setFont(gameResult.getFont().deriveFont(18.0f));
        gameResult.setHorizontalAlignment(SwingConstants.CENTER);
        gameResult.setVerticalAlignment(SwingConstants.CENTER);

        try
        {
            radar = ImageIO.read(new File("data\\radar.png"));
            sea = ImageIO.read(new File("data\\sea.png"));
            bg = ImageIO.read(new File("data\\bg3.png"));
            ship = ImageIO.read(new File("data\\ship1.png"));
            shipR = ImageIO.read(new File("data\\shipR.png"));
            red = ImageIO.read(new File("data\\red.png"));
            green = ImageIO.read(new File("data\\green.png"));
            yellow = ImageIO.read(new File("data\\yelow.png"));
            fire = ImageIO.read(new File("data\\fire.png"));
            blue = ImageIO.read(new File("data\\blue.png"));

        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        status = GameStatus.start;
        clear();

        ComputerPoint = 0;
        PlayerPoint = 0;

        selectedPoint.setLocation(-1, -1);

        for(int i = 0; i < 10; i++)
        {
            for(int j  = 0; j < 10; j++)
            {
                hitPoint[i][j] = 0;
            }
        }

        playerFirst = start;
    }

    private void playerMove()
    {
        int x = selectedPoint.x;
        int y = selectedPoint.y;

        if(x != -1 && y != -1) {
            try {
                if (network.sendMissle(x, y)) {
                    hitPoint[x][y] = 1;
                    PlayerPoint++;
                } else {
                    hitPoint[x][y] = -1;
                }

                gameStatus();
                repaint();

                selectedPoint.setLocation(-1, -1);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void computerMove()
    {
        try {
            Single2Int single2Int = network.getMissle();

            int x = single2Int.getX();
            int y = single2Int.getY();

            if(x >= 0 && x <= 9 && y >= 0 && y <= 9)
            {
                if(Array[x][y] == 1)
                {
                    ComputerPoint++;
                    Array[x][y] = -1;
                }
                else
                {
                    Array[x][y] = -2;
                }
            }

            gameStatus();
            repaint();
        }
        catch(Exception e)
        {

        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        Object source = actionEvent.getSource();

        if(source == clear)
        {
            clear();
        }

        if(source == ready && count == 6)
        {
            ready();
        }

        if(source == lauch)
        {
            playerMove();
            computerMove();
        }
    }

    private void createShip(MouseEvent mouseEvent)
    {
        System.out.println("Raw mouse point: "+ mouseEvent.getPoint());
        Point point = getMousePosition();

        if(point != null)
        {
            if(count < 6) {
                int x = (point.x - 216) / 41;
                int y = (point.y - 66) / 41;

                System.out.println("Converted position: " + x + " $ " + y);

                //button left - ship top
                if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    if (x > 0 && x < 9) {
                        if (Array[x - 1][y] != 1 && Array[x][y] != 1 && Array[x + 1][y] != 1) {
                            for (int i = 0; i < 3; i++) {
                                Array[x - 1 + i][y] = 1;
                            }

                            shipPosition[count] = new Point3D(x, y, -1);
                            count++;
                            System.out.println("Update array, actual ship: " +  count);
                        }
                    }
                }

                //button right - ship mid
                if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
                    if (y > 0 && y < 9) {
                        if (Array[x][y - 1] != 1 && Array[x][y] != 1 && Array[x][y + 1] != 1) {
                            for (int i = 0; i < 3; i++) {
                                Array[x][y - 1 + i] = 1;
                            }
                            shipPosition[count] = new Point3D(x, y, 1);
                            System.out.println(shipPosition[0].getX());

                            count++;
                            System.out.println("Update array, actual ship: " +  count);
                        }
                    }
                }
            }
        }
    }

    private void selectTarget(MouseEvent mouseEvent)
    {
        System.out.println("Raw mouse point: "+ mouseEvent.getPoint());
        Point point = getMousePosition();

        if(point != null)
        {
            int x = (point.x - 425 - 32)/32;
            int y = (point.y - 75 - 32)/32;

            if(x >= 0 && x <= 9 && y >= 0 && y <= 9)
            {
                System.out.println("Converted point: " + x + " $ " + y);
                if(hitPoint[x][y] == 0) {
                    selectedPoint.setLocation(x, y);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        if(status == GameStatus.start)
            createShip(mouseEvent);

        if(status == GameStatus.loop)
            selectTarget(mouseEvent);

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent)
    {

    }

    private void clear()
    {
        for(int i = 0; i < 10; i++)
        {
            for( int j = 0; j < 10; j++)
            {
                Array[i][j] = 0;
            }
        }

        count = 0;

        for(int i = 0; i < 6; i++)
        {
            shipPosition[i] = new Point3D(-1, -1, 0);
        }

        this.repaint();
    }

    private void ready()
    {
        this.removeAll();
        this.add(lauch);
        this.add(shootText);

        try {
            network.sendMap(new Array2Int(Array));
            network.generateMap();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }

        if(!playerFirst)
        {
            computerMove();
        }

        status = GameStatus.loop;
        this.repaint();
    }

    private void gameStatus()
    {
        if(PlayerPoint == 18) {
            //player win end game
            this.removeAll();
            gameResult.setText("Palyer win game, close window to end game");
            this.add(gameResult);
            status = GameStatus.end;
            this.repaint();
        }

        if(ComputerPoint == 18) {
            //comouter win
            this.removeAll();
            gameResult.setText("Computer win game, close window to end game");
            this.add(gameResult);
            status = GameStatus.end;
            this.repaint();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    public int gameResult()
    {
        if(PlayerPoint == 18 && ComputerPoint == 18)
        {
            return 0;
        }
        else if(PlayerPoint == 18)
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }
}

package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Menu implements ActionListener
{
    private JFrame frame = new JFrame();
    private JLabel startGame = new JLabel();
    private final String[] object0 = {"Player", "Computer"};
    private JComboBox select0 = new JComboBox(object0);
    private final String[] object1 = {"Ship", "TTT", "RSP"};
    private JComboBox select1 = new JComboBox(object1);
    private JButton newGame = new JButton();
    private JFrame gameWindow = new JFrame();
    private ShipeGame shipGame = new ShipeGame();
    private TTTGame ttt = new TTTGame();
    private RSPGame rsp = new RSPGame();
    private String selectedGame = "";
    private String login = "Craix";
    private Network network = new Network();

    public Menu()
    {
        startGame.setBounds(25, 25, 350, 50);
        startGame.setText("Select game, and start!");
        startGame.setFont(startGame.getFont().deriveFont(18.0f));
        startGame.setHorizontalAlignment(SwingConstants.CENTER);
        startGame.setVerticalAlignment(SwingConstants.CENTER);
        frame.add(startGame);

        select0.setBounds(100, 100, 200, 50);
        select0.setVisible(true);
        select0.setSelectedIndex(0);
        frame.add(select0);

        select1.setBounds(100, 175, 200, 50);
        select1.setVisible(true);
        select1.setSelectedIndex(0);
        frame.add(select1);

        newGame.setBounds(100, 250, 200, 50);
        newGame.setText("Start Game");
        newGame.addActionListener(this);
        frame.add(newGame);

        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        gameWindow.setVisible(false);

        gameWindow.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent event) {
                onEnd();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        Object source = actionEvent.getSource();

        if(source == newGame && !gameWindow.isVisible())
        {
            System.out.println("Zaczynamy nowa gre");
            boolean first = select0.getSelectedItem().equals("Player");
            System.out.println("Pierwszy ruch wykona gracz: " + first);
            System.out.println("Wybrana gra: " + select1.getSelectedItem());

            //to select specyfic game
            switch(select1.getSelectedIndex())
            {
                case 0:
                {
                    onStart();
                    selectedGame = "Ship";
                    shipGame.start(first);
                    System.out.println("Start ship game");
                    gameWindow.getContentPane().removeAll();
                    gameWindow.setVisible(true);
                    gameWindow.getContentPane().add(shipGame);
                    gameWindow.pack();
                    break;
                }
                case 1:
                {
                    onStart();
                    selectedGame = "ttt";
                    ttt.start(first);
                    System.out.println("Start ttt game");
                    gameWindow.getContentPane().removeAll();
                    gameWindow.setVisible(true);
                    gameWindow.getContentPane().add(ttt);
                    gameWindow.pack();
                    break;
                }
                case  2:
                {
                    //jeszcze nie zroibione
                    onStart();
                    selectedGame = "RSP";
                    rsp.start(first);
                    System.out.println("Start RSP game");
                    gameWindow.getContentPane().removeAll();
                    gameWindow.setVisible(true);
                    gameWindow.getContentPane().add(rsp);
                    gameWindow.pack();
                    break;
                }
            }
        }
    }

    private void onStart()
    {
        //funkcja wołana przy staarcie dowlnej gry
        System.out.println("Zaczynamay nowa gry");

    }

    private void onEnd()
    {
        //funkcja wołana na koniec każdej gry
        gameWindow.setVisible(false);
        System.out.println("Konie gry");

        switch (selectedGame)
        {
            case "Ship":
            {
                System.out.println("Ship game result: " + shipGame.gameResult());
                try
                {
                    network.gameResult(login, selectedGame, shipGame.gameResult());
                }
                catch (Exception e)
                {
                    System.err.println(e.getMessage());
                }
                break;
            }
            case "ttt":
            {
                System.out.println("Ship game result: " + ttt.gameResult());
                try
                {
                    network.gameResult(login, selectedGame, ttt.gameResult());
                }
                catch (Exception e)
                {
                    System.err.println(e.getMessage());
                }
                break;
            }
            case "RSP":
            {
                System.out.println("rps game result: " + rsp.gameResult());
                try
                {
                    network.gameResult(login, selectedGame, rsp.gameResult());
                }
                catch (Exception e)
                {
                    System.err.println(e.getMessage());
                }
                break;
            }
        }
    }
}

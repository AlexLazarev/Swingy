package com.view;

import com.GameManager;
import com.controller.Command;
import com.util.Scene;
import com.util.State;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alazarev on 8/1/18.
 */
public class GUI extends javax.swing.JFrame implements WindowManager{
    private Dimension   dimensionFrame;
    private Dimension   dimensionRadioButton;
    private Dimension   dimensionButton;
    private Dimension   dimensionLabel;
    private Dimension   dimensionTextField;
    private Command     command;
    private JFrame      frame;
    private JPanel      panel;
    private JButton     back;
    private JButton     sw;
    private Scene       current;

    public GUI() {
        command                 = new Command();
        dimensionFrame          = new Dimension(700, 700);
        dimensionButton         = new Dimension(100, 50);
        dimensionRadioButton    = new Dimension(150, 50);
        dimensionLabel          = new Dimension(150, 30);
        dimensionTextField      = new Dimension(150, 30);
        frame                   = getFrame();
        panel                   = new JPanel();
        panel.setLayout(null);
        back                    = getButton("Back", 560, 600);
        sw                      = getButton("Switch", 300, 600);
        sw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameManager.getGame().setGUI(false);
                GameManager.getGame().setScene(current);
                panel.removeAll();
                panel.repaint();
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameManager.getGame().setScene(Scene.Menu);
                GameManager.getGame().setHero(null);
            }
        });
        frame.setVisible(false);
        frame.add(panel);
    }

    public void setVisible(boolean flag){
        frame.setVisible(flag);
    }

    private JFrame  getFrame(){
        JFrame jFrame = new JFrame();
        Toolkit tool = Toolkit.getDefaultToolkit();
        Dimension dimensionWindow = tool.getScreenSize();
        jFrame.setVisible(true);
        jFrame.setBounds(dimensionWindow.width / 2 - 150, dimensionWindow.height / 2 - 250, dimensionFrame.width, dimensionFrame.height);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        return jFrame;
    }


    private JButton getButton(String title, int x, int y){
        JButton button = new JButton(title);
        button.setBounds(x, y, dimensionButton.width, dimensionButton.height);
        return button;
    }

    private JRadioButton getRadioButton(String title, int x, int y){
        JRadioButton button = new JRadioButton(title);
        button.setBounds(x , y, dimensionRadioButton.width, dimensionRadioButton.height);
        return button;
    }

    private JLabel getLabel(String title, int x, int y){
        JLabel label = new JLabel(title);
        label.setBounds(x, y, dimensionLabel.width, dimensionLabel.height);
        return label;
    }


    private JTextField getTextField(String title, int x, int y){
        JTextField label = new JTextField(title);
        label.setBounds(x, y, dimensionTextField.width, dimensionTextField.height);
        return label;
    }

    @Override
    public void menu() {
        panel.removeAll();
        current = Scene.Menu;
        final JButton createButton = getButton("Create hero", 300, dimensionFrame.height / 3);
        final JButton selectButton = getButton("Select hero", 300, dimensionFrame.height / 2);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                command.setInput(1);
                command.menu();
            }
        });
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                command.setInput(2);
                command.menu();
            }
        });
        panel.add(createButton);
        panel.add(selectButton);
        panel.add(sw);
        GameManager.getGame().setScene(Scene.Wait);
        panel.repaint();
    }

    @Override
    public void createHero() {
        panel.removeAll();
        current = Scene.CreateHero;
        JButton button = getButton("Accept", 26, 195);
        JLabel label = getLabel("Choose your name", 26, 145);
        final JTextField textField = getTextField("", 25, 165);
        JLabel label2 = getLabel("Choose your hero", 25, 2);
        final JRadioButton radioButton1 = getRadioButton("Elf Assassin", 25, 15);
        final JRadioButton radioButton2 = getRadioButton("Ork Barbarian", 25, 45);
        final JRadioButton radioButton3 = getRadioButton("Dwarf Warrior", 25, 75);
        final JRadioButton radioButton4 = getRadioButton("MageDimon", 25, 105);
        final ButtonGroup buttonGroup = new ButtonGroup();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (GameManager.getGame().getHero() == null) {
                    if (radioButton1.isSelected())
                        command.setInput(1);
                    else if (radioButton2.isSelected())
                        command.setInput(2);
                    else if (radioButton3.isSelected())
                        command.setInput(3);
                    else if (radioButton4.isSelected())
                        command.setInput(4);
                    command.createHero();
                }
                command.setNext(textField.getText());
                command.setHeroName();
            }
        });
        radioButton1.setSelected(true);
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);
        buttonGroup.add(radioButton4);
        panel.setLayout(null);
        panel.add(radioButton1);
        panel.add(radioButton2);
        panel.add(radioButton3);
        panel.add(radioButton4);
        panel.add(label);
        panel.add(label2);
        panel.add(textField);
        panel.add(button);
        panel.add(back);
        panel.add(sw);
        panel.repaint();
        GameManager.getGame().setScene(Scene.Wait);
    }

    @Override
    public void selectHero() {
        panel.removeAll();
        current = Scene.SelectHero;
        Object[] column = {"id", "type", "name", "lvl"};
        final JTable table = new JTable(command.getObjectsOfHeroes(), column);
        JButton sel = getButton("Select", 350, 50);
        JButton del = getButton("Delete", 500, 50);
        sel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() != -1) {
                    command.setInput(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));
                    command.selectHero();
                }
            }
        });

        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() != -1) {
                    command.setInput(-Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));
                    command.selectHero();
                    GameManager.getGame().setScene(Scene.SelectHero);
                }
            }
        });
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, dimensionFrame.width / 3, 700);
        panel.add(scrollPane);
        panel.add(sel);
        panel.add(del);
        panel.add(back);
        panel.add(sw);
        panel.repaint();
        GameManager.getGame().setScene(Scene.Wait);
    }

    @Override
    public void game() {
        panel.removeAll();
        current = Scene.Game;
        JTextArea area = new JTextArea(GameManager.getGame().getStrMap());
        area.setBounds(50, 50, 300, 300);
        area.setEnabled(false);
        area.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        area.setFont(new Font("monospaced", Font.PLAIN, 12));
        JTextArea info = new JTextArea(GameManager.getGame().getStrInfo());
        info.setBounds(530, 50, 150, 150);
        info.setBackground(null);
        info.setEnabled(false);
        info.setFont(new Font("monospaced", Font.PLAIN, 12));
        JButton buttonNorth = getButton("North", 300, 450);
        JButton buttonWest  = getButton("West", 220, 500);
        JButton buttonEast  = getButton("East", 370, 500);
        JButton buttonSouth = getButton("South", 300, 550);
        JButton save        = getButton("Save", 50, 600);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameAction(5);
            }
        });
        buttonNorth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameAction(1);
            }
        });
        buttonEast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameAction(2);
            }
        });
        buttonWest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameAction(3);
            }
        });
        buttonSouth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameAction(4);
            }
        });
        panel.add(info);
        panel.add(buttonEast);
        panel.add(buttonNorth);
        panel.add(buttonWest);
        panel.add(buttonSouth);
        panel.add(area);
        panel.add(save);
        panel.add(back);
        panel.add(sw);
        panel.repaint();
        GameManager.getGame().setScene(Scene.Wait);
    }

    @Override
    public void gameOver() {
        panel.removeAll();
        current = Scene.GameOver;
        JLabel label = new JLabel(command.gameOver());
        label.setBounds(250, 110, 350, 350);
        label.setFont(new Font("monospaced", Font.PLAIN, 40));
        panel.add(label);
        panel.add(back);
        panel.add(sw);
        panel.repaint();
        GameManager.getGame().setScene(Scene.Wait);
    }

    private void gameAction(int action) {
        command.setInput(action);
        command.game();
        if (command.getState() == State.ProposalFight)
            proposalFight();
        if (command.getState() == State.ProposalItem)
            proposalItem();
        if (command.getState() == State.Success)
            command.move();
        if (GameManager.getGame().getScene() != Scene.GameOver)
            GameManager.getGame().setScene(Scene.Game);
    }

    private void proposalItem(){
        int res = JOptionPane.showConfirmDialog(
                        frame,
                "Do you want to take [" + GameManager.getGame().getItemInfo() + "]",
                "Proposal item", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION)
            command.setInput(1);
        else
            command.setInput(2);
        command.proposalItem();
    }

    private void proposalFight(){
        int res = JOptionPane.showConfirmDialog(frame,
                "Do you want to fight with this spider??(run, Forest, run)",
                "Proposal fight", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION)
            command.setInput(1);
        else
            command.setInput(2);
        command.proposalFight();
    }
}

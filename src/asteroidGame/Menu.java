package asteroidGame;

/*import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.CardLayout; 
import java.awt.Color;



public class Screen extends JFrame implements ActionListener {
private static final long serialVersionUID = 1L;

int width, height;

JButton play = new JButton("play");
JButton settings = new JButton("settings");
JButton exit = new JButton("exit");
JButton mainMenu = new JButton("main menu");

CardLayout layout = new CardLayout();

JPanel panel = new JPanel();
JPanel game = new JPanel();
JPanel menu = new JPanel(); 

public Screen(int width, int height) {
    this.width = width;
    this.height = height;

    panel.setLayout(layout);        
    addButtons();

    setSize(width, height);
    setResizable(false);
    setLocationRelativeTo(null);
    setVisible(true);
    setTitle("Asteroids: Chromatic Conflict");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    requestFocus();

}

private void addButtons() {

    play.addActionListener(this);
    settings.addActionListener(this);
    exit.addActionListener(this);
    mainMenu.addActionListener(this);

    //menu buttons
    menu.add(play);
    menu.add(settings);
    menu.add(exit);

    //game buttons
    game.add(mainMenu);

    //background colors
    game.setBackground(Color.MAGENTA);
    menu.setBackground(Color.GREEN);

    //adding children to parent Panel
    panel.add(menu,"Menu");
    panel.add(game,"Game");

    add(panel);
    layout.show(panel,"Menu");

}

public void actionPerformed(ActionEvent event) {

    Object source = event.getSource();

    if (source == exit) {
        System.exit(0);
    } else if (source == play) {
        layout.show(panel, "Game");
    } else if (source == settings){

    } else if (source == mainMenu){
        layout.show(panel, "Menu");
    }

    }
    public static void main(String[]args)
    {
    	Screen screen= new Screen(600,600);
    }
}
*/

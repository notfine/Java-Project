package src;

import javax.swing.*;

public class BrickBreakerMain {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        Game game = new Game();
        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("BrickBreaker");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(game);
    }
}

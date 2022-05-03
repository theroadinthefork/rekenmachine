import java.awt.*;
import javax.swing.*;

public class RekenMachine {

    static JTextArea displayField;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = frame.getContentPane();
        container.setLayout(null);

        displayField = new JTextArea();
        displayField.setBounds(10, 20, 180, 180);
        container.add(displayField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        Font font = new Font("Courier", Font.PLAIN, 9);
        ExternalActionListener listener = new ExternalActionListener();

        JButton one = new JButton("1");
        one.setFont(font);
        one.addActionListener(listener);
        buttonPanel.add(one);

        JButton two = new JButton("2");
        two.setFont(font);
        two.addActionListener(listener);
        buttonPanel.add(two);

        JButton three = new JButton("3");
        three.setFont(font);
        three.addActionListener(listener);
        buttonPanel.add(three);

        buttonPanel.setBounds(10, 220, 180, 200);
        container.add(buttonPanel);

        frame.setSize(220, 500);
        frame.setVisible(true);
    }

}
  
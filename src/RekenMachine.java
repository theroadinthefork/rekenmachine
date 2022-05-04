import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.List;

public class RekenMachine extends RekenmachineServiceImpl {
    static Font font = new Font("Courier", Font.PLAIN, 9);


    public static void main(String[] args) {


        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = frame.getContentPane();
        container.setLayout(null);

        int width = 500;
        RekenmachineServiceImpl rekenmachine = new RekenmachineServiceImpl();

        rekenmachine.displayField = new JTextArea();
        rekenmachine.displayField.setBounds(10, 20, width-40, 180);
        container.add(rekenmachine.displayField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 3));
        createButtons(rekenmachine, buttonPanel, "7,8,9,4,5,6,1,2,3,.,0,C");
        buttonPanel.setBounds(10, 220, (int) ((width-40)*.5), 200);
        container.add(buttonPanel);

        JPanel operators = new JPanel();
        operators.setLayout(new GridLayout(4, 1));
        createButtons(rekenmachine, operators, "<-,+,-,x,/,=");
        operators.setBounds(250, 220, 160, 200);
        container.add(operators);

        frame.setSize(width, 500);
        frame.setVisible(true);
    }


    static void createButtons(RekenmachineServiceImpl r, JPanel buttonPanel, String text) {
        List<String> buttonTexts = Arrays.asList(text.split(","));
        for (String b : buttonTexts) {
            JButton digit = new JButton(b);
            digit.setFont(font);
            digit.addActionListener(e -> r.showInput(b));
            buttonPanel.add(digit);
        }
    }
}
  
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.List;

public class RekenMachine {
    static Font font = new Font("Courier", Font.PLAIN, 9);
    static JTextArea displayField;
    static StringBuilder input = new StringBuilder();
    static String currentNumb;
    static String numbAfterOperator;
    static String operation;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = frame.getContentPane();
        container.setLayout(null);

        int width = 500;

        displayField = new JTextArea();
        displayField.setBounds(10, 20, width-40, 180);
        container.add(displayField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 3));
        createButtons(buttonPanel, "7,8,9,4,5,6,1,2,3,.,0,Clear");
        buttonPanel.setBounds(10, 220, (int) ((width-40)*.5), 200);
        container.add(buttonPanel);

        JPanel operators = new JPanel();
        operators.setLayout(new GridLayout(4, 1));
        createButtons(operators, "+,-,*,/,=");
        operators.setBounds(250, 220, 100, 200);
        container.add(operators);

        frame.setSize(width, 500);
        frame.setVisible(true);
    }


    static void createButtons(JPanel buttonPanel, String text) {
        List<String> buttonTexts = Arrays.asList(text.split(","));
        for (String b : buttonTexts) {
            JButton digit = new JButton(b);
            digit.setFont(font);
            digit.addActionListener(e -> showInput(b));
            buttonPanel.add(digit);
        }
    }

    static void showInput(String button) {
        switch (button) {
            case "1","2","3","4","5","6","7","8","9","0":
                input.append(button);
                break;
            case ".":
                if (input.indexOf(".")>-1){
                    input.append(button);
                    break;
                }
            case "+","-","*","/":
                currentNumb = input.toString();
                input.append(button);
                operation = button;
                break;
            case "Clear":
                input.setLength(0);
                input.append("0");
                operation = "";
                break;
            case "=":
                numbAfterOperator = input.toString().replace(currentNumb+operation,"");
                currentNumb = resolve();
                input.setLength(0);
                input.append(currentNumb);
                break;
        }
        displayField.setText(input.toString());
    }

    static String resolve() {
        double output;
        double c = Double.parseDouble(currentNumb);
        double n = Double.parseDouble(numbAfterOperator);
        switch (operation) {
            case "+":
                output = c +n;
                currentNumb = String.valueOf(output);
                return String.valueOf(output);
            case "-":
                output = c -n;
                currentNumb = String.valueOf(output);
                return String.valueOf(output);
            case "*":
                output = c *n;
                currentNumb = String.valueOf(output);
                return String.valueOf(output);
            case "/":
                output = c /n;
                currentNumb = String.valueOf(output);
                return String.valueOf(output);
        }
        return "err";
    }
}
  
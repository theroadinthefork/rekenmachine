import javax.swing.*;
import java.util.*;

public class RekenmachineServiceImpl implements RekenmachineService {
    JTextArea displayField;
    StringBuilder input = new StringBuilder();
    String currentNumb;
    String numbAfterOperator;
    String operation;

    public void showInput(String button) {
        switch (button) {
            case "1","2","3","4","5","6","7","8","9","0":
                input.append(button);
                break;
            case "+","-","x","/":
                if (!Character.toString(input.charAt(input.length()-1)).matches("[x\\/+-]")){ // checks if last char in string isn't already an operator
                    input.append(button);
                };
                break;
            case "<-":
                input.deleteCharAt(input.length()-1);
                break;
            case ".":
                if (input.indexOf(".") == -1) input.append(button);
                break;
            case "C":
                input.setLength(0);
                operation = "";
                break;
            case "=":
                List<String> numbers = new ArrayList<>(Arrays.asList(input.toString()
                        .split("[x\\/+-]+")));
                List<String> operations = new ArrayList<>(Arrays.asList(input.toString()
                        .split("[\\d\\.]+")));
                operations.remove(0); // remove empty field

                String[] operationOrder = {"x","/","+","-"}; // set order of operations

                for (String operation : operationOrder) {
                    if (operations.contains(operation)) {
                        int operatorIndex = operations.indexOf(operation); // location of operation in array
                        currentNumb = numbers.get(operatorIndex); // Number before operation
                        numbAfterOperator = numbers.get(operatorIndex+1); // Number after operation
                        String output = resolve(currentNumb, numbAfterOperator, operation); // Solve
                        numbers.set(operatorIndex, output); // replace number with resolved number
                        numbers.remove(operatorIndex+1);
                        operations.remove(operatorIndex);
                    }
                }
                input.setLength(0);
                input.append(numbers.get(0));
                break;
        }
        displayField.setText(input.toString());
    }

    public String resolve(String current, String after, String operation) {
        double output;
        double c = Double.parseDouble(currentNumb);
        double n = Double.parseDouble(numbAfterOperator);
        switch (operation) {
            case "+":
                output = c + n;
                return String.valueOf(output);
            case "-":
                output = c - n;
                return String.valueOf(output);
            case "x":
                output = c * n;
                return String.valueOf(output);
            case "/":
                output = c / n;
                return String.valueOf(output);
        }
        return "err";
    }
}

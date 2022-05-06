import javax.swing.*;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.regex.Pattern;

public class RekenmachineServiceImpl implements RekenmachineService {
    JTextArea displayField;
    StringBuilder input = new StringBuilder("0");

    public void showInput(String button) {
        switch (button) {
            case "1","2","3","4","5","6","7","8","9","0":
                // By default calculator shows 0, but if first number is entered, the 0 should be replaced with the digit
                if (input.toString().equals("0")) {
                    input.setLength(0);
                }
                input.append(button);
                break;
            case "+","-","x","/":
                // replace operator if last character is already an operator
                if (input.substring(input.length() - 1).matches("[x\\/+-]")) {
                    input.deleteCharAt(input.length()-1);
                    input.append(button);
                } else input.append(button);
                break;
            case "<-":
                input.deleteCharAt(input.length()-1);
                break;
            case ".":
                // only add decimal if last number doesn't have one yet
                if (Pattern.compile("([-\\/x+]$|[-\\/x+][^.]+$|^[\\d-]+$)")
                        .matcher(input.toString())
                        .find()) {
                    input.append(button);
                }
                break;
            case "C":
                input = new StringBuilder("0");
                break;
            case "=":
                // Remove last character if it's an operator
                if (input.substring(input.length() - 1).matches("[x\\/+-]")) {
                    input.deleteCharAt(input.length()-1);
                }
                String outcome = solveAll(input);
                input.setLength(0); // clears stringbuilder
                input.append(outcome); // puts the remaining number (i.e. outcome) in stringbuilder
                break;
        }
        displayField.setText(input.toString());
    }

    public String solveAll(StringBuilder input) {
        List<String> numbers = new ArrayList<>(Arrays.asList(input
                .toString()
                .split("[+x\\/]|(?<=\\d)[-]"))); // filters out operations except for the minus for negative numbers
        List<String> operations = new ArrayList<>(Arrays.asList(input
                .toString()
                .split("(?<!\\d)-[\\d\\.]+|(?<!\\\\d-)[\\d\\.]+"))); // filters out numbers including negative numbers
        operations.remove(0); // remove empty element
        System.out.println("numbers: "+numbers);
        System.out.println("operations: "+operations);

        // Iterate over total number of operations, can't for(String operation:operations) as operations gets updated after every iteration
        int iterations = operations.size();
        for (int i=0; i < iterations; i++){
            // create list of indexes of where multiplications and divisions are
            List<Integer> indexesOfMultiplicationDivision = new ArrayList<>();
            for (String operation : operations) {
                if (operation.equals("x")) {
                    indexesOfMultiplicationDivision.add(operations.indexOf(operation));
                }
                if (operation.equals("/")) {
                    indexesOfMultiplicationDivision.add(operations.indexOf(operation));
                }
            }

            // let list start with either multiplication or division that comes first, i.e. smallest index first
            Collections.sort(indexesOfMultiplicationDivision);

            // if there are multiplications or divisions, solve the first one in the list
            if (indexesOfMultiplicationDivision.size() > 0) {
                basicOperation(numbers, operations, indexesOfMultiplicationDivision.get(0));
                continue; // start next iteration, so that all multiplications/divisions are done first
            }

            // do additions/subtractions
            try {
                basicOperation(numbers, operations, 0);
            } catch (IndexOutOfBoundsException e ){
                return "error";
            }
        }
        return numbers.get(0);
    }

    private void basicOperation(List<String> numbers, List<String> operations, int operatorIndex) {

        String numbBeforeOperator = numbers.get(operatorIndex);
        String numbAfterOperator = numbers.get(operatorIndex+1);

        BinaryOperator<Double> output = switch (operations.get(operatorIndex)) {
            case "+" -> (a, b) -> a + b;
            case "-" -> (a, b) -> a - b;
            case "x" -> (a, b) -> a * b;
            case "/" -> (a, b) -> a / b;
            default -> null;
        };

        String result = "";
        try {
            result = String.valueOf(output.apply(Double.parseDouble(numbBeforeOperator), Double.parseDouble(numbAfterOperator)));
        } catch (NumberFormatException e) {
            operations.clear();
            input.setLength(0);
            result = "error";
            return;
        }

        // Result becomes "-Infinity" if you divide negative number by zero somehow
        if (result.equals("-Infinity")) {
            operations.clear();
            numbers.clear();
            input.setLength(0);
            numbers.add("error");
            return;
        }

        numbers.set(operatorIndex, result); // replace number before operator with result
        numbers.remove(operatorIndex +1); // remove number after operator
        operations.remove(operatorIndex); // remove used operator
        System.out.println(numbers);
        System.out.println(operations);
    }

}

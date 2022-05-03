import java.awt.event.*;

public class ExternalActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        String actionCommand = event.getActionCommand();

        switch (actionCommand) {
            case "1":
                RekenMachine.displayField.setText("1");
                break;
            case "2":
                break;
            case "3":
                break;

        }
    }

}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {


    private JTextField display;
    private JButton[] numberButtons;
    private JButton[] operationButtons;
    private JButton equalsButton, backspaceButton;
    private JPanel panel;

    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;

    public Calculator() {
        setTitle("简单计算器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));

        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
        }

        operationButtons = new JButton[4];
        operationButtons[0] = new JButton("+");
        operationButtons[1] = new JButton("-");
        operationButtons[2] = new JButton("*");
        operationButtons[3] = new JButton("/");
        for (JButton button : operationButtons) {
            button.addActionListener(this);
        }

        equalsButton = new JButton("=");
        equalsButton.addActionListener(this);

        backspaceButton = new JButton("<-");
        backspaceButton.addActionListener(this);

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(operationButtons[0]);

        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(operationButtons[1]);

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(operationButtons[2]);

        panel.add(numberButtons[0]);
        panel.add(new JButton("."));
        panel.add(equalsButton);
        panel.add(operationButtons[3]);

        panel.add(backspaceButton);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ((command.charAt(0) >= '0' && command.charAt(0) <= '9') || command.equals(".")) {
            currentInput += command;
            display.setText(currentInput);
        } else if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/")) {
            if (!currentInput.isEmpty()) {
                firstOperand = Double.parseDouble(currentInput);
                operator = command;
                display.setText(firstOperand + " " + operator);
                currentInput = "";
            }
        } else if (command.equals("=")) {
            if (!currentInput.isEmpty() && !operator.isEmpty()) {
                double secondOperand = Double.parseDouble(currentInput);
                double result = 0;
                switch (operator) {
                    case "+":
                        result = firstOperand + secondOperand;
                        break;
                    case "-":
                        result = firstOperand - secondOperand;
                        break;
                    case "*":
                        result = firstOperand * secondOperand;
                        break;
                    case "/":
                        if (secondOperand != 0) {
                            result = firstOperand / secondOperand;
                        } else {
                            display.setText("错误：除以零");
                            return;
                        }
                        break;
                }
                display.setText(String.valueOf(result));
                currentInput = String.valueOf(result);
                operator = "";
                firstOperand = result;
            }
        } else if (command.equals("<-")) {
            if (!currentInput.isEmpty()) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
                display.setText(currentInput);
            } else if (!operator.isEmpty()) {
                operator = "";
                display.setText(String.valueOf(firstOperand));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}
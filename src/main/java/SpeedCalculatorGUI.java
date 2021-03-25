import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class SpeedCalculatorGUI extends JFrame
{
    private static final String appName = "E7 speed calculator";

    private JPanel mainPanel;
    private JTextField mySpeedField;
    private JLabel mySpeedFieldLabel;
    private JTextField enemyCRField;
    private JTextArea resultBox;
    private JLabel enemyCRLabel;
    private JButton calculateButton;
    private JRadioButton outSpedField;

    public SpeedCalculatorGUI(String appName)
    {
        super(appName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                runCalculator();
            }
        });
        this.setup();
    }

    public void setup()
    {
        setupText();
        setupToolTips();
    }

    private void setupText()
    {
        mySpeedFieldLabel.setText("Your speed");
        enemyCRLabel.setText("Enemy CR%");
        outSpedField.setText("Outsped?");
        calculateButton.setText("Calculate speed");
        resultBox.setText("");
    }

    private void setupToolTips()
    {
        mySpeedField.setToolTipText("The speed of your unit you are calculating with");
        enemyCRField.setToolTipText("Enemy combat readiness");
    }

    private double crDec(int cr, boolean lapped)
    {
        if(lapped)
            cr+=100;
        return cr/100.0;
    }

    private int calcHi(int speed, int cr, boolean lapped)
    {
        return (int) Math.ceil(speed*crDec(cr, lapped));
    }

    private int calcLo(int speed, int cr, boolean lapped)
    {
        return  (int) Math.ceil(((speed * 1.05) * crDec(cr, lapped)));
    }

    private void runCalculator()
    {
        int mySpeed, enemyCR;
        boolean outsped;
        resultBox.setText("");
        try
        {
            mySpeed = Integer.parseInt(mySpeedField.getText());
        }
        catch(NumberFormatException e)
        {
            resultBox.setText("Error parsing my speed");
            return;
        }
        try
        {
            enemyCR = Integer.parseInt(enemyCRField.getText());
        }
        catch(NumberFormatException e)
        {
            resultBox.setText("Error parsing enemy CR");
            return;
        }
        outsped = outSpedField.isSelected();
        resultBox.setText("With your speed of " + mySpeed + " and the enemy CR at " + enemyCR +
                "% while" + (outsped?" ":" not ") + "being outsped\nThey should be " +
                calcHi(mySpeed , enemyCR, outsped) + " - " + calcLo(mySpeed , enemyCR, outsped));
    }

    public static void main(String[] args)
    {
        JFrame frame = new SpeedCalculatorGUI(appName);
        frame.setVisible(true);
    }
}

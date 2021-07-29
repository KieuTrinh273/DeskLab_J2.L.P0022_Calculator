/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labcalculate;

import java.math.BigDecimal;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author DELL
 */
public class Calculator {

    /**
     * @param args the command line arguments
     */
    private BigDecimal num1, num2;
    private BigDecimal memory = new BigDecimal("0");
    private JTextField txt;
    private String op = "";
    private boolean isProcessing, isReset;
    private boolean isMR = false;
    private GUI gui;

    public Calculator(JTextField screen, GUI gui) {
        op = "";
        this.txt = screen;
        this.gui = gui;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public BigDecimal getValue() {
        if (isMR) {
            return memory;
        }
        BigDecimal tmp;
        String value = txt.getText();
        tmp = new BigDecimal(value);
        return tmp;
    }

    public void pressNumber(JButton btn) {
        BigDecimal tmp;
        String value = btn.getText();
        if (isProcessing || isReset) {
            txt.setText("0");
            isProcessing = false;
            isReset = false;
        }
        isMR = false;
        tmp = new BigDecimal(txt.getText() + value);
        txt.setText(tmp + "");
    }

    public void calculate() {
        try {
            if (!isProcessing) {
                if (op.equals("")) {
                    num1 = getValue();
                } else {
                    num2 = getValue();
                    if (op.equals("Add")) {
                        num1 = num1.add(num2);
                    }
                    if (op.equals("Sub")) {
                        num1 = num1.subtract(num2);
                    }
                    if (op.equals("Mul")) {
                        num1 = num1.multiply(num2);
                    }
                    if (op.equals("Div")) {
                        double result = num1.doubleValue() / num2.doubleValue();//divide second number to first
                        num1 = new BigDecimal(result + "");
                    }
                }
                txt.setText(num1 + "");
                isProcessing = true;
            }
        } catch (Exception e) {
            isReset = true;
            txt.setText("ERROR");
        }
    }

    public void pressEqual() {
        if (!txt.getText().equals("ERROR")) {
            calculate();
            if (!txt.getText().equals("ERROR")) {
                String display = Math.round(num1.doubleValue()*100)/100.0 + "";
                if (display.endsWith(".0")) {
                    display = display.replace(".0", "");
                }
                txt.setText(display);
            }
            op = "";
        } else {
            txt.setText(num1 + "");
        }
    }

    public void pressDot() {
        if (isProcessing || isReset) {
            txt.setText("0");
            isProcessing = false;
            isReset = false;
        }
        if (!txt.getText().contains(".")) {
            txt.setText(txt.getText() + ".");
        }
    }

    public void pressNeg() {
        txt.setText(new BigDecimal(txt.getText()).negate() + "");
        isProcessing = false;
        isReset = true;
    }

    public void pressSqrt() {
        BigDecimal value = new BigDecimal(txt.getText());
        if (value.doubleValue() >= 0) {
            String display = Math.sqrt(value.doubleValue()) + "";
            if (display.endsWith(".0")) {
                display = display.replace(".0", "");
            }
            txt.setText(display);
            isProcessing = false;
        } else {
            txt.setText("ERROR");
        }
        isReset = false;
    }

    public void pressPercent() {
        BigDecimal value = new BigDecimal(txt.getText());
        String display = value.doubleValue() / 100 + "";
        if (display.endsWith(".0")) {
            display = display.replace(".0", "");
        }
        txt.setText(display);
        isProcessing = false;
        isReset = true;
    }

    public void pressInverse() {
        BigDecimal value = new BigDecimal(txt.getText());
        try {
            if (value.doubleValue() != 0) {
                String display = 1 / value.doubleValue() + "";
                if (display.endsWith(".0")) {
                    display = display.replace(".0", "");
                }
                txt.setText(display);
            } else {
                txt.setText("ERROR");
            }
            isProcessing = false;
        } catch (Exception e) {
            txt.setText("ERROR");
        }
        isReset = true;
    }

    public void pressMAdd() {
        memory = memory.add(getValue());
        isProcessing = false;
        isReset = true;
        gui.btnMC.setEnabled(true);
        gui.btnMR.setEnabled(true);
    }

    public void pressMSub() {
        memory = memory.subtract(getValue());
        isProcessing = false;
        isReset = true;
        gui.btnMC.setEnabled(true);
        gui.btnMR.setEnabled(true);
    }

    public void pressMC() {
        memory = new BigDecimal("0");
        gui.btnMC.setEnabled(false);
        gui.btnMR.setEnabled(false);
    }

    public void pressMR() {
        txt.setText(memory + "");
        isMR = true;
    }

    public void pressAllClear() {
        num1 = new BigDecimal("0");
        num2 = new BigDecimal("0");
        memory = new BigDecimal("0");
        op = "";
        txt.setText("0");
        gui.btnMC.setEnabled(false);
        gui.btnMR.setEnabled(false);
    }

}

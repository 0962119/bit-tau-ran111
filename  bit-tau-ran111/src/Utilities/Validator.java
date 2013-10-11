/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author DoHongPhuc
 */
public class Validator {

    public static boolean isInt(JTextField textField) {
        try {
            int value = Integer.valueOf(textField.getText()).intValue();
            return true;//is integer value
        } catch (Exception e) {
            return false;//not integer value
        }
    }

    public static boolean isNotNull(JComponent jcom) {
        try {
            JTextField jTextField = (JTextField) jcom;
            if (jTextField.getText().isEmpty()) {
                return false;
            }
        } catch (Exception e) {
        }
        try {
            JTextArea jTextArea = (JTextArea) jcom;
            if (jTextArea.getText().isEmpty()) {
                return false;
            }
        } catch (Exception e) {
        }
        return true;
    }

    public static boolean isDouble(JTextField textField) {
        try {
            double value = Double.valueOf(textField.getText()).doubleValue();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

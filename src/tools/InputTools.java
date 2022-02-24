package tools;

import javax.swing.*;

public class InputTools {

    private InputTools() {}

    public static int[] judgeStringToInt(String value, String message) {
        int flag;
        int[] values = new int[2];
        try {
            values[0] = Integer.parseInt(value);
            flag = 1;
            values[1] = flag;
            return values;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(),message);
        }
        values[1] = -1;
        return values;
    }

    public static boolean judgeStringFormat(String text) {
        return text.charAt(4) != '-' || text.length() != 7;
    }
}

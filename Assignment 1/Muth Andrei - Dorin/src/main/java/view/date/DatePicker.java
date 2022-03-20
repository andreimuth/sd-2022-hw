package view.date;

//https://examples.javacodegeeks.com/desktop-java/swing/java-swing-date-picker-example/

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class DatePicker {

    private int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    private int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    private final JLabel l = new JLabel("", JLabel.CENTER);
    private String day = "";
    private final JDialog d;
    private final JButton[] button = new JButton[49];
    private JLabel monthLabel;

    public DatePicker() {
        d = new JDialog();
        d.setModal(true);
        String[] header = {"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};
        JPanel p1 = new JPanel(new GridLayout(7, 7));
        p1.setPreferredSize(new Dimension(430, 120));

        int x = 0;
        for (x = 0; x < button.length; x++) {
            int finalX = x;
            button[x] = new JButton();
            button[x].addActionListener(ae -> {
                day = button[finalX].getActionCommand();
                d.dispose();
            });
            if (x < 7) {
                button[x].setText(header[x]);
                button[x].setForeground(Color.red);
            }
            p1.add(button[x]);
        }

        JPanel p2 = new JPanel(new GridLayout(1, 3));
        JButton previous = new JButton("Previous");
        previous.addActionListener(ae -> {
            month--;
            if(month == -1) {
                month = 11;
                year--;
            }
            displayDate();
        });
        JButton next = new JButton("Next");
        next.addActionListener(ae -> {
            month++;
            if(month == 12) {
                month = 10;
                year++;
            }
            displayDate();
        });
        p2.add(previous);
        monthLabel = new JLabel(stringMonth(month) + " " + year, JLabel.CENTER);
        p2.add(monthLabel);
        p2.add(next);
        d.add(p1, BorderLayout.CENTER);
        d.add(p2, BorderLayout.SOUTH);
        d.pack();
        d.setLocationRelativeTo(null);
        displayDate();
        d.setVisible(true);
    }

    public String stringMonth(int month) {
        return switch (month) {
            case 0 -> "JANUARY";
            case 1 -> "FEBRUARY";
            case 2 -> "MARCH";
            case 3 -> "APRIL";
            case 4 -> "MAY";
            case 5 -> "JUNE";
            case 6 -> "JULY";
            case 7 -> "AUGUST";
            case 8 -> "SEPTEMBER";
            case 9 -> "OCTOBER";
            case 10 -> "NOVEMBER";
            case 11 -> "DECEMBER";
            default -> "";
        };
    }

    public void displayDate() {
        for (int x = 7; x < button.length; x++)
            button[x].setText("");
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "MMMM yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, 1);
        int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++)
            button[x].setText("" + day);
        l.setText(sdf.format(cal.getTime()));
        d.setTitle("Date Picker");
        monthLabel.setText(stringMonth(month) + " " + year);
    }

    public Date setPickedDate() {
        if (day.equals(""))
            return null;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "dd-MM-yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, Integer.parseInt(day));
        return cal.getTime();
    }

}

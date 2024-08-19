import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGame {
    private JFrame frame;
    private JLabel titleLabel;
    private JLabel label;
    private JTextField textField;
    private JButton submitButton;
    private int targetNumber;
    private int attempts;
    private int maxAttempts;
    private int score;

    public NumberGame() {
        frame = new JFrame("Number Guessing Game");
        frame.getContentPane().setBackground(new Color(0,0,255));
        titleLabel = new JLabel("Welcome to the Number Guessing Game!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); 
        titleLabel.setForeground(new Color(0,0,128)); 

        label = new JLabel("Guess the number between 1 to 100:");
        label.setFont(new Font("Arial", Font.PLAIN, 14));

        textField = new JTextField(10);
        submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(0,0,128));
        submitButton.setForeground(Color.WHITE); 
        submitButton.setFocusPainted(false); 

        targetNumber = generateRandomNumber();
        maxAttempts = 5;
        attempts = 0;
        score = 0;

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.setBackground(new Color(240, 240, 240));
        panel.add(titleLabel);
        panel.add(label);
        panel.add(textField);
        panel.add(submitButton);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(100) + 1;
    }

    private void checkGuess() {
        int userGuess;
        try {
            userGuess = Integer.parseInt(textField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
            return;
        }
        attempts++;

        if (userGuess == targetNumber) {
            JOptionPane.showMessageDialog(frame, "Great ! You guessed the correct number.");
            score++;
        } else if (attempts < maxAttempts) {
            String message = (userGuess < targetNumber) ? "Too low ! Try a higher number." : "Too high ! Try a lower number.";
            JOptionPane.showMessageDialog(frame, message);
        } else {
            JOptionPane.showMessageDialog(frame, "Sorry! You've used all your attempts. The correct number was " + targetNumber);
        }

        if (attempts < maxAttempts) {
            targetNumber = generateRandomNumber();
            textField.setText("");
        } else {
            showScore();
        }
    }

    private void showScore() {
        JOptionPane.showMessageDialog(frame, "Your Final score is: " + score);
        int option = JOptionPane.showConfirmDialog(frame, "Do you want to play again? (Yes/No)", "Play Again", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            frame.dispose();
        }
    }

    private void resetGame() {
        targetNumber = generateRandomNumber();
        attempts = 0;
        score = 0;
        textField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NumberGame();
            }
        });
    }
}
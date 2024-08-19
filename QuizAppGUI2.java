import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class QuizAppGUI2 {
    static class Question {
        String question;
        List<String> options;
        String answer;

        Question(String question, List<String> options, String answer) {
            this.question = question;
            this.options = options;
            this.answer = answer;
        }
    }

    private static final List<Question> QUESTIONS = Arrays.asList(
            new Question("What is the capital of France?", Arrays.asList("Paris", "London", "Berlin", "Madrid"), "Paris"),
            new Question("What is 2+2?", Arrays.asList("3", "4", "5", "6"), "4")
    );

    private static final int TIME_LIMIT_SECONDS = 50;

    private int remainingTime = TIME_LIMIT_SECONDS;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizAppGUI2().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Quiz App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Top panel with question and timer
        JPanel topPanel = new JPanel(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);

        JTextArea questionArea = new JTextArea();
        questionArea.setSize(100,50);
        questionArea.setEditable(false);
        questionArea.setBackground(Color.WHITE);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        topPanel.add(new JScrollPane(questionArea), BorderLayout.CENTER);

        JLabel timerLabel = new JLabel("Time left: " + TIME_LIMIT_SECONDS + " seconds");
        timerLabel.setBackground(Color.YELLOW);
        timerLabel.setOpaque(true);
        topPanel.add(timerLabel, BorderLayout.EAST);

        // Middle panel for options
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(0, 1));
        middlePanel.setBackground(Color.CYAN);
        frame.add(middlePanel, BorderLayout.CENTER);

        // Bottom panel with submit button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        frame.add(bottomPanel, BorderLayout.SOUTH);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(Color.GREEN);
        bottomPanel.add(submitButton, BorderLayout.CENTER);

        JLabel scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBackground(Color.YELLOW);
        scoreLabel.setOpaque(true);
        bottomPanel.add(scoreLabel, BorderLayout.WEST);

        Timer countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTime--;
                timerLabel.setText("Time left: " + remainingTime + " seconds");
                if (remainingTime <= 0) {
                    ((Timer) e.getSource()).stop();
                    JOptionPane.showMessageDialog(frame, "Time is up!");
                    submitButton.setEnabled(false);
                }
            }
        });

        int[] score = {0};
        int[] currentQuestionIndex = {0};

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentQuestionIndex[0] < QUESTIONS.size()) {
                    Question question = QUESTIONS.get(currentQuestionIndex[0]);
                    String selectedOption = getSelectedOption(middlePanel);
                    if (selectedOption != null) {
                        if (selectedOption.equals(question.answer)) {
                            score[0]++;
                        }
                        currentQuestionIndex[0]++;
                        if (currentQuestionIndex[0] < QUESTIONS.size()) {
                            displayQuestion(frame, questionArea, middlePanel, QUESTIONS.get(currentQuestionIndex[0]));
                            remainingTime = TIME_LIMIT_SECONDS;
                            countdownTimer.start();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Your final score is " + score[0] + " out of " + QUESTIONS.size() + ".");
                            frame.dispose();
                        }
                    }
                }
            }
        });

        displayQuestion(frame, questionArea, middlePanel, QUESTIONS.get(currentQuestionIndex[0]));
        countdownTimer.start();
        frame.setVisible(true);
    }

    private void displayQuestion(JFrame frame, JTextArea questionArea, JPanel optionsPanel, Question question) {
        questionArea.setText(question.question);
        optionsPanel.removeAll();
        ButtonGroup buttonGroup = new ButtonGroup();

        for (int i = 0; i < question.options.size(); i++) {
            JRadioButton optionButton = new JRadioButton(question.options.get(i));
            buttonGroup.add(optionButton);
            optionsPanel.add(optionButton);
        }
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    private String getSelectedOption(JPanel optionsPanel) {
        for (Component comp : optionsPanel.getComponents()) {
            if (comp instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) comp;
                if (radioButton.isSelected()) {
                    return radioButton.getText();
                }
            }
        }
        return null;
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumGame extends JFrame {
    private int randomNumber;
    private int numberOfTries;
    private JTextField guessField;
    private JLabel messageLabel;
    private JLabel triesLabel;
    private JLabel hintLabel;
    private JButton guessButton;
    private JButton restartButton;
    private JPanel mainPanel;

    public NumGame() {

        Random random = new Random();
        randomNumber = random.nextInt(100) + 1;
        numberOfTries = 0;

        setTitle("NumGame");
        setSize(650, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255, 228, 225));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(200, 50, 200));
        JLabel titleLabel = new JLabel("Welcome to Number Prediction Game!");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(255, 240, 245));

        JLabel instructionsLabel = new JLabel("You have Enter a number between 1 and 100. Try to guess the number.");
        instructionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        instructionsLabel.setFont(new Font("Serif", Font.BOLD, 20));

        guessField = new JTextField(5);
        guessField.setFont(new Font("Serif", Font.PLAIN, 100));
        guessField.setBorder(BorderFactory.createLineBorder(Color.PINK, 2));

        guessButton = new JButton("Guess");
        guessButton.setFont(new Font("Serif", Font.BOLD, 16));
        guessButton.setBackground(Color.YELLOW);
        guessButton.setForeground(Color.BLACK);
        guessButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));

        messageLabel = new JLabel("Enter your guess above and click 'Guess'.");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        messageLabel.setForeground(new Color(255, 69, 0));

        triesLabel = new JLabel("Number of tries: 0");
        triesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        triesLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        triesLabel.setForeground(new Color(34, 139, 34));

        hintLabel = new JLabel("Hint: The number is " + (randomNumber % 2 == 0 ? "even" : "odd") + ".");
        hintLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hintLabel.setFont(new Font("Serif", Font.ITALIC, 16));
        hintLabel.setForeground(new Color(138, 43, 226));

        restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Serif", Font.BOLD, 16));
        restartButton.setBackground(Color.CYAN);
        restartButton.setForeground(Color.BLACK);
        restartButton.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

        guessButton.addActionListener(new GuessButtonListener());
        restartButton.addActionListener(new RestartButtonListener());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(instructionsLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(guessField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(guessButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(messageLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(triesLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(hintLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(restartButton);
        add(mainPanel, BorderLayout.CENTER);
    }

    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String guessText = guessField.getText();
            try {
                int guess = Integer.parseInt(guessText);
                numberOfTries++;
                if (guess < 1 || guess > 100) {
                    messageLabel.setText("Invalid guess. Please enter a number between 1 and 100.");
                } else if (guess < randomNumber) {
                    messageLabel.setText("Too low. Try again.");
                } else if (guess > randomNumber) {
                    messageLabel.setText("Too high. Try again.");
                } else {
                    messageLabel.setText("Congratulations! You guessed the number!");
                    animateVictory();
                }

                triesLabel.setText("Number of tries: " + numberOfTries);
                if (Math.abs(guess - randomNumber) <= 10) {
                    hintLabel.setText("Hint: You are very close!");
                } else if (numberOfTries % 3 == 0) {
                    if (randomNumber <= 50) {
                        hintLabel.setText("Hint: The number is between 1 and 50.");
                    } else {
                        hintLabel.setText("Hint: The number is between 51 and 100.");
                    }
                } else if (numberOfTries % 5 == 0) {
                    hintLabel.setText("Hint: The number is "
                            + (randomNumber % 5 == 0 ? "a multiple of 5." : "not a multiple of 5."));
                } else if (numberOfTries % 7 == 0) {
                    hintLabel.setText(
                            "Hint: The number is " + (randomNumber > 50 ? "greater than 50." : "less than 50."));
                } else {
                    hintLabel.setText("Hint: The number is " + (randomNumber % 2 == 0 ? "even" : "odd") + ".");
                }
            } catch (NumberFormatException ex) {
                messageLabel.setText("Invalid input. Please enter a number between 1 and 100.");
            }
        }
    }

    private class RestartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Random random = new Random();
            randomNumber = random.nextInt(100) + 1;
            numberOfTries = 0;
            guessField.setText("");
            messageLabel.setText("Enter your guess above and click 'Guess'.");
            triesLabel.setText("Number of tries: 0");
            hintLabel.setText("Hint: The number is " + (randomNumber % 2 == 0 ? "even" : "odd") + ".");
        }
    }

    private void animateVictory() {
        Timer timer = new Timer(100, new ActionListener() {
            int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < 10) {
                    if (count % 2 == 0) {
                        mainPanel.setBackground(Color.YELLOW);
                        messageLabel.setForeground(Color.GREEN);
                    } else {
                        mainPanel.setBackground(new Color(255, 240, 245));
                        messageLabel.setForeground(new Color(255, 69, 0));
                    }
                    count++;
                } else {
                    ((Timer) e.getSource()).stop();
                    mainPanel.setBackground(new Color(255, 240, 245));
                    messageLabel.setForeground(new Color(255, 69, 0));
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            NumGame frame = new NumGame();
            frame.setVisible(true);
        });
    }
}

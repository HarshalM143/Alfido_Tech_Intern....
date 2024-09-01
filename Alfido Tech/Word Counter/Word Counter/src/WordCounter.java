import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class WordCounter extends JFrame {

    private JTextArea textArea;
    private JButton addButton;
    private JLabel statsLabel;

    public WordCounter() {
        setTitle("Word Counter");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        JScrollPane scrollPane = new JScrollPane(textArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(new Color(220, 220, 220));

        addButton = new JButton("Add File");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        addButton.addActionListener(new AddFileListener());
        bottomPanel.add(addButton, BorderLayout.WEST);

        statsLabel = new JLabel("Word Count Stats: ");
        statsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statsLabel.setForeground(new Color(60, 60, 60));
        statsLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        bottomPanel.add(statsLabel, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private class AddFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                processFile(selectedFile);
            }
        }
    }

    private void processFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder text = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                text.append(line).append(" ");
            }

            textArea.setText(text.toString());
            calculateStats(text.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void calculateStats(String text) {
        String[] words = text.split("\\W+");
        HashMap<String, Integer> wordCount = new HashMap<>();

        int totalLength = 0;
        for (String word : words) {
            word = word.toLowerCase();
            totalLength += word.length();
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        double averageLength = (double) totalLength / words.length;

        StringBuilder stats = new StringBuilder("<html><h2>Word Frequencies:</h2><ul>");
        wordCount.forEach((k, v) -> stats.append("<li>").append(k).append(": ").append(v).append("</li>"));
        stats.append("</ul><h3>Average Word Length: ").append(String.format("%.2f", averageLength))
                .append("</h3></html>");

        statsLabel.setText(stats.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WordCounter frame = new WordCounter();
            frame.setVisible(true);
        });
    }
}

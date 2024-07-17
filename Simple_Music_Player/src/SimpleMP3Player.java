import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class SimpleMP3Player {
    private final JFrame frame;
    private final JPanel controlPanel;
    private final JButton playButton;
    private final JButton pauseButton;
    private final JButton stopButton;
    private final JButton resumeButton;
    private final JLabel statusLabel;
    private final JLabel fileNameLabel;
    private Player player;
    private FileInputStream fileInputStream;
    private boolean playing;
    private boolean paused;
    private String currentFileName;
    private long pausedAt;
    private File selectedFile;

    public SimpleMP3Player() {
        frame = new JFrame("Music Player -By Anjali");
        frame.setSize(400, 300); // Increased frame height
        Image imageIcon = Toolkit.getDefaultToolkit().getImage("C:/Projects/Simple_Music_Player/images/musical-note.png");
        frame.setIconImage(imageIcon);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load image
        ImageIcon icon = new ImageIcon("C:/Projects/Simple_Music_Player/images/Sound.png");
        Image image = icon.getImage().getScaledInstance(100, 120, Image.SCALE_SMOOTH); // Set size
        ImageIcon scaledIcon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(scaledIcon);

        fileNameLabel = new JLabel("No file selected"); // Initialize file name label
        fileNameLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align file name label

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        stopButton = new JButton("Stop");
        resumeButton = new JButton("Resume");
        statusLabel = new JLabel();

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!playing) {
                    play();
                } else if (paused) {
                    resume();
                }
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pause();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resume();
            }
        });

        controlPanel.add(playButton);
        controlPanel.add(pauseButton);
        controlPanel.add(resumeButton);
        controlPanel.add(stopButton);

        // Add components to frame
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(BorderLayout.NORTH, imageLabel); // Add image label
        frame.getContentPane().add(BorderLayout.CENTER, controlPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, fileNameLabel); // Add file name label
        frame.setVisible(true);
    }

    private void play() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                stop(); // Stop any previous playback
                selectedFile = fileChooser.getSelectedFile();
                fileInputStream = new FileInputStream(selectedFile);
                player = new Player(fileInputStream);
                currentFileName = selectedFile.getName();
                new Thread() {
                    public void run() {
                        playing = true;
                        paused = false;
                        try {
                            player.play();
                        } catch (JavaLayerException e) {
                            e.printStackTrace();
                        } finally {
                            if (player != null) {
                                playing = false;
                                player.close();
                                try {
                                    fileInputStream.close();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                }.start();
                updateUI(currentFileName);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (JavaLayerException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void pause() {
        if (player != null) {
            paused = true;
            pausedAt = player.getPosition();
            player.close();
            statusLabel.setText("Paused");
        }
    }

    private void resume() {
        if (paused) {
            new Thread() {
                public void run() {
                    playing = true;
                    paused = false;
                    try {
                        fileInputStream = new FileInputStream(selectedFile);
                        player = new Player(fileInputStream);
                        fileInputStream.skip(pausedAt);
                        player.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (player != null) {
                            playing = false;
                            player.close();
                        }
                    }
                }
            }.start();
            statusLabel.setText("Playing");
        }
    }

    private void stop() {
        if (player != null) {
            playing = false;
            player.close();
            statusLabel.setText("Stopped");
            try {
                fileInputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void updateUI(String fileName) {
        SwingUtilities.invokeLater(() -> {
            fileNameLabel.setText("Playing: " + fileName);
            statusLabel.setText("Playing");
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleMP3Player();
            }
        });
    }
}

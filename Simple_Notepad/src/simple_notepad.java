import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import  javax.swing.UIManager;

@SuppressWarnings("ALL")
public class simple_notepad extends JFrame implements  ActionListener{
    static JFrame frame;
    JTextArea textArea;
    private final JMenuItem newMenuItem;
    private final JMenuItem cutMenuItem;
    private final JMenuItem copyMenuItem;
    private final JMenuItem pasteMenuItem;
    private final JMenuItem exitMenuItem;
    private final JMenuItem darkThemeMenuItem;
    private final JMenuItem  newWindowItem;
    private final JMenuItem undoMenuItem;
    private final JMenuItem selectAllMenuItem;
    private final JMenuItem saveAsMenuItem;
    private final JMenuItem deleteMenuItem;
    private final UndoManager undoManager;
    private final JComboBox<String> fontComboBox;
    private final JComboBox<Integer> sizeComboBox;
    private final JCheckBox boldCheckBox;
    private final JCheckBox italicCheckBox;
    private JColorChooser color_box;
    private Color color;
    private JButton button;
    private final JButton applyButton;
    JMenu recentMenu;
    List<String> recentFiles;
    private final JLabel wordCountLabel;
    private final JLabel lineCountLabel;
    private final JLabel charCountLabel;
    simple_notepad(){

        /* ***************************  adding image icon ****************************** */

        Image imageIcon = Toolkit.getDefaultToolkit().getImage("C:/Projects/Simple_Notepad/images/notepad_logo.png");
        setIconImage(imageIcon);

        /* ****************************** creating JFrame ********************************** */
        frame = new JFrame();
        setTitle("Simple Notepad");
        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* ****************************** creating JTextArea ********************************** */

        textArea = new JTextArea(10, 30);
        textArea.setText("Welcome to the Simple Notepad");
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        /* ****************************** creating JMenuBar ********************************** */

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Creating Menus for notepad

        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu formatMenu = new JMenu("Format");
        JMenu viewMenu = new JMenu("View");
        JMenu helpMenu = new JMenu("Help");
        JMenu zoomMenu = new JMenu("Zoom");
        recentMenu = new JMenu("Recent");
        recentFiles = new ArrayList<>();

        /* Adding menus in menu-bar */

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        // creating Menu Items

        newMenuItem = new JMenuItem("New");
        newWindowItem = new JMenuItem("New Window               ");
        JMenuItem openMenuItem = new JMenuItem("Open...");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveAsMenuItem = new JMenuItem("Save As...");
        JMenuItem printMenuItem = new JMenuItem("Print...");
        exitMenuItem = new JMenuItem("Exit");
        undoMenuItem = new JMenuItem("Undo");
        cutMenuItem = new JMenuItem("Cut");
        copyMenuItem = new JMenuItem("Copy");
        pasteMenuItem = new JMenuItem("Paste");
        deleteMenuItem = new JMenuItem("Delete");
        selectAllMenuItem = new JMenuItem("Select All           ");
        JMenuItem dateTimeMenuItem = new JMenuItem("Date/Time");
        JMenuItem zoomInItem = new JMenuItem("Zoom In");
        JMenuItem zoomOutItem = new JMenuItem("Zoom Out");
        JMenuItem restoreZoomItem = new JMenuItem("Restore Default Zoom            ");
        JMenuItem aboutItem = new JMenuItem(" About");
        darkThemeMenuItem = new JMenuItem("Dark Theme");
        JMenuItem wordWrapItem = new JCheckBoxMenuItem("Word Wrap");
        JMenuItem fontMenuItem = new JMenuItem("Font");
        JMenuItem statusBarItem = new JCheckBoxMenuItem("Status");

        // Adding menu items to different menu

        fileMenu.add(newMenuItem);
        fileMenu.add(newWindowItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(recentMenu);
        fileMenu.addSeparator();
        fileMenu.add(printMenuItem);
        fileMenu.add(exitMenuItem);
        editMenu.add(undoMenuItem);
        editMenu.addSeparator();
        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        editMenu.add(deleteMenuItem);
        editMenu.addSeparator();
        editMenu.add(selectAllMenuItem);
        editMenu.add(dateTimeMenuItem);
        formatMenu.add(wordWrapItem);
        formatMenu.add(fontMenuItem);
        viewMenu.add(darkThemeMenuItem);
        viewMenu.add(zoomMenu);
        zoomMenu.add(zoomInItem);
        zoomMenu.add(zoomOutItem);
        zoomMenu.add(restoreZoomItem);
        viewMenu.add(statusBarItem);
        helpMenu.add(aboutItem);

        // Adding listeners to menu items

        newMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        saveAsMenuItem.addActionListener(this);
        undoMenuItem.addActionListener(this);
        deleteMenuItem.addActionListener(this);
        selectAllMenuItem.addActionListener(this);
        dateTimeMenuItem.addActionListener(this);
        cutMenuItem.addActionListener(this);
        copyMenuItem.addActionListener(this);
        pasteMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);
        darkThemeMenuItem.addActionListener(this);
        restoreZoomItem.addActionListener(this);
        newWindowItem.addActionListener(this);
        printMenuItem.addActionListener(e -> printFile());
        aboutItem.addActionListener(e -> showAboutPopup(frame));
        fontMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showFontDialog(); // Show the font panel dialog when the menu item is clicked
            }
        });


        /* ************************ Adding Functionalities to different menu items******************* */

        openMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    //frame.setTitle("Notepad -" + selectedFile.getAbsolutePath());
                    openFile(selectedFile);
                } else if (returnValue == JFileChooser.CANCEL_OPTION) {
                    showError("File open operation was canceled.");
                }
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new TxtFileFilter());

                int result = fileChooser.showSaveDialog(simple_notepad.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                        selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
                    }
                    try (FileWriter writer = new FileWriter(selectedFile)) {
                        writer.write(textArea.getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else
                    JOptionPane.showMessageDialog(frame, "the user cancelled the operation");
            }
        });

        dateTimeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateTime = sdf.format(new Date());
                textArea.insert(dateTime, textArea.getCaretPosition());
            }
        });

        wordWrapItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean wordWrap = wordWrapItem.isSelected();
                textArea.setLineWrap(wordWrap);
                textArea.setWrapStyleWord(wordWrap);
            }
        });

        zoomInItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setFont(textArea.getFont().deriveFont(textArea.getFont().getSize() + 2.0f));
            }
        });
        zoomOutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setFont(textArea.getFont().deriveFont(textArea.getFont().getSize() - 2.0f));
            }
        });
        restoreZoomItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setFont(textArea.getFont().deriveFont(12.0f)); // Set the default font size
            }
        });

        // Creating objects for Word count

        wordCountLabel = new JLabel("Words: 0");
        lineCountLabel = new JLabel("Lines: 0");
        charCountLabel = new JLabel("Chars: 0");

        textArea.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                updateCounts();
            }
            public void removeUpdate(DocumentEvent e) {
                updateCounts();
            }
            public void changedUpdate(DocumentEvent e) {
                updateCounts();
            }
        });

        statusBarItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //statusBar.setVisible(statusBarItem.isSelected());
                JPanel statusPanel = new JPanel();
                statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                statusPanel.add(wordCountLabel);
                statusPanel.add(lineCountLabel);
                statusPanel.add(charCountLabel);

                getContentPane().add(scrollPane, BorderLayout.CENTER);
                getContentPane().add(statusPanel, BorderLayout.SOUTH);
                statusPanel.setVisible(statusBarItem.isSelected());

            }
        });

        // Creating object for undo adding functionalities in it

        undoManager = new UndoManager();
        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
                updateUndoRedoMenuItems();
            }
        });

        // Creating font items for font

        fontComboBox = new JComboBox<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        sizeComboBox = new JComboBox<>(new Integer[]{8, 10, 12, 14, 16, 18, 20,22, 24, 26, 28, 36,48, 72 });
        boldCheckBox = new JCheckBox("Bold");
        italicCheckBox = new JCheckBox("Italic");
        button = new JButton("Color");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                color_box= new JColorChooser();
                color=color_box.showDialog(button,"Select a Color",Color.white);
            }
        });
        applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyFontSettings();
            }
        });

        // Adding shortcut keys to different menu-items

        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        newWindowItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        printMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        selectAllMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        dateTimeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        zoomInItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ADD,
                KeyEvent.CTRL_DOWN_MASK));
        zoomOutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, KeyEvent.CTRL_DOWN_MASK));
        restoreZoomItem.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_0, KeyEvent.CTRL_DOWN_MASK));

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == newMenuItem) {
            textArea.setText("");
        } else if (e.getSource() == cutMenuItem) {
            textArea.cut();
        } else if (e.getSource() == copyMenuItem){
            textArea.copy();}
        else if (e.getSource() == pasteMenuItem) {
            textArea.paste();
        } else if (e.getSource() == exitMenuItem) {
            System.exit(0);
        } else if (e.getSource() == newWindowItem ){
            new simple_notepad();
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }else if(e.getSource() == selectAllMenuItem){
            textArea.selectAll();
        }else if (e.getSource() == undoMenuItem) {
            if (undoManager.canUndo()) {
                undoManager.undo();
                updateUndoRedoMenuItems();
            }
        }else if (e.getSource() == deleteMenuItem) {
            int selectionStart = textArea.getSelectionStart();
            int selectionEnd = textArea.getSelectionEnd();
            if (selectionStart != selectionEnd) {
                textArea.replaceRange("", selectionStart, selectionEnd);
            }
        } else if (e.getSource() == darkThemeMenuItem) {
            Color backgroundColor = textArea.getBackground();
            if (backgroundColor.equals((Color.BLACK))) {
                // Implement logic for applying dark theme
                textArea.setBackground(Color.WHITE);
                textArea.setForeground(Color.BLACK);
            } else {
                // Implement logic for applying dark theme
                textArea.setBackground(Color.BLACK);
                textArea.setForeground(Color.WHITE);
                textArea.setCaretColor(Color.white);
            }
        }else if (e.getSource() == saveAsMenuItem) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
            fileChooser.setFileFilter(filter);

            int returnValue = fileChooser.showSaveDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                var selectedFile = fileChooser.getSelectedFile();
                saveToFile(selectedFile);
            }
        }
    }
    private void printFile() {
        try {
            textArea.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }
    private void saveToFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(textArea.getText());
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openFile(File file) {
        if (file.getName().endsWith(".txt")) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                textArea.setText("");
                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }
                reader.close();
                recentFiles.add(file.getAbsolutePath());
                //frame.setTitle("Notepad -" + file.getAbsolutePath());

                // Update the Recent Files menu
                updateRecentFilesMenu();
            } catch (IOException e) {
                showError("Error reading the file: " + e.getMessage());
            }
        } else {
            showError("Only .txt files are supported.");
        }
    }

    private void updateRecentFilesMenu() {
        recentMenu.removeAll();
        for (String filePath : recentFiles) {
            JMenuItem recentFileMenuItem = new JMenuItem(new File(filePath).getAbsolutePath());
            recentMenu.add(recentFileMenuItem);
            recentFileMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    File selectedFile = new File(filePath);
                    openFile(selectedFile);
                }
            });
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static void showAboutPopup(JFrame parentFrame) {
        JOptionPane.showMessageDialog(parentFrame,
                "Simple Notepad Application\nVersion 1.0\nCreated by Akvr000",
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }
    private void updateCounts() {
        String text = textArea.getText();
        String[] words = text.trim().split("\\s+");
        int wordCount = words.length;
        int lineCount = textArea.getLineCount();
        int charCount = text.length();

        wordCountLabel.setText("Words: " + wordCount);
        lineCountLabel.setText("Lines: " + lineCount);
        charCountLabel.setText("Chars: " + charCount);
    }

    private void updateUndoRedoMenuItems() {
        undoMenuItem.setEnabled(undoManager.canUndo());
    }

   private void applyFontSettings() {
       String fontName = fontComboBox.getSelectedItem().toString();
       int fontSize = (int) sizeComboBox.getSelectedItem();
       int fontStyle = Font.PLAIN;
       if (boldCheckBox.isSelected()) {
           fontStyle += Font.BOLD;
       }
       if (italicCheckBox.isSelected()) {
           fontStyle += Font.ITALIC;
       }
       Font selectedFont = new Font(fontName, fontStyle, fontSize);
       textArea.setFont(selectedFont);
       textArea.setForeground(color);
   }

    private void showFontDialog() {
        JOptionPane.showMessageDialog(this, createFontPanel(), "Font Settings",
                JOptionPane.PLAIN_MESSAGE);
    }
    private JPanel createFontPanel() {
        JPanel fontPanel = new JPanel(new FlowLayout());
        fontPanel.add(new JLabel("Font:"));
        fontPanel.add(fontComboBox);
        fontPanel.add(new JLabel("Size:"));
        fontPanel.add(sizeComboBox);
        fontPanel.add(boldCheckBox);
        fontPanel.add(italicCheckBox);
        fontPanel.add(button);
        fontPanel.add(applyButton);

        return fontPanel;
    }
    public static void main(String[] arg){
        UIManager.put("MenuItem.acceleratorForeground", Color.GRAY);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new simple_notepad().setVisible(true);
                SwingUtilities.updateComponentTreeUI(frame);
            }

        });
    }
}
class TxtFileFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File file) {
        return file.getName().toLowerCase().endsWith(".txt") || file.isDirectory();
    }
    public String getDescription() {
        return "Text Files (*.txt)";
    }
}

package com.bugra;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main implements ActionListener {
    static char[] alphabetArray = {'A', 'B', 'C', 'Ç', 'D', 'E', 'F', 'G', 'Ğ', 'H', 'I', 'İ', 'J', 'K', 'L', 'M', 'N', 'O', 'Ö', 'P', 'R', 'S', 'Ş', 'T', 'U', 'Ü', 'V', 'Y', 'Z'};
    static String keyword, result;

    // for encryption purposes
    static String resultAfterEncryption = "";
    static int firstIndex = 0, secondIndex = 0, totalIndex = 0;

    // for decryption
    static String resultAfterDecryption = "";

    // for GUI operations
    static JFrame mainFrame;
    static JButton  encryptButton, decryptButton, clearButton;
    static JTextArea frameEncrypted, frameDecrypted, frameKeyword;
    static JLabel frameEncryptLabel, frameDecryptLabel, frameKeywordLabel;

    public static void main(String[] args) {

        mainFrame = new JFrame("Vigenère Cipher Application");

        frameEncrypted = new JTextArea(10,10);
        frameDecrypted = new JTextArea(10, 10);
        frameKeyword = new JTextArea(10, 10);

        encryptButton = new JButton(new AbstractAction("ENCRYPT!") {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyword = frameKeyword.getText();
                adjustKeywordLength(frameEncrypted.getText());

                result = encryptText(frameEncrypted.getText(), keyword);

                frameDecrypted.setText(result);
                frameEncrypted.setText("");
            }
        });

        decryptButton = new JButton(new AbstractAction("DECRYPT!") {
            @Override
            public void actionPerformed(ActionEvent e) {

                result = decryptText(frameDecrypted.getText(), keyword);

                frameEncrypted.setText(result);
                frameDecrypted.setText("");
            }
        });

        clearButton = new JButton(new AbstractAction("Clear All") {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameDecrypted.setText("");
                frameKeyword.setText("");
                frameEncrypted.setText("");
            }
        });

        frameEncryptLabel = new JLabel("Plaintext: ");
        frameKeywordLabel = new JLabel("Key: ");
        frameDecryptLabel = new JLabel("Ciphertext: ");

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5,30,10,30));
        panel.setLayout(new GridLayout(0,1));

        panel.add(frameKeywordLabel);
        panel.add(frameKeyword);
        panel.add(frameEncryptLabel);
        panel.add(frameEncrypted);
        panel.add(frameDecryptLabel);
        panel.add(frameDecrypted);
        panel.add(encryptButton);
        panel.add(decryptButton);
        panel.add(clearButton);

        mainFrame.add(panel);
        mainFrame.setSize(1000,1000);
        mainFrame.setLocationRelativeTo(null);   // create the frame at the center position
        mainFrame.setVisible(true);
    }

    // encrypt the plain text with the given keyword
    public static String encryptText(String plainText, String equalizedKeyword) {
        resultAfterEncryption = "";
        for(int i = 0; i < plainText.length(); i++) {
            int p = 0, k = 0;
            while(p < alphabetArray.length) {
                if(plainText.charAt(i) == alphabetArray[p]) {
                    firstIndex = p;
                    break;
                }
                p++;
            }

            while(k < alphabetArray.length) {
                if(equalizedKeyword.charAt(i) == alphabetArray[k]) {
                    secondIndex = k;
                    break;
                }
                k++;
            }

            totalIndex = firstIndex + secondIndex;
            totalIndex %= 29;

            resultAfterEncryption += alphabetArray[totalIndex];
        }

        return resultAfterEncryption;
    }

    // decrypt the text with the given keyword
    public static String decryptText(String encryptedText, String equalizedKeyword) {
        resultAfterDecryption = "";
        for(int i = 0; i < encryptedText.length(); i++) {
            int p = 0, k = 0;
            while(p < alphabetArray.length) {
                if(encryptedText.charAt(i) == alphabetArray[p]) {
                    firstIndex = p;
                    break;
                }
                p++;
            }

            while(k < alphabetArray.length) {
                if(equalizedKeyword.charAt(i) == alphabetArray[k]) {
                    secondIndex = k;
                    break;
                }
                k++;
            }

            totalIndex = (firstIndex - secondIndex + 29) % 29;

            resultAfterDecryption += alphabetArray[totalIndex];
        }

        return resultAfterDecryption;
    }

    // equalize the lengths of keyword and given text
    public static void adjustKeywordLength(String text) {
        if(keyword.length() < text.length()) {
            int difference = text.length() - keyword.length();
            for(int i = 0; i < difference; i++) {
                keyword += text.charAt(i);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

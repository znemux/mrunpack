package io.github.znemux.mrunpack.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import io.github.znemux.mrunpack.Main;
import static io.github.znemux.mrunpack.Main.frame;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Frame extends javax.swing.JFrame {
    
    public Frame() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Modrinth modpack", "mrpack"));
        label = new javax.swing.JLabel();
        textField = new javax.swing.JTextField();
        progressBar = new javax.swing.JProgressBar();
        selectButton = new javax.swing.JButton();
        unpackButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        label.setText("Select a Modrinth modpack (.mrpack)");

        progressBar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                progressBarStateChanged(evt);
            }
        });

        selectButton.setText("Select file");
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });

        unpackButton.setText("Unpack now");
        unpackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unpackButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textField)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(selectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(unpackButton, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectButton)
                    .addComponent(unpackButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectButtonActionPerformed
        File dir = new File(textField.getText().trim());
        
        if (dir.isDirectory()) fileChooser.setCurrentDirectory(dir);
        else if (dir.exists()) fileChooser.setCurrentDirectory(dir.getParentFile());
        else fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                textField.setText(fileChooser.getSelectedFile().getCanonicalPath());
            } catch (IOException ex) {
                message(ex.getMessage());
            }
        }
        
    }//GEN-LAST:event_selectButtonActionPerformed

    private void unpackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unpackButtonActionPerformed
        Path file = Path.of(textField.getText());
        if (!Files.exists(file) || Files.isDirectory(file)) {
            message("Error: " + file + " doesn't exist or is a directory");
            return;
        } else if (!file.toString().endsWith(".mrpack")) {
            message("Error: " + file + " isn't a valid Mrpack file");
            return;
        }
        unpackButton.setEnabled(false);
        Runnable task = () -> {
            try {
                Main.unpack(file);
            } catch (IOException |URISyntaxException ex) {
                message("Error: " + ex.getMessage());
            }
        };
        new Thread(task).start();
    }//GEN-LAST:event_unpackButtonActionPerformed

    private void progressBarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_progressBarStateChanged
    }//GEN-LAST:event_progressBarStateChanged
    
    public void updateProgressBar(int value) {
        progressBar.setValue(value);
    }
    
    public void message(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    public static void init() {
        /* Set the Flatlaf look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
            * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
            */
            javax.swing.UIManager.setLookAndFeel( new FlatDarkLaf() );
            } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
            //</editor-fold>
        java.awt.EventQueue.invokeLater(() -> {
            frame = new Frame();
            frame.setTitle("mrunpack: Modrinth modpack extractor");
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel label;
    public javax.swing.JProgressBar progressBar;
    private javax.swing.JButton selectButton;
    private javax.swing.JTextField textField;
    public javax.swing.JButton unpackButton;
    // End of variables declaration//GEN-END:variables
}

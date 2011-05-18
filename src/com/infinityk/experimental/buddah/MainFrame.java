/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainFrame.java
 *
 * Created on 15-may-2011, 20:42:09
 */
package com.infinityk.experimental.buddah;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opencl.CL;
import org.lwjgl.opencl.CL10;
import org.lwjgl.opencl.CLContext;
import org.lwjgl.opencl.CLDevice;
import org.lwjgl.opencl.CLPlatform;

/**
 *
 * @author Asier
 */
public class MainFrame extends javax.swing.JFrame {
    private CLPlatform platform;
    private CLContext context;
    
    private GenericListModel<CLDevice> devices;
    
    /** Creates new form MainFrame */
    public MainFrame() {
        initOpenCL();
        initComponents();
    }
    
    final void initOpenCL() {
        try {
            CL.create();
            platform = CLPlatform.getPlatforms().get(0);
            devices = new GenericListModel<CLDevice>( platform.getDevices(CL10.CL_DEVICE_TYPE_ALL) );
            context = CLContext.create(platform, devices.getElements(), null, null, null);
        } catch (LWJGLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        redMinText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        redMaxText = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        greenMinText = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        greenMaxText = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        blueMinText = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        blueMaxTest = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        transferCombo = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        transferFactor = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        overexposureText = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        exponentText = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        samplesPerWorkingEdgeText = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        nWorkingItemText = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        imageWidthText = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        imageHeightText = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        outputText = new javax.swing.JTextField();
        outputButton = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        timeText = new javax.swing.JTextField();
        generateButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        cardsList = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GPU BuddahBrot");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Color"));

        jLabel1.setText("Red:");

        redMinText.setText("0");
        redMinText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redMinTextActionPerformed(evt);
            }
        });

        jLabel2.setText("to");

        redMaxText.setText("100");

        jLabel3.setText("Green:");

        greenMinText.setText("100");

        jLabel4.setText("to");

        greenMaxText.setText("1000");

        jLabel5.setText("Blue:");

        blueMinText.setText("1000");

        jLabel6.setText("to");

        blueMaxTest.setText("10000");

        jLabel7.setText("Transfer function:");

        transferCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Identity", "Exponential", "Logarithmic" }));
        transferCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferComboActionPerformed(evt);
            }
        });

        jLabel8.setText("Factor:");

        transferFactor.setText("5");
        transferFactor.setEnabled(false);

        jLabel9.setText("Overexposure:");

        overexposureText.setText("1.5");
        overexposureText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                overexposureTextActionPerformed(evt);
            }
        });

        jLabel16.setText("P:");

        exponentText.setText("2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(blueMinText, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(blueMaxTest, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(greenMinText, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(greenMaxText, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                        .addGap(70, 70, 70)
                        .addComponent(jLabel8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(redMinText, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(redMaxText, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(transferCombo, 0, 166, Short.MAX_VALUE)
                    .addComponent(overexposureText)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(transferFactor, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exponentText, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(redMinText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(redMaxText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(transferCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(greenMinText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(greenMaxText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(transferFactor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(exponentText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(blueMinText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(blueMaxTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(overexposureText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Image"));

        jLabel10.setText("Samples per working item edge:");

        samplesPerWorkingEdgeText.setText("32");

        jLabel11.setText("NxN working items N = ");

        nWorkingItemText.setText("32");

        jLabel12.setText("Image width:");

        imageWidthText.setText("128");

        jLabel13.setText("Image height:");

        imageHeightText.setText("128");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(samplesPerWorkingEdgeText)
                    .addComponent(nWorkingItemText, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imageHeightText, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(imageWidthText, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(samplesPerWorkingEdgeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(imageWidthText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(nWorkingItemText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(imageHeightText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel14.setText("Output:");

        outputButton.setText("Select");
        outputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputButtonActionPerformed(evt);
            }
        });

        jLabel15.setText("Time:");

        timeText.setEditable(false);

        generateButton.setText("Generate");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        cardsList.setModel(devices);
        cardsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane1.setViewportView(cardsList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(outputText, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(outputButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(timeText, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(generateButton)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(outputText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(timeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generateButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void overexposureTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_overexposureTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_overexposureTextActionPerformed

    private void redMinTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redMinTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_redMinTextActionPerformed

    private void outputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputButtonActionPerformed
        String path = FileUtils.selectNonExistingFile(this, ".png");
        if(path != null){
            outputText.setText(path);
        }
    }//GEN-LAST:event_outputButtonActionPerformed

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        //check card selected
        int selectedIndex = cardsList.getSelectedIndex();
        if(selectedIndex == -1){
            return;
        }
        CLDevice device = devices.getAt(selectedIndex);
        
        
        //parse parameters
        int redMin = Integer.parseInt( redMinText.getText() );
        int redMax = Integer.parseInt( redMaxText.getText() );
        int greenMin = Integer.parseInt( greenMinText.getText() );
        int greenMax = Integer.parseInt( greenMaxText.getText() );
        int blueMin = Integer.parseInt( blueMinText.getText() );
        int blueMax = Integer.parseInt( blueMaxTest.getText() );
        String transferFunction = transferCombo.getSelectedItem().toString();
        float factor = Float.parseFloat( transferFactor.getText() );
        float overexposure = Float.parseFloat( overexposureText.getText() );
        float exponent = Float.parseFloat( exponentText.getText() );
        int samplesPerWorkingItemSide = Integer.parseInt( samplesPerWorkingEdgeText.getText() );
        int workingItemsPerSide = Integer.parseInt(nWorkingItemText.getText());
        int imageWidth = Integer.parseInt(imageWidthText.getText());
        int imageHeight = Integer.parseInt( imageHeightText.getText() );
        
        //create worker
        BuddahBrot buddah = new BuddahBrot(
                redMin,redMax,greenMin,greenMax,blueMin,blueMax,
                transferFunction,factor,overexposure,exponent,
                samplesPerWorkingItemSide,workingItemsPerSide,
                imageWidth,imageHeight,
                context,device);
        
        //calculate density maps
        buddah.calcDensityMaps();
        timeText.setText("Millis: " + buddah.getTime());
        
        //apply transfer functions
        buddah.applyTransferFunctions();
        try {
            //save image
            buddah.saveImage(outputText.getText());
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_generateButtonActionPerformed

    private void transferComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transferComboActionPerformed
        transferFactor.setEnabled(! transferCombo.getSelectedItem().toString().equalsIgnoreCase("Identity"));
    }//GEN-LAST:event_transferComboActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField blueMaxTest;
    private javax.swing.JTextField blueMinText;
    private javax.swing.JList cardsList;
    private javax.swing.JTextField exponentText;
    private javax.swing.JButton generateButton;
    private javax.swing.JTextField greenMaxText;
    private javax.swing.JTextField greenMinText;
    private javax.swing.JTextField imageHeightText;
    private javax.swing.JTextField imageWidthText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nWorkingItemText;
    private javax.swing.JButton outputButton;
    private javax.swing.JTextField outputText;
    private javax.swing.JTextField overexposureText;
    private javax.swing.JTextField redMaxText;
    private javax.swing.JTextField redMinText;
    private javax.swing.JTextField samplesPerWorkingEdgeText;
    private javax.swing.JTextField timeText;
    private javax.swing.JComboBox transferCombo;
    private javax.swing.JTextField transferFactor;
    // End of variables declaration//GEN-END:variables

    
}
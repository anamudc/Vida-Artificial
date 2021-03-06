/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turingMorphA;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JApplet;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
//import processing.core.PImage;

/**
 * An applet to demonstrate Turing's reaction-diffusion model.
 * For more information, see:
 *   [1] Rafael Collantes. Algorithm Alley. Dr. Dobb's Journal, December 1996.
 *   [2] Alan M. Turing. The chemical basis of morphogenesis. Philosophical
 *       Transactions of the Royal Society of London. B 327, 37–72 (1952)
 *
 * @author Christopher G. Jennings (cjennings [at] acm.org)
 */
public class TuringMorph extends JApplet {

    /**
     * Labels that describe the sizes of the Turing systems that the user
     * can generate. These labels are added to the size drop down and
     * when a new size is selected, it is read directly from the label.
     * You may add the size n x n by adding a new string that ends with
     * " n)" (the textures are always assumed to be square). If you add
     * an entry larger than the current maximum size, you must also update
     * <code>MAXIMUM_SYSTEM_SIZE</code>.
     */
     public TuringMorph(){

     }
    
    private static final String[] SYSTEM_SIZES = new String[] {
            "Tiny (64 x 64)", "Small (128 x 128)", "Medium (192 x 192)",
            "Large (256 x 256)", "Extra Large (350 x 350)"
    };
    /**
     * The size of the largest possible data set. This must be the largest of all
     * the size settings in <code>imageSizeDropDown</code>.
     */
    private static final int MAXIMUM_SYSTEM_SIZE = 350;
    /** Names for the built-in examples. */
    private static final String[] PRESET_NAMES = new String[] {
            "Cheetah", "Colony", "Fine", "Fingerprint", "Maze", "Pocked",
    };
    /** Constant pairs for the built-in examples. */
    private static final double[] PRESET_CONSTANTS = new double[] {
            3.5d, 16d, 1.6d, 6d, 0.1d, 1d, 1d, 16d, 2.6d, 24d, 1d, 3d,
    };

    /**
     * Initializes the applet when the web browser first loads it in.
     */
    @Override
    public void init() {
        try {
            java.awt.EventQueue.invokeAndWait( new Runnable() {
                public void run() {
                    try {
                        String lafClass = UIManager.getSystemLookAndFeelClassName();
                        for( LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels() ) {
                                if( laf.getName().equals( "Nimbus" ) ) {
                                        lafClass = laf.getClassName();
                                        break;
                                }
                        }
                        UIManager.setLookAndFeel( lafClass );
                    } catch( ClassNotFoundException e ) {
                        // shouldn't be possible, as not even a system LaF is
                        // available, but it doesn't matter
                    } catch( Exception e ) {
                        e.printStackTrace();
                    }
                    initComponents();
                }
            } );
        } catch( Exception ex ) {
                ex.printStackTrace();
        }
    }

    /**
     * Start solving for the default constants when the applet starts.
     */
    @Override
    public void start() {
        try {
            java.awt.EventQueue.invokeLater( new Runnable() {
                public void run() {
                    okButtonActionPerformed( null );
                }
            } );
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    /**
     * Stop solver thread, if running, when browser leaves the page the applet is on.
     */
    @Override
    public void stop() {
        if( solver != null ) {
            // does not have to be called from EDT
            try {
                solver.stop();
            } catch( NullPointerException e ) {
            }
        }
    }

    /**
     * If run in Java 6 update 10 or newer, this allows the applet to be dragged
     * out of the web page as an independent window by dragging the title bar.
     *
     * @param e the <code>MouseEvent</code> to test the component with
     * @return <code>true</code> if Alt+left click is down or the title bar is clicked
     */
    public boolean isAppletDragStart( MouseEvent e ) {
        Object source = e.getSource();

        // since this method was called, the applet is draggable
        // tell the user how to drag it out of the web page
        if( source == this ) {
            if( titleLabel.getToolTipText().equals( ABOUT_TIP ) ) {
                titleLabel.setToolTipText( ABOUT_TIP + DRAG_TIP );
                titlePanel.setToolTipText( ABOUT_TIP + DRAG_TIP );
            }
        }

        if( (e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0 ) {
                // check if dragging title bar
                // e.getSource() is the applet when it is embedded in the web page
                //   and a parent of the applet when it is in an independent window
                if( source == this || ((Container) source).isAncestorOf( this ) || source == titlePanel || source == titleLabel ) {
                        if( e.getY() <= titlePanel.getHeight() ) {
                                return true;
                        }
                }
                // always drag if ALT is held down
                return (e.getModifiersEx() & MouseEvent.ALT_DOWN_MASK) != 0;
        }
        return false;
    }

    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        javax.swing.JPanel controlPanel = new javax.swing.JPanel();
        javax.swing.JLabel imageSizeLabel = new javax.swing.JLabel();
        imageSizeDropDown = new javax.swing.JComboBox();
        iterationsLabel = new javax.swing.JLabel();
        iterationsSlider = new javax.swing.JSlider();
        javax.swing.JLabel constantsLabel = new javax.swing.JLabel();
        constAField = new javax.swing.JTextField();
        constBField = new javax.swing.JTextField();
        okButton = new javax.swing.JButton();
        javax.swing.JLabel colourLabel = new javax.swing.JLabel();
        minColourButton = new javax.swing.JButton();
        maxColourButton = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        randomColourButton = new javax.swing.JButton();
        tileCheckBox = new javax.swing.JCheckBox();
        presetsLabel = new javax.swing.JLabel();
        presetDropDown = new javax.swing.JComboBox();
        randomizeCheck = new javax.swing.JCheckBox();
        randomizeNowBtn = new javax.swing.JButton();
        javax.swing.JLabel appearanceLabel = new javax.swing.JLabel();
        javax.swing.JSeparator appearanceSeparator = new javax.swing.JSeparator();
        javax.swing.JLabel advancedLabel = new javax.swing.JLabel();
        javax.swing.JSeparator advancedSeparator = new javax.swing.JSeparator();
        javax.swing.JLabel basicLabel = new javax.swing.JLabel();
        javax.swing.JSeparator basicSeparator = new javax.swing.JSeparator();
        javax.swing.JPanel visualizerPanel = new javax.swing.JPanel();
        titlePanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();

        controlPanel.setBackground(java.awt.Color.white);
        controlPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        controlPanel.setLayout(new java.awt.GridBagLayout());

        imageSizeLabel.setDisplayedMnemonic('S');
        imageSizeLabel.setLabelFor(imageSizeDropDown);
        imageSizeLabel.setText("Size (in Pixels)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 4, 8);
        controlPanel.add(imageSizeLabel, gridBagConstraints);

        imageSizeDropDown.setModel( new javax.swing.DefaultComboBoxModel( SYSTEM_SIZES ) );
        imageSizeDropDown.setToolTipText("Select a Size for the Texture to be Created");
        imageSizeDropDown.setOpaque(false);
        imageSizeDropDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageSizeDropDownActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 8);
        controlPanel.add(imageSizeDropDown, gridBagConstraints);

        iterationsLabel.setDisplayedMnemonic('N');
        iterationsLabel.setLabelFor(iterationsSlider);
        iterationsLabel.setText( "Number of Iterations: " + formatter.format( 2000 ) );
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 4, 8);
        controlPanel.add(iterationsLabel, gridBagConstraints);

        iterationsSlider.setBackground(java.awt.Color.white);
        iterationsSlider.setMajorTickSpacing(4999);
        iterationsSlider.setMaximum(5000);
        iterationsSlider.setMinimum(1);
        iterationsSlider.setMinorTickSpacing(500);
        iterationsSlider.setPaintLabels(true);
        iterationsSlider.setPaintTicks(true);
        iterationsSlider.setValue(2000);
        iterationsSlider.setOpaque(false);
        iterationsSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                iterationsSliderStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 0, 8);
        controlPanel.add(iterationsSlider, gridBagConstraints);

        constantsLabel.setDisplayedMnemonic('C');
        constantsLabel.setLabelFor(constAField);
        constantsLabel.setText("Diffusion Constants");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 2, 8);
        controlPanel.add(constantsLabel, gridBagConstraints);

        constAField.setColumns(4);
        constAField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        constAField.setText( formatter.format( PRESET_CONSTANTS[0] ) );
        constAField.setMinimumSize(new java.awt.Dimension(32, 20));
        constAField.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 0, 0);
        controlPanel.add(constAField, gridBagConstraints);

        constBField.setColumns(4);
        constBField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        constBField.setText( formatter.format( PRESET_CONSTANTS[1] ) );
        constBField.setMinimumSize(new java.awt.Dimension(32, 20));
        constBField.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 4);
        controlPanel.add(constBField, gridBagConstraints);

        okButton.setMnemonic('D');
        okButton.setText("   Do It!   ");
        okButton.setToolTipText("Create a Texture Using These Settings");
        okButton.setOpaque(false);
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 16, 4, 8);
        controlPanel.add(okButton, gridBagConstraints);

        colourLabel.setText("Colours (click on a colour to change it)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 8, 2, 8);
        controlPanel.add(colourLabel, gridBagConstraints);

        minColourButton.setBackground( TuringSystemVisualizer.DEFAULT_CMIN );
        minColourButton.setText("        ");
        minColourButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colourButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 0, 0);
        controlPanel.add(minColourButton, gridBagConstraints);

        maxColourButton.setBackground( TuringSystemVisualizer.DEFAULT_CMAX );
        maxColourButton.setText("        ");
        maxColourButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colourButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        controlPanel.add(maxColourButton, gridBagConstraints);

        progressBar.setMaximum(2000);
        progressBar.setString("0");
        progressBar.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 4, 8);
        controlPanel.add(progressBar, gridBagConstraints);

        randomColourButton.setFont(randomColourButton.getFont().deriveFont(randomColourButton.getFont().getSize()-2f));
        randomColourButton.setText("Pick Randomly");
        randomColourButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomColourButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 8);
        controlPanel.add(randomColourButton, gridBagConstraints);

        tileCheckBox.setSelected(true);
        tileCheckBox.setText("Tile the Texture Over the Entire Canvas");
        tileCheckBox.setOpaque(false);
        tileCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tileCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 0, 8);
        controlPanel.add(tileCheckBox, gridBagConstraints);

        presetsLabel.setText("Presets");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 2, 8);
        controlPanel.add(presetsLabel, gridBagConstraints);

        presetDropDown.setFont(presetDropDown.getFont().deriveFont(presetDropDown.getFont().getSize()-1f));
        presetDropDown.setModel( new DefaultComboBoxModel( PRESET_NAMES ) );
        presetDropDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presetDropDownActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 8);
        controlPanel.add(presetDropDown, gridBagConstraints);

        randomizeCheck.setSelected(true);
        randomizeCheck.setText("Randomize Cells at the Start of Each Run");
        randomizeCheck.setOpaque(false);
        randomizeCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomizeCheckActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 0, 8);
        controlPanel.add(randomizeCheck, gridBagConstraints);

        randomizeNowBtn.setFont(randomizeNowBtn.getFont().deriveFont(randomizeNowBtn.getFont().getSize()-2f));
        randomizeNowBtn.setText("Randomize Cells Now");
        randomizeNowBtn.setEnabled(false);
        randomizeNowBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomizeNowBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 24, 0, 8);
        controlPanel.add(randomizeNowBtn, gridBagConstraints);

        appearanceLabel.setFont(appearanceLabel.getFont().deriveFont(appearanceLabel.getFont().getStyle() | java.awt.Font.BOLD, appearanceLabel.getFont().getSize()+2));
        appearanceLabel.setText("Texture Appearance");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 8, 0, 8);
        controlPanel.add(appearanceLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 8);
        controlPanel.add(appearanceSeparator, gridBagConstraints);

        advancedLabel.setFont(advancedLabel.getFont().deriveFont(advancedLabel.getFont().getStyle() | java.awt.Font.BOLD, advancedLabel.getFont().getSize()+2));
        advancedLabel.setText("More Options");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 8, 0, 8);
        controlPanel.add(advancedLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 8);
        controlPanel.add(advancedSeparator, gridBagConstraints);

        basicLabel.setFont(basicLabel.getFont().deriveFont(basicLabel.getFont().getStyle() | java.awt.Font.BOLD, basicLabel.getFont().getSize()+2));
        basicLabel.setText("The Basics");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 0, 8);
        controlPanel.add(basicLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 8);
        controlPanel.add(basicSeparator, gridBagConstraints);

        getContentPane().add(controlPanel, java.awt.BorderLayout.WEST);

        visualizer = new TuringSystemVisualizer( MAXIMUM_SYSTEM_SIZE, MAXIMUM_SYSTEM_SIZE );
        visualizerPanel.add( visualizer );
        visualizer.setTiled( tileCheckBox.isSelected() );
        imageSizeDropDown.setSelectedIndex( 2 );
        // copy the standard so we can swap between it and an "error" border
        textFieldBorder = constAField.getBorder();

        visualizerPanel.setBackground(java.awt.Color.white);
        visualizerPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        visualizerPanel.setLayout(new javax.swing.BoxLayout(visualizerPanel, javax.swing.BoxLayout.LINE_AXIS));
        getContentPane().add(visualizerPanel, java.awt.BorderLayout.CENTER);

        titlePanel.setBackground(java.awt.Color.black);
        titlePanel.setToolTipText( ABOUT_TIP );
        titlePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                titlePanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                titlePanelMouseExited(evt);
            }
        });

        titleLabel.setFont(titleLabel.getFont().deriveFont(titleLabel.getFont().getStyle() | java.awt.Font.BOLD, titleLabel.getFont().getSize()+1));
        titleLabel.setForeground(java.awt.Color.white);
        titleLabel.setText( TITLE );
        titleLabel.setToolTipText( ABOUT_TIP );
        titlePanel.add(titleLabel);

        getContentPane().add(titlePanel, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void iterationsSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_iterationsSliderStateChanged
        iterationsLabel.setText( "Number of Iterations: " + formatter.format( iterationsSlider.getValue() ) );
    }//GEN-LAST:event_iterationsSliderStateChanged

    private void imageSizeDropDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageSizeDropDownActionPerformed
        int newBaseSize = baseSize;
        // parse the new size directly from the drop down item text
        Object o = imageSizeDropDown.getSelectedItem();
        String s = "";
        if( o != null ) {
            try {
                    s = ((String) o);
                    s = s.substring( s.lastIndexOf( ' ' ) + 1, s.length() - 1 );
                    newBaseSize = Integer.parseInt( s );
            } catch( NumberFormatException e ) {
                    throw new AssertionError( "Size drop down item has illegal " +
                                    "format---must end in \" n)\"" );
            }

            if( newBaseSize != baseSize ) {
                    baseSize = newBaseSize;

                    if( solver != null ) {
                            solver.stop();
                    }

                    solver = new TuringSystemSolver( new TuringSystemSolver.ResultListener() {

                            public void solverResultEvent( int iteration, double[][] result ) {
                                    visualizer.updateImage( result, baseSize, baseSize );
                                    final int i = iteration;
                                    SwingUtilities.invokeLater( new Runnable() {

                                            public void run() {
                                                    progressBar.setValue( i );
                                                    progressBar.setString( formatter.format( i ) );
                                            }
                                    } );
                            }
                    }, baseSize, baseSize );

                    solver.randomize();
            }
        }
    }//GEN-LAST:event_imageSizeDropDownActionPerformed

	/** Generate a new texture. */
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        iterations = iterationsSlider.getValue();
        progressBar.setMaximum( iterations );
        solver.stop();
        if( randomizeCheck.isSelected() ) {
                solver.randomize();
        }
        CA = parseConstant( constAField, CA );
        CB = parseConstant( constBField, CB );

        solver.solve( iterations, CA, CB );
    }//GEN-LAST:event_okButtonActionPerformed

    private void colourButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colourButtonActionPerformed
        boolean isMin = evt.getSource() == minColourButton;

        Color min = minColourButton.getBackground();
        Color max = maxColourButton.getBackground();

        Component parent = isMin ? minColourButton : maxColourButton;

        Color pick = JColorChooser.showDialog( parent, "Choose Colour", isMin ? min : max );
        if( pick != null ) {
                if( isMin ) {
                        min = pick;
                } else {
                        max = pick;
                }
                visualizer.setColors( min, max );
                minColourButton.setBackground( min );
                maxColourButton.setBackground( max );
        }
    }//GEN-LAST:event_colourButtonActionPerformed

    private void randomColourButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomColourButtonActionPerformed
        float hue = (float) Math.random();
	float sat = (float) Math.random();
	float bri = (float) Math.random() * 0.667f;
	Color min = new Color( Color.HSBtoRGB( hue, sat, bri ) );

	hue += (float) Math.random() * 26f / 180f;
	bri = 0.75f + (float) Math.random() / 4f;
	Color max = new Color( Color.HSBtoRGB( hue, sat, bri ) );

	if( Math.random() >= 0.5f ) {
		Color temp = min;
		min = max;
		max = temp;
	}

	minColourButton.setBackground( min );
	maxColourButton.setBackground( max );
	visualizer.setColors( min, max );
    }//GEN-LAST:event_randomColourButtonActionPerformed

    private void tileCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tileCheckBoxActionPerformed
        visualizer.setTiled( tileCheckBox.isSelected() );
    }//GEN-LAST:event_tileCheckBoxActionPerformed

    private void presetDropDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presetDropDownActionPerformed
	int i = presetDropDown.getSelectedIndex();
	if( i >= 0 ) {
            constAField.setText( formatter.format( PRESET_CONSTANTS[i * 2] ) );
            constBField.setText( formatter.format( PRESET_CONSTANTS[i * 2 + 1] ) );
	}
    }//GEN-LAST:event_presetDropDownActionPerformed

    private void randomizeCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomizeCheckActionPerformed
	randomizeNowBtn.setEnabled( !randomizeCheck.isSelected() );
    }//GEN-LAST:event_randomizeCheckActionPerformed

    private void randomizeNowBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomizeNowBtnActionPerformed
	solver.stop();
	solver.randomize();
    }//GEN-LAST:event_randomizeNowBtnActionPerformed

    private void titlePanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titlePanelMouseEntered
        ToolTipManager.sharedInstance().setInitialDelay( 50 );
    }//GEN-LAST:event_titlePanelMouseEntered

    private void titlePanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titlePanelMouseExited
	ToolTipManager.sharedInstance().setInitialDelay( 750 );
    }//GEN-LAST:event_titlePanelMouseExited


    /**
     * Parse the content of <code>field</code> for a double value.
     * If the content is a valid double, its value is returned.
     * Otherwise, <code>defaultValue</code> is returned and the field
     * is highlighted with a red border.
     *
     * @return the value in <code>field</code>, or <code>defaultValue</code>
     */
    private double parseConstant( JTextField field, double defaultValue ) {
        double value;
        try {
            value = formatter.parse( field.getText() ).doubleValue();
            field.setBorder( textFieldBorder );
        } catch( ParseException e ) {
            field.setText( formatter.format( defaultValue ) );
            value = defaultValue;
            field.setBorder( errorBorder );
        }
        return value;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField constAField;
    private javax.swing.JTextField constBField;
    private javax.swing.JComboBox imageSizeDropDown;
    private javax.swing.JLabel iterationsLabel;
    private javax.swing.JSlider iterationsSlider;
    private javax.swing.JButton maxColourButton;
    private javax.swing.JButton minColourButton;
    private javax.swing.JButton okButton;
    private javax.swing.JComboBox presetDropDown;
    private javax.swing.JLabel presetsLabel;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton randomColourButton;
    private javax.swing.JCheckBox randomizeCheck;
    private javax.swing.JButton randomizeNowBtn;
    private javax.swing.JCheckBox tileCheckBox;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel titlePanel;
    // End of variables declaration//GEN-END:variables

    private TuringSystemSolver solver;
    private int baseSize = 64;
    private int iterations = 2000;
    private double CA = 3.5d, CB = 16d;
    private TuringSystemVisualizer visualizer;
    private Border textFieldBorder;
    private Border errorBorder = BorderFactory.createLineBorder( Color.RED, 2 );
    private NumberFormat formatter = NumberFormat.getNumberInstance();

    private static final String TITLE = "Turing Reaction-Diffusion Morphogenesis";
    private static final String ABOUT_TIP = "<html><b>Christopher G. Jennings</b>" + " (Version 5)<br>http://cgjennings.ca";
    private static final String DRAG_TIP = "<br>&nbsp;<br>Drag This Title Bar" + " to Separate From Web Page";
}

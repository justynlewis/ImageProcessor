package view;


import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import controller.Features;

import static controller.ImageProcessingControllerGUI.imageRGBCounter;

/**
 * Represents the frame of the Image Processor GUI.
 */
public class ImageProcessorFrame extends JFrame implements ImageProcessorViewGUI {
  private JPanel mainPanel;
  private JLabel fileOpenDisplay;
  private JLabel errorLabel;

  private JLabel imageLabel;
  private JButton sepia;
  private JButton greyscale;
  private JButton blur;
  private JButton sharpen;
  private JButton brighten;
  private JButton darken;
  private JButton red;
  private JButton green;
  private JButton blue;
  private JButton luma;
  private JButton intensity;
  private JButton value;
  private JButton horizontal;
  private JButton vertical;


  private JButton loadButton;
  private JButton saveButton;
  private JTextField lightNum;

  private JButton downscale;
  private JTextField widthNum;
  private JTextField heightNum;


  private JPanel redHistogram = new JPanel();
  private JPanel greenHistogram = new JPanel();
  private JPanel blueHistogram = new JPanel();
  private JPanel intensityHistogram = new JPanel();

  /**
   * Creates a new JFrame that represents the frame for the Image Processor and initializes
   * each of the panels accordingly.
   */
  public ImageProcessorFrame() {
    super();
    setTitle("Image Processor");
    setSize(1000, 600);

    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    this.setSouthPanel();
    this.setNorthPanel();
    this.setCenterPanel();
    this.setWestPanel();
    this.setEastPanel();

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * Initializes the west panel.
   */
  private void setWestPanel() {
    JPanel processingOperations = new JPanel();
    processingOperations.setBorder(BorderFactory.createTitledBorder("Available Operations"));

    sepia = new JButton("Sepia");
    sepia.setActionCommand("sepia");
    greyscale = new JButton("Greyscale");
    greyscale.setActionCommand("greyscale");
    blur = new JButton("Blur");
    blur.setActionCommand("blur");
    sharpen = new JButton("Sharpen");
    sharpen.setActionCommand("sharpen");
    JPanel lightPanel = new JPanel();
    lightPanel.setLayout(new GridLayout(1, 3));
    brighten = new JButton("Brighten");
    brighten.setActionCommand("brighten");
    darken = new JButton("Darken");
    darken.setActionCommand("darken");
    lightNum = new JTextField();
    lightPanel.add(brighten);
    lightPanel.add(darken);
    lightPanel.add(lightNum);
    red = new JButton("Red Component");
    red.setActionCommand("red-component");
    green = new JButton("Green Component");
    green.setActionCommand("green-component");
    blue = new JButton("Blue Component");
    blue.setActionCommand("blue-component");
    luma = new JButton("Luma Component");
    luma.setActionCommand("luma-component");
    intensity = new JButton("Intensity Component");
    intensity.setActionCommand("intensity-component");
    value = new JButton("Value Component");
    value.setActionCommand("value-component");
    horizontal = new JButton("Horizontal Flip");
    horizontal.setActionCommand("horizontal-flip");
    vertical = new JButton("Vertical Flip");
    vertical.setActionCommand("vertical-flip");
    processingOperations.setLayout(new GridLayout(18, 1, 10, 10));
    processingOperations.add(sepia);
    processingOperations.add(greyscale);
    processingOperations.add(blur);
    processingOperations.add(sharpen);
    processingOperations.add(red);
    processingOperations.add(green);
    processingOperations.add(blue);
    processingOperations.add(luma);
    processingOperations.add(intensity);
    processingOperations.add(value);
    processingOperations.add(horizontal);
    processingOperations.add(vertical);
    processingOperations.add(lightPanel);


    JPanel downPanel = new JPanel();
    downPanel.setLayout(new GridLayout(1, 3));
    downscale = new JButton("Downscale");
    downscale.setActionCommand("downscale");
    widthNum = new JTextField();
    heightNum = new JTextField();
    downPanel.add(downscale);
    downPanel.add(widthNum);
    downPanel.add(heightNum);
    processingOperations.add(downPanel);


    JLabel instructions = new JLabel("Brighten/Darken text field: ");
    JLabel instructionsTwo = new JLabel("Input an integer, then click " +
            "brighten/darken.");
    JLabel instructionsThree = new JLabel("Downscale text fields: ");
    JLabel instructionsFour = new JLabel(
            "Input #s for width & height %s then click downscale.");



    processingOperations.add(instructions);
    processingOperations.add(instructionsTwo);
    processingOperations.add(instructionsThree);
    processingOperations.add(instructionsFour);

    mainPanel.add(processingOperations, BorderLayout.WEST);

    add(mainPanel);
  }

  /**
   * Sets action listeners to each of the buttons.
   *
   * @param features the given Features object
   */
  public void setOperationsButtonListener(Features features) {
    sepia.addActionListener(evt -> features.applyOp("color-transformation",
            sepia.getActionCommand()));
    greyscale.addActionListener(evt -> features.applyOp("color-transformation",
            greyscale.getActionCommand()));

    blur.addActionListener(evt -> features.applyOp("filter", blur.getActionCommand()));
    sharpen.addActionListener(evt -> features.applyOp("filter", sharpen.getActionCommand()));

    red.addActionListener(evt -> features.applyOp("greyscale", red.getActionCommand()));
    green.addActionListener(evt -> features.applyOp("greyscale", green.getActionCommand()));
    blue.addActionListener(evt -> features.applyOp("greyscale", blue.getActionCommand()));
    luma.addActionListener(evt -> features.applyOp("greyscale", luma.getActionCommand()));
    intensity.addActionListener(evt -> features.applyOp("greyscale",
            intensity.getActionCommand()));
    value.addActionListener(evt -> features.applyOp("greyscale", value.getActionCommand()));

    horizontal.addActionListener(evt -> features.applyOp(horizontal.getActionCommand(),
            horizontal.getActionCommand()));
    vertical.addActionListener(evt -> features.applyOp(vertical.getActionCommand(),
            vertical.getActionCommand()));
    brighten.addActionListener(evt -> features.applyOp(brighten.getActionCommand(),
            brighten.getActionCommand()));
    darken.addActionListener(evt -> features.applyOp(darken.getActionCommand(),
            darken.getActionCommand()));

    loadButton.addActionListener(evt -> features.loadImage());
    saveButton.addActionListener(evt -> features.saveImage());

    downscale.addActionListener(evt -> features.applyOp(downscale.getActionCommand(),
            downscale.getActionCommand()));

  }

  /**
   * Initializes the center panel.
   */
  private void setCenterPanel() {
    imageLabel = new JLabel();
    imageLabel.setIcon(new ImageIcon(fileOpenDisplay.getText()));
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);

    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Current image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));

    imagePanel.setMaximumSize(new Dimension(100, 100));
    imagePanel.add(imageScrollPane);

    mainPanel.add(imagePanel, BorderLayout.CENTER);
    add(mainPanel);
  }

  /**
   * Initializes the north panel.
   */
  private void setNorthPanel() {
    errorLabel = new JLabel();
    errorLabel.setText("Free Image Processor No Credit Card");
    errorLabel.setHorizontalAlignment(JLabel.CENTER);

    mainPanel.add(errorLabel, BorderLayout.NORTH);
    add(mainPanel);
  }

  /**
   * Initializes the south panel.
   */
  private void setSouthPanel() {
    JPanel fileOperationsPanel = new JPanel();
    fileOperationsPanel.setBorder(BorderFactory.createTitledBorder("File Operations"));
    fileOperationsPanel.setLayout(new FlowLayout());

    loadButton = new JButton("Load");
    saveButton = new JButton("Save");


    loadButton.setActionCommand("Open file");
    fileOpenDisplay = new JLabel("Image name will appear here");

    saveButton.setActionCommand("Save file");

    fileOperationsPanel.add(loadButton);
    fileOperationsPanel.add(saveButton);
    fileOperationsPanel.add(fileOpenDisplay);

    mainPanel.add(fileOperationsPanel, BorderLayout.SOUTH);
    add(mainPanel);
  }


  /**
   * Sets the text for the error label.
   *
   * @param text the given text
   */
  public void setErrorLabelText(String text) {
    errorLabel.setText(text);
  }

  /**
   * Sets the text for the file label.
   *
   * @param text the given text
   */
  public void setFileLabelText(String text) {
    fileOpenDisplay.setText(text);
  }

  /**
   * Sets the icon for the image.
   *
   * @param icon the given icon
   */
  public void setImageIcon(Icon icon) {
    imageLabel.setIcon(icon);
  }

  /**
   * Gets the string in the brighten/darken text field.
   *
   * @return lightNum string
   */
  public String getLightNum() {
    return lightNum.getText();
  }

  /**
   * Gets the string that's present in the width box for the downscale method.
   *
   * @return widthNum string
   */
  public String getWidthNum() {
    return widthNum.getText();
  }

  /**
   * Gets the string that's present in the height box for the downscale method.
   *
   * @return heightNum string
   */
  public String getHeightNum() {
    return heightNum.getText();
  }

  /**
   * Updates the histogram panels to reflect the RGB and intensity values of the currrently
   * displayed image.
   */
  public void updateHistogram() {
    JPanel[] histograms = new JPanel[]{redHistogram, greenHistogram,
        blueHistogram, intensityHistogram};
    for (JPanel histogram : histograms) {
      histogram.removeAll();
      histogram.updateUI();
    }

    int dim = Math.max(imageLabel.getIcon().getIconHeight(), imageLabel.getIcon().getIconWidth());

    for (int rgb = 0; rgb < 256; rgb++) {
      JPanel redCurr = new JPanel();
      JPanel greenCurr = new JPanel();
      JPanel blueCurr = new JPanel();
      JPanel intensityCurr = new JPanel();

      JPanel[] currArray = new JPanel[]{redCurr, greenCurr, blueCurr, intensityCurr};
      for (JPanel curr : currArray) {
        curr.setAlignmentY(BOTTOM_ALIGNMENT);
      }


      int[] countArray = imageRGBCounter(rgb);


      redCurr.setMinimumSize(new Dimension(1, countArray[0] / (dim / 4)));
      redCurr.setMaximumSize(redCurr.getMinimumSize());
      redCurr.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0, 100)));
      redHistogram.add(redCurr);

      greenCurr.setMinimumSize(new Dimension(1, countArray[1] / (dim / 4)));
      greenCurr.setMaximumSize(greenCurr.getMinimumSize());
      greenCurr.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 0, 100)));
      greenHistogram.add(greenCurr);

      blueCurr.setMinimumSize(new Dimension(1, countArray[2] / (dim / 4)));
      blueCurr.setMaximumSize(blueCurr.getMinimumSize());
      blueCurr.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255, 100)));
      blueHistogram.add(blueCurr);

      intensityCurr.setMinimumSize(new Dimension(1, countArray[3] / (dim / 4)));
      intensityCurr.setMaximumSize(intensityCurr.getMinimumSize());
      intensityCurr.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 100)));
      intensityHistogram.add(intensityCurr);
    }
  }


  /**
   * Initializes the east panel.
   */
  private void setEastPanel() {
    JPanel histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histograms"));
    histogramPanel.setLayout(new BoxLayout(histogramPanel, BoxLayout.Y_AXIS));

    redHistogram.setBorder(BorderFactory.createTitledBorder("Red"));
    greenHistogram.setBorder(BorderFactory.createTitledBorder("Green"));
    blueHistogram.setBorder(BorderFactory.createTitledBorder("Blue"));
    intensityHistogram.setBorder(BorderFactory.createTitledBorder("Intensity"));

    JPanel[] histograms = new JPanel[]{redHistogram, greenHistogram,
        blueHistogram, intensityHistogram};
    for (JPanel histogram : histograms) {
      histogram.setMaximumSize(new Dimension(255, 140));
      histogram.setPreferredSize(new Dimension(255, 120));
      histogram.setMinimumSize(new Dimension(255, 120));

      histogram.setLayout(new BoxLayout(histogram, BoxLayout.X_AXIS));
      histogramPanel.add(histogram);
    }

    JLabel xAxisLabel = new JLabel("X: Values - Y: Frequencies");
    xAxisLabel.setAlignmentX(CENTER_ALIGNMENT);
    histogramPanel.add(xAxisLabel);

    mainPanel.add(histogramPanel, BorderLayout.EAST);
    add(mainPanel);
  }

}


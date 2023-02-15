package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;

import controller.commands.Brighten;
import controller.commands.ColorTransformation;
import controller.commands.Darken;
import controller.commands.Downscale;
import controller.commands.Filter;
import controller.commands.HorizontalFlip;
import controller.commands.SetToComponent;
import controller.commands.VerticalFlip;
import model.ImageGrid;
import model.ImageProcessingModel;
import model.RGB;
import view.ImageProcessorFrame;

/**
 * Represents the operations for the controller for this program and manages how the
 * given models and view interact when user input is given.
 */
public class ImageProcessingControllerGUI implements
        ImageProcessingController, Features {

  private static Map<String, ImageProcessingModel> listOfImages =
          new HashMap<String, ImageProcessingModel>();

  private static Map<String, Integer> listOfAlpha =
          new HashMap<String, Integer>();

  private final ImageProcessorFrame view;

  private Function<Scanner, ImageOperationCommand> cmd;
  private String fileType;
  private Map<String, Function<Scanner, ImageOperationCommand>> knownCommands = new HashMap<>();
  private Map<String, Function<Scanner, FileOperations>> knownOperations = new HashMap<>();

  private FileNameExtensionFilter filter = new FileNameExtensionFilter(
          "PPM, JPG, PNG, and BMP Images", "ppm", "jpg", "png", "bmp");


  /**
   * Constructs the constructor for this program given the models with images the user has already
   * loaded, the view for this program, and user input.
   *
   * @param listOfModels the images the user has already loaded into the program.
   * @param view         the view for this program.
   * @throws IllegalArgumentException if any of the givens are null
   */
  public ImageProcessingControllerGUI(Map<String, ImageProcessingModel> listOfModels,
                                      ImageProcessorFrame view)
          throws IllegalArgumentException {
    if (listOfModels == null || view == null) {
      throw new IllegalArgumentException("models or view is null");
    }
    listOfImages = listOfModels;
    this.view = view;
    for (Map.Entry<String, ImageProcessingModel> mod : listOfImages.entrySet()) {
      listOfAlpha.put(mod.getKey(), 255);
    }


    knownOperations.put("ppm", s -> new PPMFileOperationsImpl());


    knownCommands.put("brighten", s -> new Brighten(Integer.parseInt(view.getLightNum())));
    knownCommands.put("darken", s -> new Darken(Integer.parseInt(view.getLightNum())));
    knownCommands.put("vertical-flip", s -> new VerticalFlip());
    knownCommands.put("horizontal-flip", s -> new HorizontalFlip());
    knownCommands.put("greyscale", s -> new SetToComponent(s.next()));
    knownCommands.put("color-transformation", s -> new ColorTransformation(s.next()));
    knownCommands.put("filter", s -> new Filter(s.next()));

    knownCommands.put("downscale", s -> new Downscale(Integer.parseInt(view.getWidthNum()),
            Integer.parseInt(view.getHeightNum())));

  }


  @Override
  public void runProgram() throws IllegalStateException {
    this.view.setOperationsButtonListener(this);
    this.view.setVisible(true);

  }


  private BufferedImage getImage() {

    int imageType;

    switch (fileType) {
      case "jpg":
        imageType = BufferedImage.TYPE_INT_RGB;
        break;
      case "bmp":
        imageType = BufferedImage.TYPE_3BYTE_BGR;
        break;
      default:
        imageType = BufferedImage.TYPE_INT_ARGB;
        break;
    }

    ImageGrid curr = (ImageGrid) listOfImages.get("current");

    BufferedImage img = new BufferedImage(curr.getImageWidth(),
            curr.getImageHeight(), imageType);
    RGB[][] g = curr.getGrid();
    int alpha = listOfAlpha.get("current");

    for (int i = 0; i < curr.getImageHeight(); i++) {
      for (int j = 0; j < curr.getImageWidth(); j++) {
        img.setRGB(j, i, new Color(g[i][j].getRed(),
                g[i][j].getGreen(), g[i][j].getBlue(), alpha).getRGB());
      }
    }

    return img;
  }

  @Override
  public void loadImage() {
    view.setErrorLabelText("Processing");

    cmd = knownCommands.getOrDefault("load", null);

    final JFileChooser fchooser = new JFileChooser(".");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(new ImageProcessorFrame());
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      view.setFileLabelText(f.getAbsolutePath());

      String fileName = f.getAbsolutePath();
      fileType = fileName.split("\\.")[fileName.split("\\.").length - 1];

      Function<Scanner, FileOperations> oF
              = knownOperations.getOrDefault(fileType, s -> new ImageFileOperationsImpl());
      try {
        oF.apply(new Scanner("")).load(fileName, "current");
        listOfImages = ImageProcessingControllerImpl.listOfImages;
        listOfAlpha = ImageProcessingControllerImpl.listOfAlpha;

        view.setImageIcon(new ImageIcon(this.getImage()));
        view.updateHistogram();
        view.setErrorLabelText("Image loaded successfully");

      } catch (FileNotFoundException fn) {
        view.setErrorLabelText("File not found! Try again.");
      } catch (NullPointerException n) {
        view.setErrorLabelText("Unacceptable file type. Only JPG, PNG, BMP, and PPM files " +
                "are supported. Try again.");
      }
    } else {
      view.setErrorLabelText("No new image loaded.");
    }
  }

  @Override
  public void saveImage() {
    view.setErrorLabelText("Processing");

    Function<Scanner, ImageOperationCommand> cmd
            = knownCommands.getOrDefault("save", null);

    final JFileChooser fchooser = new JFileChooser(".");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showSaveDialog(new ImageProcessorFrame());
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();

      String fileName = f.getAbsolutePath();
      fileType = fileName.split("\\.")[fileName.split("\\.").length - 1];

      Function<Scanner, FileOperations> oF
              = knownOperations.getOrDefault(fileType, s -> new ImageFileOperationsImpl());
      try {
        oF.apply(new Scanner("")).save(fileName, "current");
        view.setErrorLabelText("Image saved successfully");
      } catch (IllegalArgumentException a) {
        view.setErrorLabelText("Unacceptable file type. Only JPG, PNG, BMP, and PPM files " +
                "are supported. Try again.");
      }
    } else {
      view.setErrorLabelText("Did not save image.");
    }
  }

  @Override
  public void applyOp(String method, String action) {
    cmd = knownCommands.getOrDefault(method, null);

    view.setErrorLabelText("Processing");

    try {
      ImageProcessingModel model = listOfImages.get("current");
      ImageOperationCommand command = cmd.apply(new Scanner(action));
      String next = "current";
      listOfImages.put(next, command.executeCommand(model));
      listOfAlpha.put(next, listOfAlpha.get("current"));


      view.setImageIcon(new ImageIcon(this.getImage()));
      view.updateHistogram();
      view.setErrorLabelText("Operation applied successfully");
    } catch (IllegalArgumentException ia) {
      view.setErrorLabelText("Command failed! Try again.");
    } catch (NullPointerException n) {
      view.setErrorLabelText("Image does not exist! Try again.");
    }
  }

  /**
   * Counts the number of reds, greens, blues, and intensities for the given RGB value.
   *
   * @param currentRGBValue the given RGB value
   * @return an array of the number of reds, greens, blues, and intensities
   */
  public static int[] imageRGBCounter(int currentRGBValue) {
    RGB[][] currentImage = listOfImages.get("current").getGrid();
    int redCount = 0;
    int greenCount = 0;
    int blueCount = 0;
    int intensityCount = 0;

    if (currentImage == null) {
      return new int[]{0, 0, 0, 0};
    }

    for (int h = 0; h < currentImage.length; h++) {
      for (int w = 0; w < currentImage[0].length; w++) {

        if (currentImage[h][w].getRed() == currentRGBValue) {
          redCount++;
        }
        if (currentImage[h][w].getGreen() == currentRGBValue) {
          greenCount++;
        }
        if (currentImage[h][w].getBlue() == currentRGBValue) {
          blueCount++;
        }
        if ((currentImage[h][w].getRed() + currentImage[h][w].getGreen()
                + currentImage[h][w].getBlue()) / 3 == currentRGBValue) {
          intensityCount++;
        }

      }
    }
    return new int[]{redCount, greenCount, blueCount, intensityCount};
  }
}

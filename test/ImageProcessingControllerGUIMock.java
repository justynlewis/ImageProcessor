import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;
import controller.FileOperations;
import controller.ImageOperationCommand;
import controller.ImageProcessingController;
import controller.PPMFileOperationsImpl;
import controller.commands.Brighten;
import controller.commands.ColorTransformation;
import controller.commands.Darken;
import controller.commands.Downscale;
import controller.commands.Filter;
import controller.commands.HorizontalFlip;
import controller.commands.SetToComponent;
import controller.commands.VerticalFlip;
import model.ImageProcessingModel;
import view.ImageProcessorFrame;

/**
 * Used to test that operations are properly conducted in the ImageProcessingControllerGUI.
 */
public class ImageProcessingControllerGUIMock implements
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
  private ImageOperationCommand command;

  private FileNameExtensionFilter filter = new FileNameExtensionFilter(
          "PPM, JPG, PNG, and BMP Images", "ppm", "jpg", "png", "bmp");

  private StringBuilder log;

  /**
   * Constructs the constructor for this program given the models with images the user has already
   * loaded, the view for this program, and a log to track operations.
   *
   * @param listOfModels the images the user has already loaded into the program.
   * @param view         the view for this program.
   * @param log the string builder tracking operations done.
   * @throws IllegalArgumentException if any of the givens are null
   */
  public ImageProcessingControllerGUIMock(Map<String, ImageProcessingModel> listOfModels,
                                      ImageProcessorFrame view, StringBuilder log)
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

    this.log = log;

  }


  @Override
  public void runProgram() throws IllegalStateException {
    this.view.setOperationsButtonListener(this);

  }


  private BufferedImage getImage() {
    log.append("refreshed image");
    return new BufferedImage(0,0,2);
  }

  @Override
  public void loadImage() {
    log.append("loaded image");
  }

  @Override
  public void saveImage() {
    log.append("saved image");
  }

  @Override
  public void applyOp(String method, String action) {
    log.append("operation applied");
  }
}

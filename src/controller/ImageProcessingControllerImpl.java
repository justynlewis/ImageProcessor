package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import controller.commands.Brighten;
import controller.commands.ColorTransformation;
import controller.commands.Darken;
import controller.commands.Downscale;
import controller.commands.Filter;
import controller.commands.HorizontalFlip;
import controller.commands.SetToComponent;
import controller.commands.VerticalFlip;
import model.ImageProcessingModel;
import view.ImageProcessingView;

/**
 * Represents the operations for the controller for this program and manages how the
 * given models and view interact when user input is given.
 */
public class ImageProcessingControllerImpl implements
        ImageProcessingController {

  //package private
  static Map<String, ImageProcessingModel> listOfImages =
          new HashMap<String, ImageProcessingModel>();

  //package private
  static Map<String, Integer> listOfAlpha =
          new HashMap<String, Integer>();

  private final ImageProcessingView view;
  private final Readable input;


  /**
   * Constructs the constructor for this program given the models with images the user has already
   * loaded, the view for this program, and user input.
   *
   * @param listOfModels the images the user has already loaded into the program.
   * @param view         the view for this program.
   * @param input        the user input to use this program.
   * @throws IllegalArgumentException if any of the givens are null
   */
  public ImageProcessingControllerImpl(Map<String, ImageProcessingModel> listOfModels,
                                       ImageProcessingView view, Readable input)
          throws IllegalArgumentException {
    if (listOfModels == null || view == null || input == null) {
      throw new IllegalArgumentException("models, view, or readable is null");
    }
    listOfImages = listOfModels;
    this.view = view;
    this.input = input;
    for (Map.Entry<String, ImageProcessingModel> mod : listOfImages.entrySet()) {
      listOfAlpha.put(mod.getKey(), 255);
    }
  }


  @Override
  public void runProgram() throws IllegalStateException {
    Scanner scanner = new Scanner(this.input);
    boolean quit = false;

    Map<String, Function<Scanner, ImageOperationCommand>> knownCommands = new HashMap<>();
    knownCommands = new HashMap<>();

    knownCommands.put("brighten", s -> new Brighten(s.nextInt()));
    knownCommands.put("darken", s -> new Darken(s.nextInt()));
    knownCommands.put("vertical-flip", s -> new VerticalFlip());
    knownCommands.put("horizontal-flip", s -> new HorizontalFlip());
    knownCommands.put("greyscale", s -> new SetToComponent(s.next()));
    knownCommands.put("color-transformation", s -> new ColorTransformation(s.next()));
    knownCommands.put("filter", s -> new Filter(s.next()));
    knownCommands.put("downscale", s -> new Downscale(s.nextInt(), s.nextInt()));


    Map<String, Function<Scanner, FileOperations>> knownOperations = new HashMap<>();
    knownOperations = new HashMap<>();

    knownOperations.put("ppm", s -> new PPMFileOperationsImpl());


    this.printMenu();

    while (!quit) {
      write("Type instruction: " + System.lineSeparator());
      ImageOperationCommand command;
      if (!scanner.hasNext()) {
        throw new IllegalStateException();
      }
      String userInput = scanner.next();

      Function<Scanner, ImageOperationCommand> cmd
              = knownCommands.getOrDefault(userInput, null);
      if (userInput.equals("q")) {
        write("Program ended");
        return;
      } else if (userInput.equals("load")) {
        String fileName = scanner.next();
        String fileType = fileName.split("\\.")[fileName.split("\\.").length - 1];

        Function<Scanner, FileOperations> oF
                = knownOperations.getOrDefault(fileType, s -> new ImageFileOperationsImpl());


        try {
          oF.apply(scanner).load(fileName, scanner.next());
        } catch (FileNotFoundException e) {
          write("File not found! Try again." + System.lineSeparator());
        } catch (NullPointerException n) {
          write("No such file type" + System.lineSeparator());
        }
      } else if (userInput.equals("save")) {
        String fileName = scanner.next();
        String fileType = fileName.split("\\.")[fileName.split("\\.").length - 1];

        Function<Scanner, FileOperations> oF
                = knownOperations.getOrDefault(fileType, s -> new ImageFileOperationsImpl());
        try {
          oF.apply(scanner).save(fileName, scanner.next());
        } catch (IllegalArgumentException e) {
          write("No such file type" + System.lineSeparator());
        } catch (NullPointerException e) {
          write("Image does not exist" + System.lineSeparator());
        }
      } else {
        if (cmd == null) {
          write("Command does not exist! Try again." + System.lineSeparator());
        } else {
          try {
            String old = scanner.next();
            ImageProcessingModel model = listOfImages.get(old);
            command = cmd.apply(scanner);
            String next = scanner.next();


            listOfImages.put(next, command.executeCommand(model));
            listOfAlpha.put(next, listOfAlpha.get(old));
          } catch (IllegalArgumentException e) {
            write("Command failed! Try again." + System.lineSeparator());
          } catch (NullPointerException n) {
            write("Image does not exist! Try again." + System.lineSeparator());
          }
        }
      }
    }

  }

  /**
   * Renders the given message in this program's view and throws
   * an error if there are any problems in the process.
   *
   * @param message the given message to render.
   * @throws IllegalStateException if there is an IOException thrown while rendering.
   */
  private void write(String message) throws IllegalStateException {
    try {
      view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  /**
   * Prints the menu of instructions the user can call to interact with this program.
   */
  private void printMenu() {
    write("Supported instructions are: " + System.lineSeparator());
    write("LOAD image-path image-name " +
            "(load the file from the given path and refer to it using the given name.)"
            + System.lineSeparator());
    write("SAVE new-file-name image-name " +
            "(save the image with the given name to the specified path which should include " +
            "the name of the file.)"
            + System.lineSeparator());
    write("BRIGHTEN image-name increment-value dest-image-name " +
            "(brightens the image of the given name by the increment value given and refers to " +
            "this" +
            " brighter image by the given destination name.)" + System.lineSeparator());
    write("DARKEN image-name reduction-value dest-image-name " +
            "(darkens the image of the given name by the reduction value given and refers to " +
            "this darker image by the given destination name.)" + System.lineSeparator());
    write("VERTICAL-FLIP image-name dest-image-name " +
            "(flips the image with the given name vertically and refers to it by the given " +
            "destination name.)" +
            System.lineSeparator());
    write("HORIZONTAL-FLIP image-name dest-image-name " +
            "(flips the image with the given name horizontally and refers to it by the given " +
            "destination name.)" +
            System.lineSeparator());
    write("GREYSCALE image-name color-component/channel-component dest-image-name " +
            "(creates a greyscale image with the color-component or the channel-component" +
            "of the image with the given name, " +
            "and refers to it by the given destination name.)" +
            System.lineSeparator());
    write("COLOR-TRANSFORMATION image-name transformation-type dest-image-name " +
            "(creates a new image with the color transformation of the type given " +
            "of the image with the given name, " +
            "and refers to it by the given destination name.)" +
            System.lineSeparator());
    write("FILTER image-name filter-type dest-image-name " +
            "(creates a new image with the given filter type applied " +
            "to the image with the given name, " +
            "and refers to it by the given destination name.)" +
            System.lineSeparator());
    write("DOWNSCALE image-name width-percentage height-percentage dest-image-name " +
            "(creates a new image that has been downscaled by the width " +
            "and height percentage to the image with the given name, " +
            "and refers to it by the given destination name.)" +
            System.lineSeparator());

  }
}

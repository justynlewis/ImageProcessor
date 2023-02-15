import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import controller.ImageProcessingController;
import controller.ImageProcessingControllerGUI;
import controller.ImageProcessingControllerImpl;
import model.ImageProcessingModel;
import view.ImageProcessingView;
import view.ImageProcessingViewImpl;
import view.ImageProcessorFrame;

/**
 * Contains the main class of the program to allow users to input and modify
 * files as they choose.
 */
public final class ImageProcessor {
  /**
   * Takes inputs through the command line and runs the program in the desired format (script,
   * text, GUI).
   *
   * @param args the user input when running the program.
   */
  public static void main(String[] args) {
    Readable input;
    Appendable output;
    Map<String, ImageProcessingModel> listOfModels = new HashMap<String, ImageProcessingModel>();

    if (args.length == 0) {
      ImageProcessorFrame frameView = new ImageProcessorFrame();
      ImageProcessingControllerGUI guiController = new ImageProcessingControllerGUI(listOfModels,
              frameView);
      guiController.runProgram();
    }


    if (args[0].equals("-file")) {
      try {
        input = new InputStreamReader(new FileInputStream(args[1]));
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    } else if (args[0].equals("-text")) {
      input = new InputStreamReader(System.in);
    } else {
      throw new IllegalArgumentException("Command does not exist");
    }

    output = System.out;
    ImageProcessingView view = new ImageProcessingViewImpl(output);
    ImageProcessingController controller = new ImageProcessingControllerImpl(listOfModels, view,
            input);
    controller.runProgram();
  }
}


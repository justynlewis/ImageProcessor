package controller;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import model.ImageGrid;
import model.RGB;

/**
 * Represents the operations to convert between a PPM file and image.
 */
public class PPMFileOperationsImpl implements FileOperations {

  /**
   * Translates the given PPM file to an image that the user can refer to by the given name.
   *
   * @param fileName  the PPM file to translate to an image.
   * @param imageName the name the user can use to refer to the produced image.
   * @throws FileNotFoundException if the given file doesn't exist
   */
  public void load(String fileName, String imageName) throws FileNotFoundException {

    RGB[][] pixelGrid;
    int maxValue;
    int imageWidth;
    int imageHeight;

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(fileName));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File " + fileName + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token = sc.next();
    imageWidth = sc.nextInt();
    imageHeight = sc.nextInt();
    maxValue = sc.nextInt();
    pixelGrid = new RGB[imageHeight][imageWidth];

    for (int i = 0; i < imageHeight; i++) {
      for (int j = 0; j < imageWidth; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixelGrid[i][j] = new RGB(r, g, b);
      }
    }

    ImageProcessingControllerImpl.listOfAlpha.put(imageName, 255);
    ImageProcessingControllerImpl.listOfImages.put(imageName, new ImageGrid(pixelGrid));
  }

  /**
   * Translates an image referred to by a given name to the PPM file provided.
   *
   * @param fileName  the name of the PPM file where the given image will be saved.
   * @param imageName the name the user uses to refer to the image they want to save.
   * @throws RuntimeException if transmission to file fails
   */
  public void save(String fileName, String imageName) throws RuntimeException {

    //create the file
    try {
      BufferedWriter file = new BufferedWriter(new FileWriter(fileName));
      StringBuilder txt = new StringBuilder();
      //ppm file token
      txt.append("P3\n");

      ImageGrid curr = (ImageGrid) ImageProcessingControllerImpl.listOfImages.get(imageName);
      //the file values
      txt.append("" + curr.getImageWidth());
      txt.append(" " + curr.getImageHeight() + "\n");
      txt.append("" + curr.getMaxValue());

      RGB[][] g = curr.getGrid();

      for (int i = 0; i < curr.getImageHeight(); i++) {
        for (int j = 0; j < curr.getImageWidth(); j++) {
          txt.append("\n" + g[i][j].getRed());
          txt.append("\n" + g[i][j].getGreen());
          txt.append("\n" + g[i][j].getBlue());
        }
      }
      txt.append("\n");
      file.write(txt.toString());
      file.close();
    } catch (IOException e) {
      throw new RuntimeException("Error occurred in writing the file");
    }
  }

}

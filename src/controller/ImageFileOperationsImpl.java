package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.awt.Color;
import javax.imageio.ImageIO;

import model.ImageGrid;
import model.RGB;

/**
 * Represents the operations to convert between a JPG, BMP, and PNG file and image.
 */
public class ImageFileOperationsImpl implements FileOperations {
  /**
   * Translates the given JPG, BMP, or PNG file to an image that the
   * user can refer to by the given name.
   *
   * @param fileName  the JPG, BMP, or PNG file to translate to an image.
   * @param imageName the name the user can use to refer to the produced image.
   * @throws FileNotFoundException if the given file doesn't exist or if there is an error when
   *                               reading the image file.
   */
  public void load(String fileName, String imageName) throws FileNotFoundException {

    RGB[][] pixelGrid;
    int imageWidth;
    int imageHeight;

    try {
      BufferedImage img = ImageIO.read(new FileInputStream(fileName));

      imageWidth = img.getWidth();
      imageHeight = img.getHeight();
      pixelGrid = new RGB[imageHeight][imageWidth];

      for (int i = 0; i < imageHeight; i++) {
        for (int j = 0; j < imageWidth; j++) {
          int a = (img.getRGB(j, i) >> 24) & 0xff;
          int r = new Color(img.getRGB(j, i)).getRed();
          int g = new Color(img.getRGB(j, i)).getGreen();
          int b = new Color(img.getRGB(j, i)).getBlue();
          pixelGrid[i][j] = new RGB(r, g, b);
          ImageProcessingControllerImpl.listOfAlpha.put(imageName, a);
        }
      }

    } catch (IOException e) {
      throw new FileNotFoundException("File " + fileName + " not found!");
    }

    ImageProcessingControllerImpl.listOfImages.put(imageName, new ImageGrid(pixelGrid));
  }

  /**
   * Translates an image referred to by a given name to the JPG, BMP, or PNG file provided.
   *
   * @param fileName  the name of the JGP, BMP, or PNG file where the given image will be saved.
   * @param imageName the name the user uses to refer to the image they want to save.
   * @throws RuntimeException if transmission to file fails
   *                          or the file type is not either JPG, BMP, or PNG.
   */
  @Override
  public void save(String fileName, String imageName) throws RuntimeException {
    try {
      String fileType = fileName.split("\\.")[fileName.split("\\.").length - 1];

      int imageType;

      switch (fileType) {
        case "png":
          imageType = BufferedImage.TYPE_INT_ARGB;
          break;
        case "jpg":
          imageType = BufferedImage.TYPE_INT_RGB;
          break;
        case "bmp":
          imageType = BufferedImage.TYPE_3BYTE_BGR;
          break;
        default:
          imageType = 0;
      }

      ImageGrid curr = (ImageGrid) ImageProcessingControllerImpl.listOfImages.get(imageName);

      BufferedImage img = new BufferedImage(curr.getImageWidth(),
              curr.getImageHeight(), imageType);
      RGB[][] g = curr.getGrid();
      int alpha = ImageProcessingControllerImpl.listOfAlpha.get(imageName);

      if (fileType.equals("jpg")) {
        for (int i = 0; i < curr.getImageHeight(); i++) {
          for (int j = 0; j < curr.getImageWidth(); j++) {
            img.setRGB(j, i, new Color(g[i][j].getRed(),
                    g[i][j].getGreen(), g[i][j].getBlue()).getRGB());
          }
        }
      } else if (fileType.equals("png") || fileType.equals("bmp")) {
        for (int i = 0; i < curr.getImageHeight(); i++) {
          for (int j = 0; j < curr.getImageWidth(); j++) {
            img.setRGB(j, i, new Color(g[i][j].getRed(),
                    g[i][j].getGreen(), g[i][j].getBlue(), alpha).getRGB());
          }
        }
      }

      ImageIO.write(img, fileType, new File(fileName));

    } catch (IOException e) {
      throw new RuntimeException("Error occurred in writing the file" + "\n" + e);
    }

  }

}

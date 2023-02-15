package controller;

import java.io.FileNotFoundException;

/**
 * Represents the available operations to translate between a file and an image.
 */
public interface FileOperations {
  /**
   * Translates a given file to an image that the user can refer to by the given name.
   *
   * @param fileName  the file to translate to an image.
   * @param imageName the name the user can use to refer to the produced image.
   * @throws FileNotFoundException if the given file does not exist.
   */
  void load(String fileName, String imageName) throws FileNotFoundException;

  /**
   * Translates an image referred to by a given name to the file provided.
   *
   * @param fileName  the name of the file where the given image will be saved.
   * @param imageName the name the user uses to refer to the image they want to save.
   * @throws RuntimeException if there are any errors while translating the image
   *                          to the file format.
   */
  void save(String fileName, String imageName) throws RuntimeException;
}

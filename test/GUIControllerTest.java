import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


import controller.ImageProcessingControllerGUI;
import model.ImageGrid;
import model.ImageProcessingModel;
import model.RGB;
import view.ImageProcessorFrame;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the GUI Controller.
 */
public class GUIControllerTest {
  private Map<String, ImageProcessingModel> models;
  private ImageProcessorFrame frameView;
  ImageProcessingControllerGUI guiController;

  private final RGB red = new RGB(255, 0, 0);
  private final RGB green = new RGB(0, 255, 0);
  private final RGB blue = new RGB(0, 0, 255);
  private final RGB orange = new RGB(255, 150, 0);
  private final RGB purple = new RGB(150, 0, 255);
  private final RGB white = new RGB(255, 255, 255);
  private final RGB black = new RGB(0, 0, 0);
  private final RGB grey = new RGB(150, 150, 150);

  private final RGB redLuma = new RGB(54, 54, 54);
  private final RGB greenLuma = new RGB(182, 182, 182);
  private final RGB blueLuma = new RGB(18, 18, 18);
  private final RGB orangeLuma = new RGB(161, 161, 161);
  private final RGB purpleLuma = new RGB(50, 50, 50);
  private RGB[][] testGrid;
  private RGB[][] allLumaGrid;

  @Before
  public void initializeValues() {
    testGrid = new RGB[][]{{red, red, red, red}, {green, blue, blue, green},
        {blue, green, green, blue}, {orange, orange, purple, purple}};

    allLumaGrid = new RGB[][]
        {{redLuma, redLuma, redLuma, redLuma}, {greenLuma, blueLuma, blueLuma, greenLuma},
                {blueLuma, greenLuma, greenLuma, blueLuma},
                {orangeLuma, orangeLuma, purpleLuma, purpleLuma}};

    models = new HashMap<>();
    models.put("current", new ImageGrid(testGrid));
    frameView = new ImageProcessorFrame();
    guiController = new ImageProcessingControllerGUI(models, frameView);
  }

  @Test
  public void loadImageTest() {
    StringBuilder log = new StringBuilder();
    ImageProcessingControllerGUIMock guiController =
            new ImageProcessingControllerGUIMock(models, frameView, log);
    guiController.loadImage();
    assertEquals("loaded image", log.toString());
  }

  @Test
  public void saveImageTest() {
    StringBuilder log = new StringBuilder();
    ImageProcessingControllerGUIMock guiController =
            new ImageProcessingControllerGUIMock(models, frameView, log);
    guiController.saveImage();
    assertEquals("saved image", log.toString());
  }

  @Test
  public void guiBrightenTest() {
    StringBuilder log = new StringBuilder();
    ImageProcessingControllerGUIMock guiController =
            new ImageProcessingControllerGUIMock(models, frameView, log);
    guiController.applyOp("brighten", "brighten");
    assertEquals("operation applied", log.toString());
  }

  @Test
  public void guiDarkenTest() {
    StringBuilder log = new StringBuilder();
    ImageProcessingControllerGUIMock guiController =
            new ImageProcessingControllerGUIMock(models, frameView, log);
    guiController.applyOp("darken", "darken");
    assertEquals("operation applied", log.toString());
  }

  @Test
  public void guiVerticalFlipTest() {
    RGB[][] verticallyFlippedGrid = new RGB[][]{{orange, orange, purple, purple},
        {blue, green, green, blue},
        {green, blue, blue, green}, {red, red, red, red}};

    ImageProcessingControllerGUI guiController =
            new ImageProcessingControllerGUI(models, frameView);

    guiController.applyOp("vertical-flip", "vertical-flip");
    assertArrayEquals(verticallyFlippedGrid, models.get("current").getGrid());
  }

  @Test
  public void guiHorizontalFlipTest() {
    RGB[][] horizontallyFlippedGrid = new RGB[][]{{red, red, red, red}, {green, blue, blue, green},
        {blue, green, green, blue}, {purple, purple, orange, orange}};

    ImageProcessingControllerGUI guiController =
            new ImageProcessingControllerGUI(models, frameView);

    guiController.applyOp("horizontal-flip", "horizontal-flip");
    assertArrayEquals(horizontallyFlippedGrid, models.get("current").getGrid());
  }

  @Test
  public void guiSetComponentRedTest() {
    RGB[][] allRedGrid = new RGB[][]{{white, white, white, white}, {black, black, black, black},
        {black, black, black, black}, {white, white, grey, grey}};

    ImageProcessingControllerGUI guiController =
            new ImageProcessingControllerGUI(models, frameView);

    guiController.applyOp("greyscale", "red-component");
    assertArrayEquals(allRedGrid, models.get("current").getGrid());
  }

  @Test
  public void guiSetComponentGreenTest() {
    RGB[][] allGreenGrid = new RGB[][]{{black, black, black, black}, {white, black, black, white},
        {black, white, white, black}, {grey, grey, black, black}};

    ImageProcessingControllerGUI guiController =
            new ImageProcessingControllerGUI(models, frameView);

    guiController.applyOp("greyscale", "green-component");
    assertArrayEquals(allGreenGrid, models.get("current").getGrid());
  }

  @Test
  public void guiSetComponentBlueTest() {
    RGB[][] allBlueGrid = new RGB[][]{{black, black, black, black}, {black, white, white, black},
        {white, black, black, white}, {black, black, white, white}};

    ImageProcessingControllerGUI guiController =
            new ImageProcessingControllerGUI(models, frameView);

    guiController.applyOp("greyscale", "blue-component");
    assertArrayEquals(allBlueGrid, models.get("current").getGrid());
  }

  @Test
  public void guiSetComponentValueTest() {
    RGB[][] allValueGrid = new RGB[][]{{white, white, white, white}, {white, white, white, white},
        {white, white, white, white}, {white, white, white, white}};

    ImageProcessingControllerGUI guiController =
            new ImageProcessingControllerGUI(models, frameView);

    guiController.applyOp("greyscale", "value-component");
    assertArrayEquals(allValueGrid, models.get("current").getGrid());
  }

  @Test
  public void guiSetComponentLumaTest() {
    ImageProcessingControllerGUI guiController =
            new ImageProcessingControllerGUI(models, frameView);

    guiController.applyOp("greyscale", "luma-component");
    assertArrayEquals(allLumaGrid, models.get("current").getGrid());
  }

  @Test
  public void guiSetComponentIntensityTest() {
    RGB avgSingle = new RGB(85, 85, 85);
    RGB avgOP = new RGB(135, 135, 135);
    RGB[][] allIntensityGrid = new RGB[][]{{avgSingle, avgSingle, avgSingle, avgSingle},
        {avgSingle, avgSingle, avgSingle, avgSingle},
        {avgSingle, avgSingle, avgSingle, avgSingle}, {avgOP, avgOP, avgOP, avgOP}};

    ImageProcessingControllerGUI guiController =
            new ImageProcessingControllerGUI(models, frameView);

    guiController.applyOp("greyscale", "intensity-component");
    assertArrayEquals(allIntensityGrid, models.get("current").getGrid());
  }

  @Test
  public void guiColorTransformationSepia() {
    RGB redSepia = new RGB(100, 88, 69);
    RGB greenSepia = new RGB(196, 174, 136);
    RGB blueSepia = new RGB(48, 42, 33);
    RGB orangeSepia = new RGB(215, 191, 149);
    RGB purpleSepia = new RGB(107, 95, 74);
    RGB[][] allSepiaGrid
            = new RGB[][]
            {{redSepia, redSepia, redSepia, redSepia},
                    {greenSepia, blueSepia, blueSepia, greenSepia},
                    {blueSepia, greenSepia, greenSepia, blueSepia},
                    {orangeSepia, orangeSepia, purpleSepia, purpleSepia}};

    ImageProcessingControllerGUI guiController =
            new ImageProcessingControllerGUI(models, frameView);

    guiController.applyOp("color-transformation", "sepia");
    assertArrayEquals(allSepiaGrid, models.get("current").getGrid());
  }

  @Test
  public void guiColorTransformationGrey() {
    ImageProcessingControllerGUI guiController =
            new ImageProcessingControllerGUI(models, frameView);

    guiController.applyOp("color-transformation", "greyscale");
    assertArrayEquals(allLumaGrid, models.get("current").getGrid());
  }

  @Test
  public void guiFilterBlur() {
    RGB[][] blurredGridFourByFour = new RGB[][]{
        new RGB[]{new RGB(((int) (255 * (1 / 4d)) + (int) (255 * (1 / 8d))),
                    ((int) (255 / 8d)),
                    ((int) (255 * 1 / 16d))),
                  new RGB(((int) (255 * (1 / 8d))
                            + (int) (255 * (1 / 4d)) + (int) (255 * 1 / 8d)),
                            ((int) (255 * (1 / 16d))),
                            ((int) (255 * (1 / 8d)) + (int) (255 * (1 / 16d)))),
                  new RGB(((int) (255 * (1 / 8d)) + (int) (255 * (1 / 4d))
                            + (int) (255 * 1 / 8d)),
                            ((int) (255 * (1 / 16d))),
                            ((int) (255 * (1 / 8d)) + (int) (255 * (1 / 16d)))),
                  new RGB(((int) (255 * (1 / 4d)) + (int) (255 * (1 / 8d))),
                            ((int) (255 * (1 / 8d))),
                            ((int) (255 * (1 / 16d))))},

            {new RGB(((int) (255 * (1 / 8d)) + (int) (255 * (1 / 16d))),
                    ((int) (255 * (1 / 4d)) + (int) (255 * (1 / 16d))),
                    ((int) (255 * (1 / 8d)) + (int) (255 * (1 / 8d)))),
                new RGB(((int) (255 * (1 / 8d)) + (int) (255 * (1 / 16d))
                            + (int) (255 * (1 / 16d))),
                            ((int) (255 * (1 / 8d)) + (int) (255 * (1 / 8d))
                                    + (int) (255 * (1 / 16d))),
                            ((int) (255 * (1 / 16d)) + (int) (255 * (1 / 8d))
                                    + (int) (255 * (1 / 4d)))),
                new RGB(((int) (255 * (1 / 8d)) + (int) (255 * (1 / 16d))
                            + (int) (255 * (1 / 16d))),
                            ((int) (255 * (1 / 16d)) + (int) (255 * (1 / 8d))
                                    + (int) (255 * (1 / 8d))),
                            ((int) (255 * (1 / 16d)) + (int) (255 * (1 / 8d))
                                    + (int) (255 * (1 / 4d)))),
                new RGB(((int) (255 * (1 / 8d)) + (int) (255 * (1 / 16d))),
                            ((int) (255 * (1 / 4d)) + (int) (255 * (1 / 16d))),
                            ((int) (255 * (1 / 8d)) + (int) (255 * (1 / 8d))))},

            {new RGB(((int) (255 * (1 / 16d)) + (int) (255 * (1 / 8d))),
                    ((int) (255 * (1 / 8d)) + (int) (255 * (1 / 8d)) + (int) (150 * (1 / 8d))
                            + (int) (150 * (1 / 16d))),
                    ((int) (255 * (1 / 4d)) + (int) (255 * (1 / 16d)))),
                new RGB(((int) (255 * (1 / 8d)) + (int) (255 * (1 / 16d))
                            + (int) (150 * (1 / 16d))),
                            ((int) (255 * (1 / 16d)) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (1 / 8d)) + (int) (150 * (1 / 16d))
                                    + (int) (150 * (1 / 8d))),
                            ((int) (255 * (1 / 8d)) + (int) (255 * (1 / 8d))
                                    + (int) (255 * (1 / 16d)) + (int) (255 * (1 / 16d)))),
                new RGB(((int) (255 * (1 / 16d)) + (int) (150 * (1 / 8d))
                            + (int) (150 * (1 / 16d))),
                            ((int) (255 * (1 / 16d)) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (1 / 8d)) + (int) (150 * (1 / 16d))),
                            ((int) (255 * (1 / 16d)) + (int) (255 * (1 / 8d))
                                    + (int) (255 * (1 / 8d)) + (int) (255 * (1 / 8d))
                                    + (int) (255 * (1 / 16d)))),
                new RGB(((int) (150 * (1 / 16d)) + (int) (150 * (1 / 8d))),
                            ((int) (255 * (1 / 8d)) + (int) (255 * (1 / 8d))),
                            ((int) (255 * (1 / 4d)) + (int) (255 * (1 / 16d))
                                    + (int) (255 * (1 / 8d)) + (int) (255 * (1 / 16d))))},

            {new RGB(((int) (255 * (1 / 4d)) + (int) (255 * (1 / 8d))),
                    ((int) (255 * (1 / 16d)) + (int) (150 * (1 / 4d)) + (int) (150 * (1 / 8d))),
                    ((int) (255 * (1 / 8d)))),
                new RGB(((int) (255 * (1 / 4d)) + (int) (255 * (1 / 8d))
                            + (int) (150 * (1 / 8d))),
                            ((int) (255 * (1 / 16d)) + (int) (255 * (1 / 8d))
                                    + (int) (150 * (1 / 4d)) + (int) (150 * (1 / 8d))),
                            ((int) (255 * (1 / 16d)) + (int) (255 * (1 / 8d)))),
                new RGB(((int) (255 * (1 / 8d)) + (int) (150 * (1 / 4d))
                            + (int) (150 * (1 / 8d))),
                            ((int) (255 * (1 / 16d)) + (int) (255 * (1 / 8d))
                                    + (int) (150 * (1 / 8d))),
                            ((int) (255 * (1 / 16d)) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (1 / 8d)))),
                new RGB(((int) (150 * (1 / 4d)) + (int) (150 * (1 / 8d))),
                            ((int) (255 * (1 / 16d))),
                            ((int) (255 * (1 / 8d)) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (1 / 8d))))}};

    ImageProcessingControllerGUI guiController =
            new ImageProcessingControllerGUI(models, frameView);

    guiController.applyOp("filter", "blur");
    assertArrayEquals(blurredGridFourByFour, models.get("current").getGrid());

  }

  @Test
  public void guiFilterSharpen() {
    RGB[][] sharpenedGridFourByFour = new RGB[][]{
            {new RGB(((int) (255 * (1d)) + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d))),
                    ((int) (255 * (1 / 4d))) + (int) (255 * (-1 / 8d))
                            + (int) (255 * (-1 / 8d)),
                    ((int) (255 * (1 / 4d))) + (int) (255 * (-1 / 8d))
                            + (int) (255 * (-1 / 8d))),
                new RGB(((int) (255 * (1d)) + (int) (255 * (1 / 4d))
                            + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d))),
                            ((int) (255 * (1 / 4d))) + (int) (255 * (-1 / 8d))
                                    + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d)),
                            ((int) (255 * (1 / 4d))) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))),
                new RGB(((int) (255 * (1d)) + (int) (255 * (1 / 4d))
                            + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d))),
                            ((int) (255 * (1 / 4d))) + (int) (255 * (-1 / 8d))
                                    + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d)),
                            ((int) (255 * (1 / 4d))) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))),
                new RGB(((int) (255 * (1d)) + (int) (255 * (1 / 4d))
                            + (int) (255 * (-1 / 8d))),
                            ((int) (255 * (1 / 4d))) + (int) (255 * (-1 / 8d))
                                    + (int) (255 * (-1 / 8d)),
                            ((int) (255 * (1 / 4d))) + (int) (255 * (-1 / 8d))
                                    + (int) (255 * (-1 / 8d)))},

            {new RGB(((int) (255 * (1 / 4d)) + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d))
                    + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d)) + (int) (150 * (-1 / 8d))),
                    ((int) (255 * (1d))) + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d))
                            + (int) (150 * (-1 / 8d)) + (int) (150 * (-1 / 8d)),
                    ((int) (255 * (1 / 4d))) + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d))
                            + (int) (255 * (-1 / 8d))),
                new RGB(((int) (255 * (1 / 4d)) + (int) (255 * (1 / 4d))
                            + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d))
                            + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))
                            + (int) (150 * (-1 / 8d)) + (int) (150 * (-1 / 8d))),
                            ((int) (255 * (1 / 4d))) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d))
                                    + (int) (150 * (-1 / 8d)) + (int) (150 * (-1 / 8d)),
                            ((int) (255 * (1d))) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d))
                                    + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))),
                new RGB(((int) (255 * (1 / 4d)) + (int) (255 * (1 / 4d))
                            + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d))
                            + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))
                            + (int) (150 * (-1 / 8d)) + (int) (150 * (-1 / 8d))),
                            ((int) (255 * (1 / 4d))) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d))
                                    + (int) (150 * (-1 / 8d)) + (int) (150 * (-1 / 8d)),
                            ((int) (255 * (1d))) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d))
                                    + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))),
                new RGB(((int) (255 * (1 / 4d)) + (int) (255 * (1 / 4d))
                            + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))
                            + (int) (150 * (-1 / 8d)) + (int) (150 * (-1 / 8d))),
                            ((int) (255 * (1d))) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (-1 / 8d)) + (int) (150 * (-1 / 8d)),
                            ((int) (255 * (1 / 4d))) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))
                                    + (int) (255 * (-1 / 8d)))},


            {new RGB(((int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))
                    + (int) (255 * (1 / 4d)) + (int) (255 * (1 / 4d)) + (int) (150 * (-1 / 8d))),
                    ((int) (255 * (1 / 4d))) + (int) (255 * (1 / 4d))
                            + (int) (255 * (-1 / 8d)) + (int) (150 * (1 / 4d))
                            + (int) (150 * (1 / 4d)),
                    ((int) (255 * (1d))) + (int) (255 * (1 / 4d))
                            + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))),
                new RGB(((int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))
                            + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))
                            + (int) (255 * (1 / 4d)) + (int) (255 * (1 / 4d))
                            + (int) (150 * (1 / 4d)) + (int) (150 * (-1 / 8d))),
                            ((int) (255 * (1d))) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d))
                                    + (int) (150 * (1 / 4d)) + (int) (150 * (1 / 4d)),
                            ((int) (255 * (1 / 4d))) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d))
                                    + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d))),
                new RGB(((int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))
                            + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))
                            + (int) (255 * (-1 / 8d)) + (int) (255 * (1 / 4d))
                            + (int) (150 * (1 / 4d)) + (int) (150 * (-1 / 8d))),
                            ((int) (255 * (1d))) + (int) (255 * (-1 / 8d))
                                    + (int) (255 * (1 / 4d)) + (int) (255 * (1 / 4d))
                                    + (int) (150 * (-1 / 8d)) + (int) (150 * (1 / 4d)),
                            ((int) (255 * (-1 / 8d))) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (1 / 4d)) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (1 / 4d)) + (int) (255 * (1 / 4d))),
                new RGB(((int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))
                            + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))
                            + (int) (150 * (1 / 4d)) + (int) (150 * (1 / 4d))),
                            ((int) (255 * (1 / 4d))) + (int) (255 * (-1 / 8d))
                                    + (int) (255 * (1 / 4d)) + (int) (150 * (-1 / 8d)),
                            ((int) (255 * (1d))) + (int) (255 * (-1 / 8d))
                                    + (int) (255 * (1 / 4d)) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (1 / 4d)))},

            {new RGB(((int) (255 * (1d)) + (int) (255 * (1 / 4d)) + (int) (150 * (-1 / 8d))),
                    ((int) (255 * (-1 / 8d))) + (int) (255 * (1 / 4d))
                            + (int) (255 * (-1 / 8d)) + (int) (150 * (1d)) + (int) (150 * (1 / 4d)),
                    ((int) (255 * (1 / 4d))) + (int) (255 * (-1 / 8d))
                            + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))),
                new RGB(((int) (255 * (1d)) + (int) (255 * (1 / 4d))
                            + (int) (150 * (1 / 4d)) + (int) (150 * (-1 / 8d))),
                            ((int) (255 * (-1 / 8d))) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d))
                                    + (int) (150 * (1 / 4d)) + (int) (150 * (1d)),
                            ((int) (255 * (1 / 4d))) + (int) (255 * (-1 / 8d))
                                    + (int) (255 * (-1 / 8d)) + (int) (255 * (-1 / 8d))
                                    + (int) (255 * (-1 / 8d)) + (int) (255 * (1 / 4d))),
                new RGB(((int) (255 * (1 / 4d)) + (int) (150 * (1 / 4d))
                            + (int) (150 * (1d)) + (int) (255 * (-1 / 8d))),
                            ((int) (255 * (-1 / 8d))) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d))
                                    + (int) (150 * (1 / 4d)) + (int) (150 * (-1 / 8d)),
                            ((int) (255 * (-1 / 8d))) + (int) (255 * (-1 / 8d))
                                    + (int) (255 * (-1 / 8d)) + (int) (255 * (1 / 4d))
                                    + (int) (255 * (1d)) + (int) (255 * (1 / 4d))),
                new RGB(((int) (150 * (1d)) + (int) (150 * (1 / 4d)) + (int) (255 * (-1 / 8d))),
                            ((int) (255 * (-1 / 8d))) + (int) (150 * (-1 / 8d))
                                    + (int) (255 * (1 / 4d)) + (int) (255 * (-1 / 8d)),
                            ((int) (255 * (1d))) + (int) (255 * (-1 / 8d))
                                    + (int) (255 * (-1 / 8d)) + (int) (255 * (1 / 4d)))}};

    ImageProcessingControllerGUI guiController =
            new ImageProcessingControllerGUI(models, frameView);

    guiController.applyOp("filter", "sharpen");
    assertArrayEquals(sharpenedGridFourByFour, models.get("current").getGrid());

  }


  @Test
  public void guiInvalidInputs() {
    ImageProcessingControllerGUI guiController =
            new ImageProcessingControllerGUI(models, frameView);

    guiController.applyOp("random", "random2");
    assertArrayEquals(testGrid, models.get("current").getGrid());
  }

}

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import controller.ImageProcessingController;
import controller.ImageProcessingControllerImpl;
import model.ImageGrid;
import model.ImageProcessingModel;
import model.RGB;
import view.ImageProcessingView;
import view.ImageProcessingViewImpl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the ImageProcessingControllerImpl class.
 */
public class ControllerTest {

  Map<String, ImageProcessingModel> models;
  ImageProcessingView view;
  ImageProcessingController controller;


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
  ImageProcessingModel testPPM;
  ImageProcessingModel allLumaPPM;
  Appendable output;
  Readable input;

  @Before
  public void initializeImage() {
    output = new StringBuffer();
    RGB[][] testGrid = new RGB[][]{{red, red, red, red}, {green, blue, blue, green},
        {blue, green, green, blue}, {orange, orange, purple, purple}};
    this.testPPM = new ImageGrid(testGrid);

    RGB[][] allLumaGrid = new RGB[][]
        {{redLuma, redLuma, redLuma, redLuma}, {greenLuma, blueLuma, blueLuma, greenLuma},
                {blueLuma, greenLuma, greenLuma, blueLuma},
                {orangeLuma, orangeLuma, purpleLuma, purpleLuma}};
    this.allLumaPPM = new ImageGrid(allLumaGrid);

    this.models = new HashMap<>();
    this.view = new ImageProcessingViewImpl(output);
  }


  @Test
  public void testSetUpNullModels() {
    try {
      output = new StringBuffer();
      input = new StringReader("load res/test.ppm testImage q");
      this.models = null;
      controller = new ImageProcessingControllerImpl(models, view, input);
      fail("No exception thrown");
    } catch (IllegalArgumentException e) {
      //Exception thrown
    }
  }

  @Test
  public void testSetUpNullView() {
    try {
      output = new StringBuffer();
      input = new StringReader("load res/test.ppm testImage q");
      this.view = null;
      controller = new ImageProcessingControllerImpl(models, view, input);
      fail("No exception thrown");
    } catch (IllegalArgumentException e) {
      //Exception thrown
    }
  }

  @Test
  public void testSetUpNullInput() {
    try {
      output = new StringBuffer();
      this.input = null;
      controller = new ImageProcessingControllerImpl(models, view, input);
      fail("No exception thrown");
    } catch (IllegalArgumentException e) {
      //Exception thrown
    }
  }

  @Test
  public void testSetUpNullAll() {
    try {
      output = new StringBuffer();
      this.input = null;
      this.models = null;
      this.view = null;
      controller = new ImageProcessingControllerImpl(models, view, input);
      fail("No exception thrown");
    } catch (IllegalArgumentException e) {
      //Exception thrown
    }
  }

  @Test
  public void testSetUpIncompleteInput() {
    try {
      output = new StringBuffer();
      input = new StringReader("load res/test.ppm testImage ");
      controller = new ImageProcessingControllerImpl(models, view, input);
      controller.runProgram();
      fail("No exception thrown");
    } catch (IllegalStateException e) {
      //Exception thrown
    }
  }

  @Test
  public void testIOtoIllegalState() {
    try {
      output = new StringBuffer();
      input = new StringReader("load res/test.ppm testImage q");
      this.view = new ImageProcessingViewMock();
      controller = new ImageProcessingControllerImpl(models, view, input);
      controller.runProgram();
      fail("No exception thrown");
    } catch (IllegalStateException e) {
      //Exception thrown
    }
  }

  @Test
  public void testQuit() {
    output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertEquals(System.lineSeparator() + "Program ended",
            output.toString().split("Type instruction: ")[1]);
  }

  @Test
  public void testBrightenInput() {
    RGB brighterRed = new RGB(255, 150, 150);
    RGB brighterGreen = new RGB(150, 255, 150);
    RGB brighterBlue = new RGB(150, 150, 255);
    RGB brighterOrange = new RGB(255, 255, 150);
    RGB brighterPurple = new RGB(255, 150, 255);
    RGB[][] brighterGrid = new RGB[][]{{brighterRed, brighterRed, brighterRed, brighterRed},
        {brighterGreen, brighterBlue, brighterBlue, brighterGreen},
        {brighterBlue, brighterGreen, brighterGreen, brighterBlue},
        {brighterOrange, brighterOrange, brighterPurple, brighterPurple}};
    ImageProcessingModel brighterPPM = new ImageGrid(brighterGrid);

    input = new StringReader("load res/test.ppm testImage brighten testImage 150 testBrighten q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(brighterPPM.getGrid(), models.get("testBrighten").getGrid());
  }


  @Test
  public void testDarkenInput() {
    RGB darkerRed = new RGB(105, 0, 0);
    RGB darkerGreen = new RGB(0, 105, 0);
    RGB darkerBlue = new RGB(0, 0, 105);
    RGB darkerOrange = new RGB(105, 0, 0);
    RGB darkerPurple = new RGB(0, 0, 105);
    RGB[][] darkerGrid = new RGB[][]{{darkerRed, darkerRed, darkerRed, darkerRed},
        {darkerGreen, darkerBlue, darkerBlue, darkerGreen},
        {darkerBlue, darkerGreen, darkerGreen, darkerBlue},
        {darkerOrange, darkerOrange, darkerPurple, darkerPurple}};
    ImageProcessingModel darkerPPM = new ImageGrid(darkerGrid);

    input = new StringReader("load res/test.ppm testImage darken testImage 150 testDarken q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(darkerPPM.getGrid(), models.get("testDarken").getGrid());
  }

  @Test
  public void testVerticalFlipInput() {
    RGB[][] verticallyFlippedGrid = new RGB[][]{{orange, orange, purple, purple},
        {blue, green, green, blue},
        {green, blue, blue, green}, {red, red, red, red}};
    ImageProcessingModel verticallyFlippedPPM = new ImageGrid(verticallyFlippedGrid);
    input = new StringReader("load res/test.ppm testImage " +
            "vertical-flip testImage testVertical q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(verticallyFlippedPPM.getGrid(), models.get("testVertical").getGrid());
  }

  @Test
  public void testHorizontalFlipInput() {
    RGB[][] horizontallyFlippedGrid = new RGB[][]{{red, red, red, red}, {green, blue, blue, green},
        {blue, green, green, blue}, {purple, purple, orange, orange}};
    ImageProcessingModel horizontallyFlippedPPM = new ImageGrid(horizontallyFlippedGrid);
    input = new StringReader("load res/test.ppm testImage " +
            "horizontal-flip testImage testHorizontal q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(horizontallyFlippedPPM.getGrid(), models.get("testHorizontal").getGrid());
  }

  @Test
  public void testSetComponentInputRed() {
    RGB[][] allRedGrid = new RGB[][]{{white, white, white, white}, {black, black, black, black},
        {black, black, black, black}, {white, white, grey, grey}};
    ImageProcessingModel allRedPPM = new ImageGrid(allRedGrid);

    input = new StringReader("load res/test.ppm testImage " +
            "greyscale testImage red-component testRed q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(allRedPPM.getGrid(), models.get("testRed").getGrid());
  }

  @Test
  public void testSetComponentInputGreen() {
    RGB[][] allGreenGrid = new RGB[][]{{black, black, black, black}, {white, black, black, white},
        {black, white, white, black}, {grey, grey, black, black}};
    ImageProcessingModel allGreenPPM = new ImageGrid(allGreenGrid);

    input = new StringReader("load res/test.ppm testImage " +
            "greyscale testImage green-component testGreen q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(allGreenPPM.getGrid(), models.get("testGreen").getGrid());
  }

  @Test
  public void testSetComponentInputBlue() {
    RGB[][] allBlueGrid = new RGB[][]{{black, black, black, black}, {black, white, white, black},
        {white, black, black, white}, {black, black, white, white}};
    ImageProcessingModel allBluePPM = new ImageGrid(allBlueGrid);

    input = new StringReader("load res/test.ppm testImage " +
            "greyscale testImage blue-component testBlue q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(allBluePPM.getGrid(), models.get("testBlue").getGrid());
  }

  @Test
  public void testSetComponentInputValue() {
    RGB[][] allValueGrid = new RGB[][]{{white, white, white, white}, {white, white, white, white},
        {white, white, white, white}, {white, white, white, white}};
    ImageProcessingModel allValuePPM = new ImageGrid(allValueGrid);

    input = new StringReader("load res/test.ppm testImage " +
            "greyscale testImage value-component testValue q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(allValuePPM.getGrid(), models.get("testValue").getGrid());
  }

  @Test
  public void testSetComponentInputLuma() {
    input = new StringReader("load res/test.ppm testImage " +
            "greyscale testImage luma-component testLuma q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(allLumaPPM.getGrid(), models.get("testLuma").getGrid());
  }

  @Test
  public void testColorTransformationInputGreyscale() {
    input = new StringReader("load res/test.ppm testImage " +
            "color-transformation testImage greyscale testGrey q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(allLumaPPM.getGrid(), models.get("testGrey").getGrid());
  }

  @Test
  public void testColorTransformationInputSepia() {
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

    input = new StringReader("load res/test.ppm testImage " +
            "color-transformation testImage sepia testSepia q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(allSepiaGrid, models.get("testSepia").getGrid());
  }

  @Test
  public void testFilterInputBlur() {
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

    input = new StringReader("load res/test.ppm testImage " +
            "filter testImage blur testBlur q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(blurredGridFourByFour, models.get("testBlur").getGrid());
  }

  @Test
  public void testFilterInputSharpen() {
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

    input = new StringReader("load res/test.ppm testImage " +
            "filter testImage sharpen testSharp q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(sharpenedGridFourByFour, models.get("testSharp").getGrid());
  }

  @Test
  public void testSetComponentInputIntensity() {
    RGB avgSingle = new RGB(85, 85, 85);
    RGB avgOP = new RGB(135, 135, 135);
    RGB[][] allIntensityGrid = new RGB[][]{{avgSingle, avgSingle, avgSingle, avgSingle},
        {avgSingle, avgSingle, avgSingle, avgSingle},
        {avgSingle, avgSingle, avgSingle, avgSingle}, {avgOP, avgOP, avgOP, avgOP}};
    ImageProcessingModel allIntensityPPM = new ImageGrid(allIntensityGrid);

    input = new StringReader("load res/test.ppm testImage " +
            "greyscale testImage intensity-component testIntensity q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(allIntensityPPM.getGrid(), models.get("testIntensity").getGrid());
  }

  @Test
  public void testVerticalFlipHorizontalFlipInput() {
    RGB[][] verticalHorizontalFlippedGrid = new RGB[][]{{purple, purple, orange, orange},
        {blue, green, green, blue}, {green, blue, blue, green}, {red, red, red, red}};
    ImageProcessingModel verticalHorizontalFlipPPM
            = new ImageGrid(verticalHorizontalFlippedGrid);
    input = new StringReader("load res/test.ppm testImage " +
            "vertical-flip testImage testVertical " +
            "horizontal-flip testVertical testVerticalHorizontal q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(verticalHorizontalFlipPPM.getGrid(),
            models.get("testVerticalHorizontal").getGrid());
  }

  @Test
  public void testVerticalFlipVerticalFlipInput() {
    input = new StringReader("load res/test.ppm testImage " +
            "vertical-flip testImage testVertical " +
            "vertical-flip testVertical testVerticalVertical q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(testPPM.getGrid(), models.get("testVerticalVertical").getGrid());
  }

  @Test
  public void testHorizontalFlipHorizontalFlipInput() {
    input = new StringReader("load res/test.ppm testImage " +
            "horizontal-flip testImage testHorizontal " +
            "horizontal-flip testHorizontal testHorizontalHorizontal q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(testPPM.getGrid(), models.get("testHorizontalHorizontal").getGrid());
  }

  @Test
  public void testSetComponentInputRedIntensity() {
    RGB[][] allRedGrid = new RGB[][]{{white, white, white, white}, {black, black, black, black},
        {black, black, black, black}, {white, white, grey, grey}};
    ImageProcessingModel allRedPPM = new ImageGrid(allRedGrid);

    input = new StringReader("load res/test.ppm testImage " +
            "greyscale testImage red-component testRed " +
            "greyscale testRed intensity-component testRedIntensity q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(allRedPPM.getGrid(), models.get("testRedIntensity").getGrid());
  }

  @Test
  public void testInvalidCommand() {
    Appendable output = new StringBuffer();
    input = new StringReader("ld res/test.ppm testImage q");
    this.view = new ImageProcessingViewImpl(output);
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertEquals("Command does not exist! Try again." + System.lineSeparator(),
            output.toString().split("Type instruction: " + System.lineSeparator())[1]);
  }

  @Test
  public void testInvalidImageName() {
    Appendable output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.ppm testImage " +
            "greyscale nothing intensity-component testIntensity q");
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertEquals("Image does not exist! Try again." + System.lineSeparator(),
            output.toString().split("Type instruction: " + System.lineSeparator())[2]);
  }

  @Test
  public void testInvalidCommandInvalidComponent() {
    Appendable output = new StringBuffer();
    input = new StringReader("load res/test.ppm testImage " +
            "greyscale testImage doesntexist-component testLuma q");
    this.view = new ImageProcessingViewImpl(output);
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertEquals("Command failed! Try again." + System.lineSeparator(),
            output.toString().split("Type instruction: " + System.lineSeparator())[2]);
  }

  @Test
  public void testInvalidFileName() {
    Appendable output = new StringBuffer();
    input = new StringReader("load doesntExist.ppm testImage q");
    this.view = new ImageProcessingViewImpl(output);
    ImageProcessingController controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertEquals("File not found! Try again." + System.lineSeparator(),
            output.toString().split("Type instruction: " + System.lineSeparator())[1]);

  }

  @Test
  public void testLoadPPM() {
    output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.ppm testImage q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(testPPM.getGrid(), models.get("testImage").getGrid());
  }

  @Test
  public void testLoadPNG() {
    output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.png testImage q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(testPPM.getGrid(), models.get("testImage").getGrid());
  }

  @Test
  public void testLoadTranslucentPNG() {
    output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/testOpaque.png testImage q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(testPPM.getGrid(), models.get("testImage").getGrid());
  }

  @Test
  public void testLoadJPG() {
    output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.jpg testImage q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertEquals("Type instruction: ",
            output.toString().split(System.lineSeparator())[11]);
    assertEquals(testPPM.getGrid().length, models.get("testImage").getGrid().length);
  }

  @Test
  public void testLoadBMP() {
    output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.bmp testImage q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(testPPM.getGrid(), models.get("testImage").getGrid());
  }

  @Test
  public void testLoadTranslucentBMP() {
    output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/testOpaque.bmp testImage q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(testPPM.getGrid(), models.get("testImage").getGrid());
  }

  @Test
  public void testLoadMultiple() {
    output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.bmp testImage load res/test.png testPNGImage " +
            "load res/test.jpg testJPGImage load res/test.ppm testPPMImage q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertEquals(4, models.size());
  }

  @Test
  public void testSavePPMPPM() {
    RGB[][] verticallyFlippedGrid = new RGB[][]{{orange, orange, purple, purple},
        {blue, green, green, blue},
        {green, blue, blue, green}, {red, red, red, red}};
    ImageProcessingModel verticallyFlippedPPM = new ImageGrid(verticallyFlippedGrid);
    Appendable output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.ppm testImage " +
            "vertical-flip testImage testVertical " +
            "save testVertical.ppm testVertical " +
            "load testVertical.ppm testNewVertical q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(verticallyFlippedPPM.getGrid(), models.get("testNewVertical").getGrid());
  }

  @Test
  public void testSavePPMPNG() {
    Appendable output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.ppm testImage " +
            "vertical-flip testImage testVertical " +
            "save testVertical.png testVertical " +
            "load testVertical.png testNewVertical q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(models.get("testVertical").getGrid(),
            models.get("testNewVertical").getGrid());
  }

  @Test
  public void testSavePPMJPG() {
    Appendable output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.ppm testImage " +
            "vertical-flip testImage testVertical " +
            "save testVertical.jpg testVertical " +
            "load testVertical.jpg testNewVertical q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertEquals(models.get("testVertical").getGrid().length,
            models.get("testNewVertical").getGrid().length);
  }

  @Test
  public void testSavePPMBMP() {
    Appendable output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.ppm testImage " +
            "vertical-flip testImage testVertical " +
            "save testVertical.bmp testVertical " +
            "load testVertical.bmp testNewVertical q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(models.get("testVertical").getGrid(),
            models.get("testNewVertical").getGrid());
  }

  @Test
  public void testSavePNGPNG() {
    RGB[][] verticallyFlippedGrid = new RGB[][]{{orange, orange, purple, purple},
        {blue, green, green, blue},
        {green, blue, blue, green}, {red, red, red, red}};
    ImageProcessingModel verticallyFlippedPPM = new ImageGrid(verticallyFlippedGrid);
    Appendable output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.png testImage " +
            "vertical-flip testImage testVertical " +
            "save testVertical.png testVertical " +
            "load testVertical.png testNewVertical q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(verticallyFlippedPPM.getGrid(), models.get("testNewVertical").getGrid());
  }

  @Test
  public void testSavePNGPPM() {
    Appendable output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.png testImage " +
            "vertical-flip testImage testVertical " +
            "save testVertical.ppm testVertical " +
            "load testVertical.ppm testNewVertical q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(models.get("testVertical").getGrid(),
            models.get("testNewVertical").getGrid());
  }

  @Test
  public void testSavePNGJPG() {
    Appendable output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.png testImage " +
            "vertical-flip testImage testVertical " +
            "save testVertical.jpg testVertical " +
            "load testVertical.jpg testNewVertical q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertEquals(models.get("testVertical").getGrid().length,
            models.get("testNewVertical").getGrid().length);
  }

  @Test
  public void testSavePNGBMP() {
    Appendable output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.png testImage " +
            "vertical-flip testImage testVertical " +
            "save testVertical.bmp testVertical " +
            "load testVertical.bmp testNewVertical q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(models.get("testVertical").getGrid(),
            models.get("testNewVertical").getGrid());
  }

  @Test
  public void testSaveJPGJPG() {
    RGB[][] verticallyFlippedGrid = new RGB[][]{{orange, orange, purple, purple},
        {blue, green, green, blue},
        {green, blue, blue, green}, {red, red, red, red}};
    ImageProcessingModel verticallyFlippedPPM = new ImageGrid(verticallyFlippedGrid);
    Appendable output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.jpg testImage " +
            "vertical-flip testImage testVertical " +
            "save testVertical.jpg testVertical " +
            "load testVertical.jpg testNewVertical q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertEquals(models.get("testVertical").getGrid().length,
            models.get("testNewVertical").getGrid().length);
  }

  @Test
  public void testSaveJPGPPM() {
    Appendable output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.jpg testImage " +
            "vertical-flip testImage testVertical " +
            "save testVertical.ppm testVertical " +
            "load testVertical.ppm testNewVertical q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(models.get("testVertical").getGrid(),
            models.get("testNewVertical").getGrid());
  }

  @Test
  public void testSaveJPGPNG() {
    Appendable output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.jpg testImage " +
            "vertical-flip testImage testVertical " +
            "save testVertical.png testVertical " +
            "load testVertical.png testNewVertical q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(models.get("testVertical").getGrid(),
            models.get("testNewVertical").getGrid());
  }

  @Test
  public void testSaveJPGBMP() {
    Appendable output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.jpg testImage " +
            "vertical-flip testImage testVertical " +
            "save testVertical.bmp testVertical " +
            "load testVertical.bmp testNewVertical q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(models.get("testVertical").getGrid(),
            models.get("testNewVertical").getGrid());
  }

  @Test
  public void testSaveBMPBMP() {
    RGB[][] verticallyFlippedGrid = new RGB[][]{{orange, orange, purple, purple},
        {blue, green, green, blue},
        {green, blue, blue, green}, {red, red, red, red}};
    ImageProcessingModel verticallyFlippedPPM = new ImageGrid(verticallyFlippedGrid);
    Appendable output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.bmp testImage " +
            "vertical-flip testImage testVertical " +
            "save testVertical.bmp testVertical " +
            "load testVertical.bmp testNewVertical q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(verticallyFlippedPPM.getGrid(), models.get("testNewVertical").getGrid());
  }

  @Test
  public void testSaveBMPPPM() {
    Appendable output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.bmp testImage " +
            "vertical-flip testImage testVertical " +
            "save testVertical.ppm testVertical " +
            "load testVertical.ppm testNewVertical q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(models.get("testVertical").getGrid(),
            models.get("testNewVertical").getGrid());
  }

  @Test
  public void testSaveBMPPNG() {
    Appendable output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.bmp testImage " +
            "vertical-flip testImage testVertical " +
            "save testVertical.png testVertical " +
            "load testVertical.png testNewVertical q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertArrayEquals(models.get("testVertical").getGrid(),
            models.get("testNewVertical").getGrid());
  }

  @Test
  public void testSaveBMPJPG() {
    Appendable output = new StringBuffer();
    this.view = new ImageProcessingViewImpl(output);
    input = new StringReader("load res/test.bmp testImage " +
            "vertical-flip testImage testVertical " +
            "save testVertical.jpg testVertical " +
            "load testVertical.jpg testNewVertical q");
    controller = new ImageProcessingControllerImpl(models, view, input);
    controller.runProgram();
    assertEquals(models.get("testVertical").getGrid().length,
            models.get("testNewVertical").getGrid().length);
  }

}

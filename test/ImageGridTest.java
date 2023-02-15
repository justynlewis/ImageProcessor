import org.junit.Before;
import org.junit.Test;

import model.ImageGrid;
import model.ImageProcessingModel;
import model.RGB;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * Tests the PPMImageGrid class.
 */
public class ImageGridTest {

  private final RGB red = new RGB(255, 0, 0);
  private final RGB green = new RGB(0, 255, 0);
  private final RGB blue = new RGB(0, 0, 255);
  private final RGB orange = new RGB(255, 150, 0);
  private final RGB purple = new RGB(150, 0, 255);

  private final RGB cyan = new RGB(0, 255, 255);
  private final RGB yellow = new RGB(255, 255, 0);

  private final RGB pink = new RGB(250, 100, 150);
  private final RGB indigo = new RGB(145, 145, 250);

  private final RGB white = new RGB(255, 255, 255);
  private final RGB black = new RGB(0, 0, 0);
  private final RGB grey = new RGB(150, 150, 150);

  private final RGB darkGrey = new RGB(100, 100, 100);
  private final RGB midGrey = new RGB(145, 145, 145);
  private final RGB lightGrey = new RGB(250, 250, 250);


  private final RGB redLuma = new RGB(54, 54, 54);
  private final RGB greenLuma = new RGB(182, 182, 182);
  private final RGB blueLuma = new RGB(18, 18, 18);
  private final RGB orangeLuma = new RGB(161, 161, 161);
  private final RGB purpleLuma = new RGB(50, 50, 50);

  private final RGB cyanLuma = new RGB(200, 200, 200);
  private final RGB yellowLuma = new RGB(236, 236, 236);

  private final RGB pinkLuma = new RGB(135, 135, 135);
  private final RGB indigoLuma = new RGB(152, 152, 152);

  private RGB[][] testGridFourByFour;
  ImageProcessingModel testPPMModelFourByFour;

  private RGB[][] testGridTwoByOne;
  ImageProcessingModel testPPMModelTwoByOne;

  private RGB[][] testGridOneByTwo;
  ImageProcessingModel testPPMModelOneByTwo;

  private RGB[][] allLumaGrid;
  private RGB[][] allLumaGridTwoByOne;

  private RGB[][] allLumaGridOneByTwo;

  @Before
  public void initializeImage() {
    this.testGridFourByFour = new RGB[][]{{red, red, red, red}, {green, blue, blue, green},
        {blue, green, green, blue}, {orange, orange, purple, purple}};
    this.testPPMModelFourByFour = new ImageGrid(testGridFourByFour);

    this.testGridTwoByOne = new RGB[][]{{cyan, yellow}};
    this.testPPMModelTwoByOne = new ImageGrid(testGridTwoByOne);

    this.testGridOneByTwo = new RGB[][]{{pink}, {indigo}};
    this.testPPMModelOneByTwo = new ImageGrid(testGridOneByTwo);

    this.allLumaGrid
            = new RGB[][]
            {{redLuma, redLuma, redLuma, redLuma},
                    {greenLuma, blueLuma, blueLuma, greenLuma},
                    {blueLuma, greenLuma, greenLuma, blueLuma},
                    {orangeLuma, orangeLuma, purpleLuma, purpleLuma}};

    this.allLumaGridTwoByOne = new RGB[][]{{cyanLuma, yellowLuma}};

    this.allLumaGridOneByTwo = new RGB[][]{{pinkLuma}, {indigoLuma}};

  }

  @Test
  public void getGrid() {
    assertArrayEquals(testGridFourByFour, testPPMModelFourByFour.getGrid());
    assertArrayEquals(testGridTwoByOne, testPPMModelTwoByOne.getGrid());
    assertArrayEquals(testGridOneByTwo, testPPMModelOneByTwo.getGrid());
  }

  @Test
  public void brighten() {
    RGB brighterRed = new RGB(255, 150, 150);
    RGB brighterGreen = new RGB(150, 255, 150);
    RGB brighterBlue = new RGB(150, 150, 255);
    RGB brighterOrange = new RGB(255, 255, 150);
    RGB brighterPurple = new RGB(255, 150, 255);
    RGB[][] brighterGrid = new RGB[][]{{brighterRed, brighterRed, brighterRed, brighterRed},
        {brighterGreen, brighterBlue, brighterBlue, brighterGreen},
        {brighterBlue, brighterGreen, brighterGreen, brighterBlue},
        {brighterOrange, brighterOrange, brighterPurple, brighterPurple}};
    assertEquals(brighterGrid[0][0],
            testPPMModelFourByFour.brighten(150).getGrid()[0][0]);
    assertArrayEquals(brighterGrid, testPPMModelFourByFour.brighten(150).getGrid());

    RGB brighterCyan = new RGB(5, 255, 255);
    RGB brighterYellow = new RGB(255, 255, 5);
    RGB[][] brighterGridTwoByOne = new RGB[][]{{brighterCyan, brighterYellow}};
    assertArrayEquals(brighterGridTwoByOne, testPPMModelTwoByOne.brighten(5).getGrid());

    RGB brighterPink = new RGB(255, 110, 160);
    RGB brighterIndigo = new RGB(155, 155, 255);
    RGB[][] brighterGridOneByTwo = new RGB[][]{{brighterPink}, {brighterIndigo}};
    assertArrayEquals(brighterGridOneByTwo, testPPMModelOneByTwo.brighten(10).getGrid());

  }

  @Test
  public void brightenNegative() {
    RGB darkerCyan = new RGB(0, 200, 200);
    RGB darkerYellow = new RGB(200, 200, 0);
    RGB[][] darkerGrid = new RGB[][]{{darkerCyan, darkerYellow}};
    assertArrayEquals(darkerGrid, testPPMModelTwoByOne.brighten(-55).getGrid());

    RGB darkerPink = new RGB(220, 70, 120);
    RGB darkerIndigo = new RGB(115, 115, 220);
    RGB[][] darkerGridOneByTwo = new RGB[][]{{darkerPink}, {darkerIndigo}};
    assertArrayEquals(darkerGridOneByTwo, testPPMModelOneByTwo.brighten(-30).getGrid());
  }

  @Test
  public void brightenZero() {
    assertArrayEquals(testGridTwoByOne, testPPMModelTwoByOne.brighten(0).getGrid());
    assertArrayEquals(testGridOneByTwo, testPPMModelOneByTwo.brighten(0).getGrid());
  }

  @Test
  public void brightenToWhite() {
    RGB[][] whiteGrid = new RGB[][]{{white, white}};
    assertArrayEquals(whiteGrid, testPPMModelTwoByOne.brighten(255).getGrid());

    RGB[][] whiteGridOneByTwo = new RGB[][]{{white}, {white}};
    assertArrayEquals(whiteGridOneByTwo, testPPMModelOneByTwo.brighten(400).getGrid());
  }

  @Test
  public void darkenToBlack() {
    RGB[][] blackGrid = new RGB[][]{{black, black}};
    assertArrayEquals(blackGrid, testPPMModelTwoByOne.brighten(-255).getGrid());

    RGB[][] blackGridOneByTwo = new RGB[][]{{black}, {black}};
    assertArrayEquals(blackGridOneByTwo, testPPMModelOneByTwo.brighten(-374).getGrid());
  }

  @Test
  public void flipVertical() {
    RGB[][] verticallyFlippedGrid = new RGB[][]{{orange, orange, purple, purple},
        {blue, green, green, blue},
        {green, blue, blue, green}, {red, red, red, red}};
    assertArrayEquals(verticallyFlippedGrid, testPPMModelFourByFour.flipVertical().getGrid());

    RGB[][] verticallyFlippedGridTwoByOne = new RGB[][]{{cyan, yellow}};
    assertArrayEquals(verticallyFlippedGridTwoByOne, testPPMModelTwoByOne.flipVertical().getGrid());

    RGB[][] verticallyFlippedGridOneByTwo = new RGB[][]{{indigo}, {pink}};
    assertArrayEquals(verticallyFlippedGridOneByTwo, testPPMModelOneByTwo.flipVertical().getGrid());
  }

  @Test
  public void flipHorizontal() {
    RGB[][] horizontallyFlippedGrid = new RGB[][]{{red, red, red, red}, {green, blue, blue, green},
        {blue, green, green, blue}, {purple, purple, orange, orange}};
    assertArrayEquals(horizontallyFlippedGrid, testPPMModelFourByFour.flipHorizontal().getGrid());

    RGB[][] horizontallyFlippedGridTwoByOne = new RGB[][]{{yellow, cyan}};
    assertArrayEquals(horizontallyFlippedGridTwoByOne,
            testPPMModelTwoByOne.flipHorizontal().getGrid());

    RGB[][] horizontallyFlippedGridOneByTwo = new RGB[][]{{pink}, {indigo}};
    assertArrayEquals(horizontallyFlippedGridOneByTwo,
            testPPMModelOneByTwo.flipHorizontal().getGrid());
  }

  @Test
  public void setRedComponent() {
    RGB[][] allRedGrid = new RGB[][]{{white, white, white, white}, {black, black, black, black},
        {black, black, black, black}, {white, white, grey, grey}};
    assertArrayEquals(allRedGrid,
            testPPMModelFourByFour.channelComponent("red-component").getGrid());

    RGB[][] allRedGridTwoByOne = new RGB[][]{{black, white}};
    assertArrayEquals(allRedGridTwoByOne,
            testPPMModelTwoByOne.channelComponent("red-component").getGrid());

    RGB[][] allRedGridOneByTwo = new RGB[][]{{lightGrey}, {midGrey}};
    assertArrayEquals(allRedGridOneByTwo,
            testPPMModelOneByTwo.channelComponent("red-component").getGrid());
  }

  @Test
  public void setGreenComponent() {
    RGB[][] allGreenGrid = new RGB[][]{{black, black, black, black}, {white, black, black, white},
        {black, white, white, black}, {grey, grey, black, black}};
    assertArrayEquals(allGreenGrid,
            testPPMModelFourByFour.channelComponent("green-component").getGrid());

    RGB[][] allGreenGridTwoByOne = new RGB[][]{{white, white}};
    assertArrayEquals(allGreenGridTwoByOne,
            testPPMModelTwoByOne.channelComponent("green-component").getGrid());

    RGB[][] allGreenGridOneByTwo = new RGB[][]{{darkGrey}, {midGrey}};
    assertArrayEquals(allGreenGridOneByTwo,
            testPPMModelOneByTwo.channelComponent("green-component").getGrid());
  }

  @Test
  public void setBlueComponent() {
    RGB[][] allBlueGrid = new RGB[][]{{black, black, black, black}, {black, white, white, black},
        {white, black, black, white}, {black, black, white, white}};
    assertArrayEquals(allBlueGrid,
            testPPMModelFourByFour.channelComponent("blue-component").getGrid());

    RGB[][] allBlueGridTwoByOne = new RGB[][]{{white, black}};
    assertArrayEquals(allBlueGridTwoByOne,
            testPPMModelTwoByOne.channelComponent("blue-component").getGrid());


    RGB[][] allBlueGridOneByTwo = new RGB[][]{{grey}, {lightGrey}};
    assertArrayEquals(allBlueGridOneByTwo,
            testPPMModelOneByTwo.channelComponent("blue-component").getGrid());
  }

  @Test
  public void setValueComponent() {
    RGB[][] allValueGrid = new RGB[][]{{white, white, white, white}, {white, white, white, white},
        {white, white, white, white}, {white, white, white, white}};
    assertArrayEquals(allValueGrid,
            testPPMModelFourByFour.channelComponent("value-component").getGrid());

    RGB[][] allValueGridTwoByOne = new RGB[][]{{white, white}};
    assertArrayEquals(allValueGridTwoByOne,
            testPPMModelTwoByOne.channelComponent("value-component").getGrid());

    RGB[][] allValueGridOneByTwo = new RGB[][]{{lightGrey}, {lightGrey}};
    assertArrayEquals(allValueGridOneByTwo,
            testPPMModelOneByTwo.channelComponent("value-component").getGrid());
  }

  @Test
  public void setLumaComponent() {
    assertEquals(allLumaGrid[0][0].getRed(),
            testPPMModelFourByFour.channelComponent("luma-component").getGrid()[0][0].getRed());

    assertArrayEquals(allLumaGridTwoByOne,
            testPPMModelTwoByOne.channelComponent("luma-component").getGrid());

    assertArrayEquals(allLumaGridOneByTwo,
            testPPMModelOneByTwo.channelComponent("luma-component").getGrid());
  }

  @Test
  public void testColorTransformationGreyscale() {

    assertArrayEquals(allLumaGrid,
            testPPMModelFourByFour.colorTransformation("greyscale").getGrid());

    assertArrayEquals(allLumaGridTwoByOne,
            testPPMModelTwoByOne.colorTransformation("greyscale").getGrid());


    assertArrayEquals(allLumaGridOneByTwo,
            testPPMModelOneByTwo.colorTransformation("greyscale").getGrid());

  }


  @Test
  public void testColorTransformationSepia() {
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

    assertArrayEquals(allSepiaGrid,
            testPPMModelFourByFour.colorTransformation("sepia").
                    getGrid());

    RGB cyanSepia = new RGB(244, 217, 169);
    RGB yellowSepia = new RGB(255, 255, 205);
    RGB[][] allSepiaGridTwoByOne = new RGB[][]{{cyanSepia, yellowSepia}};
    assertArrayEquals(allSepiaGridTwoByOne,
            testPPMModelTwoByOne.colorTransformation("sepia").getGrid());

    RGB pinkSepia = new RGB(203, 181, 141);
    RGB indigoSepia = new RGB(215, 192, 149);
    RGB[][] allSepiaGridOneByTwo = new RGB[][]{{pinkSepia}, {indigoSepia}};
    assertArrayEquals(allSepiaGridOneByTwo,
            testPPMModelOneByTwo.colorTransformation("sepia").getGrid());

  }

  @Test
  public void testSetFilterBlur() {
    RGB[][] blurredGridTwoByOne = new RGB[][]{{
            new RGB(((int) (0 * (1 / 4d)) + (int) (255 * (1 / 8d))),
                    ((int) (255 * (1 / 4d)) + (int) (255 * (1 / 8d))),
                    ((int) (255 * (1 / 4d)) + (int) (0 * (1 / 8d)))),
            new RGB(((int) (0 * (1 / 8d)) + (int) (255 * (1 / 4d))),
                    ((int) (255 * (1 / 8d)) + (int) (255 * (1 / 4d))),
                    ((int) (255 * (1 / 8d)) + (int) (0 * (1 / 4d))))}};
    assertArrayEquals(blurredGridTwoByOne, testPPMModelTwoByOne.filter("blur").getGrid());

    RGB[][] blurredGridOneByTwo = new RGB[][]{
            {new RGB(((int) (250 * (1 / 4d)) + (int) (145 * (1 / 8d))),
                    ((int) (100 * (1 / 4d)) + (int) (145 * (1 / 8d))),
                    ((int) (150 * (1 / 4d)) + (int) (250 * (1 / 8d))))},
            {new RGB(((int) (250 * (1 / 8d)) + (int) (145 * (1 / 4d))),
                    ((int) (100 * (1 / 8d)) + (int) (145 * (1 / 4d))),
                    ((int) (150 * (1 / 8d)) + (int) (250 * (1 / 4d))))}};
    assertArrayEquals(blurredGridOneByTwo, testPPMModelOneByTwo.filter("blur").getGrid());

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
    assertArrayEquals(blurredGridFourByFour,
            testPPMModelFourByFour.filter("blur").getGrid());

  }

  @Test
  public void testSetFilterSharpen() {
    RGB[][] sharpenedGridTwoByOne = new RGB[][]{{
            new RGB(((int) (255 * (1 / 4d))),
                    ((int) (255 * (1d)) + (int) (255 * (1 / 4d))),
                    ((int) (255 * (1d)))),
            new RGB(((int) (255 * (1d))),
                    ((int) (255.0 * (1 / 4d)) + (int) (255.0)),
                    ((int) (255 * (1 / 4d))))}};
    assertArrayEquals(sharpenedGridTwoByOne,
            testPPMModelTwoByOne.filter("sharpen").getGrid());

    RGB[][] sharpenedGridOneByTwo = new RGB[][]{
            {new RGB(((int) (250 * (1d)) + (int) (145 * (1 / 4d))),
                    ((int) (100 * (1d)) + (int) (145 * (1 / 4d))),
                    ((int) (150 * (1d)) + (int) (250 * (1 / 4d))))},
            {new RGB(((int) (250 * (1 / 4d)) + (int) (145 * (1d))),
                    ((int) (100 * (1 / 4d)) + (int) (145 * (1d))),
                    ((int) (150 * (1 / 4d)) + (int) (250 * (1d))))}};
    assertArrayEquals(sharpenedGridOneByTwo,
            testPPMModelOneByTwo.filter("sharpen").getGrid());

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

    assertArrayEquals(sharpenedGridFourByFour,
            testPPMModelFourByFour.filter("sharpen").getGrid());
  }

  @Test
  public void setIntensityComponent() {
    RGB avgSingle = new RGB(85, 85, 85);
    RGB avgOP = new RGB(135, 135, 135);
    RGB[][] allIntensityGrid = new RGB[][]
        {{avgSingle, avgSingle, avgSingle, avgSingle},
                {avgSingle, avgSingle, avgSingle, avgSingle},
                {avgSingle, avgSingle, avgSingle, avgSingle},
                {avgOP, avgOP, avgOP, avgOP}};
    assertArrayEquals(allIntensityGrid,
            testPPMModelFourByFour.channelComponent("intensity-component").getGrid());

    RGB avgTwoMax = new RGB(170, 170, 170);
    RGB[][] allIntensityGridTwoByOne = new RGB[][]{{avgTwoMax, avgTwoMax}};
    assertArrayEquals(allIntensityGridTwoByOne,
            testPPMModelTwoByOne.channelComponent("intensity-component").getGrid());

    RGB avgPink = new RGB(166, 166, 166);
    RGB avgIndigo = new RGB(180, 180, 180);
    RGB[][] allIntensityGridOneByTwo = new RGB[][]{{avgPink}, {avgIndigo}};
    assertArrayEquals(allIntensityGridOneByTwo,
            testPPMModelOneByTwo.channelComponent("intensity-component").getGrid());
  }

  @Test
  public void partialComponentException() {
    try {
      testPPMModelFourByFour.channelComponent("red");
      fail("No exception thrown");
    } catch (IllegalArgumentException e) {
      //exception thrown
    }
  }

  @Test
  public void invalidComponentException() {
    try {
      testPPMModelFourByFour.channelComponent("ddfg4");
      fail("No exception thrown");
    } catch (IllegalArgumentException e) {
      //exception thrown
    }
  }

  @Test
  public void getImageWidth() {
    assertEquals(4, testPPMModelFourByFour.getImageWidth());
    assertEquals(2, testPPMModelTwoByOne.getImageWidth());
    assertEquals(1, testPPMModelOneByTwo.getImageWidth());
  }

  @Test
  public void getImageHeight() {
    assertEquals(4, testPPMModelFourByFour.getImageHeight());
    assertEquals(1, testPPMModelTwoByOne.getImageHeight());
    assertEquals(2, testPPMModelOneByTwo.getImageHeight());
  }

  @Test
  public void getMaxValue() {
    assertEquals(255, testPPMModelFourByFour.getMaxValue());
    assertEquals(255, testPPMModelTwoByOne.getMaxValue());
    assertEquals(255, testPPMModelOneByTwo.getMaxValue());
  }

  @Test
  public void flipVerticalHorizontal() {
    RGB[][] verticalHorizontalFlippedGrid = new RGB[][]{{purple, purple, orange, orange},
        {blue, green, green, blue},
        {green, blue, blue, green}, {red, red, red, red}};
    assertArrayEquals(verticalHorizontalFlippedGrid,
            testPPMModelFourByFour.flipVertical().flipHorizontal().getGrid());
  }

  @Test
  public void flipVerticalVertical() {
    assertArrayEquals(testGridFourByFour,
            testPPMModelFourByFour.flipVertical().flipVertical().getGrid());
  }

  @Test
  public void flipHorizontalHorizontal() {
    assertArrayEquals(testGridFourByFour,
            testPPMModelFourByFour.flipHorizontal().flipHorizontal().getGrid());
  }

  @Test
  public void flipBrightenDarken() {
    assertArrayEquals(testGridOneByTwo,
            testPPMModelOneByTwo.brighten(1).brighten(-1).getGrid());
  }

  @Test
  public void redComponentIntensity() {
    assertArrayEquals(testPPMModelOneByTwo.channelComponent("red-component").getGrid(),
            testPPMModelOneByTwo.channelComponent("red-component")
                    .channelComponent("intensity-component").getGrid());
  }

  @Test
  public void allChanges() {
    RGB[][] blackWhiteGrid = new RGB[][]{{black, white}, {white, black}};
    ImageProcessingModel blackWhiteModel = new ImageGrid(blackWhiteGrid);

    ImageProcessingModel commBlackWhiteModel = blackWhiteModel.channelComponent("red-component")
            .channelComponent("green-component").channelComponent("blue-component")
            .flipHorizontal().channelComponent("intensity-component").flipVertical()
            .channelComponent("value-component")
            .brighten(10).channelComponent("luma-component");
    RGB lightBlack = new RGB(9, 9, 9);
    RGB darkWhite = new RGB(254, 254, 254);
    RGB[][] commBlackWhiteGrid = new RGB[][]{{lightBlack, darkWhite}, {darkWhite, lightBlack}};
    assertArrayEquals(commBlackWhiteGrid, commBlackWhiteModel.getGrid());
  }


}
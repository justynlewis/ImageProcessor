# Image Processor
### This is an image processing application with both a text-based and interactive GUI-based user interfaces.

## Run Instructions:

### Command line arguments supported (all arguments must be formatted as written below):
    - -file name-of-script.txt
        - Example: -file res/script.txt
        - Executes the instructions in the given script (name-of-script.txt) and exits the program.
          The script given must be a .txt file for this to work.
    - -text
        - Example: -text
        - The program will wait for an instruction to be inputted by the user.
    -
        - Example:
        - If no argument is provided, the program will run with its GUI view and wait for an
          instruction to be inputted by the user.

### Supported instructions GUI (all commands must be formatted as written below):
    - Load
        - Loads the file from the selected path.
    - Save
        - Save the image with the inputted name to the selected path
          which should include the name of the file.
    - Brighten/Darken increment/reduction-value
        - Changes the brightness of the image shown by the input integer.
    - Vertical Flip
        - Flips the image shown vertically.
    - Horizontal Flip
        - Flips the image shown.
    - Red Component
    - Green Component
    - Blue Component
    - Value Component
    - Luma Component
    - Intensity Component
        - Creates a greyscale image with the selected color-component or the channel-component
         of the image shown.
    - Greyscale
    - Sepia
        - Creates a new image with the color transformation of the selected type on the image shown.
    - Blur
    - Sharpen
        - Creates a new image with the selected filter type pplied to the image shown.
    - Downscale width-percentage height-percentage
        - Downscales the shown image to be of the given width and height percentages.
    - X (top left)
        - Ends the program.

### Supported instructions Text (all commands must be formatted as written below):
    - load image-path image-name
        - Example: load res/test.ppm myImage
        - Loads the file from the given path (image-path) and refers to it
          using the given name (image-name). The image path can be the absolute path of the image,
          otherwise it will look for the image file relative to the project directory.
    - save new-file-name image-name
        - Example: save res/newImage.ppm myImage
        - Save the image with the given name (image-name) to the specified path (new-file-name)
          which should include the name of the file. The file path can be the absolute path of the
          file, otherwise the program will place the file relative to the project directory.
    - brighten image-name increment-value dest-image-name
        - Example: brighten myImage 40 myBrightImage
        - Brightens the image of the given name (image-name) by the increment integer
          given (increment-value) and refers to this brighter image by the
          given destination name (dest-image-name).
    - darken image-name reduction-value dest-image-name
        - Example: darken myImage 40 myDarkImage
        - Darkens the image of the given name (image-name) by the reduction integer
          given (reduction-value) and refers to this darker image by the
          given destination name (dest-image-name).
    - vertical-flip image-name dest-image-name
        - Example: vertical-flip myImage myVerticalImage
        - Flips the image with the given name (image-name) vertically and refers to it by the given
          destination name (dest-image-name).
    - horizontal-flip image-name dest-image-name
        - Example: horizontal-flip myImage myHorizontalImage
        - Flips the image with the given name (image-name) horizontally and refers to it by
          the given destination name (dest-image-name).
    - greyscale image-name color-component/channel-component dest-image-name
        - Example: greyscale myImage red-component myRedImage
        - Supported inputs for the color-component/channel-component:
            - red-component
            - green-component
            - blue-component
            - value-component
            - luma-component
            - intensity-component
        - Creates a greyscale image with the color-component or the channel-component
          (color-component/channel-component) of the image with the given name (image-name),
          and refers to it by the given destination name (dest-image-name).
    - color-transformation image-name transformation-type dest-image-name
        - Example: color-transformation myImage sepia mySepiaImage
        - Supported inputs for the transformation-type:
            - greyscale
            - sepia
        - Creates a new image with the color transformation of the type given (transformation-type)
          of the image with the given name (image-name), and refers to it by the
          given destination name (dest-image-name).
    - filter image-name filter-type dest-image-name
        - Example: filter myImage blur myBlurImage
        - Supported inputs for the filter-type:
            - blur
            - sharpen
        - Creates a new image with the given filter type (filter-type) applied to the image
          with the given name (image-name), and refers to it by the
          given destination name (dest-image-name).
    - downscale image-name width-percentage height-percentage dest-image-name
        - Example: downscale myImage 90 90 mySmallImage
        - Downscales the image with the given name (image-name) to be of the given
        width (width-percentage) and height percentage (height-percentage), and refers to it by the
        given destination ame (dest-image-name).
    - q
        - Example: q
        - Ends the program.

## "imageprocessor" Design Explained

### Model:

    - ImageProcessingModel Interface: The model for our program. It represents the
    list of operations to be done on an image, with one object of type ImageProcessingModel
    representing one image. The downscale operation has been added.
        - ImageGrid Class: An implementation of the ImageProcessingModel that represents an image
        and has the available operations that can be done on an image.

    - RGB Class: Represents a pixel with RGB components.

    - PixelOperation Interface: Represents a function to be performed on a pixel. Intended
    for abstraction purposes for the image operations within the ImageGrid Class.

### View:

      - ImageProcessingView Interface: The view for our program. It represents the operation that is
      offered by the view to display commands and errors.
          - ImageProcessingViewImpl Class: An implementation of the ImageProcessingView that creates
          a view with an Appendable object as the destination.

      - ImageProcessorViewGUI Interface: Represents the view for the GUI.
          - ImageProcessorFrame class: Represents the frame for the GUI. Contains the
          available operations as buttons, and also initializes and sets the histograms.

### Controller:
     - ImageProcessingController Interface: The controller for our program. It represents the
     operation that is offered by the controller to run the program.
         - ImageProcessingControllerImpl Class: An implementation of the ImageProcessingController
         that takes in a Map of ImageProcessingModels, an ImageProcessingView, and a Readable object
         to run the program. The downscale operation has been added to the knownCommands map.

         - ImageProcessingControllerGUI Class: An implementation of the ImageProcessingController
         that takes in a Map of ImageProcessingModels and an ImageProcessorFrame to run the
         GUI version of the program.


     - ImageOperationCommand Interface: Represents a high-level command of the 5 commands that
     modify an image.
         - Brighten Class: A command that brightens an image. The given increment must be positive.
         - Darken Class: A command that darkens an image. The given increment must be positive.
         - HorizontalFlip Class: A command that flips an image horizontally.
         - VerticalFlip Class: A command that flips an image vertically.
         - SetToComponent Class: A command that sets an image to a grey-scale of the given component.
         - ColorTransformation: A command that changes the colors of the pixels of an image.
         - Filter: A command that applies a filter to an image.
         New Command:
         - Downscale: A command that downscales an image.


     - FileOperations Interface: Represents the operations to load and save files.
         - PPMFileOperationsImpl: An implementation of FileOperations that loads and saves PPM
         images.
         - ImageFileOperationsImpl: An implementation of FileOperations that loads and saves JPG,
         BMP, and PNG images.

     - Features Interface: Represents operations of the available features in the GUI.
         - ImageProcessorControllerGUI Class: This class also extends this interface in order to
         access to the image operations.
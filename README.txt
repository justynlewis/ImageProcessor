Assignment 6: Image Processing (Part 3)

"imageprocessor" Design Explained

Image attribution (cat): https://today.line.me/id/pc/article/
12%2BEditan%2Bfoto%2Bkucing%2Bdalam%2Bmakanan%2Bini%2Bimut%2Babis-YeXyoL

 Model:

    - ImageProcessingModel Interface: The model for our program. It represents the
    list of operations to be done on an image, with one object of type ImageProcessingModel
    representing one image. The downscale operation has been added.
        - ImageGrid Class: An implementation of the ImageProcessingModel that represents an image
        and has the available operations that can be done on an image.

    - RGB Class: Represents a pixel with RGB components.

    - PixelOperation Interface: Represents a function to be performed on a pixel. Intended
    for abstraction purposes for the image operations within the ImageGrid Class.

 View:

      - ImageProcessingView Interface: The view for our program. It represents the operation that is
      offered by the view to display commands and errors.
          - ImageProcessingViewImpl Class: An implementation of the ImageProcessingView that creates
          a view with an Appendable object as the destination.

      - ImageProcessorViewGUI Interface: Represents the view for the GUI.
          - ImageProcessorFrame class: Represents the frame for the GUI. Contains the
          available operations as buttons, and also initializes and sets the histograms.

 Controller:

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

 Main:



Assignment 5: Image Processing (Part 2)

"imageprocessor" Design Explained

Image attribution (cat): https://today.line.me/id/pc/article/
12%2BEditan%2Bfoto%2Bkucing%2Bdalam%2Bmakanan%2Bini%2Bimut%2Babis-YeXyoL

 Model:

    - ImageProcessingModel Interface: The model for our program. It represents the
    list of operations to be done on an image, with one object of type ImageProcessingModel
    representing one image. The color transformation and filter operations have been added.
        - ImageGrid Class: An implementation of the ImageProcessingModel that represents an image
        and has the available operations that can be done on an image. Name has been changed from
        PPMImageGrid to ImageGrid as operations are no longer exclusive to the .ppm file type.

    - RGB Class: Represents a pixel with RGB components.

    - PixelOperation Interface: Represents a function to be performed on a pixel. Intended
    for abstraction purposes for the image operations within the ImageGrid Class.

 View:

     - ImageProcessingView Interface: The view for our program. It represents the operation that is
     offered by the view to display commands and errors.
         - ImageProcessingViewImpl Class: An implementation of the ImageProcessingView that creates
         a view with an Appendable object as the destination.

 Controller:

    - ImageProcessingController Interface: The controller for our program. It represents the
    operation that is offered by the controller to run the program.
        - ImageProcessingControllerImpl Class: An implementation of the ImageProcessingController
        that takes in a Map of ImageProcessingModels, an ImageProcessingView, and a Readable object
        to run the program. Added support for jpg, png, and bmp file types and improved the
        efficiency of the save method.

    - ImageOperationCommand Interface: Represents a high-level command of the 5 commands that
    modify an image.
        - Brighten Class: A command that brightens an image. The given increment must be positive.
        - Darken Class: A command that darkens an image. The given increment must be positive.
        - HorizontalFlip Class: A command that flips an image horizontally.
        - VerticalFlip Class: A command that flips an image vertically.
        - SetToComponent Class: A command that sets an image to a grey-scale of the given component.
        New commands:
        - ColorTransformation: A command that changes the colors of the pixels of an image.
        - Filter: A command that applies a filter to an image.

    - FileOperations Interface: Represents the operations to load and save files.
        - PPMFileOperationsImpl: An implementation of FileOperations that loads and saves PPM
        images.
        - ImageFileOperationsImpl: An implementation of FileOperations that loads and saves JPG,
        BMP, and PNG images.

 Main:
    - ImageProcessor.java: The entry point into the program. Added functionality for the user to
    choose whether to run the program through a script, an interactive text-based program, or a GUI.

 Script of Commands:
    To run the script of commands below, provide this script when the program runs
    (without the # comments).

     #load test.ppm and call it 'test'
     load test.ppm test

     #brighten test by adding 88
     brighten test 88 test-brighter

     #flip test vertically
     vertical-flip test test-vertical

     #flip the vertically flipped test horizontally
     horizontal-flip test-vertical test-vertical-horizontal

     #create a greyscale using only the value component, as an image test-greyscale
     greyscale test value-component test-greyscale

     #downscale test by 95% of its original width and height
     downscale test 95 95 test-downscale

     #save test-brighter
     save test-brighter.ppm test-brighter

     #save test-greyscale
     save test-gs.ppm test-greyscale

     #end the program
     q
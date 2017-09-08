Introduction


The project is to design and implement an application that displays the US flag to scale with dimensions based on the US flag specifications. 


The application is built using the Java AWT and Java Swing libraries. Since it is a java application it can be executed by running the executable jar file (If the computer supports Java the application can be run by downloading the jar file and double clicking on it or by using the command line). To scale the flag up or down hover on the bottom right of the window frame and drag the window diagonally using a mouse or trackpad.


Usage


1. Download the executable jar file from github to a computer that has java installed
2. Open command line terminal and run the below commands on the command line
   1. cd ~/<path to where jar file is downloaded>
   2. java - jar usflag.jar


What does the program do?


At a high level the program does the following:


Steps in the main method:


1. Create a new JFrame, Get a Container from the Frame
2. Create the Panel that has the code to create a US Flag
3. Add the Panel created in step 2 above to the Container in step 1
4. Set the Frame to be visible and resizeable


Steps in the paintComponent method of the JPanel (note that the paintComponent method is called by the JVM, so paintComponent does not have to be explicitly invoked by the code)


1. Get the initial height and width of the Flag Panel from its parent (JPanel). This step is critical for the ability to later scale the flag up and down when it is resized.
2. Invoke separate methods to draw the Union, Stripes and Stars.


The program uses Trigonometry & Geometry to draw the Stars. It also allows the user to scale the US Flag up or down by dragging the window frame diagonally across using a mouse or a trackpad. Java AWT has in built functionality to drag and expand windows  but the contents inside the window (in this case the US Flag) needs to be scaled up and down using ratios, mathematics and fair amount of calculations. All the calculations, geometry and trig used to draw the Stars and Stripes is documented in the code comments. The critical part of the code involved in drawing the Stars is to compute 5 points each on an outer and inner circle and to make sure the order of the coordinates follow a particular order so that when fillPolygon() (in the Graphics2D) package is invoked it can draw the connecting lines in a Star shape by connecting the coordinates on the outer and inner circles in the order defined by the array and then color the Star. It is important to note that all the dimensions are calculated based on setting the height scale of the flag to 1.0 and computing all distances based on the height scale. Also the top right corner of the frame is considered as origin by Java AWT so all coordinates are set based on the x and y distances from the origin. Drawing the Union and Stripes are by using the fill() method (in the Graphics 2D package) and pass the Rectangle object since both the Union and Stripes are in the shape of a Rectangle. 


What the program does not do?


The application does not have separate UI controls to resize the window frame. For instance it does not have a slider that expands or contracts the window. Instead it allows the user to resize the window using traditional drag and expand feature. 


Learnings


I learned a lot from this project since I was able to discover how the java.awt.* and javax.swing.* packages could be used to create a GUI interface. Initially I started off by trying to just draw one star in the window frame and trying to scale it up and down. I realized that every time I was resizing the window the JVM would call paintComponent and so I had to dynamically reset the window width and height by calling the parent (super()) JPanel’s getSize() methods and set the Flag’s width and height so every time the window frame was repainted the Union, Stripes and Stars could be redrawn with the scaled dimensions. I also learned that I needed to understand coordinate geometry really well in order to populate the x and y coordinate arrays and use ratios for all my calculations based on the flag height. Getting the Star right was the hardest part since I needed to understand polar coordinate system and be able to convert degrees to radians and then to polar coordinates and then to cartesian coordinates and plot the points on an inner circle and outer circle. At first when I tried to draw the Star I did not know in what order the points needed to be joined so I just populated the arrays in sequential order and called fillPolygon() which did not yield the result I had intended. After some more experimentation and testing I realized that I needed to populate even indexes and the value of each even index of the array to be on outer circle first and the populate odd indexes and value of each odd index to be on the inner circle. I also learned how to organize my code into classes and methods and that made it easier for me to test my code and make changes to it. The other advantage of modules is that when I wanted to make changes I could restrict it to only the portion of the code that needed edits instead of changing too many parts of the program. In addition I am now able to comment part of the code I don’t want to test and just make changes to the code I want to test and run the program since its organized by classes and methods. After I had most of the program working I went through the code line by line and where I had logic that was being repeated in multiple places I converted the repeating logic into a one method and called the same method from places where I needed to perform that particular piece of logic. I found this to be powerful and useful and am planning to make my programs in future as modular as possible from the beginning instead of putting it off to the end.


Acknowledgements


I would like to thank Mr. Kuszmaul for encouraging us to start planning the project ahead of time so we could work on it in manageable chunks. I also would like to thank my younger brother who was always happy to try out my application by resizing the frame and counting the stars and stripes and for asking me questions like, “Why the US Flag has 50 stars and 13 stripes?”
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: 41902787
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Inheritance/Subtyping - I used inheritance for Item, DraggableImageComponent, and DraggableComponents classes. I used inheritance to allow for the same methods to be used for different classes, while allowing for some modularity and comprehensibility.


  2. File I/O - I used File I/O to save the board when the window closes and to read a csv with all the possible items. File I/O allows for easy saving and data storage.

  3. Collections - I used collections to store all elements when the application is running. By doing so, it provides an easy way for data management.

  4. JUnit Testing - I used Junit Testing to ensure that my read and write methods run efficiently and convert strings into Item types accordingly.

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
DraggableComponents contain drag and mouse listeners to allow a JComponent to be dragged, and DraggableImageComponents adds an image to the JComponent. Item stores the variables I need to allow merging and collision to work. Finally, I added FileLineIterator to add the code needed to read a csv file.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
I had a lot of trouble implementing the merge method in the Item class. It had many bugs and I needed to sometimes redesign my whole data structure to fix it.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
I think that the way I seperated the classes allowed for my code to be easier to read. I felt that I could have redid my main() class more to only contain the actual loading of the JComponents rather than having that and additional methods. I would say that the private states are sometimes well encapsulated but there are some parts in the code that fail to do so.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

Assets: https://github.com/asdf1280/java-game-alchemystics/blob/master/src/assets/alchemystics/img
Draggable Components: https://www.youtube.com/watch?v=aedYlXutIDU
Inspiration https://littlealchemy.com/
Various StackOverflow Posts to bug fix
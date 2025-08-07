Java Command-Line Task Scheduler
This is a command-line task scheduler application built in Java. It allows users to manage a list of tasks, each with a title and a due date. The application demonstrates core Java skills in Object-Oriented Programming, File I/O, and Date and Time API manipulation.

Features
Add Tasks: Create new tasks with a unique ID, title, and due date.

View Tasks: Display all scheduled tasks, sorted by their due date.

Delete Tasks: Remove tasks by their unique ID.

Data Persistence: Tasks are automatically saved to a tasks.csv file upon exit and loaded when the program starts.

Command-Line Interface: Provides a simple, interactive menu for users to manage their tasks.

How to Run
Save the Files: Ensure you have the three Java files (Main.java, Task.java, and TaskManager.java) in the same directory.

Compile the Code: Open your terminal or command prompt, navigate to the project directory, and compile the code.

javac *.java

Run the Application: Execute the Main class.

java Main

The application will launch with a menu, allowing you to add, view, and delete tasks.

Code Structure
Main.java: The entry point of the application. It contains the main menu logic and handles all user input and output.

Task.java: A simple data class that models a single task, containing its ID, title, and due date.

TaskManager.java: The core business logic class. It manages the collection of tasks, handles file I/O for saving and loading data, and provides methods for all task management operations.

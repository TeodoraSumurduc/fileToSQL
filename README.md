**File to SQL Application**

**Overview**
File to SQL is a Java-based application designed to facilitate the conversion of data from various file formats into SQL statemants(Insers or Update specific Columns). It provides a command-line interface for users to interact with the system, allowing for efficient conversion and management of data.

**Features**
Convert data from files to SQL statemants
Support for multiple file formats
Simple command-line interface
Error handling and exception management
Flexible and customizable data mapping
Detailed logging and reporting

**Supported File Formats**
CSV (Comma-Separated Values)
JSON (JavaScript Object Notation)

**Types of Users**
File to SQL supports a single type of user:

     + Admin: Users who have full access to all commands and functionalities of the application.
     + Regular: Users who have acces to a part of functionalities of the application.
     + Anonymous: User who have acces to the commands: help, quit, register, login.
    
**Commands**

For all users:

    + Help: Display all available commands and their descriptions.
    + Quit: Exit the application.
    + Register: Register a new user and be authenticated as him.
    + Login: Login to your account.

For Admin and Regular users:

    + Logout: Logout from your account.
    + Load: Load a CSV/JSON file and generate SQL statements.
    + Audit: Display the last 5 commands history for a user.
    + Execute: Execute a specific SQL command from history.

For Admin users:

    + Promote: Make user an admin.
    + Create table: Add a table in the data base.
    + Insert column: Add a new line in the table with the column name and her type.

**Usage**

Launch the application by executing the main Java class.
Use the command-line interface to enter commands and interact with the application.
Follow the prompts and instructions to perform file conversion and SQL operations.
Notes
Ensure that the file paths are correctly specified for import and export operations.
Review the application logs for detailed information about the conversion process and any errors encountered.
Make sure to backup important data before performing any SQL operations to avoid data loss.

**Results**

This application returns a txt file that contains all the update or insert statements of the input file. We have the possibility to bind INSERT or UPDATE. For the UPDATE command, we must specify the columns for which we want to make these statements.



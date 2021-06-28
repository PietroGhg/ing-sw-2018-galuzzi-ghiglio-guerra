# Sagrada

This game was developed as a final project for the course of "Ingegneria del Software" @ [Politecnico di Milano](https://www.polimi.it/), A.Y. 2017-2018.

## Authors:
- Galuzzi Andrea
- Ghiglio Pietro
- Guerra Leonardo

## Implemented functionalities
- Complete rules of the game,
- CLI (Command Line Interface),
- Sockets,
- RMI (Remote Method Invocation),
- GUI (Graphical User Interface),
- Extra scheme-card uploading.

### Scheme-card uploading
Scheme-cards must be uploaded in a folder called "schemas", which is in the same directory of the Server jar file.
Scheme-cards are xml files, in the relative folder only one example has been uploaded.

### Special characters visualization
Windows terminal does not print correctly the dice characters and does not correctly format the text with the color set through ANSI escape (differently from Linux and Mac-OS).
To solve this problem, we used the jansi library, which allows to print in color, but does not solve the dice problem. On startup, is, therefore, asked to the user whether he/she wants to use jansi library. If not, we assume it is not necessary to print in color.

### Jar file opening
On Windows, JARs do not automatically open with a double click, so we added .bat file which simply contain the necessary commands to open them.

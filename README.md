Prova Finale di Ingegneria del Software a.a. 2017/2018

Galuzzi Andrea
Ghiglio Pietro
Guerra Leonardo

Funzionalità implementate:
-Regole complete
-CLI
-Socket
-RMI
-GUI
-Caricamento carte schema extra

Caricamento carte schema.
Le carte schema vanno inserite in una cartella denominata "schemas" nella stessa directory del file jar del Server.
Le carte schema sono file xml, nell'apposita cartella è stata inserita una carta di esempio.

Visualizzazione caratteri speciali.
Nell'implementazione della cli abbiamo riscontrato che il terminale di windows non stampa correttamente (a differenza di linux
e mac) i caratteri per i dadi, e non formatta il testo con i colori settati mediante ANSI escape.
Per ovviare a questo problema abbiamo utilizzato la libreria jansi, che consente di stampare a colori, ma non risolve il problema
dei dadi. All'avvio è quindi richiesto all'utente se vuole usare jansi oppure no, se l'utente dice di no assumiamo che non sia necessario
utilizzare jansi per una corretta visualizzazione, e quindi stampiamo a video anche i caratteri speciali per i dadi.
Se l'utente dice che vuole utilizzare jansi, non stampiamo i caratteri speciali (il valore del dado è indicato semplicemente con un numero).

Apertura file jar.
Abbiamo riscontrato che su windows i jar non si aprono automaticamente facendo doppio click, sono presenti quindi due file .bat che contengono
semplicemente il comando necessario a far partire i file jar.
# adSz - Puzzle

## Lizenz
Der Quellcode, die Bin�rdateien und die Ressourcen im Verzeichnis _/res_ des
Distributionsverzeichnisses unterliegen der GPLv3. Die Lizenz liegt in
englischer Sprache der Distribution bei. Eine deutschsprachige �bersetzung der
GPLv3 finden Sie hier: https://www.gnu.org/licenses/gpl-3.0.de.html

adSz-Puzzle nutzt die [LWJGL](http://www.lwjgl.org/) von Caspian Prince und
[Slick](http://slick.cokeandcode.com/) von Kevin Glass.

* [LWJGL Lizenz](http://www.lwjgl.org/license.php)
* [Slick Lizenz](http://slick.cokeandcode.com/static.php?page=license)

Die der Distribution beiliegenden Beispiele im Verzeichnis _/puzzle_
unterliegen keiner Lizenz.

## Haftungsausschluss

Die Software, ihr Quellcode und ihre Dokumentation wird "wie sie ist" und ohne
jede Gew�hrleistung f�r Funktion, Korrektheit oder Fehlerfreiheit zur Verf�gung
gestellt.

F�r jedweden direkten oder indirekten Schaden - insbesondere Schaden an anderer
Software, Schaden an Hardware, Schaden durch Nutzungsausfall und Schaden durch
Funktionsunt�chtigkeit der Software, kann der Autor nicht haftbar gemacht
werden. Ausschlie�lich der Benutzer haftet f�r die Folgen der Benutzung dieser
Software.

Diese Software wurde mit gr��ter Sorgfalt entwickelt, jedoch k�nnen Fehler
niemals ausgeschlossen werden. Es kann daher keine Gew�hr f�r die Sicherheit
Ihrer Daten �bernommen werden.

## Quellcode
Der Quellcode der Anwendung befindet sich im Verzeichnis _/src_ innerhalb der
ausgelieferten .jar-Datei. Dar�ber hinaus steht die jeweils aktuellste Version
des Quellcodes unter folgender Adresse zur Verf�gung:
https://github.com/obnoxint/adSz-Puzzle

## Systemanforderungen
Zus�tzlich zu den Systemanforderungen Ihres Betriebssystems bestehen folgende
Anforderungen zum Ausf�hren von adSz-Puzzle:

Software:

* [Oracle Java Runtime Environment](http://www.java.com/de/download/) (JRE)
Version 1.7 oder h�her
* OpenGL-kompatibler Grafikkartentreiber

Hardware:

* 128 MB Arbeitsspeicher
* 1 GHz CPU
* 16 MB Festplattenspeicher
* Maus oder vergleichbares Eingabeger�t

## Installation
1. Laden Sie die jeweils aktuellste Distribution von der Seite
[_Downloads_](https://github.com/obnoxint/adSz-Puzzle/downloads) herunter.
2. Entpacken Sie den Inhalt der .zip-Datei in ein beliebiges Verzeichnis. Achten
Sie darauf, dass Ihr Benutzerkonto f�r dieses Verzeichnis �ber Lese- und
Schreibrechte verf�gt.

## Starten der Anwendung
Wenn die Java Runtime Environment (JRE) korrekt installiert und konfiguriert
wurde, k�nnen Sie die Anwendung mit einem Doppelklick auf die .jar-Datei
starten.

Anderenfalls k�nnen Sie auch die beigef�gte .bat-Datei (f�r Windows-Nutzer) oder
.sh-Datei (f�r Linux-/Mac-Nutzer) verwenden. Dies setzt allerdings voraus, dass
die PATH-Umgebungsvariable korrekt auf die installierte JRE verweist.

Bitten Sie ggf. Ihren Systemadministrator um Unterst�tzung.

## Erstellen und konfigurieren eigener Puzzles
Ein Puzzle besteht aus den folgenden Dateien:

1. Eine Konfigurationsdatei mit der Dateinamenserweiterung _.properties_.
2. Einer .png-Bilddatei.
3. Einer optionalen zweiten .png-Bilddatei die ein Hintergrundbild darstellt,
das anstelle des noch ungel�sten Puzzles angezeigt werden soll.

### Konfigurationsdatei
1. Erstellen Sie mit einem beliebigen Texteditor eine Datei mit der
Dateinamenserweiterung _.properties_. Der Name der Datei sollte keine
Leerzeichen enthalten.
2. Kopieren Sie den folgenden Text und f�gen Sie ihn in die Textdatei ein:

    title=  
    image=  
    background=  
    maxDifficulty=

3. Tragen Sie hinter _title=_ einen beliebigen Titel f�r das Puzzle ein.
4. Tragen Sie hinter _image=_ den Dateinamen des Bildes ohne die
Dateinamenserweiterung (.png) ein.
5. Tragen Sie hinter _background=_ den Dateinamen des Hintergrundbildes ohne die
Dateinamenserweiterung (.png) ein.
6. Tragen Sie hinter _maxDifficulty=_ den Wert einer der Schwierigkeitsgrade
ein. Wenn das Puzzle �ber gro�e einfarbige Fl�chen verf�gt, sollte ein niedriger
Wert eingetragen werden.

### Schwierigkeitsgrade.
adSz-Puzzle unterst�tzt sieben Schwierigkeitsgrade f�r Puzzles. Anhand des vom
Spieler gew�hlten Schwierigkeitsgrads bestimmt die Anwendung die Gr��e der
Puzzleteile auf dem Spielfeld.

Der Wert _maxDifficulty=_ bestimmt den maximalen Schwierigkeitsgrad eines
Puzzles. D.h., dass alle Schwierigkeitsgrade niedriger und gleich dem
entsprechenden Wert vom Spieler ausgew�hlt werden k�nnen.

<u>M�gliche Schwierigkeitsgrade:</u>

<table width="400px" align="center">
  <tr align="center">
      <td width="10%"><b>Wert</b></td><td width="60%"><b>Kantenl�nge pro Teil in px</b></td><td width="30%"><b>Anz. der Teile</b></td>
  </tr>
  <tr align="center">
    <td><b>0</b></td><td>200</td><td>12</td>
  </tr>
  <tr align="center">
    <td><b>1</b></td><td>100</td><td>48</td>
  </tr>
  <tr align="center">
    <td><b>2</b></td><td>50</td><td>192</td>
  </tr>
  <tr align="center">
    <td><b>3</b></td><td>40</td><td>300</td>
  </tr>
  <tr align="center">
    <td><b>4</b></td><td>25</td><td>768</td>
  </tr>
  <tr align="center">
    <td><b>5</b></td><td>20</td><td>1200</td>
  </tr>
  <tr align="center">
    <td><b>6</b></td><td>10</td><td>4800</td>
  </tr>
</table>

### Puzzle- und Hintergrundbild
Die Puzzle-Bilddatei muss folgende Anforderungen erf�llen:

1. Sie muss im Portable Network Graphics (PNG) Format vorliegen.
2. Sie muss exakt 800 Pixel breit und 600 Pixel hoch sein.
3. Sie muss relativ zum _/puzzle_-Verzeichnis oder in diesem Verzeichnis
abgelegt sein.

F�r das Hintergrundbild gelten die gleichen Anforderungen.

## Unterst�tzung
Wenn Sie ein Problem melden oder einen Verbesserungsvorschlag einreichen
m�chten, nutzen Sie bitte den
[Issue-Tracker](https://github.com/obnoxint/adSz-Puzzle/issues) des Projekts
(GitHub-Konto erforderlich).

Bitte machen Sie im Fall eines Problemberichts folgende Angaben:

* Betriebssystem, -version und -architektur
* Programmversion
* Falls vorhanden, Inhalt der _error\_[Zahl].txt_-Datei.
* Welche Aktionen Sie durchgef�hrt haben, bevor der Fehler aufgetreten ist.

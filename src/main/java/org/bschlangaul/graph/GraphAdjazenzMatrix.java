package org.bschlangaul.graph;

import org.bschlangaul.helfer.Farbe;

/**
 * Klasse für einen ungerichteten, gewichteten Graphen.
 *
 * Als Datenstruktur wird eine Adjazenzmatrix verwendet.
 *
 * nach Schulbuch: Informatik 1 Oberstufe Oldenbourg Verlag
 */
public class GraphAdjazenzMatrix extends Graph {

  /**
   * Die aktuelle maximale Knotenanzahl.
   */
  protected int anzahlKnoten;

  /**
   * Feld der Knoten des Graphen. Der erste Knoten hat die Knotennummer 0.
   */
  protected Knoten[] knoten;

  /**
   * Das zweidimensionale Feld der Adjazenzmatrix.
   */
  protected int[][] matrix;

  /**
   * Konstruktor für Objekte der Klasse AdjazenzMatrix.
   *
   * Die maximale Anzahl der Knoten wird dabei festgelegt.
   *
   * @param maximaleKnotenAnzahl Anzahl der maximal möglichen Knoten
   */
  public GraphAdjazenzMatrix(int maximaleKnotenAnzahl) {
    initialisiereMatrix(maximaleKnotenAnzahl);
  }

  /**
   * Mit diesem Konstruktur kann eine Adjazenzmatrix durch das einfache
   * Graphenformat erzeugt werden.
   *
   * @param graphenFormat Ein String im einfachen Graphenformat.
   */
  public GraphAdjazenzMatrix(String graphenFormat) {
    EinfachesGraphenFormat format = new EinfachesGraphenFormat(graphenFormat);
    initialisiereMatrix(format.gibAnzahlKnoten());

    for (String knotenName : format.gibKnotenNamen()) {
      setzeKnoten(knotenName);
    }

    for (EinfachesGraphenFormat.Kante kante : format.gibKanten()) {
      if (kante.gerichtet) {
        setzeGerichteteKante(kante.von, kante.nach, (int) kante.gewicht);
      } else {
        setzeUngerichteteKante(kante.von, kante.nach, (int) kante.gewicht);
      }
    }
  }

  private void initialisiereMatrix(int maximaleAnzahlKnoten) {
    anzahlKnoten = 0;
    knoten = new Knoten[maximaleAnzahlKnoten];
    matrix = new int[maximaleAnzahlKnoten][maximaleAnzahlKnoten];
  }

  /**
   * Einfügen eines neuen Knoten in den Graphen.
   *
   * Wenn die maximale Anzahl an Knoten erreicht wird oder der Knoten bereits
   * eingefügt ist, dann erfolgt kein Einfügen. Ob der Knoten bereits eingefügt
   * ist, findet man mit der Methode gibKnotenNummer(name) heraus. In der Matrix
   * soll an der Position der Diagonalen eine 0 stehen. An den übrigen Plätzen bis
   * zu dieser Position wird eine -1 eingefügt, die in der Methode gibMatrixAus()
   * die Ausgabe einer Leerstelle bewirkt.
   *
   * @param knotenName Name des neuen Knotens, der dem Graphen hinzugefügt wird.
   */
  public int setzeKnoten(String knotenName) {
    super.setzeKnoten(knotenName);
    if ((anzahlKnoten < knoten.length) && (gibKnotenNummer(knotenName) == -1)) {
      int knotenNummer = anzahlKnoten;
      knoten[knotenNummer] = new Knoten(knotenName);
      matrix[knotenNummer][knotenNummer] = 0;
      for (int i = 0; i < anzahlKnoten; i++) {
        // Symmetrie, da ungerichteter Graph
        matrix[knotenNummer][i] = -1;
        matrix[i][knotenNummer] = -1;
      }
      anzahlKnoten = knotenNummer + 1;
      return knotenNummer;
    }
    return -1;
  }

  /**
   * Gibt die interne Nummer des Knoten.
   *
   * Wenn ein Knoten mit diesem Name nicht bekannt ist, wird -1 zurückgegeben.
   *
   * @param name Name des Knoten der gesucht wird.
   *
   * @return Indexnummer des Knotens im Knotenarray; 0 &#x3C;= x &#x3C;= anzahl-1
   *         bzw. -1
   */
  public int gibKnotenNummer(String name) {
    int i, ergebnis;

    ergebnis = -1;
    for (i = 0; (i < anzahlKnoten) && (ergebnis == -1); i++)
      if (knoten[i].gibName().equals(name))
        ergebnis = i;

    return ergebnis;
  }

  /**
   * Berechne das kleine Einzel-Kantengewicht aller Kanten.
   *
   * Diese Methode ist nützlich für die negativen Zahlen.
   * Dieser Wert ist z. B. nützlich, wenn die Adjazenz-Matrix in der Konsole
   * ausgegeben werden soll. Mit Hilfe dieses Wertes kann die Breite der Tabelle
   * bestimmt werden.
   *
   * @return Das Gewicht der Kante mit dem Minimalgewicht.
   */
  public int gibMinimalesGewicht() {
    int min = 0;
    for (int[] reihe : matrix) {
      for (int gewicht : reihe) {
        if (gewicht < min)
          min = gewicht;
      }
    }
    return min;
  }

  /**
   * Berechne das größte Einzel-Kantengewicht aller Kanten.
   *
   * Dieser Wert ist z. B. nützlich, wenn die Adjazenz-Matrix in der Konsole
   * ausgegeben werden soll. Mit Hilfe dieses Wertes kann die Breite der Tabelle
   * bestimmt werden.
   *
   * @return Das Gewicht der Kante mit dem Maximalgewicht.
   */
  public int gibMaximalesGewicht() {
    int max = 0;
    for (int[] reihe : matrix) {
      for (int gewicht : reihe) {
        if (gewicht > max)
          max = gewicht;
      }
    }
    return max;
  }

  /**
   * Berechne die maximale Textbreite der Knotennamen.
   *
   * @return Die maximale Textbreite in Anzahl an Zeichen.
   */
  public int gibMaximaleKnotennameTextbreite() {
    int max = -1;
    for (Knoten k : knoten) {
      int länge = k.gibName().length();
      if (länge > max)
        max = länge;
    }
    return max;
  }

  /**
   * Ein kombinierter Wert aus der maximalen Textbreite der Knotennamen und des
   * maximalen Kantengewichts.
   *
   * @return Die Anzahl an Zeichen die benötigt werden, damit sowohl alle
   *         Knotennamen als auch alle Gewichte in einer Spalte mit gewisser
   *         Breite abgebildet werden können.
   */
  public int gibSpaltenBreite() {
    int minGewicht = String.valueOf(gibMinimalesGewicht()).length();
    int maxGewicht = String.valueOf(gibMaximalesGewicht()).length();
    int maxKnoten = gibMaximaleKnotennameTextbreite();
    return Math.max(Math.max(minGewicht, maxGewicht), maxKnoten);
  }

  /**
   * Gibt die Bezeichnung eines Knotens mit der internen Knotennummer.
   *
   * @param knotenNummer Die Knotennummer des Knotens im Knotenarray; 0 &#x3C;= x
   *                     &#x3C;= anzahl-1
   *
   * @return Der Name des Knoten.
   */
  public String gibKnotenNamen(int knotenNummer) {
    if ((knotenNummer < anzahlKnoten) && (knotenNummer >= 0))
      return knoten[knotenNummer].gibName();
    else
      return "";
  }

  /**
   * Gib alle Knotennamen als Feld zurück.
   */
  public String[] gibAlleKnotenNamen() {
    String[] ausgabe = new String[gibKnotenAnzahl()];
    for (int i = 0; i < gibKnotenAnzahl(); i++) {
      ausgabe[i] = knoten[i].gibName();
    }
    return ausgabe;
  }

  /**
   * Füge eine Kante ein. Diese Methode ist in der allgemeinsten Form. Es gibt
   * weitere Methoden, die auf diese Methode aufbauen, die weniger Argumente
   * benötigen.
   *
   * Eine Kante ist durch einen Anfangsknoten und einen Endknoten festgelegt und
   * hat eine Gewichtung.
   *
   * @param von       Name des Anfangsknotens
   * @param nach      Name des Endknotens
   * @param gewicht   Gewichtung der Kante als Ganzzahl
   * @param gerichtet Ob die Kante gerichtet oder ungerichtet ist. Ist die Kante
   *                  ungerichtet werden zwei Einträge in die Matrix gesetzt.
   */
  public void setzeKante(String von, String nach, int gewicht, boolean gerichtet) {
    int vonNummer, nachNummer;
    vonNummer = gibKnotenNummer(von);
    nachNummer = gibKnotenNummer(nach);
    if ((vonNummer != -1) && (nachNummer != -1) && (vonNummer != nachNummer)) {
      matrix[vonNummer][nachNummer] = gewicht;
      if (!gerichtet)
        matrix[nachNummer][vonNummer] = gewicht;
    }
  }

  /**
   * Gibt die Adjazenzmatrix des Graphen in der Konsole formatiert aus.
   *
   * Matrixelemente, die mit -1 belegt sind, werden als Leerzeichen ausgegeben.
   * Als Spaltenbreite wurde hier 4 Zeichen gewählt.
   */
  public void gibMatrixAus() {
    int breite = 4;
    // Kopfzeile
    System.out.print("    ");
    for (int i = 0; i < anzahlKnoten; i++)
      System.out.print(knoten[i].gibNameFormatiert(breite));
    System.out.println();

    for (int i = 0; i < anzahlKnoten; i++) {
      System.out.print(knoten[i].gibNameFormatiert(breite));
      for (int j = 0; j < anzahlKnoten; j++)
        if (matrix[i][j] == 0) {
          System.out.print(Farbe.gelb("0") + "   ");
        } else if (matrix[i][j] != -1)
          System.out.print((matrix[i][j] + "   ").substring(0, breite));
        else
          System.out.print("    ");
      System.out.println();
    }
  }

  /**
   * Gib die Gewichtung einer Kante. Die Kante ist durch einen Anfangsknoten und
   * einen Endknoten festgelegt. Ist die Kante unbekannt, wird -1 ausgegeben.
   *
   * @param von  Name des Anfangsknotens
   * @param nach Name des Endknotens
   *
   * @return Gewichtung der Kante
   */
  int gibKanteGewicht(String von, String nach) {
    int vonNummer, nachNummer;

    vonNummer = gibKnotenNummer(von);
    nachNummer = gibKnotenNummer(nach);
    if ((vonNummer != -1) && (nachNummer != -1))
      return matrix[vonNummer][nachNummer];
    else
      return -1;
  }

  public static void main(String[] args) {
    GraphAdjazenzMatrix matrix = new GraphAdjazenzMatrix(20);

    matrix.setzeKnoten("A");
    matrix.setzeKnoten("B");
    matrix.setzeKnoten("C");
    matrix.setzeKnoten("D");
    matrix.setzeKnoten("E");
    matrix.setzeKnoten("F");
    matrix.setzeKnoten("G");
    matrix.setzeKnoten("H");
    matrix.setzeKnoten("J");
    matrix.setzeKnoten("K");

    matrix.setzeUngerichteteKante("A", "B", 1);
    matrix.setzeUngerichteteKante("A", "C", 1);

    matrix.setzeUngerichteteKante("B", "A", 1);
    matrix.setzeUngerichteteKante("B", "D", 1);
    matrix.setzeUngerichteteKante("B", "E", 1);

    matrix.setzeUngerichteteKante("C", "A", 1);
    matrix.setzeUngerichteteKante("C", "F", 1);
    matrix.setzeUngerichteteKante("C", "G", 1);

    matrix.setzeUngerichteteKante("D", "B", 1);
    matrix.setzeUngerichteteKante("D", "H", 1);

    matrix.setzeUngerichteteKante("E", "B", 1);
    matrix.setzeUngerichteteKante("E", "F", 1);

    matrix.setzeUngerichteteKante("F", "C", 1);
    matrix.setzeUngerichteteKante("F", "E", 1);
    matrix.setzeUngerichteteKante("F", "G", 1);
    matrix.setzeUngerichteteKante("F", "J", 1);

    matrix.setzeUngerichteteKante("G", "C", 1);
    matrix.setzeUngerichteteKante("G", "F", 1);

    matrix.setzeUngerichteteKante("H", "D", 1);

    matrix.setzeUngerichteteKante("J", "F", 1);

    matrix.setzeUngerichteteKante("K", "F", 1);

    matrix.gibMatrixAus();
  }
}
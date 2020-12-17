package org.bschlangaul.graph;

import java.util.ArrayList;
import java.util.Collections;

import org.bschlangaul.helfer.Farbe;

/**
 * Klasse für einen ungerichteten, gewichteten Graphen.
 *
 * Als Datenstruktur wird eine Adjazenzmatrix verwendet.
 *
 * nach Schulbuch: Informatik 1 Oberstufe Oldenbourg Verlag
 */

public class AdjazenzMatrix {
  // aktuelle Knotenanzahl.
  protected int anzahlKnoten;

  // Feld der Knoten des Graphen.
  protected Knoten[] knoten;

  // 2-dim Feld der Adjazenzmatrix.
  protected int[][] matrix;

  /**
   * Konstruktor für Objekte der Klasse GraphAdjazenzMatrix.
   *
   * Die maximale Anzahl der Knoten wird dabei festgelegt.
   *
   * @param maximaleKnoten Anzahl der maximal möglichen Knoten
   */
  public AdjazenzMatrix(int maximaleKnoten) {
    anzahlKnoten = 0;
    knoten = new Knoten[maximaleKnoten];
    matrix = new int[maximaleKnoten][maximaleKnoten];
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
   * @param name Name des neuen Knotens, der dem Graphen hinzugefügt wird.
   */
  public void fügeKnotenEin(String name) {
    if ((anzahlKnoten < knoten.length) && (gibKnotenNummer(name) == -1)) {
      knoten[anzahlKnoten] = new Knoten(name);
      matrix[anzahlKnoten][anzahlKnoten] = 0;
      for (int i = 0; i < anzahlKnoten; i++) {
        // Symmetrie, da ungerichteter Graph
        matrix[anzahlKnoten][i] = -1;
        matrix[i][anzahlKnoten] = -1;
      }
      anzahlKnoten = anzahlKnoten + 1;
    }
  }

  /**
   * Gibt die interne Nummer des Knoten.
   *
   * Wenn ein Knoten mit diesem Name nicht bekannt ist, wird -1 zurückgegeben.
   *
   * @param name Name des Knoten der gesucht wird.
   *
   * @return Indexnummer des Knotens im Knotenarray; 0 &#x3C;= x &#x3C;= anzahl-1 bzw. -1
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
   * Gibt die Bezeichnung eines Knotens mit der internen Knotennummer.
   *
   * @param knotenNummer Die Knotennummer des Knotens im Knotenarray; 0 &#x3C;= x &#x3C;=
   *                     anzahl-1
   *
   * @return name Name des Knoten
   */
  public String gibKnotenName(int knotenNummer) {
    if ((knotenNummer < anzahlKnoten) && (knotenNummer >= 0))
      return knoten[knotenNummer].gibName();
    else
      return "";
  }

  /**
   * Einfügen einer Kante in den Graphen.
   *
   * Eine Kante ist durch einen Anfangsknoten und einen Endknoten festgelegt und
   * hat eine Gewichtung.
   *
   * @param von        Name des Anfangsknotens
   * @param nach       Name des Endknotens
   * @param gewichtung Gewichtung der Kante als Ganzzahl
   */
  public void fügeKanteEin(String von, String nach, int gewichtung) {
    int vonNummer, nachNummer;

    vonNummer = gibKnotenNummer(von);
    nachNummer = gibKnotenNummer(nach);
    if ((vonNummer != -1) && (nachNummer != -1) && (vonNummer != nachNummer)) {
      matrix[vonNummer][nachNummer] = gewichtung;
      matrix[nachNummer][vonNummer] = gewichtung;
    }
  }

  /**
   * Einfügen von Knoten und Kanten eines ungerichteten, ungewichteten
   * Graphens. Die Knoten werden alphabetisch sortiert.
   *
   * @param kanten Eingabe in der Form “A-B A-C A-D” etc.
   */
  public void fügeKnotenUndKantenEin(String kanten) {
    String[] kantenFeld = kanten.split("[ \t\n\r]+");
    ArrayList<String> knoten = new ArrayList<String>();
    ArrayList<String[]> kantenListe = new ArrayList<String[]>();

    for (int i = 0; i < kantenFeld.length; i++) {
      String kante = kantenFeld[i];
      int index = kante.indexOf("-");
      String von = kante.substring(0, index);
      String nach = kante.substring(index + 1);
      if (!knoten.contains(von)) knoten.add(von);
      if (!knoten.contains(nach)) knoten.add(nach);
      kantenListe.add(new String[]{ von, nach });
    }

    Collections.sort(knoten);
    for (String knotenName : knoten) fügeKnotenEin(knotenName);
    for (String[] k : kantenListe) fügeKanteEin(k[0], k[1], 1);
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
   * Gibt die Anzahl der Knoten des Graphen.
   *
   * @return Anzahl der Knoten
   */
  int gibKnotenAnzahl() {
    return anzahlKnoten;
  }

  /**
   * Gibt die Gewichtung einer Kante Die Kante ist durch einen Anfangsknoten und
   * einen Endknoten festgelegt Ist die Kante unbekannt, wird -1 ausgegeben.
   *
   * @param von  Name des Anfangsknotens
   * @param nach Name des Endknotens
   * @return Gewichtung der Kante
   *
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
    AdjazenzMatrix matrix = new AdjazenzMatrix(20);

    matrix.fügeKnotenEin("A");
    matrix.fügeKnotenEin("B");
    matrix.fügeKnotenEin("C");
    matrix.fügeKnotenEin("D");
    matrix.fügeKnotenEin("E");
    matrix.fügeKnotenEin("F");
    matrix.fügeKnotenEin("G");
    matrix.fügeKnotenEin("H");
    matrix.fügeKnotenEin("J");
    matrix.fügeKnotenEin("K");

    matrix.fügeKanteEin("A", "B", 1);
    matrix.fügeKanteEin("A", "C", 1);

    matrix.fügeKanteEin("B", "A", 1);
    matrix.fügeKanteEin("B", "D", 1);
    matrix.fügeKanteEin("B", "E", 1);

    matrix.fügeKanteEin("C", "A", 1);
    matrix.fügeKanteEin("C", "F", 1);
    matrix.fügeKanteEin("C", "G", 1);

    matrix.fügeKanteEin("D", "B", 1);
    matrix.fügeKanteEin("D", "H", 1);

    matrix.fügeKanteEin("E", "B", 1);
    matrix.fügeKanteEin("E", "F", 1);

    matrix.fügeKanteEin("F", "C", 1);
    matrix.fügeKanteEin("F", "E", 1);
    matrix.fügeKanteEin("F", "G", 1);
    matrix.fügeKanteEin("F", "J", 1);

    matrix.fügeKanteEin("G", "C", 1);
    matrix.fügeKanteEin("G", "F", 1);

    matrix.fügeKanteEin("H", "D", 1);

    matrix.fügeKanteEin("J", "F", 1);

    matrix.fügeKanteEin("K", "F", 1);

    matrix.gibMatrixAus();
  }
}
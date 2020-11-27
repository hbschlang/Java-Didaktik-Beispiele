package org.bschlangaul.graph;

import org.bschlangaul.helfer.Farbe;
import java.util.Stack;

/**
 * nach Schulbuch: Informatik 1 Oberstufe Oldenbourg Verlag
 */
public class TiefenSucheStapel extends AdjazenzMatrix {
  // Liste der besuchten Knoten
  private boolean[] besucht;

  // Stapel für die Tiefensuche
  private Stack<String> stapel;
  private Stack<String> route;

  /**
   * Die maximale Anzahl der Knoten wird dabei festgelegt.
   *
   * @param maximaleKnoten Anzahl der maximal möglichen Knoten
   */
  public TiefenSucheStapel(int maximaleKnoten) {
    super(maximaleKnoten);
    besucht = new boolean[maximaleKnoten];
    stapel = new Stack<String>();
    route = new Stack<String>();
  }

  /**
   * Durchlauf aller Knoten und Ausgabe auf der Konsole
   *
   * @param knotenNummer Nummer des Startknotens
   */
  public void besucheKnoten(int knotenNummer) {
    besucht[knotenNummer] = true;
    stapel.push(knoten[knotenNummer].gibName());
    // Stapel ausgeben
    System.out.println(Farbe.grün("Stapel: ") + stapel.toString());
    while (!stapel.isEmpty()) {
      // oberstes Element des Stapels nehmen und in die Route einfügen
      String knotenName = stapel.pop();
      System.out.println(Farbe.rot("besucht: ") + knotenName);
      route.push(knotenName);

      // alle nicht besuchten Nachbarn von w in den Stapel einfügen
      for (int abzweigung = 0; abzweigung <= anzahlKnoten - 1; abzweigung++) {
        if (matrix[gibKnotenNummer(knotenName)][abzweigung] > 0 && !besucht[abzweigung]) {
          besucht[abzweigung] = true;
          stapel.push(knoten[abzweigung].gibName());
        }
      }
      // Stapel ausgeben
      System.out.println(Farbe.grün("Stapel: ") + stapel.toString());
    }
    // Route ausgeben
    System.out.println(Farbe.gelb("Route: ") + route.toString());
  }

  /**
   * Start der Tiefensuche
   *
   * @param startKnoten Bezeichnung des Startknotens
   */
  public void starteTiefenSucheStapel(String startKnoten) {
    int startnummer;
    startnummer = gibKnotenNummer(startKnoten);

    if (startnummer != -1) {
      for (int i = 0; i <= anzahlKnoten - 1; i++) {
        besucht[i] = false;
      }
      besucheKnoten(startnummer);
    }
  }

  public static void main(String[] args) {
    TiefenSucheStapel ts = new TiefenSucheStapel(20);

    ts.fügeKnotenEin("A");
    ts.fügeKnotenEin("B");
    ts.fügeKnotenEin("C");
    ts.fügeKnotenEin("D");
    ts.fügeKnotenEin("E");
    ts.fügeKnotenEin("F");
    ts.fügeKnotenEin("G");
    ts.fügeKnotenEin("H");
    ts.fügeKnotenEin("J");
    ts.fügeKnotenEin("K");

    ts.fügeKanteEin("A", "B", 1);
    ts.fügeKanteEin("A", "C", 1);

    ts.fügeKanteEin("B", "A", 1);
    ts.fügeKanteEin("B", "D", 1);
    ts.fügeKanteEin("B", "E", 1);

    ts.fügeKanteEin("C", "A", 1);
    ts.fügeKanteEin("C", "F", 1);
    ts.fügeKanteEin("C", "G", 1);

    ts.fügeKanteEin("D", "B", 1);
    ts.fügeKanteEin("D", "H", 1);

    ts.fügeKanteEin("E", "B", 1);
    ts.fügeKanteEin("E", "F", 1);

    ts.fügeKanteEin("F", "C", 1);
    ts.fügeKanteEin("F", "E", 1);
    ts.fügeKanteEin("F", "G", 1);
    ts.fügeKanteEin("F", "J", 1);

    ts.fügeKanteEin("G", "C", 1);
    ts.fügeKanteEin("G", "F", 1);

    ts.fügeKanteEin("H", "D", 1);

    ts.fügeKanteEin("J", "F", 1);

    ts.fügeKanteEin("K", "F", 1);

    ts.gibMatrixAus();

    ts.starteTiefenSucheStapel("A");
  }
}

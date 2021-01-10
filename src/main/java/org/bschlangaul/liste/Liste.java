package org.bschlangaul.liste;

/**
 * Implementierung einer einfach verketteten Liste.
 *
 * nach Saake/Sattler: Algorithmen und Datenstrukturen (Seite 326-329) heißt im
 * Original „List“.
 */
public class Liste {

  /**
   * Listenkopf
   */
  private ListenKnoten kopf = null;

  public Liste() {
    kopf = new ListenKnoten();
  }

  public void fügeErstesHinzu(Object datenObjekt) {
    // neuen Knoten hinter kopf einfügen
    ListenKnoten knoten = new ListenKnoten(datenObjekt, kopf.gibNächstes());
    kopf.setzteNächstes(knoten);
  }

  public void fügeLetztesHinzu(Object datenObjekt) {
    ListenKnoten tmp = kopf;
    // letzten Knoten ermitteln
    while (tmp.gibNächstes() != null)
      tmp = tmp.gibNächstes();
    ListenKnoten knoten = new ListenKnoten(datenObjekt, null);
    // neuen Knoten anfügen
    tmp.setzteNächstes(knoten);
  }

  public Object gibErstes() throws LeereListeFehler {
    if (istLeer())
      throw new LeereListeFehler();
    // erstes Element ist Nachfolger von kopf
    return kopf.gibNächstes().gibDaten();
  }

  public Object gibLetztes() throws LeereListeFehler {
    if (istLeer())
      throw new LeereListeFehler();
    ListenKnoten tmp = kopf;
    // letzten Knoten ermitteln
    while (tmp.gibNächstes() != null)
      tmp = tmp.gibNächstes();
    return tmp.gibDaten();
  }

  /**
   * Verweis von {@link kopf} aktualisieren
   *
   * @return Das entfernte Datenobjekt.
   *
   * @throws LeereListeFehler Leere Liste Fehler
   */
  public Object entferneErstes() throws LeereListeFehler {
    if (istLeer())
      throw new LeereListeFehler();
    Object datenObjekt = kopf.gibNächstes().gibDaten();
    kopf.setzteNächstes(kopf.gibNächstes().gibNächstes());
    return datenObjekt;
  }

  public Object entferneLetztes() throws LeereListeFehler {
    if (istLeer())
      throw new LeereListeFehler();
    ListenKnoten tmp = kopf;
    // vorletzten Knoten ermitteln
    while (tmp.gibNächstes().gibNächstes() != null)
      tmp = tmp.gibNächstes();
    Object datenObjekt = tmp.gibNächstes().gibDaten();
    // Verweis auf Nachfolger löschen
    tmp.setzteNächstes(null);
    return datenObjekt;
  }

  /**
   * „size()“
   *
   * @return Die Anzahl der Knoten.
   */
  public int gibAnzahl() {
    int anzahl = 0;
    ListenKnoten tmp = kopf;
    // Knoten zählen
    while (tmp.gibNächstes() != null) {
      anzahl++;
      tmp = tmp.gibNächstes();
    }
    return anzahl;
  }

  public boolean istLeer() {
    return kopf.gibNächstes() == null;
  }

  public static void main(String[] args) {
    Liste liste = new Liste();
    liste.fügeErstesHinzu(1);
    liste.fügeErstesHinzu(2);
    liste.fügeErstesHinzu(3);
    System.out.println(liste.gibAnzahl());
  }
}
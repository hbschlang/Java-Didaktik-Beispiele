package org.bschlangaul.baum;

/**
 * Saake Seite 357
 */
@SuppressWarnings({ "rawtypes" })
public class BinaerBaum extends Baum {

  /**
   * Saake Seite 349
   */
  public BinaerBaum() {
    kopf = new Knoten(null);
    nullKnoten = new Knoten(null);
    kopf.setzeRechts(nullKnoten);
    nullKnoten.setzeLinks(nullKnoten);
    nullKnoten.setzeRechts(nullKnoten);
  }

  public Knoten gibKopf() {
    return kopf.gibRechts();
  }

  /**
   * Füge einen Schlüssel in den Binären Baum ein. Vergleiche Saake Seite 363.
   *
   * @param schlüssel Ein Schlüssel, der eingefügt werden soll.
   *
   * @return Wahr, wenn das Einfügen erfolgreich war.
   */
  public boolean fügeEin(Comparable schlüssel) {
    Knoten eltern = kopf;
    Knoten kind = gibKopf();
    while (kind != nullKnoten) {
      eltern = kind;
      int vergleich = kind.vergleiche(schlüssel);
      if (vergleich == 0)
        return false;
      else
        kind = (vergleich > 0 ? kind.gibLinks() : kind.gibRechts());
    }
    Knoten knoten = new Knoten(schlüssel);
    if (eltern.vergleiche(schlüssel) > 0)
      eltern.setzeLinks(knoten);
    else
      eltern.setzeRechts(knoten);
    knoten.setzeLinks(nullKnoten);
    knoten.setzeRechts(nullKnoten);
    return true;
  }

  /**
   * Vergleiche Saake Seite 362.
   *
   * @param schlüssel
   * @return
   */
  protected Knoten findeKnoten(Comparable schlüssel) {
    Knoten knoten = gibKopf();
    while (knoten != nullKnoten) {
      int cmp = knoten.vergleiche(schlüssel);
      if (cmp == 0)
        return knoten;
      else
        knoten = (cmp > 0 ? knoten.gibLinks() : knoten.gibRechts());
    }
    return null;
  }

  /**
   * Finde einen Schlüssel im Binär Baum. Vergleiche Saake Seite 362.
   *
   * @param schlüssel Der Schlüssel, nach dem gesucht werden soll.
   *
   * @return Wahr, wenn der Schlüssel gefunden wurde.
   */
  public boolean finde(Comparable schlüssel) {
    return (findeKnoten(schlüssel) != null);
  }

  /**
   * Saake Seite 365-366
   *
   * @param schlüssel Der Schlüssel, der gelöscht werden soll.
   */
  public boolean entferne(Comparable schlüssel) {
    Knoten eltern = kopf;
    Knoten knoten = gibKopf();
    Knoten kind = null;
    Knoten tmp = null;

    // zu löschenden Knoten suchen
    while (knoten != nullKnoten) {
      int vergleich = knoten.vergleiche(schlüssel);
      if (vergleich == 0)
        break;
      else {
        eltern = knoten;
        knoten = (vergleich > 0 ? knoten.gibLinks() : knoten.gibRechts());
      }
    }
    if (knoten == nullKnoten)
      // Kein Knoten gefunden
      return false;
    // Fall 1
    if (knoten.gibLinks() == nullKnoten && knoten.gibRechts() == nullKnoten)
      kind = nullKnoten;
    // Fall 2
    else if (knoten.gibLinks() == nullKnoten)
      kind = knoten.gibRechts();
    else if (knoten.gibRechts() == nullKnoten)
      kind = knoten.gibLinks();
    else { // Fall 3
      // minimales Element suchen
      kind = knoten.gibRechts();
      tmp = knoten;
      while (kind.gibLinks() != nullKnoten) {
        tmp = kind;
        kind = kind.gibLinks();
      }
      kind.setzeLinks(knoten.gibLinks());
      if (tmp != knoten) {
        tmp.setzeLinks(kind.gibRechts());
        kind.setzeRechts(knoten.gibRechts());
      }
    }
    if (eltern.gibLinks() == knoten)
      eltern.setzeLinks(kind);
    else
      eltern.setzeRechts(kind);
    return true;
  }
}
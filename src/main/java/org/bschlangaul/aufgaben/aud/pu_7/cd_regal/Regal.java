package org.bschlangaul.aufgaben.aud.pu_7.cd_regal;

public class Regal {
  private Disk[][] regal = new Disk[5][30];

  /** * erzeugt eine neues (leeres) Regal */
  public Regal() {
    for (int i = 0; i < regal.length; i++) {
      for (int j = 0; j < regal[i].length; j++) {
        regal[i][j] = null;
      }
    }
  }

  /**
   * * berechnet die mittlere Bewertung für ein Regal
   *
   * @return mittlere Bewertung
   */
  public double mittlereBewertung() {
    int anzahl = 0;
    int bewertungspunkteGesamt = 0;
    for (int i = 0; i < regal.length; i++) {
      for (int j = 0; j < regal[i].length; j++) {
        if (regal[i][j] != null) {
          anzahl++;
          bewertungspunkteGesamt += regal[i][j].getBewertung();
        }
      }
    }
    if (anzahl == 0) {
      // falls Regal komplett leer ist
      return 0;
    }
    return bewertungspunkteGesamt / anzahl;
  }
}

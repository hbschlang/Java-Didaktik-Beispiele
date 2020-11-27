package org.bschlangaul.aufgaben.sosy.examen_66116_2019_09;

/**
 * Entspricht der „State“-Klasse in der Terminologie der Gang of
 * Four.
 */
abstract class ProzessZustand {

  Prozess prozess;

  String zustand;

  public ProzessZustand(String zustand, Prozess prozess) {
    this.zustand = zustand;
    this.prozess = prozess;
    System.out.println(String.format("Der Prozess ist im Zustand „%s“", zustand));
  }

  private void gibFehlermeldungAus(String transition) {
    System.err.println(
        String.format("Im Zustand „%s“ kann die Transition „%s“ nicht ausführt werden!",
            zustand, transition));
  }

  public void starten() {
    gibFehlermeldungAus("starten");
  }

  public void suspendieren() {
    gibFehlermeldungAus("suspendieren");
  }

  public void fortsetzen() {
    gibFehlermeldungAus("fortsetzen");
  }

  public void beenden() {
    gibFehlermeldungAus("beenden");
  }

  public void abbrechen() {
    gibFehlermeldungAus("abbrechen");
  }

}

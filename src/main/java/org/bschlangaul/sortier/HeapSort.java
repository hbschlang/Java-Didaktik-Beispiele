package org.bschlangaul.sortier;

/**
 * Sortiere ein Zahlen-Feld mit Hilfe des Heapsort-Algorithmus. (Nach Saake
 * Seite 412)
 */
public class HeapSort {
  private static void versickere(int[] zahlen, int index, int letzterIndex) {
    int i = index + 1, j;
    // zahlen[i] hat linken Sohn
    while (2 * i <= letzterIndex) {
      // zahlen[j] ist linker Sohn von zahlen[i]
      j = 2 * i;
      // zahlen[i] hat auch rechten Sohn
      if (j < letzterIndex)
        if (zahlen[j - 1] < zahlen[j])
          // zahlen[j] ist jetzt kleiner
          j++;
      if (zahlen[i - 1] < zahlen[j - 1]) {
        Helfer.vertausche(zahlen, i - 1, j - 1);
        // versickere weiter
        i = j;
      } else
        // halte an, heap-Bedingung erfüllt
        break;
    }
  }

  /**
   * Sortiere ein Zahlen-Feld mit Hilfe des Heapsort-Algorithmus.
   *
   * @param zahlen Ein Feld mit Zahlen, das sortiert werden soll.
   *
   * @return Das sortierte Zahlenfeld.
   */
  public static int[] sortiere(int[] zahlen) {
    int i;
    for (i = zahlen.length / 2; i >= 0; i--)
      versickere(zahlen, i, zahlen.length);
    for (i = zahlen.length - 1; i > 0; i--) {
      // tauscht jeweils letztes Element des Heaps mit dem ersten
      Helfer.vertausche(zahlen, 0, i);
      // heap wird von Position 0 bis i hergestellt
      versickere(zahlen, 0, i);
    }
    return zahlen;
  }
}

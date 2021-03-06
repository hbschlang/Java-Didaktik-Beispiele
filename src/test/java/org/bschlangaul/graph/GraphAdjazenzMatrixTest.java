package org.bschlangaul.graph;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class GraphAdjazenzMatrixTest {

  @Test
  public void methodeFügeKnotenEin() {
    GraphAdjazenzMatrix matrix = new GraphAdjazenzMatrix(5);
    matrix.setzeKnoten("A");
    assertEquals(1, matrix.gibKnotenAnzahl());
    matrix.setzeKnoten("B");
    assertEquals(2, matrix.gibKnotenAnzahl());
    matrix.setzeKnoten("C");
    assertEquals(3, matrix.gibKnotenAnzahl());
  }

  @Test
  public void methodeFügeKanteEinUngerichtet() {
    GraphAdjazenzMatrix matrix = new GraphAdjazenzMatrix(5);
    matrix.setzeKnoten("A");
    matrix.setzeKnoten("B");
    matrix.setzeUngerichteteKante("A", "B", 13);

    assertEquals(13, matrix.gibKanteGewicht("A", "B"), 0);
    assertEquals(13, matrix.gibKanteGewicht("B", "A"), 0);
  }

  @Test
  public void methodeFügeKanteEinGerichtet() {
    GraphAdjazenzMatrix matrix = new GraphAdjazenzMatrix(5);
    matrix.setzeKnoten("A");
    matrix.setzeKnoten("B");
    matrix.setzeGerichteteKante("A", "B", 13);

    assertEquals(13, matrix.gibKanteGewicht("A", "B"), 0);
    assertEquals(-Double.MAX_VALUE, matrix.gibKanteGewicht("B", "A"), 0);
  }

  @Test
  public void einfachesGraphenFormat() {
    GraphAdjazenzMatrix matrix = new GraphAdjazenzMatrix("a -> b: 7\na -- c: 3");
    assertEquals(3, matrix.gibKnotenAnzahl());
    assertEquals(7, matrix.gibKanteGewicht("a", "b"), 0);
    assertEquals(3, matrix.gibKanteGewicht("a", "c"), 0);
    assertEquals(3, matrix.gibKanteGewicht("c", "a"), 0);
    assertEquals(-1, matrix.gibKanteGewicht("a", "x"), 0);
  }

  @Test
  public void methodeGibMaximalesGewicht() {
    GraphAdjazenzMatrix matrix = new GraphAdjazenzMatrix("a -> b: 99;a -> c: 3;a--d: 1");
    assertEquals(99, matrix.gibMaximalesGewicht(), 0);
  }

  @Test
  public void methodeGibMaximaleKnotennameTextbreite() {
    GraphAdjazenzMatrix matrix = new GraphAdjazenzMatrix("abc--abcd;abc--abcde;abc--abcdef;");
    assertEquals(6, matrix.gibMaximaleKnotennameTextbreite());
  }

  @Test
  public void methodeGibSpaltenBreite() {
    GraphAdjazenzMatrix matrix = new GraphAdjazenzMatrix("abc--abcd;abc--abcde;abc--abcdef;");
    assertEquals(6, matrix.gibSpaltenBreite());

    GraphAdjazenzMatrix matrix2 = new GraphAdjazenzMatrix("a--b:123; a--c:12; a--d:1; ");
    assertEquals(3, matrix2.gibSpaltenBreite());
  }

}

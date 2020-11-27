package org.bschlangaul.aufgaben.aud.examen_66115_2015_03;

public class Stapel {
  public Element top;

  public Stapel() {
    top = null;
  }

  public int top() {
    return top.getValue();
  }

  /**
   * @param element Das Element, dass hinzugefügt werden soll zur Stapel.
   */
  public void push(int value) {
    Element element = new Element(value);
    element.setNext(top);
    top = element;
  }

  /**
   * @return Das Element oder null, wenn der Stapel leer ist.
   */
  public int pop() {
    Element element = top;
    top = top.getNext();
    return element.getValue();
  }

  /**
   * @return Wahr wenn der Stapel leer ist.
   */
  public boolean isEmpty() {
    return top == null;
  }

}

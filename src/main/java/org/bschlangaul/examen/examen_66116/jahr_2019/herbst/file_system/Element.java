package org.bschlangaul.examen.examen_66116.jahr_2019.herbst.file_system;

public abstract class Element {

  protected String name;

  protected Element parent;

  protected Element(String name, Element parent) {
    this.name = name;
    this.parent = parent;
  }

  public String getName() {
    return name;
  }

  public abstract void delete();

  public abstract boolean isDirectory();

  public abstract void addChild(Element child);
}

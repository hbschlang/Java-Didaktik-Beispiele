package org.bschlangaul.examen.examen_66116.jahr_2019.herbst.file_system;

import java.util.ArrayList;
import java.util.List;

public class Directory extends Element {
  private List<Element> children;

  public Directory(String name, Element parent) {
    super(name, parent);
    children = new ArrayList<Element>();
    if (parent != null)
      parent.addChild(this);
  }

  public void delete() {
    System.out.println("The directory “" + name + "” was deleted and it’s children were also deleted.");
    for (int i = 0; i < children.size(); i++) {
      Element child = children.get(i);
      child.delete();
    }
  }

  public void addChild(Element child) {
    children.add(child);
  }

  public boolean isDirectory() {
    return true;
  }
}

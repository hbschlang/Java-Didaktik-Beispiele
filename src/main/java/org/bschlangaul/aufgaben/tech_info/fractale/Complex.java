package org.bschlangaul.aufgaben.tech_info.fractale;

public interface Complex {
  public ComplexImpl plus(ComplexImpl z);

  public double betrag();

  public ComplexImpl mal(ComplexImpl z);

  public double nenneX();

  public double nenneY();
}

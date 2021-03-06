package org.bschlangaul.aufgaben.tech_info.fractale;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

public class Fractals {
  /**
   * Specifies when the computation is aborted
   */
  public static double MAXLENGTH = 2.0;

  /**
   * Computes a the number of iterations for a Mandelbrot/Julia-set see (
   * http://plus.maths.org/issue9/features/mandelbrot/ )
   *
   * @param start   the initial value of the iteration (z_0)
   * @param step    the increment in each iteration (c)
   * @param maxIter maximal number of iterations that are processed
   *
   * @return number of iterations between [0..maxIter]
   */
  public static int computeIterations(ComplexImpl start, ComplexImpl step, int maxIter) {
    for (int t = 0; t < maxIter; t++) {
      if (start.betrag() >= MAXLENGTH)
        return t;
      start = start.mal(start).plus(step);
    }
    return maxIter;
  }

  /**
   * Jeder Thread bearbeitet eine entsprechende Anzahl an Spalten des Bilds. Ein
   * Thread (hier der letzte) muss zusätzlich zu seinem Anteil den Rest erledigen,
   * der nicht mehr aufteilbar ist. Jeder Thread bekommt einen Streifen einer
   * bestimmten X-Breite zum Berechnen. Der letzte Thread bekommt unter Umständen
   * einen etwas breiteren Streifen (plus Rest)
   */
  public class Mandelbrot extends Thread {
    Color[][] pixel;
    int x;
    int xBegin;
    int xEnd;
    int y;
    Color[] palette;
    double realBegin;
    double imBegin;
    double realEnd;
    double imEnd;

    public Mandelbrot(Color[][] pixel, int x, int xBegin, int xEnd, int y, Color[] palette, double realBegin,
        double imBegin, double realEnd, double imEnd) {
      this.pixel = pixel;
      this.x = x;
      this.xBegin = xBegin;
      this.xEnd = xEnd;
      this.y = y;
      this.palette = palette;
      this.realBegin = realBegin;
      this.imBegin = imBegin;
      this.realEnd = realEnd;
      this.imEnd = imEnd;
    }

    public void run() {
      for (int i = xBegin; i < xEnd; i++) {
        for (int j = 0; j < y; j++) {
          ComplexImpl start = new ComplexImpl(0, 0);
          ComplexImpl step = new ComplexImpl(realBegin + ((realEnd - realBegin) / x * i),
              imBegin + ((imEnd - imBegin) / y * j));
          int maxIterations = computeIterations(start, step, palette.length - 1);
          pixel[i][j] = palette[maxIterations];
        }
      }
    }
  }

  /**
   * Creates a Mandelbrot-Picture with the size [x,y] using the area [realBegin +
   * (imBegin)i, realEnd + (imEnd)i)
   *
   * @param x         Number of pixels in x direction
   * @param y         Number of pixels in y direction
   * @param realBegin Real begin of the area
   * @param imBegin   Imaginary begin of the area
   * @param realEnd   Real end of the area
   * @param imEnd     Imaginary end of the area
   * @param palette   Number of used colors (limits the iteration count)
   * @param threads   Number of threads
   *
   * @return Pixel array of the picture
   */
  public Color[][] mandelbrot(int x, int y, double realBegin, double imBegin, double realEnd, double imEnd,
      Color[] palette, int threads) {
    Color[][] pixel = new Color[x][y];

    int streifenBreite = x / threads;

    int rest = x % threads;

    // Wir bauen ein Feld mit den x-Breiten, die ein Thread bearbeiten soll.
    // Über dieses Feld können wir dann iterien, und brauchen das Starten der
    // Threads nur einmal behandeln und nicht für den Rest extra.
    // z. B. x = 100, threads = 5: { 20, 20, 20, 20, 20 }
    // oder bei „krummen“ Werten:
    // z. B. x = 53, threads = 5: { 10, 10, 10, 10, 53 }
    int[] streifen;

    streifen = new int[threads];
    Arrays.fill(streifen, streifenBreite);

    if (rest > 0) {
      streifen[threads - 1] = streifenBreite + rest;
    }

    // Damit wir die Threads wieder einfangen können mit join().
    Mandelbrot[] m = new Mandelbrot[streifen.length];

    int i = 0;
    int xBegin = 0;
    int xEnd = xBegin;
    for (int breite : streifen) {
      xEnd = xBegin + breite;
      m[i] = new Mandelbrot(pixel, x, xBegin, xEnd, y, palette, realBegin, imBegin, realEnd, imEnd);
      m[i].start();
      i++;
      xBegin = xEnd;
    }

    for (Mandelbrot mandelbrot : m) {
      try {
        mandelbrot.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return pixel;
  }

  /**
   * Creates a Mandelbrot-Picture with the size [x,y] using the area [realBegin +
   * (imBegin)i, realEnd + (imEnd)i)
   *
   * @param x         Number of pixels in x direction
   * @param y         Number of pixels in y direction
   * @param realBegin Real begin of the area
   * @param imBegin   Imaginary begin of the area
   * @param realEnd   Real end of the area
   * @param imEnd     Imaginary end of the area
   * @param palette   Number of used colors (limits the iteration count)
   * @param threads   Number of threads
   *
   * @return Pixel array of the picture
   */
  public Color[][] mandelbrotSingleThreaded(int x, int y, double realBegin, double imBegin, double realEnd,
      double imEnd, Color[] palette, int threads) {
    Color[][] pixel = new Color[x][y];
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        ComplexImpl start = new ComplexImpl(0, 0);
        ComplexImpl step = new ComplexImpl(realBegin + ((realEnd - realBegin) / x * i),
            imBegin + ((imEnd - imBegin) / y * j));
        int maxIterations = computeIterations(start, step, palette.length - 1);
        pixel[i][j] = palette[maxIterations];
      }
    }
    return pixel;
  }

  /**
   * Jeder Thread bearbeitet zyklisch die ihm zugeordneten Spalten. (Die
   * Extrabehandlung des nicht mehr aufteilbaren Rests funktioniert ohne weiteres
   * Zutun.)
   */
  public class Julia extends Thread {
    Color[][] pixel;
    int x;
    int xStart;
    int threadAnzahl;
    int y;
    Color[] palette;
    double realBegin;
    double imBegin;
    double realEnd;
    double imEnd;
    ComplexImpl step;

    public Julia(Color[][] pixel, int x, int xStart, int threadAnzahl, int y, Color[] palette, double realBegin,
        double imBegin, double realEnd, double imEnd, ComplexImpl step) {
      this.pixel = pixel;
      this.x = x;
      this.xStart = xStart;
      this.threadAnzahl = threadAnzahl;
      this.y = y;
      this.palette = palette;
      this.realBegin = realBegin;
      this.imBegin = imBegin;
      this.realEnd = realEnd;
      this.imEnd = imEnd;
      this.step = step;
    }

    public void run() {
      int i = xStart;
      while (i < x) {
        for (int j = 0; j < y; j++) {
          ComplexImpl start = new ComplexImpl(realBegin + ((realEnd - realBegin) / x * i),
              imBegin + ((imEnd - imBegin) / y * j));
          int maxIterations = computeIterations(start, step, palette.length - 1);
          pixel[i][j] = palette[maxIterations];
        }
        i += threadAnzahl;
      }
    }
  }

  /**
   * Creates a Julia-Picture in parallel with the size [x,y] using the area
   * [realBegin + (imBegin)i, realEnd + (imEnd)i)
   *
   * @param x         Number of pixels in x direction
   * @param y         Number of pixels in y direction
   * @param palette   Number of used colors (limits the iteration count)
   * @param realBegin Real begin of the area
   * @param imBegin   Imaginary begin of the area
   * @param realEnd   Real end of the area
   * @param imEnd     Imaginary end of the area
   * @param step      The increment of the Julia-Set
   * @param threads   Number of threads
   *
   * @return Pixel array of the picture
   */
  public Color[][] julia(int x, int y, Color[] palette, double realBegin, double imBegin, double realEnd, double imEnd,
      ComplexImpl step, int threads) {
    Color[][] pixel = new Color[x][y];
    Julia[] j = new Julia[threads];

    for (int i = 0; i < threads; i++) {
      j[i] = new Julia(pixel, x, i, threads, y, palette, realBegin, imBegin, realEnd, imEnd, step);
      j[i].start();
    }

    for (Julia julia : j) {
      try {
        julia.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return pixel;
  }

  /**
   * Creates a Julia-Picture in parallel with the size [x,y] using the area
   * [realBegin + (imBegin)i, realEnd + (imEnd)i)
   *
   * @param x         Number of pixels in x direction
   * @param y         Number of pixels in y direction
   * @param palette   Number of used colors (limits the iteration count)
   * @param realBegin Real begin of the area
   * @param imBegin   Imaginary begin of the area
   * @param realEnd   Real end of the area
   * @param imEnd     Imaginary end of the area
   * @param step      The increment of the Julia-Set
   * @param threads   Number of threads
   *
   * @return Pixel array of the picture
   */
  public Color[][] juliaSingleThreaded(int x, int y, Color[] palette, double realBegin, double imBegin, double realEnd,
      double imEnd, ComplexImpl step, int threads) {
    Color[][] pixel = new Color[x][y];
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        ComplexImpl start = new ComplexImpl(realBegin + ((realEnd - realBegin) / x * i),
            imBegin + ((imEnd - imBegin) / y * j));
        int maxIterations = computeIterations(start, step, palette.length - 1);
        pixel[i][j] = palette[maxIterations];
      }
    }
    return pixel;
  }

  public static void main(String[] args) {
    int x = 500;
    int y = 500;
    Color[] palette = ColorPalette.createStandardGradient();
    Color[][] feld = new Fractals().mandelbrot(x, y, -1.5, -1, 0.5, 1, palette, 4);
    Canvas.show(feld, "full mandelbrot");
    Color[][] feld2 = new Fractals().mandelbrot(x, y, -0.87484, 0.22884, -0.85084, 0.25284, palette, 4);
    Canvas.show(feld2, "mandel 2");
    Color[][] feld3 = new Fractals().mandelbrot(x, y, -0.415, -0.683, -0.415 + 0.05, -0.683 + 0.05, palette, 4);
    Canvas.show(feld3, "mandel 3");
    Color[][] feld4 = new Fractals().julia(x, y, palette, -1, -1, 1, 1, new ComplexImpl(-0.81, -0.177), 4);
    Color[][] feld5 = new Fractals().julia(x, y, palette, -1, -1, 1, 1, new ComplexImpl(-0.8, 0.156), 4);
    Canvas.show(feld4, "Julia");
    Canvas.show(feld5, "Julia 2");
  }

  public static void saveImage(String path, Color[][] array, int x, int y) {
    // Helper method for saving a picture as an image file.
    BufferedImage img = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < array.length; i++) {
      for (int j = 0; j < array[i].length; j++) {
        img.setRGB(i, j, array[i][j].getRGB());
      }
    }

    File f = new File(path);
    try {
      ImageIO.write(img, "png", f);
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }

}

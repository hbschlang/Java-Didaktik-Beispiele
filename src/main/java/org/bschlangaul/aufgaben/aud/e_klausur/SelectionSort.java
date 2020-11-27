package org.bschlangaul.aufgaben.aud.e_klausur;

public class SelectionSort {

  static void swap(int[] arr, int i, int j) {
    int tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

  public static void selectionSortIterativ(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
      int min = i;
      for (int j = i + 1; j < arr.length; j++) {
        if (arr[j] < arr[min]) {
          min = j;
        }
      }
      swap(arr, i, min);
    }
  }

  public static void selectionSortRekursiv(int[] arr, int i, int n) {
    if (i == n) {
      return;
    }
    int min = i;
    for (int j = i + 1; j <= n; j++) {
      if (arr[j] < arr[min]) {
        min = j;
      }
    }
    swap(arr, i, min);
    selectionSortRekursiv(arr, i + 1, n);
  }

  public static void main(String[] args) {
    int[] zahlen = { 3, 5, 8, 4, 1, 9, -2 };
    // selectionSortIterativ(zahlen);
    selectionSortRekursiv(zahlen, 0, zahlen.length - 1);
    for (int i = 0; i < zahlen.length; i++) {
      System.out.print(zahlen[i] + " ");
    }
  }

}

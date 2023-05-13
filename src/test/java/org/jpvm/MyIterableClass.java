package org.jpvm;

import java.util.Iterator;

public class MyIterableClass implements Iterable<Integer> {
  private Integer[] elements;

  public MyIterableClass(Integer[] elements) {
    this.elements = elements;
  }

  @Override
  public Iterator<Integer> iterator() {
    return new MyIterator();
  }

  private class MyIterator implements Iterator<Integer> {
    private int currentIndex = 0;

    @Override
    public boolean hasNext() {
      return currentIndex < elements.length;
    }

    @Override
    public Integer next() {
      return elements[currentIndex++];
    }
  }

  public static void main(String[] args) {
    Integer[] numbers = {1, 2, 3, 4, 5};
    MyIterableClass iterableClass = new MyIterableClass(numbers);

    for (Integer number : iterableClass) {
      System.out.println(number);
    }
  }
}

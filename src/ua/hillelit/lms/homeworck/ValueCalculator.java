package ua.hillelit.lms.homeworck;

import java.util.Objects;

public class ValueCalculator implements Runnable {

  private final int len;
  private float[] a1;
  private float[] a2;

  public ValueCalculator(int len) {
    this.len = len;
  }

  public void doCalc() {

    long start = System.currentTimeMillis();

    int half = len / 2;
    float[] arr = getArr();

    splitArray(arr, half);

    Thread thread1 = new Thread(this, "Thread1");
    Thread thread2 = new Thread(this, "Thread2");

    thread1.start();
    thread2.start();

    try {
      thread1.join();
      thread2.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    mergeTwoArrays(arr, half);

    System.out.println("Run time = [" + (System.currentTimeMillis() - start) + " ms]");

  }

  private float[] getArr() {
    float[] arr = new float[len];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = i;
    }
    return arr;
  }

  private void splitArray(float[] arr, int half) {
    a1 = new float[half];
    a2 = new float[half];

    System.arraycopy(arr, 0, a1, 0, half);
    System.arraycopy(arr, half, a2, 0, half);
  }

  @Override
  public void run() {
    if (Objects.equals(Thread.currentThread().getName(), "Thread1")) {
      calcNewValue(a1);
    }

    if (Objects.equals(Thread.currentThread().getName(), "Thread2")) {
      calcNewValue(a2);
    }
  }

  private void calcNewValue(float[] arr) {
    for (int i = 0; i < arr.length; i++) {
      arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(
          0.4f + i / 2));
    }
  }

  private void mergeTwoArrays(float[] arr, int half) {
    System.arraycopy(a1, 0, arr, 0, half);
    System.arraycopy(a2, 0, arr, half, half);
  }

}

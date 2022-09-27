package ua.hillelit.lms.homeworck;

public class ValueCalculator {

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

    runThreads();

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

  private void runThreads() {
    Thread thread1 = new Thread(new MyThread(a1));
    Thread thread2 = new Thread(new MyThread(a2));

    thread1.start();
    thread2.start();

    try {
      thread1.join();
      thread2.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void mergeTwoArrays(float[] arr, int half) {
    System.arraycopy(a1, 0, arr, 0, half);
    System.arraycopy(a2, 0, arr, half, half);
  }

}

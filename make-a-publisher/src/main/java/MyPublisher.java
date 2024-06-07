import java.util.Random;
import java.util.concurrent.Flow;

public class MyPublisher implements Flow.Publisher<String> {

  private final long maxElements;

  public MyPublisher(long maxElements) {
    this.maxElements = maxElements;
  }

  @Override
  public void subscribe(Flow.Subscriber<? super String> subscriber) {

  }

  public static void main(String[] args) {

  }
}

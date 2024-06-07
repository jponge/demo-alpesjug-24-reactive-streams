import org.reactivestreams.tck.TestEnvironment;
import org.reactivestreams.tck.flow.FlowPublisherVerification;

import java.util.concurrent.Flow;

public class TckTest extends FlowPublisherVerification<String> {

  public TckTest() {
    super(new TestEnvironment());
  }

  @Override
  public Flow.Publisher<String> createFlowPublisher(long elements) {
    return new MyPublisher(elements);
  }

  @Override
  public Flow.Publisher<String> createFailedFlowPublisher() {
    return null;
  }
}

package in.vaidicjoshi.geektrust.backend.family.exceptions;

/**
 * @author Vaidic Joshi
 * @date 18/09/21
 */
public class MemberAdditionFailedException extends Exception {
  private static final long serialVersionUID = 6501613020826290356L;

  /** Constructs a ChildAdditionFailedException with no detail message. */
  public MemberAdditionFailedException() {
    super();
  }

  /**
   * Constructs a ChildAdditionFailedException with the specified detail message. A detail message
   * is a String that describes this particular exception.
   *
   * @param message
   */
  public MemberAdditionFailedException(String message) {
    super(message);
  }
}

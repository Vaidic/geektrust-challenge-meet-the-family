package in.vaidicjoshi.geektrust.backend.family.exceptions;

/**
 * @author Vaidic Joshi
 * @date 17/09/21
 */
public class MemberNotFoundException extends Exception {
  private static final long serialVersionUID = 3995889505128821939L;

  /** Constructs a MemberNotFoundException with no detail message. */
  public MemberNotFoundException() {
    super();
  }

  /**
   * Constructs a MemberNotFoundException with the specified detail message. A detail message is a
   * String that describes this particular exception.
   *
   * @param message
   */
  public MemberNotFoundException(String message) {
    super(message);
  }
}

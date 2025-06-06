package nz.ac.auckland.se281;

public class InvalidCountryNameException extends Exception {
  public InvalidCountryNameException(String country) {
    super(MessageCli.INVALID_COUNTRY.getMessage(country));
  }
}

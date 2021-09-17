package in.vaidicjoshi.geektrust.backend.family.utils;

import in.vaidicjoshi.geektrust.backend.family.enums.Gender;
import in.vaidicjoshi.geektrust.backend.family.enums.SupportedCommand;
import in.vaidicjoshi.geektrust.backend.family.exceptions.MemberAdditionFailedException;
import in.vaidicjoshi.geektrust.backend.family.exceptions.MemberNotFoundException;
import in.vaidicjoshi.geektrust.backend.family.model.FamilyTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.zip.DataFormatException;

/**
 * @author Vaidic Joshi
 * @date 18/09/21
 */
public class FIleProcessor {

  public static void executeCommandsFromFile(String file, FamilyTree familyTree)
      throws IOException {
    try (Stream<String> lines = Files.lines(Paths.get(file))) {
      lines
          .filter((line) -> !isLineEmptyOrComment(line))
          .forEach(
              (line) -> {
                try {
                  processLineAsCommand(line, familyTree);
                } catch (Exception e) {
                  throw new RuntimeException(e);
                }
              });

    } catch (IOException e) {
      throw new IOException("The filepath " + file + "is not reachable");
    }
  }

  /**
   * Processes a Sting as command followed by the required arguments.
   *
   * @param line
   * @param familyTree
   * @throws DataFormatException
   * @throws MemberAdditionFailedException
   * @throws MemberNotFoundException
   * @throws IllegalStateException
   */
  public static void processLineAsCommand(String line, FamilyTree familyTree)
      throws DataFormatException, MemberAdditionFailedException, MemberNotFoundException,
          IllegalStateException {
    String[] commandWithParams = line.split(" ");
    switch (SupportedCommand.valueOf(commandWithParams[0])) {
      case ADD_SPOUSE:
        validateParamsCardinality(commandWithParams, 2);
        familyTree.addSpouse(commandWithParams[1], commandWithParams[2]);
        System.out.printf(
            "Spouse %s to %s was added successfully.\n",
            commandWithParams[2], commandWithParams[1]);
        break;
      case ADD_CHILD:
        validateParamsCardinality(commandWithParams, 3);
        familyTree.addChild(
            commandWithParams[1], commandWithParams[2], Gender.valueOf(commandWithParams[3]));
        System.out.printf(
            "Child <%s, %s> was added successfully to %s.\n",
            commandWithParams[2], commandWithParams[3], commandWithParams[1]);
        break;
      case GET_RELATIONSHIP:

      default:
        throw new DataFormatException("Invalid Command " + commandWithParams[0] + " was supplied");
    }
  }

  /**
   * Validates that the required number of parameters are passed along with the command.
   *
   * @param commandWithParams
   * @param len
   * @return
   */
  private static void validateParamsCardinality(String[] commandWithParams, int len)
      throws DataFormatException {
    if (commandWithParams.length != len + 1) {
      String errorMessage =
          String.format(
              "Command %s requires %s parameters, however %s were supplied ",
              commandWithParams[0], len, commandWithParams.length - 1);
      throw new DataFormatException(errorMessage);
    }
  }

  /**
   * Returns true if a String starts with # or has just whitespaces.
   *
   * @param line
   * @return
   */
  public static boolean isLineEmptyOrComment(String line) {
    return (line.trim().isEmpty() || line.startsWith("#"));
  }
}

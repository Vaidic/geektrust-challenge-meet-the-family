package in.vaidicjoshi.geektrust.backend.family.utils;

import in.vaidicjoshi.geektrust.backend.family.enums.Gender;
import in.vaidicjoshi.geektrust.backend.family.enums.SupportedCommand;
import in.vaidicjoshi.geektrust.backend.family.enums.SupportedRelationship;
import in.vaidicjoshi.geektrust.backend.family.exceptions.MemberAdditionFailedException;
import in.vaidicjoshi.geektrust.backend.family.exceptions.MemberNotFoundException;
import in.vaidicjoshi.geektrust.backend.family.model.FamilyTree;
import in.vaidicjoshi.geektrust.backend.family.service.RelationshipService;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.DataFormatException;

/**
 * @author Vaidic Joshi
 * @date 18/09/21
 */
@Log4j2
public class FIleProcessor {

  private static final String CHILD_ADDITION_SUCCEEDED = "CHILD_ADDITION_SUCCEEDED";
  private static final String CHILD_ADDITION_FAILED = "CHILD_ADDITION_FAILED";
  private static final String PERSON_NOT_FOUND = "PERSON_NOT_FOUND";

  /**
   * Processes a file as input stream and executes all valid commands on it.
   *
   * @param inputStream
   * @param familyTree
   * @return
   */
  public static List<String> executeCommandsFromFile(
      InputStream inputStream, FamilyTree familyTree) {
    try (Stream<String> lines =
        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines(); ) {
      return processFile(familyTree, lines);
    }
  }

  /**
   * Processes a file and executes all valid commands on it.
   *
   * @param file
   * @param familyTree
   * @throws IOException
   * @return
   */
  public static List<String> executeCommandsFromFile(String file, FamilyTree familyTree)
      throws IOException {
    try (Stream<String> lines = Files.lines(Paths.get(file))) {
      return processFile(familyTree, lines);

    } catch (IOException e) {
      throw new IOException("The filepath " + file + " is not reachable");
    }
  }

  /**
   * Processes all the lines with valid command of the given file and displays output.
   *
   * @param familyTree
   * @param lines
   * @return
   */
  private static List<String> processFile(FamilyTree familyTree, Stream<String> lines) {
    return lines
        .filter((line) -> !isLineEmptyOrComment(line))
        .map(
            (line) -> {
              try {
                return processLineAsCommand(line, familyTree);
              } catch (MemberAdditionFailedException e) {
                log.error(CHILD_ADDITION_FAILED);
                return CHILD_ADDITION_FAILED;
              } catch (DataFormatException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
              } catch (MemberNotFoundException e) {
                log.error(PERSON_NOT_FOUND);
                return PERSON_NOT_FOUND;
              }
            })
        .filter(res -> !isBlank(res))
        .collect(Collectors.toList());
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
  public static String processLineAsCommand(String line, FamilyTree familyTree)
      throws DataFormatException, MemberAdditionFailedException, MemberNotFoundException,
          IllegalStateException {
    String[] commandWithParams = line.split(" ");
    switch (SupportedCommand.valueOf(commandWithParams[0])) {
      case ADD_SPOUSE:
        validateParamsCardinality(commandWithParams, 2);
        familyTree.addSpouse(commandWithParams[1], commandWithParams[2]);
        log.debug(
            "Spouse {} to {} was added successfully.\n",
            commandWithParams[2],
            commandWithParams[1]);
        return "";
      case ADD_CHILD:
        validateParamsCardinality(commandWithParams, 3);
        familyTree.addChild(
            commandWithParams[1], commandWithParams[2], Gender.valueOf(commandWithParams[3]));
        log.debug(
            "Child {} to {} was added successfully.\n", commandWithParams[1], commandWithParams[2]);
        return CHILD_ADDITION_SUCCEEDED;
      case GET_RELATIONSHIP:
        validateParamsCardinality(commandWithParams, 2);
        String relationship =
            RelationshipService.getRelationship(
                familyTree, commandWithParams[1], SupportedRelationship.get(commandWithParams[2]));
        log.debug(
            "Relationship {} for {} are {}",
            commandWithParams[2],
            commandWithParams[1],
            relationship);
        return relationship;
      default:
        log.error("Invalid Command {} was supplied", commandWithParams[0]);
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

  /**
   * Returns true if a String is null or blank.
   *
   * @param str
   * @return
   */
  private static boolean isBlank(String str) {
    return Objects.isNull(str) || str.trim().isEmpty();
  }
}

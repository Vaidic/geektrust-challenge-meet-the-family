package in.vaidicjoshi.geektrust.backend.family;

import in.vaidicjoshi.geektrust.backend.family.exceptions.MemberAdditionFailedException;
import in.vaidicjoshi.geektrust.backend.family.exceptions.MemberNotFoundException;
import in.vaidicjoshi.geektrust.backend.family.model.FamilyTree;
import in.vaidicjoshi.geektrust.backend.family.utils.FIleProcessor;
import in.vaidicjoshi.geektrust.backend.family.utils.FamilyTreeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static in.vaidicjoshi.geektrust.backend.family.utils.FIleProcessor.isBlank;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Vaidic Joshi
 * @date 18/09/21
 */
class FamilyAppE2ETest {
  private FamilyTree familyTree;

  @BeforeEach
  void setUp() throws MemberAdditionFailedException, IOException, MemberNotFoundException {
    familyTree = FamilyTreeUtils.initFamilyTree();
  }

  /**
   * Takes a file as input and compares the outcome with another file containing expected output.
   *
   * @throws IOException
   */
  @Test
  void endToEndTest() throws IOException {

    String inputFile =
        Objects.requireNonNull(this.getClass().getClassLoader().getResource("testInputFile.txt"))
            .getFile();
    String outputFile =
        Objects.requireNonNull(this.getClass().getClassLoader().getResource("testOutputFile.txt"))
            .getFile();

    List<String> output = FIleProcessor.executeCommandsFromFile(inputFile, familyTree);
    try (Stream<String> lines = Files.lines(Paths.get(outputFile))) {
      String expectedResult = lines.filter(str -> !isBlank(str)).collect(Collectors.joining(";"));
      assertEquals(expectedResult, String.join(";", output));
    }
  }
}

package in.vaidicjoshi.geektrust.backend.family.utils;

import in.vaidicjoshi.geektrust.backend.family.exceptions.MemberAdditionFailedException;
import in.vaidicjoshi.geektrust.backend.family.exceptions.MemberNotFoundException;
import in.vaidicjoshi.geektrust.backend.family.model.FamilyNode;
import in.vaidicjoshi.geektrust.backend.family.model.FamilyTree;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static in.vaidicjoshi.geektrust.backend.family.enums.Gender.FEMALE;

/**
 * @author Vaidic Joshi
 * @date 18/09/21
 */
public class FamilyTreeUtils {
  public static FamilyTree initFamilyTree()
      throws MemberAdditionFailedException, MemberNotFoundException, IOException {
    FamilyTree familyTree = new FamilyTree();
    FamilyNode queen = familyTree.addMember("QueenAnga", FEMALE);
    familyTree.addSpouse("QueenAnga", "KingShan");
    loadFamilyFromFile(familyTree);
    return familyTree;
  }

  public static void loadFamilyFromFile(FamilyTree familyTree) throws IOException {

    InputStream inputStream = Objects.requireNonNull(
            FamilyTreeUtils.class.getClassLoader().getResourceAsStream("initializeFamily.txt"));
    FIleProcessor.executeCommandsFromFile(inputStream, familyTree);
  }
}

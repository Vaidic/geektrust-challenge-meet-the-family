package in.vaidicjoshi.geektrust.backend.family;

import in.vaidicjoshi.geektrust.backend.family.model.FamilyTree;
import in.vaidicjoshi.geektrust.backend.family.utils.FIleProcessor;
import in.vaidicjoshi.geektrust.backend.family.utils.FamilyTreeUtils;

import java.util.InputMismatchException;
import java.util.List;

/**
 * @author Vaidic Joshi
 * @date 17/09/21
 */
public class FamilyApp {

  public static void main(String[] args) {
    try {
      if (args.length < 1) {
        throw new InputMismatchException("Please specify the input file as argument");
      }
      FamilyTree familyTree = FamilyTreeUtils.initFamilyTree();
      List<String> commandOutputs = FIleProcessor.executeCommandsFromFile(args[0], familyTree);
      commandOutputs.forEach(System.out::println);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}

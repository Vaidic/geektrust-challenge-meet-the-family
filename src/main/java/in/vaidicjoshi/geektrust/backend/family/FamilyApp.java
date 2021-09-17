package in.vaidicjoshi.geektrust.backend.family;

import in.vaidicjoshi.geektrust.backend.family.model.FamilyTree;
import in.vaidicjoshi.geektrust.backend.family.utils.FIleProcessor;
import in.vaidicjoshi.geektrust.backend.family.utils.FamilyTreeUtils;

import java.util.InputMismatchException;

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
      FIleProcessor.executeCommandsFromFile(args[1], familyTree);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}

package in.vaidicjoshi.geektrust.backend.family.utils;

import in.vaidicjoshi.geektrust.backend.family.model.FamilyMember;
import in.vaidicjoshi.geektrust.backend.family.model.FamilyNode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vaidic Joshi
 * @date 18/09/21
 */
public class FamilyRelationshipResolver {

  public static List<String> getSon(FamilyNode member) {
    return member.getChildren().stream()
        .map(FamilyNode::getMaleMember)
        .map(FamilyMember::getName)
        .collect(Collectors.toList());
  }

  public static List<String> getPaternalUncle(FamilyNode member) {}

  public static List<String> getMaternalUncle(FamilyNode member) {}

  public static List<String> getPaternalAunt(FamilyNode member) {}

  public static List<String> getMaternalAunt(FamilyNode member) {}

  public static List<String> getSisterInLaw(FamilyNode member) {}

  public static List<String> getBrotherInLaw(FamilyNode member) {}

  public static List<String> getDaughter(FamilyNode member) {
    return member.getChildren().stream()
            .map(FamilyNode::getMaleMember)
            .map(FamilyMember::getName)
            .collect(Collectors.toList());
  }

  public static List<String> getSiblings(FamilyNode member) {}
}

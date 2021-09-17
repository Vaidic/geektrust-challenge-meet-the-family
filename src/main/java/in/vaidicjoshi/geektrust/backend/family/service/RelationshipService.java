package in.vaidicjoshi.geektrust.backend.family.service;

import in.vaidicjoshi.geektrust.backend.family.enums.SupportedRelationship;
import in.vaidicjoshi.geektrust.backend.family.exceptions.MemberNotFoundException;
import in.vaidicjoshi.geektrust.backend.family.model.FamilyNode;
import in.vaidicjoshi.geektrust.backend.family.model.FamilyTree;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

/**
 * @author Vaidic Joshi
 * @date 18/09/21
 */
public class RelationshipService {

  public static String getRelationship(
      FamilyTree familyTree, String memberName, SupportedRelationship relationship)
      throws MemberNotFoundException {
    FamilyNode member = familyTree.getMember(memberName);
    List<String> result = relationship.getRelationResolver().apply(member);
    return result.isEmpty() ? "NONE" : Strings.join(result, ' ');
  }
}

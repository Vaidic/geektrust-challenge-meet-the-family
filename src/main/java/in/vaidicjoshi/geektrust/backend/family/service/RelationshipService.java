package in.vaidicjoshi.geektrust.backend.family.service;

import in.vaidicjoshi.geektrust.backend.family.enums.SupportedRelationship;
import in.vaidicjoshi.geektrust.backend.family.model.FamilyTree;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

/**
 * @author Vaidic Joshi
 * @date 18/09/21
 */
public class RelationshipService {

  public static String getRelationship(
      FamilyTree familyTree, String memberName, SupportedRelationship relationship) {
    List<String> result = relationship.getRelationResolver().apply(familyTree, memberName);
    return result.isEmpty() ? "NONE" : Strings.join(result, ' ');
  }
}

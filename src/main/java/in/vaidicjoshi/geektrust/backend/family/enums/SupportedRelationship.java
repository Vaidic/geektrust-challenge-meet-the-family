package in.vaidicjoshi.geektrust.backend.family.enums;

import in.vaidicjoshi.geektrust.backend.family.model.FamilyTree;
import in.vaidicjoshi.geektrust.backend.family.utils.FamilyRelationshipResolver;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.BiFunction;

/**
 * @author Vaidic Joshi
 * @date 18/09/21
 */
@Getter
@RequiredArgsConstructor
public enum SupportedRelationship {
  PATERNAL_UNCLE("Paternal-Uncle", FamilyRelationshipResolver::getPaternalUncle),
  MATERNAL_UNCLE("Maternal-Uncle", FamilyRelationshipResolver::getMaternalUncle),
  PATERNAL_AUNT("Paternal-Aunt", FamilyRelationshipResolver::getPaternalAunt),
  MATERNAL_AUNT("Maternal-Aunt", FamilyRelationshipResolver::getMaternalAunt),
  SISTER_IN_LAW("Sister-In-Law", FamilyRelationshipResolver::getSisterInLaw),
  BROTHER_IN_LAW("Brother-In-Law", FamilyRelationshipResolver::getBrotherInLaw),
  SON("Son", FamilyRelationshipResolver::getSon),
  DAUGHTER("Daughter", FamilyRelationshipResolver::getDaughter),
  SIBLINGS("Siblings", FamilyRelationshipResolver::getSiblings);

  @NonNull private String name;
  @NonNull private BiFunction<FamilyTree, String, List<String>> relationResolver;
  private static Map<String, SupportedRelationship> supportedRelationships = new HashMap<>();

  static {
    EnumSet.allOf(SupportedRelationship.class)
        .forEach(sr -> supportedRelationships.put(sr.name, sr));
  }

  @Override
  public String toString() {
    return this.name;
  }

  public static SupportedRelationship get(String name) {
    return Optional.ofNullable(supportedRelationships.get(name))
        .orElseThrow(IllegalArgumentException::new);
  }
}

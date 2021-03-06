package in.vaidicjoshi.geektrust.backend.family.utils;

import in.vaidicjoshi.geektrust.backend.family.enums.Gender;
import in.vaidicjoshi.geektrust.backend.family.exceptions.MemberNotFoundException;
import in.vaidicjoshi.geektrust.backend.family.model.FamilyMember;
import in.vaidicjoshi.geektrust.backend.family.model.FamilyNode;
import in.vaidicjoshi.geektrust.backend.family.model.FamilyTree;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Vaidic Joshi
 * @date 18/09/21
 */
public class FamilyRelationshipResolver {

  private static final String PERSON_NOT_FOUND = "PERSON_NOT_FOUND";

  /**
   * Returns all sons of the member.
   *
   * @param familyTree
   * @param memberName
   * @return
   * @throws MemberNotFoundException
   */
  public static List<String> getSon(FamilyTree familyTree, String memberName) {
    FamilyNode member = null;
    try {
      member = familyTree.getMember(memberName);
    } catch (MemberNotFoundException e) {
      return Collections.singletonList(PERSON_NOT_FOUND);
    }
    return member.getChildren().stream()
        .filter(child -> child.getGender().equals(Gender.MALE))
        .map(FamilyMember::getName)
        .collect(Collectors.toList());
  }

  /**
   * Returns all daughters of the member.
   *
   * @param familyTree
   * @param memberName
   * @return
   */
  public static List<String> getDaughter(FamilyTree familyTree, String memberName) {
    FamilyNode member = null;
    try {
      member = familyTree.getMember(memberName);
    } catch (MemberNotFoundException e) {
      return Collections.singletonList(PERSON_NOT_FOUND);
    }
    return member.getChildren().stream()
        .filter(child -> child.getGender().equals(Gender.FEMALE))
        .map(FamilyMember::getName)
        .collect(Collectors.toList());
  }

  /**
   * Returns all PaternalUncle of the member.
   *
   * @param familyTree
   * @param memberName
   * @return
   */
  public static List<String> getSiblings(FamilyTree familyTree, String memberName) {
    FamilyNode member = null;
    try {
      member = familyTree.getMember(memberName);
    } catch (MemberNotFoundException e) {
      return Collections.singletonList(PERSON_NOT_FOUND);
    }
    if (hasParent(member, memberName)) {
      return member.getParent().getChildren().stream()
          .map(FamilyMember::getName)
          .filter(name -> !memberName.equals(name))
          .collect(Collectors.toList());
    }
    return new ArrayList<>();
  }

  /**
   * Returns all PaternalUncle of the member.
   *
   * @param familyTree
   * @param memberName
   * @return
   */
  public static List<String> getPaternalUncle(FamilyTree familyTree, String memberName) {
    FamilyNode member = null;
    try {
      member = familyTree.getMember(memberName);
    } catch (MemberNotFoundException e) {
      return Collections.singletonList(PERSON_NOT_FOUND);
    }
    String fathersName = member.getParent().getMaleMember().getName();
    return getUncles(member, fathersName);
  }

  /**
   * Returns PaternalAunt of the member.
   *
   * @param familyTree
   * @param memberName
   * @return
   */
  public static List<String> getPaternalAunt(FamilyTree familyTree, String memberName) {
    FamilyNode member = null;
    try {
      member = familyTree.getMember(memberName);
    } catch (MemberNotFoundException e) {
      return Collections.singletonList(PERSON_NOT_FOUND);
    }
    String fathersName = member.getParent().getMaleMember().getName();
    return getAunts(member, fathersName);
  }

  /**
   * Returns sisters of either parent.
   *
   * @param member
   * @param parentName
   * @return
   */
  private static List<String> getAunts(FamilyNode member, String parentName) {
    if (hasParent(member.getParent(), parentName)) {
      return member.getParent().getParent().getChildren().stream()
          .filter(child -> child.getGender().equals(Gender.FEMALE))
          .map(FamilyMember::getName)
          .filter(name -> !name.equals(parentName))
          .collect(Collectors.toList());
    }
    return new ArrayList<>();
  }

  /**
   * Returns MaternalUncle of the member.
   *
   * @param familyTree
   * @param memberName
   * @return
   */
  public static List<String> getMaternalUncle(FamilyTree familyTree, String memberName) {
    FamilyNode member = null;
    try {
      member = familyTree.getMember(memberName);
    } catch (MemberNotFoundException e) {
      return Collections.singletonList(PERSON_NOT_FOUND);
    }
    String mothersName = member.getParent().getFemaleMember().getName();
    return getUncles(member, mothersName);
  }

  /**
   * Returns brothers of either parent.
   *
   * @param member
   * @param parentName
   * @return
   */
  private static List<String> getUncles(FamilyNode member, String parentName) {
    if (hasParent(member.getParent(), parentName)) {
      return member.getParent().getParent().getChildren().stream()
          .filter(child -> child.getGender().equals(Gender.MALE))
          .map(FamilyMember::getName)
          .filter(name -> !name.equals(parentName))
          .collect(Collectors.toList());
    }
    return new ArrayList<>();
  }

  /**
   * Returns MaternalAunt of the member.
   *
   * @param familyTree
   * @param memberName
   * @return
   */
  public static List<String> getMaternalAunt(FamilyTree familyTree, String memberName) {
    FamilyNode member = null;
    try {
      member = familyTree.getMember(memberName);
    } catch (MemberNotFoundException e) {
      return Collections.singletonList(PERSON_NOT_FOUND);
    }
    String mothersName = member.getParent().getFemaleMember().getName();
    return getAunts(member, mothersName);
  }

  /**
   * Returns SisterInLaw of the member.
   *
   * @param familyTree
   * @param memberName
   * @return
   */
  public static List<String> getSisterInLaw(FamilyTree familyTree, String memberName) {
    FamilyNode member = null;
    try {
      member = familyTree.getMember(memberName);
    } catch (MemberNotFoundException e) {
      return Collections.singletonList(PERSON_NOT_FOUND);
    }

    List<String> sistersInLaw = new LinkedList<>();

    List<String> brothers = getBrothers(member, memberName);
    List<String> wivesOfBrothers =
        brothers.stream()
            .map(
                name -> {
                  try {
                    FamilyNode node = familyTree.getMember(name);
                    return getWife(node, name);
                  } catch (MemberNotFoundException e) {
                    return null;
                  }
                })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    FamilyMember spouse =
        member.isMale(memberName) ? member.getFemaleMember() : member.getMaleMember();
    if (Objects.nonNull(spouse)) {
      List<String> sistersOfSpouse = getSisters(member, spouse.getName());
      sistersInLaw.addAll(sistersOfSpouse);
    }
    sistersInLaw.addAll(wivesOfBrothers);
    return sistersInLaw;
  }

  /**
   * Returns Wife of a member.
   *
   * @param member
   * @param memberName
   * @return
   */
  private static String getWife(FamilyNode member, String memberName) {
    return member.isMale(memberName)
        ? Optional.ofNullable(member.getFemaleMember()).map(FamilyMember::getName).orElse(null)
        : null;
  }

  /**
   * Returns Wife of a member.
   *
   * @param member
   * @param memberName
   * @return
   */
  private static String getHusband(FamilyNode member, String memberName) {
    return member.isFemale(memberName)
        ? Optional.ofNullable(member.getMaleMember()).map(FamilyMember::getName).orElse(null)
        : null;
  }

  /**
   * Returns Brothers of the member.
   *
   * @param member
   * @param memberName
   * @return
   */
  private static List<String> getBrothers(FamilyNode member, String memberName) {
    if (hasParent(member, memberName)) {
      return member.getParent().getChildren().stream()
          .filter(child -> child.getGender().equals(Gender.MALE))
          .map(FamilyMember::getName)
          .filter(name -> !name.equals(memberName))
          .collect(Collectors.toList());
    }
    return new ArrayList<>();
  }

  /**
   * Returns Sisters of the member.
   *
   * @param member
   * @param memberName
   * @return
   */
  private static List<String> getSisters(FamilyNode member, String memberName) {
    if (hasParent(member, memberName)) {
      return member.getParent().getChildren().stream()
          .filter(child -> child.getGender().equals(Gender.FEMALE))
          .map(FamilyMember::getName)
          .filter(name -> !name.equals(memberName))
          .collect(Collectors.toList());
    }
    return new ArrayList<>();
  }

  /**
   * Returns BrotherInLaw of the member.
   *
   * @param familyTree
   * @param memberName
   * @return
   */
  public static List<String> getBrotherInLaw(FamilyTree familyTree, String memberName) {
    FamilyNode member = null;
    try {
      member = familyTree.getMember(memberName);
    } catch (MemberNotFoundException e) {
      return Collections.singletonList(PERSON_NOT_FOUND);
    }
    List<String> brothersInLaw = new LinkedList<>();
    List<String> sisters = getSisters(member, memberName);
    List<String> husbandsOfSisters =
        sisters.stream()
            .map(
                name -> {
                  try {
                    FamilyNode node = familyTree.getMember(name);
                    return getHusband(node, name);
                  } catch (MemberNotFoundException e) {
                    return null;
                  }
                })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    FamilyMember spouse =
        member.isMale(memberName) ? member.getFemaleMember() : member.getMaleMember();
    if (Objects.nonNull(spouse)) {
      List<String> brothersOfSpouse = getBrothers(member, spouse.getName());
      brothersInLaw.addAll(brothersOfSpouse);
    }
    brothersInLaw.addAll(husbandsOfSisters);
    return brothersInLaw;
  }

  /**
   * Check if the member was added as child and not as a spouse.
   *
   * @param member
   * @param memberName
   * @return
   */
  private static boolean hasParent(FamilyNode member, String memberName) {
    return member.getParent().getChildren().contains(member.getMember(memberName));
  }
}

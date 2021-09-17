package in.vaidicjoshi.geektrust.backend.family.model;

import in.vaidicjoshi.geektrust.backend.family.enums.Gender;
import in.vaidicjoshi.geektrust.backend.family.exceptions.MemberAdditionFailedException;
import in.vaidicjoshi.geektrust.backend.family.exceptions.MemberNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Vaidic Joshi
 * @date 17/09/21
 */
public class FamilyTree {
  // Cache stores member name to Family Node mappings
  private final Map<String, FamilyNode> familyMembers;

  public FamilyTree() {
    this.familyMembers = new HashMap<>();
  }

  /**
   * This method returns the Family Node for the Family Member identified by his/her name.
   *
   * @param name
   * @return FamilyNode
   */
  public FamilyNode getMember(String name) throws MemberNotFoundException {
    return Optional.ofNullable(familyMembers.get(name))
        .orElseThrow(() -> new MemberNotFoundException("Member with name " + name + " not found."));
  }

  /**
   * Checks if a member identified by his/her name is part of the current Family Tree.
   *
   * @param name
   * @return
   */
  private boolean isMember(String name) {
    return Optional.ofNullable(familyMembers.get(name)).isPresent();
  }

  /**
   * Adds a new member to family tree and caches it.
   *
   * @param name
   * @param gender
   * @return
   */
  public FamilyNode addMember(String name, Gender gender) throws MemberAdditionFailedException {
    if (isMember(name)) {
      throw new MemberAdditionFailedException(
          "Member with name " + name + " already exists in the family");
    }
    FamilyMember newMember = new FamilyMember(name, gender);
    FamilyNode newNode = new FamilyNode();
    if (gender.equals(Gender.MALE)) {
      newNode.setMaleMember(newMember);
    } else {
      newNode.setFemaleMember(newMember);
    }
    familyMembers.put(name, newNode);
    return newNode;
  }

  /**
   * Adds a new member to Family tree through the member's mother.
   *
   * @param nameOfMother
   * @param nameOfChild
   * @param genderOfChild
   * @return
   */
  public void addChild(String nameOfMother, String nameOfChild, Gender genderOfChild)
      throws MemberNotFoundException, MemberAdditionFailedException {

    if (isMember(nameOfChild)) {
      throw new MemberAdditionFailedException(
          "Child with name " + nameOfChild + " already exists.");
    }
    FamilyNode parentNode = getMember(nameOfMother);
    if (!parentNode.isFemale(nameOfMother)) {
      throw new MemberAdditionFailedException("Child can be added only through mother.");
    }
    FamilyNode childNode = addMember(nameOfChild, genderOfChild);
    childNode.setParent(parentNode);
    parentNode.getChildren().add(childNode);
  }

  /**
   * Adds spouse to a member on family tree.
   *
   * @param memberName
   * @param spouseName
   * @return
   */
  public void addSpouse(String memberName, String spouseName)
      throws MemberNotFoundException, MemberAdditionFailedException {
    if (isMember(spouseName)) {
      throw new MemberAdditionFailedException(
          "Member " + spouseName + " already exists in Family Tree");
    }
    FamilyNode memberNode = getMember(memberName);
    if (memberNode.isMale(memberName)) {
      memberNode.setFemaleMember(new FamilyMember(spouseName, Gender.FEMALE));
    } else {
      memberNode.setMaleMember(new FamilyMember(spouseName, Gender.MALE));
    }
    familyMembers.put(spouseName, memberNode);
  }
}

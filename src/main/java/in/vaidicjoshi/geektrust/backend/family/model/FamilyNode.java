package in.vaidicjoshi.geektrust.backend.family.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Vaidic Joshi
 * @date 17/09/21
 */
@Getter
@Setter
@NoArgsConstructor
public class FamilyNode {
  private FamilyNode parent;
  private FamilyMember maleMember;
  private FamilyMember femaleMember;
  private Set<FamilyMember> children = new LinkedHashSet<>();

  /**
   * Checks if the member with given name on this node is male.
   *
   * @param name
   * @return
   */
  public boolean isMale(String name) {
    return Objects.nonNull(this.maleMember) && this.maleMember.getName().equals(name);
  }

  /**
   * Checks if the member with given name on this node is female.
   *
   * @param name
   * @return
   */
  public boolean isFemale(String name) {
    return Objects.nonNull(this.femaleMember) && this.femaleMember.getName().equals(name);
  }

  /**
   * Returns Member details from mode.
   *
   * @param memberName
   * @return
   */
  public FamilyMember getMember(String memberName) {
    return this.isMale(memberName) ? this.getMaleMember() : this.getFemaleMember();
  }
}

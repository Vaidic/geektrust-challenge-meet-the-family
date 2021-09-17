package in.vaidicjoshi.geektrust.backend.family.enums;

/**
 * @author Vaidic Joshi
 * @date 18/09/21
 */
public enum SupportedRelationship {
  PATERNAL_UNCLE("Paternal-Uncle"),
  MATERNAL_UNCLE("Maternal-Uncle"),
  PATERNAL_AUNT("Paternal-Aunt"),
  MATERNAL_AUNT("Maternal-Aunt"),
  SISTER_IN_LAW("Sister-In-Law"),
  BROTHER_IN_LAW("Brother-In-Law"),
  SON("Son"),
  DAUGHTER("Daughter"),
  SIBLINGS("Siblings");

  private String name;

  SupportedRelationship(String name) {
    this.name = name;
  }
}

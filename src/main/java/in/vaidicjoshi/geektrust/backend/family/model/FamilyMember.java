package in.vaidicjoshi.geektrust.backend.family.model;

import in.vaidicjoshi.geektrust.backend.family.enums.Gender;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author Vaidic Joshi
 * @date 17/09/21
 */
@Getter
@Setter
@RequiredArgsConstructor
public class FamilyMember {
  @NonNull private String name;
  @NonNull private Gender gender;
}

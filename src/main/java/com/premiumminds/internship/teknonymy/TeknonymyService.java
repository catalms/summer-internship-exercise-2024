package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.Person;

class TeknonymyService implements ITeknonymyService {

  private class FamilyMember {
    private Person person;
    private int generation;
    private Person descendent;

    public FamilyMember(Person person, int generation, Person descendent) {
      this.person = person;
      this.generation = generation;
      this.descendent = descendent;
    }
  }

  private FamilyMember createFamilyMember(Person person, int generation, Person descendent) {
    return new FamilyMember(person, generation, descendent);
  }

  /**
   * Method to get a Person Teknonymy Information
   * 
   * @param Person person
   * @return FamilyMember which contains the person, the generation and the descendent
   */
  private FamilyMember determineTeknonymy(Person person) {
    // If the person has no children
    if (person.children() == null || person.children().length == 0) {
      return createFamilyMember(person, 0, null);
    }
    
    FamilyMember descendent = determineTeknonymy(person.children()[0]);
    
    // If the person has more than one child
    if (person.children().length != 1) {
      //Compare all children
      for (int i = 1; i < person.children().length; i++) {
        FamilyMember descendent2 = determineTeknonymy(person.children()[i]);
        if (descendent2.generation == descendent.generation) {
          //Verify if they don't have children
          if (descendent.generation == 0) {
            //Compare date of birth
            if (descendent2.person.dateOfBirth().isBefore(descendent.person.dateOfBirth())) {
              descendent = descendent2;
            }
          // If they do have children, compare descendent's date of birth
          } else if (descendent2.descendent.dateOfBirth().isBefore(descendent.descendent.dateOfBirth())) {
            descendent = descendent2;
          }
        } else if (descendent2.generation > descendent.generation) {
          descendent = descendent2;
        }
      }
    }

    if (descendent.generation == 0) {
      return createFamilyMember(person, 1, descendent.person); 
    } else {
      return createFamilyMember(person, descendent.generation + 1, descendent.descendent);
    }
  }

  /**
   * Method to get a Person Teknonymy Name
   * 
   * @param Person person
   * @return String which is the Teknonymy Name 
   */
  public String getTeknonymy(Person person) {
    FamilyMember teknonymyInfo = determineTeknonymy(person);
    switch (teknonymyInfo.generation) {
      case 0:
      return "";
      case 1:
      if (person.sex().equals('F')) {
        return "mother of " + teknonymyInfo.descendent.name();
      } else {
        return "father of " + teknonymyInfo.descendent.name();
      }
      case 2:
        if (person.sex().equals('F')) {
          return "grandmother of " + teknonymyInfo.descendent.name();
        } else {
          return "grandfather of " + teknonymyInfo.descendent.name();
        }
        default:
        StringBuilder teknonymyName = new StringBuilder();
        for (int i = 3; i <= teknonymyInfo.generation; i++) {
          teknonymyName.append("great-");
        }
        if (person.sex().equals('F')) {
          return teknonymyName + "grandmother of " + teknonymyInfo.descendent.name();
        } else {
          return teknonymyName + "grandfather of " + teknonymyInfo.descendent.name();
        }
      }
  };
}

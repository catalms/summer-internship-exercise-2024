package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.TeknonymyService;
import com.premiumminds.internship.teknonymy.Person;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class TeknonymyServiceTest {

  /**
   * The corresponding implementations to test.
   *
   * If you want, you can make others :)
   *
   */
  public TeknonymyServiceTest() {
  };

  @Test
  public void PersonNoChildrenTest() {
    Person person = new Person("John",'M',null, LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "";
    assertEquals(result, expected);
  }

  @Test
  public void PersonOneChildTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[] { new Person("Holy",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)) },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(result, expected);
  }

  @Test
  public void PersonTwoChildrenTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[] { 
          new Person("Mary",'F', null, LocalDateTime.of(2000, 1, 1, 0, 0)),
          new Person("Holy",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)),
        },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(result, expected);
  }

  @Test
  public void PersonThreeChildrenTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[] { 
          new Person("Mary",'F', null, LocalDateTime.of(2000, 1, 1, 0, 0)),
          new Person("Holy",'F', null, LocalDateTime.of(1047, 1, 1, 0, 0)),
          new Person("Kate",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)),
        },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Kate";
    assertEquals(result, expected);
  }

  @Test
  public void GrandfatherTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[] { 
          new Person("Mary",'F', null, LocalDateTime.of(2000, 1, 1, 0, 0)),
          new Person(
            "Holy",
            'F',
            new Person[] {
              new Person("Molly",'F', null, LocalDateTime.of(2000, 1, 1, 0, 0)),
              new Person("Olly",'M', null, LocalDateTime.of(2001, 1, 1, 0, 0)),
            },
            LocalDateTime.of(1046, 1, 1, 0, 0)),
        },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "grandfather of Molly";
    assertEquals(result, expected);
  }

  @Test
  public void GrandfatherTest2() {
    Person person = new Person(
        "John",
        'M',
        new Person[] { 
          new Person(
            "Mary",
            'F',
            new Person[] {
              new Person("Molly",'F', null, LocalDateTime.of(2000, 1, 1, 0, 0)),
              new Person("Olly",'M', null, LocalDateTime.of(1999, 1, 1, 0, 0)),
            },
            LocalDateTime.of(1046, 1, 1, 0, 0)),
          new Person(
            "Holy",
            'F',
            new Person[] {
              new Person("Kate",'F', null, LocalDateTime.of(2000, 1, 1, 0, 0)),
              new Person("Oscar",'M', null, LocalDateTime.of(1995, 1, 1, 0, 0)),
            },
            LocalDateTime.of(1046, 1, 1, 0, 0)),
        },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "grandfather of Oscar";
    assertEquals(result, expected);
  }

  
  @Test
  public void GreatGrandmotherTest() {
    Person person = new Person(
        "Jasmine",
        'F',
        new Person[] { 
          new Person("Mary",'F', null, LocalDateTime.of(2000, 1, 1, 0, 0)),
          new Person(
            "Holy",
            'F',
            new Person[] {
              new Person(
                "Molly",
                'F',
                new Person[] { new Person("Felicity", 'F', null, LocalDateTime.of(1998, 1, 1, 0, 0)) },
                LocalDateTime.of(2000, 1, 1, 0, 0)),
              new Person(
                "Olly",
                'M',
                new Person[] {
                  new Person("Beatrice",'F', null, LocalDateTime.of(2000, 1, 1, 0, 0)),
                  new Person("Daniel",'M', null, LocalDateTime.of(1999, 1, 1, 0, 0)),
                },
                LocalDateTime.of(1999, 1, 1, 0, 0)),
            },
            LocalDateTime.of(1046, 1, 1, 0, 0)),
          },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-grandmother of Felicity";
    assertEquals(result, expected);
  }

  @Test
  public void GreatGrandfatherTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[] { 
          new Person(
            "Mary",
            'F',
            new Person[] {
              new Person(
                "Molly",
                'F',
                new Person[] { new Person("Felicity", 'F', null, LocalDateTime.of(2002, 1, 1, 0, 0)) },
                LocalDateTime.of(2000, 1, 1, 0, 0)),
            },
            LocalDateTime.of(1046, 1, 1, 0, 0)),
          new Person(
            "Holy",
            'F',
            new Person[] {
              new Person(
                "Kate",
                'F',
                new Person[] { new Person("Beatrice", 'F', null, LocalDateTime.of(2001, 1, 1, 0, 0)) },
                LocalDateTime.of(2000, 1, 1, 0, 0)),
            },
            LocalDateTime.of(1046, 1, 1, 0, 0)),
        },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-grandfather of Beatrice";
    assertEquals(result, expected);
  }

  @Test
  public void Great2xGrandfatherTest() {
    Person person = new Person(
      "John",
        'M',
        new Person[] { 
          new Person(
            "Holy",
            'F',
            new Person[] {
              new Person("Molly",'F', null, LocalDateTime.of(2003, 1, 1, 0, 0)),
              new Person(
                "Olly",
                'M',
                new Person[] {
                  new Person("Beatrice",'F', null, LocalDateTime.of(2003, 1, 1, 0, 0)),
                  new Person(
                    "Daniel",
                    'M',
                    new Person[] {
                      new Person("Catherine",'F', null, LocalDateTime.of(2003, 1, 1, 0, 0)),
                      new Person("Edward",'M', null, LocalDateTime.of(2002, 1, 1, 0, 0)),
                    },
                    LocalDateTime.of(1999, 1, 1, 0, 0)),
                },
                LocalDateTime.of(1999, 1, 1, 0, 0)),
            },
            LocalDateTime.of(1046, 1, 1, 0, 0)),
        new Person("Mary",'F', null, LocalDateTime.of(2000, 1, 1, 0, 0)),
        },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-great-grandfather of Edward";
    assertEquals(result, expected);
  }
}
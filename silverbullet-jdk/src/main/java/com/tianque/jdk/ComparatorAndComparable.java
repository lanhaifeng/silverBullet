package com.tianque.jdk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *@author:linlinan
 *@version:1.0
 *@Date:20180408
 * JDK
 * 在Java集合框架里面，各种集合的操作很大程度上都离不开Comparable和Comparator，虽然它们与集合没有显示的关系，
 * 但是它们只有在集合里面的时候才能发挥最大的威力。下面是开始我们的分析。
 **/
public class ComparatorAndComparable {
    public static void main(String[] args) {
        List<ComparatorAndComparable.Person> persons = new ArrayList<>();
        persons.add(new ComparatorAndComparable().new Person("12314"));
        ComparatorAndComparable.Person person1 = new ComparatorAndComparable().new Person("12315");
        ComparatorAndComparable.Person person2 = new ComparatorAndComparable().new Person("12316");
        ComparatorAndComparable.Person person3 = new ComparatorAndComparable().new Person("12317");
        ComparatorAndComparable.Person person4 = new ComparatorAndComparable().new Person("12318");
        ComparatorAndComparable.Person person5 = new ComparatorAndComparable().new Person("12310");
        persons.add(person1);
        Collections.sort(persons);
    }

    class Person implements Comparable<Person>{
        public Person(String name){
            this.name = name;
        }
        @Override
        public int compareTo(Person o) {
            return name.length()>o.getName().length()?0:1;
        }

        private String name;

        public String getName() {
            return name;
        }
    }
}

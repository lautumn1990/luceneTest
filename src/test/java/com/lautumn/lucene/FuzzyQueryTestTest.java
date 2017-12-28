package com.lautumn.lucene;

import com.lautumn.lucene.bean.Person;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by Administrator on 2017/12/28.
 */
public class FuzzyQueryTestTest {
    private static final Logger logger = LoggerFactory.getLogger(FuzzyQueryTestTest.class);
    @Before
    public void initLog(){
//        Property
    }
    FuzzyQueryTest fuzzyQueryTest = new FuzzyQueryTest();
    @Test
    public void testGetPerson(){
        List<Person> persons = fuzzyQueryTest.getPersons(10);
        System.out.println(persons);
    }
    @Test
    public void testPersonToIndex(){
        logger.info("abc");
        fuzzyQueryTest.personToIndex();
    }

    @Test
    public void testSearchPerson(){
        String keyword = "uc";
        List<Person> persons = fuzzyQueryTest.getPersonsByKeyword(keyword, 0, 100);
        System.out.println("<br/><br/><span style='color:red;'>关键字：：："+keyword+"</span>\n\n<br/><br/>");
        System.out.println("条数："+persons.size() +"\n\n"+"<br/>"+"<br/>");
        for (Person person : persons) {
            System.out.println(person.getName()+"<br/>");
            System.out.println(person.getInfo()+"<br/>");
            System.out.println(person.getAge()+"<br/>");
            System.out.println("<br/>");
        }
    }
}
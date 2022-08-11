package org.example;

import org.example.model.Employee;
import org.example.model.Person;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

class GoodbyeImperativeProgrammingTest {

    TestUtil testUtil = new TestUtil();

    @Test
    public void filterSortPrint() {

        //prepare the variables
        List<Person> groupOfPeople = testUtil.generateGroupOfPeople();

        //begin sample code
        List<Person> youngPeople = new ArrayList<>();
        for (Person p : groupOfPeople) {
            if (p.getAge() <= 30) {
                youngPeople.add(p);
            }
        }
        List<Person> sortedYoungPeople = new ArrayList(youngPeople);
        Collections.sort(sortedYoungPeople, new Comparator<Person>() {

            @Override
            public int compare(Person o1, Person o2) {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });
        for (Person p : sortedYoungPeople) {
            System.out.println(p.getLastName() + ", " + p.getAge());
        }
    }


    public Map<String, List<Employee>> groupByJobTitle(List<Employee> employeeList) {
        Map<String, List<Employee>> resultMap = new HashMap<>();
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            List<Employee> employeeSubList = resultMap.getOrDefault(employee.getTitle(), new ArrayList<Employee>());
            employeeSubList.add(employee);
            resultMap.put(employee.getTitle(), employeeSubList);
        }
        return resultMap;
    }

    @Test
    public void testMethod1() throws SQLException {
        //prepare the variables
        ResultSetMetaData metaData = mock(ResultSetMetaData.class);
        when(metaData.getColumnName(any(Integer.class))).thenReturn(UUID.randomUUID().toString().substring(0, 5));
        when(metaData.getColumnCount()).thenReturn(5);

        //begin sample code
        List<String> cols = new ArrayList<>();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            cols.add(columnName);
        }
        System.out.println(cols);
    }

    @Test
    public void getOddNums() {
        //prepare
        List<Integer> nums = Arrays.asList(1,2,3,4,5,6,7);
        //begin sample code
        List<Integer> oddNums = new ArrayList();
        Iterator var2 = nums.iterator();

        while(var2.hasNext()) {
            int n = (Integer)var2.next();
            if (n % 2 > 0) {
                oddNums.add(n);
            }
        }
        System.out.println(oddNums);
    }
}
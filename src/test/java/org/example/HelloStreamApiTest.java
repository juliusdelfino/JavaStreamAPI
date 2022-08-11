package org.example;

import org.example.model.Book;
import org.example.model.Employee;
import org.example.model.Person;
import org.junit.jupiter.api.Test;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HelloStreamApiTest {

    TestUtil testUtil = new TestUtil();

    @Test
    public void filterSortPrint() {

        //prepare the variables
        List<Person> groupOfPeople = testUtil.generateGroupOfPeople();

        //begin sample code
        groupOfPeople.stream().filter(p -> p.getAge() <= 30)
                .sorted((o1, o2) -> o1.getLastName().compareTo(o2.getLastName()))
                .forEach(p -> System.out.println(p.getLastName() + ", " + p.getAge()));
    }

    @Test
    public void streamCreation() {
        Stream employeeList = testUtil.generateEmployeeList().stream();

        // Peek inside generateStreamOfBooks() method - it uses Stream.of()
        Stream books = testUtil.generateStreamOfBooks();

        Stream s1 = Stream.builder().add(3).add(5).add(6).build();
        System.out.println(s1.collect(Collectors.toList()));
    }

    @Test
    public void streamInfinite() {
        Stream.generate(Math::random).limit(5).forEach(p -> {
            System.out.println(p);
            testUtil.unchecked(() -> Thread.sleep(1000));
        });

        Stream.iterate(10, i -> i * 2).limit(5).forEach(p -> {
            System.out.println(p);
            testUtil.unchecked(() -> Thread.sleep(1000));
        });
    }

    @Test
    public void whenApplySummaryStatistics_thenGetBasicStats() {
        List<Employee> empList = new ArrayList<>();
        DoubleSummaryStatistics stats = empList.stream()
                .mapToDouble(Employee::getSalary)
                .summaryStatistics();

        assertEquals(stats.getCount(), 3);
        assertEquals(stats.getSum(), 600000.0, 0);
        assertEquals(stats.getMin(), 100000.0, 0);
        assertEquals(stats.getMax(), 300000.0, 0);
        assertEquals(stats.getAverage(), 200000.0, 0);
    }

    @Test
    public void lazyExecution() {

        //prepare the variables
        List<Person> groupOfPeople = testUtil.generateGroupOfPeople();

        //begin sample code
        Stream<Person> s1 = groupOfPeople.stream().filter(p -> p.getAge() <= 30);
        System.out.println("Setup filtering by age done");
        Stream<Person> s2 = s1.filter(p -> p.getLastName().contains("e"));
        System.out.println("Setup filtering by last name done");
        Stream<String> s3 = s2.map(p -> p.getLastName().toUpperCase());
        System.out.println("Setup converting last name to uppercase done");
        List<String> names = s3.collect(Collectors.toList());
        System.out.println(names);
    }

    @Test
    public void shortCircuiting_limit() {

        //prepare the variables
        List<Person> groupOfPeople = testUtil.generateGroupOfPeople();

        //begin sample code
        Stream<String> s1 = groupOfPeople.stream().filter(p -> p.getAge() <= 30)
                .filter(p -> p.getLastName().contains("e"))
                .map(p -> p.getLastName().toUpperCase())
                .limit(1);
        List<String> names = s1.collect(Collectors.toList());
        System.out.println(names);
    }

    @Test
    public void shortCircuiting_findFirst() {

        //prepare the variables
        List<Person> groupOfPeople = testUtil.generateGroupOfPeople();

        Stream<String> s2 = groupOfPeople.stream().filter(p -> { System.out.println("filter by age"); return p.getAge() <= 30; })
                .filter(p -> { System.out.println("filter by last name"); return p.getLastName().contains("e"); })
                .map(p -> { System.out.println("transform last name to uppercase"); return p.getLastName().toUpperCase(); });
        System.out.println(s2.findFirst().get());
    }

    @Test
    public void shortCircuiting_findAny() {

        //prepare the variables
        List<Person> groupOfPeople = testUtil.generateGroupOfPeople();

        Stream<String> s2 = groupOfPeople.parallelStream().filter(p -> { System.out.println("filter by age"); return p.getAge() <= 30; })
                .filter(p -> { System.out.println("filter by last name"); return p.getLastName().contains("e"); })
                .map(p -> { System.out.println("transform last name to uppercase"); return p.getLastName().toUpperCase(); });
        System.out.println(s2.findAny().get());
    }

    @Test
    public void shortCircuiting_anyMatch() {

        //prepare the variables
        List<Person> groupOfPeople = testUtil.generateGroupOfPeople();

        Stream<Person> s1 = groupOfPeople.stream();
        Predicate<Person> filterByAge = p -> { System.out.println("filter by age"); return p.getAge() <= 30; };
        System.out.println(s1.anyMatch(filterByAge));
    }

    @Test
    public void shortCircuiting_allMatch() {

        //prepare the variables
        List<Person> groupOfPeople = testUtil.generateGroupOfPeople();

        Stream<Person> s1 = groupOfPeople.stream();
        Predicate<Person> filterByAge = p -> { System.out.println("filter by age"); return p.getAge() <= 30; };
        System.out.println(s1.allMatch(filterByAge));
    }


    @Test
    public void testReduce() {

        Stream<Book> books = testUtil.generateStreamOfBooks();

        //begin sample code
        System.out.println(books
                        .map(Book::getName)
                        .reduce("START", (a, b) -> a + ", " + b));
    }

    @Test
    public void testCollectors_joining() {
        System.out.println(testUtil.generateStreamOfBooks()
                .map(Book::getName).collect(Collectors.joining("><")));
    }

    @Test
    public void testCollectors_toSet() {
        List<Person> group1 = testUtil.generateGroupOfPeople();
        List<Person> group2 = testUtil.generateGroupOfPeople();
        System.out.println("Using toSet():    " + Stream.concat(group1.stream(), group2.stream())
                .collect(Collectors.toSet()));

        // Alternatively you can use Stream.distinct()
        System.out.println("Using distinct(): " + Stream.concat(group1.stream(), group2.stream()).distinct()
                .collect(Collectors.toList()));
    }

    @Test
    public void testCollectors_toCollection() {
        LinkedList<Person> list = testUtil.generateGroupOfPeople().stream()
                .collect(Collectors.toCollection(LinkedList<Person>::new));
        System.out.println(list.size());
    }

    @Test
    public void testCollectors_groupingBy() {
        List<Employee> employeeList = testUtil.generateEmployeeList();
        Map<String, List<Employee>> map = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getTitle));
        System.out.println(map);
    }

    @Test
    public void testCollectors_mapping() {
        List<Employee> employeeList = testUtil.generateEmployeeList();
        Map<String, List<Double>> map = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getTitle,
                        Collectors.mapping(Employee::getSalary, Collectors.toList())));
        System.out.println(map);
    }

    @Test
    public void testStream_concat_and_empty() {

        boolean isAuProfile = false;
        Stream s1 = Stream.of("AU_wsoEnabled", "AU_theme", "AU_timeout");
        Stream s2 = Stream.of("AU_memory", "AU_cpu", "AU_threads");
        Stream s3 = isAuProfile ? Stream.concat(s1, s2) : Stream.empty();
        System.out.println(s3.collect(Collectors.toList()));
    }

    @Test
    public void parallelStream() {
        // System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "5");
        Stream.generate(Math::random).parallel().forEach(p -> {
            System.out.println(Thread.currentThread().getName() + ": " + p);
            testUtil.unchecked(() -> Thread.sleep(1000));
        });
    }

    @Test
    public void testMethod1() throws SQLException {
        //prepare the variables
        ResultSetMetaData metaData = mock(ResultSetMetaData.class);
        when(metaData.getColumnName(any(Integer.class))).thenReturn(UUID.randomUUID().toString().substring(0, 5));
        when(metaData.getColumnCount()).thenReturn(5);

        //begin sample code
        List<String> cols = IntStream.range(1, metaData.getColumnCount() + 1).boxed()
                .map(testUtil.unchecked(metaData::getColumnName)).collect(Collectors.toList());
        System.out.println(cols);
    }

    @Test
    public void getOddNums() {
        //prepare
        List<Integer> nums = Arrays.asList(1,2,3,4,5,6,7);
        //begin sample code
        List<Integer> oddNums = nums.stream().filter((n) -> n % 2 > 0).collect(Collectors.toList());

        System.out.println(oddNums);
    }

//    @Test
//    public void streamConcat() {
//        // get by Aggregations Metric & get by Aggregations type, Then join with aggregationSqlFunctions
//        List<SqlColumn> aggregationSqlFunctions = Stream.concat(
//                        sqlUtil.getAggregationFunctions(request.getAggregations(), request).stream(),
//                        sqlUtil.getAggregationFunctionsByType(request.getAggregations(), request).stream())
//                .filter(x -> StringUtils.isNotEmpty(x.getName())).collect(Collectors.toList());
//    }
//
//    @Test
//    public void multiTransform() {
//        return selectColumnsStream
//                .filter(x -> StringUtils.isNotEmpty(x.getName()))
//                .map(locationUtil::tryReplaceGeoQuery)
//                .map(sqlUtil::replaceColumnNameIfIsInAliasMap)
//                .map(x -> sqlUtil.replaceLinksColumnWithAlias(x, request))
//                .map(s -> formatUtil.formatDateColumn(s, table, Constants.SQL_DATE_PATTERN))
//                .map(x -> sqlUtil.tryPrefixAmbiguousColumn(x, table))
//                .map(x -> sqlUtil.tryPrefixArrayType(x, table))
//                .map(x -> sqlUtil.replaceColumnNullValue(x, table, true, false))
//                .distinct().collect(Collectors.toList());
//    }
}

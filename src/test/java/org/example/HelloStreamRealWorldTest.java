package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.h2.tools.Csv;

public class HelloStreamRealWorldTest {

    @Test
    public void testJdbc() throws IOException {
        //Prepare data
        String csvResults =
            "0001, John Doe\n" +
            "0002, Bob Smith\n" +
            "0003, Alice Doe\n";
        ResultSet rs = new Csv().read(new StringReader(csvResults), new String[] {"id", "name"});

        //begin sample code
        Stream<String> rsStream = StreamSupport.stream(new Spliterators.AbstractSpliterator<String>(
                Long.MAX_VALUE, Spliterator.ORDERED) {
            @Override
            public boolean tryAdvance(Consumer<? super String> action) {
                try {
                    if(!rs.next()) return false;
                    action.accept(rs.getString("name"));
                    return true;
                } catch(SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }, false);
        System.out.println(rsStream.collect(Collectors.toList()));
    }
}

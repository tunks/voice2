package com.att.voice2;

import com.att.voice2.parser.ParserFactory;
import com.att.voice2.parser.QueryProcessor;
import com.att.voice2.parser.SSOTQueryParser;
import com.att.voice2.parser.ParserFactory.QueryModel;
import org.junit.Before;
import org.junit.Test;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertNotNull;

/**
 * Created by ebrimatunkara on 3/16/17.
 */

public class SSOTQueryParserTest {
    private SSOTQueryParser queryParser;
    private String text ;

    @Before
    public void setUp() throws Exception {
        queryParser = new SSOTQueryParser();
        //text = "how many customers are affected on this cable id 8232323323232";
        text = "customer Cable 2000";//"how many customers are affected on this incident id 8232323323232";
    }

    @Test
    public void testParse() {
        QueryModel result = queryParser.parse(text);
        assertNotNull(result);
        System.out.println(result.toQueryString());

    }
}

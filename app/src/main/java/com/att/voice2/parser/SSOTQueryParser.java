package com.att.voice2.parser;

import android.util.Log;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import com.att.voice2.parser.ParserFactory.QueryModel;

/**
 * Created by ebrimatunkara on 3/16/17.
 */

public class SSOTQueryParser implements  BaseParser {
    @Override
    public QueryModel parse(String text) {
        QueryModel queryModel = null;
        Set<Map.Entry<ParserFactory.QueryElements,String>> patterns= ParserFactory.getPatterns().entrySet();
        for(Map.Entry<ParserFactory.QueryElements,String> entry : patterns) {
            try {
                Pattern r = Pattern.compile(entry.getValue());
                Matcher m = r.matcher(text.trim());
               Log.i("Pattern match :", text);
                System.out.println("Pattern matching, "+text);
                if (m.find()) {
                    queryModel = ParserFactory.getQueryModel(entry.getKey());
                    queryModel.setValue(m.group(3));
                    return queryModel;
                }
            }catch (PatternSyntaxException ex){
                System.out.println("Pattern matching error "+text+" , "+ex.getMessage());
                Log.e("Pattern matching error "+text,ex.getMessage());
            }
        }
        return queryModel;

    }
}

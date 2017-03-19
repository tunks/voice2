package com.att.voice2.parser;

import com.att.voice2.parser.ParserFactory.QueryModel;
/**
 * Created by ebrimatunkara on 3/16/17.
 */

public interface BaseParser {
    public QueryModel parse(String text);
}

package com.att.voice2.parser;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by ebrimatunkara on 3/16/17.
 */

public class ParserFactory {
    private static Map<QueryElements,String> patterns = new HashMap<>();

    private static Map<QueryElements,QueryModel> queries = new HashMap<>();
    static {
        //cable query
        patterns.put(QueryElements.CABLE,"\\b([cC]ustomer[s]?)\\b.{0,}\\b([cC]able)\\b.{0,}\\b(\\d+)");//\\b([cC]ustomer[s]?)\\b.*\\b([cC]able)\\b+.*\\b(\\d+)");
        QueryModel qModel1 = new QueryModel("MATCH (c:CABLE), (cu:CUSTOMER) , (p.PROD_SERVICE) where c:CABLE=\"CABLE_ID\"RETURN *","CABLE_ID", QueryElements.CABLE);
        queries.put(QueryElements.CABLE,qModel1);

        //incident query
        patterns.put(QueryElements.INCIDENT,"\\b([cC]ustomer[s]?)\\b.{0,}\\b([iI]ncident|[tT]icket)\\b.{0,}\\b(\\d+)");///"\\b([cC]ustomer[s]?)\\b.*\\b([iI]ncident|[tT]icket)\\b+.*\\b(\\d+)");
        QueryModel qModel2 = new QueryModel("MATCH (in:INCIDENT_TICKET),(c:CABLE),(cu:CUSTOMER)  where in:INCIDENT_TICKET=\"TICKET_NO\"RETURN *","TICKET_NO",QueryElements.INCIDENT);
        queries.put(QueryElements.INCIDENT,qModel2);

    }

    public static QueryModel getQueryModel(QueryElements element){
        return queries.get(element);
    }

    public static Map<QueryElements, String> getPatterns() {
        return patterns;
    }


    public static enum QueryElements{
        CABLE,
        INCIDENT,
        INCIDENT_CUSTOMER
    }

    public static class QueryModel{
        private QueryElements element;
        private String query;
        private String identifier;
        private String value;

        public QueryModel(String query, String identifier, QueryElements element) {
            this.query = query;
            this.identifier = identifier;
            this.element = element;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String toQueryString(String value){
             return query.replaceAll(identifier,value);
        }
        public String toQueryString(){
            return query.replaceAll(identifier,value);
        }

        @Override
        public String toString() {
            return "QueryModel{" +
                    "element=" + element +
                    ", query='" + query + '\'' +
                    ", identifier='" + identifier + '\'' +
                    '}';
        }
    }

}

package rest.dto.query;

public class QueryRequest {
    private String userID;
    private String sqlQuery;

    public QueryRequest() {
    }

    public QueryRequest(String userID, String sqlQuery) {
        this.userID = userID;
        this.sqlQuery = sqlQuery;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }
}

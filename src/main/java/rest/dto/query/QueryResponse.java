package rest.dto.query;

public class QueryResponse {
    private String result;

    public QueryResponse(String result) {
        this.result = result;
    }

    public QueryResponse() {

    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

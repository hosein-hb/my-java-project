package ir.ac.kntu;

import java.util.List;

public class Request {

    private String title;
    private String requestText;
    private String status;
    private String supportAnswer;
    private String code;

    public Request() {
        this.code = GenerateRandomCode.generateUniqueCode(Database.getInstance().getRequestsCodes());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRequestText() {
        return requestText;
    }

    public void setRequestText(String requestText) {
        this.requestText = requestText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupportAnswer() {
        return supportAnswer;
    }

    public void setSupportAnswer(String supportAnswer) {
        this.supportAnswer = supportAnswer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void printFullRequest() {
        System.out.println("Details of request:");
        System.out.println("\tTitle: " + getTitle());
        System.out.println("\tRrequest text: " + getRequestText());
        System.out.println("\tStatus: " + getStatus());
        if (getSupportAnswer() != null) {
            System.out.println("Support message:" + getSupportAnswer());
        }
    }

    public static int printSummeryRequests(int firstIndex, List<Request> requests) {
        int index = firstIndex;
        for (; index < firstIndex + 10 && index <= requests.size(); index++) {
            System.out.println("\t" + index + ". " + requests.get(index - 1).getTitle());
        }
        return index - 1;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Request request = (Request) object;
        return getCode().equals(request.getCode());
    }

}

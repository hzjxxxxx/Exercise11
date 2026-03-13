public class Feedback {
    private String firstName;
    private String lastName;
    private String email;
    private String completeFeedback;
    private String reviewID;
    private boolean longFeedback;

    public Feedback(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void analyseFeedback(boolean isConcatenation, String sent1, String sent2, String sent3, String sent4, String sent5) {
        if (isConcatenation) {
            completeFeedback = feedbackUsingConcatenation(sent1, sent2, sent3, sent4, sent5);
        } else {
            completeFeedback = feedbackUsingPlus(sent1, sent2, sent3, sent4, sent5);
        }
        longFeedback = checkFeedbackLength(completeFeedback);
        createReviewID(firstName, lastName, completeFeedback);
    }

    private String feedbackUsingConcatenation(String sent1, String sent2, String sent3, String sent4, String sent5) {
        return sent1 + sent2 + sent3 + sent4 + sent5;
    }

    private String feedbackUsingPlus(String sent1, String sent2, String sent3, String sent4, String sent5) {
        return sent1 + sent2 + sent3 + sent4 + sent5;
    }

    private boolean checkFeedbackLength(String feedback) {
        return feedback.length() > 500;
    }

    private void createReviewID(String firstName, String lastName, String completeFeedback) {
        String namePart = (firstName + lastName).substring(2, 6).toUpperCase();
        String feedbackPart = completeFeedback.substring(10, 15).toLowerCase();
        String id = namePart + feedbackPart + completeFeedback.length() + "_" + System.currentTimeMillis();
        reviewID = id.replace(" ", "");
    }

    @Override
    public String toString() {
        return "First Name: " + firstName + "\nLast Name: " + lastName + "\nEmail: " + email + 
               "\nComplete Feedback: " + completeFeedback + "\nLong Feedback: " + longFeedback + 
               "\nReview ID: " + reviewID;
    }

    public static void main(String[] args) {
        String s1 = "I was very satisfied with the service.";
        String s2 = "The e-Bike is quite comfortable to ride.";
        String s3 = "The battery life of the e-Bike is impressive.";
        String s4 = "The customer support was helpful and responsive.";
        String s5 = "I would recommend this e-Bike to my friends and family.";
        
        Feedback fb = new Feedback("Alice", "Smith", "alice@example.com");
        fb.analyseFeedback(true, s1, s2, s3, s4, s5);
        System.out.println(fb);
    }
}

public class Test {// This is a test comment.
    public static void main(String[] args) {
        String phrase = "Tin man";
        System.out.println(reverse(phrase));

    }
    public static String reverse(String phrase) {
        String result = "";
        for (int i=0; i < phrase.length(); i++) {
            result = phrase.charAt(i) + result;
        }
        return result;
    }
}

package patternMatching;

public class Main {
    public static void main(String []args) {
        Kmp kmp = new Kmp("AABABAABAAABAB");
        kmp.find("ABACA");
    }
}

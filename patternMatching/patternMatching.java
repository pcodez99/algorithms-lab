package patternMatching;

public class patternMatching {
    public void bruteForce(String pattern, String text) {
        int M = pattern.length();
        int N = text.length();

        System.out.println("M: " + M + " N: " + N);

        for(int i = 0; i <= N - M; i++) {
            int j = 0;
            for(; j < M; j++) {
                if(pattern.charAt(j) != text.charAt(i + j)) break;
            }
            if(j == M) System.out.println(i);
        }
    }
}

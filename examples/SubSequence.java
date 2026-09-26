import java.util.ArrayList;

public class SubSequence {

    public static int maxSubsequenceSum(int[] X, int N) {
        int maxSoFar = 0;

        for (int L = 0; L < N; L++) {
            for (int U = L; U < N; U++) {

                int sum = 0;

                for (int I = L; I <= U; I++) {
                    sum = sum + X[I];
                }

                maxSoFar = Math.max(maxSoFar, sum);
            }
        }

        return maxSoFar;
    }

    public static int[] parse(String s) {
        ArrayList<Integer> parsedStr = new ArrayList<Integer>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '-') {
                parsedStr.add(Integer.parseInt(s.substring(i, i+2)));
                i++;
            } else {
                parsedStr.add(Integer.parseInt(s.substring(i, i+1)));
            }
        }
        
        int[] array = new int[parsedStr.size()];

        for (int i = 0; i < parsedStr.size(); i++) {
            array[i] = parsedStr.get(i);
        }
        return array;
    }

    public static void main(String[] args) {
        if(args.length > 0) {
            System.out.println("Processing " + args[0].length() + " characters.");
            int array[] = parse(args[0]);
            System.out.println(maxSubsequenceSum(array, array.length));
            
        } else {
            System.out.println("String required.");
        }
    }
}
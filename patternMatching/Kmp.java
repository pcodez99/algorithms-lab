package patternMatching;

public class Kmp {

    private String text;

        private int[] makeLPS(String pattern) {
        int M = pattern.length();
        int []LPS = new int[M];
        int i = 1;
        int len = 0;
        
        while(i < M) {
            System.out.println("i->" + pattern.charAt(i) + ", len->" + pattern.charAt(len));

            if(pattern.charAt(i) == pattern.charAt(len)) { 
                //leggendo il nuovo carattere P[i] siamo riusciti ad allungare il bordo
                len++; //visto che il bordo si riesce ad allungare aggiorniamo len
                LPS[i] = len; //aggiorniamo la posizione con il giusto len 
                i++; //incrementiamo i per leggere il prossimo carattere del pattern
            } else {
                // Il bordo attuale non si allunga: non ricomincio a leggere la stringa da 0.
                // Tengo fermo i e riprovo lo stesso carattere usando un bordo più corto.
                //quindi qui capiamo da dove ricominciare
                if(len != 0) {
                    //se len non è 0, allora controlliamo via via bordi sempre più corti 
                    len = LPS[len - 1];
                } else {
                    //se len è 0, allora bisogna ricominciare la stringa da capo, impostiamo 0 in quella posizione
                    LPS[i] = 0;
                    i++;
                }
            }
        }

        return LPS;
    }

    public Kmp(String text) {
        this.text = text;
    }

    public void find(String pattern) {
        int[] LPS = makeLPS(pattern);
        printArray(LPS);
    }

    private void printArray(int[] array) {
        for(int i = 0; i < array.length; i++){
            System.out.println(array[i] + ", ");
        }
    }

}

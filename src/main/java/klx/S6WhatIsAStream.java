package klx;

@SuppressWarnings({"java:S1223","java:S1172","java:S2259"}) // not production code, just a demo
public class S6WhatIsAStream {

    public static void main(String[] args){
        var inputStream = createPseudoCodeStream( "HugeFile.txt" );
        long totalLines = 0L;

        while(inputStream.nextLine()) totalLines++;

        System.out.println("Total number line: " +totalLines);
    }

     static PseudoStream createPseudoCodeStream(String file) {
        return null;
    }

    class PseudoStream {
        public boolean nextLine() {
            return false;
        }
        public String currentLine() {
            return "";
        }
    }
}


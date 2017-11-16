package alviz.graph.factory;

import java.util.Random;

public class SequenceGenerator {
    float alpha;

    int lenGenerated; // N
    int lenModified; // M

    Sequence generated;
    Sequence modified;

    Random random;

    private static final String sequenceCharacters = "ATGC";

    public SequenceGenerator(float alpha, int lenGenerated, int lenModified) {
        random = new Random();
        this.lenGenerated = lenGenerated;
        this.lenModified = lenModified;

        generated = new Sequence();
        modified = new Sequence();
    }

    public int getLenGenerated() {
        return lenGenerated;
    }

    public int getLenModified() {
        return lenModified;
    }

    public void generateNewPair() {
        generated = newGenerated();
        modified = newModified();
    }

    private char getNewRandomChar() {
        int newIndex = random.nextInt(sequenceCharacters.length());
        return sequenceCharacters.charAt(newIndex);
    }

    private Sequence newGenerated() {
        StringBuilder sb = new StringBuilder(lenGenerated);
        for (int i = 0; i < lenGenerated; i++) {
            sb.append(getNewRandomChar());
        }

        return new Sequence(sb.toString());
    }

    private Sequence newModified() {
        String generatedString = generated.getSeq();

        StringBuilder modifiedBuilder = new StringBuilder(lenGenerated);
        for (int i = 0; i < generatedString.length(); i++) {
            char charToAdd = generatedString.charAt(i);

            boolean shouldMutate = random.nextFloat() < alpha;
            if (shouldMutate) {
                charToAdd = getNewRandomChar();
            }

            modifiedBuilder.append(charToAdd);
        }

        // ASSUMING THAT LENGTH MODIFIED IS ALWAYS LESS THAN LENGTH GENERATED
        int numToRemove = lenGenerated - lenModified;
        while (numToRemove > 0) {
            int indexToRemove = random.nextInt(modifiedBuilder.length());
            modifiedBuilder.deleteCharAt(indexToRemove);
            numToRemove--;
        }

        return new Sequence(modifiedBuilder.toString());
    }

    public Sequence getGenerated() {
        return generated;
    }

    public Sequence getModified() {
        return modified;
    }
}

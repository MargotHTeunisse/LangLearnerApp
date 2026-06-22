package nl.margothteunisse.langlearner;

class Vocabulary {
    private final String[][] words;

    Vocabulary(String dataFile) {
        switch (dataFile)
        {
            case "empty.txt":
                words = new String[][] {};
                break;
            case "cat.txt":
                words = new String[][] {{"cat", "kissa"}};
                break;
            case "dog.txt":
                words = new String[][] {{"dog", "koira"}};
                break;
            case "bird.txt":
                words = new String[][] {{"bird", "lintu"}};
                break;
            case "bear.txt":
                words = new String[][] {{"bear", "karhu"}};
                break;
            default:
                words = new String[][] {{"", ""}};
        }
    }

    int wordCount() {
        return words.length;
    }

    Card getCard(int wordIndex) {
        return new Card(words[wordIndex][0], words[wordIndex][1]);
    }
}

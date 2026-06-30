async function fetchWord() {
    var deckContainsCards = await fetch("/session/draw-next-card")
        .then(response => response.json())

    var wordToTranslate = document.getElementById("word");
    if (deckContainsCards) {
        wordToTranslate.textContent = "Translate:" + await fetch("/session/fetch-word")
            .then(response => response.text());
    }
    else {
        wordToTranslate.textContent = "You have translated all words!";
    }
}

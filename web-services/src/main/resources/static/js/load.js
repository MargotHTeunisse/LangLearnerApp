async function load() {
    const deckJSON = await fetch("/api/drawn-card").then(response => response.json());

    let wordToTranslate = document.getElementById("word");
    wordToTranslate.textContent = deckJSON.card.visibleWord;

    let sourceLanguage = document.getElementById("source");
    sourceLanguage.textContent = deckJSON.sourceLanguage;

    let targetLanguage = document.getElementById("target");
    targetLanguage.textContent = deckJSON.targetLanguage;

    let form = document.getElementById("translation");

    form.addEventListener('submit', (e) => {
        e.preventDefault();
        e.stopPropagation();
        submitAnswer();
    });

    if (deckJSON.card.answerIsVisible) {
        form.textContent = "";

        document.getElementById("word").style.borderColor = "red";

        changeButtonToDraw();
    }

    else {
        let answer = document.createElement("input");
        answer.id = "answer";
        answer.type = "text";
        answer.autofocus = "autofocus";
        form.textContent = "Your answer: "
        form.appendChild(answer);
    }
}
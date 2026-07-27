async function load() {
    const deckView = await fetch("/session/fetch-deck").then(response => response.json());

    let wordToTranslate = document.getElementById("word");
    wordToTranslate.textContent = deckView.visibleWord;

    let form = document.getElementById("translation");

    form.addEventListener('submit', (e) => {
        e.preventDefault();
        e.stopPropagation();
        submitAnswer();
    });

    if (deckView.answerIsVisible) {
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
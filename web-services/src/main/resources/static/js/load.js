async function load() {
    const cardView = await fetch("/api/drawn-card").then(response => response.json());

    let wordToTranslate = document.getElementById("word");
    wordToTranslate.textContent = cardView.visibleWord;

    let form = document.getElementById("translation");

    form.addEventListener('submit', (e) => {
        e.preventDefault();
        e.stopPropagation();
        submitAnswer();
    });

    if (cardView.answerIsVisible) {
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
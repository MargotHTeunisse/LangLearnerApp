async function submitAnswer() {

    let feedback = document.getElementById("feedback");
    let answer = document.getElementById("answer")
    let answerIsCorrect = await fetch("/session/submit?answer="+answer.value, {method: "POST"})
        .then(response => response.json());

    if (answerIsCorrect) {
        feedback.textContent = "Correct!";
        let translation = document.getElementById("translation");
        translation.removeChild(answer);
        translation.textContent += answer.value;

        await changeButtonToDraw();
    }
    else {
        feedback.textContent = "That is incorrect.";
    }
}

async function showAnswer() {
    await fetch("/session/show-answer", {method: "PATCH"})

    window.location.reload();
}

async function changeButtonToDraw() {
    let button = document.getElementById("button");
    button.value = "Draw next";
    button.onclick = function() {drawNext()};

    document.addEventListener('keypress', (e) => {
        if (e.key === "Enter") {
            button.click();
        }
    });
}

async function drawNext() {
    let deckContainsCards = await fetch("/session/draw-next-card", {method: "PATCH"})
        .then(response => response.json());

    if (deckContainsCards) {
        window.location.reload();
    }
    else {
        window.location.replace("endcard.html");
        await fetch("/session/close", {method:"POST"});
    }
}

async function load() {
    let wordToTranslate = document.getElementById("word");
    wordToTranslate.textContent = await fetch("/session/fetch-word")
        .then(response => response.text());

    let answerIsVisible = await fetch("/session/fetch-answer-visibility")
        .then(response => response.json());

    let form = document.getElementById("translation");

    if (answerIsVisible) {
        form.textContent = "";

        await changeButtonToDraw();
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
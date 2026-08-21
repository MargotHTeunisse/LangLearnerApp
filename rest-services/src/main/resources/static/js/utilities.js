async function drawNext(deck) {
    let wordToTranslate = document.getElementById("word");

    if (deck.draw()) {
        let card = await fetch("card?id=" + deck.getDrawnCardId())
            .then(response => response.json());

        wordToTranslate.textContent = card.visibleWord;

        resetFeedback(deck);

        loadInputField();
    }
    else {
        window.location.replace("endcard.html");
    }

    return deck;
}

function loadInputField() {
    let form = document.getElementById("translation");

    let answer = document.createElement("input");
    answer.id = "answer";
    answer.type = "text";
    answer.autofocus = true;
    form.textContent = "Your answer: "
    form.appendChild(answer);
    answer.focus();
}

function resetFeedback(deck) {
    document.getElementById("word").classList.remove("correct");
    document.getElementById("word").classList.remove("incorrect");
    document.getElementById("feedback").innerHTML = "<br>"

    let button = document.getElementById("drawButton");
    button.onclick = () => {showAnswer(deck).then()};
    button.value = "Show answer";

    document.onkeydown = undefined;
}

async function submitAnswer(deck) {
    let cardID = deck.getDrawnCardId();

    let feedback = document.getElementById("feedback");
    let answer = document.getElementById("answer");

    let response = await fetch("feedback?answer="+answer.value+"&id="+cardID)
        .then(response => response.json());

    if (response.answerIsCorrect) {
        feedback.textContent = "Correct!";
        let translation = document.getElementById("translation");
        translation.removeChild(answer);
        translation.textContent += answer.value;

        document.getElementById("word").classList.add("correct");

        changeButtonToDraw(deck);
    }
    else {
        feedback.textContent = "That is incorrect.";
    }
}

async function showAnswer(deck) {
    let wordToTranslate = document.getElementById("word");

    let cardID = deck.getDrawnCardId();

    wordToTranslate.textContent = await fetch("answer?id=" + cardID)
        .then(response => response.json())
        .then(response => response.visibleWord);

    wordToTranslate.classList.add("incorrect");

    let form = document.getElementById("translation");
    form.innerHTML = "<br>";

    changeButtonToDraw(deck);
}

function changeButtonToDraw(deck) {
    let button = document.getElementById("drawButton");
    button.onclick = () => {drawNext(deck).then()};
    button.value = "Draw next";

    document.onkeydown = function(e) {
        if (e.key === "Enter") {
            drawNext(deck).then();
        }
    }
}
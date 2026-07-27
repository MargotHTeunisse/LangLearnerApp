async function submitAnswer() {
    let feedback = document.getElementById("feedback");
    let answer = document.getElementById("answer");

    let answerIsCorrect = await fetch("/session/submit?answer="+answer.value, {method: "POST"})
        .then(response => response.json());

    if (answerIsCorrect) {
        feedback.textContent = "Correct!";
        let translation = document.getElementById("translation");
        translation.removeChild(answer);
        translation.textContent += answer.value;

        document.getElementById("word").style.borderColor = "green";

        changeButtonToDraw();
    }
    else {
        feedback.textContent = "That is incorrect.";
    }
}
async function submitAnswer() {
    let feedback = document.getElementById("feedback");
    let answer = document.getElementById("answer");

    let response = await fetch("/api/submit?answer="+answer.value)
        .then(response => response.json());

    if (response.answerIsCorrect) {
        feedback.textContent = "Correct!";
        let translation = document.getElementById("translation");
        translation.removeChild(answer);
        translation.textContent += answer.value;

        document.getElementById("word").style.borderColor = "green";
        document.getElementById("word").style.backgroundColor = "lightgreen";

        changeButtonToDraw();
    }
    else {
        feedback.textContent = "That is incorrect.";
    }
}
async function submitAnswer() {

    let feedback = document.getElementById("feedback");
    let answerIsCorrect = await fetch("/session/submit-answer",
        {
            method: "POST",
            body: JSON.stringify({answer: document.getElementById("answer").value}),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        })
        .then(response => response.json());

    if (answerIsCorrect) {
        feedback.textContent = "Correct!";
        let checkButton = document.getElementById("check");
        let translation = checkButton.parentElement;
        let answer = document.getElementById("answer");
        translation.removeChild(answer);
        translation.removeChild(checkButton);
        translation.textContent += answer.value;
    }
    else {
        feedback.textContent = "That is incorrect.";
    }
}
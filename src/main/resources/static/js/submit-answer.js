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
        let translation = document.getElementById("translation");
        let answer = document.getElementById("answer")
        translation.removeChild(answer);
        translation.textContent += answer.value;

        var drawButton = document.getElementById("drawButton");

        document.addEventListener('keypress', (e) => {
            if (e.key === "Enter") {
                    drawButton.click();
            }
        });
    }
    else {
        feedback.textContent = "That is incorrect.";
    }
}
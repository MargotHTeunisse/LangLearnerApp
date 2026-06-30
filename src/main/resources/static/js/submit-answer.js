async function submitAnswer() {
    var feedback = document.getElementById("feedback");
    feedback.textContent = await fetch("/session/submit-answer",
        {
            method: "POST",
            body: JSON.stringify({answer: document.getElementById("answer").value}),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        })
        .then(response => response.json());
}
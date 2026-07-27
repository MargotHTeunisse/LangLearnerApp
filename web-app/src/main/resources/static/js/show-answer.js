async function showAnswer() {
    await fetch("/session/show-answer", {method: "POST"})

    window.location.reload();
}
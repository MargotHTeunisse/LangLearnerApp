async function showAnswer() {
    await fetch("/api/show-answer", {method: "POST"})

    window.location.reload();
}
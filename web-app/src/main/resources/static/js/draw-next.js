async function drawNext() {
    const deckView = await fetch("/session/draw-next-card", {method: "POST"})
        .then(response => response.json());

    if (deckView.deckIsDepleted) {
        window.location.replace("endcard.html");
    }
    else {
        window.location.reload();
    }
}

async function drawNext() {
    let response = await fetch("/api/draw-next-card", {method: "POST"});

    if (response.deckIsDepleted) {
        window.location.replace("endcard.html");
    }
    else {
        window.location.reload();
    }
}

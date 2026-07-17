async function drawNext() {
    let deckContainsCards = await fetch("/session/draw-next-card", {method: "PATCH"})
        .then(response => response.json());

    if (deckContainsCards) {
        window.location.replace("index.html");
    }
    else {
        window.location.replace("endcard.html");
    }
}
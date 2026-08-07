function changeButtonToDraw() {
    let button = document.getElementById("button");
    button.value = "Draw next";
    button.onclick = function() {drawNext()};

    document.addEventListener('keypress', (e) => {
        if (e.key === "Enter") {
            button.click();
        }
    });
}
async function fetchWord() {
    let wordToTranslate = document.getElementById("word");
    wordToTranslate.textContent = await fetch("/session/fetch-word")
            .then(response => response.text());
}

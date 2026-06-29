async function fetchWord() {
    var wordToTranslate = document.getElementById('word');
    let fetchedWord = await fetch("/session/")
        .then(response => response.text());
    wordToTranslate.textContent = fetchedWord;
}
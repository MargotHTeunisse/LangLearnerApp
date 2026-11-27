async function changeLanguage() {
    let sourceLanguage = document.getElementById("selectedSourceLanguage");
    let targetLanguage = document.getElementById("selectedTargetLanguage");

    await fetch("/api/change-language?sourceLanguage="+sourceLanguage.value
        +"&targetLanguage="+targetLanguage.value, {method:"post"});

    window.location.replace("index.html");
}
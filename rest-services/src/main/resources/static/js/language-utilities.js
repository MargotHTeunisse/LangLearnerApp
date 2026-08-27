async function changeLanguage() {
    let sourceLanguage = document.getElementById("selectedSourceLanguage");
    let targetLanguage = document.getElementById("selectedTargetLanguage");

    sessionStorage.clear();

    sessionStorage.setItem("sourceLanguage", sourceLanguage.value);
    sessionStorage.setItem("targetLanguage", targetLanguage.value);

    window.location.replace("index.html");
}

async function loadSettings() {
    let languageOptions = await fetch("/language-options", {method:"get"})
        .then(response => response.json());

    const sourceLanguages = Object.keys(languageOptions);

    const sourceLanguageOptions = document.getElementById("selectedSourceLanguage");

    sourceLanguages.forEach(language => {
        const option = document.createElement('option');
        option.value = language;
        option.textContent = language;

        sourceLanguageOptions.appendChild(option);
    })

    sourceLanguageOptions.onchange = () => {
        const targetLanguageOptions = document.getElementById("selectedTargetLanguage");
        targetLanguageOptions.length=0;

        let choice = sourceLanguageOptions.value;

        let targetLanguages = languageOptions[choice];

        targetLanguages.forEach(language => {
            const option = document.createElement('option');
            option.value = language;
            option.textContent = language;

            targetLanguageOptions.appendChild(option);
        });

        let button = document.getElementById("languageButton");
        button.onclick= () => {changeLanguage()};
    }
}

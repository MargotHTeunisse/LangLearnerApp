async function loadSettings() {
    let languageOptions = await fetch("/api/language-options", {method:"get"})
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
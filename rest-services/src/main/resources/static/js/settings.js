const {createApp, ref} = Vue

const app = createApp({
    data() {
        let sourceLanguage = sessionStorage.getItem('sourceLanguage')
        let targetLanguage = sessionStorage.getItem('targetLanguage')
        return {
            deckChosen: (sourceLanguage !== null && targetLanguage !== null),
            sourceLanguage: null,
            targetLanguage: null,
            languageOptions: ref({
                value:{}
            })
        }
    },

    async beforeMount() {
        this.languageOptions.value = await fetch("/language-options")
            .then(response => response.json());
    },

    methods: {
        sourceLanguageOptions() {
            return Object.keys(this.languageOptions.value)
        },
        targetLanguageOptions() {
            return this.languageOptions.value[this.sourceLanguage]
        },

        selectSource() {
            this.targetLanguage = null;
        },

        changeLanguage() {
            if (this.sourceLanguage === null || this.targetLanguage === null) {
                return
            }

            sessionStorage.clear();

            sessionStorage.setItem("sourceLanguage", this.sourceLanguage);
            sessionStorage.setItem("targetLanguage", this.targetLanguage);

            window.location.replace("index.html");
        },
    }
})
app.directive('beforeMount')
app.mount("#app")
import {UserDeck} from "./UserDeck.js";

const {createApp, ref, reactive, toRaw} = Vue

const app = createApp({
    data() {
        let numberTranslated = (sessionStorage.getItem("numberTranslated"))
        if (numberTranslated === null) {
            numberTranslated = 0;
        }

        return {
            source: sessionStorage.getItem("sourceLanguage"),
            target: sessionStorage.getItem("targetLanguage"),

            drawnCardId: null,

            numberTranslated: ref(numberTranslated),
            vocabularySize: parseInt(numberTranslated),

            card: reactive({
                visibleWord: "####",
                answerIsCorrect: null,
                answerIsVisible: false
            }),
            deck: ref({
                type: UserDeck,
                value: null
            }),

            answer: ""
        }
    },

    async beforeMount() {
        let card = JSON.parse(sessionStorage.getItem('card'))

        if (sessionStorage.getItem('cardIDs')) {
            let cardIDs = JSON.parse(sessionStorage.getItem('cardIDs'))

            this.deck.value = new UserDeck(cardIDs)
            this.drawnCardId = sessionStorage.getItem('drawnCardID')

            if (card === null) {
                card = await fetch("card?id=" + this.drawnCardId)
                    .then(response => response.json())
            }

            let answer = sessionStorage.getItem("answer")
            if (answer !== null) {
                this.answer = answer
            }

            this.updateCard(card)

            this.vocabularySize += cardIDs.length + (this.answerIsCorrect === true? 0 : 1)
        }
        else {
            let cardIDs = await fetch("all-card-ids-for-languages?"
                + "source=" + this.source + "&target=" + this.target)
                .then(response => response.json())
                .then(response => response.cardIDs)

            this.vocabularySize += cardIDs.length;

            this.deck.value = new UserDeck(cardIDs)
            await this.drawNext()
        }
    },

    mounted() {
        window.addEventListener('keypress', async(e) => {
            if (e.key === 'Enter' && (this.card.answerIsCorrect || this.card.answerIsVisible)) {
                await this.drawNext()
            }
        })
    },

    watch: {
        'card.answerIsCorrect' (newValue, oldValue) {
                if (newValue === true && oldValue !== true) {
                    this.numberTranslated++
                    sessionStorage.setItem('numberTranslated', this.numberTranslated)
                }
            }
        },

    methods: {
        async drawNext() {
            sessionStorage.removeItem('answer')

            const deck = toRaw(this.deck).value
            if (deck.draw(this.card.answerIsVisible)) {
                deck.cache()
                this.deck.value = deck

                this.drawnCardId = deck.getDrawnCardId()

                let card = await fetch("card?id=" + this.drawnCardId)
                    .then(response => response.json())

                this.updateCard(card)
            }
            else {
                sessionStorage.removeItem('cardIDs')
                sessionStorage.removeItem('drawnCardID')
                sessionStorage.removeItem('card')
                sessionStorage.removeItem('numberTranslated')

                window.location.replace("endcard.html")
            }
        },

        async submitAnswer() {
            if (this.card.answerIsVisible) {
                throw new Error("Cannot submit answer while answer is visible.")
            }
            else {
                let card = await fetch("feedback?id=" + this.drawnCardId
                    + "&answer=" + this.answer)
                    .then(response => response.json())

                this.updateCard(card)
            }

            sessionStorage.setItem("answer", this.answer)
        },

        async showAnswer() {
            let card = await fetch("answer?id=" + this.drawnCardId)
                .then(response => response.json())

            this.updateCard(card)
        },

        updateCard(card) {
            this.card.visibleWord = card.visibleWord
            this.card.answerIsCorrect = card.answerIsCorrect
            this.card.answerIsVisible = card.answerIsVisible

            if (this.card.answerIsCorrect === null) {
                this.answer = ""
                this.$nextTick(() => {
                        this.$refs.answer.focus()
                    }
                )
            }

            sessionStorage.setItem('card', JSON.stringify(card))
        }
    }
})
app.directive('beforeMount')
app.directive('mounted')
app.directive('watch')
app.mount("#app")
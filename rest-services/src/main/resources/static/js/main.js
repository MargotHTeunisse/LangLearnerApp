import {UserDeck} from "./UserDeck.js";

const {createApp, ref, reactive, toRaw} = Vue

const app = createApp({
    data() {
        return {
            source: sessionStorage.getItem("sourceLanguage"),
            target: sessionStorage.getItem("targetLanguage"),

            drawnCardId: null,

            card: reactive({
                visibleWord: null,
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
        let cardIDs = sessionStorage.getItem('cardIDs')

        let card = JSON.parse(sessionStorage.getItem('card'))

        if (cardIDs !== null) {
            this.deck.value = new UserDeck(JSON.parse(cardIDs))
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
        }
        else {
            this.deck.value = new UserDeck(await fetch("all-card-ids-for-languages?"
                + "source=" + this.source + "&target=" + this.target)
                .then(response => response.json())
                .then(response => response.cardIDs)
            )
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

    methods: {
        async drawNext() {
            sessionStorage.removeItem('answer')

            const deck = toRaw(this.deck).value
            if (deck.draw()) {
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
app.mount("#app")
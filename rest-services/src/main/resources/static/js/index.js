import {UserDeck} from "./UserDeck.js";

const {createApp, ref, reactive, toRaw} = Vue

const app = createApp({
    data() {
        if (!sessionStorage.getItem("discards")) {
            sessionStorage.setItem("discards", JSON.stringify([]))
        }

        let card = {
            id: null,
            visibleWord: "####",
            answerIsCorrect: null,
            answerIsVisible: false
        }

        if (sessionStorage.getItem("card")) {
            let cardInStorage = JSON.parse(sessionStorage.getItem("card"))

            card.id = cardInStorage.id
            card.visibleWord = cardInStorage.visibleWord
            card.answerIsCorrect = cardInStorage.answerIsCorrect
            card.answerIsVisible = cardInStorage.answerIsVisible
        }

        return {
            source: sessionStorage.getItem("sourceLanguage"),
            target: sessionStorage.getItem("targetLanguage"),

            numberTranslated: ref(JSON.parse(sessionStorage.getItem("discards")).length),
            vocabularySize: 0,

            card: reactive(card),
            deck: ref({
                type: UserDeck,
                value: null
            }),

            answer: ""
        }
    },

    async beforeMount() {
        let cardIDs = await fetch("all-card-ids-for-languages?"
            + "source=" + this.source + "&target=" + this.target)
            .then(response => response.json())
            .then(response => response.cardIDs)

        let discards = JSON.parse(sessionStorage.getItem("discards"))
        let remaining = cardIDs.filter(x => !discards.includes(x))
        this.vocabularySize += discards.length + remaining.length

        let answer = sessionStorage.getItem("answer")
        if (answer !== null) {
            this.answer = answer
        }

        let deck = new UserDeck(remaining)
        deck.shuffle()

        this.deck.value = deck
        if (this.card.id === null) {
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

                    let discards = JSON.parse(sessionStorage.getItem("discards"))
                    discards.push(this.card.id)
                    sessionStorage.setItem("discards", JSON.stringify(discards))
                }
            }
        },

    methods: {
        async drawNext() {
            sessionStorage.removeItem('answer')

            const deck = toRaw(this.deck).value
            if (deck.draw(this.card.answerIsVisible)) {
                this.deck.value = deck

                let card = await fetch("card?id=" + deck.getDrawnCardId())
                    .then(response => response.json())

                this.updateCard(card)
            }
            else {
                sessionStorage.removeItem('discards')
                sessionStorage.removeItem('card')

                window.location.replace("endcard.html")
            }
        },

        async submitAnswer() {
            if (this.card.answerIsVisible) {
                throw new Error("Cannot submit answer while answer is visible.")
            }
            else {
                let card = await fetch("feedback?id=" + this.card.id
                    + "&answer=" + this.answer)
                    .then(response => response.json())

                this.updateCard(card)
            }

            sessionStorage.setItem("answer", this.answer)
        },

        async showAnswer() {
            let card = await fetch("answer?id=" + this.card.id)
                .then(response => response.json())

            this.updateCard(card)
        },

        updateCard(card) {
            this.card.id = card.id
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
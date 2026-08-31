import {UserDeck} from "./UserDeck.js";

const {createApp, ref, reactive, toRaw} = Vue

const app = createApp({
    data() {
        if (!sessionStorage.getItem("discards")) {
            sessionStorage.setItem("discards", JSON.stringify([]))
        }

        let card = {
            visibleWord: "####",
            answerIsCorrect: null,
            answerIsVisible: false
        }

        if (sessionStorage.getItem("card")) {
            let cardInStorage = JSON.parse(sessionStorage.getItem("card"))

            card.visibleWord = cardInStorage.visibleWord
            card.answerIsCorrect = cardInStorage.answerIsCorrect
            card.answerIsVisible = cardInStorage.answerIsVisible
        }

        return {
            source: sessionStorage.getItem("sourceLanguage"),
            target: sessionStorage.getItem("targetLanguage"),

            drawnCardId: null,

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
        this.drawnCardId = sessionStorage.getItem('drawnCardID')

        let cardIDs = await fetch("all-card-ids-for-languages?"
            + "source=" + this.source + "&target=" + this.target)
            .then(response => response.json())
            .then(response => response.cardIDs)

        let discards = JSON.parse(sessionStorage.getItem("discards"))
        let remaining = cardIDs.filter(x => !discards.includes(x))
        this.vocabularySize += discards.length + remaining.length

        this.deck.value = new UserDeck(remaining)
        if (this.drawnCardId === null) {
            await this.drawNext()
        }
        else if (!sessionStorage.getItem("card")) {
            let card = await fetch("card?id=" + this.drawnCardId)
                    .then(response => response.json())

            this.updateCard(card)
        }

        let answer = sessionStorage.getItem("answer")
        if (answer !== null) {
            this.answer = answer
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
                    discards.push(this.drawnCardId)
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

                this.drawnCardId = deck.getDrawnCardId()

                let card = await fetch("card?id=" + this.drawnCardId)
                    .then(response => response.json())

                this.updateCard(card)
            }
            else {
                sessionStorage.removeItem('discards')
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
            sessionStorage.setItem('drawnCardID', this.drawnCardId)
        }
    }
})
app.directive('beforeMount')
app.directive('mounted')
app.directive('watch')
app.mount("#app")
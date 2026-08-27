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
        this.deck.value =
            new UserDeck(await fetch("all-card-ids-for-languages?"
                + "source=" + this.source + "&target=" + this.target)
                .then(response => response.json())
                .then(response => response.cardIDs)
            )

        await this.drawNext()
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
            const deck = toRaw(this.deck).value
            if (deck.draw()) {
                this.deck.value = deck

                this.drawnCardId = deck.getDrawnCardId()

                let card = await fetch("card?id=" + this.drawnCardId)
                    .then(response => response.json())

                this.updateCard(card)
            }
            else {
                window.location.replace("endcard.html")
            }
        },

        async submitAnswer() {
            let card = await fetch("feedback?id=" + this.drawnCardId
                + "&answer="+this.answer)
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
        }
    }
})
app.directive('beforeMount')
app.directive('mounted')
app.mount("#app")
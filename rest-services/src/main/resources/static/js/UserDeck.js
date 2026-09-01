export class UserDeck {
    #cardIDs;
    #drawnCardID = null;

    constructor(cardIDs) {
        this.#cardIDs = cardIDs;

        this.draw = function() {
            if (this.#cardIDs.length === 0) {
                return false;
            }

            this.#drawnCardID = this.#cardIDs[0];
            this.#cardIDs.shift();

            return true;
        }

        this.putBack = function() {
            if (this.#drawnCardID === null) {
                throw new Error("Cannot put back drawn card if drawn card is null.")
            }

            this.#cardIDs.push(this.#drawnCardID)
            this.#drawnCardID = null;
            this.shuffle();
        }

        this.shuffle = function() {
            //Shuffles card indices using Fisher-Yates algorithm
            for (let i = this.#cardIDs.length - 1; i > 0; i--) {
                // Generate a random index j such that 0 ≤ j ≤ i
                const j = Math.floor(Math.random() * (i + 1));

                // Swap elements at indices i and j
                [this.#cardIDs[i], this.#cardIDs[j]] = [this.#cardIDs[j], this.#cardIDs[i]];
            }
        }

        this.getDrawnCardId = function() {
            return this.#drawnCardID;
        }
    }
}
export class UserDeck {
    #cardIDs;
    #drawnCardID = null;

    constructor(cardIDs) {
        this.#cardIDs = cardIDs;

        this.draw = function(putBack) {
            if (putBack && this.#drawnCardID === null) {
                throw new Error("Cannot put back drawn card if drawn card is null.")
            }

            if (this.#cardIDs.length === 0) {
                return putBack;
            }

            if (putBack) {
                this.#cardIDs.push(this.#drawnCardID)
            }


            this.#drawnCardID = this.#cardIDs[0];
            this.#cardIDs.shift();

            return true;
        }

        this.getDrawnCardId = function() {
            return this.#drawnCardID;
        }

        this.cache = function() {
            sessionStorage.setItem('drawnCardID', this.#drawnCardID)
            sessionStorage.setItem('cardIDs', JSON.stringify(this.#cardIDs))
        }
    }
}
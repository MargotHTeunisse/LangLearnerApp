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

        this.getDrawnCardId = function() {
            return this.#drawnCardID;
        }
    }
}
import assert from "node:assert";
import {UserDeck} from "../../main/resources/static/js/UserDeck.js"

import flush from "flush-cache";

describe("UserDeck", function () {
  describe("draw", function () {
    it("should return false if no cards left",function () {
        let deck = new UserDeck([]);

        assert.equal(deck.draw(), false);
    });

    it("should return true if any cards left",  function() {
        let deck = new UserDeck([0]);

        assert.equal(deck.draw(), true);
    });

    it("should remove card", function() {
      let deck = new UserDeck([0])

      deck.draw();

      assert.equal(deck.draw(), false)
    });
  });

  describe("drawn card id", function() {
      it("is null upon initiation", function() {
          let deck = new UserDeck([0])

          assert.equal(deck.getDrawnCardId(), null);
      });

      it("is not null after drawing once", function() {
              let deck = new UserDeck([0])

              deck.draw();

              assert.notEqual(deck.getDrawnCardId(), null);
          });

      it("is updated after drawing", async function() {
          let deck = new UserDeck([1, 2]);
          deck.draw();
          let firstCardId = deck.getDrawnCardId();

          deck.draw();
          let secondCardId = deck.getDrawnCardId();

          assert.notEqual(secondCardId, firstCardId);
      });
  });

    describe("cache", function() {
        it("stores remaining card IDs", function() {
            let deck = new UserDeck([0, 1, 2, 3])
            deck.draw()

            deck.cache()

            assert.deepEqual(JSON.parse(sessionStorage.getItem('cardIDs')), [1, 2, 3])

            flush()
        })

        it("stores drawn card ID", function() {
            let deck = new UserDeck([0, 1, 2, 3])
            deck.draw()

            deck.cache()

            assert.equal(sessionStorage.getItem('drawnCardID'), [0])

            flush()
        })
    })
});
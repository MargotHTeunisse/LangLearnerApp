import assert from "node:assert";
import {UserDeck} from "../../main/resources/static/js/UserDeck.js"

describe("UserDeck", function () {
  describe("draw", function () {
    it("should return false if no cards left",function () {
        let deck = new UserDeck([]);

        assert.equal(deck.draw(false), false);
    });

    it("should return true if any cards left",  function() {
        let deck = new UserDeck([0]);

        assert.equal(deck.draw(false), true);
    });

    it("should remove card if card not put back", function() {
      let deck = new UserDeck([0])
      deck.draw(false);

      let canDraw = deck.draw(false)

      assert.equal(canDraw, false)
    });

    it("should not remove card if card is put back", function() {
       let deck = new UserDeck([0]);
       deck.draw(false);

       let canDraw = deck.draw(true)

       assert.equal(canDraw, true)
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

      it("is updated after drawing", function() {
          let deck = new UserDeck([1, 2]);
          deck.draw(false);
          let firstCardId = deck.getDrawnCardId();

          deck.draw(true);
          let secondCardId = deck.getDrawnCardId();

          assert.notEqual(secondCardId, firstCardId);
      });

      it("retrieves card which was put back", function() {
          let deck = new UserDeck([1, 2]);
          deck.draw(false);
          let firstCardId = deck.getDrawnCardId();

          deck.draw(true);
          deck.draw(true);
          let thirdCardId = deck.getDrawnCardId();

          assert.equal(thirdCardId, firstCardId);
      });

      it("cannot put back if drawn card is null", function() {
          let deck = new UserDeck([])

         assert.throws(() => {deck.draw(true)},
             {message:"Cannot put back drawn card if drawn card is null."})
      })
  });
});
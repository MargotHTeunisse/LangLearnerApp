import assert from "node:assert";
import * as sinon from "sinon";
import {UserDeck} from "../../main/resources/static/js/UserDeck.js"

describe("UserDeck", function () {
  describe("draw", function () {
    it("returns false if no cards left",function () {
        let deck = new UserDeck([]);

        assert.equal(deck.draw(), false);
    });

    it("returns true if any cards left",  function() {
        let deck = new UserDeck([0]);

        assert.equal(deck.draw(), true);
    });

    it("removes card", function() {
      let deck = new UserDeck([0])
      deck.draw(false);

      assert.equal(deck.draw(), false)
    });

    it("does not shuffle deck", function() {
        let deck = new UserDeck([0])
        let shuffleSpy = sinon.spy(deck, "shuffle");
        deck.draw();

        assert.equal(shuffleSpy.notCalled, true);
    })
  });

  describe("put back", function() {
      it("shuffles deck once", function() {
          let deck = new UserDeck([0])
          let shuffleSpy = sinon.spy(deck, "shuffle");
          deck.draw();

          deck.putBack();

          assert.equal(shuffleSpy.calledOnce, true);
      });

      it("raises error if drawn card id is null", function() {
          let deck = new UserDeck([])

          assert.throws(() => {deck.putBack()},
              {message:"Cannot put back drawn card if drawn card is null."})
      });

      it("puts drawn card back in deck", function() {
          let deck = new UserDeck([1]);
          deck.draw();
          let firstCardId = deck.getDrawnCardId();
          deck.putBack()

          deck.draw();
          let secondCardId = deck.getDrawnCardId();

          assert.equal(secondCardId, firstCardId);
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

      it("is null after card is put back", function() {
          let deck = new UserDeck([0])

          deck.draw();
          deck.putBack();

          assert.equal(deck.getDrawnCardId(), null);
      });

      it("is updated after drawing", function() {
          let deck = new UserDeck([1, 2]);
          deck.draw();
          let firstCardId = deck.getDrawnCardId();

          deck.draw();
          let secondCardId = deck.getDrawnCardId();

          assert.notEqual(secondCardId, firstCardId)
      });
  });
});
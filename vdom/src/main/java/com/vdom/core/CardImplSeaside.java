package com.vdom.core;

import java.util.ArrayList;
import java.util.List;

import com.vdom.api.Card;
import com.vdom.api.GameEvent;

public class CardImplSeaside extends CardImpl {
	private static final long serialVersionUID = 1L;

	public CardImplSeaside(CardImpl.Builder builder) {
		super(builder);
	}
	
	protected CardImplSeaside() { }

	@Override
    public void followInstructions(Game game, MoveContext context, Card responsible, Player currentPlayer, boolean isThronedEffect, PlayContext playContext) {
        super.followInstructions(game, context, responsible, currentPlayer, isThronedEffect, playContext);
		switch(getKind()) {
		case Ambassador:
            ambassador(game, context, currentPlayer);
            break;
		case Cutpurse:
             cutpurse(game, context, currentPlayer);
             break;
		case Embargo:
            embargo(game, context, currentPlayer);
            break;
		case Explorer:
            explorer(context, currentPlayer);
            break;
		case GhostShip:
            ghostShip(game, context, currentPlayer);
            break;
		case Haven:
            haven(context, currentPlayer, isThronedEffect);
            break;
		case Island:
            island(game, context, currentPlayer);
            break;
		case Lookout:
            lookout(game, context, currentPlayer);
            break;
		case NativeVillage:
            nativeVillage(game, context, currentPlayer);
            break;
		case Navigator:
            navigator(game, context, currentPlayer);
            break;
		case Outpost:
			currentPlayer.addStartTurnDurationEffect(this, 1, isThronedEffect);
			break;
		case PearlDiver:
            pearlDiver(context, currentPlayer);
            break;
		case PirateShip:
            pirateShip(game, context, currentPlayer, playContext);
            break;
		case Salvager:
            salvager(context, currentPlayer, playContext);
            break;
		case SeaHag:
            seaHag(game, context, currentPlayer);
            break;
		case Smugglers:
            smugglers(context, currentPlayer);
            break;
		case Tactician:
            tactician(context, currentPlayer, isThronedEffect);
            break;
		case TreasureMap:
            treasureMap(context, currentPlayer);
            break;
		case Warehouse:
            warehouse(context, currentPlayer);
            break;
		default:
			break;
		}
	}

	private void ambassador(Game game, MoveContext context, Player currentPlayer) {
        if (currentPlayer.hand.size() == 0) {
            return;
        }

        Card card = currentPlayer.controlPlayer.ambassador_revealedCard(context);

        if (card == null) {
            card = Util.randomCard(currentPlayer.hand);
        } else if (!currentPlayer.hand.contains(card)) {
            Util.playerError(currentPlayer, "Ambassador revealed card error, picking random card.");
            card = Util.randomCard(currentPlayer.hand);
        }

        CardPile pile = game.getGamePile(card);

        currentPlayer.reveal(card, this, context);
        //Util.log("Ambassador revealed card:" + origCard.getName());

        int returnCount = -1;
        if (!pile.isSupply()) {
            // Wiki: If you reveal a card which is not in the Supply, such as Spoils, Madman Mercenary, or Shelters, Ambassador does nothing
            Util.playerError(currentPlayer, "Ambassador revealed card not in supply, returning 0.");
        } else {
            returnCount = currentPlayer.controlPlayer.ambassador_returnToSupplyFromHand(context, card);
            if (returnCount > 2) {
                Util.playerError(currentPlayer, "Ambassador return to supply error (more than 2 cards), returning 2.");
                returnCount = 2;
            } else {
                int inHandCount = currentPlayer.inHandCount(card);
                if (returnCount > inHandCount) {
                    Util.playerError(currentPlayer, "Ambassador return to supply error (more than cards in hand), returning " + inHandCount);
                    returnCount = inHandCount;
                }
            }
        }

        for (int i = 0; i < returnCount; i++) {
            int idx = currentPlayer.hand.indexOf(card);
            if (idx > -1) {
                Card returningCard = currentPlayer.hand.remove(idx);
                pile.addCard(returningCard);
            } else {
                Util.playerError(currentPlayer, "Ambassador return to supply error, just returning those available.");
                break;
            }
        }

        /* Even if revealed Shelters, opponents may react */
        for (Player player : game.getPlayersInTurnOrder()) {
            if (player != currentPlayer && !Util.isDefendedFromAttack(game, player, this)) {
                player.attacked(this, context);

                if (returnCount > -1) {
                  if (pile.isSupply()) {
                      player.gainNewCard(card, this, new MoveContext(game, player));
                  }
                }
            }
        }
    }
	
	private void cutpurse(Game game, MoveContext context, Player currentPlayer) {
        for (Player player : game.getPlayersInTurnOrder()) {
            if (player != currentPlayer && !Util.isDefendedFromAttack(game, player, this)) {
                player.attacked(this, context);
                MoveContext playerContext = new MoveContext(game, player);
                playerContext.attackedPlayer = player;

                if (player.hand.contains(Cards.copper)) {
                    Card card = player.hand.get(Cards.copper);
                    player.hand.remove(Cards.copper);
                    player.discard(card, this, playerContext);
                } else {
                    for (Card card : player.getHand()) {
                        player.reveal(card, this, playerContext);
                    }
                }
            }
        }
    }
	
    private void embargo(Game game, MoveContext context, Player currentPlayer) {
    	if (!currentPlayer.trashSelfFromPlay(this, context))
    		return;
        Card card = currentPlayer.controlPlayer.embargo_supplyToEmbargo(context);

        while (game.addEmbargo(card) == null) {
            Util.playerError(currentPlayer, "Embargo error, adding embargo to random card.");
            while (true) {
                card = Util.randomCard(context.getCardsInGame(GetCardsInGameOptions.Placeholders, true));
                if (game.isValidEmbargoPile(card)) break;
            }
        }

        GameEvent event = new GameEvent(GameEvent.EventType.Embargo, context);
        event.card = card;
        game.broadcastEvent(event);
    }
    
    private void explorer(MoveContext context, Player currentPlayer) {
        Card province = null;
        for (Card card : currentPlayer.hand) {
            if (card.equals(Cards.province)) {
                province = card;
                break;
            }
        }

        Card treasure;
        if (province != null && currentPlayer.controlPlayer.explorer_shouldRevealProvince(context)) {
            currentPlayer.reveal(province, this, context);
            treasure = Cards.gold;
        } else {
            treasure = Cards.silver;
        }

        currentPlayer.gainNewCard(treasure, this, context);
    }

    private void ghostShip(Game game, MoveContext context, Player currentPlayer) {
        for (Player player : game.getPlayersInTurnOrder()) {
            if (player != currentPlayer && !Util.isDefendedFromAttack(game, player, this)) {
                player.attacked(this, context);
                MoveContext playerContext = new MoveContext(game, player);
                playerContext.attackedPlayer = player;

                if (player.hand.size() >= 4) {
                    Card[] cards = player.controlPlayer.ghostShip_attack_cardsToPutBackOnDeck(playerContext);
                    boolean bad = false;
                    if (cards == null || cards.length != player.hand.size() - 3) {
                        bad = true;
                    } else {
                        ArrayList<Card> copy = Util.copy(player.hand);
                        for (Card card : cards) {
                            if (!copy.remove(card)) {
                                bad = true;
                                break;
                            }
                        }
                    }

                    if (bad) {
                        Util.playerError(player, "Ghost Ship put back cards error, putting back the first " + (player.hand.size() - 3) + " cards.");

                        cards = new Card[player.hand.size() - 3];
                        for (int i = 0; i < player.hand.size() - 3; i++) {
                            cards[i] = player.hand.get(i);
                        }
                    }
                    
                    GameEvent event = new GameEvent(GameEvent.EventType.CardOnTopOfDeck, context);
                    event.setPlayer(player);
                    
                    for (int i = cards.length - 1; i >= 0; i--) {
                        player.hand.remove(cards[i]);
                        player.putOnTopOfDeck(cards[i]);
                        context.game.broadcastEvent(event);
                    }
                }
            }
        }
    }
    
    private void haven(MoveContext context, Player currentPlayer, boolean isThronedEffect) {
        Card card = currentPlayer.getHand().size() == 0 ? null : currentPlayer.controlPlayer.haven_cardToSetAside(context);
        if ((card == null && currentPlayer.getHand().size() > 0) || (card != null && !currentPlayer.getHand().contains(card))) {
            Util.playerError(currentPlayer, "Haven set aside card error, setting aside the first card in hand.");
            card = currentPlayer.getHand().get(0);
        }

        if (card != null) {
            currentPlayer.getHand().remove(card);
            currentPlayer.haven.add(card);
            currentPlayer.addStartTurnDurationEffect(this, 1, isThronedEffect);	
            GameEvent event = new GameEvent(GameEvent.EventType.CardSetAsidePrivate, (MoveContext) context);
            event.card = card;
            event.responsible = this;
            event.setPrivate(true);
            context.game.broadcastEvent(event);
        }
    }

    private void island(Game game, MoveContext context, Player currentPlayer) {
        Card card = currentPlayer.controlPlayer.island_cardToSetAside(context);
        if (card != null && !currentPlayer.hand.contains(card)) {
            Util.playerError(currentPlayer, "Island set aside card error, just setting aside island.");
            card = null;
        }

        // Move to island mat if not already played
        if (currentPlayer.isInPlay(this)) {
            currentPlayer.playedCards.remove(currentPlayer.playedCards.indexOf(this.getId()));
            currentPlayer.island.add(this);

            GameEvent event = new GameEvent(GameEvent.EventType.CardSetAsideOnIslandMat, (MoveContext) context);
            event.card = this;
            game.broadcastEvent(event);
        }

        if (card != null) {
            currentPlayer.hand.remove(card);
            currentPlayer.island.add(card);

            GameEvent event = new GameEvent(GameEvent.EventType.CardSetAsideOnIslandMat, (MoveContext) context);
            event.card = card;
            game.broadcastEvent(event);
        }
    }
    
    private void lookout(Game game, MoveContext context, Player currentPlayer) {
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 3; i++) {
            Card card = game.draw(context, Cards.lookout, 3 - i);
            if (card != null) {
                cards.add(card);
            }
        }

        if (cards.size() == 0) {
            return;
        }

        Card toTrash;

        if (cards.size() > 1) {
            toTrash = currentPlayer.controlPlayer.lookout_cardToTrash(context, cards.toArray(new Card[cards.size()]));
        } else {
            toTrash = cards.get(0);
        }

        if (toTrash == null || !cards.contains(toTrash)) {
            Util.playerError(currentPlayer, "Lookout trash error, just picking the first card.");
            toTrash = cards.get(0);
        }

        currentPlayer.trash(toTrash, this, context);

        cards.remove(toTrash);
        if (cards.size() == 0) {
            return;
        }

        Card toDiscard;

        if (cards.size() > 1) {
            toDiscard = currentPlayer.controlPlayer.lookout_cardToDiscard(context, cards.toArray(new Card[cards.size()]));
        } else {
            toDiscard = cards.get(0);
        }
        if (toDiscard == null || !cards.contains(toDiscard)) {
            Util.playerError(currentPlayer, "Lookout discard error, just picking the first card.");
            toDiscard = cards.get(0);
        }

        currentPlayer.discard(toDiscard, this, context);

        cards.remove(toDiscard);

        if (cards.size() > 0) {
            currentPlayer.putOnTopOfDeck(cards.get(0));
        }
    }
    
    private void nativeVillage(Game game, MoveContext context, Player currentPlayer) {
        if (currentPlayer.controlPlayer.nativeVillage_takeCards(context)) {
            while (!currentPlayer.nativeVillage.isEmpty()) {
                currentPlayer.hand.add(currentPlayer.nativeVillage.remove(0));
            }
        } else {
            Card draw = game.draw(context, Cards.nativeVillage, 1);
            if (draw != null) {
                currentPlayer.nativeVillage.add(draw);
                Util.sensitiveDebug(currentPlayer, "Added to Native Village:" + draw.getName(), true);
            }
        }
    }

    private void navigator(Game game, MoveContext context, Player currentPlayer) {
        ArrayList<Card> topOfTheDeck = new ArrayList<Card>();
        for (int i = 0; i < 5; i++) {
            Card card = game.draw(context, Cards.navigator, 5 - i);
            if (card != null) {
                topOfTheDeck.add(card);
            }
        }

        if (topOfTheDeck.size() > 0) {
            if (currentPlayer.controlPlayer.navigator_shouldDiscardTopCards(context, topOfTheDeck.toArray(new Card[topOfTheDeck.size()]))) {
                while (!topOfTheDeck.isEmpty()) {
                    currentPlayer.discard(topOfTheDeck.remove(0), this, context);
                }
            } else {
                Card[] order = currentPlayer.controlPlayer.navigator_cardOrder(context, topOfTheDeck.toArray(new Card[topOfTheDeck.size()]));

                // Check that they returned the right cards
                boolean bad = false;

                if (order == null) {
                    bad = true;
                } else {
                    ArrayList<Card> copy = new ArrayList<Card>();
                    for (Card card : topOfTheDeck) {
                        copy.add(card);
                    }

                    for (Card card : order) {
                        if (!copy.remove(card)) {
                            bad = true;
                            break;
                        }
                    }

                    if (!copy.isEmpty()) {
                        bad = true;
                    }
                }

                if (bad) {
                    Util.playerError(currentPlayer, "Navigator order cards error, ignoring.");
                    order = topOfTheDeck.toArray(new Card[topOfTheDeck.size()]);
                }

                // Put the cards back on the deck
                for (int i = order.length - 1; i >= 0; i--) {
                    currentPlayer.putOnTopOfDeck(order[i]);
                }
            }
        }
    }
    
    private void pearlDiver(MoveContext context, Player currentPlayer) {
        if (currentPlayer.getDeckSize() == 0 && currentPlayer.discard.size() > 0) {
            context.game.replenishDeck(context, Cards.pearlDiver, 0);
        }

        if (currentPlayer.getDeckSize() > 1) {
            Card card = currentPlayer.peekAtDeckBottom();
            if (currentPlayer.controlPlayer.pearlDiver_shouldMoveToTop(context, card)) {
                currentPlayer.removeFromDeckBottom();
                currentPlayer.putOnTopOfDeck(card);
            }
        }
    }
    
    private void pirateShip(Game game, MoveContext context, Player currentPlayer, PlayContext playContext) {
        ArrayList<Player> playersToAttack = new ArrayList<Player>();
        for (Player targetPlayer : game.getPlayersInTurnOrder()) {
            if (targetPlayer != currentPlayer && !Util.isDefendedFromAttack(game, targetPlayer, this)) {
                playersToAttack.add(targetPlayer);
                targetPlayer.attacked(this, context);
            }
        }

        if (currentPlayer.controlPlayer.pirateShip_takeTreasure(context)) {
            takePirateShipTreasure(context, playContext);
        } else {
            boolean treasureFound = false;
            for (Player targetPlayer : playersToAttack) {
                MoveContext targetContext = new MoveContext(game, targetPlayer);
                targetContext.attackedPlayer = targetPlayer;
                ArrayList<Card> treasures = new ArrayList<Card>();
                List<Card> cardToDiscard = new ArrayList<Card>();

                for (int i = 0; i < 2; i++) {
                    Card card = game.draw(targetContext, Cards.pirateShip, 2 - i);

                    if (card != null) {
                        targetPlayer.reveal(card, this, targetContext);

                        if (card.is(Type.Treasure, targetPlayer, context)) {
                            treasures.add(card);
                        } else {
                            cardToDiscard.add(card);
                        }
                    }
                }
                for (Card c: cardToDiscard) {
                    targetPlayer.discard(c, this, targetContext);
                }

                Card cardToTrash = null;

                if (treasures.size() == 1) {
                    cardToTrash = treasures.get(0);
                } else if (treasures.size() == 2) {
                    if (treasures.get(0).equals(treasures.get(1))) {
                        cardToTrash = treasures.get(0);
                        targetPlayer.discard(treasures.get(1), this, targetContext);
                    } else {
                        cardToTrash = currentPlayer.controlPlayer.pirateShip_treasureToTrash(context, treasures.toArray(new Card[] {}));
                    }

                    for (Card treasure : treasures) {
                        if (!treasure.equals(cardToTrash)) {
                            targetPlayer.discard(treasure, this, targetContext);
                        }
                    }
                }

                if (cardToTrash != null) {
                    targetPlayer.trash(cardToTrash, this, targetContext);
                    treasureFound = true;
                }
            }

            if (treasureFound) {
                increasePirateShipTreasure(currentPlayer);
            }
        }
    }
    
    private void salvager(MoveContext context, Player currentPlayer, PlayContext playContext) {
        if (currentPlayer.hand.size() == 0) {
            return;
        }

        Card card = currentPlayer.controlPlayer.salvager_cardToTrash(context);

        if (card == null || !currentPlayer.hand.contains(card)) {
            Util.playerError(currentPlayer, "Salvager trash error, trashing first card.");
            card = currentPlayer.hand.get(0);
        }

        currentPlayer.trashFromHand(card, this, context);
        context.addCoins(card.getCost(context), this, playContext);
    }
    
    private void seaHag(Game game, MoveContext context, Player currentPlayer) {
        for (Player player : game.getPlayersInTurnOrder()) {
            if (player != currentPlayer && !Util.isDefendedFromAttack(game, player, this)) {
                player.attacked(this, context);
                MoveContext playerContext = new MoveContext(game, player);
                playerContext.attackedPlayer = player;

                Card draw = game.draw(playerContext, Cards.seaHag, 1);
                if (draw != null) {
                    player.discard(draw, this, playerContext);
                }

                player.gainNewCard(Cards.curse, this, playerContext);
            }
        }
    }
    
    private void smugglers(MoveContext context, Player currentPlayer) {
        Card card = currentPlayer.controlPlayer.smugglers_cardToObtain(context);
        if (card != null) {
            if (card.getCost(context) > 6 || !Cards.isSupplyCard(card) || card.costPotion()) {
                Util.playerError(currentPlayer, "Smugglers card error, ignoring.");
                card = null;
            } else {
                boolean found = false;

                for (Card cardToCheck : context.getCardsObtainedByLastPlayer()) {
                    if (cardToCheck == card) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    Util.playerError(currentPlayer, "Smugglers card error, ignoring.");
                    card = null;
                }
            }
        }

        if (card != null) {
            if (currentPlayer.gainNewCard(card, this, context) == null) {
                // TODO do this.controlCard error output everywhere
                Util.playerError(currentPlayer, "Smugglers card error, no more cards left of that type, ignoring.");
            }
        }
    }
    
    private void tactician(MoveContext context, Player currentPlayer, boolean isThronedEffect) {
        if (currentPlayer.hand.size() > 0) {
            while (!currentPlayer.hand.isEmpty()) {
                currentPlayer.discard(currentPlayer.hand.remove(0), this, context);
            }
            currentPlayer.addStartTurnDurationEffect(this, 1, isThronedEffect);
        }
    }
    
    private void treasureMap(MoveContext context, Player currentPlayer) {
    	boolean thisIsMap = this.equals(Cards.treasureMap);
        boolean trashedBoth = currentPlayer.trashSelfFromPlay(this, context);
        trashedBoth &= currentPlayer.trashFromHand(Cards.treasureMap, this, context);
        if (thisIsMap && trashedBoth) {
        	for (int i = 0; i < 4; i++) {
                currentPlayer.gainNewCard(Cards.gold, this, context);
            }
        }
    }
    
    private void warehouse(MoveContext context, Player currentPlayer) {
        if (currentPlayer.hand.size() == 0) {
            return;
        }

        Card[] cards;
        if (currentPlayer.hand.size() > 3) {
            cards = currentPlayer.controlPlayer.warehouse_cardsToDiscard(context);
        } else {
            cards = currentPlayer.getHand().toArray();
        }
        boolean bad = false;
        if (cards == null) {
            bad = true;
        } else if (cards.length > 3) {
            bad = true;
        } else {
            ArrayList<Card> handCopy = Util.copy(currentPlayer.hand);
            for (Card card : cards) {
                if (!handCopy.remove(card)) {
                    bad = true;
                    break;
                }
            }
        }

        if (bad) {
            Util.playerError(currentPlayer, "Warehouse discard error, discarding first 3 cards.");
            cards = new Card[3];

            for (int i = 0; i < cards.length; i++) {
                cards[i] = currentPlayer.hand.get(i);
            }
        }

        for (int i = 0; i < cards.length; i++) {
            currentPlayer.hand.remove(cards[i]);
            currentPlayer.discard(cards[i], this, context);
        }
    }
    
    
    protected void increasePirateShipTreasure(Player player) {
        (player).pirateShipTreasure++;
    }

    protected void takePirateShipTreasure(MoveContext context, PlayContext playContext) {
        context.addCoins(context.getPlayer().pirateShipTreasure, this, playContext);
    }
}

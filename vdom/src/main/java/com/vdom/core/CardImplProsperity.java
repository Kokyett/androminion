package com.vdom.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vdom.api.Card;
import com.vdom.api.GameEvent;

public class CardImplProsperity extends CardImpl {
	private static final long serialVersionUID = 1L;

	public CardImplProsperity(CardImpl.Builder builder) {
		super(builder);
	}
	
	protected CardImplProsperity() { }

	@Override
    public void followInstructions(Game game, MoveContext context, Card responsible, Player currentPlayer, boolean isThronedEffect, PlayContext playContext) {
        super.followInstructions(game, context, responsible, currentPlayer, isThronedEffect, playContext);
		switch(getKind()) {
		case Bank:
			context.addCoins(context.countTreasureCardsInPlay());
			break;
        case Bishop:
            bishop(game, context, currentPlayer);
            break;
        case City:
            city(game, context, currentPlayer, playContext);
            break;
        case Contraband:
        	contraband(context, game);
        	break;
        case CountingHouse:
            countingHouse(context, currentPlayer);
            break;
        case Expand:
            expand(context, currentPlayer);
            break;
        case Forge:
            forge(context, currentPlayer);
            break;
        case Goons:
            goons(game, context, currentPlayer);
            break;
        case KingsCourt:
            throneRoomKingsCourt(game, context, currentPlayer);
            break;
        case Loan:
        	loanVenture(context, currentPlayer, game);
        	break;
        case Mint:
            mint(context, currentPlayer);
            break;
        case Mountebank:
            mountebank(game, context, currentPlayer);
            break;
        case Rabble:
            rabble(game, context, currentPlayer);
            break;
        case TradeRoute:
            tradeRoute(game, context, currentPlayer, playContext);
            break;
        case Vault:
            vault(game, context, currentPlayer, playContext);
            break;
        case Venture:
        	loanVenture(context, currentPlayer, game);
        	break;
        case WatchTower:
            watchTower(game, context, currentPlayer);
            break;
		default:
			break;
		}
	}
	
	@Override
    public void isBuying(MoveContext context) {
		super.isBuying(context);
        switch (this.getKind()) {
            case Mint:
            {
            	ArrayList<Card> toTrash = new ArrayList<Card>();
                for (Card card : context.player.playedCards) {
                    if (card.is(Type.Treasure, context.player, context)) {
                        toTrash.add(card);
                    }
                }
                for (Card c : toTrash) {
                	context.player.trashFromPlay(c, this, context);
                }
                break;
            }
            default:
                break;
        }
    }

	private void bishop(Game game, MoveContext context, Player currentPlayer) {
        if (currentPlayer.getHand().size() > 0) {
            Card card = currentPlayer.controlPlayer.bishop_cardToTrashForVictoryTokens(context);

            if (card == null || !currentPlayer.hand.contains(card)) {
                Util.playerError(currentPlayer, "Bishop trash error, trashing a random card.");
                card = Util.randomCard(currentPlayer.hand);
            }

            currentPlayer.trashFromHand(card, this, context);
            currentPlayer.addVictoryTokens(context, card.getCost(context) / 2, this);
        }

        for (Player player : game.getPlayersInTurnOrder()) {
            if (player != currentPlayer) {
                MoveContext playerContext = new MoveContext(game, player);
                Card card = (player).controlPlayer.bishop_cardToTrash(playerContext);

                if (card != null && player.hand.contains(card)) {
                    player.trashFromHand(card, this, playerContext);
                }
            }
        }
    }
	
	private void city(Game game, MoveContext context, Player currentPlayer, PlayContext playContext) {
        if (game.emptyPiles() > 0) {
            game.drawToHand(context, this, 1, playContext);
        }
        if (game.emptyPiles() > 1) {
            context.buys++;
            context.addCoins(1, this, playContext);
        }
    }
	
	private void contraband(MoveContext context, Game game) {
        Card cantBuyCard = game.getNextPlayer().controlPlayer.contraband_cardPlayerCantBuy(context);

        if (cantBuyCard != null && !context.cantBuy.contains(cantBuyCard)) {
            context.cantBuy.add(cantBuyCard);
            GameEvent e = new GameEvent(GameEvent.EventType.CantBuy, (MoveContext) context);
            game.broadcastEvent(e);
        }
    }

    private void countingHouse(MoveContext context, Player currentPlayer) {
        if (!currentPlayer.discard.isEmpty()) {
            int coppers = 0;
            for (Iterator<Card> it = currentPlayer.discard.iterator(); it.hasNext();) {
                Card card = it.next();

                if (Cards.copper.equals(card)) {
                    coppers++;
                }
            }
            int coppersTotal = currentPlayer.controlPlayer.countingHouse_coppersIntoHand(context, coppers);
            if (coppersTotal < 0 || coppersTotal > coppers) {
                Util.playerError(currentPlayer, "CountingHouse error, invalid number of coppers. Taking all coppers into hand.");
                coppersTotal = coppers;
            }
            coppers = 0;
            for (Iterator<Card> it = currentPlayer.discard.iterator(); it.hasNext();) {
                Card card = it.next();

                if (coppers >= coppersTotal) {
                    break;
                }
                if (Cards.copper.equals(card)) {
                    coppers++;
                    currentPlayer.reveal(card, this, context);
                    it.remove();
                    currentPlayer.hand.add(card);                    
                }
            }
        }
    }

    private void expand(MoveContext context, Player currentPlayer) {
        if (currentPlayer.getHand().size() == 0) {
            return;
        }

        Card card = currentPlayer.controlPlayer.expand_cardToTrash(context);
        if (card == null || !currentPlayer.hand.contains(card)) {
            Util.playerError(currentPlayer, "Expand trash error, expanding a random card.");
            card = Util.randomCard(currentPlayer.hand);
        }

        int maxCost = card.getCost(context) + 3;
        boolean potion = card.costPotion();
        int maxDebtCost = card.getDebtCost(context);
        currentPlayer.trashFromHand(card, this, context);

        card = currentPlayer.controlPlayer.expand_cardToObtain(context, maxCost, maxDebtCost, potion);
        if (card != null) {
            if (card.getCost(context) > maxCost) {
                Util.playerError(currentPlayer, "Expand error, new card costs too much.");
            } else if (card.getDebtCost(context) > maxDebtCost) {
                Util.playerError(currentPlayer, "Expand error, new card costs too much debt.");
            } else if(card.costPotion() && !potion) {
                Util.playerError(currentPlayer, "Expand error, new card costs potion and trashed card does not.");
            } else {
                if(currentPlayer.gainNewCard(card, this, context) == null) {
                    Util.playerError(currentPlayer, "Expand error, pile is empty or card is not in the game.");
                }
            }
        }
    }

    private void forge(MoveContext context, Player currentPlayer) {
        int totalCost = 0;
        Card[] cards = currentPlayer.controlPlayer.forge_cardsToTrash(context);

        if (cards != null) {
            for (Card card : cards) {
                if (card != null && currentPlayer.hand.contains(card)) {
                    totalCost += card.getCost(context);
                    currentPlayer.trashFromHand(card, this, context);
                }
            }
        }

        Card card = currentPlayer.controlPlayer.forge_cardToObtain(context, totalCost);
        if (card != null) {
            if (card.getCost(context) != totalCost || card.getDebtCost(context) > 0 || card.costPotion() || !Cards.isSupplyCard(card)) {
                Util.playerError(currentPlayer, "Forge returned invalid card, ignoring.");
            } else {
                if(currentPlayer.gainNewCard(card, this, context) == null) {
                    Util.playerError(currentPlayer, "Forge error, pile is empty or card is not in the game.");
                }
            }
        }
    }
    
    private void goons(Game game, MoveContext context, Player currentPlayer) {
        for (Player player : game.getPlayersInTurnOrder()) {
            if (player != currentPlayer && !Util.isDefendedFromAttack(game, player, this)) {
                player.attacked(this, context);
                MoveContext playerContext = new MoveContext(game, player);
                playerContext.attackedPlayer = player;

                int keepCardCount = 3;
                if (player.hand.size() > keepCardCount) {
                    Card[] cardsToKeep = player.controlPlayer.goons_attack_cardsToKeep(playerContext);
                    player.discardRemainingCardsFromHand(playerContext, cardsToKeep, this, keepCardCount);
                }

            }
        }
    }
    
    private void loanVenture(MoveContext context, Player player, Game game) {
        ArrayList<Card> toDiscard = new ArrayList<Card>();
        Card treasureCardFound = null;

        while (treasureCardFound == null) {
            Card draw = game.draw(context, this, -1);
            if (draw == null) {
                break;
            }
            
            player.reveal(draw, this, context);

            if (draw.is(Type.Treasure, player, context)) {
                treasureCardFound = draw;
            } else {
                toDiscard.add(draw);
            }
        }

        if (treasureCardFound != null) {
            if (equals(Cards.loan)) {
                if (player.controlPlayer.loan_shouldTrashTreasure(context, treasureCardFound)) {
                    player.trash(treasureCardFound, this, context);
                } else {
                    player.discard(treasureCardFound, this, context);
                }
            } else if (equals(Cards.venture)) {
                treasureCardFound.play(game, context, false);
            }
        }

        while (!toDiscard.isEmpty()) {
            player.discard(toDiscard.remove(0), this, null);
        }
    }
    
    private void mint(MoveContext context, Player currentPlayer) {
        Card cardToMint = currentPlayer.controlPlayer.mint_treasureToMint(context);

        if (cardToMint != null && (!currentPlayer.hand.contains(cardToMint) || 
        		!Cards.isSupplyCard(cardToMint) || !cardToMint.is(Type.Treasure, currentPlayer, context)) ) {
            Util.playerError(currentPlayer, "Mint treasure selection error, not minting anything.");
        }
        else if (cardToMint != null) {
            currentPlayer.reveal(cardToMint, this, context);
            currentPlayer.gainNewCard(cardToMint, this, context);
        }
    }
    
    private void mountebank(Game game, MoveContext context, Player currentPlayer) {
        for (Player player : game.getPlayersInTurnOrder()) {
            if (player != currentPlayer && !Util.isDefendedFromAttack(game, player, this)) {
                player.attacked(this, context);
                MoveContext playerContext = new MoveContext(game, player);
                playerContext.attackedPlayer = player;

                Card curseCard = null;
                for (Card card : player.hand) {
                    if (Cards.curse.equals(card)) {
                        curseCard = card;
                        break;
                    }
                }

                if (curseCard != null && (player).controlPlayer.mountebank_attack_shouldDiscardCurse(playerContext)) {
                    player.hand.remove(curseCard);
                    player.discard(curseCard, this, playerContext);
                } else {
                    player.gainNewCard(Cards.curse, this, playerContext);
                    player.gainNewCard(Cards.copper, this, playerContext);
                }
            }
        }
    }

    private void rabble(Game game, MoveContext context, Player currentPlayer) {
        for (Player player : game.getPlayersInTurnOrder()) {
            if (player != currentPlayer && !Util.isDefendedFromAttack(game, player, this)) {
                player.attacked(this, context);
                MoveContext playerContext = new MoveContext(game, player);
                playerContext.attackedPlayer = player;
                ArrayList<Card> topOfTheDeck = new ArrayList<Card>();
                List<Card> cardToDiscard = new ArrayList<Card>();

                for (int i = 0; i < 3; i++) {
                    Card card = game.draw(playerContext, Cards.rabble, 3 - i);
                    if (card != null) {
                        player.reveal(card, this, playerContext);

                        if (card.is(Type.Treasure, player, context) || card.is(Type.Action, player)) {
                            cardToDiscard.add(card);
                        } else {
                            topOfTheDeck.add(card);
                        }
                    }
                }
                for (Card c: cardToDiscard) {
                    player.discard(c, this, playerContext);
                }


                if (!topOfTheDeck.isEmpty()) {
                    Card[] order = (player).controlPlayer.rabble_attack_cardOrder(playerContext, topOfTheDeck.toArray(new Card[topOfTheDeck.size()]));

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
                        Util.playerError(currentPlayer, "Rabble order cards error, ignoring.");
                        order = topOfTheDeck.toArray(new Card[topOfTheDeck.size()]);
                    }

                    // Put the cards back on the deck
                    for (int i = order.length - 1; i >= 0; i--) {
                        player.putOnTopOfDeck(order[i]);
                    }
                }
            }
        }
    }

    private void tradeRoute(Game game, MoveContext context, Player currentPlayer, PlayContext playContext) {
        if (!currentPlayer.hand.isEmpty()) {
            Card card = currentPlayer.controlPlayer.tradeRoute_cardToTrash(context);

            if (card == null || !currentPlayer.hand.contains(card)) {
                Util.playerError(currentPlayer, "Trade Route card selection error, no card selected or card not in hand, choosing random card to trash");
                card = Util.randomCard(currentPlayer.hand);
            }

            currentPlayer.trashFromHand(card, this, context);
        }

        context.addCoins(game.tradeRouteValue, this, playContext);
    }

    private void vault(Game game, MoveContext context, Player currentPlayer, PlayContext playContext) {
        Card[] cards = currentPlayer.controlPlayer.vault_cardsToDiscardForGold(context);
        if (cards != null) {
            int numberOfCardsDiscarded = 0;
            for (Card card : cards) {
                if (currentPlayer.hand.remove(card)) {
                    currentPlayer.discard(card, this, context);
                    numberOfCardsDiscarded++;
                }
            }

            if (numberOfCardsDiscarded != cards.length) {
                Util.playerError(currentPlayer, "Vault discard error, trying to discard cards not in hand, ignoring extra.");
            }

            context.addCoins(numberOfCardsDiscarded, this, playContext);
        }

        for (Player player : game.getPlayersInTurnOrder()) {
            if (player != currentPlayer) {
                MoveContext playerContext = new MoveContext(game, player);
                cards = (player).controlPlayer.vault_cardsToDiscardForCard(playerContext);

                if (cards != null) {
                    int numberOfCardsDiscarded = 0;

                    if (cards.length > 1 || player.hand.size() == 1) {
                        for (Card card : cards) {
                            if (numberOfCardsDiscarded < 2 && player.hand.remove(card)) {
                                player.discard(card, this, playerContext);
                                numberOfCardsDiscarded++;
                            }
                        }
                    }

                    if (numberOfCardsDiscarded != cards.length) {
                        if (cards.length > 2) {
                            Util.playerError(player, "Vault discard error, trying to discard more than 2 cards, discarding first 2");
                        } else if (cards.length < 2) {
                            Util.playerError(player, "Vault discard error, trying to discard only 1 card, discarding none");
                        } else {
                            Util.playerError(player, "Vault discard error, trying to discard cards not in hand, ignoring extra.");
                        }
                    }

                    if (numberOfCardsDiscarded == 2) {
                        // Uses "draw" instead of "+Card"
                        game.drawToHand(playerContext, this, 1, new PlayContext());
                    }
                }
            }
        }
    }

    private void watchTower(Game game, MoveContext context, Player currentPlayer) {
    	int cardsToDraw = 6 - currentPlayer.hand.size();
    	PlayContext drawContext = new PlayContext();
    	if (cardsToDraw > 0 && currentPlayer.getMinusOneCardToken()) {
        	game.drawToHand(context, this, -1, drawContext);
        }
    	for (int i = 0; i < cardsToDraw; ++i) {
    		if(!game.drawToHand(context, this, cardsToDraw - i, drawContext))
                break;
    	}
    }

}

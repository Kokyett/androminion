package com.vdom.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vdom.api.Card;
import com.vdom.api.GameEvent;
import com.vdom.api.GameEventListener;
import com.vdom.core.Player.CourtierOption;

public class CardImplIntrigue extends CardImpl {
	private static final long serialVersionUID = 1L;

	public CardImplIntrigue(CardImpl.Builder builder) {
		super(builder);
	}

	protected CardImplIntrigue() { }

	@Override
    public void followInstructions(Game game, MoveContext context, Card responsible, Player currentPlayer, boolean isThronedEffect, PlayContext playContext) {
        super.followInstructions(game, context, responsible, currentPlayer, isThronedEffect, playContext);
		switch(getKind()) {
		case Baron:
            baron(context, currentPlayer, playContext);
            break;
		case Bridge:
	        context.cardCostModifier -= 1;
	        break;
        case Conspirator:
            conspirator(game, context, currentPlayer, playContext);
            break;
        case Coppersmith:
            copperSmith(context);
            break;
        case Courtier:
            courtier(game, context, currentPlayer, playContext);
            break;
        case Courtyard:
            courtyard(context, currentPlayer);
            break;
        case Diplomat:
            diplomat(game, context, currentPlayer);
            break;
        case Ironworks:
            ironworks(game, context, currentPlayer, playContext);
            break;
        case Lurker:
            lurker(game, context, currentPlayer);
            break;
        case Masquerade:
            masquerade(game, context, currentPlayer);
            break;
        case Mill:
            mill(game, context, currentPlayer, playContext);
            break;
        case MiningVillage:
            miningVillage(context, currentPlayer, playContext);
            break;
        case Minion:
            minion(game, context, currentPlayer, playContext);
            break;
        case Nobles:
            nobles(game, context, currentPlayer, playContext);
            break;
        case Patrol:
            scoutPatrol(game, context, currentPlayer, true);
            break;
        case Pawn:
            pawn(game, context, currentPlayer, playContext);
            break;
        case Replace:
            replace(game, context, currentPlayer);
            break;
        case Saboteur:
            saboteur(game, context, currentPlayer);
            break;
        case Scout:
            scoutPatrol(game, context, currentPlayer, false);
            break;
        case SecretChamber:
            secretChamber(context, currentPlayer, playContext);
            break;
        case SecretPassage:
            secretPassage(game, context, currentPlayer);
            break;
        case ShantyTown:
            shantyTown(game, context, currentPlayer, playContext);
            break;
        case Steward:
            steward(game, context, currentPlayer, playContext);
            break;
        case Swindler:
            swindler(game, context, currentPlayer);
            break;
        case Torturer:
            torturer(game, context, currentPlayer);
            break;
        case TradingPost:
            tradingPost(context, currentPlayer);
            break;
        case Tribute:
            tribute(game, context, currentPlayer, playContext);
            break;
        case Upgrade:
            upgrade(context, currentPlayer);
            break;
        case WishingWell:
            wishingWell(game, context, currentPlayer);
            break;
		default:
			break;
		}
	}
	
	private void baron(MoveContext context, Player currentPlayer, PlayContext playContext) {
        boolean discard = false;
        for (Card cardToCheck : currentPlayer.hand) {
            if (cardToCheck.equals(Cards.estate)) {
                discard = currentPlayer.controlPlayer.baron_shouldDiscardEstate(context);
                break;
            }
        }

        if (discard) {
            Card card = currentPlayer.hand.get(Cards.estate);
            currentPlayer.hand.remove(Cards.estate);
            currentPlayer.discard(card, this, context);
            context.addCoins(4, this, playContext);
        } else {
            currentPlayer.gainNewCard(Cards.estate, this, context);
        }
    }
	
    private void conspirator(Game game, MoveContext context, Player currentPlayer, PlayContext playContext) {
        if (context.actionsPlayedSoFar >= 3) {
            context.addActions(1, this);
            game.drawToHand(context, this, 1, playContext);
        }
    }
    
    private void copperSmith(MoveContext context) {
        context.coppersmithsPlayed++;
    }
    
    private void courtier(Game game, MoveContext context, Player currentPlayer, PlayContext playContext) {
        if (currentPlayer.getHand().size() == 0) return;

        Card card = currentPlayer.controlPlayer.courtier_cardToReveal(context);
        if (card == null) {
            Util.playerError(currentPlayer, "Courtier errror, reveal random card.");
            card = Util.randomCard(currentPlayer.hand);
        }

        currentPlayer.reveal(card, this, context);

        int numTypes = card.getNumberOfTypes(currentPlayer);
        if (numTypes > 0) {
            CourtierOption[] options = null;

            if (numTypes >= 4) {
                options = CourtierOption.values();
            } else {
                options = currentPlayer.controlPlayer.courtier_chooseOptions(context, CourtierOption.values(), numTypes);
            }

            if (options == null || options.length != numTypes /*TODO CHECK THAT THERE ARE NO DUPLICATES */) {
                Util.playerError(currentPlayer, "Courtier Erro, Ignoring");

            } else {
                for (CourtierOption option : options) {
                    switch (option) {
                        case AddAction:
                            context.addActions(1, this);
                            break;
                        case AddBuy:
                            context.buys++;
                            break;
                        case AddCoins:
                            context.addCoins(3, this, playContext);
                            break;
                        case GainGold:
                            currentPlayer.gainNewCard(Cards.gold, this, context);
                            break;
                    }
                }
            }
        }
    }

    private void courtyard(MoveContext context, Player currentPlayer) {
        if (currentPlayer.getHand().size() > 0) {
            Card card = currentPlayer.controlPlayer.courtyard_cardToPutBackOnDeck(context);

            if (card == null || !currentPlayer.hand.contains(card)) {
                Util.playerError(currentPlayer, "Courtyard error, just putting back a random card.");
                card = Util.randomCard(currentPlayer.hand);
            }

            currentPlayer.putOnTopOfDeck(currentPlayer.hand.remove(currentPlayer.hand.indexOf(card)));
        }
    }

    private void diplomat(Game game, MoveContext context, Player currentPlayer) {
        if (currentPlayer.getHand().size() <= 5) {
            context.addActions(2, this);
        }
    }
   
    private void ironworks(Game game, MoveContext context, Player currentPlayer, PlayContext playContext) {
        Card card = currentPlayer.controlPlayer.ironworks_cardToObtain(context);
        if (card != null && card.getCost(context) <= 4 && card.getDebtCost(context) == 0 && !card.costPotion()) {
            if (currentPlayer.gainNewCard(card, this, context).equals(card)) {
                //note these could be wrong if Watchtower is used to trash a gained inherited Estate
                if (card.is(Type.Action, currentPlayer)) {
                    context.addActions(1, this);
                }
                if (card.is(Type.Treasure, currentPlayer, context)) {
                    context.addCoins(1, this, playContext);
                }
                if (card.is(Type.Victory, currentPlayer)) {
                    game.drawToHand(context, this, 1, playContext);
                }
            }
        }
    }

    private void lurker(Game game, MoveContext context, Player currentPlayer) {
        Player.LurkerOption option = currentPlayer.controlPlayer.lurker_selectChoice(context, Player.LurkerOption.values());
        if (option == null) {
            Util.playerError(currentPlayer, "Lurker option error, choosing automatically");
            option = Player.LurkerOption.GainFromTrash;
        }

        switch (option) {
            case TrashActionFromSupply:
                Card cardToTrash = currentPlayer.controlPlayer.lurker_cardToTrash(context);
                CardPile pile = null;
                if (cardToTrash != null) {
                    pile = game.getPile(cardToTrash);
                    if (pile == null || !cardToTrash.equals(pile.topCard()) || !pile.isSupply() || !cardToTrash.is(Type.Action)) {
                        Util.playerError(currentPlayer, "Lurker trash error, trashing nothing.");
                        return;
                    }
                    currentPlayer.trashFromSupply(cardToTrash, this, context);
                }
                break;
            case GainFromTrash:
                Card cardToGain = currentPlayer.controlPlayer.lurker_cardToGainFromTrash(context);
                if (cardToGain == null || !game.trashPile.contains(cardToGain) || !cardToGain.is(Type.Action)) {
                    Util.playerError(currentPlayer, "Lurker gain card choice error, gaining nothing");
                    return;
                }

                cardToGain = game.trashPile.remove(game.trashPile.indexOf(cardToGain));
                currentPlayer.gainCardAlreadyInPlay(cardToGain, this, context);

                break;
        }
    }

    private void masquerade(Game game, MoveContext context, Player currentPlayer) {
    	List<Player> passingPlayers = new ArrayList<Player>(Game.players.length);
    	for (int i = 0; i < Game.players.length; ++i) {
    		if (Game.errataMasqueradeAlwaysAffects || !Game.players[i].getHand().isEmpty()) {
    			passingPlayers.add(Game.players[i]);
    		}
    	}
    	if (passingPlayers.size() > 1) {
	        Card[] passedCards = new Card[passingPlayers.size()];
	
	        for (int i = 0; i < passingPlayers.size(); i++) {
	            Player player = passingPlayers.get(i);
	            if (player.getHand().size() == 0) {
	                continue;
	            }
	            Card card = player.controlPlayer.masquerade_cardToPass(new MoveContext(context, game, player));
	            if (card == null || !(player).hand.contains(card)) {
	                Util.playerError(player, "Masquerade pass card error, picking random card to pass.");
	                card = Util.randomCard(player.getHand());
	            }
                player.hand.remove(card);
                passedCards[i] = card;
	        }
	
	        for (int i = 0; i < passingPlayers.size(); i++) {
	            int next = i + 1;
	            if (next >= passingPlayers.size()) {
	                next = 0;
	            }
	
	            Player nextPlayer = passingPlayers.get(next);
	
	            Card card = passedCards[i];
	            if (card != null) {
	                nextPlayer.hand.add(card);
	                if (nextPlayer instanceof GameEventListener) {
	                    GameEvent event = new GameEvent(GameEvent.EventType.CardObtained, new MoveContext(context, game, nextPlayer));
	                    event.card = card;
	                    event.responsible = this;
	                    event.newCard = false;
	                    ((GameEventListener) nextPlayer).gameEvent(event);
	                }
	            }
	        }
    	}

    	if (!currentPlayer.getHand().isEmpty()) {
	        Card toTrash = currentPlayer.controlPlayer.masquerade_cardToTrash(context);
	        if (toTrash != null) {
	            if (currentPlayer.hand.contains(toTrash)) {
	                currentPlayer.trashFromHand(toTrash, this, context);
	            } else {
	                Util.playerError(currentPlayer, "Masquerade trash error, card not in hand, ignoring.");
	            }
	        }
    	}
    }

    private void mill(Game game, MoveContext context, Player currentPlayer, PlayContext playContext) {
        if (currentPlayer.getHand().size() == 0) {
            return;
        }
        ArrayList<Card> handCopy = Util.copy(currentPlayer.getHand());
        Card[] cardsToDiscard = currentPlayer.controlPlayer.mill_cardsToDiscard(context);
        if (cardsToDiscard == null || !(cardsToDiscard.length == 2 || cardsToDiscard.length == 1 && currentPlayer.getHand().size() == 1)) {
            return;
        }

        ArrayList<Card> copy = Util.copy(currentPlayer.hand);
        for (Card cardToKeep : cardsToDiscard) {
            if (!copy.remove(cardToKeep)) {
                return;
            }
        }

        for (Card card : cardsToDiscard) {

            currentPlayer.discard(card, this, context);
            currentPlayer.hand.remove(card);
        }
        if (cardsToDiscard.length == 2) {
            context.addCoins(2, this, playContext);
        }
    }

    private void miningVillage(MoveContext context, Player currentPlayer, PlayContext playContext) {
        if (currentPlayer.isInPlay(this) && currentPlayer.controlPlayer.miningVillage_shouldTrashMiningVillage(context, this)) {
        	if (currentPlayer.trashSelfFromPlay(this, context)) {
        		context.addCoins(2, this, playContext);
        	}
        }
    }

    private void minion(Game game, MoveContext context, Player currentPlayer, PlayContext playContext) {
        ArrayList<Player> playersToAttack = new ArrayList<Player>();
        for (Player player : game.getPlayersInTurnOrder()) {
            if (player == currentPlayer || !Util.isDefendedFromAttack(game, player, this)) {
                playersToAttack.add(player);
                if (player != currentPlayer) {
                    player.attacked(this, context);
                }
            }
        }

        Player.MinionOption option = currentPlayer.controlPlayer.minion_chooseOption(context);

        if (option == null) {
            Util.playerError(currentPlayer, "Minion option error, choosing to add gold.");
            option = Player.MinionOption.AddGold;
        }

        if (option == Player.MinionOption.AddGold) {
            context.addCoins(2, this, playContext);
        } else if (option == Player.MinionOption.RolloverCards) {
            for (Player player : playersToAttack) {
                if (player == currentPlayer || player.hand.size() >= 5) {
                    MoveContext targetContext = new MoveContext(game, player);
                    targetContext.attackedPlayer = player;
                    while (!player.hand.isEmpty()) {
                        player.discard(player.hand.remove(0), this, targetContext);
                    }
                    // Uses "draw" instead of "+Card" for other players
                    PlayContext drawContext = player == currentPlayer ? playContext : new PlayContext();
                    game.drawToHand(targetContext, this, 4, drawContext);
                    game.drawToHand(targetContext, this, 3, drawContext);
                    game.drawToHand(targetContext, this, 2, drawContext);
                    game.drawToHand(targetContext, this, 1, drawContext);
                }
            }
        }
    }

    private void nobles(Game game, MoveContext context, Player currentPlayer, PlayContext playContext) {
        Player.NoblesOption option = currentPlayer.controlPlayer.nobles_chooseOptions(context);
        if (option == null) {
            Util.playerError(currentPlayer, "Nobles option error, ignoring.");
        } else {
            if (option == Player.NoblesOption.AddActions) {
                context.addActions(2, this);
            } else if (option == Player.NoblesOption.AddCards) {
                game.drawToHand(context, this, 3, playContext);
                game.drawToHand(context, this, 2, playContext);
                game.drawToHand(context, this, 1, playContext);
            }
        }
    }

    private void pawn(Game game, MoveContext context, Player currentPlayer, PlayContext playContext) {
        Player.PawnOption[] options = currentPlayer.controlPlayer.pawn_chooseOptions(context);
        if (options == null || options.length != 2 || options[0] == options[1]) {
            Util.playerError(currentPlayer, "Pawn options error, ignoring.");
        } else {
            for (Player.PawnOption option : options) {
                if (option == Player.PawnOption.AddAction) {
                    context.addActions(1, this);
                } else if (option == Player.PawnOption.AddBuy) {
                    context.buys++;
                } else if (option == Player.PawnOption.AddCard) {
                    game.drawToHand(context, this, 1, playContext);
                } else if (option == Player.PawnOption.AddGold) {
                    context.addCoins(1, this, playContext);
                }
            }
        }
    }

    private void replace(Game game, MoveContext context, Player currentPlayer) {
        ArrayList<Player> playersToAttack = new ArrayList<Player>();
        for (Player player : game.getPlayersInTurnOrder()) {
            if (player != currentPlayer && !Util.isDefendedFromAttack(game, player, this)) {
                playersToAttack.add(player);
                player.attacked(this, context);
            }
        }

        if(currentPlayer.getHand().size() <= 0) {
            return;
        }

        Card cardToTrash = currentPlayer.controlPlayer.replace_cardToTrash(context);

        if (cardToTrash == null || !currentPlayer.inHand(cardToTrash)) {
            Util.playerError(currentPlayer, "Replace did not return a card to trash, trashing random card.");
            cardToTrash = Util.randomCard(currentPlayer.getHand());
        }

        int cost = -1;
        int debt = -1;
        boolean potion = false;

        for (int i = 0; i < currentPlayer.hand.size(); i++) {
            Card playersCard = currentPlayer.hand.get(i);
            if (playersCard.equals(cardToTrash)) {
                cost = playersCard.getCost(context);
                debt = playersCard.getDebtCost(context);
                potion = playersCard.costPotion();
                playersCard = currentPlayer.hand.get(i);

                currentPlayer.trashFromHand(playersCard, this, context);
                break;
            }
        }

        cost += 2;

        Card card = currentPlayer.controlPlayer.replace_cardToObtain(context, cost, debt, potion);
        boolean cardOk = true;
        if (card != null) {
            // check cost
            if (card.getCost(context) > cost) {
                Util.playerError(currentPlayer, "Replace new card costs too much, ignoring.");
                cardOk = false;
            }
            else if (card.getDebtCost(context) > debt) {
                Util.playerError(currentPlayer, "Replace new card costs too much debt, ignoring.");
                cardOk = false;
            } else if (card.costPotion() && !potion) {
                Util.playerError(currentPlayer, "Replace new card costs potion, ignoring.");
                cardOk = false;
            }
        } else {
            cardOk = false;
        }

        if (cardOk) {
            Card gained = currentPlayer.gainNewCard(card, this, context);
            if (gained != null && gained.equals(card)) {
                if (gained.is(Type.Victory, currentPlayer)) { //Topdecking if it's an Action or Treasure is already handled in the gameEvent handler
                    for (Player player : playersToAttack) {
                        MoveContext playerContext = new MoveContext(game, player);
                        playerContext.attackedPlayer = player;
                        player.gainNewCard(Cards.curse, this, playerContext);
                    }
                }
            } else {
                Util.playerError(currentPlayer, "Replace new card is invalid, ignoring.");
            }
        }
    }

    private void saboteur(Game game, MoveContext context, Player currentPlayer) {
        for (Player player : game.getPlayersInTurnOrder()) {
            if (player != currentPlayer && !Util.isDefendedFromAttack(game, player, this)) {
                player.attacked(this, context);
                MoveContext playerContext = new MoveContext(game, player);
                playerContext.attackedPlayer = player;
                playerContext.cardCostModifier = context.cardCostModifier;

                ArrayList<Card> toDiscard = new ArrayList<Card>();
                Card draw;

                while ((draw = game.draw(playerContext, Cards.saboteur, -1)) != null) {
                    if (draw.getCost(context) >= 3) {
                        int value = draw.getCost(context);
                        value -= 2;
                        if (value < 0) {
                            value = 0;
                        }

                        boolean potion = draw.costPotion();
                        int debt = draw.getDebtCost(context);
                        
                        player.trash(draw, this, playerContext);

                        Card card = (player).controlPlayer.saboteur_cardToObtain(playerContext, value, debt, potion);
                        if (card != null) {
                            if (card.getCost(context) > value || card.getDebtCost(context) > debt || (card.costPotion() && !potion) || !Cards.isSupplyCard(card)) {
                                Util.playerError(currentPlayer, "Saboteur obtain error, ignoring.");
                            }
                            else {
                                if(player.gainNewCard(card, this, playerContext) == null) {
                                    Util.playerError(currentPlayer, "Saboteur obtain error, ignoring.");
                                }
                            }
                        }

                        break;
                    } else {
                        player.reveal(draw, this, playerContext);
                        toDiscard.add(draw);
                    }
                }

                while (!toDiscard.isEmpty()) {
                    player.discard(toDiscard.remove(0), this, playerContext);
                }
            }
        }
    }

    private void scoutPatrol(Game game, MoveContext context, Player currentPlayer, boolean cursesToHand) {
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 4; i++) {
            Card card = game.draw(context, this, 4 - i);
            if (card == null) {
                break;
            }
            if (card.is(Type.Victory, currentPlayer) || cursesToHand && card.is(Type.Curse, currentPlayer)) {
                currentPlayer.hand.add(card);
            } else {
                cards.add(card);
            }
        }

        if (cards.size() == 0) {
            return;
        }

        for (Card card : cards) {
            currentPlayer.reveal(card, this, context);
        }

        Card[] order = currentPlayer.controlPlayer.scoutPatrol_orderCards(context, cards.toArray(new Card[cards.size()]));
        boolean bad = false;
        if (order == null || order.length != cards.size()) {
            bad = true;
        } else {
            ArrayList<Card> orderArray = new ArrayList<Card>();
            for (Card card : order) {
                orderArray.add(card);
                if (!cards.contains(card)) {
                    bad = true;
                }
            }

            for (Card card : cards) {
                if (!orderArray.contains(card)) {
                    bad = true;
                }
            }
        }

        if (bad) {
            Util.playerError(currentPlayer, this.getName() + " order cards error, ignoring.");
            order = cards.toArray(new Card[cards.size()]);
        }

        for (int i = order.length - 1; i >= 0; i--) {
            currentPlayer.putOnTopOfDeck(order[i]);
        }
    }
    
    private void secretChamber(MoveContext context, Player currentPlayer, PlayContext playContext) {
        Card[] cards = currentPlayer.controlPlayer.secretChamber_cardsToDiscard(context);
        if (cards != null) {
            int numberOfCardsDiscarded = 0;
            for (Card card : cards) {
                if (currentPlayer.hand.remove(card)) {
                    currentPlayer.discard(card, this, context);
                    numberOfCardsDiscarded++;
                }
            }

            if (numberOfCardsDiscarded != cards.length) {
                Util.playerError(currentPlayer, "Secret chamber discard error, trying to discard cards not in hand, ignoring extra.");
            }

            context.addCoins(numberOfCardsDiscarded, this, playContext);
        }
    }

    private void secretPassage(Game game, MoveContext context, Player currentPlayer) {
        if (currentPlayer.getHand().size() == 0) return;

        Card card = currentPlayer.controlPlayer.secretPassage_cardToPutInDeck(context);
        ArrayList<Card> copy = Util.copy(currentPlayer.hand);
        if (card == null || !copy.remove(card)) {
            Util.playerError(currentPlayer, "Secret passage error, choosing a random card to put in deck.");
            card = currentPlayer.getHand().get(Game.rand.nextInt(currentPlayer.getHand().size()));
        }

        int position = currentPlayer.controlPlayer.secretPassage_positionToPutCard(context, card);
        if (position < 0 || position > currentPlayer.deck.size()) {
            Util.playerError(currentPlayer, "Secret passage error, place card on top of deck.");
            position = 0;
        }

        currentPlayer.getHand().remove(card);
        currentPlayer.deck.add(position, card);

    }
    
    private void shantyTown(Game game, MoveContext context, Player currentPlayer, PlayContext playContext) {
        boolean actions = false;
        for (Card card : currentPlayer.hand) {
            currentPlayer.reveal(card, this, context);

            if (card.is(Type.Action, currentPlayer)) {
                actions = true;
            }
        }

        if (!actions) {
            game.drawToHand(context, this, 2, playContext);
            game.drawToHand(context, this, 1, playContext);
        }
    }
    
    private void steward(Game game, MoveContext context, Player currentPlayer, PlayContext playContext) {
        Player.StewardOption option = currentPlayer.controlPlayer.steward_chooseOption(context);

        if (option == null) {
            Util.playerError(currentPlayer, "Steward option error, ignoring.");
        } else {
            if (option == Player.StewardOption.AddGold) {
                context.addCoins(2, this, playContext);
            } else if (option == Player.StewardOption.AddCards) {
                game.drawToHand(context, this, 2, playContext);
                game.drawToHand(context, this, 1, playContext);
            } else if (option == Player.StewardOption.TrashCards) {
                CardList hand = currentPlayer.getHand();
                if (hand.size() == 0) {
                    return;
                }

                Card[] cards = currentPlayer.controlPlayer.steward_cardsToTrash(context);
                boolean bad = false;
                if (cards == null) {
                    bad = true;
                } else if (cards.length != 2) {
                    if (hand.size() >= 2 || cards.length != hand.size()) {
                        bad = true;
                    }
                } else {
                    ArrayList<Card> copy = Util.copy(currentPlayer.hand);
                    for (Card card : cards) {
                        if (!copy.remove(card)) {
                            bad = true;
                            break;
                        }
                    }
                }

                if (bad) {
                    Util.playerError(currentPlayer, "Steward trash error, picking first two cards.");

                    if (hand.size() >= 2) {
                        cards = new Card[2];
                    } else {
                        cards = new Card[hand.size()];
                    }
                    for (int i = 0; i < cards.length; i++) {
                        cards[i] = hand.get(i);
                    }
                }

                for (Card card : cards) {
                    currentPlayer.trashFromHand(card, this, context);
                }
            }
        }
    }
    
    private void swindler(Game game, MoveContext context, Player currentPlayer) {
    	//TODO: when attack played logic first
        for (Player player : game.getPlayersInTurnOrder()) {
            if (player != currentPlayer && !Util.isDefendedFromAttack(game, player, this)) {
                player.attacked(this, context);
                MoveContext playerContext = new MoveContext(game, player);
                playerContext.attackedPlayer = player;

                Card draw = game.draw(playerContext, Cards.swindler, 1);
                if (draw != null) {
                    player.trash(draw, this, playerContext);
                    
                    //TODO: don't ask if there isn't a valid card to select or there is only 1 card to select

                    Card card = currentPlayer.controlPlayer.swindler_cardToSwitch(context, draw.getCost(context), draw.getDebtCost(context), draw.costPotion());

                    boolean bad = false;
                    if (card == null) {
                        // Check that there are no cards that are possible to trade for...
                        for (Card thisCard : context.getCardsInGame(GetCardsInGameOptions.TopOfPiles, true)) {
                            if (!game.isPileEmpty(thisCard) && thisCard.getCost(context) == draw.getCost(context) && thisCard.getDebtCost(context) == draw.getDebtCost(context) && thisCard.costPotion() == draw.costPotion()) {
                                bad = true;
                                break;
                            }
                        }
                    } else if (!Cards.isSupplyCard(card) || game.isPileEmpty(card) || card.getCost(context) != draw.getCost(context) || card.getDebtCost(context) != draw.getDebtCost(context) || card.costPotion() != draw.costPotion()) {
                        bad = true;
                    }

                    if (bad) {
                        Util.playerError(currentPlayer, "Swindler swap card error, picking a random card.");

                        ArrayList<Card> possible = new ArrayList<Card>();
                        for (Card thisCard : context.getCardsInGame(GetCardsInGameOptions.TopOfPiles, true)) {
                            if (!game.isPileEmpty(thisCard) && thisCard.getCost(context) == draw.getCost(context) && thisCard.getDebtCost(context) == draw.getDebtCost(context) && thisCard.costPotion() == draw.costPotion()) {
                                possible.add(thisCard);
                            }
                        }

                        card = Util.randomCard(possible);
                    }

                    if (card != null) {
                        player.gainNewCard(card, this, playerContext);
                    }
                }
            }
        }
    }
    
    private void torturer(Game game, MoveContext context, Player currentPlayer) {
        for (Player targetPlayer : game.getPlayersInTurnOrder()) {
            if (targetPlayer != currentPlayer && !Util.isDefendedFromAttack(game, targetPlayer, this)) {
                targetPlayer.attacked(this, context);
                MoveContext playerContext = new MoveContext(game, targetPlayer);
                playerContext.attackedPlayer = targetPlayer;
                Player.TorturerOption option;
                try {
                    option = (targetPlayer).controlPlayer.torturer_attack_chooseOption(playerContext);
                } catch (NoSuchFieldError e) {
                    Util.playerError(targetPlayer, "'Take three cards' version of torturer attack no longer supported.");
                    option = null;
                }

                if (option == null) {
                    Util.playerError(targetPlayer, "Torturer option error, taking curse card.");
                    option = Player.TorturerOption.TakeCurse;
                }

                if (option == Player.TorturerOption.TakeCurse) {
                    targetPlayer.gainNewCard(Cards.curse, this, playerContext);
                } else {
                    ArrayList<Card> handCopy = Util.copy(targetPlayer.getHand());
                    Card[] cardsToDiscard = (targetPlayer).controlPlayer.torturer_attack_cardsToDiscard(playerContext);

                    boolean bad = false;
                    if (cardsToDiscard == null) {
                        bad = true;
                    } else if (handCopy.size() < 2 && cardsToDiscard.length != handCopy.size()) {
                        bad = true;
                    } else if (cardsToDiscard.length != 2) {
                        bad = true;
                    } else {
                        ArrayList<Card> copyForDiscard = Util.copy(targetPlayer.getHand());
                        for (Card cardToKeep : cardsToDiscard) {
                            if (!copyForDiscard.remove(cardToKeep)) {
                                bad = true;
                                break;
                            }
                        }
                    }

                    if (bad) {
                        if (handCopy.size() >= 2) {
                            Util.playerError(targetPlayer, "Torturer discard error, just discarding the first 2.");
                        }
                        cardsToDiscard = new Card[Math.min(2, handCopy.size())];
                        for (int i = 0; i < cardsToDiscard.length; i++) {
                            cardsToDiscard[i] = handCopy.get(i);
                        }
                    }

                    for (Card card : cardsToDiscard) {
                        targetPlayer.hand.remove(card);
                        targetPlayer.discard(card, this, playerContext);
                    }
                }
            }
        }
    }
    
    private void tradingPost(MoveContext context, Player currentPlayer) {
        if (currentPlayer.getHand().size() == 0) {
            return;
        }
        ArrayList<Card> handCopy = Util.copy(currentPlayer.getHand());
        Card[] cardsToTrash = currentPlayer.controlPlayer.tradingPost_cardsToTrash(context);
        // Trash forced, pick cards randomly if not selected
        boolean bad = false;
        if (cardsToTrash == null) {
            bad = true;
        } else if (handCopy.size() < 2 && cardsToTrash.length != handCopy.size()) {
            bad = true;
        } else if (handCopy.size() >= 2 && cardsToTrash.length != 2) {
            bad = true;
        } else {
            ArrayList<Card> copyForTrash = Util.copy(currentPlayer.getHand());
            for (Card cardToKeep : cardsToTrash) {
                if (!copyForTrash.remove(cardToKeep)) {
                    bad = true;
                    break;
                }
            }
        }

        if (bad) {
            if (handCopy.size() >= 2) {
                Util.playerError(currentPlayer, "TradingPost trash error, just trashing the first 2.");
            }
            cardsToTrash = new Card[Math.min(2, handCopy.size())];
            for (int i = 0; i < cardsToTrash.length; i++) {
                cardsToTrash[i] = handCopy.get(i);
            }
        }

        for (int i = cardsToTrash.length - 1; i >= 0 ; i--) {
            currentPlayer.trashFromHand(cardsToTrash[i], this, context);
        }
        if (cardsToTrash.length == 2) {
            currentPlayer.gainNewCard(Cards.silver, this, context);
        }
    }
    
    private void tribute(Game game, MoveContext context, Player currentPlayer, PlayContext playContext) {
        Card[] revealedCards = new Card[2];
        Player nextPlayer = game.getNextPlayer();
        MoveContext targetContext = new MoveContext(game, nextPlayer);
        revealedCards[0] = game.draw(targetContext, Cards.tribute, 2);
        revealedCards[1] = game.draw(targetContext, Cards.tribute, 1);

        if (revealedCards[0] != null) {
            nextPlayer.reveal(revealedCards[0], this, targetContext);
            (nextPlayer).discard(revealedCards[0], this, targetContext);

        }
        if (revealedCards[1] != null) {
            nextPlayer.reveal(revealedCards[1], this, targetContext);
            (nextPlayer).discard(revealedCards[1], this, targetContext);
        }

        // "For each differently named card revealed..."
        if (revealedCards[0] != null && revealedCards[0].equals(revealedCards[1])) {
            revealedCards[1] = null;
        }

        for (Card card : revealedCards) {
            if (card != null && !card.equals(Cards.curse)) {
                if (card.is(Type.Action, nextPlayer)) {
                    context.addActions(2, this);
                }
                if (card.is(Type.Treasure, nextPlayer, context)) {
                    context.addCoins(2, this, playContext);
                }
                if (card.is(Type.Victory, nextPlayer)) {
                    game.drawToHand(context, this, 2, playContext);
                    game.drawToHand(context, this, 1, playContext);
                }
            }
        }
    }
   
    private void upgrade(MoveContext context, Player currentPlayer) {
        if (currentPlayer.getHand().size() > 0) {
            Card card = currentPlayer.controlPlayer.upgrade_cardToTrash(context);
            if (card == null || !currentPlayer.hand.contains(card)) {
                Util.playerError(currentPlayer, "Upgrade trash error, upgrading a random card.");
                card = Util.randomCard(currentPlayer.hand);
            }

            int value = card.getCost(context) + 1;
            boolean potion = card.costPotion();
            int debt = card.getDebtCost(context);
            currentPlayer.trashFromHand(card, this, context);

            card = currentPlayer.controlPlayer.upgrade_cardToObtain(context, value, debt, potion);
            if (card != null) {
                if (card.getCost(context) != value || card.getDebtCost(context) != debt || card.costPotion() != potion) {
                    Util.playerError(currentPlayer, "Upgrade error, new card does not cost value of the old card +1.");
                } else {
                    if(currentPlayer.gainNewCard(card, this, context) == null) {
                        Util.playerError(currentPlayer, "Upgrade error, pile is empty or card is not in the game.");
                    }
                }
            }
        }
    }

    private void wishingWell(Game game, MoveContext context, Player currentPlayer) {

        if (currentPlayer.deck.size() > 0 || currentPlayer.discard.size() > 0) {  // Only allow a guess if there are cards in the deck or discard pile

            // Create a list of possible cards to guess, using the player's hand, discard pile, and deck 
            // (even though the player could technically name a card he doesn't have)
            ArrayList<Card> options = new ArrayList<Card>(currentPlayer.getDistinctCards());
            Collections.sort(options, new Util.CardNameComparator());

            if (!options.isEmpty()) {
                Card card = currentPlayer.controlPlayer.wishingWell_cardGuess(context, options);
                currentPlayer.controlPlayer.namedCard(card, this, context);
                Card draw = game.draw(context, Cards.wishingWell, 1);
                if (draw != null) {
                    currentPlayer.reveal(draw, this, context);

                    if (card != null && card.equals(draw)) {
                        currentPlayer.hand.add(draw, true);
                    } else {
                        currentPlayer.putOnTopOfDeck(draw, context, true);
                    }
                }
            }
        }
    }

}

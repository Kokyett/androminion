package com.vdom.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vdom.api.Card;
import com.vdom.api.GameEvent;
import com.vdom.core.MoveContext.PileSelection;

public class CardImplDarkAges extends CardImpl {
	private static final long serialVersionUID = 1L;

	public CardImplDarkAges(CardImpl.Builder builder) {
		super(builder);
	}

	protected CardImplDarkAges() { }

	@Override
    public void followInstructions(Game game, MoveContext context, Card responsible, Player currentPlayer, boolean isThronedEffect, PlayContext playContext) {
        super.followInstructions(game, context, responsible, currentPlayer, isThronedEffect, playContext);
		switch(getKind()) {
		case Altar:
            altar(currentPlayer, context);
            break;
		case Armory:
            armory(currentPlayer, context);
            break;
		case BandOfMisfits:
            bandOfMisfits(game, context, currentPlayer);
            break;
		case BanditCamp:
            banditCamp(context, currentPlayer);
            break;
        case Beggar:
            beggar(currentPlayer, context);
            break;
        case Catacombs:
            catacombs(game, currentPlayer, context, playContext);
            break;
        case Count:
            count(currentPlayer, context, playContext);
            break;
        case Counterfeit:
        	multiPlayTreasure(context, game, currentPlayer);
        	break;
        case Cultist:
            cultist(context, game, currentPlayer);
            break;
        case DameAnna:
            dameAnna(context, currentPlayer);
            break;
        case DameJosephine:
        case DameMolly:
        	knight(context, currentPlayer);
        	break;
        case DameNatalie:
            dameNatalie(context, currentPlayer);
            break;
        case DameSylvia:
        	knight(context, currentPlayer);
        	break;
        case DeathCart:
            deathCart(currentPlayer, context, playContext);
            break;
        case Forager:
            forager(game, currentPlayer, context, playContext);
            break;
        case Graverobber:
            graverobber(game, currentPlayer, context);
            break;
        case Hermit:
            hermit(context, game, currentPlayer);
            break;
        case Ironmonger:
            ironmonger(game, currentPlayer, context, playContext);
            break;
        case JunkDealer:
            junkDealer(currentPlayer, context);
            break;
        case Madman:
            madman(context, game, currentPlayer, playContext);
            break;
        case Marauder:
            marauder(context, game, currentPlayer);
            break;
        case Mercenary:
            mercenary(context, game, currentPlayer, playContext);
            break;
        case Mystic:
            mystic(game, context, currentPlayer);
            break;
        case Pillage:
            pillage(game, context, currentPlayer);
            break;
        case PoorHouse:
            poorHouse(context, currentPlayer);
            break;
        case Procession:
        	throneRoomKingsCourt(game, context, currentPlayer);
        	break;
        case Rats:
            rats(context, currentPlayer);
            break;
        case Rebuild:
            rebuild(currentPlayer, context);
            break;
        case Rogue:
            rogue(game, context, currentPlayer);
            break;
        case Sage:
            sage(game, context, currentPlayer);
            break;
        case Scavenger:
            scavenger(game, context, currentPlayer);
            break;
        case SirBailey:
        case SirDestry:
        case SirMartin:
        	knight(context, currentPlayer);
            break;
        case SirMichael:
            sirMichael(context, game, currentPlayer);
            break;
        case SirVander:
            knight(context, currentPlayer);
            break;
        case Spoils:
        	spoils(game, currentPlayer);
        	break;
        case Squire:
            squire(context, currentPlayer);
            break;            
        case Storeroom:
            storeroom(game, context, currentPlayer, playContext);
            break;
        case Survivors:
            survivors(context, game, currentPlayer);
            break;
        case Urchin:
            urchin(context, game, currentPlayer);
            break;
        case Vagrant:
            vagrant(context, game, currentPlayer);
            break;
        case WanderingMinstrel:
            wanderingMinstrel(currentPlayer, context);
            break;
		default:
			break;
        
		}
	}
	
	@Override
	public void isTrashed(MoveContext context) {
		Cards.Kind trashKind = this.getKind();
    	switch (trashKind) {
        case Rats:
            context.game.drawToHand(context, this, 1, true, new PlayContext());
            break;
        case Squire:
            boolean attackCardAvailable = false;

            for (Card c : context.game.getCardsInGame(GetCardsInGameOptions.TopOfPiles, true, Type.Attack))
            {
                if (context.game.getPile(c).getCount() > 0) {
                    attackCardAvailable = true;
                    break;
                }
            }

            if (attackCardAvailable) {
                Card s = context.player.controlPlayer.squire_cardToObtain(context);

                if (s != null) {
                    context.player.controlPlayer.gainNewCard(s, this, context);
                }
            }
            break;
        case Catacombs:
        	int cost = this.getCost(context);
        	cost--;
        	if (cost >= 0) {
                Card c = context.player.controlPlayer.catacombs_cardToObtain(context, cost);
                if (c != null && c.getCost(context) <= cost && c.getDebtCost(context) == 0 && !c.costPotion()) {
                    context.player.controlPlayer.gainNewCard(c, this, context);
                }
        	}
            break;
        case HuntingGrounds:
              // Wiki: If you trash Hunting Grounds and the Duchy pile is empty,
              // you can still choose Duchy (and gain nothing). 
            int duchyCount      = context.game.getPile(Cards.duchy).getCount();
            int estateCount     = context.game.getPile(Cards.estate).getCount();
            boolean gainDuchy   = false;
            boolean gainEstates = false;

            if (duchyCount > 0 || estateCount > 0)
            {
                Player.HuntingGroundsOption option = context.player.controlPlayer.huntingGrounds_chooseOption(context);
                if (option != null) {
                    switch (option) {
                        case GainDuchy:
                            gainDuchy = true;
                            break;
                        case GainEstates:
                            gainEstates = true;
                            break;
                        default:
                            break;
                    }
                }
            }
            
            if (gainDuchy)
            {
                context.player.controlPlayer.gainNewCard(Cards.duchy, this, context);
            }
            else if (gainEstates)
            {
                context.player.controlPlayer.gainNewCard(Cards.estate, this, context);
                context.player.controlPlayer.gainNewCard(Cards.estate, this, context);
                context.player.controlPlayer.gainNewCard(Cards.estate, this, context);
            }

            break;
        case Fortress:
        	//TODO: if Possessed, give choice of whether to put in hand or set aside card
            context.game.trashPile.remove(this);
            context.player.hand.add(this);
            break;
        case Cultist:
            PlayContext playContext = new PlayContext();
            context.game.drawToHand(context, this, 3, false, playContext);
            context.game.drawToHand(context, this, 2, false, playContext);
            context.game.drawToHand(context, this, 1, false, playContext);
            break;
        case SirVander:
            context.player.controlPlayer.gainNewCard(Cards.gold, this, context);
            break;
        case OvergrownEstate:
            context.game.drawToHand(context, this, 1, new PlayContext());
            break;
        case Feodum:
            context.player.controlPlayer.gainNewCard(Cards.silver, this, context);
            context.player.controlPlayer.gainNewCard(Cards.silver, this, context);
            context.player.controlPlayer.gainNewCard(Cards.silver, this, context);
            break;
        default:
            break;
	    }
	}
	
	private void altar(Player currentPlayer, MoveContext context) {
        if (currentPlayer.getHand().size() > 0) {
            Card card = currentPlayer.controlPlayer.altar_cardToTrash(context);

            if (card == null || !currentPlayer.hand.contains(card)) {
                Util.playerError(currentPlayer, "Altar trash error, trashing a random card.");
                card = Util.randomCard(currentPlayer.hand);
            }

            currentPlayer.trashFromHand(card, this, context);
        }

        Card card = currentPlayer.controlPlayer.altar_cardToObtain(context);
        if (card != null) {
            // check cost
            if (card.getCost(context) <= 5 && card.getDebtCost(context) == 0 && !card.costPotion()) {
                currentPlayer.gainNewCard(card, this, context);
            }
        }
    }
	
	public void armory(Player currentPlayer, MoveContext context) {
        Card card = currentPlayer.controlPlayer.armory_cardToObtain(context);
        if (card != null) {
            // check cost
            if (card.getCost(context) <= 4 && card.getDebtCost(context) == 0 && !card.costPotion()) {
                currentPlayer.gainNewCard(card, this, context);
            }
        }
    }
	
	private void bandOfMisfits(Game game, MoveContext context, Player currentPlayer) {
        Card cardToPlay = currentPlayer.controlPlayer.bandOfMisfits_actionCardToPlay(context, cost - 1);
        if (cardToPlay != null 
            && !game.isPileEmpty(cardToPlay)
            && Cards.isSupplyCard(cardToPlay)
            && cardToPlay.is(Type.Action, null)
            && !cardToPlay.is(Type.Command, null)
            && cardToPlay.getCost(context) < cost
            && cardToPlay.getDebtCost(context) == 0
        	&& !cardToPlay.costPotion()) {
            cardToPlay.play(game, context, false, true, false);
        } else {
            Card[] cards = game.getCardsInGame(GetCardsInGameOptions.TopOfPiles, true, Type.Action);
            if (cards.length != 0 && cardToPlay != null) {
                Util.playerError(currentPlayer, "Band of Misfits returned invalid card (" + cardToPlay.getName() + "), ignoring.");
            }
            return;
        }
    }

	private void banditCamp(MoveContext context, Player currentPlayer)
    {
        // Gain a Spoils from the Spoils pile
        currentPlayer.gainNewCard(Cards.spoils, this, context);
    }

    public void beggar(Player currentPlayer, MoveContext context) {
        currentPlayer.gainNewCard(Cards.copper, this, context);
        currentPlayer.gainNewCard(Cards.copper, this, context);
        currentPlayer.gainNewCard(Cards.copper, this, context);
    }

    public void catacombs(Game game, Player currentPlayer, MoveContext context, PlayContext playContext) {
        ArrayList<Card> topOfTheDeck = new ArrayList<Card>();
        for (int i = 0; i < 3; i++) {
            Card card = game.draw(context, Cards.catacombs, 3 - i);
            if (card != null) {
                topOfTheDeck.add(card);
            }
        }

        if (topOfTheDeck.size() > 0) {
            if (currentPlayer.controlPlayer.catacombs_shouldDiscardTopCards(context, topOfTheDeck.toArray(new Card[topOfTheDeck.size()]))) {
                while (!topOfTheDeck.isEmpty()) {
                    currentPlayer.discard(topOfTheDeck.remove(0), this, context);
                }
                game.drawToHand(context, this, 3, playContext);
                game.drawToHand(context, this, 2, playContext);
                game.drawToHand(context, this, 1, playContext);
            } else {
                // Put the cards in hand
                for (Card c : topOfTheDeck) {
                    currentPlayer.hand.add(c);
                }
            }
        }
    }
    
    private void count(Player currentPlayer, MoveContext context, PlayContext playContext) {
        Player.CountFirstOption option1 = currentPlayer.controlPlayer.count_chooseFirstOption(context);
        if (option1 == null) {
            Util.playerError(currentPlayer, "Count first option error, ignoring.");
        } else {
            switch (option1) {
                case Discard:
                    Card[] cards;
                    if (currentPlayer.hand.size() > 2) {
                        cards = currentPlayer.controlPlayer.count_cardsToDiscard(context);
                    } else {
                        cards = currentPlayer.getHand().toArray();
                    }
                    boolean bad = false;
                    if (cards == null) {
                        bad = true;
                    } else if (cards.length > 2) {
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
                        Util.playerError(currentPlayer, "Count discard error, discarding first 2 cards.");
                        cards = new Card[2];

                        for (int i = 0; i < cards.length; i++) {
                            cards[i] = currentPlayer.hand.get(i);
                        }
                    }

                    for (int i = 0; i < cards.length; i++) {
                        currentPlayer.hand.remove(cards[i]);
                        currentPlayer.reveal(cards[i], this, context);
                        currentPlayer.discard(cards[i], this, context);
                    }
                    break;
                case PutOnDeck:
                    if (currentPlayer.getHand().size() > 0) {
                        Card card = currentPlayer.controlPlayer.count_cardToPutBackOnDeck(context);

                        if (card == null || !currentPlayer.hand.contains(card)) {
                            Util.playerError(currentPlayer, "Count error, just putting back a random card.");
                            card = Util.randomCard(currentPlayer.hand);
                        }

                        currentPlayer.hand.remove(card);
                        currentPlayer.putOnTopOfDeck(card);
                    }
                    break;
                case GainCopper:
                    currentPlayer.gainNewCard(Cards.copper, this, context);
                    break;
            }
        }

        Player.CountSecondOption option2 = currentPlayer.controlPlayer.count_chooseSecondOption(context);
        if (option2 == null) {
            Util.playerError(currentPlayer, "Count second option error, ignoring.");
        } else {
            switch (option2) {
                case Coins:
                    context.addCoins(3, this, playContext);
                    break;
                case TrashHand:
                    if (currentPlayer.hand.size() > 0) {
                        Card[] temp = currentPlayer.hand.toArray();
                        for (Card c : temp) {
                            currentPlayer.trashFromHand(c, this, context);
                        }
                    }
                    break;
                case GainDuchy:
                    currentPlayer.gainNewCard(Cards.duchy, this, context);
                    break;
            }
        }
    }
    
    private void cultist(MoveContext context, Game game, Player currentPlayer) {
        for (Player player : game.getPlayersInTurnOrder()) {
            if (player != currentPlayer && !Util.isDefendedFromAttack(game, player, this)) {
                player.attacked(this, context);
                MoveContext playerContext = new MoveContext(game, player);
                playerContext.attackedPlayer = player;
                player.gainNewCard(Cards.virtualRuins, this, playerContext);
            }
        }

        if (currentPlayer.hand.contains(Cards.cultist) && currentPlayer.controlPlayer.cultist_shouldPlayNext(context)) {
            Card next = currentPlayer.hand.get(Cards.cultist);
            if (next != null) {
                next.play(game, context, true);
            }
        }
    }
    
    private void dameAnna(MoveContext context, Player currentPlayer) {
        Card[] cards = currentPlayer.controlPlayer.dameAnna_cardsToTrash(context);
        if (cards != null) {
            if (cards.length > 2) {
                Util.playerError(currentPlayer, "Dame Anna trash error, trying to trash too many cards, ignoring.");
            } else {
                for (Card card : cards) {
                    for (int i = 0; i < currentPlayer.hand.size(); i++) {
                        Card playersCard = currentPlayer.hand.get(i);
                        if (playersCard.equals(card)) {
                            Card thisCard = currentPlayer.hand.get(i);
                            currentPlayer.trashFromHand(thisCard, this, context);
                            break;
                        }
                    }
                }
            }
        }

        knight(context, currentPlayer);
    }
    
    private void dameNatalie(MoveContext context, Player currentPlayer) {
        Card card = currentPlayer.controlPlayer.dameNatalie_cardToObtain(context);
        if (card != null) {
            // check cost
            if (card.getCost(context) <= 3 && card.getDebtCost(context) == 0 && !card.costPotion()) {
                currentPlayer.gainNewCard(card, this, context);
            } else {
                Util.playerError(currentPlayer, "Dame Natalie error: chosen card that costs more then 3");
            }
        }

        knight(context, currentPlayer);
    }
    
    private void deathCart(Player currentPlayer, MoveContext context, PlayContext playContext)
    {
        Card actionCardToTrash = currentPlayer.controlPlayer.deathCart_actionToTrash(context);
        if (actionCardToTrash != null)
        {
            if (currentPlayer.trashFromHand(actionCardToTrash, this, context)) {
            	context.addCoins(5, this, playContext);
            }
        } else {
        	if (currentPlayer.trashSelfFromPlay(this, context)) {
        		context.addCoins(5, this, playContext);
        	}
        }
    }

    private void forager(Game game, Player currentPlayer, MoveContext context, PlayContext playContext) {
        if (currentPlayer.getHand().size() > 0) {
            Card card = currentPlayer.controlPlayer.forager_cardToTrash(context);

            if (card == null || !currentPlayer.hand.contains(card)) {
                Util.playerError(currentPlayer, "Forager trash error, trashing a random card.");
                card = Util.randomCard(currentPlayer.hand);
            }

            currentPlayer.trashFromHand(card, this, context);
        }

        HashSet<String> cardNames = new HashSet<String>();
        for (Card card : game.trashPile) {
            if (card == null) {
                break;
            }

            if (card.is(Type.Treasure, null, context)) {
                cardNames.add(card.getName());
            }
        }
        context.addCoins(cardNames.size(), this, playContext);
    }

    private void graverobber(Game game, Player currentPlayer, MoveContext context) {
        Player.GraverobberOption option = currentPlayer.controlPlayer.graverobber_chooseOption(context);
        if (option == null) {
            Util.playerError(currentPlayer, "Graverobber option error, choosing automatically");
            option = Player.GraverobberOption.GainFromTrash;
        }

        Card toGain;

        switch (option) {
            case GainFromTrash:
                toGain = currentPlayer.controlPlayer.graverobber_cardToGainFromTrash(context);

                if (toGain == null || toGain.costPotion() || toGain.getCost(context) < 3 || toGain.getCost(context) > 6) {
                    Util.playerError(currentPlayer, "Graverobber gain card choice error, gaining nothing");
                    return;
                }

                context.graverobberGainedCardOnTop = true;
                toGain = game.trashPile.remove(game.trashPile.indexOf(toGain));
                currentPlayer.gainCardAlreadyInPlay(toGain, this, context);

                break;

            case TrashActionCard:
                Card toTrash = currentPlayer.controlPlayer.graverobber_cardToTrash(context);

                if (toTrash == null || !currentPlayer.hand.contains(toTrash) || !(toTrash.is(Type.Action, currentPlayer))) {
                    Util.playerError(currentPlayer, "Graverobber trash error, trashing nothing.");
                    return;
                }

                currentPlayer.trashFromHand(toTrash, this, context);

                context.graverobberGainedCardOnTop = false;
                toGain = currentPlayer.controlPlayer.graverobber_cardToReplace(context, 3 + toTrash.getCost(context), toTrash.getDebtCost(context), toTrash.costPotion());
                if (toGain != null && toGain.getCost(context) <= toTrash.getCost(context) + 3 && 
                		toGain.getDebtCost(context) <= toTrash.getDebtCost(context) && 
                		(!toGain.costPotion() || toTrash.costPotion())) {
                    currentPlayer.gainNewCard(toGain, this, context);
                }
                break;
        }
    }
    
    private void hermit(MoveContext context, Game game, Player currentPlayer)
    {
        ArrayList<Card> options = new ArrayList<Card>();

        Set<Card> inDiscard = new HashSet<Card>();
        for (Card c : currentPlayer.discard) {
            if (!(c.is(Type.Treasure, currentPlayer, context))) {
                inDiscard.add(c);
            }
        }
        options.addAll(inDiscard);
        Collections.sort(options, new Util.CardNameComparator());

        Set<Card> inHand = new HashSet<Card>();
        for (Card c: currentPlayer.hand) {
            if (!(c.is(Type.Treasure, currentPlayer, context))) {
                inHand.add(c);
            }
        }
        List<Card> handList = new ArrayList<Card>(inHand);
        Collections.sort(handList, new Util.CardNameComparator());
        options.addAll(handList);

        if (!options.isEmpty()) {
            // Offer the option to trash a non-treasure card
            context.hermitTrashCardPile = PileSelection.ANY;
            Card toTrash = currentPlayer.controlPlayer.hermit_cardToTrash(context,
                                                                          options,
                                                                          inDiscard.size());

            if (toTrash != null) {
                if (currentPlayer.discard.contains(toTrash) && (context.hermitTrashCardPile == PileSelection.ANY || context.hermitTrashCardPile == PileSelection.DISCARD)) {
                    currentPlayer.discard.remove(toTrash);
                    currentPlayer.trash(toTrash, this, context);
                } else if (currentPlayer.hand.contains(toTrash) && (context.hermitTrashCardPile == PileSelection.ANY || context.hermitTrashCardPile == PileSelection.HAND)) {
                    currentPlayer.trashFromHand(toTrash, this, context);
                } else {
                    Util.playerError(currentPlayer, "Hermit trash error, chosen card to trash not in hand or discard, ignoring.");
                }
            }
        }

        // Gain a card costing up to 3 coins (no potion)
        Card c = currentPlayer.controlPlayer.hermit_cardToGain(context);
        if (c != null) {
            if (c.getCost(context, false) > 3 || c.getDebtCost(context) > 0 || c.costPotion() || !Cards.isSupplyCard(c)) {
                Util.playerError(currentPlayer, "Hermit card selection error, picking card from table.");
                c = (context.getCardsLeftInPile(Cards.silver) > 0) ? Cards.silver : Cards.copper; 
            }
            currentPlayer.controlPlayer.gainNewCard(c, this, context);
        }
    }
    
    private void ironmonger(Game game, Player currentPlayer, MoveContext context, PlayContext playContext) {
        Card card = game.draw(context, Cards.ironmonger, 1);
        
        if (card != null) {
            currentPlayer.reveal(card, this, context);
            if (currentPlayer.controlPlayer.ironmonger_shouldDiscard(context, card)) {
                currentPlayer.discard(card, this, context);
            } else {
                currentPlayer.putOnTopOfDeck(card, context, true);
            }
            
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

    private void junkDealer(Player currentPlayer, MoveContext context) {
        if (!currentPlayer.hand.isEmpty()) {
            Card card = currentPlayer.controlPlayer.junkDealer_cardToTrash(context);
            if(card == null || !currentPlayer.hand.contains(card)) {
                Util.playerError(currentPlayer, "Junk Dealer card to trash invalid, picking one");
                card = currentPlayer.hand.get(0);
            }
            currentPlayer.trashFromHand(card, this, context);
        }
    }
    
    private void madman(MoveContext context, Game game, Player currentPlayer, PlayContext playContext) {
        if (currentPlayer.isInPlay(this)) {
            // Return to the Madman pile
            currentPlayer.playedCards.remove(currentPlayer.playedCards.indexOf(this.getId()));
            CardPile pile = game.getPile(this);
            pile.addCard(this);

            int handSize = currentPlayer.hand.size();

            for (int i = 0; i < handSize; ++i) {
                game.drawToHand(context, this, handSize - i, playContext);
            }
        }
    }

    private void marauder(MoveContext context, Game game, Player currentPlayer) {
        currentPlayer.gainNewCard(Cards.spoils, this, context);

        for (Player player : game.getPlayersInTurnOrder()) 
        {
            if (player != currentPlayer && !Util.isDefendedFromAttack(game, player, this)) 
            {
                player.attacked(this, context);
                MoveContext playerContext = new MoveContext(game, player);
                playerContext.attackedPlayer = player;
                player.gainNewCard(Cards.virtualRuins, this, playerContext);
            }
        }
    }
    
    private void mercenary(MoveContext context, Game game, Player currentPlayer, PlayContext playContext) {
        int cardsTrashedCount = 0;

        Card[] cards = currentPlayer.controlPlayer.mercenary_cardsToTrash(context);

        if (cards != null) {
            if (cards.length > 2) {
                Util.playerError(currentPlayer, "Mercenary trash error, trying to trash too many cards, ignoring.");
            } else {
                if (cards.length > 1 || currentPlayer.hand.size() == 1) {
                    for (Card card : cards) {
                        for (int i = 0; i < currentPlayer.hand.size(); i++) {
                            Card playersCard = currentPlayer.hand.get(i);
                            if (playersCard.equals(card)) {
                                Card thisCard = currentPlayer.hand.get(i);
                                currentPlayer.trashFromHand(thisCard, this, context);
                                ++cardsTrashedCount;
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (cardsTrashedCount == 2)
        {
            game.drawToHand(context, this, 2, playContext);
            game.drawToHand(context, this, 1, playContext);

            context.addCoins(2, this, playContext);

            for (Player player : game.getPlayersInTurnOrder()) {
                if (player != currentPlayer && !Util.isDefendedFromAttack(game, player, this)) {
                    player.attacked(this, context);
                    MoveContext playerContext = new MoveContext(game, player);
                    playerContext.attackedPlayer = player;

                    int keepCardCount = 3;
                    if (player.hand.size() > keepCardCount) {
                        Card[] cardsToKeep = player.controlPlayer.mercenary_attack_cardsToKeep(playerContext);
                        player.discardRemainingCardsFromHand(playerContext, cardsToKeep, this, keepCardCount);
                    }
                }
            }
        }
    }
    
    private void mystic(Game game, MoveContext context, Player currentPlayer) {

        if (currentPlayer.deck.size() > 0 || currentPlayer.discard.size() > 0) {  // Only allow a guess if there are cards in the deck or discard pile

            // Create a list of all possible cards to guess, using the player's hand, discard pile, and deck 
            // (even though the player could technically name a card he doesn't have)
            ArrayList<Card> options = new ArrayList<Card>(currentPlayer.getDistinctCards());
            Collections.sort(options, new Util.CardNameComparator());

            if (options.size() > 0) {
                Card toName = currentPlayer.controlPlayer.mystic_cardGuess(context, options);
                currentPlayer.controlPlayer.namedCard(toName, this, context);
                Card draw = game.draw(context, Cards.mystic, 1);

                if (draw != null) {
                    currentPlayer.reveal(draw, this, context);

                    if (toName != null && toName.equals(draw)) {
                        currentPlayer.hand.add(draw);
                    } else {
                        currentPlayer.putOnTopOfDeck(draw, context, true);
                    }
                }
            }
        }
    }
    
    private void pillage(Game game, MoveContext context, Player currentPlayer) {
    	ArrayList<Player> attackedPlayers = new ArrayList<Player>();
    	for (Player player : context.game.getPlayersInTurnOrder()) {
            if (player != currentPlayer && !Util.isDefendedFromAttack(context.game, player, this)) {
            	attackedPlayers.add(player);
            }
    	}
    	if (!currentPlayer.trashSelfFromPlay(this, context))
    		return;
    	
        // Gain 2 Spoils from the Spoils pile
        currentPlayer.gainNewCard(Cards.spoils, this, context);
        currentPlayer.gainNewCard(Cards.spoils, this, context);
    	
        // Each other player with 5 cards in hand reveals his hand and discards a card that you choose.
        for (Player targetPlayer : attackedPlayers) {
            if (targetPlayer.getHand().size() >= 5) {
                targetPlayer.attacked(this, context);
                MoveContext targetContext = new MoveContext(context, game, targetPlayer);
                targetContext.attackedPlayer = targetPlayer;
                ArrayList<Card> cardsInHand = new ArrayList<Card>();

                for (Card card : targetPlayer.getHand())
                {
                    cardsInHand.add(card);
                    targetPlayer.reveal(card, this, targetContext);
                }

                Card cardToDiscard = currentPlayer.controlPlayer.pillage_opponentCardToDiscard(targetContext, cardsInHand);

                if (cardToDiscard != null)
                {
                    targetPlayer.hand.remove(cardToDiscard);
                    targetPlayer.discard(cardToDiscard, this, targetContext);
                }
            }
        }
    }
    
    private void poorHouse(MoveContext context, Player currentPlayer) {
        int treasures = 0;

        for (int i = 0; i < currentPlayer.hand.size(); i++) {
            Card card = currentPlayer.hand.get(i);
            currentPlayer.reveal(card, this, context);
            if (card.is(Type.Treasure, currentPlayer, context)) {
                treasures++;
            }
        }
        context.addCoins(-treasures);
    }
    
    private void rats(MoveContext context, Player currentPlayer) {
        currentPlayer.gainNewCard(Cards.rats, this, context);

        if(currentPlayer.hand.size() > 0) {
            boolean hasother = false;
            for (Card c : currentPlayer.hand) {
                if (!c.equals(Cards.rats)) {
                    hasother = true;
                    break;
                }
            }
            if (!hasother) {
                for (int i = 0; i < currentPlayer.hand.size(); i++) {
                    Card card = currentPlayer.hand.get(i);
                    currentPlayer.reveal(card, this, context);
                }
            } else {
                Card card = currentPlayer.controlPlayer.rats_cardToTrash(context);
                if(card == null || card.equals(Cards.rats) || !currentPlayer.hand.contains(card)) {
                    Util.playerError(currentPlayer, "Rats card to trash invalid, picking one");
                    for (Card c : currentPlayer.hand){
                    	if (!c.equals(Cards.rats)) {
                    		card = c;
                    		break;
                    	}
                    }
                }

                currentPlayer.trashFromHand(card, this, context);
            }
        }
    }
    
    private void rebuild(Player currentPlayer, MoveContext context) {
        ArrayList<Card> allCards = new ArrayList<Card>(currentPlayer.getDistinctCards());
        ArrayList<Card> options = new ArrayList<Card>();
        for (Card c : allCards) {
            if(c.is(Type.Victory, currentPlayer))
                options.add(c);
        }
        Collections.sort(options, new Util.CardNameComparator());
        
        Card named = currentPlayer.controlPlayer.rebuild_cardToPick(context, options);        
        currentPlayer.controlPlayer.namedCard(named, this, context);
        ArrayList<Card> cards = new ArrayList<Card>();
        Card last = null;

        // search for first Victory card that was not named
        while ((last = context.game.draw(context, Cards.rebuild, -1)) != null) {
            if (last.is(Type.Victory, currentPlayer) && !last.equals(named)) break;
            cards.add(last);
            currentPlayer.reveal(last, this, context);
        }

        // Discard all other revealed cards
        for (Card c : cards) {
            currentPlayer.discard(c, this, context);
        }

        if (last != null) {
            // Trash the found Victory card
            currentPlayer.trash(last, this, context);

            // Gain Victory card that cost up to 3 more coins
            Card toGain = currentPlayer.controlPlayer.rebuild_cardToGain(context, 3 + last.getCost(context), last.getDebtCost(context), last.costPotion());
            if (toGain != null && toGain.getCost(context) <= last.getCost(context) + 3 &&
            		toGain.getDebtCost(context) <= last.getDebtCost(context) && (!toGain.costPotion() || last.costPotion())) {
                currentPlayer.gainNewCard(toGain, this, context);
            }
        }
    }

    private void rogue(Game game, MoveContext context, Player currentPlayer) {
        ArrayList<Card> options = new ArrayList<Card>();
        for (Card c : game.trashPile) {
            if (!c.costPotion() && c.getCost(context) >= 3 && c.getCost(context) <= 6) {
                options.add(c);
            }
        }

        if (options.size() > 0) { // gain a card
            Card toGain = currentPlayer.controlPlayer.rogue_cardToGain(context);
            if (toGain == null) {
                Util.playerError(currentPlayer, "Rogue error, no card to gain selected, picking random");
                toGain = Util.randomCard(options);
            }

            game.trashPile.remove(toGain);
            currentPlayer.gainCardAlreadyInPlay(toGain, this, context);
        } else { // Other players trash a card
            for (Player targetPlayer : game.getPlayersInTurnOrder()) {
                if (targetPlayer != currentPlayer && !Util.isDefendedFromAttack(game, targetPlayer, this)) {
                    targetPlayer.attacked(this, context);
                    MoveContext targetContext = new MoveContext(game, targetPlayer);
                    targetContext.attackedPlayer = targetPlayer;
                    ArrayList<Card> canTrash = new ArrayList<Card>();

                    List<Card> cardsToDiscard = new ArrayList<Card>();
                    for (int i = 0; i < 2; i++) {
                        Card card = game.draw(targetContext, Cards.rogue, 2 - i);

                        if (card != null) {
                            targetPlayer.reveal(card, this, targetContext);
                            int cardCost = card.getCost(context);

                            if (!card.costPotion() && cardCost >= 3 && cardCost <= 6) {
                                canTrash.add(card);
                            } else {
                                cardsToDiscard.add(card);
                            }
                        }
                    }

                    for (Card c: cardsToDiscard) {
                        targetPlayer.discard(c, this, targetContext);
                    }

                    Card cardToTrash = null;

                    if (canTrash.size() == 1) {
                        cardToTrash = canTrash.get(0);
                    } else if (canTrash.size() == 2) {
                        if (canTrash.get(0).equals(canTrash.get(1))) {
                            cardToTrash = canTrash.get(0);
                            targetPlayer.discard(canTrash.remove(1), this, targetContext);
                        } else {
                            cardToTrash = targetPlayer.controlPlayer.rogue_cardToTrash(context, canTrash);
                        }

                        for (Card card : canTrash) {
                            if (!card.equals(cardToTrash)) {
                                targetPlayer.discard(card, this, targetContext);
                            }
                        }
                    }

                    if (cardToTrash != null) {
                        targetPlayer.trash(cardToTrash, this, targetContext);
                    }
                }
            }
        }
    }
    
    private void sage(Game game, MoveContext context, Player currentPlayer) {
        HashSet<String> cardNames = new HashSet<String>();

        for (int i = 0; i < currentPlayer.hand.size(); i++) {
            Card card = currentPlayer.hand.get(i);
            cardNames.add(card.getName());
            //currentPlayer.reveal(card, this, context);
        }

        ArrayList<Card> toDiscard = new ArrayList<Card>();

        Card draw = null;
        while ((draw = game.draw(context, Cards.sage, -1)) != null && draw.getCost(context) < 3) {
            currentPlayer.reveal(draw, this, context);
            toDiscard.add(draw);
        }

        if (draw != null) {
            currentPlayer.reveal(draw, this, context);
            currentPlayer.hand.add(draw);
        }

        while (!toDiscard.isEmpty()) {
            currentPlayer.discard(toDiscard.remove(0), this, context);
        }
    }
    
    private void scavenger(Game game, MoveContext context, Player currentPlayer) {
        boolean discard = currentPlayer.getDeckSize() == 0 ? false : currentPlayer.controlPlayer.scavenger_shouldDiscardDeck(context);

        // Discard the entire deck if the player chose to do so
        if (discard)
        {
        	currentPlayer.deckToDiscard(context, this);
        }

        // Prompt to add a card from the discard pile back onto the deck, but only if at least one is available
        if (currentPlayer.getDiscardSize() > 0)
        {
            Card card = currentPlayer.controlPlayer.scavenger_cardToPutBackOnDeck(context);

            if (card != null)
            {
                currentPlayer.discard.remove(card);
                currentPlayer.putOnTopOfDeck(card);
                
                GameEvent event = new GameEvent(GameEvent.EventType.CardOnTopOfDeck, context);
                game.broadcastEvent(event);
            }
        }
    }
    
    private void sirMichael(MoveContext context, Game game, Player currentPlayer) {
        for (Player player : context.game.getPlayersInTurnOrder()) {
            if (player != currentPlayer && !Util.isDefendedFromAttack(context.game, player, this)) {
                player.attacked(this, context);
                MoveContext playerContext = new MoveContext(game, player);
                playerContext.attackedPlayer = player;

                int keepCardCount = 3;
                if (player.hand.size() > keepCardCount) {
                    Card[] cardsToKeep = player.controlPlayer.sirMichael_attack_cardsToKeep(playerContext);
                    player.discardRemainingCardsFromHand(playerContext, cardsToKeep, this, keepCardCount);
                }
            }
        }

        knight(context, currentPlayer);
    }
    
    private void spoils(Game game, Player currentPlayer) {
    	if (currentPlayer.isInPlay(this)) {
            CardPile pile = game.getPile(this);
            pile.addCard(currentPlayer.playedCards.remove(currentPlayer.playedCards.indexOf(this.getId())));
    	}
    }
    
    private void squire(MoveContext context, Player currentPlayer) {
        Player.SquireOption option = currentPlayer.controlPlayer.squire_chooseOption(context);

        if (option == null) {
            Util.playerError(currentPlayer, "Squire option error, ignoring.");
        } else {
            if (option == Player.SquireOption.AddActions) {
                context.addActions(2, this);
            } else if (option == Player.SquireOption.AddBuys) {
                context.buys += 2;
            } else if (option == Player.SquireOption.GainSilver) {
                currentPlayer.gainNewCard(Cards.silver, this, context);
            }
        }
    }
    
    private void storeroom(Game game, MoveContext context, Player currentPlayer, PlayContext playContext) {
        Card[] cards = currentPlayer.controlPlayer.storeroom_cardsToDiscardForCards(context);
        if (cards != null) {
            int numberOfCards = 0;
            for (Card card : cards) {
                for (int i = 0; i < currentPlayer.hand.size(); i++) {
                    Card playersCard = currentPlayer.hand.get(i);
                    if (playersCard.equals(card)) {
                        currentPlayer.discard(currentPlayer.hand.remove(i), this, context);
                        numberOfCards++;
                        break;
                    }
                }
            }

            if (numberOfCards != cards.length) {
                Util.playerError(currentPlayer, "Storeroom discard error, trying to discard cards not in hand, ignoring extra.");
            }

            for (int i = 0; i < numberOfCards; ++i) {
                // Uses "draw" instead of "+Card"
            	game.drawToHand(context, this, numberOfCards - i, new PlayContext());
            }
        }

        cards = currentPlayer.controlPlayer.storeroom_cardsToDiscardForCoins(context);
        if (cards != null) {
            int numberOfCards = 0;
            for (Card card : cards) {
                for (int i = 0; i < currentPlayer.hand.size(); i++) {
                    Card playersCard = currentPlayer.hand.get(i);
                    if (playersCard.equals(card)) {
                        currentPlayer.discard(currentPlayer.hand.remove(i), this, context);
                        numberOfCards++;
                        break;
                    }
                }
            }

            if (numberOfCards != cards.length) {
                Util.playerError(currentPlayer, "Storeroom discard error, trying to discard cards not in hand, ignoring extra.");
            }

            while (numberOfCards > 0) {
                numberOfCards--;
                context.addCoins(1, this, playContext);
            }
        }
    }
    
    private void survivors(MoveContext context, Game game, Player currentPlayer) {
        ArrayList<Card> topOfTheDeck = new ArrayList<Card>();
        for (int i = 0; i < 2; i++) {
            Card card = game.draw(context, Cards.survivors, 2 - i);
            if (card != null) {
                topOfTheDeck.add(card);
            }
        }

        if (topOfTheDeck.size() > 0) {
            if (currentPlayer.controlPlayer.survivors_shouldDiscardTopCards(context, topOfTheDeck.toArray(new Card[topOfTheDeck.size()]))) {
                while (!topOfTheDeck.isEmpty()) {
                    currentPlayer.discard(topOfTheDeck.remove(0), this, context);
                }
            } else {
                Card[] order = currentPlayer.controlPlayer.survivors_cardOrder(context, topOfTheDeck.toArray(new Card[topOfTheDeck.size()]));

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
                    Util.playerError(currentPlayer, "Survivors order cards error, ignoring.");
                    order = topOfTheDeck.toArray(new Card[topOfTheDeck.size()]);
                }

                // Put the cards back on the deck
                for (int i = order.length - 1; i >= 0; i--) {
                    currentPlayer.putOnTopOfDeck(order[i]);
                }
            }
        }
    }

    private void urchin(MoveContext context, Game game, Player currentPlayer)   {       
        for (Player player : context.game.getPlayersInTurnOrder()) {
            if (player != currentPlayer && !Util.isDefendedFromAttack(context.game, player, this)) {
                player.attacked(this, context);
                MoveContext playerContext = new MoveContext(game, player);
                playerContext.attackedPlayer = player;

                int keepCardCount = 4;
                if (player.hand.size() > keepCardCount) {
                    Card[] cardsToKeep = player.controlPlayer.urchin_attack_cardsToKeep(playerContext);
                    player.discardRemainingCardsFromHand(playerContext, cardsToKeep, this, keepCardCount);
                }
            }
        }
    }

    private void vagrant(MoveContext context, Game game, Player currentPlayer) {
        Card c = game.draw(context, Cards.vagrant, 1);
        if (c != null) {
            currentPlayer.reveal(c, this, context);
            if (c.getKind() == Cards.Kind.Curse || c.is(Type.Shelter, currentPlayer) || (c.is(Type.Victory, currentPlayer)) || (c.is(Type.Ruins, currentPlayer))) {
                currentPlayer.hand.add(c);
            } else {
                currentPlayer.putOnTopOfDeck(c, context, true);
            }
        }
    }
    
    private void wanderingMinstrel(Player currentPlayer, MoveContext context) {
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 3; i++) {
            Card card = context.game.draw(context, Cards.wanderingMinstrel, 3 - i);
            if (card == null) {
                break;
            }
            if (!(card.is(Type.Action, currentPlayer)) ) {
                currentPlayer.discard(card, this, context);
            } else {
                currentPlayer.reveal(card, this, context);
                cards.add(card);
            }
        }

        if (cards.size() == 0) {
            return;
        }

        Card[] orderedCards = currentPlayer.controlPlayer.topOfDeck_orderCards(context, cards.toArray(new Card[0]));

        for (int i = orderedCards.length - 1; i >= 0; i--) {
            currentPlayer.putOnTopOfDeck(orderedCards[i], context, true);
        }

    }
    
    
    
    
    
    
    private void knight(MoveContext context, Player currentPlayer) {
        for (Player targetPlayer : context.game.getPlayersInTurnOrder()) {
            if (targetPlayer != currentPlayer && !Util.isDefendedFromAttack(context.game, targetPlayer, this)) {
                targetPlayer.attacked(this, context);
                MoveContext targetContext = new MoveContext(context.game, targetPlayer);
                targetContext.attackedPlayer = targetPlayer;
                ArrayList<Card> canTrash = new ArrayList<Card>();

                List<Card> cardsToDiscard = new ArrayList<Card>();
                for (int i = 0; i < 2; i++) {
                    Card card = context.game.draw(targetContext, this, 2 - i);

                    if (card != null) {
                        targetPlayer.reveal(card, this, targetContext);
                        int cardCost = card.getCost(context);

                        if (!card.costPotion() && cardCost >= 3 && cardCost <= 6) {
                            canTrash.add(card);
                        } else {
                            cardsToDiscard.add(card);
                        }
                    }
                }

                for (Card c: cardsToDiscard) {
                    targetPlayer.discard(c, this, targetContext);
                }

                Card cardToTrash = null;

                if (canTrash.size() == 1) {
                    cardToTrash = canTrash.get(0);
                } else if (canTrash.size() == 2) {
                    if (canTrash.get(0).equals(canTrash.get(1))) {
                        cardToTrash = canTrash.get(0);
                        targetPlayer.discard(canTrash.remove(1), this, targetContext);
                    } else {
                        cardToTrash = targetPlayer.knight_cardToTrash(targetContext, canTrash);
                    }

                    for (Card card : canTrash) {
                        if (!card.equals(cardToTrash)) {
                            targetPlayer.discard(card, this, targetContext);
                        }
                    }
                }

                if (cardToTrash != null) {
                    targetPlayer.trash(cardToTrash, this, targetContext);

                    // If the card trashed was a knight, the attacking knight should be trashed as well
                    if (cardToTrash.is(Type.Knight, targetPlayer) && currentPlayer.playedCards.contains(this) && currentPlayer.playedCards.getLastCard() == this) {
                        currentPlayer.trashSelfFromPlay(this, context);
                    }
                }
            }
        }       
    }

}

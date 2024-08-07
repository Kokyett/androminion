package com.vdom.core;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;

import com.vdom.api.Card;
import com.vdom.api.CardCostComparator;
import com.vdom.api.FrameworkEvent;
import com.vdom.api.FrameworkEventHelper;
import com.vdom.api.GameEvent;
import com.vdom.api.GameEvent.EventType;
import com.vdom.api.GameEventListener;
import com.vdom.api.GameType;
import com.vdom.core.CardSet.ExpansionAllocation;
import com.vdom.core.MoveContext.TurnPhase;
import com.vdom.core.Player.DurationEffect;
import com.vdom.core.Player.ExtraTurnOption;
import com.vdom.core.Player.HuntingGroundsOption;
import com.vdom.core.Player.SleighOption;
import com.vdom.core.Player.WatchTowerOption;
import com.vdom.core.Player.FoolsGoldOption;

public class Game {
    public static boolean junit = false;
    public static boolean debug = false;
    public static Integer cardSequence = 1;
    public static HashMap<String, Double> GAME_TYPE_WINS = new HashMap<String, Double>();

    public static HashMap<String, Integer> winStats = new HashMap<String, Integer>();
    public static final String QUICK_PLAY = "(QuickPlay)";
    public static final String BANE = "bane+";
    public static final String OBELISK = "obelisk+";
    public static final String WAY_OF_THE_MOUSE = "wayOfTheMouse+";
    public static final String BLACKMARKET = "blackMarket+";
    public static final String DRUID_BOON = "druidBoon+";

    public static String[] cardsSpecifiedAtLaunch;
    public static ArrayList<String> unfoundCards = new ArrayList<String>();
    String cardListText = "";
    String unfoundCardText = "";
    int totalCardCountGameBegin = 0;
    int totalCardCountGameEnd = 0;


    /**
     * Player classes and optionally the url to the jar on the web that the class is located in. Player class
     * is in index [0] of the array and the jar is in index [1]. Index [1] is null (but still there) if the
     * default class loader should be used.
     */
    static ArrayList<String[]> playerClassesAndJars = new ArrayList<String[]>();
    /**
     * The card set to use for the game.
     *
     * @see com.vdom.api.GameType
     */
    public static GameType gameType = GameType.Random;
    public static List<Expansion> randomExpansions = null;
    public static List<Expansion> randomExcludedExpansions = null;
    public static String gameTypeStr = null;
    public static boolean showUsage = false;

    public static boolean sheltersNotPassedIn = false;
    public static boolean sheltersPassedIn = false;
    public static boolean platColonyNotPassedIn = false;
    public static boolean platColonyPassedIn = false;
    public static double chanceForPlatColony = -1;
    public static double chanceForShelters = 0.0;
    
    public static enum BlackMarketSplitPileOptions {
    	NONE, ONE, ANY, ALL
    }
    
    public static enum PossessionPossessorTokens {
    	NONE, //Original behavior
    	ALL, //Errata introduced Oct 2016
    	DEBT //Errata introduced Mar 2018
    }

    public static int blackMarketCount = 25;
    public static BlackMarketSplitPileOptions blackMarketSplitPileOptions = BlackMarketSplitPileOptions.NONE;
    public static boolean blackMarketOnlyCardsFromUsedExpansions = false;
    
    public static int numRandomEvents = 0;
    public static int numRandomProjects = 0;
    public static int numRandomLandmarks = 0;
    public static int numRandomWays = 0;
    public static boolean splitMaxSidewaysCards = true;
    public static boolean keepSidewaysCardsWithExpansion = false;
    public static int randomExpansionLimit = 0;
    public static ExpansionAllocation randomExpansionAllocation = ExpansionAllocation.Random;
    
    public static int numProjectCubes = 2;

    public static boolean quickPlay = false;
    public static boolean sortCards = false;
    public static boolean actionChains = false;
    public static boolean suppressRedundantReactions = false;
    public static boolean equalStartHands = false;
    public static boolean errataMasqueradeAlwaysAffects = false; //Errata introduced Oct 2016 - true enables old behavior
    public static boolean errataMineForced = false; //Errata introduced Oct 2016 - true enables old behavior
    public static boolean errataMoneylenderForced = false; //Errata introduced Oct 2016 - true enables old behavior
    public static PossessionPossessorTokens errataPossession = PossessionPossessorTokens.DEBT; 
    public static boolean errataThroneRoomForced = false; //Errata introduced Oct 2016 - true enables old behavior
    public static boolean errataShuffleDeckEmptyOnly = false; //Errata introduced Oct 2016 - true enables old behavior
    public static boolean startGuildsCoinTokens = false; //only for testing
    public static boolean lessProvinces = false; //only for testing
    public static boolean noCards = false; //only for testing
    public static boolean godMode = false; //only for testing
    public static boolean disableAi = false; //only for testing
    public static boolean controlAi = false; //only for testing
    public static boolean maskPlayerNames = false;
    public static boolean vpCounter = false; // report vp counts to remote players during the game
    public boolean vpCounterInGame = false;

    public static final HashSet<GameEvent.EventType> showEvents = new HashSet<GameEvent.EventType>();
    public static final HashSet<String> showPlayers = new HashSet<String>();
    static boolean test = false;
    static boolean ignoreAllPlayerErrors = false;
    static boolean ignoreSomePlayerErrors = false;
    static HashSet<String> ignoreList = new HashSet<String>();

    static ArrayList<GameStats> gameTypeStats = new ArrayList<GameStats>();

    static int numGames = -1;
    static int gameCounter = 0;
    public ArrayList<GameEventListener> listeners = new ArrayList<GameEventListener>();
    public GameEventListener gameListener;
    static boolean forceDownload = false;
    static HashMap<String, Double> overallWins = new HashMap<String, Double>();

    public static Random rand = new Random(System.currentTimeMillis());
    public HashMap<String, CardPile> piles = new HashMap<String, CardPile>();
    public HashMap<String, CardPile> placeholderPiles = new HashMap<String, CardPile>();
    public HashMap<String, Integer> embargos = new HashMap<String, Integer>();
    public HashMap<String, Integer> pileVpTokens = new HashMap<String, Integer>();
    public HashMap<String, Integer> pileDebtTokens = new HashMap<String, Integer>();
    private HashMap<String, HashMap<Player, List<PlayerSupplyToken>>> playerSupplyTokens = new HashMap<String, HashMap<Player,List<PlayerSupplyToken>>>();
    public ArrayList<Card> trashPile = new ArrayList<Card>();
    public ArrayList<Card> trashPileFaceDown = new ArrayList<Card>();
    public ArrayList<Card> possessedTrashPile = new ArrayList<Card>();
    public ArrayList<Card> possessedBoughtPile = new ArrayList<Card>();
    public ArrayList<Card> blackMarketPile = new ArrayList<Card>();
    public ArrayList<Card> blackMarketPileShuffled = new ArrayList<Card>();
    public ArrayList<Card> boonDrawPile = new ArrayList<Card>();
    public ArrayList<Card> boonDiscardPile = new ArrayList<Card>();
    public ArrayList<Card> druidBoons = new ArrayList<Card>();
    public ArrayList<Card> hexDrawPile = new ArrayList<Card>();
    public ArrayList<Card> hexDiscardPile = new ArrayList<Card>();
    public ArrayList<Card> sharedStates = new ArrayList<Card>();

    public int tradeRouteValue = 0;
    public Card baneCard = null;
    public Card obeliskCard = null;
    public Card wayOfTheMouseCard = null;
    public boolean firstProvinceWasGained = false;
    public boolean doMountainPassAfterThisTurn = false;
    public int firstProvinceGainedBy;

    public boolean bakerInPlay = false;
    public boolean journeyTokenInPlay = false;

    private static final int kingdomCardPileSize = 10;
    public static int victoryCardPileSize = 12;

    ArrayList<Card>[] cardsObtainedLastTurn;
    ArrayList<Card>[] allCardsObtainedLastTurn;
    ArrayList<Card>[] cardsTrashedLastTurn;
    static int playersTurn;

    //    public UI ui;
    int turnCount = 0;
    int consecutiveTurnCounter = 0;

    public static HashMap<String, Player> cachedPlayers = new HashMap<String, Player>();
    public static HashMap<String, Class<?>> cachedPlayerClasses = new HashMap<String, Class<?>>();
    public static Player[] players;

    public boolean sheltersInPlay = false;
    public List<Card> heirloomsInPlay = new ArrayList<Card>();

    public int possessionsToProcess = 0;
    public Player possessingPlayer = null;
    public int nextPossessionsToProcess = 0;
    public Player nextPossessingPlayer = null;

    public static int numPlayers;
    boolean gameOver = false;
    private Player playerStartedFleetRound;
    boolean isFleetRound = false;
    boolean fleetRoundPending = false;

    private static HashMap<String, Player> playerCache = new HashMap<String, Player>();

    public static void main(String[] args) {
        try {
            go(args, false);
        } catch (ExitException e) {
            // This is what we would correctly need to do.
            // May break some code though which relies on vdom being less strict
            System.exit(-1);
        }
    }

    public static void go(String[] args, boolean html) throws ExitException {

        /*
         * Don't catch ExitException here. If someone throws an ExitException, it means the game is over, which should be handled by whoever owns us.
         */

        processArgs(args);

        Util.debug("");

        // Start game(s)
        if (gameTypeStr != null) {
            gameType = GameType.valueOf(gameTypeStr);            
            new Game().start();
        } else {
            for (String[] className : playerClassesAndJars) {
                overallWins.put(className[0], 0.0);
            }

        for (GameType p : GameType.values()) {
            gameType = p;
            new Game().start();
        }

            if (test) {
                for (int i = 0; i < 5; i++) {
                    gameType = GameType.RandomBaseGame;
                    new Game().start();
                }
                for (int i = 0; i < 5; i++) {
                    gameType = GameType.RandomIntrigue;
                    new Game().start();
                }
                for (int i = 0; i < 5; i++) {
                    gameType = GameType.RandomSeaside;
                    new Game().start();
                }
                for (int i = 0; i < 5; i++) {
                    gameType = GameType.RandomDarkAges;
                    new Game().start();
                }
                for (int i = 0; i < 5; i++) {
                    gameType = GameType.Random;
                    new Game().start();
                }
            }
            if (!debug && !test) {
                Util.log("----------------------------------------------------");
            }
            printStats(overallWins, numGames * GameType.values().length, "Total");
            printStats(GAME_TYPE_WINS, GameType.values().length, "Types");
        }
        if (test) {
            printGameTypeStats();
        }


        FrameworkEvent frameworkEvent = new FrameworkEvent(FrameworkEvent.Type.AllDone);
        FrameworkEventHelper.broadcastEvent(frameworkEvent);
    }
    
    private static class ExtraTurnInfo {
		public ExtraTurnInfo() {}
		public ExtraTurnInfo(boolean canBuyCards) {
			this.canBuyCards = canBuyCards;
		}
		public boolean canBuyCards = true;
	}

    void start() throws ExitException {
        HashMap<String, Double> gameTypeSpecificWins = new HashMap<String, Double>();

        for (String[] className : playerClassesAndJars) {
            gameTypeSpecificWins.put(className[0], 0.0);
            if (!GAME_TYPE_WINS.containsKey(className[0])) {
                GAME_TYPE_WINS.put(className[0], 0.0);
            }
        }

        long turnCountTotal = 0;
        long vpTotal = 0;
        long numCardsTotal = 0;

        // if (test) {
        // System.out.print(gameType + " ");
        // }
        for (int gameCount = 0; gameCount < numGames; gameCount++) {
            // if (test) {
            // System.out.print(gameCount + " ");
            // System.out.flush();
            // }
            Util.debug("---------------------", false);

            Util.debug("New Game:" + gameType);
            initGameBoard();
            if (test) {
                Util.log(gameType.toString());
                totalCardCountGameBegin = totalCardCount();
            }

            gameOver = false;
            playerStartedFleetRound = null;
            isFleetRound = false;
            fleetRoundPending = false;
            gameCounter++;
            playersTurn = 0;
            turnCount = 1;
            Util.debug("Turn " + turnCount);
            
            Queue<ExtraTurnInfo> extraTurnsInfo = new LinkedList<ExtraTurnInfo>();

            while (!gameOver) {
                Player player = players[playersTurn];
                boolean canBuyCards = extraTurnsInfo.isEmpty() ? true : extraTurnsInfo.remove().canBuyCards;
                MoveContext context = new MoveContext(this, player, canBuyCards);
                context.phase = TurnPhase.Action;
                context.startOfTurn = true;
                playerBeginTurn(player, context);
                context.startOfTurn = false;

                do {
                    if (godMode && !player.isAi())
                    {
                        context.buys = 999;
                        if (placeholderPiles.containsKey(Cards.potion.getName())) {
                            context.potions = 999;
                        }
                        context.addCoins(999);
                        context.addActions(999);
                    }


                	context.phase = TurnPhase.Action;
                	context.returnToActionPhase = false;
                	
                    // /////////////////////////////////
                    // Actions
                    // /////////////////////////////////
	                playerAction(player, context);
	                spendExtraVillagers(player, context);
		
	                // /////////////////////////////////
	                // Buy Phase
	                // /////////////////////////////////
	                context.phase = TurnPhase.Buy;
                    context.totalCardsGainedInMostRecentBuyPhase = 0;
	                playerBeginBuy(player, context);
	                
	                boolean hasMoreTreasures = false;
	                boolean declinedTokens = false;
	                do {
		                playTreasures(player, context, -1, null);
		                hasMoreTreasures = playerHasMoreTreasures(player, context);
		                // Spend Guilds coin tokens if applicable
		                int tokensSpent = playGuildsTokens(player, context);
		                declinedTokens = tokensSpent == 0;
		                //Allow playing treasures again for cards like Fortune where you want to play the Treasure after spending tokens
	                } while (hasMoreTreasures && !declinedTokens);
	
	                // /////////////////////////////////
	                // Buying cards
	                // /////////////////////////////////
	                playerBuy(player, context);
                } while (context.returnToActionPhase);
                
                if (context.totalCardsBoughtThisTurn + context.totalEventsBoughtThisTurn + context.totalProjectsBoughtThisTurn == 0) {
                    GameEvent event = new GameEvent(GameEvent.EventType.NoBuy, context);
                    broadcastEvent(event);
                    Util.debug(player.getPlayerName() + " did not buy a card with coins:" + context.getCoinAvailableForBuy());
                }
                
                context.phase = TurnPhase.Night;
                playNight(player, context);

                // /////////////////////////////////
                // Discard, draw new hand
                // /////////////////////////////////
                context.phase = TurnPhase.CleanUp;
                player.cleanup(context);
                
                //clean up other players cards in play without future duration effects, e.g. Duplicate
                for (Player otherPlayer : getPlayersInTurnOrder()) {
                	if (otherPlayer != player) {
                		otherPlayer.cleanupOutOfTurn(new MoveContext(this, otherPlayer));
                	}
                }

                playerEndOfTurn(player, context);
                
                boolean wasPossessed = player.isPossessed();
                extraTurnsInfo.addAll(playerEndTurn(player, context));
                gameOver = checkGameOver(wasPossessed);

                if (!gameOver) {
                	context.phase = null;
                	if (player.isControlled()) {
                		player.stopBeingControlled();
                	}
                    setPlayersTurn(!extraTurnsInfo.isEmpty());
                }
            }

            // unmask players
            maskPlayerNames = false;
            int vps[] = gameOver(gameTypeSpecificWins);
            if (test) {
                // Compute game stats
                turnCountTotal += turnCount;
                for (int i = 0; i < vps.length; i++) {
                    vpTotal += vps[i];
                    numCardsTotal += players[i].getAllCards().size();
                }
                totalCardCountGameEnd = totalCardCount();
                assert (totalCardCountGameBegin == totalCardCountGameEnd);
                printCardCounts();
            }

        }

        // Java program ending
        if (!debug) {
            markWinner(gameTypeSpecificWins);
            printStats(gameTypeSpecificWins, numGames, gameType.toString());

            // Util.log("---------------------");
        }

        if (test) {
            // System.out.println();
            ArrayList<Card> gameCards = new ArrayList<Card>();
            for (CardPile pile : piles.values()) {
                Card card = pile.placeholderCard();
                if (!card.equals(Cards.copper) && !card.equals(Cards.silver) && !card.equals(Cards.gold) && !card.equals(Cards.platinum)
                    && !card.equals(Cards.estate) && !card.equals(Cards.duchy) && !card.equals(Cards.province) && !card.equals(Cards.colony)
                    && !card.equals(Cards.curse)) {
                    gameCards.add(card);
                }
            }

            GameStats stats = new GameStats();
            stats.gameType = gameType;
            stats.cards = gameCards.toArray(new Card[0]);
            stats.aveTurns = (int) (turnCountTotal / numGames);
            stats.aveNumCards = (int) (numCardsTotal / (numGames * numPlayers));
            stats.aveVictoryPoints = (int) (vpTotal / (numGames * numPlayers));

            gameTypeStats.add(stats);
        }

        FrameworkEvent frameworkEvent = new FrameworkEvent(FrameworkEvent.Type.GameTypeOver);
        frameworkEvent.setGameType(gameType);
        frameworkEvent.setGameTypeWins(gameTypeSpecificWins);
        FrameworkEventHelper.broadcastEvent(frameworkEvent);
    }
    
    private boolean playerHasMoreTreasures(Player p, MoveContext context) {
    	boolean result = false;
    	for(Card c : p.getHand()) {
    		if (c.is(Type.Treasure, p, context)) {
    			result = true;
    			break;
    		}
    	}
    	return result;
    }

    protected void setPlayersTurn(boolean takeAnotherTurn) {
        if (!takeAnotherTurn && consecutiveTurnCounter > 0) {
            // next player
            consecutiveTurnCounter = 0;
            if (fleetRoundPending)
            	isFleetRound = true;
        	//find next player (Fleet player or possessed player if in Fleet Round)
        	do {
        		if (++playersTurn >= numPlayers) {
	                playersTurn = 0;
	                Util.debug("Turn " + ++turnCount, true);
	            }
        	} while (isFleetRound && possessionsToProcess == 0 && !players[playersTurn].hasProject(Cards.fleet));
        }
    }

    public int cardsInLowestPiles (int numPiles) {
        int[] ips = new int[piles.size() - 1 - (isColonyInGame() ? 1 : 0)];
        int count = 0;
        for (CardPile pile : piles.values()) {
            if (pile.placeholderCard() != Cards.province && pile.topCard() != Cards.colony)
                ips[count++] = pile.getCount();
        }
        Arrays.sort(ips);
        count = 0;
        for (int i = 0; i < numPiles; i++) {
            count += ips[i];
        }
        return count;
    }

    protected int playTreasures(Player player, MoveContext context, int maxCards, Card responsible) {
    	// storyteller sets maxCards != -1
    	if (disableAi && player.isAi()) return 0;
    	int totalPlayed = 0;
    	boolean selectingCoins = playerShouldSelectCoinsToPlay(context, player.getHand());
        if (maxCards != -1) selectingCoins = true;// storyteller
        ArrayList<Card> treasures = null;
        treasures = (selectingCoins) ? player.controlPlayer.treasureCardsToPlayInOrder(context, maxCards, responsible) : player.getTreasuresInHand(context);

        while (treasures != null && !treasures.isEmpty() && maxCards != 0) {
            while (!treasures.isEmpty() && maxCards != 0) {
                Card card = treasures.remove(0);
                if (player.hand.contains(card)) {// this is needed due to counterfeit which trashes cards during this loop
                    card.play(context.game, context, true);
                    totalPlayed++;
                    maxCards--;
                }
            }
            if (maxCards != 0)
            	treasures = (selectingCoins) ? player.controlPlayer.treasureCardsToPlayInOrder(context, maxCards, responsible) : player.getTreasuresInHand(context);
        }
        return totalPlayed;
    }
    
    protected void playNight(Player player, MoveContext context) {
	    Card nightCard = null;
	    do {
	        nightCard = null;
	        ArrayList<Card> nightCards = null;
	        if (disableAi && player.isAi()) continue;
	        if (!actionChains || player.controlPlayer.isAi()) {
	            nightCard = player.controlPlayer.nightCardToPlay(context);
	            if (nightCard != null) {
	                nightCards = new ArrayList<Card>();
	                nightCards.add(nightCard);
	            }
	        } else {
	            Card[] cs = player.controlPlayer.nightCardsToPlayInOrder(context);
	            if (cs != null && cs.length != 0) {
	                nightCards = new ArrayList<Card>();
	                for (int i = 0; i < cs.length; i++) {
	                    nightCards.add(cs[i]);
	                }
	            }
	        }
	
	        while (nightCards != null && !nightCards.isEmpty()) {
	            nightCard = nightCards.remove(0);
	            if (nightCard != null) {
	                if (isValidNightCard(context, nightCard)) {
	                    GameEvent event = new GameEvent(GameEvent.EventType.Status, context);
	                    broadcastEvent(event);
	                    nightCard.play(this, context, true);
	                } else {
	                    Util.debug("Error:Invalid Night card selected");
	                }
	            }
	        }
	    } while (nightCard != null);
    }

    protected int playGuildsTokens(Player player, MoveContext context)
    {
    	if (disableAi && player.isAi()) return 0;
        int coinTokenTotal = player.getGuildsCoinTokenCount();

        if (coinTokenTotal > 0)
        {
            // Offer the player the option of "spending" Guilds coin tokens prior to buying cards
            int numTokensToSpend = player.controlPlayer.numGuildsCoinTokensToSpend(context, coinTokenTotal, false/*!butcher*/);

            if (numTokensToSpend > 0 && numTokensToSpend <= coinTokenTotal)
            {
                player.spendGuildsCoinTokens(numTokensToSpend);
                context.addCoins(numTokensToSpend);
                GameEvent event = new GameEvent(GameEvent.EventType.GuildsTokenSpend, context);
                event.setAmount(numTokensToSpend);
                context.game.broadcastEvent(event);
                return numTokensToSpend;
            }
        }
        return 0;
    }
    
    public void useVillagersForActions(Player player, MoveContext context) {
    	if (disableAi && player.isAi()) return;
        int villagerTotal = player.getVillagers();

        if (villagerTotal > 0)
        {
            // Offer the player the option of "spending" Villager coin tokens prior to buying cards
            int numTokensToSpend = player.controlPlayer.numVillagerTokensToSpend(context, villagerTotal);

            if (numTokensToSpend > 0 && numTokensToSpend <= villagerTotal)
            {
                player.useVillagers(numTokensToSpend, context, null);
                context.addActions(numTokensToSpend);
            }
        }
    }
    
    protected void useVillagerForAction(Player player, MoveContext context) {
    	if (disableAi && player.isAi()) return;
        int villagerTotal = player.getVillagers();

        if (villagerTotal > 0)
        {
            // Offer the player the option of using a Villagers token for an extra action
            boolean spendToken = player.controlPlayer.spendVillagerForAction(context);

            if (spendToken)
            {
                player.useVillagers(1, context, null);
                context.addActions(1);
            }
        }
    }

    private void markWinner(HashMap<String, Double> gameTypeSpecificWins) {
        double highWins = 0;
        int winners = 0;

        for (String player : gameTypeSpecificWins.keySet()) {
            if (gameTypeSpecificWins.get(player) > highWins) {
                highWins = gameTypeSpecificWins.get(player);
            }
        }

        for (String player : gameTypeSpecificWins.keySet()) {
            if (gameTypeSpecificWins.get(player) == highWins) {
                winners++;
            }
        }

        double points = 1.0 / winners;
        for (String player : gameTypeSpecificWins.keySet()) {
            if (gameTypeSpecificWins.get(player) == highWins) {
                double playerWins = 0;
                if (GAME_TYPE_WINS.containsKey(player)) {
                    playerWins = GAME_TYPE_WINS.get(player);
                }
                playerWins += points;

                GAME_TYPE_WINS.put(player, playerWins);
            }
        }

        GAME_TYPE_WINS.toString();
    }

    protected int[] gameOver(HashMap<String, Double> gameTypeSpecificWins) {
        if (debug)
            printPlayerTurn();

        int[] vps = calculateVps();

        for (int i = 0; i < numPlayers; i++) {
            int tieCount = 0;
            boolean loss = false;
            for (int j = 0; j < numPlayers; j++) {
                if (i == j) {
                    continue;
                }
                if (vps[i] < vps[j]) {
                    loss = true;
                    break;
                }
                if (vps[i] == vps[j]) {
                    tieCount++;
                }
            }

            if (!loss) {
                double num = gameTypeSpecificWins.get(players[i].getClass().getName());
                Double overall = overallWins.get(players[i].getClass().getName());
                boolean trackOverall = (overall != null);
                if (tieCount == 0) {
                    num += 1.0;
                    if (trackOverall) {
                        overall += 1.0;
                    }
                } else {
                    num += 1.0 / (tieCount + 1);
                    if (trackOverall) {
                        overall += 1.0 / (tieCount + 1);
                    }
                }
                gameTypeSpecificWins.put(players[i].getClass().getName(), num);
                if (trackOverall) {
                    overallWins.put(players[i].getClass().getName(), overall);
                }
            }

            Player player = players[i];
            player.vps = vps[i];
            player.win = !loss;
            if (test && player.win) {
            	Util.log("Winner: " + player.getPlayerName());
            }
            MoveContext context = new MoveContext(this, player);
            broadcastEvent(new GameEvent(GameEvent.EventType.GameOver, context));

        }
        int index = 0;
        for (Player player : players) {
            int vp = vps[index++];
            Util.debug(player.getPlayerName() + ":Victory Points=" + vp, true);
            GameEvent event = new GameEvent(GameEvent.EventType.VictoryPoints, null);
            event.setPlayer(player);
            event.setComment(":" + vp);
            broadcastEvent(event);
        }
        return vps;

    }

    protected void printPlayerTurn() {
        for (Player player : players) {
            Util.debug("", true);
            ArrayList<Card> allCards = player.getAllCards();
            StringBuilder msg = new StringBuilder();
            msg.append(" " + allCards.size() + " Cards: ");

            final HashMap<String, Integer> cardCounts = new HashMap<String, Integer>();
            for (Card card : allCards) {
                String key = card.getName() + " -> " + card.getDescription();
                Integer count = cardCounts.get(key);
                if (count == null) {
                    cardCounts.put(key, 1);
                } else {
                    cardCounts.put(key, count + 1);
                }
            }

            ArrayList<Card> removeDuplicates = new ArrayList<Card>();
            for (Card card : allCards) {
                if (!removeDuplicates.contains(card)) {
                    removeDuplicates.add(card);
                }
            }
            allCards = removeDuplicates;

            Collections.sort(allCards, new Comparator<Card>() {
                public int compare(Card o1, Card o2) {
                    String keyOne = o1.getName() + " -> " + o1.getDescription();
                    String keyTwo = o2.getName() + " -> " + o2.getDescription();
                    return cardCounts.get(keyTwo) - cardCounts.get(keyOne);
                }
            });

            boolean first = true;
            for (Card card : allCards) {
                String key = card.getName() + " -> " + card.getDescription();
                if (first) {
                    first = false;
                } else {
                    msg.append(", ");
                }
                msg.append("" + cardCounts.get(key) + " " + card.getName());
            }

            Util.debug(player.getPlayerName() + ":" + msg, true);
        }
        Util.debug("", true);
    }

    protected List<ExtraTurnInfo> playerEndTurn(Player player, MoveContext context) {
        int handCount = 5;

        List<ExtraTurnInfo> result = new ArrayList<Game.ExtraTurnInfo>();
        // Can only have at most two consecutive turns
        for (Card card : player.playedCards) {
            if (card.takeAnotherTurn()) {
                handCount = card.takeAnotherTurnCardCount();
                if (consecutiveTurnCounter <= 1) {
                	result.add(new ExtraTurnInfo());
                    break;
                }
            }
        }
        
        handCount += context.totalExpeditionBoughtThisTurn;
        if (context.game.hasState(player, Cards.flag)) {
        	handCount += 1;
        }

        // draw next hand
        {
            PlayContext drawContext = new PlayContext();
            for (int i = 0; i < handCount; i++) {
                drawToHand(context, null, handCount - i, false, drawContext);
            }
        }
        	        	
        // /////////////////////////////////
        // Reset context for status update
        // /////////////////////////////////
        context.resetActions();
        context.buys = 1;
                
        GameEvent event = new GameEvent(GameEvent.EventType.NewHand, context);
        broadcastEvent(event);
        event = null;

        // /////////////////////////////////
        // Turn End
        // /////////////////////////////////
                
        if (player.save != null) {
            player.hand.add(player.save);
            player.save = null;
        }
        
		while (!player.faithfulHound.isEmpty()) {
			player.hand.add(player.faithfulHound.remove(0));
		}
		
		for (Player currentPlayer : getPlayersInTurnOrder()) {
            PlayContext drawContext = new PlayContext();
        	for (int i = 0; i < currentPlayer.theRiversGiftDraw; ++i) {
            	drawToHand(context, Cards.theRiversGift, player.theRiversGiftDraw - i, drawContext);
            }
        	currentPlayer.theRiversGiftDraw = 0;
        }
        
        for (Player currentPlayer : getPlayersInTurnOrder()) {
            PlayContext drawContext = new PlayContext();
        	for (int i = 0; i < currentPlayer.wayOfTheSquirrelDraw; ++i) {
            	drawToHand(context, Cards.wayOfTheSquirrel, player.wayOfTheSquirrelDraw - i, drawContext);
            }
        	currentPlayer.wayOfTheSquirrelDraw = 0;
        }
                        
        if (cardsObtainedLastTurn[playersTurn].size() == 0 && cardInGame(Cards.baths)) {
        	int tokensLeft = getPileVpTokens(Cards.baths);
    		if (tokensLeft > 0) {
    			int tokensToTake = Math.min(tokensLeft, 2);
    			removePileVpTokens(Cards.baths, tokensToTake, context);
    			player.addVictoryTokens(context, tokensToTake, Cards.baths);
    		}
        }
                
        if (player.isPossessed()) {
            while (!possessedTrashPile.isEmpty()) {
                player.discard(possessedTrashPile.remove(0), null, null, false, false);
            }
            possessedBoughtPile.clear();
        }
        
        for (Player otherPlayer : getPlayersInTurnOrder()) {
        	if (otherPlayer != player) {
        		while (!otherPlayer.faithfulHound.isEmpty()) {
        			otherPlayer.hand.add(otherPlayer.faithfulHound.remove(0));
        		}
        	}
        }
        
        trashPileFaceDown.clear();
        
        context.actionsPlayedSoFar = 0;
        context.actionsPlayedThisTurnStillInPlay.clear();
        context.coppersmithsPlayed = 0;
                
        event = new GameEvent(GameEvent.EventType.TurnEnd, context);
        broadcastEvent(event);        
        
        if (player.isPossessed()) {
            if (--possessionsToProcess == 0)
                possessingPlayer = null;
                player.controlPlayer = player;
        } else if (nextPossessionsToProcess > 0) {
            possessionsToProcess = nextPossessionsToProcess;
            possessingPlayer = nextPossessingPlayer;
            nextPossessionsToProcess = 0;
            nextPossessingPlayer = null;
        }
        
        if (context.missionBought && consecutiveTurnCounter <= 1) {
			if (!result.isEmpty()) {
				//ask player if they want to do mission turn with three cards or do outpost turn first then mission turn
				// player not possessed because we are between turns
				
				//TODO: dominionator - integrate this with Possession turn logic
				ExtraTurnOption[] options = new ExtraTurnOption[]{ExtraTurnOption.OutpostFirst, ExtraTurnOption.MissionFirst};
				switch(player.extraTurn_chooseOption(context, options)) {
				case MissionFirst:
					result.get(0).canBuyCards = false;
					break;
				case OutpostFirst:
					result.add(new ExtraTurnInfo(false));
					break;
				case PossessionFirst:
					//TODO
					break;
				default:
					break;
				}
			} else {
				result.add(new ExtraTurnInfo(false));
			}
		}
        
        if (context.seizeTheDayBought) {
			//TODO: what about extra turn ordering (mission/possession)?
			result.add(new ExtraTurnInfo());
		}
        
        return result;
    }

    protected void playerEndOfTurn(Player player, MoveContext context) {
    	while (context.donatesBought-- > 0) {
        	while(!player.deck.isEmpty()) {
        		player.hand.add(player.deck.removeLastCard());
        	}
        	while(!player.discard.isEmpty()) {
        		player.hand.add(player.discard.removeLastCard());
        	}
        	Card[] cardsToTrash = player.donate_cardsToTrash(context);
        	if (cardsToTrash != null) {
                for (Card c : cardsToTrash) {
                    Card toTrash = player.hand.get(c);
                    if (toTrash == null) {
                        Util.playerError(player, "Donate error, tried to trash card not in hand: " + c);
                    } else {
                        player.trashFromHand(toTrash, Cards.donate, context);
                    }
                }
            }
            while(!player.hand.isEmpty()) {
                player.deck.add(player.hand.removeLastCard());
            }
            player.shuffleDeck(context, Cards.donate);
            PlayContext drawContext = new PlayContext();
            for (int i = 0; i < 5; ++i) {
                drawToHand(context, Cards.donate, 5 - i, drawContext);
        	}
        }
        
        // Mountain Pass bidding
    	if (cardInGame(Cards.mountainPass) && doMountainPassAfterThisTurn) {
    		doMountainPassAfterThisTurn = false;
    		
    		int highestBid = 0;
    		Player highestBidder = null;
    		final int MAX_BID = 40;
    		int playersLeftToBid = numPlayers;
    		for (Player biddingPlayer : getPlayersInTurnOrder((firstProvinceGainedBy + 1) % numPlayers)) {
    			MoveContext bidContext = new MoveContext(this, biddingPlayer);
    			int bid = biddingPlayer.mountainPass_getBid(context, highestBidder, highestBid, --playersLeftToBid);
    			if (bid > MAX_BID) bid = MAX_BID;
    			if (bid < 0) bid = 0;
    			if (bid != 0 && bid > highestBid) {
    				highestBid = bid;
    				highestBidder = biddingPlayer;
    			}
    			GameEvent event = new GameEvent(GameEvent.EventType.MountainPassBid, bidContext);
	        	event.setAmount(bid);
	        	event.card = Cards.mountainPass;
	            context.game.broadcastEvent(event);
    			if (bid == MAX_BID) {
    				break;
    			}
    		}
    		if (highestBidder != null) {
    			MoveContext bidContext = new MoveContext(this, highestBidder);
    			highestBidder.addVictoryTokens(bidContext, 8, Cards.mountainPass);
    			highestBidder.gainDebtTokens(highestBid);
    			GameEvent event = new GameEvent(GameEvent.EventType.DebtTokensObtained, context);
            	event.setAmount(highestBid);
                context.game.broadcastEvent(event);
    		}
    		GameEvent winEvent = new GameEvent(GameEvent.EventType.MountainPassWinner, context);
    		winEvent.setPlayer(highestBidder == null ? context.getPlayer(): highestBidder);
    		winEvent.setAmount(highestBid);
            context.game.broadcastEvent(winEvent);
    	}
    }
    
    protected void playerAction(Player player, MoveContext context) {
        // TODO move this check to action and buy (and others?)
        // if(player.hand.size() > 0)
        Card action = null;
        do {
            action = null;
            ArrayList<Card> actionCards = null;
            if (disableAi && player.isAi()) continue;
            if (!actionChains || player.controlPlayer.isAi()) {
                action = player.controlPlayer.doAction(context);
                if (action != null) {
                    actionCards = new ArrayList<Card>();
                    actionCards.add(action);
                }
            } else {
                Card[] cs = player.controlPlayer.actionCardsToPlayInOrder(context);
                if (cs != null && cs.length != 0) {
                    actionCards = new ArrayList<Card>();
                    for (int i = 0; i < cs.length; i++) {
                        actionCards.add(cs[i]);
                    }
                }
            }

            while (context.getActions() > 0 && actionCards != null && !actionCards.isEmpty()) {
                action = actionCards.remove(0);
                if (action != null) {
                    if (isValidAction(context, action)) {
                        GameEvent event = new GameEvent(GameEvent.EventType.Status, (MoveContext) context);
                        broadcastEvent(event);
                        context.spendAction();
                        action.play(this, (MoveContext) context, true);
                    } else {
                        Util.debug("Error:Invalid action selected");
                        // action = null;
                    }
                }
            }
            
            // Villagers
            boolean hasAction = false;
            for (Card card : context.player.hand) {
                if (card.is(Type.Action, context.player)) {
                    hasAction = true;
                }
            }
            if (context.getActions() == 0 && context.player.getVillagers() > 0 && hasAction) {
            	useVillagerForAction(context.player, context);
            }
        } while (context.getActions() > 0 && action != null);
    }
    
    protected void spendExtraVillagers(Player player, MoveContext context) {
    	boolean hasDiademInHand = false;
        for (Card card : context.player.hand) {
            if (card.equals(Cards.diadem)) {
            	hasDiademInHand = true;
            }
        }
        //TODO: allow this when diadem is in the deck at all (e.g. for Venture)
        if (cardInGame(Cards.possession) || hasDiademInHand) {
        	useVillagersForActions(player, context);
        }
    }
    
    public void playerPayOffDebt(Player player, MoveContext context) {
    	if (player.getDebtTokenCount() > 0 && context.getCoins() > 0) {
    		int payOffNum = player.controlPlayer.numDebtTokensToPayOff(context);
    		if (payOffNum > context.getCoins() || payOffNum < 0) {
    			payOffNum = 0;
    		}
    		if (payOffNum > player.getDebtTokenCount()) {
    			payOffNum = player.getDebtTokenCount();
    		}
    		if (payOffNum > 0) {
	    		context.spendCoins(payOffNum);
	    		player.payOffDebtTokens(payOffNum);
	    		GameEvent event = new GameEvent(GameEvent.EventType.DebtTokensPaidOff, context);
	        	event.setAmount(payOffNum);
	            context.game.broadcastEvent(event);
    		}
    	}
    }

    protected void playerBuy(Player player, MoveContext context) {
        Card buy = null;
        do {
        	if (disableAi && player.isAi()) continue;
            if (context.buys <= 0) break; //Player can enter buy phase with 0 or less buys after buying but not playing Villa
        	buy = null;
            try {
            	playerPayOffDebt(player, context);
            	if (player.getDebtTokenCount() == 0) {
            		buy = player.controlPlayer.doBuy(context);
                    if (buy != null) {
                        buy = getPile(buy).topCard(); //Swap in the actual top card of the pile
                    }
            	}
            } catch (Throwable t) {
                Util.playerError(player, t);
            }

            if (buy != null) {
                if (isValidBuy(context, buy)) {
                	if(buy.is(Type.Event)) {
                        context.totalEventsBoughtThisTurn++;
                	} else if (buy.is(Type.Project)) {
                        context.totalProjectsBoughtThisTurn++;
                	}
                	else
                	{
                        context.totalCardsBoughtThisTurn++;
                        context.totalCardsBoughtInMostRecentBuyPhase++;
                	}
                    GameEvent statusEvent = new GameEvent(GameEvent.EventType.Status, (MoveContext) context);
                    broadcastEvent(statusEvent);



                    playBuy(context, buy);
                    playerPayOffDebt(player, context);
                    if (context.returnToActionPhase)
                    	return;
                } else {
                    // TODO report?
                    buy = null;
                }
            }
        } while (context.buys > 0 && buy != null);

        // Get Coffers from Merchant Guild
        int merchantGuildCoffers = context.merchantGuildEffects * context.totalCardsGainedInMostRecentBuyPhase;
        if (merchantGuildCoffers > 0) {
            player.gainGuildsCoinTokens(merchantGuildCoffers, context, Cards.merchantGuild);
        }

        //Discard Wine Merchants from Tavern
        if(context.getCoinAvailableForBuy() >= 2) {
        	int wineMerchants = 0;
            for (Card card : player.getTavern()) {
                if (Cards.wineMerchant.equals(card)) {
                	wineMerchants++;
                }
            }
            if (wineMerchants > 0) {
	            int wineMerchantsTotal = player.controlPlayer.cleanup_wineMerchantToDiscard(context, wineMerchants);
	            if (wineMerchants < 0 || wineMerchantsTotal > wineMerchants) {
	                Util.playerError(player, "Wine Merchant discard error, invalid number of Wine Merchants. Discarding all Wine Merchants.");
	                wineMerchantsTotal = wineMerchants;
	            }
	            if(wineMerchantsTotal > 0) {
	            	for (int i = 0; i < wineMerchantsTotal; i++) {
	            	    Card card = player.getTavern().get(Cards.wineMerchant);
	            	    player.getTavern().remove(card);
	                    player.discard(card, null, context, true, false); //set commandedDiscard=true and cleanup=false to force GameEvent 
	            	}
	            }
            }
        }

        if (player.hasProject(Cards.exploration) && context.getTotalCardsBoughtInMostRecentBuyPhase() == 0) {
        	player.gainGuildsCoinTokens(1, context, Cards.exploration);
        	player.takeVillagers(1, context, Cards.exploration);
        }
        
        if (player.hasProject(Cards.pageant) && context.getCoins() > 0) {
        	if (player.controlPlayer.pageant_payCoinForCoffers(context)) {
        		context.spendCoins(1);
        		player.gainGuildsCoinTokens(1, context, Cards.pageant);
        	}
        }
    }

	protected void playerBeginTurn(Player player, MoveContext context) {
        if (context.game.possessionsToProcess > 0) {
            player.controlPlayer = context.game.possessingPlayer;
        } else {
            player.controlPlayer = player;
            consecutiveTurnCounter++;
        }
        if (controlAi && player.isAi()) {
        	for (Player curPlayer : players) {
        		if (curPlayer.isAi()) continue;
        		player.startBeingControlled(curPlayer);
        		break;
        	}
        }
        
        cardsObtainedLastTurn[playersTurn].clear();
        allCardsObtainedLastTurn[playersTurn].clear();
        cardsTrashedLastTurn[playersTurn].clear();
        if (consecutiveTurnCounter == 1 && !isFleetRound)
            player.newTurn();
        
        player.clearDurationEffectsOnOtherPlayers();
        player.guardianEffect = false;
        
        GameEvent gevent = new GameEvent(GameEvent.EventType.TurnBegin, context);
        broadcastEvent(gevent);
        
      //Send events for multiplying cards so they show up as in play
        for (Card c : player.playedCards) {
        	CardImpl actualCard = (CardImpl) c;
        	if (actualCard.getMultiplyingCards() != null && actualCard.getMultiplyingCards().size() > 0) {
        		GameEvent event = new GameEvent(GameEvent.EventType.CardInPlay, context);
                event.card = c;
                event.newCard = true;
                broadcastEvent(event);
        	}
        }

        /* Duration cards, horse traders, cards on prince*/
        
        /* selectOption() must know if horse traders are set aside by reaction or by haven/gear or by prince.
         * We put 2 cards in list durationEffects to differentiate:
         * Examples (Curse is here a dummy card):
         * HorseTrader - (Curse)
         * Haven - Card set aside by haven
         * Gear - List of Cards set aside by gear
         * Archive - List of Cards set aside by archive
         * Crypt - List of Cards set aside by crypt
         * Prince - Card set aside by prince
         * other Durations like Wharf - (Curse)
         */
        boolean allDurationAreSimple = true;
        ArrayList<Object> durationEffects = new ArrayList<Object>();
        ArrayList<Boolean> durationEffectsAreCards = new ArrayList<Boolean>();
        int archiveNum = 0;
        int cryptNum = 0;
        for (Iterator<DurationEffect> it = player.startTurnDurationEffects.iterator(); it.hasNext();) {
        	DurationEffect effect = it.next();
        	if (effect.numTurnsLeft > 0) {
        		effect.numTurnsLeft--;
        		if (effect.numTurnsLeft == 0)
        			it.remove();
        	}
            Card thisCard = effect.effect;
            boolean effectHasCard = effect.sourceCard != null && !effect.isThronedEffect;
            Card cardOrEffect = effectHasCard ? effect.sourceCard : thisCard;
            /* Wiki:
             * Effects that resolve at the start of your turn can be resolved in any order;
             * this includes multiple plays of the same Duration card by a Throne Room variant.
             * For example, if you played a Wharf and then a Throne Room on an Amulet last turn,
             * on this turn you could choose to first gain a Silver from the first Amulet play,
             * then draw 2 cards from Wharf (perhaps triggering a reshuffle and maybe drawing
             * that Silver), and then choose to trash a card from the second Amulet play,
             * now that you have more cards to choose from. 
             */
            if(thisCard.equals(Cards.amulet)
               || thisCard.equals(Cards.dungeon)
               || thisCard.equals(Cards.cobbler)
               || thisCard.equals(Cards.captain)
               || thisCard.equals(Cards.church)
               || thisCard.equals(Cards.mastermind)) {
                allDurationAreSimple = false;
            }
            if(thisCard.equals(Cards.haven)) {
            	if(player.haven != null && player.haven.size() > 0) {
            		durationEffects.add(cardOrEffect);
            		durationEffects.add(player.haven.remove(0));
            		durationEffectsAreCards.add(effectHasCard);
            		durationEffectsAreCards.add(false);
        		}
            } else if (thisCard.equals(Cards.gear)) {
            	if(player.gear.size() > 0) {
            		durationEffects.add(cardOrEffect);
            		durationEffects.add(player.gear.remove(0));
            		durationEffectsAreCards.add(effectHasCard);
            		durationEffectsAreCards.add(false);
            	}
            } else if (thisCard.equals(Cards.archive)) {
            	if(player.archive.size() > 0) {
            		durationEffects.add(cardOrEffect);
            		durationEffects.add(player.archive.get(archiveNum++));
            		durationEffectsAreCards.add(effectHasCard);
            		durationEffectsAreCards.add(false);
            	}
            } else if (thisCard.equals(Cards.champion)) {
            	// champion doesn't have a start of turn effect - just show as in play
            	GameEvent event = new GameEvent(GameEvent.EventType.CardInPlay, context);
                event.card = thisCard;
                event.newCard = effectHasCard;
                broadcastEvent(event);
            } else if (thisCard.equals(Cards.crypt)) {
            	if(player.crypt.size() > 0) {
            		durationEffects.add(cardOrEffect);
            		durationEffects.add(player.crypt.get(cryptNum++));
            		durationEffectsAreCards.add(effectHasCard);
            		durationEffectsAreCards.add(false);
            	}
            } else if (thisCard.equals(Cards.ghost)) {
            	//handled below
            } else if(thisCard.equals(Cards.cargoShip)) {
            	if(player.cargoShip.size() > 0) {
            		durationEffects.add(cardOrEffect);
            		durationEffects.add(player.cargoShip.remove(0));
            		durationEffectsAreCards.add(effectHasCard);
            		durationEffectsAreCards.add(false);
        		}
            } else if (thisCard.equals(Cards.research)) {
            	if(player.research.size() > 0) {
            		durationEffects.add(cardOrEffect);
            		durationEffects.add(player.research.remove(0));
            		durationEffectsAreCards.add(effectHasCard);
            		durationEffectsAreCards.add(false);
            	}
            } else if (thisCard.equals(Cards.church)) {
            	if(player.church.size() > 0) {
            		durationEffects.add(cardOrEffect);
            		durationEffects.add(player.church.remove(0));
            		durationEffectsAreCards.add(effectHasCard);
            		durationEffectsAreCards.add(false);
            	}
            } else {
            	durationEffects.add(cardOrEffect);
            	durationEffects.add(Cards.curse); /*dummy*/
            	durationEffectsAreCards.add(effectHasCard);
        		durationEffectsAreCards.add(false);
            }
        }
        for (Card card : player.horseTraders) {
            durationEffects.add(card);
            durationEffects.add(Cards.curse); /*dummy*/
            durationEffectsAreCards.add(true);
    		durationEffectsAreCards.add(false);
        }
        for (Card card : player.prince) {
            allDurationAreSimple = false;
            durationEffects.add(Cards.prince);
            durationEffects.add(card);
            durationEffectsAreCards.add(true);
    		durationEffectsAreCards.add(false);
        }
        for (Card card : player.summon) {
            if (!card.equals(Cards.summon)) {
                allDurationAreSimple = false;
                durationEffects.add(Cards.summon);
                durationEffects.add(card);
                durationEffectsAreCards.add(false);
        		durationEffectsAreCards.add(false);
            }
        }
        while (!player.haven.isEmpty()) {
            durationEffects.add(Cards.haven);
            durationEffects.add(player.haven.remove(0));
            durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
        }
        while (archiveNum < player.archive.size()) {
            durationEffects.add(Cards.archive);
            durationEffects.add(player.archive.get(archiveNum++));
            durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
        }
        while (cryptNum < player.crypt.size()) {
            durationEffects.add(Cards.crypt);
            durationEffects.add(player.crypt.get(cryptNum++));
            durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
        }
        for (Card card : player.ghost) {
            allDurationAreSimple = false;
            durationEffects.add(Cards.ghost);
            durationEffects.add(card);
            durationEffectsAreCards.add(true);
    		durationEffectsAreCards.add(false);
        }
        for (Card card : player.delay) {
            allDurationAreSimple = false;
            durationEffects.add(Cards.delay);
            durationEffects.add(card);
            durationEffectsAreCards.add(false);
            durationEffectsAreCards.add(false);
        }
        for (Card card : player.reap) {
            allDurationAreSimple = false;
            durationEffects.add(Cards.reap);
            durationEffects.add(card);
            durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
        }
        for (Card card : player.wayOfTheTurtle) {
            allDurationAreSimple = false;
            durationEffects.add(Cards.wayOfTheTurtle);
            durationEffects.add(card);
            durationEffectsAreCards.add(false);
            durationEffectsAreCards.add(false);
        }
        while (!player.cargoShip.isEmpty()) {
            durationEffects.add(Cards.cargoShip);
            durationEffects.add(player.cargoShip.remove(0));
            durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
        }
        while (!player.nextTurnBoons.isEmpty()) {
        	Card boon = player.nextTurnBoons.remove(0);
            durationEffects.add(boon);
            durationEffects.add(Cards.curse);
            durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
    		if (!(boon.equals(Cards.theFieldsGift) ||
    				boon.equals(Cards.theForestsGift) ||
    				boon.equals(Cards.theRiversGift) ||
    				boon.equals(Cards.theSeasGift))) {
    			allDurationAreSimple = false;
    		}
        }
        if (hasState(player, Cards.lostInTheWoods)) {
        	durationEffects.add(Cards.lostInTheWoods);
        	durationEffects.add(Cards.curse);
        	durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
    		allDurationAreSimple = false;
        }
        if (hasState(player, Cards.key)) {
        	durationEffects.add(Cards.key);
        	durationEffects.add(Cards.curse);
        	durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
        }
        if (player.hasProject(Cards.barracks)) {
        	durationEffects.add(Cards.barracks);
        	durationEffects.add(Cards.curse);
        	durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
        }
        if (player.hasProject(Cards.cathedral)) {
        	durationEffects.add(Cards.cathedral);
        	durationEffects.add(Cards.curse);
        	durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
    		allDurationAreSimple = false;
        }
        if (player.hasProject(Cards.cityGate)) {
        	durationEffects.add(Cards.cityGate);
        	durationEffects.add(Cards.curse);
        	durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
    		allDurationAreSimple = false;
        }
        if (player.hasProject(Cards.cropRotation)) {
        	durationEffects.add(Cards.cropRotation);
        	durationEffects.add(Cards.curse);
        	durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
    		allDurationAreSimple = false;
        }
        if (player.hasProject(Cards.fair)) {
        	durationEffects.add(Cards.fair);
        	durationEffects.add(Cards.curse);
        	durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
        }
        if (player.hasProject(Cards.piazza)) {
        	durationEffects.add(Cards.piazza);
        	durationEffects.add(Cards.curse);
        	durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
    		allDurationAreSimple = false;
        }
        if (player.hasProject(Cards.silos)) {
        	durationEffects.add(Cards.silos);
        	durationEffects.add(Cards.curse);
        	durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
    		allDurationAreSimple = false;
        }
        if (player.hasProject(Cards.sinisterPlot)) {
        	durationEffects.add(Cards.sinisterPlot);
        	durationEffects.add(Cards.curse);
        	durationEffectsAreCards.add(false);
    		durationEffectsAreCards.add(false);
    		allDurationAreSimple = false;
        }
        int numOptionalItems = 0;
        ArrayList<Card> callableCards = new ArrayList<Card>();
        for (Card c : player.tavern) {
        	if (c.isCallableWhenTurnStarts()) {
        		callableCards.add((Card) c);
        	}
        }
        if (!callableCards.isEmpty()) {
         	Collections.sort(callableCards, new Util.CardCostComparator());
 	        for (Card c : callableCards) {
 	        	if (c.equals(Cards.guide)
 	        		|| c.equals(Cards.ratcatcher)
 	        		|| c.equals(Cards.transmogrify)) {
 	        		allDurationAreSimple = false;
 	        	}
 	        }
        }
        if (!allDurationAreSimple) {
        	// Add cards callable at start of turn
        	for (Card c : callableCards) {
 	        	durationEffects.add(c);
 	        	durationEffects.add(Cards.curse);
 	        	durationEffectsAreCards.add(false);
 	    		durationEffectsAreCards.add(false);
 	        	numOptionalItems += 2;
 	        }
        }
        
        while (durationEffects.size() > numOptionalItems) {
        	int selection=0;
            if(allDurationAreSimple) {
            	selection=0;
            } else {
            	selection = 2*player.controlPlayer.duration_cardToPlay(context, durationEffects.toArray(new Object[durationEffects.size()]));
            }
            Card card = (Card) durationEffects.get(selection);
            boolean isRealCard = durationEffectsAreCards.get(selection);
            if (card == null) {
                Util.log("ERROR: duration_cardToPlay returned " + selection);
                selection=0;
                card = (Card) durationEffects.get(selection);
            }
            Card card2 = null;
            if (durationEffects.get(selection+1) instanceof Card) {
            	card2 = (Card) durationEffects.get(selection+1);
            }
            ArrayList<Card> setAsideCards = null;
            if (durationEffects.get(selection+1) instanceof ArrayList<?>) {
            	setAsideCards = (ArrayList<Card>) durationEffects.get(selection+1);
            }
            if (card2 == null) {
                Util.log("ERROR: duration_cardToPlay returned " + selection);
                card2 = card;
            }

            durationEffects.remove(selection+1);
            durationEffects.remove(selection);
            durationEffectsAreCards.remove(selection+1);
            durationEffectsAreCards.remove(selection);
            
            if(card.equals(Cards.prince)) {
                player.prince.remove(card2);
                card2.play(this, context, false, true, false);
            } else if(card.equals(Cards.summon)) {
                player.summon.remove(card2);
                card2.play(this, context, false);
            } else if(card.equals(Cards.delay)) {
                player.delay.remove(card2);
                card2.play(this, context, false);
            } else if(card.equals(Cards.reap)) {
                player.reap.remove(card2);
                card2.play(this, context, false);
            } else if(card.equals(Cards.wayOfTheTurtle)) {
                player.wayOfTheTurtle.remove(card2);
                card2.play(this, context, false);
            } else if(card.equals(Cards.horseTraders)) {
            	//BUG: this doesn't let you call estates inheriting horse trader differently
                Card horseTrader = player.horseTraders.remove(0);
                player.hand.add(horseTrader);
                drawToHand(context, horseTrader, 1, new PlayContext());
            } else if(card.is(Type.Boon)) {
            	recieveBoonAndDiscard(context, card, Cards.blessedVillage);
            } else if(card.equals(Cards.cathedral) || card.equals(Cards.cityGate) ||
            		card.equals(Cards.cropRotation) || card.equals(Cards.lostInTheWoods) ||
            		card.equals(Cards.key) || card.equals(Cards.piazza) ||
            		card.equals(Cards.silos) || card.equals(Cards.sinisterPlot)) {
                card.followInstructions(this, context, card, context.getPlayer(), false);
            } else if (card.equals(Cards.barracks)) {
            	context.addActions(1, Cards.barracks);
            } else if (card.equals(Cards.fair)) {
            	context.buys += 1;
            } else if(card.is(Type.Duration, player)) {
            	if(card.equals(Cards.haven) || card.equals(Cards.cargoShip)) {
                    player.hand.add(card2);
                }
                if(card.equals(Cards.gear) || card.equals(Cards.research)
                		|| card.equals(Cards.church)) {
                	for (Card c : setAsideCards)
                		player.hand.add(c);
                }
                if (card.equals(Cards.archive)) {
                	CardImplEmpires.archiveSelect(this, context, player, setAsideCards);
                }
                if (card.equals(Cards.cobbler)) {
                	Card cardToGain = player.controlPlayer.cobbler_cardToObtain(context);
                    if (cardToGain != null) {
                        if (cardToGain.getCost(context) <= 4 && cardToGain.getDebtCost(context) == 0 && !cardToGain.costPotion()) {
                        	player.gainNewCard(cardToGain, card, context);
                        } else {
                        	Util.playerError(player, "Cobbler error, invalid card to gain, ignoring");
                        }
                    }
                }
                if (card.equals(Cards.crypt)) {
                	CardImplNocturne.cryptSelect(this, context, player, setAsideCards);
                }
                if (card.equals(Cards.secretCave)) {
                	context.addCoins(3, card, new PlayContext());
                }
                if (card.equals(Cards.barge)) {
                    PlayContext drawContext = new PlayContext();
                	for (int i = 0; i < 3; ++i) {
                    	context.game.drawToHand(context, card, 3 - i, drawContext);
                    }
        			context.buys += 1;
                }
                if (card.equals(Cards.villageGreen)) {
                    context.game.drawToHand(context, card, 1, new PlayContext());
                    context.addActions(2);
                }
                
                Card thisCard = card;
                
                GameEvent event = new GameEvent(GameEvent.EventType.PlayingDurationAction, context);
                event.card = card;
                event.newCard = isRealCard;
                broadcastEvent(event);

                context.addActions(thisCard.getAddActionsNextTurn(), thisCard);
                context.addCoins(thisCard.getAddGoldNextTurn());
                context.buys += thisCard.getAddBuysNextTurn();
                int addCardsNextTurn = thisCard.getAddCardsNextTurn();

                /* addCardsNextTurn are displayed like addCards but sometimes the text differs */
                if (thisCard.getKind() == Cards.Kind.Tactician) {
                    context.addActions(1, Cards.tactician);
                    context.buys += 1;
                    addCardsNextTurn = 5;
                }
                if (thisCard.getKind() == Cards.Kind.Dungeon) {
                    addCardsNextTurn = 2;
                }
                if (thisCard.getKind() == Cards.Kind.Hireling) {
                    addCardsNextTurn = 1;
                }

                PlayContext drawContext = new PlayContext();
                for (int i = 0; i < addCardsNextTurn; i++) {
                    drawToHand(context, thisCard, addCardsNextTurn - i, true, drawContext);
                }
                
                if (thisCard.getKind() == Cards.Kind.Amulet) {
                	((CardImplAdventures) thisCard).amuletEffect(context.game, context, player, new PlayContext());
                }
                if (thisCard.getKind() == Cards.Kind.Dungeon) {
                	((CardImpl) thisCard).discardMultiple(context, player, 2);
                }    
                if (thisCard.getKind() == Cards.Kind.Ghost) {
                	player.ghost.remove(card2);
                    card2.play(this, context, false);
                    card2.play(this, context, false, true, true);
                }
                if (thisCard.getKind() == Cards.Kind.Captain) {
                	((CardImplPromo) thisCard).captainEffect(context.game, context, player);
                }
                
                if (card.equals(Cards.church)) {
                	CardList hand = player.getHand();
                    if (hand.size() == 0) {
                        return;
                    }

                    Card cardToTrash = player.controlPlayer.church_cardToTrash(context);
                    if (cardToTrash != null) {
	                    if (!player.hand.contains(cardToTrash)) {
	                        Util.playerError(player, "Church card to trash error, trashing none.");
	                    } else {
	                    	player.trashFromHand(cardToTrash, card, context);
	                    }
                    }
                }
                if (card.equals(Cards.mastermind)) {
                	ArrayList<Card> actionCards = new ArrayList<Card>();
                    for (Card c : player.hand) {
                        if (c.is(Type.Action, player)) {
                            actionCards.add(c);
                        }
                    }
                    if (actionCards.isEmpty()) return;
                    Card cardToPlay = player.controlPlayer.mastermind_cardToPlay(context);
                    if (cardToPlay == null) return;
                    if (!actionCards.contains(cardToPlay)) {
                    	Util.playerError(player, "Mastermind card to play error, playing none.");
                    }
                    for (int i = 0; i < 3; ++i) {
                        cardToPlay.play(this, context, i == 0, i > 0, i > 0);
                    }
                    if (cardToPlay.is(Type.Duration, player)) {
                    	((CardImpl)card).multiplyCard(cardToPlay);
                    }
                }
            } else if(card.isCallableWhenTurnStarts()) {
            	numOptionalItems -= 2;
            	card.callAtStartOfTurn(context);
            } else {
                Util.log("ERROR: nextTurnCards contains " + card);
            }
        }
                
        //Clean up empty Archive lists
        Iterator<ArrayList<Card>> it = player.archive.iterator();
        while (it.hasNext()) {
        	if (it.next().isEmpty()) 
        		it.remove();
        }
        //Clean up empty Crypt lists
        it = player.crypt.iterator();
        while (it.hasNext()) {
        	if (it.next().isEmpty()) 
        		it.remove();
        }
        
        //TODO: integrate this into the main action selection UI if possible to make it more seamless
        //check for start-of-turn callable cards
        callableCards = new ArrayList<Card>();
        Card toCall = null;
        for (Card c : player.tavern) {
        	if (c.isCallableWhenTurnStarts()) {
        		callableCards.add(c);
        	}
        }
        if (!callableCards.isEmpty()) {
        	Collections.sort(callableCards, new Util.CardCostComparator());
	        do {
	        	toCall = null;
	        	// we want null entry at the end for None
	        	Card[] cardsAsArray = callableCards.toArray(new Card[callableCards.size() + 1]);
	        	//ask player which card to call
	        	toCall = player.controlPlayer.call_whenTurnStartCardToCall(context, cardsAsArray);
	        	if (toCall != null && callableCards.contains(toCall)) {
	        		toCall = callableCards.remove(callableCards.indexOf(toCall));
	        		toCall.callAtStartOfTurn(context);
	        	}
		        // loop while we still have cards to call
	        } while (toCall != null && !callableCards.isEmpty());
        }
    }

    protected void playerBeginBuy(Player player, MoveContext context) {
    	context.totalCardsBoughtInMostRecentBuyPhase = 0;
    	if (cardInGame(Cards.arena)) {
    		arena(player, context);
    	}
    	if (context.game.hasState(player, Cards.deluded)) {
    		context.game.returnState(context, Cards.deluded);
    		context.canBuyActions = false;
    	}
    	if (context.game.hasState(player, Cards.envious)) {
    		context.game.returnState(context, Cards.envious);
    		context.envious = true;
    	}
    	if (context.game.hasState(player, Cards.treasureChest)) {
    		player.gainNewCard(Cards.gold, Cards.treasureChest, context);
    	}
    }
    
    private void arena(Player player, MoveContext context) {
    	boolean hasAction = false;
		for (Card c : player.getHand()) {
			if (c.is(Type.Action, player)) {
				hasAction = true;
				break;
			}
		}
		if (!hasAction) return;
		Card toDiscard = player.controlPlayer.arena_cardToDiscard(context);
		if (toDiscard != null && (!player.getHand().contains(toDiscard) || toDiscard.is(Type.Action, player))) {
			Util.playerError(player, "Arena - invalid card specified, ignoring.");
		}
		if (toDiscard == null) return;
		player.discard(player.getHand().remove(player.getHand().indexOf(toDiscard)), Cards.arena, context);
		int tokensToTake = Math.min(getPileVpTokens(Cards.arena), 2);
		removePileVpTokens(Cards.arena, tokensToTake, context);
		player.addVictoryTokens(context, tokensToTake, Cards.arena);
    }
    
	private static void printStats(HashMap<String, Double> wins, int gameCount, String gameType) {
        if (!test || gameCount == 1) {
            return;
        }

        double totalGameCount = 0;
        Iterator<Entry<String, Double>> it = wins.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Double> e = it.next();
            totalGameCount += e.getValue();
        }
        gameCount = (int) totalGameCount;

        StringBuilder sb = new StringBuilder();

        String s = gameType + ":";

        String start = "" + gameCount;

        if (gameCount > 1) {
            s = start + (gameType.equals("Types") ? " types " : " games ") + s;
        }

        if (!debug) {
            while (s.length() < 30) {
                s += " ";
            }
        }
        sb.append(s);
        String winner = null;
        double high = 0.0;
        Iterator<String> keyIter = wins.keySet().iterator();

        while (keyIter.hasNext()) {
            String className = keyIter.next();
            double num = wins.get(className);
            double val = Math.round((num * 100 / gameCount));
            if (val > high) {
                high = val;
                winner = className;
            }
        }

        keyIter = wins.keySet().iterator();
        while (keyIter.hasNext()) {
            String className = keyIter.next();
            double num = wins.get(className);

            String name;
            Player pclass = playerCache.get(className);
            if (pclass != null) {
                name = pclass.getPlayerName();
            } else {
                name = className;
            }

            double val = Math.round((num * 100 / gameCount));
            String numStr = "" + (int) val;
            while (numStr.length() < 3) {
                numStr += " ";
            }

            sb.append(" ");
            if (className.equals(winner)) {
                sb.append("*");
            } else {
                sb.append(" ");
            }
            sb.append(name + " " + numStr + "%");

            winStats.put(name, Integer.parseInt(numStr.trim()));
        }

        Util.log(sb.toString());
    }

    private static void printGameTypeStats() {
        for (int i=0; i < gameTypeStats.size(); i++) {
            GameStats stats = gameTypeStats.get(i);
            StringBuilder sb = new StringBuilder();
            sb.append(stats.gameType);
            if (stats.gameType.toString().length() < 8) {
                sb.append("\t");
            }
            if (stats.gameType.toString().length() < 16) {
                sb.append("\t");
            }
            sb.append("\tvp=" + stats.aveVictoryPoints + "\t\tcards=" + stats.aveNumCards + "\tturns=" + stats.aveTurns + "\t"
                      + Util.cardArrayToString(stats.cards));
            Util.log(sb.toString());
        }
    }

    public int calculateLead(Player player) {
        int playerVictoryPoints = -999;
        int otherHigh = -999;

        int[] vps = calculateVps();
        for (int i = 0; i < vps.length; i++) {
            if (players[i].equals(player)) {
                playerVictoryPoints = vps[i];
            } else if (vps[i] > otherHigh) {
                otherHigh = vps[i];
            }
        }

        return playerVictoryPoints - otherHigh;
    }

    private static int[] calculateVps() {
        int[] vps = new int[numPlayers];
        for (int i = 0; i < players.length; i++) {
            vps[i] = players[i].getVPs();
        }

        return vps;
    }

    protected static void processArgs(String[] args) throws ExitException {
        // dont remove tabs in following to keep easy upstream-mergeability
        processNewGameArgs(args);
        processUserPrefArgs(args);
    }

    protected static void processNewGameArgs(String[] args) throws ExitException {
    	
        numPlayers = 0;
        cardsSpecifiedAtLaunch = null;
        overallWins.clear();
        GAME_TYPE_WINS.clear();
        gameTypeStats.clear();
        playerClassesAndJars.clear();
        playerCache.clear();
        numRandomEvents = 0;
        numRandomProjects = 0;
        numRandomLandmarks = 0;
        numRandomWays = 0;
        splitMaxSidewaysCards = false;
        randomExpansions = null;
        randomExcludedExpansions = null;
        keepSidewaysCardsWithExpansion = false;
        randomExpansionLimit = 0;
        randomExpansionAllocation = ExpansionAllocation.Random;
        vpCounter = false;

        String gameCountArg = "-count";
        String debugArg = "-debug";
        String showEventsArg = "-showevents";
        String gameTypeArg = "-type";
        String numRandomEventsArg = "-eventcards";
        String numRandomProjectsArg = "-projectcards";
        String numRandomLandmarksArg = "-landmarkcards";
        String numRandomWaysArg = "-waycards";
        String randomExcludesArg = "-randomexcludes";
        String splitMaxSidewaysCardsArg = "-splitmaxsidewayscards";
        String keepSidewaysCardsWithExpansionArg = "-sidewayscardswithexpansion";
        String limitRandomExpansionsArg = "-limitrandom";
        String randomAllocationArg = "-randomallocation";
        String gameTypeStatsArg = "-test";
        String ignorePlayerErrorsArg = "-ignore";
        String showPlayersArg = "-showplayers";
        String vpCounterArg = "-vpcounter";
        String siteArg = "-site=";
        String cardArg = "-cards=";

        for (String arg : args) {
            if (arg == null) {
                continue;
            }

            if (arg.startsWith("#")) {
                continue;
            }

            if (arg.startsWith("-")) {
                if (arg.toLowerCase().equals(debugArg)) {
                    debug = true;
                    if (showEvents.isEmpty()) {
                        for (GameEvent.EventType eventType : GameEvent.EventType.values()) {
                            showEvents.add(eventType);
                        }
                    }
                } else if (arg.toLowerCase().startsWith(showEventsArg)) {
                    String showEventsString = arg.substring(showEventsArg.length() + 1);
                    for (String event : showEventsString.split(",")) {
                        showEvents.add(GameEvent.EventType.valueOf(event));
                    }
                } else if (arg.toLowerCase().startsWith(showPlayersArg)) {
                    String showPlayersString = arg.substring(showPlayersArg.length() + 1);
                    for (String player : showPlayersString.split(",")) {
                        showPlayers.add(player);
                    }
                } else if (arg.toLowerCase().startsWith(vpCounterArg)) {
                    vpCounter = true;
                } else if (arg.toLowerCase().startsWith(ignorePlayerErrorsArg)) {
                    if (arg.trim().toLowerCase().equals(ignorePlayerErrorsArg)) {
                        ignoreAllPlayerErrors = true;
                    } else {
                        ignoreSomePlayerErrors = true;

                        try {
                            String player = arg.substring(ignorePlayerErrorsArg.length()).trim();
                            ignoreList.add(player);
                        } catch (Exception e) {
                            Util.log(e);
                            throw new ExitException();
                        }
                    }
                } else if (arg.toLowerCase().equals(gameTypeStatsArg)) {
                    test = true;
                } else if (arg.toLowerCase().startsWith(gameCountArg)) {
                    try {
                        numGames = Integer.parseInt(arg.substring(gameCountArg.length()));
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }
                } else if (arg.toLowerCase().startsWith(cardArg)) {
                    try {
                        cardsSpecifiedAtLaunch = arg.substring(cardArg.length()).replace("Will-o'-Wisp", Cards.willOWisp.getSafeName()).split("-");
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }
                } else if (arg.toLowerCase().startsWith(siteArg)) {
                    try {
                        // UI.downloadSite =
                        // arg.substring(siteArg.length());
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }
                } else if (arg.toLowerCase().startsWith(numRandomEventsArg)) {
                    try {
                        int num = Integer.parseInt(arg.substring(numRandomEventsArg.length()));
                        if (num != 0) {
                        	numRandomEvents = num;
                        }
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }
                } else if (arg.toLowerCase().startsWith(numRandomProjectsArg)) {
                    try {
                        int num = Integer.parseInt(arg.substring(numRandomProjectsArg.length()));
                        if (num != 0) {
                        	numRandomProjects = num;
                        }
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }
                } else if (arg.toLowerCase().startsWith(numRandomLandmarksArg)) {
                    try {
                        int num = Integer.parseInt(arg.substring(numRandomLandmarksArg.length()));
                        if (num != 0) {
                        	numRandomLandmarks = num;
                        }
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }
                } else if (arg.toLowerCase().startsWith(numRandomWaysArg)) {
                    try {
                        int num = Integer.parseInt(arg.substring(numRandomWaysArg.length()));
                        if (num != 0) {
                        	numRandomWays = num;
                        }
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }
                } else if (arg.toLowerCase().equals(splitMaxSidewaysCardsArg)) {
                    splitMaxSidewaysCards = true;
                } else if (arg.toLowerCase().equals(keepSidewaysCardsWithExpansionArg)) {
                    keepSidewaysCardsWithExpansion = true;
                } else if (arg.toLowerCase().startsWith(limitRandomExpansionsArg)) {
                    try {
                    	randomExpansionLimit = Integer.parseInt(arg.substring(limitRandomExpansionsArg.length()));
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }
                } else if (arg.toLowerCase().startsWith(randomAllocationArg)) {
                    try {
                    	randomExpansionAllocation = ExpansionAllocation.valueOf(arg.substring(randomAllocationArg.length()));
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }
                } else if (arg.toLowerCase().startsWith(gameTypeArg)) {
                    try {
                        gameTypeStr = arg.substring(gameTypeArg.length());
                        String[] parts = gameTypeStr.split("-");
                        if (parts.length > 0) {
                        	gameTypeStr = parts[0];
                        	randomExpansions = new ArrayList<Expansion>();
                    		for (int i = 1; i < parts.length; ++i) {
                        		randomExpansions.add(Expansion.valueOf(parts[i]));
                        	}
                        }
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }

                } else if (arg.toLowerCase().startsWith(randomExcludesArg)) {
                    try {
                        String exclusions = arg.substring(randomExcludesArg.length());
                        String[] parts = exclusions.split("-");
                        if (parts.length > 0) {
                        	randomExcludedExpansions = new ArrayList<Expansion>();
                    		for (int i = 1; i < parts.length; ++i) {
                        		randomExcludedExpansions.add(Expansion.valueOf(parts[i]));
                        	}
                        }
                    } catch (Exception e) {
                        Util.log(e);
                        throw new ExitException();
                    }
                }
            } else {
                String options = "";
                String name = null;
                int starIndex = arg.indexOf("*");
                if (starIndex != -1) {
                    name = arg.substring(starIndex + 1);
                    arg = arg.substring(0, starIndex);
                }
                if (arg.endsWith(QUICK_PLAY)) {
                    arg = arg.substring(0, arg.length() - QUICK_PLAY.length());
                    options += "q";
                }
                int atIndex = arg.indexOf("@");
                String className = arg;
                String jar = null;
                if (atIndex != -1) {
                    className = className.substring(0, atIndex);
                    jar = arg.substring(atIndex + 1);
                }
                playerClassesAndJars.add(new String[] { className, jar, name, options });
            }
        }

        numPlayers = playerClassesAndJars.size();

        if (numPlayers < 1 || numPlayers > 6 || showUsage) {
            Util.log("Usage: [-debug][-ignore(playername)][-count(# of Games)][-type(Game type)] class1 class2 [class3] [class4]");
            throw new ExitException();
        }

        if (gameTypeStr == null) {
            if (debug) {
                gameTypeStr = "FirstGame";
            }
        }

        if (numGames == -1) {
            numGames = 1;
        }

    }

    public static void processUserPrefArgs(String[] args) throws ExitException {
        quickPlay = false;
        sortCards = false;
        maskPlayerNames = false;
        actionChains = false;
        suppressRedundantReactions = false;
        chanceForPlatColony = -1;
        chanceForShelters = -1;
        equalStartHands = false;
        blackMarketOnlyCardsFromUsedExpansions = false;
        blackMarketSplitPileOptions = BlackMarketSplitPileOptions.NONE;
        errataMasqueradeAlwaysAffects = false;
        errataMineForced = false;
        errataPossession = PossessionPossessorTokens.DEBT;
        errataThroneRoomForced = false;
        errataShuffleDeckEmptyOnly = false;
        startGuildsCoinTokens = false; //only for testing
        lessProvinces = false; //only for testing
        noCards = false;
        godMode = false; //only for testing
        controlAi = false; //only for testing
        disableAi = false;

        String quickPlayArg = "-quickplay";
        String maskPlayerNamesArg = "-masknames";
        String sortCardsArg = "-sortcards";
        String actionChainsArg = "-actionchains";
        String suppressRedundantReactionsArg = "-suppressredundantreactions";
        String platColonyArg = "-platcolony";
        String useSheltersArg = "-useshelters";
        String blackMarketCountArg = "-blackmarketcount";
        String bmSplitPileArg = "-blackmarketsplitpiles-";
        String bmOnlyUsedExpArg = "-blackmarketonlycardsfromusedexpansions";
        String equalStartHandsArg = "-equalstarthands";
        String errataThroneRoomForcedArg = "-erratathroneroomforced";
        String errataMasqueradeAlwaysAffectsArg = "-erratamasqueradealwaysaffects";
        String errataMineForcedArg = "-erratamineforced";
        String errataMoneylenderForcedArg = "-erratamoneylenderforced";
        String errataPossessionArg = "-erratapossessortakestokens-";
        String errataShuffleDeckEmptyOnlyArg = "-erratashuffledeckemptyonly";
        String startGuildsCoinTokensArg = "-startguildscointokens"; //only for testing
        String lessProvincesArg = "-lessprovinces"; //only for testing
        String noCardsArg = "-nocards";
        String godModeArg = "-godmode";
        String disableAiArg = "-disableai";
        String controlAiArg = "-controlai";

        for (String arg : args) {
            if (arg == null) {
                continue;
            }

            if (arg.startsWith("#")) {
                continue;
            }

            if (arg.startsWith("-")) {
                if (arg.toLowerCase().equals(quickPlayArg)) {
                    quickPlay = true;
                } else if (arg.toLowerCase().equals(sortCardsArg)) {
                    sortCards = true;
                } else if (arg.toLowerCase().equals(maskPlayerNamesArg)) {
                    maskPlayerNames = true;
                } else if (arg.toLowerCase().equals(actionChainsArg)) {
                    actionChains = true;
                } else if (arg.toLowerCase().equals(suppressRedundantReactionsArg)) {
                    suppressRedundantReactions = true;
                } else if (arg.toLowerCase().startsWith(platColonyArg)) {
                	chanceForPlatColony = Integer.parseInt(arg.toLowerCase().substring(platColonyArg.length())) / 100.0;
                } else if (arg.toLowerCase().startsWith(useSheltersArg)) {
                	chanceForShelters = Integer.parseInt(arg.toLowerCase().substring(useSheltersArg.length())) / 100.0;
                } else if (arg.toLowerCase().startsWith(blackMarketCountArg)) {
                    blackMarketCount = Integer.parseInt(arg.toLowerCase().substring(blackMarketCountArg.length()));
                } else if (arg.toLowerCase().startsWith(bmSplitPileArg)) {
                    blackMarketSplitPileOptions = BlackMarketSplitPileOptions.valueOf(arg.substring(bmSplitPileArg.length()).toUpperCase());
                } else if (arg.toLowerCase().startsWith(bmOnlyUsedExpArg)) {
                    blackMarketOnlyCardsFromUsedExpansions = true;
                } else if (arg.toLowerCase().equals(equalStartHandsArg)) {
                    equalStartHands = true;
                } else if (arg.toLowerCase().equals(startGuildsCoinTokensArg)) {
                    startGuildsCoinTokens = true; //only for testing
                } else if (arg.toLowerCase().equals(noCardsArg)) {
                    noCards = true; //only for testing
                } else if (arg.toLowerCase().equals(godModeArg)) {
                    godMode = true; //only for testing
                } else if (arg.toLowerCase().equals(disableAiArg)) {
                    disableAi = true; //only for testing
                } else if (arg.toLowerCase().equals(controlAiArg)) {
                    controlAi = true; //only for testing
                } else if (arg.toLowerCase().equals(lessProvincesArg)) {
                    lessProvinces = true; //only for testing
                } else if (arg.toLowerCase().equals(errataMasqueradeAlwaysAffectsArg)) {
                	errataMasqueradeAlwaysAffects = true;
                } else if (arg.toLowerCase().equals(errataMineForcedArg)) {
                	errataMineForced = true;
                } else if (arg.toLowerCase().equals(errataMoneylenderForcedArg)) {
                	errataMoneylenderForced = true;
                } else if (arg.toLowerCase().startsWith(errataPossessionArg)) {
                	errataPossession = PossessionPossessorTokens.valueOf(arg.substring(errataPossessionArg.length()).toUpperCase());
                } else if (arg.toLowerCase().equals(errataThroneRoomForcedArg)) {
                	errataThroneRoomForced = true;
                } else if (arg.toLowerCase().equals(errataShuffleDeckEmptyOnlyArg)) {
                	errataShuffleDeckEmptyOnly = true;
                }
            }
        }
    }

    public boolean isValidAction(MoveContext context, Card action) {
        if (action == null) {
            return false;
        }

        if (!(action.is(Type.Action, context.player))) {
            return false;
        }

        for (Card card : context.getPlayer().hand) {
            if (action.equals(card)) {
                return true;
            }
        }

        return false;
    }
    
    public boolean isValidNightCard(MoveContext context, Card nightCard) {
        if (nightCard == null) {
            return false;
        }

        if (!(nightCard.is(Type.Night, context.player))) {
            return false;
        }

        for (Card card : context.getPlayer().hand) {
            if (nightCard.equals(card)) {
                return true;
            }
        }

        return false;
    }

    public boolean isValidBuy(MoveContext context, Card card) {
        return isValidBuy(context, card, context.getCoinAvailableForBuy());
    }

    public boolean isValidBuy(MoveContext context, Card card, int gold) {
        if (card == null) {
            return true;
        }

        // TODO: Temp hack to prevent AI from buying possession, even though human player can, since it only half works
        //       (AI will make decisions while possessed, but will try to make "good" ones)
        //        if(card.equals(Cards.possession) && context != null && context.getPlayer() != null && context.getPlayer().isAi()) {
        //            return false;
        //        }

        CardPile thePile = getPile(card);
        if (thePile == null) {
            return false;
        }
        if (context.getPlayer().getDebtTokenCount() > 0) {
        	return false;
        }
        
        if (card.is(Type.Project) && context.player.getProjectsBought().size() >= context.game.numProjectCubes) {
        	return false;
        }
        
        if (!context.canBuyActions && card.is(Type.Action)) {
        	return false;
        }
        if (!context.canBuyCards && !(card.is(Type.Event) || card.is(Type.Project))) {
        	return false;
        }
        if (context.blackMarketBuyPhase) {
            if (thePile.isBlackMarket() == false) {
                return false;
            }
            if (Cards.isSupplyCard(card)) {
                return false;
            }
        }
        else if ((card.is(Type.Event) || card.is(Type.Project)) && context.phase != TurnPhase.Buy) {
        	return false;
        }
        else if (!(card.is(Type.Event) || card.is(Type.Project))) {
            if (thePile.isSupply() == false) {
                return false;
            }
            if (!Cards.isSupplyCard(card)) {
                return false;
            }
        }

        if (isPileEmpty(card)) {
            return false;
        }

        if (context.cantBuy.contains(card)) {
            return false;
        }

        if (card.equals(Cards.grandMarket) && (context.countCardsInPlay(Cards.copper) > 0)) {
            return false;
        }

        int cost = card.getCost(context);

        if (card.equals(Cards.animalFair) && canPayAnimalFairByTrashing(context)) {
        	return true;
        }
        
        int potions = context.getPotions();
        if (cost <= gold && (!card.costPotion() || potions > 0)) {
            return true;
        }

        return false;
    }

    Card playBuy(MoveContext context, Card buy) {
        Player player = context.getPlayer();
        
        // expend buys
        if (!context.blackMarketBuyPhase) {
            context.buys--;
        }
        
        // pay cost
        if (buy.equals(Cards.animalFair) &&
        		canPayAnimalFairByTrashing(context) &&
        		(context.getCoins() < Cards.animalFair.getCost(context) ||
        		!player.controlPlayer.animalFair_shouldPayCost(context))) {
        	// handle special cost for Animal Fair
        	Card toTrash = player.controlPlayer.animalFair_actionToTrash(context);
        	if (toTrash == null || !toTrash.is(Type.Action) || !player.getHand().contains(toTrash)) {
        		Util.playerError(player, "Animal Farm trash error, trashing first Action card.");
                for (Card c : player.getHand()) {
                	if (c.is(Type.Action)) {
                		toTrash = c;
                		break;
                	}
                }
        	}
        	player.trashFromHand(toTrash, Cards.animalFair, context);
        } else {
        	// pay normal cost
        	context.spendCoins(buy.getCost(context));
            if (buy.costPotion()) {
                context.potions--;
            } else if (buy.getDebtCost(context) > 0) {
            	int debtCost = buy.getDebtCost(context);
            	context.getPlayer().gainDebtTokens(debtCost);
            	GameEvent event = new GameEvent(GameEvent.EventType.DebtTokensObtained, context);
            	event.setAmount(debtCost);
                context.game.broadcastEvent(event);
            }

        }
                
        // On-buy effects

        // Start inheriting newly gained estate
        Card abilityCard = buy;

        // Achievement check...
        if(!player.achievementSingleCardFailed) {
            if (Cards.isKingdomCard(buy)) {
                if(player.achievementSingleCardFirstKingdomCardBought == null) {
                    player.achievementSingleCardFirstKingdomCardBought = buy;
                }
                else {
                    if(!player.achievementSingleCardFirstKingdomCardBought.equals(buy)) {
                        player.achievementSingleCardFailed = true;
                        player.achievementSingleCardFirstKingdomCardBought = null;
                    }
                }
            }
        }

        int embargos = getEmbargos(buy);
        for (int i = 0; i < embargos; i++) {
            player.gainNewCard(Cards.curse, Cards.embargo, context);
        }
        
        // Tax Debt tokens
        int numDebtTokensOnPile = getPileDebtTokens(buy); 
        if (numDebtTokensOnPile > 0) {
        	removePileDebtTokens(buy, numDebtTokensOnPile, context);
        	context.getPlayer().controlPlayer.gainDebtTokens(numDebtTokensOnPile);
        	GameEvent event = new GameEvent(GameEvent.EventType.DebtTokensObtained, context);
        	event.setAmount(numDebtTokensOnPile);
            context.game.broadcastEvent(event);
        }

        /* GameEvent.Type.BuyingCard must be after overpaying! */
        
        // cost adjusted based on any cards played or card being bought
        int cost = buy.getCost(context);
        
        if (buy != null) {
            GameEvent event = new GameEvent(GameEvent.EventType.BuyingCard, (MoveContext) context);
            event.card = buy;
            event.newCard = true;
            broadcastEvent(event);
        }
        
        // If card can be overpaid for, do so now
        if (abilityCard.isOverpay(player))
        {
            int coinOverpay = player.amountToOverpay(context, buy);
            coinOverpay = Math.max(0,  coinOverpay);
            coinOverpay = Math.min(coinOverpay, context.getCoinAvailableForBuy());
            context.overpayAmount = coinOverpay;
            
            context.spendCoins(context.overpayAmount);

            if (context.potions > 0)
            {
            	int potionOverpay = player.overpayByPotions(context, context.potions);
            	potionOverpay = Math.max(0, potionOverpay);
            	potionOverpay = Math.min(potionOverpay, context.getPotions());
                context.overpayPotions = potionOverpay;
                context.potions -= context.overpayPotions;
            }

            if (context.overpayAmount > 0 || context.overpayPotions > 0)
            {
            	GameEvent event = new GameEvent(GameEvent.EventType.OverpayForCard, (MoveContext) context);
                event.card = buy;
                event.newCard = true;
                broadcastEvent(event);
            }
        }
        else
        {
            context.overpayAmount  = 0;
            context.overpayPotions = 0;
        }
        
        if (buy.is(Type.Project)) {
        	context.cantBuy.add(buy); //once per game
        	player.projectsBought.add(buy);
        } else if (buy.is(Type.Event)) {
        	if (!player.eventsBought.containsKey(buy))
        		player.eventsBought.put(buy, 0);
        	player.eventsBought.put(buy, player.eventsBought.get(buy) + 1);
        }
                
        buy.isBuying(context);
                
        // On-buy a card effects
        if(!(buy.is(Type.Event) || buy.is(Type.Project))) {
        	// Plan - Trashing Token
        	if (player.getHand().size() > 0 && isPlayerSupplyTokenOnPile(buy, player, PlayerSupplyToken.Trashing)) {
                Card cardToTrash = player.controlPlayer.trashingToken_cardToTrash((MoveContext) context);
                if (cardToTrash != null) {
                	if (!player.getHand().contains(cardToTrash)) {
                		Util.playerError(player, "Trashing token error, invalid card to trash, ignoring.");
                	} else {
                		player.trashFromHand(cardToTrash, Cards.plan, context);
                	}
                }
        	}
        	
	        for (int i=0; i < swampHagAttacks(player); i++) {
	            player.gainNewCard(Cards.curse, Cards.swampHag, context);                    	
	        }
	        
	        if (hauntedWoodsAttacks(player)) {
	            if(player.hand.size() > 0) {
	                Card[] order;
	                if (player.hand.size() == 1)
	                    order = player.hand.toArray();
	                else
	                    order = player.controlPlayer.mandarin_orderCards(context, player.hand.toArray());
	
	                for (int i = order.length - 1; i >= 0; i--) {
	                    Card c = order[i];
	                    player.putOnTopOfDeck(c);
	                    player.hand.remove(c);
	                }                            
	            }
	        }
        }
        
        // On-buy effects from triggers
        if (!buy.costPotion() && buy.getDebtCost(context) == 0 && !(buy.is(Type.Victory, context.player)) && cost < 5 && !(buy.is(Type.Event) || buy.is(Type.Project))) {
            for (int i = 1; i <= context.countCardsInPlay(Cards.talisman); i++) {
                if (buy.equals(getPile(buy).topCard())) {
                    context.getPlayer().gainNewCard(buy, Cards.talisman, context);
                }
            }
        }

        if(!(buy.is(Type.Event) || buy.is(Type.Project))) {
            player.addVictoryTokens(context, context.countGoonsInPlay(), Cards.goons);
        }

        if (buy.is(Type.Victory, context.player)) {
            context.victoryCardsBoughtThisTurn++;
            for (int i = 1; i <= context.countCardsInPlay(Cards.hoard); i++) {
                player.gainNewCard(Cards.gold, Cards.hoard, context);
            }
        }

        // Other on-buy effects from triggers
        if(!(buy.is(Type.Event) || buy.is(Type.Project))) {
        	haggler(context, buy);
        	charmWhenBuy(context, buy);
        	basilicaWhenBuy(context);
        	colonnadeWhenBuy(context, buy);
        	defiledShrineWhenBuy(context, buy);
        }
        
        // start gain - would gain from Trader first and lift card from pile
        Card card = buy;
        if(!(buy.is(Type.Event) || buy.is(Type.Project))) {
            card = takeFromPileCheckGetTop(buy, context);
        }

        // Gain card after buying - event handler will deposit in appropriate location
        if (buy != null && !(buy.is(Type.Event) || buy.is(Type.Project))) {
        	GameEvent gainEvent = new GameEvent(GameEvent.EventType.CardObtained, (MoveContext) context);
            gainEvent.card = (card == null) ? buy : card;
            gainEvent.responsible = (card != null && card != buy) ? Cards.trader : null;
            gainEvent.newCard = true;
            context.game.broadcastEvent(gainEvent);
        }
        
        return card;
    }

    public boolean canPayAnimalFairByTrashing(MoveContext context) {
    	if (context == null || context.getPlayer() == null) return false;
		for (Card c : context.getPlayer().getHand()) {
			if (c.is(Type.Action)) return true;
		}
		return false;
	}

	private void haggler(MoveContext context, Card cardBought) {
        if(!context.game.piles.containsKey(Cards.haggler.getName()))
            return;
        int hagglers = context.countCardsInPlay(Cards.haggler);

        int cost = cardBought.getCost(context);
        int debt = cardBought.getDebtCost(context);
        boolean potion = cardBought.costPotion();
        int potionCost = potion ? 1 : 0;
        List<Card> validCards = new ArrayList<Card>();

        for (int i = 0; i < hagglers; i++) {
            validCards.clear();
            for (Card card : getCardsInGame(GetCardsInGameOptions.TopOfPiles, true)) {
                if (!(card.is(Type.Victory)) && Cards.isSupplyCard(card) && isCardOnTop(card)) {
                    int gainCardCost = card.getCost(context);
                    int gainCardPotionCost = card.costPotion() ? 1 : 0;
                    int gainCardDebt = card.getDebtCost(context);

                    if ((gainCardCost < cost || gainCardDebt < debt || gainCardPotionCost < potionCost) && 
                    		(gainCardCost <= cost && gainCardDebt <= debt && gainCardPotionCost <= potionCost)) {
                        validCards.add(card);
                    }
                }
            }

            if (validCards.size() > 0) {
                Card toGain = context.getPlayer().controlPlayer.haggler_cardToObtain(context, cost, debt, potion);
                if(toGain != null) {
                    if (!validCards.contains(toGain)) {
                        Util.playerError(context.getPlayer(), "Invalid card returned from Haggler, ignoring.");
                    }
                    else {
                        context.getPlayer().gainNewCard(toGain, Cards.haggler, context);
                    }
                }
            }
        }

    }
    
    private void charmWhenBuy(MoveContext context, Card buy) {
    	Player player = context.getPlayer();
    	if (context.charmsNextBuy > 0) {
    		//is there another valid card to gain?
    		boolean validCard = validCharmCardLeft(context, buy);
    		while (context.charmsNextBuy-- > 0) {
    			if (validCard) {
	        		Card toGain = player.controlPlayer.charm_cardToObtain(context, buy);
	        		if (toGain != null) {
		        		if (!isValidCharmCard(context, buy, toGain)) {
		        			Util.playerError(player, "Charm card to gain invalid, ignoring");
		        		} else {
		        			player.gainNewCard(toGain, Cards.charm, context);
		        		}
	        		}
	        		validCard = validCharmCardLeft(context, buy);
    			}
        	}
    	}
    }
    
    private boolean validCharmCardLeft(MoveContext context, Card buy) {
    	for (Card c : context.game.getCardsInGame(GetCardsInGameOptions.TopOfPiles, true)) {
			if (isValidCharmCard(context, buy, c)) {
				return true;
			}
		}
    	return false;
    }
    
    private boolean isValidCharmCard(MoveContext context, Card buy, Card c) {
    	return !buy.equals(c) && context.game.isCardOnTop(c) &&
				!context.game.isPileEmpty(c) &&
				Cards.isSupplyCard(c) &&
				buy.getCost(context) == c.getCost(context) &&
				buy.getDebtCost(context) == c.getDebtCost(context) &&
				(buy.costPotion() == c.costPotion());
    }
    
    private void basilicaWhenBuy(MoveContext context) {
    	//TODO?: Can resolve Basilica before overpay to not get tokens in some cases (would matter with old Possession rules)
    	if (cardInGame(Cards.basilica) && (context.getCoins() + context.overpayAmount) >= 2) {
    		int tokensLeft = getPileVpTokens(Cards.basilica);
    		if (tokensLeft > 0) {
    			int tokensToTake = Math.min(tokensLeft, 2);
    			removePileVpTokens(Cards.basilica, tokensToTake, context);
    			context.getPlayer().addVictoryTokens(context, tokensToTake, Cards.basilica);
    		}
    	}
    }
    
    private void colonnadeWhenBuy(MoveContext context, Card buy) {
    	 if(buy.is(Type.Action, context.getPlayer())) {
	    	if (cardInGame(Cards.colonnade)) {
	    		Player player = context.getPlayer();
	    		if (player.playedCards.contains(buy)) {
	    			int tokensLeft = getPileVpTokens(Cards.colonnade);
            		if (tokensLeft > 0) {
            			int tokensToTake = Math.min(tokensLeft, 2);
            			removePileVpTokens(Cards.colonnade, tokensToTake, context);
            			player.addVictoryTokens(context, tokensToTake, Cards.colonnade);
            		}
	    		}
	    	}
	    }
    }
    
    private void defiledShrineWhenBuy(MoveContext context, Card buy) {
    	//TODO?: Can resolve Basilica before overpay to not get tokens in some cases (would matter with old Possession rules)
    	 if(buy.equals(Cards.curse)) {
	    	if (cardInGame(Cards.defiledShrine)) {
	    		int tokensLeft = getPileVpTokens(Cards.defiledShrine);
	    		if (tokensLeft > 0) {
	    			removePileVpTokens(Cards.defiledShrine, tokensLeft, context);
	    			context.getPlayer().addVictoryTokens(context, tokensLeft, Cards.defiledShrine);
	    		}
	    	}
	    }
    }
    
    public boolean buyWouldEndGame(Card card) {
        if (isColonyInGame() && card.equals(Cards.colony)) {
            if (pileSize(card) <= 1) {
                return true;
            }
        }

        if (card.equals(Cards.province)) {
            if (pileSize(card) <= 1) {
                return true;
            }
        }

        if (emptyPiles() >= 2 && pileSize(card) <= 1) {
            return true;
        }

        return false;
    }

    private boolean checkGameOver(boolean wasPossessed) {
    	boolean result = false;
    	if (isColonyInGame() && isPileEmpty(Cards.colony)) {
    		result = true;
        } else if (isPileEmpty(Cards.province)) {
        	result = true;
        } else {
	        switch (numPlayers) {
	            case 1:
	            case 2:
	            case 3:
	            case 4:
	                /* Ends game for 1, 2, 3 or 4 players */
	                if (emptyPiles() >= 3) {
	                	result = true;
	                }
	                break;
	            case 5:
	            case 6:
	                /* Ends game for 5 or 6 players */
	                if (emptyPiles() >= 4) {
	                    result = true;
	                }
	                break;
	        }
        }
        
        //TODO: for now we're being dynamic with this 
    	//      (e.g. if someone possesses the next player and causes them to buy Fleet, they would get a Fleet turn)
    	int playersWithFleet = 0;
    	for (Player p : players) {
    		if (p.hasProject(Cards.fleet)) {
    			playersWithFleet++;
    		}
    	}
        
    	if (isFleetRound) {
    		if (wasPossessed)
    			return false;
    		//find next eligible Fleet player 
    		//   - if we pass/hit the player that could have had the first Fleet turn (whether or not they had a Fleet turn),
    		//     the game is over
    		int playerIdx = playersTurn;
    		do {
    			playerIdx = (playerIdx + 1) % numPlayers;
    			if (playerStartedFleetRound == players[playerIdx])
    				return true;
    		} while (!players[playerIdx].hasProject(Cards.fleet));
    		return false;
    	} else if (result && playersWithFleet > 0) {
    		fleetRoundPending = true;
    		playerStartedFleetRound = players[(playersTurn+1) % numPlayers];
    		return false;
    	}
    	return result;
    }

    // Use drawToHand when "drawing" or "+ X cards" when -1 Card token could be drawn instead
    boolean drawToHand(MoveContext context, Card responsible, int cardsLeftToDraw, PlayContext playContext) {
        return drawToHand(context, responsible, cardsLeftToDraw, true, playContext);
    }

    boolean drawToHand(MoveContext context, Card responsible, int cardsLeftToDraw, boolean showUI, PlayContext playContext) {
        if (playContext.chameleonEffect) {
            context.addCoins(1, responsible, new PlayContext());
            return true;
        }

    	Player player = context.player;
    	if (player.getMinusOneCardToken()) {
    		player.setMinusOneCardToken(false, context);
    		return true;
    	}
        Card card = draw(context, responsible, cardsLeftToDraw);
        if (card == null)
            return false;

        if (responsible != null) {
            Util.debug(player, responsible.getName() + " draw:" + card.getName(), true);
        }

        player.hand.add(card, showUI);

        return true;
    }

    // Use draw when removing a card from the top of the deck without "drawing" it (e.g. look at or reveal)
    Card draw(MoveContext context, Card responsible, int cardsLeftToDraw) {
    	if (errataShuffleDeckEmptyOnly) {
	    	if (context.player.deck.isEmpty()) {
	            if (context.player.discard.isEmpty()) {
	                return null;
	            } else {
	                replenishDeck(context, responsible, cardsLeftToDraw);
	            }
	        }
    	} else {
    		if (cardsLeftToDraw > 0 && context.player.deck.size() < cardsLeftToDraw) {
    			ArrayList<Card> cardsToDraw = new ArrayList<Card>();
    			for (Card c : context.player.deck) {
    				cardsToDraw.add(c);
    			}
    			context.player.deck.clear();
    			if (!context.player.discard.isEmpty()) {
    				replenishDeck(context, responsible, cardsLeftToDraw - cardsToDraw.size());
    			}
    			if (context.player.deck.isEmpty() && cardsToDraw.isEmpty()) {
    				return null;
    			}
    			Collections.reverse(cardsToDraw);
    			for (Card c : cardsToDraw) {
    				context.player.deck.add(0, c);
    			}
    		} else if (context.player.deck.isEmpty()) {
	            if (context.player.discard.isEmpty()) {
	                return null;
	            } else {
	                replenishDeck(context, responsible, cardsLeftToDraw);
	            }
	        }
    	}
        return context.player.deck.remove(0);
    }
    
    private void shuffleBoonsIfNeeded() {
    	if (boonDrawPile.isEmpty()) {
    		boonDrawPile.addAll(boonDiscardPile);
    		boonDiscardPile.clear();
    		Collections.shuffle(boonDrawPile);
    	}
    }
    
    private void shuffleHexesIfNeeded() {
    	if (hexDrawPile.isEmpty()) {
    		hexDrawPile.addAll(hexDiscardPile);
    		hexDiscardPile.clear();
    		Collections.shuffle(hexDrawPile);
    	}
    }
    
    public Card takeNextBoon(MoveContext context, Card responsible) {
    	shuffleBoonsIfNeeded();
    	Card boon = boonDrawPile.remove(0);
    	//TODO: event for taking boon?
    	//for now we are leaving it up to caller to put the boon somewhere (e.g. nextTurnCards, receiveAndDiscard, etc.)
    	return boon;
    }
    
    public Card discardNextBoon(MoveContext context, Card responsible) {
    	shuffleBoonsIfNeeded();
    	Card boon = boonDrawPile.remove(0);
    	//TODO: event for discarding boon?
    	boonDiscardPile.add(boon);
    	return boon;
    }
    
    public void discardBoon(MoveContext context, Card boon) {
    	//For boons that were received without being discarded (e.g. The Field's Gift & The Forest's Gift)
    	boonDiscardPile.add(boon);
    }
    
    private boolean isKeepUntilCleanupBoon(Card boon) {
    	return boon.equals(Cards.theFieldsGift) || boon.equals(Cards.theForestsGift) || boon.equals(Cards.theRiversGift);
    }
    
    public Card receiveNextBoon(MoveContext context, Card responsible) {
    	shuffleBoonsIfNeeded();
    	Card boon = boonDrawPile.remove(0);
    	if (isKeepUntilCleanupBoon(boon)) {
    		if (!context.getPlayer().boonsForCleanup.contains(boon))
    			context.getPlayer().boonsForCleanup.add(boon);
    	} else {
    		boonDiscardPile.add(boon);
    	}
    	boon.followInstructions(this, context, responsible, context.game.getCurrentPlayer(), false);
    	return boon;
    }
    
    public void recieveBoonAndDiscard(MoveContext context, Card boon, Card responsible) {
        boon.followInstructions(this, context, responsible, context.game.getCurrentPlayer(), false);
    	if (isKeepUntilCleanupBoon(boon)) {
    		if (!context.getPlayer().boonsForCleanup.contains(boon))
    			context.getPlayer().boonsForCleanup.add(boon);
    	} else {
    		boonDiscardPile.add(boon);
    	}
    }
    
    public void recieveBoon(MoveContext context, Card boon, Card responsible) {
        boon.followInstructions(this, context, responsible, context.game.getCurrentPlayer(), false);
    }
    
    public Card receiveNextHex(MoveContext context, Card responsible) {
    	shuffleHexesIfNeeded();
    	Card hex = hexDrawPile.remove(0);
    	hexDiscardPile.add(hex);
        hex.followInstructions(this, context, responsible, context.game.getCurrentPlayer(), false);
    	return hex;
    }
    
    public Card othersReceiveNextHex(MoveContext context, List<Player> attackedPlayers, Card responsible) {
    	shuffleHexesIfNeeded();
    	Card hex = hexDrawPile.remove(0);
    	hexDiscardPile.add(hex);
    	for (Player player : attackedPlayers) {
    		player.attacked(hex, context);
            MoveContext playerContext = new MoveContext(this, player);
            playerContext.attackedPlayer = player;
            hex.followInstructions(this, playerContext, responsible, playerContext.getPlayer(), false);
    	}
    	return hex;
    }
    
    public void takeState(MoveContext context, Card state) {
    	if (context.player.states.contains(state)) return;
    	//currently instantiating these as needed instead of moving around a set number
    	// we may have to change this if number of states a player takes of a type matters in the future
    	context.player.states.add(state.instantiate());
    	GameEvent event = new GameEvent(GameEvent.EventType.TakeState, (MoveContext) context);
    	event.card = state;
    	broadcastEvent(event);
    }
    
    public void returnState(MoveContext context, Card state) {
    	if (!context.player.states.contains(state)) return;
    	context.player.states.remove(state);
    	GameEvent event = new GameEvent(GameEvent.EventType.ReturnState, (MoveContext) context);
    	event.card = state;
    	broadcastEvent(event);
    }
    
    public void returnSharedState(MoveContext context, Card state) {
    	if (!context.player.states.contains(state)) return;
    	GameEvent event = new GameEvent(GameEvent.EventType.ReturnState, (MoveContext) context);
    	event.card = state;
    	int idx = sharedStates.indexOf(state);
		state = sharedStates.get(idx);
		sharedStates.add(state);
    	broadcastEvent(event);
    }
    
    public boolean hasState(Player player, Card state) {
    	return player.states.contains(state);
    }
    
    public void takeSharedState(MoveContext context, Card state) {
    	Player playerWithState = null;
    	for (Player player : players) {
    		int idx = player.states.indexOf(state);
    		if (idx >= 0) {
    			if (player != context.player) {
    				state = player.states.remove(idx);
        		}
    			playerWithState = player;
    			break;
    		}
    	}
    	if (playerWithState == null) {
    		int idx = sharedStates.indexOf(state);
    		state = sharedStates.get(idx);
    	} else if (playerWithState == context.player) {
    		return;
    	}
    	
    	context.player.states.add(state);
    	
    	GameEvent event = new GameEvent(GameEvent.EventType.TakeState, (MoveContext) context);
    	event.attackedPlayer = playerWithState;
    	event.card = state;
    	broadcastEvent(event);
    }

    public void replenishDeck(MoveContext context, Card responsible, int cardsLeftToDraw) {
        context.player.replenishDeck(context, responsible, cardsLeftToDraw);
        
        GameEvent event = new GameEvent(GameEvent.EventType.DeckReplenished, context);
        broadcastEvent(event);
    }

    private void handleShowEvent(GameEvent event) {
        if (showEvents.contains(event.getType())) {
            Player player = event.getPlayer();

            if (player == null || (player != null && !showPlayers.isEmpty() && !showPlayers.contains(player.getPlayerName()))) {
                return;
            }

            if (event.getType() == GameEvent.EventType.TurnEnd) {
                Util.debug("");
                return;
            }

            StringBuilder msg = new StringBuilder();
            msg.append(player.getPlayerName() + "::" + turnCount + ":" + event.getType());

            if (event.getType() == GameEvent.EventType.CardObtained) {
                msg.append(":" + event.getContext().getCoinAvailableForBuy() + " gold");
                if (event.getContext().getBuysLeft() > 0) {
                    msg.append(", buys remaining: " + event.getContext().getBuysLeft() + ")");
                }
            } else if (event.getType() == GameEvent.EventType.PlayingCard || event.getType() == GameEvent.EventType.TurnBegin
                       || event.getType() == GameEvent.EventType.NoBuy) {
                msg.append(":" + getHandString(player));
            } else if (event.attackedPlayer != null) {
                msg.append(":" + event.attackedPlayer.getPlayerName() + " with " + event.card.getName());
            }

            if (event.card != null) {
                msg.append(" -> " + event.card.getName());
            }

            if (event.getComment() != null) {
                msg.append(event.getComment());
            }

            Util.debug(msg.toString());
        }
    }
    
    void printCardCounts() {
    	for(Player p : players) {
    		Map<Object,Integer> counts = p.getAllCardCounts();
    		counts.remove(Player.DISTINCT_CARDS);
    		Util.log("\t" + p.getPlayerName() + ": " + counts.toString());
    	}
    }

    int totalCardCount() {
        HashMap<String, Integer> cardCounts = new HashMap<String, Integer>();
        for (String cardName : piles.keySet()) {
            cardCounts.put(cardName, piles.get(cardName).getCount());
        }
        for (Card card : trashPile) {
            cardCounts.put(card.getName(), cardCounts.get(card.getName()) + 1);
        }
        for (int i = 0; i < numPlayers; i++) {
            for (Card card : players[i].getAllCards()) {
                cardCounts.put(card.getName(), cardCounts.get(card.getName()) + 1);
            }
        }
        Util.log(cardCounts.toString());

        int totalCardCount = 0;
        for (Integer v : cardCounts.values()) {
            totalCardCount += v;
        }

        return totalCardCount;
    }

    void initGameBoard() throws ExitException {
        cardSequence = 1;
        baneCard = null;
        wayOfTheMouseCard = null;
        firstProvinceWasGained = false;
        doMountainPassAfterThisTurn = false;

        initGameListener();

        initCards();
        initPlayers(numPlayers);
        initPlayerCards();


        gameOver = false;
        playerStartedFleetRound = null;
        isFleetRound = false;
        fleetRoundPending = false;
    }

    protected void initPlayers(int numPlayers) throws ExitException {
        initPlayers(numPlayers, true);
    }

    protected void initPlayers(int numPlayers, boolean isRandom) throws ExitException {
        players = new Player[numPlayers];
        cardsObtainedLastTurn = new ArrayList[numPlayers];
        allCardsObtainedLastTurn = new ArrayList[numPlayers];
        cardsTrashedLastTurn = new ArrayList[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            cardsObtainedLastTurn[i] = new ArrayList<Card>();
            allCardsObtainedLastTurn[i] = new ArrayList<Card>();
            cardsTrashedLastTurn[i] = new ArrayList<Card>();
        }

        ArrayList<String[]> randomize = new ArrayList<String[]>();
        while (!playerClassesAndJars.isEmpty()) {
            if(isRandom) {
                randomize.add(playerClassesAndJars.remove(rand.nextInt(playerClassesAndJars.size())));
            } else {
                randomize.add(playerClassesAndJars.remove(0));
            }
        }

        playerClassesAndJars = randomize;
        playerCache.clear();
        playersTurn = 0;

        for (int i = 0; i < numPlayers; i++) {
            try {
                String[] playerStartupInfo = playerClassesAndJars.get(i);
                if (playerStartupInfo[1] == null) {
                    players[i] = (Player) Class.forName(playerStartupInfo[0]).newInstance();
                } else {
                    String key = playerStartupInfo[0] + "::" + playerStartupInfo[1];
                    // players[i] = cachedPlayers.get(key);
                    //
                    // if(players[i] == null) {
                    // URLClassLoader classLoader = new URLClassLoader(new URL[] { new URL(classAndJar[1]) });
                    // players[i] = classLoader.loadClass(classAndJar[0]).newInstance();
                    // cachedPlayers.put(key, players[i]);
                    // }

                    Class<?> playerClass = cachedPlayerClasses.get(key);

                    if (playerClass == null) {
                        URLClassLoader classLoader = new URLClassLoader(new URL[] { new URL(playerStartupInfo[1]) });
                        playerClass = classLoader.loadClass(playerStartupInfo[0]);
                        cachedPlayerClasses.put(key, playerClass);
                    }

                    players[i] = (Player) playerClass.newInstance();
                }
                if(playerStartupInfo[2] != null) {
                    players[i].setName(playerStartupInfo[2]);
                }
                //String options = playerStartupInfo[3];
                playerCache.put(playerStartupInfo[0], players[i]);
            } catch (Exception e) {
                Util.log(e);
                throw new ExitException();
            }

            players[i].game = this;
            players[i].playerNumber = i;

            // Interactive player needs this called once for each player on startup so internal counts work properly.
            players[i].getPlayerName();
            
            MoveContext context = new MoveContext(this, players[i]);
            players[i].newGame(context);
            players[i].initCards();

            context = new MoveContext(this, players[i]);
            String s = cardListText + "\n---------------\n\n";
            
            if (platColonyPassedIn || chanceForPlatColony > 0.9999) {
                s += "Platinum/Colony included...\n";
            } else if (platColonyNotPassedIn || Math.round(chanceForPlatColony * 100) == 0) {
                s += "Platinum/Colony not included...\n";
            } else {
                s += "Chance for Platinum/Colony\n   " + (Math.round(chanceForPlatColony * 100)) + "% ... " + (isPlatInGame() ? "included\n" : "not included\n");
            }

            if (baneCard != null) {
                s += "Bane card: " + baneCard.getName() + "\n";
            }

            // When Baker is included in the game, each Player starts with 1 coin token on Coffers mat
            if (bakerInPlay)
            {
                players[i].gainGuildsCoinTokens(1, null, null);
            }
            //only for testing
            if (startGuildsCoinTokens && !players[i].isAi())
            {
                players[i].gainGuildsCoinTokens(99, null, null);
            }

            /* The journey token is face up at the start of a game.
             * It can be turned over by Ranger, Giant and Pilgrimage.
             */
            if (journeyTokenInPlay)
            {
                players[i].flipJourneyToken(null);
            }

            if (sheltersPassedIn || chanceForShelters > 0.9999) {
                s += "Shelters included...\n";
            } else if (sheltersNotPassedIn || Math.round(chanceForShelters * 100) == 0) {
                s += "Shelters not included...\n";
            }
            else {
                s += "Chance for Shelters\n   " + (Math.round(chanceForShelters * 100)) + "% ... " + (sheltersInPlay ? "included\n" : "not included\n");
            }

            s += unfoundCardText;
            context.message = s;
            broadcastEvent(new GameEvent(GameEvent.EventType.GameStarting, context));

        }
    }

    protected void initPlayerCards() {
        Player player;
        for (int i = 0; i < numPlayers; i++) {
            player = players[i];
            
            //TODO: what if more than 7 heirlooms occur?
            for (Card c : heirloomsInPlay) {
            	player.discard(takeFromPile(c), null, null);
            }
            int numCoppers = 7 - heirloomsInPlay.size();
            for (int j = 0; j < numCoppers; j++){
            	player.discard(takeFromPile(Cards.copper), null, null);
            }
            
            if (sheltersInPlay)
            {
                player.discard(takeFromPile(Cards.necropolis), null, null);
                player.discard(takeFromPile(Cards.overgrownEstate), null, null);
                player.discard(takeFromPile(Cards.hovel), null, null);

                // Also need to remove the Estates that were put in the pile prior to
                // determining if Shelters would be used
                takeFromPile(Cards.estate);
                takeFromPile(Cards.estate);
                takeFromPile(Cards.estate);
            }
            else
            {
                player.discard(takeFromPile(Cards.estate), null, null);
                player.discard(takeFromPile(Cards.estate), null, null);
                player.discard(takeFromPile(Cards.estate), null, null);
            }

            if (!equalStartHands || i == 0) {
                PlayContext drawContext = new PlayContext();
                while (player.hand.size() < 5)
                    drawToHand(new MoveContext(this, player), null, 5 - player.hand.size(), false, drawContext);
            }
            else {
                // make subsequent player hands equal
                for (int j = 0; j < 5; j++) {
                    Card card = players[0].hand.get(j);
                    player.discard.remove(card);
                    player.hand.add(card);
                }
                player.replenishDeck(null, null, 0);
            }
        }

        if (noCards) //only for testing
        {
            for (int i = 0; i < numPlayers; i++) {
                player = players[i];
                if (player.isAi())
                {
                    continue;
                }
                player.hand.clear();
                player.deck.clear();
                player.discard.clear();
            }
        }
        
        // Add tradeRoute tokens if tradeRoute in play
        tradeRouteValue = 0;
        if (cardInGame(Cards.tradeRoute)) {
            for (CardPile pile : piles.values()) {
                if ((pile.placeholderCard().is(Type.Victory)) && pile.isSupply()) {
                    pile.setTradeRouteToken();
                }
            }
        }

    }

    protected void initCards() {
        piles.clear();
        embargos.clear();
        pileVpTokens.clear();
        playerSupplyTokens.clear();
        trashPile.clear();
        blackMarketPile.clear();
        blackMarketPileShuffled.clear();
        boonDrawPile.clear();
        boonDiscardPile.clear();
        druidBoons.clear();
        hexDrawPile.clear();
        hexDiscardPile.clear();
        sharedStates.clear();
        
        platColonyNotPassedIn = false;
        platColonyPassedIn = false;
        sheltersNotPassedIn = false;
        sheltersPassedIn = false;

        int provincePileSize = -1;
        int curseCount = -1;
        int treasureMultiplier = 1;

        switch (numPlayers) {
            case 1:
            case 2:
                curseCount = 10;
                provincePileSize = 8;
                victoryCardPileSize = 8;
                break;
            case 3:
                curseCount = 20;
                provincePileSize = 12;
                victoryCardPileSize = 12;
                break;
            case 4:
                curseCount = 30;
                provincePileSize = 12;
                victoryCardPileSize = 12;
                break;
            case 5:
                curseCount = 40;
                provincePileSize = 15;
                victoryCardPileSize = 12;
                treasureMultiplier = 2;
                break;
            case 6:
                curseCount = 50;
                provincePileSize = 18;
                victoryCardPileSize = 12;
                treasureMultiplier = 2;
                break;
        }
        //only for testing
        if (lessProvinces)
        {
            provincePileSize = 1; 
        }

        addPile(Cards.gold, 30 * treasureMultiplier);
        addPile(Cards.silver, 40 * treasureMultiplier);
        addPile(Cards.copper, 60 * treasureMultiplier);

        addPile(Cards.curse, curseCount);
        addPile(Cards.province, provincePileSize);
        addPile(Cards.duchy, victoryCardPileSize);
        addPile(Cards.estate, victoryCardPileSize + (3 * numPlayers));

        unfoundCards.clear();
        int added = 0;

        if(cardsSpecifiedAtLaunch != null) {
            platColonyNotPassedIn = true;
            sheltersNotPassedIn = true;
            for(String cardName : cardsSpecifiedAtLaunch) {
                Card card = null;
                boolean bane = false;
                boolean obelisk = false;
                boolean wayOfTheMouse = false;
                boolean blackMarket = false;

                if(cardName.startsWith(BANE)) {
                    bane = true;
                    cardName = cardName.substring(BANE.length());
                }
                if(cardName.startsWith(OBELISK)) {
                    obelisk = true;
                    cardName = cardName.substring(OBELISK.length());
                }
                if(cardName.startsWith(WAY_OF_THE_MOUSE)) {
                	wayOfTheMouse = true;
                    cardName = cardName.substring(WAY_OF_THE_MOUSE.length());
                }
                if(cardName.startsWith(BLACKMARKET)) {
                    blackMarket = true;
                    cardName = cardName.substring(BLACKMARKET.length());
                }
                if (cardName.startsWith(DRUID_BOON)) {
                	cardName = cardName.substring(DRUID_BOON.length());
                	String s = cardName.replace("/", "").replace(" ", "").replace("'", "");
                	 for (Card c : Cards.boonCards) {
                         if(c.getSafeName().equalsIgnoreCase(s)) {
                             card = c;
                             break;
                         }
                     }
                	 if (card != null)
                		 druidBoons.add(card.instantiate());
                	 continue;
                }
                String s = cardName.replace("/", "").replace(" ", "");
                for (Card c : Cards.actionCards) {
                    if(c.getSafeName().equalsIgnoreCase(s)) {
                        card = c;
                        break;
                    }
                }
                for (Card c : Cards.eventsCards) {
                    if(c.getSafeName().equalsIgnoreCase(s)) {
                        card = c;
                        break;
                    }
                }
                for (Card c : Cards.projectCards) {
                    if(c.getSafeName().equalsIgnoreCase(s)) {
                        card = c;
                        break;
                    }
                }
                for (Card c : Cards.landmarkCards) {
                    if(c.getSafeName().equalsIgnoreCase(s)) {
                        card = c;
                        break;
                    }
                }
                for (Card c : Cards.wayCards) {
                    if(c.getSafeName().equalsIgnoreCase(s)) {
                        card = c;
                        break;
                    }
                }
                // Handle split pile / knights cards being passed in incorrectly
                for (Card c : Cards.variablePileCards) {
                    if(c.getSafeName().equalsIgnoreCase(s)) {
                        card = c;
                        break;
                    }
                }
                for (Card c : Cards.castleCards) {
                    if(c.getSafeName().equalsIgnoreCase(s)) {
                        card = c;
                        break;
                    }
                }
                for (Card c : Cards.knightsCards) {
                    if(c.getSafeName().equalsIgnoreCase(s)) {
                        card = c;
                        break;
                    }
                }
                if (card != null && Cards.variablePileCardToRandomizer.containsKey(card)) {
                	card = Cards.variablePileCardToRandomizer.get(card);
                }
                
                if(card != null && bane) {
                    baneCard = card;
                }
                if(card != null && obelisk) {
                    obeliskCard = card;
                }
                if(card != null && wayOfTheMouse) {
                    wayOfTheMouseCard = card;
                }
                if(card != null && blackMarket) {
                    blackMarketPile.add(card);
                }
                if (cardName.equalsIgnoreCase("Knights")) {
                    card = Cards.virtualKnight;
                }

                if(card != null && !piles.containsKey(card.getName())) {
                    addPile(card);
                    added += 1;
                } else if ( s.equalsIgnoreCase(Cards.curse.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.estate.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.duchy.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.province.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.copper.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.silver.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.potion.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.gold.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.diadem.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.followers.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.princess.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.trustySteed.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.bagOfGold.getSafeName()) ) {
                    // do nothing
                } else if ( s.equalsIgnoreCase(Cards.platinum.getSafeName()) ||
                            s.equalsIgnoreCase(Cards.colony.getSafeName()) ) {
                    platColonyPassedIn = true;
                } else if (s.equalsIgnoreCase("Shelter") ||
                			s.equalsIgnoreCase("Shelters") ||
                           s.equalsIgnoreCase(Cards.hovel.getSafeName()) ||
                           s.equalsIgnoreCase(Cards.overgrownEstate.getSafeName()) ||
                           s.equalsIgnoreCase(Cards.necropolis.getSafeName()) ) {
                    sheltersPassedIn = true;
                } else {
                    unfoundCards.add(s);
                    Util.debug("ERROR::Could not find card:" + s);
                }
            }
            
            for(String s : unfoundCards) {
                if (added >= 10)
                    break;
                Card c = null;
                int replacementCost = -1;

                if(replacementCost != -1) {
                    ArrayList<Card> cardsWithSameCost = new ArrayList<Card>();
                    for (Card card : Cards.actionCards) {
                        if(card.getCost(null) == replacementCost && !cardInGame(card)) {
                            cardsWithSameCost.add(card);
                        }
                    }

                    if(cardsWithSameCost.size() > 0) {
                        c = cardsWithSameCost.get(rand.nextInt(cardsWithSameCost.size()));
                    }
                }

                while(c == null) {
                    c = Cards.actionCards.get(rand.nextInt(Cards.actionCards.size()));
                    if(cardInGame(c)) {
                        c = null;
                    }
                }

                Util.debug("Adding replacement for " + s + ": " + c);
                addPile(c);
                added += 1;
            }

            gameType = GameType.Specified;
        } else {
            CardSet cardSet = CardSet.getCardSet(gameType, -1, randomExpansions, randomExcludedExpansions, randomExpansionLimit, randomExpansionAllocation, 
            		numRandomEvents, 
            		numRandomProjects,
            		numRandomLandmarks,
            		numRandomWays,
            		!splitMaxSidewaysCards, keepSidewaysCardsWithExpansion, 
            		false);
            if(cardSet == null) {
                cardSet = CardSet.getCardSet(CardSet.defaultGameType, -1);
            }

            for(Card card : cardSet.getCards()) {
                this.addPile(card);
            }

            if(cardSet.getBaneCard() != null) {
                this.baneCard = cardSet.getBaneCard();
                //Adding the bane card could probably be done in the CardSet class, but it seems better to call it out explicitly.
                this.addPile(this.baneCard);
            }
            if (cardSet.getObeliskCard() != null) {
            	this.obeliskCard = cardSet.getObeliskCard();
            }
            if (cardSet.getDruidBoons() != null) {
            	this.druidBoons.addAll(cardSet.getDruidBoons());
            }
            if (cardSet.getWayOfTheMouseCard() != null) {
            	this.wayOfTheMouseCard = cardSet.getWayOfTheMouseCard();
            	addPile(wayOfTheMouseCard, 1, false, false, false);
            }
        }

        // Black Market
        Cards.blackMarketCards.clear();
        if (piles.containsKey(Cards.blackMarket.getName()))
        {
            List<Card> allCards;

            // get 10 cards more then needed. Extract the cards in supply
            int count = Math.max(blackMarketCount - blackMarketPile.size(), 0);
            if (blackMarketOnlyCardsFromUsedExpansions) {
                List<Expansion> expansions = new ArrayList<Expansion>();
                if (randomExpansions != null && randomExpansions.size() > 0) {
                    expansions.addAll(randomExpansions);
                } else {
                    for (CardPile pile : placeholderPiles.values()) {
                        if (pile != null &&
                                pile.placeholderCard() != null &&
                                pile.placeholderCard().getExpansion() != null &&
                                Cards.isKingdomCard(pile.placeholderCard()) &&
                                !expansions.contains(pile.placeholderCard().getExpansion()) &&
                                pile.placeholderCard().getExpansion() != Expansion.Promo) {
                            expansions.add(pile.placeholderCard().getExpansion());
                        }
                    }
                }
                allCards = CardSet.getCardSet(GameType.Random, count+10, expansions, randomExcludedExpansions, 0, ExpansionAllocation.Random,
                		0, 0, 0, 0, 
                		false, false,
                		false).getCards();
            } else {
                allCards = CardSet.getCardSet(GameType.Random, count+10).getCards();
            }

            List<Card> remainingCards = new ArrayList<Card>();
            for (int i = 0; i < allCards.size(); i++) {
                if (!piles.containsKey(allCards.get(i).getName())) {
                	CardPile tempPile = allCards.get(i).getPileCreator().create(allCards.get(i), 12); //count doesn't matter as we only need templates
                    ArrayList<Card> templates = tempPile.getTemplateCards();
                	if (Cards.variablePileCards.contains(templates.get(0)) || Cards.castleCards.contains(templates.get(0))) {
                		if (blackMarketSplitPileOptions == BlackMarketSplitPileOptions.ANY) {
                            for (Card card : templates) {
                                remainingCards.add(card);
                            }
                    	} else if (blackMarketSplitPileOptions == BlackMarketSplitPileOptions.ONE) {
                            remainingCards.add(Util.randomCard(templates));
                        } else if (blackMarketSplitPileOptions == BlackMarketSplitPileOptions.ALL) {
                            remainingCards.add(allCards.get(i));
                        }
                	} else {
                    	remainingCards.add(Util.randomCard(templates));
                    }
                }
            }
            // take count cards from the rest
            List<Card> cards = CardSet.getRandomCardSet(remainingCards, count).getCards();
            
            //Force remaining split pile cards into black market deck if one from a split pile is in
            if (blackMarketSplitPileOptions == BlackMarketSplitPileOptions.ALL) {
            	ArrayList<Card> extraCards = new ArrayList<Card>();
            	for (int i = 0; i < cards.size(); ++i) {
            		Card c = cards.get(i);
            		ArrayList<Card> templates = c.getPileCreator().create(allCards.get(i), 12).getTemplateCards(); //count doesn't matter as we only need templates
            		if (templates.size() > 1) {
            			cards.set(i, templates.get(0));
            			for (int j = 1; j < templates.size(); ++j) {
            				extraCards.add(templates.get(j));
            			}
            		}
            	}
            	int cardsToRemove = extraCards.size() - (count - cards.size());
            	for (int n = 0; n < cardsToRemove; ++n) {
            		for (int i = 0; i < cards.size(); ++i) {
            			if (!Cards.variablePileCards.contains(cards.get(i)) && !Cards.castleCards.contains(cards.get(i))) {
            				cards.remove(i);
            				break;
            			}
            		}
            	}
            	for (Card c : extraCards) {
            		if (cards.size() < count)
            			cards.add(c);
            	}
            }
            
            for (int i = 0; i < cards.size(); i++) {
            	remainingCards.remove(cards.get(i));
                blackMarketPile.add(cards.get(i).instantiate());
            }

            if (this.baneCard == null && blackMarketPile.contains(Cards.youngWitch)) {
            	this.baneCard = CardSet.getBaneCard(remainingCards);
                if (this.baneCard != null) {
                    this.addPile(this.baneCard);
                }
            }
            // sort
            Collections.sort(blackMarketPile, new Util.CardCostNameComparator());
            // put all in piles
            cards.clear();
            for (int i = 0; i < blackMarketPile.size(); i++) {
                cards.add(blackMarketPile.get(i));
                addPile(blackMarketPile.get(i).getTemplateCard(), 1, false, true);
                Cards.blackMarketCards.add(blackMarketPile.get(i));
            }
            // shuffle
            while (cards.size() > 0) {
                blackMarketPileShuffled.add(cards.remove(Game.rand.nextInt(cards.size())));
            }
        }
        
        if (obeliskCard != null && !piles.containsKey(Cards.obelisk.getName())) {
        	addPile(Cards.obelisk);
        }
        if (wayOfTheMouseCard != null && !piles.containsKey(Cards.wayOfTheMouse.getName())) {
        	addPile(Cards.wayOfTheMouse);
        }

        //determine shelters & plat/colony use
        boolean alreadyCountedKnights = false;
        int darkAgesCards = 0;
        int prosperityCards = 0;
        int kingdomCards = 0;
        for (CardPile pile : placeholderPiles.values()) {
            if (pile != null &&
            		pile.placeholderCard() != null &&
            		pile.placeholderCard().getExpansion() != null &&
            		Cards.isKingdomCard(pile.placeholderCard())) {
            	kingdomCards++;
            	if (pile.placeholderCard.getExpansion() == Expansion.DarkAges) {
                    darkAgesCards++;
                }
            	if (pile.placeholderCard().getExpansion() == Expansion.Prosperity) {
                    prosperityCards++;
                }
            }
        }

        sheltersInPlay = false;
        if (sheltersPassedIn) {
            sheltersInPlay = true;
            chanceForShelters = 1;
        } else if (!(sheltersNotPassedIn && cardsSpecifiedAtLaunch != null)) {
            if (chanceForShelters > -0.0001) {
                sheltersInPlay = rand.nextDouble() < chanceForShelters;
            } else {
                chanceForShelters = darkAgesCards / (double) kingdomCards;

                if (rand.nextDouble() < chanceForShelters) {
                    sheltersInPlay = true;
                }
            }
        }

        if (sheltersInPlay) {
            addPile(Cards.necropolis, numPlayers, false, false, false);
            addPile(Cards.overgrownEstate, numPlayers, false, false, false);
            addPile(Cards.hovel, numPlayers, false, false, false);
        }
        
        heirloomsInPlay = new ArrayList<Card>();
        for (CardPile pile : placeholderPiles.values()) {
            if (pile != null &&
            		pile.placeholderCard() != null &&
            		Cards.isKingdomCard(pile.placeholderCard())) {
            	Card heirloom = pile.placeholderCard().getHeirloom();
            	if (heirloom != null && !heirloomsInPlay.contains(heirloom)) {
            		heirloomsInPlay.add(heirloom);
            	}
            	Card[] states = pile.placeholderCard().getLinkedStates();
            	for (Card state : states) {
		        	if (state != null && !sharedStates.contains(state)) {
		            	sharedStates.add(state.instantiate());
		        	}
            	}
            }
        }
        for (Card c : heirloomsInPlay) {
        	addPile(c, numPlayers, false, false, false);
        }
        
        // Check for PlatColony
        boolean addPlatColony = false;
        if (platColonyPassedIn) {
            addPlatColony = true;
        } else if (!(platColonyNotPassedIn && cardsSpecifiedAtLaunch != null)) {
            if (chanceForPlatColony > -0.0001) {
                addPlatColony = rand.nextDouble() < chanceForPlatColony;
            } else {
                chanceForPlatColony = prosperityCards / (double) kingdomCards;

                if (rand.nextDouble() < chanceForPlatColony) {
                    addPlatColony = true;
                }
            }
        }

        if (addPlatColony) {
            addPile(Cards.platinum, 12);
            addPile(Cards.colony);
        }

        // Add the potion if there are any cards that need them.
        outerloop:
        for (CardPile pile : piles.values()) {
            for (Card cardInPile : pile.getTemplateCards()) {
                if (cardInPile.costPotion()) {
                    addPile(Cards.potion, 16);
                    break outerloop;
                }
            }
        }

        boolean looter = false;
        boolean fate = false;
        boolean doom = false;
        for (CardPile pile : piles.values()) {
            for (Card cardInPile : pile.getTemplateCards()) {
                if (cardInPile.is(Type.Looter, null)) {
                    looter = true;
                }
                if (cardInPile.is(Type.Fate, null)) {
                    fate = true;
                }
                if (cardInPile.is(Type.Doom, null)) {
                    doom = true;
                }
            }
        }
        if (looter) {
            addPile(Cards.virtualRuins, Math.max(10, (numPlayers * 10) - 10));
        }
        if (fate) {
        	boonDrawPile.add(Cards.theEarthsGift.instantiate());
        	boonDrawPile.add(Cards.theFieldsGift.instantiate());
        	boonDrawPile.add(Cards.theFlamesGift.instantiate());
        	boonDrawPile.add(Cards.theForestsGift.instantiate());
        	boonDrawPile.add(Cards.theMoonsGift.instantiate());
        	boonDrawPile.add(Cards.theMountainsGift.instantiate());
        	boonDrawPile.add(Cards.theRiversGift.instantiate());
        	boonDrawPile.add(Cards.theSeasGift.instantiate());
        	boonDrawPile.add(Cards.theSkysGift.instantiate());
        	boonDrawPile.add(Cards.theSunsGift.instantiate());
        	boonDrawPile.add(Cards.theSwampsGift.instantiate());
        	boonDrawPile.add(Cards.theWindsGift.instantiate());
        	Collections.shuffle(boonDrawPile);
        	
        	if (piles.containsKey(Cards.druid.getName())) {
        		if(druidBoons.isEmpty()) {
        			for (int i = 0; i < 3; ++i) {
            			druidBoons.add(boonDrawPile.remove(0));
            		}
        		} else {
        			boonDrawPile.removeAll(druidBoons);
        		}
        		Collections.sort(druidBoons, new Util.CardNameComparator());
        	}
        }
        if (doom) {
        	hexDrawPile.add(Cards.badOmens.instantiate());
        	hexDrawPile.add(Cards.delusion.instantiate());
        	hexDrawPile.add(Cards.envy.instantiate());
        	hexDrawPile.add(Cards.famine.instantiate());
        	hexDrawPile.add(Cards.fear.instantiate());
        	hexDrawPile.add(Cards.greed.instantiate());
        	hexDrawPile.add(Cards.haunting.instantiate());
        	hexDrawPile.add(Cards.locusts.instantiate());
        	hexDrawPile.add(Cards.misery.instantiate());
        	hexDrawPile.add(Cards.plague.instantiate());
        	hexDrawPile.add(Cards.poverty.instantiate());
        	hexDrawPile.add(Cards.war.instantiate());
        	Collections.shuffle(hexDrawPile);
        }

        if (piles.containsKey(Cards.tournament.getName()) && !piles.containsKey(Cards.bagOfGold.getName())) {
            addPile(Cards.bagOfGold, 1, false);
            addPile(Cards.diadem, 1, false);
            addPile(Cards.followers, 1, false);
            addPile(Cards.princess, 1, false);
            addPile(Cards.trustySteed, 1, false);
        }

        // If Bandit Camp, Pillage, or Marauder is in play, we'll need Spoils (non-supply)
        if (piles.containsKey(Cards.banditCamp.getName()) ||
            piles.containsKey(Cards.pillage.getName()) ||
            piles.containsKey(Cards.marauder.getName()))
        {
            addPile(Cards.spoils, 15, false);
        }

        // If Urchin is in play, we'll need Mercenary (non-supply)
        if (piles.containsKey(Cards.urchin.getName()))
        {
            addPile(Cards.mercenary, 10, false);
        }

        // If Hermit is in play, we'll need Madman (non-supply)
        if (piles.containsKey(Cards.hermit.getName()))
        {
            addPile(Cards.madman, 10, false);
        }

        // If Page is in play, we'll need treasureHunter, warrior, hero, champion (non-supply)
        if (piles.containsKey(Cards.page.getName()))
        {
            addPile(Cards.treasureHunter, 5, false);
            addPile(Cards.warrior, 5, false);
            addPile(Cards.hero, 5, false);
            addPile(Cards.champion, 5, false);
        }

        // If Peasant is in play, we'll need soldier, fugitive, disciple, teacher (non-supply)
        if (piles.containsKey(Cards.peasant.getName()))
        {
            addPile(Cards.soldier, 5, false);
            addPile(Cards.fugitive, 5, false);
            addPile(Cards.disciple, 5, false);
            addPile(Cards.teacher, 5, false);
        }
        
        boolean hasExorcist = piles.containsKey(Cards.exorcist.getName());
        
        // If Devil's Workshop, Tormentor, or Exorcist is in play, we'll need Imp (non-supply)
        if (piles.containsKey(Cards.devilsWorkshop.getName()) || piles.containsKey(Cards.tormentor.getName()) || hasExorcist)
        {
            addPile(Cards.imp, 13, false);
        }
        
        // If Cemetery or Exorcist is in play, we'll need Ghost (non-supply)
        if (piles.containsKey(Cards.cemetery.getName()) || hasExorcist) {
            addPile(Cards.ghost, 6, false);
        }
                
        // If a Fate card or Exorcist is in play, we'll need Will-o'-Wisp (non-supply)
        if (fate || hasExorcist) {
            addPile(Cards.willOWisp, 12, false);
        }
        
        // If Leprechaun or Secret Cave are in play, we'll need Wish (non-supply)
        if (piles.containsKey(Cards.leprechaun.getName()) || piles.containsKey(Cards.secretCave.getName())) {
            addPile(Cards.wish, 12, false);
        }
        
        // If Vampire is in play, we'll need Bat (non-supply)
        if (piles.containsKey(Cards.vampire.getName())) {
            addPile(Cards.bat, 10, false);
        }
        
        if (piles.containsKey(Cards.necromancer.getName())) {
        	addPile(Cards.zombieApprentice, 1, false, false, false);
        	addPile(Cards.zombieMason, 1, false, false, false);
        	addPile(Cards.zombieSpy, 1, false, false, false);
        	trashPile.add(takeFromPile(Cards.zombieApprentice));
        	trashPile.add(takeFromPile(Cards.zombieMason));
        	trashPile.add(takeFromPile(Cards.zombieSpy));
        }
        
        // If certain cards/events/ways are in play, we'll need Horses (non-supply)
        if (piles.containsKey(Cards.bargain.getName())
        		|| piles.containsKey(Cards.cavalry.getName())
        		|| piles.containsKey(Cards.demand.getName())
        		|| piles.containsKey(Cards.groom.getName())
        		|| piles.containsKey(Cards.hostelry.getName())
        		|| piles.containsKey(Cards.livery.getName())
        		|| piles.containsKey(Cards.paddock.getName())
        		|| piles.containsKey(Cards.ride.getName())
        		|| piles.containsKey(Cards.scrap.getName())
        		|| piles.containsKey(Cards.sleigh.getName())
        		|| piles.containsKey(Cards.stampede.getName())
        		|| piles.containsKey(Cards.supplies.getName())) {
            addPile(Cards.horse, 30, false);
        }

        // If Baker is in play, each player starts with one coin token on Coffers mat
        if (piles.containsKey(Cards.baker.getName()))
        {
            bakerInPlay = true;
        }
        
        // Setup for Landmarks starting with tokens
        Card[] landmarksWithTokens = {Cards.arena, Cards.basilica, Cards.battlefield, Cards.baths, Cards.colonnade, Cards.labyrinth};
        for (Card c : landmarksWithTokens) {
        	if (piles.containsKey(c.getName())) {
                addPileVpTokens(c, 6 * numPlayers, null);
            }
        }
        
        // Setup for Aqueduct
        if (piles.containsKey(Cards.aqueduct.getName())) {
        	addPileVpTokens(Cards.silver, 8, null);
            addPileVpTokens(Cards.gold, 8, null);
        }
        
        // Setup for Defiled Shrine
        if (piles.containsKey(Cards.defiledShrine.getName())) {
            for (CardPile pile : placeholderPiles.values()) {
                Card c = pile.placeholderCard();
        		if (pile.isSupply() && c.is(Type.Action) && !c.is(Type.Gathering)) {
        			addPileVpTokens(c, 2, null);
        		}
        	}
        }
        
        // Setup for Obelisk
        if (piles.containsKey(Cards.obelisk.getName())) {
        	if (obeliskCard == null) {
        		ArrayList<Card> validObeliskCards = new ArrayList<Card>();
            	for (String p : placeholderPiles.keySet()) {
                    CardPile pile = placeholderPiles.get(p);
                    Card placeholder = pile.placeholderCard();
            		if (pile.isSupply() && placeholder.is(Type.Action)  && !validObeliskCards.contains(placeholder)) {
            			validObeliskCards.add(placeholder);
            		}
            	}
            	if (validObeliskCards.size() > 0) {
            		obeliskCard = validObeliskCards.get(rand.nextInt(validObeliskCards.size()));
            	}
        	}
        }
        
        // Setup for Tax
        if (piles.containsKey(Cards.tax.getName())) {
        	for (String cardName : placeholderPiles.keySet()) {
        		Card c = piles.get(cardName).placeholderCard();
        		if (Cards.isSupplyCard(c)) {
        			addPileDebtTokens(c, 1, null);
        		}
        	}
        }
        
        // Setup for Way Of The Mouse
        if (piles.containsKey(Cards.wayOfTheMouse.getName())) {
        	if (wayOfTheMouseCard == null) {
//        		ArrayList<Card> validObeliskCards = new ArrayList<Card>();
//            	for (String p : placeholderPiles.keySet()) {
//                    CardPile pile = placeholderPiles.get(p);
//                    Card placeholder = pile.placeholderCard();
//            		if (pile.isSupply() && placeholder.is(Type.Action)  && !validObeliskCards.contains(placeholder)) {
//            			validObeliskCards.add(placeholder);
//            		}
//            	}
//            	if (validObeliskCards.size() > 0) {
//            		obeliskCard = validObeliskCards.get(rand.nextInt(validObeliskCards.size()));
//            	}
        	}
    		
    	}

        // If Ranger, Giant or Pilgrimage are in play, each player starts with a journey token faced up
        if (   piles.containsKey(Cards.ranger.getName())
            || piles.containsKey(Cards.giant.getName())
            || piles.containsKey(Cards.pilgrimage.getName()))
        {
            journeyTokenInPlay = true;
        } else {
        	journeyTokenInPlay = false;
        }

        boolean oldDebug = debug;
        if (!debug && !showEvents.isEmpty()) {
            debug = true;
        }
        Util.debug("");
        Util.debug("Cards in Play", true);
        Util.debug("---------------", true);
        cardListText += "Cards in play\n---------------\n";

        ArrayList<Card> cards = new ArrayList<Card>();
        ArrayList<Card> events = new ArrayList<Card>();
        ArrayList<Card> landmarks = new ArrayList<Card>();
        ArrayList<Card> ways = new ArrayList<Card>();
        for (CardPile pile : placeholderPiles.values()) {
        	Card c = pile.placeholderCard();
        	if (Cards.isKingdomCard(c)) {
        		cards.add(c);
        	} else if (Cards.eventsCards.contains(c)) {
        		events.add(c);
        	} else if (Cards.landmarkCards.contains(c)) {
        		landmarks.add(c);
        	} else if (Cards.wayCards.contains(c)) {
        		ways.add(c);
        	}
        }
        Collections.sort(cards, new Util.CardCostNameComparator());
        Collections.sort(events, new Util.CardCostNameComparator());
        Collections.sort(landmarks, new Util.CardCostNameComparator());
        Collections.sort(ways, new Util.CardCostNameComparator());
        
        for (Card c : cards) {
        	cardListText += Util.getShortText(c) + ((baneCard != null && c.equals(baneCard)) ? " (Bane)" + baneCard.getName() : "") + "\n";
        }
        if (!events.isEmpty()) {
        	 cardListText += "\nEvents in play\n---------------\n";
        	for (Card c : events) {
            	cardListText += Util.getShortText(c) + "\n";
            }
        }
        if (!landmarks.isEmpty()) {
        	cardListText += "\nLandmarks in play\n---------------\n";
        	for (Card c : landmarks) {
            	cardListText += c.getName() + (c.equals(Cards.obelisk) && obeliskCard != null ? " (" + obeliskCard.getName() + ")" : "") +  "\n";
            }
        }
        
        if (!ways.isEmpty()) {
        	cardListText += "\nWays in play\n---------------\n";
        	for (Card c : ways) {
        		cardListText += Util.getShortText(c) + "\n";
        		cardListText += c.getName() + (c.equals(Cards.wayOfTheMouse) && wayOfTheMouseCard != null ? " (" + wayOfTheMouseCard.getName() + ")" : "") +  "\n";
            }
        }
                
        for (Entry<String, CardPile> cEntry : piles.entrySet()) {
            if (cEntry.getKey().equals(cEntry.getValue().placeholderCard().getName())) {
                Util.debug(cEntry.getKey() + ": " + cEntry.getValue().cards.toString());
            } else {

            }
        }

        Util.debug("");
        debug = oldDebug;

        if (unfoundCards != null && unfoundCards.size() > 0) {
            unfoundCardText += "\n";
            String cardList = "";
            boolean first = true;
            for (String s : unfoundCards) {
                if (first) {
                    first = false;
                } else {
                    cardList += "\n";
                }
                cardList += s;
            }
            cardList += "\n\n";
            unfoundCardText += "The following cards are not \navailable, so replacements \nhave been used:\n" + cardList;
        }

        // context = new MoveContext(this, null);
        // context.message = "" + ((int) chance * 100) + "% - " +
        // (platInPlay?"Yes":"No");
        // broadcastEvent(new GameEvent(GameEvent.Type.PlatAndColonyChance,
        // context));
    }

    protected void initGameListener() {
        listeners.clear();

        gameListener = new GameEventListener() {

            public void gameEvent(GameEvent event) {
                handleShowEvent(event);

                if (event.getType() == GameEvent.EventType.GameStarting || event.getType() == GameEvent.EventType.GameOver) {
                    return;
                }

                if ((event.getType() == GameEvent.EventType.CardObtained) &&
                		!(event.card.is(Type.Event) || event.card.is(Type.Project))) {
                	
                    MoveContext context = event.getContext();
                    Player player = context.getPlayer();

                    if (player.isPossessed()) {
                        possessedBoughtPile.add(event.card);
                        MoveContext controlContext = new MoveContext(context.game, context.getPlayer().controlPlayer);
                        controlContext.getPlayer().gainCardAlreadyInPlay(event.card, Cards.possession, controlContext);
                        return;
                    }

                    if (context != null && event.card.is(Type.Victory, context.player)) {
                        context.vpsGainedThisTurn += event.card.getVictoryPoints();
                    }
                                       
                    if (Cards.inn.equals(event.responsible))
                        Util.debug((String.format("discard pile: %d", player.discard.size())), true);

                    // See rules explanation of Tunnel for what commandedDiscard means.
                    boolean commandedDiscard = true;
                    if(event.getType() == GameEvent.EventType.CardObtained) {
                        commandedDiscard = false;
                    } else if(event.responsible != null) {
                        Card r = event.responsible;
                        if(r.equals(Cards.borderVillage) ||
                           r.equals(Cards.feast) ||
                           r.equals(Cards.remodel) ||
                           r.equals(Cards.swindler) ||
                           r.equals(Cards.ironworks) ||
                           r.equals(Cards.saboteur) ||
                           r.equals(Cards.upgrade) ||
                           r.equals(Cards.ambassador) ||
                           r.equals(Cards.smugglers) ||
                           r.equals(Cards.talisman) ||
                           r.equals(Cards.expand) ||
                           r.equals(Cards.forge) ||
                           r.equals(Cards.remake) ||
                           r.equals(Cards.hornOfPlenty) ||
                           r.equals(Cards.jester) ||
                           r.equals(Cards.develop) ||
                           r.equals(Cards.haggler) ||
                           r.equals(Cards.workshop) ||
                           r.equals(Cards.hermit) ||
                           r.equals(Cards.dameNatalie))
                        {
                            commandedDiscard = false;
                        }
                    }
                    boolean handled = false;

                    //Not sure if this is exactly right for the Trader, but it seems to be based on detailed card explanation in the rules
                    //The handling for new cards is done before taking the card from the pile in a different method below.
                    if(!event.newCard) {
                        boolean hasTrader = context.getPlayer().hand.contains(Cards.trader);
                        if(hasTrader) {
                            if(player.controlPlayer.trader_shouldGainSilverInstead((MoveContext) context, event.card)) {
                                player.reveal(Cards.trader, null, context);
                                player.trash(event.card, Cards.trader, (MoveContext) context);
                                event.card = Cards.silver;
                                player.gainNewCard(Cards.silver, Cards.trader, context);
                                return;
                            }
                        }
                    }

                    if (event.getPlayer() == players[playersTurn] && context.phase == TurnPhase.Buy) {
                        context.totalCardsGainedInMostRecentBuyPhase++;
                    }

                    if (event.getPlayer() == players[playersTurn]) {
                        cardsObtainedLastTurn[playersTurn].add(event.card);
                    }
                    allCardsObtainedLastTurn[playersTurn].add(event.card);
                    
                    if (cardsObtainedLastTurn[playersTurn].size() == 2) {
                    	if (cardInGame(Cards.labyrinth) && player == players[playersTurn] && (!player.isPossessed() || Game.errataPossession == PossessionPossessorTokens.ALL)) {
                    		int tokensLeft = getPileVpTokens(Cards.labyrinth);
                    		if (tokensLeft > 0) {
                    			int tokensToTake = Math.min(tokensLeft, 2);
                    			removePileVpTokens(Cards.labyrinth, tokensToTake, context);
                    			player.addVictoryTokens(context, tokensToTake, Cards.labyrinth);
                    		}
                    	}
                    }
                    
                    if (cardInGame(Cards.changeling) && Util.costsEqualOrMore(context, event.card, 3, 0, 0) && context.isCardOnTop(Cards.changeling)) {
                    	CardPile pile = getPile(event.card);
                    	// can't exchange for cards that don't have a real pile associated with them since they can't be returned to their pile
                    	if (pile.isRealPile() && player.controlPlayer.changeling_shouldExchange(context, event.card)) {
                            pile.addCard(event.card);
                            player.discard.add(takeFromPile(Cards.changeling));
                            GameEvent exchangedEvent = new GameEvent(GameEvent.EventType.TravellerExchanged, context);
                            exchangedEvent.card = Cards.changeling;
                            exchangedEvent.responsible = event.card;
                            exchangedEvent.setPlayer(player);
                            context.game.broadcastEvent(exchangedEvent);
                            handled = true;
                    	}
                    }

                    if (!handled && context.getPlayer().hand.contains(Cards.trader) && !event.card.equals(Cards.silver)) {
                        CardPile pile = getPile(event.card);
                        if (pile.isRealPile() && context.player.controlPlayer.trader_shouldGainSilverInstead(context, event.card)) {
                            context.player.reveal(Cards.trader, Cards.trader, context);
                            pile.addCard(event.card);
                            player.discard.add(takeFromPile(Cards.silver));
                            GameEvent exchangedEvent = new GameEvent(GameEvent.EventType.TravellerExchanged, context);
                            exchangedEvent.card = Cards.silver;
                            exchangedEvent.responsible = event.card;
                            exchangedEvent.setPlayer(player);
                            context.game.broadcastEvent(exchangedEvent);
                            handled = true;
                        }
                    }

                    if (!handled && context.cargoShipsEffectsPending.size() > 0) {
                    	if (player.controlPlayer.cargoShip_shouldSetAside(context, event.card)) {
                    		player.cargoShip.add(event.card);
                    		player.addStartTurnDurationEffect(context.cargoShipsEffectsPending.remove(0));
                    		GameEvent setAsideEvent = new GameEvent(GameEvent.EventType.CardSetAside, context);
                    		setAsideEvent.card = event.card;
                    		setAsideEvent.responsible = Cards.cargoShip;
                    		setAsideEvent.setPlayer(player);
                            context.game.broadcastEvent(setAsideEvent);
                    		handled = true;
                    	}
                    }
                    
                    if (!handled && player.getCardInHand(Cards.sleigh) != null) {
                    	SleighOption option = player.controlPlayer.sleigh_discardOption(context, event.card);
                    	if (option != null && option != SleighOption.Pass) {
                    		Card sleigh = player.hand.remove(player.hand.indexOf(Cards.sleigh));
                    		player.discard(sleigh, Cards.sleigh, context);
                    		if (option == SleighOption.DiscardForGainedCardToHand) {
                    			player.hand.add(event.card);
                    		} else if (option == SleighOption.DiscardForGainedCardToDeck) {
                    			player.putOnTopOfDeck(event.card);
                    			GameEvent deckEvent = new GameEvent(GameEvent.EventType.CardOnTopOfDeck, context);
                    			deckEvent.card = event.card;
                    			deckEvent.responsible = Cards.sleigh;
                                context.game.broadcastEvent(deckEvent);
                    		}
                    		handled = true;
                    	}
                    }
                    
                    if (!handled && player.hasProject(Cards.innovation) && context.game.getCurrentPlayer() == player &&
                    		event.card.is(Type.Action) && context.game.getActionCardsObtainedByPlayer().size() == 1) {
                    	//TODO: technically you should be able to order the when-gain abilities
                    	//      either play it before or after exercising the other when-gain abilities
                    	//      that don't move it
                    	//TODO: set aside and play
                    	// Don't have to technically set aside here since we don't handle when-gain lose-track correctly, anyway
                    	if (player.controlPlayer.innovation_shouldSetAsideToPlay(context, event.card)) {
                    	    event.card.play(context.game, context, false, false, false);
                    		handled = true;
                    	}
                    }
                    
                    if (player.hasProject(Cards.academy) && event.card.is(Type.Action, player)) {                    
                    	context.player.takeVillagers(1, context, Cards.academy);
                    }
                    
                    if (player.hasProject(Cards.guildhall) && event.card.is(Type.Treasure, player, context)) {                    
                    	context.player.gainGuildsCoinTokens(1, context, Cards.guildhall);
                    }
                    
                    if (context.liveryEffects > 0 && event.card.getCost(context) >= 4) {
                    	int liverys = context.liveryEffects; 
                    	for(int i = 0; i < liverys; ++ i) {
                    		context.player.gainNewCard(Cards.horse, Cards.livery, context);
                    	}
                    }

                    boolean hasWatchtower = player.hand.contains(Cards.watchTower);
                    if (!handled && hasWatchtower) {
                        WatchTowerOption choice = context.player.controlPlayer.watchTower_chooseOption((MoveContext) context, event.card);

                        if (choice == WatchTowerOption.TopOfDeck) {
                            handled = true;
                            GameEvent watchTowerEvent = new GameEvent(GameEvent.EventType.CardRevealed, context);
                            watchTowerEvent.card = Cards.watchTower;
                            watchTowerEvent.responsible = null;
                            context.game.broadcastEvent(watchTowerEvent);

                            player.putOnTopOfDeck(event.card, context, true);
                        } else if (choice == WatchTowerOption.Trash) {
                            handled = true;
                            GameEvent watchTowerEvent = new GameEvent(GameEvent.EventType.CardRevealed, context);
                            watchTowerEvent.card = Cards.watchTower;
                            watchTowerEvent.responsible = null;
                            context.game.broadcastEvent(watchTowerEvent);

                            player.trash(event.card, Cards.watchTower, context);
                        }
                    }
                    
                    if (!handled && context.game.gatekeeperAttacks(context.player)) {
                    	if ((event.card.is(Type.Action, context.player, context) ||
                    			event.card.is(Type.Treasure, context.player, context)) &&
                    			!player.hasCopyInExile(event.card)) {
                    		player.exile(event.card, Cards.gatekeeper, context);
                    		handled = true;
                    	}
                    }
                    
                    Card gainedCardAbility = event.card;

                    if(!handled) {
                    	//handle gaining to particular locations
                    	if (context.isRoyalSealInPlay() && context.player.controlPlayer.royalSealTravellingFairTracker_shouldPutCardOnDeck((MoveContext) context, Cards.royalSeal, event.card)) {
                            player.putOnTopOfDeck(event.card, context, true);
                    	} else if (context.travellingFairBought && context.player.controlPlayer.royalSealTravellingFairTracker_shouldPutCardOnDeck((MoveContext) context, Cards.travellingFair, event.card)) {
                    		player.putOnTopOfDeck(event.card, context, true);
                        } else if (context.wayOfTheSealPlayed && context.player.controlPlayer.royalSealTravellingFairTracker_shouldPutCardOnDeck((MoveContext) context, Cards.wayOfTheSeal, event.card)) {
                    		player.putOnTopOfDeck(event.card, context, true);
                        } else if ((context.countCardsInPlay(Cards.tracker) > 0) && context.player.controlPlayer.royalSealTravellingFairTracker_shouldPutCardOnDeck((MoveContext) context, Cards.tracker, event.card)) {
                    		player.putOnTopOfDeck(event.card, context, true);
                        } else if (event.responsible != null && event.responsible.equals(Cards.summon)) {
                        	context.player.summon.add(event.card);
        					GameEvent summonEvent = new GameEvent(GameEvent.EventType.CardSetAside, context);
        					summonEvent.card = event.card;
        					summonEvent.responsible = event.responsible;
        					context.game.broadcastEvent(summonEvent);
                        } else if (event.responsible != null && event.responsible.equals(Cards.reap)) {
                        	context.player.reap.add(event.card);
        					GameEvent setAsideEvent = new GameEvent(GameEvent.EventType.CardSetAside, context);
        					setAsideEvent.card = event.card;
        					setAsideEvent.responsible = event.responsible;
        					context.game.broadcastEvent(setAsideEvent);
                        } else if (gainedCardAbility.equals(Cards.nomadCamp)) {
                            player.putOnTopOfDeck(event.card, context, true);
                        } else if (gainedCardAbility.equals(Cards.ghostTown) ||
                        		gainedCardAbility.equals(Cards.denOfSin) ||
                        		gainedCardAbility.equals(Cards.guardian) ||
                        		gainedCardAbility.equals(Cards.nightWatchman)) {
                        	player.hand.add(event.card);
                        } else if (gainedCardAbility.equals(Cards.villa)) {
                        	player.hand.add(event.card);
                        } else if (event.responsible != null) {
                        	// handle custom gain locations based on what ability is gaining
                            Card r = event.responsible;
                            if (r.equals(Cards.armory)
                                || r.equals(Cards.artificer)
                                || r.equals(Cards.bagOfGold)
                                || r.equals(Cards.bureaucrat)
                                || r.equals(Cards.develop)
                                || r.equals(Cards.foolsGold)
                                || r.equals(Cards.graverobber) && context.graverobberGainedCardOnTop == true
                                || r.equals(Cards.greed)
                                || r.equals(Cards.seaHag)
                                || r.equals(Cards.supplies)
                                || r.equals(Cards.taxman)
                                || r.equals(Cards.tournament)
                                || r.equals(Cards.treasureMap)
                                || r.equals(Cards.stampede)
                                || r.equals(Cards.demand)
                                || r.equals(Cards.replace) && context.attackedPlayer != player && (gainedCardAbility.is(Type.Action) || gainedCardAbility.is(Type.Treasure, null, context))) {
                                player.putOnTopOfDeck(event.card, context, true);
                            } else if (r.equals(Cards.beggar)) {
                                if (event.card.equals(Cards.copper)) {
                                    player.hand.add(event.card);
                                } else if (event.card.equals(Cards.silver) && context.beggarSilverIsOnTop++ == 0) {
                                    player.putOnTopOfDeck(event.card, context, true);
                                } else if (event.card.equals(Cards.silver)) {
                                    player.discard.add(event.card);
                                }
                            } else if (r.equals(Cards.tradingPost) || r.equals(Cards.mine) || r.equals(Cards.explorer) || 
                            		r.equals(Cards.torturer) || r.equals(Cards.transmogrify) || r.equals(Cards.artisan) || 
                            		r.equals(Cards.cobbler) || r.equals(Cards.plague) || r.equals(Cards.wish) || 
                            		r.equals(Cards.sculptor) || r.equals(Cards.treasurer) || r.equals(Cards.falconer)) {
                                player.hand.add(event.card);
                            } else if (r.equals(Cards.illGottenGains) && event.card.equals(Cards.copper)) {
                                player.hand.add(event.card);
                            } else if (r.equals(Cards.rocks)) {
                            	if (context.phase == TurnPhase.Buy && context.game.getCurrentPlayer() == player) {
                            		player.putOnTopOfDeck(event.card, context, true);
                            	} else {
                            		player.hand.add(event.card);
                            	}
                            } else {
                                player.discard(event.card, null, null, commandedDiscard, false);
                            }
                        } else {
                            player.discard(event.card, null, null, commandedDiscard, false);
                        }
                    }
                    
                    // check for when-gain callable cards
                    // NOTE: Technically this should be done in a loop, as you can call multiple cards for one when-gain.
                    //   However, since the only card here, Duplicate, will trigger another on-gain anyway
                    //   we don't need to.
                    ArrayList<Card> callableCards = new ArrayList<Card>();
                    for (Card c : player.tavern) {
                    	if (c.isCallableWhenCardGained()) {
                    		int callCost = c.getCallableWhenGainedMaxCost();
                    		if (callCost == -1 || (event.card.getCost(context) <= callCost && event.card.getDebtCost(context) == 0 && !event.card.costPotion())) {
                    			callableCards.add(c);
                    		}
                    	}
                    }
                    if (!callableCards.isEmpty()) {
                    	//ask player which card to call
                    	Collections.sort(callableCards, new Util.CardCostComparator());
                    	// we want null entry at the end for None
                    	Card[] cardsAsArray = callableCards.toArray(new Card[callableCards.size() + 1]);
                    	Card toCall = player.controlPlayer.call_whenGainCardToCall(context, event.card, cardsAsArray);
                    	if (toCall != null || callableCards.contains(toCall)) {
                    		toCall.callWhenCardGained(context, event.card);
                    	}
                    }
                    
                    if (event.card.equals(Cards.province) && !firstProvinceWasGained) {
                    	doMountainPassAfterThisTurn = true;
                    	firstProvinceWasGained = true;
                    	firstProvinceGainedBy = playersTurn;
                    }
                    
                    if(event.card.is(Type.Treasure, player, context)) {
                    	if (cardInGame(Cards.aqueduct)) {
                    		//TODO?: you can technically choose the order of resolution for moving the VP
                    		//       tokens from the treasure after taking the tokens, but why would you ever do this?
                    		int tokensLeft = getPileVpTokens(event.card);
                    		if (tokensLeft > 0) {
                    			removePileVpTokens(event.card, 1, context);
                    			addPileVpTokens(Cards.aqueduct, 1, context);
                    		}
                    	}
                    }
                    if(event.card.is(Type.Victory, player)) {
                    	if (cardInGame(Cards.battlefield)) {
                    		int tokensLeft = getPileVpTokens(Cards.battlefield);
                    		if (tokensLeft > 0) {
                    			int tokensToTake = Math.min(tokensLeft, 2);
                    			removePileVpTokens(Cards.battlefield, tokensToTake, context);
                    			player.addVictoryTokens(context, tokensToTake, Cards.battlefield);
                    		}
                    	}
                    	if (cardInGame(Cards.aqueduct)) {
                    		int tokensLeft = getPileVpTokens(Cards.aqueduct);
                    		if (tokensLeft > 0) {
                    			removePileVpTokens(Cards.aqueduct, tokensLeft, context);
                    			player.addVictoryTokens(context, tokensLeft, Cards.aqueduct);
                    		}
                    	}
                    	int groundsKeepers = context.countCardsInPlay(Cards.groundskeeper);
                    	player.addVictoryTokens(context, groundsKeepers, Cards.groundskeeper);
                    	
                    	for (Player otherPlayer : context.game.getPlayersInTurnOrder()) {
                            if (player == otherPlayer)
                            	continue;
                            MoveContext otherPlayerContext = new MoveContext(context.game, otherPlayer);
                            if (otherPlayer.hasProject(Cards.roadNetwork)) {
                            	context.game.drawToHand(otherPlayerContext, Cards.roadNetwork, 1, new PlayContext());
                            }
                            while(otherPlayer.getCardInHand(Cards.blackCat) != null && otherPlayer.controlPlayer.blackCat_shouldPlay(otherPlayerContext)) {
                            	otherPlayer.getCardInHand(Cards.blackCat).play(context.game, otherPlayerContext, true);
                            }
                    	}
                    }

                    player.checkOtherPlayerInvest(context.game, event.card);
                    
                    // handle other when-gain abilities
                    
                    if (gainedCardAbility.equals(Cards.illGottenGains)) {
                        for(Player targetPlayer : getPlayersInTurnOrder()) {
                            if(targetPlayer != player) {
                                MoveContext targetContext = new MoveContext(Game.this, targetPlayer);
                                targetPlayer.gainNewCard(Cards.curse, event.card, targetContext);
                            }
                        }
                    } else if(event.card.equals(Cards.province)) {
                        for(Player targetPlayer : getPlayersInTurnOrder()) {
                            if(targetPlayer != player) {
                                int foolsGoldCount = 0;
                                // Check all of the cards, not just for existence, since there may be more than 1
                                for(Card c : targetPlayer.hand) {
                                    if(c.equals(Cards.foolsGold)) {
                                        foolsGoldCount++;
                                    }
                                }

                                while(foolsGoldCount-- > 0) {
                                    MoveContext targetContext = new MoveContext(Game.this, targetPlayer);
									FoolsGoldOption option = targetPlayer.controlPlayer.foolsGold_chooseOption(targetContext);
                                    if(option == FoolsGoldOption.TrashForGold) {
                                        targetPlayer.trashFromHand(Cards.foolsGold, Cards.foolsGold, targetContext);
                                        targetPlayer.gainNewCard(Cards.gold, Cards.foolsGold, targetContext);
                                    } else if (option == FoolsGoldOption.PassAll) {
										break;
									}
                                }
                            }
                        }
                    } else if(event.card.equals(Cards.duchy)) {
                        if (Cards.isSupplyCard(Cards.duchess) && isCardOnTop(Cards.duchess)) {
                            if(player.controlPlayer.duchess_shouldGainBecauseOfDuchy((MoveContext) context)) {
                                player.gainNewCard(Cards.duchess, Cards.duchess, context);
                            }
                        }
                    } else if(gainedCardAbility.equals(Cards.embassy)) {
                        for(Player targetPlayer : getPlayersInTurnOrder()) {
                            if(targetPlayer != player) {
                                MoveContext targetContext = new MoveContext(Game.this, targetPlayer);
                                targetPlayer.gainNewCard(Cards.silver, event.card, targetContext);
                            }
                        }
                    } else if(gainedCardAbility.equals(Cards.cache)) {
                        for(int i=0; i < 2; i++) {
                            player.gainNewCard(Cards.copper, event.card, context);
                        }
                    } else if(gainedCardAbility.equals(Cards.inn)) {
                        ArrayList<Card> cards = new ArrayList<Card>();
                        int actionCardsFound = 0;
                        for(int i=player.discard.size() - 1; i >= 0; i--) {
                            Card c = player.discard.get(i);
                            if(c.is(Type.Action, player)) {
                                actionCardsFound++;
                                if(player.controlPlayer.inn_shuffleCardBackIntoDeck(event.getContext(), c)) {
                                    cards.add(c);
                                }
                            }
                        }

                        Util.debug((String.format("Inn: %d action(s) found in %d-card discard pile", actionCardsFound, player.discard.size())), true);
                        if (cards.size() > 0) {
                            for(Card c : cards) {
                                player.discard.remove(c);
                                player.deck.add(c);
                            }
                        }
                        player.shuffleDeck(context, Cards.inn);
                    } else if (gainedCardAbility.equals(Cards.borderVillage)) {
                        boolean validCard = false;
                        int gainedCardCost = event.card.getCost(context);
                        for(Card c : event.context.getCardsInGame(GetCardsInGameOptions.TopOfPiles, true)) {
                            if(Cards.isSupplyCard(c) && c.getCost(context) < gainedCardCost && !c.costPotion() && c.getDebtCost(context) <= 0 && event.context.isCardOnTop(c)) {
                                validCard = true;
                                break;
                            }
                        }

                        if(validCard) {
                            Card card = context.player.controlPlayer.borderVillage_cardToObtain(context, gainedCardCost - 1);
                            if (card != null) {
                                if(card.getCost(context) < gainedCardCost && card.getDebtCost(context) == 0 && !card.costPotion()) {
                                    player.gainNewCard(card, event.card, (MoveContext) context);
                                }
                                else {
                                    Util.playerError(player, "Border Village returned invalid card, ignoring.");
                                }
                            }
                        }
                    } else if (gainedCardAbility.equals(Cards.mandarin)) {
                        CardList playedCards = context.getPlayedCards();
                        ArrayList<Card> treasureCardsInPlay = new ArrayList<Card>();

                        for(Card c : playedCards) {
                            if(c.is(Type.Treasure, player, context)) {
                                treasureCardsInPlay.add(c);
                            }
                        }
                        
                        if(treasureCardsInPlay.size() > 0) {
                            Card[] order ;
                            if (treasureCardsInPlay.size() == 1)
                                order = treasureCardsInPlay.toArray(new Card[treasureCardsInPlay.size()]);
                            else
                                order = player.controlPlayer.mandarin_orderCards(context, treasureCardsInPlay.toArray(new Card[treasureCardsInPlay.size()]));

                            for (int i = order.length - 1; i >= 0; i--) {
                                Card c = order[i];
                                player.putOnTopOfDeck(c);
                                playedCards.remove(c); 
                            }
                        }
                    } else if (gainedCardAbility.equals(Cards.deathCart)) {
                        context.player.controlPlayer.gainNewCard(Cards.virtualRuins, event.card, context);
                        context.player.controlPlayer.gainNewCard(Cards.virtualRuins, event.card, context);
                    } else if (gainedCardAbility.equals(Cards.lostCity)) {
                        for(Player targetPlayer : getPlayersInTurnOrder()) {
                            if(targetPlayer != player) {
                                drawToHand(new MoveContext(Game.this, targetPlayer), Cards.lostCity, 1, true, new PlayContext());
                            }
                        }
                    } else if (gainedCardAbility.equals(Cards.emporium)) {
                    	if (context.countActionCardsInPlay() >= 5) {
                    		player.addVictoryTokens(context, 2, Cards.emporium);
                    	}
                    } else if (gainedCardAbility.equals(Cards.fortune)) {
                    	int gladiators = context.countCardsInPlayByName(Cards.gladiator);
                    	for (int i = 0; i < gladiators; ++i) {
                    		player.gainNewCard(Cards.gold, event.card, context);
                    	}
                    } else if (gainedCardAbility.equals(Cards.rocks)) {
                    	player.gainNewCard(Cards.silver, event.card, context);
                    } else if (gainedCardAbility.equals(Cards.crumblingCastle)) {
                    	player.addVictoryTokens(context, 1, Cards.crumblingCastle);
                    	player.gainNewCard(Cards.silver, event.card, context);
                    } else if (gainedCardAbility.equals(Cards.hauntedCastle) && context.game.getCurrentPlayer() == player) {
                    	player.gainNewCard(Cards.gold, event.card, context);
                    	for (Player targetPlayer : context.game.getPlayersInTurnOrder()) {
                    		if (targetPlayer == player) continue;
                    		if (targetPlayer.hand.size() >= 5) {
                    			MoveContext playerContext = new MoveContext(context.game, targetPlayer);
                                playerContext.attackedPlayer = targetPlayer;
                                Card[] cards = targetPlayer.controlPlayer.hauntedCastle_gain_cardsToPutBackOnDeck(playerContext);
                                boolean bad = false;
                                if (cards == null || cards.length == 2) {
                                    bad = true;
                                } else {
                                    ArrayList<Card> copy = Util.copy(targetPlayer.hand);
                                    for (Card card : cards) {
                                        if (!copy.remove(card)) {
                                            bad = true;
                                            break;
                                        }
                                    }
                                }
                                if (bad) {
                                    Util.playerError(targetPlayer, "Haunted Castle put back cards error, putting back the first 2 cards.");
                                    cards = new Card[2];
                                    cards[0] = targetPlayer.hand.get(0);
                                    cards[1] = targetPlayer.hand.get(1);
                                }                                
                                GameEvent topDeckEvent = new GameEvent(GameEvent.EventType.CardOnTopOfDeck, playerContext);
                                topDeckEvent.setPlayer(targetPlayer);
                                for (int i = cards.length - 1; i >= 0; i--) {
                                	targetPlayer.hand.remove(cards[i]);
                                	targetPlayer.putOnTopOfDeck(cards[i]);
                                	playerContext.game.broadcastEvent(topDeckEvent);
                                }
                            }
                    	}
                    } else if (gainedCardAbility.equals(Cards.temple)) {
                    	int numTokens = context.game.getPileVpTokens(Cards.temple);
                    	context.game.removePileVpTokens(Cards.temple, numTokens, context);
                    	player.addVictoryTokens(context, numTokens, Cards.temple);
                    } else if (gainedCardAbility.equals(Cards.sprawlingCastle)) {
                    	int duchyCount = context.game.getPile(Cards.duchy).getCount();
                        int estateCount = context.game.getPile(Cards.estate).getCount();
                    	if (duchyCount == 0 && estateCount == 0) return;
                        
                        Player.HuntingGroundsOption option = context.player.controlPlayer.sprawlingCastle_chooseOption(context);
                        if (option == null) option = HuntingGroundsOption.GainEstates;
                        switch (option) {
                            case GainDuchy:
                            	context.player.controlPlayer.gainNewCard(Cards.duchy, event.card, context);
                                break;
                            case GainEstates:
                            	context.player.controlPlayer.gainNewCard(Cards.estate, event.card, context);
                                context.player.controlPlayer.gainNewCard(Cards.estate, event.card, context);
                                context.player.controlPlayer.gainNewCard(Cards.estate, event.card, context);
                                break;
                            default:
                                break;
                        }
                    } else if (gainedCardAbility.equals(Cards.grandCastle)) {
                    	int victoryCards = 0;
                        for(Card c : player.getHand()) {
                            player.reveal(c, event.card, context);
                            if(c.is(Type.Victory, player)) {
                                victoryCards++;
                            }
                        }

                        for (Player opponent : context.game.getPlayersInTurnOrder()) {
                            if (opponent != player) {
                                for (Card c : opponent.playedCards) {
                                    if (c.is(Type.Victory, opponent)) {
                                        victoryCards++;
                                    }
                                }
                            }
                        }

                        victoryCards += context.countVictoryCardsInPlay();

                        player.addVictoryTokens(context, victoryCards, Cards.grandCastle);
                    } else if (gainedCardAbility.equals(Cards.villa)) {
                    	if (context.game.getCurrentPlayer() == player) {
							context.addActions(1, Cards.villa);
							if (context.phase == TurnPhase.Buy) {
								context.returnToActionPhase = true;
							}
                    	}
                    } else if (gainedCardAbility.equals(Cards.cavalry)) {
                    	if (context.game.getCurrentPlayer() == player) {
                    	    PlayContext drawContext = new PlayContext();
                    		for (int i = 0; i < 2; ++i) {
                    			context.game.drawToHand(context, Cards.cavalry, 2 - i, drawContext);
                    		}
							context.buys++;
							if (context.phase == TurnPhase.Buy) {
								context.returnToActionPhase = true;
							}
                    	}
                    } else if(gainedCardAbility.equals(Cards.cemetery)) {
                    	Card[] cards = player.controlPlayer.cemetery_cardsToTrash(context);
                        if (cards != null) {
                            if (cards.length > 4) {
                                Util.playerError(player, "Cemetery trash error, trying to trash too many cards, ignoring.");
                            } else {
                                for (Card card : cards) {
                                    for (int i = 0; i < player.hand.size(); i++) {
                                        Card playersCard = player.hand.get(i);
                                        if (playersCard.equals(card)) {
                                            player.trashFromHand(playersCard, Cards.cemetery, context);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } else if (gainedCardAbility.equals(Cards.blessedVillage)) {
                    	Card boon = context.game.takeNextBoon(context, event.card);
                    	                        
                        boolean receiveNow = context.player.controlPlayer.blessedVillage_shouldReceiveNow(context, boon);
                        if (receiveNow) {
                        	context.game.recieveBoonAndDiscard(context, boon, event.card);
                        } else {
                        	player.nextTurnBoons.add(boon);
                        }
                    } else if(gainedCardAbility.equals(Cards.cursedVillage)) {
                    	context.game.receiveNextHex(context, event.card);
                    } else if(gainedCardAbility.equals(Cards.skulk)) {
                    	player.gainNewCard(Cards.gold, event.card, context);
                    } else if (gainedCardAbility.equals(Cards.experiment)) {
                    	if (!(event.responsible != null && (event.responsible.equals(Cards.experiment))))
                    		player.gainNewCard(Cards.experiment, event.card, context);
                    } else if (gainedCardAbility.equals(Cards.ducat) && player.hand.contains(Cards.copper)) {
                    	boolean trashCopper = context.player.controlPlayer.ducat_shouldTrashCopper(context);
                        if (trashCopper) {
                        	context.player.trashFromHand(context.player.hand.get(Cards.copper), Cards.ducat, context);
                        }
                    } else if (gainedCardAbility.equals(Cards.silkMerchant)) {
                    	context.player.gainGuildsCoinTokens(1, context, Cards.silkMerchant);
                    	context.player.takeVillagers(1, context, Cards.silkMerchant);
                    } else if (gainedCardAbility.equals(Cards.flagBearer)) {
                    	context.game.takeSharedState(context, Cards.flag);
                    } else if (gainedCardAbility.equals(Cards.lackeys)) {
                    	context.player.takeVillagers(2, context, Cards.lackeys);
                    } else if (gainedCardAbility.equals(Cards.spices)) {
                    	context.player.gainGuildsCoinTokens(2, context, Cards.spices);
                    } else if(gainedCardAbility.equals(Cards.camelTrain)) {
                    	player.exileFromSupply(Cards.gold, event.card, context);
                    } else if (gainedCardAbility.equals(Cards.hostelry)) {
                    	int numTreasures = 0;
                    	for(Card c : player.hand) {
                    		if (c.is(Type.Treasure))
                    			numTreasures++;
                    	}
                    	if (numTreasures > 0) {
                    		Card[] cards = player.controlPlayer.hostelry_treasuresToDiscard(context);
                    		if (cards != null) {
	                    		for (Card card : cards) {
	                    			if (!card.is(Type.Treasure, player)) {
	                    				Util.playerError(player, "Hostelry choice error, trying to discard non-treasure cards, ignoring.");
	                    				cards = null;
	                    				break;
	                    			}
	                    		}
                    		}
                    		if (cards != null) {
                    			ArrayList<Card> toDiscard = new ArrayList<Card>();
                    			int numberOfCards = 0;
                    			for (Card card : cards) {
                    				for (int i = 0; i < player.hand.size(); i++) {
                    					Card playersCard = player.hand.get(i);
                    					if (playersCard.equals(card)) {
                    						player.hand.remove(card);
                    						player.reveal(card, Cards.hostelry, context);
                    						toDiscard.add(card);
                    						numberOfCards++;
                    						break;
                    					}
                    				}
                    			}
                    			for(Card c : toDiscard) {
                    				player.discard(c, Cards.hostelry, context);
                    			}

                    			if (numberOfCards != cards.length) {
                    				Util.playerError(player, "Hostelry discard error, trying to discard cards not in hand, ignoring extra.");
                    			}

                    			for(int i = 0; i < numberOfCards; ++i) {
                    				player.gainNewCard(Cards.horse, Cards.hostelry, context);
                    			}
                    		}
                    	}
                    }
                    
                    if(event.card.is(Type.Action, player)) {
                    	if (cardInGame(Cards.defiledShrine)) {
                    		//TODO?: you can technically choose the order of resolution for moving the VP
                    		//       tokens from the action to before taking the ones from Temple when it, 
                    		//       but why would you ever do this outside of old possession rules?
                    		int tokensLeft = getPileVpTokens(event.card);
                    		if (tokensLeft > 0) {
                    			removePileVpTokens(event.card, 1, context);
                    			addPileVpTokens(Cards.defiledShrine, 1, context);
                    		}
                    	}
                    }
                    
                    if (context.player.hasCopyInExile(event.card)) {
                    	int numCopies = context.player.countCopiesInExile(event.card);
                    	if (context.player.controlPlayer.gain_shouldDiscardFromExile(context, event.card, numCopies)) {
                    		context.player.discardCopiesFromExile(event.card, context);
                    	}
                    }
                    
                    //TODO: order of these abilities should be able to be interleaved with other when-gains, technically
                    while(player.getCardInHand(Cards.sheepdog) != null && player.controlPlayer.sheepdog_shouldPlay(context)) {
                    	player.getCardInHand(Cards.sheepdog).play(context.game, context, true);
                    }
                    if (event.card.getNumberOfTypes(player) >= 2) {
	                    for (Player otherPlayer : context.game.getPlayersInTurnOrder()) {
	                        MoveContext otherPlayerContext = (player == otherPlayer) ? context : new MoveContext(context.game, otherPlayer);
	                        while(otherPlayer.getCardInHand(Cards.falconer) != null && otherPlayer.controlPlayer.falconer_shouldPlay(otherPlayerContext)) {
	                        	otherPlayer.getCardInHand(Cards.falconer).play(context.game, otherPlayerContext, true);
	                        }
	                	}
	                }
                }

                boolean shouldShow = (debug || junit);
                if (!shouldShow) {
                    if (event.getType() != GameEvent.EventType.TurnBegin && event.getType() != GameEvent.EventType.TurnEnd
                        && event.getType() != GameEvent.EventType.DeckReplenished && event.getType() != GameEvent.EventType.GameStarting) {
                        shouldShow = true;
                    }
                }

                if (!showEvents.contains(event.getType()) && shouldShow) {
                    StringBuilder msg = new StringBuilder();
                    msg.append(event.getPlayer().getPlayerName() + ":" + event.getType());
                    if (event.card != null) {
                        msg.append(":" + event.card.getName());
                    }
                    if (event.getType() == GameEvent.EventType.TurnBegin && event.getPlayer().isPossessed()) {
                        msg.append(" possessed by " + event.getPlayer().controlPlayer.getPlayerName() + "!");
                    }
                    if (event.attackedPlayer != null) {
                        msg.append(", attacking:" + event.attackedPlayer.getPlayerName());
                    }

                    if (event.getType() == GameEvent.EventType.BuyingCard) {
                        msg.append(" (with gold: " + event.getContext().getCoinAvailableForBuy() + ", buys remaining: " + event.getContext().getBuysLeft());
                    }
                    Util.debug(msg.toString(), true);
                }
            }

        };
    }

    boolean hasMoat(Player player) {
        for (Card card : player.hand) {
            if (card.equals(Cards.moat)) {
                return true;
            }
        }

        return false;
    }

    boolean hasLighthouse(Player player) {
    	for (Card card : player.playedCards) {
            if (card.equals(Cards.lighthouse))
                return true;
        }
        return false;
    }

    /*
       Note that any cards in the supply can have Embargo coins added.
       This includes the basic seven cards (Victory, Curse, Treasure),
       any of the 10 game piles, and Colony/Platinum when included.
       However, this does NOT include any Prizes from Cornucopia.
       */

    CardPile addEmbargo(Card card) {
        if (isValidEmbargoPile(card)) {
            String name = card.getName();
            embargos.put(name, getEmbargos(card) + 1);
            return piles.get(name);
        }
        return null;
    }
    
    CardPile addPileVpTokens(Card card, int num, MoveContext context) {
    	if (Cards.isBlackMarketCard(card)) {
    		return null;
    	}
        String name = card.getName();
        pileVpTokens.put(name, getPileVpTokens(card) + num);
        if (context != null) {
        	GameEvent event = new GameEvent(GameEvent.EventType.VPTokensPutOnPile, context);
    		event.setAmount(num);
    		event.card = card;
            context.game.broadcastEvent(event);
    	}
        return piles.get(name);
    }
    
    CardPile removePileVpTokens(Card card, int num, MoveContext context) {
    	if (Cards.isBlackMarketCard(card)) {
    		return null;
    	}
        if (getPile(card) != null)
            card = getPile(card).placeholderCard();

    	num = Math.min(num, getPileVpTokens(card));
        String name = card.getName();
        if (num > 0) {
        	pileVpTokens.put(name, getPileVpTokens(card) - num);
        	if (context != null) {
	        	GameEvent event = new GameEvent(GameEvent.EventType.VPTokensTakenFromPile, context);
	    		event.setAmount(num);
	    		event.card = card;
	            context.game.broadcastEvent(event);
        	}
        }
        return piles.get(name);
    }

    CardPile addPileDebtTokens(Card card, int num, MoveContext context) {
        card = getPile(card).placeholderCard();
        String name = card.getName();
        pileDebtTokens.put(name, getPileDebtTokens(card) + num);
        if (context != null) {
        	GameEvent event = new GameEvent(GameEvent.EventType.DebtTokensPutOnPile, context);
    		event.setAmount(num);
    		event.card = card;
            context.game.broadcastEvent(event);
    	}
        return piles.get(name);
    }

    CardPile removePileDebtTokens(Card card, int num, MoveContext context) {
        card = getPile(card).placeholderCard();
    	num = Math.min(num, getPileDebtTokens(card));
        String name = card.getName();
        if (num > 0) {
        	pileDebtTokens.put(name, getPileDebtTokens(card) - num);
        	if (context != null) {
	        	GameEvent event = new GameEvent(GameEvent.EventType.DebtTokensTakenFromPile, context);
	    		event.setAmount(num);
	    		event.card = card;
	            context.game.broadcastEvent(event);
        	}
        }
        return piles.get(name);
    }

    public boolean isValidEmbargoPile(Card card) {
        return !(card == null || !pileInGame(getPile(card)) || !Cards.isSupplyCard(card) );
    }

    public int getEmbargos(Card card) {

        Integer count = embargos.get(getPile(card).placeholderCard().getName());
        return (count == null) ? 0 : count;
    }
    
    public int getPileVpTokens(Card card) {
    	if (Cards.isBlackMarketCard(card)) {
    		return 0;
    	}
        if (getPile(card) != null)
            card = getPile(card).placeholderCard();

        Integer count = pileVpTokens.get(card.getName());
        return (count == null) ? 0 : count;
    }
    
    public int getPileDebtTokens(Card card) {
        card = getPile(card).placeholderCard();
        Integer count = pileDebtTokens.get(card.getName());
        return (count == null) ? 0 : count;
    }
    
    public int getPileTradeRouteTokens(Card card) {
    	return piles.get(card.getName()).hasTradeRouteToken() ? 1 : 0;
    }
    
    public List<PlayerSupplyToken> getPlayerSupplyTokens(Card card, Player player) {
    	card = card.getTemplateCard();
    	if (player == null || !playerSupplyTokens.containsKey(card.getName()))
    		return new ArrayList<PlayerSupplyToken>();
    	
    	if (!playerSupplyTokens.get(card.getName()).containsKey(player)) {
        	playerSupplyTokens.get(card.getName()).put(player, new ArrayList<PlayerSupplyToken>());
    	}
    	return playerSupplyTokens.get(card.getName()).get(player);
    }
    
    public boolean isPlayerSupplyTokenOnPile(Card card, Player player, PlayerSupplyToken token) {
    	return getPlayerSupplyTokens(card, player).contains(token);
    }
    
    public void movePlayerSupplyToken(Card card, Player player, PlayerSupplyToken token) {
    	removePlayerSupplyToken(player, token);
    	getPlayerSupplyTokens(card, player).add(token);
    	
        MoveContext context = new MoveContext(this, player);
        GameEvent.EventType eventType = null;
        if (token == PlayerSupplyToken.PlusOneCard) {
        	eventType = EventType.PlusOneCardTokenMoved;
        } else if (token == PlayerSupplyToken.PlusOneAction) {
        	eventType = EventType.PlusOneActionTokenMoved;
        } else if (token == PlayerSupplyToken.PlusOneBuy) {
        	eventType = EventType.PlusOneBuyTokenMoved;
        } else if (token == PlayerSupplyToken.PlusOneCoin) {
        	eventType = EventType.PlusOneCoinTokenMoved;
        } else if (token == PlayerSupplyToken.MinusTwoCost) {
        	eventType = EventType.MinusTwoCostTokenMoved;
        } else if (token == PlayerSupplyToken.Trashing) {
        	eventType = EventType.TrashingTokenMoved;
        }
        GameEvent event = new GameEvent(eventType, context);
        event.card = card;
        broadcastEvent(event);
    }
    
    private void removePlayerSupplyToken(Player player, PlayerSupplyToken token) {
		for (String cardName : playerSupplyTokens.keySet()) {
			if (playerSupplyTokens.get(cardName).containsKey(player)) {
				playerSupplyTokens.get(cardName).get(player).remove(token);
			}
		}
	}

	// Only is valid for cards in play...
    //    protected Card readCard(String name) {
    //        CardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPileCardPile pile = piles.get(name);
    //        if (pile == null || pile.getCount() <= 0) {
    //            return null;
    //        }
    //        return pile.card();
    //    }

    protected Card takeFromPile(Card card) {
        return takeFromPile(card, null);
    }
    
    protected Card takeFromPile(Card card, MoveContext context) {
        CardPile pile = getPile(card);
        if (pile == null || pile.getCount() <= 0) {
            return null;
        }

        tradeRouteValue += pile.takeTradeRouteToken();

        return pile.removeCard();
    }

    protected Card takeFromPileCheckGetTop(Card cardToGain, MoveContext context) {

        //If the pile was specified instead of a card, take the top card from that pile.
        if (cardToGain.isPlaceholderCard() || cardToGain.isTemplateCard()) {
            cardToGain = getPile(cardToGain).topCard();


        //If the desired card is not on top of the pile, don't take a card
        } else if (!isCardOnTop(cardToGain)) {
            return null;
        }

        return takeFromPile(cardToGain, context);
    }

    public int pileSize(Card card) {
        CardPile pile = getPile(card);
        if (pile == null) {
            return -1;
        }

        return pile.getCount();
    }

    public boolean isPileEmpty(Card card) {
        return pileSize(card) <= 0;
    }

    public int emptyPiles() {
        int emptyPiles = 0;
        ArrayList<CardPile> alreadyCounted = new ArrayList<CardPile>();
        for (CardPile pile : piles.values()) {
            if (pile.getCount() <= 0 && pile.isSupply() && !alreadyCounted.contains(pile)) {
                emptyPiles++;
                alreadyCounted.add(pile);
            }
        }
        return emptyPiles;
    }

    public Card[] getCardsInGame(GetCardsInGameOptions opt) {
        return getCardsInGame(opt, false);
    }

    public Card[] getCardsInGame(GetCardsInGameOptions opt, boolean supplyOnly) {
        return getCardsInGame(opt, supplyOnly, null);
    }
    
    public Card[] getCardsInGame(GetCardsInGameOptions opt, boolean supplyOnly, Type type) {
    	return getCardsInGame(opt, supplyOnly, type, false);
    }

    public Card[] getCardsInGame(GetCardsInGameOptions opt, boolean supplyOnly, Type type, boolean negateType) {
        ArrayList<Card> cards = new ArrayList<Card>();
        for (CardPile pile : piles.values()) {

            if (supplyOnly && !pile.isSupply) continue;

            if (opt == GetCardsInGameOptions.All || opt == GetCardsInGameOptions.Placeholders)
            {
                if ((type == null || (negateType ^ pile.placeholderCard().is(type, null)))
                        && !cards.contains(pile.placeholderCard()))
                    cards.add(pile.placeholderCard());
            }
            if (opt == GetCardsInGameOptions.All || opt == GetCardsInGameOptions.Templates) {
                for (Card c : pile.getTemplateCards()) {
                    if ((type == null || c.is(type, null))
                            && !cards.contains(c)) {
                        cards.add(c);
                    }
                }
            }
            if (opt == GetCardsInGameOptions.TopOfPiles) {
                if (pile.topCard() != null && (type == null || (negateType ^ pile.topCard().is(type)))
                        && !cards.contains(pile.topCard())) {
                    cards.add(pile.topCard());
                }
            }
            if (opt == GetCardsInGameOptions.Buyables) {
                if (pile.topCard() != null && (type == null || (negateType ^ pile.topCard().is(type)))
                        && !cards.contains(pile.topCard()) && (pile.isSupply() || pile.topCard().is(Type.Event) || pile.topCard().is(Type.Project))) {
                    cards.add(pile.topCard());
                }
            }
        }
        return cards.toArray(new Card[0]);
    }

    public boolean cardInGame(Card c) {
        for (CardPile pile : piles.values()) {
            if(c.equals(pile.placeholderCard())) {
                return true;
            }
            for (Card template : pile.getTemplateCards()) {
                if (c.equals(template)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCardOnTop(Card card) {
        CardPile pile = getPile(card);
        if (pile == null || pile.isBlackMarket()) return false;
        Card top = pile.topCard();
        return top != null && top.equals(card);
    }

    public boolean pileInGame(CardPile p) {
        for (CardPile pile : piles.values()) {
            if(pile.equals(p)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlatInGame() {
        return cardInGame(Cards.platinum);
    }

    public boolean isColonyInGame() {
        return cardInGame(Cards.colony);
    }

    public int getCardsLeftInPile(Card card) {
        CardPile pile = getPile(card);
        if (pile == null || pile.getCount() < 0) {
            return 0;
        }

        return pile.getCount();
    }

    public ArrayList<Card> GetTrashPile()
    {
        return trashPile;
    }

    public ArrayList<Card> GetBlackMarketPile()
    {
        return blackMarketPile;
    }

    protected CardPile addPile(Card card) {
    	boolean isSupply = true;
        int count = kingdomCardPileSize;
        if(card.is(Type.Victory)) count = victoryCardPileSize;
        if(card.equals(Cards.rats)) count = 20;
        if(card.equals(Cards.port)) count = 12;
        if(card.is(Type.Event) || card.is(Type.Project) || card.is(Type.Landmark) || card.is(Type.Way)) {
        	count = 1;
        	isSupply = false;
        }
        return addPile(card, count, isSupply);
    }

    protected CardPile addPile(Card card, int count) {
        return addPile(card, count, true);
    }

    protected CardPile addPile(Card card, int count, boolean isSupply) {
        return addPile(card, count, isSupply, false);
    }
    
    protected CardPile addPile(Card card, int count, boolean isSupply, boolean isBlackMarket) {
    	return addPile(card, count, isSupply, isBlackMarket, true);
    }

    protected CardPile addPile(Card card, int count, boolean isSupply, boolean isBlackMarket, boolean isPile) {
        CardPile pile = card.getPileCreator().create(card, count);


        if (!isSupply) {
            pile.notInSupply();
        }
        if (isBlackMarket) {
            pile.inBlackMarket();
        }
        if (!isPile) {
            pile.notRealPile();
        }

        piles.put(card.getName(), pile);
        placeholderPiles.put(card.getName(), pile);
        HashMap<Player, List<PlayerSupplyToken>> tokenMap = new HashMap<Player, List<PlayerSupplyToken>>();
        playerSupplyTokens.put(card.getName(), tokenMap);

        //Add the to the list for each templateCard used (this replaces addLinkedPile)
        //Also add the an entry for each templateCardName to the playerSupplyTokens because at some places in the code
        //the token is checked with the actual card and not the placeholder.
        for (Card templateCard : pile.getTemplateCards()) {
            if (!piles.containsKey(templateCard.getName())) {
                piles.put(templateCard.getName(), pile);
            }
            if (!playerSupplyTokens.containsKey(templateCard.getName())) {
                playerSupplyTokens.put(templateCard.getName(), tokenMap);
            }
        }
        

        return pile;
    }

    private ArrayList<Card> getCardsObtainedByPlayer(int PlayerNumber) {
        return cardsObtainedLastTurn[PlayerNumber];
    }
    
    public ArrayList<Card> getCardsObtainedByPlayer() {
        return getCardsObtainedByPlayer(playersTurn);
    }
    
    public ArrayList<Card> getCardsObtainedThisTurn() {
        return allCardsObtainedLastTurn[playersTurn];
    }
    
    
    private ArrayList<Card> getCardsTrashedByPlayer(int PlayerNumber) {
        return cardsTrashedLastTurn[PlayerNumber];
    }
    
    public ArrayList<Card> getActionCardsObtainedByPlayer() {
        ArrayList<Card> cards = getCardsObtainedByPlayer(playersTurn);
        ArrayList<Card> result = new ArrayList<Card>();
        Player player = players[playersTurn];
        for(Card c : cards) {
        	if (c.is(Type.Action, player)) {
        		result.add(c);
        	}
        }
        return result;
    }
    
    public ArrayList<Card> getCardsObtainedByLastPlayer() {
        int playerOnRight = playersTurn - 1;
        if (playerOnRight < 0) {
            playerOnRight = numPlayers - 1;
        }
        return getCardsObtainedByPlayer(playerOnRight);
    }
    
    public ArrayList<Card> getCardsTrashedByLastPlayer() {
        int playerOnRight = playersTurn - 1;
        if (playerOnRight < 0) {
            playerOnRight = numPlayers - 1;
        }
        return getCardsTrashedByPlayer(playerOnRight);
    }
    
    public Player getNextPlayer() {
        int next = playersTurn + 1;
        if (next >= numPlayers) {
            next = 0;
        }

        return players[next];
    }
    
    public Player getCurrentPlayer() {
        return players[playersTurn];
    }

    public Player[] getPlayersInTurnOrder() {
    	return getPlayersInTurnOrder(playersTurn);
    }
    
    public Player[] getPlayersInTurnOrder(Player startingPlayer) {
        int at = 0;
        for (int i = 0; i < numPlayers; i++) {
        	if (players[i] == startingPlayer) {
        		at = i;
        		break;
        	}
        }
        return getPlayersInTurnOrder(at);
    }
    
    public Player[] getPlayersInTurnOrder(int startingPlayerIdx) {
        Player[] ordered = new Player[numPlayers];

        int at = startingPlayerIdx;
        for (int i = 0; i < numPlayers; i++) {
            ordered[i] = players[at];
            at++;
            if (at >= numPlayers) {
                at = 0;
            }
        }

        return ordered;
    }

    public void broadcastEvent(GameEvent event) {
        for (GameEventListener listener : listeners) {
            listener.gameEvent(event);
        }
        // notify this class' listener last for proper action/logging order
        if(gameListener != null)
            gameListener.gameEvent(event);
    }

    String getHandString(Player player) {
        String handString = null;
        Card[] hand = player.getHand().toArray();
        Arrays.sort(hand, new CardCostComparator());
        for (Card card : hand) {
            if (card == null) {
                continue;
            }
            if (handString == null) {
                handString = card.getName();
            } else {
                handString += ", " + card.getName();
            }
        }

        return handString;
    }

    public boolean playerShouldSelectCoinsToPlay(MoveContext context, CardList cards) {
        if(!quickPlay) {
            return true;
        }

        if(cards == null) {
            return false;
        }

        CardPile grandMarket = getPile(Cards.grandMarket);
        for(Card card : cards) {
            if (
                    card.equals(Cards.philosophersStone) ||
                    card.equals(Cards.bank) ||
                    card.equals(Cards.contraband) ||
                    card.equals(Cards.loan) ||
                    card.equals(Cards.quarry) ||
                    card.equals(Cards.talisman) ||
                    card.equals(Cards.hornOfPlenty) ||
                    card.equals(Cards.diadem) ||
                    (card.equals(Cards.copper) && grandMarket != null && grandMarket.getCount() > 0)
               )
            {
                return true;
            }
        }

        return false;
    }

    static boolean checkForInteractive() throws ExitException {
        for (int i = 0; i < numPlayers; i++) {
            Player player;
            try {
                String[] classAndJar = playerClassesAndJars.get(i);
                if (classAndJar[1] == null) {
                    player = (Player) Class.forName(classAndJar[0]).newInstance();
                } else {
                    URLClassLoader classLoader = new URLClassLoader(new URL[] { new URL(classAndJar[1]) });
                    player = (Player) classLoader.loadClass(classAndJar[0]).newInstance();
                }
                if(classAndJar[2] != null) {
                    player.setName(classAndJar[2]);
                }
            } catch (Exception e) {
                Util.log(e);
                throw new ExitException();
            }
        }
        return false;
    }



    public CardPile getPile(Card card) {
        if (card == null) return null;
        return piles.get(card.getName());
    }
    
    public CardPile getGamePile(Card card) {
        return getPile(card);
    }

    public boolean cardsInSamePile(Card first, Card second) {
        return getPile(first).equals(getPile(second));
    }

    public void trashHovelsInHandOption(Player player, MoveContext context, Card responsible)
    {
        // If player has a Hovel (or multiple Hovels), offer the option to trash...
        ArrayList<Card> hovelsToTrash = new ArrayList<Card>();

        for (Card c : player.hand)
        {
            if (c.getKind() == Cards.Kind.Hovel && player.controlPlayer.hovel_shouldTrash(context))
            {
                hovelsToTrash.add(c);
            }
        }

        if (hovelsToTrash.size() > 0)
        {
            for (Card c : hovelsToTrash)
            {
                player.trashFromHand(c, responsible, context);
            }
        }
    }
    
    public boolean hauntedWoodsAttacks(Player player) {
        for (Player otherPlayer : players) {
            if (otherPlayer != null && otherPlayer != player) {
            	if (otherPlayer.getDurationEffectsOnOtherPlayer(player, Cards.Kind.HauntedWoods) > 0) {
            		return true;
            	}
            }
        }
        return false;
    }
    
    public boolean enchantressAttacks(Player player) {
    	if (getCurrentPlayer() != player) return false;
        for (Player otherPlayer : players) {
            if (otherPlayer != null && otherPlayer != player) {
            	if (otherPlayer.getDurationEffectsOnOtherPlayer(player, Cards.Kind.Enchantress) > 0) {
            		return true;
            	}
            }
        }
        return false;
    }

    public int swampHagAttacks(Player player)
    {
    	int swampHags = 0;
        for (Player otherPlayer : players) {
            if (otherPlayer != null && otherPlayer != player) {
            	swampHags += otherPlayer.getDurationEffectsOnOtherPlayer(player, Cards.Kind.SwampHag);
            }
        }
        return swampHags;
    }
    
    public boolean gatekeeperAttacks(Player player) {
    	if (getCurrentPlayer() != player) return false;
        for (Player otherPlayer : players) {
            if (otherPlayer != null && otherPlayer != player) {
            	if (otherPlayer.getDurationEffectsOnOtherPlayer(player, Cards.Kind.Gatekeeper) > 0) {
            		return true;
            	}
            }
        }
        return false;
    }
}

package com.mehtank.androminion.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;
import androidx.preference.PreferenceManager;

import com.mehtank.androminion.R;
import com.vdom.api.Card;
import com.vdom.api.GameEvent;
import com.vdom.api.GameType;
import com.vdom.comms.Event;
import com.vdom.comms.MyCard;
import com.vdom.comms.SelectCardOptions;
import com.vdom.comms.SelectCardOptions.ActionType;
import com.vdom.comms.SelectCardOptions.PickType;
import com.vdom.core.CardSet;
import com.vdom.core.CardSet.UseOptionalCards;
import com.vdom.core.Cards;
import com.vdom.core.Expansion;
import com.vdom.core.IndirectPlayer;
import com.vdom.core.IndirectPlayer.StashOption;
import com.vdom.core.Player.AmuletOption;
import com.vdom.core.Player.CharmOption;
import com.vdom.core.Player.CountFirstOption;
import com.vdom.core.Player.CountSecondOption;
import com.vdom.core.Player.DoctorOverpayOption;
import com.vdom.core.Player.EncampmentOption;
import com.vdom.core.Player.ExtraTurnOption;
import com.vdom.core.Player.GovernorOption;
import com.vdom.core.Player.GraverobberOption;
import com.vdom.core.Player.HuntingGroundsOption;
import com.vdom.core.Player.JesterOption;
import com.vdom.core.Player.MinionOption;
import com.vdom.core.Player.MonasteryOption;
import com.vdom.core.Player.NoblesOption;
import com.vdom.core.Player.PawnOption;
import com.vdom.core.Player.PutBackOption;
import com.vdom.core.Player.QuestOption;
import com.vdom.core.Player.ScrapOption;
import com.vdom.core.Player.SentryOption;
import com.vdom.core.Player.SleighOption;
import com.vdom.core.Player.SpiceMerchantOption;
import com.vdom.core.Player.SquireOption;
import com.vdom.core.Player.StewardOption;
import com.vdom.core.Player.TorturerOption;
import com.vdom.core.Player.TournamentOption;
import com.vdom.core.Player.TransportOption;
import com.vdom.core.Player.TreasurerOption;
import com.vdom.core.Player.TrustySteedOption;
import com.vdom.core.Player.WatchTowerOption;
import com.vdom.core.Player.WildHuntOption;
import com.vdom.core.PlayerSupplyToken;
import com.vdom.core.Player.CourtierOption;
import com.vdom.core.Player.LurkerOption;
import com.vdom.core.Player.FoolsGoldOption;
import com.vdom.core.Type;

public class Strings {

    private static final String TAG = "Androminion.Strings";

    static HashMap<Card, String> nameCache = new HashMap<Card, String>();
    static HashMap<Card, String> descriptionCache = new HashMap<Card, String>();
    static HashMap<Expansion, String> expansionCache = new HashMap<Expansion, String>();
    static HashMap<Type, String> typeCache = new HashMap<Type, String>();
    static HashMap<GameType, String> gametypeCache = new HashMap<GameType, String>();
    static HashMap<Card, String> boonShortTextCache = new HashMap<Card, String>();
    static HashMap<Card, String> wayShortNameCache = new HashMap<Card, String>();
    static HashMap<Card, String> wayShortTextCache = new HashMap<Card, String>();
    private static Map<String, String> actionStringMap;
    private static Set<String> simpleActionStrings;
    private static Context context;

    public static void initContext(Context c) {
        context = c;
        nameCache = new HashMap<Card, String>();
        descriptionCache = new HashMap<Card, String>();
        expansionCache = new HashMap<Expansion, String>();
        typeCache = new HashMap<Type, String>();
        nameCache = new HashMap<Card, String>();
        gametypeCache = new HashMap<GameType, String>();
        boonShortTextCache = new HashMap<Card, String>();
        wayShortNameCache = new HashMap<Card, String>();
        wayShortTextCache = new HashMap<Card, String>();
        initActionStrings();
    }
    
    private static String getTrashString() {
        Resources r = context.getResources();
        return r.getString(R.string.trash);
    }
    
    public static String getCardName(Card c) {
        String name = nameCache.get(c);
        if(name == null) {
            try {
                Resources r = context.getResources();
                int id = r.getIdentifier(c.getSafeName() + "_name", "string", context.getPackageName());
                name = r.getString(id);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            if(name == null) {
                name = c.getName();
            }

            nameCache.put(c, name);
        }
        return name;
    }

    public static String getCardDescription(Card c) {
        String description = descriptionCache.get(c);
        if(description == null) {
            try {
                Resources r = context.getResources();
                int id = r.getIdentifier(c.getSafeName() + "_desc", "string", context.getPackageName());
                description = r.getString(id);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            if(description == null) {
                description = c.getDescription();
            }

            description = Strings.replaceCoinsString(description);

            descriptionCache.put(c, description);
        }
        return description;
    }

    public static String getCardExpansion(Card c) {
    	String expansionString = c.getExpansion() != null ? c.getExpansion().toString() : ""; 
        if (expansionString == "") {
            // Victory cards (e.g. "Duchy") don't have a single expansion;
            // they're both in Base and Intrigue.
            return "";
        }
        return getExpansionName(c.getExpansion());
    }
    
    public static void localizeMyCard(MyCard c) {
        Card card = Cards.cardNameToCard.get(c.originalSafeName);
        c.name = getCardName(card);
        c.desc = getFullCardDescription(card);
        c.expansion = getCardExpansion(card);
    }
    
    public static String getTypeName(Type t) {
        String name = typeCache.get(t);
        if(name == null) {
            try {
                Resources r = context.getResources();
                int id = r.getIdentifier("type_" + t.name().toLowerCase(Locale.US), "string", context.getPackageName());
                name = r.getString(id);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            if(name == null) {
            	name = t.toString();
            }

            typeCache.put(t, name);
        }
        return name;
    }

    public static String getGameTypeName(GameType g) {

        String gametype = gametypeCache.get(g);
        if (gametype==null){
            try {
            	String idPrefix = g.toString();
            	if (idPrefix.endsWith("2")) {
            		idPrefix = idPrefix.substring(0, idPrefix.length() - 1);
            	}
                Resources r = context.getResources();
                int id = r.getIdentifier(idPrefix + "_gametype", "string", context.getPackageName());
                gametype = r.getString(id);
            }
            catch(Exception e) {
                //e.printStackTrace();
            }
        }
        if (gametype == null){
            //          Fallback is the name in the enumeration
            gametype = g.getName();
        }

        gametypeCache.put(g, gametype);
        return gametype;
    }

    public static GameType getGameTypefromName(String s){

        for (GameType g : GameType.values()) {
            if (getGameTypeName(g).equals(s))
            { return g; }
        }
        return null;
    }
    
    public static String getExpansionName(Expansion expansion) {

    	String expansionStr = expansionCache.get(expansion);
    	if (expansionStr == null) {
            try {
                Resources r = context.getResources();
                int id = r.getIdentifier(expansion.toString(), "string", context.getPackageName());
                expansionStr = r.getString(id);
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            if(expansionStr.equals(""))
            {
                expansionStr = expansion.toString();
            }

            expansionCache.put(expansion, expansionStr);
        }
    	return expansionStr;
    }

    public static String getBoonShortText(Card c) {
        String shortText = boonShortTextCache.get(c);
        if(shortText == null) {
            try {
                Resources r = context.getResources();
                int id = r.getIdentifier(c.getSafeName() + "_shortDesc", "string", context.getPackageName());
                shortText = r.getString(id);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            if(shortText == null) {
            	shortText = c.getName();
            }

            boonShortTextCache.put(c, shortText);
        }
        return shortText;
    }
    
    public static String getWayShortName(Card c) {
        String shortText = wayShortNameCache.get(c);
        if(shortText == null) {
            try {
                Resources r = context.getResources();
                int id = r.getIdentifier(c.getSafeName() + "_shortName", "string", context.getPackageName());
                shortText = r.getString(id);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            if(shortText == null) {
            	shortText = c.getName();
            }

            wayShortNameCache.put(c, shortText);
        }
        return shortText;
    }
    
    public static String getWayShortText(Card c) {
        String shortText = wayShortTextCache.get(c);
        if(shortText == null) {
            try {
                Resources r = context.getResources();
                int id = r.getIdentifier(c.getSafeName() + "_shortDesc", "string", context.getPackageName());
                if (id != 0) {
                	shortText = r.getString(id);
                } else {
                	String desc = getFullCardDescription(c);
                	shortText = desc.substring(0, desc.length() - 1).replace("\n", ", ");
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            if(shortText == null) {
            	shortText = c.getName();
            }

            wayShortTextCache.put(c, shortText);
        }
        Card mouseCard = GameTableViews.getWayOfTheMouseCard();
        return format(shortText, mouseCard == null ? "" : getCardName(GameTableViews.getWayOfTheMouseCard()));
    }
    
    public static String format(String str, Object... args) {
        return replaceCoinsString(String.format(str, args));
    }

    public static String format(int resId, Object... args) {
        return replaceCoinsString(String.format(context.getString(resId), args));
    }

    public static String getString(int resId) {
        return replaceCoinsString(context.getString(resId));
    }

    private static final Pattern COINS_PATTERN = Pattern.compile("\\$[\\d*X]+");
    private static final String ENCLOSING_CIRCLE = "\u25ef";
    private static final String CIRCLED_0 = "\u24ea";
    private static final String CIRCLED_1 = "\u2460";
    private static final String CIRCLED_21 = "\u3251";
    private static final String CIRCLED_36 = "\u32b1";
    private static final String CIRCLED_X = "\u24cd";
    public static final String replaceCoinsString(String str) {
        Matcher m = COINS_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String coinStr = m.group().substring(1);
            if (coinStr.equals("*")) {
                m.appendReplacement(sb, ENCLOSING_CIRCLE);
            } else if (coinStr.equals("X")) {
                m.appendReplacement(sb, CIRCLED_X);
            } else {
                int coins = Integer.parseInt(coinStr);
                m.appendReplacement(sb, Strings.getCoinsString(coins));
            }
        }
        m.appendTail(sb);
        return sb.toString();
    }

    static String getCoinsString(int coins) {
        if (coins == 0) {
            return CIRCLED_0;
        }
        if (coins >= 1 && coins <= 20) {
            return new Character((char)(Character.codePointAt(CIRCLED_1, 0) + coins - 1)).toString();
        }
        if (coins >= 21 && coins <= 35) {
            return new Character((char)(Character.codePointAt(CIRCLED_21, 0) + coins - 21)).toString();
        }
        if (coins >= 36 && coins <= 50) {
            return new Character((char)(Character.codePointAt(CIRCLED_36, 0) + coins - 36)).toString();
        }
        return "(" + coins + ")";
    }

    /**
     * Given an Event object, return the string that should be shown in the game log.  We look into
     * the GameEvent stored inside the Event object to decide what that string should be (GameEvent
     * is how the Game talks to RemotePlayer, while Event is how RemotePlayer talks to
     * Androminion).
     *
     * @param event The event object that we're generating status text from.
     * @param extras Some extra objects that contain things we need to render the status text.
     *               This is quite ugly, I think, but it was the easiest way make RemotePlayer less
     *               ugly...  We could clean this up in a few ways, notably by adding a
     *               StatusObject or something to the Event.  Items in the extras list (be sure
     *               this is in sync with RemotePlayer.gameEvent!):
     *               extras[0]: string possessingPlayerName (or null)
     *               extras[1]: string attackedPlayerName (or null)
     *               extras[2]: string context.getMessage() (or null)
     *               If event type is GameOver: extras[3:7] are used
     *               If event type is Status: extras[3:5] are used
     *               If event type is TurnBegin: extras[3:4] are used
     *               If event type is TurnEnd: extras[3:4] are used
     *               If event type is GuildsTokenSpend: extras[3] is used
     */
    public static String getStatusText(Event event, Object[] extras) {
        String statusText = event.s;
        // These events already had their strings handled, so we don't need to do anything with
        // them here.
        if (event.t == Event.EType.CARDOBTAINED
                || event.t == Event.EType.CARDTRASHED
                || event.t == Event.EType.CARDREVEALED) {
            return statusText;
        }

        // The first part of the text tells us what kind of event we're dealing with.
        if (event.gameEventType == GameEvent.EventType.BuyingCard) {
            statusText += format(R.string.BuyingCard, getCardName(event.c));
        } else if (event.gameEventType == GameEvent.EventType.Embargo) {
            statusText += getString(R.string.Embargo);
        } else if (event.gameEventType == GameEvent.EventType.PlusOneCardTokenMoved) {
            statusText += getString(R.string.PlusOneCardTokenMoved);
        } else if (event.gameEventType == GameEvent.EventType.PlusOneActionTokenMoved) {
        	statusText += getString(R.string.PlusOneActionTokenMoved);
        } else if (event.gameEventType == GameEvent.EventType.PlusOneBuyTokenMoved) {
        	statusText += getString(R.string.PlusOneBuyTokenMoved);
        } else if (event.gameEventType == GameEvent.EventType.PlusOneCoinTokenMoved) {
        	statusText += getString(R.string.PlusOneCoinTokenMoved);
        } else if (event.gameEventType == GameEvent.EventType.MinusTwoCostTokenMoved) {
        	statusText += getString(R.string.MinusTwoCostTokenMoved);
        } else if (event.gameEventType == GameEvent.EventType.TrashingTokenMoved) {
        	statusText += getString(R.string.TrashingTokenMoved);
        } else if (event.gameEventType == GameEvent.EventType.GameStarting) {
            statusText += getString(R.string.GameStarting);
        } else if (event.gameEventType == GameEvent.EventType.GameOver) {
            statusText += getGameTimeString((Long) extras[7]);
            if (extras[8] != null) {
                statusText += getGameTypeName((GameType) extras[8]);
            }

            statusText += "\n\n";
            statusText = getVPOutput((String) extras[3],
                                     (Map<Object, Integer>) extras[4],
                                     (Map<Card, Integer>) extras[5],
                                     (Map<Card, Integer>) extras[6]);
            statusText += getString(R.string.GameOver);
        } else if (event.gameEventType == GameEvent.EventType.CardRevealedFromHand) {
            statusText += getString(R.string.CardRevealedFromHand);
        } else if (event.gameEventType == GameEvent.EventType.CardNamed) {
            statusText += getString(R.string.CardNamed);
        } else if (event.gameEventType == GameEvent.EventType.CardDiscarded) {
            statusText += getString(R.string.CardDiscarded);
        } else if (event.gameEventType == GameEvent.EventType.CardAddedToHand) {
            statusText += getString(R.string.CardAddedToHand);
        } else if (event.gameEventType == GameEvent.EventType.CardRemovedFromHand) {
            statusText += getString(R.string.CardRemovedFromHand);
        } else if (event.gameEventType == GameEvent.EventType.NoBuy) {
            statusText += getString(R.string.NoBuy);
        } else if (event.gameEventType == GameEvent.EventType.DeckReplenished) {
            statusText += getString(R.string.DeckReplenished);
        } else if (event.gameEventType == GameEvent.EventType.PlayerAttacking) {
            statusText += getString(R.string.PlayerAttacking);
        } else if (event.gameEventType == GameEvent.EventType.PlayerDefended) {
            statusText += getString(R.string.PlayerDefended);
        } else if (event.gameEventType == GameEvent.EventType.CardOnTopOfDeck) {
            statusText += getString(R.string.CardOnTopOfDeck);
        } else if (event.gameEventType == GameEvent.EventType.PlayingCard) {
        	if (!event.c.is(Type.State)) {
        		statusText += getString(R.string.PlayingCard);
        	}
        } else if (event.gameEventType == GameEvent.EventType.PlayedCard) {
            statusText += getString(R.string.PlayedCard);
        } else if (event.gameEventType == GameEvent.EventType.PlayingDurationAction) {
            statusText += getString(R.string.PlayingDurationAction);
        } else if (event.gameEventType == GameEvent.EventType.CardSetAside) {
        	String cardResponsible = getCardName((Card)extras[3]); // card doing the setting aside
        	statusText += format(R.string.CardSetAside, getCardName(event.c), cardResponsible);
        } else if (event.gameEventType == GameEvent.EventType.CardSetAsidePrivate) {
        	String cardResponsible = getCardName((Card)extras[3]); // card doing the setting aside
            statusText += format(R.string.CardSetAsidePrivate, cardResponsible);
        } else if (event.gameEventType == GameEvent.EventType.CardSetAsideOnTavernMat) {
            statusText += getString(R.string.CardSetAsideOnTavernMat);
        } else if (event.gameEventType == GameEvent.EventType.CallingCard) {
			statusText += getString(R.string.CallingCard);
		} else if (event.gameEventType == GameEvent.EventType.CalledCard) {
			statusText += getString(R.string.CalledCard);
		} else if (event.gameEventType == GameEvent.EventType.CardExiled) {
			String cardResponsible = getCardName((Card)extras[3]); // card doing the setting aside
        	statusText += format(R.string.CardExiled, getCardName(event.c), cardResponsible);
        } else if (event.gameEventType == GameEvent.EventType.CardSetAsideOnIslandMat) {
            statusText += getString(R.string.CardSetAsideOnIslandMat);
        } else if (event.gameEventType == GameEvent.EventType.DeckPutIntoDiscardPile) {
            statusText += getString(R.string.DeckPutIntoDiscardPile);
        } else if (event.gameEventType == GameEvent.EventType.TravellerExchanged) {
        	String cardExchanged = getCardName((Card)extras[3]); // card being exchanged
        	statusText += format(R.string.TravellerExchanged, cardExchanged);
        } else if (event.gameEventType == GameEvent.EventType.TurnJourneyTokenFaceUp) {
            statusText += getString(R.string.TurnJourneyTokenFaceUp);
        } else if (event.gameEventType == GameEvent.EventType.TurnJourneyTokenFaceDown) {
            statusText += getString(R.string.TurnJourneyTokenFaceDown);
        } else if (event.gameEventType == GameEvent.EventType.MinusOneCoinTokenOn) {
            statusText += getString(R.string.MinusOneCoinTokenOn);
        } else if (event.gameEventType == GameEvent.EventType.MinusOneCoinTokenOff) {
            statusText += getString(R.string.MinusOneCoinTokenOff);
        } else if (event.gameEventType == GameEvent.EventType.MinusOneCardTokenOn) {
            statusText += getString(R.string.MinusOneCardTokenOn);
        } else if (event.gameEventType == GameEvent.EventType.MinusOneCardTokenOff) {
            statusText += getString(R.string.MinusOneCardTokenOff);
        } else if (event.gameEventType == GameEvent.EventType.ReceivingBoonHex) {
            statusText += getString(R.string.ReceivingBoonOrHex);
        } else if (event.gameEventType == GameEvent.EventType.ReceivedBoonHex) {
            statusText += getString(R.string.ReceivedBoonOrHex);
        } else if (event.gameEventType == GameEvent.EventType.TakeState) {
        	if (extras[1] != null) {
        		statusText += format(R.string.TookStateFromPlayer, getCardName(event.c), extras[1]);
        	} else {
        		statusText += format(R.string.TookState, getCardName(event.c));
        	}
        } else if (event.gameEventType == GameEvent.EventType.ReturnState) {
            statusText += format(R.string.ReturnedState, getCardName(event.c));
        } else if (event.gameEventType == GameEvent.EventType.TurnEnd) {
            /* end of turn: inform about cards on island and nativeVillage */
            String tmp = statusText;
            int islandSize = 0;
            if (extras[3] != null) {
                islandSize = (Integer) extras[3];
            }
            if (islandSize > 0) {
                statusText += getString(R.string.OnIslandMat) + ": " + islandSize + "\n" + tmp;
            }
            int nativeVillageSize = 0;
            if (extras[4] != null) {
                nativeVillageSize = (Integer) extras[4];
            }
            if (nativeVillageSize > 0) {
                statusText += getString(R.string.OnNativeVillageMat) + ": " + nativeVillageSize + "\n" + tmp;
            }
            statusText += getString(R.string.TurnEnd);
        } else if (event.gameEventType == GameEvent.EventType.VictoryPoints) {
            statusText += getString(R.string.VictoryPoints);
        } else if (event.gameEventType == GameEvent.EventType.NewHand) {
            statusText += getString(R.string.NewHand);
        } else if (event.gameEventType == GameEvent.EventType.TurnBegin) {
            /* begin of turn: inform about swampHag and hauntedWoods*/
            statusText += getString(R.string.TurnBegin);
            int swampHagAttacks = 0;
            if (extras[3] != null) {
            	swampHagAttacks = (Integer) extras[3];
            }
            if (swampHagAttacks == 1) {
                statusText += "\n" + Strings.format(R.string.SwampHagAttacks_single, swampHagAttacks);
            }
            if (swampHagAttacks >= 2) {
                statusText += "\n" + Strings.format(R.string.SwampHagAttacks_multiple, swampHagAttacks);
            }
            boolean hauntedWoodsAttacks = false;
            if (extras[4] != null) {
            	hauntedWoodsAttacks = (Boolean) extras[4];
            }
            if (hauntedWoodsAttacks) {
                statusText += "\n" + getString(R.string.HauntedWoodsAttacks);
            }
            boolean enchantressAttacks = false;
            if (extras[5] != null) {
            	enchantressAttacks = (Boolean) extras[5];
            }
            if (enchantressAttacks) {
                statusText += "\n" + getString(R.string.EnchantressAttacks);
            }
            if (swampHagAttacks > 0 || hauntedWoodsAttacks || enchantressAttacks)
            	statusText += "\n";
        } else if (event.gameEventType == GameEvent.EventType.CantBuy) {
            Card[] cantBuy = event.o.cs;
            String cards = "";
            boolean first = true;
            for(Card card : cantBuy) {
                if(first) {
                    first = false;
                }
                else {
                    cards += ", ";
                }
                cards += getCardName(card);
            }
            statusText += format(R.string.CantBuy, cards);
        } else if (event.gameEventType == GameEvent.EventType.Status) {
            statusText += format(R.string.action_buys_coin, extras[3], extras[4], extras[5]);
        } else if (event.gameEventType == GameEvent.EventType.GuildsTokenObtained) {
            statusText += format(R.string.GuildsTokenObtained, extras[3]);
        } else if (event.gameEventType == GameEvent.EventType.GuildsTokenSpend) {
            statusText += format(R.string.GuildsTokenSpend, extras[3]);
        } else if (event.gameEventType == GameEvent.EventType.OverpayForCard) {
            statusText += getString(R.string.OverpayForCard);
        } else if (event.gameEventType == GameEvent.EventType.DebtTokensObtained) {
        	statusText += format(R.string.DebtTokensObtained, extras[3]);
        } else if (event.gameEventType == GameEvent.EventType.DebtTokensPaidOff) {
        	statusText += format(R.string.DebtTokensPaidOff, extras[3]);
        } else if (event.gameEventType == GameEvent.EventType.DebtTokensPutOnPile) {
        	statusText += format(R.string.DebtTokensPutOnPile, extras[3]);
        } else if (event.gameEventType == GameEvent.EventType.DebtTokensTakenFromPile) {
        	statusText += format(R.string.DebtTokensTakenFromPile, extras[3]);
        } else if (event.gameEventType == GameEvent.EventType.VPTokensObtained) {
        	statusText += format(R.string.VPTokensObtained, extras[3]);
        } else if (event.gameEventType == GameEvent.EventType.VPTokensPutOnPile) {
        	statusText += format(R.string.VPTokensPutOnPile, extras[3]);
        } else if (event.gameEventType == GameEvent.EventType.VPTokensTakenFromPile) {
        	statusText += format(R.string.VPTokensTakenFromPile, extras[3]);
        } else if (event.gameEventType == GameEvent.EventType.MountainPassBid) {
        	Integer bid = (Integer) extras[3];
        	if (bid == 0) {
        		statusText += getString(R.string.MountainPassBidPass);
        	} else {
        		statusText += format(R.string.MountainPassBid, bid);
        	}
        } else if (event.gameEventType == GameEvent.EventType.MountainPassWinner) {
        	return null;
        } else if (event.gameEventType == GameEvent.EventType.VillagersTokensObtained) {
        	statusText += format(R.string.VillagersObtained, extras[3]);
        } else if (event.gameEventType == GameEvent.EventType.VillagerSpend) {
        	statusText += format(R.string.VillagersSpend, extras[3]);
        } else if (event.gameEventType == GameEvent.EventType.SinisterPlotAdd) {
        	statusText += format(R.string.SinisterPlotAdd, extras[3]);
        } else if (event.gameEventType == GameEvent.EventType.SinisterPlotRemove) {
        	statusText += format(R.string.SinisterPlotRemove, extras[3]);
        } else if (event.gameEventType == GameEvent.EventType.UsedWay) {
            statusText += getString(R.string.UsedWay);
        } else if (event.gameEventType != null) {
            statusText += event.gameEventType.toString();
        }

        // Then, if there's a card associated with the event, we display it here.
        if (event.c != null
        		&& event.gameEventType != GameEvent.EventType.BuyingCard
        		&& event.gameEventType != GameEvent.EventType.CardAddedToHand
                && event.gameEventType != GameEvent.EventType.PlayerAttacking
                && event.gameEventType != GameEvent.EventType.VPTokensObtained
                && event.gameEventType != GameEvent.EventType.CardSetAside
                && event.gameEventType != GameEvent.EventType.CardExiled
                && event.gameEventType != GameEvent.EventType.CardSetAsidePrivate
                && event.gameEventType != GameEvent.EventType.TakeState
                && event.gameEventType != GameEvent.EventType.ReturnState
                && event.gameEventType != GameEvent.EventType.VillagersTokensObtained
                && event.gameEventType != GameEvent.EventType.GuildsTokenObtained
                && event.gameEventType != GameEvent.EventType.GuildsTokenSpend
                && event.gameEventType != GameEvent.EventType.SinisterPlotAdd
                && event.gameEventType != GameEvent.EventType.SinisterPlotRemove) {
            statusText += " " + getCardName(event.c) + " ";
        }

        // And a few other random things that should be added to the status text.
        if (extras == null) {
            return statusText;
        }

        if (event.gameEventType == GameEvent.EventType.TurnBegin && extras[0] != null) {
            statusText += " " + getString(R.string.possessed_by) + " " + extras[0] + "!";
        }
        if (extras[1] != null && event.gameEventType != GameEvent.EventType.TakeState) {
            statusText += " (" + extras[1] + ") ";  // this is the player that's being attacked.
        }
        if (extras[2] != null) {
            statusText += "\n" + extras[2];  // if there's a message from the context, add it here.
        }
        return statusText;
    }

    public static String getFullCardDescription(Card c) {
        String ret = Strings.getCardDescription(c);

        if (c.equals(Cards.curse)) {
            ret = Strings.format(R.string.vp_single, "" + c.getVictoryPoints()) + "\n" + ret;
        }
        if (c.is(Type.Victory)) {
            if (c.getVictoryPoints() > 1)
                ret = Strings.format(R.string.vp_multiple, "" + c.getVictoryPoints()) + "\n" + ret;
            else if (c.getVictoryPoints() > 0)
                ret = Strings.format(R.string.vp_single, "" + c.getVictoryPoints()) + "\n" + ret;
            else if (c.getVictoryPoints() < -1)
                ret = Strings.format(R.string.vp_multiple, "" + c.getVictoryPoints()) + "\n" + ret;
            else if (c.getVictoryPoints() < 0)
                ret = Strings.format(R.string.vp_single, "" + c.getVictoryPoints()) + "\n" + ret;
        }
        if (c.is(Type.Treasure)) {
        	if (c.getAddBuys() > 1) ret = Strings.format(R.string.card_buys_multiple, "" + c.getAddBuys()) + "\n" + ret;
            else if (c.getAddBuys() > 0) ret = Strings.format(R.string.card_buy_single, "" + c.getAddBuys()) + "\n" + ret;
        	int value = c.getAddGold();
        	if (value == 1)
        		ret = Strings.format(R.string.card_coin_single, value) + "\n" + ret;
        	else if (value > 1)
        		ret = Strings.format(R.string.card_coin_multiple, value) + "\n" + ret;
        	if (c.getAddVictoryTokens() > 1) ret = Strings.format(R.string.card_victory_tokens_multiple, "" + c.getAddVictoryTokens()) + "\n" + ret;
            else if (c.getAddVictoryTokens() > 0) ret = Strings.format(R.string.card_victory_token_single, "" + c.getAddVictoryTokens()) + "\n" + ret;
        	if (c.getAddCoffers() > 1) ret = Strings.format(R.string.card_coffers_multiple, "" + c.getAddCoffers()) + "\n" + ret;
            else if (c.getAddCoffers() > 0) ret = Strings.format(R.string.card_coffers_single, "" + c.getAddCoffers()) + "\n" + ret;
        	if (c.getAddVillagers() > 1) ret = Strings.format(R.string.card_villagers_multiple, "" + c.getAddVillagers()) + "\n" + ret;
            else if (c.getAddVillagers() > 0) ret = Strings.format(R.string.card_villager_single, "" + c.getAddVillagers()) + "\n" + ret;
        }
        if (c.is(Type.Action) || c.is(Type.Night) || c.is(Type.Boon) || c.is(Type.Way)) {
            if (c.is(Type.Duration)) {
                if (c.getAddGoldNextTurn() > 1) ret = Strings.format(R.string.coin_next_turn_multiple, "" + c.getAddGoldNextTurn()) + "\n" + ret;
                else if (c.getAddGoldNextTurn() > 0) ret = Strings.format(R.string.coin_next_turn_single, "" + c.getAddGoldNextTurn()) + "\n" + ret;
                if (c.getAddBuysNextTurn() > 1) ret = Strings.format(R.string.buys_next_turn_multiple, "" + c.getAddBuysNextTurn()) + "\n" + ret;
                else if (c.getAddBuysNextTurn() > 0) ret = Strings.format(R.string.buy_next_turn_single, "" + c.getAddBuysNextTurn()) + "\n" + ret;
                if (c.getAddActionsNextTurn() > 1) ret =  Strings.format(R.string.actions_next_turn_multiple, "" + c.getAddActionsNextTurn()) + "\n" + ret;
                else if (c.getAddActionsNextTurn() > 0) ret =  Strings.format(R.string.action_next_turn_single, "" + c.getAddActionsNextTurn()) + "\n" + ret;
                if (c.getAddCardsNextTurn() > 1) ret = Strings.format(R.string.cards_next_turn_multiple, "" + c.getAddCardsNextTurn()) + "\n" + ret;
                else if (c.getAddCardsNextTurn() > 0) ret = Strings.format(R.string.card_next_turn_single, "" + c.getAddCardsNextTurn()) + "\n" + ret;

            }

            if (c.getAddCoffers() > 1) ret = Strings.format(R.string.card_coffers_multiple, "" + c.getAddCoffers()) + "\n" + ret;
            else if (c.getAddCoffers() > 0) ret = Strings.format(R.string.card_coffers_single, "" + c.getAddCoffers()) + "\n" + ret;
        	if (c.getAddVillagers() > 1) ret = Strings.format(R.string.card_villagers_multiple, "" + c.getAddVillagers()) + "\n" + ret;
            else if (c.getAddVillagers() > 0) ret = Strings.format(R.string.card_villager_single, "" + c.getAddVillagers()) + "\n" + ret;
            if (c.getAddGold() > 1) ret = Strings.format(R.string.card_coin_multiple, "" + c.getAddGold()) + "\n" + ret;
            else if (c.getAddGold() > 0) ret = Strings.format(R.string.card_coin_single, "" + c.getAddGold()) + "\n" + ret;
            if (c.getAddBuys() > 1) ret = Strings.format(R.string.card_buys_multiple, "" + c.getAddBuys()) + "\n" + ret;
            else if (c.getAddBuys() > 0) ret = Strings.format(R.string.card_buy_single, "" + c.getAddBuys()) + "\n" + ret;
            if (c.getAddActions() > 1) ret = Strings.format(R.string.card_actions_multiple, "" + c.getAddActions()) + "\n" + ret;
            else if (c.getAddActions() > 0) ret = Strings.format(R.string.card_action_single, "" + c.getAddActions()) + "\n" + ret;
            if (c.getAddCards() > 1) ret = Strings.format(R.string.card_cards_multiple, "" + c.getAddCards()) + "\n" + ret;
            else if (c.getAddCards() > 0) ret = Strings.format(R.string.card_card_single, "" + c.getAddCards()) + "\n" + ret;
            if (c.getAddVictoryTokens() > 1) ret = Strings.format(R.string.card_victory_tokens_multiple, "" + c.getAddVictoryTokens()) + "\n" + ret;
            else if (c.getAddVictoryTokens() > 0) ret = Strings.format(R.string.card_victory_token_single, "" + c.getAddVictoryTokens()) + "\n" + ret;
        }

        if (c.is(Type.Event)) {
            if (c.getAddBuys() > 1) ret = Strings.format(R.string.card_buys_multiple, "" + c.getAddBuys()) + "\n" + ret;
            else if (c.getAddBuys() > 0) ret = Strings.format(R.string.card_buy_single, "" + c.getAddBuys()) + "\n" + ret;
            if (c.getAddVictoryTokens() > 1) ret = Strings.format(R.string.card_victory_tokens_multiple, "" + c.getAddVictoryTokens()) + "\n" + ret;
            else if (c.getAddVictoryTokens() > 0) ret = Strings.format(R.string.card_victory_token_single, "" + c.getAddVictoryTokens()) + "\n" + ret;
        }
        
        if (c.getLinkedStates() != null) {
    		Card[] states = c.getLinkedStates();
    		for (Card state : states) {
	    		String stateName = Strings.getCardName(state);
	    		String stateDesc = Strings.getCardDescription(state);
	    		ret += "\n\n" + Strings.format(R.string.card_linked_stateArtifact, stateName, stateDesc);
    		}
    	}
        return ret;
    }

    /**
     * Takes a card and an array of "options", and returns an array of strings, where each string
     * corresponds to an actual option in the options array.  Some of the items in the options
     * array might not actually be options, they might be information that tells us about the
     * options, or about the header to show, and that depends on the card.  That's why we take the
     * card as input here.
     */
    public static String[] getOptions(Card card, Object[] options) {
        int startIndex = getOptionStartIndex(card, options);
        String[] strings = new String[options.length - startIndex];
                
        if (card != null && getCardName(card).equals(getCardName(Cards.hermit)) && !(options[0] instanceof String)) {
            strings = new String[options.length - 1];
            strings[0] = getString(R.string.none);
            int nonTreasureCountInDiscard = (Integer) options[0];
            String pile = " (" + getString(R.string.discardPile) + ")";
            for (int i = 2; i < options.length; i++) {
                if (i - 2 >= nonTreasureCountInDiscard) {
                    pile = " (" + getString(R.string.hand) + ")";
                }
                strings[i - 1] = getCardName((Card)options[i]) + pile;
            }
            return strings;
        }

        if (card != null && getCardName(card).equals(getCardName(Cards.prince)) && !(options.length > 0 && options[0] instanceof String && options[0].equals(IndirectPlayer.OPTION_CALL_RESOLVE_ACTION))) {
            String[] strings2 = new String[(options.length - startIndex)/2];
            for (int i = startIndex; i < options.length-1; i=i+2) {
                if (options[i] != null && options[i+1] != null) {
                    if ( ((Card)options[i]).equals(Cards.haven) ) {
                        strings2[(i - startIndex)/2] = getCardName((Card)options[i]) 
                                + " (" + "\u261e" + getCardName(((Card) options[i+1])) + ")";
                    } else if ( ((Card)options[i]).equals(Cards.gear) || ((Card)options[i]).equals(Cards.research) ) {
                    	ArrayList<Card> handCards = (ArrayList<Card>) options[i+1];
                    	ArrayList<String> cardNames = new ArrayList<String>(handCards.size());
                    	for (Card c : handCards)
                    		cardNames.add(getCardName(c));
                    	String cardsString = TextUtils.join(", ", cardNames);
                        strings2[(i - startIndex)/2] = getCardName((Card)options[i]) 
                                + " (" + "\u261e" + cardsString + ")";
                    } else if ( ((Card)options[i]).equals(Cards.church) ) {
                    	ArrayList<Card> handCards = (ArrayList<Card>) options[i+1];
                    	String handCardsString = "";
                    	if (!handCards.isEmpty()) {
                    		ArrayList<String> cardNames = new ArrayList<String>(handCards.size());
                        	for (Card c : handCards)
                        		cardNames.add(getCardName(c));
                        	String cardsString = TextUtils.join(", ", cardNames);
                            handCardsString = "\u261e" + cardsString + "; ";
                    	}
                        strings2[(i - startIndex)/2] = getCardName((Card)options[i]) 
                                + " (" + handCardsString + getTrashString() + ")";
                    } else if ( ((Card)options[i]).equals(Cards.archive) || ((Card)options[i]).equals(Cards.crypt) ) {
                    	ArrayList<Card> archiveCards = (ArrayList<Card>) options[i+1];
                    	String cardsString = getCardName(archiveCards.get(0));
                    	if (archiveCards.size() > 1) {
                    		cardsString += ", " + getCardName(archiveCards.get(1));
                    	}
                        strings2[(i - startIndex)/2] = getCardName((Card)options[i]) 
                                + " (" + cardsString + ")";
                    } else if (((Card)options[i]).equals(Cards.prince)
                    		|| ((Card)options[i]).equals(Cards.summon)
                    		|| ((Card)options[i]).equals(Cards.ghost)
                            || ((Card)options[i]).equals(Cards.delay)) {
                        strings2[(i - startIndex)/2] = getCardName((Card)options[i+1]) 
                            + " (" + getCardName(((Card) options[i])) + ")";
                    } else if (((Card)options[i]).equals(Cards.horseTraders)) {
                        strings2[(i - startIndex)/2] = "\u261e" + getCardName((Card)options[i]) 
                            + ", " + getString(R.string.pawn_one);
                    } else if (((Card)options[i]).isCallableWhenTurnStarts() || ((Card)options[i]).equals(Cards.estate)) {
                        strings2[(i - startIndex)/2] = format(R.string.call_optional, getCardName((Card)options[i]));
                    } else if (((Card)options[i]).is(Type.Boon)) {
                        strings2[(i - startIndex)/2] = format(R.string.boon_name_and_desc, getCardName((Card)options[i]), getBoonShortText((Card)options[i]));
                    } else {
                        strings2[(i - startIndex)/2] = getCardName((Card)options[i]);
                    }
                }
            }
            return strings2;
        }
        
        if (card != null && card.equals(Cards.stash) && options[0].equals(IndirectPlayer.OPTION_STASH_POSITION)) {
        	int numStashes = (Integer) options[2];        	
        	if (numStashes == 1) {
	        	strings[0] = getString(R.string.pass);
	        	strings[1] = getString(R.string.stash_on_top);
	        	for (int i = 2; i < strings.length - 1; ++i) {
	        		int cardsDown = i - 1;
	        		strings[i] = format(cardsDown == 1 ? R.string.stash_1_card_down : R.string.stash_x_cards_down, cardsDown);
	        	}
	        	strings[strings.length-1] = getString(R.string.stash_on_bottom);
        	} else {
        		strings[0] = getString(R.string.stash_choose_individually);
	        	strings[1] = getString(R.string.pass);
	        	strings[2] = getString(R.string.stash_on_top);
	        	for (int i = 3; i < strings.length - 1; ++i) {
	        		int cardsDown = i - 2;
	        		strings[i] = format(cardsDown == 1 ? R.string.stash_1_card_down : R.string.stash_x_cards_down, cardsDown);
	        	}
	        	strings[strings.length-1] = getString(R.string.stash_on_bottom);
        	}
        	return strings;
        }

        if (card != null && card.equals(Cards.secretPassage) && !(options.length > 0 && options[0] instanceof String && options[0].equals(IndirectPlayer.OPTION_CALL_RESOLVE_ACTION))) {
            if (strings.length > 0) strings[0] = getString(R.string.stash_on_top);
            for (int i = 1; i < strings.length - 1; ++i) {
                strings[i] = format(i == 1 ? R.string.stash_1_card_down : R.string.stash_x_cards_down, i);
            }
            if (strings.length > 1) strings[strings.length-1] = getString(R.string.stash_on_bottom);
            return strings;
        }
        
        if (card != null && getCardName(card).equals(getCardName(Cards.mountainPass))) {
        	strings[0] = getString(R.string.pass);
        	for (int i = startIndex + 1; i < options.length; i++) {
                strings[i - startIndex] = options[i] + "";
            }
        	return strings;
        }
        
        if (card != null && getCardName(card).equals(getCardName(Cards.mountainPass))) {
        	strings[0] = getString(R.string.pass);
        	for (int i = startIndex + 1; i < options.length; i++) {
                strings[i - startIndex] = options[i] + "";
            }
        	return strings;
        }
        
        if (options[0] instanceof String && IndirectPlayer.OPTION_WAY.equals(options[0])) {
        	strings[0] = getString(R.string.way_play_normally_option);
        	for (int i = startIndex + 1; i < options.length; i++) {
        		strings[i - startIndex] = Strings.getOptionText(options[i], options);
            }
        	return strings;
        }
        
        for (int i = startIndex; i < options.length; i++) {
            strings[i - startIndex] = Strings.getOptionText(options[i], options);
        }
        return strings;
    }

    private static int getOptionStartIndex(Card card, Object[] options) {
        // TODO(matt): it'd be cleaner to make this an enum, or something, instead of using these
        // strings.
    	if (card != null && getCardName(card).equals(getCardName(Cards.mountainPass))) {
    		return 1;
    	}
    	
        if (options[0] instanceof String) {
            String optionString = (String) options[0];
            if (optionString.equals(IndirectPlayer.OPTION_REACTION)) {
                return 1;
            } else if (optionString.equals(IndirectPlayer.OPTION_PUTBACK)) {
                return 1;
            } else if (optionString.equals(IndirectPlayer.OPTION_SPEND_GUILD_COINS)) {
                return 1;
            } else if (optionString.equals(IndirectPlayer.OPTION_SPEND_VILLAGERS)) {
                return 1;
            } else if (optionString.equals(IndirectPlayer.OPTION_OVERPAY)) {
                return 1;
            } else if (optionString.equals(IndirectPlayer.OPTION_OVERPAY_POTION)) {
                return 1;
            } else if (optionString.equals(IndirectPlayer.OPTION_PAY_DEBT)) {
                return 1;
            } else if (optionString.equals(IndirectPlayer.OPTION_CALL_WHEN_GAIN)) {
				return 1;
			} else if (optionString.equals(IndirectPlayer.OPTION_CALL_RESOLVE_ACTION)) {
				return 1;
			} else if (optionString.equals(IndirectPlayer.OPTION_START_TURN_EFFECT)) {
				return 1;
			} else if (optionString.equals(IndirectPlayer.OPTION_STASH)) {
				return 5;
			} else if (optionString.equals(IndirectPlayer.OPTION_STASH_POSITION)) {
				return 4;
			} else if (optionString.equals(IndirectPlayer.OPTION_WAY)) {
				return 2;
			}
        }
        if (card == null)
            return 0;
        String cardName = getCardName(card);
        // TODO(matt): I could put these into sets, for startIndex = 1, 2, etc., to make this more
        // compact.
        if (cardName.equals(getCardName(Cards.advisor))) {
            return 1;
        } else if (cardName.equals(getCardName(Cards.ambassador))) {
            return 1;
        } else if (cardName.equals(getCardName(Cards.doctor))) {
            if (options[0] == null) {
                return 0;
            } else {
                return 1;
            }
        } else if (cardName.equals(getCardName(Cards.envoy))) {
            return 1;
        } else if (cardName.equals(getCardName(Cards.jester))) {
            return 2;
        } else if (cardName.equals(getCardName(Cards.lookout)) || cardName.equals(getCardName(Cards.scoutingParty))) {
            return 1;
        } else if (cardName.equals(getCardName(Cards.pillage))) {
            return 1;
        } else if (cardName.equals(getCardName(Cards.watchTower))) {
            return 1;
        } else if (cardName.equals(getCardName(Cards.annex))) {
            return 1;
        } else if (cardName.equals(getCardName(Cards.sentry))) {
            return 2;
        } else if (cardName.equals(getCardName(Cards.secretPassage))) {
            return 1;
        } else if (cardName.equals(getCardName(Cards.foolsGold))) {
			return 1;
		} else if (cardName.equals(getCardName(Cards.sleigh))) {
			return 1;
		}
        return 0;
    }

    public static String getSelectOptionHeader(Card card, Object[] extras) {
        if (extras[0] instanceof String && ((String)extras[0]).equals(IndirectPlayer.OPTION_PUTBACK)) {
            return getString(R.string.putback_query);
        } else if (extras[0] instanceof String && ((String)extras[0]).equals(IndirectPlayer.OPTION_REACTION)) {
            return getString(R.string.reaction_query) + " [" + getCardName(card) + "]";
        } else if (extras[0] instanceof String && ((String)extras[0]).equals(IndirectPlayer.OPTION_SPEND_GUILD_COINS)) {
            return getString(R.string.spend_guilds_coin_tokens);
        } else if (extras[0] instanceof String && ((String)extras[0]).equals(IndirectPlayer.OPTION_SPEND_VILLAGERS)) {
            return getString(R.string.spend_villager_tokens);
        } else if (extras[0] instanceof String && ((String)extras[0]).equals(IndirectPlayer.OPTION_OVERPAY)) {
            return getString(R.string.buy_overpay);
        } else if (extras[0] instanceof String && ((String)extras[0]).equals(IndirectPlayer.OPTION_OVERPAY_POTION)) {
            return getString(R.string.buy_overpay_by_potions);
        } else if (extras[0] instanceof String && ((String)extras[0]).equals(IndirectPlayer.OPTION_PAY_DEBT)) {
            return getString(R.string.pay_off_debt);
        } else if (extras[0] instanceof String && ((String) extras[0]).equals(IndirectPlayer.OPTION_CALL_WHEN_GAIN)) {
			return format(R.string.call_when_gain_query, getCardName(card));
		} else if (extras[0] instanceof String && ((String) extras[0]).equals(IndirectPlayer.OPTION_CALL_RESOLVE_ACTION)) {
			return format(R.string.call_resolve_action_query, getCardName(card));
		} else if (extras[0] instanceof String && ((String) extras[0]).equals(IndirectPlayer.OPTION_START_TURN_EFFECT)) {
			return getString(R.string.call_start_turn_query);
		} else if (extras[0] instanceof String && (((String) extras[0]).equals(IndirectPlayer.OPTION_STASH)
				|| ((String) extras[0]).equals(IndirectPlayer.OPTION_STASH_POSITION))) {
			if ((Integer)extras[2] == 1) {
				return getString(R.string.place_stash_query);
			}
			return format(R.string.place_stashes_query, extras[2]);
		} else if (extras[0] instanceof String && ((String)extras[0]).equals(IndirectPlayer.OPTION_WAY)) {
			String cardName = getCardName((Card)extras[1]);
            return format(R.string.way_query, cardName);
        } else if (extras[0] instanceof ExtraTurnOption) {
			return getString(R.string.extra_turns_query);
		}
        String cardName = getCardName(card);
        if (cardName.equals(getCardName(Cards.advisor))) {
            return getActionString(ActionType.OPPONENTDISCARD, card, (String) extras[0]);
        } else if (cardName.equals(getCardName(Cards.secretPassage))) {
            return format(R.string.secretPassage_query, getCardName((Card)extras[0]));
        } else if (cardName.equals(getCardName(Cards.ambassador))) {
            return format(R.string.ambassador_query, getCardName((Card)extras[0]));
        } else if (cardName.equals(getCardName(Cards.cartographer))) {
            return format(R.string.Cartographer_query, cardName);
        } else if (cardName.equals(getCardName(Cards.countingHouse))) {
            return getString(R.string.countingHouse_query);
        } else if (cardName.equals(getCardName(Cards.doctor))) {
            if (extras[0] == null) {
                return cardName;
            } else {
                return format(R.string.card_revealed, cardName, getCardName((Card)extras[0]));
            }
        } else if (cardName.equals(getCardName(Cards.envoy))) {
            return getActionString(ActionType.OPPONENTDISCARD, card, (String) extras[0]);
        } else if (cardName.equals(getCardName(Cards.golem))) {
            return getString(R.string.golem_first_action);
        } else if (cardName.equals(getCardName(Cards.prince))) { // Prince signifies all start of turn effects
            return getString(R.string.start_turn_query);
        } else if (cardName.equals(getCardName(Cards.herald))) {
            return getString(R.string.herald_overpay_query) + " [" + cardName + "]";
        } else if (cardName.equals(getCardName(Cards.herbalist))) {
            return getString(R.string.herbalist_query);
        } else if (cardName.equals(getCardName(Cards.hermit))) {
            return getActionString(ActionType.TRASH, Cards.hermit);
        } else if (cardName.equals(getCardName(Cards.jester))) {
            return format(R.string.card_revealed, cardName, getCardName((Card)extras[1]));
        } else if (cardName.equals(getCardName(Cards.lookout)) || cardName.equals(getCardName(Cards.scoutingParty))) {
            if (extras[0] == ActionType.TRASH) {
                return getString(R.string.lookout_query_trash);
            } else if (extras[0] == ActionType.DISCARD) {
                return getString(R.string.lookout_query_discard);
            }
        } else if (cardName.equals(getCardName(Cards.nightWatchman))) {
            return format(R.string.Cartographer_query, cardName);
        } else if (cardName.equals(getCardName(Cards.pillage))) {
            return getActionString(ActionType.OPPONENTDISCARD, card, (String) extras[0]);
        } else if (cardName.equals(getCardName(Cards.pirateShip))) {
            return getString(R.string.treasure_to_trash);
        } else if (cardName.equals(getCardName(Cards.quest)) && extras[0] instanceof Card) {
            return getString(R.string.quest_attack_to_discard);
        } else if (cardName.equals(getCardName(Cards.raze)) || cardName.equals(getCardName(Cards.borderGuard))) {
            return getString(R.string.raze_query);
        } else if(cardName.equals(getCardName(Cards.rogue))) {
			return getString(R.string.rogue_query);
		} else if (cardName.equals(getCardName(Cards.scheme))) {
            return getString(R.string.scheme_query);
        } else if (cardName.equals(getCardName(Cards.sentry))) {
        	Card currentCard = (Card)extras[0];
        	Card[] cards = (Card[])extras[1];
            return format(R.string.sentry_look_at, cardName, getCardName(currentCard), combineCardNames(cards));
        } else if (cardName.equals(getCardName(Cards.smugglers))) {
            return getString(R.string.smuggle_query);
        } else if (cardName.equals(getCardName(Cards.teacher))) {
			return getString(R.string.teacher_query);
        } else if (cardName.equals(getCardName(Cards.theSunsGift))) {
            return format(R.string.Cartographer_query, cardName);
        } else if (cardName.equals(getCardName(Cards.thief))) {
            if (extras[0] == null) {
                // In this case we're gaining treasures that have been trashed.
                return getString(R.string.thief_query);
            } else {
                // And in this case we're deciding which of two treasures we should trash.
                return getString(R.string.treasure_to_trash);
            }
        } else if (cardName.equals(getCardName(Cards.bandit))) {
            return getString(R.string.bandit_treasure_to_trash);
        } else if (cardName.equals(getCardName(Cards.watchTower))) {
            return format(R.string.watch_tower_query, getCardName((Card)extras[0]));
        } else if (cardName.equals(getCardName(Cards.wineMerchant))) {
            return getString(R.string.wineMerchant_query);
        } else if (cardName.equals(getCardName(Cards.estate))) {
            return getString(R.string.wineMerchantEstate_query);
        } else if (cardName.equals(getCardName(Cards.mountainPass))) {
        	if (extras[0] == null) {
        		return getString(R.string.mountainPass_query);
        	} else {
        		return format(R.string.mountainPass_high_bid_query, extras[0]);
        	}
        } else if (cardName.equals(getCardName(Cards.archive))) {
            return getString(R.string.archive_query);
        } else if (cardName.equals(getCardName(Cards.annex))) {
            return format(R.string.annex_query, extras[0]);
        } else if (cardName.equals(getCardName(Cards.changeling))) {
            return format(R.string.changeling_gain_query, cardName);
        } else if (cardName.equals(getCardName(Cards.crypt))) {
            return format(R.string.crypt_query, cardName);
        } else if (cardName.equals(getCardName(Cards.fool))) {
            return format(R.string.fool_query, cardName);
        } else if (cardName.equals(getCardName(Cards.necromancer))) {
            return format(R.string.necromancer_query, cardName);
        } else if (cardName.equals(getCardName(Cards.raider)) ||
        		cardName.equals(getCardName(Cards.villain))) {
            return format(R.string.discard_card_query, cardName);
        } else if (cardName.equals(getCardName(Cards.theMoonsGift)) ||
        		cardName.equals(getCardName(Cards.harbinger)) ||
        		cardName.equals(getCardName(Cards.scavenger))) {
            return format(R.string.deck_from_discard_query, cardName);
        } else if (cardName.equals(getCardName(Cards.improve))) {
            return format(R.string.improve_query, cardName);
        } else if (cardName.equals(getCardName(Cards.scepter))) {
            return format(R.string.scepter_query, cardName);
        } else if (cardName.equals(getCardName(Cards.silos))) {
            return format(R.string.silos_query, cardName);
        } else if (cardName.equals(getCardName(Cards.starChart))) {
            return format(R.string.starChart_query, cardName);
        } else if (cardName.equals(getCardName(Cards.mountainVillage))) {
            return format(R.string.hand_from_discard_query, cardName);
        } else if (cardName.equals(getCardName(Cards.march))) {
            return format(R.string.play_from_discard_query, cardName);
        } else if (cardName.equals(getCardName(Cards.transport))) {
             if (extras[0] instanceof TransportOption) {
                 return cardName;
             } else {
                 return getString(R.string.transport_query);
             }
        }
        return cardName;
    }
    /**
     * Takes an option object and returns the string the corresponds to the option.  The API could
     * be a little cleaner than just passing Objects around, but it was the easiest way to change
     * the API to decouple to server code from the android framework.
     */
    public static String getOptionText(Object option, Object[] extras) {
        if (option instanceof SpiceMerchantOption) {
            // Actually, if this works without a cast, and it appears that it does, we don't need
            // the outer if-statements.  But maybe this way is a little more organized?  Or we
            // could do an outer switch?  Would that be any faster?
            if (option == SpiceMerchantOption.AddCardsAndAction) {
                return getString(R.string.spice_merchant_option_one);
            } else if (option == SpiceMerchantOption.AddGoldAndBuy) {
                return getString(R.string.spice_merchant_option_two);
            }
        } else if (option instanceof TorturerOption) {
            if (option == TorturerOption.TakeCurse) {
                return getString(R.string.torturer_option_one);
            } else if (option == TorturerOption.DiscardTwoCards) {
                return getString(R.string.torturer_option_two);
            }
        } else if (option instanceof StewardOption) {
            if (option == StewardOption.AddCards) {
                return getString(R.string.steward_option_one);
            } else if (option == StewardOption.AddGold) {
                return getString(R.string.steward_option_two);
            } else if (option == StewardOption.TrashCards) {
                return getString(R.string.steward_option_three);
            }
        } else if (option instanceof AmuletOption) {
            if (option == AmuletOption.AddGold) {
                return getString(R.string.amulet_option_one);
            } else if (option == AmuletOption.TrashCard) {
                return getString(R.string.trash_card_from_hand);
            } else if (option == AmuletOption.GainSilver) {
                return getString(R.string.gain_silver);
            }
        } else if (option instanceof ExtraTurnOption) {
            if (option == ExtraTurnOption.OutpostFirst) {
                return getString(R.string.extra_turns_outpost_first);
            } else if (option == ExtraTurnOption.MissionFirst) {
                return getString(R.string.extra_turns_mission_first);
            } else if (option == ExtraTurnOption.PossessionFirst) {
                return getString(R.string.extra_turns_possession_first);
            }
        } else if (option instanceof QuestOption) {
            if (option == QuestOption.DiscardAttack) {
                return getString(R.string.quest_discard_attack);
            } else if (option == QuestOption.DiscardTwoCurses) {
                return getString(R.string.quest_discard_two_curses);
            } else if (option == QuestOption.DiscardSixCards) {
                return getString(R.string.quest_discard_six_cards);
            }
        } else if (option instanceof NoblesOption) {
            if (option == NoblesOption.AddCards) {
                return getString(R.string.nobles_option_one);
            } else if (option == NoblesOption.AddActions) {
                return getString(R.string.nobles_option_two);
            }
        } else if (option instanceof MinionOption) {
            if (option == MinionOption.AddGold) {
                return getString(R.string.minion_option_one);
            } else if (option == MinionOption.RolloverCards) {
                return getString(R.string.minion_option_two);
            }
        } else if (option instanceof WatchTowerOption) {
            if (option == WatchTowerOption.Normal) {
                return getString(R.string.watch_tower_option_one);
            } else if (option == WatchTowerOption.Trash) {
                return getString(R.string.trash);
            } else if (option == WatchTowerOption.TopOfDeck) {
                return getString(R.string.watch_tower_option_three);
            }
        } else if (option instanceof JesterOption) {
            if (option == JesterOption.GainCopy) {
                return getString(R.string.jester_option_one);
            } else if (option == JesterOption.GiveCopy) {
                 return format(R.string.jester_option_two, extras[0]);
            }
        } else if (option instanceof TournamentOption) {
            if (option == TournamentOption.GainPrize) {
                return getString(R.string.tournament_option_two);
            } else if (option == TournamentOption.GainDuchy) {
                return getString(R.string.tournament_option_three);
            }
        } else if (option instanceof TrustySteedOption) {
            if (option == TrustySteedOption.AddActions) {
                return getString(R.string.trusty_steed_option_one);
            } else if (option == TrustySteedOption.AddCards) {
                return getString(R.string.trusty_steed_option_two);
            } else if (option == TrustySteedOption.AddGold) {
                return getString(R.string.trusty_steed_option_three);
            } else if (option == TrustySteedOption.GainSilvers) {
                return getString(R.string.trusty_steed_option_four);
            }
        } else if (option instanceof SquireOption) {
            if (option == SquireOption.AddActions) {
                return getString(R.string.squire_option_one);
            } else if (option == SquireOption.AddBuys) {
                return getString(R.string.squire_option_two);
            } else if (option == SquireOption.GainSilver) {
                return getString(R.string.squire_option_three);
            }
        } else if (option instanceof CountFirstOption) {
            if (option == CountFirstOption.Discard) {
                return getString(R.string.count_firstoption_one);
            } else if (option == CountFirstOption.PutOnDeck) {
                return getString(R.string.count_firstoption_two);
            } else if (option == CountFirstOption.GainCopper) {
                return getString(R.string.count_firstoption_three);
            }
        } else if (option instanceof CountSecondOption) {
            if (option == CountSecondOption.Coins) {
                return getString(R.string.count_secondoption_one);
            } else if (option == CountSecondOption.TrashHand) {
                return getString(R.string.count_secondoption_two);
            } else if (option == CountSecondOption.GainDuchy) {
                return getString(R.string.count_secondoption_three);
            }
        } else if (option instanceof GraverobberOption) {
            if (option == GraverobberOption.GainFromTrash) {
                return getString(R.string.graverobber_option_one);
            } else if (option == GraverobberOption.TrashActionCard) {
                return getString(R.string.graverobber_option_two);
            }
        } else if (option instanceof HuntingGroundsOption) {
            if (option == HuntingGroundsOption.GainDuchy) {
                return getString(R.string.hunting_grounds_option_one);
            } else if (option == HuntingGroundsOption.GainEstates) {
                return getString(R.string.hunting_grounds_option_two);
            }
        } else if (option instanceof GovernorOption) {
            if (option == GovernorOption.AddCards) {
                return getString(R.string.governor_option_one);
            } else if (option == GovernorOption.GainTreasure) {
                return getString(R.string.governor_option_two);
            } else if (option == GovernorOption.Upgrade) {
                return getString(R.string.governor_option_three);
            }
        } else if (option instanceof PawnOption) {
            if (option == PawnOption.AddCard) {
                return getString(R.string.pawn_one);
            } else if (option == PawnOption.AddAction) {
                return getString(R.string.pawn_two);
            } else if (option == PawnOption.AddBuy) {
                return getString(R.string.pawn_three);
            } else if (option == PawnOption.AddGold) {
                return getString(R.string.pawn_four);
            }
        } else if (option instanceof PlayerSupplyToken) {
			if (option == PlayerSupplyToken.PlusOneCard) {
				return getString(R.string.plus_one_card_token);
			} else if (option == PlayerSupplyToken.PlusOneAction) {
				return getString(R.string.plus_one_action_token);
			} else if (option == PlayerSupplyToken.PlusOneBuy) {
				return getString(R.string.plus_one_buy_token);
			} else if (option == PlayerSupplyToken.PlusOneCoin) {
				return getString(R.string.plus_one_coin_token);
			}
        } else if (option instanceof DoctorOverpayOption) {
            if (option == DoctorOverpayOption.TrashIt) {
                return getString(R.string.doctor_overpay_option_one);
            } else if (option == DoctorOverpayOption.DiscardIt) {
                return getString(R.string.doctor_overpay_option_two);
            } else if (option == DoctorOverpayOption.PutItBack) {
                return getString(R.string.doctor_overpay_option_three);
            }
        } else if (option instanceof CharmOption) {
            if (option == CharmOption.OneBuyTwoCoins) {
                return getString(R.string.charm_option_one);
            } else if (option == CharmOption.NextBuyGainDifferentWithSameCost) {
                return getString(R.string.charm_option_two);
            }
        } else if (option instanceof EncampmentOption) {
            if (option == EncampmentOption.RevealGold) {
                return getString(R.string.reveal_gold);
            } else if (option == EncampmentOption.RevealPlunder) {
                return getString(R.string.reveal_plunder);
            }
        } else if (option instanceof WildHuntOption) {
            if (option == WildHuntOption.Draw3AndPlaceToken) {
                return getString(R.string.draw_three_add_token);
            } else if (option == WildHuntOption.GainEstateAndTokens) {
                return getString(R.string.gain_estate_and_tokens);
            }
        } else if (option instanceof MonasteryOption) {
            if (option == MonasteryOption.TrashFromHand) {
                return getString(R.string.trash_card_from_hand);
            } else if (option == MonasteryOption.TrashCopperFromPlay) {
                return getString(R.string.monastery_trash_copper_from_play);
            } else {
            	return getString(R.string.pass);
            }
        } else if (option instanceof TreasurerOption) {
            if (option == TreasurerOption.TrashTreasure) {
                return getString(R.string.treasurer_trash_treasure);
            } else if (option == TreasurerOption.GainTreasureFromTrash) {
                return getString(R.string.treasurer_gain_treasure_from_trash);
            } else if (option == TreasurerOption.TakeKey) {
                return getString(R.string.treasurer_take_key);
            }
        } else if (option instanceof StashOption) {
            if (option == StashOption.PlaceOnTop) {
                return getString(R.string.stash_on_top);
            } else if (option == StashOption.PlaceAfterCardsToDraw) {
            	int deckSize = (Integer) extras[1];
            	int cardsToDraw = (Integer) extras[3];
            	if (cardsToDraw >= deckSize) {
            		return getString(R.string.stash_on_bottom);
            	} else {
            		return format(R.string.stash_after_cards_to_draw, cardsToDraw);
            	}
            } else if (option == StashOption.PlaceOther) {
                return getString(R.string.stash_more_options);
            }
        } else if (option instanceof PutBackOption) {
            if (option == PutBackOption.Action) {
                return getString(R.string.putback_option_two);
            } else if (option == PutBackOption.Alchemist) {
                return getCardName(Cards.alchemist);
            } else if (option == PutBackOption.Coin) {
                return getString(R.string.putback_option_one);
            } else if (option == PutBackOption.None) {
                return getString(R.string.none);
            } else if (option == PutBackOption.All) {
                return getString(R.string.putback_option_all);
            } else if (option == PutBackOption.Treasury) {
                return getCardName(Cards.treasury);
            } else if (option == PutBackOption.WalledVillage) {
                return getCardName(Cards.walledVillage);
            } else if (option == PutBackOption.BorderGuard) {
                return getCardName(Cards.borderGuard);
            }
        } else if (option instanceof CourtierOption) {
            if (option == CourtierOption.AddAction) {
                return getString(R.string.courtier_option_add_action);
            } else if (option == CourtierOption.AddBuy) {
                return getString(R.string.courtier_option_add_buy);
            } else if (option == CourtierOption.AddCoins) {
                return getString(R.string.courtier_option_add_coins);
            } else if (option == CourtierOption.GainGold) {
                return getString(R.string.courtier_option_gain_gold);
            }
        } else if (option instanceof LurkerOption) {
            if (option == LurkerOption.GainFromTrash) {
                return getString(R.string.lurker_gainFromTrash);
            } else if (option == LurkerOption.TrashActionFromSupply) {
                return getString(R.string.lurker_trashActionFromSupply);
            }
        } else if (option instanceof SentryOption) {
            if (option == SentryOption.Trash) {
                return getString(R.string.trash);
            } else if (option == SentryOption.Discard) {
                return getString(R.string.discard);
            } else if (option == SentryOption.PutBack) {
                return getString(R.string.doctor_overpay_option_three);
            }
        } else if (option instanceof FoolsGoldOption) {
            if (option == FoolsGoldOption.TrashForGold) {
                return getString(R.string.fools_gold_option_one);
            } else if (option == FoolsGoldOption.Pass) {
                return getString(R.string.pass);
            } else if (option == FoolsGoldOption.PassAll) {
                return getString(R.string.pass_all);
            }
        } else if (option instanceof ScrapOption) {
            if (option == ScrapOption.AddAction) {
                return getString(R.string.scrap_option_add_action);
            } else if (option == ScrapOption.AddCard) {
                return getString(R.string.scrap_option_add_card);
            } else if (option == ScrapOption.AddBuy) {
                return getString(R.string.scrap_option_add_buy);
            } else if (option == ScrapOption.AddCoin) {
                return getString(R.string.scrap_option_add_coin);
            } else if (option == ScrapOption.GainSilver) {
                return getString(R.string.scrap_option_gain_silver);
            } else if (option == ScrapOption.GainHorse) {
                return getString(R.string.scrap_option_gain_horse);
            }
        } else if (option instanceof SleighOption) {
            if (option == SleighOption.DiscardForGainedCardToHand) {
            	return format(R.string.sleigh_option_discard_for_gained_to_hand, getCardName((Card)extras[0]));
            } else if (option == SleighOption.DiscardForGainedCardToDeck) {
            	return format(R.string.sleigh_option_discard_for_gained_to_deck, getCardName((Card)extras[0]));
            } else if (option == SleighOption.Pass) {
            	return getString(R.string.pass);
            }
        } else if (option instanceof TransportOption) {
            if (option == TransportOption.ExileActionFromSupply) {
                return getString(R.string.transport_exileActionFromSupply);
            } else if (option == TransportOption.TopdeckActionFromExile) {
                return getString(R.string.transport_topdeckActionFromExile);
            }
        } else if (option instanceof Card && ((Card)option).is(Type.Boon)) {
            return format(R.string.boon_name_and_desc, getCardName((Card) option), getBoonShortText((Card) option));
        } else if (option instanceof Card && ((Card)option).is(Type.Way)) {
            return format(R.string.way_name_and_desc, getWayShortName((Card) option), getWayShortText((Card) option));
        } else if (option instanceof Card) {
            return getCardName((Card) option);
        } else if (option == null) {
            return getString(R.string.none);
        } else if (option instanceof Integer) {
            return "" + option;
        }
        throw new RuntimeException("I got passed an option object that I don't understand!: " + option.toString());
    }

    /**
     * Get the strings necessary for selecting a boolean option initiated by cardResponsible,
     * using extras to fill in the necessary information for creating the strings.
     *
     * The first item in the returned array is the header to show, the second item is the string
     * that will be interpreted as "true" if selected, and the third item is the string that will
     * be interpreted as "false".
     */
    public static String[] getBooleanStrings(Card cardResponsible, Object[] extras) {
    	// Handle non-card boolean choices - extras[0] should distinguish choices
    	String[] strings = new String[3];
    	if (cardResponsible == null) {
    		if (extras[0].equals(IndirectPlayer.BOOLEAN_USE_VILLAGER)) {
    			strings[0] = getString(R.string.villager_query);
    			strings[1] = getString(R.string.villager_option);
    			strings[2] = getString(R.string.pass);
    		} else if (extras[0].equals(IndirectPlayer.BOOLEAN_DISCARD_FROM_EXILE)) {
    			Card c = (Card)extras[1];
    			int copies = (Integer)extras[2];
    			strings[0] = format(R.string.discard_from_exile_query, c, copies);
    			strings[1] = getString(R.string.discard_from_exile_option);
    			strings[2] = getString(R.string.pass);
    		}
    		return strings;
    	}
    	
        // See note below under getActionCardText for why we can't test for object equality here,
        // and instead use string equality.
    	String cardName = getCardName(cardResponsible);
        String controlName = getCardName(cardResponsible);

        strings[0] = cardName;  // common enough to set this as a default; override if necessary.
        if (cardName.equals(getCardName(Cards.alchemist))) {
            strings[1] = getString(R.string.alchemist_option_one);
            strings[2] = getString(R.string.alchemist_option_two);
        } else if (cardName.equals(getCardName(Cards.baron))) {
            strings[1] = getString(R.string.baron_option_one);
            strings[2] = getString(R.string.baron_option_two);
        } else if (cardName.equals(getCardName(Cards.catacombs))) {
            strings[0] = format(R.string.catacombs_header, combineCardNames(extras));
            strings[1] = getString(R.string.catacombs_option_one);
            strings[2] = getString(R.string.catacombs_option_two);
        } else if (   cardName.equals(getCardName(Cards.chancellor))
        		   || cardName.equals(getCardName(Cards.scavenger))
        		   || cardName.equals(getCardName(Cards.messenger))) {
            strings[1] = getString(R.string.chancellor_query);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.cultist))) {
            strings[1] = getString(R.string.cultist_play_next);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.duchess))) {
            if (extras == null) {
                // This is asking if you want to _gain_ a duchess (upon purchase of a duchy).
                strings[0] = getString(R.string.duchess_query);
                strings[1] = getString(R.string.duchess_option_one);
                strings[2] = getString(R.string.pass);
            } else {
                // And this one is from _playing_ the duchess.
                strings[0] = getCardRevealedHeader(extras);
                strings[1] = getString(R.string.duchess_play_option_one);
                strings[2] = getString(R.string.discard);
            }
        } else if (cardName.equals(getCardName(Cards.explorer))) {
            strings[1] = getString(R.string.explorer_reveal);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.hovel))) {
            strings[1] = getString(R.string.hovel_option);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.illGottenGains))) {
            strings[1] = getString(R.string.ill_gotten_gains_option_one);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.inn))) {
            strings[0] = getCardRevealedHeader(extras);
            strings[1] = getString(R.string.inn_option_one);
            strings[2] = getString(R.string.inn_option_two);
        } else if (cardName.equals(getCardName(Cards.ironmonger))) {
            strings[0] = getCardRevealedHeader(extras);
            strings[1] = getString(R.string.ironmonger_option_one);
            strings[2] = getString(R.string.discard);
        } else if (cardName.equals(getCardName(Cards.jackOfAllTrades))) {
            strings[0] = getCardRevealedHeader(extras);
            strings[1] = getString(R.string.jack_of_all_trades_option_one);
            strings[2] = getString(R.string.discard);
        } else if (cardName.equals(getCardName(Cards.library))) {
            strings[0] = getCardRevealedHeader(extras);
            strings[1] = getString(R.string.keep);
            strings[2] = getString(R.string.discard);
        } else if (cardName.equals(getCardName(Cards.loan))) {
            strings[0] = getCardRevealedHeader(extras);
            strings[1] = getString(R.string.trash);
            strings[2] = getString(R.string.discard);
        } else if (cardName.equals(getCardName(Cards.madman))) {
            strings[1] = getString(R.string.madman_option);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.marketSquare))) {
        	if (getCardName((Card)extras[0]).equals(getCardName(Cards.estate))) {
        		strings[0] = getCardName(Cards.estate) + " (" + getCardName(Cards.marketSquare) + ")";
        	}
            strings[1] = getString(R.string.discard);
            strings[2] = getString(R.string.keep);
        } else if (cardName.equals(getCardName(Cards.miningVillage))) {
            if (!controlName.equals(cardName)) strings[0] = Strings.format(getString(R.string.card_played_as_card), controlName, cardName);
            strings[1] = getString(R.string.mining_village_option_one);
            strings[2] = getString(R.string.keep);
        } else if (cardName.equals(getCardName(Cards.moneyLender))) {
            strings[1] = getString(R.string.moneylender_trash_copper);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.mountebank))) {
            strings[0] = getString(R.string.mountebank_query);
            strings[1] = getString(R.string.mountebank_option_one);
            strings[2] = getString(R.string.mountebank_option_two);
        } else if (cardName.equals(getCardName(Cards.pearlDiver))) {
            strings[0] = getCardRevealedHeader(extras);
            strings[1] = getString(R.string.pearldiver_option_one);
            strings[2] = getString(R.string.pearldiver_option_two);
        } else if (cardName.equals(getCardName(Cards.pirateShip))) {
            strings[1] = format(R.string.pirate_ship_option_one, "" + (Integer) extras[0]);
            strings[2] = getString(R.string.pirate_ship_option_two);
        } else if (cardName.equals(getCardName(Cards.miser))) {
            strings[1] = format(R.string.miser_option_one, "" + (Integer) extras[0]);
            strings[2] = getString(R.string.miser_option_two);
        } else if (cardName.equals(getCardName(Cards.nativeVillage))) {
            strings[1] = getString(R.string.native_village_option_one);
            strings[2] = getString(R.string.native_village_option_two);
        } else if (cardName.equals(getCardName(Cards.navigator))) {
            strings[0] = Strings.format(R.string.navigator_header, combineCardNames(extras));
            strings[1] = getString(R.string.discard);
            strings[2] = getString(R.string.navigator_option_two);
        } else if (cardName.equals(getCardName(Cards.nobleBrigand))) {
            strings[0] = Strings.format(R.string.noble_brigand_query, extras[0]);
            strings[1] = getCardName((Card) extras[1]);
            strings[2] = getCardName((Card) extras[2]);
        } else if (cardName.equals(getCardName(Cards.oracle))) {
            String cardNames = combineCardNames(extras, 1);
            strings[0] = format(R.string.card_revealed, extras[0], cardNames);
            strings[1] = getString(R.string.top_of_deck);
            strings[2] = getString(R.string.discard);
        } else if (cardName.equals(getCardName(Cards.raze))) {
            if (!controlName.equals(cardName)) strings[0] = Strings.format(getString(R.string.card_played_as_card), controlName, cardName);
            strings[1] = getString(R.string.trash_this);
            strings[2] = getString(R.string.trash_card_from_hand);
        } else if (cardName.equals(getCardName(Cards.royalSeal)) || cardName.equals(getCardName(Cards.travellingFair)) || cardName.equals(getCardName(Cards.tracker)) || cardName.equals(getCardName(Cards.wayOfTheSeal))) {
            strings[0] = getCardName((Card) extras[1]);
            strings[1] = getString(R.string.top_of_deck);
            strings[2] = getString(R.string.take_normal);
        } else if (cardName.equals(getCardName(Cards.scryingPool))) {
            strings[0] = getPlayerRevealedCardHeader(extras);
            strings[1] = getString(R.string.discard);
            strings[2] = getString(R.string.replace);
        } else if (cardName.equals(getCardName(Cards.spy))) {
            strings[0] = getPlayerRevealedCardHeader(extras);
            strings[1] = getString(R.string.discard);
            strings[2] = getString(R.string.replace);
        } else if (cardName.equals(getCardName(Cards.survivors))) {
            strings[0] = Strings.format(R.string.survivors_header, combineCardNames(extras));
            strings[1] = getString(R.string.discard);
            strings[2] = getString(R.string.navigator_option_two);
        } else if (cardName.equals(getCardName(Cards.tournament))) {
            strings[1] = getString(R.string.tournament_reveal);
            strings[2] = getString(R.string.tournament_option_one);
        } else if (cardName.equals(getCardName(Cards.trader))) {
            String c_name = getCardName((Card)extras[0]);
            strings[1] = format(R.string.trader_gain, c_name);
            strings[2] = format(R.string.trader_gain_instead_of, getCardName(Cards.silver), c_name);
        } else if (cardName.equals(getCardName(Cards.tunnel))) {
            strings[0] = getString(R.string.tunnel_query);
            strings[1] = getString(R.string.tunnel_option_one);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.urchin))) {
            if (!controlName.equals(cardName)) strings[0] = Strings.format(getString(R.string.card_played_as_card), controlName, cardName);
            strings[1] = getString(R.string.urchin_trash_for_mercenary);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.vassal))) {
            strings[1] = format(R.string.vassal_option_play, getCardName((Card)extras[0]));
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.walledVillage))) {
            strings[1] = getString(R.string.walledVillage_option_one);
            strings[2] = getString(R.string.walledVillage_option_two);
        } else if (cardName.equals(getCardName(Cards.youngWitch))) {
            strings[1] = format(R.string.bane_option_one, getCardName((Card)extras[0]));
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.wineMerchant)) || cardName.equals(getCardName(Cards.estate))) {
            strings[1] = getString(R.string.wineMerchant_option_one);
            strings[2] = getString(R.string.pass);
        } else if (   cardName.equals(getCardName(Cards.peasant))
     		       || cardName.equals(getCardName(Cards.soldier))
    		       || cardName.equals(getCardName(Cards.fugitive))
        		   || cardName.equals(getCardName(Cards.disciple))
    	    	   || cardName.equals(getCardName(Cards.page))
    		       || cardName.equals(getCardName(Cards.treasureHunter))
    		       || cardName.equals(getCardName(Cards.warrior))
    		       || cardName.equals(getCardName(Cards.hero))
        		  ) {
            if (!controlName.equals(cardName)) strings[0] = Strings.format(getString(R.string.card_played_as_card), controlName, cardName);
            strings[1] = format(R.string.traveller_exchange, getCardName((Card)extras[0]));
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.settlers))) {
	         strings[1] = getString(R.string.settlers_query);
	         strings[2] = getString(R.string.pass);
	    } else if (cardName.equals(getCardName(Cards.bustlingVillage))) {
	         strings[1] = getString(R.string.bustlingVillage_query);
	         strings[2] = getString(R.string.pass);
	    } else if (cardName.equals(getCardName(Cards.engineer))) {
            strings[1] = getString(R.string.trash_this_gain_another);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.gladiator))) {
            strings[0] = getPlayerRevealedCardHeader(extras);
            strings[1] = getString(R.string.reveal_copy);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.legionary))) {
            strings[1] = getString(R.string.legionary_reveal_gold);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.smallCastle))) {
            if (!controlName.equals(cardName)) strings[0] = Strings.format(getString(R.string.card_played_as_card), controlName, cardName);
            strings[1] = getString(R.string.trash_this);
            strings[2] = getString(R.string.trash_castle_from_hand);
        } else if ( cardName.equals(getCardName(Cards.sprawlingCastle))) {
            strings[1] = getString(R.string.sprawlingcastle_gain_duchy);
            strings[2] = getString(R.string.sprawlingcastle_gain_estates);
        } else if (cardName.equals(getCardName(Cards.blessedVillage))) {
            strings[1] = format(R.string.blessedVillage_receive_boon_now, getCardName((Card)extras[0]), getBoonShortText((Card)extras[0]));
            strings[2] = getString(R.string.blessedVillage_receive_boon_next_turn);
        } else if (cardName.equals(getCardName(Cards.changeling))) {
            strings[0] = format(R.string.gained_card, getCardName((Card)extras[0]));
            strings[1] = format(R.string.changeling_exchange, getCardName((Card)extras[0]), cardName);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.faithfulHound))) {
            strings[0] = getString(R.string.faithfulHound_query);
            strings[1] = getString(R.string.faithfulHound_set_aside);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.monastery))) {
            strings[1] = getString(R.string.monastery_trash_copper_from_play);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.pixie))) {
            strings[1] = format(R.string.pixie_trash_for_double_boon, getCardName((Card)extras[0]), getBoonShortText((Card)extras[0]));
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.sacredGrove))) {
            strings[1] = format(R.string.sacredGrove_receive_boon, getCardName((Card)extras[0]), getBoonShortText((Card)extras[0]));
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.zombieSpy))) {
            strings[0] = getCardRevealedHeader(extras);
            strings[1] = getString(R.string.discard);
            strings[2] = getString(R.string.replace);
        } else if (cardName.equals(getCardName(Cards.borderGuard))) {
            strings[1] = format(R.string.take_artifact, getCardName(Cards.lantern));
            strings[2] = format(R.string.take_artifact, getCardName(Cards.horn));
        } else if (cardName.equals(getCardName(Cards.cargoShip))) {
        	strings[0] = format(R.string.gained_card, getCardName((Card)extras[0]));
            strings[1] = format(R.string.set_aside_for_next_turn, cardName);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.ducat))) {
            strings[1] = getString(R.string.ducat_trash_copper);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.innovation))) {
            strings[1] = format(R.string.innovation_set_aside_to_play, getCardName((Card)extras[1]));
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.oldWitch))) {
            strings[1] = getString(R.string.oldWitch_trash_curse);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.pageant))) {
            strings[1] = getString(R.string.pageant_pay_coin_for_coffers);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.scepter))) {
            strings[1] = getString(R.string.scepter_add_coins);
            strings[2] = getString(R.string.scepter_replay_action);
        } else if (cardName.equals(getCardName(Cards.sinisterPlot))) {
        	int tokens = (Integer)extras[0];
            strings[1] = getString(R.string.sinisterPlot_add_token);
            int tokenRemoveResource = tokens == 0 ? R.string.sinisterPlot_remove_token_zero : 
            	(tokens == 1 ? R.string.sinisterPlot_remove_tokens_one : R.string.sinisterPlot_remove_tokens_multiple);
            strings[2] = format(tokenRemoveResource, tokens);
        } else if (cardName.equals(getCardName(Cards.animalFair))) {
        	strings[1] = getString(R.string.animalFair_pay_cost);
            strings[2] = getString(R.string.animalFair_pay_by_trashing);
        } else if (cardName.equals(getCardName(Cards.barge))) {
        	strings[1] = getString(R.string.barge_now);
            strings[2] = getString(R.string.barge_next_turn);
        } else if (cardName.equals(getCardName(Cards.blackCat)) || 
        		cardName.equals(getCardName(Cards.falconer)) ||
        		cardName.equals(getCardName(Cards.sheepdog))) {
        	strings[1] = getString(R.string.reaction_play);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.desperation))) {
        	strings[1] = getString(R.string.gain_curse);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.huntingLodge))) {
        	strings[1] = getString(R.string.huntingLodge_discard);
        	strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.gamble))) {
            strings[0] = getCardRevealedHeader(extras);
            strings[1] = format(R.string.gamble_play_card, getCardName((Card)extras[1]));
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.kiln))) {
            strings[1] = format(R.string.kiln_gain_copy, getCardName((Card)extras[1]));
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.wayfarer))) {
        	strings[1] = getString(R.string.gain_silver);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.villageGreen))) {
        	if (((String)extras[0]).equals(IndirectPlayer.BOOLEAN_PLAY)) {
        		strings[0] = getString(R.string.villageGreen_query);
        		strings[1] = getString(R.string.reaction_play);
                strings[2] = getString(R.string.pass);
        	} else {
        		strings[1] = getString(R.string.villageGreen_now);
                strings[2] = getString(R.string.villageGreen_next_turn);
        	}
        } else if (cardName.equals(getCardName(Cards.prince))) {
            strings[1] = getString(R.string.set_aside);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.sauna))) {
            strings[1] = getString(R.string.sauna_play_avanto);
            strings[2] = getString(R.string.pass);
        } else if (cardName.equals(getCardName(Cards.avanto))) {
            strings[1] = getString(R.string.avanto_play_sauna);
            strings[2] = getString(R.string.pass);
        }
        if (strings[1] != null) {
            return strings;
        }
        throw new RuntimeException("I got passed a card that I don't know how to create a boolean "
                                   + "option for!: " + cardName);
    }

    public static String combineCardNames(Object[] cards) {
        return combineCardNames(cards, 0);
    }

    public static String getCardRevealedHeader(Object[] extras) {
        return getCardRevealedHeader((Card) extras[0], (Card) extras[1]);
    }

    public static String getCardRevealedHeader(Card responsible, Card revealed) {
        return format(R.string.card_revealed, getCardName(responsible), getCardName(revealed));
    }

    public static String getPlayerRevealedCardHeader(Object[] extras) {
        return getPlayerRevealedCardHeader((String) extras[0], (Card) extras[1], (Card) extras[2]);
    }

    public static String getPlayerRevealedCardHeader(String name, Card responsible, Card revealed) {
        return format(R.string.card_revealed_from_player,
                      name,
                      getCardName(responsible),
                      getCardName(revealed));
    }

    public static String combineCardNames(Object[] cards, int startIndex) {
        String cardNames = "";
        for (int i = startIndex; i < cards.length; i++) {
            cardNames += getCardName((Card) cards[i]);
            if (i != cards.length - 1) {
                cardNames += ", ";
            }
        }
        return cardNames;
    }
    
    public static String getSelectCardText(SelectCardOptions sco, String header) {
        String minCostString = (sco.minCost <= 0) ? "" : "" + sco.minCost;
        String maxCostString = (sco.maxCost == Integer.MAX_VALUE) ?
                "" : "" + sco.maxCost + sco.debtString() + sco.potionString();
        String selectString;

        if (sco.fromTable) {
            if (sco.fromPrizes)
                selectString = header;
            else if (sco.minCost == sco.maxCost) {
                if (sco.isAttack) {
                    selectString = Strings.format(R.string.select_from_table_attack, maxCostString, header);
                } else if (sco.isAction) {
                    selectString = Strings.format(R.string.select_from_table_exact_action, maxCostString, header);
                } else if (sco.except != null) {
                    selectString = Strings.format(R.string.select_from_table_exact_except, getCardName(sco.except), maxCostString, header);
                } else {
                    selectString = Strings.format(R.string.select_from_table_exact, maxCostString, header);
                }
            } else if (sco.minCost <= 0 && sco.maxCost < Integer.MAX_VALUE) {
                if (sco.isVictory) {
                    selectString = Strings.format(R.string.select_from_table_max_vp, maxCostString, header);
                } else if (sco.isNonVictory) {
                	if (sco.isAction) {
                		selectString = Strings.format(R.string.select_from_table_max_non_vp_action, maxCostString, header);
                	} else if (sco.lessThanMax) {
                		selectString = Strings.format(R.string.select_from_table_less_non_vp, maxCostString, header);
                	} else {
                		selectString = Strings.format(R.string.select_from_table_max_non_vp, maxCostString, header);
                	}
                } else if (sco.isTreasure) {
                    selectString = Strings.format(R.string.select_from_table_max_treasure, maxCostString, header);
                } else if (sco.isAction) {
                	if (sco.isNonDuration) {
                		if (sco.isNonCommand) {
                			selectString = Strings.format(R.string.select_from_table_max_action_non_duration_non_command, maxCostString, header);
                		} else {
                			selectString = Strings.format(R.string.select_from_table_max_action_non_duration, maxCostString, header);
                		}
                	} if (sco.isNonCommand) {
                		selectString = Strings.format(R.string.select_from_table_max_action_non_command, maxCostString, header);
                	} else {
                		selectString = Strings.format(R.string.select_from_table_max_action, maxCostString, header);
                	}
                } else if (sco.isSpirit && sco.lessThanMax) {
                	selectString = Strings.format(R.string.select_from_table_less_spirit, maxCostString, header);
                } else if (containsOnlyEvents(sco)) {
                    selectString = Strings.format(R.string.select_from_table_max_events, maxCostString, header);
                } else if (containsOnlyProjects(sco)) {
                    selectString = Strings.format(R.string.select_from_table_max_projects, maxCostString, header);
                } else if (containsOnlyCardsAndProjects(sco)) {
                    selectString = Strings.format(R.string.select_from_table_max_cards_projects, maxCostString, header);
                } else if (containsOnlyEventsAndProjects(sco)) {
                    selectString = Strings.format(R.string.select_from_table_max_events_projects, maxCostString, header);
                } else if (containsEventsCardsAndProjects(sco)) {
                    selectString = Strings.format(R.string.select_from_table_max_cards_events_projects, maxCostString, header);
                } else if (containsOnlyCards(sco)) {
                	if (sco.lessThanMax) {
                		if (sco.atLeastOneOfTypes != null)
                			selectString = Strings.format(R.string.select_from_table_less_of_types, getTypesString(sco.atLeastOneOfTypes), maxCostString, header);
                		else
                			selectString = Strings.format(R.string.select_from_table_less, maxCostString, header);
                	} else if (sco.except != null) {
                		selectString = Strings.format(R.string.select_from_table_max_except, getCardName(sco.except), maxCostString, header);
                	} else {
                		selectString = Strings.format(R.string.select_from_table_max, maxCostString, header);
                	}
                } else {
                    selectString = Strings.format(R.string.select_from_table_max_cards_events, maxCostString, header);
                }
            } else if (sco.minCost > 0 && sco.maxCost < Integer.MAX_VALUE) {
                selectString = Strings.format(R.string.select_from_table_between, minCostString, maxCostString, header);
            } else if (sco.minCost > 0) {
                selectString = Strings.format(R.string.select_from_table_min, minCostString + sco.debtString() + sco.potionString(), header);
            } else {
                if (sco.isAttack) {
                    selectString = Strings.format(R.string.select_from_table_attack, header);
                } else if (sco.isAction) {
                	selectString = Strings.format(R.string.select_from_table_action, header);
                } else if (sco.isTreasure) {
                	selectString = Strings.format(R.string.select_from_table_treasure, header);
                } else if (sco.isVictory) {
                	selectString = Strings.format(R.string.select_from_table_victory, header);
                } else if (sco.isNonVictory) {
                	selectString = Strings.format(R.string.select_from_table_non_victory, header);
                } else {
                    selectString = Strings.format(R.string.select_from_table, header);
                }
            }
            return selectString;
        } else if (sco.fromHand) {
            String str = "";
            if (sco.isAction) {
                if(sco.count == 1) {
                	if (sco.notInPlay)
                		str = Strings.format(R.string.select_one_action_from_hand_not_in_play, header);
                	else
                		str = Strings.format(R.string.select_one_action_from_hand, header);
                } else if(sco.exactCount)
                    str = Strings.format(R.string.select_exactly_x_actions_from_hand, "" + sco.count, header);
                else
                    str = Strings.format(R.string.select_up_to_x_actions_from_hand, "" + sco.count, header);
            } else if (sco.isNight) {
                if(sco.count == 1)
                    str = Strings.format(R.string.select_one_night_from_hand, header);
                else if(sco.exactCount)
                    str = Strings.format(R.string.select_exactly_x_nights_from_hand, "" + sco.count, header);
                else
                    str = Strings.format(R.string.select_up_to_x_nights_from_hand, "" + sco.count, header);
            } else if (sco.isTreasure) {
                if(sco.count == 1) {
                	if (sco.except != null)
                		str = Strings.format(R.string.select_one_treasure_from_hand_except, getCardName(sco.except), header);
            		else
            			str = Strings.format(R.string.select_one_treasure_from_hand, header);
                } else if(sco.exactCount)
                    str = Strings.format(R.string.select_exactly_x_treasures_from_hand, "" + sco.count, header);
                else
                    str = Strings.format(R.string.select_up_to_x_treasures_from_hand, "" + sco.count, header);
            } else if (sco.isVictory) {
                if(sco.count == 1)
                    str = Strings.format(R.string.select_one_victory_from_hand, header);
                else if(sco.exactCount)
                    str = Strings.format(R.string.select_exactly_x_victorys_from_hand, "" + sco.count, header);
                else
                    str = Strings.format(R.string.select_up_to_x_victorys_from_hand, "" + sco.count, header);
            } else if (sco.isNonTreasure) {
                if(sco.count == 1)
                    str = Strings.format(R.string.select_one_nontreasure_from_hand, header);
                else if(sco.exactCount)
                    str = Strings.format(R.string.select_exactly_x_nontreasures_from_hand, "" + sco.count, header);
                else
                    str = Strings.format(R.string.select_up_to_x_nontreasures_from_hand, "" + sco.count, header);
            } else if (sco.isNonVictory) {
                    str = Strings.format(R.string.select_one_non_vp_from_hand, header);
            } else {
                if(sco.count == 1) {
                	if (sco.atLeastOneOfTypes != null)
                		str = Strings.format(R.string.select_one_of_types_card_from_hand, getTypesString(sco.atLeastOneOfTypes), header);
                	else
                		str = Strings.format(R.string.select_one_card_from_hand, header);
                } else if(sco.exactCount)
                    str = Strings.format(R.string.select_exactly_x_cards_from_hand, "" + sco.count, header);
                else if (sco.minCount > 0) {
                	if (sco.different) {
                		str = Strings.format(R.string.select_from_x_to_y_different_cards_from_hand, "" + sco.minCount, "" + sco.count, header);
                	} else {
                		str = Strings.format(R.string.select_from_x_to_y_cards_from_hand, "" + sco.minCount, "" + sco.count, header);
                	}
                } else {
                    if (sco.same) {
                        str = Strings.format(R.string.select_same_cards_from_hand, header);
                    } else {
                        str = Strings.format(R.string.select_up_to_x_cards_from_hand, "" + sco.count, header);
                    }
                }
            }
            return str;
        } else if (sco.fromPlayed) {
            String str = "";
            if(sco.count == 1)
                str = Strings.format(R.string.select_one_card_from_played, header);
            else if(sco.exactCount)
                str = Strings.format(R.string.select_exactly_x_cards_from_played, "" + sco.count, header);
            else {
            	if (sco.different)
            		str = Strings.format(R.string.select_up_to_x_different_cards_from_played, "" + sco.count, header);
            	else {
            		if (sco.isTreasure)
            			str = Strings.format(R.string.select_up_to_x_treasure_cards_from_played, "" + sco.count, header);
            		else
            			str = Strings.format(R.string.select_up_to_x_cards_from_played, "" + sco.count, header);
            	}
            }
            return str;
        }
        throw new RuntimeException("SelectCardOptions isn't from table or from hand or from played...");
    }
    
    private static String getTypesString(Type[] types) {
    	if (types.length == 1) {
    		return getTypeName(types[0]);
    	}
    	if (types.length == 2) {
    		return Strings.format(R.string.list_two_types, getTypeName(types[0]), getTypeName(types[1]));
    	}
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < types.length; ++i) {
    		boolean first = i == 0;
    		boolean last = i == types.length - 1;
    		if (!first && !last) {
    			sb.append(getString(R.string.list_type_separator));
    		} else if (last) {
    			sb.append(getString(R.string.list_penultimate_type_separator));
    		}
    		sb.append(getTypeName(types[i]));
    	}
    	return sb.toString();
    }

    private static boolean containsOnlyCards(SelectCardOptions sco) {
    	for (int cardId : sco.allowedCards) {
    		if (GameTableViews.intToMyCard(cardId).isEvent || GameTableViews.intToMyCard(cardId).isProject)
    			return false;
    	}
    	return true;
	}

	private static boolean containsOnlyEvents(SelectCardOptions sco) {
    	for (int cardId : sco.allowedCards) {
    		if (!GameTableViews.intToMyCard(cardId).isEvent)
    			return false;
    	}
    	return true;
	}
	
	private static boolean containsOnlyProjects(SelectCardOptions sco) {
    	for (int cardId : sco.allowedCards) {
    		if (!GameTableViews.intToMyCard(cardId).isProject)
    			return false;
    	}
    	return true;
	}
	
	private static boolean containsOnlyCardsAndProjects(SelectCardOptions sco) {
		boolean containsCards = false;
		boolean containsProjects = false;
		boolean containsEvents = false;
    	for (int cardId : sco.allowedCards) {
    		if (GameTableViews.intToMyCard(cardId).isProject) {
    			containsProjects = true;
    		} else if (GameTableViews.intToMyCard(cardId).isEvent) {
    			containsEvents = true;
    		} else {
    			containsCards = true;
    		}
    		
    	}
    	return containsCards && containsProjects && !containsEvents;
	}
	
	private static boolean containsOnlyEventsAndProjects(SelectCardOptions sco) {
		boolean containsCards = false;
		boolean containsProjects = false;
		boolean containsEvents = false;
    	for (int cardId : sco.allowedCards) {
    		if (GameTableViews.intToMyCard(cardId).isProject) {
    			containsProjects = true;
    		} else if (GameTableViews.intToMyCard(cardId).isEvent) {
    			containsEvents = true;
    		} else {
    			containsCards = true;
    		}
    		
    	}
    	return containsEvents && containsProjects && !containsCards;
	}
	
	private static boolean containsEventsCardsAndProjects(SelectCardOptions sco) {
		boolean containsCards = false;
		boolean containsProjects = false;
		boolean containsEvents = false;
    	for (int cardId : sco.allowedCards) {
    		if (GameTableViews.intToMyCard(cardId).isProject) {
    			containsProjects = true;
    		} else if (GameTableViews.intToMyCard(cardId).isEvent) {
    			containsEvents = true;
    		} else {
    			containsCards = true;
    		}
    		
    	}
    	return containsEvents && containsProjects && containsCards;
	}
	
	public static String getActionString(SelectCardOptions sco) {
        return getActionString(sco.actionType, sco.cardResponsible);
    }

    public static String getActionString(ActionType action, Card cardResponsible) {
        return getActionString(action, cardResponsible, null);
    }

    public static String getActionString(ActionType action, Card cardResponsible, String opponentName) {
        // TODO(matt): ActionType seems mostly to be redundant with PickType in the
        // SelectCardOptions.  Just in terms of cleaning up the code, it would probably be nice to
        // remove one of them (probably ActionType).  The only tricky thing is that ActionType
        // distinguishes between DISCARDFORCARD and DISCARDFORCOIN, while PickType doesn't.  But
        // this isn't necessary for the multiplayer stuff, so I'll leave it for later.
        if( action == null )
        {
            return "WRONG TEXT" + Strings.format(R.string.card_to_discard, getCardName(cardResponsible));
        }
        switch (action) {
            case DISCARD: return Strings.format(R.string.card_to_discard, getCardName(cardResponsible));
            case DISCARDFORCARD: return Strings.format(R.string.card_to_discard_for_card, getCardName(cardResponsible));
            case DISCARDFORCOIN: return Strings.format(R.string.card_to_discard_for_coin, getCardName(cardResponsible));
            case REVEAL: return Strings.format(R.string.card_to_reveal, getCardName(cardResponsible));
            case GAIN: return Strings.format(R.string.card_to_gain, getCardName(cardResponsible));
            case TRASH: return Strings.format(R.string.card_to_trash, getCardName(cardResponsible));
            case EXILE: return Strings.format(R.string.card_to_exile, getCardName(cardResponsible));
            case NAMECARD: return Strings.format(R.string.card_to_name, getCardName(cardResponsible));
            case OPPONENTDISCARD: return Strings.format(R.string.opponent_discard, opponentName, getCardName(cardResponsible));
            case SETASIDE: return Strings.format(R.string.card_to_set_aside, getCardName(cardResponsible));
            case PLAY: return Strings.format(R.string.card_to_play, getCardName(cardResponsible));
            case KEEP: return Strings.format(R.string.cards_to_keep, getCardName(cardResponsible));
            case TOPDECK: return Strings.format(R.string.cards_to_topdeck, getCardName(cardResponsible));
        }
        return null;
    }

    private static void initActionStrings() {
        if (simpleActionStrings != null) return;
        simpleActionStrings = new HashSet<String>(Arrays.asList(
            getCardName(Cards.altar),
            getCardName(Cards.ambassador),
            getCardName(Cards.apprentice),
            getCardName(Cards.armory),
            getCardName(Cards.borderVillage),
            getCardName(Cards.butcher),
            getCardName(Cards.catacombs),
            getCardName(Cards.cellar),
            getCardName(Cards.chapel),
            getCardName(Cards.counterfeit),
            getCardName(Cards.dameAnna),
            getCardName(Cards.dameNatalie),
            getCardName(Cards.deathCart),
            getCardName(Cards.develop),
            getCardName(Cards.diplomat),
            getCardName(Cards.doctor),
            getCardName(Cards.embassy),
            getCardName(Cards.expand),
            getCardName(Cards.farmland),
            getCardName(Cards.feast),
            getCardName(Cards.forager),
            getCardName(Cards.forge),
            getCardName(Cards.graverobber),
            getCardName(Cards.governor),
            getCardName(Cards.haggler),
            getCardName(Cards.hermit),
            getCardName(Cards.hornOfPlenty),
            getCardName(Cards.horseTraders),
            getCardName(Cards.inn),
            getCardName(Cards.ironworks),
            getCardName(Cards.jackOfAllTrades),
            getCardName(Cards.journeyman),
            getCardName(Cards.junkDealer),
            getCardName(Cards.messenger),
            getCardName(Cards.mill),
            getCardName(Cards.oasis),
            getCardName(Cards.plaza),
            getCardName(Cards.poacher),
            getCardName(Cards.quest),
            getCardName(Cards.rats),
            getCardName(Cards.rebuild),
            getCardName(Cards.remake),
            getCardName(Cards.remodel),
            getCardName(Cards.replace),
            getCardName(Cards.salvager),
            /* secretChamber isn't simple, causes error in getActionString() */
            getCardName(Cards.spiceMerchant),
            getCardName(Cards.squire),
            getCardName(Cards.stables),
            getCardName(Cards.steward),
            getCardName(Cards.stonemason),
            getCardName(Cards.storeroom),
            getCardName(Cards.torturer),
            getCardName(Cards.tradeRoute),
            getCardName(Cards.trader),
            getCardName(Cards.tradingPost),
            getCardName(Cards.transmute),
            getCardName(Cards.upgrade),
            getCardName(Cards.warehouse),
            getCardName(Cards.workshop),
            getCardName(Cards.youngWitch),
            /*Adventures*/
            getCardName(Cards.amulet),
            getCardName(Cards.artificer),
            getCardName(Cards.bonfire),
            getCardName(Cards.dungeon),
            getCardName(Cards.fugitive),
            getCardName(Cards.hero),
            getCardName(Cards.ratcatcher),
            getCardName(Cards.raze),
            getCardName(Cards.storyteller),
            getCardName(Cards.soldier),
            getCardName(Cards.trade),
            getCardName(Cards.transmogrify),
            /*Adventures Events*/
            getCardName(Cards.alms),
            getCardName(Cards.ball),
            getCardName(Cards.inheritance),
            getCardName(Cards.pilgrimage),
            getCardName(Cards.seaway),
            /*Empires*/
            getCardName(Cards.charm),
            getCardName(Cards.engineer),
            getCardName(Cards.forum),
            getCardName(Cards.gladiator),
            getCardName(Cards.opulentCastle),
            getCardName(Cards.sacrifice),
            getCardName(Cards.smallCastle),
            getCardName(Cards.temple),
            /*Empires Events*/
            getCardName(Cards.advance),
            getCardName(Cards.banquet),
            getCardName(Cards.donate),
            getCardName(Cards.ritual),
            getCardName(Cards.saltTheEarth),
            /*Empires Landmarks*/
            getCardName(Cards.arena),
            /*Nocturne*/
            getCardName(Cards.bat),
            getCardName(Cards.cemetery),
            getCardName(Cards.cobbler),
            getCardName(Cards.conclave),
            getCardName(Cards.crypt),
            getCardName(Cards.devilsWorkshop),
            getCardName(Cards.exorcist),
            getCardName(Cards.fear),
            getCardName(Cards.goat),
            getCardName(Cards.hauntedMirror),
            getCardName(Cards.haunting),
            getCardName(Cards.imp),
            getCardName(Cards.locusts),
            getCardName(Cards.monastery),
            getCardName(Cards.pooka),
            getCardName(Cards.poverty),
            getCardName(Cards.secretCave),
            getCardName(Cards.theFlamesGift),
            getCardName(Cards.theWindsGift),
            getCardName(Cards.tragicHero),
            getCardName(Cards.shepherd),
            getCardName(Cards.vampire),
            getCardName(Cards.wish),
            getCardName(Cards.zombieApprentice),
            getCardName(Cards.zombieMason),
            /*Renaissance*/
            getCardName(Cards.cathedral),
            getCardName(Cards.cityGate),
            getCardName(Cards.cropRotation),
            getCardName(Cards.hideout),
            getCardName(Cards.improve),
            getCardName(Cards.inventor),
            getCardName(Cards.priest),
            getCardName(Cards.recruiter),
            getCardName(Cards.research),
            getCardName(Cards.sculptor),
            getCardName(Cards.sewers),
            getCardName(Cards.treasurer),
            /*Menagerie*/
            getCardName(Cards.animalFair),
            getCardName(Cards.banish),
            getCardName(Cards.bargain),
            getCardName(Cards.bountyHunter),
            getCardName(Cards.camelTrain),
            getCardName(Cards.delay),
            getCardName(Cards.demand),
            getCardName(Cards.displace),
            getCardName(Cards.enhance),
            getCardName(Cards.falconer),
            getCardName(Cards.goatherd),
            getCardName(Cards.groom),
            getCardName(Cards.hostelry),
            getCardName(Cards.invest),
            getCardName(Cards.pursue),
            getCardName(Cards.sanctuary),
            getCardName(Cards.scrap),
            getCardName(Cards.toil),
            getCardName(Cards.transport),
            getCardName(Cards.wayOfTheButterfly),
            getCardName(Cards.wayOfTheGoat),
            getCardName(Cards.wayOfTheRat),
            /*Promo*/
            getCardName(Cards.dismantle),
            getCardName(Cards.sauna),
            /*Promo Events*/
            getCardName(Cards.summon)
            
        ));
        actionStringMap = new HashMap<String, String>();
        actionStringMap.put(getCardName(Cards.bureaucrat), getString(R.string.bureaucrat_part));
        actionStringMap.put(getCardName(Cards.bandOfMisfits), getString(R.string.part_play));
        actionStringMap.put(getCardName(Cards.ferry), getString(R.string.part_move_token_minus_2_cost));
        actionStringMap.put(getCardName(Cards.courtyard),
                            Strings.format(R.string.courtyard_part_top_of_deck,
                                           getCardName(Cards.courtyard)));
        actionStringMap.put(getCardName(Cards.contraband), getString(R.string.contraband_part));
        actionStringMap.put(getCardName(Cards.embargo), getString(R.string.embargo_part));
        actionStringMap.put(getCardName(Cards.followers), getString(R.string.followers_part));
        actionStringMap.put(getCardName(Cards.ghostShip), getString(R.string.ghostship_part));
        actionStringMap.put(getCardName(Cards.goons), getString(R.string.goons_part));
        actionStringMap.put(getCardName(Cards.hauntedCastle), getString(R.string.hauntedcastle_part));
        actionStringMap.put(getCardName(Cards.haven), getString(R.string.haven_part));
        actionStringMap.put(getCardName(Cards.gear), getString(R.string.gear_part));
        actionStringMap.put(getCardName(Cards.island), getString(R.string.island_part));
        actionStringMap.put(getCardName(Cards.pathfinding), getString(R.string.part_move_token_plus_one_card));
        actionStringMap.put(getCardName(Cards.prince), getString(R.string.prince_part));
        actionStringMap.put(getCardName(Cards.kingsCourt), getString(R.string.kings_court_part));
        actionStringMap.put(getCardName(Cards.legionary), getString(R.string.legionary_part));
        actionStringMap.put(getCardName(Cards.lostArts), getString(R.string.part_move_token_plus_one_action));
        actionStringMap.put(getCardName(Cards.mandarin), getString(R.string.mandarin_part));
        actionStringMap.put(getCardName(Cards.margrave), getString(R.string.margrave_part));
        actionStringMap.put(getCardName(Cards.mastermind), getString(R.string.kings_court_part));
        actionStringMap.put(getCardName(Cards.militia), getString(R.string.militia_part));
        actionStringMap.put(getCardName(Cards.mint), getString(R.string.mint_part));
        actionStringMap.put(getCardName(Cards.overlord), getString(R.string.part_play));
        actionStringMap.put(getCardName(Cards.saboteur), getString(R.string.saboteur_part));
        actionStringMap.put(getCardName(Cards.save), getString(R.string.save_part));
        actionStringMap.put(getCardName(Cards.sirMichael), getString(R.string.sir_michael_part));
        actionStringMap.put(getCardName(Cards.tax), getString(R.string.tax_part));
        actionStringMap.put(getCardName(Cards.theSkysGift), getString(R.string.the_skys_gift_part));
        actionStringMap.put(getCardName(Cards.throneRoom), getString(R.string.throne_room_part));
        actionStringMap.put(getCardName(Cards.disciple), getString(R.string.throne_room_part));
        actionStringMap.put(getCardName(Cards.crown), getString(R.string.throne_room_part));
        actionStringMap.put(getCardName(Cards.tournament), getString(R.string.select_prize));
        actionStringMap.put(getCardName(Cards.training), getString(R.string.part_move_token_plus_one_coin));
        actionStringMap.put(getCardName(Cards.university), getString(R.string.university_part));
        actionStringMap.put(getCardName(Cards.urchin), getString(R.string.urchin_keep));
        actionStringMap.put(getCardName(Cards.captain), getString(R.string.part_play));
    }

    public static String getActionCardText(SelectCardOptions sco) {
        // We can't test for object equality with, e.g., Cards.militia here, because the object was
        // originally created in another process, possibly on a separate machine, serialized, sent
        // over a network, and then deserialized.  So we use the card name string throughout this
        // method.
        String cardName = getCardName(sco.cardResponsible);
        if (simpleActionStrings.contains(cardName)) {
            return getActionString(sco);
        }

        String actionString = actionStringMap.get(cardName);
        if (actionString != null) {
        	if (actionString.contains("%1$s")) {
        		return format(actionString, cardName);
        	}
            return actionString;
        }

        // Here we just have special cases, where there is more than one possible string for a
        // particular card, and the difference isn't captured in the action type, or we need to
        // include some dynamic information from the select card options in the string.
        if (cardName.equals(getCardName(Cards.mine))) {
            if (sco.pickType == PickType.UPGRADE) {
                return getString(R.string.mine_part);
            } else {
                return getActionString(sco);
            }
        } else if (cardName.equals(getCardName(Cards.artisan))) {
        	if (sco.actionType == ActionType.GAIN) {
                return getActionString(sco);
            } else {
                return getString(R.string.mandarin_part);
            }
        } else if (cardName.equals(getCardName(Cards.swindler))) {
            return Strings.format(R.string.swindler_part,
                                  "" + sco.maxCost + (sco.maxDebtCost == 0 ? "" : "d" + sco.maxDebtCost) + (sco.maxPotionCost == 0 ? "" : "p"));
        } else if (cardName.equals(getCardName(Cards.masquerade))) {
            if (sco.pickType == PickType.GIVE) {
                return getString(R.string.masquerade_part);
            } else {
                return getActionString(sco);
            }
        } else if (cardName.equals(getCardName(Cards.courtier))) {
            return getString(R.string.courtier_ask_reveal);
        } else if (cardName.equals(getCardName(Cards.secretPassage))) {
            return getString(R.string.secretPassage_cardToPutInDeck);
        } else if (cardName.equals(getCardName(Cards.lurker))) {
            return getString(R.string.lurker_cardToTrashFromSupply);
        } else if (cardName.equals(getCardName(Cards.bishop))) {
            if (sco.actionType == ActionType.TRASH) {
                return getActionString(sco);
            } else {
                return getString(R.string.bishop_part);
            }
        } else if (cardName.equals(getCardName(Cards.vault))) {
            if (sco.count == 2) {
                return getString(R.string.vault_part_discard_for_card);
            } else {
                return getString(R.string.vault_part_discard_for_gold);
            }
        } else if (cardName.equals(getCardName(Cards.hamlet))) {
            // WARNING: This is a total hack!  We need to differentiate the "discard for action" from
            // the "discard for buy", but we don't have any way in the SelectCardOptions to do
            // that.  So we set a fake action type in IndirectPlayer.java, and handle it as a
            // special case here.  This is fragile and could easily break if the rest of the code
            // changes and we aren't careful.  TODO(matt): come up with a better way to do this.
            // Probably the right way is to add an action type called DISCARDFORACTION and
            // DISCARDFORBUY, like there is DISCARDFORCARD and DISCARDFORCOIN.  That would require
            // adding some new strings, though, and getting rid of the strings here.
            if (sco.actionType == ActionType.DISCARD) {
                return getString(R.string.hamlet_part_discard_for_action);
            } else {
                return getString(R.string.hamlet_part_discard_for_buy);
            }
        } else if (cardName.equals(getCardName(Cards.secretChamber))) {
            if (sco.actionType == ActionType.DISCARD) {
                return getString(R.string.secretchamber_query_discard);
            } else {
                return getString(R.string.secretchamber_part);
            }
        } else if (cardName.equals(getCardName(Cards.count))) {
            if (sco.actionType == ActionType.DISCARD) {
                return getActionString(sco);
            } else {
                return Strings.format(R.string.count_part_top_of_deck, getCardName(Cards.count));
            }
        } else if (cardName.equals(getCardName(Cards.procession))) {
            if (sco.actionType == ActionType.GAIN) {
                return getActionString(sco);
            } else {
                return getCardName(Cards.procession);
            }
        } else if (cardName.equals(getCardName(Cards.mercenary))) {
            if (sco.actionType == ActionType.TRASH) {
                return getActionString(sco);
            } else {
                return getString(R.string.mercenary_part);
            }
        } else if (cardName.equals(getCardName(Cards.taxman))) {
            if (sco.actionType == ActionType.TRASH) {
                return getActionString(sco);
            } else {
                return getString(R.string.taxman_part);
            }
        } else if (cardName.equals(getCardName(Cards.plan))) {
            if (sco.actionType == ActionType.TRASH) {
                return getActionString(sco);
            } else {
                return getString(R.string.part_move_token_trashing);
            }
        } else if (cardName.equals(getCardName(Cards.teacher))) {
            if (sco.token == PlayerSupplyToken.PlusOneCard) {
            	return getString(R.string.teacher_move_token_plus_one_card);
            } else if (sco.token == PlayerSupplyToken.PlusOneAction) {
            	return getString(R.string.teacher_move_token_plus_one_action);
            } else if (sco.token == PlayerSupplyToken.PlusOneBuy) {
            	return getString(R.string.teacher_move_token_plus_one_buy);
            } else if (sco.token == PlayerSupplyToken.PlusOneCoin) {
            	return getString(R.string.teacher_move_token_plus_one_coin);
            } 
        } else if (cardName.equals(getCardName(Cards.catapult))) {
            if (sco.actionType == ActionType.TRASH) {
                return getActionString(sco);
            } else {
                return getString(R.string.catapult_part);
            }
        } else if (cardName.equals(getCardName(Cards.opulentCastle))) {
            return getString(R.string.opulent_castle_ask_discard);
        } else if (cardName.equals(getCardName(Cards.lostInTheWoods))) {
            return getString(R.string.lostInTheWoods_part_discard_for_boon);
        } else if (cardName.equals(getCardName(Cards.theEarthsGift))) {
        	if (sco.actionType == ActionType.DISCARD) {
        		return format(R.string.the_earths_gift_part, cardName);
            } else {
            	return getActionString(sco);
            }
        } else if (cardName.equals(getCardName(Cards.church))) {
            if (sco.pickType == PickType.TRASH) {
            	return getActionString(sco);
            } else {
            	return getString(R.string.church_part);
            }
        }
        
        throw new RuntimeException("Found a card in getActionCardText that I don't know how to handle yet: " + cardName);
    }

    /**
     * Not totally sure what the real use of this is yet, but it was a private method in
     * RemotePlayer that didn't need to be there.
     */
    public static String getCardText(final Map<Object, Integer> counts,
                                     final Map<Card, Integer> totals,
                                     final Card card) {
        final StringBuilder sb = new StringBuilder()
                .append('\t')
                //.append(card.getName())
                .append(getCardName(card))
                .append(" x")
                .append(counts.get(card))
                .append(": ")
                .append(totals.get(card))
                .append(" ")
                .append(Strings.getString(R.string.game_over_vps))
                .append('\n');

        return sb.toString();
    }

    /**
     * Get the end game text that shows victory point totals for a particular player.
     */
    public static String getVPOutput(String playerName,
                                     Map<Object, Integer> counts,
                                     Map<Card, Integer> totals,
                                     Map<Card, Integer> victoryTokensTotals) {
        int totalVPs = 0;
        for (Integer total : totals.values()) {
            totalVPs += total;
        }
        final StringBuilder sb
                = new StringBuilder()
                .append(playerName)
                .append(": ")
                .append(totalVPs)
                .append(" ")
                .append(Strings.getString(R.string.game_over_vps))
                .append('\n');

        sb.append(Strings.getCardText(counts, totals, Cards.estate));
        sb.append(Strings.getCardText(counts, totals, Cards.duchy));
        sb.append(Strings.getCardText(counts, totals, Cards.province));

        if(counts.containsKey(Cards.colony)) {
            sb.append(Strings.getCardText(counts, totals, Cards.colony));
        }

        sb.append(Strings.getCardText(counts, totals, Cards.curse));

        // display victory cards from sets
        boolean hasCastle = false;
        for(Card card : totals.keySet()) {
            if(Cards.isKingdomCard(card) || card.is(Type.Heirloom)) {
                if (!card.is(Type.Castle)) {
                    sb.append("  " + Strings.getCardText(counts, totals, card));
                } else {
                    hasCastle = true;
                }
            }
        }

        //Add the castles separately so they are ordered.
        if (hasCastle) {
            StringBuilder castleDetails = new StringBuilder();
            int castleTotals = 0;
            int castleVpCount = 0;
            for (Card card : Cards.castleCards) {
                if (totals.containsKey(card)) {
                    if (counts.get(card) > 0) {
                        castleDetails.append("\t" + Strings.getCardText(counts, totals, card));
                        castleVpCount += totals.get(card);
                        castleTotals += counts.get(card);
                    }
                }
            }

            //Add Castle Total count and VP summary
            Map<Object, Integer> castlecnt = new HashMap<Object, Integer>();
            Map<Card, Integer> castletot = new HashMap<Card, Integer>();
            castlecnt.put(Cards.virtualCastle, castleTotals);
            castletot.put(Cards.virtualCastle, castleVpCount);
            sb.append(Strings.getCardText(castlecnt, castletot, Cards.virtualCastle));

            if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean("game_over_castle_details", true)) {
                sb.append(castleDetails);
            }
        }

        
        for(Card card : totals.keySet()) {
            if(card.is(Type.Landmark)) {
            	sb.append('\t')
                .append(getCardName(card))
                .append(": ")
                .append(totals.get(card))
                .append(" ")
                .append(Strings.getString(R.string.game_over_vps))
                .append('\n');
            }
        }
        
        for(Card card : totals.keySet()) {
            if(card.is(Type.State)) {
            	sb.append('\t')
                .append(getCardName(card))
                .append(": ")
                .append(totals.get(card))
                .append(" ")
                .append(Strings.getString(R.string.game_over_vps))
                .append('\n');
            }
        }

        sb.append("\t"+Strings.getString(R.string.victory_tokens)+": ")
                .append(totals.get(Cards.victoryTokens))
                .append('\n');

        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean("game_over_vp_token_details", true)) {
            boolean landmarks = false;
            for (int i = 0; i < 2; i++) {
                for (Map.Entry<Card, Integer> entry : victoryTokensTotals.entrySet()) {
                    if (entry.getKey().is(Type.Landmark) == landmarks) {
                        sb.append("\t\t")
                                .append(getCardName(entry.getKey()))
                                .append(": ")
                                .append(entry.getValue())
                                .append("\n");
                    }
                }
                landmarks = !landmarks;
            }
        }

        return sb.toString();
    }

    public static String getGameTimeString(long duration) {
        String time = getString(R.string.game_over_status);
        time += " ";
        if (duration > 1000 * 60 * 60)
            time += (duration / (1000 * 60 * 60)) + "h ";
        duration = duration % (1000 * 60 * 60);
        if (duration > 1000 * 60)
            time += (duration / (1000 * 60)) + "m ";
        duration = duration % (1000 * 60);
        time += (duration / (1000)) + "s.\n";
        return time;
    }

	public static String getCardSetDescription(CardSet cardSet) {
		ArrayList<String> events = new ArrayList<String>();
		ArrayList<String> projects = new ArrayList<String>();
		ArrayList<String> landmarks = new ArrayList<String>();
		ArrayList<String> ways = new ArrayList<String>();
		ArrayList<String> kingdomCards = new ArrayList<String>();
		boolean hasDarkAges = false;
		boolean hasProsperity = false;
		for (Card c : cardSet.getCards()) {
			if (c.is(Type.Event)) {
				events.add(getCardName(c));
			} else if (c.is(Type.Project)) {
				projects.add(getCardName(c));
			} else if (c.is(Type.Landmark)) {
				if (c.equals(Cards.obelisk) && cardSet.getObeliskCard() != null) {
					landmarks.add(format(R.string.card_set_card_options, getCardName(c), getCardName(cardSet.getObeliskCard())));
				} else {
					landmarks.add(getCardName(c));
				}
			} else if (c.is(Type.Way)) {
				if (c.equals(Cards.wayOfTheMouse) && cardSet.getWayOfTheMouseCard() != null) {
					ways.add(format(R.string.card_set_card_options, getCardName(c), getCardName(cardSet.getWayOfTheMouseCard())));
				} else {
					ways.add(getCardName(c));
				}
			} else {
				if (c.equals(Cards.druid) && cardSet.getDruidBoons() != null) {
					//kingdomCards.add(getCardName(c));
					ArrayList<String> druidBoons = new ArrayList<String>();
					for (Card b : cardSet.getDruidBoons()) {
						druidBoons.add(getCardName(b));
					}
					Collections.sort(druidBoons);
					kingdomCards.add(format(R.string.card_set_card_options, getCardName(c), joinList(druidBoons, ", ")));
				} else {
					kingdomCards.add(getCardName(c));
				}
				if (c.getExpansion() == Expansion.DarkAges) {
					hasDarkAges = true;
				}
				if (c.getExpansion() == Expansion.Prosperity) {
					hasProsperity = true;
				}
				
			}
		}
		Collections.sort(events);
		Collections.sort(projects);
		Collections.sort(landmarks);
		Collections.sort(ways);
		Collections.sort(kingdomCards);
		
		ArrayList<String> cardSetParts = new ArrayList<String>();
		cardSetParts.add(joinList(kingdomCards, ", "));
		if (cardSet.getBaneCard() != null) {
			cardSetParts.add(format(R.string.card_set_bane, getCardName(cardSet.getBaneCard())));
		}
		if (events.size() > 0) {
			cardSetParts.add(format(R.string.card_set_events, joinList(events, ", ")));
		}
		if (projects.size() > 0) {
			cardSetParts.add(format(R.string.card_set_projects, joinList(projects, ", ")));
		}
		if (landmarks.size() > 0) {
			cardSetParts.add(format(R.string.card_set_landmarks, joinList(landmarks, ", ")));
		}
		if (ways.size() > 0) {
			cardSetParts.add(format(R.string.card_set_ways, joinList(ways, ", ")));
		}
		if (cardSet.getUsePlatColony() == UseOptionalCards.Use) {
			cardSetParts.add(getString(R.string.card_set_plat_colony));
		} else if (hasProsperity && cardSet.getUsePlatColony() == UseOptionalCards.DontUse) {
			cardSetParts.add(getString(R.string.card_set_no_plat_colony));
		}
		if (cardSet.getUseShelters() == UseOptionalCards.Use) {
			cardSetParts.add(getString(R.string.card_set_shelters));
		} else if (hasDarkAges && cardSet.getUseShelters() == UseOptionalCards.DontUse) {
			cardSetParts.add(getString(R.string.card_set_no_shelters));
		}
		return joinList(cardSetParts, ". ");
	}
	
	private static String joinList(ArrayList<String> strings, String separator) {
		String sep = "";
		StringBuilder result = new StringBuilder();
		for (String str : strings) {
			result.append(sep);
			result.append(str);
			sep = separator;
		}
		return result.toString();
	}
}

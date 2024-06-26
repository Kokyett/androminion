package com.mehtank.androminion.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.preference.PreferenceManager;

import com.mehtank.androminion.R;
import com.mehtank.androminion.util.HapticFeedback;
import com.mehtank.androminion.util.HapticFeedback.AlertType;
import com.vdom.comms.GameStatus.JourneyTokenState;

public class DeckView extends RelativeLayout implements OnLongClickListener {
	private static final String TAG = "DeckView";
	
	private TextView name;
	private TextView pirates;
	private TextView victoryTokens;
	private TextView debtTokens;
	private TextView guildsCoinTokens;
	private TextView villagerTokens;
	private TextView journeyToken;
	private TextView minusOneCoinToken;
	private TextView minusOneCardToken;
	private TextView deluded;
	private TextView envious;
	private TextView lostInTheWoods;
	private TextView miserable;
	private TextView twiceMiserable;
	private TextView flag;
	private TextView horn;
	private TextView key;
	private TextView lantern;
	private TextView treasureChest;
	private LinearLayout counts;
	private TextView countsPrefix;
	private TextView countsDeck;
	private TextView countsMiddle;
	private TextView countsStashesInHand;
	private TextView countsSuffix;
	
	private String nameStr;
	private int turns;
	private int deckSize;
	private boolean stashOnDeck;
	private int handSize;
	private int stashesInHand;
	private int numCards;
	private int vp;
	private int numPirateTokens;
	private int numVictoryTokens;
	private int numDebtTokens;
	private int numCoinTokens;
	private int numVillagerTokens;
	private boolean hasMinusOneCardToken;
	private boolean hasMinusOneCoinToken;
	private JourneyTokenState journeyTokenState;
	private boolean hasDeluded;
	private boolean hasEnvious;
	private boolean hasLostInTheWoods;
	private boolean hasMiserable;
	private boolean hasTwiceMiserable;
	private boolean hasFlag;
	private boolean hasHorn;
	private boolean hasKey;
	private boolean hasLantern;
	private boolean hasTreasureChest;
	private boolean isCurrentTurn;
	
	private int textColor;
	private int stashColor;

	private boolean showCardCounts = true;
	private boolean showVp = false;

	

	public DeckView(Context context) {
		this(context, null);
	}

	public DeckView(Context context, AttributeSet attrs) {
		super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_deck, this, true);
		name = (TextView) findViewById(R.id.name);
		pirates = (TextView) findViewById(R.id.pirates);
		victoryTokens = (TextView) findViewById(R.id.victoryTokens);
		debtTokens = (TextView) findViewById(R.id.debtTokens);
		guildsCoinTokens = (TextView) findViewById(R.id.guildsCoinTokens);
		villagerTokens = (TextView) findViewById(R.id.villagerTokens);
		journeyToken = (TextView) findViewById(R.id.journeyToken);
		minusOneCoinToken = (TextView) findViewById(R.id.minusOneCoinToken);
		minusOneCardToken = (TextView) findViewById(R.id.minusOneCardToken);
		deluded = (TextView) findViewById(R.id.deluded);
		envious = (TextView) findViewById(R.id.envious);
		lostInTheWoods = (TextView) findViewById(R.id.lostInTheWoods);
		miserable = (TextView) findViewById(R.id.miserable);
		twiceMiserable = (TextView) findViewById(R.id.twiceMiserable);
		flag = (TextView) findViewById(R.id.flag);
		horn = (TextView) findViewById(R.id.horn);
		key = (TextView) findViewById(R.id.key);
		lantern = (TextView) findViewById(R.id.lantern);
		treasureChest = (TextView) findViewById(R.id.treasureChest);
		counts = (LinearLayout) findViewById(R.id.counts);
		countsPrefix = (TextView) findViewById(R.id.countsPrefix);
		countsDeck = (TextView) findViewById(R.id.countsDeck);
		countsMiddle = (TextView) findViewById(R.id.countsMiddle);
		countsStashesInHand = (TextView) findViewById(R.id.countsStashesInHand);
		countsSuffix = (TextView) findViewById(R.id.countsSuffix);

        if(!PreferenceManager.getDefaultSharedPreferences(context).getBoolean("card_counts", true)) {
            showCardCounts = false;
            counts.setVisibility(INVISIBLE);
        }
        
        if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean("vp_counter", true)) {
            showVp = true;
        }
        
        TypedValue typedValue = new TypedValue();
		context.getTheme().resolveAttribute(R.attr.stashTextColor, typedValue, true);
		stashColor = typedValue.data;
		
		textColor = new TextView(context).getTextColors().getDefaultColor();
		
		setOnLongClickListener(this);
	}

	public void set(String nameStr, int turns, int deckSize, boolean stashOnDeck, int handSize, int stashesInHand, int numCards, int vp, 
			int pt, int vt, int dt, int gct, int villagers,
			boolean minusOneCoinTokenOn, boolean minusOneCardTokenOn, JourneyTokenState journeyTokenState, 
			boolean hasDeluded, boolean hasEnvious, boolean hasLostInTheWoods, boolean hasMiserable, boolean hasTwiceMiserable,
			boolean hasFlag, boolean hasHorn, boolean hasKey, boolean hasLantern, boolean hasTreasureChest,
			boolean highlight, boolean showColor, int color) {
		this.nameStr = nameStr;
		this.turns = turns;
		this.deckSize = deckSize;
		this.stashOnDeck = stashOnDeck;
		this.handSize = handSize;
		this.stashesInHand = stashesInHand;
		this.numCards = numCards;
		this.vp = vp;
		this.hasMinusOneCardToken = minusOneCardTokenOn;
		this.hasMinusOneCoinToken = minusOneCoinTokenOn;
		this.journeyTokenState = journeyTokenState;
		this.hasDeluded = hasDeluded;
		this.hasEnvious = hasEnvious;
		this.hasLostInTheWoods = hasLostInTheWoods;
		this.hasMiserable = hasMiserable;
		this.hasTwiceMiserable = hasTwiceMiserable;
		this.hasFlag = hasFlag;
		this.hasHorn = hasHorn;
		this.hasKey = hasKey;
		this.hasLantern = hasLantern;
		this.hasTreasureChest = hasTreasureChest;
		this.numPirateTokens = pt;
		this.numVictoryTokens = vt;
		this.numDebtTokens = dt;
		this.numCoinTokens = gct;
		this.numVillagerTokens = villagers;
		this.isCurrentTurn = highlight;
		String txt = nameStr + getContext().getString(R.string.turn_header) + turns;
		name.setText(txt);
		if (highlight) {
//			name.setTextColor(Color.BLACK);
//			name.setBackgroundColor(Color.GRAY);
			name.setTypeface(Typeface.DEFAULT_BOLD);
		} else {
//			name.setTextColor(Color.WHITE);
//			name.setBackgroundColor(Color.TRANSPARENT);
			name.setTypeface(Typeface.DEFAULT);
		}
		if (showColor) {
			name.setBackgroundColor(color);
		} else {
			name.setBackgroundColor(Color.TRANSPARENT);
		}

		
		if (pt != 0) {
			pirates.setText(" " + pt + " ");
			pirates.setVisibility(VISIBLE);
		} else {
			pirates.setText("");
			pirates.setVisibility(GONE);
		}
        
        if (vt != 0) {
        	victoryTokens.setText(" " + vt + " ");
            victoryTokens.setVisibility(VISIBLE);
        } else {
        	victoryTokens.setText("");
            victoryTokens.setVisibility(GONE);
        }
        
        if (dt != 0) {
        	debtTokens.setText(" " + dt + " ");
        	debtTokens.setVisibility(VISIBLE);
        } else {
        	debtTokens.setText("");
        	debtTokens.setVisibility(GONE);
        }
        
        if (gct != 0) {
        	guildsCoinTokens.setText(" " + gct + " ");
            guildsCoinTokens.setVisibility(VISIBLE);
        } else {
        	guildsCoinTokens.setText("");
            guildsCoinTokens.setVisibility(GONE);
        }
        
        if (villagers != 0) {
        	villagerTokens.setText(" " + villagers + " ");
        	villagerTokens.setVisibility(VISIBLE);
        } else {
        	villagerTokens.setText("");
        	villagerTokens.setVisibility(GONE);
        }

        journeyToken.setTextColor(journeyTokenState == JourneyTokenState.FACE_UP ? Color.BLACK : Color.TRANSPARENT);
        if (journeyTokenState == null)
            journeyToken.setVisibility(GONE);
        else
            journeyToken.setVisibility(VISIBLE);
        
        
        if (minusOneCardTokenOn) {
        	minusOneCardToken.setText(" -1 ");
        	minusOneCardToken.setVisibility(VISIBLE);
        } else {
        	minusOneCardToken.setText("");
        	minusOneCardToken.setVisibility(GONE);
        }
        
        if (minusOneCoinTokenOn) {
			minusOneCoinToken.setText(" -1 ");
        	minusOneCoinToken.setVisibility(VISIBLE);
		} else {
			minusOneCoinToken.setText("");
        	minusOneCoinToken.setVisibility(GONE);
		}
        
        if (hasDeluded) {
        	deluded.setVisibility(VISIBLE);
        } else {
        	deluded.setVisibility(GONE);
        }
        
        if (hasEnvious) {
        	envious.setVisibility(VISIBLE);
        } else {
        	envious.setVisibility(GONE);
        }
        
        if (hasLostInTheWoods) {
        	lostInTheWoods.setVisibility(VISIBLE);
        } else {
        	lostInTheWoods.setVisibility(GONE);
        }
        
        if (hasMiserable) {
			miserable.setText(" -2 ");
        	miserable.setVisibility(VISIBLE);
		} else {
			miserable.setText("");
        	miserable.setVisibility(GONE);
		}
        
        if (hasTwiceMiserable) {
			twiceMiserable.setText(" -4 ");
			twiceMiserable.setVisibility(VISIBLE);
		} else {
			twiceMiserable.setText("");
			twiceMiserable.setVisibility(GONE);
		}
        
        if (hasFlag) {
        	flag.setVisibility(VISIBLE);
        } else {
        	flag.setVisibility(GONE);
        }
        
        if (hasHorn) {
        	horn.setVisibility(VISIBLE);
        } else {
        	horn.setVisibility(GONE);
        }
        
        if (hasKey) {
        	key.setVisibility(VISIBLE);
        } else {
        	key.setVisibility(GONE);
        }
        
        if (hasLantern) {
        	lantern.setVisibility(VISIBLE);
        } else {
        	lantern.setVisibility(GONE);
        }
        
        if (hasTreasureChest) {
        	treasureChest.setVisibility(VISIBLE);
        } else {
        	treasureChest.setVisibility(GONE);
        }
        
        if(showCardCounts) {
        	countsPrefix.setText("{ ");
        	countsDeck.setText("\u2261 ");
        	countsDeck.setTextColor(stashOnDeck ? stashColor : textColor);
        	countsMiddle.setText(deckSize + "    \u261e " + handSize);
        	countsStashesInHand.setText(stashesInHand == 0 ? "" : " (" + stashesInHand + ")");
        	countsSuffix.setText("    \u03a3 " + numCards +
        		(showVp ? "    \u26c9 " + vp :"") + " }");
        }
	}
	
	@Override
	public boolean onLongClick(View view) {
		HapticFeedback.vibrate(getContext(),AlertType.LONGCLICK);
		
		TextView textView = new TextView(view.getContext());
		textView.setPadding(15, 0, 15, 5);
		textView.setText(getDescription());
		new AlertDialog.Builder(view.getContext())
		.setTitle(nameStr)
		.setView(textView)
		.setPositiveButton(android.R.string.ok, null)
		.show();

	return true;
	}
	
	private String getDescription() {
		Context c = getContext();
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(c.getString(R.string.status_turn_number), turns, (isCurrentTurn ? c.getString(R.string.status_current_turn) : "")));
		sb.append("\n\n");
		if(showCardCounts) {
			sb.append(String.format(c.getString(R.string.status_deck_size), deckSize, (this.stashOnDeck ? c.getString(R.string.status_stash_on_deck) : "")) + "\n");
			sb.append(String.format(c.getString(R.string.status_hand_size), handSize) + "\n");
			if (stashesInHand > 0) {
				sb.append(String.format(c.getString(R.string.status_hand_stashes), stashesInHand) + "\n");
			}
			sb.append(String.format(c.getString(R.string.status_total_cards), numCards) + "\n");
			if (showVp) {
				sb.append(String.format(c.getString(R.string.status_vp), vp) + "\n");
			}
			sb.append("\n");
		}
		if (hasMinusOneCoinToken)
			sb.append(c.getString(R.string.status_has_minus_coin_token) + "\n");
		if (hasMinusOneCardToken)
			sb.append(c.getString(R.string.status_has_minus_card_token) + "\n");
		if (journeyTokenState != null) {
			sb.append(c.getString(journeyTokenState == JourneyTokenState.FACE_UP ? R.string.status_journey_token_up : R.string.status_journey_token_down) + "\n");
		}
		if (hasDeluded)
			sb.append(c.getString(R.string.status_has_deluded) + "\n");
		if (hasEnvious)
			sb.append(c.getString(R.string.status_has_envious) + "\n");
		if (hasLostInTheWoods)
			sb.append(c.getString(R.string.status_has_lostInTheWoods) + "\n");
		if (hasMiserable)
			sb.append(c.getString(R.string.status_has_miserable) + "\n");
		if (hasTwiceMiserable)
			sb.append(c.getString(R.string.status_has_twiceMiserable) + "\n");
		if (hasFlag)
			sb.append(c.getString(R.string.status_has_flag) + "\n");
		if (hasHorn)
			sb.append(c.getString(R.string.status_has_horn) + "\n");
		if (hasKey)
			sb.append(c.getString(R.string.status_has_key) + "\n");
		if (hasLantern)
			sb.append(c.getString(R.string.status_has_lantern) + "\n");
		if (hasTreasureChest)
			sb.append(c.getString(R.string.status_has_treasureChest) + "\n");
		
		if (numCoinTokens > 0)
			sb.append(String.format(c.getString(R.string.status_coin_tokens), numCoinTokens) + "\n");
		if (numVillagerTokens > 0)
			sb.append(String.format(c.getString(R.string.status_villager_tokens), numVillagerTokens) + "\n");
		if (numPirateTokens > 0)
			sb.append(String.format(c.getString(R.string.status_pirate_tokens), numPirateTokens) + "\n");
		if (numDebtTokens > 0)
			sb.append(String.format(c.getString(R.string.status_debt_tokens), numDebtTokens) + "\n");
		if (numVictoryTokens > 0)
			sb.append(String.format(c.getString(R.string.status_victory_tokens), numVictoryTokens) + "\n");
		
		return sb.toString();
	}
}

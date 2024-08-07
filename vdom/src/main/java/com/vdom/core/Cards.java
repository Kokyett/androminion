package com.vdom.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.vdom.api.Card;

public class Cards {
    public static ArrayList<Card> actionCardsBaseGame = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsBaseGame2E = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsBaseGameAll = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsIntrigue = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsIntrigue2E = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsIntrigueAll = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsSeaside = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsAlchemy = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsProsperity = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsCornucopia = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsHinterlands = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsDarkAges = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsGuilds = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsAdventures = new ArrayList<Card>();
    public static ArrayList<Card> eventCardsAdventures = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsEmpires = new ArrayList<Card>();
    public static ArrayList<Card> eventCardsEmpires = new ArrayList<Card>();
    public static ArrayList<Card> landmarkCardsEmpires = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsNocturne = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsRenaissance = new ArrayList<Card>();
    public static ArrayList<Card> artifactCardsRenaissance = new ArrayList<Card>();
    public static ArrayList<Card> projectCardsRenaissance = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsMenagerie = new ArrayList<Card>();
    public static ArrayList<Card> eventCardsMenagerie = new ArrayList<Card>();
    public static ArrayList<Card> wayCardsMenagerie = new ArrayList<Card>();
    public static ArrayList<Card> actionCardsPromo = new ArrayList<Card>();
    public static ArrayList<Card> eventCardsPromo = new ArrayList<Card>();
    public static ArrayList<Card> actionCards = new ArrayList<Card>();
    public static ArrayList<Card> prizeCards = new ArrayList<Card>();
    public static ArrayList<Card> nonSupplyCards = new ArrayList<Card>();
    public static ArrayList<Card> variablePileCards = new ArrayList<Card>();
    public static ArrayList<Card> knightsCards = new ArrayList<Card>();
    public static ArrayList<Card> ruinsCards = new ArrayList<Card>();
    public static ArrayList<Card> castleCards = new ArrayList<Card>();
	public static ArrayList<Card> eventsCards = new ArrayList<Card>();
    public static ArrayList<Card> landmarkCards = new ArrayList<Card>();
    public static ArrayList<Card> boonCards = new ArrayList<Card>();
    public static ArrayList<Card> hexCards = new ArrayList<Card>();
    public static ArrayList<Card> stateCards = new ArrayList<Card>();
    public static ArrayList<Card> projectCards = new ArrayList<Card>();
    public static ArrayList<Card> wayCards = new ArrayList<Card>();
    public static ArrayList<Card> blackMarketCards = new ArrayList<Card>();
    public static HashSet<Card> nonKingdomCards = new HashSet<Card>();

    public static HashMap<String, Card> cardNameToCard = new HashMap<String, Card>();
    public static HashMap<Card, Card> variablePileCardToRandomizer = new HashMap<Card, Card>();

    static final String KNIGHTS_TEXT = "Each other player reveals the top 2 cards of his deck, " +
            "trashes one of them costing from 3 to 6 coins, and discards the rest. If a Knight " +
            "is trashed by this, trash this card.";
    static final String TRAVELLERS_TEXT = "TRAVELLERS_TEXT.";

    public enum Kind {
        // Non-Kingdom Cards
        Platinum, Gold, Silver, Copper, Potion, Colony, Province, Duchy, Estate, Curse,
        // Base Set
        Gardens, Moat, Adventurer, Bureaucrat, Cellar, Chancellor, Chapel, CouncilRoom, Feast,
        Festival, Laboratory, Library, Market, Militia, Mine, Moneylender, Remodel, Smithy, Spy,
        Thief, ThroneRoom, Village, Witch, Woodcutter, Workshop,
        // Base Set Second Edition
        Artisan, Bandit, Harbinger, Merchant, Poacher, Sentry, Vassal,
		
        // Intrigue Expansion
        Duke, SecretChamber, Nobles, Coppersmith, Courtyard, Torturer, Harem, Baron, Bridge,
        Conspirator, Ironworks, Masquerade, MiningVillage, Minion, Pawn, Saboteur, ShantyTown,
        Scout, Steward, Swindler, TradingPost, WishingWell, Upgrade, Tribute, GreatHall,
        // Intrigue Second Edition
        Courtier, Diplomat, Lurker, Mill, Patrol, Replace, SecretPassage,

        // Seaside Expansion
        Haven, SeaHag, Tactician, Caravan, Lighthouse, FishingVillage, Wharf, MerchantShip,
        Outpost, GhostShip, Salvager, PirateShip, NativeVillage, Island, Cutpurse, Bazaar,
        Smugglers, Explorer, PearlDiver, TreasureMap, Navigator, Treasury, Lookout, Ambassador,
        Warehouse, Embargo,
        // Alchemy Expansion
        Alchemist, Apothecary, Apprentice, Familiar, Golem, Herbalist, PhilosophersStone,
        Possession, ScryingPool, Transmute, University, Vineyard,
        // Prosperity Expansion
        Bank, Bishop, City, Contraband, CountingHouse, Expand, Forge, Goons, GrandMarket, Hoard,
        KingsCourt, Loan, Mint, Monument, Mountebank, Peddler, Quarry, Rabble, RoyalSeal, Talisman,
        TradeRoute, Vault, Venture, WatchTower, WorkersVillage,
        // Cornucopia Expansion
        HornofPlenty, Fairgrounds, FarmingVillage, FortuneTeller, Hamlet, Harvest, HorseTraders,
        HuntingParty, Jester, Menagerie, Remake, Tournament, YoungWitch, BagofGold, Diadem,
        Followers, Princess, TrustySteed,
        // Hinterlands Expansion
        BorderVillage, Cache, Cartographer, Crossroads, Develop, Duchess, Embassy, Farmland,
        FoolsGold, Haggler, Highway, IllGottenGains, Inn, JackofAllTrades, Mandarin, Margrave,
        NobleBrigand, NomadCamp, Oasis, Oracle, Scheme, SilkRoad, SpiceMerchant, Stables, Trader,
        Tunnel,
        // Dark Ages Expansion
        Altar, Armory, BandOfMisfits, BanditCamp, Beggar, Catacombs, Count, Counterfeit, DeathCart,
        Feodum, Forager, Fortress, Graverobber, HuntingGrounds, Ironmonger, JunkDealer,
        MarketSquare, Mystic, Pillage, PoorHouse, Procession, Rats, Rebuild, Rogue, Sage,
        Scavenger, Spoils, Squire, Storeroom, WanderingMinstrel, Necropolis, Hovel,
        OvergrownEstate, AbandonedMine, RuinedLibrary, RuinedMarket, RuinedVillage, Survivors,
        Cultist, Urchin, Mercenary, Marauder, Hermit, Madman, Vagrant, DameAnna, DameJosephine,
        DameMolly, DameNatalie, DameSylvia, SirBailey, SirDestry, SirMartin, SirMichael, SirVander,
        VirtualRuins, VirtualKnight,
        // Guilds Expansion
        Advisor, Baker, Butcher, CandlestickMaker, Doctor, Herald, Journeyman, Masterpiece, MerchantGuild, Plaza, StoneMason, Soothsayer, Taxman,

        // Adventures Expansion
        CoinOfTheRealm, Page, Peasant, Ratcatcher, Raze, Amulet, CaravanGuard, Dungeon, Gear, Guide,
        Duplicate, Magpie, Messenger, Miser, Port, Ranger, Transmogrify, Artificer, BridgeTroll, DistantLands,
        Giant, HauntedWoods, LostCity, Relic, RoyalCarriage, Storyteller, SwampHag, TreasureTrove, WineMerchant, Hireling, 
        
        Soldier, TreasureHunter, Fugitive, Warrior, Disciple, Hero, Champion, Teacher, 
        
        Alms, Borrow, Quest, Save, ScoutingParty, TravellingFair, Bonfire, Expedition, Ferry, Plan, Mission, Pilgrimage,
        Ball, Raid, Seaway, Trade, LostArts, Training, Inheritance, Pathfinding,
        
        // Empires Expansion
        Archive, BustlingVillage, Capital, Catapult, ChariotRace, Charm, CityQuarter, Crown, Emporium, Encampment, Enchantress, 
        Engineer, FarmersMarket, Fortune, Forum, Gladiator, Groundskeeper, Legionary, Overlord, Patrician, Plunder, Rocks, 
        RoyalBlacksmith, Sacrifice, Settlers, Temple, Villa, WildHunt,
        HumbleCastle, CrumblingCastle, SmallCastle, HauntedCastle, OpulentCastle, SprawlingCastle, GrandCastle, KingsCastle,
        
        CatapultRocks, Castles, EncampmentPlunder, GladiatorFortune, PatricianEmporium, SettlersBustlingVillage,
        
        Advance, Annex, Banquet, Conquest, Dominate, Delve, Donate, Ritual, SaltTheEarth, Tax, Triumph, Wedding, Windfall,
        Aqueduct, Arena, BanditFort, Basilica, Baths, Battlefield, Colonnade, DefiledShrine, Fountain, Keep, Labyrinth, MountainPass, Museum, Obelisk, Orchard, Palace, Tomb, Tower, TriumphalArch, Wall, WolfDen,
        
        // Nocturne Expansion
        Bard, Bat, BlessedVillage, Cemetery, Changeling, Cobbler, Conclave, Crypt, CursedGold, CursedVillage, DenOfSin, DevilsWorkshop,
        Druid, Exorcist, FaithfulHound, Fool, Ghost, GhostTown, Goat, Guardian, HauntedMirror, Idol, Imp, Leprechaun, LuckyCoin, MagicLamp, 
        Monastery, Necromancer, NightWatchman, Pasture, Pixie, Pooka, Pouch, Raider, SacredGrove, SecretCave, Shepherd, Skulk, Tormentor, 
        Tracker, TragicHero, Vampire, Werewolf, WillOWisp, Wish, ZombieApprentice, ZombieMason, ZombieSpy,
        
        TheEarthsGift, TheFieldsGift, TheFlamesGift, TheForestsGift, TheMoonsGift, TheMountainsGift, TheRiversGift, TheSeasGift,
        TheSkysGift, TheSunsGift, TheSwampsGift, TheWindsGift,
        
        BadOmens, Delusion, Envy, Famine, Fear, Greed, Haunting, Locusts, Misery, Plague, Poverty, War,
        
        Deluded, Envious, LostInTheWoods, Miserable, TwiceMiserable,
        
        // Renaissance Expansion
        ActingTroupe, BorderGuard, CargoShip, Ducat, Experiment, FlagBearer, Hideout, Improve, Inventor, Lackeys, MountainVillage, 
        OldWitch, Patron, Priest, Recruiter, Research, Scepter, Scholar, Sculptor, Seer, Spices, Swashbuckler, SilkMerchant, 
        Treasurer, Villain,
        
        Flag, Horn, Key, Lantern, TreasureChest,
        
        Academy, Barracks, Canal, Capitalism, Cathedral, Citadel, CityGate, CropRotation, Exploration, Fair, Fleet, Guildhall, 
        Innovation, Pageant, Piazza, RoadNetwork, Sewers, Silos, SinisterPlot, StarChart,
        
        // Menagerie Expansion
        AnimalFair, Barge, BlackCat, BountyHunter, Cavalry, CamelTrain, Cardinal, Coven, Destrier, Displace, Falconer, Fisherman, 
        Gatekeeper, Goatherd, Groom, Horse, Hostelry, HuntingLodge, Kiln, Livery, Mastermind, Paddock, Sanctuary, Scrap, Sheepdog, 
        Sleigh, SnowyVillage, Stockpile, Supplies, VillageGreen, Wayfarer,
        
        Alliance, Banish, Bargain, Commerce, Delay, Demand, Desperation, Enclave, Enhance, Gamble, Invest, March, Pursue, 
        Populate, Reap, Ride, SeizeTheDay, Stampede, Toil, Transport,
        
        WayOfTheButterfly, WayOfTheCamel, WayOfTheChameleon, WayOfTheFrog, WayOfTheGoat, WayOfTheHorse,
        WayOfTheMole, WayOfTheMonkey, WayOfTheMouse, WayOfTheMule, WayOfTheOtter, WayOfTheOwl, WayOfTheOx,
        WayOfThePig, WayOfTheRat, WayOfTheSeal, WayOfTheSheep, WayOfTheSquirrel, WayOfTheTurtle, WayOfTheWorm,
        
        // Promo Cards
        Envoy, Governor, WalledVillage, Prince, BlackMarket, Stash, Summon, Sauna, Avanto, Dismantle,
        SaunaAvanto, Church, Captain,
        // Promo Cards (not yet implemented)
        // Stash
        // Victory token card container
        VictoryTokens
    }

    public static final Card platinum;
    public static final Card gold;
    public static final Card silver;
    public static final Card copper;
    public static final Card potion;

    public static final Card victoryTokens;
    public static final Card colony;
    public static final Card province;
    public static final Card duchy;
    public static final Card estate;

    public static final Card curse;

    // Dominion base
    public static final Card gardens;
    public static final Card moat;
    public static final Card adventurer;
    public static final Card bureaucrat;
    public static final Card cellar;
    public static final Card chancellor;
    public static final Card chapel;
    public static final Card councilRoom;
    public static final Card feast;
    public static final Card festival;
    public static final Card laboratory;
    public static final Card library;
    public static final Card market;
    public static final Card militia;
    public static final Card mine;
    public static final Card moneyLender;
    public static final Card remodel;
    public static final Card smithy;
    public static final Card spy;
    public static final Card thief;
    public static final Card throneRoom;
    public static final Card village;
    public static final Card witch;
    public static final Card woodcutter;
    public static final Card workshop;
    
    // Dominion base, second edition
    public static final Card artisan;
    public static final Card bandit;
    public static final Card harbinger;
    public static final Card merchant;
    public static final Card poacher;
    public static final Card sentry;
    public static final Card vassal;

    // Intrigue expansion
    public static final Card duke;
    public static final Card nobles;
    public static final Card courtyard;
    public static final Card torturer;
    public static final Card harem;
    public static final Card baron;
    public static final Card bridge;
    public static final Card conspirator;
    public static final Card ironworks;
    public static final Card masquerade;
    public static final Card miningVillage;
    public static final Card minion;
    public static final Card pawn;
    public static final Card shantyTown;
    public static final Card steward;
    public static final Card swindler;
    public static final Card tradingPost;
    public static final Card wishingWell;
    public static final Card upgrade;

    // Intrigue first Edition (removed in second Edition)
    public static final Card coppersmith;
    public static final Card greatHall;
    public static final Card saboteur;
    public static final Card scout;
    public static final Card secretChamber;
    public static final Card tribute;

    // Intrigue second Edition
    public static final Card courtier;
    public static final Card diplomat;
    public static final Card lurker;
    public static final Card mill;
    public static final Card patrol;
    public static final Card replace;
    public static final Card secretPassage;

    // Seaside expansion
    public static final Card haven;
    public static final Card seaHag;
    public static final Card tactician;
    public static final Card caravan;
    public static final Card lighthouse;
    public static final Card fishingVillage;
    public static final Card wharf;
    public static final Card merchantShip;
    public static final Card outpost;
    public static final Card ghostShip;
    public static final Card salvager;
    public static final Card pirateShip;
    public static final Card nativeVillage;
    public static final Card island;
    public static final Card cutpurse;
    public static final Card bazaar;
    public static final Card smugglers;
    public static final Card explorer;
    public static final Card pearlDiver;
    public static final Card treasureMap;
    public static final Card navigator;
    public static final Card treasury;
    public static final Card lookout;
    public static final Card ambassador;
    public static final Card warehouse;
    public static final Card embargo;

    // Alchemy expansion
    public static final Card alchemist;
    public static final Card apothecary;
    public static final Card apprentice;
    public static final Card familiar;
    public static final Card golem;
    public static final Card herbalist;
    public static final Card philosophersStone;
    public static final Card possession;
    public static final Card scryingPool;
    public static final Card transmute;
    public static final Card university;
    public static final Card vineyard;

    // Prosperity expansion
    public static final Card bank;
    public static final Card bishop;
    public static final Card city;
    public static final Card contraband;
    public static final Card countingHouse;
    public static final Card expand;
    public static final Card forge;
    public static final Card goons;
    public static final Card grandMarket;
    public static final Card hoard;
    public static final Card kingsCourt;
    public static final Card loan;
    public static final Card mint;
    public static final Card monument;
    public static final Card mountebank;
    public static final Card peddler;
    public static final Card quarry;
    public static final Card rabble;
    public static final Card royalSeal;
    public static final Card talisman;
    public static final Card tradeRoute;
    public static final Card vault;
    public static final Card venture;
    public static final Card watchTower;
    public static final Card workersVillage;

    // Cornucopia expansion
    public static final Card hornOfPlenty;
    public static final Card fairgrounds;
    public static final Card farmingVillage;
    public static final Card fortuneTeller;
    public static final Card hamlet;
    public static final Card harvest;
    public static final Card horseTraders;
    public static final Card huntingParty;
    public static final Card jester;
    public static final Card menagerie;
    public static final Card remake;
    public static final Card tournament;
    public static final Card youngWitch;
    public static final Card bagOfGold;
    public static final Card diadem;
    public static final Card followers;
    public static final Card princess;
    public static final Card trustySteed;

    // Hinterlands expansion
    public static final Card borderVillage;
    public static final Card cache;
    public static final Card cartographer;
    public static final Card crossroads;
    public static final Card develop;
    public static final Card duchess;
    public static final Card embassy;
    public static final Card farmland;
    public static final Card foolsGold;
    public static final Card haggler;
    public static final Card highway;
    public static final Card illGottenGains;
    public static final Card inn;
    public static final Card jackOfAllTrades;
    public static final Card mandarin;
    public static final Card margrave;
    public static final Card nobleBrigand;
    public static final Card nomadCamp;
    public static final Card oasis;
    public static final Card oracle;
    public static final Card scheme;
    public static final Card silkRoad;
    public static final Card spiceMerchant;
    public static final Card stables;
    public static final Card trader;
    public static final Card tunnel;

    // Dark Ages expansion
    public static final Card altar;
    public static final Card armory;
    public static final Card banditCamp;
    public static final Card bandOfMisfits;
    public static final Card beggar;
    public static final Card deathCart;
    public static final Card feodum;
    public static final Card fortress;
    public static final Card poorHouse;
    public static final Card rats;
    public static final Card sage;
    public static final Card squire;
    public static final Card catacombs;
    public static final Card count;
    public static final Card forager;
    public static final Card graverobber;
    public static final Card huntingGrounds;
    public static final Card ironmonger;
    public static final Card junkDealer;
    public static final Card marketSquare;
    public static final Card mystic;
    public static final Card scavenger;
    public static final Card storeroom;
    public static final Card wanderingMinstrel;
    public static final Card procession;
    public static final Card rebuild;
    public static final Card rogue;
    public static final Card spoils;
    public static final Card counterfeit;
    public static final Card pillage;
    public static final Card cultist;
    public static final Card urchin;
    public static final Card mercenary;
    public static final Card marauder;
    public static final Card hermit;
    public static final Card madman;
    public static final Card vagrant;

    public static final Card dameAnna;
    public static final Card dameJosephine;
    public static final Card dameMolly;
    public static final Card dameSylvia;
    public static final Card dameNatalie;
    public static final Card sirBailey;
    public static final Card sirDestry;
    public static final Card sirMartin;
    public static final Card sirMichael;
    public static final Card sirVander;
    public static final Card virtualKnight;

    public static final Card necropolis;
    public static final Card hovel;
    public static final Card overgrownEstate;

    public static final Card abandonedMine;
    public static final Card ruinedLibrary;
    public static final Card ruinedMarket;
    public static final Card ruinedVillage;
    public static final Card survivors;
    public static final Card virtualRuins;

    // Guilds expansion
    public static final Card advisor;
    public static final Card baker;
    public static final Card butcher;
    public static final Card candlestickMaker;
    public static final Card doctor;
    public static final Card herald;
    public static final Card journeyman;
    public static final Card masterpiece;
    public static final Card merchantGuild;
    public static final Card plaza;
    public static final Card soothsayer;
    public static final Card stonemason;
    public static final Card taxman;

    // Adventures expansion
    public static final Card coinOfTheRealm;
    public static final Card page;
    public static final Card peasant;
    public static final Card ratcatcher;
    public static final Card raze;
    public static final Card amulet;
    public static final Card caravanGuard;
    public static final Card dungeon;
    public static final Card gear;
    public static final Card guide;
    public static final Card duplicate;
    public static final Card magpie;
    public static final Card messenger;
    public static final Card miser;
    public static final Card port;
    public static final Card ranger;
    public static final Card transmogrify;
    public static final Card artificer;
    public static final Card bridgeTroll;
    public static final Card distantLands;
    public static final Card giant;
    public static final Card hauntedWoods;
    public static final Card lostCity;
    public static final Card relic;
    public static final Card royalCarriage;
    public static final Card storyteller;
    public static final Card swampHag;
    public static final Card treasureTrove;
    public static final Card wineMerchant;
    public static final Card hireling;

    public static final Card soldier;
    public static final Card treasureHunter;
    public static final Card fugitive;
    public static final Card warrior;
    public static final Card disciple;
    public static final Card hero;
    public static final Card champion;
    public static final Card teacher;

    public static final Card alms;
    public static final Card borrow;
    public static final Card quest;
    public static final Card save;
    public static final Card scoutingParty;
    public static final Card travellingFair;
    public static final Card bonfire;
    public static final Card expedition;
    public static final Card ferry;
    public static final Card plan;
    public static final Card mission;
    public static final Card pilgrimage;
    public static final Card ball;
    public static final Card raid;
    public static final Card seaway;
    public static final Card trade;
    public static final Card lostArts;
    public static final Card training;
    public static final Card inheritance;
    public static final Card pathfinding;
    
    // Empires expansion
    public static final Card archive;
    public static final Card bustlingVillage;
    public static final Card capital;
    public static final Card catapult;
    public static final Card chariotRace;
    public static final Card charm;
    public static final Card cityQuarter;
    public static final Card crown;
    public static final Card emporium;
    public static final Card encampment;
    public static final Card enchantress;
    public static final Card engineer;
    public static final Card farmersMarket;
    public static final Card fortune;
    public static final Card forum;
    public static final Card gladiator;
    public static final Card groundskeeper;
    public static final Card legionary;
    public static final Card overlord;
    public static final Card patrician;
    public static final Card plunder;
    public static final Card rocks;
    public static final Card royalBlacksmith;
    public static final Card sacrifice;
    public static final Card settlers;
    public static final Card temple;
    public static final Card villa;
    public static final Card wildHunt;
    
    public static final Card virtualCatapultRocks;
    public static final Card virtualEncampmentPlunder;
    public static final Card virtualGladiatorFortune;
    public static final Card virtualPatricianEmporium;
    public static final Card virtualSettlersBustlingVillage;
        
    public static final Card humbleCastle;
    public static final Card crumblingCastle;
    public static final Card smallCastle;
    public static final Card hauntedCastle;
    public static final Card opulentCastle;
    public static final Card sprawlingCastle;
    public static final Card grandCastle;
    public static final Card kingsCastle;
    public static final Card virtualCastle;
    
    public static final Card advance;
    public static final Card annex;
    public static final Card banquet;
    public static final Card conquest;
    public static final Card dominate;
    public static final Card delve;
    public static final Card donate;
    public static final Card ritual;
    public static final Card saltTheEarth;
    public static final Card tax;
    public static final Card triumph;
    public static final Card wedding;
    public static final Card windfall;
    
    public static final Card aqueduct;
    public static final Card arena;
    public static final Card banditFort;
    public static final Card basilica;
    public static final Card baths;
    public static final Card battlefield;
    public static final Card colonnade;
    public static final Card defiledShrine;
    public static final Card fountain;
    public static final Card keep;
    public static final Card labyrinth;
    public static final Card mountainPass;
    public static final Card museum;
    public static final Card obelisk;
    public static final Card orchard;
    public static final Card palace;
    public static final Card tomb;
    public static final Card tower;
    public static final Card triumphalArch;
    public static final Card wall;
    public static final Card wolfDen;
    
    // Nocturne expansion
    public static final Card bard;
    public static final Card bat;
    public static final Card blessedVillage;
    public static final Card cemetery;
    public static final Card changeling;
    public static final Card cobbler;
    public static final Card conclave;
    public static final Card crypt;
    public static final Card cursedGold;
    public static final Card cursedVillage;
    public static final Card denOfSin;
    public static final Card devilsWorkshop;
    public static final Card druid;
    public static final Card exorcist;
    public static final Card faithfulHound;
    public static final Card fool;
    public static final Card ghost;
    public static final Card ghostTown;
    public static final Card goat;
    public static final Card guardian;
    public static final Card hauntedMirror;
    public static final Card idol;
    public static final Card imp;
    public static final Card leprechaun;
    public static final Card luckyCoin;
    public static final Card magicLamp;
    public static final Card monastery;
    public static final Card necromancer;
    public static final Card nightWatchman;
    public static final Card pasture;
    public static final Card pixie;
    public static final Card pooka;
    public static final Card pouch;
    public static final Card raider;
    public static final Card sacredGrove;
    public static final Card secretCave;
    public static final Card shepherd;
    public static final Card skulk;
    public static final Card tormentor;
    public static final Card tracker;
    public static final Card tragicHero;
    public static final Card vampire;
    public static final Card werewolf;
    public static final Card willOWisp;
    public static final Card wish;
    public static final Card zombieApprentice;
    public static final Card zombieMason;
    public static final Card zombieSpy;
    
    public static final Card theEarthsGift;
    public static final Card theFieldsGift;
    public static final Card theFlamesGift;
    public static final Card theForestsGift;
    public static final Card theMoonsGift;
    public static final Card theMountainsGift;
    public static final Card theRiversGift;
    public static final Card theSeasGift;
    public static final Card theSkysGift;
    public static final Card theSunsGift;
    public static final Card theSwampsGift;
    public static final Card theWindsGift;
    
    public static final Card badOmens;
    public static final Card delusion;
    public static final Card envy;
    public static final Card famine;
    public static final Card fear;
    public static final Card greed;
    public static final Card haunting;
    public static final Card locusts;
    public static final Card misery;
    public static final Card plague;
    public static final Card poverty;
    public static final Card war;
    
    public static final Card deluded;
    public static final Card envious;
    public static final Card lostInTheWoods;
    public static final Card miserable;
    public static final Card twiceMiserable;
    
    // Renaissance expansion
    public static final Card actingTroupe;
    public static final Card borderGuard;
    public static final Card cargoShip;
    public static final Card ducat;
    public static final Card experiment;
    public static final Card flagBearer;
    public static final Card hideout;
    public static final Card improve;
    public static final Card inventor;
    public static final Card lackeys;
    public static final Card mountainVillage;
    public static final Card oldWitch;
    public static final Card patron;
    public static final Card priest;
    public static final Card recruiter;
    public static final Card research;
    public static final Card scepter;
    public static final Card scholar;
    public static final Card sculptor;
    public static final Card seer;
    public static final Card spices;
    public static final Card swashbuckler;
    public static final Card silkMerchant;
    public static final Card treasurer;
    public static final Card villain;
    
    public static final Card flag;
    public static final Card horn;
    public static final Card key;
    public static final Card lantern;
    public static final Card treasureChest;

    public static final Card academy;
    public static final Card barracks;
    public static final Card canal;
    public static final Card capitalism;
    public static final Card cathedral;
    public static final Card citadel;
    public static final Card cityGate;
    public static final Card cropRotation;
    public static final Card exploration;
    public static final Card fair;
    public static final Card fleet;
    public static final Card guildhall;
    public static final Card innovation;
    public static final Card pageant;
    public static final Card piazza;
    public static final Card roadNetwork;
    public static final Card sewers;
    public static final Card silos;
    public static final Card sinisterPlot;
    public static final Card starChart;
    
    // Menagerie expansion
    public static final Card animalFair;
    public static final Card barge;
    public static final Card blackCat;
    public static final Card bountyHunter;
    public static final Card cavalry;
    public static final Card camelTrain;
    public static final Card cardinal;
    public static final Card coven;
    public static final Card destrier;
    public static final Card displace;
    public static final Card falconer;
    public static final Card fisherman; 
    public static final Card gatekeeper;
    public static final Card goatherd;
    public static final Card groom;
    public static final Card horse;
    public static final Card hostelry;
    public static final Card huntingLodge;
    public static final Card kiln;
    public static final Card livery;
    public static final Card mastermind;
    public static final Card paddock;
    public static final Card sanctuary;
    public static final Card scrap;
    public static final Card sheepdog; 
    public static final Card sleigh;
    public static final Card snowyVillage;
    public static final Card stockpile;
    public static final Card supplies;
    public static final Card villageGreen;
    public static final Card wayfarer;
    
    public static final Card alliance;
    public static final Card banish;
    public static final Card bargain;
    public static final Card commerce;
    public static final Card delay;
    public static final Card demand;
    public static final Card desperation;
    public static final Card enclave;
    public static final Card enhance;
    public static final Card gamble;
    public static final Card invest;
    public static final Card march;
    public static final Card pursue;
    public static final Card populate;
    public static final Card reap;
    public static final Card ride;
    public static final Card seizeTheDay;
    public static final Card stampede;
    public static final Card toil;
    public static final Card transport;
    
    public static final Card wayOfTheButterfly;
    public static final Card wayOfTheCamel;
    public static final Card wayOfTheChameleon;
    public static final Card wayOfTheFrog;
    public static final Card wayOfTheGoat;
    public static final Card wayOfTheHorse;
    public static final Card wayOfTheMole;
    public static final Card wayOfTheMonkey;
    public static final Card wayOfTheMouse;
    public static final Card wayOfTheMule;
    public static final Card wayOfTheOtter;
    public static final Card wayOfTheOwl;
    public static final Card wayOfTheOx;
    public static final Card wayOfThePig;
    public static final Card wayOfTheRat;
    public static final Card wayOfTheSeal;
    public static final Card wayOfTheSheep;
    public static final Card wayOfTheSquirrel;
    public static final Card wayOfTheTurtle;
    public static final Card wayOfTheWorm;
        
    // Promo Cards
    public static final Card walledVillage;
    public static final Card governor;
    public static final Card envoy;
    public static final Card prince;
    public static final Card blackMarket;
    public static final Card stash;
    public static final Card summon;
    public static final Card sauna;
    public static final Card avanto;
    public static final Card dismantle;
    public static final Card church;
    public static final Card captain;
    
    public static final Card virtualSaunaAvanto;

    static {
        // nonKingdomCards
        nonKingdomCards.add(platinum = new CardImpl.Builder(Cards.Kind.Platinum, 9, Type.Treasure).addGold(5).build());
        nonKingdomCards.add(gold = new CardImpl.Builder(Cards.Kind.Gold, 6, Type.Treasure).addGold(3).build());
        nonKingdomCards.add(silver = new CardImpl.Builder(Cards.Kind.Silver, 3, Type.Treasure).addGold(2).build());
        nonKingdomCards.add(copper = new CardImpl.Builder(Cards.Kind.Copper, 0, Type.Treasure).addGold(1).build());
        nonKingdomCards.add(potion = new CardImpl.Builder(Cards.Kind.Potion, 4, Type.Treasure).providePotion().build());

        nonKingdomCards.add(curse = new CardImpl.Builder(Cards.Kind.Curse, 0, Type.Curse).vp(-1).build());

        nonKingdomCards.add(victoryTokens = new CardImpl(Cards.Kind.VictoryTokens, 0));
        
        nonKingdomCards.add(colony = new CardImpl.Builder(Cards.Kind.Colony, 11, Type.Victory).vp(10).build());
        nonKingdomCards.add(province = new CardImpl.Builder(Cards.Kind.Province, 8, Type.Victory).vp(6).build());
        nonKingdomCards.add(duchy = new CardImpl.Builder(Cards.Kind.Duchy, 5, Type.Victory).vp(3).build());
        nonKingdomCards.add(estate = new CardImpl.Builder(Cards.Kind.Estate, 2, Type.Victory).vp(1).build());
        
        // Base Game
        actionCardsBaseGame.add(bureaucrat = new CardImpl.Builder(Cards.Kind.Bureaucrat, 4, Type.Action, Type.Attack).description("Gain a Silver onto your deck. Each other player reveals a Victory card from their hand and puts it onto their deck (or reveals a hand with no Victory cards).").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(cellar = new CardImpl.Builder(Cards.Kind.Cellar, 2, Type.Action).addActions(1).description("Discard any number of cards, then draw that many.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(chapel = new CardImpl.Builder(Cards.Kind.Chapel, 2, Type.Action).description("Trash up to 4 cards from your hand.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(councilRoom = new CardImpl.Builder(Cards.Kind.CouncilRoom, 5, Type.Action).addCards(4).addBuys(1).description("Each other player draws a card.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(festival = new CardImpl.Builder(Cards.Kind.Festival, 5, Type.Action).addActions(2).addBuys(1).addGold(2).expansion(Expansion.Base).build());
        actionCardsBaseGame.add(gardens = new CardImpl.Builder(Cards.Kind.Gardens, 4, Type.Victory).description("Worth 1 Victory Point for every 10 cards in your deck (rounded down).").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(laboratory = new CardImpl.Builder(Cards.Kind.Laboratory, 5, Type.Action).addActions(1).addCards(2).expansion(Expansion.Base).build());
        actionCardsBaseGame.add(library = new CardImpl.Builder(Cards.Kind.Library, 5, Type.Action).description("Draw until you have 7 cards in hand, skipping any Action cards you choose to; set those aside, discarding them afterwards.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(market = new CardImpl.Builder(Cards.Kind.Market, 5, Type.Action).addActions(1).addBuys(1).addGold(1).addCards(1).expansion(Expansion.Base).build());
        actionCardsBaseGame.add(militia = new CardImpl.Builder(Cards.Kind.Militia, 4, Type.Action, Type.Attack).addGold(2).description("Each other player discards down to 3 cards in hand.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(mine = new CardImpl.Builder(Cards.Kind.Mine, 5, Type.Action).description("You may trash a Treasure from your hand. Gain a Treasure to your hand costing up to (3) Coins more than it.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(moat = new CardImpl.Builder(Cards.Kind.Moat, 2, Type.Action, Type.Reaction).addCards(2).description("When another player plays an Attack card, you may reveal this from your hand. If you do, you are unaffected by that Attack.").description("When another player plays an Attack card, you may reveal this from your hand. If you do, you are unaffected by that Attack.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(moneyLender = new CardImpl.Builder(Cards.Kind.Moneylender, 4, Type.Action).hasPlusCoin().description("You may trash a Copper from your hand for +(3) Coins.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(remodel = new CardImpl.Builder(Cards.Kind.Remodel, 4, Type.Action).trashForced().description("Trash a card from your hand. Gain a card costing up to (2) Coins more than it.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(smithy = new CardImpl.Builder(Cards.Kind.Smithy, 4, Type.Action).addCards(3).expansion(Expansion.Base).build());
        actionCardsBaseGame.add(throneRoom = new CardImpl.Builder(Cards.Kind.ThroneRoom, 4, Type.Action).description("You may play an Action card from your hand twice.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(village = new CardImpl.Builder(Cards.Kind.Village, 3, Type.Action).addCards(1).addActions(2).expansion(Expansion.Base).build());
        actionCardsBaseGame.add(witch = new CardImpl.Builder(Cards.Kind.Witch, 5, Type.Action, Type.Attack).addCards(2).description("Each other player gains a Curse.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(workshop = new CardImpl.Builder(Cards.Kind.Workshop, 3, Type.Action).description("Gain a card costing up to 4 Coins.").expansion(Expansion.Base).build());

        actionCardsBaseGame2E.add(artisan = new CardImpl.Builder(Cards.Kind.Artisan, 6, Type.Action).description("Gain a card to your hand costing up to (5) Coins. Put a card from your hand onto your deck.").expansion(Expansion.Base).build());
        actionCardsBaseGame2E.add(bandit = new CardImpl.Builder(Cards.Kind.Bandit, 5, Type.Action, Type.Attack).description("Gain a Gold. Each other player reveals the top two cards of their deck, trashes a revealed Treasure other than Copper, and discards the rest.").expansion(Expansion.Base).build());
        actionCardsBaseGame2E.add(harbinger = new CardImpl.Builder(Cards.Kind.Harbinger, 3, Type.Action).addCards(1).addActions(1).description("Look through your discard pile. You may put a card from it onto your deck.").expansion(Expansion.Base).build());
        actionCardsBaseGame2E.add(merchant = new CardImpl.Builder(Cards.Kind.Merchant, 3, Type.Action).hasPlusCoin().addCards(1).addActions(1).description("The first time you play a Silver this turn, +(1) Coin.").expansion(Expansion.Base).build());
        actionCardsBaseGame2E.add(poacher = new CardImpl.Builder(Cards.Kind.Poacher, 4, Type.Action).addCards(1).addActions(1).addGold(1).description("Discard a card per empty Supply pile.").expansion(Expansion.Base).build());
        actionCardsBaseGame2E.add(sentry = new CardImpl.Builder(Cards.Kind.Sentry, 5, Type.Action).addCards(1).addActions(1).description("Look at the top 2 cards of your deck. Trash and/or discard any number of them. Put the rest back on top on any order.").expansion(Expansion.Base).build());
        actionCardsBaseGame2E.add(vassal = new CardImpl.Builder(Cards.Kind.Vassal, 3, Type.Action).addGold(2).description("Discard the top card of your deck. If it's an Action card, you may play it.").expansion(Expansion.Base).build());
        actionCardsBaseGameAll.addAll(actionCardsBaseGame2E);
        actionCardsBaseGame2E.addAll(actionCardsBaseGame);
        
        actionCardsBaseGame.add(adventurer = new CardImpl.Builder(Cards.Kind.Adventurer, 6, Type.Action).description("Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(chancellor = new CardImpl.Builder(Cards.Kind.Chancellor, 3, Type.Action).addGold(2).description("You may immediately put your deck into your discard pile.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(feast = new CardImpl.Builder(Cards.Kind.Feast, 4, Type.Action).description("Trash this card. Gain a card costing up to 5 coin.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(spy = new CardImpl.Builder(Cards.Kind.Spy, 4, Type.Action, Type.Attack).addCards(1).addActions(1).description("Each player (including you) reveals the top card of his deck and either discards it or puts it back, your choice.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(thief = new CardImpl.Builder(Cards.Kind.Thief, 4, Type.Action, Type.Attack).description("Each other player reveals the top 2 cards of his deck. If they revealed any Treasure cards, they trash one of them that you choose. You may gain any or all of these trashed cards. They discard the other revealed cards.").expansion(Expansion.Base).build());
        actionCardsBaseGame.add(woodcutter = new CardImpl.Builder(Cards.Kind.Woodcutter, 3, Type.Action).addBuys(1).addGold(2).expansion(Expansion.Base).build());
        
        actionCardsBaseGameAll.addAll(actionCardsBaseGame);
        
        // Intrigue
        actionCardsIntrigue.add(baron = new CardImpl.Builder(Cards.Kind.Baron, 4, Type.Action).hasPlusCoin().addBuys(1).description("You may discard an Estate for +(4) Coins. If you don't, gain an Estate.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(bridge = new CardImpl.Builder(Cards.Kind.Bridge, 4, Type.Action).addBuys(1).addGold(1).description("This turn, cards (everywhere) cost (1) Coin less, but not less than (0) Coins.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(conspirator = new CardImpl.Builder(Cards.Kind.Conspirator, 4, Type.Action).addGold(2).description("If you've played 3 or more Actions this turn (counting this): +1 Card, +1 Action.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(courtyard = new CardImpl.Builder(Cards.Kind.Courtyard, 2, Type.Action).addCards(3).description("Put a card from your hand onto your deck.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(duke = new CardImpl.Builder(Cards.Kind.Duke, 5, Type.Victory).description("Worth 1 Victory Point per Duchy you have.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(harem = new CardImpl.Builder(Cards.Kind.Harem, 6, Type.Treasure, Type.Victory).addGold(2).vp(2).expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(ironworks = new CardImpl.Builder(Cards.Kind.Ironworks, 4, Type.Action).hasPlusCoin().description("Gain a card costing up to 4 Coins. If the gained card is an... Action card, +1 Action. Treasure card, +1 Coin. Victory card, +1 Card.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(masquerade = new CardImpl.Builder(Cards.Kind.Masquerade, 3, Type.Action).addCards(2).description("Each player with any cards in hand passes one to the next such player to their left, at once. Then you may trash a card from your hand.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(miningVillage = new CardImpl.Builder(Cards.Kind.MiningVillage, 4, Type.Action).hasPlusCoin().addCards(1).addActions(2).description("You may trash this for +(2) Coins.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(minion = new CardImpl.Builder(Cards.Kind.Minion, 5, Type.Action, Type.Attack).hasPlusCoin().addActions(1).description("Choose one: +(2) Coins; or discard your hand, +4 Cards, and each other player with at least 5 cards in hand discards their hand and draws 4 cards.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(nobles = new CardImpl.Builder(Cards.Kind.Nobles, 6, Type.Action, Type.Victory).vp(2).description("Choose one: +3 Cards; or +2 Actions.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(pawn = new CardImpl.Builder(Cards.Kind.Pawn, 2, Type.Action).hasPlusCoin().description("Choose two: +1 Card; +1 Action; +1 Buy; +(1) Coin. The choices must be different.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(shantyTown = new CardImpl.Builder(Cards.Kind.ShantyTown, 3, Type.Action).addActions(2).description("Reveal you hand. If you have no Action cards in hand, +2 Cards.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(steward = new CardImpl.Builder(Cards.Kind.Steward, 3, Type.Action).hasPlusCoin().description("Choose one: +2 Cards; or +2 Coins; or trash 2 cards from your hand.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(swindler = new CardImpl.Builder(Cards.Kind.Swindler, 3, Type.Action, Type.Attack).addGold(2).description("Each other player trashes the top card of their deck and gains a card with the same cost that you choose.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(torturer = new CardImpl.Builder(Cards.Kind.Torturer, 5, Type.Action, Type.Attack).addCards(3).description("Each other player either discards 2 cards or gains a Curse to their hand, their choice. (They may pick an option they can't do.)").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(tradingPost = new CardImpl.Builder(Cards.Kind.TradingPost, 5, Type.Action).trashForced().description("Trash 2 cards from your hand. If you did, gain a Silver to your hand.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(upgrade = new CardImpl.Builder(Cards.Kind.Upgrade, 5, Type.Action).trashForced().addCards(1).addActions(1).description("Trash a card from your hand. Gain a card costing exactly 1 Coin more than it.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(wishingWell = new CardImpl.Builder(Cards.Kind.WishingWell, 3, Type.Action).addCards(1).addActions(1).description("Name a card, then reveal the top card of your deck. If you named it, put it into your hand.").expansion(Expansion.Intrigue).build());

        actionCardsIntrigue2E.add(courtier = new CardImpl.Builder(Cards.Kind.Courtier, 5, Type.Action).hasPlusCoin().description("Reveal a card from your hand.  For each type it has (Action, Attack, etc.), choose one: +1 Action, or +1 Buy, or +, or gain a Gold.  The choices must be different.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue2E.add(patrol = new CardImpl.Builder(Cards.Kind.Patrol, 5, Type.Action).addCards(3).description("Reveal the top 4 cards of your deck. Put the Victory cards and Curse cards into your hand. Put the rest back in any order.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue2E.add(replace = new CardImpl.Builder(Cards.Kind.Replace, 5, Type.Action, Type.Attack).description("Trash a card from your hand. Gain a card costing up to 2 Coins more than the trashed card. If the gained card is an Action or Treasure, put it onto your deck. If it's a Victory card, each other player gains a Curse.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue2E.add(diplomat = new CardImpl.Builder(Cards.Kind.Diplomat, 4, Type.Action, Type.Reaction).addCards(2).description("If you have 5 or fewer cards in hand (after drawing), +2 Actions.  When another player plays an Attack card, you may first reveal this from a hand of 5 or more cards, to draw 2 cards then discard 3.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue2E.add(mill = new CardImpl.Builder(Cards.Kind.Mill, 4, Type.Action, Type.Victory).hasPlusCoin().addActions(1).addCards(1).vp(1).description("You may discard 2 cards for 2 Coins.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue2E.add(secretPassage = new CardImpl.Builder(Kind.SecretPassage, 4, Type.Action).addCards(2).addActions(1).description("Take a card from your hand and put it anywhere in your deck.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue2E.add(lurker = new CardImpl.Builder(Cards.Kind.Lurker, 2, Type.Action).addActions(1).description("Choose one: Trash an Action card from the Supply, or gain an Action card from the trash.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigueAll.addAll(actionCardsIntrigue2E);
        actionCardsIntrigue2E.addAll(actionCardsIntrigue);
        
        actionCardsIntrigue.add(coppersmith = new CardImpl.Builder(Cards.Kind.Coppersmith, 4, Type.Action).description("Copper produces an extra 1 coin this turn.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(greatHall = new CardImpl.Builder(Cards.Kind.GreatHall, 3, Type.Action, Type.Victory).addCards(1).addActions(1).vp(1).expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(saboteur = new CardImpl.Builder(Cards.Kind.Saboteur, 5, Type.Action, Type.Attack).description("Each other player reveals cards from the top of his deck until revealing one costing 3 Coins or more. He trashes that card and may gain a card costing at most 2 Coins less than it. He discards the other revealed cards.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(scout = new CardImpl.Builder(Cards.Kind.Scout, 4, Type.Action).addActions(1).description("Reveal the top 4 cards of your deck. Put the revealed Victory cards into your hand. Put the other cards on top of your deck in any order.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(secretChamber = new CardImpl.Builder(Cards.Kind.SecretChamber, 2, Type.Action, Type.Reaction).hasPlusCoin().description("Discard any number of cards. +1 Coin per card discarded. When another player plays an Attack card, you may reveal this from your hand. If you do, +2 Cards, then put 2 cards from your hand on top of your deck.").expansion(Expansion.Intrigue).build());
        actionCardsIntrigue.add(tribute = new CardImpl.Builder(Cards.Kind.Tribute, 5, Type.Action).hasPlusCoin().description("The player to your left reveals then discards the top 2 cards of his deck. For each differently named card revealed, if it is an... Action Card, +2 Actions. Treasure Card, +2 Coins. Victory Card, +2 Cards.").expansion(Expansion.Intrigue).build());
        
        actionCardsIntrigueAll.addAll(actionCardsIntrigue);
        
        // Seaside
        actionCardsSeaside.add(ambassador = new CardImpl.Builder(Cards.Kind.Ambassador, 3, Type.Action, Type.Attack).trashForced().description("Reveal a card from your hand. Return up to 2 copies of it from your hand to the Supply. Then each other player gains a copy of it.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(bazaar = new CardImpl.Builder(Cards.Kind.Bazaar, 5, Type.Action).addCards(1).addActions(2).addGold(1).expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(caravan = new CardImpl.Builder(Cards.Kind.Caravan, 4, Type.Action, Type.Duration).addCardsNextTurn(1).addCards(1).addActions(1).expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(cutpurse = new CardImpl.Builder(Cards.Kind.Cutpurse, 4, Type.Action, Type.Attack).addGold(2).description("Each other player discards a Copper card (or reveals a hand with no Copper).").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(embargo = new CardImpl.Builder(Cards.Kind.Embargo, 2, Type.Action).addGold(2).description("Trash this to add an Embargo token to a Supply pile. (For the rest of the game, when a player buys a card from that pile, they gain a Curse.)").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(explorer = new CardImpl.Builder(Cards.Kind.Explorer, 5, Type.Action).description("You may reveal a Province card from your hand. If you do, gain a Gold card, putting it into your hand. Otherwise, gain a Silver card, putting it into your hand.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(fishingVillage = new CardImpl.Builder(Cards.Kind.FishingVillage, 3, Type.Action, Type.Duration).addGoldNextTurn(1).addActionsNextTurn(1).addActions(2).addGold(1).expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(ghostShip = new CardImpl.Builder(Cards.Kind.GhostShip, 5, Type.Action, Type.Attack).addCards(2).description("Each other player with 4 or more cards in hand puts cards from his hand on top of his deck until he has 3 cards in his hand.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(haven = new CardImpl.Builder(Cards.Kind.Haven, 2, Type.Action, Type.Duration).addCards(1).addActions(1).description("Set aside a card from your hand face down. At the start of your next turn, put it into your hand.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(island = new CardImpl.Builder(Cards.Kind.Island, 4, Type.Action, Type.Victory).vp(2).description("Set aside this and another card from your hand. Return them to your deck at the end of the game.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(lighthouse = new CardImpl.Builder(Cards.Kind.Lighthouse, 2, Type.Action, Type.Duration).addGoldNextTurn(1).addActions(1).addGold(1).description("While you have this in play, when another player plays an Attack card, it doesn't affect you.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(lookout = new CardImpl.Builder(Cards.Kind.Lookout, 3, Type.Action).addActions(1).description("Look at the top 3 cards of your deck. Trash one of them. Discard one of them. Put the other one on top of your deck.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(merchantShip = new CardImpl.Builder(Cards.Kind.MerchantShip, 5, Type.Action, Type.Duration).addGoldNextTurn(2).addGold(2).expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(nativeVillage = new CardImpl.Builder(Cards.Kind.NativeVillage, 2, Type.Action).addActions(2).description("Choose one: Set aside the top card of your deck face down on your Native Village mat; or put all the cards from your mat into your hand.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(navigator = new CardImpl.Builder(Cards.Kind.Navigator, 4, Type.Action).addGold(2).description("Look at the top 5 cards of your deck. Either discard all of them, or put them back on top of your deck in any order.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(outpost = new CardImpl.Builder(Cards.Kind.Outpost, 5, Type.Action, Type.Duration).takeAnotherTurn(3).description("You only draw 3 cards (instead of 5) in this turn's Clean-up phase. Take an extra turn after this one. This can't cause you to take more than two consecutive turns.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(pearlDiver = new CardImpl.Builder(Cards.Kind.PearlDiver, 2, Type.Action).addCards(1).addActions(1).description("Look at the bottom card of your deck. You may put it on top.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(pirateShip = new CardImpl.Builder(Cards.Kind.PirateShip, 4, Type.Action, Type.Attack).hasPlusCoin().description("Choose one: Each other player reveals the top 2 cards of his deck, trashes a revealed Treasure that you choose, discards the rest, and if anyone trashed a Treasure you take a Coin token; or, +1 Coin per Coin token you've taken with Pirate Ships this game.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(salvager = new CardImpl.Builder(Cards.Kind.Salvager, 4, Type.Action).hasPlusCoin().trashForced().addBuys(1).description("Trash a card from your hand. + Coins equal to its cost.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(seaHag = new CardImpl.Builder(Cards.Kind.SeaHag, 4, Type.Action, Type.Attack).description("Each other player discards the top card of his deck, then gains a Curse card, putting it on top of his deck.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(smugglers = new CardImpl.Builder(Cards.Kind.Smugglers, 3, Type.Action).description("Gain a copy of a card costing up to 6 Coins that the player to your right gained on his last turn.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(tactician = new CardImpl.Builder(Cards.Kind.Tactician, 5, Type.Action, Type.Duration).description("Discard your hand. If you discarded any cards this way, then at the start of your next turn, +5 Cards, +1 Buy, and +1 Action.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(treasureMap = new CardImpl.Builder(Cards.Kind.TreasureMap, 4, Type.Action).description("Trash this and another copy of Treasure Map from your hand. If you do trash two Treasure Maps, gain 4 Gold cards putting them on top of your deck.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(treasury = new CardImpl.Builder(Cards.Kind.Treasury, 5, Type.Action).addCards(1).addActions(1).addGold(1).description("When you discard this from play, if you didn't buy a Victory card this turn, you may put this on top of your deck.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(warehouse = new CardImpl.Builder(Cards.Kind.Warehouse, 3, Type.Action).addCards(3).addActions(1).description("Discard 3 cards.").expansion(Expansion.Seaside).build());
        actionCardsSeaside.add(wharf = new CardImpl.Builder(Cards.Kind.Wharf, 5, Type.Action, Type.Duration).addCardsNextTurn(2).addBuysNextTurn(1).addCards(2).addBuys(1).expansion(Expansion.Seaside).build());

        // Alchemy
        actionCardsAlchemy.add(alchemist = new CardImpl.Builder(Cards.Kind.Alchemist, 3, Type.Action).addActions(1).addCards(2).costPotion().description("When you discard this from play, you may put this on top of your deck if you have a Potion in play.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(apothecary = new CardImpl.Builder(Cards.Kind.Apothecary, 2, Type.Action).addActions(1).addCards(1).costPotion().description("Reveal the top 4 cards of your deck.  Put the revealed Coppers and Potions into your hand.  Put the other cards back on top of your deck in any order.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(apprentice = new CardImpl.Builder(Cards.Kind.Apprentice, 5, Type.Action).trashForced().addActions(1).description("Trash a card from your hand.  +1 Card per Coin it costs.  +2 Cards if it has a Potion in its cost.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(familiar = new CardImpl.Builder(Cards.Kind.Familiar, 3, Type.Action, Type.Attack).addCards(1).addActions(1).costPotion().description("Each other player gains a Curse.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(golem = new CardImpl.Builder(Cards.Kind.Golem, 4, Type.Action).costPotion().description("Reveal cards from your deck until you reveal 2 Action cards other than Golem cards.  Discard the other cards, then play the Action cards in either order.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(herbalist = new CardImpl.Builder(Cards.Kind.Herbalist, 2, Type.Action).addBuys(1).addGold(1).description("When you discard this from play, you may put one of your Treasures from play on top of your deck.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(philosophersStone = new CardImpl.Builder(Cards.Kind.PhilosophersStone, 3, Type.Treasure).costPotion().description("Count your deck and discard pile. +1 Coin per 5 cards total between them (rounded down).").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(possession = new CardImpl.Builder(Cards.Kind.Possession, 6, Type.Action).costPotion().description("The player to your left takes an extra turn after this one, in which you can see all cards they can and make all decisions for them. Any cards or Debt tokens they would gain on that turn, you gain instead; any cards of theirs that are trashed are set aside and put in their discard pile at end of turn.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(scryingPool = new CardImpl.Builder(Cards.Kind.ScryingPool, 2, Type.Action, Type.Attack).addActions(1).costPotion().description("Each player (including you) reveals the top card of his deck and either discards it or puts it back, your choice.  Then reveal cards from the top of your deck until you reveal one that is not an Action.  Put all of your revealed cards into your hand.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(transmute = new CardImpl.Builder(Cards.Kind.Transmute, 0, Type.Action).trashForced().costPotion().description("Trash a card from your hand.  If it is an . . . Action card, gain a Duchy; Treasure card, gain a Transmute; Victory card gain a Gold.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(university = new CardImpl.Builder(Cards.Kind.University, 2, Type.Action).addActions(2).costPotion().description("You may gain an Action card costing up to 5.").expansion(Expansion.Alchemy).build());
        actionCardsAlchemy.add(vineyard = new CardImpl.Builder(Cards.Kind.Vineyard, 0, Type.Victory).costPotion().description("Worth 1 Victory Point for every 3 Action cards in your deck (rounded down).").expansion(Expansion.Alchemy).build());

        // Prosperity
        actionCardsProsperity.add(bank = new CardImpl.Builder(Cards.Kind.Bank, 7, Type.Treasure).description("+1 Coin per Treasure card you have in play (counting this).").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(bishop = new CardImpl.Builder(Cards.Kind.Bishop, 4, Type.Action).trashForced().addGold(1).addVictoryTokens(1).description("Trash a card from your hand.  Gain Victory tokens equal to half its cost in coins, rounded down.  Each other player may trash a card from his hand.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(city = new CardImpl.Builder(Cards.Kind.City, 5, Type.Action).hasPlusCoin().addActions(2).addCards(1).description("If there are one or more empty Supply piles, +1 Card.  If there are two or more, +1 Coin and +1 Buy.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(contraband = new CardImpl.Builder(Cards.Kind.Contraband, 5, Type.Treasure).addGold(3).addBuys(1).description("The player to your left names a card.  You can't buy that card this turn.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(countingHouse = new CardImpl.Builder(Cards.Kind.CountingHouse, 5, Type.Action).description("Look through your discard pile, reveal any number of Copper cards from it, and put them into your hand.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(expand = new CardImpl.Builder(Cards.Kind.Expand, 7, Type.Action).trashForced().description("Trash a card from your hand.  Gain a card costing up to 3 coins more than the trashed card.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(forge = new CardImpl.Builder(Cards.Kind.Forge, 7, Type.Action).trashForced().description("Trash any number of cards from your hand.  Gain a card with cost exactly equal to the total cost in coins of the trashed cards.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(goons = new CardImpl.Builder(Cards.Kind.Goons, 6, Type.Action, Type.Attack).addBuys(1).addGold(2).description("Each other player discards down to 3 cards in hand. While you have this in play, when you buy a card, +1 Victory token.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(grandMarket = new CardImpl.Builder(Cards.Kind.GrandMarket, 6, Type.Action).addCards(1).addActions(1).addBuys(1).addGold(2).description("You can't buy this if you have any Copper in play.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(hoard = new CardImpl.Builder(Cards.Kind.Hoard, 6, Type.Treasure).addGold(2).description("While you have this in play, when you buy a Victory card, gain a Gold.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(kingsCourt = new CardImpl.Builder(Cards.Kind.KingsCourt, 7, Type.Action).description("You may choose an Action card in your hand.  Play it three times.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(loan = new CardImpl.Builder(Cards.Kind.Loan, 3, Type.Treasure).addGold(1).description("Reveal cards from your deck until you reveal a Treasure. Discard it or trash it. Discard the other cards.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(mint = new CardImpl.Builder(Cards.Kind.Mint, 5, Type.Action).description("You may reveal a Treasure card from your hand.  Gain a copy of it.  When you buy this, trash all Treasures you have in play.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(monument = new CardImpl.Builder(Cards.Kind.Monument, 4, Type.Action).addGold(2).addVictoryTokens(1).expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(mountebank = new CardImpl.Builder(Cards.Kind.Mountebank, 5, Type.Action, Type.Attack).addGold(2).description("Each other player may discard a Curse.  If he doesn't, he gains a Curse and a Copper.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(peddler = new CardImpl.Builder(Cards.Kind.Peddler, 8, Type.Action).addActions(1).addCards(1).addGold(1).description("During a player's Buy phase, this costs 2 Coins less per Action card they have in play.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(quarry = new CardImpl.Builder(Cards.Kind.Quarry, 4, Type.Treasure).addGold(1).description("While this is in play, Action cards cost 2 Coins less.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(rabble = new CardImpl.Builder(Cards.Kind.Rabble, 5, Type.Action, Type.Attack).addCards(3).description("Each other player reveals the top 3 cards of his deck, discards the revealed Actions and Treasures, and puts the rest back on top in any order he chooses.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(royalSeal = new CardImpl.Builder(Cards.Kind.RoyalSeal, 5, Type.Treasure).addGold(2).description("While this is in play, when you gain a card, you may put that card on top of your deck.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(talisman = new CardImpl.Builder(Cards.Kind.Talisman, 4, Type.Treasure).addGold(1).description("While you have this in play, when you buy a card costing 4 coins or less that is not a Victory card, gain a copy of it.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(tradeRoute = new CardImpl.Builder(Cards.Kind.TradeRoute, 3, Type.Action).hasPlusCoin().trashForced().addBuys(1).description("+1 Coin per token on the Trade Route mat.  Trash a card from your hand.  Setup: Put a token on each Victory card Supply pile.  When a card is gained from that pile, move the token to the Trade Route mat.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(vault = new CardImpl.Builder(Cards.Kind.Vault, 5, Type.Action).hasPlusCoin().addCards(2).description("Discard any number of cards.  +1 coin per card discarded.  Each other player may discard 2 cards.  If he does, he draws a card.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(venture = new CardImpl.Builder(Cards.Kind.Venture, 5, Type.Treasure).addGold(1).description("Reveal cards from your deck until you reveal a Treasure.  Discard the other cards.  Play that Treasure.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(watchTower = new CardImpl.Builder(Cards.Kind.WatchTower, 3, Type.Action, Type.Reaction).description("Draw until you have 6 cards in hand.  When you gain a card, you may reveal this from your hand.  If you do, either trash that card, or put it on top of your deck.").expansion(Expansion.Prosperity).build());
        actionCardsProsperity.add(workersVillage = new CardImpl.Builder(Cards.Kind.WorkersVillage, 4, Type.Action).addCards(1).addActions(2).addBuys(1).expansion(Expansion.Prosperity).build());

        // Cornucopia
        actionCardsCornucopia.add(fairgrounds = new CardImpl.Builder(Cards.Kind.Fairgrounds, 6, Type.Victory).description("Worth 2 points for every 5 differently named cards in your deck (round down).").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(farmingVillage = new CardImpl.Builder(Cards.Kind.FarmingVillage, 4, Type.Action).addActions(2).description("Reveal cards from the top of your deck until you reveal an Action or Treasure card.  Put that card into your hand and discard the other cards.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(fortuneTeller = new CardImpl.Builder(Cards.Kind.FortuneTeller, 3, Type.Action, Type.Attack).addGold(2).description("Each other player reveals cards from the top of his deck until he reveals a Victory or Curse card.  He puts it on top and discards the other revealed cards.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(hamlet = new CardImpl.Builder(Cards.Kind.Hamlet, 2, Type.Action).addActions(1).addCards(1).description("You may discard a card; if you do +1 Action.  You may discard a card; if you do +1 Buy.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(harvest = new CardImpl.Builder(Cards.Kind.Harvest, 5, Type.Action).hasPlusCoin().description("Reveal the top 4 cards of your deck, then discard them.  +1 coin per differently named card revealed.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(hornOfPlenty = new CardImpl.Builder(Cards.Kind.HornofPlenty, 5, Type.Treasure).description("When you play this, gain a card costing up to 1 coin per differently named card you have in play, counting this.  If it's a Victory card, trash this.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(horseTraders = new CardImpl.Builder(Cards.Kind.HorseTraders, 4, Type.Action, Type.Reaction).addBuys(1).addGold(3).description("Discard 2 cards.  When another player plays an Attack card, you may set this aside from your hand.  If you do, then at the start of your next turn, +1 Card and return this to your hand.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(huntingParty = new CardImpl.Builder(Cards.Kind.HuntingParty, 5, Type.Action).addActions(1).addCards(1).description("Reveal your hand.  Reveal cards from your deck until you reveal a card that isn't a duplicate of one in your hand.  Put it into your hand and discard the rest.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(jester = new CardImpl.Builder(Cards.Kind.Jester, 5, Type.Action, Type.Attack).addGold(2).description("Each other player discards the top card of his deck.  If it's a Victory card, he gains a Curse.  Otherwise either he gains a copy of the discarded card or you do, your choice.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(menagerie = new CardImpl.Builder(Cards.Kind.Menagerie, 3, Type.Action).addActions(1).description("Reveal your hand.  If there are no duplicate cards in it, +3 Cards.  Otherwise, +1 Card.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(remake = new CardImpl.Builder(Cards.Kind.Remake, 4, Type.Action).trashForced().description("Do this twice.  Trash a card from your hand, then gain a card costing exactly 1 coin more than the trashed card.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(tournament = new CardImpl.Builder(Cards.Kind.Tournament, 4, Type.Action).hasPlusCoin().addActions(1).description("Each player may reveal a Province from his hand.  If you do, discard it and gain a Prize (from the Prize pile) or a Duchy, putting it on top of your deck.  If no one else does, +1 Card, +1 coin.").expansion(Expansion.Cornucopia).build());
        actionCardsCornucopia.add(youngWitch = new CardImpl.Builder(Cards.Kind.YoungWitch, 4, Type.Action, Type.Attack).addCards(2).description("Discard 2 cards.  Each other player may reveal a Bane card from his hand.  If he doesn't, he gains a Curse.  Setup:  Add an extra Kingdom card pile costing 2 or 3 coins to the Supply.  Cards from that pile are Bane cards.").expansion(Expansion.Cornucopia).build());

        // Prizes
        prizeCards.add(bagOfGold   = new CardImpl.Builder(Cards.Kind.BagofGold, 0, Type.Action, Type.Prize).addActions(1).description("Gain a Gold, putting it on top of your deck.  (This is not in the Supply.)").expansion(Expansion.Cornucopia).build());
        prizeCards.add(diadem      = new CardImpl.Builder(Cards.Kind.Diadem, 0, Type.Treasure, Type.Prize).addGold(2).description("When you play this, +1 coin per unused Action you have (Action, not Action card).  (This is not in the Supply.)").expansion(Expansion.Cornucopia).build());
        prizeCards.add(followers   = new CardImpl.Builder(Cards.Kind.Followers, 0, Type.Action, Type.Attack, Type.Prize).addCards(2).description("Gain an Estate.  Each other player gains a Curse and discards down to 3 cards in hand.  (This is not in the Supply.)").expansion(Expansion.Cornucopia).build());
        prizeCards.add(princess    = new CardImpl.Builder(Cards.Kind.Princess, 0, Type.Action, Type.Prize).addBuys(1).description("While this is in play, cards cost 2 coins less, but not less than 0.  (This is not in the Supply.)").expansion(Expansion.Cornucopia).build());
        prizeCards.add(trustySteed = new CardImpl.Builder(Cards.Kind.TrustySteed, 0, Type.Action, Type.Prize).hasPlusCoin().description("Choose two:  +2 Cards; +2 Actions; +2 coins; gain 4 silvers and put your deck into your discard pile.  (The choices must be different.)  (This is not in the Supply.)").expansion(Expansion.Cornucopia).build());

        // Hinterlands
        actionCardsHinterlands.add(borderVillage = new CardImpl.Builder(Cards.Kind.BorderVillage, 6, Type.Action).addCards(1).addActions(2).description("When you gain this, gain a card costing less than this.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(cache = new CardImpl.Builder(Cards.Kind.Cache, 5, Type.Treasure).addGold(3).description("When you gain this, gain two Coppers.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(cartographer = new CardImpl.Builder(Cards.Kind.Cartographer, 5, Type.Action).addCards(1).addActions(1).description("Look at the top 4 cards of your deck. Discard any number of them. Put the rest back on top in any order.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(crossroads = new CardImpl.Builder(Cards.Kind.Crossroads, 2, Type.Action).description("Reveal your hand. +1 Card per Victory card revealed. If this is the first time you played a Crossroads this turn, +3 Actions.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(develop = new CardImpl.Builder(Cards.Kind.Develop, 3, Type.Action).trashForced().description("Trash a card from your hand. Gain a card costing exactly 1 coin more than it and a card costing exactly 1 less than it, in either order, putting them on top of your deck.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(duchess = new CardImpl.Builder(Cards.Kind.Duchess, 2, Type.Action).addGold(2).description("Each player (including you) looks at the top card of his deck, and discards it or puts it back - In games using this, when you gain a Duchy, you may gain a Duchess.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(embassy = new CardImpl.Builder(Cards.Kind.Embassy, 5, Type.Action).addCards(5).description("Discard 3 cards - When you gain this, each other player gains a Silver.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(farmland = new CardImpl.Builder(Cards.Kind.Farmland, 6, Type.Victory).vp(2).description("When you buy this, trash a card from your hand. Gain a card costing exactly 2 Coins more than the trashed card.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(foolsGold = new CardImpl.Builder(Cards.Kind.FoolsGold, 2, Type.Treasure, Type.Reaction).addGold(1).description("If this is the first time you played a Fool's Gold this turn, +1 Coin, otherwise +4 Coins - When another player gains a Province, you may trash this from your hand to gain a Gold onto your deck.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(haggler = new CardImpl.Builder(Cards.Kind.Haggler, 5, Type.Action).addGold(2).description(" — While you have this in play, when you buy a card, gain a cheaper non-Victory card.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(highway = new CardImpl.Builder(Cards.Kind.Highway, 5, Type.Action).addCards(1).addActions(1).description("- While this is in play, cards cost 1 Coin less.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(illGottenGains = new CardImpl.Builder(Cards.Kind.IllGottenGains, 5, Type.Treasure).addGold(1).description("You may gain a Copper to your hand. - When you gain this, each other player gains a Curse.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(inn = new CardImpl.Builder(Cards.Kind.Inn, 5, Type.Action).addCards(2).addActions(2).description("Discard 2 cards - When you gain this, look through your discard pile (including this), reveal any number of Action cards from it, and shuffle them into your deck.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(jackOfAllTrades = new CardImpl.Builder(Cards.Kind.JackofAllTrades, 4, Type.Action).description("Gain a Silver. Look at the top card of your deck; discard it or put it back. Draw until you have 5 cards in hand. You may trash a card from your hand that is not a Treasure.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(mandarin = new CardImpl.Builder(Cards.Kind.Mandarin, 5, Type.Action).addGold(3).description("Put a card from your hand on top of your deck - When you gain this, put all Treasures you have in play on top of your deck in any order.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(margrave = new CardImpl.Builder(Cards.Kind.Margrave, 5, Type.Action, Type.Attack).addCards(3).addBuys(1).description("Each other player draws a card, then discards down to 3 cards in hand.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(nobleBrigand = new CardImpl.Builder(Cards.Kind.NobleBrigand, 4, Type.Action, Type.Attack).addGold(1).description("Each other player reveals the top 2 cards of their deck, trashes a revealed Silver or Gold you choose, and discards the rest, and gains a Copper if they didn't reveal a Treasure. You gain the trashed cards. - When you buy this, do its attack.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(nomadCamp = new CardImpl.Builder(Cards.Kind.NomadCamp, 4, Type.Action).addBuys(1).addGold(2).description("This is gained onto your deck (instead of to your discard pile).").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(oasis = new CardImpl.Builder(Cards.Kind.Oasis, 3, Type.Action).addCards(1).addActions(1).addGold(1).description("Discard a card.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(oracle = new CardImpl.Builder(Cards.Kind.Oracle, 3, Type.Action, Type.Attack).description("Each player (including you) reveals the top 2 cards of their deck, and discards them or puts them back, your choice (they choose the order). Then, +2 Cards.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(scheme = new CardImpl.Builder(Cards.Kind.Scheme, 3, Type.Action).addCards(1).addActions(1).description("At the start of Clean-up this turn, you may choose an Action card you have in play. If you discard it from play this turn, put it on your deck.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(silkRoad = new CardImpl.Builder(Cards.Kind.SilkRoad, 4, Type.Victory).description("Worth 1 VP for every 4 Victory cards in your deck (round down).").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(spiceMerchant = new CardImpl.Builder(Cards.Kind.SpiceMerchant, 4, Type.Action).hasPlusCoin().trashForced().description("You may trash a Treasure from your hand. If you do, choose one: +2 Cards and +1 Action; or +2 Coin and +1 Buy.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(stables = new CardImpl.Builder(Cards.Kind.Stables, 5, Type.Action).description("You may discard a Treasure. If you do, +3 Cards and +1 Action.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(trader = new CardImpl.Builder(Cards.Kind.Trader, 4, Type.Action, Type.Reaction).trashForced().description("Trash a card from your hand. Gain a Silver per 1 Coin it costs. - When you gain a card, you may reveal this from your hand, to exchange the card for a Silver.").expansion(Expansion.Hinterlands).build());
        actionCardsHinterlands.add(tunnel = new CardImpl.Builder(Cards.Kind.Tunnel, 3, Type.Victory, Type.Reaction).vp(2).description("When you discard this other than during a Clean-up phase, you may reveal it. If you do, gain a Gold.").expansion(Expansion.Hinterlands).build());

        // Dark Ages
        actionCardsDarkAges.add(altar = new CardImpl.Builder(Cards.Kind.Altar, 6, Type.Action).trashForced().description("Trash a card from your hand. Gain a card costing up to 5 coins.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(armory = new CardImpl.Builder(Cards.Kind.Armory, 4, Type.Action).description("Gain a card costing up to 4 coins. Put it on top of your deck.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(banditCamp = new CardImpl.Builder(Cards.Kind.BanditCamp, 5, Type.Action).addActions(2).addCards(1).description("Gain a Spoils from the Spoils pile.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(bandOfMisfits = new CardImpl.Builder(Cards.Kind.BandOfMisfits, 5, Type.Action, Type.Command).description("Play a non-Command Action card from the Supply that costs less than this, leaving it there.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(beggar = new CardImpl.Builder(Cards.Kind.Beggar, 2, Type.Action, Type.Reaction).description("Gain 3 Coppers, putting them into your hand. When another player plays an Attack card, you may discard this. If you do, gain two Silvers, putting one on top of your deck.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(catacombs = new CardImpl.Builder(Cards.Kind.Catacombs, 5, Type.Action).description("Look at the top 3 cards of your deck. Choose one: Put them into your hand; or discard them and +3 Cards. When you trash this, gain a cheaper card.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(count = new CardImpl.Builder(Cards.Kind.Count, 5, Type.Action).hasPlusCoin().description("Choose one: Discard 2 cards; or put a card from your hand on top of your deck; or gain a Copper. Choose one: +3 coins; or trash your hand; or gain a Duchy.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(counterfeit = new CardImpl.Builder(Cards.Kind.Counterfeit, 5, Type.Treasure).addBuys(1).addGold(1).description("When you play this, you may play a Treasure from your hand twice. If you do, trash that Treasure.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(cultist = new CardImpl.Builder(Cards.Kind.Cultist, 5, Type.Action, Type.Attack, Type.Looter).addCards(2).description("Each other player gains a Ruins. You may play a Cultist from your hand. When you trash this, +3 Cards.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(deathCart = new CardImpl.Builder(Cards.Kind.DeathCart, 4, Type.Action, Type.Looter).hasPlusCoin().description("You may trash this or an Action card from your hand, for +(5) Coins. When you gain this, gain 2 Ruins.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(feodum = new CardImpl.Builder(Cards.Kind.Feodum, 4, Type.Victory).description("Worth 1 VP for every 3 Silvers in your deck (round down). When you trash this, gain 3 Silvers.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(forager = new CardImpl.Builder(Cards.Kind.Forager, 3, Type.Action).hasPlusCoin().addActions(1).addBuys(1).trashForced().description("Trash a card from your hand. +1 coin per differently named Treasure in the trash.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(fortress = new CardImpl.Builder(Cards.Kind.Fortress, 4, Type.Action).addCards(1).addActions(2).description("When you trash this, put it into your hand.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(graverobber = new CardImpl.Builder(Cards.Kind.Graverobber, 5, Type.Action).description("Choose one: Gain a card from the trash costing from 3 to 6 coins, putting it on top of your deck; or trash an Action card from your hand and gain a card costing up to 3 more than it.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(hermit = new CardImpl.Builder(Cards.Kind.Hermit, 3, Type.Action).description("Look through your discard pile. You may trash a card from your discard pile or hand that is not a Treasure. Gain a card costing up to 3 coins. When you discard this from play, if you did not buy any cards this turn, trash this and gain a Madman from the Madman pile.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(huntingGrounds = new CardImpl.Builder(Cards.Kind.HuntingGrounds, 6, Type.Action).addCards(4).description("When you trash this, gain a Duchy or 3 Estates.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(ironmonger = new CardImpl.Builder(Cards.Kind.Ironmonger, 4, Type.Action).hasPlusCoin().addCards(1).addActions(1).description("Reveal the top card of your deck; you may discard it. Either way, if it is an Action card, +1 Action; Treasure card, + 1 coin; Victory card, +1 Card.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(junkDealer = new CardImpl.Builder(Cards.Kind.JunkDealer, 5, Type.Action).addCards(1).addActions(1).addGold(1).trashForced().description("Trash a card from your hand.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(marauder = new CardImpl.Builder(Cards.Kind.Marauder, 4, Type.Action, Type.Attack, Type.Looter).description("Gain a Spoils from the Spoils pile. Each other player gains a Ruins.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(marketSquare = new CardImpl.Builder(Cards.Kind.MarketSquare, 3, Type.Action, Type.Reaction).addCards(1).addActions(1).addBuys(1).description("When one of your cards is trashed, you may discard this from your hand. If you do, gain a Gold.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(mystic = new CardImpl.Builder(Cards.Kind.Mystic, 5, Type.Action).addActions(1).addGold(2).description("Name a card. Reveal the top card of your deck. If it’s the named card, put it into your hand.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(pillage = new CardImpl.Builder(Cards.Kind.Pillage, 5, Type.Action, Type.Attack).description("Trash this. If you did, gain 2 Spoils, and each other player with 5 or more cards in hand reveals their hand and discards a card that you choose.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(poorHouse = new CardImpl.Builder(Cards.Kind.PoorHouse, 1, Type.Action).addGold(4).description("Reveal your hand. -1 coin per treasure card in your hand, to a minimum of 0 coins.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(procession = new CardImpl.Builder(Cards.Kind.Procession, 4, Type.Action).description("You may play a non-Duration Action card from your hand twice. Trash it. Gain an Action card costing exactly 1 more than it.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(rats = new CardImpl.Builder(Cards.Kind.Rats, 4, Type.Action).addCards(1).addActions(1).trashForced().description("Gain a Rats. Trash a card from your hand other than a Rats (or reveal a hand of all Rats).").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(rebuild = new CardImpl.Builder(Cards.Kind.Rebuild, 5, Type.Action).addActions(1).description("Name a card. Reveal cards from the top of your deck until you reveal a Victory card that is not the named card. Discard the other cards. Trash the Victory card and gain a Victory card costing up to 3 more than it.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(rogue = new CardImpl.Builder(Cards.Kind.Rogue, 5, Type.Action, Type.Attack).addGold(2).description("If there are any cards in the trash costing from 3 to 6 coins, gain one of them. Otherwise, each other player reveals the top 2 cards of his deck, trashes one of them costing from 3 to 6 coins, and discards the rest.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(sage = new CardImpl.Builder(Cards.Kind.Sage, 3, Type.Action).addActions(1).description("Reveal cards from the top of your deck until you reveal one costing 3 coins or more. Put that card into your hand and discard the rest.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(scavenger = new CardImpl.Builder(Cards.Kind.Scavenger, 4, Type.Action).addGold(2).description("You may put your deck into your discard pile. Look through your discard pile and put one card from it on top of your deck.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(squire = new CardImpl.Builder(Cards.Kind.Squire, 2, Type.Action).addGold(1).description("Choose one: +2 Actions; or +2 Buys; or gain a Silver. - When you trash this, gain an Attack card.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(storeroom = new CardImpl.Builder(Cards.Kind.Storeroom, 3, Type.Action).hasPlusCoin().addBuys(1).description("Discard any number of cards. +1 Card per card discarded. Discard any number of cards. +1 coin per card discarded the second time.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(urchin = new CardImpl.Builder(Cards.Kind.Urchin, 3, Type.Action, Type.Attack).addCards(1).addActions(1).description("Each other player discards down to 4 cards in hand. When you play another attack card with this in play, you may trash this. If you do, gain a Mercenary from the Mercenary pile.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(vagrant = new CardImpl.Builder(Cards.Kind.Vagrant, 2, Type.Action).addCards(1).addActions(1).description("Reveal the top card of your deck. If it's a Curse, Ruins, Shelter, or Victory card, put it into your hand.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(wanderingMinstrel = new CardImpl.Builder(Cards.Kind.WanderingMinstrel, 4, Type.Action).addCards(1).addActions(2).description("Reveal the top 3 cards of your deck. Put the Actions back on top in any order and discard the rest.").expansion(Expansion.DarkAges).build());

        // Guilds
        actionCardsGuilds.add(advisor          = new CardImpl.Builder(Cards.Kind.Advisor, 4, Type.Action).addActions(1).description("Reveal the top 3 cards of your deck. The player to your left chooses one of them. Discard that card and put the rest into your hand.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(soothsayer       = new CardImpl.Builder(Cards.Kind.Soothsayer, 5, Type.Action, Type.Attack).description("Gain a Gold. Each other player gains a Curse, and if they did, draws a card.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(taxman           = new CardImpl.Builder(Cards.Kind.Taxman, 4, Type.Action, Type.Attack).description("You may trash a Treasure from your hand. Each other player with 5 or more cards in hand discards a copy of it (or reveals they can't). Gain a Treasure card onto your deck costing up to (3) Coins more than it.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(plaza            = new CardImpl.Builder(Cards.Kind.Plaza, 4, Type.Action).addCards(1).addActions(2).description("You may discard a Treasure for +1 Coffers.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(candlestickMaker = new CardImpl.Builder(Cards.Kind.CandlestickMaker, 2, Type.Action).addActions(1).addBuys(1).addCoffers(1).expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(baker            = new CardImpl.Builder(Cards.Kind.Baker, 5, Type.Action).addCards(1).addActions(1).addCoffers(1).description("Setup: Each player gets +1 Coffers.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(merchantGuild    = new CardImpl.Builder(Cards.Kind.MerchantGuild, 5, Type.Action).addBuys(1).addGold(1).description("At the end of your Buy phase this turn, +1 Coffers per card you gained in it.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(butcher          = new CardImpl.Builder(Cards.Kind.Butcher, 5, Type.Action).addCoffers(2).description("You may trash a card from your hand. If you do, remove any number of tokens from your Coffers and gain a card, costing up to the cost of the trashed card plus (1) Coin per token removed.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(journeyman       = new CardImpl.Builder(Cards.Kind.Journeyman, 5, Type.Action).description("Name a card. Reveal cards from your deck until you reveal 3 cards without the name. Put those cards into your hand and discard the rest.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(stonemason       = new CardImpl.Builder(Cards.Kind.StoneMason, 2, Type.Action).trashForced().isOverpay().description("Trash a card from your hand. Gain 2 cards each costing less than it. — When you buy this, you may overpay for it. If you do, gain 2 Action cards each costing the amount you overpaid.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(masterpiece      = new CardImpl.Builder(Cards.Kind.Masterpiece, 3, Type.Treasure).addGold(1).isOverpay().description("When you buy this, you may overpay for it. For each (1) Coin you overpaid, gain a Silver.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(doctor           = new CardImpl.Builder(Cards.Kind.Doctor, 3, Type.Action).isOverpay().description("Name a card. Reveal the top 3 cards of your deck. Trash the matches. Put the rest back in any order. — When you buy this, you may overpay for it. For each (1) Coin you overpaid, look at the top card of your deck; trash it, discard it, or put it back.").expansion(Expansion.Guilds).build());
        actionCardsGuilds.add(herald           = new CardImpl.Builder(Cards.Kind.Herald, 4, Type.Action).addCards(1).addActions(1).isOverpay().description("Reveal the top card of your deck. If it's an Action, play it. — When you buy this, you may overpay for it. For each (1) Coin you overpaid, look through your discard pile and put a card from it onto your deck.").expansion(Expansion.Guilds).build());

        // Non-Supply Cards
        nonSupplyCards.add(madman    = new CardImpl.Builder(Cards.Kind.Madman, 0, Type.Action).addActions(2).description("Return this to the Madman pile. If you do, +1 Card per card in your hand. (This is not in the Supply.)").expansion(Expansion.DarkAges).build());
        nonSupplyCards.add(mercenary = new CardImpl.Builder(Cards.Kind.Mercenary, 0, Type.Action, Type.Attack).hasPlusCoin().description("You may trash 2 cards from your hand. If you do, +2 cards, +2 coins, and each other player discards down to 3 cards in hand. (This is not in the Supply.)").expansion(Expansion.DarkAges).build());
        nonSupplyCards.add(spoils    = new CardImpl.Builder(Cards.Kind.Spoils, 0, Type.Treasure).addGold(3).description("When you play this, return it to the Spoils pile. (This is not in the Supply.)").expansion(Expansion.DarkAges).build());

        // Ruins
        ruinsCards.add(abandonedMine     = new CardImpl.Builder(Cards.Kind.AbandonedMine, 0, Type.Action, Type.Ruins).addGold(1).expansion(Expansion.DarkAges).build());
        ruinsCards.add(ruinedLibrary     = new CardImpl.Builder(Cards.Kind.RuinedLibrary, 0, Type.Action, Type.Ruins).addCards(1).expansion(Expansion.DarkAges).build());
        ruinsCards.add(ruinedMarket      = new CardImpl.Builder(Cards.Kind.RuinedMarket, 0, Type.Action, Type.Ruins).addBuys(1).expansion(Expansion.DarkAges).build());
        ruinsCards.add(ruinedVillage     = new CardImpl.Builder(Cards.Kind.RuinedVillage, 0, Type.Action, Type.Ruins).addActions(1).expansion(Expansion.DarkAges).build());
        ruinsCards.add(survivors         = new CardImpl.Builder(Cards.Kind.Survivors, 0, Type.Action, Type.Ruins).description("Look at the top 2 cards of your deck. Discard them or put them back in any order.").expansion(Expansion.DarkAges).build());
        nonKingdomCards.add(virtualRuins = new CardImpl.Builder(Cards.Kind.VirtualRuins, 0, Type.Action, Type.Ruins).pileCreator(new RuinsPileCreator()).expansion(Expansion.DarkAges).build());
        
        // Knights
        knightsCards.add(dameAnna = new CardImpl.Builder(Cards.Kind.DameAnna, 5, Type.Action, Type.Attack, Type.Knight).description("You may trash up to 2 cards from your hand. " + KNIGHTS_TEXT).expansion(Expansion.DarkAges).build());
        knightsCards.add(dameJosephine = new CardImpl.Builder(Cards.Kind.DameJosephine, 5, Type.Action, Type.Attack, Type.Victory, Type.Knight).vp(2).description(KNIGHTS_TEXT).expansion(Expansion.DarkAges).build());
        knightsCards.add(dameMolly = new CardImpl.Builder(Cards.Kind.DameMolly, 5, Type.Action, Type.Attack, Type.Knight).addActions(2).description(KNIGHTS_TEXT).expansion(Expansion.DarkAges).build());
        knightsCards.add(dameNatalie = new CardImpl.Builder(Cards.Kind.DameNatalie, 5, Type.Action, Type.Attack, Type.Knight).description("You may gain a card costing up to 3 coins. " + KNIGHTS_TEXT).expansion(Expansion.DarkAges).build());
        knightsCards.add(dameSylvia = new CardImpl.Builder(Cards.Kind.DameSylvia, 5, Type.Action, Type.Attack, Type.Knight).addGold(2).description(KNIGHTS_TEXT).expansion(Expansion.DarkAges).build());
        knightsCards.add(sirBailey = new CardImpl.Builder(Cards.Kind.SirBailey, 5, Type.Action, Type.Attack, Type.Knight).addActions(1).addCards(1).description(KNIGHTS_TEXT).expansion(Expansion.DarkAges).build());
        knightsCards.add(sirDestry = new CardImpl.Builder(Cards.Kind.SirDestry, 5, Type.Action, Type.Attack, Type.Knight).addCards(2).description(KNIGHTS_TEXT).expansion(Expansion.DarkAges).build());
        knightsCards.add(sirMartin = new CardImpl.Builder(Cards.Kind.SirMartin, 4, Type.Action, Type.Attack, Type.Knight).addBuys(2).description(KNIGHTS_TEXT).expansion(Expansion.DarkAges).build());
        knightsCards.add(sirMichael = new CardImpl.Builder(Cards.Kind.SirMichael, 5, Type.Action, Type.Attack, Type.Knight).description("Each other player discards down to 3 cards in hand. " + KNIGHTS_TEXT).expansion(Expansion.DarkAges).build());
        knightsCards.add(sirVander = new CardImpl.Builder(Cards.Kind.SirVander, 5, Type.Action, Type.Attack, Type.Knight).description(KNIGHTS_TEXT + " When you trash this, gain a Gold.").expansion(Expansion.DarkAges).build());
        actionCardsDarkAges.add(virtualKnight = new CardImpl.Builder(Kind.VirtualKnight, 5, Type.Action, Type.Attack, Type.Knight).pileCreator(new KnightsPileCreator()).expansion(Expansion.DarkAges).build());

        // Shelters
        nonSupplyCards.add(necropolis = new CardImpl.Builder(Cards.Kind.Necropolis, 1, Type.Action, Type.Shelter).addActions(2).expansion(Expansion.DarkAges).build());
        nonSupplyCards.add(overgrownEstate = new CardImpl.Builder(Cards.Kind.OvergrownEstate, 1, Type.Victory, Type.Shelter).description("When you trash this, +1 Card").expansion(Expansion.DarkAges).build());
        nonSupplyCards.add(hovel = new CardImpl.Builder(Cards.Kind.Hovel, 1, Type.Reaction, Type.Shelter).description("When you buy a Victory card, you may trash this from your hand").expansion(Expansion.DarkAges).build());

        // Adventures
        actionCardsAdventures.add(amulet          = new CardImpl.Builder(Cards.Kind.Amulet, 3, Type.Action, Type.Duration).hasPlusCoin().description("Now and at the start of your next turn, choose one: +1 Coin; or trash a card from your hand; or gain a Silver.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(artificer       = new CardImpl.Builder(Cards.Kind.Artificer, 5, Type.Action).addCards(1).addActions(1).addGold(1).description("Discard any number of cards. You may gain a card onto your deck costing exactly 1 Coin per card discarded.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(bridgeTroll     = new CardImpl.Builder(Cards.Kind.BridgeTroll, 5, Type.Action, Type.Attack, Type.Duration).addBuysNextTurn(1).addBuys(1).description("Each other player takes their -1 Coin token. - While you have this in play, cards cost 1 Coin less on your turns.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(caravanGuard    = new CardImpl.Builder(Cards.Kind.CaravanGuard, 3, Type.Action, Type.Duration, Type.Reaction).addGoldNextTurn(1).addCards(1).addActions(1).description("When another player plays an Attack card, you may play this from your hand. (+1 Action has no effect if it's not your turn.)").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(coinOfTheRealm  = new CardImpl.Builder(Cards.Kind.CoinOfTheRealm, 2, Type.Treasure, Type.Reserve).addGold(1).callWhenActionResolved().description("Put this on your Tavern mat. - After you play an Action card, you may call this, for +2 Actions.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(distantLands    = new CardImpl.Builder(Cards.Kind.DistantLands, 5, Type.Action, Type.Reserve, Type.Victory).description("Put this on your Tavern mat. ~ Worth 4 VP if on your Tavern mat at the end of the game (otherwise worth 0 VP).").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(dungeon         = new CardImpl.Builder(Cards.Kind.Dungeon, 3, Type.Action, Type.Duration).addCards(2).addActions(1).description("Discard 2 cards. At the start of your next turn: +2 Cards, then discard 2 cards.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(duplicate       = new CardImpl.Builder(Cards.Kind.Duplicate, 4, Type.Action, Type.Reserve).callWhenGainCard(6).description("Put this on your Tavern mat. ~ When you gain a card costing up to 6 Coins, you may call this, to gain a copy of that card.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(gear            = new CardImpl.Builder(Cards.Kind.Gear, 3, Type.Action, Type.Duration).addCards(2).description("Set aside up to 2 cards from your hand face down. At the start of your next turn, put them into your hand.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(giant           = new CardImpl.Builder(Cards.Kind.Giant, 5, Type.Action, Type.Attack).hasPlusCoin().description("Turn your Journey token over (it starts face up). If it's face down, +1 Coin. If it's face up, +5 Coins, and each other player reveals the top card of his deck, trashes it if it costs from 3 to 6 Coins, and otherwise discards it and gains a Curse.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(guide           = new CardImpl.Builder(Cards.Kind.Guide, 3, Type.Action, Type.Reserve).callWhenTurnStarts().addCards(1).addActions(1).description("Put this on your Tavern mat. ~ At the start of your turn, you may call this, to discard your hand and draw 5 cards.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(hauntedWoods    = new CardImpl.Builder(Cards.Kind.HauntedWoods, 5, Type.Action, Type.Attack, Type.Duration).addCardsNextTurn(3).description("Until your next turn, when any other player buys a card, he puts his hand on top of his deck in any order.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(hireling        = new CardImpl.Builder(Cards.Kind.Hireling, 6, Type.Action, Type.Duration).description("At the start of each of your turns for the rest of the game: +1 Card. (This stays in play.)").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(lostCity        = new CardImpl.Builder(Cards.Kind.LostCity, 5, Type.Action).addCards(2).addActions(2).description("When you gain this, each other player draws a card.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(magpie          = new CardImpl.Builder(Cards.Kind.Magpie, 4, Type.Action).addCards(1).addActions(1).description("Reveal the top card of your deck. If it's a Treasure, put it into your hand. If it's an Action or Victory card, gain a Magpie.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(messenger       = new CardImpl.Builder(Cards.Kind.Messenger, 4, Type.Action).addGold(2).addBuys(1).description("You may put your deck into your discard pile. ~ When this is your first buy in a turn, gain a card costing up to 4 Coins, and each other player gains a copy of it.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(miser           = new CardImpl.Builder(Cards.Kind.Miser, 4, Type.Action).hasPlusCoin().description("Choose one: Put a Copper from your hand onto your Tavern mat; or +1 Coin per Copper on your Tavern mat.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(page            = new CardImpl.Builder(Cards.Kind.Page, 2, Type.Action, Type.Traveller).addCards(1).addActions(1).description("When you discard this from play, you may exchange it for a Treasure Hunter.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(peasant         = new CardImpl.Builder(Cards.Kind.Peasant, 2, Type.Action, Type.Traveller).addGold(1).addBuys(1).description("When you discard this from play, you may exchange it for a Soldier.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(port            = new CardImpl.Builder(Cards.Kind.Port, 4, Type.Action).addCards(1).addActions(2).description("When you buy this, gain another Port.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(ranger          = new CardImpl.Builder(Cards.Kind.Ranger, 4, Type.Action).addBuys(1).description("Turn your Journey token over (it starts face up). If it's face up, +5 Cards.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(ratcatcher      = new CardImpl.Builder(Cards.Kind.Ratcatcher, 2, Type.Action, Type.Reserve).callWhenTurnStarts().addCards(1).addActions(1).description("Put this on your Tavern mat. ~ At the start of your turn, you may call this, to trash a card from your hand.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(raze            = new CardImpl.Builder(Cards.Kind.Raze, 2, Type.Action).addActions(1).description("Trash this or a card from your hand. Look at a number of cards from the top of your deck equal to the cost in Coins of the trashed card. Put one into your hand and discard the rest.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(relic           = new CardImpl.Builder(Cards.Kind.Relic, 5, Type.Treasure, Type.Attack).addGold(2).description("Each other player puts their -1 Card token on their deck.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(royalCarriage   = new CardImpl.Builder(Cards.Kind.RoyalCarriage, 5, Type.Action, Type.Reserve).callWhenActionResolved(true).addActions(1).description("Put this on your Tavern mat. - After you play an Action card, if it's still in play, you may call this, to replay that Action.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(storyteller     = new CardImpl.Builder(Cards.Kind.Storyteller, 5, Type.Action).addGold(1).addActions(1).description("Play up to 3 Treasures from your hand. Pay all of your Coins; +1 Card per Coin paid.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(swampHag        = new CardImpl.Builder(Cards.Kind.SwampHag, 5, Type.Action, Type.Attack, Type.Duration).addGoldNextTurn(3).description("Until your next turn, when any other player buys a card, he gains a Curse.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(transmogrify    = new CardImpl.Builder(Cards.Kind.Transmogrify, 4, Type.Action, Type.Reserve).callWhenTurnStarts().addActions(1).description("Put this on your Tavern mat. ~ At the start of your turn, you may call this, to trash a card from your hand, gain a card costing up to 1 Coin more than it, and put that card into your hand.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(treasureTrove   = new CardImpl.Builder(Cards.Kind.TreasureTrove, 5, Type.Treasure).addGold(2).description("Gain a Gold and a Copper.").expansion(Expansion.Adventures).build());
        actionCardsAdventures.add(wineMerchant    = new CardImpl.Builder(Cards.Kind.WineMerchant, 5, Type.Action, Type.Reserve).addGold(4).addBuys(1).description("Put this on your Tavern mat. ~ At the end of your Buy phase, if you have at least 2 Coins unspent, you may discard this from your Tavern mat.").expansion(Expansion.Adventures).build());
        
        // events
        eventCardsAdventures.add(alms              = new CardImpl.Builder(Cards.Kind.Alms            , 0, Type.Event).description("Once per turn: If you have no Treasures in play, gain a card costing up to 4 Coins.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(ball              = new CardImpl.Builder(Cards.Kind.Ball            , 5, Type.Event).description("Take your -1 Coin token. Gain 2 cards each costing up to 4 Coins.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(bonfire           = new CardImpl.Builder(Cards.Kind.Bonfire         , 3, Type.Event).description("Trash up to 2 cards you have in play.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(borrow            = new CardImpl.Builder(Cards.Kind.Borrow          , 0, Type.Event).addBuys(1).description("Once per turn: If your -1 Card token isn't on your deck, put it there and +1 Coin.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(expedition        = new CardImpl.Builder(Cards.Kind.Expedition      , 3, Type.Event).description("Draw 2 extra cards for your next hand.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(ferry             = new CardImpl.Builder(Cards.Kind.Ferry           , 3, Type.Event).description("Move your -2 cost token to an Action Supply pile (cards from that pile cost 2 Coins less on your turns, but not less than 0 Coins).").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(inheritance       = new CardImpl.Builder(Cards.Kind.Inheritance     , 7, Type.Event).description("Once per game: Set aside a non-Command Action card from the Supply costing up to Coins. Move your Estate token to it (During your turns, Estates are also Actions with \"Play the card with your Estate token, leaving it there.\").").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(lostArts          = new CardImpl.Builder(Cards.Kind.LostArts        , 6, Type.Event).description("Move your +1 Action token to an Action Supply pile (when you play a card from that pile, you first get +1 Action).").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(mission           = new CardImpl.Builder(Cards.Kind.Mission         , 4, Type.Event).description("Once per turn: If the previous turn wasn't yours, take another turn after this one, in which you can't buy cards.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(pathfinding       = new CardImpl.Builder(Cards.Kind.Pathfinding     , 8, Type.Event).description("Move your +1 Card token to an Action Supply pile (when you play a card from that pile, you first get +1 Card).").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(pilgrimage        = new CardImpl.Builder(Cards.Kind.Pilgrimage      , 4, Type.Event).description("Once per turn: Turn your Journey token over (it starts face up); then if it's face up, choose up to 3 differently named cards you have in play and gain a copy of each.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(plan              = new CardImpl.Builder(Cards.Kind.Plan            , 3, Type.Event).description("Move your Trashing token to an Action Supply pile (when you buy a card from that pile, you may trash a card from your hand.)").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(quest             = new CardImpl.Builder(Cards.Kind.Quest           , 0, Type.Event).description("You may discard an Attack, two Curses, or six cards. If you do, gain a Gold.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(raid              = new CardImpl.Builder(Cards.Kind.Raid            , 5, Type.Event).description("Gain a Silver per Silver you have in play. Each other player puts his -1 Card token on his deck.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(save              = new CardImpl.Builder(Cards.Kind.Save            , 1, Type.Event).addBuys(1).description("Once per turn: Set aside a card from your hand, and put it into your hand at end of turn (after drawing).").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(scoutingParty     = new CardImpl.Builder(Cards.Kind.ScoutingParty   , 2, Type.Event).addBuys(1).description("Look at the top 5 cards of your deck. Discard 3 and put the rest back in any order.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(seaway            = new CardImpl.Builder(Cards.Kind.Seaway          , 5, Type.Event).description("Gain an Action card costing up to 4 Coins. Move your +1 Buy token to its pile (when you play a card from that pile, you first get +1 Buy).").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(trade             = new CardImpl.Builder(Cards.Kind.Trade           , 5, Type.Event).description("Trash up to 2 cards from your hand. Gain a Silver per card you trashed.").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(training          = new CardImpl.Builder(Cards.Kind.Training        , 6, Type.Event).description("Move your +1 Coin token to an Action Supply pile (when you play a card from that pile, you first get +1 Coin).").expansion(Expansion.Adventures).build());
        eventCardsAdventures.add(travellingFair    = new CardImpl.Builder(Cards.Kind.TravellingFair  , 2, Type.Event).addBuys(2).description("When you gain a card this turn, you may put it on top of your deck.").expansion(Expansion.Adventures).build());
        
        // Travellers
        nonSupplyCards.add(champion        = new CardImpl.Builder(Cards.Kind.Champion, 6, Type.Action, Type.Duration).addActions(1).description("For the rest of the game, when another player plays an Attack, it doesn't affect you, and when you play an Action, +1 Action. (This stays in play. This is not in the Supply.)").expansion(Expansion.Adventures).build());
        nonSupplyCards.add(disciple        = new CardImpl.Builder(Cards.Kind.Disciple        , 5, Type.Action, Type.Traveller).description("You may play an Action card from your hand twice. Gain a copy of it. ~ When you discard this from play, you may exchange it for a Teacher. (This is not in the Supply.)").expansion(Expansion.Adventures).build());
        nonSupplyCards.add(fugitive        = new CardImpl.Builder(Cards.Kind.Fugitive        , 4, Type.Action, Type.Traveller).addCards(2).addActions(1).description("Discard a card. ~ When you discard this from play, you may exchange it for a Disciple. (This is not in the Supply.)").expansion(Expansion.Adventures).build());
        nonSupplyCards.add(hero            = new CardImpl.Builder(Cards.Kind.Hero            , 5, Type.Action, Type.Traveller).addGold(2).description("Gain a Treasure - When you discard this from play, you may exchange it for a Champion. (This is not in the Supply.)").expansion(Expansion.Adventures).build());
        nonSupplyCards.add(soldier         = new CardImpl.Builder(Cards.Kind.Soldier         , 3, Type.Action, Type.Attack, Type.Traveller).addGold(2).description("+1 Coin per other Attack you have in play. Each other player with 4 or more cards in hand discards a card. ~ When you discard this from play, you may exchange it for a Fugitive. (This is not in the Supply.)").expansion(Expansion.Adventures).build());
        nonSupplyCards.add(teacher         = new CardImpl.Builder(Cards.Kind.Teacher , 6, Type.Action, Type.Reserve).hasPlusCoin().callWhenTurnStarts().description("Put this on your Tavern mat. ~ At the start of your turn, you may call this, to move your +1 Card, +1 Action, +1 Buy, or +1 Coin token to an Action Supply pile you have no tokens on (when you play a card from that pile, you first get that bonus). (This is not in the Supply.)").expansion(Expansion.Adventures).build());
        nonSupplyCards.add(treasureHunter  = new CardImpl.Builder(Cards.Kind.TreasureHunter  , 3, Type.Action, Type.Traveller).addGold(1).addActions(1).description("Gain a Silver per card the player to your right gained in his last turn. ~ When you discard this from play, you may exchange it for a Warrior. (This is not in the Supply.)").expansion(Expansion.Adventures).build());
        nonSupplyCards.add(warrior         = new CardImpl.Builder(Cards.Kind.Warrior         , 4, Type.Action, Type.Attack, Type.Traveller).addCards(2).description("For each Traveller you have in play (including this), each other player discards the top card of his deck and trashes it if it costs 3 Coins or 4 Coins. ~ When you discard this from play, you may exchange it for a Hero. (This is not in the Supply.)").expansion(Expansion.Adventures).build());

        // Empires

        // split pile cards
        variablePileCards.add(catapult            = new CardImpl.Builder(Cards.Kind.Catapult, 3, Type.Action, Type.Attack).addGold(1).trashForced().description("Trash a card from your hand. If it costs (3) Coins or more, each other player gains a Curse. If it's a Treasure, each other player discards down to 3 cards in hand.").expansion(Expansion.Empires).build());
        variablePileCards.add(rocks               = new CardImpl.Builder(Cards.Kind.Rocks, 4, Type.Treasure).addGold(1).description("- When you gain or trash this: if it's your Buy phase, gain a Silver onto your deck, otherwise gain a Silver to your hand.").expansion(Expansion.Empires).build());
        
		variablePileCards.add(encampment          = new CardImpl.Builder(Cards.Kind.Encampment, 2, Type.Action).addActions(2).addCards(2).description("You may reveal a Gold or Plunder from your hand. If you do not, set this aside, and return it to the Supply at the start of Clean-up.").expansion(Expansion.Empires).build());
        variablePileCards.add(plunder             = new CardImpl.Builder(Cards.Kind.Plunder, 5, Type.Treasure).addGold(2).addVictoryTokens(1).description("").expansion(Expansion.Empires).build());
        
		variablePileCards.add(gladiator           = new CardImpl.Builder(Cards.Kind.Gladiator, 3, Type.Action).addGold(2).description("Reveal a card from your hand. The player to your left may reveal a copy from their hand. If they do not, +(1) Coin and trash a Gladiator from the Supply.").expansion(Expansion.Empires).build());
        variablePileCards.add(fortune             = new CardImpl.Builder(Cards.Kind.Fortune, 8, Type.Treasure).addBuys(1).costDebt(8).description("Double your Coins if you haven't yet this turn. - When you gain this, gain a Gold per Gladiator you have in play.").expansion(Expansion.Empires).build());
        
		variablePileCards.add(patrician           = new CardImpl.Builder(Cards.Kind.Patrician, 2, Type.Action).addActions(1).addCards(1).description("Reveal the top card of your deck. If it costs (5) Coins or more, put it into your hand.").expansion(Expansion.Empires).build());
        variablePileCards.add(emporium            = new CardImpl.Builder(Cards.Kind.Emporium, 5, Type.Action).addActions(1).addCards(1).addGold(1).description("When you gain this, if you have at least 5 Action cards in play, +2 Victory tokens.").expansion(Expansion.Empires).build());
        
		variablePileCards.add(settlers            = new CardImpl.Builder(Cards.Kind.Settlers, 2, Type.Action).addCards(1).addActions(1).description("Look through your discard pile. You may reveal a Copper from it and put it into your hand.").expansion(Expansion.Empires).build());
        variablePileCards.add(bustlingVillage     = new CardImpl.Builder(Cards.Kind.BustlingVillage, 5, Type.Action).addCards(1).addActions(3).description("Look through your discard pile. You may reveal a Settlers from it and put it into your hand.").expansion(Expansion.Empires).build());
		
        // castles
        castleCards.add(humbleCastle        = new CardImpl.Builder(Cards.Kind.HumbleCastle, 3, Type.Treasure, Type.Victory, Type.Castle).addGold(1).description("Worth 1 VP per Castle you have.").expansion(Expansion.Empires).build());
        castleCards.add(crumblingCastle     = new CardImpl.Builder(Cards.Kind.CrumblingCastle, 4, Type.Victory, Type.Castle).vp(1).description("When you gain or trash this, +1 Victory token and gain a Silver.").expansion(Expansion.Empires).build());
        castleCards.add(smallCastle         = new CardImpl.Builder(Cards.Kind.SmallCastle, 5, Type.Action, Type.Victory, Type.Castle).vp(2).trashForced().description("Trash this or a Castle from your hand. If you do, gain a Castle.").expansion(Expansion.Empires).build());
        castleCards.add(hauntedCastle       = new CardImpl.Builder(Cards.Kind.HauntedCastle, 6, Type.Victory, Type.Castle).vp(2).description("When you gain this during your turn, gain a Gold, and each other player with 5 or more cards in hand puts 2 cards from their hand onto their deck.").expansion(Expansion.Empires).build());
        castleCards.add(opulentCastle       = new CardImpl.Builder(Cards.Kind.OpulentCastle, 7, Type.Action, Type.Victory, Type.Castle).hasPlusCoin().vp(3).description("Discard any number of Victory cards, revealed. +(2) Coins per card discarded.").expansion(Expansion.Empires).build());
        castleCards.add(sprawlingCastle     = new CardImpl.Builder(Cards.Kind.SprawlingCastle, 8, Type.Victory, Type.Castle).vp(4).description("When you gain this, gain a Duchy or 3 Estates.").expansion(Expansion.Empires).build());
        castleCards.add(grandCastle         = new CardImpl.Builder(Cards.Kind.GrandCastle, 9, Type.Victory, Type.Castle).vp(5).description("When you gain this, reveal your hand. +1 Victory token per Victory card in your hand and/or in play.").expansion(Expansion.Empires).build());
        castleCards.add(kingsCastle         = new CardImpl.Builder(Cards.Kind.KingsCastle, 10, Type.Victory, Type.Castle).description("Worth 2 VP per Castle you have.").expansion(Expansion.Empires).build());

        actionCardsEmpires.add(archive                        = new CardImpl.Builder(Cards.Kind.Archive, 5, Type.Action, Type.Duration).addActions(1).description("Set aside the top 3 cards of your deck face down (you may look at them). Now and at the start of your next two turns, put one into your hand.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(capital                        = new CardImpl.Builder(Cards.Kind.Capital, 5, Type.Treasure).addBuys(1).addGold(6).description("When you discard this from play, take 6 Debt tokens, and then you may pay off Debt tokens.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(virtualCastle                  = new CardImpl.Builder(Cards.Kind.Castles, 3, Type.Victory, Type.Castle).pileCreator(new CastlesPileCreator()).description("Sort the Castle pile by cost, putting the more expensive Castles on the bottom. For a 2-player game, use only one of each Castle. Only the top card of the pile can be gained or bought.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(virtualCatapultRocks           = new CardImpl.Builder(Cards.Kind.CatapultRocks, 3, Type.Action, Type.Attack).pileCreator(new SplitPileCreator(catapult, rocks)).description("This pile starts the game with 5 copies of Catapult on top, then 5 copies of Rocks. Only the top card of the pile can be gained or bought.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(chariotRace                    = new CardImpl.Builder(Cards.Kind.ChariotRace, 3, Type.Action).hasPlusCoin().addActions(1).description("Reveal the top card of your deck and put it into your hand. The player to your left reveals the top card of their deck. If your card costs more, +(1) Coin and +1 Victory token.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(charm                          = new CardImpl.Builder(Cards.Kind.Charm, 5, Type.Treasure).hasPlusCoin().description("Choose one: +1 Buy and +2 Coins; or the next time you buy a card this turn, you may also gain a differently named card with the same cost.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(cityQuarter                    = new CardImpl.Builder(Cards.Kind.CityQuarter, 0, Type.Action).addActions(2).costDebt(8).description("Reveal your hand. +1 Card per Action card revealed.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(crown                          = new CardImpl.Builder(Cards.Kind.Crown, 5, Type.Action, Type.Treasure).description("If it's your Action phase, you may play an Action from your hand twice. If it's your Buy phase, you may play a Treasure from your hand twice.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(virtualEncampmentPlunder       = new CardImpl.Builder(Cards.Kind.EncampmentPlunder, 2, Type.Action).pileCreator(new SplitPileCreator(encampment, plunder)).description("This pile starts the game with 5 copies of Encampment on top, then 5 copies of Plunder. Only the top card of the pile can be gained or bought.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(enchantress                    = new CardImpl.Builder(Cards.Kind.Enchantress, 3, Type.Action, Type.Attack, Type.Duration).addCardsNextTurn(2).description("Until your next turn, the first time each other player plays an Action card on their turn, they get +1 Card and +1 Action instead of following its instructions.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(engineer                       = new CardImpl.Builder(Cards.Kind.Engineer, 0, Type.Action).costDebt(4).description("Gain a card costing up to (4) Coins. You may trash this. If you do, gain a card costing up to (4) Coins.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(farmersMarket                  = new CardImpl.Builder(Cards.Kind.FarmersMarket, 3, Type.Action, Type.Gathering).hasPlusCoin().addBuys(1).description("If there are 4 Victory tokens or more on the Farmers' Market pile, take them and trash this. Otherwise, add 1 Victory token to the pile and then +1 Coin per 1 Victory token on the pile.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(forum                          = new CardImpl.Builder(Cards.Kind.Forum, 5, Type.Action).addActions(1).addCards(3).description("Discard 2 cards. ~ When you buy this, +1 Buy.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(virtualGladiatorFortune        = new CardImpl.Builder(Cards.Kind.GladiatorFortune, 3, Type.Action).pileCreator(new SplitPileCreator(gladiator, fortune)).description("This pile starts the game with 5 copies of Gladiator on top, then 5 copies of Fortune. Only the top card of the pile can be gained or bought.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(groundskeeper                  = new CardImpl.Builder(Cards.Kind.Groundskeeper, 5, Type.Action).addCards(1).addActions(1).description("- While you have this in play, when you gain a Victory card, +1 Victory token.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(legionary                      = new CardImpl.Builder(Cards.Kind.Legionary, 5, Type.Action, Type.Attack).addGold(3).description("You may reveal a Gold from your hand. If you do, each other player discards down to 2 cards in hand, then draws a card.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(overlord                       = new CardImpl.Builder(Cards.Kind.Overlord, 0, Type.Action, Type.Command).costDebt(8).description("Play a non-Command Action card from the Supply costing up to (5) Coins, leaving it there.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(virtualPatricianEmporium       = new CardImpl.Builder(Cards.Kind.PatricianEmporium, 2, Type.Action).pileCreator(new SplitPileCreator(patrician, emporium)).description("This pile starts the game with 5 copies of Patrician on top, then 5 copies of Emporium. Only the top card of the pile can be gained or bought.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(royalBlacksmith                = new CardImpl.Builder(Cards.Kind.RoyalBlacksmith, 0, Type.Action).addCards(5).costDebt(8).description("Reveal your hand; discard the Coppers.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(sacrifice                      = new CardImpl.Builder(Cards.Kind.Sacrifice, 4, Type.Action).hasPlusCoin().trashForced().description("Trash a card from your hand. If it's an... Action card, +2 Cards and +2 Actions; Treasure card, +2 Coins; Victory card, +2 Victory tokens").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(virtualSettlersBustlingVillage = new CardImpl.Builder(Cards.Kind.SettlersBustlingVillage, 2, Type.Action).pileCreator(new SplitPileCreator(settlers, bustlingVillage)).description("This pile starts the game with 5 copies of Settlers on top, then 5 copies of Bustling Village. Only the top card of the pile can be gained or bought.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(temple                         = new CardImpl.Builder(Cards.Kind.Temple, 4, Type.Action, Type.Gathering).addVictoryTokens(1).trashForced().description("Trash from 1 to 3 differently named cards from your hand. Add 1 Victory token to the Temple pile. - When you gain this, take the Victory tokens from the Temple pile.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(villa                          = new CardImpl.Builder(Cards.Kind.Villa, 4, Type.Action).addActions(2).addBuys(1).addGold(1).description("When you gain this, put it into your hand, +1 Action, and if it's your Buy phase return to your Action phase.").expansion(Expansion.Empires).build());
        actionCardsEmpires.add(wildHunt                       = new CardImpl.Builder(Cards.Kind.WildHunt, 5, Type.Action, Type.Gathering).description("Choose one: +3 Cards and add 1 Victory token to the Wild Hunt pile; or gain an Estate, and if you do, take the Victory tokens from the pile.").expansion(Expansion.Empires).build());

        // events
        eventCardsEmpires.add(advance          = new CardImpl.Builder(Cards.Kind.Advance, 0, Type.Event).description("You may trash an Action card from your hand. If you do, gain an Action card costing up to (6) Coins.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(annex            = new CardImpl.Builder(Cards.Kind.Annex, 0, Type.Event).costDebt(8).description("Look through your discard pile. Shuffle all but up to 5 cards from it into your deck. Gain a Duchy.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(banquet          = new CardImpl.Builder(Cards.Kind.Banquet, 3, Type.Event).description("Gain 2 Coppers and a non-Victory card costing up to (5) Coins.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(conquest         = new CardImpl.Builder(Cards.Kind.Conquest, 6, Type.Event).description("Gain 2 Silvers. +1 Victory token per Silver you've gained this turn.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(delve            = new CardImpl.Builder(Cards.Kind.Delve, 2, Type.Event).addBuys(1).description("Gain a Silver.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(dominate         = new CardImpl.Builder(Cards.Kind.Dominate, 14, Type.Event).description("Gain a Province. If you do, +9 Victory tokens.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(donate           = new CardImpl.Builder(Cards.Kind.Donate, 0, Type.Event).costDebt(8).description("At end of turn, put all cards from your deck and discard pile into your hand, trash any number, shuffle your hand into your deck, then draw 5 cards.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(ritual           = new CardImpl.Builder(Cards.Kind.Ritual, 4, Type.Event).trashForced().description("Gain a Curse. If you do, trash a card from your hand. +1VP per (1) Coin it cost.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(saltTheEarth     = new CardImpl.Builder(Cards.Kind.SaltTheEarth, 4, Type.Event).addVictoryTokens(1).description("Trash a Victory card from the Supply.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(tax              = new CardImpl.Builder(Cards.Kind.Tax, 2, Type.Event).description("Add 2 Debt Tokens to a Supply pile. ~ Setup: Add 1 Debt Token to each Supply pile. When a player buys a card, they take the Debt Tokens from its pile.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(triumph          = new CardImpl.Builder(Cards.Kind.Triumph, 0, Type.Event).costDebt(5).description("Gain an Estate. If you did, +1 Victory token per card you've gained this turn.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(wedding          = new CardImpl.Builder(Cards.Kind.Wedding, 4, Type.Event).costDebt(3).addVictoryTokens(1).description("Gain a Gold.").expansion(Expansion.Empires).build());
        eventCardsEmpires.add(windfall         = new CardImpl.Builder(Cards.Kind.Windfall, 5, Type.Event).description("If your deck and discard pile are empty, gain 3 Golds.").expansion(Expansion.Empires).build());
        
        // landmarks
        landmarkCardsEmpires.add(aqueduct      = new CardImpl.Builder(Cards.Kind.Aqueduct, Type.Landmark).description("When you gain a Treasure, move 1 Victory token from its pile to this. When you gain a Victory card, take the Victory tokens from this. ~ Setup: Put 8 Victory tokens on the Silver and Gold piles.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(arena         = new CardImpl.Builder(Cards.Kind.Arena, Type.Landmark).description("At the start of your Buy phase, you may discard an Action card. If you do, take 2 Victory tokens from here. ~ Setup: Put 6 Victory tokens here per player.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(banditFort    = new CardImpl.Builder(Cards.Kind.BanditFort, Type.Landmark).description("When scoring, -2 VP for each Silver and each Gold you have.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(basilica      = new CardImpl.Builder(Cards.Kind.Basilica, Type.Landmark).description("When you buy a card, if you have (2) Coins or more left, take 2 Victory tokens from here. ~ Setup: Put 6 Victory tokens here per player.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(baths         = new CardImpl.Builder(Cards.Kind.Baths, Type.Landmark).description("When you end your turn without having gained a card, take 2 Victory tokens from here. ~ Setup: Put 6 Victory tokens here per player.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(battlefield   = new CardImpl.Builder(Cards.Kind.Battlefield, Type.Landmark).description("When you gain a Victory card, take 2 Victory tokens from here. — Setup: Put 6 Victory tokens here per player.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(colonnade     = new CardImpl.Builder(Cards.Kind.Colonnade, Type.Landmark).description("When you buy an Action card, if you have a copy of it in play, take 2 Victory tokens from here. ~ Setup: Put 6 Victory tokens here per player.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(defiledShrine = new CardImpl.Builder(Cards.Kind.DefiledShrine, Type.Landmark).description("When you gain an Action, move 1 Victory token from its pile to this. When you buy a Curse, take the Victory tokens from this. ~ Setup: Put 2 Victory tokens on each non-Gathering Action Supply pile.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(fountain      = new CardImpl.Builder(Cards.Kind.Fountain, Type.Landmark).description("When scoring, 15 VP if you have at least 10 Coppers.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(keep          = new CardImpl.Builder(Cards.Kind.Keep, Type.Landmark).description("When scoring, 5 VP per differently named Treasure you have, that you have more copies of than each other player, or tied for most.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(labyrinth     = new CardImpl.Builder(Cards.Kind.Labyrinth, Type.Landmark).description("When you gain a 2nd card in one of your turns, take 2 Victory tokens from here. ~ Setup: Put 6 Victory tokens here per player.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(mountainPass  = new CardImpl.Builder(Cards.Kind.MountainPass, Type.Landmark).description("When you are the first player to gain a Province, at end of turn, each player bids once, up to 40 Debt tokens, ending with you. High bidder gets +8 Victory tokens and takes the Debt tokens they bid.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(museum        = new CardImpl.Builder(Cards.Kind.Museum, Type.Landmark).description("When scoring, 2 VP per differently named card you have.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(obelisk       = new CardImpl.Builder(Cards.Kind.Obelisk, Type.Landmark).description("When scoring, 2 VP per card you have from the chosen pile. ~ Setup: Choose a random Action Supply pile.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(orchard       = new CardImpl.Builder(Cards.Kind.Orchard, Type.Landmark).description("When scoring, 4 VP per differently named Action card you have 3 or more copies of.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(palace        = new CardImpl.Builder(Cards.Kind.Palace, Type.Landmark).description("When scoring, 3 VP per set you have of Copper - Silver - Gold.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(tomb          = new CardImpl.Builder(Cards.Kind.Tomb, Type.Landmark).description("When you trash a card, +1VP.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(tower         = new CardImpl.Builder(Cards.Kind.Tower, Type.Landmark).description("When scoring, 1 VP per non-Victory card you have from an empty Supply pile.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(triumphalArch = new CardImpl.Builder(Cards.Kind.TriumphalArch, Type.Landmark).description("When scoring, 3 VP per copy you have of the 2nd most common Action card among your cards (if it’s a tie, count either).").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(wall          = new CardImpl.Builder(Cards.Kind.Wall, Type.Landmark).description("When scoring, -1VP per card you have after the first 15.").expansion(Expansion.Empires).build());
        landmarkCardsEmpires.add(wolfDen       = new CardImpl.Builder(Cards.Kind.WolfDen, Type.Landmark).description("When scoring, -3 VP per card you have exactly one copy of.").expansion(Expansion.Empires).build());
        
        // Nocturne
        nonSupplyCards.add(cursedGold     = new CardImpl.Builder(Cards.Kind.CursedGold, 4, Type.Treasure, Type.Heirloom).addGold(3).description("Gain a Curse.").expansion(Expansion.Nocturne).build());
        nonSupplyCards.add(hauntedMirror  = new CardImpl.Builder(Cards.Kind.HauntedMirror, 0, Type.Treasure, Type.Heirloom).addGold(1).description("- When you trash this, you may discard an Action card, to gain a Ghost.").expansion(Expansion.Nocturne).build());
        nonSupplyCards.add(goat           = new CardImpl.Builder(Cards.Kind.Goat, 2, Type.Treasure, Type.Heirloom).addGold(1).description("You may trash a card from your hand.").expansion(Expansion.Nocturne).build());
        nonSupplyCards.add(luckyCoin      = new CardImpl.Builder(Cards.Kind.LuckyCoin, 4, Type.Treasure, Type.Heirloom).addGold(1).description("Gain a Silver.").expansion(Expansion.Nocturne).build());
        nonSupplyCards.add(magicLamp      = new CardImpl.Builder(Cards.Kind.MagicLamp, 0, Type.Treasure, Type.Heirloom).addGold(1).description("If there are at least 6 cards that you have exactly 1 copy of in play (counting this), trash this. If you do, gain 3 Wishes.").expansion(Expansion.Nocturne).build());
        nonSupplyCards.add(pasture        = new CardImpl.Builder(Cards.Kind.Pasture, 2, Type.Treasure, Type.Victory, Type.Heirloom).addGold(1).description("Worth 1 VP per Estate you have.").expansion(Expansion.Nocturne).build());
        nonSupplyCards.add(pouch          = new CardImpl.Builder(Cards.Kind.Pouch, 2, Type.Treasure, Type.Heirloom).addGold(1).addBuys(1).description("").expansion(Expansion.Nocturne).build());
        
        stateCards.add(lostInTheWoods = new CardImpl.Builder(Cards.Kind.LostInTheWoods, Type.State).description("At the start of your turn, you may discard a card to receive a Boon.").expansion(Expansion.Nocturne).build());
        
        actionCardsNocturne.add(bard           = new CardImpl.Builder(Cards.Kind.Bard, 4, Type.Action, Type.Fate).addGold(2).description("Receive a Boon.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(blessedVillage = new CardImpl.Builder(Cards.Kind.BlessedVillage, 4, Type.Action, Type.Fate).addActions(2).addCards(1).description("When you gain this, take a Boon. Receive it now or at the start of your next turn.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(cemetery       = new CardImpl.Builder(Cards.Kind.Cemetery, 4, Type.Victory).vp(2).heirloom(Cards.hauntedMirror).description("When you gain this, trash up to 4 cards from your hand.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(changeling     = new CardImpl.Builder(Cards.Kind.Changeling, 3, Type.Night).description("Trash this. Gain a copy of a card you have in play. — In games using this, when you gain a card costing (3) Coins or more, you may exchange it for a Changeling.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(cobbler        = new CardImpl.Builder(Cards.Kind.Cobbler, 5, Type.Night, Type.Duration).description("At the start of your next turn, gain a card to your hand costing up to (4) Coins.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(conclave       = new CardImpl.Builder(Cards.Kind.Conclave, 4, Type.Action).addGold(2).description("You may play an Action card from your hand that you don't have a copy of in play. If you do, +1 Action.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(cursedVillage  = new CardImpl.Builder(Cards.Kind.CursedVillage, 5, Type.Action, Type.Doom).addActions(2).description("Draw until you have 6 cards in hand. — When you gain this, receive a Hex.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(crypt          = new CardImpl.Builder(Cards.Kind.Crypt, 5, Type.Night, Type.Duration).description("Set aside any number of Treasures you have in play, face down (under this). While any remain, at the start of each of your turns, put one of them into your hand.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(denOfSin       = new CardImpl.Builder(Cards.Kind.DenOfSin, 5, Type.Night, Type.Duration).addCardsNextTurn(2).description("This is gained to your hand (instead of your discard pile).").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(devilsWorkshop = new CardImpl.Builder(Cards.Kind.DevilsWorkshop, 4, Type.Night).description("If the number of cards you've gained this turn is: 2+, gain an Imp; 1, gain a card costing up to (4) Coins; 0, gain a Gold.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(druid          = new CardImpl.Builder(Cards.Kind.Druid, 2, Type.Action, Type.Fate).addBuys(1).description("Receive one of the set-aside Boons (leaving it there). — Setup: Set aside the top 3 Boons face up.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(exorcist       = new CardImpl.Builder(Cards.Kind.Exorcist, 4, Type.Night).description("Trash a card from your hand. Gain a cheaper Spirit from one of the Spirit piles.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(faithfulHound  = new CardImpl.Builder(Cards.Kind.FaithfulHound, 2, Type.Action, Type.Reaction).addCards(2).description("When you discard this other than during Clean-up, you may set it aside, and put it into your hand at end of turn.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(fool           = new CardImpl.Builder(Cards.Kind.Fool, 3, Type.Action, Type.Fate).linkedState(lostInTheWoods).heirloom(Cards.luckyCoin).description("If you aren't the player with Lost in the Woods, take it, take 3 Boons, and receive the Boons in any order.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(ghostTown      = new CardImpl.Builder(Cards.Kind.GhostTown, 3, Type.Night, Type.Duration).addCardsNextTurn(1).addActionsNextTurn(1).description("This is gained to your hand (instead of your discard pile).").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(guardian       = new CardImpl.Builder(Cards.Kind.Guardian, 2, Type.Night, Type.Duration).addGoldNextTurn(1).description("Until your next turn, when another player plays an Attack card, it doesn't affect you. - This is gained to your hand (instead of your discard pile).").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(idol           = new CardImpl.Builder(Cards.Kind.Idol, 5, Type.Treasure, Type.Attack, Type.Fate).addGold(2).description("If you have an odd number of Idols in play (counting this), receive a Boon; otherwise, each other player gains a Curse.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(leprechaun     = new CardImpl.Builder(Cards.Kind.Leprechaun, 3, Type.Action, Type.Doom).description("Gain a Gold. If you have exactly 7 cards in play, gain a Wish. Otherwise, receive a Hex.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(monastery      = new CardImpl.Builder(Cards.Kind.Monastery, 2, Type.Night).description("For each card you've gained this turn, you may trash a card from your hand or a Copper you have in play.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(necromancer    = new CardImpl.Builder(Cards.Kind.Necromancer, 4, Type.Action).description("Choose a face up, non-Duration Action card in the trash. Turn it face down for the turn, and play it, leaving it there. — Setup: Put the 3 Zombies into the trash.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(nightWatchman  = new CardImpl.Builder(Cards.Kind.NightWatchman, 3, Type.Night).description("Look at the top 5 cards of your deck, discard any number, and put the rest back in any order. - This is gained to your hand (instead of your discard pile).").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(pixie          = new CardImpl.Builder(Cards.Kind.Pixie, 2, Type.Action, Type.Fate).heirloom(Cards.goat).addActions(1).addCards(1).description("Discard the top Boon. You may trash this to receive that Boon twice.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(pooka          = new CardImpl.Builder(Cards.Kind.Pooka, 5, Type.Action).heirloom(Cards.cursedGold).description("You may trash a Treasure other than Cursed Gold from your hand, for +4 Cards.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(raider         = new CardImpl.Builder(Cards.Kind.Raider, 6, Type.Night, Type.Duration, Type.Attack).addGoldNextTurn(3).description("Each other player with 5 or more cards in hand discards a copy of a card you have in play (or reveals they can't).").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(sacredGrove    = new CardImpl.Builder(Cards.Kind.SacredGrove, 5, Type.Action, Type.Fate).addBuys(1).addGold(3).description("Receive a Boon. If it doesn't give +(1) Coin, each other player may receive it.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(secretCave     = new CardImpl.Builder(Cards.Kind.SecretCave, 3, Type.Action, Type.Duration).hasPlusCoin().addCards(1).addActions(1).heirloom(Cards.magicLamp).description("You may discard 3 cards. If you did, then at the start of your next turn, +(3) Coins.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(shepherd       = new CardImpl.Builder(Cards.Kind.Shepherd, 4, Type.Action).addActions(1).heirloom(Cards.pasture).description("Discard any number of Victory cards, revealing them. +2 Cards per card discarded.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(skulk          = new CardImpl.Builder(Cards.Kind.Skulk, 4, Type.Action, Type.Attack, Type.Doom).addBuys(1).description("Each other player receives the next Hex. — When you gain this, gain a Gold.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(tormentor      = new CardImpl.Builder(Cards.Kind.Tormentor, 5, Type.Action, Type.Attack, Type.Doom).addGold(2).description("If you have no other cards in play, gain an Imp. Otherwise, each other player receives the next Hex.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(tracker        = new CardImpl.Builder(Cards.Kind.Tracker, 2, Type.Action, Type.Fate).addGold(1).heirloom(Cards.pouch).description("Receive a Boon. — While you have this in play, when you gain a card, you may put that card onto your deck.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(tragicHero     = new CardImpl.Builder(Cards.Kind.TragicHero, 5, Type.Action).addCards(3).addBuys(1).description("If you have 8 or more cards in hand (after drawing), trash this and gain a Treasure.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(vampire        = new CardImpl.Builder(Cards.Kind.Vampire, 5, Type.Night, Type.Attack, Type.Doom).description("Each other player receives the next Hex. Gain a card costing up to (5) Coins other than a Vampire. Exchange this for a Bat.").expansion(Expansion.Nocturne).build());
        actionCardsNocturne.add(werewolf       = new CardImpl.Builder(Cards.Kind.Werewolf, 5, Type.Action, Type.Night, Type.Attack, Type.Doom).description("If it's your Night phase, each other player receives the next Hex. Otherwise, +3 Cards.").expansion(Expansion.Nocturne).build());
        
        nonSupplyCards.add(bat              = new CardImpl.Builder(Cards.Kind.Bat, 2, Type.Night).description("Trash up to 2 cards from your hand. If you trashed at least one, exchange this for a Vampire. (This is not in the Supply.)").expansion(Expansion.Nocturne).build());
        nonSupplyCards.add(ghost            = new CardImpl.Builder(Cards.Kind.Ghost, 4, Type.Night, Type.Duration, Type.Spirit).description("Reveal cards from your deck until you reveal an Action. Discard the other cards and set aside the Action. At the start of your next turn, play it twice. (This is not in the Supply.)").expansion(Expansion.Nocturne).build());
        nonSupplyCards.add(imp              = new CardImpl.Builder(Cards.Kind.Imp, 2, Type.Action, Type.Spirit).addCards(2).description("You may play an Action card from your hand that you don't have a copy of in play. (This is not in the Supply.)").expansion(Expansion.Nocturne).build());
        nonSupplyCards.add(willOWisp        = new CardImpl.Builder(Cards.Kind.WillOWisp, 0, Type.Action, Type.Spirit).addActions(1).addCards(1).description("Reveal the top card of your deck. If it costs (2) Coins or less, put it into your hand. (This is not in the Supply.)").expansion(Expansion.Nocturne).build());
        nonSupplyCards.add(wish             = new CardImpl.Builder(Cards.Kind.Wish, 0, Type.Action).addActions(1).description("Return this to its pile. If you did, gain a card to your hand costing up to (6) Coins. (This is not in the Supply.)").expansion(Expansion.Nocturne).build());
        nonSupplyCards.add(zombieApprentice = new CardImpl.Builder(Cards.Kind.ZombieApprentice, 3, Type.Action, Type.Zombie).description("You may trash an Action card from your hand for +3 Cards and +1 Action.").expansion(Expansion.Nocturne).build());
        nonSupplyCards.add(zombieMason      = new CardImpl.Builder(Cards.Kind.ZombieMason, 3, Type.Action, Type.Zombie).description("Trash the top card of your deck. You may gain a card costing up to (1) Coin more than it.").expansion(Expansion.Nocturne).build());
        nonSupplyCards.add(zombieSpy        = new CardImpl.Builder(Cards.Kind.ZombieSpy, 3, Type.Action, Type.Zombie).addActions(1).addCards(1).description("Look at the top card of your deck. Discard it or put it back.").expansion(Expansion.Nocturne).build());
        
        boonCards.add(theEarthsGift    = new CardImpl.Builder(Cards.Kind.TheEarthsGift, Type.Boon).description("You may discard a Treasure to gain a card costing up to (4) Coins.").expansion(Expansion.Nocturne).build());
        boonCards.add(theFieldsGift    = new CardImpl.Builder(Cards.Kind.TheFieldsGift, Type.Boon).addActions(1).addGold(1).description("(Keep this until Clean-up.)").expansion(Expansion.Nocturne).build());
        boonCards.add(theFlamesGift    = new CardImpl.Builder(Cards.Kind.TheFlamesGift, Type.Boon).description("You may trash a card from your hand.").expansion(Expansion.Nocturne).build());
        boonCards.add(theForestsGift   = new CardImpl.Builder(Cards.Kind.TheForestsGift, Type.Boon).addBuys(1).addGold(1).description("(Keep this until Clean-up.)").expansion(Expansion.Nocturne).build());
        boonCards.add(theMoonsGift     = new CardImpl.Builder(Cards.Kind.TheMoonsGift, Type.Boon).description("Look through your discard pile. You may put a card from it onto your deck.").expansion(Expansion.Nocturne).build());
        boonCards.add(theMountainsGift = new CardImpl.Builder(Cards.Kind.TheMountainsGift, Type.Boon).description("Gain a Silver.").expansion(Expansion.Nocturne).build());
        boonCards.add(theRiversGift    = new CardImpl.Builder(Cards.Kind.TheRiversGift, Type.Boon).description("+1 Card at the end of this turn. (Keep this until Clean-up.)").expansion(Expansion.Nocturne).build());
        boonCards.add(theSeasGift      = new CardImpl.Builder(Cards.Kind.TheSeasGift, Type.Boon).addCards(1).description("").expansion(Expansion.Nocturne).build());
        boonCards.add(theSkysGift      = new CardImpl.Builder(Cards.Kind.TheSkysGift, Type.Boon).description("You may discard 3 cards to gain a Gold.").expansion(Expansion.Nocturne).build());
        boonCards.add(theSunsGift      = new CardImpl.Builder(Cards.Kind.TheSunsGift, Type.Boon).description("Look at the top 4 cards of your deck. Discard any number of them and put the rest back in any order.").expansion(Expansion.Nocturne).build());
        boonCards.add(theSwampsGift    = new CardImpl.Builder(Cards.Kind.TheSwampsGift, Type.Boon).description("Gain a Will-o'-Wisp.").expansion(Expansion.Nocturne).build());
        boonCards.add(theWindsGift     = new CardImpl.Builder(Cards.Kind.TheWindsGift, Type.Boon).addCards(2).description("Discard 2 cards.").expansion(Expansion.Nocturne).build());
        
        hexCards.add(badOmens = new CardImpl.Builder(Cards.Kind.BadOmens, Type.Hex).description("Put your deck into your discard pile. Look through it and put 2 Coppers from it onto your deck (or reveal you can't).").expansion(Expansion.Nocturne).build());
        hexCards.add(delusion = new CardImpl.Builder(Cards.Kind.Delusion, Type.Hex).description("If you don't have Deluded or Envious, take Deluded.").expansion(Expansion.Nocturne).build());
        hexCards.add(envy     = new CardImpl.Builder(Cards.Kind.Envy, Type.Hex).description("If you don't have Deluded or Envious, take Envious.").expansion(Expansion.Nocturne).build());
        hexCards.add(famine   = new CardImpl.Builder(Cards.Kind.Famine, Type.Hex).description("Reveal the top 3 cards of your deck. Discard the Actions. Shuffle the rest into your deck.").expansion(Expansion.Nocturne).build());
        hexCards.add(fear     = new CardImpl.Builder(Cards.Kind.Fear, Type.Hex).description("If you have at least 5 cards in hand, discard an Action or Treasure (or reveal you can't).").expansion(Expansion.Nocturne).build());
        hexCards.add(greed    = new CardImpl.Builder(Cards.Kind.Greed, Type.Hex).description("Gain a Copper onto your deck.").expansion(Expansion.Nocturne).build());
        hexCards.add(haunting = new CardImpl.Builder(Cards.Kind.Haunting, Type.Hex).description("If you have at least 4 cards in hand, put one of them onto your deck.").expansion(Expansion.Nocturne).build());
        hexCards.add(locusts  = new CardImpl.Builder(Cards.Kind.Locusts, Type.Hex).description("Trash the top card of your deck. If it's a Copper or Estate, gain a Curse. Otherwise, gain a cheaper card that shares a type with it.").expansion(Expansion.Nocturne).build());
        hexCards.add(misery   = new CardImpl.Builder(Cards.Kind.Misery, Type.Hex).description("If this is your first Misery this game, take Miserable. Otherwise, flip it over to Twice Miserable.").expansion(Expansion.Nocturne).build());
        hexCards.add(plague   = new CardImpl.Builder(Cards.Kind.Plague, Type.Hex).description("Gain a Curse to your hand.").expansion(Expansion.Nocturne).build());
        hexCards.add(poverty  = new CardImpl.Builder(Cards.Kind.Poverty, Type.Hex).description("Discard down to 3 cards in hand.").expansion(Expansion.Nocturne).build());
        hexCards.add(war      = new CardImpl.Builder(Cards.Kind.War, Type.Hex).description("Reveal cards from your deck until revealing one costing (3) Coins or (4) Coins. Trash it and discard the rest.").expansion(Expansion.Nocturne).build());
        
        stateCards.add(deluded        = new CardImpl.Builder(Cards.Kind.Deluded, Type.State).description("At the start of your Buy phase, return this, and you can't buy Actions this turn.").expansion(Expansion.Nocturne).build());
        stateCards.add(envious        = new CardImpl.Builder(Cards.Kind.Envious, Type.State).description("At the start of your Buy phase, return this, and Silver and Gold make (1) Coin this turn.").expansion(Expansion.Nocturne).build());
        stateCards.add(miserable      = new CardImpl.Builder(Cards.Kind.Miserable, Type.State).vp(-2).description("").expansion(Expansion.Nocturne).build());
        stateCards.add(twiceMiserable = new CardImpl.Builder(Cards.Kind.TwiceMiserable, Type.State).vp(-4).description("").expansion(Expansion.Nocturne).build());
        
        // Renaissance Cards
        artifactCardsRenaissance.add(flag          = new CardImpl.Builder(Cards.Kind.Flag, Type.Artifact).description("When drawing your hand, +1 Card.").expansion(Expansion.Renaissance).build());
        artifactCardsRenaissance.add(horn          = new CardImpl.Builder(Cards.Kind.Horn, Type.Artifact).description("Once per turn, when you discard a Border Guard from play, you may put it onto your deck.").expansion(Expansion.Renaissance).build());
        artifactCardsRenaissance.add(key           = new CardImpl.Builder(Cards.Kind.Key, Type.Artifact).description("At the start of your turn, +(1) Coin.").expansion(Expansion.Renaissance).build());
        artifactCardsRenaissance.add(lantern       = new CardImpl.Builder(Cards.Kind.Lantern, Type.Artifact).description("Border Guards you play reveal 3 cards and discard 2. (It takes all 3 being Actions to take the Horn.)").expansion(Expansion.Renaissance).build());
        artifactCardsRenaissance.add(treasureChest = new CardImpl.Builder(Cards.Kind.TreasureChest, Type.Artifact).description("At the start of your Buy phase, gain a Gold.").expansion(Expansion.Renaissance).build());
        
        actionCardsRenaissance.add(actingTroupe    = new CardImpl.Builder(Cards.Kind.ActingTroupe, 3, Type.Action).addVillagers(4).description("Trash this.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(borderGuard     = new CardImpl.Builder(Cards.Kind.BorderGuard, 2, Type.Action).linkedState(horn).addActions(1).linkedState(lantern).description("Reveal the top 2 cards of your deck. Put one into your hand and discard the other. If both were Actions, take the Lantern or Horn.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(cargoShip       = new CardImpl.Builder(Cards.Kind.CargoShip, 3, Type.Action, Type.Duration).addGold(2).description("Once this turn, when you gain a card, you may set it aside face up (on this). At the start of your next turn, put it into your hand.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(ducat           = new CardImpl.Builder(Cards.Kind.Ducat, 2, Type.Treasure).addBuys(1).addCoffers(1).description("When you gain this, you may trash a Copper from your hand.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(experiment      = new CardImpl.Builder(Cards.Kind.Experiment, 3, Type.Action).addCards(2).addActions(1).description("Return this to the Supply. - When you gain this, gain another Experiment (that doesn't come with another).").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(flagBearer      = new CardImpl.Builder(Cards.Kind.FlagBearer, 4, Type.Action).linkedState(flag).addGold(2).description("When you gain or trash this, take the Flag.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(hideout         = new CardImpl.Builder(Cards.Kind.Hideout, 4, Type.Action).addCards(1).addActions(2).description("Trash a card from your hand. If it's a Victory card, gain a Curse.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(improve         = new CardImpl.Builder(Cards.Kind.Improve, 3, Type.Action).addGold(2).description("At the start of Clean-up, you may trash an Action card you would discard from play this turn, to gain a card costing exactly (1) Coin more than it.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(inventor        = new CardImpl.Builder(Cards.Kind.Inventor, 4, Type.Action).description("Gain a card costing up to 4 Coins, then cards cost 1 Coin less this turn.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(lackeys         = new CardImpl.Builder(Cards.Kind.Lackeys, 2, Type.Action).addCards(2).description("When you gain this, +2 Villagers.").expansion(Expansion.Renaissance).build());        
        actionCardsRenaissance.add(mountainVillage = new CardImpl.Builder(Cards.Kind.MountainVillage, 4, Type.Action).addActions(2).description("Look through your discard pile and put a card from it into your hand; if you can't, +1 Card.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(oldWitch        = new CardImpl.Builder(Cards.Kind.OldWitch, 5, Type.Action, Type.Attack).addCards(3).description("Each other player gains a Curse and may trash a Curse from their hand.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(patron          = new CardImpl.Builder(Cards.Kind.Patron, 4, Type.Action, Type.Reaction).addVillagers(1).addGold(2).description("When something causes you to reveal this (using the word \"reveal\") in an Action phase, +1 Coffers.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(priest          = new CardImpl.Builder(Cards.Kind.Priest, 4, Type.Action).trashForced().addGold(2).description("Trash a card from your hand. For the rest of this turn, when you trash a card, +(2) Coins.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(recruiter       = new CardImpl.Builder(Cards.Kind.Recruiter,5, Type.Action).trashForced().addCards(2).description("Trash a card from your hand. +1 Villager per (1) Coin it costs.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(research        = new CardImpl.Builder(Cards.Kind.Research, 4, Type.Action, Type.Duration).addActions(1).description("Trash a card from your hand. Per (1) Coin it costs, set aside a card from your deck face down (on this). At the start of your next turn, put those cards into your hand.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(scepter         = new CardImpl.Builder(Cards.Kind.Scepter, 5, Type.Treasure).hasPlusCoin().description("Choose one: +2 Coins; or replay an Action card you played this turn that's still in play.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(scholar         = new CardImpl.Builder(Cards.Kind.Scholar, 5, Type.Action).description("Discard your hand. +7 Cards.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(sculptor        = new CardImpl.Builder(Cards.Kind.Sculptor, 5, Type.Action).description("Gain a card to your hand costing up to (4) Coins. If it's a Treasure, +1 Villager.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(seer            = new CardImpl.Builder(Cards.Kind.Seer, 5, Type.Action).addCards(1).addActions(1).description("Reveal the top 3 cards of your deck. Put the ones costing from (2) Coins to (4) Coins into your hand. Put the rest back in any order.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(spices          = new CardImpl.Builder(Cards.Kind.Spices, 5, Type.Treasure).addGold(2).addBuys(1).description("When you gain this, +2 Coffers.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(swashbuckler    = new CardImpl.Builder(Cards.Kind.Swashbuckler, 5, Type.Action).linkedState(treasureChest).addCards(3).description("If your discard pile has any cards in it: +1 Coffers, then if you have at least 4 Coffers tokens, take the Treasure Chest.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(silkMerchant    = new CardImpl.Builder(Cards.Kind.SilkMerchant, 4, Type.Action).addCards(2).addBuys(1).description("When you gain or trash this, +1 Coffers and +1 Villager.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(treasurer       = new CardImpl.Builder(Cards.Kind.Treasurer, 5, Type.Action).linkedState(key).addGold(3).description("Choose one: Trash a Treasure from your hand; or gain a Treasure from the trash to your hand; or take the Key.").expansion(Expansion.Renaissance).build());
        actionCardsRenaissance.add(villain         = new CardImpl.Builder(Cards.Kind.Villain, 5, Type.Action, Type.Attack).addCoffers(2).description("Each other player with 5 or more cards in hand discards one costing (2) Coins or more (or reveals they can't).").expansion(Expansion.Renaissance).build());
       
        projectCardsRenaissance.add(academy      = new CardImpl.Builder(Cards.Kind.Academy, 5, Type.Project).description("When you gain an Action card, +1 Villager.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(barracks     = new CardImpl.Builder(Cards.Kind.Barracks, 6, Type.Project).description("At the start of your turn, +1 Action.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(canal        = new CardImpl.Builder(Cards.Kind.Canal, 7, Type.Project).description("During your turns, cards cost 1 Coin less.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(capitalism   = new CardImpl.Builder(Cards.Kind.Capitalism, 5, Type.Project).description("During your turns, Actions with +Coin amounts in their text are also Treasures.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(cathedral    = new CardImpl.Builder(Cards.Kind.Cathedral, 3, Type.Project).description("At the start of your turn, trash a card from your hand.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(citadel      = new CardImpl.Builder(Cards.Kind.Citadel, 8, Type.Project).description("The first time you play an Action card during each of your turns, replay it afterwards.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(cityGate     = new CardImpl.Builder(Cards.Kind.CityGate, 3, Type.Project).description("At the start of your turn, +1 Card, then put a card from your hand onto your deck.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(cropRotation = new CardImpl.Builder(Cards.Kind.CropRotation, 6, Type.Project).description("At the start of your turn, you may discard a Victory card for +2 Cards.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(exploration  = new CardImpl.Builder(Cards.Kind.Exploration, 4, Type.Project).description("At the end of your Buy phase, if you didn't buy any cards during it, +1 Coffers and +1 Villager.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(fair         = new CardImpl.Builder(Cards.Kind.Fair, 4, Type.Project).description("At the start of your turn, +1 Buy.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(fleet        = new CardImpl.Builder(Cards.Kind.Fleet, 5, Type.Project).description("After the game ends, there's an extra round of turns just for the players with this.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(guildhall    = new CardImpl.Builder(Cards.Kind.Guildhall, 5, Type.Project).description("When you gain a Treasure, +1 Coffers.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(innovation   = new CardImpl.Builder(Cards.Kind.Innovation, 6, Type.Project).description("The first time you gain an Action card during each of your turns, you may play it.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(pageant      = new CardImpl.Builder(Cards.Kind.Pageant, 3, Type.Project).description("At the end of your Buy phase, you may pay (1) Coin for +1 Coffers.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(piazza       = new CardImpl.Builder(Cards.Kind.Piazza, 5, Type.Project).description("At the start of your turn, reveal the top card of your deck. If it's an Action, play it.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(roadNetwork  = new CardImpl.Builder(Cards.Kind.RoadNetwork, 5, Type.Project).description("When another player gains a Victory card, +1 Card.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(sewers       = new CardImpl.Builder(Cards.Kind.Sewers, 3, Type.Project).description("When you trash a card other than with this, you may trash a card from your hand.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(silos        = new CardImpl.Builder(Cards.Kind.Silos, 4, Type.Project).description("At the start of your turn, discard and number of Coppers, revealed, and draw that many cards.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(sinisterPlot = new CardImpl.Builder(Cards.Kind.SinisterPlot, 4, Type.Project).description("At the start of your turn; add a token here, or remove your tokens here for +1 Card each.").expansion(Expansion.Renaissance).build());
        projectCardsRenaissance.add(starChart    = new CardImpl.Builder(Cards.Kind.StarChart, 3, Type.Project).description("When you shuffle, you may pick one of the cards to go on top.").expansion(Expansion.Renaissance).build());
        
        // Menagerie Cards
        nonSupplyCards.add(horse   = new CardImpl.Builder(Cards.Kind.Horse, 3, Type.Action).addCards(2).addActions(1).description("Return this to its pile. (This is not in the Supply.)").expansion(Expansion.Menagerie).build());

        actionCardsMenagerie.add(animalFair      = new CardImpl.Builder(Cards.Kind.AnimalFair, 7, Type.Action).addGold(4).description("+1 Buy per empty Supply pile. — Instead of paying this card's cost, you may trash an Action card from your hand.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(barge           = new CardImpl.Builder(Cards.Kind.Barge, 5, Type.Action, Type.Duration).description("Either now or at the start of your next turn, +3 Cards and +1 Buy.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(blackCat        = new CardImpl.Builder(Cards.Kind.BlackCat, 2, Type.Action, Type.Attack, Type.Reaction).addCards(2).description("If it isn't your turn, each other player gains a Curse. — When another player gains a Victory card, you may play this from your hand.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(bountyHunter    = new CardImpl.Builder(Cards.Kind.BountyHunter, 4, Type.Action).addActions(1).description("Exile a card from your hand. If you didn't have a copy of it in Exile, +(3) Coins.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(cavalry         = new CardImpl.Builder(Cards.Kind.Cavalry, 4, Type.Action).description("Gain 2 Horses. — When you gain this, +2 Cards, +1 Buy, and if it's your Buy phase return to your Action phase.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(camelTrain      = new CardImpl.Builder(Cards.Kind.CamelTrain, 3, Type.Action).description("Exile a non-Victory card from the Supply. — When you gain this, Exile a Gold from the Supply.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(cardinal        = new CardImpl.Builder(Cards.Kind.Cardinal, 4, Type.Action, Type.Attack).addGold(2).description("Each other player reveals the top 2 cards of their deck, Exiles one costing from (3) to (6) Coins, and discards the rest.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(coven           = new CardImpl.Builder(Cards.Kind.Coven, 5, Type.Action, Type.Attack).addActions(1).addGold(2).description("Each other player Exiles a Curse from the Supply. If they can't, they discard their Exiled Curses.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(destrier        = new CardImpl.Builder(Cards.Kind.Destrier, 6, Type.Action).addCards(2).addActions(1).description("During your turns, this costs (1) Coin less per card you've gained this turn.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(displace        = new CardImpl.Builder(Cards.Kind.Displace, 5, Type.Action).description("Exile a card from your hand. Gain a differently named card costing up to (2) Coins more than it.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(falconer        = new CardImpl.Builder(Cards.Kind.Falconer, 5, Type.Action, Type.Reaction).description("Gain a card to your hand costing less than this. — When any player gains a card with 2 or more types (Action, Attack, etc.), you may play this from your hand.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(fisherman       = new CardImpl.Builder(Cards.Kind.Fisherman, 5, Type.Action).addCards(1).addActions(1).addGold(1).description("During your turns, if your discard pile is empty, this costs (3) Coins less.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(gatekeeper      = new CardImpl.Builder(Cards.Kind.Gatekeeper, 5, Type.Action, Type.Duration, Type.Attack).addGoldNextTurn(3).description("Until then, when another player gains an Action or Treasure card they don't have an Exiled copy of, they Exile it.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(goatherd        = new CardImpl.Builder(Cards.Kind.Goatherd, 3, Type.Action).addActions(1).description("You may trash a card from your hand. +1 Card per card the player to your right trashed on their last turn.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(groom           = new CardImpl.Builder(Cards.Kind.Groom, 4, Type.Action).description("Gain a card costing up to $4 If it's an... Action card, gain a Horse; Treasure card, gain a Silver; Victory card, +1 Card and +1 Action.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(hostelry        = new CardImpl.Builder(Cards.Kind.Hostelry, 4, Type.Action).addCards(1).addActions(2).description("When you gain this, you may discard any number of Treasures, revealed, to gain that many Horses.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(huntingLodge    = new CardImpl.Builder(Cards.Kind.HuntingLodge, 5, Type.Action).addCards(1).addActions(2).description("You may discard your hand for +5 Cards.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(kiln            = new CardImpl.Builder(Cards.Kind.Kiln, 5, Type.Action).addGold(2).description("The next time you play a card this turn, you may first gain a copy of it.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(livery          = new CardImpl.Builder(Cards.Kind.Livery, 5, Type.Action).addGold(3).description("This turn, when you gain a card costing (4) Coins or more, gain a Horse.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(mastermind      = new CardImpl.Builder(Cards.Kind.Mastermind, 5, Type.Action, Type.Duration).description("At the start of your next turn, you may play an Action card from your hand three times.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(paddock         = new CardImpl.Builder(Cards.Kind.Paddock, 5, Type.Action).addGold(2).description("Gain 2 Horses. +1 Action per empty Supply pile.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(sanctuary       = new CardImpl.Builder(Cards.Kind.Sanctuary, 5, Type.Action).addCards(1).addActions(1).addBuys(1).description("You may Exile a card from your hand.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(scrap           = new CardImpl.Builder(Cards.Kind.Scrap, 3, Type.Action).description("Trash a card from your hand. Choose a different thing per (1) Coin it costs: +1 Card; +1 Action; +1 Buy; +(1) Coin; gain a Silver; gain a Horse.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(sheepdog        = new CardImpl.Builder(Cards.Kind.Sheepdog, 3, Type.Action, Type.Reaction).addCards(2).description("When you gain a card, you may play this from your hand.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(sleigh          = new CardImpl.Builder(Cards.Kind.Sleigh, 2, Type.Action, Type.Reaction).description("Gain 2 Horses. — When you gain a card, you may discard this, to put that card into your hand or onto your deck.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(snowyVillage    = new CardImpl.Builder(Cards.Kind.SnowyVillage, 3, Type.Action).addCards(1).addActions(4).addBuys(1).description("Ignore any further +Actions you get this turn.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(stockpile       = new CardImpl.Builder(Cards.Kind.Stockpile, 3, Type.Treasure).addGold(3).addBuys(1).description("When you play this, Exile it.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(supplies        = new CardImpl.Builder(Cards.Kind.Supplies, 2, Type.Treasure).addGold(1).description("When you play this, gain a Horse onto your deck.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(villageGreen    = new CardImpl.Builder(Cards.Kind.VillageGreen, 4, Type.Action, Type.Duration, Type.Reaction).description("Either now or at the start of your next turn, +1 Card and +2 Actions. — When you discard this other than during Clean-up, you may play it.").expansion(Expansion.Menagerie).build());
        actionCardsMenagerie.add(wayfarer        = new CardImpl.Builder(Cards.Kind.Wayfarer, 6, Type.Action).addCards(3).description("You may gain a Silver. — This has the same cost as the last other card gained this turn, if any.").expansion(Expansion.Menagerie).build());
        
        // events
        eventCardsMenagerie.add(alliance    = new CardImpl.Builder(Cards.Kind.Alliance, 10, Type.Event).description("Gain a Provice, a Duchy, an Estate, a Gold, a Silver, and a Copper.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(banish      = new CardImpl.Builder(Cards.Kind.Banish, 4, Type.Event).description("Exile any number of cards with the same name from your hand.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(bargain      = new CardImpl.Builder(Cards.Kind.Bargain, 4, Type.Event).description("Gain a non-Victory card costing up to (5) Coins. Each other player gains a Horse.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(commerce    = new CardImpl.Builder(Cards.Kind.Commerce, 5, Type.Event).description("Gain a Gold per differently named card you've gained this turn.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(delay       = new CardImpl.Builder(Cards.Kind.Delay, 0, Type.Event).description("You may set aside an Action card from your hand. At the start of your next turn, play it.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(demand      = new CardImpl.Builder(Cards.Kind.Demand, 5, Type.Event).description("Gain a Horse and a card costing up to (4) Coins, both onto your deck.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(desperation = new CardImpl.Builder(Cards.Kind.Desperation, 0, Type.Event).description("Once per turn: You may gain a Curse. If you do, +1 Buy and +(2) Coins.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(enclave     = new CardImpl.Builder(Cards.Kind.Enclave, 8, Type.Event).description("Gain a Gold. Exile a Duchy from the Supply.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(enhance     = new CardImpl.Builder(Cards.Kind.Enhance, 3, Type.Event).description("You may trash a non-Victory card from your hand, to gain a card costing up to (2) Coins more than it.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(gamble      = new CardImpl.Builder(Cards.Kind.Gamble, 2, Type.Event).addBuys(1).description("Reveal the top card of your deck. If it's a Treasure or Action, you may play it. Otherwise, discard it.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(invest      = new CardImpl.Builder(Cards.Kind.Invest, 4, Type.Event).description("Exile an Action card from the Supply. While it's in Exile, when another player gains or Invests in a copy of it, +2 Cards.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(march       = new CardImpl.Builder(Cards.Kind.March, 3, Type.Event).description("Look through your discard pile. You may play an Action card from it.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(pursue      = new CardImpl.Builder(Cards.Kind.Pursue, 2, Type.Event).addBuys(1).description("Name a card. Reveal the top 4 cards from your deck. Put the matches back and discard the rest.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(populate    = new CardImpl.Builder(Cards.Kind.Populate, 10, Type.Event).description("Gain one card from each Action Supply pile.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(reap        = new CardImpl.Builder(Cards.Kind.Reap, 7, Type.Event).description("Gain a Gold. Set it aside. If you do, at the start of your next turn, play it.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(ride        = new CardImpl.Builder(Cards.Kind.Ride, 2, Type.Event).description("Gain a Horse.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(seizeTheDay = new CardImpl.Builder(Cards.Kind.SeizeTheDay, 4, Type.Event).description("Once per game: Take an extra turn after this one.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(stampede    = new CardImpl.Builder(Cards.Kind.Stampede, 5, Type.Event).description("If you have 5 or fewer cards in play, gain 5 Horses onto your deck.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(toil        = new CardImpl.Builder(Cards.Kind.Toil, 2, Type.Event).addBuys(1).description("You may play an Action card from your hand.").expansion(Expansion.Menagerie).build());
        eventCardsMenagerie.add(transport   = new CardImpl.Builder(Cards.Kind.Transport, 3, Type.Event).description("Choose one: Exile an Action card from the Supply; or put an Action card you have in Exile onto your deck.").expansion(Expansion.Menagerie).build());
                
        // ways
        wayCardsMenagerie.add(wayOfTheButterfly = new CardImpl.Builder(Cards.Kind.WayOfTheButterfly, Type.Way).description("You may return this to its pile to gain a card costing exactly (1) Coin more than it.").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheCamel = new CardImpl.Builder(Cards.Kind.WayOfTheCamel, Type.Way).description("Exile a Gold from the Supply.").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheChameleon = new CardImpl.Builder(Cards.Kind.WayOfTheChameleon, Type.Way).description("Follow this card's instructions; each time that would give you +Cards this turn, you get +Coins instead, and vice-versa.").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheFrog = new CardImpl.Builder(Cards.Kind.WayOfTheFrog, Type.Way).addActions(1).description("When you discard this from play this turn, put it onto your deck.").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheGoat = new CardImpl.Builder(Cards.Kind.WayOfTheGoat, Type.Way).description("Trash a card from your hand.").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheHorse = new CardImpl.Builder(Cards.Kind.WayOfTheHorse, Type.Way).addCards(2).addActions(1).description("Return this to its pile.").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheMole = new CardImpl.Builder(Cards.Kind.WayOfTheMole, Type.Way).addActions(1).description("Discard your hand. +3 Cards.").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheMonkey = new CardImpl.Builder(Cards.Kind.WayOfTheMonkey, Type.Way).addBuys(1).addGold(1).description("").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheMouse = new CardImpl.Builder(Cards.Kind.WayOfTheMouse, Type.Way).description("Play the set-aside card, leaving it there. — Setup: Set aside an unused Action costing (2) or (3) Coins.").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheMule = new CardImpl.Builder(Cards.Kind.WayOfTheMule, Type.Way).addActions(1).addGold(1).description("").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheOtter = new CardImpl.Builder(Cards.Kind.WayOfTheOtter, Type.Way).addCards(2).description("").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheOwl = new CardImpl.Builder(Cards.Kind.WayOfTheOwl, Type.Way).description("Draw until you have 6 cards in hand.").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheOx = new CardImpl.Builder(Cards.Kind.WayOfTheOx, Type.Way).addActions(2).description("").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfThePig = new CardImpl.Builder(Cards.Kind.WayOfThePig, Type.Way).addCards(1).addActions(1).description("").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheRat = new CardImpl.Builder(Cards.Kind.WayOfTheRat, Type.Way).description("You may discard a Treasure to gain a copy of this.").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheSeal = new CardImpl.Builder(Cards.Kind.WayOfTheSeal, Type.Way).addGold(1).description("This turn, when you gain a card, you may put it onto your deck.").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheSheep = new CardImpl.Builder(Cards.Kind.WayOfTheSheep, Type.Way).addGold(2).description("").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheSquirrel = new CardImpl.Builder(Cards.Kind.WayOfTheSquirrel, Type.Way).description("+2 Cards at the end of this turn.").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheTurtle = new CardImpl.Builder(Cards.Kind.WayOfTheTurtle, Type.Way).description("Set this aside. If you did, play it at the start of your next turn.").expansion(Expansion.Menagerie).build());
        wayCardsMenagerie.add(wayOfTheWorm = new CardImpl.Builder(Cards.Kind.WayOfTheWorm, Type.Way).description("Exile an Estate from the Supply.").expansion(Expansion.Menagerie).build());
               
        // Promo Cards
        variablePileCards.add(sauna        = new CardImpl.Builder(Cards.Kind.Sauna, 4, Type.Action).addCards(1).addActions(1).description("You may play an Avanto from your hand. - While this is in play, when you play a Silver, you may trash a card from your hand.").expansion(Expansion.Promo).build());
        variablePileCards.add(avanto       = new CardImpl.Builder(Cards.Kind.Avanto, 5, Type.Action).addCards(3).description("You may play a Sauna from your hand.").expansion(Expansion.Promo).build());
        
        actionCardsPromo.add(walledVillage = new CardImpl.Builder(Cards.Kind.WalledVillage, 4, Type.Action).addCards(1).addActions(2).description("At the start of Clean-up, if you have this and no more than one other Action card in play, you may put this on top of your deck.").expansion(Expansion.Promo).build());
        actionCardsPromo.add(governor      = new CardImpl.Builder(Cards.Kind.Governor, 5, Type.Action).addActions(1).description("Choose one; you get the version in parentheses: Each player gets +1 (+3) Cards; or each player gains a Silver (Gold); or each player may trash a card from his hand and gain a card costing exactly 1 (2) more.").expansion(Expansion.Promo).build());
        actionCardsPromo.add(envoy         = new CardImpl.Builder(Cards.Kind.Envoy, 4, Type.Action).description("Reveal the top 5 cards of your deck. The player to your left chooses one for you to discard. Draw the rest.").expansion(Expansion.Promo).build());
        actionCardsPromo.add(prince        = new CardImpl.Builder(Cards.Kind.Prince, 8, Type.Action, Type.Duration, Type.Command).description("You may set aside (on this) a non-Duration Action card from your hand costing up to (4) Coins. At the start of each of your turns, play that card, leaving it set aside.").expansion(Expansion.Promo).build());
        actionCardsPromo.add(blackMarket   = new CardImpl.Builder(Cards.Kind.BlackMarket, 3, Type.Action).addGold(2).description("Reveal the top 3 cards of the Black Market deck. You may buy one of them immediately. Put the unbought cards on the bottom of the Black Market deck in any order./n(Before the game, make a Black Market deck out of one copy of each Kingdom card not in the supply.)").expansion(Expansion.Promo).build());
        actionCardsPromo.add(stash         = new CardImpl.Builder(Cards.Kind.Stash, 5, Type.Treasure).addGold(2).description("When you shuffle, you may put this anywhere in your deck.").expansion(Expansion.Promo).build());
        actionCardsPromo.add(virtualSaunaAvanto = new CardImpl.Builder(Cards.Kind.SaunaAvanto, 4, Type.Action).pileCreator(new SplitPileCreator(sauna, avanto)).description("This pile starts the game with 5 copies of Sauna on top, then 5 copies of Avanto. Only the top card of the pile can be gained or bought.").expansion(Expansion.Promo).build());
        actionCardsPromo.add(dismantle     = new CardImpl.Builder(Cards.Kind.Dismantle, 4, Type.Action).description("Trash a card from your hand. If it costs (1) Coin or more, gain a cheaper card and a Gold.").expansion(Expansion.Promo).build());
        actionCardsPromo.add(church        = new CardImpl.Builder(Cards.Kind.Church, 3, Type.Action, Type.Duration).addActions(1).description("Set aside up to 3 cards from your hand face down. At the start of your next turn, put them into your hand, then you may trash a card from your hand.").expansion(Expansion.Promo).build());
        actionCardsPromo.add(captain       = new CardImpl.Builder(Cards.Kind.Captain, 6, Type.Action, Type.Duration, Type.Command).description("Now and at the start of your next turn: Play a non-Duration non-Command Action card from the Supply costing up to (4) Coins, leaving it there.").expansion(Expansion.Promo).build());
        
        eventCardsPromo.add(summon         = new CardImpl.Builder(Cards.Kind.Summon, 5, Type.Event).description("Gain an Action card costing up to 4 Coins. Set it aside. If you do, then at the start of your next turn, play it.").expansion(Expansion.Promo).build());

        // Create map for from variable pile card to randomizer
        for (Card card : knightsCards) { variablePileCardToRandomizer.put(card, virtualKnight); }
        for (Card card : castleCards) { variablePileCardToRandomizer.put(card, virtualCastle); }
        variablePileCardToRandomizer.put(catapult, virtualCatapultRocks);
        variablePileCardToRandomizer.put(rocks, virtualCatapultRocks);
        variablePileCardToRandomizer.put(encampment, virtualEncampmentPlunder);
        variablePileCardToRandomizer.put(plunder, virtualEncampmentPlunder);
        variablePileCardToRandomizer.put(gladiator, virtualGladiatorFortune);
        variablePileCardToRandomizer.put(fortune, virtualGladiatorFortune);
        variablePileCardToRandomizer.put(patrician, virtualPatricianEmporium);
        variablePileCardToRandomizer.put(emporium, virtualPatricianEmporium);
        variablePileCardToRandomizer.put(settlers, virtualSettlersBustlingVillage);
        variablePileCardToRandomizer.put(bustlingVillage, virtualSettlersBustlingVillage);
        variablePileCardToRandomizer.put(sauna, virtualSaunaAvanto);
        variablePileCardToRandomizer.put(avanto, virtualSaunaAvanto);
        
        // Set up Expansion enum
        Expansion.Base.setKingdomCards(actionCardsBaseGame);
        Expansion.Base2E.setKingdomCards(actionCardsBaseGame2E);
        Expansion.BaseAll.setKingdomCards(actionCardsBaseGameAll);
        Expansion.Intrigue.setKingdomCards(actionCardsIntrigue);
        Expansion.Intrigue2E.setKingdomCards(actionCardsIntrigue2E);
        Expansion.IntrigueAll.setKingdomCards(actionCardsIntrigueAll);
        Expansion.Seaside.setKingdomCards(actionCardsSeaside);
        Expansion.Alchemy.setKingdomCards(actionCardsAlchemy);
        Expansion.Prosperity.setKingdomCards(actionCardsProsperity);
        Expansion.Cornucopia.setKingdomCards(actionCardsCornucopia);
        Expansion.Hinterlands.setKingdomCards(actionCardsHinterlands);
        Expansion.DarkAges.setKingdomCards(actionCardsDarkAges);
        Expansion.Guilds.setKingdomCards(actionCardsGuilds);
        Expansion.Adventures.setKingdomCards(actionCardsAdventures);
        Expansion.Adventures.setEventCards(eventCardsAdventures);
        Expansion.Empires.setKingdomCards(actionCardsEmpires);
        Expansion.Empires.setEventCards(eventCardsEmpires);
        Expansion.Empires.setLandmarkCards(landmarkCardsEmpires);
        Expansion.Nocturne.setKingdomCards(actionCardsNocturne);
        Expansion.Renaissance.setKingdomCards(actionCardsRenaissance);
        Expansion.Renaissance.setProjectCards(projectCardsRenaissance);
        Expansion.Menagerie.setKingdomCards(actionCardsMenagerie);
        Expansion.Menagerie.setEventCards(eventCardsMenagerie);
        Expansion.Menagerie.setWayCards(wayCardsMenagerie);
        Expansion.Promo.setKingdomCards(actionCardsPromo);
        Expansion.Promo.setEventCards(eventCardsPromo);
                
        // Collect all Expansions
        for (Card card : actionCardsBaseGameAll) { actionCards.add(card); }
        for (Card card : actionCardsIntrigueAll) { actionCards.add(card); }
        for (Card card : actionCardsSeaside)     { actionCards.add(card); }
        for (Card card : actionCardsAlchemy)     { actionCards.add(card); }
        for (Card card : actionCardsProsperity)  { actionCards.add(card); }
        for (Card card : actionCardsCornucopia)  { actionCards.add(card); }
        for (Card card : actionCardsHinterlands) { actionCards.add(card); }
        for (Card card : actionCardsDarkAges)    { actionCards.add(card); }
        for (Card card : actionCardsGuilds)      { actionCards.add(card); }
        for (Card card : actionCardsAdventures)  { actionCards.add(card); }
        for (Card card : actionCardsEmpires)     { actionCards.add(card); }
        for (Card card : actionCardsNocturne)    { actionCards.add(card); }
        for (Card card : actionCardsRenaissance) { actionCards.add(card); }
        for (Card card : actionCardsMenagerie) { actionCards.add(card); }
        for (Card card : actionCardsPromo)       { actionCards.add(card); }
        
        for (Card card : eventCardsAdventures)  { eventsCards.add(card); }
        for (Card card : eventCardsEmpires)     { eventsCards.add(card); }
        for (Card card : eventCardsMenagerie)   { eventsCards.add(card); }
        for (Card card : eventCardsPromo)  		{ eventsCards.add(card); }
        
        for (Card card : landmarkCardsEmpires) { landmarkCards.add(card); }
        
        for (Card card : projectCardsRenaissance)  { projectCards.add(card); }
        for (Card card : artifactCardsRenaissance) { nonKingdomCards.add(card); }
        
        for (Card card : wayCardsMenagerie) { wayCards.add(card); }

        for (Card card : nonSupplyCards)        { nonKingdomCards.add(card); };
        for (Card card : prizeCards)            { nonKingdomCards.add(card); };
        for (Card card : eventsCards)           { nonKingdomCards.add(card); };
        for (Card card : landmarkCards)         { nonKingdomCards.add(card); };
        for (Card card : boonCards)				{ nonKingdomCards.add(card); };
        for (Card card : hexCards)				{ nonKingdomCards.add(card); };
        for (Card card : stateCards)			{ nonKingdomCards.add(card); };
        for (Card card : wayCards)              { nonKingdomCards.add(card); };
        
        for (Card card : actionCards)       { cardNameToCard.put(card.getName(), card); }
        for (Card card : prizeCards)        { cardNameToCard.put(card.getName(), card); }
        for (Card card : nonSupplyCards)    { cardNameToCard.put(card.getName(), card); }
        for (Card card : knightsCards)      { cardNameToCard.put(card.getName(), card); }
        for (Card card : ruinsCards)        { cardNameToCard.put(card.getName(), card); }
        for (Card card : castleCards)       { cardNameToCard.put(card.getName(), card); }
        for (Card card : boonCards)         { cardNameToCard.put(card.getName(), card); }
        for (Card card : hexCards)          { cardNameToCard.put(card.getName(), card); }
        for (Card card : variablePileCards) { cardNameToCard.put(card.getName(), card); }
        for (Card card : nonKingdomCards)   { cardNameToCard.put(card.getName(), card); }
        for (Card card : eventsCards)       { cardNameToCard.put(card.getName(), card); }
        for (Card card : landmarkCards)     { cardNameToCard.put(card.getName(), card); }
        for (Card card : stateCards)        { cardNameToCard.put(card.getName(), card); }
        for (Card card : projectCards)      { cardNameToCard.put(card.getName(), card); }
        for (Card card : wayCards)          { cardNameToCard.put(card.getName(), card); }
        
        blackMarketCards.clear(); // Cards in Black Market deck are not in supply
    }

    public static boolean isKingdomCard(Card c) {
        return !nonKingdomCards.contains(c);
    }

    public static boolean isSupplyCard(Card c) {
        return !(nonSupplyCards.contains(c) || prizeCards.contains(c) || eventsCards.contains(c) || projectCards.contains(c) || landmarkCards.contains(c) || wayCards.contains(c) || blackMarketCards.contains(c));
    }
    
    public static boolean isBlackMarketCard(Card c) {
        return blackMarketCards.contains(c);
    }
}


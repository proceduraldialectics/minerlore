package minerlore;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetNBT;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = BookLoot.MODID, name = BookLoot.NAME, version = BookLoot.VERSION, dependencies = "")
@Mod.EventBusSubscriber 
public class BookLoot {
    public static final String MODID = "minerlore";
    public static final String NAME = "Miner Lore";
    public static final String VERSION = "1.0";
       
    public static LootEntry entry = new LootEntryItem(
			Items.WRITTEN_BOOK, 1, 0, new LootFunction[0], new LootCondition[0], "minerlore:lootbook"
			);
    

	
    @SubscribeEvent
    public static void onLootTableLoad(final LootTableLoadEvent event)
    {
    	List<LootEntry> entries = new ArrayList<LootEntry>();
        if(event.getName().equals(LootTableList.CHESTS_IGLOO_CHEST) || 
        		event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON) || 
        		event.getName().equals(LootTableList.CHESTS_WOODLAND_MANSION) ||
        		event.getName().equals(LootTableList.CHESTS_VILLAGE_BLACKSMITH) ||
        		event.getName().equals(LootTableList.CHESTS_STRONGHOLD_LIBRARY) ||
        		event.getName().equals(new ResourceLocation("champions:champion_loot")))
        {
	        for (BookData book : book_array)
	        {
	        	NBTTagCompound tag = new NBTTagCompound();
	
		        tag.setString("author",book.author);
		        if (book.title == "Questions From a Worker Who Reads")
		        	tag.setString("title","Questions From a Worker");
		        else if (book.title == "The Testimony of Patience Kershaw")
		        	tag.setString("title","Testimony of Patience Kershaw");
		        else tag.setString("title",book.title);
	
		        NBTTagList pages = new NBTTagList();
		        pages.appendTag(new NBTTagString(String.format("{\"text\":\"%s\"}", book.title+"\n\n"+book.author)));						        	
		        for (String bookpage : book.pages)
		        {
			        pages.appendTag(new NBTTagString(String.format("{\"text\":\"%s\"}", bookpage)));						        	
		        }
		        tag.setTag("pages", pages);

		        LootEntry entry = new LootEntryItem(Items.WRITTEN_BOOK, 1, 0, new LootFunction[]{new SetNBT(new LootCondition[0],tag)}, new LootCondition[0], "written_book");
		        entries.add(entry);
	        }

	        if(event.getName().equals(LootTableList.CHESTS_STRONGHOLD_LIBRARY))
	        {
    			LootPool pool = new LootPool(entries.toArray(new LootEntry[] {}), new LootCondition[0], new RandomValueRange(8,16), new RandomValueRange(0,1), "bookpool");        
    	        event.getTable().addPool(pool);
	        }
	        else if (event.getName().equals(new ResourceLocation("champions:champion_loot")))
	        {
    			LootPool pool = new LootPool(entries.toArray(new LootEntry[] {}), new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0,1), "bookpool");        
    	        event.getTable().addPool(pool);	
	        }
	        else
	        {
    			LootPool pool = new LootPool(entries.toArray(new LootEntry[] {}), new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0,1), "bookpool");        
    	        event.getTable().addPool(pool);	
	        }
        }
    
    }
    
    static BookData[] book_array = {
    		new BookData(){{
				author = "Jean Ritchie";
				title = "Blue Diamond Mines";
				pages = new String[] {
						"I remember the ways in the bygone days\nWhen we was all in our prime\nHow us and John L. gave the old man hell\nDown in the Blue Diamond mines\nWhere the whistle would blow \'fore the rooster crowed\nFull two hours before daylight",
						"When a man done his best, and he earned his good rest\nAnd had seventeen dollars at night\n\nIn the mines, in the mines, in the Blue Diamond mines\nI have worked my life away",
						"In the mines, in the mines, in the Blue Diamond mines\nOh, fall on your knees and pray\n\nYou old black gold, you\'ve taken my soul\nYour dust has darkened my home\nAnd now that I\'m old, you\'re turning your back",
						"Where else can an old miner go\nWell, first it\'s Big Block and then Leatherwood\nAnd now it\'s Blue Diamond too\nThe pits are all closed, and it\'s \\\"go find a job\\\"\nWhat else can an old miner do",
						"Your union is dead and you\'re shaking your head\nThey say mining\'s had its day\n\nBut you\'re stripping off my mountain top\nAnd you pay me three dollars a day",
						"Well, you might get a poke of welfare meat\nA little poke of welfare flour\nBut I tell you right now, you won\'t qualify\nLess you work for a quarter an hour\n ",
						"John L. had a dream but it\'s broken, it seems\nOur union is letting us down\nLast week they took away my hospital card\nAnd it\'s \\\"why don\'t you leave this old town\\\"",
						"Well, you go downtown and you hang around\nWell, maybe it ain\'t so bad\nThen you come back home and they meet you at the door\nAnd it\'s \\\"what did you bring me, dad\\\""
				};
    		}},
    		new BookData(){{
				author = "Kate Campbell";
				title = "Mining Camp Blues";
				pages = new String[] {
						"Once i had a daddy\nAnd he worked down in a hole\nOnce i had a daddy\nAnd he worked down in a hole\nDigging and hauling\nHauling that birmingham coal\n",
						"Many times i wondered\nWhen they took my daddy down\nMany times i wondered\nWhen they took my daddy down\nWill he come back to me\nWill they leave him\nIn the ground",
						"Something like the pitcher\nThat they sent down in the well\nSomething like the pitcher\nThat they sent down in the well\nWondering will they break it\nLordy lordy who can tell\n",
						"It was late one evening\nI was standing at that mine\nIt was late one evening\nI was standing at that mine\nForeman said my daddy\nHad gone down\nFor his last last time\n",
						"Now he was a coalminer\nFrom his hat down to his shoes\nNow he was a coalminer\nFrom his hat down to his shoes\nAnd i\'m nearly dying\nWith these mining camp blues"
				};
    		}},
    		new BookData(){{
				author = "Unknown";
				title = "Coal Miner's Blues";
				pages = new String[] {
						"Some blues are just blues, mine are the miner\\'s blues.\nSome blues are just blues, mine are the miner\\'s blues.\nMy troubles are coming by threes and by twos.",
						"Blues and more blues, it\\'s that coal black blues.\nBlues and more blues, it\\'s that coal black blues.\nGot coal in my hair, got coal in my shoes",
						"These blues are so blue, they are the coal black blues.\nThese blues are so blue, they are the coal black blues.\nFor my place will cave in, and my life I will lose.\n",
						"You say they are blues, these old miner\\'s blues.\nYou say they are blues, these old miner\\'s blues.\nNow I must have sharpened these picks that I use.\n",
						"I\'m out with these blues, dirty coal black blues.\nI\'m out with these blues, dirty coal black blues.\nWe\'ll lay off tomorrow with the coal miner\'s blues."
				};
    		}},
    		new BookData(){{
				author = "Sarah Ogan Gunning";
				title = "Come All You Coal Miners";
				pages = new String[] {
						"Come all you coal miners wherever you may be\nAnd listen to a story that I\'ll relate to thee\nMy name is nothing extra, but the truth to you I\'ll tell\nI am a coal miner\'s wife, I\'m sure l wish you well",
						"l was born in old Kentucky, in a coal camp born and bred,\nI know all about the pinto beans, bulldog gravy and cornbread,\nAnd I know how the coal miners work and slave in the coal mines every day\nFor a dollar in the company store, for that is all they pay.\n ",
						"Coal mining is the most dangerous work in our land today\nWith plenty of dirty. slaving work, and very little pay.\nCoal miner, won\'t you wake up, and open your eyes and see\nWhat the dirty capitalist system is doing to you and me.",
						"They take your very life blood, they take our children\'s lives\nThey take fathers away from children, and husbands away from wives.\nOh miner, won\'t you organize wherever you may be\nAnd make this a land of freedom for workers like you and me.",
						"Dear miner, they will slave you \'til you can\'t work no more\nAnd what\'ll you get for your living but a dollar in a company store\nA tumbled-down shack to live in, snow and rain pours in the top.\nYou have to pay the company rent, your dying never stops.",
						"I am a coal miner\'s wife, I\'m sure l wish you well.\nLet\'s sink this capitalist system in the darkest pits of hell."
				};
    		}},
    		new BookData(){{
				author = "Gerrard Winstanley";
				title = "Digger's Song (1894)";
				pages = new String[] {
						"You noble Diggers all, stand up now, stand up now,\nYou noble Diggers all, stand up now,\nThe waste land to maintain, seeing Cavaliers by name\nYour digging do distain and your persons all defame\nStand up now, Diggers all.",
						"Your houses they pull down, stand up now, stand up now,\nYour houses they pull down, stand up now.\nYour houses they pull down to fright poor men in town,\nBut the gentry must come down and the poor shall wear the crown.\nStand up now, Diggers all.",
						"With spades and hoes and ploughs, stand up now, stand up now,\nWith spades and hoes and ploughs, stand up now.\nYour freedom to uphold, seeing Cavaliers are bold\nTo kill you if they could and rights from you withhold.\nStand up now, Diggers all.",
						"Their self-will is their law, stand up now, stand up now,\nTheir self-will is their law, stand up now.\nSince tyranny came in they count it now no sin\nTo make a gaol a gin and to serve poor men therein.\nStand up now, Diggers all.",
						"The gentry are all round, stand up now, stand up now,\nThe gentry are all round, stand up now.\nThe gentry are all round, on each side they are found,\nTheir wisdom\'s so profound to cheat us of the ground.\nStand up now, Diggers all.",
						"The lawyers they conjoin, stand up now, stand up now,\nThe lawyers they conjoin, stand up now,\nTo arrest you they advise, such fury they devise,\nBut the devil in them lies, and hath blinded both their eyes.\nStand up now, Diggers all.",
						"The clergy they come in, stand up now, stand up now,\nThe clergy they come in, stand up now.\nThe clergy they come in and say it is a sin\nThat we should now begin our freedom for to win.\nStand up now, Diggers all.",
						"\'Gainst lawyers and \'gainst priests, stand up now, stand up now,\n\'Gainst lawyers and \'gainst Priests, stand up now.\nFor tyrants are they both even flat against their oath,\nTo grant us they are loath free meat and drink and cloth.\nStand up now, Diggers all.",
						"The club is all their law, stand up now, stand up now,\nThe club is all their law, stand up now.\nThe club is all their law to keep poor folk in awe,\nButh they no vision saw to maintain such a law.\nGlory now, Diggers all."
				};
    		}},
    		new BookData(){{
				author = "Merle Travis";
				title = "Dark As A Dungeon";
				pages = new String[] {
						"Come and listen you fellows, so young and so fine,\nAnd seek not your fortune in the dark, dreary mines.\nIt will form as a habit and seep in your soul,\n\'Till the stream of your blood is as black as the coal",
						"CHORUS: It\'s dark as a dungeon and damp as the dew,\nWhere danger is double and pleasures are few,\nWhere the rain never falls and the sun never shines\nIt\'s dark as a dungeon way down in the mine.",
						"It\'s a-many a man I have seen in my day,\nWho lived just to labor his whole life away.\nLike a fiend with his dope and a drunkard his wine,\nA man will have lust for the lure of the mines.",
						"I hope when I\'m gone and the ages shall roll,\nMy body will blacken and turn into coal.\nThen I\'ll look from the door of my heavenly home,\nAnd pity the miner a-diggin\' my bones.",
						"The midnight, the morning, or the middle of day,\nIs the same to the miner who labors away.\nWhere the demons of death often come by surprise,\nOne fall of the slate and you\'re buried alive."
				};
    		}},
    		new BookData(){{
				author = "Emile Zola";
				title = "Germinal";
				pages = new String[] {
						"And beneath his feet, the deep blows, those obstinate blows of the pick, continued. The mates were all there; he heard them following him at every stride.",
						"Was not that Maheude beneath the beetroots. with bent back and hoarse respiration accompanying the rumble of the ventilator? To left, to right, farther on, he seemed to recognize others beneath the wheatfields, the hedges, the young trees.",
						"Now the April sun, in the open sky, was shining in his glory, and warming the pregnant earth. From its fertile flanks life was leaping out, buds were bursting into green leaves, and the fields were quivering with the growth of the grass.",
						"On every side seeds were swelling, stretching out, cracking the plain, filled by the need of heat and light. An overflow of sap was mixed with whispering voices, the sound of the germs expanding in a great kiss.",
						"Again and again, more and more distinctly, as though they were approaching the soil, the mates were hammering. In the fiery rays of the sun on this youthful morning the country seemed full of that sound.",
						"Men were springing forth, a black avenging army, germinating slowly in the furrows, growing towards the harvests of the next century, and their germination would soon overturn the earth."
				};
    		}},
    		new BookData(){{
				author = "Orville J. Jenks";
				title = "Sprinkle Coal Dust On My Grave";
				pages = new String[] {
						"I\'m just an old coal miner\nAnd I labored for my bread;\nThis story in my memory I hear told;\nFor the sake of wife and baby\nHow a miner risks his life\nFor the price of just a little lump of coal.",
						"Mother Jones is not forgotten\nBy the miners of this field,\nShe\'s gone to rest above, God bless her soul;\nTried to lead the boys to victory.\nBut was punished here in jail,\nFor the price of just a little lump of coal.",
						"When a man has toiled and labored\n\'Til his life it\'s almost gone,\nThen the operator thinks he\'s just a fool:\nThey sneak around and fire him\nJust because he\'s growing old,\nAnd swear they caught him breaking company rules.",
						"Don\'t forget me, little darling,\nWhen they lay me down to rest,\nTell my brothers all these loving words I say;\nLet the flowers be forgotten,\nSprinkle coal dust on my grave\nIn remembrance of the UMWA."
				};
    		}},
    		new BookData(){{
				author = "Anonymous (Written from jail)";
				title = "The Iron Ore Miners";
				pages = new String[] {
						"The Miners of the Iron Range\nKnow there was something wrong\nThey banded all together, yes,\nIn One Big Union strong.",
						"The Steel Trust got the shivers,\nAnd the Mine Guards had some fits,\nThe Miners didn\'t give a damn,\nBut closed down all the pits.",
						"It\'s a long way to monthly pay day.\nIt\'s a long way to go\nIt\'s a long way to monthly pay day,\nFor the Miners need the dough,",
						"Goodbye Steel Trust profits,\nThe Morgans they feel blue.\nIt\'s a long way to monthly pay day\nFor the miners want two.",
						"They worked like hell on contract, yes,\nAnd got paid by the day,\nWhenever they got fired, yes,\nThe bosses held their pay.",
						"But now they want a guarantee\nOf just three bones a day.\nAnd when they quit their lousy jobs\nThey must receive their pay.",
						"It\'s the wrong way to work, by contract\nIt\'s the wrong way to go.\nIt\'s the wrong way to work, by contract\nFor the Miners need the dough.",
						"Goodbye bosses\' handouts, -\nFarewell Hibbing Square.\nIt\'s the wrong way to work, by contract\nYou will find no Miners there.",
						"John Allar died of Mine Guards\' guns\nThe Steel Trust had engaged.\nAt Gilbert, wives and children\nOf the Miners were outraged",
						"No Mine Guards were arrested,\nYet the law is claimed to be\nThe mightiest conception\nOf a big democracy.",
						"It\'s the wrong way to treat the Miners,\nIt\'s the wrong way to go.\nIt\'s the wrong way to treat the Miners,\nAs the Steel Trust soon will know.",
						"God help those dirty Mine Guards,\nThe Miners won\'t forget.\nIt\'s the wrong way to treat the Miners,\nAnd the guards will know that yet.",
						"The Governor got his orders for\nTo try and break the strike.\nHe sent his henchmen on the Range,\nJust what the Steel Trust liked.",
						"The Miners were arrested, yes,\nAnd thrown into the jail,\nBut they had no legal rights\nWhen they presented bail.",
						"It\'s a short way to next election,\nIt\'s a short way to go.\nFor the Governor\'s in deep reflection\nAs to Labor\'s vote, you know.",
						"Goodbye, Dear Old State House,\nFarewell, Bernquist there.\nIt\'s a short way to next election\nAnd you\'ll find no Bernquist there.",
						"Get busy, was the order to\nThe lackeys of the Trust,\nJail all the Organizers\nAnd the Strike will surely bust.",
						"Trump up a charge, a strong one,\nThat will kill all sympathy,\nSo murder was the frame-up,\nAnd one of first degree.",
						"It is this way in Minnesota\nIs it this way you go?\nIt is this way in Minnesota,\nWhere justice has no show.",
						"Wake up all Wage Workers,\nIn One Big Union strong.\nIf we all act unified together,\nWe can right all things that\'s wrong."
				};
    		}},
    		new BookData(){{
				author = "Unknown";
				title = "Only A Miner";
				pages = new String[] {
						"The hard-working miners, their dangers are great,\nMany while mining have met their sad fate,\nWhile doing their duties as miners all do\nShut out from the daylight and their darling ones, too.",
						"He\\'s only a miner been killed in the ground,\nOnly a miner, and one more is found;\nKilled by an accident, no one can tell\nHis mining\\'s all over, poor miner, farewell.",
						"He leaves his dear wife, and little ones, too\nTo earn them a living as miners all do.\nWhile he was working for those whom he loved\nHe met a sad fate from a boulder above.",
						"With a heart full of sorrow we bid him farewell,\nHow soon we may follow, there\'s no one can tell.\nGod pity the miners, protect them as well\nAnd shield them from danger while down in the ground."
				};
    		}},
    		new BookData(){{
				author = "Valerie Smith";
				title = "In Those Mines";
				pages = new String[] {
						"Twenty men saw their maker\\'s face\nTwenty men saw their maker\\'s face\nWhen the old Coeburn mine took their life\\'s breath away\n\nChorus:\nIn those mines\nIn those mines\nIn those cold, dark, dingy, coal-diggin\\' mines",
						"Momma cried and she held to me\nMy momma cried and she held to me\nShe\\'d lost seven sons and I a husband to be\n\nIn those mines\nIn those mines\nIn those cold, dark, dingy, coal-diggin\\' mines",
						"That old coal train sings a lonesome song\nThat old coal train sings a lonesome song\nFor the men that it\'s doomed to those days black and long\n\nIn those mines\nIn those mines\nIn those cold, dark, dingy, coal-diggin\' mines",
						"When I die wontcha throw me down\nWell, when I die wontcha throw me down\nTo be with my lover so deep in the ground\n\nIn those mines\nIn those mines\nIn those cold, dark, dingy, coal-diggin\' mines",
						"In those mines\nIn those mines\nIn those cold, dark, dingy, coal-diggin\' mines\n\nTwenty men saw their maker\'s face\nTwenty men saw their maker\'s face"
				};
    		}},
    		new BookData(){{
				author = "Steve Earle";
				title = "The Mountain";
				pages = new String[] {
						"I was born on this mountain a long time ago\nBefore they knocked down the timber and strip-mined the coal\nWhen you rose in the mornin\\' before it was light\nTo go down in that dark hole and come back up at nigh",
						"I was born on this mountain, this mountain\\'s my home\nAnd she holds me and keeps me from worry and woe\nWell, they took everything that she gave, now they\\'re gone\nBut I\\'ll die on this mountain, this mountain\\'s my home",
						"I was young on this mountain, now I am old\nAnd I knew every holler, every cool swimmin\' hole\nTil one night I lay down and woke up to find\nThat my childhood was over, I went down in the mine.",
						"There\'s a hole in this mountain, it\'s dark and it\'s deep\nAnd God only knows all the secrets it keeps\nThere\'s a chill in the air only miners can feel\nAnd there\'s ghosts in the tunnels that the company sealed."
				};
    		}},
    		new BookData(){{
				author = "Shirley Hill";
				title = "Prayer Of A Miner's Child";
				pages = new String[] {
						"He\\'s just an old coal miner, Lord,\nThat\\'s all he\\'s ever been.\nHe\\'s worked his life away in mines\nWith all the other men",
						"So keep him safe and be with him\nWhen he goes in that mine;\nAnd also help him stay away\nFrom the unemployment line.",
						"Stay by his side in all he does,\nHe\'s a-getting tired, you know.\nHis hair is changing color fast,\nAnd his age has began to show.",
						"I know some day he\'ll leave this earth\nAnd I will stay behind.\nBut when he leaves, I hope it\'s not\nCaused by that old coal mine.",
						"I want the very best for him,\nI don\'t want him to be sad.\nBecause, dear Lord, I think you know,\nThis coal miner is my dad."
				};
    		}},
    		new BookData(){{
				author = "Bertolt Brecht";
				title = "Questions From a Worker Who Reads";
				pages = new String[] {
						"Who built Thebes of the seven gates?\nIn the books you will find the names of kings.\nDid the kings haul up the lumps of rock?\nAnd Babylon, many times demolished\nWho raised it up so many times?",
						"In what houses\nof gold-glittering Lima did the builders live?\nWhere, the evening that the Wall of China was finished\nDid the masons go? Great Rome\nIs full of triumphal arches. Who erected them? Over whom\nDid the Caesars triumph?",
						"Had Byzantium, much praised in song\nOnly palaces for its inhabitants? Even in fabled Atlantis\nThe night the ocean engulfed it\nThe drowning still bawled for their slaves.",
						"The young Alexander conquered India.\nWas he alone?\nCaesar beat the Gauls.\nDid he not have even a cook with him?",
						"Philip of Spain wept when his armada\nWent down. Was he the only one to weep?\nFrederick the Second won the Seven Year\'s War. Who\nElse won it?\n ",
						"Every page a victory.\nWho cooked the feast for the victors?\nEvery ten years a great man?\nWho paid the bill?",
						"So many reports.\nSo many questions."
				};
    		}},
    		new BookData(){{
				author = "Frank Higgins";
				title = "The Testimony of Patience Kershaw";
				pages = new String[] {
						"It\'s good of you to ask me, Sir, to tell you how I spend my days\nDown in a coal black tunnel, Sir, I hurry corves to earn my pay.\nThe corves are full of coal, kind Sir, I push them with my hands and head.\nIt isn\'t lady-like, but Sir, you\'ve got to earn your daily bread.",
						"I push them with my hands and head, and so my hair gets worn away.\nYou see this baldy patch I\'ve got, it shames me like I just can\'t say.",
						"A lady\'s hands are lily white, but mine are full of cuts and segs.\nAnd since I\'m pushing all the time, I\'ve got great big muscles on my legs.",
						"I try to be respectable, but sir, the shame, God save my soul.\nI work with naked, sweating men who curse and swear and hew the coal.",
						"The sights, the sounds, the smells, kind Sir, not even God could sense my shame.\nI say my prayers, but what\'s the use? Tomorrow will be just the same.",
						"Now, sometimes, Sir, I don\'t feel well, my stomach\'s sick, my head it aches.\nI\'ve got to hurry best I can. My knees are weak, my back near breaks.",
						"And then I\'m slow, and then I\'m scared these naked men will batter me.\nBut they\'re not to blame, for if I\'m slow, their families will starve, you see.",
						"Now all the lads, they laugh at me, and Sir, the mirror tells me why.\nPale and dirty can\'t look nice. It doesn\'t matter how hard I try.\nGreat big muscles on my legs, a baldy patch upon my head.\nA lady, Sir? Oh, no, not me! I should\'ve been a boy instead.",
						"I praise your good intentions, Sir, I love your kind and gentle heart\nBut now it\'s 1842, and you and I, we\'re miles apart.",
						"A hundred years and more will pass before we\'re standing side by side\nBut please accept my grateful thanks. God bless you Sir, at least you tried."
				};
    		}},
    		new BookData(){{
				author = "Merle Travis";
				title = "Sixteen Tons";
				pages = new String[] {
						"Some people say a man is made outta mud\nA poor man\'s made outta muscle and blood\nMuscle and blood and skin and bones\nA mind that\'s a-weak and a back that\'s strong",
						"You load sixteen tons, what do you get\nAnother day older and deeper in debt\nSaint Peter don\'t you call me \'cause I can\'t go\nI owe my soul to the company store",
						"I was born one mornin\' when the sun didn\'t shine\nI picked up my shovel and I walked to the mine\nI loaded sixteen tons of number nine coal\nAnd the straw boss said \\\"Well, a-bless my soul\\\"",
						"You load sixteen tons, what do you get\nAnother day older and deeper in debt\nSaint Peter don\'t you call me \'cause I can\'t go\nI owe my soul to the company store",
						"I was born one mornin\', it was drizzlin\' rain\nFightin\' and trouble are my middle name\nI was raised in the canebrake by an ol\' mama lion\nCain\'t no-a high-toned woman make me walk the line",
						"You load sixteen tons, what do you get\nAnother day older and deeper in debt\nSaint Peter don\'t you call me \'cause I can\'t go\nI owe my soul to the company store",
						"If you see me comin\', better step aside\nA lotta men didn\'t, a lotta men died\nOne fist of iron, the other of steel\nIf the right one don\'t a-get you\nThen the left one will",
						"You load sixteen tons, what do you get\nAnother day older and deeper in debt\nSaint Peter don\'t you call me \'cause I can\'t go\nI owe my soul to the company store"
				};
    		}},
    		new BookData(){{
				author = "Tom Waits";
				title = "Underground";
				pages = new String[] {
						"Rattle Big Black Bones\nin the Danger zone\nthere\'s a rumblin\' groan\ndown below\nthere\'s a big dark town\nit\'s a place I\'ve found\nthere\'s a world going on\nUNDERGROUND",
						"they\'re alive, they\'re awake\nwhile the rest of the world is asleep\nbelow the mine shaft roads\nit will all unfold\nthere\'s a world going on\nUNDERGROUND",
						"all the roots hang down\nswing from town to town\nthey are marching around\ndown under your boots\nall the trucks unload\nbeyond the gopher holes\nthere\'s a world going on\nUNDERGROUND"
				};
    		}},
    		new BookData(){{
				author = "Ruthie Gorton";
				title = "Voices From the Mountains";
				pages = new String[] {
						"You\'d better listen to the voices from the mountains,\nTryin\' to tell you what you just might need to know,\n\'Cause the empire\'s days are numbered if you\'re countin\'\nAnd the people just get stronger blow by blow.",
						"You\'d better listen when they talk about strip mining,\nGonna turn the rollin\' hills to acid clay.\nIf you\'re preachin\' all about that silver lining,\nYou\'ll be talkin\' till the hills are stripped away.",
						"You\'d better listen to the cries of the dyin\' miners,\nBetter feel the pain of their children and their wives.\nWe gotta stand and fight together for survival,\nAnd it\'s bound to mean a change in all our lives.",
						"In explosions or from Black Lung they\'ll be dyin\'\nAnd the operator\'s guilty of this crime,\nBut the killin\' won\'t be stopped by all your cryin\'.\nWe gotta fight for what we need, let\'s seize the time.",
						"You\'d better listen to the voices from the mountains,\nTryin\' to tell you what you need to know,\n\'Cause the empire\'s days are numbered if you\'re countin\'\nAnd the people just get stronger blow by blow."
				};
    		}},
    		new BookData(){{
				author = "Unknown";
				title = "What She Aims To Be";
				pages = new String[] {
						"Well Jenny\'s husband left her with babies on her knee\nHe\'s long been gone, nobody\'s come to feed the family\nNow miners are making wages in the county she comes from\nThe children do get hungry, Lord, but Jenny\'s back is strong",
						"She\'s a coal-mining woman and that\'s what she aims to be\nShe breathes that black and dusty air, wears pants upon her knees\nShe\'s proud to be a woman and she\'s working to be free\nShe\'s a coal-mining woman and that\'s what she aims to be",
						"Robin\'s learned it\'s hard to find a job that satisfies\nA woman\'s need to use the strength that in her body lies\nIt\'s a rough and rocky journey from the kitchen to the mines\nBut strength comes from strugglin\', and Robin\'s doing fine",
						"She\'s a coal-mining woman and that\'s what she aims to be\nShe breathes that black and dusty air, wears pants upon her knees\nShe\'s proud to be a woman and she\'s working to be free\nShe\'s a coal-mining woman and that\'s what she aims to be",
						"Life is dark and dangerous down there in those mines\nFear of fire and cave-in are never far behind\nYou could make life easier down in that dangerous hole\nIf you show some respect to those women low and cold"
				};
    		}}
    };
}
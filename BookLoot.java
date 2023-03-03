package com.proceduraldialectics.minerlore;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.loot.StandaloneLootEntry.Builder;
import net.minecraft.loot.functions.SetNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;


@Mod("minerlore")
@Mod.EventBusSubscriber(modid = BookLoot.MOD_ID)
public class BookLoot
{
	public static final String MOD_ID = "minerlore";
	private static final Logger logger = LogManager.getLogger();
	public static final RegistryObject<Item> WRITTENBOOK = RegistryObject.of(new ResourceLocation("minecraft:written_book"), ForgeRegistries.ITEMS);

	public BookLoot()
	{
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	
		MinecraftForge.EVENT_BUS.register(this);
	}

    private void setup(final FMLCommonSetupEvent event)
    {
		logger.debug("Miner Lore loaded.");
    }

	@SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event)
    {
		ResourceLocation name = event.getName();
        if(name.equals(new ResourceLocation("minecraft:chests/igloo_chest")) || 
				name.equals(new ResourceLocation("minecraft:chests/simple_dungeon")) || 
        		name.equals(new ResourceLocation("minecraft:chests/wooldland_mansion"))||
        		name.equals(new ResourceLocation("minecraft:chests/village_blacksmith")) ||
        		name.equals(new ResourceLocation("minecraft:chests/stronghold_library")) ||
        		name.equals(new ResourceLocation("minecraft:chests/stronghold_corridor")) ||
				name.equals(new ResourceLocation("minecraft:chests/stronghold_crossing")) ||
				name.equals(new ResourceLocation("minecraft:chests/abandoned_mineshaft")) ||
				name.equals(new ResourceLocation("champions:champion_loot")))
        {
			LootPool.Builder poolBuilder = LootPool.builder();
			poolBuilder.name("lore");

			for (BookData book : book_array)
	        {
				for (int generation = 0; generation < 4; generation++)
				{
					CompoundNBT tag = new CompoundNBT();
	
					String title = "title";
					tag.putString("author",book.author);
					if (book.title.equals("Questions From a Worker Who Reads") )
						tag.putString(title,"Questions From a Worker");
					else if (book.title.equals("The Testimony of Patience Kershaw") )
						tag.putString(title,"Testimony of Patience Kershaw");
					else tag.putString(title,book.title);
					tag.putInt("generation", generation);
		
					ListNBT pages = new ListNBT();
					pages.add(StringNBT.valueOf(String.format("{\"text\":\"%s\"}", book.title+"\n\n"+book.author)));						        	
					for (String bookpage : book.pages)
					{
						pages.add(StringNBT.valueOf(String.format("{\"text\":\"%s\"}", bookpage)));			
					}
					tag.put("pages", pages);
					
					Builder<?> item = ItemLootEntry.builder(WRITTENBOOK.get());
					if (generation == 0) item.weight(3);
					if (generation == 1) item.weight(30);
					if (generation == 2) item.weight(300);
					if (generation == 3) item.weight(666);
					item.quality(0);
					item.acceptFunction(SetNBT.builder(tag));
	
					poolBuilder.addEntry(item);
				}
			}

	        if(name.equals(new ResourceLocation("minecraft:chests/stronghold_library")))
	        {
				poolBuilder.rolls(new RandomValueRange(8,16));
				poolBuilder.bonusRolls(0,1);
    	        event.getTable().addPool(poolBuilder.build());
	        }
	        else if (name.equals(new ResourceLocation("champions:champion_loot")))
	        {
				poolBuilder.rolls(new RandomValueRange(1));
				poolBuilder.bonusRolls(0,1);
    	        event.getTable().addPool(poolBuilder.build());	
	        }
	        else
	        {
				poolBuilder.rolls(new RandomValueRange(1));
				poolBuilder.bonusRolls(0,1);
				event.getTable().addPool(poolBuilder.build());	
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
				author = "Bertolt Brecht";
				title = "Evolved Human's";
				pages = new String[] {
					"Today I encountered an evolved human, and it was a shock to see just how much the NZS virus had changed this infected. The evolved human was taller and leaner than any of the other infected I had encountered before. Its skin was a sickly grey, and it had a strange, feral look in its eyes.",
					"What struck me most was its strength. The evolved human was easily twice as strong as any other infected I had encountered. It tore through walls and obstacles with ease, and it moved with a grace and speed that was almost supernatural.",
					"The evolved human didn't seem to have any of the weaknesses of the other infected. It wasn't slowed down by wounds or injuries, and it seemed to be able to see and track me even when I was hidden. It was almost as if it had evolved beyond the limitations of the other infected."
				};
    		}},
    		new BookData(){{
				author = "Bertolt Brecht";
				title = "Slasher";
				pages = new String[] {
						"I encountered a new type of infected today, and it was one of the most terrifying things I have ever seen. It was a villager, but it had evolved in a way that I couldn't have imagined. I'm calling it a Slasher for now, because of the large sickle that has grown out of its neck.",
						"The Slasher was fast and deadly. It moved with an agility that I hadn't seen in any of the other infected. And that sickle - it was like nothing I had ever seen before. It was at least a foot long and razor sharp.",
						"The Slasher moved towards me, and I raised my weapon, ready to defend myself. But it was too fast, and it dodged out of the way of my attack. Then it came at me with that sickle, slashing at me with a speed and precision that was impossible to defend against.",
						"I managed to dodge out of the way, but I could feel the wind from the sickle as it passed by me. The Slasher continued to attack, moving with a speed and ferocity that I had never seen before."
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
				author = "Bertolt Brecht";
				title = "Spitter";
				pages = new String[] {
					"I encountered a new type of infected today. It was a villager, but it had evolved in a way that I didn't think was possible. I'm calling it a Spitter for now, because of its ability to shoot a stream of acid from its trunk-like tongue.",
					"The Spitter was unlike any infected I had seen before. Its jaw was completely disjointed, and it seemed to have no control over the movements of its tongue. It flailed around, lashing out at anything in its path, and then suddenly it shot a stream of acid towards me.",
					"I managed to dodge out of the way just in time, but I could feel the heat from the acid on my skin. It was like nothing I had ever felt before. The Spitter continued to spit acid, hitting anything and everything in its path."
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
				author = "Bertolt Brecht";
				title = "phayrius";
				pages = new String[] {
					"I saw something today that I can't explain. It was like nothing I've ever seen before. It was a creature, but it looked like a bat...or at least, like something that used to be a bat.",	
					"I don't know what it's called, but I've decided to call it a Phayrius for now. It was flying through the air, flapping its wings in a way that didn't quite look right. And then, I realized that it wasn't flapping its wings at all - it was gliding through the air with what looked like pure skin stretched between its arms and body.",
					"I watched in horror as the Phayrius swooped down on a group of wandering villagers. They didn't stand a chance - the Phayrius was fast and agile, and it carried a group of infected with it that ambushed the villagers from behind.",
					"What scares me the most is that the Phayrius is unlike anything we've seen before. It can fly, which means that we're no longer safe on the ground. And it can carry infected with it, which means that we have to be even more vigilant than before.",
					"I don't know what to do about the Phayrius, but I do know that we can't let our guard down. It's a reminder that the NZS virus is still evolving, and it's only going to get more dangerous from here on out."
				};
    		}},
    		new BookData(){{
				author = "Valerie Smith";
				title = "Necrotizing fasciitis";
				pages = new String[] {
						"Regarding the evolution of Necrotizing fasciitis, it is believed to have emerged as a result of various bacterial strains evolving to become more virulent and resistant to antibiotics. Some of the common bacteria that cause Necrotizing fasciitis include Streptococcus pyogenes (group A streptococcus), Vibrio vulnificus, and Clostridium perfringens.",
						"These bacteria can enter the body through breaks in the skin, such as cuts, scrapes, or surgical wounds, and release toxins that damage surrounding tissues. This can lead to the rapid spread of the infection and the destruction of tissue, which can cause severe pain, swelling, fever, and other symptoms.",
						"While the exact mechanism by which Necrotizing fasciitis evolved is not fully understood, it is believed that environmental factors, such as changes in temperature, humidity, and antibiotic use, may have played a role in the evolution of these bacteria. Additionally, genetic mutations and the acquisition of new genetic material through horizontal gene transfer may have also contributed to the evolution of these bacterial strains.",
						"In conclusion, Necrotizing fasciitis is a severe bacterial infection that can cause significant harm to the body. However, it is not known to turn dead people into reanimated husks.",
				};
    		}},
    		new BookData(){{
				author = "Sarah Earle";
				title = "Sarah's diary";
				pages = new String[] {
						"Day 1: March 1, 2028: Today marks the beginning of my journey to find other survivors of the outbreak. I've heard rumors of a safe zone on the outskirts of the city, and I am determined to find it. I know it won't be easy, but I have to try.",
						"Day 3: March 3, 2028: I've been walking for two days now, and I am exhausted. I haven't encountered any infected individuals yet, but I am constantly on guard. I miss my family and my old life, but I have to keep moving forward.",
						"Day 7: March 7, 2028: I finally found the safe zone, and it's a relief to be among other survivors. The community is small, but we are all working together to build a new life. I've been assigned to the scavenging team, and we go out daily to collect supplies.",
						"Day 14: March 14, 2028: I've started to develop close friendships with some of the other survivors, and it feels good to have a sense of belonging again. We all share our stories and our struggles, and it helps us to feel less alone in this new world.",
						"Day 21: March 21, 2028: We encountered a group of infected individuals on our scavenging run today, and it was a close call. One of our team members was bitten, and we had to make the difficult decision to leave them behind. It's a harsh reality, but we have to do whatever it takes to survive.",
						"Day 28: March 28, 2028: I've started to notice some changes in myself. My hair is falling out, and my skin has become dry and scaly. I am worried that I might be infected, but I am too scared to get tested. I don't know what I would do if I found out I had the virus.",
						"Day 30: March 30, 2028: I finally mustered up the courage to get tested, and the results came back negative. It's a relief, but I can't help but feel guilty for doubting myself. I know that many others aren't as lucky, and it's a sobering reminder of the fragility of life in this new world."
						
				};
    		}},
    		new BookData(){{
				author = "Dr.John lee";
				title = "Necrotizing fasciitisV2 tests";
				pages = new String[] {
						"Day 1: March 1, 2023: Today marks the first day of our investigation into the new outbreak of Necrotizing fasciitis V2. We have received several cases of patients with severe soft tissue damage and are currently in the process of analyzing samples to identify the bacterial strain responsible.",
						"Day 2: March 2, 2023: We have identified the bacterial strain responsible for the outbreak as a new variant of Necrotizing fasciitis. The bacteria appear to be more virulent and resistant to antibiotics than previous strains we have encountered.",
						"Day 3-6: March 3-6, 2023: We are continuing our investigations into the new strain of Necrotizing fasciitis. Our team is working around the clock to study the bacterial samples and develop new treatment strategies.",
						"Day 7-9: March 7-9, 2023: The number of patients with Necrotizing fasciitis V2 has increased significantly. We are seeing more severe cases and are struggling to keep up with the demand for treatment.",
						"Day 10-12: March 10-12, 2023: We have observed some unusual behavior in some of the infected patients. They seem to be exhibiting symptoms of delirium and aggression. We are monitoring this closely and will continue to investigate.",
						"Day 13: March 13, 2023: The situation is becoming increasingly dire. The number of infected patients has skyrocketed, and our treatment options are becoming limited. We need to find a way to contain the spread of the infection.",
						"Day 14: March 14, 2023: After extensive research and discussion with my colleagues, I propose that we name the new variant of Necrotizing fasciitis Necrotizing Zombiefication Syndrome (NZS) due to the strange behavior exhibited by some of the infected patients resembling that of zombies.",
						"Day 22: March 23, 2023: We have been working tirelessly to find a solution to the Necrotizing Zombiefication Syndrome (NZS) outbreak, but we have not made any significant progress. The number of infected patients continues to increase, and our resources are running low.",
						"Day 23-25: March 24-26, 2023: We have been conducting experiments to test various treatment options for NZS. Unfortunately, none of the treatments we have tried so far have been effective. We are considering more experimental approaches, but we need to ensure their safety before moving forward.",
						"Day 26-29: March 27-30, 2023: We have noticed a trend in the infected patients. Those who exhibit the aggressive behavior seem to have a higher mortality rate. We are investigating this further to determine if there is a correlation between the behavior and the severity of the infection.",
						"Day 30-32: March 31-April 2, 2023: We have developed a new treatment protocol that shows some promise. We are cautiously optimistic and will begin testing it on infected patients soon. However, we need to ensure the safety of the treatment before administering it widely."
				};
    		}},
    		new BookData(){{
				author = "Bertolt Brecht";
				title = "infected human";
				pages = new String[] {
						"The human infected are those who were once fully human but have been transformed by the NZS virus. At first glance, they may appear to be normal humans, but upon closer inspection, it becomes clear that they have undergone some drastic changes.",
						"One of the most noticeable changes is the color of their skin. It takes on a sickly shade of gray, almost like the color of ash. Their eyes also change - they become lifeless and devoid of any emotion. It's as if the person who once inhabited the body has been replaced by something else entirely.",
						"In terms of behavior, the human infected move in strange, jerky motions. It's almost as if they're being controlled by something else, like a puppet on strings. They shuffle along, almost zombie-like, and their movements are often accompanied by strange, guttural noises.",
						"The human infected are also extremely aggressive. They will attack anyone who gets in their way, and they seem to take pleasure in causing harm to others. They're not very intelligent, however, and can be outsmarted with some clever maneuvering.",
						"One of the most terrifying things about the human infected is that they were once just like us. They were people with hopes, dreams, and families. Now, they're little more than monsters. It's a harsh reminder of just how fragile our humanity really is."
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
				author = "Dr. Samantha Lee";
				title = "10 days";
				pages = new String[] {
						"Day 1: November 1, 2024: Today marks my 210 day working on the Necrotizing Zombiefication Syndrome outbreak. We have made some progress, but it seems like every time we take a step forward, the virus evolves and becomes more dangerous.",
						"Day 2: November 2, 2024: Our latest test results show that the virus has mutated again, and the infected individuals are exhibiting even more aggressive behavior than before. It's becoming increasingly dangerous to continue our research.",
						"Day 3: November 3, 2024: We have been informed that a nearby facility has been breached, and several infected individuals have escaped. This is a major cause for concern, as it could lead to a further spread of the virus.",
						"Day 4: November 4, 2023: We have been instructed to increase our security measures and to take additional precautions to avoid infection. Our team is feeling the strain, and it's becoming harder to focus on our work.",
						"Day 5: November 5, 2023: We have received news that a group of infected individuals has breached the quarantine zone and is heading towards our facility. We are currently preparing for a potential breach and are making plans to evacuate if necessary. ",
						"Day 6: November 6, 2023: The infected group has breached our perimeter, and we are now under attack. We have activated our emergency protocols and are working to contain the situation. It's clear that the virus has evolved and become more dangerous, and we are struggling to keep up.",
						"Day 7: November 7, 2023: The situation is getting worse by the hour. Our team is exhausted and overwhelmed, and we are facing increasing difficulty in keeping the infected individuals at bay.",
						"Day 8: November 8, 2023: We have lost contact with several members of our team, and it's unclear if they are alive or dead. We are doing everything we can to maintain our research and protect ourselves, but it's becoming increasingly dangerous to continue.",
						"Day 9: November 9, 2023: The infected group has breached our inner defenses, and it's clear that we can no longer contain the situation. We have made the difficult decision to abandon our research and evacuate the facility. ",
						"Day 10: November 10, 2023: As I write this final report, I am on the run from the infected individuals who breached our facility. We have lost everything, including our research, and it's unclear what the future holds. I urge everyone to take this outbreak seriously and to take every precaution to avoid infection. The virus is evolving, and it's more dangerous than ever. We must work together to find a cure and prevent further loss of life."
				};	
    		}},
    		new BookData(){{
				author = "Dr. Samantha Lee";
				title = "Report: September 18, 2024";
				pages = new String[] {
						"It has been over 200 days since the first cases of Necrotizing Zombiefication Syndrome were reported. The situation has only gotten worse since then, and we are still struggling to find a solution to this deadly outbreak.",
						"Our team has been working tirelessly to understand the virus and develop a cure, but progress has been slow. Many of our colleagues have fallen ill or died from the infection, including the scientist who wrote the previous report. His sacrifice will not be forgotten, and we will continue his work in his memory.",
						"We have seen some success in developing a vaccine that can prevent the infection from taking hold, but we are still struggling to produce enough doses to make a significant impact. Additionally, the vaccine is not effective for those who are already infected.",
						"The infected population continues to grow, and we are facing increasing challenges in containing the spread of the virus. Many regions have been placed under strict quarantine measures, and travel has been severely restricted.",
						"The infected individuals have become more aggressive and difficult to control, which has made it challenging for medical staff and researchers to work safely. Our team has had to make difficult decisions and sacrifices in order to continue our work.",
						"In conclusion, the situation remains dire, and we are facing unprecedented challenges in our efforts to control this outbreak. We will continue our work, however, and we remain hopeful that we will find a solution to this devastating disease. We urge everyone to take every precaution to avoid infection and to support those who are working tirelessly to find a cure."
				};
    		}},
    		new BookData(){{
				author = "Sarah Earle";
				title = "2033";
				pages = new String[] {
						"Day 1: January 1, 2033: My name is Sarah, and I am a survivor of the Necrotizing Zombiefication Syndrome outbreak. It has been ten years since the virus first appeared, and I still can't believe I made it this far. I've seen things I wish I could unsee, but I am still here, and that's something to be grateful for. ",
						"Day 10: January 10, 2033: I have learned that the older the infected individual, the more mutated they are. It's like the virus has been evolving within them, and they become more dangerous and difficult to kill. It's a terrifying thought, but it's something I have to keep in mind as I navigate this new world.",
						"Day 20: January 20, 2033: I've been scavenging for supplies with a small group of survivors, and we stumbled upon an old lab. We found some notes left behind by the scientists who were studying the virus. It seems like the virus was engineered in a lab, and it was meant to be a weapon. I can't believe humans could be capable of creating something like this. ",
						"Day 30: January 30, 2033: We encountered some infected individuals who were different from the ones we've seen before. They were faster, stronger, and their skin had mutated into a hard, scaly texture. We barely made it out alive, and it's clear that the virus is still evolving.",
						"Day 40: February 9, 2033: I found an abandoned journal from a scientist who was studying the virus during the early days of the outbreak. It's interesting to read about their findings and how they were struggling to understand the virus. It's a reminder that we are all human, and we are all trying to survive.",
						"Day 50: February 19, 2033: We encountered a group of infected individuals who were elderly, and they were unlike any other infected we've seen before. Their skin had turned black, and their eyes glowed a bright red. They were fast and agile, and it took all of our strength to take them down.",
						"Day 60: March 1, 2033: I am starting to notice some changes in myself. I've been coughing more, and I feel weaker than before. I don't know if it's just fatigue or if I've been infected with the virus. It's a scary thought, but I am trying not to panic.",
						"Day 61: March 2, 2033: I woke up with a fever today, and my skin has turned a sickly shade of green. I know what this means - I've been infected with the virus. I am not sure how long I have left, but I am determined to make the most of it.",	
						"Day 62: March 3, 2033: I am starting to feel the effects of the virus. My skin is turning hard and scaly, and my eyesight is getting worse. I don't know how much longer I can hold on, but I am trying to stay strong.",
						"Day 63: March 4, 2033: I can feel myself slipping away. The virus has taken hold of my body, and I can't fight it any longer. I hope that whoever finds this journal will remember that we are all human, and we all have the capacity for both good and evil. We must continue to fight for our survival and for the survival of the human race."
				};
    		}}
    };
}

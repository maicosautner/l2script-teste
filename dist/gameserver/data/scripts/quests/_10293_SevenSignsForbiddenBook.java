package quests;

import l2s.gameserver.model.Player;
import l2s.gameserver.model.instances.NpcInstance;
import l2s.gameserver.model.quest.Quest;
import l2s.gameserver.model.quest.QuestState;
import l2s.gameserver.utils.Location;

/**
 * @author pchayka
 */
public class _10293_SevenSignsForbiddenBook extends Quest
{
	private static final int Elcardia = 32784;
	private static final int Sophia = 32596;

	private static final int SophiaInzone1 = 32861;
	private static final int ElcardiaInzone1 = 32785;
	private static final int SophiaInzone2 = 32863;

	private static final int SolinasBiography = 17213;

	private static final int[] books = {32809, 32810, 32811, 32812, 32813};

	private static final int EXP_REWARD = 15000000;	private static final int SP_REWARD = 3600; 	public _10293_SevenSignsForbiddenBook()
	{
		super(PARTY_NONE, ONETIME);
		addStartNpc(Elcardia);
		addTalkId(Sophia, SophiaInzone1, ElcardiaInzone1, SophiaInzone2);
		addTalkId(books);
		addQuestItem(SolinasBiography);
		
		addLevelCheck("elcardia_q10293_0.htm", 81);
		addQuestCompletedCheck("elcardia_q10293_0.htm", 10292);			
	}

	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		Player player = st.getPlayer();
		String htmltext = event;
		if(event.equalsIgnoreCase("elcardia_q10293_3.htm"))
		{
			st.setCond(1);
		}
		else if(event.equalsIgnoreCase("enter_library"))
		{
			enterInstance(st, 156);
			return null;
		}
		else if(event.equalsIgnoreCase("sophia2_q10293_4.htm"))
		{
			st.setCond(2);
		}
		else if(event.equalsIgnoreCase("sophia2_q10293_8.htm"))
		{
			st.setCond(4);
		}
		else if(event.equalsIgnoreCase("elcardia2_q10293_4.htm"))
		{
			st.setCond(5);
		}
		else if(event.equalsIgnoreCase("sophia2_q10293_10.htm"))
		{
			st.setCond(6);
		}
		else if(event.equalsIgnoreCase("teleport_in"))
		{
			Location loc = new Location(37348, -50383, -1168);
			st.getPlayer().teleToLocation(loc);
			teleportElcardia(player);
			return null;
		}
		else if(event.equalsIgnoreCase("teleport_out"))
		{
			Location loc = new Location(37205, -49753, -1128);
			st.getPlayer().teleToLocation(loc);
			teleportElcardia(player);
			return null;
		}
		else if(event.equalsIgnoreCase("book_q10293_3a.htm"))
		{
			st.giveItems(SolinasBiography, 1);
			st.setCond(7);
		}
		else if(event.equalsIgnoreCase("elcardia_q10293_7.htm"))
		{
			st.addExpAndSp(EXP_REWARD, SP_REWARD);
			st.finishQuest();
		}
		return htmltext;
	}

	@Override
	public String onTalk(NpcInstance npc, QuestState st)
	{
		String htmltext = NO_QUEST_DIALOG;
		int npcId = npc.getNpcId();
		int cond = st.getCond();
		Player player = st.getPlayer();
		if(!player.isBaseClassActive())
			return "no_subclass_allowed.htm";
		switch(npcId)
		{
			case Elcardia:
				if(cond == 0)
					htmltext = "elcardia_q10293_1.htm";
				else if(cond >= 1 && cond < 8)
					htmltext = "elcardia_q10293_4.htm";
				else if(cond == 8)
					htmltext = "elcardia_q10293_5.htm";
				break;
			case Sophia:
				if(cond >= 1 && cond <= 7)
					htmltext = "sophia_q10293_1.htm";
				break;
			case SophiaInzone1:
				if(cond == 1)
					htmltext = "sophia2_q10293_1.htm";
				else if(cond == 2 || cond == 4 || cond == 7 || cond == 8)
					htmltext = "sophia2_q10293_5.htm";
				else if(cond == 3)
					htmltext = "sophia2_q10293_6.htm";
				else if(cond == 5)
					htmltext = "sophia2_q10293_9.htm";
				else if(cond == 6)
					htmltext = "sophia2_q10293_11.htm";
				break;
			case ElcardiaInzone1:
				if(cond == 1 || cond == 3 || cond == 5 || cond == 6)
					htmltext = "elcardia2_q10293_1.htm";
				else if(cond == 2)
				{
					st.setCond(3);
					htmltext = "elcardia2_q10293_2.htm";
				}
				else if(cond == 4)
					htmltext = "elcardia2_q10293_3.htm";
				else if(cond == 7)
				{
					st.setCond(8);
					htmltext = "elcardia2_q10293_5.htm";
				}
				else if(cond == 8)
					htmltext = "elcardia2_q10293_5.htm";

				break;
			case SophiaInzone2:
				if(cond == 6 || cond == 7)
					htmltext = "sophia3_q10293_1.htm";
				else if(cond == 8)
					htmltext = "sophia3_q10293_4.htm";
				break;
			// Books
			case 32809:
				htmltext = "book_q10293_3.htm";
				break;
			case 32811:
				htmltext = "book_q10293_1.htm";
				break;
			case 32812:
				htmltext = "book_q10293_2.htm";
				break;
			case 32810:
				htmltext = "book_q10293_4.htm";
				break;
			case 32813:
				htmltext = "book_q10293_5.htm";
				break;

		}
		return htmltext;
	}

	private void teleportElcardia(Player player)
	{
		for(NpcInstance n : player.getReflection().getNpcs())
			if(n.getNpcId() == ElcardiaInzone1)
				n.teleToLocation(Location.findPointToStay(player, 60));
	}
}
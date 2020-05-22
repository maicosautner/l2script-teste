package quests;

import l2s.commons.util.Rnd;
import l2s.gameserver.model.instances.NpcInstance;
import l2s.gameserver.model.quest.Quest;
import l2s.gameserver.model.quest.QuestState;

/**
 * @author pchayka
 *         Начинается по двойному клику по итему 15537
 */

public class _464_Oath extends Quest
{
	private static final int Sophia = 32596;
	private static final int Seresin = 30657;
	private static final int Holly = 30839;
	private static final int Flauen = 30899;
	private static final int Dominic = 31350;
	private static final int Chichirin = 30539;
	private static final int Tobias = 30297;
	private static final int Blacksmith = 31960;
	private static final int Agnes = 31588;

	private static final int BookofSilence1 = 15538;
	private static final int BookofSilence2 = 15539;

	public _464_Oath()
	{
		super(PARTY_NONE, REPEATABLE);
		addTalkId(Sophia, Seresin, Holly, Flauen, Dominic, Chichirin, Tobias, Blacksmith, Agnes);
		addQuestItem(BookofSilence1, BookofSilence2);
		addLevelCheck(NO_QUEST_DIALOG, 82);
	}

	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("bookowner"))
		{
			switch(Rnd.get(2, 8))
			{
				case 2:
					st.setCond(2);
					htmltext = "sophia_q464_04a.htm";
					break;
				case 3:
					st.setCond(3);
					htmltext = "sophia_q464_04b.htm";
					break;
				case 4:
					st.setCond(4);
					htmltext = "sophia_q464_04c.htm";
					break;
				case 5:
					st.setCond(5);
					htmltext = "sophia_q464_04d.htm";
					break;
				case 6:
					st.setCond(6);
					htmltext = "sophia_q464_04e.htm";
					break;
				case 7:
					st.setCond(7);
					htmltext = "sophia_q464_04f.htm";
					break;
				case 8:
					st.setCond(8);
					htmltext = "sophia_q464_04g.htm";
					break;
				case 9:
					st.setCond(9);
					htmltext = "sophia_q464_04h.htm";
					break;
			}
			st.takeAllItems(BookofSilence1);
			st.giveItems(BookofSilence2, 1);
		}
		else if(event.equalsIgnoreCase("request_reward"))
		{
			switch(npc.getNpcId())
			{
				case Seresin:
					htmltext = "seresin_q464_02.htm";
					st.giveItems(ADENA_ID, 42910);
					st.addExpAndSp(15449, 5);
					break;
				case Holly:
					htmltext = "holly_q464_02.htm";
					st.giveItems(ADENA_ID, 52599);
					st.addExpAndSp(189377, 45);
					break;
				case Flauen:
					htmltext = "flauen_q464_02.htm";
					st.giveItems(ADENA_ID, 69210);
					st.addExpAndSp(249180, 59);
					break;
				case Dominic:
					htmltext = "dominic_q464_02.htm";
					st.giveItems(ADENA_ID, 69210);
					st.addExpAndSp(249180, 59);
					break;
				case Chichirin:
					htmltext = "chichirin_q464_02.htm";
					st.giveItems(ADENA_ID, 169442);
					st.addExpAndSp(19408, 5);
					break;
				case Tobias:
					htmltext = "tobias_q464_02.htm";
					st.giveItems(ADENA_ID, 210806);
					st.addExpAndSp(24146, 5);
					break;
				case Blacksmith:
					htmltext = "blacksmith_q464_02.htm";
					st.giveItems(ADENA_ID, 42910);
					st.addExpAndSp(15449, 5);
					break;
				case Agnes:
					htmltext = "agnes_q464_02.htm";
					st.giveItems(ADENA_ID, 42910);
					st.addExpAndSp(15449, 5);
					break;
			}
			st.takeAllItems(BookofSilence2);
			st.finishQuest();
		}
		return htmltext;
	}

	@Override
	public String onTalk(NpcInstance npc, QuestState st)
	{
		String htmltext = NO_QUEST_DIALOG;
		int cond = st.getCond();
		int npcId = npc.getNpcId();
		if(npcId == Sophia)
		{
			switch(cond)
			{
				case 1:
					htmltext = "sophia_q464_01.htm";
					break;
				case 2:
					htmltext = "sophia_q464_05a.htm";
					break;
				case 3:
					htmltext = "sophia_q464_05b.htm";
					break;
				case 4:
					htmltext = "sophia_q464_05c.htm";
					break;
				case 5:
					htmltext = "sophia_q464_05d.htm";
					break;
				case 6:
					htmltext = "sophia_q464_05e.htm";
					break;
				case 7:
					htmltext = "sophia_q464_05f.htm";
					break;
				case 8:
					htmltext = "sophia_q464_05g.htm";
					break;
				case 9:
					htmltext = "sophia_q464_05h.htm";
					break;
			}
		}
		else if(npcId == Seresin)
		{
			if(cond == 2)
				htmltext = "seresin_q464_01.htm";
		}
		else if(npcId == Holly)
		{
			if(cond == 3)
				htmltext = "holly_q464_01.htm";
		}
		else if(npcId == Flauen)
		{
			if(cond == 4)
				htmltext = "flauen_q464_01.htm";
		}
		else if(npcId == Dominic)
		{
			if(cond == 5)
				htmltext = "dominic_q464_01.htm";
		}
		else if(npcId == Chichirin)
		{
			if(cond == 6)
				htmltext = "chichirin_q464_01.htm";
		}
		else if(npcId == Tobias)
		{
			if(cond == 7)
				htmltext = "tobias_q464_01.htm";
		}
		else if(npcId == Blacksmith)
		{
			if(cond == 8)
				htmltext = "blacksmith_q464_01.htm";
		}
		else if(npcId == Agnes)
		{
			if(cond == 9)
				htmltext = "agnes_q464_01.htm";
		}
		return htmltext;
	}
}
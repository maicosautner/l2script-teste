package quests;

import l2s.gameserver.model.instances.NpcInstance;
import l2s.gameserver.model.quest.Quest;
import l2s.gameserver.model.quest.QuestState;

public class _623_TheFinestFood extends Quest
{
	public final int JEREMY = 31521;

	public static final int HOT_SPRINGS_BUFFALO = 21315;
	public static final int HOT_SPRINGS_FLAVA = 21316;
	public static final int HOT_SPRINGS_ANTELOPE = 21318;

	public static final int LEAF_OF_FLAVA = 7199;
	public static final int BUFFALO_MEAT = 7200;
	public static final int ANTELOPE_HORN = 7201;

	public _623_TheFinestFood()
	{
		super(PARTY_ALL, REPEATABLE);

		addStartNpc(JEREMY);

		addTalkId(JEREMY);

		addKillId(HOT_SPRINGS_BUFFALO);
		addKillId(HOT_SPRINGS_FLAVA);
		addKillId(HOT_SPRINGS_ANTELOPE);

		addQuestItem(BUFFALO_MEAT);
		addQuestItem(LEAF_OF_FLAVA);
		addQuestItem(ANTELOPE_HORN);
		addLevelCheck("jeremy_q0623_0103.htm", 71/*, 78*/);
	}

	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("quest_accept"))
		{
			htmltext = "jeremy_q0623_0104.htm";
			st.setCond(1);
		}
		else if(event.equalsIgnoreCase("623_3"))
		{
			htmltext = "jeremy_q0623_0201.htm";
			st.takeItems(LEAF_OF_FLAVA, -1);
			st.takeItems(BUFFALO_MEAT, -1);
			st.takeItems(ANTELOPE_HORN, -1);
			st.giveItems(ADENA_ID, 73000);
			st.finishQuest();
		}
		return htmltext;
	}

	@Override
	public String onTalk(NpcInstance npc, QuestState st)
	{
		String htmltext = NO_QUEST_DIALOG;
		int npcId = npc.getNpcId();
		// На случай любых ошибок, если предметы есть - квест все равно пройдется.
		if(summ(st) >= 300)
			st.setCond(2);
		int cond = st.getCond();
		if(npcId == JEREMY)
			if(cond == 0)
				htmltext = "jeremy_q0623_0101.htm";
			else if(cond == 1 && summ(st) < 300)
				htmltext = "jeremy_q0623_0106.htm";
			else if(cond == 2 && summ(st) >= 300)
				htmltext = "jeremy_q0623_0105.htm";
		return htmltext;
	}

	@Override
	public String onKill(NpcInstance npc, QuestState st)
	{
		int cond = st.getCond();
		int npcId = npc.getNpcId();
		if(cond == 1) // Like off C4 PTS AI (убрали  && Rnd.chance(50))
			if(npcId == HOT_SPRINGS_BUFFALO)
			{
				if(st.getQuestItemsCount(BUFFALO_MEAT) < 100)
				{
					st.giveItems(BUFFALO_MEAT, 1);
					if(st.getQuestItemsCount(BUFFALO_MEAT) == 100)
					{
						if(summ(st) >= 300)
							st.setCond(2);
					}
					else
						st.playSound(SOUND_ITEMGET);
				}
			}
			else if(npcId == HOT_SPRINGS_FLAVA)
			{
				if(st.getQuestItemsCount(LEAF_OF_FLAVA) < 100)
				{
					st.giveItems(LEAF_OF_FLAVA, 1);
					if(st.getQuestItemsCount(LEAF_OF_FLAVA) == 100)
					{
						if(summ(st) >= 300)
							st.setCond(2);
					}
					else
						st.playSound(SOUND_ITEMGET);
				}
			}
			else if(npcId == HOT_SPRINGS_ANTELOPE)
				if(st.getQuestItemsCount(ANTELOPE_HORN) < 100)
				{
					st.giveItems(ANTELOPE_HORN, 1);
					if(st.getQuestItemsCount(ANTELOPE_HORN) == 100)
					{
						if(summ(st) >= 300)
							st.setCond(2);
					}
					else
						st.playSound(SOUND_ITEMGET);
				}
		return null;
	}

	private long summ(QuestState st)
	{
		return st.getQuestItemsCount(LEAF_OF_FLAVA) + st.getQuestItemsCount(BUFFALO_MEAT) + st.getQuestItemsCount(ANTELOPE_HORN);
	}
}
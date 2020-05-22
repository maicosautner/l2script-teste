package quests;

import l2s.gameserver.model.instances.NpcInstance;
import l2s.gameserver.model.quest.Quest;
import l2s.gameserver.model.quest.QuestState;

public class _110_ToThePrimevalIsle extends Quest
{
	// NPC
	int ANTON = 31338;
	int MARQUEZ = 32113;

	// QUEST ITEM and REWARD
	int ANCIENT_BOOK = 8777;

	private static final int EXP_REWARD = 887732;	private static final int SP_REWARD = 213; 	public _110_ToThePrimevalIsle()
	{
		super(PARTY_NONE, ONETIME);

		addStartNpc(ANTON);
		addTalkId(ANTON);

		addTalkId(MARQUEZ);
		
		addLevelCheck("scroll_seller_anton_q0110_02.htm", 75);
	}

	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		String htmltext = event;
		if(event.equals("1"))
		{
			htmltext = "scroll_seller_anton_q0110_05.htm";
			st.setCond(1);
			st.giveItems(ANCIENT_BOOK, 1);
		}
		else if(event.equals("2") && st.getQuestItemsCount(ANCIENT_BOOK) > 0)
		{
			htmltext = "marquez_q0110_05.htm";
			st.giveItems(ADENA_ID, 189208);
			st.addExpAndSp(EXP_REWARD, SP_REWARD);
			st.takeItems(ANCIENT_BOOK, -1);
			st.finishQuest();
		}
		else if(event.equals("3"))
		{
			htmltext = "marquez_q0110_06.htm";
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
		if(npcId == ANTON)
		{
			if(cond == 0)
				htmltext = "scroll_seller_anton_q0110_01.htm";
			if(cond == 1)
				htmltext = "scroll_seller_anton_q0110_07.htm";
		}
		else if(npcId == MARQUEZ)
		{
			if(cond == 1)
			{
				if(st.getQuestItemsCount(ANCIENT_BOOK) == 0)
					htmltext = "marquez_q0110_07.htm";
				else
					htmltext = "marquez_q0110_01.htm";
			}
		}
		return htmltext;
	}

	@Override
	public String onKill(NpcInstance npc, QuestState st)
	{
		return null;
	}
}
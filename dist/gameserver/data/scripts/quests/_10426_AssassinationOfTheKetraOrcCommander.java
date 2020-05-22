package quests;

import l2s.gameserver.model.Player;
import l2s.gameserver.model.base.ClassType;
import l2s.gameserver.model.base.Race;
import l2s.gameserver.model.instances.NpcInstance;
import l2s.gameserver.model.quest.Quest;
import l2s.gameserver.model.quest.QuestState;
import org.apache.commons.lang3.ArrayUtils;
import l2s.commons.util.Rnd;
import l2s.gameserver.ai.CtrlEvent;
import l2s.gameserver.network.l2.components.NpcString;

public class _10426_AssassinationOfTheKetraOrcCommander extends Quest
{
    //Квестовые персонажи
    private static final int LUKONES = 33852;

    //Монстры
    private static final int MOB = 27500;

	private static final int EXP_REWARD = 327446943;
	private static final int SP_REWARD = 1839; 

	public _10426_AssassinationOfTheKetraOrcCommander()
	{
		super(PARTY_NONE, ONETIME);
		addStartNpc(LUKONES);
		addTalkId(LUKONES);
		addKillId(MOB);
		addRaceCheck("rugoness_q10426_02a.htm", Race.HUMAN, Race.ELF, Race.DARKELF, Race.ORC, Race.DWARF, Race.KAMAEL);
		addLevelCheck("rugoness_q10426_02.htm", 76, 80);
		addQuestCompletedCheck("rugoness_q10426_02.htm", 10425);
		addClassTypeCheck("rugoness_q10426_02.htm", ClassType.MYSTIC);
	}

	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		if(event.equalsIgnoreCase("rugoness_q10426_05.htm"))
		{
			st.setCond(1);
		}

		else if(event.equalsIgnoreCase("rugoness_q10426_08.htm"))
		{
			st.addExpAndSp(EXP_REWARD, SP_REWARD);
			st.finishQuest();
		}

		return event;
	}

	@Override
	public String onTalk(NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getCond();
		String htmltext = NO_QUEST_DIALOG;
		switch (npcId)
		{
			case LUKONES:
				if (cond == 0)
					htmltext = "rugoness_q10426_01.htm";
				else if (cond == 1)
					htmltext = "rugoness_q10426_06.htm";
				else if (cond == 2)
					htmltext = "rugoness_q10426_07.htm";
				break;
		}
		return htmltext;
	}

	@Override
	public String onKill(NpcInstance npc, QuestState qs)
	{
		if(qs.getCond() != 1)
			return null;
		
		qs.setCond(2);
		return null;
	}	
}
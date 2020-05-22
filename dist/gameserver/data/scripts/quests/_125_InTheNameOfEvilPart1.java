package quests;

import l2s.commons.util.Rnd;
import l2s.gameserver.Config;
import l2s.gameserver.model.instances.NpcInstance;
import l2s.gameserver.model.quest.Quest;
import l2s.gameserver.model.quest.QuestState;

public class _125_InTheNameOfEvilPart1 extends Quest
{
	private int Mushika = 32114;
	private int Karakawei = 32117;
	private int UluKaimu = 32119;
	private int BaluKaimu = 32120;
	private int ChutaKaimu = 32121;
	private int OrClaw = 8779;
	private int DienBone = 8780;

	private static final int EXP_REWARD = 898056;	private static final int SP_REWARD = 215; 	public _125_InTheNameOfEvilPart1()
	{
		super(PARTY_NONE, ONETIME);

		addStartNpc(Mushika);
		addTalkId(Karakawei);
		addTalkId(UluKaimu);
		addTalkId(BaluKaimu);
		addTalkId(ChutaKaimu);
		addQuestItem(OrClaw, DienBone);
		addKillId(22742, 22743, 22744, 22745);
		addLevelCheck("32114-0.htm", 76);
		addQuestCompletedCheck("32114-0.htm", 124);
	}

	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("32114-05.htm"))
		{
			st.setCond(1);
		}
		else if(event.equalsIgnoreCase("32114-07.htm"))
		{
			st.setCond(2);
		}
		else if(event.equalsIgnoreCase("32117-08.htm"))
		{
			st.setCond(3);
		}
		else if(event.equalsIgnoreCase("32117-13.htm"))
		{
			st.setCond(5);
		}
		else if(event.equalsIgnoreCase("stat1false"))
			htmltext = "32119-2.htm";
		else if(event.equalsIgnoreCase("stat1true"))
		{
			st.setCond(6);
			htmltext = "32119-1.htm";
		}
		else if(event.equalsIgnoreCase("stat2false"))
			htmltext = "32120-2.htm";
		else if(event.equalsIgnoreCase("stat2true"))
		{
			st.setCond(7);
			htmltext = "32120-1.htm";
		}
		else if(event.equalsIgnoreCase("stat3false"))
			htmltext = "32121-2.htm";
		else if(event.equalsIgnoreCase("stat3true"))
		{
			st.giveItems(8781, 1);
			st.setCond(8);
			htmltext = "32121-1.htm";
		}
		return htmltext;
	}

	@Override
	public String onTalk(NpcInstance npc, QuestState st)
	{
		String htmltext = NO_QUEST_DIALOG;
		int npcId = npc.getNpcId();
		int cond = st.getCond();
		if(npcId == Mushika)
		{
			if(cond == 0)
				htmltext = "32114.htm";
			else if(cond == 1)
				htmltext = "32114-05.htm";
			else if(cond == 8)
			{
				htmltext = "32114-08.htm";
				st.addExpAndSp(EXP_REWARD, SP_REWARD);
				st.finishQuest();
			}
		}
		else if(npcId == Karakawei)
		{
			if(cond == 2)
				htmltext = "32117.htm";
			else if(cond == 3)
				htmltext = "32117-09.htm";
			else if(cond == 4)
			{
				st.takeAllItems(DienBone);
				st.takeAllItems(OrClaw);
				htmltext = "32117-1.htm";
			}
		}
		else if(npcId == UluKaimu)
		{
			if(cond == 5)
				htmltext = "32119.htm";
		}
		else if(npcId == BaluKaimu)
		{
			if(cond == 6)
				htmltext = "32120.htm";
		}
		else if(npcId == ChutaKaimu)
			if(cond == 7)
				htmltext = "32121.htm";
		return htmltext;
	}

	@Override
	public String onKill(NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();

		if(st.getCond() == 3)
		{
			if((npcId == 22744 || npcId == 22742) && st.getQuestItemsCount(OrClaw) < 2 && Rnd.chance(10 * Config.RATE_QUESTS_DROP))
			{
				st.giveItems(OrClaw, 1);
				st.playSound(SOUND_MIDDLE);
			}
			if((npcId == 22743 || npcId == 22745) && st.getQuestItemsCount(DienBone) < 2 && Rnd.chance(10 * Config.RATE_QUESTS_DROP))
			{
				st.giveItems(DienBone, 1);
				st.playSound(SOUND_MIDDLE);
			}
			if(st.getQuestItemsCount(DienBone) >= 2 && st.getQuestItemsCount(OrClaw) >= 2)
				st.setCond(4);
		}
		return null;
	}
}
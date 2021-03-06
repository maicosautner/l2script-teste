package quests;


import org.apache.commons.lang3.ArrayUtils;

import l2s.commons.util.Rnd;

import l2s.gameserver.model.Player;
import l2s.gameserver.model.instances.NpcInstance;
import l2s.gameserver.model.quest.Quest;
import l2s.gameserver.model.quest.QuestState;

public class _466_PlacingMySmallPower extends Quest
{
	//npc
	public static final int ASTERIOS = 30154;
	public static final int NOEM_MILID = 32895;
	
	//mobs
	private final int[] WingMobs = {22863, 22864, 22907, 22899, 22891, 22875, 22867, 22883};
	private final int[] CoconMobs = {32920};
	private final int[] BreathMobs = {22870, 22886, 22902, 22894, 22878};
	
	//q items
	public static final int WingI = 17597;
	public static final int CoconI = 17598;
	public static final int BreathI = 17599;
	public static final int ProofWord = 30384;
	public static final int NavozItem = 17596;
	public static final int NavozRecipe = 17603;

	public _466_PlacingMySmallPower()
	{
		super(PARTY_NONE, DAILY);
		addStartNpc(ASTERIOS);
		addTalkId(NOEM_MILID);
		addKillId(WingMobs);
		addKillId(CoconMobs);
		addKillId(BreathMobs);
		addQuestItem(WingI);
		addQuestItem(CoconI);
		addQuestItem(BreathI);
		addQuestItem(NavozItem);
		addQuestItem(NavozRecipe);
		addLevelCheck("30154-lvl.htm", 88);
	}

	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		Player player = st.getPlayer();
		String htmltext = event;
		if(event.equalsIgnoreCase("30154-4.htm"))
		{
			st.setCond(1);
		}
		if(event.equalsIgnoreCase("32895-4.htm"))
		{
			st.setCond(2);
		}
		return event;
	}

	@Override
	public String onTalk(NpcInstance npc, QuestState st)
	{
		Player player = st.getPlayer();
		int npcId = npc.getNpcId();
		int cond = st.getCond();
		if(npcId == ASTERIOS)
		{
			if(cond == 0)
				return "30154.htm";
			if(cond == 1)
				return "30154-5.htm";
			if(cond == 5)
			{
				st.giveItems(ProofWord, 1);
				st.takeItems(NavozItem, 1);
				st.finishQuest();		
				return "30154-6.htm"; //no further html do here
			}
		}
		if(npcId == NOEM_MILID)
		{
			if(cond == 1)
				return "32895.htm";
			if(cond == 2)
				return "32895-5.htm";
			if(cond == 3)
			{
				st.setCond(4);
				st.giveItems(NavozRecipe, 1);
				return "32895-6.htm";
			}	
			if(cond == 4 && st.getQuestItemsCount(NavozItem) == 0)
				return "32895-7.htm";
			if(cond == 4 && st.getQuestItemsCount(NavozItem) != 0)
			{
				st.setCond(5);
				return "32895-8.htm";
			}
			if(cond == 5)
				return "32895-10.htm";
		}		
		return NO_QUEST_DIALOG;
	}

	@Override
	public String onCompleted(NpcInstance npc, QuestState st)
	{
		String htmltext = COMPLETED_DIALOG;
		if(npc.getNpcId() == ASTERIOS)
			htmltext = "30154-comp.htm";
		return htmltext;
	}

	@Override
	public String onKill(NpcInstance npc, QuestState st)
	{
		int cond = st.getCond();
		if(cond != 2 || npc == null)
			return null;
		if(ArrayUtils.contains(WingMobs, npc.getNpcId()) && Rnd.chance(10))
		{
			st.giveItems(WingI, 1);
		}
		if(ArrayUtils.contains(CoconMobs, npc.getNpcId()) && Rnd.chance(7))
		{
			st.giveItems(CoconI, 1);
		}		
		if(ArrayUtils.contains(BreathMobs, npc.getNpcId()) && Rnd.chance(12))
		{
			st.giveItems(BreathI, 1);
		}			
		checkCond(st);
		return null;
	}	
	private static void checkCond(QuestState st)
	{
		if(st.getQuestItemsCount(WingI) >= 5 && st.getQuestItemsCount(CoconI) >= 5 && st.getQuestItemsCount(BreathI) >= 5)
			st.setCond(3);
	}
}
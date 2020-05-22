package quests;

import l2s.gameserver.listener.actor.player.OnLevelChangeListener;
import l2s.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2s.gameserver.model.Player;
import l2s.gameserver.model.actor.listener.CharListenerList;
import l2s.gameserver.model.base.Race;
import l2s.gameserver.model.instances.NpcInstance;
import l2s.gameserver.model.quest.Quest;
import l2s.gameserver.model.quest.QuestState;
import l2s.gameserver.network.l2.components.NpcString;
import l2s.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2s.gameserver.network.l2.s2c.ExShowScreenMessage.ScreenMessageAlign;

//By Evil_dnk

public class _10769_Lettersfromth_QueenCrumaTowerPart1 extends Quest
{
	// NPC's
	private static final int SILVIAN = 30070;
	private static final int LOREIN = 30673;

	//ITEMS
	
	private static final int ENCHANTARMOR = 23420;
	private static final int ENCHANTWEAPON = 23414;

	private static final int EXP_REWARD = 370440;
	private static final int SP_REWARD = 88;

	private static final OnPlayerEnterListener PLAYER_ENTER_LISTENER = new PlayerEnterListener();
	private static final OnLevelChangeListener LEVEL_UP_LISTENER = new ChangeLevelListener();

	private static class PlayerEnterListener implements OnPlayerEnterListener
	{
		@Override
		public void onPlayerEnter(Player player)
		{
			QuestState questState = player.getQuestState(10769);
			if(questState == null)
				return;

			if(player.isBaseClassActive() && player.getLevel() > 49)
			{
				if (!questState.isCompleted())
				{
					questState.abortQuest();
				}
			}
		}
	}

	private static class ChangeLevelListener implements OnLevelChangeListener
	{
		@Override
		public void onLevelChange(Player player, int was, int set)
		{
			QuestState questState = player.getQuestState(10769);
			if(questState == null)
				return;

			if(player.isBaseClassActive() && player.getLevel() > 49)
			{
				if (!questState.isCompleted())
				{
					questState.abortQuest();
				}
			}
		}
	}

	@Override
	public void onInit()
	{
		super.onInit();
		CharListenerList.addGlobal(PLAYER_ENTER_LISTENER);
		CharListenerList.addGlobal(LEVEL_UP_LISTENER);
	}

	public _10769_Lettersfromth_QueenCrumaTowerPart1()
	{
		super(PARTY_NONE, ONETIME, false);
		addTalkId(LOREIN);
		addTalkId(SILVIAN);
		addLevelCheck(NO_QUEST_DIALOG, 40, 49);
		addRaceCheck(NO_QUEST_DIALOG, Race.ERTHEIA);
	}

	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30070-3.htm"))
		{
			st.giveItems(39594, 1, false);
			st.getPlayer().sendPacket(new ExShowScreenMessage(NpcString.TRY_USING_THE_TELEPORT_SCROLL_SYLVAIN_GAVE_YOU_TO_GO_TO_CRUMA_TOWER, 5000, ScreenMessageAlign.TOP_CENTER, false));
			st.setCond(3);
		}

		else if(event.equalsIgnoreCase("30673-3.htm"))
		{
			st.getPlayer().sendPacket(new ExShowScreenMessage(NpcString.GROW_STRONGER_HERE_UNTIL_YOU_RECEIVE_THE_NEXT_LETTER_FROM_QUEEN_NAVARI_AT_LV_46, 5000, ScreenMessageAlign.TOP_CENTER, false));
			st.addExpAndSp(EXP_REWARD, SP_REWARD);
			st.finishQuest();
		}
		return htmltext;
	}

	@Override
	public String onTalk(NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getCond();
		String htmltext = NO_QUEST_DIALOG;
		switch (npcId)
		{
			case SILVIAN:
				if(cond == 2)
					htmltext = "30070-1.htm";
				else if(cond == 3)
					htmltext = "30070-4.htm";
			break;

			case LOREIN:
				if (cond == 3)
					htmltext = "30673-1.htm";
			break;
		}
		return htmltext;
	}
}
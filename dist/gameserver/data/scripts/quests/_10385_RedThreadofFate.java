package quests;

import l2s.gameserver.ai.CtrlEvent;
import l2s.gameserver.instancemanager.ReflectionManager;
import l2s.gameserver.listener.actor.OnMagicUseListener;
import l2s.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2s.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2s.gameserver.model.Creature;
import l2s.gameserver.model.GameObject;
import l2s.gameserver.model.Player;
import l2s.gameserver.model.Skill;
import l2s.gameserver.model.Zone;
import l2s.gameserver.model.actor.listener.CharListenerList;
import l2s.gameserver.model.base.Race;
import l2s.gameserver.model.entity.Reflection;
import l2s.gameserver.model.instances.NpcInstance;
import l2s.gameserver.model.quest.Quest;
import l2s.gameserver.model.quest.QuestState;
import l2s.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2s.gameserver.network.l2.components.NpcString;
import l2s.gameserver.network.l2.components.SceneMovie;
import l2s.gameserver.network.l2.s2c.SocialActionPacket;
import l2s.gameserver.utils.ItemFunctions;
import l2s.gameserver.utils.Location;
import l2s.gameserver.utils.NpcUtils;
import l2s.gameserver.utils.ReflectionUtils;
import l2s.gameserver.model.base.ClassLevel;

public class _10385_RedThreadofFate extends Quest
{
	private static class PlayerEnterListener implements OnPlayerEnterListener
	{
		@Override
		public void onPlayerEnter(Player player)
		{
			// Если каким-то чудом пропадает итем, то возвращаем его после релога.
			QuestState qs = player.getQuestState(10385);
			if(qs != null && qs.getCond() == 18)
			{
				if(!qs.haveQuestItem(Clearestwater))
					qs.giveItems(Clearestwater, 1);
			}
		}
	}

	private static class MagicUseListener implements OnMagicUseListener
	{
		@Override
		public void onMagicUse(Creature actor, Skill skill, Creature target, boolean alt) 
		{
			if(actor == null || !actor.isPlayer() || target == null || !target.isNpc()) 
				return;	

			QuestState st = actor.getPlayer().getQuestState(10385);
			if(st == null)
				return;

			NpcInstance npc = (NpcInstance) target;
			Player player = st.getPlayer();
			int cond = st.getCond();
			int npcId = npc.getNpcId();
			switch (skill.getId()) 
			{
				case water:
					if(npcId == MotherTree && cond == 18) 
					{
						if(st.getQuest().enterInstance(st, INSTANCE_ID))
						{
							ItemFunctions.deleteItem(st.getPlayer(), Clearestwater, 1L, true);
							st.setCond(19, false);
						}
					}
					break;
				case light:
					if(npcId == AltarofShilen && cond == 16) 
					{
						ItemFunctions.deleteItem(st.getPlayer(), Brightestlight, 1L, true);
						player.sendPacket(new ExShowScreenMessage(NpcString.YOU_MUST_DEFEAT_SHILENS_MESSENGER, 4500, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, new String[0]));
						NpcInstance mob = st.addSpawn(ShilensMessenger, 28760, 11032, -4252);
						mob.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, player, 100000);
					}
					break;
				case soul:
					if(npcId == CaveofSouls && cond == 17)
					{
						ItemFunctions.deleteItem(st.getPlayer(), Purestsoul, 1L, true);
						st.setCond(18);
					}
					break;
				case flame:
					if(npcId == PaagrioTemple && cond == 15)
					{
						ItemFunctions.deleteItem(st.getPlayer(), Fiercestflame, 1L, true);
						st.setCond(16);
					}
					break;
				case love:	
					if(npcId == DesertedDwarvenHouse && cond == 14) 
					{
						ItemFunctions.deleteItem(st.getPlayer(), Fondestheart, 1L, true);
						st.setCond(15);
					}
					break;
			}
		}
	}

	public static final int INSTANCE_ID = 241;

	private static final int Rean = 33491;
	private static final int Morelin = 30925;
	private static final int Lania = 33783;
	private static final int HeineWaterSource = 33784;
	private static final int Ladyofthelike = 31745;
	private static final int Nerupa = 30370;
	private static final int Innocentin = 31328;
	private static final int Enfeux = 31519;
	private static final int Vulkan = 31539;
	private static final int Urn = 31149;
	private static final int Wesley = 30166;
	private static final int DesertedDwarvenHouse = 33788;
	private static final int PaagrioTemple = 33787;
	private static final int AltarofShilen = 33785;
	private static final int ShilensMessenger = 27492;  //spawned by scripts mob
	private static final int CaveofSouls = 33789;
	private static final int MotherTree = 33786;
	private static final int Darin = 33748; //instance
	private static final int Roxxy = 33749; //instance
	private static final int BiotinHighPriest = 30031; //instance
	private static final int MysteriousDarkKnight = 33751; //spawned by script npc
	//Items
	private static final int MysteriosLetter = 36072;
	private static final int Waterfromthegardenofeva = 36066;
	private static final int Clearestwater = 36067;
	private static final int Brightestlight = 36068;
	private static final int Purestsoul = 36069;
	private static final int Vulkangold = 36113;
	private static final int Vulkansilver = 36114;
	private static final int Vulkanfire = 36115;
	private static final int Fiercestflame = 36070;
	private static final int Fondestheart = 36071;
	private static final int SOEDwarvenvillage = 20376;
	private static final int DimensionalDiamond = 7562;
	//Skills
	private static final int water = 9579;
	private static final int light = 9580;
	private static final int soul = 9581;
	private static final int flame = 9582;
	private static final int love = 9583;

	private static final OnPlayerEnterListener PLAYER_ENTER_LISTENER = new PlayerEnterListener();
	private static final OnMagicUseListener MAGIC_USE_LISTENER = new MagicUseListener();

	private ZoneListener _zoneListener;

	public _10385_RedThreadofFate() 
	{
		super(PARTY_NONE, ONETIME);

		addStartNpc(Rean);
		addTalkId(Morelin, Lania, HeineWaterSource, Ladyofthelike, Nerupa, Innocentin, Enfeux, Vulkan, Urn, Wesley, DesertedDwarvenHouse, PaagrioTemple, AltarofShilen, ShilensMessenger, CaveofSouls, MotherTree, Darin, Roxxy, BiotinHighPriest, MysteriousDarkKnight);

		addQuestItem(MysteriosLetter, Waterfromthegardenofeva, Clearestwater, Brightestlight, Purestsoul, Vulkangold, Vulkansilver, Vulkanfire, Fiercestflame, Fondestheart, SOEDwarvenvillage, DimensionalDiamond);

		addKillId(ShilensMessenger);

		addLevelCheck("<html><head><body>For 85 level and more.</body></html>", 85);
		addRaceCheck("<html><head><body>Not for Ertheia race...</body></html>", Race.HUMAN, Race.ELF, Race.DARKELF, Race.ORC, Race.DWARF, Race.KAMAEL);
		addClassLevelCheck("<html><head><body>You didn't complete quest Seize Your Destiny...</body></html>", false, ClassLevel.AWAKED);
		//addQuestCompletedCheck("<html><head><body>You didn't complete quest Seize Your Destiny...</body></html>", 10338);

		_zoneListener = new ZoneListener();
	}

	@Override
	public void onInit() 
	{
		super.onInit();
		CharListenerList.addGlobal(PLAYER_ENTER_LISTENER);
		CharListenerList.addGlobal(MAGIC_USE_LISTENER);
	}
	
	@Override
	public String onKill(NpcInstance npc, QuestState st) 
	{
		int npcId = npc.getNpcId();
		int cond = st.getCond();

		Player player = st.getPlayer();
		if(player == null) 
			return null;
			
		if(npcId == ShilensMessenger && cond == 16) 
		{
			st.setCond(17);
		}
		return null;
	}
	
	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc) 
	{
		Player player = st.getPlayer();
		String htmltext = event;
		int cond = st.getCond();
		if(cond == 0 && event.equalsIgnoreCase("Rean_q10385_03.htm")) 
		{
			st.setCond(1);
			st.giveItems(MysteriosLetter, 1L);
		} 
		else if(cond == 1 && event.equalsIgnoreCase("Morelin_q10385_02.htm")) 
		{
			st.setCond(2);
		} 
		else if(cond == 2 && event.equalsIgnoreCase("Lania_q10385_02.htm")) 
		{
			st.setCond(3);
		} 
		else if(cond == 6 && event.equalsIgnoreCase("Ladyofthelike_q10385_03.htm")) 
		{
			st.takeItems(Waterfromthegardenofeva, 1L);
			st.playSound(SOUND_MIDDLE);
		} 
		else if(cond == 6 && event.equalsIgnoreCase("Ladyofthelike_q10385_06.htm")) 
		{
			st.giveItems(Clearestwater, 1L);
			st.setCond(7);
			player.teleToLocation(new Location(172440, 90312, -2011));
		} 
		else if(cond == 7 && event.equalsIgnoreCase("Nerupa_q10385_04.htm")) 
		{
			st.setCond(8);
			st.giveItems(Brightestlight, 1L);
		} 
		else if(cond == 8 && event.equalsIgnoreCase("Enfeux_q10385_02.htm")) 
		{
			st.giveItems(Purestsoul, 1L);
			st.setCond(9);
		} 
		else if(cond == 9 && event.equalsIgnoreCase("Innocentin_q10385_02.htm")) 
		{
			st.setCond(10);
		} 
		else if(cond == 10 && event.equalsIgnoreCase("Vulkan_q10385_04.htm")) 
		{
			st.giveItems(Vulkangold, 1L);
			st.giveItems(Vulkansilver, 1L);
			st.giveItems(Vulkanfire, 1L);
			st.setCond(11);
		} 
		else if(cond == 13 && event.equalsIgnoreCase("Wesley_q10385_03.htm")) 
		{
			player.teleToLocation(new Location(180168, -111720, -5856));
		} 
		else if(cond == 13 && event.equalsIgnoreCase("Vulkan_q10385_08.htm")) 
		{
			st.giveItems(Fiercestflame, 1L);
			st.giveItems(Fondestheart, 1L);
			st.giveItems(SOEDwarvenvillage, 1L);
			st.setCond(14);
		} 
		else if(cond == 11 && event.equalsIgnoreCase("Urn_q10385_02.htm")) 
		{
			st.takeItems(Vulkangold, 1L);
			st.takeItems(Vulkansilver, 1L);
			st.takeItems(Vulkanfire, 1L);
			st.setCond(12);
		} 
		else if(cond == 19 && htmltext.equalsIgnoreCase("Darin_q10385_03.htm")) 
		{
			player.sendPacket(new ExShowScreenMessage(NpcString.SPEAK_WITH_ROXXY, 4500, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, new String[0]));
		}
		if(cond == 19 && event.equalsIgnoreCase("Roxxy_q10385_02.htm")) 
		{
			st.setCond(20);
		}

		if(cond == 20 && event.equalsIgnoreCase("Biotin_q10385_03.htm")) 
		{
			player.sendPacket(new ExShowScreenMessage(NpcString.GO_OUTSIDE_THE_TEMPLE, 4500, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, new String[0]));
			st.setCond(21);
		}
		if(cond == 21 && event.equalsIgnoreCase("showMovie")) 
		{
			st.getPlayer().startScenePlayer(SceneMovie.SC_SUB_QUEST);
			st.startQuestTimer("timer1", 25000L);
			htmltext = null;
		} 
		else if(event.equalsIgnoreCase("timer1")) 
		{
			st.setCond(22);
			st.cancelQuestTimer("timer1");
			player.teleToLocation(-113656, 246040, -3724, ReflectionManager.MAIN);
			htmltext = null;
		} 
		else if(cond == 22 && event.equalsIgnoreCase("Rean_q10385_05.htm")) 
		{
			st.giveItems(DimensionalDiamond, 40L);
			st.finishQuest();
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(NpcInstance npc, QuestState st) 
	{
		int cond = st.getCond();
		int npcId = npc.getNpcId();
		String htmltext = NO_QUEST_DIALOG;
		switch (npcId)
		{
			case Rean:
				if(cond == 22)
					htmltext = "Rean_q10385_04.htm";
				else if(cond == 0)
					htmltext = "Rean_q10385_01.htm";
				else if(cond > 22)
					htmltext = "Rean_q10385_0.htm";
				break;
			case Morelin:
				if(cond == 1)
					htmltext = "Morelin_q10385_01.htm";
				break;
			case Lania:
				if(cond == 4) 
				{
					htmltext = "Lania_q10385_03.htm";
					st.setCond(5);
				} 
				else if(cond == 2) 
				{
					htmltext = "Lania_q10385_01.htm";
				} 
				else if(cond == 3)
				{
					htmltext = "Lania_q10385_02.htm";
				}
				break;
			case HeineWaterSource:
				if(cond == 5) 
				{
					htmltext = "HeineWaterSource_q10385_01.htm";
					st.giveItems(Waterfromthegardenofeva, 1L);
					st.setCond(6);
				}
				else if(cond == 6)
					htmltext = "HeineWaterSource_q10385_01.htm";
				break;
			case Ladyofthelike:
				if(cond == 6)
					htmltext = "Ladyofthelike_q10385_01.htm";
				break;
			case Nerupa:
				if(cond == 7)
					htmltext = "Nerupa_q10385_01.htm";
				break;
			case Enfeux:
				if(cond == 8)
					htmltext = "Enfeux_q10385_01.htm";
				break;
			case Innocentin:
				if(cond == 9)
					htmltext = "Innocentin_q10385_01.htm";
				break;
			case Vulkan:
				if(cond == 10)
					htmltext = "Vulkan_q10385_01.htm";
				else if(cond == 13)
					htmltext = "Vulkan_q10385_05.htm";
				break;
			case Urn:
				if(cond == 11)
					htmltext = "Urn_q10385_01.htm";
				break;
			case Wesley:
				if(cond == 12) 
				{
					htmltext = "Wesley_q10385_01.htm";
					st.setCond(13);
				}
				break;
			case Darin:
				if(cond == 19)
					htmltext = "Darin_q10385_01.htm";
				break;
			case Roxxy:
				if(cond == 19)
					htmltext = "Roxxy_q10385_01.htm";
				break;
			case BiotinHighPriest:
				if(cond == 20) 
				{
					htmltext = "Biotin_q10385_01.htm";
				}
				break;
		}
		return htmltext;
	}

	@Override
	public void onEnterInstance(QuestState st, Reflection reflection, Object[] args)
	{
		reflection.getZone("[Q10385_EXIT_1]").addListener(_zoneListener);
		reflection.getZone("[Q10385_EXIT_2]").addListener(_zoneListener);
	}
	
	@Override
	public void onSocialActionUse(QuestState st, int actionId)
	{
		if(st.getPlayer().getTarget() == null || !st.getPlayer().getTarget().isNpc())
			return;
			
		GameObject npc1 = st.getPlayer().getTarget();		
		int npcId = ((NpcInstance) npc1).getNpcId();
		int cond = st.getCond();

		if(cond == 3 && npcId == Lania && actionId == SocialActionPacket.BOW) 
		{
			st.setCond(4);
		}
	}

	public class ZoneListener implements OnZoneEnterLeaveListener 
	{
		@Override
		public void onZoneEnter(Zone zone, Creature actor) 
		{}
		
		@Override
		public void onZoneLeave(Zone zone, Creature actor) 
		{
			if(!actor.isPlayer())
				return;
			Player player = actor.getPlayer();
			QuestState st = player.getQuestState(10385);
			if(st == null)
				return;
			if(st.getCond() == 21) 
			{
				Location loc = zone.getName().contains("Q10385_EXIT_1") ? new Location(210632, 15576, -3754) : new Location(210632, 15576, -3754);
				if(player.getReflection().getAllByNpcId(MysteriousDarkKnight, true).isEmpty())
					NpcUtils.spawnSingle(MysteriousDarkKnight, loc, player.getReflection());
			}
		}
	}
}

package npc.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import l2s.commons.collections.MultiValueSet;
import l2s.commons.threading.RunnableImpl;
import l2s.gameserver.ThreadPoolManager;
import l2s.gameserver.model.GameObjectsStorage;
import l2s.gameserver.model.Player;
import l2s.gameserver.model.instances.NpcInstance;
import l2s.gameserver.model.items.ItemInstance;
import l2s.gameserver.network.l2.components.ChatType;
import l2s.gameserver.network.l2.components.HtmlMessage;
import l2s.gameserver.network.l2.s2c.SayPacket2;
import l2s.gameserver.templates.npc.NpcTemplate;
import l2s.gameserver.utils.ItemFunctions;
import l2s.gameserver.utils.PositionUtils;
import bosses.FourSepulchersManager;
import bosses.FourSepulchersSpawn;
import bosses.FourSepulchersSpawn.GateKeeper;

public class SepulcherNpcInstance extends NpcInstance
{
	private static final long serialVersionUID = 1L;

	protected static Map<Integer, Integer> _hallGateKeepers = new HashMap<Integer, Integer>();

	protected Future<?> _closeTask = null, _spawnMonsterTask = null;

	private final static String HTML_FILE_PATH = "SepulcherNpc/";

	private final static int HALLS_KEY = 7260;

	public SepulcherNpcInstance(int objectId, NpcTemplate template, MultiValueSet<String> set)
	{
		super(objectId, template, set);
	}

	@Override
	protected void onDelete()
	{
		if(_closeTask != null)
		{
			_closeTask.cancel(false);
			_closeTask = null;
		}
		if(_spawnMonsterTask != null)
		{
			_spawnMonsterTask.cancel(false);
			_spawnMonsterTask = null;
		}
		super.onDelete();
	}

	@Override
	public void showChatWindow(Player player, int val, boolean firstTalk, Object... arg)
	{
		if(isDead())
		{
			player.sendActionFailed();
			return;
		}

		switch(getNpcId())
		{
			case 31468:
			case 31469:
			case 31470:
			case 31471:
			case 31472:
			case 31473:
			case 31474:
			case 31475:
			case 31476:
			case 31477:
			case 31478:
			case 31479:
			case 31480:
			case 31481:
			case 31482:
			case 31483:
			case 31484:
			case 31485:
			case 31486:
			case 31487:
				doDie(player);
				if(_spawnMonsterTask != null)
					_spawnMonsterTask.cancel(false);
				_spawnMonsterTask = ThreadPoolManager.getInstance().schedule(new SpawnMonster(getNpcId()), 3500);
				return;

			case 31455:
			case 31456:
			case 31457:
			case 31458:
			case 31459:
			case 31460:
			case 31461:
			case 31462:
			case 31463:
			case 31464:
			case 31465:
			case 31466:
			case 31467:
				if(player.isInParty() && !hasPartyAKey(player) && player.getParty().getPartyLeader() == player)
				{
					ItemFunctions.addItem(player.getParty().getPartyLeader(), HALLS_KEY, 1);
					doDie(player);
				}
				return;
		}
		super.showChatWindow(player, val, firstTalk, arg);
	}

	@Override
	public String getHtmlDir(String filename, Player player)
	{
		return HTML_FILE_PATH;
	}

	@Override
	public void onBypassFeedback(Player player, String command)
	{
		if(command.startsWith("open_gate"))
		{
			ItemInstance hallsKey = player.getInventory().getItemByItemId(HALLS_KEY);
			if(hallsKey == null)
				showHtmlFile(player, "Gatekeeper-no.htm");
			else if(FourSepulchersManager.isAttackTime())
			{
				switch(getNpcId())
				{
					case 31929:
					case 31934:
					case 31939:
					case 31944:
						if(!FourSepulchersSpawn.isShadowAlive(getNpcId()))
							FourSepulchersSpawn.spawnShadow(getNpcId());
				}

				// Moved here from switch-default
				openNextDoor(getNpcId());
				if(player.getParty() != null)
					for(Player mem : player.getParty().getPartyMembers())
					{
						hallsKey = mem.getInventory().getItemByItemId(HALLS_KEY);
						if(hallsKey != null)
							ItemFunctions.deleteItem(mem, HALLS_KEY, hallsKey.getCount());
					}
				else
					ItemFunctions.deleteItem(player, HALLS_KEY, hallsKey.getCount());
			}
		}
		else
			super.onBypassFeedback(player, command);
	}

	public void openNextDoor(int npcId)
	{
		GateKeeper gk = FourSepulchersManager.getHallGateKeeper(npcId);
		gk.door.openMe();

		if(_closeTask != null)
			_closeTask.cancel(false);
		_closeTask = ThreadPoolManager.getInstance().schedule(new CloseNextDoor(gk), 10000);
	}

	private class CloseNextDoor extends RunnableImpl
	{
		private final GateKeeper _gk;
		private int state = 0;

		public CloseNextDoor(GateKeeper gk)
		{
			_gk = gk;
		}

		@Override
		public void runImpl() throws Exception
		{
			if(state == 0)
			{
				try
				{
					_gk.door.closeMe();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				state++;
				_closeTask = ThreadPoolManager.getInstance().schedule(this, 10000);
			}
			else if(state == 1)
			{
				FourSepulchersSpawn.spawnMysteriousBox(_gk.npcId);
				_closeTask = null;
			}
		}
	}

	private class SpawnMonster extends RunnableImpl
	{
		private final int _NpcId;

		public SpawnMonster(int npcId)
		{
			_NpcId = npcId;
		}

		@Override
		public void runImpl() throws Exception
		{
			FourSepulchersSpawn.spawnMonster(_NpcId);
		}
	}

	public void showHtmlFile(Player player, String file)
	{
		HtmlMessage html = new HtmlMessage(this);
		html.setFile("SepulcherNpc/" + file);
		player.sendPacket(html);
	}

	private boolean hasPartyAKey(Player player)
	{
		for(Player m : player.getParty().getPartyMembers())
			if(ItemFunctions.getItemCount(m, HALLS_KEY) > 0)
				return true;
		return false;
	}
}
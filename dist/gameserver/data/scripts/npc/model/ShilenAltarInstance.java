package npc.model;

import l2s.commons.collections.MultiValueSet;
import l2s.gameserver.instancemanager.ReflectionManager;
import l2s.gameserver.model.GameObjectsStorage;
import l2s.gameserver.model.Player;
import l2s.gameserver.model.instances.NpcInstance;
import l2s.gameserver.network.l2.components.HtmlMessage;
import l2s.gameserver.templates.npc.NpcTemplate;
import l2s.gameserver.utils.Location;

/**
 * @author Awakeninger
 */

public final class ShilenAltarInstance extends NpcInstance
{
	private static final int DoorEnter1 = 25180001;
	private static final int DoorEnter2 = 25180002;
	private static final int DoorEnter3 = 25180003;
	private static final int DoorEnter4 = 25180004;
	private static final int DoorEnter5 = 25180005;
	private static final int DoorEnter6 = 25180006;
	private static final int DoorEnter7 = 25180007;
	private static final Location FLOOR2 = new Location(179336, 13608, -9852);
	private static final Location FLOOR3 = new Location(179304, 12824, -12796);
	private static final Location OFF = new Location(193353, 22608, -3616);
	private long _savedTime;
	//DoorInstance _door1 = getReflection().getDoor(DoorEnter1);

	public ShilenAltarInstance(int objectId, NpcTemplate template, MultiValueSet<String> set)
	{
		super(objectId, template, set);
	}

	@Override
	public void onBypassFeedback(Player player, String command)
	{
		if(command.startsWith("start1"))
		{
			//тут пропускам т.к. это первый этаж, и перемещение на него == вход в инстанс
		}
		else if(command.startsWith("start2"))
		{

			for(Player party : GameObjectsStorage.getPlayers())
			{
				party.unsetVar("Altar1");
				party.setVar("Altar2", "true", -1);
				party.teleToLocation(FLOOR2, player.getReflection());
			}
		}
		else if(command.startsWith("start3"))
		{
			for(Player party : GameObjectsStorage.getPlayers())
			{
				party.unsetVar("Altar2");
				party.setVar("Altar3", "true", -1);
				party.teleToLocation(FLOOR3, player.getReflection());
			}

		}
		else if(command.startsWith("exit"))
		{
			for(Player party : GameObjectsStorage.getPlayers())
			{
				party.unsetVar("Altar3");
				party.teleToLocation(OFF, ReflectionManager.MAIN);
			}
		}
		else
		{
			super.onBypassFeedback(player, command);
		}
	}

	@Override
	public void showChatWindow(Player player, int val, boolean firstTalk, Object... replace)
	{
		HtmlMessage htmlMessage = new HtmlMessage(getObjectId()).setPlayVoice(firstTalk);
		htmlMessage.setFile("default/33785.htm");
		if(player.getVar("Altar1") != null)
		{
			htmlMessage.setFile("default/33785-2.htm");
		}
		if(player.getVar("Altar2") != null)
		{
			htmlMessage.setFile("default/33785-3.htm");
		}
		if(player.getVar("Altar3") != null)
		{
			htmlMessage.setFile("default/33785-e.htm");
		}

		player.sendPacket(htmlMessage);
	}
}
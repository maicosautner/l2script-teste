package npc.model;

import l2s.commons.collections.MultiValueSet;
import l2s.gameserver.model.Player;
import l2s.gameserver.model.instances.NpcInstance;
import l2s.gameserver.templates.npc.NpcTemplate;

import bosses.BaiumManager;
import bosses.EpicBossState.State;

/**
 * @author Bonux
 */
public final class BaiumStoneInstance extends NpcInstance
{
	private static final long serialVersionUID = 1L;

	public BaiumStoneInstance(int objectId, NpcTemplate template, MultiValueSet<String> set)
	{
		super(objectId, template, set);
	}

	@Override
	public void onMenuSelect(Player player, int ask, long reply)
	{
		if(ask == 9999)
		{
			if(reply == 1)
			{
				if(!isBusy())
				{
					/*if(player.getVar("baiumPermission") != null && player.getVar("baiumPermission").equalsIgnoreCase("granted"))
					{*/
						if(BaiumManager.getState() == State.NOTSPAWN)
						{
							setBusy(true);
							BaiumManager.spawnBaium(this, player);
						}
					//}
				}
			}
		}
		else
			super.onMenuSelect(player, ask, reply);
	}

	@Override
	public void showChatWindow(Player player, int val, boolean firstTalk, Object... arg)
	{
		if(val == 0)
			showChatWindow(player, "default/baium_npc001.htm", firstTalk);
		else
			super.showChatWindow(player, val, firstTalk, arg);
	}
}
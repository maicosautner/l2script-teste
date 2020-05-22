package npc.model;

import org.apache.commons.lang3.ArrayUtils;
import l2s.commons.collections.MultiValueSet;
import l2s.commons.util.Rnd;
import l2s.gameserver.model.Creature;
import l2s.gameserver.model.instances.MonsterInstance;
import l2s.gameserver.templates.npc.MinionData;
import l2s.gameserver.templates.npc.NpcTemplate;

/**
 * У монстров в Seed of Annihilation список минионов может быть разный.
 * @author Bonux
**/
public class SeedOfAnnihilationInstance extends MonsterInstance
{
	private static final long serialVersionUID = 1L;

	private static final int[] BISTAKON_MOBS = new int[] { 22750, 22751, 22752, 22753 };
	private static final int[] COKRAKON_MOBS = new int[] { 22763, 22764, 22765 };
	private static final int[][] BISTAKON_MINIONS = new int[][] {
			{ 22746, 22746, 22746 },
			{ 22747, 22747, 22747 },
			{ 22748, 22748, 22748 },
			{ 22749, 22749, 22749 } };
	private static final int[][] COKRAKON_MINIONS = new int[][] {
			{ 22760, 22760, 22761 },
			{ 22760, 22760, 22762 },
			{ 22761, 22761, 22760 },
			{ 22761, 22761, 22762 },
			{ 22762, 22762, 22760 },
			{ 22762, 22762, 22761 } };

	public SeedOfAnnihilationInstance(int objectId, NpcTemplate template, MultiValueSet<String> set)
	{
		super(objectId, template, set);
		if(ArrayUtils.contains(BISTAKON_MOBS, template.getId()))
			addMinions(BISTAKON_MINIONS[Rnd.get(BISTAKON_MINIONS.length)], template);
		else if(ArrayUtils.contains(COKRAKON_MOBS, template.getId()))
			addMinions(COKRAKON_MINIONS[Rnd.get(COKRAKON_MINIONS.length)], template);
	}

	private static void addMinions(int[] minions, NpcTemplate template)
	{
		if(minions != null && minions.length > 0)
		{
			for(int id : minions)
				template.addMinion(new MinionData(id, null, 1, 0));
		}
	}

	@Override
	protected void onDeath(Creature killer)
	{
		//TODO: Проверить на оффе, при убийстве главного миньёны анспавнятся или нет.
		getMinionList().despawnMinions();
		super.onDeath(killer);
	}

	@Override
	public boolean canChampion()
	{
		return false;
	}
}
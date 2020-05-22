package ai.hellbound;

import l2s.commons.util.Rnd;
import l2s.gameserver.ai.CtrlEvent;
import l2s.gameserver.ai.Fighter;
import l2s.gameserver.idfactory.IdFactory;
import l2s.gameserver.model.Creature;
import l2s.gameserver.model.Skill;
import l2s.gameserver.model.Zone;
import l2s.gameserver.model.instances.NpcInstance;
import l2s.gameserver.utils.Location;
import l2s.gameserver.utils.NpcUtils;
import l2s.gameserver.utils.ReflectionUtils;

/**
 * При смерти запускает телепорт на следующий этаж
 * Контролирует спаун охраны.
 * @author SYS
 */
public class MasterFestina extends Fighter
{
	private static Zone _zone;
	private static Location[] _mysticSpawnPoints;
	private static Location[] _spiritGuardSpawnPoints;
	private final static int FOUNDRY_MYSTIC_ID = 22387;
	private final static int FOUNDRY_SPIRIT_GUARD_ID = 22389;
	private long _lastFactionNotifyTime = 0;

	public MasterFestina(NpcInstance actor)
	{
		super(actor);

		_zone = ReflectionUtils.getZone("[tully2]");
		_mysticSpawnPoints = new Location[] {
				new Location(-11480, 273992, -11768),
				new Location(-11128, 273992, -11864),
				new Location(-10696, 273992, -11936),
				new Location(-12552, 274920, -11752),
				new Location(-12568, 275320, -11864),
				new Location(-12568, 275784, -11936),
				new Location(-13480, 273880, -11752),
				new Location(-13880, 273880, -11864),
				new Location(-14328, 273880, -11936),
				new Location(-12456, 272968, -11752),
				new Location(-12456, 272552, -11864),
				new Location(-12456, 272120, -11936) };

		_spiritGuardSpawnPoints = new Location[] {
				new Location(-12552, 272168, -11936),
				new Location(-12552, 272520, -11872),
				new Location(-12552, 272984, -11744),
				new Location(-13432, 273960, -11736),
				new Location(-13864, 273960, -11856),
				new Location(-14296, 273976, -11936),
				new Location(-12504, 275736, -11936),
				new Location(-12472, 275288, -11856),
				new Location(-12472, 274888, -11744),
				new Location(-11544, 273912, -11752),
				new Location(-11160, 273912, -11856),
				new Location(-10728, 273896, -11936) };
	}

	@Override
	protected void onEvtSpawn()
	{
		NpcInstance actor = getActor();
		// Спауним охрану
		for(Location loc : _mysticSpawnPoints)
		{
			NpcUtils.spawnSingle(FOUNDRY_MYSTIC_ID, loc, actor.getReflection());
		}
		for(Location loc : _spiritGuardSpawnPoints)
		{
			NpcUtils.spawnSingle(FOUNDRY_SPIRIT_GUARD_ID, loc, actor.getReflection());
		}

		setZoneInactive();
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, Skill skill, int damage)
	{
		NpcInstance actor = getActor();
		if(System.currentTimeMillis() - _lastFactionNotifyTime > _minFactionNotifyInterval)
		{
			_lastFactionNotifyTime = System.currentTimeMillis();

			for(NpcInstance npc : actor.getAroundNpc(3000, 500))
				if(npc.getNpcId() == FOUNDRY_MYSTIC_ID || npc.getNpcId() == FOUNDRY_SPIRIT_GUARD_ID)
					npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, attacker, Rnd.get(1, 100));
		}

		super.onEvtAttacked(attacker, skill, damage);
	}

	@Override
	protected void onEvtDead(Creature killer)
	{
		NpcInstance actor = getActor();
		_lastFactionNotifyTime = 0;

		// Удаляем охрану
		for(NpcInstance npc : actor.getAroundNpc(3000, 500))
			if(npc.getNpcId() == FOUNDRY_MYSTIC_ID || npc.getNpcId() == FOUNDRY_SPIRIT_GUARD_ID)
				npc.deleteMe();

		setZoneActive();
		super.onEvtDead(killer);
	}

	private void setZoneActive()
	{
		_zone.setActive(true);
	}

	private void setZoneInactive()
	{
		_zone.setActive(false);
	}
}
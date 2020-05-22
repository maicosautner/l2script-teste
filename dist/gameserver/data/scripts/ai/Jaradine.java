package ai;

import l2s.commons.util.Rnd;
import l2s.gameserver.ai.DefaultAI;
import l2s.gameserver.model.Creature;
import l2s.gameserver.model.Skill;
import l2s.gameserver.model.instances.NpcInstance;
import l2s.gameserver.utils.Functions;
import l2s.gameserver.utils.Location;

public class Jaradine extends DefaultAI
{
	static final Location[] points = {
			new Location(44964, 50568, -3056),
			new Location(44435, 50025, -3056),
			new Location(44399, 49078, -3056),
			new Location(45058, 48437, -3056),
			new Location(46132, 48724, -3056),
			new Location(46452, 49743, -3056),
			new Location(45730, 50590, -3056) };

	private int current_point = -1;
	private long wait_timeout = 0;
	private boolean wait = false;

	public Jaradine(NpcInstance actor)
	{
		super(actor);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected boolean thinkActive()
	{
		NpcInstance actor = getActor();
		if(actor.isDead())
			return true;

		if(_def_think)
		{
			doTask();
			return true;
		}

		if(System.currentTimeMillis() > wait_timeout && (current_point > -1 || Rnd.chance(5)))
		{
			if(!wait)
				switch(current_point)
				{
					case 3:
						wait_timeout = System.currentTimeMillis() + 15000;
						Functions.npcSay(actor, "The Mother Tree is slowly dying.");
						wait = true;
						return true;
					case 4:
						wait_timeout = System.currentTimeMillis() + 15000;
						Functions.npcSay(actor, "How can we save the Mother Tree?");
						wait = true;
						return true;
					case 6:
						wait_timeout = System.currentTimeMillis() + 60000;
						wait = true;
						return true;
				}

			wait_timeout = 0;
			wait = false;
			current_point++;

			if(current_point >= points.length)
				current_point = 0;

			addTaskMove(points[current_point], true);
			doTask();
			return true;
		}

		if(randomAnimation())
			return true;

		return false;
	}

	@Override
	protected void onEvtAttacked(Creature attacker, Skill skill, int damage)
	{}

	@Override
	protected void onEvtAggression(Creature target, int aggro)
	{}
}
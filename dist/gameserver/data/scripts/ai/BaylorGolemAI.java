package ai;

import l2s.commons.util.Rnd;
import l2s.gameserver.ai.CtrlIntention;
import l2s.gameserver.ai.DefaultAI;
import l2s.gameserver.model.Creature;
import l2s.gameserver.model.instances.NpcInstance;

/**
 Obi-Wan
 14.10.2016
 */
public class BaylorGolemAI extends DefaultAI
{
	private boolean _run = false;

	public BaylorGolemAI(NpcInstance actor)
	{
		super(actor);
		actor.setRunning();
	}

	@Override
	public void runImpl()
	{
		if(getActor().isDead())
			return;

		if(!isRun())
			return;

		if(getActor().isCastingNow() || getActor().isAttackingNow())
			return;

		if(_def_think)
		{
			if(doTask())
				clearTasks();
			return;
		}

		for(Creature target : getActor().getAroundCharacters(3000, 500))
		{
			if(!canAttack(target))
				continue;

			if(target.getAbnormalList().contains(5225))
			{
				if(!target.isDead())
				{
					if(getActor().isCastingNow() || getActor().isAttackingNow())
						return;
				}
				else
				{
					getActor().abortAttack(true, false);
					getActor().abortCast(true, false);
					getActor().setTarget(null);
				}
				attack(target);
				return;
			}
		}

		for(Creature target : getActor().getAroundCharacters(3000, 500))
		{
			if(!canAttack(target))
				continue;

			if(!target.isDead())
			{
				if(getActor().isCastingNow() || getActor().isAttackingNow())
					return;
			}
			else
			{
				getActor().abortAttack(true, false);
				getActor().abortCast(true, false);
				getActor().setTarget(null);
			}
			attack(target);
			return;
		}
	}

	public boolean isRun()
	{
		return _run;
	}

	private void attack(Creature target)
	{
		getActor().getAggroList().addDamageHate(target, 0, Rnd.get(1, 100)); // Это нужно, чтобы гвард не перестал атаковать цель после первых ударов
		setAttackTarget(target); // На всякий случай, не обязательно делать
		changeIntention(CtrlIntention.AI_INTENTION_ATTACK, target, null); // Переводим в состояние атаки
		addTaskAttack(target); // Добавляем отложенное задание атаки, сработает в самом конце движения
	}

	@Override
	public boolean canAttackCharacter(Creature target)
	{
		return canAttack(target);
	}

	private boolean canAttack(Creature target)
	{
		return !target.isPlayable() && target.getNpcId() == 29213;
	}
}
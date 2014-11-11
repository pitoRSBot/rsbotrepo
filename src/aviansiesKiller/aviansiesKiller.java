package aviansiesKiller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.concurrent.Callable;

import org.powerbot.script.Tile;
import org.powerbot.bot.rt6.client.input.Keyboard;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Random;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.*;
import org.powerbot.script.Input;
import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.PaintListener;
import org.powerbot.script.rt6.ClientAccessor;
import org.powerbot.script.rt6.ClientContext;


@Script.Manifest(name = "aviansiesKiller", description = "It's killing aviansies")
public class aviansiesKiller extends PollingScript<ClientContext> implements PaintListener{

	private int aviansiesIDs[] = {6244,6233,6241,6242,6236};
	final int[] bounds = {-192, 192, -768, 0, -192, 192};
	private String foodnames[] = {"Swordfish","Lobster"};	
	
	int addyBarID = 2362;
	
	static int rangedStartXP = 0;	
	static int rangedXPMade;
	
	static int addyBarsStart;
	static int Profit;
	
	public static String status = "null";
	
	public static long startTime;
	
	static boolean setup = true;
	
	
	@Override
	public void poll() {
		if(setup == true)
		{
			rangedStartXP = ctx.skills.experience(Constants.SKILLS_RANGE);
			startTime = System.currentTimeMillis();
			setup = false;
		}
		switch(state())
		{
		case LOOT:
			final GroundItem loot = ctx.groundItems.select().id(addyBarID).nearest().within(10).poll();	
			final GroundItem swordfish = ctx.groundItems.select().name("Swordfish").within(10).poll();
			if(loot.valid())
			{
			status = ("Grabing loot");
			if(!loot.inViewport())
				ctx.camera.turnTo(loot);
			loot.hover();
			Condition.sleep(Random.nextInt(250, 400));
			loot.interact(false,"Take","Adamant bar");
			Condition.wait(new Callable<Boolean>() {
	            @Override
	            public Boolean call() throws Exception {
	            	status = ("Running!");
	                return !ctx.players.local().inMotion();
	            }
	        });
			status = ("Looted!");	  
			}
			if(swordfish.valid() && ctx.backpack.select().count() < 26)
			{
				if(!swordfish.inViewport())
					ctx.camera.turnTo(swordfish);
				swordfish.hover();
				Condition.sleep(Random.nextInt(250, 400));
				swordfish.interact(false,"Take","Swordfish");
				Condition.wait(new Callable<Boolean>() {
		            @Override
		            public Boolean call() throws Exception {
		            	status = ("Running!");
		                return !ctx.players.local().inMotion();
		            }
		        });
			}
			break;
		case ATTACK:
			if(ctx.players.local().healthPercent() > 40)
			{
				if(!ctx.players.local().inCombat() || !ctx.players.local().interacting().valid() && getTarget().valid())
				{
					status = ("Searching for aviansie to kill");
					final Npc aviansie = getTarget();	
					if(aviansie.valid())
					{
						if(ctx.movement.distance(ctx.players.local().tile(),aviansie.tile()) > 9)
						ctx.movement.step(aviansie.tile());
						if(!aviansie.inViewport())
							ctx.camera.turnTo(aviansie);
						aviansie.hover();
						if(aviansie.interact(false,"Attack","Aviansie"))
						{
							if(ctx.players.local().interacting() != null)
							{
							Condition.wait(new Callable<Boolean>() {
							     @Override
							     public Boolean call() {
							    	 status = "Waiting for battle to end";
							        return aviansie.healthPercent() == 0 || !aviansie.valid();
							     }
							}, 700, 50);
							status = "Battle ended";		
							if(ctx.players.local().healthPercent() < 90)
							{
							ctx.keyboard.send("=");
							Condition.sleep(12000);
							}
							status = "Going further";
							}
						}
					}
				}
			}
			else
			{
				stop();
			}
			break;
		case HEAL:			
				Item food = ctx.backpack.name(foodnames).poll();
				food.interact("Eat");		
		break;		
	
		
		}
}
    public Npc getTarget() {
        return !ctx.npcs.select().select(aggroFilter).isEmpty()
                ? ctx.npcs.nearest().poll() : ctx.npcs.select().id(aviansiesIDs).select(fightFilter).nearest().poll();
    }

    private final Filter<Npc> aggroFilter = new Filter<Npc>() {
        @Override
        public boolean accept(Npc npc) {
            return npc.interacting().valid()
                    && npc.interacting().equals(ctx.players.local())
                    && Arrays.asList(npc.actions()).contains("Attack");
        }
    };

    private final Filter<Npc> fightFilter = new Filter<Npc>() {
        @Override
        public boolean accept(Npc npc) {
            return (!npc.interacting().valid() || !npc.inCombat())
                    && npc.tile().matrix(ctx).reachable()
                    && npc.animation() == -1
                    && npc.healthPercent() > 5;
        }
    };
    
	private enum State {
		ATTACK, HEAL, LOOT, IDLE
	}
	private State state() {
		final GroundItem loot = ctx.groundItems.select().id(addyBarID).nearest().within(15).poll();
		final GroundItem swordfish = ctx.groundItems.select().name("Swordfish").within(10).poll();
		if(ctx.players.local().healthPercent() < 50)
		{
			return State.HEAL;
		}
		if(loot.valid())
		{
			status = ("We found loot");
			return State.LOOT;
		}
		if(swordfish.valid() && ctx.backpack.select().count() < 26)
		{
			status = ("We found loot");
			return State.LOOT;
		}
		return State.ATTACK;
	}
	public String runTime(long i) {
		DecimalFormat nf = new DecimalFormat("00");
		long millis = System.currentTimeMillis() - i;
		long hours = millis / (1000 * 60 * 60);
		millis -= hours * (1000 * 60 * 60);
		long minutes = millis / (1000 * 60);
		millis -= minutes * (1000 * 60);
		long seconds = millis / 1000;
		return nf.format(hours) + ":" + nf.format(minutes) + ":" + nf.format(seconds);
	}
	
	
	@Override
	public void repaint(Graphics g) {
		rangedXPMade = (ctx.skills.experience(Constants.SKILLS_RANGE) - rangedStartXP);
		long totalRunTime = System.currentTimeMillis() - startTime;
		g.setColor(new Color(255,255,255));
		g.drawString("Running time: " +runTime(startTime), 10, 220);
		g.drawString("Ranged XP Made: " +rangedXPMade, 10, 240);
		int xph = (int)Math.floor(rangedXPMade * 3600000D / totalRunTime);
		int profith = (int)Math.floor(Profit * 3600000D / totalRunTime);
		g.drawString("Ranged exp/h: " +xph, 10, 260);		
		g.drawString("Status: " +status, 10, 280);
	}

}



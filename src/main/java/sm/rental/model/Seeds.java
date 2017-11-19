package sm.rental.model;

import cern.jet.random.engine.RandomSeedGenerator;

public class Seeds 
{
	public int seed1;   // comment 1
	public int seed2;   // comment 2
	public int seed3;   // comment 3
	public int seed4;   // comment 4

	public Seeds(RandomSeedGenerator rsg)
	{
		seed1=rsg.nextSeed();
		seed2=rsg.nextSeed();
		seed3=rsg.nextSeed();
		seed4=rsg.nextSeed();
	}
}

package Forces;

import processing.core.PApplet;
import Forces.Force;
import Sprites.*;

public class Turbulence extends Force
	{
		private float scale = 1;
		private float falloff = (float) 0.75;
		private int octaves = 3;
		private PApplet screen;
		
		public Turbulence(PApplet np)
		{
			screen = np;
		}
		
		public Turbulence applyForce(Sprite obj)
		{
			screen.noiseDetail(octaves, falloff);
			obj.set_dx((float) (obj.get_dx() + (float) 2 * ((double) screen.random(0, 1) - 0.5) * screen.noise(obj.get_x() * scale / screen.width, obj.get_y() * scale / screen.height) * get_str_x()));
			obj.set_dy((float) (obj.get_dy() + (float) 2 * ((double) screen.random(0, 1) - 0.5) * screen.noise(obj.get_x() * scale / screen.width, obj.get_y() * scale / screen.height) * get_str_y()));
			return this;
		}
		
		//------------------MUTATORS-----------------
		public Turbulence set_scale(double s)
		{
			scale = (float) s;
			return this;
		}
		
		public Turbulence set_falloff(double f)
		{
			falloff = (float) f;
			return this;
		}
		
		public Turbulence set_octaves(int oct)
		{
			octaves = oct;
			return this;
		}
		//--------------------------------------------
		
		//--------------------ACCESSORS---------------
		public float get_scale() {
			return scale;
		}
		
		public float get_falloff() {
			return falloff;
		}
		
		public int get_octaves() {
			return octaves;
		}
		//--------------------------------------------
		
	}
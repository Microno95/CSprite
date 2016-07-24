package Forces;

import Sprites.*;

public class PointForce extends Force
	{
		private float x;
		private float y;
		private float falloff;
		
		public PointForce()
		{
			set_str_x(1); //str_x = 1;
			set_str_y(1); //str_y = 1;
			x = 1;
			y = 1;
		}
		
		//-----------------------MUTATORS-------------------
		public PointForce set_x(double px)
		{
			x = (float) px;
			return this;
		}
		
		public PointForce set_y(double py)
		{
			y = (float) py;
			return this;
		}
		
		public PointForce set_falloff(double foff)
		{
			falloff = (float) foff;
			return this;
		}
		//----------------------------------------------------
		
		//-----------------------ACCESSORS--------------------
		public float get_x() {
			return x;
		}
		
		public float get_y() {
			return y;
		}
		
		public float get_falloff() {
			return falloff;
		}	
		//----------------------------------------------------
		
		
		public PointForce applyForce(Sprite obj)
		{
			float x_dist = obj.get_x() - x;
			float y_dist = obj.get_y() - y;
			float tot_dist = (float) Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2));
			if (Math.abs(x_dist) != 0)
			{
				obj.set_dx((float) (obj.get_dx() + get_str_x() * x_dist/Math.pow(tot_dist, falloff + 1)));
			}
			if (Math.abs(y_dist) != 0)
			{
				obj.set_dy((float) (obj.get_dy() + get_str_y() * y_dist/Math.pow(tot_dist, falloff + 1)));
			}
			return this;
		}
		

		public float distance(double x1, double x2, double y1, double y2)
		{
			return (float) Math.sqrt(Math.pow((float) (x1 - x2), 2) + Math.pow((float) (y1- y2), 2));
		}
	}
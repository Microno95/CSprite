package Forces;

import Sprites.*;

public class AngularDampingForce extends Force
	{
		public AngularDampingForce()
		{
			set_str_x(1); //= 1;
			set_str_y(1); //= 1;
		}
		
		public AngularDampingForce applyForce(Sprite obj)
		{
			obj.set_angular(obj.get_angular() * get_str_x());
			return this;
		}
	}
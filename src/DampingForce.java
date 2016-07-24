public class DampingForce extends Force
	{		
		public DampingForce()
		{
			set_str_x(1);
			set_str_y(1);
		}
		
		public DampingForce applyForce(Sprite obj)
		{
			obj.set_dx(obj.get_dx() * get_str_x());
			obj.set_dy(obj.get_dy() * get_str_y());
			return this;
		}
	}
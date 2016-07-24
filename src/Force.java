public class Force
	{
		private float str_x;
		private float str_y;
		
		public Force()
		{
			str_x = 1;
			str_y = 1;
		}

		public Force(float a, float b)
		{
			str_x = a;
			str_y = b;
		}

		public float get_str_x() {
			return str_x;
		}

		public float get_str_y() {
			return str_y;
		}

		public Force set_str_x(double str)
		{
			str_x = (float) str;
			return this;
		}
		
		public Force set_str_y(double str)
		{
			str_y = (float) str;
			return this;
		}
		
		public Force applyForce(Sprite obj)
		{
			obj.set_dx(obj.get_dx() + str_x);
			obj.set_dy(obj.get_dy() + str_y);
			return this;
		}
	}
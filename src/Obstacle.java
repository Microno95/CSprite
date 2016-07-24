import Sprites.Sprite;

@SuppressWarnings("SameParameterValue")
public class Obstacle
	{
		private float x,  y, width, orientation, restitution;
		
		public Obstacle(double px, double py, double pf, double orien)
		{
			x = (float) px;
			y = (float) py;
			width = (float) pf;
			orientation = (float) orien;
			restitution = 1;
		}

        public float get_x() {
            return x;
        }

        public float get_y() {
            return y;
        }

        public float get_width() {
            return width;
        }

        public float get_orientation() {
            return orientation;
        }

        public float get_restitution() {
            return restitution;
        }

        public Obstacle set_x(double px)
		{
			x = (float) px;
			return this;
		}
		
		public Obstacle set_y(double py)
		{
			y = (float) py;
			return this;
		}
		
		public Obstacle set_falloff(double pf)
		{
			width = (float) pf;
			return this;
		}
		
		public Obstacle set_orientation(double orien)
		{
			orientation = (float) orien;
			return this;
		}
		
		public Obstacle set_restitution(double rest)
		{
			restitution = (float) rest;
			return this;
		}
		
		public float distance(double x1, double x2, double y1, double y2)
		{
			return (float) Math.sqrt(Math.pow((float) (x1 - x2), 2) + Math.pow((float) (y1- y2), 2));
		}
		
		public Obstacle overlaps(Sprite obj)
		{

			return this;
		}
	}	
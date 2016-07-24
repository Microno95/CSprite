package CartCoords;

public class CartCoords
	{
		public float x;
		public float y;
		
		public CartCoords()
		{
			x = 0;
			y = 0;
		}
		
		public CartCoords(double a, double b)
		{
			x = (float) a;
			y = (float) b;
		}
	}
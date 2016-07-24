package Forces;

import java.util.ArrayList;
import CartCoords.CartCoords;
import Sprites.*;

@SuppressWarnings("SameParameterValue")
public class PathForce extends Force
	{
		//ATTRIBUTES
		private ArrayList<CartCoords> path_points;
		private float falloff;
		
		public PathForce()
		{
			path_points = new ArrayList<CartCoords>();
		}
		
		public PathForce addPoint(double x1, double y1)
		{
			path_points.add(new CartCoords(x1, y1));
			return this;
		}
		
		public PathForce applyForce(Sprite obj)
		{
			CartCoords[] pair;
			pair = new CartCoords[3];
			pair[0] = new CartCoords();
			pair[1] = new CartCoords();
			pair[2] = new CartCoords(10000, 10000);
			for (int cou = 0; cou <= path_points.size(); cou++)
			{
				int i = cou;
				int k = cou - 1;
				if (k < 0)
				{
					k = path_points.size() - 1;
				}
				i %= path_points.size();
				if (pair[2].x > distance(path_points.get(i).x, obj.get_x(), path_points.get(i).y, obj.get_y()) || pair[2].y > distance(path_points.get(k).x, obj.get_x(), path_points.get(k).y, obj.get_y()))
				{

					pair[0] = path_points.get(k);
					pair[1] = path_points.get(i);
					pair[2].x = distance(pair[0].x, obj.get_x(), pair[0].y, obj.get_y());
					pair[2].y = distance(pair[1].x, obj.get_x(), pair[1].y, obj.get_y());
				}
			}
			for (int i = 0; i < 10; i++)
			{
				float midpoint_distance = distance((pair[0].x + pair[1].x)/2, obj.get_x(), (pair[0].y + pair[1].y)/2, obj.get_y());
				if (midpoint_distance < pair[2].x)
				{
					pair[2].x = midpoint_distance;
					pair[0] = new CartCoords((pair[0].x + pair[1].x)/2, (pair[0].y + pair[1].y)/2);
				}
				else
				{
					pair[2].x = midpoint_distance;
					pair[1] = new CartCoords((pair[0].x + pair[1].x)/2, (pair[0].y + pair[1].y)/2);
				}
			}
			obj.set_dx(obj.get_dx() - get_str_x() * (pair[1].x - pair[0].x));
			obj.set_dy(obj.get_dy() - get_str_y() * (pair[1].y - pair[0].y));
			if (pair[2].x > falloff && pair[2].y > falloff)
			{
				obj.set_dx(obj.get_dx() - (obj.get_x() - ((pair[0].x + pair[1].x) / 2)) / falloff);
				obj.set_dy(obj.get_dy() - (obj.get_y() - ((pair[0].y + pair[1].y) / 2)) / falloff);
			}
			return this;
		}
		

		private float distance(double x1, double x2, double y1, double y2)
		{
			return (float) Math.sqrt(Math.pow((float) (x1 - x2), 2) + Math.pow((float) (y1- y2), 2));
		}
		
		//------------------------MUTATORS---------------------
		public PathForce set_falloff(double foff)
		{
			falloff = (float) foff;
			return this;
		}
		
		public PathForce set_path_points(ArrayList<CartCoords> path_points_) {
			path_points = path_points_;
			return this;
		}
		
		
		
		
	}
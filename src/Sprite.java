import java.awt.Color;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Sprite
	{
		private float x, y, angle, dx, dy, angular, step;
		private float[] collision_bounds;
		private int width, height;
		private boolean collides, vis_bounds;
		private Color alpha;
		private String image_path;
		private PApplet screen;
		
		protected PImage disp_image;
		protected PImage disp_image_mask;
		
		public Sprite()
		{
			
		}
		
		public Sprite(PApplet np, String ipath)
		{
			screen = np;
			setVis_bounds(false);
			set_x(screen.width/2);
			set_y(screen.height/2);
			set_dx(0);
			set_dy(0);
			set_angular(0);
			set_angle(0);
			step = 1;
			setCollides(false);
			image_path = ipath;
			disp_image = screen.loadImage(image_path);
			disp_image.loadPixels();
			disp_image_mask = disp_image.copy();
			alpha = new Color(disp_image.get(0, 0));
			width = disp_image.width;
			height = disp_image.height;
			Color cur_color;
			for (int i = 0; i < width; i++)
			{
				for (int k = 0; k < height; k++)
				{
					cur_color = new Color(disp_image.get(i, k));
					if (alpha.equals(cur_color))
					{
						disp_image_mask.set(i, k, 0);
					}
					else
					{
						disp_image_mask.set(i, k, -1);
					}
				}
			}
			disp_image_mask.updatePixels();
			disp_image.mask(disp_image_mask);
			collision_bounds = new float[]{screen.width/2, screen.height/2, screen.width/2, screen.height/2};
		}
		
		public Sprite draw()
		{
			screen.imageMode(PConstants.CENTER);
			screen.rectMode(PConstants.CENTER);
			if (isVis_bounds())
			{
				screen.fill(255, 255, 255, 10);
				screen.strokeWeight(0);
				screen.rect(collision_bounds[0], collision_bounds[1], collision_bounds[2] * 2, collision_bounds[3] * 2);
				screen.rectMode(PConstants.CORNER);
			}
			screen.pushMatrix();
			screen.translate(get_x(), get_y());
			screen.rotate(get_angle());
			screen.image(disp_image, 0, 0, width, height);
			screen.popMatrix();
			screen.imageMode(PConstants.CORNER);
			return this;
		}
		
		public Sprite img_path(String ipath)
		{
			image_path = ipath;
			disp_image = screen.loadImage(image_path);
			width = disp_image.width;
			height = disp_image.height;
			return this;
		}
		
		public Sprite set_pos_rad(float center_x, float center_y, float radius, float angle)
		{
			set_x((float) (center_x + radius * Math.cos(angle)));
			set_y((float) (center_y + radius * Math.sin(angle)));
			return this;
		}
		
		public Sprite set_collision_bounds(double rect_x, double rect_y, double rect_width, double rect_height)
		{
			collision_bounds = new float[]{(float) rect_x, (float) rect_y, (float) rect_width, (float) rect_height};
			return this;
		}
		
		public Sprite set_collision_bounds(float[] col_bounds)
		{
			collision_bounds = col_bounds.clone();
			return this;
		}
		
		public float[] get_collision_bounds()
		{
			return collision_bounds;
		}
		
		public Sprite set_step(double d)
		{
			step = (float) d;
			return this;
		}
		
		public Sprite set_step(float d)
		{
			step = d;
			return this;
		}
		
		public Sprite set_width(int swidth)
		{
			width = swidth;
			return this;
		}
		
		public Sprite set_height(int sheight)
		{
			height = sheight;
			return this;
		}
		
		public int get_height()
		{
			return height;
		}
		
		public int get_width()
		{
			return width;
		}
		
		public Sprite bounds_visible(boolean a)
		{
			setVis_bounds(a);
			return this;
		}
		
		public Sprite tick()
		{
			set_x(get_x() + step * get_dx());
			set_y(get_y() + step * get_dy());
			set_angle(get_angle() + step * get_angular());
			if (isCollides())
			{
				if (get_x() + (float) width/2 >= collision_bounds[0] + collision_bounds[2])
				{
					set_dx(get_dx() * -1);
					set_x((float) (collision_bounds[0] + collision_bounds[2]) - (float) width/2);
				}
				else if (get_x() - (float) width/2 <= collision_bounds[0] - collision_bounds[2])
				{
					set_dx(get_dx() * -1);
					set_x((float) (collision_bounds[0] - collision_bounds[2]) + (float) width/2);
				}
				if (get_y() + (float) height/2 >= collision_bounds[1] + collision_bounds[3])
				{
					set_dy(get_dy() * -1);
					set_y((float) (collision_bounds[1] + collision_bounds[3]) - (float) height/2);
				}
				else if (get_y() - (float) height/2 <= collision_bounds[1] - collision_bounds[3])
				{
					set_dy(get_dy() * -1);
					set_y((float) (collision_bounds[1] - collision_bounds[3]) + (float) height/2);
				}
			}
			else
			{
				if (get_x() - get_width()/2 > screen.width)
				{
					set_x(- get_width()/2);
				}
				else if (get_x() + get_width()/2 < 0)
				{
					set_x(screen.width + get_width()/2);
				}
				if (get_y() - get_height()/2 > screen.height)
				{
					set_y(- get_height()/2);
				}
				else if (get_y() + get_height()/2 < 0)
				{
					set_y(screen.height + get_height()/2);
				}
				
			}
			return this;
		}
		
		public boolean overlaps(Sprite e)
		{
			return (Math.abs(e.get_x() - get_x()) * 2 < (e.width + width)) &&
			         (Math.abs(e.get_y() - get_y()) * 2 < (e.height + height));
		}
		
		public PImage xReversePImage( PImage image ) 
		{
			PImage reverse;
			reverse = screen.createImage(image.width, image.height, PConstants.ARGB );
		
			for( int i=0; i < image.width; i++ )
			{
					for(int j=0; j < image.height; j++)
					{
						int xPixel, yPixel;
						xPixel = image.width - 1 - i;
						yPixel = j;
						reverse.pixels[yPixel*image.width+xPixel]=image.pixels[j*image.width+i] ;
					}
			}
			return reverse;
		}
		
		public PImage yReversePImage( PImage image ) 
		{
			PImage reverse;
			reverse = screen.createImage(image.width, image.height, PConstants.ARGB );
		
			for( int i=0; i < image.width; i++ )
			{
					for(int j=0; j < image.height; j++)
					{
						int xPixel, yPixel;
						xPixel = i;
						yPixel = image.height - j - 1;
						reverse.pixels[yPixel*image.width+xPixel]=image.pixels[j*image.width+i] ;
					}
			}
			return reverse;
		}
		
		public float get_x() { return x; }

		public Sprite set_x(float x) {
			this.x = x;
			return this;
		}

		public float get_y() {
			return y;
		}

		public Sprite set_y(float get_y) {
			y = get_y;
			return this;
		}

		public float get_dx() {
			return dx;
		}

		public Sprite set_dx(float get_dx) {
			dx = get_dx;
			return this;
		}

		public float get_dy() {
			return dy;
		}

		public Sprite set_dy(float get_dy) {
			dy = get_dy;
			return this;
		}

		public float get_angle() {
			return angle;
		}

		public Sprite set_angle(float get_angle) {
			angle = get_angle;
			return this;
		}

		public float get_angular() {
			return angular;
		}

		public Sprite set_angular(float get_angular) {
			angular = get_angular;
			return this;
		}

		public boolean isCollides() {
			return collides;
		}

		public Sprite setCollides(boolean get_collides) {
			collides = get_collides;
			return this;
		}

		public boolean isVis_bounds() {
			return vis_bounds;
		}

		public Sprite setVis_bounds(boolean get_vis_bounds) {
			vis_bounds = get_vis_bounds;
			return this;
		}

		public float get_step() {
			return step;
		}
		
		public Sprite set_disp_image(PImage img) {
			disp_image = img;
			return this;
		}

		public PImage get_disp_image() {
			return disp_image;
		}

		public Color get_alpha() {
			return alpha;
		}
		
		public Sprite set_alpha(Color alph)
		{
			alpha = alph;
			return this;
		}
		
		public PImage get_disp_image_mask()
		{
			return disp_image_mask;
		}
		
		public Sprite set_disp_image_mask(PImage img)
		{
			disp_image_mask = img;
			return this;
		}

		public String get_image_path() {
			return image_path;
		}
		
		public Sprite set_image_path(String fn)
		{
			image_path = fn;
			return this;
		}

		public PApplet get_screen() {
			return screen;
		}
		
		public Sprite set_screen(PApplet scr)
		{
			screen = scr;
			return this;
		}
	}
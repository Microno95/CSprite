import java.awt.Color;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class AnimationSprite extends Sprite
	{
		private int frameTime = 1;
		private PImage base_image;
		private PImage[] frames;
		private boolean xOrientation = false;
		private boolean yOrientation = false;
		private boolean reorient = false;
		private int[] animDims = {0};

        public AnimationSprite()
        {

        }

		public AnimationSprite(PApplet np)
		{
		    set_screen(np);
			set_frameTime(1);
			base_image = get_screen().createImage(1, 1, PConstants.ARGB);
		}
		
		public AnimationSprite(PApplet np, String ipath)
		{
			set_screen(np);
			setVis_bounds(false);
			set_x(get_screen().width/2);
			set_y(get_screen().height/2);
			set_dx(0);
			set_dy(0);
			set_angular(0);
			set_angle(0);
			set_step(1);
			setCollides(false);
			set_image_path(ipath);
			base_image = get_screen().loadImage(get_image_path());
			base_image.loadPixels();
			disp_image_mask = base_image.copy();
			set_alpha(new Color(base_image.get(0, 0)));
			set_width(base_image.width);
			set_height(base_image.height);
			Color cur_color;
			for (int i = 0; i < get_width(); i++)
			{
				for (int k = 0; k < get_height(); k++)
				{
					cur_color = new Color(base_image.get(i, k));
					if (get_alpha().equals(cur_color))
					{
						get_disp_image_mask().set(i, k, 0);
					}
					else
					{
						get_disp_image_mask().set(i, k, -1);
					}
				}
			}
			get_disp_image_mask().updatePixels();
			base_image.mask(get_disp_image_mask());
			frames = new PImage[]{base_image.copy()};
			set_disp_image(frames[0].copy());
			set_collision_bounds(new float[]{get_screen().width/2, get_screen().height/2, get_screen().width/2, get_screen().height/2});
			set_animDims(new int[]{get_width(), get_height(), -1, 0, 0});
			set_frameTime(10);
		}
		
		
		public AnimationSprite animTick()
		{
			if (get_screen().frameCount % frameTime == 0)
			{
				animDims[3]++;
			}
			if ((animDims[3] >= animDims[2] && animDims[2] >= 0) || animDims[3] >= frames.length)
			{
				animDims[4]++;
				animDims[3] = 0;
			}
			disp_image = frames[animDims[3]];
			if (xOrientation && reorient)
			{
				disp_image = xReversePImage(disp_image);
			}
			if (yOrientation && reorient)
			{
				disp_image = yReversePImage(disp_image);
			}
			return this;
		}
		
		public Sprite tick()
		{
			set_x(get_x() + get_step() * get_dx());
			set_y(get_y() + get_step() * get_dy());
			set_angle(get_angle() + get_step() * get_angular());
			if (isCollides())
			{
				if (get_x() + (float) get_width()/2 >= get_collision_bounds()[0] + get_collision_bounds()[2])
				{
					set_dx(get_dx() * -1);
					xOrientation = !xOrientation;
					set_x((float) (get_collision_bounds()[0] + get_collision_bounds()[2]) - (float) get_width()/2);
				}
				else if (get_x() - (float) get_width()/2 <= get_collision_bounds()[0] - get_collision_bounds()[2])
				{
					set_dx(get_dx() * -1);
					xOrientation = !xOrientation;
					set_x((float) (get_collision_bounds()[0] - get_collision_bounds()[2]) + (float) get_width()/2);
				}
				if (get_y() + (float) get_height()/2 >= get_collision_bounds()[1] + get_collision_bounds()[3])
				{
					set_dy(get_dy() * -1);
					yOrientation = !yOrientation;
					set_y((float) (get_collision_bounds()[1] + get_collision_bounds()[3]) - (float) get_height()/2);
				}
				else if (get_y() - (float) get_height()/2 <= get_collision_bounds()[1] - get_collision_bounds()[3])
				{
					set_dy(get_dy() * -1);
					yOrientation = !yOrientation;
					set_y((float) (get_collision_bounds()[1] - get_collision_bounds()[3]) + (float) get_height()/2);
				}
			}
			return this;
		}

		public AnimationSprite set_animDims(int[] get_is) {
			animDims = get_is;
			return this;
		}

		public AnimationSprite set_animDims(int width, int height, int loopCount)
		{
			animDims[0] = width;
			animDims[1] = height;
			animDims[2] = loopCount;
			frames = new PImage[ (get_width() / width) * (get_height() / height)];
			for (int fr = 0; fr < (get_width() / width) * (get_height() / height); fr++)
			{
				if ((animDims[4] < animDims[2] || animDims[2] == -1))
				{
					int row = fr * animDims[0];
					int col = 0;
					if (row >= base_image.width)
					{
						col = (row / base_image.width) * animDims[1];
						row %= base_image.width;
						if (col >= base_image.height)
						{
							col %= base_image.height;
						}
					}
					frames[fr] = base_image.get(row, col, animDims[0], animDims[1]);
					frames[fr].loadPixels();
					disp_image_mask = frames[fr].copy();
					Color cur_color;
					for (int i = 0; i < base_image.width; i++)
					{
						for (int k = 0; k < base_image.height; k++)
						{
							cur_color = new Color(frames[fr].get(i, k));
							if (get_alpha().equals(cur_color))
							{
								get_disp_image_mask().set(i, k, 0);
							}
							else
							{
								get_disp_image_mask().set(i, k, -1);
							}
						}
					}
					get_disp_image_mask().updatePixels();
					frames[fr].mask(get_disp_image_mask());
				}
			}
			base_image = new PImage();
			return this;
		}
		
		public AnimationSprite set_frameTime(int ft)
		{
			frameTime = ft;
			return this;
		}
		
		public AnimationSprite set_reorient(boolean yeha)
		{
			reorient = yeha;
			return this;
		}
		
		public AnimationSprite set_xOrientation(boolean xOrientation_) {
			xOrientation = xOrientation_;
			return this;
		}
		
		public AnimationSprite set_yOrientation(boolean yOrientation_) {
			yOrientation = yOrientation_;
			return this;
		}
		
		public AnimationSprite set_base_image(PImage image_) {
			base_image = image_;
			return this;
		}
		
		public AnimationSprite set_frames(PImage[] frames_) {
			frames = frames_;
			return this;
		}

		public int get_frameTime() { return frameTime; }
		
		public boolean get_xOrientation() {
			return xOrientation;
		}
		
		public boolean get_yOrientation() {
			return yOrientation;
		}
		
		public boolean get_reorient() {
			return reorient;
		}
		
		public int[] get_animDims() { return animDims; }
		
		public PImage get_base_image() { return base_image; }
		
		public PImage[] get_frames() { return frames; }
	}



	
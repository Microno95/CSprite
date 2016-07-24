public class AnimSpriteInstance extends AnimationSprite
	{
		public AnimSpriteInstance(AnimationSprite obj) 
		{
			set_screen(obj.get_screen());
			set_x(1);
			set_y(1);
			set_dx(1);
			set_dy(1);
			set_angle(0);
			set_angular(0);
			setCollides(false);
			setVis_bounds(false);
			set_width(obj.get_width());
			set_height(obj.get_height());
			set_collision_bounds(obj.get_collision_bounds().clone());
			set_xOrientation(false); //xOrientation = false;
			set_yOrientation(false);
			set_frameTime(obj.get_frameTime());
			set_animDims(obj.get_animDims());
			set_frames(obj.get_frames());
			//frames = obj.frames;
		}
		
		public AnimSpriteInstance set_animDims()
		{
			return this;
		}
	}
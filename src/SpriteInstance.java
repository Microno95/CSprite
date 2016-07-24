class SpriteInstance extends Sprite
	{
		public SpriteInstance(Sprite obj)
		{
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
			set_step(obj.get_step());
			set_disp_image(obj.get_disp_image());
			set_alpha(obj.get_alpha());
			set_image_path(obj.get_image_path());
			set_screen(obj.get_screen());
		}
	}
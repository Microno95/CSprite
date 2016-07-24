import java.util.ArrayList;

import Forces.*;
import processing.core.PApplet;
import Sprites.*;

public class CSprite extends PApplet {
	public static void main(String[] args) {
		PApplet.main("CSprite");
	}

	int to_draw;
	ArrayList<SpriteInstance> fallen;
	Sprite fallen2;
	static int TOT_ADDITION = 10000;
	DampingForce damper;
	AngularDampingForce angdamper;
	Force wave;
	PointForce pointerFollow;
	PathForce path;
	Turbulence turb;
    Obstacle obs;

	public void settings() {
		size(1280, 720, P2D);
	}

	public void setup() {
		frameRate(80);
		surface.setResizable(true);
		to_draw = 0;
		fallen2 = new Sprite(this, "mushroom.png");
		fallen2.set_x(500).set_y(500).set_dy(2).set_dx(5).setCollides(true);
		fallen = new ArrayList<>(1);
		fallen.add(new SpriteInstance(fallen2));
		fallen.get(0).setCollides(true).set_x(500).set_y(500).set_dx(0).set_dy(0);
		damper = new DampingForce();
		damper.set_str_x(.95);
		damper.set_str_y(.95);
		angdamper = new AngularDampingForce();
		angdamper.set_str_x(0.95);
		wave = new Force();
		wave.set_str_x(0);
		wave.set_str_y(-1);
		pointerFollow = new PointForce();
		pointerFollow.set_str_x(-1);
		pointerFollow.set_str_y(-1);
		path = new PathForce();
		path.addPoint(width / 3, height / 2);
		path.addPoint(width / 2, height / 3);
		path.addPoint(5 * width / 6, 5 * height / 6);
		path.set_str_x(-1);
		path.set_str_y(-1);
		path.set_falloff(50);
		turb = new Turbulence(this);
		turb.set_str_x(4);
		turb.set_str_y(4);
		turb.set_scale(10);
        obs = new Obstacle(200, 200, 100, 0);
	}

	public void draw() {
		background(128);
		if (to_draw == 1) {
			for (SpriteInstance i : fallen) {
				wave.set_str_y(-1 * sin(frameCount / frameRate) + random(-10, 10) / 10);
				wave.applyForce(i);
				angdamper.applyForce(i);
				damper.applyForce(i);
				pointerFollow.set_x(mouseX);
				pointerFollow.set_y(mouseY);
				pointerFollow.applyForce(i);
				turb.applyForce(i);
				obs.overlaps(i);
				i.tick().draw();
			}
			fallen2.tick().draw().set_collision_bounds(width / 2, height / 2, width / 2, height / 2);
		}
		strokeWeight(1);
		textAlign(LEFT, TOP);
		textSize(24);
		text("FPS: " + frameRate, 0, 0);
		textAlign(RIGHT, TOP);
		textSize(30);
		text("Sprite Count: " + fallen.size(), width, 0);
	}

	public void mousePressed() {
		if (mouseButton == LEFT) {
			to_draw = 1;
			fallen.add(new SpriteInstance(fallen2));
			fallen.get(fallen.size() - 1).setCollides(true).set_dx(random(-10, 10)).set_dy(random(-10, 10))
					.set_angular(random(-1, 1) / 10).set_x(mouseX).set_y(mouseY).set_step(1)
					.bounds_visible(false);
		} else if (mouseButton == RIGHT) {
			to_draw = 2;
		} else {
			fallen = new ArrayList<>(1);
			fallen.add(new SpriteInstance(fallen2));
			fallen.get(0).setCollides(true).set_dx(-2).set_dy(3).set_angular(3);
		}
		redraw();
	}

	public void keyPressed() {
		if (key == 'a' || key == 'A') {
			for (int i = 0; i < TOT_ADDITION; i++) {
				fallen.add(new SpriteInstance(fallen2));
				fallen.get(fallen.size() - 1).setCollides(true).set_dx(random(-10, 10)).set_dy(random(-10, 10))
						.set_angular(random(-1, 1) / 10)
						.set_pos_rad(width / 2, height / 2, random(0, height / 4), random(0, 2 * PI)).set_step(1)
						.bounds_visible(false);
			}
		}
	}
}
package com.xinghen.multiThreads;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.xinghen.multiThreads.BounceFrame.Ball;
import com.xinghen.multiThreads.BounceFrame.BallComponent;

public class MultiThreads {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new BounceFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

class BounceFrame extends JFrame {
	private BallComponent comp;
	private static final int STEPS = 1000;
	private static final int DELAY = 3;

	public BounceFrame() {
		setTitle("Bounce");
		comp = new BallComponent();
		add(comp, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		addButton(buttonPanel, "Start", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addBall();
			}
		});
		addButton(buttonPanel, "Close", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		add(buttonPanel, BorderLayout.SOUTH);
		pack();

	}

	public void addButton(Container c, String title, ActionListener listener) {
		JButton button = new JButton(title);
		c.add(button);
		button.addActionListener(listener);
	}

	public void addBall() {
		Ball b = new Ball();
		BallRunnable br = new BallRunnable(b, comp);
		Thread tr = new Thread(br);
		tr.start();
	}

	class BallComponent extends JPanel {
		private static final int DEFAULT_WIDTH = 450;
		private static final int DEFAULT_HEIGHT = 450;
		private java.util.List<Ball> balls = new ArrayList<Ball>();

		public void add(Ball ball) {
			balls.add(ball);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			for (Ball b : balls) {
				g2.fill(b.getShape());
			}
		}

		public Dimension getPreferredSize() {
			return new Dimension(DEFAULT_HEIGHT, DEFAULT_WIDTH);
		}
	}

	class Ball {
		private static final int XSIZE = 15;
		private static final int YSIZE = 15;
		private double x = 0;
		private double y = 0;
		private double dx = 1;
		private double dy = 1;

		public Ball() {
			x = Math.random() * 350;
			y = Math.random() * 450;
			if (Math.random() * 2 - 2 > 0)
				dx = 1;
			else
				dx = -1;
			if (Math.random() * 2 - 2 > 0)
				dy = 1;
			else
				dy = -1;

		}

		public void move(Rectangle2D rt) {
			x += dx;
			y += dy;

			if (x < rt.getMinX()) {
				dx = -dx;
				x = rt.getMinX();
			}
			if (x > rt.getMaxX()) {
				dx = -dx;
				x = rt.getMaxX() - XSIZE;
			}
			if (y < rt.getMinY()) {
				dy = -dy;
				y = rt.getMinY();
			}
			if (y > rt.getMaxY()) {
				dy = -dy;
				y = rt.getMaxY() - YSIZE;
			}
			System.out.println("x:" + x + ",y:" + y + ",dx:" + dx + ",dy:" + dy + ",maxx:" + rt.getMaxX() + ",minx:" + rt.getMinX() + ",miny:" + rt.getMinY() + ",maxy :" + rt.getMaxY());

		}

		public Ellipse2D getShape() {
			return new Ellipse2D.Double(x, y, XSIZE, YSIZE);
		}
	}
}

class BallRunnable implements Runnable {
	private Ball ball;
	private BallComponent comp;
	private static final int STEPS = 10000;
	private static final int DELAY = 3;

	public BallRunnable(Ball ball, BallComponent comp) {
		this.ball = ball;
		this.comp = comp;
	}

	@Override
	public void run() {
		try {
			comp.add(ball);
			for (int i = 0; i < STEPS; i++) {
				ball.move(comp.getBounds());
				comp.paint(comp.getGraphics());
				Thread.sleep(DELAY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

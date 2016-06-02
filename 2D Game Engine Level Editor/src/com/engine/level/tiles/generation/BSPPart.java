package com.engine.level.tiles.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BSPPart {

	private float width;
	private float height;
	private float x, y;

	private BSPPart parent;
	private int depth = 0;

	/**
	 * used to alternate the divisions of the space
	 * */
	private boolean createdFromHorizontalDivide = false;

	private BSPPart childA;
	private BSPPart childB;

	private final int maxDepth;

	private Random rand;

	/**
	 * if parent is null this will create a root, else it will establish a child part. Generating children will set up the net generation
	 * */
	public BSPPart(BSPPart parent, int depth, Random random, int maxDepth) {
		this.parent = parent;
		this.depth = depth;
		rand = random;
		this.maxDepth = maxDepth;
	}

	public BSPPart(BSPPart parent, int depth, int maxDepth) {
		this(parent, depth, new Random(), maxDepth);
	}

	public BSPPart setRectParts(float width, float height, float x, float y, boolean fromHorizontal) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.createdFromHorizontalDivide = fromHorizontal;
		return this;
	}

	public void genChildren() {
		if (this.depth >= maxDepth) {
			System.out.println("BSPPart '" + toString() + "' has reached the maximum depth allowed");
			return;
		}
		childA = new BSPPart(this, getDepth() + 1, rand, maxDepth);
		childB = new BSPPart(this, getDepth() + 1, rand, maxDepth);
		if (createdFromHorizontalDivide) {
			float divide = rand.nextFloat() * height;
			float heightA = divide;
			float heightB = height - divide;
			childA.setRectParts(width, heightA, x, y, false);
			childB.setRectParts(width, heightB, x, y + divide, false);
		} else {
			float divide = rand.nextFloat() * width;
			float widthA = divide;
			float widthB = width - divide;
			childA.setRectParts(widthA, height, x, y, false);
			childB.setRectParts(widthB, height, x + divide, y, false);
		}
	}

	public List<BSPPart> getAllDescendents(int depthOfSearch) {
		List<BSPPart> children = new ArrayList<BSPPart>();
		if (isLeaf()) return children;
		children.add(childA);
		children.add(childB);
		List<BSPPart> children_queue = new ArrayList<BSPPart>();
		for (int i = 0; i < depthOfSearch; i++) {
			for (BSPPart part : children) {
				if (part.isLeaf()) continue;
				BSPPart a = part.getChildA();
				BSPPart b = part.getChildB();
				if (!children.contains(a)) if (a != null) children_queue.add(a);
				if (!children.contains(b)) if (b != null) children_queue.add(b);
			}
			children.addAll(children_queue);
		}
		return children;
	}

	public void generateAll() {
		genChildren();
		if (childA != null) childA.generateAll();
		if (childB != null) childA.generateAll();
	}

	public boolean isLeaf() {
		return childA == null && childB == null;
	}

	public boolean isRoot() {
		return parent == null;
	}

	/**
	 * @return the parent
	 */
	public BSPPart getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(BSPPart parent) {
		this.parent = parent;
	}

	/**
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * @param depth the depth to set
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return the createdFromHorizontalDivide
	 */
	public boolean isCreatedFromHorizontalDivide() {
		return createdFromHorizontalDivide;
	}

	/**
	 * @param createdFromHorizontalDivide the createdFromHorizontalDivide to set
	 */
	public void setCreatedFromHorizontalDivide(boolean createdFromHorizontalDivide) {
		this.createdFromHorizontalDivide = createdFromHorizontalDivide;
	}

	/**
	 * @return the childA
	 */
	public BSPPart getChildA() {
		return childA;
	}

	/**
	 * @param childA the childA to set
	 */
	public void setChildA(BSPPart childA) {
		this.childA = childA;
	}

	/**
	 * @return the childB
	 */
	public BSPPart getChildB() {
		return childB;
	}

	/**
	 * @param childB the childB to set
	 */
	public void setChildB(BSPPart childB) {
		this.childB = childB;
	}

}

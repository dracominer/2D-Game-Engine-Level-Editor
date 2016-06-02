package com.engine.items;

import java.util.HashMap;
import java.util.Map;

import com.engine.items.util.ActionHandler;
import com.engine.render.textures.ModelTexture;

public class Item {

	protected ModelTexture texture;
	protected String name = "defaultName";

	protected int maxGroupSize = 99;

	protected ActionHandler handler = new ActionHandler();

	private Map<String, String> attributes = new HashMap<String, String>();

	public Item(ModelTexture texture, String name) {
		this.texture = texture;
		this.name = name;
	}

	protected Item(ModelTexture texture, String name, Map<String, String> attributes, ActionHandler handler) {
		this.texture = texture;
		this.name = name;
		this.attributes = attributes;
		this.handler = handler;
	}

	public void registerAttribute(String name, String value) {
		attributes.put(name, value);
	}

	public String getAttriibute(String name) {
		return attributes.get(name);
	}

	/**
	 * @return the handler
	 */
	public ActionHandler getHandler() {
		return handler;
	}

	/**
	 * @param handler the handler to set
	 */
	public void setHandler(ActionHandler handler) {
		this.handler = handler;
	}

	/**
	 * @return the attributes
	 */
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the texture
	 */
	public ModelTexture getTexture() {
		return texture;
	}

	/**
	 * @param texture the texture to set
	 */
	public void setTexture(ModelTexture texture) {
		this.texture = texture;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * @return the maxGroupSize
	 */
	public int getMaxGroupSize() {
		return maxGroupSize;
	}

	/**
	 * @param maxGroupSize the maxGroupSize to set
	 */
	public void setMaxGroupSize(int maxGroupSize) {
		this.maxGroupSize = maxGroupSize;
	}

	public Item copy() {
		System.out.println("Copying item: " + name);
		return new Item(texture, name, attributes, handler);
	}

}

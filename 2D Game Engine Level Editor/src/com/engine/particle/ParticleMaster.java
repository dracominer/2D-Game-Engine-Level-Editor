package com.engine.particle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.util.vector.Matrix4f;

import com.engine.camera.Camera;
import com.engine.render.ParticleRenderer;
import com.engine.toolbox.InsertionSort;

public class ParticleMaster {

	private static Map<ParticleTexture, List<Particle>> particles = new HashMap<ParticleTexture, List<Particle>>();
	
	public static final float ParticleStartHeight = 0.1f;

	private static ParticleRenderer renderer;

	public static void init(Matrix4f projectionMatrix) {
		renderer = new ParticleRenderer(projectionMatrix);
	}

	public static void addParticle(Particle p) {
		List<Particle> list = particles.get(p.getTexture());
		if (list == null) {
			list = new ArrayList<Particle>();
			particles.put(p.getTexture(), list);
		}
		list.add(p);
	}

	public static void update(Camera cam) {
		Iterator<Entry<ParticleTexture, List<Particle>>> mapIterator = particles.entrySet().iterator();
		while (mapIterator.hasNext()) {
			Entry<ParticleTexture, List<Particle>> entry = mapIterator.next();
			List<Particle> list = entry.getValue();
			Iterator<Particle> iterator = list.iterator();
			while (iterator.hasNext()) {
				Particle p = iterator.next();
				boolean stillAlive = p.update(cam);
				if (!stillAlive) {
					iterator.remove();
					if (list.isEmpty()) {
						mapIterator.remove();
					}
				}
			}
			if (!entry.getKey().usesAdditive()) {
				InsertionSort.sortHighToLow(list);
			}
		}
	}

	public static void render(Camera cam) {
		renderer.render(particles, cam);
	}

	public static void cleanup() {
		renderer.cleanUp();
	}

}

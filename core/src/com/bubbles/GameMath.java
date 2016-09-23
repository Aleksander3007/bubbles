package com.bubbles;

import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;

public class GameMath {
	/**
	 * Генерация одного из объектов из списка, содержащего вероятность выпадения каждого из этих объектов.
	 * @param valProbabilities Список объектов с вероятностью выпадения кажого из них.
	 * @return Случайный объект из списка.
	 */
	public static <T> T generateValue(ArrayMap<T, Float> objectProbabilities) {
		int sumProbabilities = 0;
		for (ObjectMap.Entry<T, Float> objectProbability : objectProbabilities) {
			sumProbabilities += objectProbability.value;
		}
		
		double randomVal = Math.random() * sumProbabilities;
		double range = 0;
		for (ObjectMap.Entry<T, Float> objectProbability : objectProbabilities) {
			range += objectProbability.value;
			if (randomVal <= range) {
				return objectProbability.key;
			}
		}
		
		return objectProbabilities.getKeyAt(0);
	}
}

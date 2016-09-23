package com.bubbles;

import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;

public class GameMath {
	/**
	 * ��������� ������ �� �������� �� ������, ����������� ����������� ��������� ������� �� ���� ��������.
	 * @param valProbabilities ������ �������� � ������������ ��������� ������ �� ���.
	 * @return ��������� ������ �� ������.
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

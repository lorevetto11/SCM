package com.gpi.scm.generic.utils;

public class CommonEnums {

	public enum materialType {
		PACKAGING, CLEANING, FOOD, EQUIPMENT
	}

	public enum flowShapeType {
		RECTANGLE, ELLIPSE, RHOMBUS, PARALLELOGRAM
	}

	public enum flowElementsType {
		BEGIN_END, PHASE, MATERIAL, DECISION
	}

	public enum flowRelationsType {
		BEGIN_END, PHASE, MATERIAL, DECISION, REGULAR, BIDIRECTIONAL
	}

	public enum ccpType {
		CCP, OPRP, NONE
	}
}

package com.example.victim;

import java.util.List;

final class DemoDataFactory {

	private DemoDataFactory() {
	}

	static Order order() {
		return new Order(
				"ORD-42",
				"gold",
				"card",
				false,
				List.of(
						new OrderLine("laptop", 2, 850, true, false),
						new OrderLine("adapter", 1, 30, false, true),
						new OrderLine("warranty", 1, 120, false, false)));
	}
}

package com.example.victim;

import java.util.List;

public record Order(
		String id,
		String customerTier,
		String paymentType,
		boolean fraudSuspected,
		List<OrderLine> lines) {
}

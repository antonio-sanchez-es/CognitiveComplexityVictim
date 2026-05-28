package com.example.victim;

public class LegacyOrderProcessor {

	public int calculateFulfillmentScore(Order order, String region, boolean express) {
		int score = 0;

		if (order == null) {
			return -100;
		}

		if (order.id() == null || order.id().isBlank()) {
			score -= 40;
		} else {
			if (order.id().startsWith("ORD")) {
				score += 5;
			} else {
				score -= 5;
			}
		}

		if (order.lines().isEmpty()) {
			score -= 80;
		} else {
			for (OrderLine line : order.lines()) {
				if (line.quantity() <= 0) {
					score -= 30;
				} else {
					if (line.unitPrice() <= 0) {
						score -= 25;
					} else {
						score += line.quantity() * 2;
						if (line.unitPrice() > 500) {
							if (line.insured()) {
								score += 12;
							} else {
								score -= 18;
							}
						} else if (line.unitPrice() > 100) {
							score += 6;
						} else {
							if (line.fragile()) {
								score -= 6;
							} else {
								score += 2;
							}
						}
					}
				}

				if (line.fragile()) {
					if ("EU".equals(region)) {
						score += 4;
					} else if ("US".equals(region)) {
						score += 3;
					} else {
						score -= 4;
					}
				}
			}
		}

		if ("gold".equals(order.customerTier())) {
			if (express) {
				score += 20;
			} else {
				score += 12;
			}
		} else if ("silver".equals(order.customerTier())) {
			if (express) {
				score += 8;
			} else {
				score += 4;
			}
		} else {
			if (express) {
				score -= 8;
			} else {
				score -= 2;
			}
		}

		if (order.fraudSuspected()) {
			score -= 100;
		} else {
			if ("card".equals(order.paymentType())) {
				score += 5;
			} else if ("invoice".equals(order.paymentType())) {
				if ("gold".equals(order.customerTier())) {
					score += 4;
				} else {
					score -= 12;
				}
			} else {
				score -= 8;
			}
		}

		return score;
	}

	public String decideShippingLane(Order order, String region, boolean weekend, int warehouseLoad) {
		String lane = "standard";

		if (order == null || order.lines().isEmpty()) {
			return "hold";
		}

		for (OrderLine line : order.lines()) {
			if (line.fragile()) {
				if (line.unitPrice() > 500) {
					if (weekend) {
						lane = "insured-monday";
					} else {
						lane = "insured-priority";
					}
				} else {
					if (warehouseLoad > 80) {
						lane = "fragile-delayed";
					} else {
						lane = "fragile";
					}
				}
			} else if (line.quantity() > 10) {
				if ("EU".equals(region)) {
					lane = "bulk-eu";
				} else if ("US".equals(region)) {
					lane = "bulk-us";
				} else {
					lane = "bulk-international";
				}
			} else {
				if (warehouseLoad > 90) {
					lane = "overflow";
				}
			}
		}

		if (order.fraudSuspected()) {
			lane = "manual-review";
		} else if ("gold".equals(order.customerTier())) {
			if (weekend) {
				lane = lane + "-vip-weekend";
			} else {
				lane = lane + "-vip";
			}
		}

		return lane;
	}
}

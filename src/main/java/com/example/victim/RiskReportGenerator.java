package com.example.victim;

import java.util.List;

public class RiskReportGenerator {

	public String buildRiskReport(List<Order> orders, boolean includeDetails) {
		StringBuilder report = new StringBuilder();
		int critical = 0;
		int warning = 0;

		if (orders == null || orders.isEmpty()) {
			return "No orders";
		}

		for (Order order : orders) {
			int risk = 0;

			if (order.fraudSuspected()) {
				risk += 80;
				if (includeDetails) {
					report.append(order.id()).append(": fraud flag\n");
				}
			}

			if (order.lines().isEmpty()) {
				risk += 50;
			} else {
				for (OrderLine line : order.lines()) {
					if (line.quantity() <= 0) {
						risk += 30;
					} else {
						if (line.quantity() > 20) {
							risk += 10;
						}
					}

					if (line.unitPrice() <= 0) {
						risk += 40;
					} else if (line.unitPrice() > 1000) {
						if (line.insured()) {
							risk += 5;
						} else {
							risk += 25;
						}
					} else if (line.unitPrice() > 500) {
						risk += 10;
					}

					if (line.fragile()) {
						if (line.insured()) {
							risk -= 2;
						} else {
							risk += 8;
						}
					}
				}
			}

			if ("gold".equals(order.customerTier())) {
				risk -= 8;
			} else if ("silver".equals(order.customerTier())) {
				risk -= 3;
			} else {
				if ("invoice".equals(order.paymentType())) {
					risk += 14;
				} else {
					risk += 4;
				}
			}

			if (risk >= 80) {
				critical++;
				report.append(order.id()).append(": critical\n");
			} else if (risk >= 35) {
				warning++;
				report.append(order.id()).append(": warning\n");
			} else if (includeDetails) {
				report.append(order.id()).append(": ok\n");
			}
		}

		report.append("critical=").append(critical).append(", warning=").append(warning);
		return report.toString();
	}
}

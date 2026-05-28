package com.example.victim;

import java.util.List;

public class App {

	public static void main(String[] args) {
		LegacyOrderProcessor processor = new LegacyOrderProcessor();
		Order order = DemoDataFactory.order();
		int score = processor.calculateFulfillmentScore(order, "EU", true);

		RiskReportGenerator generator = new RiskReportGenerator();
		String report = generator.buildRiskReport(List.of(order), true);

		System.out.println("Score: " + score);
		System.out.println(report);
	}
}

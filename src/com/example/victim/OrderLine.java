package com.example.victim;

public record OrderLine(
		String sku,
		int quantity,
		int unitPrice,
		boolean fragile,
		boolean insured) {
}

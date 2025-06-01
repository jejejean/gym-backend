package com.gym.shared.Utils;

import com.gym.shared.interfaces.LastNumberProvider;

import java.time.Year;

public class GenerateNumber {

    public static String generateNextNumber(LastNumberProvider lastNumberProvider) {
        int currentYear = Year.now().getValue() % 100;

        String lastServiceNumber = lastNumberProvider.findLastNumber()
                .orElse("00000-" + currentYear);

        String[] parts = lastServiceNumber.split("-");
        int lastSequentialNumber = Integer.parseInt(parts[0]);
        int newSequentialNumber = lastSequentialNumber + 1;

        return String.format("%05d-%02d", newSequentialNumber, currentYear);
    }
}
